<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.ArrayList, com.kh.board.model.vo.Board, com.kh.common.model.vo.PageInfo"%>
<%
	ArrayList<Board> list = (ArrayList<Board>)request.getAttribute("list");
	PageInfo pi = (PageInfo)request.getAttribute("pi");

%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	
	.list-area{
		border: 1px solid white;
		text-align: center;
	}
	
	.list-area>tbody>tr:hover{
		background-color: gray;
		cursor: pointer;
	}
	
</style>
</head>
<body>
	<%@include file="../common/menubar.jsp" %>
	
	<div class="outer" style="height: 600px">
		<br>
		<h1 align="center">일반 게시판</h1>
		<br><br>
		<%if(loginUser != null){ %>
			<div align="center">
				<a href="<%=contextPath%>/insert.bo" class="btn btn-info">글작성</a>
			</div>
		<%} %>
		
		<br>
		<table align="center" class="list-area">
			<thead>
				<tr>
					<th width="70">글번호</th>
					<th width="70">카테고리</th>
					<th width="300">제목</th>
					<th width="100">작성자</th>
					<th width="70">조회수</th>
					<th width="100">작성일</th>
				</tr>
			</thead>
			<tbody>
				<!-- 
				<tr>
					<td>1</td>
					<td>운동</td>
					<td>운동하러가실분 모집합니다.</td>
					<td>헬스보이</td>
					<td>0</td>
					<td>2010-05-05</td>
				</tr>
				
				<tr>
					<td>6</td>
					<td>요리</td>
					<td>제가 만든 김치찌개 먹어요</td>
					<td>백종원</td>
					<td>1000</td>
					<td>2010-04-11</td>
				</tr>
				
				<tr>
					<td>8</td>
					<td>게임</td>
					<td>롤 하실분</td>
					<td>겜해용</td>
					<td>1</td>
					<td>2011-02-05</td>
				</tr>
			 	-->
			 	<%if(list.isEmpty()){%> <!-- 조회된 목록이 비어있다면 -->
			 		<tr>
			 			<td colspan="6">조회된 게시글이 없습니다.</td>
			 		</tr>
			 	<%}else{ %> <!-- 게시글이 있다면 -->
			 		<%for(Board b : list){ %>
			 			<tr>
							<td><%=b.getBoardNo()%></td>
							<td><%=b.getCategory()%></td>
							<td><%=b.getBoardTitle()%></td>
							<td><%=b.getBoardWriter()%></td>
							<td><%=b.getCount()%></td>
							<td><%=b.getCreateDate()%></td>
						</tr>
			 		<%} %>
			 	<%} %>
			</tbody>
		</table>
		
		<script>
			$(function(){
				$(".list-area>tbody>tr").click(function(){
					//글번호 추출
					var bno = $(this).children().eq(0).text();
					
					location.href = "<%=contextPath%>/detail.bo?bno="+bno;
				});				
			});
		</script>
		
		<br><br>
		
		<div align="center" class="paging-area">
			<!-- 
			<button>1</button>
			<button>2</button>
			<button>3</button>
			<button>4</button>
			<button>5</button>
			<button>다음</button>
			 -->
			 <%if(pi.getCurrentPage()!=1){ %>
			 	<button onclick="location.href='<%=contextPath%>/list.bo?currentPage=<%=pi.getCurrentPage()-1%>'">&lt;</button>
			 <%} %>
			 
			 <%for(int i=pi.getStartPage(); i<=pi.getEndPage(); i++){ %>
			 	<!-- 내가 보고있는 페이지 버튼은 비활성화 하기 -->
			 	<%if(i != pi.getCurrentPage()){ %>
			 		<button onclick="location.href='<%=contextPath%>/list.bo?currentPage=<%=i%>'"><%=i%></button>			 		
			 	<%}else{ %>
			 		<button disabled><%=i%></button>
			 	<%} %>
			 <%} %>
			 
			 <%if(pi.getCurrentPage()!=pi.getMaxPage()){ %>
			 	<button onclick="location.href='<%=contextPath%>/list.bo?currentPage=<%=pi.getCurrentPage()+1%>'">&gt;</button>
			 <%} %>
			 
			 <br>
		</div>
		
	</div>
</body>
</html>