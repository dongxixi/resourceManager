package com.lovo.hospital.dao;

import com.lovo.hospital.dto.EventRecordListDto;
import com.lovo.hospital.entity.EventEntity;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EventDao extends CrudRepository<EventEntity, String> {

    @Query(value = "select " +
            " e.id as eventIdNull," +
            " e.event_id as eventId," +
            " e.event_name as eventName," +
            " e.event_time as eventBeginTime," +
            " d.p_num as peopleNum," +
            " d.c_num as carNum," +
            " e.event_proceed as state" +
            " FROM" +
            " t_event  e" +
            " ,t_dispatch  d " +
            " where " +
            " if(:eventName is not null ,e.event_name like CONCAT('%',:eventName,'%'),1=1) " +
            " and if(:startTime is not null and :endTime is not null,event_time between :startTime and :endTime,1=1) " +
            " and if(:startTime is not null and :endTime is  null,event_time > :startTime ,1=1) " +
            " and if(:startTime is  null and :endTime is not null,event_time < :endTime,1=1) " +
            " limit :startIndex,:pageNum", nativeQuery = true)
    public List<Object> getEventDtoList(@Param("starIndex") Integer starIndex,
                                        @Param("pageNum") Integer pageNum,
                                        @Param("eventName") String eventName,
                                        @Param("startTime") String startTime,
                                        @Param("endTime") String endTime);

    @Query(value = "select count(*)" +
            " FROM" +
            " t_event  e" +
            " ,t_dispatch  d " +
            " where " +
            "  if(:eventName is not null ,e.event_name like CONCAT('%',:eventName,'%'),1=1) " +
            "  and if(:startTime is not null and :endTime is not null,event_time between :startTime and :endTime,1=1) " +
            "   and if(:startTime is not null and :endTime is  null,event_time > :startTime ,1=1) " +
            "   and if(:startTime is  null and :endTime is not null,event_time < :endTime,1=1) ", nativeQuery = true)
    public Integer getTotalCountByCondition(@Param("eventName") String eventName,
                                            @Param("startTime") String startTime,
                                            @Param("endTime") String endTime);
}
