\<%@page import="com.rays.pro4.controller.TradeHistoryCtl"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>
<%@page import="com.rays.pro4.Util.HTMLUtility"%>
<%@page import="com.rays.pro4.Util.DataUtility"%>
<%@page import="com.rays.pro4.Util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<html>
<head>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16*16" />
<title>Trade History Page</title>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<link rel="stylesheet" href="/resources/demos/style.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script src="<%=ORSView.APP_CONTEXT%>/js/Validation.js"></script>
<script>
$(function() {
	$("#Udate").datepicker({
		changeMonth : true,
		changeYear : true,
		yearRange : '1980:2030',
		dateFormat:'yy-mm-dd'
	});
});

$(function() {
	$("#Udatee").datepicker({
		changeMonth : true,
		changeYear : true,
		yearRange : '1980:2030',
		dateFormat:'yy-mm-dd'
	});
});
</script>
<body>
	<jsp:useBean id="bean" class="com.rays.pro4.Bean.TradeHistoryBean"
		scope="request"></jsp:useBean>
	<%@ include file="Header.jsp"%>

	<center>

		<form action="<%=ORSView.TRADEHISTORY_CTL%>" method="post">

			<%-- <%
				List list = (List) request.getAttribute("bList");
			%> --%>


			<div align="center">
				<h1>

					<%
						if (bean != null && bean.getId() > 0) {
					%>
					<tr>
						<th><font size="5px"> Update Trade History </font></th>
					</tr>
					<%
						} else {
					%>
					<tr>
						<th><font size="5px"> Add Trade History</font></th>
					</tr>
					<%
						}
					%>
				</h1>

				<h3>
					<font color="red"> <%=ServletUtility.getErrorMessage(request)%></font>
					<font color="green"> <%=ServletUtility.getSuccessMessage(request)%></font>
				</h3>

			</div>
			<input type="hidden" name="id" value="<%=bean.getId()%>"> 

			<table>
				<tr>
					<th align="left">User Id: <span style="color: red">*</span> :

					</th>
					<td><input type="text" name="userId"  placeholder="Enter userId"
						size="25" value="<%=DataUtility.getStringData(bean.getUserId())%>"></td>
					<td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("userId", request)%></font></td>

				</tr>

				<tr>
					<th style="padding: 3px"></th>
				</tr>


				<tr>
					<th align="left">Transaction Type <span style="color: red">*</span> :
					</th>
					<td>
						<%
						HashMap map = (HashMap) request.getAttribute("map");

							String hlist = HTMLUtility.getList("transactionType", String.valueOf(bean.getTransactionType()), map);
						%> <%=hlist%>
					</td>
					<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("transactionType", request)%></font></td>
				</tr>
				<tr>
					<th style="padding: 3px"></th>
				</tr>

				<tr>
					<th align="left">startDate<span style="color: red">*</span>
						:
					</th>
					<td><input type="text" style="width: 210px" name="startDate"
						placeholder="Enter Start date" readonly="readonly" id="Udate"
						value="<%=DataUtility.getDateString(bean.getStartDate())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("startDate", request)%>
					</font></td>
				</tr>


				<tr>
					<th style="padding: 3px"></th>
				</tr>
				<tr>
					<th align="left">Enddate<span style="color: red">*</span>
						:
					</th>
					<td><input type="text" style="width: 210px" name="endDate"
						placeholder="Enter endDate" readonly="readonly" id="Udatee"
						value="<%=DataUtility.getDateString(bean.getEndDate())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("endDate", request)%>
					</font></td>
				</tr>

				<tr>
					<th style="padding: 3px"></th>
				</tr>

				<tr>
					<th></th>
					<%
						if (bean.getId() > 0) {
					%>
					<td>&emsp;
					<td><input type="submit" name="operation"
						value="<%=TradeHistoryCtl.OP_UPDATE%>"> &nbsp; &nbsp; <input
						type="submit" name="operation" value="<%=TradeHistoryCtl.OP_CANCEL%>"></td>

					<%
						} else {
					%>

					<td colspan="2">&nbsp; &emsp; <input type="submit"
						name="operation" value="<%=TradeHistoryCtl.OP_SAVE%>"> &nbsp;
						&nbsp; <input type="submit" name="operation"
						value="<%=TradeHistoryCtl.OP_RESET%>"></td>

					<%
						}
					%>
				</tr>
			</table>
		</form>
	</center>

	<%@ include file="Footer.jsp"%>
</body>
</html>