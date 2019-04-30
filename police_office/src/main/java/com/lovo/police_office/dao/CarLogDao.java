package com.lovo.police_office.dao;


import com.lovo.police_office.entity.CarLogEntity;
import org.springframework.data.repository.CrudRepository;

public interface CarLogDao extends CrudRepository<CarLogEntity, String > {

}
