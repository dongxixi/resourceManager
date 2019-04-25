package com.lovo.hospital.dao;

import com.lovo.hospital.entity.PersonnelEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PensonnelDao extends CrudRepository<PersonnelEntity,String> {


    /**
     * 通过搜索框，和下标来进行分页显示查询表格
     * @param name
     * @param pnum
     * @param position
     * @param startIndex
     * @param endIndex
     * @return
     */
    @Query(value ="select * from t_personnel where if(:name is not null,`name` like concat('%',:name,'%'),1=1) "+
    " and if(:pnum is not null,pnum like concat('%',:pnum,'%'),1=1) " +
    " and if(:position is not null,position like concat('%',:position,'%'),1=1) limit :startIndex,:endIndex",
            nativeQuery = true)
    public List<PersonnelEntity> getAllListBySerch(@Param("name")String name, @Param("pnum")String pnum,
                                                   @Param("position")String position, @Param("startIndex")Integer startIndex,
                                                   @Param("endIndex")Integer endIndex);

    /**
     * 搜索出来的总页数
     * @param name
     * @param pnum
     * @param position
     * @return
     */
    @Query(value = "select count(*) from t_personnel where if(:name is not null,name like concat('%',:name,'%'),1=1) "+
            " and if(:pnum is not null,pnum like concat('%',:pnum,'%'),1=1) " +
            " and if(:position is not null,position like concat('%',:position,'%'),1=1)",
            nativeQuery = true)
    public  int findTotalPageByCondition(@Param("name")String name, @Param("pnum")String pnum,
                                         @Param("position")String position);



    @Query(value="select count(*) from t_personnel where :tel =?1", nativeQuery = true)
    public Integer findTelByTel(@Param("tel")String tel);
}
