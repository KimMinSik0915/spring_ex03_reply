<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<!-- BootStrap Library 등록 CDN 방식 : SiteMesh에서 Decorator.jsp에서 한꺼번에 해결 -->
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<style type="text/css">
	.title_label {
	
		border: 1px dotted #ddd;
		
	}
</style>
<script type="text/javascript">
	$(function() {
		
		${(empty msg)?"":"alert('" += msg += "')"}
		
 		$(document).keydown(function (e) {
		    
		    if (e.which === 116) {
		    	
		        if (typeof event == "object") {
		        	
		            event.keyCode = 0;
		            
		        }
		        
		        return false;
		        
		    } else if (e.which === 82 && e.ctrlKey) {
		    	
		        return false;
		        
		    }
		    
		});
		
		$("#modal_Btn").click(function() {	// modal안에 삭제 버튼 이벤트
			
			//alert("Modal_delete");
			
			$("#modal_form").submit();
			
		});
		
	});
	
</script>
<title>게시판 글 보기</title>
</head>
<body>
 <div class="container">
  <h1>게시판 글 보기</h1>
  <ul class="list-group">
   <li class="list-group-item row">
    <div class="col-md-2 title_label">번호</div>
    <div class="col-md-10">${vo.no }</div>
   </li>
   <li class="list-group-item row">
    <div class="col-md-2 title_label">제목</div>
    <div class="col-md-10">${vo.title }</div>
   </li>
   <li class="list-group-item row">
    <div class="col-md-2 title_label">내용</div>
    <div class="col-md-10"><pre>${vo.content }</pre></div>
   </li>
   <li class="list-group-item row">
    <div class="col-md-2 title_label">작성자</div>
    <div class="col-md-10">${vo.writer }</div>
   </li>
   <li class="list-group-item row">
    <div class="col-md-2 title_label">작성일</div>
    <div class="col-md-10"><fmt:formatDate value="${vo.writeDate }" pattern="yyyy.MM.dd hh:mm"/></div>
   </li>
   <li class="list-group-item row">
    <div class="col-md-2 title_label">조회수</div>
    <div class="col-md-10">${vo.hit }</div>
   </li>
  </ul>
  <a href="update?no=${vo.no }&page=${pageObject.page}&perPageNum=${pageObject.perPageNum}&key=${pageObject.key}&word=${pageObject.word}" class="btn btn-default">수정</a>
  <button class="btn btn-default"  data-toggle="modal" data-target="#delete">삭제</button>
  <a href="list?page=${pageObject.page }&perPageNum=${pageObject.perPageNum}&key=${pageObject.key}&word=${pageObject.word}" class="btn btn-default">리스트</a>
 </div>
 <div class="modal fade" id="delete" role="dialog">
  <div class="modal-dialog">
   <div class="modal-content">
    <div class="modal-header">
     <button type="button" class="close" data-dismiss="modal"></button>
     <h4 class="modal-title">게시판 글 삭제 비밀번호 입력</h4>
    </div>
    <div class="modal-body">
     <form action="delete?perPageNum=${pageObject.perPageNum }" method="post" id="modal_form">
      <input type="hidden" name="no" value="${vo.no }">
      <input type="hidden" name="perPageNum" value="${pageObjcet.perPageNum }">
      <div class="form-group">
       <label for="pw">비밀번호:</label>
       <input class="form-control" name="pw" id="pw" type="password" pattern="[^가-힣ㄱ-ㅎㅏ-ㅣ]{4,20}" required="required" maxlength="20" title="한글은 입력할 수 없습니다.">
      </div>
     </form>
    </div>
    <div class="modal-footer">
     <button type="button" class="btn btn-default" id="modal_Btn">삭제</button>
     <button type="button" class="btn btn-default" data-dismiss="modal">취소</button>
    </div>
   </div>
  </div>
 </div>
</body>
</html>