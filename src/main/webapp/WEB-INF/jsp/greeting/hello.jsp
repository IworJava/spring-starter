<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Hello</title>
</head>
<body>

HELLO ${sessionScope.user.username}!
<br><br>
<button><a href="${pageContext.request.contextPath}/api/v1/login">Login</a></button>
</body>
</html>