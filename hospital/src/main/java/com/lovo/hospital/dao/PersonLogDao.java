package com.lovo.hospital.dao;

import com.lovo.hospital.entity.PersonnelLogEntity;
import org.springframework.data.repository.CrudRepository;

public interface PersonLogDao extends CrudRepository<PersonnelLogEntity, String > {

}
