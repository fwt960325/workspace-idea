package com.jk.controller;

import com.jk.model.*;
import com.jk.service.UserService;
import com.jk.util.CheckImgUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("zhuce")
    public String zhuce(){
        return "zhuce";
    }

    @RequestMapping("index")
    public String index(){
        return "index";
    }

    @RequestMapping("toindex")
    public String toindex(){
        return "show";
    }


    @RequestMapping("toshow")
    public String toshow(){
        return "show";
    }
    @RequestMapping("rolepower")
    public String rolepower(){
        return "rolepower";
    }
    @RequestMapping("touserlist")
    public String touserlist(){
        return "userlist";
    }
    @RequestMapping("queryTree")
    public @ResponseBody List<TreeBean> queryTree(HttpSession session){
        return userService.queryTree(session);
    }
    @RequestMapping("queryUserPage")
    @ResponseBody
    public HashMap<String ,Object> queryUserPage(Integer page, Integer rows, UserBean user){

        return userService.queryUserPage(page,rows,user);
    }

    @RequestMapping("queryRole")
    @ResponseBody
    public List<RoleBean> queryRole(){
        return userService.queryRole();
    }

    @RequestMapping("queryPowerTree")
    @ResponseBody
    public List<TreeBean> queryPowerTree(Integer roleid){
        return  userService.queryPowerTree(roleid);
    }

    @RequestMapping("addPower")
    @ResponseBody
    public void addPower(TreeBean power){
        userService.addPower(power);
    }

    @RequestMapping("deletePowerById")
    @ResponseBody
    public void deletePowerById(Integer powerid){
        userService.deletePowerById(powerid);
    }

    @RequestMapping("saveRolePower")
    @ResponseBody
    public void saveRolePower(Integer roleid,String[] ids){
        userService.saveRolePower(roleid,ids);
    }

    @RequestMapping("queryMenu")
    @ResponseBody
    public List<MenuBean> queryMenu(Integer powerid){
        return userService.queryMenu(powerid);
    }

    @RequestMapping("gainCode")
    @ResponseBody
    public void gainCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
        CheckImgUtil.checkImg(request,response);
    }

    @RequestMapping("login")
    @ResponseBody
    public String login (UserBean user, String code, HttpSession session){
        String checkcode = session.getAttribute("checkcode").toString();
        if (!code.equalsIgnoreCase(checkcode)){
            return "验证码错误";
        }
        UserBean user2 = userService.queryUserName(user.getLoginNumber());
        if (user2==null){
            return "用户名不存在";
        }
        if (!user.getPassword().equals(user.getPassword())){
            return "密码错误";
        }
        session.setAttribute("user",user2);

        session.setAttribute("id", user2.getId());

        return "登录成功";
    }

    @RequestMapping("zhuceuser")
    @ResponseBody
    public String zhuceuser(UserBean u){
        UserBean user = userService.queryUserName(u.getLoginNumber());
        if (user!=null){
            return "用户名已存在";
        }
        userService.zhuceuser(u);
        return "注册成功";
    }
    @RequestMapping("removeUser")
    public String removeUser(HttpSession session){
        session.removeAttribute("user");
        return "index";
    }

    @RequestMapping("queryUserById")
    @ResponseBody
    public UserBean queryUserById(Integer userId){
        return userService.queryUserById(userId);
    }
    @RequestMapping("queryArea")
    @ResponseBody
    public List<AreaBean> queryArea(Integer pid){
        return userService.queryArea(pid);
    }
    @RequestMapping("queryDept")
    @ResponseBody
    public List<DeptBean> queryDept(){
        return userService.queryDept();
    }
    @RequestMapping("addUser")
    @ResponseBody
    public void addUser (UserBean user){
        userService.addUser(user);
    }
}
