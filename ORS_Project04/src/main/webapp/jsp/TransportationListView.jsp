<%@page import="com.rays.pro4.Model.TransportationModel"%>
<%@page import="java.util.Map"%>
<%@page import="com.rays.pro4.Bean.TransportationBean"%>
<%@page import="com.rays.pro4.Bean.BaseBean"%>
<%@page import="com.rays.pro4.Util.HTMLUtility"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="com.rays.pro4.Util.DataUtility"%>
<%@page import="com.rays.pro4.Util.ServletUtility"%>
<%@page import="com.rays.pro4.controller.TransportationListCtl"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<html>
<head>
    <link rel="icon" type="image/png" href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
    <title>Transportation List</title>

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
</script>

</head>
<body>
    <jsp:useBean id="bean" class="com.rays.pro4.Bean.TransportationBean" scope="request"></jsp:useBean>
    <%@include file="Header.jsp"%>

    <form action="<%=ORSView.TRANSPORTATION_LIST_CTL%>" method="post">
        <center>
            <div align="center">
                <h1>Transportation List</h1>
                <h3>
                    <font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
                    <font color="limegreen"><%=ServletUtility.getSuccessMessage(request)%></font>
                </h3>
            </div>

            <%
                List tlist = (List) request.getAttribute("Discription");
                int next = DataUtility.getInt(request.getAttribute("nextlist").toString());
                Map map = (Map) request.getAttribute("cate");
                int pageNo = ServletUtility.getPageNo(request);
                int pageSize = ServletUtility.getPageSize(request);
                int index = ((pageNo - 1) * pageSize) + 1;
                List list = ServletUtility.getList(request);
                Iterator<TransportationBean> it = list.iterator();

                if (list.size() != 0) {
            %>
            <table width="100%" align="center">
                <tr>
                    <td align="center">
                        <label>Discription:</label>
                        <input type="text" name="discription" placeholder="Enter discription"
                            value="<%=ServletUtility.getParameter("discription", request)%>">				
                        
                        <label>Cost:</label>
                        <input type="text" name="cost" placeholder="Enter Cost"
                            value="<%=ServletUtility.getParameter("cost", request)%>">
                        
                        <label>Date:</label>
                        <input type="text" name="date" placeholder="Enter date" readonly="readonly" id="udate"
                            value="<%=ServletUtility.getParameter("date", request)%>">
                        
                        <label>Mode:</label>
                        <%=HTMLUtility.getList2("mode", DataUtility.getStringData(bean.getMode()), map)%>

                        <input type="submit" name="operation" value="<%=TransportationListCtl.OP_SEARCH%>">
                        <input type="submit" name="operation" value="<%=TransportationListCtl.OP_RESET%>">
                    </td>
                </tr>
            </table>
            
            <!-- Table for displaying transportation list -->
            <table border="1" width="100%" align="center" cellpadding="6" cellspacing=".2">
                <tr style="background: skyblue">
                   <th><input type="checkbox" id="select all" name="select">Select
						All</th>
                    
                    <th>S.No.</th>
                    <th>Discription</th>
                    <th>Mode</th>
                    <th>Date</th>
                    <th>Cost</th>
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
                    <td><%=bean.getDiscription()%></td>
                    <td><%=map.get(bean.getMode())%></td>
                    <td><%=bean.getDate()%></td>
                    <td><%=bean.getCost()%></td>
                    <td><a href="TransportationCtl?id=<%=bean.getId()%>">Edit</a></td>
                </tr>
                <%
                    }
                %>
            </table>
            
            <table width="100%">
				<tr>
					<th></th>
					<%
						if (pageNo == 1) {
					%>
					<td><input type="submit" name="operation" disabled="disabled"
						value="<%=TransportationListCtl.OP_PREVIOUS%>"></td>
					<%
						} else {
					%>
					<td><input type="submit" name="operation"
						value="<%=TransportationListCtl.OP_PREVIOUS%>"></td>
					<%
						}
					%>

					<td><input type="submit" name="operation"
						value="<%=TransportationListCtl.OP_DELETE%>"></td>
					<td><input type="submit" name="operation"
						value="<%=TransportationListCtl.OP_NEW%>"></td>
					<td align="right"><input type="submit" name="operation"
						value="<%=TransportationListCtl.OP_NEXT%>"
						<%=(list.size() < pageSize || next == 0) ? "disabled" : ""%>></td>



				</tr>
			</table>
            <%
                }
                if (list.size() == 0) {
            %>
            <input type="submit" name="operation" value="<%=TransportationListCtl.OP_BACK%>">
            <%
                }
            %>

            <input type="hidden" name="pageNo" value="<%=pageNo%>">
            <input type="hidden" name="pageSize" value="<%=pageSize%>">
        </center>
    </form>
    <br>
    <br>
    <br>
    <%@include file="Footer.jsp"%>
</body>
</html>
