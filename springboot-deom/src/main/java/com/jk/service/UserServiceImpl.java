package com.jk.service;

import com.jk.mapper.UserMapper;
import com.jk.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserMapper userMapper;


    @Override
    public List<TreeBean> queryTree(HttpSession session) {
        UserBean user = (UserBean) session.getAttribute("user");
        Integer userid = user.getId();
        int pid = 0;
        return queryNodes(pid,userid);
    }



    private List<TreeBean> queryNodes(int pid,int userid) {
        List<TreeBean> list = userMapper.queryTreeByid(pid);
        for (TreeBean treeBean : list) {
            Integer id = treeBean.getId();
            //查询对应的子节点
            List<TreeBean> nodes = queryNodes(id,userid);//递归：自己调自己
            treeBean.setChildren(nodes);
        }
        return list;
    }
    @Override
    public HashMap<String, Object> queryUserPage(Integer page, Integer rows, UserBean user) {
        int total = userMapper.queryCount(user);
        int start = (page-1)*rows;
        List<UserBean> list = userMapper.queryUserPage(start,rows,user);
        HashMap<String,Object> map = new HashMap<String, Object>();
        map.put("total",total);
        map.put("rows",list);
        return map;
    }

    @Override
    public List<RoleBean> queryRole() {

        return userMapper.queryRole();
    }

    @Override
    public List<MenuBean> queryMenu(Integer powerid) {
        return userMapper.queryMenu(powerid);
    }

    @Override
    public List<TreeBean> queryPowerTree(Integer roleid) {

        List<TreeBean> rolePower = userMapper.queryPowerTreeByRoleid(roleid);
        int pid = 0;
        List<TreeBean> list = queryPowerNodes(pid,rolePower);
        //添加虚拟的根节点
        TreeBean tree = new TreeBean();
        tree.setId(0);
        tree.setPid(-1);
        tree.setText("根节点");
        tree.setChildren(list);

        ArrayList<TreeBean> list2 = new ArrayList<TreeBean>();
        list2.add(tree);
        return list2;
    }

    @Override
    public void addPower(TreeBean power) {
        userMapper.addPower(power);
    }

    @Override
    public void deletePowerById(Integer powerid) {
        userMapper.deletePowerById(powerid);
    }

    @Override
    public void saveRolePower(Integer roleid, String[] ids) {
        //删除
        userMapper.deleteByRoleid(roleid);
        //新增
        if(ids.length>0){
            userMapper.addRolePower(roleid,ids);
        }
    }

    @Override
    public UserBean queryUserName(String loginNumber) {
        return userMapper.queryUserName(loginNumber);
    }

    @Override
    public void zhuceuser(UserBean u) {
        userMapper.zhuceuser(u);
    }

    @Override
    public UserBean queryUserById(Integer userId) {

        return userMapper.queryUserById(userId);
    }

    @Override
    public List<AreaBean> queryArea(Integer pid) {

        return userMapper.queryArea(pid);
    }

    @Override
    public List<DeptBean> queryDept() {
        int pid = 0;
        List<DeptBean> list = queryDeptNodes(pid);
        return list;
    }

    @Override
    public void addUser(UserBean user) {
        Integer id = user.getId();
        if (id!=null){
            userMapper.updateUser(user);
            userMapper.deleteUserRole(id);
        }else{
            userMapper.addUser(user);
        }
        //新增用户角色中间表：批量新增
        String roleids = user.getRoleid();
        String[] arrId = roleids.split(",");
        userMapper.addUserRole(arrId,user.getId());
    }

    private List<DeptBean> queryDeptNodes(int pid) {
        List<DeptBean> list = userMapper.queryDeptByPid(pid);
        for (DeptBean deptBean : list) {
            Integer id = deptBean.getId();
            List<DeptBean> nodes = queryDeptNodes(id);
            deptBean.setChildren(nodes);
        }
        return list;
    }

    private List<TreeBean> queryPowerNodes(int pid, List<TreeBean> rolePower) {
        List<TreeBean> list = userMapper.queryTreeByid(pid);

        for (TreeBean treeBean : list) {
            Integer id = treeBean.getId();
            //查询对应的子节点
            List<TreeBean> nodes = queryPowerNodes(id,rolePower);//递归：自己调自己
            treeBean.setChildren(nodes);

            //把所有的权限，跟当前角色拥有的权限比较：checked属性：true
            for (TreeBean treeBean2 : rolePower) {
                Integer id3 = treeBean2.getId();
                if(nodes.size()<=0 && id==id3){//是子节点 并且 有权限
                    treeBean.setChecked(true);
                }
            }
        }
        return list;
    }
}
