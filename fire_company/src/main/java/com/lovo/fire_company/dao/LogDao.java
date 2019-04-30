package com.lovo.fire_company.dao;

import com.lovo.fire_company.entity.LogEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;

public interface LogDao extends CrudRepository<LogEntity, String> {
    /**
     * @param username    用户名
     * @param description 操作关键字
     * @param strTime     开始时间
     * @param endTime     结束时间
     * @param startIndex  开始小标
     * @param pageNum     每页显示的条数
     * @return 日志集合
     */
    @Query(value = "select * from t_log as l where if(:username is not null ,l.username like CONCAT('%',:username,'%'),1=1) " +
            " and if(:description is not null ,l.description like CONCAT('%',:description,'%'),1=1) " +
            " and if(:strTime is not null and :endTime is not null,l.time between :strTime and :endTime,1=1) " +
            " and if(:strTime is not null and :endTime is  null,l.time > :strTime ,1=1) " +
            " and if(:strTime is  null and :endTime is not null,l.time < :endTime,1=1) " +
            " Order by  l.time  desc limit :startIndex,:pageNum  ", nativeQuery = true)
    public List<LogEntity> findPageby(@Param("username") String username,
                                      @Param("description") String description,
                                      @Param("strTime") Timestamp strTime,
                                      @Param("endTime") Timestamp endTime,
                                      @Param("startIndex") Integer startIndex,
                                      @Param("pageNum") Integer pageNum

    );

    /**
     * @param username    用户名
     * @param description 操作关键字
     * @param strTime     开始时间
     * @param endTime     结束时间
     * @return 总条数
     */
    @Query(value = "select count(*) from t_log as l where if(:username is not null ,l.username like CONCAT('%',:username,'%'),1=1) " +
            " and if(:description is not null ,l.description like CONCAT('%',:description,'%'),1=1) " +
            " and if(:strTime is not null and :endTime is not null,l.time between :strTime and :endTime,1=1) " +
            " and if(:strTime is not null and :endTime is  null,l.time > :strTime ,1=1) " +
            " and if(:strTime is  null and :endTime is not null,l.time < :endTime,1=1) ", nativeQuery = true)
    public int findTotalPage(@Param("username") String username,
                             @Param("description") String description,
                             @Param("strTime") Timestamp strTime,
                             @Param("endTime") Timestamp endTime


    );


}
