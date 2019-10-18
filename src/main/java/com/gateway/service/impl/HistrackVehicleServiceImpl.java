package com.gateway.service.impl;

import com.gateway.dao.HistrackVehicleMapper;
import com.gateway.entity.HistrackVehicle;
import com.gateway.service.HistrackVehicleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author ：Mr-Candy
 * @date ：Created in 2019-10-18 11:04
 * @description：历史轨迹
 * @modified By：
 * @version: 1.0
 */
@Service
public class HistrackVehicleServiceImpl implements HistrackVehicleService {
    @Resource
    HistrackVehicleMapper histrackVehicleMapper;

    @Override
    public void save(HistrackVehicle his) {
        histrackVehicleMapper.insertSelective(his);
    }
}
