package com.gateway.entity;

import com.gateway.tool.UuidGenerator;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author ：Mr-Candy
 * @date ：
 * @description：历史轨迹实体类
 * @modified By：
 * @version: 1.0
 */
public class HistrackVehicle {
    private String uuid;

    private Long deviceId;

    private BigDecimal lng;

    private BigDecimal lat;

    private BigDecimal speed;

    private BigDecimal direction;

    private BigDecimal temperature;

    private BigDecimal pressure;

    private BigDecimal level;

    private Integer electric;

    private Date uploadTime;

    public HistrackVehicle(String uuid, Long deviceId, BigDecimal lng, BigDecimal lat, BigDecimal speed, BigDecimal direction, BigDecimal temperature, BigDecimal pressure, BigDecimal level, Integer electric, Date uploadTime) {
        this.uuid = uuid;
        this.deviceId = deviceId;
        this.lng = lng;
        this.lat = lat;
        this.speed = speed;
        this.direction = direction;
        this.temperature = temperature;
        this.pressure = pressure;
        this.level = level;
        this.electric = electric;
        this.uploadTime = uploadTime;
    }

    public HistrackVehicle(){
        this.uuid = UuidGenerator.getUUID();
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid == null ? null : uuid.trim();
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public BigDecimal getLng() {
        return lng;
    }

    public void setLng(BigDecimal lng) {
        this.lng = lng;
    }

    public BigDecimal getLat() {
        return lat;
    }

    public void setLat(BigDecimal lat) {
        this.lat = lat;
    }

    public BigDecimal getSpeed() {
        return speed;
    }

    public void setSpeed(BigDecimal speed) {
        this.speed = speed;
    }

    public BigDecimal getDirection() {
        return direction;
    }

    public void setDirection(BigDecimal direction) {
        this.direction = direction;
    }

    public BigDecimal getTemperature() {
        return temperature;
    }

    public void setTemperature(BigDecimal temperature) {
        this.temperature = temperature;
    }

    public BigDecimal getPressure() {
        return pressure;
    }

    public void setPressure(BigDecimal pressure) {
        this.pressure = pressure;
    }

    public BigDecimal getLevel() {
        return level;
    }

    public void setLevel(BigDecimal level) {
        this.level = level;
    }

    public Integer getElectric() {
        return electric;
    }

    public void setElectric(Integer electric) {
        this.electric = electric;
    }

    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }
}