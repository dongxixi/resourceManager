package com.lovo.hospital.service.impl;

import com.lovo.hospital.dao.EventDao;
import com.lovo.hospital.dto.EventRecordListDto;
import com.lovo.hospital.entity.EventEntity;
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


}
