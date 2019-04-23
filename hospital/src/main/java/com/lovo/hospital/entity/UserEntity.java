package com.lovo.hospital.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "t_user")
public class UserEntity {
    @Id
    @Column(name="uid",length=32)
    @GenericGenerator(name="powerUUID",strategy="uuid")
    @GeneratedValue(generator="powerUUID")
    private String uId;
    @Column(length=32)
    private String userName;
    @Column(length=32)
    private String password;
    @Column(length=32)
    private String systemName;
    @OneToMany(mappedBy="user")
    private Set<UserRoleEntity> setUserRole;


    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public Set<UserRoleEntity> getSetUserRole() {
        return setUserRole;
    }

    public void setSetUserRole(Set<UserRoleEntity> setUserRole) {
        this.setUserRole = setUserRole;
    }
}
