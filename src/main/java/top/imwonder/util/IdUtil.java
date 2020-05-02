/*
 * @Author: Wonder2019 
 * @Date: 2020-05-02 17:59:51 
 * @Last Modified by:   Wonder2019 
 * @Last Modified time: 2020-05-02 17:59:51 
 */
package top.imwonder.util;

import java.util.UUID;

public class IdUtil {
	public static String uuid() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
}
