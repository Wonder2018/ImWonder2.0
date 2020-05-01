package top.imwonder.util;

import java.util.UUID;

public class IdUtil {
	public static String uuid() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
}
