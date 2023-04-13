<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.kh.board.model.vo.*, java.util.ArrayList"%>
<%
	Board b = (Board)request.getAttribute("board");
	ArrayList<Attachment> atList = (ArrayList<Attachment>)request.getAttribute("atList");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%@include file = "../common/menubar.jsp" %>
	<div class="outer" style="height:800px">
		<br>
		<h2 align="center">사진 게시글 상세보기</h2>
		<br>
		
		<table align="center" border="1">
			<tr>
				<th width="100">제목</th>
				<td colspan="3"><%=b.getBoardTitle()%></td>
			</tr>
			
			<tr>
				<th>작성자</th>
				<td><%=b.getBoardWriter()%></td>
				<th>작성일</th>
				<td><%=b.getCreateDate() %></td>
			</tr>
			
			<tr>
				<th>내용</th>
				<td colspan="3">
					<p><%=b.getBoardContent()%></p>
				</td>
			</tr>
			
			<tr>
				<th>대표이미지</th>
				<td colspan="3" align="center">
					<img src="<%=contextPath+atList.get(0).getFilePath()+atList.get(0).getChangeName()%>" width="200px" height="150px">
				</td>
			</tr>
			
			
			<tr>
				<%if(atList.isEmpty()){ %>
					<th>상세이미지</th>
					<td></td>
				<%}else{ %>
					<th>상세이미지</th>
					<%for(int i=1; i<atList.size(); i++){ %>
						<td><img src="<%=contextPath+atList.get(i).getFilePath()+atList.get(i).getChangeName()%>" width="150" height="120"></td>
					<%} %>
				<%} %>
			</tr>
			
		</table>
		
		
		
		<br><br>
		
		<div align="center">
			<button type="button" class="btn btn-warning">수정하기</button>
			<button type="button" class="btn btn-danger">삭제하기</button>
			<br><br>
		</div>
		
	</div>
</body>
</html>