package com.lovo.hospital.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "t_role_power")
public class RolePowerEntity {
    @Id
    @GenericGenerator(name="roleUserUUID",strategy="uuid")
    @GeneratedValue(generator="roleUserUUID")
    @Column(length=32)
    private String rpId;
    @ManyToOne
    @JoinColumn(name="role_id")
    private RoleEntity role;
    @ManyToOne
    @JoinColumn(name="power_id")
    private PowerEntity power;

    public String getRpId() {
        return rpId;
    }

    public void setRpId(String rpId) {
        this.rpId = rpId;
    }

    public RoleEntity getRole() {
        return role;
    }

    public void setRole(RoleEntity role) {
        this.role = role;
    }

    public PowerEntity getPower() {
        return power;
    }

    public void setPower(PowerEntity power) {
        this.power = power;
    }
}
