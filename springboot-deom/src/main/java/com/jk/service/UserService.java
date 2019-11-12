package com.jk.service;

import com.jk.model.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

public interface UserService {
    List<TreeBean> queryTree(HttpSession session);

    HashMap<String, Object> queryUserPage(Integer page, Integer rows, UserBean user);

    List<RoleBean> queryRole();

    List<MenuBean> queryMenu(Integer powerid);

    List<TreeBean> queryPowerTree(Integer roleid);

    void addPower(TreeBean power);

    void deletePowerById(Integer powerid);

    void saveRolePower(Integer roleid, String[] ids);

    UserBean queryUserName(String loginNumber);

    void zhuceuser(UserBean u);

    UserBean queryUserById(Integer userId);

    List<AreaBean> queryArea(Integer pid);

    List<DeptBean> queryDept();

    void addUser(UserBean user);
}
