package com.lovo.hospital.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "t_event")
public class EventEntity {
    private String id;
    private String eventId;
    private String eventName;
    private String eventType;
    private String eventArea;
    private Timestamp eventTime;
    private String alarmPerson;
    private String alarmTel;
    private String alarmAddress;
    private Integer eventProceed;
    private String pId;

    @Id
    @Column(name="id",length=32)
    @GenericGenerator(name="powerUUID",strategy="uuid")
    @GeneratedValue(generator="powerUUID")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "event_id")
    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    @Basic
    @Column(name = "event_name")
    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    @Basic
    @Column(name = "event_type")
    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    @Basic
    @Column(name = "event_area")
    public String getEventArea() {
        return eventArea;
    }

    public void setEventArea(String eventArea) {
        this.eventArea = eventArea;
    }

    @Basic
    @Column(name = "event_time")
    public Timestamp getEventTime() {
        return eventTime;
    }

    public void setEventTime(Timestamp eventTime) {
        this.eventTime = eventTime;
    }

    @Basic
    @Column(name = "alarm_person")
    public String getAlarmPerson() {
        return alarmPerson;
    }

    public void setAlarmPerson(String alarmPerson) {
        this.alarmPerson = alarmPerson;
    }

    @Basic
    @Column(name = "alarm_tel")
    public String getAlarmTel() {
        return alarmTel;
    }

    public void setAlarmTel(String alarmTel) {
        this.alarmTel = alarmTel;
    }

    @Basic
    @Column(name = "alarm_address")
    public String getAlarmAddress() {
        return alarmAddress;
    }

    public void setAlarmAddress(String alarmAddress) {
        this.alarmAddress = alarmAddress;
    }

    @Basic
    @Column(name = "event_proceed")
    public Integer getEventProceed() {
        return eventProceed;
    }

    public void setEventProceed(Integer eventProceed) {
        this.eventProceed = eventProceed;
    }

    @Basic
    @Column(name = "p_id")
    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventEntity that = (EventEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(eventId, that.eventId) &&
                Objects.equals(eventName, that.eventName) &&
                Objects.equals(eventType, that.eventType) &&
                Objects.equals(eventArea, that.eventArea) &&
                Objects.equals(eventTime, that.eventTime) &&
                Objects.equals(alarmPerson, that.alarmPerson) &&
                Objects.equals(alarmTel, that.alarmTel) &&
                Objects.equals(alarmAddress, that.alarmAddress) &&
                Objects.equals(eventProceed, that.eventProceed) &&
                Objects.equals(pId, that.pId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, eventId, eventName, eventType, eventArea, eventTime, alarmPerson, alarmTel, alarmAddress, eventProceed, pId);
    }
}
