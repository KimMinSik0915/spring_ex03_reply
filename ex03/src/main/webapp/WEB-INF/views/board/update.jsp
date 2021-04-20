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
<title>게시판 글 수정</title>
</head>
<body>
 <div class="container">
  <h1>게시판 글 수정</h1>
  <form action="update" method="post" id="updateForm">
   <div class="form-group">
    <input class="form-control" name="no" id="no" readonly="readonly" value="${vo.no }" type="hidden">
    <input type="hidden" name="page" value="${param.page }">
    <input type="hidden" name="perPageNum" value="${param.perPageNum }">
    <input type="hidden" name="key" value="${param.key }">
    <input type="hidden" name="word" value="${param.word}">
   </div>
   <div class="form-group">
   <!-- requiered : 필수 입력 || placeholder : 입력외 초기 안내 -->
   <!-- pattern : 정규표현식으로 유효성 검사 || title : 페턴이 맞지 않을 때의 메시지 -->
    <label for="title">제목:</label>
    <input class="form-control" name="title" id="title" pattern=".{4,100}" maxlength="100" required="required" title="4자이상 100자 이상 입력해주세요" value="${vo.title }">
   </div>
   <div class="form-group">
    <label for="content">내용:</label>
    <textarea rows="5" name="content" class="form-control" id="content" maxlength="700" required="required">${vo.content }</textarea>
   </div>
   <div class="form-group">
    <label for="writer">작성자:</label>
    <input class="form-control" name="writer" id="writer" required="required" pattern="[A-Za-z가-힣][0-9A-Za-z가-힣]{1,9}" title="이름은 2자부터 10자까지, 첫 글자에는 숫자가 올 수 없습니다." value="${vo.writer }">
   </div>
   <div class="form-group">
    <!-- 본인 확인용 비밀번호 -->
    <label for="pw">비밀번호:</label>
    <input class="form-control" name="pw" id="pw" type="password" pattern="[^가-힣ㄱ-ㅎㅏ-ㅣ]{4,20}" required="required" maxlength="20" title="한글은 입력할 수 없습니다.">
   </div>
   <button class="btn btn-default">수정</button>
   <button class="btn btn-default" type="reset">새로 입력</button>
   <button class="btn btn-default cancelBackBtn" type="button">취소</button>
  </form>
 </div>
</body>
</html>