package com.lovo.hospital.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "t_personnel_log")
public class PersonnelLogEntity {
    private String id;
    private Timestamp startTime;
    private Timestamp returnTime;
    private int state;

    private PersonnelEntity personnelEntity;
    private DispatchEntity dispatchEntity;

    @ManyToOne
    @JoinColumn(name = "p_id")
    public PersonnelEntity getPersonnelEntity() {
        return personnelEntity;
    }

    public void setPersonnelEntity(PersonnelEntity personnelEntity) {
        this.personnelEntity = personnelEntity;
    }

    @ManyToOne
    @JoinColumn(name = "d_id")
    public DispatchEntity getDispatchEntity() {
        return dispatchEntity;
    }

    public void setDispatchEntity(DispatchEntity dispatchEntity) {
        this.dispatchEntity = dispatchEntity;
    }

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
    @Column(name = "start_time")
    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    @Basic
    @Column(name = "return_time")
    public Timestamp getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(Timestamp returnTime) {
        this.returnTime = returnTime;
    }

    @Basic
    @Column(name = "state")
    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonnelLogEntity that = (PersonnelLogEntity) o;
        return state == that.state &&
                Objects.equals(id, that.id) &&
                Objects.equals(startTime, that.startTime) &&
                Objects.equals(returnTime, that.returnTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, startTime, returnTime, state);
    }
}
