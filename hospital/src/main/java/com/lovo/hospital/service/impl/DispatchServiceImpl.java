package com.lovo.hospital.service.impl;

import com.lovo.hospital.dao.*;
import com.lovo.hospital.entity.*;
import com.lovo.hospital.service.CarService;
import com.lovo.hospital.service.DispatchService;
import com.lovo.hospital.service.PersonnelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;

@Service
public class DispatchServiceImpl implements DispatchService {

    @Autowired
    private CarLogDao carLogDao;
    @Autowired
    private PersonLogDao personLogDao;
    @Autowired
    private CarService carService;
    @Autowired
    private PersonnelService personnelService;

    @Autowired
    private DispatchDao dispatchDao;

    @Autowired
    private EventDao eventDao;
    @Autowired
    private PensonnelDao pensonnelDao;

    @Transactional
    @Override
    public int dispatch(String dispatchId, String personInCharge, String personUpdateList, String carUpdateList) {

        //判断是否有派出单
        DispatchEntity dispatchEntity = dispatchDao.findById(dispatchId).get();
        if (dispatchEntity == null) {
            return 1;//返回1表示没有找到对应派出单
        }

        //添加负责人信息
        EventEntity eventEntity = dispatchEntity.getEventEntity();
        PersonnelEntity principal = pensonnelDao.findById(personInCharge).get();
        eventEntity.setPersonnelEntity(principal);
        eventDao.save(eventEntity);

        //验证车辆数量
        String[] carArray = carUpdateList.split(",");
        if (carArray.length != dispatchEntity.getcNum()) {
            return 2;//选择的车辆数量不正确
        }
        //验证车辆数量
        String[] personArray = personUpdateList.split(",");
        if (personArray.length != dispatchEntity.getpNum()) {
            return 3;//选择的入数不正确
        }

        //通过返回的字符串，获取所有需要派出车辆的id，在添加到记录里
        ArrayList<CarLogEntity> carList = new ArrayList<>();
        for (String carId : carArray) {
            CarLogEntity carLog = new CarLogEntity();
            carLog.setDispatchEntity(dispatchEntity);

            CarEntity car = carService.infoCarById(carId);
            carLog.setCarEntity(car);

            Timestamp startTime = new Timestamp(System.currentTimeMillis());
            carLog.setStartTime(startTime);

            carLog.setState(0);

            carList.add(carLog);
        }

        carLogDao.saveAll(carList);


        //通过返回的字符串，获取所有需要派出人员的id，在添加到记录里
        ArrayList<PersonnelLogEntity> personList = new ArrayList<>();
        for (String personId : personArray) {
            PersonnelLogEntity personnelLog = new PersonnelLogEntity();
            personnelLog.setDispatchEntity(dispatchEntity);

            PersonnelEntity person = personnelService.selectOne(personId);
            personnelLog.setPersonnelEntity(person);

            Timestamp startTime = new Timestamp(System.currentTimeMillis());
            personnelLog.setStartTime(startTime);

            personnelLog.setState(0);

            personList.add(personnelLog);
        }

        personLogDao.saveAll(personList);


        return 0;//正常完成返回0
    }
}
