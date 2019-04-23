package com.lovo.hospital.dao;

import com.lovo.hospital.entity.UserEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserDao extends PagingAndSortingRepository<UserEntity,String> {
    @Query(" from UserEntity u where u.systemName='医院'and u.userName=?1 and u.password=?2")
    public UserEntity logindBy(String userName, String password);

    @Query(value="select u.user_name,r.role_name,p.powern_name,u.uid" +
            " from t_user u LEFT JOIN t_user_role ur on u.uid=ur.user_id  " +
            " LEFT JOIN t_role r on r.rid=ur.role_id " +
            " LEFT JOIN t_role_power rp on r.rid=rp.role_id " +
            " LEFT JOIN t_power p on p.pid=rp.power_id " +
            " where u.system_name='医院'and if (:userName is not null ,u.user_name like CONCAT('%',:userName,'%'),1=1) " +
            " and if(:powerName is not null ,p.powern_name like CONCAT('%',:powerName,'%'),1=1) " +
            " limit :startIndex,:pageNum ",nativeQuery = true)
    public List<Object[]> findAllUser(@Param("userName") String userName,
                                        @Param("powerName") String powerName,
                                        @Param("startIndex") Integer startIndex,
                                        @Param("pageNum") Integer pageNum);


    @Query(value = "select count(*) from t_user u LEFT JOIN t_user_role ur on u.uid=ur.user_id " +
            " LEFT JOIN t_role r on r.rid=ur.role_id " +
            " LEFT JOIN t_role_power rp on r.rid=rp.role_id " +
            " LEFT JOIN t_power p on p.pid=rp.power_id " +
            " where u.system_name='医院'and if(:userName is not null ,u.user_name like CONCAT('%',:userName,'%'),1=1) " +
            "            and if(:powerName is not null ,p.powern_name like CONCAT('%',:powerName,'%'),1=1) ",nativeQuery = true)
    public int findTotalPageByCondition(@Param("userName") String userName, @Param("powerName") String powerName);

    @Modifying
    @Query("update UserRoleEntity ur set " +
            " ur.user.userName=?2,ur.user.password=?3,ur.role.rId=?4 " +
            " where ur.user.uId=?1")
    public void updateInfoUser(String uid, String userName, String password, String roleName);

}