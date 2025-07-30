<%@page import="com.rays.pro4.controller.CustomerListCtl"%>
<%@page import="com.rays.pro4.Util.HTMLUtility"%>
<%@page import="com.rays.pro4.Bean.CustomerBean"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="com.rays.pro4.Util.DataUtility"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.rays.pro4.Util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Customer page</title>
<script src="<%=ORSView.APP_CONTEXT%>/js/jquery.min.js"></script>
<script src="<%=ORSView.APP_CONTEXT%>/js/Checkbox11.js"></script>

</head>
<link rel="stylesheet"
href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<!-- <link rel="stylesheet" href="/resources/demos/style.css"> -->
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script src="<%=request.getContextPath()%>/js/Utilities.js"></script>
<script>
$(function() {
	$("#udatee").datepicker({
		changeMonth : true,
		changeYear : true,
		yearRange : '2000:2025',
	});
});

function validatePhoneNumber(input, errorId, maxLength) {
    let value = input.value;

    let cleaned = value.replace(/\D/g, '');

    if (value !== cleaned) {
        document.getElementById(errorId).textContent = "Only numeric values allowed";
    } else if (cleaned.length > maxLength) {
        document.getElementById(errorId).textContent = "Maximum " + maxLength + " digits allowed";
    } else {
        document.getElementById(errorId).textContent = "";
    }

    input.value = cleaned.slice(0, maxLength);
}

</script>
<body>
	<jsp:useBean id="bean" class="com.rays.pro4.Bean.CustomerBean"
		scope="request"></jsp:useBean>
	<%@include file="Header.jsp"%>
	<form action="<%=ORSView.CUSTOMER_LIST_CTL%>" method="post">
		<center>

			<div align="center">
				<h1>Customer List</h1>
				<h3>
					<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
					<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
				</h3>

			</div>
			
			
			<%
				List tlist = (List) request.getAttribute("isuue");

				int next = DataUtility.getInt(request.getAttribute("nextlist").toString());
			%>

			<%
				Map map = (Map) request.getAttribute("issue");
			%>

			<%
				int pageNo = ServletUtility.getPageNo(request);
				int pageSize = ServletUtility.getPageSize(request);
				int index = ((pageNo - 1) * pageSize) + 1;

				List list = ServletUtility.getList(request);
				Iterator<CustomerBean> it = list.iterator();
				if (list.size() != 0) {
			%>
			<table width="100%" align="center">

				<td align="center"><label>Name</font> :</label> 
				<input 
   					 type="text" 
   					 name="name"
  				     id="nameInput"
   					 placeholder="Enter name"
  				     value="<%=ServletUtility.getParameter("name", request)%>"
    				 oninput="handleAlphabetInput('nameInput', 'nameError', 20)" 
    				 onblur="handleAlphabetInput('nameInput', 'nameError', 20)">
					 <span style="color: red;" id="nameError"></span>
					 	
				<td align="center"><label>Location</font> :</label>
				<input 
   					 type="text" 
   					 name="location"
  				     id="locationInput"
   					 placeholder="Enter location"
  				     value="<%=ServletUtility.getParameter("location", request)%>"
    				 oninput="handleAlphabetInput('locationInput', 'locationError', 20)" 
    				 onblur="handleAlphabetInput('locationInput', 'locationError', 20)"
					 >
					 	<span style="color: red;" id="locationError"></span>
					 	
				<td align="center"><label>PhoneNumber:</label> 
				 <input type="text" name="phoneNumber"
           placeholder="Enter Phone Number"
           value="<%=ServletUtility.getParameter("phoneNumber", request)%>"
           oninput="validatePhoneNumber(this, 'phoneNumberError', 10)">
			
			<font color="red" id="phoneNumberError">
        <%=ServletUtility.getErrorMessage("phoneNumber", request)%>
    </font>		 
					
					
					
					 <label>Importance</font></label>
						:
				 <%=HTMLUtility.getList2("importance", String.valueOf(bean.getImportance()), map)%>

					<input type="submit" name="operation"
					value="<%=CustomerListCtl.OP_RESET%>"> 
					
					<input type="submit"
					name="operation" 
					value="<%=CustomerListCtl.OP_SEARCH%>"></td>
			</table>
			<br>

			<table border="1" width="100%" align="center" cellpadding=6px
				cellspacing=".2">


				<tr style= "background-color: lightblue" >
					<th><input type="checkbox" id="select_all" name="select">SelectAll</th>
					
					<th>S.No.</th>
					<th>Name</th>
					<th>Location</th>
					<th>PhoneNumber</th>
					<th>Importance</th>
					<th>Edit</th>
				</tr>
				<%
					while (it.hasNext()) {
							bean = it.next();
				%>
				<tr align="center">
					<td><input type="checkbox" class="checkbox" name="ids"
						value="<%=bean.getId()%>"></td>

					<td><%=index++%></td>
					<td><%=bean.getName()%></td>
					<td><%=bean.getLocation()%></td>
					<td><%=bean.getPhoneNumber()%></td>
					<td><%=map.get(bean.getImportance()) %></td>

					<td><a href="CustomerCtl?id=<%=bean.getId()%>">Edit</td>
				</tr>
				<%
					}
				%>

				<table width="100%">

					<tr>
						<th></th>
						<%
							if (pageNo == 1) {
						%>
						<td><input type="submit" name="operation" disabled="disabled"
							value="<%=CustomerListCtl.OP_PREVIOUS%>"></td>
						<%
							} else {
						%>
						<td><input type="submit" name="operation"
							value="<%=CustomerListCtl.OP_PREVIOUS%>"></td>
						<%
							}
						%>

						<td><input type="submit" name="operation"
							value="<%=CustomerListCtl.OP_DELETE%>"></td>
						<td align="center"><input type="submit" name="operation"
							value="<%=CustomerListCtl.OP_NEW%>"></td>

						<td align="right"><input type="submit" name="operation"
							value="<%=CustomerListCtl.OP_NEXT%>"
							<%=(list.size() < pageSize || next == 0) ? "disabled" : ""%>></td>



					</tr>
				</table>
				<%
					}
					if (list.size() == 0) {
				%>
				<td align="center"><input type="submit" name="operation"
					value="<%=CustomerListCtl.OP_BACK%>"></td>


				<%
					}
				%>

				<input type="hidden" name="pageNo" value="<%=pageNo%>">
				<input type="hidden" name="pageSize" value="<%=pageSize%>">

				</form>
				</br>
				</br>
				</br>
				</br>
				</br>
				</br>
				</br>

				</center>
				<%@include file="Footer.jsp"%>
</body>
</html>