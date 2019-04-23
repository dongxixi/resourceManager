package com.lovo.hospital.dao;

import com.lovo.hospital.dto.EventRecordListDto;
import com.lovo.hospital.entity.EventEntity;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EventDao extends CrudRepository<EventEntity, String> {

    @Query(value = "SELECT " +
            " e.id AS eventIdNull, " +
            "e.event_id AS eventId, " +
            "e.event_name AS eventName, " +
            "e.event_time AS eventBeginTime, " +
            "sum(d.p_num) AS peopleNum, " +
            "sum(d.c_num) AS carNum, " +
            "e.event_proceed AS state " +
            "FROM " +
            "t_event e  " +
            "INNER JOIN t_dispatch d ON e.id = d.e_id " +
            " where " +
            " if(:eventName is not null ,e.event_name like CONCAT('%',:eventName,'%'),1=1) " +
            " and if(:startTime is not null and :endTime is not null,e.event_time between :startTime and :endTime,1=1) " +
            " and if(:startTime is not null and :endTime is  null,e.event_time > :startTime ,1=1) " +
            " and if(:startTime is  null and :endTime is not null,e.event_time < :endTime,1=1) " +
            " group by e.id limit :startIndex,:pageNum", nativeQuery = true)
    public List<Object[]> getEventDtoList(@Param("startIndex") Integer startIndex,
                                        @Param("pageNum") Integer pageNum,
                                        @Param("eventName") String eventName,
                                        @Param("startTime") String startTime,
                                        @Param("endTime") String endTime);

    @Query(value = "select count(*)" +
            "          FROM " +
            "          t_event e  " +
            "          INNER JOIN t_dispatch d ON e.id = d.e_id " +
            "          where " +
            "          if(:eventName is not null ,e.event_name like CONCAT('%',:eventName,'%'),1=1) " +
            "          and if(:startTime is not null and :endTime is not null,e.event_time between :startTime and :endTime,1=1) " +
            "          and if(:startTime is not null and :endTime is  null,e.event_time > :startTime ,1=1) " +
            "          and if(:startTime is  null and :endTime is not null,e.event_time < :endTime,1=1) " +
            "          group by e.id ", nativeQuery = true)
    public Integer getTotalCountByCondition(@Param("eventName") String eventName,
                                            @Param("startTime") String startTime,
                                            @Param("endTime") String endTime);
}
