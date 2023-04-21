<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Insert title here</title>
	</head>
	<body>
		<a href="${pageContext.request.contextPath}/member/memberJoin.do"> 회원가입하기</a>
		<a href="${pageContext.request.contextPath}/member/memberList.do"> 회원목록</a>
		<a href="${pageContext.request.contextPath}/board/boardList.do"> 게시판목록</a>
		
		
		
		<%if (session.getAttribute("midx")!=null){
				/* int midx = (int)session.getAttribute("midx"); */
				int midx = Integer.parseInt(session.getAttribute("midx").toString());
				
				out.println("회원번호 :"+midx);
				String memberName = (String)session.getAttribute("memberName");
				out.println("회원이름 :"+memberName);
		%>
		<a href="${pageContext.request.contextPath}/member/memberLogOut.do">로그아웃</a>
		<%
		} else{
		%>
<a href="${pageContext.request.contextPath}/member/memberLogin.do">회원로그인</a>
<%} %>
		
	</body>
</html>