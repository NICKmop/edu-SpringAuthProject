package com.cspro.edu_SpringAuthProject.util;

import jakarta.servlet.http.Cookie;

public class CookieUtil {
	public static Cookie createCookie(String key, String value, Integer expireds) {
		Cookie cookie = new Cookie(key, value);
		
		cookie.setHttpOnly(true);
		cookie.setPath("/"); // 최상위에 저장
		cookie.setMaxAge(expireds);
		
		return cookie;
	}
}
