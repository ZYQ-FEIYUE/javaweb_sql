<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>welcome</title>
</head>
<body>
<form action="Login" method="post">
<br>email:<input type="text" name="email">
<br>password:<input type="text" name="password">
<br><input type="submit" value="Login">
</form>

<form action="Register" method="post">
<br>name:<input type="text" name="name">
<br>email:<input type="text" name="email">
<br>password:<input type="text" name="password">
<br><input type="submit" value="Register">
</form>

<form action="DeviceMessage" method="post">
<br>email:<input type="text" name="email">
<br>person:<input type=text name="person">
<br><input type="submit" value="DeviceMessage">
</form>

<form action="DeviceQuery" method="post">
<br>email:<input type="text" name="email">
<br><input type="submit" value="DeviceQuery">
</form>

<form action="DeviceShare" method="post">
<br>email:<input type="text" name="email">
<br><input type="submit" value="DeviceShare">
</form>

</body>
</html>