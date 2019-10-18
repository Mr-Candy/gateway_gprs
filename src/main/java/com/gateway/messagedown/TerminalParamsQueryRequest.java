package com.gateway.messagedown;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;

/**
 * 查询指定终端参数
 */

public class TerminalParamsQueryRequest extends AbstractRequest {

	@Override
	public byte[] message(int commandType, Map<String, String> info) throws NumberFormatException, RuntimeException {

		if (info == null) {
			throw new NullPointerException();
		}

		try {
			ByteArrayOutputStream boutStreamBody = new ByteArrayOutputStream();
			DataOutputStream doutBody = new DataOutputStream(boutStreamBody);

			String count = info.get("count");
			Integer paramCount = Integer.valueOf(count == null ? "0" : count);

			if (paramCount == 0) {
				doutBody.writeByte(0);
			}else {
				String[] params = info.get("params").split(",");
				for(int i = 0;i < params.length; i++) {
					String param = params[i];
					doutBody.writeByte(Integer.valueOf(param));
				}
			}
			return super.sendMessage(info, commandType, boutStreamBody);
		} catch (IOException e) {
			throw new RuntimeException("Unkown error, please try again!");
		}
	}
}
