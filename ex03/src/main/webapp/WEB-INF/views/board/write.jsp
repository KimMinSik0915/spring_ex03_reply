<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<!-- BootStrap Library 등록 CDN 방식 : SiteMesh에서 Decorator.jsp에서 한꺼번에 해결 -->
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<script type="text/javascript">
	$(function() {
		
		$(".cancelBackBtn").click(function() {
			
			history.back();
			
		});
		
	});
</script>
<title>게시판 글 쓰기</title>
</head>
<body>
 <div class="container">
  <h1>게시판 글 쓰기</h1>
  <form action="write" method="post" id="writeForm">
   <input name="perPageNum" type="hidden" value="${param.perPageNum }">
   <div class="form-group">
   <!-- requiered : 필수 입력 || placeholder : 입력외 초기 안내 -->
   <!-- pattern : 정규표현식으로 유효성 검사 || title : 페턴이 맞지 않을 때의 메시지 -->
    <label for="title">제목:</label>
    <input class="form-control" name="title" id="title" placeholder="제목은 4자 이상 입력하셔야 합니다." pattern=".{4,100}" maxlength="100" required="required" title="4자이상 100자 이상 입력해주세요">
   </div>
   <div class="form-group">
    <label for="content">내용:</label>
    <textarea rows="5" name="content" class="form-control" id="content" placeholder="내용은 4자 이상 700자 이하로 입력해 주셔야 합니다." maxlength="700" required="required"></textarea>
   </div>
   <div class="form-group">
    <label for="writer">작성자:</label>
    <input class="form-control" name="writer" id="writer" placeholder="작성자는 2자 이상 10자 이하로 입력하셔야 합니다." required="required" pattern="[A-Za-z가-힣][0-9A-Za-z가-힣]{1,9}" title="이름은 2자부터 10자까지, 첫 글자에는 숫자가 올 수 없습니다.">
   </div>
   <div class="form-group">
    <label for="pw">비밀번호:</label>
    <input class="form-control" name="pw" id="pw" type="text" pattern="[^가-힣ㄱ-ㅎㅏ-ㅣ]{4,20}" required="required" maxlength="20" title="한글은 입력할 수 없습니다.">
   </div>
   <button class="btn btn-default">등록</button>
   <button class="btn btn-default" type="reset">새로입력</button>
   <button class="btn btn-default cancelBackBtn" type="button">취소</button>
  </form>
 </div>
</body>
</html>