package com.lovo.police_office.service;


import com.lovo.police_office.entity.CarEntity;

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
    public List<CarEntity> findCarByCondition(Integer pageNum, Integer showNum, String carNum, String dirveName, Integer state);

    /**
     * 查询总页数
     * @param carNum    车牌
     * @param dirveName 司机
     * @param state 状态
     * @param showNum 每页显示条数
     * @return  总条数
     */
    public int findTotalPageByCondition(String carNum, String dirveName, Integer state, Integer showNum);

    /**
     * 存储车辆
     * @param carNum    车辆名称
     * @param dirveName 司机名字
     * @return  装有存储的实体，失败则为空
     */
    public CarEntity saveCar(String carNum, String dirveName);

    /**
     * 查询车辆的详情
     * @param cid   车辆ID
     * @return  车辆实体
     */
    public CarEntity infoCarById(String cid);

    /**
     * 修改车辆
     * @param carEntity
     * @return  装有修改后的实体，失败则为空
     */
    public CarEntity updateCar(CarEntity carEntity);

    /**
     * 删除车辆
     * @param carId 车辆Id
     * @return
     */
    public Integer deleteCar(String carId);

    /**
     * 查询车牌是否存在
     * @param carNum
     * @return
     */
    public boolean findCarEntityByCarNumExists(String carNum);

}
