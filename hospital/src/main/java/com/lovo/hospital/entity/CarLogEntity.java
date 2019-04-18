package com.lovo.hospital.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "t_car_log")
public class CarLogEntity {
    private String id;
    private String startTime;
    private String returnTime;
    private int state;
    private CarEntity carEntity;

    @ManyToOne
    @JoinColumn()
    public CarEntity getCarEntity() {
        return carEntity;
    }

    public void setCarEntity(CarEntity carEntity) {
        this.carEntity = carEntity;
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
    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    @Basic
    @Column(name = "return_time")
    public String getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(String returnTime) {
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
    public int hashCode() {
        return Objects.hash(id, startTime, returnTime, state);
    }
}
