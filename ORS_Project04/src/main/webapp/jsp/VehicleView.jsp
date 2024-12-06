<%@page import="com.rays.pro4.controller.VehicleCtl"%>
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
<title>Vehicle Page</title>

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
		$("#udate").datepicker({
			changeMonth : true,
			changeYear : true,
			yearRange : '1900:2024',
		//dateFormat:'yy-mm-dd'
		});
	});
</script>
<body>
	<jsp:useBean id="bean" class="com.rays.pro4.Bean.VehicleBean"
		scope="request"></jsp:useBean>
	<%@ include file="Header.jsp"%>

	<center>

		<form action="<%=ORSView.VEHICLE_CTL%>" method="post">

			<%-- <%
				List list = (List) request.getAttribute("bList");
			%> --%>


			<div align="center">
				<h1>

					<%
						if (bean != null && bean.getId() > 0) {
					%>
					<tr>
						<th><font size="5px"> Update Vehicle </font></th>
					</tr>
					<%
						} else {
					%>
					<tr>
						<th><font size="5px"> Add Vehicle</font></th>
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
					<th align="left">Number: <span style="color: red">*</span> :

					</th>
					<td><input type="text" name="number"  placeholder="Enter number"
						size="25" value="<%=DataUtility.getStringData(bean.getNumber())%>"></td>
					<td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("number", request)%></font></td>

				</tr>

				<tr>
					<th style="padding: 3px"></th>
				</tr>


				<tr>
					<th align="left">Colour <span style="color: red">*</span> :
					</th>
					<td>
						<%
						HashMap map = (HashMap) request.getAttribute("map");

							String hlist = HTMLUtility.getList("colour", String.valueOf(bean.getColour()), map);
						%> <%=hlist%>
					</td>
					<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("colour", request)%></font></td>
				</tr>
				<tr>
					<th style="padding: 3px"></th>
				</tr>

				<tr>
					<th align="left">purchaseDate<span style="color: red">*</span>
						:
					</th>
					<td><input type="text" style="width: 210px" name="purchaseDate"
						placeholder="Select date" readonly="readonly" id="udate"
						value="<%=DataUtility.getDateString(bean.getPurchaseDate())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("purchaseDate", request)%>
					</font></td>
				</tr>

				<tr>
					<th style="padding: 3px"></th>
				</tr>
				<tr>
					<th align="left">InsuranceAmount <span style="color: red">*</span> :
					</th>
					<td><input type="text" style="width: 210px" name="insuranceAmount" 
						maxlength="10" placeholder="Enter Amount" size="25"
						value="<%=DataUtility.getStringData(bean.getInsuranceAmount())%>"></td>
					<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("insuranceAmount", request)%></font></td>
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
						value="<%=VehicleCtl.OP_UPDATE%>"> &nbsp; &nbsp; <input
						type="submit" name="operation" value="<%=VehicleCtl.OP_CANCEL%>"></td>

					<%
						} else {
					%>

					<td colspan="2">&nbsp; &emsp; <input type="submit"
						name="operation" value="<%=VehicleCtl.OP_SAVE%>"> &nbsp;
						&nbsp; <input type="submit" name="operation"
						value="<%=VehicleCtl.OP_RESET%>"></td>

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