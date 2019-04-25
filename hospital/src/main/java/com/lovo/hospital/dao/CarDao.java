package com.lovo.hospital.dao;

import com.lovo.hospital.entity.CarEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface CarDao extends CrudRepository<CarEntity,String> {

    /**
     * 通过条件来查询车辆情况
     * @param carNum    车辆情况
     * @param dirveName 司机
     * @param state     状态
     * @param startIndex    开始下标
     * @param pageNum  结束下标
     * @return  装有car的集合
     */
    @Query(value = "select * from t_car where if(:carNum is not null ,car_num like CONCAT('%',:carNum,'%'),1=1) " +
            "and if(:dirveName is not null ,driver like CONCAT('%',:dirveName,'%'),1=1) " +
            "and if(:state is not null ,state like CONCAT('%',:state,'%'),1=1) " +
            "limit :startIndex,:pageNum",nativeQuery = true)
    public List<CarEntity> findCarByCondition(@Param("carNum") String carNum, @Param("dirveName") String dirveName,
                                              @Param("state") Integer state, @Param("startIndex") Integer startIndex,
                                              @Param("pageNum") Integer pageNum);


    /**
     *  查询总条数
     * @param carNum    车牌
     * @param dirveName 司机
     * @param state 状态
     * @return  int总条数
     */
    @Query(value = "select count(*) from t_car where if(:carNum is not null ,car_num like CONCAT('%',:carNum,'%'),1=1) " +
            "            and if(:dirveName is not null ,driver like CONCAT('%',:dirveName,'%'),1=1) " +
            "            and if(:state is not null ,state like CONCAT('%',:state,'%'),1=1)",nativeQuery = true)
    public int findTotalPageByCondition(@Param("carNum") String carNum, @Param("dirveName") String dirveName,
                                        @Param("state") Integer state);

    /**
     * 查询车牌是否存在
     * @param carNum
     * @return
     */
    public CarEntity findCarEntityByCarNum(String carNum);
}
