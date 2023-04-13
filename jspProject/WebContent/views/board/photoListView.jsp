<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.ArrayList, com.kh.board.model.vo.Board"%>
<%
	ArrayList<Board> list = (ArrayList<Board>)request.getAttribute("atList");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	.list-area{
		width: 760px;
		margin: auto;
	}
	
	.thumbnail{
		border: 1px solid white;
		width: 220px;
		display: inline-block;
		margin: 14px;
	}
	
	.thumbnail:hover{
		cursor: pointer;
		opacity: 0.7;
	}
	
</style>
</head>
<body>
	<%@include file = "../common/menubar.jsp" %>
	
	<div class="outer" style="height: 1000px;">
		<h2 align="center">사진 게시판</h2>
		<br><br>
		<%if(loginUser != null){ %>		
		<div align="center">
			<a href="<%=contextPath%>/insert.ph" class="btn btn-info">글작성</a>
			<br><br>
		</div>
		<%} %>
		
		<div class="list-area">
			<%for(Board b : list){ %>
			<div class="thumbnail" align="center">
				<input type="hidden" name="bno" value="<%=b.getBoardNo()%>">
				<img src="<%=contextPath+b.getTitleImg()%>"width="200px" height="150px">
				<%if(list.isEmpty()){ %>
				<p>
					조회된 게시글이 없습니다.
				</p>
				<%}else{ %>
				<p>
					<%=b.getBoardNo()+". "+b.getBoardTitle()%><br>
					<%=b.getCount()%>
				</p>
				<%} %>
				
			</div>
			<%} %>
		</div>
	
	</div>
	
	<script>
		$(function(){
			$(".thumbnail").click(function(){
				//내풀이
				//var idx = $(this).text().indexOf(".");
				//var bno = $(this).text().substring(0, idx);
				//console.log(bno);
				
				//강사님
				var bno = $(this).children("input[name=bno]").val();
				
				location.href = "<%=contextPath%>/detail.ph?bno="+bno;
				
				
			});
		})
	</script>
	
</body>
</html>