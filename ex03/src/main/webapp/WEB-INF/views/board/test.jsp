<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>

<html>
<head>
<meta charset="UTF-8">
<title>게시판 글보기</title> 
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
$(function(){
   ${(empty msg)?"" : "alert('"+= msg +="');"}
   // F5, ctrl + F5, ctrl + r 새로고침 막기
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
   
// 모달 안에 삭제 버튼 이벤트
   $("#modal_deleteBtn").click(function(){
//    alert("modal 삭제");
   $("#modal_form").submit();

   });
 });
</script>


</head>
<body>
   <div class="container">
      <h1>게시판 글보기</h1>
      <!-- 데이터 표시하는 부분 -->
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
            <div class="col-md-10">
               <pre>${vo.content }</pre>
            </div>
         </li>

         <li class="list-group-item row">
            <div class="col-md-2 title_label">작성자</div>
            <div class="col-md-10">${vo.writer }</div>
         </li>

         <li class="list-group-item row">
            <div class="col-md-2 title_label">작성일</div>
            <div class="col-md-10">
               <fmt:formatDate value="${vo.writeDate }" pattern="yyyy.MM.dd" />
               <fmt:formatDate value="${vo.writeDate }" pattern="hh:mm:ss" />
            </div>
         </li>
         <li class="list-group-item row">
            <div class="col-md-2 title_label">조회수</div>
            <div class="col-md-10">${vo.hit }</div>
         </li>
      </ul>

      <a href="update.do?no=${vo.no }&page=${pageObject.page}&perPageNum=${pageObject.perPageNum}&key=${pageObject.key}&word=${pageObject.word}"
      class="btn btn-default">수정</a>
      <a class="btn btn-default" onclick="return false;"
      data-toggle="modal" data-target="#myModal" >삭제</a>
      <a href="list.do?page=${pageObject.page }&perPageNum=${pageObject.perPageNum}&key=${pageObject.key}&word=${pageObject.word}" class="btn btn-default">리스트</a>
   </div>

   <!-- Modal -->
   <div id="myModal" class="modal fade" role="dialog">
      <div class="modal-dialog">

         <!-- Modal content-->
         <div class="modal-content">
            <div class="modal-header">
               <button type="button" class="close" data-dismiss="modal">&times;</button>
               <h4 class="modal-title">게시판 글삭제 비밀번호 입력</h4>
            </div>
            <div class="modal-body" >
               <form action="delete.do" method="post" id="modal_form">
                  <input type="hidden" name="no" value="${vo.no }">
                  <input type="hidden" name="perPageNum"
                  value="${pageObject.perPageNum }">
                  <div class="form-group">
                     <label>비밀번호 :</label>
                     <input name="pw" type="password" class="form-control" id="pw" pattern="[^가-힣ㄱ-ㅎ]{4,20}" required="required" title="4-20자, 한글은 입력할 수 없습니다" />
                  
                  </div>
               </form>
            </div>
            <div class="modal-footer">
               <button type="button" class="btn btn-default" id="modal_deleteBtn">삭제</button>
               <button type="button" class="btn btn-default" data-dismiss="modal">취소</button>
            </div>
         </div>

      </div>
   </div>

</body>
</html>