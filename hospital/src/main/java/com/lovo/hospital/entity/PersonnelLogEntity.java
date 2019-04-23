package com.lovo.hospital.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity@Data
@Table(name = "t_personnel_log")
public class PersonnelLogEntity {


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
    private String id;

    @Column(name = "start_time")
    private Timestamp startTime;

    @Column(name = "return_time")
    private Timestamp returnTime;

    @Column(name = "state")
    private int state;

    @ManyToOne
    @JoinColumn(name = "d_id")
    private DispatchEntity dispatchEntity;

    @OneToOne
    @JoinColumn(name = "p_id")
    private PersonnelEntity personnelEntity;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(Timestamp returnTime) {
        this.returnTime = returnTime;
    }

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
