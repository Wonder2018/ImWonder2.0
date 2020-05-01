/*
 * @Author: Wonder2019 
 * @Date: 2020-04-28 23:04:44 
 * @Last Modified by: Wonder2019
 * @Last Modified time: 2020-04-28 23:11:09
 */
package top.imwonder.myblog.login;

import org.apache.shiro.authc.UsernamePasswordToken;

public class WonderUPT extends UsernamePasswordToken {

	private static final long serialVersionUID = 1L;

	private boolean correctCaptcha;

	private String roleBase;

	public WonderUPT(String username, String password, boolean rememberMe, String host, boolean correctCaptcha, String roleBase) {
		super(username, password, rememberMe, host);
		this.correctCaptcha = correctCaptcha;
		this.roleBase = roleBase;
	}

	public boolean isCorrectCaptcha() {
		return correctCaptcha;
	}

	public String getRoleBase() {
		return roleBase;
	}
}
