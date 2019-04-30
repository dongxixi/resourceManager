package com.lovo.police_office.service;



import com.lovo.police_office.dto.EventRecordListDto;
import com.lovo.police_office.entity.CarLogEntity;
import com.lovo.police_office.entity.EventEntity;
import com.lovo.police_office.entity.PersonnelLogEntity;

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
    public List<PersonnelLogEntity> getEventInfoPersonnel(String eid);

    /**
     * 通过事件信息查询关联的所有的car信息
     * @param eid
     * @return
     */
    public List<CarLogEntity> getEventInfoCar(String eid);

    /**
     * 通过事件id查询事件详情
     * @param eid
     * @return
     */
    public EventEntity getEventInfo(String eid);


    /**
     * 通过事件id查询说关联的在外人员信息
     *
     * @param eid
     * @return
     */
    public List<PersonnelLogEntity> getOuterPersons(String eid);

    /**
     * 通过事件id查询说关联的归巢人员信息
     *
     * @param eid
     * @return
     */
    public List<PersonnelLogEntity> getInnerPersons(String eid);

    /**
     * 通过事件id查询说关联的在外车辆
     *
     * @param eid
     * @return
     */
    public List<CarLogEntity> getOuterCars(String eid);

    /**
     * 通过事件id查询说关联的归巢车辆
     *
     * @param eid
     * @return
     */
    public List<CarLogEntity> getInnerCars(String eid);

    /**
     *
     * 将车辆人员作归队处理
     * @param persons 人员ID字符串， 逗号分隔符
     * @param cars  车辆ID字符串， 逗号分隔符
     */
    public void returnPersonAndCar(String id, String persons, String cars);

    /**
     * 添加事件
     * @param eventEntity
     * @return
     */
    public EventEntity saveEvent(EventEntity eventEntity);

    /**
     * 通过ID判断事件实体是否存在
     * @param eid
     * @return
     */
    public boolean isHaveEventById(String eid);
}
