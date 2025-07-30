
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>
<%@page import="com.rays.pro4.Util.HTMLUtility"%>
<%@page import="com.rays.pro4.Util.DataUtility"%>
<%@page import="com.rays.pro4.Util.ServletUtility"%>
<%@page import="com.rays.pro4.controller.TransportationCtl"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<html>
<head>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16*16" />
<title>Transportation Page</title>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- Include jQuery and jQuery UI -->
<link rel="stylesheet"
	href="https://code.jquery.com/ui/1.13.2/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://code.jquery.com/ui/1.13.2/jquery-ui.min.js"></script>

<!-- Custom Script -->
<script>
	$(function() {
		$("#udate").datepicker({
			changeMonth : true, // Enable month selection
			changeYear : true, // Enable year selection
			yearRange : '1980:2030', // Year range
			dateFormat : 'yy-mm-dd' // Correct format of the date
		});
	});
	function limitInputLength(input, maxLength) {
		if (input.value.length > maxLength) {
			input.value = input.value.slice(0, maxLength);
		}
	}
</script>

</head>
<body>
	<jsp:useBean id="bean" class="com.rays.pro4.Bean.TransportationBean"
		scope="request"></jsp:useBean>
	<%@ include file="Header.jsp"%>


	<div align="center">

		<form action="<%=ORSView.TRANSPORTATION_CTL%>" method="post">



			<div align="center">
				<h1>

					<%
						if (bean != null && bean.getId() > 0) {
					%>
					<tr>
						<th><font size="5px"> Update Transportation </font></th>
					</tr>
					<%
						} else {
					%>
					<tr>
						<th><font size="5px"> Add Transportation </font></th>
					</tr>
					<%
						}
					%>
				</h1>

				<h3>
					<font color="red"> <%=ServletUtility.getErrorMessage(request)%></font>
					<font color="limegreen"> <%=ServletUtility.getSuccessMessage(request)%></font>
				</h3>

				<%
					Map map = (Map) request.getAttribute("cate");
				%>

			</div>


			<table>
				<tr>
					<input type="hidden" name="id" value="<%=bean.getId()%>">
					<th align="left">Discription<span style="color: red">*</span>
						:
					</th>
					<td><textarea type="text" name="discription"
							oninput=" handleLetterInput(this, 'discriptionError', 15)"
							onblur=" validateLetterInput(this, 'discriptionError', 15)"
							placeholder="Enter Discription" style="height: 34; width: 219;"><%=DataUtility.getStringData(bean.getDiscription())%></textarea></td>
					<td style="position: fixed"><font color="red"
						id="discriptionError"><%=ServletUtility.getErrorMessage("discription", request)%></font></td>

				</tr>

				<tr>
					<th style="padding: 3px"></th>
				</tr>

				<tr>
					<th align="left">Mode<span style="color: red">*</span> :
					</th>
					<td>
						<%
							String hlist = HTMLUtility.getList2("mode", DataUtility.getStringData(bean.getMode()), map);
						%> <%=hlist%>
					</td>
					<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("mode", request)%></font></td>
				</tr>

				<tr>
					<th style="padding: 3px"></th>
				</tr>

				<tr>
					<th align="left">Date <span style="color: red">*</span> :
					</th>
					<td><input type="text" name="date" placeholder="Enter Date"
						size="26" readonly="readonly" id="udate"
						value="<%=DataUtility.getDateString(bean.getDate())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("date", request)%></font></td>
				</tr>

				<tr>
					<th style="padding: 3px"></th>
				</tr>

				<tr>
					<th align="left">Cost<span style="color: red">*</span> :
					</th>
					<td><input type="text" name="cost" placeholder="Enter Cost"
						size="26"
						value="<%=(DataUtility.getStringData(bean.getCost()).equals("0") ? "" : bean.getCost())%>"></td>
					<td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("cost", request)%></font></td>

				</tr>

				<tr>
					<th style="padding: 3px"></th>
				</tr>


				<tr>
					<th></th>
					<%
						if (bean.getId() > 0) {
					%>
					<td colspan="2">&nbsp; &emsp; <input type="submit"
						name="operation" value="<%=TransportationCtl.OP_UPDATE%>">
						&nbsp; &nbsp; <input type="submit" name="operation"
						value="<%=TransportationCtl.OP_CANCEL%>"></td>

					<%
						} else {
					%>

					<td colspan="2">&nbsp; &emsp; <input type="submit"
						name="operation" value="<%=TransportationCtl.OP_SAVE%>">
						&nbsp; &nbsp; <input type="submit" name="operation"
						value="<%=TransportationCtl.OP_RESET%>"></td>

					<%
						}
					%>
				</tr>
			</table>
		</form>
	</div>

	<%@ include file="Footer.jsp"%>
</body>
</html>
