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
		회원목록
		<table border=1>
			<tr>
				<td>회원번호</td>
				<td>회원아이디</td>
				<td>회원이름</td>
				<td>탈퇴여부</td>
				<td>가입일</td>
			</tr>
			<c:forEach var="mv" items="${alist}">
			<tr>
				<td>${mv.midx}</td>
				<td>${mv.memberid}</td>
				<td>${mv.membername}</td>
				<td>${mv.delyn}</td>
				<td>${mv.writeday}</td>
			</tr>
			</c:forEach>
		</table>
	
	</body>
</html>