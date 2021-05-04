<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<title>이미지 보기</title>
<script type="text/javascript">

	$(function () {
		
		var imageExt = ["JPG","GIF","PNG","JPEG"];
		
		$("#changeBtn").click(function () {	// 이벤트 처리 : modal창 이미지 바꾸기
			
			//alert("change");
			 
			// 파일이 비어 있는지 확인
			 
			var fileName = $("#imageFile").val();
			
// 			alert(fileName);
			
			if(!fileName) {	// 바꿀 파일란이 비어 있는 경우의 처리
				
				alert("바꿀 이미지를 반드시 선택하셔야 합니다.");
				
				$("#imageFile").focus();
				
				return false;
				
			}
			
			// 바꿀 파일란이 비어 있지 않는 경우의 처리 : 지원하지 않는 파일일 경우
			// substring(시작, 끝) : 끝 부분은 생략 가능 - 부분 문자열 잘라내기
			// toUpperCase() : 모든 영문자를 모두 대문자로 만들어 준다.
			// toLowerCase() : 모든 영문자를 모두 소문자로 만들어 준다.
			// lastIndexOf("찾는 문자열") : 뒤에서 부터 찾는 문자열의 위치를 알려준다. 찾는 문자열이 없으면 -1이 나온다.
			var ext = fileName.substring(fileName.lastIndexOf(".") + 1).toUpperCase();
			
			//alert(ext);
			
			// 이미지 확장자 인지 확인하는 반복문
			var checkExt = false;	// 지원하지 않는 확장자를 기본으로 세팅 : imageExt가 배열로 선언이 되어 있어야 한다.
			for(i = 0; i < imageExt.length; i++) {
				
				if(ext == imageExt[i]) {
					
					checkExt = true;	// 지원한는 확장자로 바꾼다.
					
					break;
					
				}
				
			}
			
			// 지원하지 않는 이미지 파일 선택한 경우의 처리
			if(!checkExt) {
			
			alert("지원하지 않는 이미지 파일 입니다.");
			
			$("#imageFile").focus();
			
			return false;
			
		}
			
		// 강제적으로 FORM 데이터를 전송
		$("#updateFileForm").submit();
		
		});
		
		$("modalCancel").click(function() {
			
			$("#imageFile").val("");
			
		});
		
	});

</script>
</head>
<body>
 <div class="container">
  <h1>이미지 보기</h1>
   <table class="table">
    <tr>
     <td colspan="2">
      <c:if test="${vo.id == login.id || login.gradeNo == 9 }">
       <!-- 작성자가 로그인 한 회원인 경우에만 나타나는 메뉴 -->
       <a href="update?no=${vo.no }&page=${param.page }&perPageNum=${param.perPageNum }" class="btn btn-default ">수정(제목, 내용)</a>
       <a type="button" class="btn btn-default " data-toggle="modal" data-target="#myModal">파일바꾸기</a>
       <a href="delete?no=${vo.no }&perPageNum=${param.perPageNum }&deleteFile=${vo.fileName }" class="btn btn-default">삭제</a>
      </c:if>
      <!-- EL객체 " : param.page = request.getParameter("page") -->
      <a href="list?page=${param.page }&perPageNum=${param.perPageNum }" class="btn btn-default pull-rigth" >리스트</a> 
     </td>
    </tr>
    <tr>
     <th>번호</th>
     <td>${vo.no }</td>
    </tr>
    <tr>
     <th>제목</th>
     <td>${vo.title }</td>
    </tr>
    <tr>
     <th>이미지</th>
     <td>${path }</td>
    </tr>
    <tr>
     <th>내용</th>
     <td><img src="${vo.fileName }" style="width: 95%; "></td>
    </tr>
    <tr>
     <th>작성자</th>
     <td>${vo.id }</td>
    </tr>
    <tr>
     <th>작정일</th>
     <td><fmt:formatDate value="${vo.writeDate }" pattern="yyyy.MM.dd hh:mm"/></td>
    </tr>
   </table>
 <!-- 	Image Modify Modal -->
<div id="myModal" class="modal fade" role="dialog">
  <div class="modal-dialog">
    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">이미지 바꾸기</h4>
      </div>
      <div class="modal-body">
        <p>바꿀 이미지 파일을 선택하세요</p>
        <form action="updateFile" method="post" enctype="multipart/form-data" id="updateFileForm">
         <input name="page" value="${param.page }" type="hidden">
         <input name="perPageNum" value="${param.perPageNum }" type="hidden">
         <div class="form-group">
          <label for="no">번호</label>
          <input name="no" id="no" class="form-control" value="${vo.no }" readonly="readonly">
         </div>
         <div class="form-group">
          <label for="deleteFile">원본파일</label>
          <input name="deleteFile" id="deleteFile" class="form-control" value="${vo.fileName }" readonly="readonly">
         </div>
         <div class="form-group">
          <label for="imageFile">바꿀 파일 선택</label>
          <input name="multipartFile" id="imageFile" class="form-control" value="" type="file">
         </div>
        </form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" id="changeBtn">바꾸기</button>
        <button type="button" class="btn btn-default" id="modalCancel" data-dismiss="modal">Close</button>
      </div>
    </div>
   </div>
   </div>
 </div>
</body>
</html>