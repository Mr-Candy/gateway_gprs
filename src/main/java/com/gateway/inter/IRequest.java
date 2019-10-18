package com.gateway.inter;

import java.util.Map;

/**
 * 消息体组帧
 */

public interface IRequest<T, V> {
	
	public byte[] message(int commandType, Map<T, V> info) throws NumberFormatException, RuntimeException;
	
}
