<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>  
<%@ page import="com.myezen.myapp.domain.BoardVo" %>   
<% BoardVo bv= (BoardVo)request.getAttribute("bv");%>   

    
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>글수정화면</title>
		<script src="https://code.jquery.com/jquery-3.6.0.js"></script>
		<script type="text/javascript">
		function check(){	
		
			var fm = document.frm;	
			if (fm.subject.value == "" ){
				alert("제목을 입력하세요");
				fm.subject.focus();
				return;
			}else if (fm.contents.value == ""){
				alert("내용을 입력하세요");
				fm.contents.focus();
				return;
			}else if (fm.writer.value == ""){
				alert("작성자를 입력하세요");
				fm.writer.focus();
				return;
			}else if (fm.pwd.value == ""){
				alert("비밀번호를 입력하세요");
				fm.pwd.focus();
				return;
			} 
			fm.action = "${pageContext.request.contextPath}/board/boardModifyAction.do";
			fm.enctype ="multipart/form-data";
			fm.method="post";
			fm.submit();
			return;
		}

		function getOriginalFileName(fileName){
			
			var idx = fileName.lastIndexOf("_")+1;
			
			return fileName.substr(idx);
		}
		

		</script>
	</head>
	<body>
	게시판 글수정
		<form name="frm">
			<input type="hidden" name="bidx" value="${bv.bidx}">
			<table  border=1 style="width:500px;">
				<tr>
					<td>제목</td>
					<td><input type="text" name="subject"  value="${bv.subject}"></td>
				</tr>
				<tr>
					<td>내용</td>
					<td><textarea name="contents" cols="50"  rows="5">${bv.contents}</textarea></td>
				</tr>
				<tr>
					<td>작성자</td>
					<td><input type="text" name="writer" maxlength=5  value="${bv.writer}"></td>
				</tr>
				<tr>
					<td>파일첨부</td>
					<td><input type="file" name="filename" value="">
					<span id="filenm"></span>
					</td>
				</tr>
				<tr>
					<td>비밀번호</td>
					<td><input type="password" name="pwd"></td>
				</tr>
				
				<tr>
					<td colspan=2>
						<input type="button" name="btn"  value="확인" onclick="check();">
						<input type="reset" name="rst" value="리셋">
					</td>
				</tr>
			
			</table>
		</form>
		<script type="text/javascript">
		var originalFileName =getOriginalFileName("<%=bv.getFilename()%>");
		document.getElementById("filenm").innerHTML = originalFileName;
		</script>
	</body>
</html>