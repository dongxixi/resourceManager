package com.lovo.police_office.controller;


import com.lovo.police_office.bean.PaginationBean;
import com.lovo.police_office.dto.UserAllDto;
import com.lovo.police_office.entity.UserEntity;
import com.lovo.police_office.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * UserController
 *
 * @author 向伟
 */
@Controller
public class UserController {
    @Autowired
    private IUserService userService;

    @RequestMapping("gotoeventAll")
    public String gotoeventAll() {
        return "eventAll";
    }

    @RequestMapping("gotoUserAll")
    public String gotoUser() {
        return "userAll";
    }

    @RequestMapping("gotoLogin")
    public String gotoLogin(HttpServletRequest request,HttpServletResponse response) {
        request.getSession().removeAttribute("roleNamePolice");
        return "login";
    }

    @RequestMapping("userAdd")
    public String userAdd() {
        return "userAdd";
    }

    @RequestMapping("userUpdate")
    public String userUpdate() {
        return "userUpdate";
    }

    /**
     * 登录验证
     *
     * @param userName 登录表单中提交的用户名
     * @param password 登录表单中提交的密码
     * @return 查栈出来的user对象返回给页面操作
     */
    @RequestMapping("loginyanzheng")
    @ResponseBody
    public String findBy(String userName, String password, HttpServletRequest request,HttpServletResponse response) {

        UserEntity user = userService.logindBy(userName, password);
        String state="";
        if (user != null && !"".equals(user)) {
            String uid = user.getuId();
            String roleName = userService.findRoleNameByUserId(uid);
            request.getSession().setAttribute("roleNamePolice", roleName);
            if ("值班员".equals(roleName)) {
                //重定向到查询controller
               /* RedirectView rv = new RedirectView("gotoeventAll");
                mv.setView(rv);*/
                //返回1为权限为值班员
                state="1";

            } else {
                //重定向到查询controller
               /* RedirectView rv = new RedirectView("gotoUserAll");
                mv.setView(rv);*/
               //返回2为管理员
               state="2";
            }
        } else {
            //返回-1为登录失败
            state="-1";
        }
        return state;

    }

    /**
     * 查询所有用户信息，分页，条件查询
     *
     * @param currPage  当前页
     * @param userName  用户名
     * @param powerName 密码
     * @return
     */
    @RequestMapping("findAllUser")
    @ResponseBody
    public PaginationBean<UserAllDto> findAllUser(Integer currPage, String userName, String powerName) {
        //每页显示10行
        int showNum = 10;
        List<UserAllDto> listDto = userService.findAllUser(userName, powerName, currPage, showNum);
        int totalPage = userService.findTotalPageByCondition(userName, powerName, showNum);

        PaginationBean<UserAllDto> paginationBean = new PaginationBean<>();
        paginationBean.setCurrPage(currPage);
        paginationBean.setDataList(listDto);
        paginationBean.setTotalPage(totalPage);
        return paginationBean;
    }

    /**
     * 删除用户
     *
     * @param uid 用户id
     * @return
     */
    @RequestMapping("deleteUser")
    @ResponseBody
    public boolean deleteUser(String uid) {
        boolean b = userService.deleteUser(uid);

        return b;
    }

    /**
     * 新增用户
     *
     * @param userName 用户名
     * @param password 密码
     * @param roleName 角色名
     * @return
     */
    @RequestMapping("addUser")
    public ModelAndView addUser(String userName, String password, String roleName) {
        ModelAndView mv = new ModelAndView();
        UserEntity user = userService.findByUserName(userName);
        if (user == null) {
            userService.saveUserAll(userName, password, roleName);
            //重定向到查询controller
            RedirectView rv = new RedirectView("gotoUserAll");
            mv.setView(rv);
        } else {
            //重定向到查询controller
            RedirectView rv = new RedirectView("gotoLogin");
            mv.setView(rv);
        }
        return mv;
    }

    /**
     * 修改用户信息
     * @param password 密码
     * @param uid      用户id
     * @return
     */
    @RequestMapping("updateInfoUser")
    public ModelAndView updateInfoUser(String uid, String password) {
        ModelAndView mv = new ModelAndView();
            userService.updateInfoUser(uid, password);
            //重定向到查询controller
            RedirectView rv = new RedirectView("gotoUserAll");
            mv.setView(rv);
        return mv;
    }

    /**
     * 显示点击用户的详情
     *
     * @param uid 用户id
     * @return
     */
    @RequestMapping("goToUserUpdate")
    public ModelAndView goToUserUpdate(String uid) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("uid", uid);
        UserEntity user = userService.findUserById(uid);
        mv.addObject("user", user);
        String roleName = userService.findRoleNameByUserId(uid);
        mv.addObject("roleName", roleName);
        mv.setViewName("userUpdate");
        return mv;
    }

    /**
     * 根据用户名查询用户
     *
     * @param userName
     * @return
     */
    @RequestMapping("findByUserName")
    @ResponseBody
    public String findByUserName(String userName, HttpServletResponse response) {
        String a="1";
        UserEntity user = userService.findByUserName(userName);
        response.setContentType("text/html");
        response.setCharacterEncoding("utf-8");
       if (user==null){
           a="0";
       }
        return a;
    }

    @RequestMapping("loginMessage")
    public ModelAndView loginMessage() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("loginMessage");
        return mv;
    }



}
