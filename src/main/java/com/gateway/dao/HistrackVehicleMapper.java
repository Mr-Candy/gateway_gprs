package com.gateway.dao;

import com.gateway.entity.HistrackVehicle;
import org.springframework.stereotype.Repository;

@Repository
public interface HistrackVehicleMapper {
    int deleteByPrimaryKey(String uuid);

    int insert(HistrackVehicle record);

    int insertSelective(HistrackVehicle record);

    HistrackVehicle selectByPrimaryKey(String uuid);

    int updateByPrimaryKeySelective(HistrackVehicle record);

    int updateByPrimaryKey(HistrackVehicle record);
}