package com.gateway.messagedown;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;

/**
 * 平台通用应答
 */

public class PlatformUniversalAnswerRequest extends AbstractRequest {

	@Override
	public byte[] message(int commandType, Map<String, String> info)
			throws NumberFormatException, RuntimeException {

		if (info == null) {
			throw new NullPointerException();
		}

		try {
			ByteArrayOutputStream boutStreamBody = new ByteArrayOutputStream();
			DataOutputStream doutBody = new DataOutputStream(boutStreamBody);
			// 应答流水号, 对应的终端消息的流水号
			String flowId = info.get("answer_flow_num");
			if (flowId != null & flowId.matches("-?\\d+")) {
				try {
					Integer flowNum = Integer.parseInt(flowId);
					doutBody.writeShort(flowNum.shortValue());
				} catch (NumberFormatException e) {
					throw new NumberFormatException("Illegal value: " + flowId + " for the key: answer_flow_num");
				}
			} else {
				throw new NumberFormatException("Illegal value: " + flowId + " for the key: answer_flow_num");
			}
			
			// 应答ID，对应终端消息的ID
			String answer = info.get("answer_id");
			if (answer != null && answer.matches("\\d+")) {
				try {
					Integer answerID = Integer.parseInt(answer);
					doutBody.writeShort(answerID.shortValue());
				} catch (NumberFormatException e) {
					throw new NumberFormatException("Illegal value: " + answer + " for the key: answer_id" );
				}
			} else {
				throw new NumberFormatException("Illegal value: " + answer + " for the key: answer_id");
			}
			// 结果，0：成功/确认；1：失败；2：消息有误；3：不支持;4:报警处理确认
			String res = info.get("result");
			if (res != null && res.matches("[0-4]")) {
				Byte result = Byte.parseByte(res);
				doutBody.writeByte(result);
			} else {
				throw new NumberFormatException("Illegal value: " + res + " for the key: result");
			}
			return super.sendMessage(info, commandType, boutStreamBody);
		} catch (IOException e) {
			throw new RuntimeException("Unkown error, please try again!");
		}
	}
}
