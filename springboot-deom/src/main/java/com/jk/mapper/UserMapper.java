package com.jk.mapper;

import com.jk.model.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {


    List<TreeBean> queryTreeByid(int pid);

    int queryCount(@Param("user") UserBean user);

    List<UserBean> queryUserPage(@Param("start") int start,@Param("rows") Integer rows,@Param("user") UserBean user);

    List<RoleBean> queryRole();

    List<MenuBean> queryMenu(Integer powerid);

    List<TreeBean> queryPowerTreeByRoleid(Integer roleid);

    void addPower(TreeBean power);

    void deletePowerById(Integer powerid);

    void deleteByRoleid(Integer roleid);

    void addRolePower(@Param("roleid") Integer roleid,@Param("ids") String[] ids);

    UserBean queryUserName(String loginNumber);

    void zhuceuser(UserBean u);

    UserBean queryUserById(Integer userId);

    List<AreaBean> queryArea(Integer pid);

    List<DeptBean> queryDeptByPid(int pid);

    void updateUser(UserBean user);

    void addUser(UserBean user);

    void addUserRole(@Param("arrId") String[] arrId,@Param("userid") Integer userid);

    void deleteUserRole(Integer id);
}
