package com.lovo.police_office.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "t_power")
public class PowerEntity {
    @Id
    @Column(name="pid",length=32)
    @GenericGenerator(name="powerUUID",strategy="uuid")
    @GeneratedValue(generator="powerUUID")
    private String pId;
    @Column(length=32)
    private String powernName;
    @OneToMany(mappedBy="power")
    private Set<RolePowerEntity> setRolePower;

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getPowernName() {
        return powernName;
    }

    public void setPowernName(String powernName) {
        this.powernName = powernName;
    }

    public Set<RolePowerEntity> getSetRolePower() {
        return setRolePower;
    }

    public void setSetRolePower(Set<RolePowerEntity> setRolePower) {
        this.setRolePower = setRolePower;
    }
}
