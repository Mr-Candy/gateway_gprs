package com.gateway.messagedown;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;

/**
 * 终端点名
 */
public class VehiclePosInfoQueryRequest extends AbstractRequest {

	@Override
	public byte[] message(int commandType, Map<String, String> info)  throws NumberFormatException, RuntimeException  {

		if (info == null) {
			throw new NullPointerException();
		}

		try {
			ByteArrayOutputStream boutStreamBody = new ByteArrayOutputStream();
			DataOutputStream doutBody = new DataOutputStream(boutStreamBody);

			String value = info.get("intertime");
			if (value != null && value.matches("\\d+")) {
				try {
					int intertime = Integer.valueOf(value);
					doutBody.writeByte(intertime);
				} catch (NumberFormatException e) {
					throw new NumberFormatException("Illegal value: " + value + " for the key: intertime");
				}
			} else if(value != null){
				throw new NumberFormatException("Illegal value: " + value + " for the key: intertime");
			}

			value = info.get("num");
			if (value != null && value.matches("\\d+")) {
				try {
					int num = Integer.valueOf(value);
					doutBody.writeByte(num);
				} catch (NumberFormatException e) {
					throw new NumberFormatException("Illegal value: " + value + " for the key: num");
				}
			} else if(value != null){
				throw new NumberFormatException("Illegal value: " + value + " for the key: num");
			}
			doutBody.writeByte(0);
			return super.sendMessage(info, commandType, boutStreamBody);
		} catch (IOException e) {
			throw new RuntimeException("Unkown error, please try again!");
		}
	}

}
