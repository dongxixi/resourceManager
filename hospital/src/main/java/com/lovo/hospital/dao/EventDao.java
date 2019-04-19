package com.lovo.hospital.dao;

import com.lovo.hospital.entity.EventEntity;

import org.springframework.data.repository.CrudRepository;

public interface EventDao extends CrudRepository<EventEntity,String> {

}
