package com.lovo.fire_company.dao;

import com.lovo.fire_company.entity.UserRoleEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserRoleDao extends PagingAndSortingRepository<UserRoleEntity,String> {
    @Modifying
    @Query(value=" delete from t_user_role  where t_user_role.user_id=?1 ",nativeQuery = true)
    public void deleteByUserId(String uId);
}
