package com.lovo.police_office.service;




import com.lovo.police_office.dto.UserAllDto;
import com.lovo.police_office.entity.UserEntity;

import java.util.List;

public interface IUserService {
    /**
     * 登录验证
     * @param userName  用户名
     * @param password  密码
     * @return  用户对象
     */
    public UserEntity logindBy(String userName, String password);

    /**
     * 显示所有用户信息 ，分页，条件查询
     * @param userName  用户名
     * @param powerName 权限名
     * @param startIndex    总页数
     * @param pageNum   当前页
     * @return  用户权限集合
     */
    public List<UserAllDto> findAllUser(String userName, String powerName, Integer startIndex, Integer pageNum);

    /**
     * 查询总页数
     * @param userName  用户名
     * @param powerName 权限名
     * @param showNum   每页显示条数
     * @return
     */
    public int findTotalPageByCondition(String userName, String powerName, Integer showNum);

    /**
     * 根据用户id删除用户
      * @param uId  用户id
     */
    public boolean deleteUser(String uId);

    /**
     * 新增用户
     * @param userName  用户名
     * @param password  密码
     * @param roleName  角色名
     */
    public void saveUserAll(String userName, String password, String roleName);

    /**
     * 修改用户
     * @param uid 用户id
     * @param password  密码
     */
    public void updateInfoUser(String uid, String password);

    /**
     * 根据用户id查询用户
     * @param uid   用户id
     * @return
     */
    public UserEntity findUserById(String uid);

    /**
     * 根据用户id查询角色名
     * @param uid 用户id
     * @return
     */
    public String findRoleNameByUserId(String uid);

    /**
     * 根据用户名查询用户
     * @param userName  用户名
     * @return  用户对象
     */
    public UserEntity findByUserName(String userName);

}
