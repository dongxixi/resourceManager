package com.lovo.hospital.service;

import com.lovo.hospital.dto.EventRecordListDto;

import java.util.List;

public interface EventService {

    /**
     * 条件得到事件列表
     * @param currPage 当前页
     * @param eventName 事件名称
     * @param startTime 条件开始时间
     * @param endTime  条件结束时间
     * @return
     */
    List<EventRecordListDto> getEventDtoList(Integer currPage, String eventName, String startTime, String endTime);

    /**
     * 总页数
     * @param carNum 当前页
     * @param eventName 事件名称
     * @param startTime 条件开始时间
     * @param endTime 条件结束时间
     * @return
     */
    Integer getTotalPage(Integer carNum, String eventName, String startTime, String endTime);
}
