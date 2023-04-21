<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ page import="com.myezen.myapp.domain.BoardVo" %>   
 
 <% BoardVo bv   = (BoardVo)request.getAttribute("bv"); %>   
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>내용보기</title>
<script src="https://code.jquery.com/jquery-3.6.0.js"></script>
<script type="text/javascript">
$(document).ready(function(){	
	var originalFileName = getOriginalFileName("<%=bv.getFilename()%>");
	var str2 = getImageLink("<%=bv.getFilename()%>");
	var str="";
	str = "<div><a href=<%=request.getContextPath()%>/board/displayFile.do?fileName="+str2+"&down=1>"+originalFileName+"</a></div>";
	
	$("#download").html(str);
	
	
	$.boardCommentList(0);
	
	$("#save").click(function(){		
		
		var bidx = <%=bv.getBidx()%>;	
		var cwriter = $("#cwriter").val();
		var ccontents = $("#ccontents").val();
		var nextBlock = $("#nextBlock").val();
		var midx = <%=session.getAttribute("midx")%>;
		
		$.ajax({			
			type: "post",
			url: "<%=request.getContextPath()%>/comment/commentWrite.do",
			dataType: "json",
			data : {
				"bidx": bidx,
				"cwriter": cwriter,
				"ccontents": ccontents,
				"nextBlock":nextBlock,
				"midx":midx
			},
			cache: false,
			success: function(data){
				//alert("등록성공");	
				$.boardCommentList();
				$("#cwriter").val("");
				$("#ccontents").val("");
				
			} ,
			error:function(){
				alert("등록실패");				
			}			
		});		
		
	});
	
	$("#more").click(function(){
		
		var nextBlock = $("#nextBlock").val();
		
		$.ajax({
			
			type: "get",
			url : "<%=request.getContextPath()%>/comment/<%=bv.getBidx()%>/"+nextBlock+"/more.do",
			dataType : "json",
			cache : false,
			success : function(data){
			//	alert("성공");
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

$.boardCommentList = function(nb){
	
	var nextBlock;
	if (nb ==0){
	 nextBlock = 1;
	}else{
	 nextBlock = nb;	
	}
	
	$.ajax({			
		type: "get",
		url: "<%=request.getContextPath()%>/comment/<%=bv.getBidx()%>/"+nextBlock+"/commentList.do",
		dataType: "json",		
		cache: false,
		success: function(data){
		//	alert("성공");
		//	console.log(data);			
		//	alert(JSON.stringify(data));
			commentList(data.alist);
			
			if(data.moreView =="N"){
				$("#morebtn").css("display","none");
			}else{
				$("#morebtn").css("display","block");
			}
			
		} ,
		error:function(){
			alert("등록실패");				
		}			
	});		
}


function getOriginalFileName(fileName){	
	var idx = fileName.lastIndexOf("_")+1;	
	return fileName.substr(idx);
}
//파일이 이미지일경우
function getImageLink(fileName){
	
	if (!checkImageType(fileName)) {
		return fileName;
	}	
	//위치 폴더뽑기
	var front = fileName.substr(0,12);
	//alert(front);
	//파일이름뽑기
	var end = fileName.substr(14);	
	//alert(end);
	return front+end;	
}
function checkImageType(fileName){
	
	var pattern = /jpg$|gif$|png$|jpeg$/i;
	return fileName.match(pattern);
}


function commentList(data){
	var str = "";	
	str ="<tr><td>이름</td><td>내용</td></tr>"
	 
	$(data).each(function(){		
	str= str+ "<tr><td>"+this.cwriter+"</td><td>"+this.ccontents+"</td><tr>"
	});	
	
	$("#tbl").html("<table>"+str+"</table>"); 
	   
	return;
}


</script>
</head>
<body>
내용보기
<table  border=1 style="width:500px;">
<tr>
<td style="width:50px;">제목</td>
<td> <%=bv.getSubject() %> &nbsp;&nbsp;&nbsp;&nbsp;  조회수(<%=bv.getViewcnt() %>) </td>
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
<%
if (bv.getFilename() ==null){
}else{
String exp =  bv.getFilename().substring(bv.getFilename().length()-3, bv.getFilename().length());

if (exp.equals("jpg") || exp.equals("gif") || exp.equals("png")   ) { %>
<img src="<%=request.getContextPath()%>/board/displayFile.do?fileName=<%=bv.getFilename() %>"  width="400px" height="100px"></td>
<%} 
}
%>
</tr>


<tr>
<td style="height:200px;">내용</td>
<td><%=bv.getContents() %>  </td>
</tr>
<tr>
<td>작성자</td>
<td><%=bv.getWriter() %></td>
</tr>
<tr>
<td colspan=2 style="text-align:right;">
<button type="button" onclick="location.href='<%=request.getContextPath()%>/board/boardModify.do?bidx=<%=bv.getBidx() %>'   ">수정</button>
<button type="button" onclick="location.href='<%=request.getContextPath()%>/board/boardDelete.do?bidx=<%=bv.getBidx() %>'   ">삭제</button>
<button type="button" onclick="location.href='<%=request.getContextPath()%>/board/boardReply.do?bidx=<%=bv.getBidx() %>&originbidx=<%=bv.getOriginbidx()%>&depth=<%=bv.getDepth()%>&level_=<%=bv.getLevel_()%>'   ">답변</button>
<button type="button" onclick="location.href='<%=request.getContextPath()%>/board/boardList.do'">목록</button>
</td>
</tr>
</table>

<table>
<tr>
<td>이름</td>
<td><input type="text" name="cwriter" id="cwriter" size="10"></td>
<td></td>
</tr>
<tr>
<td>내용</td>
<td><textarea name="ccontents" id="ccontents" cols=50 rows=3 placeholder="내용을입력하세요"></textarea></td>
<td><input type="button" name="save" id="save" value="확인"></td>
</tr>
</table>
<input id="nextBlock" type="text" value="2" />

<div id="tbl"></div>
<div id="morebtn">
<button id="more">더보기</button>
</div>

</body>
</html>