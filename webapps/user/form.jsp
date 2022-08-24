<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="kr">
<head>
    <meta charset="UTF-8">
    <title>form</title>
</head>
<body>
<form method="post" action="/users">
    <div>
        <label for="userId">사용자 아이디</label>
        <input class="form-control" id="userId" name="userId" placeholder="User ID">
    </div>
    <div>
        <label for="name">이름</label>
        <input class="form-control" id="name" name="name" placeholder="Name">
    </div>
    <button type="submit">회원가입</button>
</form>
</body>
</html>