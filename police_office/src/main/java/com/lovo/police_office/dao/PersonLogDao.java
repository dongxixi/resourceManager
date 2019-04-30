package com.lovo.police_office.dao;


import com.lovo.police_office.entity.PersonnelLogEntity;
import org.springframework.data.repository.CrudRepository;

public interface PersonLogDao extends CrudRepository<PersonnelLogEntity, String> {

}
