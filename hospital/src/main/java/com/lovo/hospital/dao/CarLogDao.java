package com.lovo.hospital.dao;

import com.lovo.hospital.entity.CarLogEntity;
import org.springframework.data.repository.CrudRepository;

public interface CarLogDao extends CrudRepository<CarLogEntity, String > {

}
