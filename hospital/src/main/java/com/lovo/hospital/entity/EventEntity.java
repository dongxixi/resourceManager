package com.lovo.hospital.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity@Data@NoArgsConstructor@AllArgsConstructor
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

    @Column(name = "p_id")
    private String pId;

}
