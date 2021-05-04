<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>File Upload</title>
</head>
<body>
 <h1>File Upload</h1>
 <form action="uploadFormAction" method="post" enctype="multipart/form-data">
 <!-- multiple = 여러개를 선택 할 수 있다 없으면 1개만 선택할 수 있다. -->
  첨부파일 : <input type="file" name="uploadFile" multiple="multiple"><br>
  <button>전송</button>
 </form>
</body>
</html>