package com.lovo.fire_company.dao;

import com.lovo.fire_company.entity.RoleEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface RoleDao extends CrudRepository<RoleEntity,String> {
    @Query(value = "select r.role_name from t_user u LEFT JOIN t_user_role ur on u.uid=ur.user_id " +
            "   LEFT JOIN t_role r on r.rid=ur.role_id " +
            "  where u.uid=?1",nativeQuery = true)
    public String  findRoleNameByUserId(String uid);
}
