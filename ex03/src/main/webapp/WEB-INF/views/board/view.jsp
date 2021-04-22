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
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

<!-- util Js 포함 -->
<script type="text/javascript" src="/js/util.js"></script>
<!-- reply Moduel Js 포함 -->
<script type="text/javascript" src="/js/reply.js"></script>
<style type="text/css">

	.title_label {
	
		border: 1px dotted #ddd;
		
	}
	
	ul.chat {
	
		list-style: none;
		
	}
	
</style>
<script type="text/javascript">

	$(function() {
		
		${(empty msg)?"":"alert('" += msg += "')"}
		
//  		$(document).keydown(function (e) {
		    
// 		    if (e.which === 116) {
		    	
// 		        if (typeof event == "object") {
		        	
// 		            event.keyCode = 0;
		            
// 		        }
		        
// 		        return false;
		        
// 		    } else if (e.which === 82 && e.ctrlKey) {
		    	
// 		        return false;
		        
// 		    }
		    
// 		});
		
		$("#modal_Btn").click(function() {	// modal안에 삭제 버튼 이벤트
			
			//alert("Modal_delete");
			
			$("#modal_form").submit();
			
		});
		
		// 댓글 처리 부분에 대한 JS 부분
		console.log("===============================================================");
		console.log("JS Reply List Test!!!");
		
		// 전역 변수 선언 - $(function(){@@!!}) : 안에 선언된 함수에서는 공통으로 사용 가능
		
		var no = ${vo.no};
		var repPage = 1;
		var repPerPageNum=5;
		var replyUL = $(".chat");
		
		console.log("JS Reply List no : " + no);
		
		//alert('test');
		
		// 댓글보기
		showList();
		
		
		// function showList() - no, page, perPageNum : 전역 변수로 선언되어 있으므로 변수명 사용
		function showList() {
			
			replyService.list({no:no, repPage:repPage, repPerPageNum:repPerPageNum}, 	// 서버에 넘겨줄 data
					// 성공했을 때의 함수 = data라는 이름으로 list가 들어온다.
					function(list){
				
						//alert(list);
				
						// 데이터가 없을 때의 처리
						var str = "";
						
						if(!list || list.length == 0) {
							
							//alert("data 없음");
							
							str += "<li>데이터가 존재하지 않습니다.</li>"
							
							//alert(str);
							
						} else {		// 데이터가 있을 때의 처리
				
							//alert('data 있음');
							
							for(var i = 0; i < list.length; i++) {
								
								console.log(list[i]);
								
								// tag 만들기 : 데이터 한개당 li tag 하나가 생긴다.
								str += "<li class='let clearfix' data-rno='" + list[i].rno + "'>";
						        str += "<div>";
						        str +=  "<div class='header'>";
						        str += "<strong class='primary-font'>" + list[i].writer + "</strong>";
						        str +=  "<small class='pull-right text-muted' >"+ replyService.displayTime(list[i].writeDate) + "</small>";
						        str += "</div>";
						        str += "<p><pre style='background: none;'>" + list[i].content + "</pre></p>";
						        str += "</div>";
						        str += "</li>";
								
							}
						
						}
						
						replyUL.html(str);
							
					});	// 실패시 함수는 생략 가능하다.
					
		}
		
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
 
 <!-- 댓글의 시작 -->
  <div class="=row" style="margin: 20px -30px;">
   <div class="col-lg-12">
    <div class="panel panel-default">
     <div class="panel-heading">
      <i class="fa fa-comments fa-fw"></i>
      reply
 	 </div>
     <div class="panel-body">
      <ul class="chat">
       <!-- 데이터가 있는 만큼 반복처리 (li태그 만들어 내기) -->
       <!-- rno를 표시하지 않고 태그안에 속성으로 숨겨 놓음 data-rno="12" -->
      </ul>
	 </div>
    </div>
   </div>
  </div>
 <!-- 댓글의 끝 -->
 
 </div>
 <!-- conatiner의 끝 -->
 <!-- modal - 게시판 글 삭제 시 사용되는 모달 창 -->
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