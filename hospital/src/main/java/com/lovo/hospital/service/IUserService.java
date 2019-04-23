package com.lovo.hospital.service;

import com.lovo.hospital.dto.UserAllDto;
import com.lovo.hospital.entity.RoleEntity;
import com.lovo.hospital.entity.UserEntity;

import java.util.List;

public interface IUserService {
    public UserEntity logindBy(String userName, String password);
    public List<UserAllDto> findAllUser( String userName, String powerName, Integer startIndex, Integer pageNum);
    public int findTotalPageByCondition(String userName,String powerName,Integer showNum);
    public void deleteUser(String uId);
    public void saveUserAll(String userName,String password,String roleName);
    public void updateInfoUser(String uid,String userName,String password,String roleName);
    public UserEntity findUserById(String uid);
    public String findRoleNameByUserId(String uid);

}
