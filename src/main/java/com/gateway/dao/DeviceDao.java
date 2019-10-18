package com.gateway.dao;

import com.gateway.entity.Device;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
//@Mapper
public interface DeviceDao {

	Device selectByPrimaryKey(Long deviceId);

	int updateByPrimaryKeySelective(Device record);

	int updateByPrimaryKey(Device record);

}