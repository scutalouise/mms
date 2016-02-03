package com.agama.pemm.service;

import java.util.Hashtable;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;

import com.agama.common.web.BaseController;

public abstract class SpringMockTest {
	protected MockHttpServletRequest request;

	protected MockHttpServletResponse response;

	protected MockHttpSession httpSession;

	protected Map<Object, Object> session;

	@Before
	public void init() throws Throwable {
		session = new Hashtable<Object, Object>();

		request = new MockHttpServletRequest();
		response = new MockHttpServletResponse();
		httpSession = new MockHttpSession();
		request.setSession(httpSession);

		initServletConfig();
	}

	protected abstract void initServletConfig();

	@After
	public void destroy() {
		session.clear();
	}

	public void mockLoginUser() {

	}

	/**
	 * 公用方法，方便初始化 action
	 * 
	 * @param action
	 */
	public void initAction(BaseController action) {
		
	}

}
