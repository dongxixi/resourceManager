package com.lovo.hospital.service.impl;

import com.lovo.hospital.dao.RoleDao;
import com.lovo.hospital.dao.UserDao;
import com.lovo.hospital.dao.UserRoleDao;
import com.lovo.hospital.entity.UserRoleEntity;
import com.lovo.hospital.dto.UserAllDto;
import com.lovo.hospital.entity.RoleEntity;
import com.lovo.hospital.entity.UserEntity;
import com.lovo.hospital.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service(value="userService")
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserRoleDao userRoleDao;
    @Autowired
    private RoleDao roleDao;

    /**
     * 登录验证
     * @param userName  用户名
     * @param password  密码
     * @return
     */
    @Override
    public UserEntity logindBy(String userName, String password) {

        return userDao.logindBy(userName,password);
    }

    /**
     * 查询用户所有信息
     * @param userName  用户名
     * @param powerName 权限名
     * @param showNum   每页显示条数
     * @param pageNum   当前页
     * @return
     */
    @Override
    public List<UserAllDto> findAllUser(String userName, String powerName, Integer showNum, Integer pageNum) {
        int startIndex = (showNum - 1) * pageNum;
        List<Object[]> allUser = userDao.findAllUser(userName, powerName, startIndex, pageNum);
        List<UserAllDto> listDto=null;
        if(null!=allUser&&!allUser.isEmpty()) {
            listDto =new ArrayList<>();
            for (Object[] objs : allUser){
                UserAllDto dto=new UserAllDto();
                dto.setUserName(objs[0].toString());
                dto.setPowerName(objs[2].toString());
                dto.setUserId(objs[3].toString());
                listDto.add(dto);
            }
        }

        return listDto;
    }

    /**
     * 查询总页数
     * @param userName  用户名
     * @param powerName 权限名
     * @param showNum   每页显示条数
     * @return
     */
    @Override
    public int findTotalPageByCondition(String userName, String powerName, Integer showNum) {
        int totalCount = userDao.findTotalPageByCondition(userName, powerName);
        Integer totalPage = (totalCount + showNum - 1) / showNum;
        return totalPage;
    }

    /**
     * 删除用户
     * @param uId  用户id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteUser(String uId) {
        userDao.deleteById(uId);
        boolean b = userDao.existsById(uId);
            userRoleDao.deleteByUserId(uId);
        return b;
    }

    /**
     * 新增用户
     * @param userName  用户名
     * @param password  密码
     * @param roleName  角色名
     */
    @Override
    public void saveUserAll(String userName, String password,String roleName ) {
        if (roleName.equals("1")){
            roleName="值班员";
        }else {
            roleName="管理员";
        }
        UserEntity user=new UserEntity();
        user.setUserName(userName);
        user.setSystemName("医院");
        user.setPassword(password);
        UserEntity save = userDao.save(user);

        UserRoleEntity ure= new UserRoleEntity();
        ure.setUser(save);
        //查询所有的角色
        List<RoleEntity> roleEntities = (List<RoleEntity>) roleDao.findAll();

        //循环所有的角色
        for (RoleEntity role :
             roleEntities) {
            //如果要添加的值和某个role的roleName一致，把该角色加入到中间表中，跳出循环
            if (role.getRoleName().equals(roleName) ) {
                ure.setRole(role);
                break;
            }
        }

        userRoleDao.save(ure);

    }

    /**
     * 修改用户
     * @param uid 用户id
     * @param password  密码
     */
    @Transactional
    public void updateInfoUser(String uid, String password) {
        UserEntity user=(UserEntity)userDao.findById(uid).get();
        user.setPassword(password);
    }

    /**
     * 根据用户id查询用户
     * @param uid   用户id
     * @return  用户对象
     */
    @Override
    public UserEntity findUserById(String uid) {

        return userDao.findById(uid).get();
    }

    /**
     * 根据用户id查询角色名
     * @param uid 用户id
     * @return
     */
    @Override
    public String findRoleNameByUserId(String uid) {

        return roleDao.findRoleNameByUserId(uid);
    }

    /**
     * 根据用户名查询用户
     * @param userName  用户名
     * @return  用户对象
     */
    @Override
    public UserEntity findByUserName(String userName) {
        return userDao.findByUserName(userName);
    }


}

