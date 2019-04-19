package com.lovo.hospital.dao;

import com.lovo.hospital.entity.CarEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface CarDao extends CrudRepository<CarEntity,String> {

    /**
     * 通过条件来查询车辆情况
     * @param carNum    车辆情况
     * @param dirveName 司机
     * @param state     状态
     * @param startIndex    开始下标
     * @param endIndex  结束下标
     * @return  装有car的集合
     */
    @Query(value = "select * from t_car where if(?1 !='',car_num=?1,1=1) " +
            "and if(?2 !='',driver=?2,1=1) " +
            "and if(?3 !='',state=?3,1=1) limit ?4,?5",nativeQuery = true)
    public List<CarEntity> findCarByCondition(String carNum,String dirveName,int state,int startIndex,int endIndex);
}
