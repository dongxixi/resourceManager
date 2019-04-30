package com.lovo.fire_company.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "t_role")
public class RoleEntity {
    @Id
    @Column(name="rid",length=32)
    @GenericGenerator(name="roleUUID",strategy="uuid")
    @GeneratedValue(generator="roleUUID")
    private String rId;
    @Column(length=32)
    private String roleName;
    @OneToMany(mappedBy="role")
    private Set<UserRoleEntity> setUserRole;
	@OneToMany(mappedBy="role")
    private Set<RolePowerEntity> setRolePower;

    public String getrId() {
        return rId;
    }

    public void setrId(String rId) {
        this.rId = rId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Set<UserRoleEntity> getSetUserRole() {
        return setUserRole;
    }

    public void setSetUserRole(Set<UserRoleEntity> setUserRole) {
        this.setUserRole = setUserRole;
    }

    public Set<RolePowerEntity> getSetRolePower() {
        return setRolePower;
    }

    public void setSetRolePower(Set<RolePowerEntity> setRolePower) {
        this.setRolePower = setRolePower;
    }
}
