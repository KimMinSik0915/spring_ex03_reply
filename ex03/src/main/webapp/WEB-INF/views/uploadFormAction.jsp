<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Server File Info</title>
</head>
<body>
 <h1>Server File Info</h1>
 <c:forEach items="${uploadFile }" var="mf">
  ----------------------------------------- <br>
  File Name : ${mf.originalFilename } <br>
  File Size : ${mf.size }<br>
  <img  src="/upload/${mf.originalFilename }"><br>
  <a href="/upload/${mf.originalFilename }" download="download">다운로드</a><br>
 </c:forEach>
</body>
</html>