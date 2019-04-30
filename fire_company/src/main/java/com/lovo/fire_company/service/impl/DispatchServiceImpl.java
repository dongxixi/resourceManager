package com.lovo.fire_company.service.impl;

import com.lovo.fire_company.dao.*;
import com.lovo.fire_company.dto.CarDto;
import com.lovo.fire_company.dto.EventSendDto;
import com.lovo.fire_company.dto.PersonDto;
import com.lovo.fire_company.entity.*;
import com.lovo.fire_company.service.CarService;
import com.lovo.fire_company.service.DispatchService;
import com.lovo.fire_company.service.PersonnelService;
import com.lovo.fire_company.service.ResourceStatisticsService;
import com.lovo.fire_company.util.MQUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

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
    private ResourceStatisticsService resourceStatisticsService;

    @Autowired
    private DispatchDao dispatchDao;

    @Autowired
    private EventDao eventDao;
    @Autowired
    private PensonnelDao pensonnelDao;
    @Autowired
    private CarDao carDao;

    @Autowired
    private MQUtil mqUtil;

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

        //获取库存实例
        ResourceStatisticsEntity resourceStatisticsEntity = resourceStatisticsService.getResourceStatisticsEntity();

        //创建mq传送数据对象
        EventSendDto eventSendDto = new EventSendDto();
        List<CarDto> carDtos = new ArrayList<>();
        eventSendDto.setCarDtos(carDtos);
        List<PersonDto> personDtos = new ArrayList<>();
        eventSendDto.setPersonDtos(personDtos);

        //通过返回的字符串，获取所有需要派出车辆的id，在添加到记录里
        ArrayList<CarLogEntity> carList = new ArrayList<>();
        for (String carId : carArray) {
            CarLogEntity carLog = new CarLogEntity();
            carLog.setDispatchEntity(dispatchEntity);

            CarEntity car = carService.infoCarById(carId);
            carLog.setCarEntity(car);

            Timestamp startTime = new Timestamp(System.currentTimeMillis());
            carLog.setStartTime(startTime);

            carLog.setState(1);

            //修改相应车辆状态
            CarEntity carEntity = carDao.findById(carId).get();
            carEntity.setState(1);

            carList.add(carLog);

            //为mq传输数据添加车辆信息
            carDtos.add(toCarDto(car));
        }

        //修改库存
        resourceStatisticsEntity.setcVacantNum(resourceStatisticsEntity.getcVacantNum() - carList.size());

        carLogDao.saveAll(carList);
        dispatchEntity.setState(1);
        dispatchDao.save(dispatchEntity);



        //通过返回的字符串，获取所有需要派出人员的id，在添加到记录里
        ArrayList<PersonnelLogEntity> personList = new ArrayList<>();
        for (String personId : personArray) {
            PersonnelLogEntity personnelLog = new PersonnelLogEntity();
            personnelLog.setDispatchEntity(dispatchEntity);

            PersonnelEntity person = personnelService.selectOnePerson(personId);
            personnelLog.setPersonnelEntity(person);

            Timestamp startTime = new Timestamp(System.currentTimeMillis());
            personnelLog.setStartTime(startTime);

            personnelLog.setState(1);

            //修改相应人员状态
            PersonnelEntity personnelEntity = pensonnelDao.findById(personId).get();
            personnelEntity.setState(1);

            personList.add(personnelLog);

            //为mq传送数据添加人员数据
            personDtos.add(toPersonDto(person));
        }

        resourceStatisticsEntity.setpRescuingNum(resourceStatisticsEntity.getpRescuingNum() - personList .size());

        personLogDao.saveAll(personList);

        //发生mq数据，告知调度中心

        mqUtil.sendMQ(eventSendDto);


        return 0;//正常完成返回0
    }

    //封装数据到carDto
    private CarDto toCarDto(CarEntity carEntity) {
        CarDto carDto = new CarDto();

        carDto.setId(carEntity.getId());
        carDto.setCarNum(carEntity.getCarNum());
        carDto.setDriver(carEntity.getDriver());
        carDto.setStartTime(new Timestamp(System.currentTimeMillis()));

        return carDto;
    }

    //封装数据到PersonDto
    private PersonDto toPersonDto(PersonnelEntity personnelEntity) {
        PersonDto personDto = new PersonDto();

        personDto.setId(personnelEntity.getId());
        personDto.setPersonName(personnelEntity.getName());
        personDto.setTel(personnelEntity.getTel());
        personDto.setStartTime(new Timestamp(System.currentTimeMillis()));

        return personDto;
    }

    @Override
    public DispatchEntity getDispatchById(String id) {
        return dispatchDao.findById(id).get();
    }

    /**
     * 通过事件ID得到该事件的资源请求集合
     * @param id 事件ID
     * @param state 处理状态
     * @return
     */
    @Override
    public List<DispatchEntity> getDispatchByEventId(String id, Integer state) {
        return dispatchDao.getDispatchByEventId(id,state);
    }

    @Override
    public DispatchEntity saveDispatchEntity(DispatchEntity dispatchEntity) {
        return dispatchDao.save(dispatchEntity);
    }
}
