<%--
  Created by IntelliJ IDEA.
  User: iw
  Date: 03.08.23
  Time: 12:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>

<form action="${pageContext.request.contextPath}/login" method="post">
  <label for="username">Username:
    <input id="username" type="text" name="username">
  </label>
  <br>
  <label for="password">Password:
    <input id="password" type="password" name="password">
  </label>
  <br>
  <button type="submit">Login</button>
</form>

</body>
</html>
