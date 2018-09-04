package com.dai.util;

import javax.servlet.http.HttpServletRequest;
/**
 * 检验验证码的正确性
 * @author 18491
 *
 */
public class CodeUtil {

	public static boolean checkVerifyCode(HttpServletRequest request) {
		String 	verifyCodeExpected = (String)request.getSession().getAttribute(
				com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
		String verifyCodeActual = HttpServletRequestUtil.getString(request, "verifyCodeActual");
		if(verifyCodeActual == null || !verifyCodeActual.equals(verifyCodeExpected)) {
			return false;
		}
		return true;
	}
}
