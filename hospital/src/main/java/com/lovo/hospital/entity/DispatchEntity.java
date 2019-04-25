package com.lovo.hospital.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "t_dispatch")
public class DispatchEntity {
    private String requestId;
    private EventEntity eventEntity;
    private int pNum;
    private int cNum;
    private int state;

    private Set<PersonnelLogEntity> personnelLogEntities;


    private Set<CarLogEntity> carLogEntities;

    @OneToMany(mappedBy = "dispatchEntity")
    public Set<CarLogEntity> getCarLogEntities() {
        return carLogEntities;
    }

    public void setCarLogEntities(Set<CarLogEntity> carLogEntities) {
        this.carLogEntities = carLogEntities;
    }

    @OneToMany(mappedBy = "dispatchEntity")
    public Set<PersonnelLogEntity> getPersonnelLogEntities() {
        return personnelLogEntities;
    }

    public void setPersonnelLogEntities(Set<PersonnelLogEntity> personnelLogEntities) {
        this.personnelLogEntities = personnelLogEntities;
    }

    @Id
    @Column(name = "request_id", length = 32)
    /*@GenericGenerator(name = "powerUUID", strategy = "uuid")
    @GeneratedValue(generator = "powerUUID")*/
    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    @Basic
    @Column(name = "p_num")
    public int getpNum() {
        return pNum;
    }

    public void setpNum(int pNum) {
        this.pNum = pNum;
    }

    @Basic
    @Column(name = "c_num")
    public int getcNum() {
        return cNum;
    }

    public void setcNum(int cNum) {
        this.cNum = cNum;
    }

    @ManyToOne
    @JoinColumn(name = "e_id")
    public EventEntity getEventEntity() {
        return eventEntity;
    }

    public void setEventEntity(EventEntity eventEntity) {
        this.eventEntity = eventEntity;
    }

    @Column(name = "state")
    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public DispatchEntity() {
    }

    public DispatchEntity(String requestId, EventEntity eventEntity, int pNum, int cNum) {
        this.requestId = requestId;
        this.eventEntity = eventEntity;
        this.pNum = pNum;
        this.cNum = cNum;
    }

    @Override
    public int hashCode() {
        return Objects.hash(requestId, pNum, cNum);
    }
}
