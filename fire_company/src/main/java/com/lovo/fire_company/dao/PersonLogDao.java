package com.lovo.fire_company.dao;


import com.lovo.fire_company.entity.PersonnelLogEntity;
import org.springframework.data.repository.CrudRepository;

public interface PersonLogDao extends CrudRepository<PersonnelLogEntity, String> {

}
