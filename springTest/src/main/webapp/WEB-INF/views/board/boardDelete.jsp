<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>  
 <!DOCTYPE html>
<html>
	<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
	<script type="text/javascript">
	function check(){	
	
		var fm = document.frm;	
		if (fm.pwd.value == "" ){
			alert("비밀번호를 입력하세요");
			fm.pwd.focus();
			return;
		}
		
		var flag = confirm("삭제하시겠습니까?");
		if (flag ==false){
			return;
		}
		
		fm.action = "${pageContext.request.contextPath}/board/boardDeleteAction.do";
		fm.method="post";
		fm.submit();
		return;
	}
	
	
	</script>


</head>
	<body>
	삭제페이지입니다
		<form name="frm">
		<input type="hidden" name="bidx" value="${bv.bidx}">
			<table border=1 style="width:500px;">
				<tr>
					<td style="width:50px;">제목</td>
					<td>${bv.subject}</td>
				</tr>
				<tr>
					<td>내용</td>
					<td>${bv.contents}</td>
				</tr>
				<tr>
					<td>작성자</td>
					<td>${bv.writer}</td>
				</tr>
				<tr>
					<td>비밀번호</td>
					<td><input type="password" name="pwd"></td>
				</tr>
				<tr>
					<td colspan=2>
					<input type="button" name="btn"  value="확인" onclick="check();">
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>