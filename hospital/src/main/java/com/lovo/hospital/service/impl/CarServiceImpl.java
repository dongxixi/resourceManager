package com.lovo.hospital.service.impl;

import com.lovo.hospital.dao.CarDao;
import com.lovo.hospital.entity.CarEntity;
import com.lovo.hospital.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "carService")
public class CarServiceImpl implements CarService {
    @Autowired
    private CarDao carDao;
    @Override
    public List<CarEntity> findCarByCondition(Integer pageNum,Integer showNum, String carNum, String dirveName, Integer state) {

        int startIndex=(pageNum-1)*showNum;
        return carDao.findCarByCondition(carNum,dirveName,state,startIndex,showNum);
    }

    @Override
    public int findTotalPageByCondition(String carNum, String dirveName, Integer state,Integer showNum) {
        int totalCount = carDao.findTotalPageByCondition(carNum, dirveName, state);
        Integer totalPage = (totalCount + showNum - 1) / showNum;
        return totalPage;
    }

    @Override
    public CarEntity saveCar(String carNum, String dirveName) {
        CarEntity carEntity=new CarEntity();
        carEntity.setCarNum(carNum);
        carEntity.setDriver(dirveName);
        //默认添加状态为0，未派出
        carEntity.setState(0);
        return carDao.save(carEntity);
    }

    @Override
    public CarEntity updateCar(String carId, String dirveName) {
        return null;
    }

    @Override
    public CarEntity deleteCar(String carId) {
        return null;
    }
}
