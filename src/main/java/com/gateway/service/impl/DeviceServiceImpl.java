package com.gateway.service.impl;

import com.gateway.dao.DeviceDao;
import com.gateway.entity.Device;
import com.gateway.service.DeviceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author:
 * @Describe: TODO
 * @Date
 */
@Service
public class DeviceServiceImpl implements DeviceService {
    @Resource
    DeviceDao deviceDao;

    /**
     * @Author: Mr-Candy
     * @description:
     * @creat time: 9:26 2019-10-18
     * @param: [id]
     * @return:com.gateway.entity.Device
     */
    @Override
    public Device getDeviceInfoById (String id) {
        //根据设备号查询设备应用对象
        Device device = deviceDao.selectByPrimaryKey(Long.parseLong(id));
        System.out.println("设备查询！");
        return device;
    }

}
