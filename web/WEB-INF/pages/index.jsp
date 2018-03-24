<%@ page import="ru.vainahtelecom.model.Subs.Subscriber" %>
<%@ page import="java.util.List" %>
<%--
  Created by IntelliJ IDEA.
  User: REsterWolf
  Date: 4/21/2017
  Time: 9:48 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>GPON Web Snmp</title>
</head>
<body>

<%
    Object switchIP = request.getSession().getAttribute("targetIpAddress");
%>


Поиск абонентов:
<br/>
<br/>

<form method="post">

    <select id="switch_select" name="switch_ip_select" style="width:200px">
        <optgroup label="Huawei MA5608T">
            <option value="HuaweiMA5608T_10.25.38.22">10.25.38.22</option>
            <option value="HuaweiMA5608T_10.26.45.105">10.26.45.105</option>
            <option value="HuaweiMA5608T_10.26.45.134">10.26.45.134</option>
            <option value="HuaweiMA5608T_10.26.45.168">10.26.45.168</option>
            <option value="HuaweiMA5608T_10.26.52.213">10.26.52.213</option>
            <option value="HuaweiMA5608T_10.26.70.127">10.26.70.127</option>
            <option value="HuaweiMA5608T_10.26.72.237">10.26.72.237</option>
            <option value="HuaweiMA5608T_10.26.72.242">10.26.72.242</option>
            <option value="HuaweiMA5608T_10.26.76.11">10.26.76.11</option>
            <option value="HuaweiMA5608T_10.26.76.12">10.26.76.12</option>
            <option value="HuaweiMA5608T_10.26.76.239">10.26.76.239</option>
            <option value="HuaweiMA5608T_10.26.76.243">10.26.76.243</option>
            <option value="HuaweiMA5608T_10.26.80.155">10.26.80.155</option>
        </optgroup>

        <optgroup label="ELTX">
            <option value="ELTX_10.25.7.253">10.25.7.253</option>
        </optgroup>
    </select>

    <p><input type="text" name="subscriber_name_input"></p>

    <p><input type="submit" value="Поиск" style="width: 100px"></p>
</form>

<br/>
<hr/>

<script>
    function selectOption(string) {
        var select = document.getElementById("switch_select");
        for (var i = 0; i < select.length; i++) {
            var optString = select.options[i].value;
            if (optString.includes(string)) {
                select.selectedIndex = i;
                return;
            }
        }
    }
</script>

<table border="1" cellspacing="0" style="width: 500px">
    <caption>Коммутатор: <b>
        <%

            if (switchIP != null) {
                out.print(switchIP.toString());
        %>
        <script>
            selectOption("<%=switchIP%>");
        </script>
        <%
            }
        %>
    </b> Список абонентов:
    </caption>


    <tr>
        <td align="center" width="60px">№</td>
        <td style="padding-left: 20px">Описание порта</td>
    </tr>

    <%

        List<Subscriber> subscriberList = ((List<Subscriber>) request.getSession().getAttribute("subscribers"));

        if (subscriberList != null)

            for (int i = 0; i < subscriberList.size(); i++) {

                int nubmer = i + 1;

                out.println("<tr height=\"40px\">");
                out.println("<td align=\"center\">" + nubmer + "</td>");

                out.println("<td style=\"padding-left: 30px\">" + "<a href=\"/subscriber_info?index=" + i + "\">" + subscriberList.get(i).getName() + "</a>" + "</td>");
                out.println("</tr>");

            }

    %>


</table>


</body>
</html>
