package com.lovo.hospital.service.impl;

import com.lovo.hospital.dao.CarLogDao;
import com.lovo.hospital.dao.EventDao;
import com.lovo.hospital.dao.PersonLogDao;
import com.lovo.hospital.dto.EventRecordListDto;
import com.lovo.hospital.entity.*;
import com.lovo.hospital.service.DispatchService;
import com.lovo.hospital.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service(value = "eventService")
public class EventServiceImpl implements EventService {

    @Autowired
    private EventDao eventDao;
    @Autowired
    private CarLogDao carLogDao;
    @Autowired
    private PersonLogDao personLogDao;
    @Autowired
    private DispatchService dispatchService;

    /**
     * 条件得到事件列表
     *
     * @param currPage  当前页
     * @param eventName 事件名称
     * @param startTime 条件开始时间
     * @param endTime   条件结束时间
     * @return
     */
    @Override
    public List<EventRecordListDto> getEventDtoList(Integer currPage, String eventName, String startTime, String endTime) {
        Integer starIndex = (currPage - 1) * currPage;
        List<Object[]> objects = eventDao.getEventDtoList(starIndex, 10, eventName, startTime, endTime);
        List<EventRecordListDto> eventRecordListDtos = new ArrayList<>();


        for (Object[] objs : objects) {
            EventRecordListDto erDto = new EventRecordListDto();
            String eventId = objs[0].toString();
            erDto.setEventId(eventId);
            erDto.setEventName(objs[1].toString());
            erDto.setEventBeginTime((Date) objs[2]);
            erDto.setPeopleNum(Integer.parseInt(objs[3].toString()));
            erDto.setCarNum(Integer.parseInt(objs[4].toString()));

            //判断有没有没有处理的资源请求
            List<DispatchEntity> dispatchEntities = dispatchService.getDispatchByEventId(eventId, 0);
            if (dispatchEntities.size()>0) {
                erDto.setState(0);
            }else {
                erDto.setState(1);
            }
            eventRecordListDtos.add(erDto);
        }
        return eventRecordListDtos;
    }

    /**
     * 总页数
     *
     * @param pageSize  页量
     * @param eventName 事件名称
     * @param startTime 条件开始时间
     * @param endTime   条件结束时间
     * @return
     */
    @Override
    public Integer getTotalPage(Integer pageSize, String eventName, String startTime, String endTime) {
        Integer totalCount = eventDao.getTotalCountByCondition(eventName, startTime, endTime);
        Integer totalPage = (totalCount + pageSize - 1) / pageSize;
        return totalPage;
    }

    @Override
    public EventEntity getEventInfo(String eid) {
        EventEntity eventEntity = eventDao.findById(eid).get();
        //通过事件编号查所有的派遣

        return eventEntity;
    }

    @Override
    public List<PersonnelLogEntity> getOuterPersons(String eid) {
        List<PersonnelLogEntity> ps = getEventInfoPersonnel(eid);
        List<PersonnelLogEntity> list = new ArrayList<>();
        for (PersonnelLogEntity p : ps) {
            if (p.getState() > 0) {
                list.add(p);
            }
        }
        return list;
    }

    @Override
    public List<PersonnelLogEntity> getInnerPersons(String eid) {
        List<PersonnelLogEntity> list = new ArrayList<>();
        for (PersonnelLogEntity p : getEventInfoPersonnel(eid)) {
            if (p.getState() < 1) {
                list.add(p);
            }
        }
        return list;
    }

    @Override
    public List<CarLogEntity> getOuterCars(String eid) {
        List<CarLogEntity> list = new ArrayList<>();
        for (CarLogEntity c : getEventInfoCar(eid)) {
            if (c.getState() > 0) {
                list.add(c);
            }
        }
        return list;
    }

    @Override
    public List<CarLogEntity> getInnerCars(String eid) {
        List<CarLogEntity> list = new ArrayList<>();
        for (CarLogEntity c : getEventInfoCar(eid)) {
            if (c.getState() < 1) {
                list.add(c);
            }
        }
        return list;
    }

    @Override
    public boolean isHaveEventById(String eid) {
        return eventDao.existsById(eid);
    }

    @Override
    public EventEntity saveEvent(EventEntity eventEntity) {

        return eventDao.save(eventEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void returnPersonAndCar(String persons, String cars) {
//        if (persons != null && !"".equals(persons))
//            for (String s : persons.split("[,]")) {
//                PersonnelLogEntity pl = personLogDao.findById(s).get();
//                pl.setReturnTime(new Timestamp(System.currentTimeMillis()));
//                pl.setState(0);
//                pl.getPersonnelEntity().setState(0);
//                personLogDao.save(pl);
//            }
//        if (cars != null && !"".equals(cars))
//            for (String id : cars.split(",")) {
//                CarLogEntity c = carLogDao.findById(id).get();
//                c.setReturnTime(new Timestamp(System.currentTimeMillis()));
//                c.setState(0);
//                c.getCarEntity().setState(0);
//                carLogDao.save(c);
//            }
        if (persons != null && !"".equals(persons)) {
            Iterable<PersonnelLogEntity> ps = personLogDao.findAllById(Arrays.asList(persons.split(",")));
            for (PersonnelLogEntity pl : ps) {
                pl.setReturnTime(new Timestamp(System.currentTimeMillis()));
                pl.setState(0);
                pl.getPersonnelEntity().setState(0);
            }
            personLogDao.saveAll(ps);
        }
        if (cars != null && !"".equals(cars)) {
            Iterable<CarLogEntity> cs = carLogDao.findAllById(Arrays.asList(cars.split(",")));
            for (CarLogEntity c : cs) {
                c.setReturnTime(new Timestamp(System.currentTimeMillis()));
                c.setState(0);
                c.getCarEntity().setState(0);
            }
            carLogDao.saveAll(cs);
        }


    }

    @Override
    public List<PersonnelLogEntity> getEventInfoPersonnel(String eid) {
        List<Object[]> objects = eventDao.getEventInfoPersonnel(eid);
        List<PersonnelLogEntity> personnelLogEntityList = new ArrayList<>();

        for (Object[] obj :
                objects) {
            PersonnelLogEntity pl = new PersonnelLogEntity();
            pl.setId(obj[8].toString());
            pl.setState(Integer.parseInt(obj[9].toString()));

            PersonnelEntity p = new PersonnelEntity();
            p.setId(obj[0].toString());
            p.setName(obj[1].toString());
            p.setPnum(obj[2].toString());
            p.setPosition(obj[3].toString());
            p.setSex(obj[4].toString());
            p.setState(Integer.parseInt(obj[5].toString()));
            p.setTel(obj[6].toString());
            p.setWorkTime(Integer.parseInt(obj[7].toString()));

            pl.setPersonnelEntity(p);

            personnelLogEntityList.add(pl);
        }
        return personnelLogEntityList;
    }

    @Override
    public List<CarLogEntity> getEventInfoCar(String eid) {
        List<Object[]> objects = eventDao.getEventInfoCar(eid);
        List<CarLogEntity> carLogEntityList = new ArrayList<>();
        for (Object[] obj :
                objects) {
            CarLogEntity cl = new CarLogEntity();
            cl.setId(obj[4].toString());
            cl.setState(Integer.parseInt(obj[5].toString()));

            CarEntity c = new CarEntity();
            c.setId(obj[0].toString());
            c.setCarNum(obj[1].toString());
            c.setDriver(obj[2].toString());
            c.setState(Integer.parseInt(obj[3].toString()));

            cl.setCarEntity(c);
            carLogEntityList.add(cl);
        }
        return carLogEntityList;
    }
}
