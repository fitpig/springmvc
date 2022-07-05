<%--
  Created by IntelliJ IDEA.
  User: Lai Ting Geng
  Date: 2022/6/29
  Time: 22:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="http://localhost:8080/springmvc/upload" enctype="multipart/form-data" method="post">
    <input type="file" name="file"/>
    <input type="submit" value="upload">
</form>

<form action="http://localhost:8080/springmvc/upcsv" enctype="multipart/form-data" method="post">
    <input type="file" name="file"/>
    <input type="submit" value="upload">
</form>


<a href="http://localhost:8080/springmvc/download">点击下载</a>
</body>
</html>
