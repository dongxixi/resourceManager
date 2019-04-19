package com.lovo.hospital.service;

import com.lovo.hospital.entity.CarEntity;

import java.util.List;

/**
 * 车辆service
 * @author 狄亚宁
 */
public interface CarService {
    /**
     *  通过条件来查询车辆情况
     * @param pageNum   当前页数
     * @param carNum    车牌号
     * @param dirveName 司机
     * @param state 状态
     * @param showNum  每页显示多少条
     * @return  车辆集合
     */
    public List<CarEntity> findCarByCondition(int pageNum,int showNum,String carNum,String dirveName,int state);

    public int findTotalPageByCondition(String carNum,String dirveName,int state);

    /**
     * 存储车辆
     * @param carNum    车辆名称
     * @param dirveName 司机名字
     * @return  装有存储的实体，失败则为空
     */
    public CarEntity saveCar(String carNum,String dirveName);

    /**
     * 修改车辆
     * @param carId 车辆ID
     * @param dirveName 司机名字
     * @return  装有修改后的实体，失败则为空
     */
    public CarEntity updateCar(String carId,String dirveName);

    /**
     * 删除车辆
     * @param carId 车辆Id
     * @return
     */
    public CarEntity deleteCar(String carId);

}
