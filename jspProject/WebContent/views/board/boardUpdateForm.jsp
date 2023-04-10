<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.ArrayList, com.kh.board.model.vo.*"%>
<%
	Board b = (Board)request.getAttribute("board");
	Attachment at = (Attachment)request.getAttribute("at");
	ArrayList<Category> clist = (ArrayList<Category>)request.getAttribute("clist");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	#update-form>table{
		border: 1px solid white;
	}
	
	#update-form input,textarea{
		width: 100%;
		box-sizing: border-box;
	}
</style>
</head>
<body>
	<%@include file="../common/menubar.jsp"%>
	
	<div class="outer">
		<br>
		<h2 align="center">수정 페이지</h2>
		<!-- 카테고리, 제목, 내용, 첨부파일, 작성자번호 -->
		<form action="<%=contextPath%>/update.bo" method="post" id="update-form" enctype="multipart/form-data">
			<!-- 게시글 번호 숨겨보내기 -->
			<input type="hidden" name="boardNo" value="<%=b.getBoardNo()%>">
			<!-- 카테고리 테이블에서 조회해온 카테고리 목록 선택상자 만들기 -->
			<script>
				$(function(){
					//option에 있는 text와 조회해온 게시글 카테고리 일치하는지 찾아내어 선택되어있게 작업하기
					$("#update-form option").each(function(){
						//현재 접근된 요소객체의 TEXT와 조회해온 카테고리가 같다면
						if($(this).text() == "<%=b.getCategory()%>"){
							//해당 요소를 선택되어있게 만들기
							$(this).attr("selected", true);
						}
					});
				});
			</script>
			<table align="center">
				<tr>
					<th width="100">카테고리</th>
					<td width="500">
						<select name="category">
						<!-- 
							<option value="10">공통</option>
							<option value="20">운동</option>
							<option value="30">등산</option>
							<option value="40">게임</option>
							<option value="50">낚시</option>
							<option value="60">요리</option>
							<option value="70">기타</option>
						 -->
						 	<%for(Category c : clist){ %>
						 		<option value="<%=c.getCategoryNo()%>"><%=c.getCategoryName()%></option>
						 	<%} %>
						</select>
					</td>
				</tr>
				
				<tr>
					<th>제목</th>
					<td><input type="text" name="title" value="<%=b.getBoardTitle()%>" required></td>
				</tr>
				
				<tr>
					<th>내용</th>
					<td>
						<textarea name="content" rows="10" cols="30" required><%=b.getBoardContent()%></textarea>
					</td>
				</tr>
				
				<tr>
					<th>첨부파일</th>
					<!-- 
					<td>
						<input type="file" name="upfile">
					</td>
					 -->
					 <td>
					 	<!-- 내풀이 -->
						
						<!--
						<%if(at == null){ %>
							<input type="file" name="reUpfile">
						<%}else{ %>
							<a href="<%=contextPath + at.getFilePath()+"/"+at.getChangeName()%>" download><%=at.getOriginName()%></a>
							<button type="button" id="delAt">삭제</button>
						<%} %>
						 -->
					 	 
					 	 
					 	 <!-- 강사님 풀이  -->
					 	 <%if(at!=null){ %>
					 	 	<!-- 기존 첨부파일이 있었을 경우 수정할때 첨부파일 정보를 보내야한다. -->
					 	 	<!-- 파일번호,변경된 파일명 전달하기 -->
					 	 	<%=at.getOriginName()%>
					 	 	<input type="hidden" name="fileNo" value="<%=at.getFileNo()%>">
					 	 	<input type="hidden" name="originFileName" value="<%=at.getChangeName()%>">
					 	 <%} %>
					 	 <input type="file" name="reUpfile">
	
					 </td>
				</tr>
			</table>
			<br>
			<div align="center">
				<button type="submit">게시글 등록</button>
				<button type="reset">초기화</button>
			</div>
			
			 <%if(at != null){%>
				 <script>
				 	$(function(){
				 		$("#delAt").click(function(){
				 			location.href = "<%=contextPath%>/atUpdate.bo?fno=<%=at.getFileNo()%>&bno=<%=b.getBoardNo()%>";
				 		});
				 	});
				 </script>
			 <%} %>
		</form>
	
	</div>
</body>
</html>