<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Authentication</title>
    <script>
        function redirectTo(url) {
            window.location.href = url;
        }
    </script>
</head>
<body>
    <h1>Authentication Page</h1>
    <p>Choose an action:</p>
    <button onclick="redirectTo('http://localhost:8066/validate')">Continue without registration</button><br><br>
    <div id="registrationForm" style="display: none;">


<h2>User Registration</h2>

<form action="${pageContext.request.contextPath}/registration" method="post">
    <label for="name">name:</label>
    <input type="name" id="name" name="name" required><br><br>

    <label for="password">Пароль:</label>
    <input type="password" id="password" name="password" required><br><br>

    <select id="userRole" name="role" required>
        <option value="USER">User</option>
        <option value="ADMIN">Admin</option>
    </select><br>

    <input type="submit" value="Register">
</form>

<c:if test="${not empty accountExists}">
    <p style=> user already exists <a href="${pageContext.request.contextPath}/login">enter</a>.</p>
</c:if>

</body>
</html>
