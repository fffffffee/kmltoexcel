<%--
  Created by IntelliJ IDEA.
  User: ldx
  Date: 2019/12/24
  Time: 16:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="Dom4JforGooglEearth_Kml.Entity.PlacemarkEntity" %>

<%@ page  taglib prefix ="c" uri = "http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
    <title>Hello</title>
</head>
<body>
    <h1>Hello!</h1>
    <h2>${String}</h2>
    <form method="post" action="">
        <table>
            <tr>
                <td>里程</td>
                <td>经纬度</td>
            </tr>
            <%while (placemarkList.hasnext())
        </table>
    </form>
</body>
</html>
