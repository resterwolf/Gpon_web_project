<%@ page import="ru.vainahtelecom.model.Subs.SubsInfo" %>
<%@ page import="ru.vainahtelecom.model.Subs.Subscriber" %>
<%--
  Created by IntelliJ IDEA.
  User: REsterWolf
  Date: 4/21/2017
  Time: 11:27 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>GPON Web Snmp</title>
</head>
<body>


<%
    Subscriber subscriber = ((Subscriber) request.getAttribute("subscriber"));
    SubsInfo subsInfo = subscriber.getSubsInfo();
%>

<br>
<table border="1" cellspacing="0" style="width: 500px">
    <caption>Коммутатор: <b><%=request.getSession().getAttribute("targetIpAddress").toString()%>
    </b> Информация по абоненту.
    </caption>
    <tr>
        <td colspan="2" align="center"><%=subscriber.getName()%>
        </td>
    </tr>

    <tr>
        <td align="left"><b>ONT статус</b></td>
        <td align="center"><%=subsInfo.getConvertedOntStatus().rowValue()%>
        </td>
    </tr>

    <tr>
        <td align="left"><b>OLT Rx ONT optical power(dBm)</b></td>
        <td align="center"><%=subsInfo.getConvertedOpticalPower()%>
        </td>
    </tr>

</table>

<br>
<a href="/">На главную</a>

</body>
</html>
