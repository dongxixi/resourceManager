package com.lovo.hospital.service.impl;

import com.lovo.hospital.dao.CarDao;
import com.lovo.hospital.dao.ResourceStatisticsDao;
import com.lovo.hospital.entity.CarEntity;
import com.lovo.hospital.entity.ResourceStatisticsEntity;
import com.lovo.hospital.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service(value = "carService")
public class CarServiceImpl implements CarService {
    @Autowired
    private CarDao carDao;
    @Autowired
    private ResourceStatisticsDao resourceStatisticsDao;

    @Override
    public List<CarEntity> findCarByCondition(Integer pageNum, Integer showNum, String carNum, String dirveName, Integer state) {

        int startIndex = (pageNum - 1) * showNum;
        return carDao.findCarByCondition(carNum, dirveName, state, startIndex, showNum);
    }

    @Override
    public int findTotalPageByCondition(String carNum, String dirveName, Integer state, Integer showNum) {
        int totalCount = carDao.findTotalPageByCondition(carNum, dirveName, state);
        Integer totalPage = (totalCount + showNum - 1) / showNum;
        return totalPage;
    }

    @Override
    public CarEntity saveCar(String carNum, String dirveName) {
        CarEntity carEntity = new CarEntity();
        carEntity.setCarNum(carNum);
        carEntity.setDriver(dirveName);
        //默认添加状态为0，未派出
        carEntity.setState(0);
        return carDao.save(carEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CarEntity updateCar(CarEntity carEntity) {
        CarEntity save = carDao.save(carEntity);
        //在资源表里面car加1
        ResourceStatisticsEntity rs = resourceStatisticsDao.findById("1").get();
        //在原有的基础上加1
        rs.setcVacantNum(rs.getcVacantNum() + 1);
        resourceStatisticsDao.save(rs);
        return save;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer deleteCar(String carId) {
        //在资源表里面car加1
        ResourceStatisticsEntity rs = resourceStatisticsDao.findById("1").get();
        //在原有的基础上加1
        rs.setcVacantNum(rs.getcVacantNum() - 1);
        resourceStatisticsDao.save(rs);
        try {
            carDao.deleteById(carId);
            return 1;
        } catch (Exception e) {
            return 0;
        }

    }

    @Override
    public CarEntity infoCarById(String cid) {
        return carDao.findById(cid).get();
    }
}
