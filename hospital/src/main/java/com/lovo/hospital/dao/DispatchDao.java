package com.lovo.hospital.dao;

import com.lovo.hospital.entity.DispatchEntity;
import org.springframework.data.repository.CrudRepository;

public interface DispatchDao extends CrudRepository<DispatchEntity, String> {
}
