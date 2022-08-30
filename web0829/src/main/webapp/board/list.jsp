<%@page import="com.academy.web0829.domain.Board"%>
<%@page import="com.academy.web0829.util.Pager"%>
<%@page import="java.util.List"%>
<%@page import="com.academy.web0829.board.repository.BoardDAO"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%! 
	BoardDAO boardDAO = new BoardDAO(); 
	Pager pager = new Pager();
	%>
<%
	List <Board> boardList = boardDAO.selectAll();
	out.print("게시물 수는 :"+boardList.size());
	pager.init(boardList, request);//공식 계산 
	
	
%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<style>
table {
	border-collapse: collapse;
	border-spacing: 0;
	width: 100%;
	border: 1px solid #ddd;
}

th, td {
	text-align: left;
	padding: 16px;
}

tr:nth-child(even) {
	background-color: #f2f2f2;
}

.page-style{
	font-size:20px;
	font-weight:bold;
	color:red;
}
</style>
</head>
<body>

	<table>
		<tr>
			<th width="5%">No</th>
			<th width="75%">제목</th>
			<th width="5%">작성자</th>
			<th width="10%">작성일</th>
			<th width="5%">조회수</th>
		</tr>
		<%
			int curPos = pager.getCurPos();
			int num = pager.getNum();
		%>
		<%=pager.getPageSize() %>
	<%for(int i=1; i<=pager.getPageSize(); i++){ %>
	<% if(num<1)break; %>
	<%Board board =boardList.get(curPos++); %>
		<tr>
			<td><%=num-- %></td>
			<td><a href="/news/content.jsp?news_id="><%=board.getTitle() %></a></td>
			<td><%=board.getWriter() %></td>
			<td><%=board.getRegdate() %></td>
			<td><%=board.getHit() %></td>
		</tr>
	<%} %>
		<tr>
			<td colspan="5" style="text-align:center">
			<%for(int i = pager.getFirstPage();i<=pager.getLastPage();i++){ %>
			<%if(i>pager.getTotalPage())break; %>
				[<%=i %>]
				<%} %>
			</td>
		</tr>
		<tr>
			<td colspan="5"><button onClick="location.href='/board/regist.jsp';">글작성</button></td>
		</tr>
	</table>

</body>
</html>