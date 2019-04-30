package com.lovo.police_office.dao;


import com.lovo.police_office.entity.DispatchEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DispatchDao extends CrudRepository<DispatchEntity, String> {

   @Query(value = " SELECT * FROM t_dispatch d WHERE d.e_id=?1 AND d.state=?2 " +
           "order by  d.request_id desc ", nativeQuery = true)
   List<DispatchEntity> getDispatchByEventId(String eid, Integer di);
}
