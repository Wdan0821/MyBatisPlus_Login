package com.swjd.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.swjd.bean.User;
import com.swjd.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
@Controller
public class UserController {
    @Autowired
    UserService userService;

    //去登陆
    @RequestMapping("/toLogin")
    public String toLogin(Model model){
        User user=new User();
        model.addAttribute("user",user);
        return "login";
    }

    //做登录
    @RequestMapping("/doLogin")
    public String doLogin(User user, Model model, HttpSession session){
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("uname",user.getUname());
        queryWrapper.eq("password",user.getPassword());
        User u=userService.getOne(queryWrapper);
        if (u!=null){
            //账号密码没问题
            if (u.getFlag().equals("1")){
                //登录成功把用户名存到session
                session.setAttribute("activeName",u.getUname());
                return "main";
            }else {
                //账号被禁用了
                model.addAttribute("errorMsg","该账号被禁用,请联系管理员");
                model.addAttribute("user",user);
                return "login";
            }
        }else {
            //账号密码有问题
            model.addAttribute("errorMsg","账号或者密码错误");
            model.addAttribute("user",new User());
            return "login";
        }
    }
}
