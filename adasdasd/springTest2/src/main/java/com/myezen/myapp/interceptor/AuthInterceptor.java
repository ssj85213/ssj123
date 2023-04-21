package com.myezen.myapp.interceptor;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class AuthInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		HttpSession session = request.getSession();
		
		if (session.getAttribute("midx")== null) {
			//로그인후 이동할 주소를 담는다
			saveDest(request);
			
			response.sendRedirect(request.getContextPath()+"/member/memberLogin.do");
		}
		
		return true;
	}
	
	private void saveDest(HttpServletRequest request) {
		
		String root  = request.getContextPath();		
		String uri = request.getRequestURI().substring(root.length());
		String query = request.getQueryString();
		
		if (query == null || query.equals("null")) {
			query = "";
		}else {
			query = "?"+query;
		}
		
		
		
		if (request.getMethod().equals("GET")) {
			request.getSession().setAttribute("dest", uri+query);
		}
		
	}
	
	
}
