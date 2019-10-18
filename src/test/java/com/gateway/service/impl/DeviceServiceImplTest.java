package com.gateway.service.impl;

import com.gateway.entity.Device;
import com.gateway.service.DeviceService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class DeviceServiceImplTest {

    @Resource
    private DeviceService deviceService;

    @Test
    public void getDeviceInfoById() {
        Device de = deviceService.getDeviceInfoById("13600000010");
        System.out.println(de.getDeviceID());
    }
}