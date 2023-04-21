<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%-- <%@ page import="com.myezen.myapp.domain.BoardVo" %>   
 <% BoardVo bv= (BoardVo)request.getAttribute("bv"); %>    --%>


<!DOCTYPE html>
<html>
	<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
	<script src="https://code.jquery.com/jquery-3.6.0.js"></script>
	<script type="text/javascript">
	$(document).ready(function(){
		

		
		var originalFileName =getOriginalFileName("${bv.filename}");
		
		/* alert(originalFileName); */
		
		var str2 = getImageLink("${bv.filename}");
		
		var str ="";
		str = "<div><a href='${pageContext.request.contextPath}/board/displayFile.do?fileName="+str2+"&down=1'>"+originalFileName +"</a></div>";
		

		
		if(("${bv.filename}")!=null){
		$("#download").html(str);
		};


		$.boardCommentList(0);

		$("#save").click(function(){
			var bidx = ("${bv.bidx}");
			var cwriter = $("#cwriter").val();
			var ccontents = $("#ccontents").val();
			var nextBlock = $("#nextBlock").val();
			var midx = ("${sessionScope.midx}");
			$.ajax({
				type: "post",
				url: "${pageContext.request.contextPath}/comment/commentWrite.do",
				dataType : "json",
				data : {
						"bidx" : bidx,
						"cwriter" :  cwriter,
						"ccontents" : ccontents,
						"nextBlock" : nextBlock,
						"midx" : midx
				},
				cache : false,
				success : function(data){
				/* 			alert("등록성공");	 */
							$.boardCommentList(0);
							$("#cwriter").val("");
							$("#ccontents").val("");
				},
				error : function(){
							alert("등록실패");						
				} 	
			});	
		});
		
		
		$("#more").click(function(){
			
			var nextBlock = $("#nextBlock").val();

			
			$.ajax({				
				type : "get",
				url: "${pageContext.request.contextPath}/comment/${bv.bidx}/"+nextBlock+"/more.do",
				dataType : "json",
				cache : false,
				success : function(data){
							// alert("성공"); 
							//	console.log(data);
							//	alert(JSON.stringify(data));
							$("#nextBlock").val(data.nextBlock);	
							$.boardCommentList(nextBlock);
				},
				error : function(){
							alert("실패");
							
				}
			
			});
			
		});
			
	});

	/* $(document).on("click",$("#del"),function(){
		var i = $(this).attr("value");
		alert(i);
	}); */
	
/*   	$(document).on("click",$("#del"),function(){
 		 var cidx = $(this).closest("tr").find("input[name='']").val();
		var cidx = $(this).attr("name")
 		
 		alert(cidx);
	});  */
	
	
