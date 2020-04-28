package top.imwonder.util;

import java.util.UUID;

public class IdGen {
	public static String uuid() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
}
