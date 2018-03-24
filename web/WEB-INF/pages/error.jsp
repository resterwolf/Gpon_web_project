<%@ page import="ru.vainahtelecom.errors.Error" %>

<%--
  Created by IntelliJ IDEA.
  User: REsterWolf
  Date: 4/24/2017
  Time: 9:49 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>GPON Web Snmp</title>
</head>
<body>

<%
    Error error = (Error) request.getAttribute("error");
    if (error != null) {
        out.println("Возникла ошибка:");
        out.print(error.getErrorString());
    }

%>
</br>
</br>
<a href="/">На главную</a>

</body>
</html>
