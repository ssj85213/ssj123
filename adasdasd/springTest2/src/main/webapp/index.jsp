<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<a href="<%=request.getContextPath()%>/member/memberJoin.do"> 회원가입하기</a>
<a href="<%=request.getContextPath()%>/member/memberList.do"> 회원목록</a>
<a href="<%=request.getContextPath()%>/board/boardList.do">게시판목록</a>

<% if (session.getAttribute("midx") != null){
	int midx = Integer.parseInt(session.getAttribute("midx").toString());
	out.println("회원번호:"+midx);
	String memberName = (String)session.getAttribute("memberName");
	out.println("회원이름:"+memberName);
%>
<a href="<%=request.getContextPath()%>/member/memberLogOut.do">회원로그아웃</a>	
<%	
} else{
%>
<a href="<%=request.getContextPath()%>/member/memberLogin.do">회원로그인</a>
<%}%>




</body>
</html>