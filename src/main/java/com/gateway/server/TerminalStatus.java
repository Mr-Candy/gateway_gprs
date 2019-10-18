package com.gateway.server;

import com.alibaba.fastjson.JSONObject;

import java.util.Date;

public class TerminalStatus {
	// 告警信息
	public int warningFlagField;
	
	// 状态
	public int statusField;
	
	// 纬度
	public float latitude;
	
	// 经度
	public float longitude;
	

	// 海拔
	public int elevation;

	// 速度
	public float speed;

	// 方向
	public int direction;

	// 时间
	public Date time;
	
	public String toString(){
		JSONObject object=new JSONObject();
		object.put("warning", warningFlagField);
		object.put("status", statusField);
		object.put("latitude", latitude);
		object.put("longitude", longitude);
		object.put("elevation", elevation);
		object.put("speed", speed);
		object.put("direction", direction);
		object.put("time", time);
		return object.toString();
	}
}
