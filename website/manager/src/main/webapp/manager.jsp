<%--
  Created by IntelliJ IDEA.
  User: domea
  Date: 17-11-21
  Time: 下午4:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Mappingo后台管理系统</title>
    <script src="js/libs/requirejs/require.js" data-main="js/app/manager.js"></script>
    <style>
        *,html,body{
            margin:0px;
            padding:0px;
            /*width:100%;*/
            /*height: 100%;*/
            /*overflow: hidden;*/
        }
    </style>
</head>
<body>
<%String token=session.getAttribute("token").toString();%>
<%String userid=session.getAttribute("userid").toString();%>
<input type="hidden" name="token" id="token" value="<%=token%>"/>
<input type="hidden" name="username" id="username" value="<%=userid%>"/>
<div id="header" style="height: 70px;"></div>
<div id="footer" style="height: 25px;"></div>
</body>
</html>
