package com.gateway.messagedown;

import org.apache.commons.lang3.StringUtils;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;

/**
 * 设置终端参数
 */

public class TerminalParamsSetRequest extends AbstractRequest {

	@Override
	public byte[] message(int commandType, Map<String, String> info)throws NumberFormatException, RuntimeException {

		if (info == null) {
			throw new NullPointerException();
		}

		try {
			ByteArrayOutputStream boutStreamBody = new ByteArrayOutputStream();
			DataOutputStream doutBody = new DataOutputStream(boutStreamBody);

			String count = info.get("count");
			Integer paramCount = Integer.valueOf(count == null ? "0" : count);

			if (paramCount <= 0) {
				throw new NumberFormatException("Illegal value: " + count + " for the key: count");
			}
			doutBody.writeByte(paramCount);

			String value = info.get("version");	 //固件版本号
			if (value != null && value.matches("\\d+")) {
				try {
					int version = Integer.valueOf(value);
					doutBody.writeByte(0x01);
					doutBody.writeShort(version);
				} catch (NumberFormatException e) {
					throw new NumberFormatException("Illegal value: " + value + " for the key: version");
				}
			} else if(value != null){
				throw new NumberFormatException("Illegal value: " + value + " for the key: version");
			}

			value = info.get("bd_card");  //北斗卡号
			if (value != null && value.matches("\\d+")) {
				try {
					Long tcp_ack_timeout = Long.parseLong(value);
					doutBody.writeByte(0x02);
					doutBody.writeInt(tcp_ack_timeout.intValue());
				} catch (NumberFormatException e) {
					throw new NumberFormatException("Illegal value: " + value + " for the key: bd_card" );
				}
			} else if(value != null) {
				throw new NumberFormatException("Illegal value: " + value + " for the key: bd_card" );
			}

			value = info.get("sim_card");	//sim卡号
			if (value != null && value.matches("\\d+")) {
				try {
					Long tcp_retry_times = Long.parseLong(value);
					doutBody.writeInt(0x03);
					doutBody.writeInt(tcp_retry_times.intValue());
				} catch (NumberFormatException e) {
					throw new NumberFormatException("Illegal value: " + value + " for the key: sim_card");
				}
			} else if(value != null) {
				throw new NumberFormatException("Illegal value: " + value + " for the key: sim_card");
			}

			value = info.get("RDSS_address");	//北斗上报地址
			if (value != null && value.matches("\\d+")) {
				try {
					Long udp_ack_timeout = Long.parseLong(value);
					doutBody.writeInt(0x04);
					doutBody.writeInt(udp_ack_timeout.intValue());
				} catch (NumberFormatException e) {
					throw new NumberFormatException("Illegal value: " + value + " for the key: RDSS_address");
				}
			} else if(value != null) {
				throw new NumberFormatException("Illegal value: " + value + " for the key: RDSS_address");
			}

			value = info.get("ip");		//gsm上报ip
			if (value != null && value.matches("\\d+")) {
				try {
					Long udp_retry_times = Long.parseLong(value);
					doutBody.writeInt(0x05);
					doutBody.writeInt(udp_retry_times.intValue());
				} catch (NumberFormatException e) {
					throw new NumberFormatException("Illegal value: " + value + " for the key: ip");
				}
			} else if(value != null) {
				throw new NumberFormatException("Illegal value: " + value + " for the key: ip");
			}

			value = info.get("port");		//gsm上报端口
			if (value != null && value.matches("\\d+")) {
				try {
					Long sms_ack_timeout = Long.parseLong(value);
					doutBody.writeInt(0x06);
					doutBody.writeInt(sms_ack_timeout.intValue());
				} catch (NumberFormatException e) {
					throw new NumberFormatException("Illegal value: " + value + " for the key: port");
				}
			} else if(value != null) {
				throw new NumberFormatException("Illegal value: " + value + " for the key: port");
			}

			value = info.get("intertime");	//终端定时采集时间
			if (value != null && value.matches("\\d+")) {
				try {
					Long sms_retry_times = Long.parseLong(value);
					doutBody.writeInt(0x07);
					doutBody.writeInt(sms_retry_times.intValue());
				} catch (NumberFormatException e) {
					throw new NumberFormatException("Illegal value: " + value + " for the key: intertime");
				}
			} else if(value != null) {
				throw new NumberFormatException("Illegal value: " + value + " for the key: intertime");
			}
			
			value = info.get("voltage_low");	//电池低压报警阈值
			if (value != null && value.matches("\\d+")) {
				try {
					Integer distance = Integer.parseInt(value);
					doutBody.writeInt(0x08);
					doutBody.writeShort(distance.shortValue());
				} catch (NumberFormatException e) {
					throw new NumberFormatException("Illegal value: " + value + " for the key: distance");
				}
			} else if(value != null) {
				throw new NumberFormatException("Illegal value: " + value + " for the key: distance" );
			}
			
			value = info.get("pressure_upper");		//罐箱压力报警阈值（上限）
			if (value != null && value.matches("\\d+")) {
				try {
					Integer tanwu = Integer.parseInt(value);
					doutBody.writeInt(0x09);
					doutBody.writeShort(tanwu.shortValue());
				} catch (NumberFormatException e) {
					throw new NumberFormatException("Illegal value: " + value + " for the key: pressure_upper");
				}
			} else if(value != null) {
				throw new NumberFormatException("Illegal value: " + value + " for the key: pressure_upper" );
			}

			value = info.get("pressure_lower");	//罐箱压力报警阈值（下限）
			if (value != null && StringUtils.isNotBlank(value)) {
				doutBody.writeInt(0x0A);

			}else if(value != null){
				throw new NumberFormatException("Illegal value: " + value + " for the key: pressure_lower");
			}

			value = info.get("liquid_level_upper");		//罐箱液位报警阈值（上限）
			if (value != null && value.matches("\\d+")) {
				try {
					Integer tanwu = Integer.parseInt(value);
					doutBody.writeInt(0x0B);
					doutBody.writeShort(tanwu.shortValue());
				} catch (NumberFormatException e) {
					throw new NumberFormatException("Illegal value: " + value + " for the key: liquid_level_upper");
				}
			} else if(value != null) {
				throw new NumberFormatException("Illegal value: " + value + " for the key: liquid_level_upper" );
			}

			value = info.get("liquid_level_lower");		//罐箱液位报警阈值（下限）
			if (value != null && value.matches("\\d+")) {
				try {
					Integer tanwu = Integer.parseInt(value);
					doutBody.writeInt(0x0C);
					doutBody.writeShort(tanwu.shortValue());
				} catch (NumberFormatException e) {
					throw new NumberFormatException("Illegal value: " + value + " for the key: liquid_level_lower");
				}
			} else if(value != null) {
				throw new NumberFormatException("Illegal value: " + value + " for the key: liquid_level_lower" );
			}

			value = info.get("temp_upper");		//罐箱温度报警阈值（上限）
			if (value != null && value.matches("\\d+")) {
				try {
					Integer tanwu = Integer.parseInt(value);
					doutBody.writeInt(0x0D);
					doutBody.writeShort(tanwu.shortValue());
				} catch (NumberFormatException e) {
					throw new NumberFormatException("Illegal value: " + value + " for the key: temp_upper");
				}
			} else if(value != null) {
				throw new NumberFormatException("Illegal value: " + value + " for the key: temp_upper" );
			}

			value = info.get("temp_lower");		//罐箱温度报警阈值（下限）
			if (value != null && value.matches("\\d+")) {
				try {
					Integer tanwu = Integer.parseInt(value);
					doutBody.writeInt(0x0E);
					doutBody.writeShort(tanwu.shortValue());
				} catch (NumberFormatException e) {
					throw new NumberFormatException("Illegal value: " + value + " for the key: temp_lower");
				}
			} else if(value != null) {
				throw new NumberFormatException("Illegal value: " + value + " for the key: temp_lower" );
			}

			value = info.get("onoroff_state");		//功能开关状态
			if (value != null && value.matches("\\d+")) {
				try {
					Integer onoroff_state = Integer.parseInt(value);
					doutBody.writeInt(0x0F);
					doutBody.writeByte(onoroff_state.shortValue());
				} catch (NumberFormatException e) {
					throw new NumberFormatException("Illegal value: " + value + " for the key: onoroff_state");
				}
			} else if(value != null) {
				throw new NumberFormatException("Illegal value: " + value + " for the key: onoroff_state" );
			}

			value = info.get("BD_white_list1");
			if (value != null  && StringUtils.isNotBlank(value)) {
				doutBody.writeInt(0x10);

			}else if(value != null){
				throw new NumberFormatException("Illegal value: " + value + " for the key: BD_white_list1");
			}

			value = info.get("BD_white_list2");
			if (value != null  && StringUtils.isNotBlank(value)) {
				doutBody.writeInt(0x11);

			}else if(value != null){
				throw new NumberFormatException("Illegal value: " + value + " for the key: BD_white_list2");
			}

			value = info.get("BD_white_list3");
			if (value != null  && StringUtils.isNotBlank(value)) {
				doutBody.writeInt(0x12);

			}else if(value != null){
				throw new NumberFormatException("Illegal value: " + value + " for the key: BD_white_list3");
			}

			value = info.get("GSM_white_list1");
			if (value != null  && StringUtils.isNotBlank(value)) {
				doutBody.writeInt(0x13);

			}else if(value != null){
				throw new NumberFormatException("Illegal value: " + value + " for the key: GSM_white_list1");
			}

			value = info.get("GSM_white_list2");
			if (value != null  && StringUtils.isNotBlank(value)) {
				doutBody.writeInt(0x14);

			}else if(value != null){
				throw new NumberFormatException("Illegal value: " + value + " for the key: GSM_white_list2");
			}

			value = info.get("GSM_white_list3");
			if (value != null  && StringUtils.isNotBlank(value)) {
				doutBody.writeInt(0x15);

			}else if(value != null){
				throw new NumberFormatException("Illegal value: " + value + " for the key: GSM_white_list3");
			}

			
			return super.sendMessage(info, commandType, boutStreamBody);
		} catch (IOException e) {
			throw new RuntimeException("Unkown error, please try again!");
		}
	}

}
