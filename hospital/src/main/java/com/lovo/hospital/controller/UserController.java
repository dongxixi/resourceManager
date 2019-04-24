package com.lovo.hospital.controller;

import com.lovo.hospital.bean.PaginationBean;
import com.lovo.hospital.dto.UserAllDto;
import com.lovo.hospital.entity.RoleEntity;
import com.lovo.hospital.entity.UserEntity;
import com.lovo.hospital.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    private IUserService userService;

    @RequestMapping("gotoUserAll")
    public String gotoUser(){
        return "userAll";
    }
    @RequestMapping("gotoLogin")
    public String gotoLogin(){
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
     * @param userName 登录表单中提交的用户名
     * @param password  登录表单中提交的密码
     * @return 查栈出来的user对象返回给页面操作
     */
    @RequestMapping("loginyanzheng")
    public ModelAndView findBy(String userName, String password, HttpServletRequest request){
        ModelAndView mv=new ModelAndView();
        UserEntity user=userService.logindBy(userName,password);
        if (user!=null&&!" ".equals(user)){
            //重定向到查询controller
            request.getSession().setAttribute("user",user);
            RedirectView rv=new RedirectView("gotoUserAll");
            mv.setView(rv);
        }
        else {
            mv.setViewName("login");
        }
        return mv;
    }

    /**
     * 查询所有用户信息，分页，条件查询
     * @param currPage 当前页
     * @param userName  用户名
     * @param powerName 密码
     * @return
     */
    @RequestMapping("findAllUser")
    @ResponseBody
    public PaginationBean<UserAllDto> findAllUser(Integer currPage, String userName, String powerName){
        //每页显示10行
        int showNum = 10;
        List<UserAllDto> listDto=userService.findAllUser(userName,powerName,currPage,showNum);
        int totalPage = userService.findTotalPageByCondition(userName, powerName, showNum);

        PaginationBean<UserAllDto> paginationBean = new PaginationBean<>();
        paginationBean.setCurrPage(currPage);
        paginationBean.setDataList(listDto);
        paginationBean.setTotalPage(totalPage);
        return paginationBean;
    }

    /**
     *删除用户
     * @param uid   用户id
     * @return
     */
    @RequestMapping("deleteUser")
    public ModelAndView deleteUser(String uid){
        System.out.println(uid);
        userService.deleteUser(uid);
        ModelAndView modelAndView=new ModelAndView("userAll");
        return modelAndView;
    }

    /**
     * 新增用户
     * @param userName  用户名
     * @param password  密码
     * @param roleName  角色名
     * @return
     */
    @RequestMapping("addUser")
    public ModelAndView addUser(String userName,String password,String roleName){
        ModelAndView mv=new ModelAndView();
        UserEntity user=userService.findByUserName(userName);
        if (user==null){
            userService.saveUserAll(userName,password,roleName);
            //重定向到查询controller
            RedirectView rv=new RedirectView("gotoUserAll");
            mv.setView(rv);
        }else {
            //重定向到查询controller
            RedirectView rv=new RedirectView("gotoLogin");
            mv.setView(rv);
        }
        return mv;
    }

    /**
     * 修改用户信息
     * @param userName  用户名
     * @param password  密码
     * @param roleName  角色名
     * @param uid   用户id
     * @return
     */
    @RequestMapping("updateInfoUser")
    public ModelAndView updateInfoUser(String userName,String password,String roleName,String uid) {
        ModelAndView mv=new ModelAndView();
        UserEntity user=userService.findByUserName(userName);
        if (user==null){
            userService.updateInfoUser(userName,password,roleName,uid);
            //重定向到查询controller
            RedirectView rv=new RedirectView("gotoUserAll");
            mv.setView(rv);
        }else {
            //重定向到查询controller
            RedirectView rv=new RedirectView("gotoLogin");
            mv.setView(rv);
        }
        return mv;
    }

    /**
     * 显示点击用户的详情
     * @param uid   用户id
     * @return
     */
    @RequestMapping("goToUserUpdate")
    public ModelAndView goToUserUpdate(String uid){
        ModelAndView mv=new ModelAndView();
        mv.addObject("uid",uid);
        UserEntity user= userService.findUserById(uid);
        mv.addObject("user",user);
        String roleName=userService.findRoleNameByUserId(uid);
        mv.addObject("roleName",roleName);
        mv.setViewName("userUpdate");
        return mv;
    }

}
