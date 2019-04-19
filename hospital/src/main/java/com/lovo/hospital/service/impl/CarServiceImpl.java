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
    public List<CarEntity> findCarByCondition(int pageNum,int showNum, String carNum, String dirveName, int state) {
        int startIndex=(pageNum-1)*showNum;
        return carDao.findCarByCondition(carNum,dirveName,state,startIndex,showNum);
    }

    @Override
    public int findTotalPageByCondition(String carNum, String dirveName, int state) {
        return carDao.findTotalPageByCondition(carNum,dirveName,state);
    }

    @Override
    public CarEntity saveCar(String carNum, String dirveName) {
        return null;
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
