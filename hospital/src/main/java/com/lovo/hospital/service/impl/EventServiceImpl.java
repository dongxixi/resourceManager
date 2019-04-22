package com.lovo.hospital.service.impl;

import com.lovo.hospital.dao.EventDao;
import com.lovo.hospital.dto.EventRecordListDto;
import com.lovo.hospital.entity.EventEntity;
import com.lovo.hospital.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        List<Object> objects =eventDao.getEventDtoList(starIndex,10,eventName,startTime,endTime);
        List<EventRecordListDto> eventRecordListDtos = new ArrayList<>();
        EventRecordListDto eventRecordListDto = null;
        for (Object object : objects) {
            eventRecordListDto = (EventRecordListDto) object;
            eventRecordListDtos.add(eventRecordListDto);
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
        Integer totalCount = eventDao.getTotalCountByCondition(eventName,startTime,endTime);
        Integer totalPage = (totalCount + pageSize - 1) / pageSize;
        return totalPage;
    }


}
