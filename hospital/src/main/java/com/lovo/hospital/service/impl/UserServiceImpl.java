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

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service(value="userService")
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserRoleDao userRoleDao;
    @Autowired
    private RoleDao roleDao;
    @Override
    public UserEntity logindBy(String userName, String password) {

        return userDao.logindBy(userName,password);
    }

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

    @Override
    public int findTotalPageByCondition(String userName, String powerName, Integer showNum) {
        int totalCount = userDao.findTotalPageByCondition(userName, powerName);
        Integer totalPage = (totalCount + showNum - 1) / showNum;
        return totalPage;
    }

    @Override
    public void deleteUser(String uId) {
        userDao.deleteById(uId);
    }

    @Override
    public void saveUserAll(String userName, String password,String roleName ) {
        if (roleName.equals("1")){
            roleName="医院值班员";
        }else {
            roleName="医院管理员";
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

    @Transactional
    public void updateInfoUser(String uid, String userName, String password, String roleName) {
        userDao.updateInfoUser(uid,userName,password,roleName);
    }

    @Override
    public UserEntity findUserById(String uid) {

        return userDao.findById(uid).get();
    }

    @Override
    public String findRoleNameByUserId(String uid) {

        return roleDao.findRoleNameByUserId(uid);
    }


}

