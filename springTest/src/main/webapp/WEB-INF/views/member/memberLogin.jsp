<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<% String msg = "";
if (request.getAttribute("msg") != null){
	msg = (String)request.getAttribute("msg");} %>
 
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>회원로그인</title>
		<script src="https://code.jquery.com/jquery-3.6.0.js"></script>
		<script type="text/javascript">
			$(document).ready(function(){
				var msg = "<%=msg%>";
				if(msg !=""){
					alert(msg);
				}
				});
			function check(){

				if ($("#memberId").val() == ""){
					alert("아이디를 입력하세요");
					$("#memberId").focus();
					return;
				}else if ($("#memberPwd").val() == ""){
					alert("비밀번호를 입력하세요");
					$("#memberPwd").focus();
					return;
				}
				var fm = document.frm;

				fm.action ="${pageContext.request.contextPath}/member/memberLoginAction.do";    
				fm.method = "post";
				fm.submit();   
				
				return;
			}	
		</script>
	</head>
	<body>
	회원로그인 페이지
		<form name="frm" id="frm">
			<table style="border:1px solid #CCC;width:500px">
			<tr>
				<td>아이디</td>
				<td>
					<input type="text" name="memberId" id="memberId">
				</td>
			</tr>
			<tr>
				<td>비밀번호</td>
				<td>
					<input type="password" name="memberPwd" id="memberPwd">
				</td>
			</tr>
			<tr>
				<td>
					<input type="button" name="btn"  id="btn" value="확인" onclick="check();">
				</td>
			</tr>
			</table>
		</form>
	</body>
</html>