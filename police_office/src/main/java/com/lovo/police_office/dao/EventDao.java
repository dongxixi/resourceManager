package com.lovo.police_office.dao;


import com.lovo.police_office.entity.EventEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;

public interface EventDao extends CrudRepository<EventEntity, String> {
    /**
     * 待条件查询事件信息
     * @param startIndex    开始下标
     * @param pageNum       要显示多少条
     * @param eventName     事件名字
     * @param startTime     事件开始时间
     * @param endTime       事件结束时间
     * @return
     */
    @Query(value = "SELECT " +
            "e.id AS eventId, " +
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
            " and if(:startTime is not null  and :endTime is not null,e.event_time between :startTime and :endTime,1=1) " +
            " and if(:startTime is not null and :endTime is  null,e.event_time > :startTime ,1=1) " +
            " and if(:startTime is  null and :endTime is not null,e.event_time < :endTime,1=1) " +
            " group by e.id order by e.event_proceed  limit :startIndex,:pageNum", nativeQuery = true)
    public List<Object[]> getEventDtoList(@Param("startIndex") Integer startIndex,
                                          @Param("pageNum") Integer pageNum,
                                          @Param("eventName") String eventName,
                                          @Param("startTime") Timestamp startTime,
                                          @Param("endTime") Timestamp endTime);

    /**
     * 查询事件的总条数
     * @param eventName     事件名字
     * @param startTime     事件开始时间
     * @param endTime       事件结束时间
     * @return
     */
    @Query(value = "select count(*)" +
            "          FROM " +
            "          t_event e  " +
            "          where " +
            "          if(:eventName is not null ,e.event_name like CONCAT('%',:eventName,'%'),1=1) " +
            "          and if(:startTime is not null and :endTime is not null,e.event_time between :startTime and :endTime,1=1) " +
            "          and if(:startTime is not null and :endTime is  null,e.event_time > :startTime ,1=1) " +
            "          and if(:startTime is  null and :endTime is not null,e.event_time < :endTime,1=1) " +
            "         ", nativeQuery = true)
    public Integer getTotalCountByCondition(@Param("eventName") String eventName,
                                            @Param("startTime") Timestamp startTime,
                                            @Param("endTime") Timestamp endTime);

    /**
     * 通过事件id查询说关联的所有人员信息
     * @param eid
     * @return
     */
    @Query(value = "SELECT \n" +
            "p.id , p.name ,p.pnum ,p.position,p.sex,p.state ,p.tel ,p.work_time,pl.id as pl_id,pl.state as pl_state\n" +
            "from t_event e\n" +
            "INNER JOIN t_dispatch d on e.id=d.e_id\n" +
            "INNER JOIN t_personnel_log pl on d.request_id=pl.d_id\n" +
            "INNER JOIN t_personnel p on p.id=pl.p_id" +
            " where e.id=?1",nativeQuery = true)
    public List<Object[]> getEventInfoPersonnel(String eid);

    /**
     * 通过事件信息查询所有的car信息
     * @param eid
     * @return
     */
    @Query(value = "SELECT \n" +
            "c.id ,c.car_num ,c.driver ,c.state,cl.id as cl_id, cl.state as cl_state \n" +
            "from t_event e\n" +
            "INNER JOIN t_dispatch d on e.id=d.e_id\n" +
            "INNER JOIN t_car_log cl on d.request_id=cl.d_id\n" +
            "INNER JOIN t_car c on c.id=cl.c_id " +
            "where e.id=?1",nativeQuery = true)
    public List<Object[]> getEventInfoCar(String eid);
}
