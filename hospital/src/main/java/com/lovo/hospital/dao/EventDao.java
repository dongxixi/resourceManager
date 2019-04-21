package com.lovo.hospital.dao;

import com.lovo.hospital.dto.EventRecordListDto;
import com.lovo.hospital.entity.EventEntity;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EventDao extends CrudRepository<EventEntity,String> {

    @Query(value = "select " +
            "new com.lovo.hospital.dto.EventRecordListDto" +
            "(e.id as eventIdNull," +
            "e.event_id as eventId," +
            "e.event_name as eventName," +
            "e.event_time as eventBeginTime," +
            "d.p_num as peopleNum," +
            "d.c_num as carNum," +
            "e.event_proceed as state)" +
            " FROM" +
            " t_event  e" +
            ",t_dispatch  d " +
            "where" +
            "if(:eventName is not null ,e.event_name like CONCAT('%',:eventName,'%'),1=1) AND" +
            "if(:startTime is not null ,e.event_time <= CONCAT('%',:eventName,'%'),1=1) AND" +
            "if(:endTime is not null ,e.event_time >= CONCAT('%',:eventName,'%'),1=1)" +
            " limit:startIndex,:pageNum",nativeQuery = true)
    List<Object>getEventDtoList(@Param("starIndext") Integer starIndext,
                                            @Param("eventName") String eventName,
                                            @Param("startTime") String startTime,
                                            @Param("endTime") String endTime);

    @Query(value = "select count(*)" +
            "where" +
            "if(:eventName is not null ,e.event_name like CONCAT('%',:eventName,'%'),1=1) AND" +
            "if(:startTime is not null ,e.event_time <= CONCAT('%',:eventName,'%'),1=1) AND" +
            "if(:endTime is not null ,e.event_time >= CONCAT('%',:eventName,'%'),1=1)",nativeQuery = true)
    Integer getTotalCountByCondition( @Param("eventName") String eventName,
                                      @Param("startTime") String startTime,
                                      @Param("endTime") String endTime);
}
