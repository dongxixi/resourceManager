package com.lovo.fire_company.dto;

public class UserAllDto {
    private String userId;
    private String userName;
    private String roleName;
    private String powerName;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getPowerName() {
        return powerName;
    }

    public void setPowerName(String powerName) {
        this.powerName = powerName;
    }

    public UserAllDto(String userName, String roleName, String powerName) {
        this.userName = userName;
        this.roleName = roleName;
        this.powerName = powerName;
    }
    public UserAllDto() {
    }
}
