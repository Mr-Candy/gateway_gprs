package com.gateway.entity;
import java.io.Serializable;
import java.util.Date;

public class Device implements Serializable {

    private Long deviceID;

    private String deviceName;

    private String deviceType;

    private Long mfrID;

    private String protName;

    private Date registerDate;

    private Integer saleState;

    private Integer servState;

    private Integer workState;

    private Long userID;

    private Long deptID;

    private Integer platServID;

    private Integer applyTo;

    private Long objectID;

    private Date activateDate;

    private Date servExpireDate;

    private String staffName;

    private Integer onlineState;

    public Long getDeviceID () {
        return deviceID;
    }

    public void setDeviceID (Long deviceID) {
        this.deviceID = deviceID;
    }

    public String getDeviceName () {
        return deviceName;
    }

    public void setDeviceName (String deviceName) {
        this.deviceName = deviceName == null ? null : deviceName.trim();
    }

    public String getDeviceType () {
        return deviceType;
    }

    public void setDeviceType (String deviceType) {
        this.deviceType = deviceType == null ? null : deviceType.trim();
    }

    public Long getMfrID () {
        return mfrID;
    }

    public void setMfrID (Long mfrID) {
        this.mfrID = mfrID;
    }

    public String getProtName () {
        return protName;
    }

    public void setProtName (String protName) {
        this.protName = protName == null ? null : protName.trim();
    }

    public Date getRegisterDate () {
        return registerDate;
    }

    public void setRegisterDate (Date registerDate) {
        this.registerDate = registerDate;
    }

    public Integer getSaleState () {
        return saleState;
    }

    public void setSaleState (Integer saleState) {
        this.saleState = saleState;
    }

    public Integer getServState () {
        return servState;
    }

    public void setServState (Integer servState) {
        this.servState = servState;
    }

    public Integer getWorkState () {
        return workState;
    }

    public void setWorkState (Integer workState) {
        this.workState = workState;
    }

    public Long getUserID () {
        return userID;
    }

    public void setUserID (Long userID) {
        this.userID = userID;
    }

    public Long getDeptID () {
        return deptID;
    }

    public void setDeptID (Long deptID) {
        this.deptID = deptID;
    }

    public Integer getPlatServID () {
        return platServID;
    }

    public void setPlatServID (Integer platServID) {
        this.platServID = platServID;
    }

    public Integer getApplyTo () {
        return applyTo;
    }

    public void setApplyTo (Integer applyTo) {
        this.applyTo = applyTo;
    }

    public Long getObjectID () {
        return objectID;
    }

    public void setObjectID (Long objectID) {
        this.objectID = objectID;
    }

    public Date getActivateDate () {
        return activateDate;
    }

    public void setActivateDate (Date activateDate) {
        this.activateDate = activateDate;
    }

    public Date getServExpireDate () {
        return servExpireDate;
    }

    public void setServExpireDate (Date servExpireDate) {
        this.servExpireDate = servExpireDate;
    }

    public String getStaffName () {
        return staffName;
    }

    public void setStaffName (String staffName) {
        this.staffName = staffName == null ? null : staffName.trim();
    }

    public Integer getOnlineState () {
        return onlineState;
    }

    public void setOnlineState (Integer onlineState) {
        this.onlineState = onlineState;
    }

}