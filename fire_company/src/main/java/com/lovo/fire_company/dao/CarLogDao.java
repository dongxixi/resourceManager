package com.lovo.fire_company.dao;

import com.lovo.fire_company.entity.CarLogEntity;
import org.springframework.data.repository.CrudRepository;

public interface CarLogDao extends CrudRepository<CarLogEntity, String > {

}
