package com.lovo.hospital.dao;

import com.lovo.hospital.entity.UserRoleEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserRoleDao extends PagingAndSortingRepository<UserRoleEntity,String> {
}
