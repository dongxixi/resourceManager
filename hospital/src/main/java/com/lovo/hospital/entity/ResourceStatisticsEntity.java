package com.lovo.hospital.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "t_resource_statistics")
public class ResourceStatisticsEntity {
    private String id;
    private int pRescuingNum;
    private int cVacantNum;

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
    @Column(name = "p_rescuing_num")
    public int getpRescuingNum() {
        return pRescuingNum;
    }

    public void setpRescuingNum(int pRescuingNum) {
        this.pRescuingNum = pRescuingNum;
    }

    @Basic
    @Column(name = "c_vacant_num")
    public int getcVacantNum() {
        return cVacantNum;
    }

    public void setcVacantNum(int cVacantNum) {
        this.cVacantNum = cVacantNum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResourceStatisticsEntity that = (ResourceStatisticsEntity) o;
        return pRescuingNum == that.pRescuingNum &&
                cVacantNum == that.cVacantNum &&
                Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, pRescuingNum, cVacantNum);
    }
}
