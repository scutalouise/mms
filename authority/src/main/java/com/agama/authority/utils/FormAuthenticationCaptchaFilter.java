package com.agama.authority.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

/**
 * 扩展认证默认过滤
 * 
 * @author ty
 * @date 2014年12月2日 下午10:47:09
 */
public class FormAuthenticationCaptchaFilter extends FormAuthenticationFilter {

	public static final String DEFAULT_CAPTCHA_PARAM = "captcha";
	private String captchaParam = DEFAULT_CAPTCHA_PARAM;

	public String getCaptchaParam() {
		return captchaParam;
	}

	protected String getCaptcha(ServletRequest request) {
		return WebUtils.getCleanParam(request, getCaptchaParam());
	}

	protected AuthenticationToken createToken(ServletRequest request,
			ServletResponse response) {
		
		try {
			((HttpServletRequest)request).setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String username = getUsername(request);
		
		String password = getPassword(request);
		String captcha = getCaptcha(request);
		boolean rememberMe = isRememberMe(request);
		String host = getHost(request);
		return new UsernamePasswordCaptchaToken(username,
				password.toCharArray(), rememberMe, host, captcha);
	}

	@Override
	protected boolean onLoginSuccess(AuthenticationToken token,
			Subject subject, ServletRequest request, ServletResponse response)
			throws Exception {
		String successUrl = getSuccessUrl();
		WebUtils.redirectToSavedRequest(request, response, successUrl);
		return false;
	}

	/**
	 * 重写登录地址
	 */
	protected void redirectToLogin(ServletRequest request,
			ServletResponse response) throws IOException {
		String currentURL = ((HttpServletRequest)request).getRequestURI();//取得根目录所对应的绝对路径:   
		 String targetURL = currentURL.substring(currentURL.indexOf("/", 1), currentURL.length());  //截取到当前文件名用于比较
	
		 setLoginUrl(targetURL);
		WebUtils.issueRedirect(request, response, getLoginUrl());
	}

}