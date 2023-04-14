<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.kh.board.model.vo.Board, com.kh.board.model.vo.Attachment"%>
<% 
	Board b = (Board)request.getAttribute("board");
	Attachment at = (Attachment)request.getAttribute("at");
%>
  

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>

</style>
</head>
<body>
	<%@include file="../common/menubar.jsp" %>
	
	<div class="outer" style="height:600px">
		<br>
		<h2 align="center">일반게시판 상세조회</h2>
		
		<table id="detail-area" align="center" border="1">
			<tr>
				<th>카테고리</th>
				<td width="100"><%=b.getCategory()%></td>
				<th>제목</th>
				<td width="200"><%=b.getBoardTitle()%></td>
			</tr>
			<tr style="text-align: right">
				<th>작성자</th>
				<td><%=b.getBoardWriter()%></td>
				<th>작성일</th>
				<td><%=b.getCreateDate()%></td>
			</tr>
			<tr>
				<th>내용</th>
				<td colspan="3">
					<p style="height:200px"><%=b.getBoardContent()%></p>
				</td>
			</tr>
			<tr>
				<th>첨부파일</th>
				<td colspan="3">
					<%if(at == null){ %> <!-- 없을경우  -->
						첨부파일이 없습니다.
					<%}else{ %> <!-- 있을 경우  -->
						<a href="<%=contextPath + at.getFilePath()+"/"+at.getChangeName()%>" download><%=at.getOriginName()%></a>
					<%} %>
				</td>
			</tr>
		</table>
		<br>
		<br>
		<%if(loginUser != null && loginUser.getUserId().equals(b.getBoardWriter())){ %>
			<div align="center">
				<button onclick="location.href='<%=contextPath%>/update.bo?bno=<%=b.getBoardNo()%>'" class="btn btn-info">수정하기</button>
				<button onclick="location.href='<%=contextPath%>/delete.bo?bno=<%=b.getBoardNo()%>'" class="btn btn-danger">삭제하기</button>
			</div>
		<%} %>
		
		<br>
		<div id="reply-area">
			<%if(loginUser != null){ %>
				<input type="hidden" name="userId" value="<%=loginUser.getUserNo()%>">
			<%} %>
			<table border="1" align="center">
				<thead>
				<%if(loginUser != null){ %>
					<tr>
						<th>댓글작성</th>
						<td><textarea rows="3" cols="50" style="resize:none;"></textarea></td>
						<td><button onclick="insertReply()">댓글등록</button></td>
					</tr>
				<%}else{ %>
					<tr>
						<th>댓글작성</th>
						<td><textarea rows="3" cols="50" style="resize:none;" readonly>로그인 후 이용이 가능합니다.</textarea></td>
						<td><button type="button">댓글등록</button></td>						
					</tr>
				<%} %>
				</thead>
				<tbody>
					<!--
					<tr>
						<td>작성자</td>
						<td>댓글 내용</td>
						<td>댓글 작성일 2023/04/13</td>
					</tr>
					  -->
				</tbody>
			</table>
			<br><br>
			
			<script>
				$(function(){
					selectReplyList();
				});
				
				function insertReply(){
					//댓글 삽입
					//게시글 번호
					//성공시에는 댓글 리스트 조회함수 실행 후 textarea 비워주기
					//console.log(<%=b.getBoardNo()%>);
					//console.log($("#reply-area textarea").val());
					//console.log($("#reply-area input[type=hidden]").val());
					
					$.ajax({
						url:"insertRp.bo",
						data:{
							bno:<%=b.getBoardNo()%>,
							content:$("#reply-area textarea").val(),
							//userNo:$("#reply-area input[type=hidden]").val() jsp에서 담아서 넘기기
						},
						success:function(result){
							if(result>0){
								alert("댓글 등록 성공");
								$("#reply-area textarea").val("");
								
								//댓글리스트 갱신
								selectReplyList();
							}else{
								alert("댓글 등록 실패");
							}
						},
						error:function(){
							console.log("통신 실패");
						}
					});
					
				}
				
				function selectReplyList(){
					//댓글 목록 조회
					//조회해온 데이터를 tbody에 tr로 출력해주기
					$.ajax({
						url:"selectRp.bo",
						data:{
							bno:<%=b.getBoardNo()%>
						},
						success:function(list){
							var result = "";
							
							for(var i in list){
								result += "<tr>"
											+"<td>"+list[i].replyWriter+"</td>"
											+"<td>"+list[i].replyContent+"</td>"
											+"<td>"+list[i].createDate+"</td>"
											+"</tr>";
							}
							
							$("#reply-area tbody").html(result);
						},
						error:function(){
							console.log("통신 실패");	
						}
					});
				}
			</script>
		
		</div>
	</div>
</body>
</html>