//jquery 함수 사용
	$.boardCommentList = function(nb){
		
		var nextBlock;
		
		if(nb ==0){ nextBlock = 1;
		}else{
			nextBlock = nb;
		}
		
		
		$.ajax({
			type: "get",
			url: "${pageContext.request.contextPath}/comment/${bv.bidx}/"+nextBlock+"/commentList.do" ,
			dataType : "json",
			cache : false,
			success : function(data){
			/* 			 alert("성공");
						console.log(data);
						alert(JSON.stringify(data)); */
						commentList(data.alist);
						if(data.moreView =="N"){
							$("#morebtn").css("display","none");
						}else{
							$("#morebtn").css("display","block");
						}
			},
			error : function(){
						alert("등록실패");						
			} 	
		});
	}
	
	//파일 이름 출력
	function getOriginalFileName(fileName){
		
		var idx = fileName.lastIndexOf("_")+1;
		return fileName.substr(idx);
	}
	//파일 이미지일경우
	function getImageLink(fileName){
		
		if(!checkImageType(fileName)){
			return fileName;
		}
		//위치 폴더 뽑기
		var front = fileName.substr(0,12);
		//파일 이름뽑기
		var end = fileName.substr(14);
		return front+end;
	}
	
	function checkImageType(fileName){
		var pattern = /jpg$|gif$|png$|jpge$/i;
		return fileName.match(pattern);	
	}
	
	function commentList(data){
		var str ="";
		str = "<tr><td>이름</td><td>내용</td></td>"
		
		
		$(data).each(function(){
			
			str = str+"<tr><td>"+this.cwriter+"</td><td>"+this.ccontents+"</td>";
			str = str+"<td><button onclick='del("+this.cidx+");'>삭제</button></td></tr>";
		});
		
		$("#tbl").html("<table>"+str+"</table>");
		
		return;
	}
	
	function del(a){
		
		var bidx = ("${bv.bidx}");
		var cidx = a;
		
			$.ajax({
				type: "post",
				url: "${pageContext.request.contextPath}/comment/commentDelete.do",
				dataType : "json",
				data : {
						"bidx" : bidx,
						"cidx" : cidx
				},
				cache : false,
				success : function(data){
							alert("삭제성공");
							$.boardCommentList(0);
				},
				error : function(){
							alert("삭제실패");						
				} 	
			});
		}
		
		

	
	
	</script>

	</head>
	<body>
	내용보기
		<table  border=1 style="width:500px;">
			<tr>
				<td style="width:50px;">제목</td>
				<td>${bv.subject} &nbsp;&nbsp;&nbsp; 조회수(${bv.viewcnt})</td>
			</tr>
			<tr>
				<td>파일다운로드</td>
				<td>
					<div id="download"></div>
				</td>
			</tr>
			<tr>
				<td>이미지</td>
				<td>
				<c:set var = "str" value="${bv.filename}" ></c:set>
				<c:set var = "len" value="${fn:length(str)}"></c:set>
				<c:set var = "exp" value="${fn:substring(str,len-3,len)}"></c:set>
					<c:if test="${exp eq 'jpg' || exp eq 'gif' || exp eq 'png'}" >
						<img src="${pageContext.request.contextPath}/board/displayFile.do?fileName=${bv.filename}" width="300px" height="200px">
					</c:if>
				</td>
	<%-- 			<td>
					<%
					if (bv.getFilename() ==null){
					}else{
					String exp =  bv.getFilename().substring(bv.getFilename().length()-3, bv.getFilename().length());
					
					if (exp.equals("jpg") || exp.equals("gif") || exp.equals("png")   ) { %>
					<img src="<%=request.getContextPath()%>/board/displayFile.do?fileName=<%=bv.getFilename()%>"  width="400px" height="100px"></td>
					<%} 
					}
					%> --%>

			</tr>
			<tr>
				<td style="height:200px;">내용</td>
				<td>${bv.contents}</td>
			</tr>
			<tr>
				<td>작성자</td>
				<td>${bv.writer}</td>
			</tr>
			<tr>
				<td colspan=2 style="text-align:right;">
				<button onclick="location.href=' ${pageContext.request.contextPath}/board/boardModify.do?bidx=${bv.bidx}'">수정</button>
				<button onclick="location.href=' ${pageContext.request.contextPath}/board/boardDelete.do?bidx=${bv.bidx}'">삭제</button>
				<button onclick="location.href=' ${pageContext.request.contextPath}/board/boardReply.do?bidx=${bv.bidx}&originbidx=${bv.originbidx}&depth=${bv.depth}&level_=${bv.level_}'" >답변</button>
				<button onclick = "location.href = ' ${pageContext.request.contextPath}/board/boardList.do?'">목록</button>
			</td>
			</tr>
		</table>
		<table>
			<tr>
				<td>이름</td>
				<td> <input type="text" name="cwriter" id="cwriter" size="10" /> </td>
				<td></td>
			</tr>
			<tr>
				<td>내용</td>
				<td><textarea name="ccontents" id="ccontents" cols=50 rows=3 placeholder="내용을 입력하세요"></textarea></td>
				<td><input type="button" name="save" id="save" value="확인" /></td>
			</tr>
		</table>
			<input id="nextBlock" type="hidden" value="2" />
			<div id="tbl">
			</div>
			<div id="morebtn">
				<button id="more">더보기</button>
			</div>
	</body>
</html>