package com.gateway.tool;

import java.util.UUID;

public class UuidGenerator {
	public static String getUUID() {
		String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");
		return uuid;
	}
}
