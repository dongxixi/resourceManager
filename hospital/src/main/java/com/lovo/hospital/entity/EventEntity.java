package com.lovo.hospital.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity@Data@NoArgsConstructor@AllArgsConstructor    //实体类，get\set方法，无参构造器，有参构造器
@Table(name = "t_event")
public class EventEntity {

    @Id
    @Column(name="id",length=32)
    @GenericGenerator(name="powerUUID",strategy="uuid")
    @GeneratedValue(generator="powerUUID")
    private String id;

    @Column(name = "event_name")
    private String eventName;

    @Column(name = "event_type")
    private String eventType;

    @Column(name = "event_area")
    private String eventArea;

    @Column(name = "event_time")
    private Timestamp eventTime;

    @Column(name = "alarm_person")
    private String alarmPerson;

    @Column(name = "alarm_tel")
    private String alarmTel;

    @Column(name = "alarm_address")
    private String alarmAddress;

    @Column(name = "event_proceed")
    private Integer eventProceed;

    @OneToOne
    @JoinColumn(name = "p_id")
    private PersonnelEntity personnelEntity;





    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getEventArea() {
        return eventArea;
    }

    public void setEventArea(String eventArea) {
        this.eventArea = eventArea;
    }

    public Timestamp getEventTime() {
        return eventTime;
    }

    public void setEventTime(Timestamp eventTime) {
        this.eventTime = eventTime;
    }

    public String getAlarmPerson() {
        return alarmPerson;
    }

    public void setAlarmPerson(String alarmPerson) {
        this.alarmPerson = alarmPerson;
    }

    public String getAlarmTel() {
        return alarmTel;
    }

    public void setAlarmTel(String alarmTel) {
        this.alarmTel = alarmTel;
    }

    public String getAlarmAddress() {
        return alarmAddress;
    }

    public void setAlarmAddress(String alarmAddress) {
        this.alarmAddress = alarmAddress;
    }

    public Integer getEventProceed() {
        return eventProceed;
    }

    public void setEventProceed(Integer eventProceed) {
        this.eventProceed = eventProceed;
    }

    public PersonnelEntity getPersonnelEntity() {
        return personnelEntity;
    }

    public void setPersonnelEntity(PersonnelEntity personnelEntity) {
        this.personnelEntity = personnelEntity;
    }
}
