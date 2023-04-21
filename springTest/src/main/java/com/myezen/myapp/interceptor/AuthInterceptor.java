package com.myezen.myapp.interceptor;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
//interceptor를 다루는 클레스 상속받음
public class AuthInterceptor extends HandlerInterceptorAdapter {

	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		//다른 핸들러가 작동하기 전에 동작하는 것
		
		HttpSession session = request.getSession();
		//세션에 로그인정보가 있는지 확인
		
		if(session.getAttribute("midx")==null){
			//로그인후 이동할 주소를 담는다.
			saveDest(request);
			response.sendRedirect(request.getContextPath()+"/member/memberLogin.do");
		}
		
		return true;
	}
	//주소를 뽑아내는 메소드
	private void saveDest(HttpServletRequest request) {
		
		String root = request.getContextPath();
		
		String uri = request.getRequestURI().substring(root.length());
		String query = request.getQueryString();
		
		if(query==null || query.equals("null")) {
			query = "";
		}else {
			query = "?"+query;			
		}
		
		if(request.getMethod().equals("GET")) {
			request.getSession().setAttribute("dest", uri+query);
		}
		
	}
}
