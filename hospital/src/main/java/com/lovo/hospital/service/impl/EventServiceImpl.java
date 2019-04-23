package com.lovo.hospital.service.impl;

import com.lovo.hospital.dao.EventDao;
import com.lovo.hospital.dto.EventRecordListDto;
import com.lovo.hospital.entity.CarEntity;
import com.lovo.hospital.entity.EventEntity;
import com.lovo.hospital.entity.PersonnelEntity;
import com.lovo.hospital.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service(value = "eventService")
public class EventServiceImpl implements EventService {

    @Autowired
    private EventDao eventDao;

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
            erDto.setEventId(objs[0].toString());
            erDto.setEventIdNull(objs[1].toString());
            erDto.setEventName(objs[2].toString());
            erDto.setEventBeginTime((Date) objs[3]);
            erDto.setPeopleNum( Integer.parseInt(objs[4].toString()));
            erDto.setCarNum( Integer.parseInt(objs[5].toString()));
            erDto.setState( Integer.parseInt(objs[6].toString()));


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
    public EventEntity getEventInfo(String eid){
        EventEntity eventEntity = eventDao.findById(eid).get();
        //通过事件编号查所有的派遣

        return eventEntity;
    }

    @Override
    public List<PersonnelEntity> getEventInfoPersonnel(String eid) {
        List<Object[]> objects = eventDao.getEventInfoPersonnel(eid);
        List<PersonnelEntity> personnelEntityList=new ArrayList<>();

        for (Object[] obj :
                objects) {
            PersonnelEntity p=new PersonnelEntity();
            p.setId(obj[0].toString());
            p.setName(obj[1].toString());
            p.setPnum(obj[2].toString());
            p.setPosition(obj[3].toString());
            p.setSex(obj[4].toString());
            p.setState(Integer.parseInt(obj[5].toString()));
            p.setTel(obj[6].toString());
            p.setWorkTime(Integer.parseInt(obj[7].toString()));
            personnelEntityList.add(p);
        }
        return personnelEntityList;
    }

    @Override
    public List<CarEntity> getEventInfoCar(String eid) {
        List<Object[]> objects = eventDao.getEventInfoCar(eid);
        List<CarEntity> carEntityList=new ArrayList<>();
        for (Object[] obj :
                objects) {
            CarEntity c = new CarEntity();
            c.setId(obj[0].toString());
            c.setCarNum(obj[1].toString());
            c.setDriver(obj[2].toString());
            c.setState(Integer.parseInt(obj[3].toString()));
            carEntityList.add(c);
        }
        return carEntityList;
    }
}
