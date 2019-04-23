package com.lovo.hospital.service;

import com.lovo.hospital.dto.EventRecordListDto;
import com.lovo.hospital.entity.CarEntity;
import com.lovo.hospital.entity.EventEntity;
import com.lovo.hospital.entity.PersonnelEntity;
import org.springframework.data.jpa.repository.Query;

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
    public List<EventRecordListDto> getEventDtoList(Integer currPage, String eventName, String startTime, String endTime);

    /**
     * 总页数
     * @param pageSize 页量
     * @param eventName 事件名称
     * @param startTime 条件开始时间
     * @param endTime 条件结束时间
     * @return
     */
    public Integer getTotalPage(Integer pageSize, String eventName, String startTime, String endTime);


    /**
     * 通过事件id查询说关联的所有人员信息
     * @param eid
     * @return
     */
    public List<PersonnelEntity> getEventInfoPersonnel (String eid);

    /**
     * 通过事件信息查询关联的所有的car信息
     * @param eid
     * @return
     */
    public List<CarEntity> getEventInfoCar (String eid);

    /**
     * 通过事件id查询事件详情
     * @param eid
     * @return
     */
    public EventEntity getEventInfo(String eid);
}
