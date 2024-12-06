<%@page import="com.rays.pro4.controller.OrderCtl"%>
<%@page import="com.rays.pro4.Util.HTMLUtility"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.rays.pro4.Util.DataUtility"%>
<%@page import="com.rays.pro4.Util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16*16" />
<title>Order Page</title>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<link rel="stylesheet" href="/resources/demos/style.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script>
	$(function() {
		$("#udatee").datepicker({
			changeMonth : true,
			changeYear : true,
			yearRange : '1980:2002',
		});
	});
	function limitInputLength(input, maxLength) {
		if (input.value.length > maxLength) {
			input.value = input.value.slice(0, maxLength);
		}
	}
</script>
<body>
	<jsp:useBean id="bean" class="com.rays.pro4.Bean.OrderBean"
		scope="request"></jsp:useBean>
	<%@ include file="Header.jsp"%>

	<center>

		<form action="<%=ORSView.ORDER_CTL%>" method="post">

			<div align="center">
				<h1>

					<%
						if (bean != null && bean.getId() > 0) {
					%>
					<tr>
						<th><font size="5px"> Update Order </font></th>
					</tr>
					<%
						} else {
					%>
					<tr>
						<th><font size="5px"> Add Order </font></th>
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
					<th align="left">Product Name <span style="color: red">*</span>
						:
					</th>
					<td><input type="text" name="ProductName"
						placeholder="Enter ProductName" size="25"
						value="<%=DataUtility.getStringData(bean.getProductName())%>"></td>
					<td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("ProductName", request)%></font></td>

				</tr>

				<tr>
					<th style="padding: 1px"></th>
				</tr>
				<tr>
					<th align="left">Order Date <span style="color: red">*</span>
						:
					</th>
					<td><input type="text" name="Dob"
						placeholder="Enter Order Date " size="25" id="udatee"
						readonly="readonly"
						value="<%=DataUtility.getDateString(bean.getDob())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("Dob", request)%></font></td>
				</tr>
				<tr>
					<th style="padding: 1px"></th>
				</tr>
				<tr>
					<th align="left">Quantity<span style="color: red">*</span> :
					</th>
					<td><input type="number" name="Quantity" id="quantityInput"
						placeholder="Enter Quantity" style="width: 205px"
						value="<%=DataUtility.getStringData(bean.getQuantity())%>"
						oninput="limitInputLength(this, 15)"></td>
					<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("Quantity", request)%></font></td>
				</tr>
				<tr>
					<th style="padding: 1px"></th>
				</tr>

				<tr>
					<th align="left">Customer<span style="color: red">*</span> :
					</th>
					<td>
						<%
							HashMap map = new HashMap();
							map.put("Mr.Amitabh", "Mr.Amitabh");
							map.put("Miss.jaya", "Miss.jaya");
							map.put("Mr.Govinda", "Mr.Govinda");
							map.put("Mr.rajnikant", "Mr.rajnikant");
							map.put("Mr.Mahesh", "Mr.Mahesh");

							String hlist = HTMLUtility.getList("Customer", String.valueOf(bean.getCustomer()), map);
						%> <%=hlist%>
					</td>
					<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("Customer", request)%></font></td>
				</tr>
				<tr>
					<th style="padding: 1px"></th>
				</tr>

				<tr>
					<th></th>
					<%
						if (bean.getId() > 0) {
					%>
					<td colspan="2">&nbsp; &emsp; <input type="submit"
						name="operation" value="<%=OrderCtl.OP_UPDATE%>"> &nbsp;
						&nbsp; <input type="submit" name="operation"
						value="<%=OrderCtl.OP_CANCEL%>"></td>

					<%
						} else {
					%>

					<td colspan="2">&nbsp; &emsp; <input type="submit"
						name="operation" value="<%=OrderCtl.OP_SAVE%>"> &nbsp;
						&nbsp; <input type="submit" name="operation"
						value="<%=OrderCtl.OP_RESET%>"></td>

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