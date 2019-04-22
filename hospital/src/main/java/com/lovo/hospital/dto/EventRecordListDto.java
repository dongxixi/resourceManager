package com.lovo.hospital.dto;


import java.util.Date;

public class EventRecordListDto {

    private String eventIdNull;
    private String eventId;
    private String eventName;
    private Date eventBeginTime;
    private Integer peopleNum;
    private Integer carNum;
    private Integer state;

    public EventRecordListDto() {
    }

    public EventRecordListDto(String eventIdNull, String eventId, String eventName, Date eventBeginTime, Integer peopleNum, Integer carNum, Integer state) {
        this.eventIdNull = eventIdNull;
        this.eventId = eventId;
        this.eventName = eventName;
        this.eventBeginTime = eventBeginTime;
        this.peopleNum = peopleNum;
        this.carNum = carNum;
        this.state = state;
    }

    public String getEventIdNull() {
        return eventIdNull;
    }

    public void setEventIdNull(String eventIdNull) {
        this.eventIdNull = eventIdNull;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public Date getEventBeginTime() {
        return eventBeginTime;
    }

    public void setEventBeginTime(Date eventBeginTime) {
        this.eventBeginTime = eventBeginTime;
    }

    public Integer getPeopleNum() {
        return peopleNum;
    }

    public void setPeopleNum(Integer peopleNum) {
        this.peopleNum = peopleNum;
    }

    public Integer getCarNum() {
        return carNum;
    }

    public void setCarNum(Integer carNum) {
        this.carNum = carNum;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
