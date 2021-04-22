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
		
//  		$(document).keydown(function (e) {		// F5m ctrl + F5, ctrl + r(새로고침) 막기
		    
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
		
		// 글 보기를 하면 바로 댓글 리스트 호출
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
						      	//  데이터가 있는 만큼 반복처리 (li태그 만들어 내기)
      							// rno를 표시하지 않고 태그안에 속성으로 숨겨 놓음 data-rno="12"
								str += "<li class='let clearfix' data-rno='" + list[i].rno + "'>";
						        str += "<div>";
						        str +=  "<div class='header'>";
						        str += "<strong class='primary-font'>" + list[i].writer + "</strong>";
						        str +=  "<small class='pull-right text-muted' >"+ replyService.displayTime(list[i].writeDate) + "</small>";		// class=muted : 글자색을 회색으로 만들어 주는 BS, CSS
						        str += "</div>";
						        str += "<p><pre style='background: none;'>" + list[i].content + "</pre></p>";
						        str += "</div>";
						        str += "</li>";
								
							}
						
						}
						
						replyUL.html(str);		// .HTML(str) : str을 HTML태그로 만들어 준다.
							
					});	// 실패시 함수는 생략 가능하다.  
					
		}	// end of showList()
		
		// 댓글 모달창 전역변수 지정
		var replyModal = $("#replyModal");
		
		// 댓글 등록 버튼 이벤트 처리 : 댓글의 모달창 정보 수정 및 보이기
		$("#writeReplyBtn").click(function() {
			
			// alert('댓들 등록');

			// Modal창 title 변경
			$("#replyModalTitle").text("Reply Write");
			
			// reply > Form > input Data 지우기
			replyModal.find("input, textarea").not("#replyNo").val("");		// input 중에서 id = 'replyNo'를 제외 시킨다 [.not(selector)]
			
			// replyModal에 있는 입력 항목을 모두 보이게 하기
			replyModal.find("div.form-control").show();
			
			// rno는 사용하지 않기 때문에 rno는 보이지 않게 한다.
			replyModal.find("#replyRnoDiv").hide();
			
			replyModal.modal("show");
			
		});
		
		// 댓글 모달창의 등록 버튼에 대한 Event처리 : 입력된 Data를 JSON data를 만든 후 Server에 전송
		$("#replyModalWriteBtn").click(function () {
			
			var reply = {};
			
			reply.no = $("#replyNo").val();
			reply.content = $("#replyContent").val();
			reply.writer = $("#replyWriter").val();
			reply.pw = $("#replyPw").val();
			
			// alert(reply);
			
			// alert(JSON.stringify(reply));
			
			// Ajax를 이용한 댓글 등록 처리
			replyService.write(reply, function (result) {		// 등록을 성공했을 때의 처리 함수
				
				alert(result);
			
				replyModal.modal("hide");
				
				showList();		// 등록 후 리스트를 다시 가져오기
				
			});			
			
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
 
 <!-- 댓글의 시작 -->
  <div class="=row" style="margin: 20px -30px;">
   <div class="col-lg-12">
    <div class="panel panel-default">
     <div class="panel-heading">
      <i class="fa fa-comments fa-fw"></i>
      reply
      <button class="pull-right btn btn-primary btn-xs" id="writeReplyBtn">new reply</button>
 	 </div>
     <div class="panel-body">
      <ul class="chat">
      </ul>
	 </div>
    </div>
   </div>
  </div>
 <!-- 댓글의 끝 -->
 </div>
 <!-- conatiner의 끝 -->
 
 <!-- Modal - 댓글 쓰기/ 수정 시 사용되는 모달창  -->
 <div class="modal fade" id="replyModal" role="dialog">
  <div class="modal-dialog">
   <div class="modal-content">
    <div class="modal-header">
     <button type="button" class="close" data-dismiss="modal">&times;</button>
     <h4 class="modal-title">
      <i class="fa fa-comments fa-fw"></i>
      <span id="replyModalTitle">Reply Modal</span>
     </h4>
    </div>
    <div class="modal-body">
     <form>
      <div class="form-group" id="replyNoDiv">
       <label for="replyNo">번호: </label>
       <input name="no" type="text" class="form-control" id="replyNo" readonly="readonly" value="${vo.no }">
      </div>
      <div class="form-group" id="replyRnoDiv">
       <label for="replyRno">댓글번호: </label>
       <input name="rno" type="text" class="form-control" id="replyRno" readonly="readonly">
      </div>
      <div class="form-group" id="replyContentDiv">
       <label for="content">내용:</label>
       <textarea rows="5" name="content" class="form-control" id="replyContent"></textarea>
      </div>
      <div class="form-group" id="replyWriterDiv">
       <label for="writer">작성자: </label>
       <input name="writer" type="text" class="form-control" id="replyWriter" required="required" pattern="[A-Za-z가-힣][A-Za-z가-힣0-9]{1,9}">
      </div>
      <div class="form-group" id="replyPwDiv">
       <label for="replyPw">비밀번호: </label>
       <input name="pw" type="text" class="form-control" id="replyPw" required="required" pattern=".{4,20}">
      </div>
     </form>
    </div>
    <div class="modal-footer">
     <button type="button" class="btn btn-default" id="replyModalWriteBtn">등록</button>
     <button type="button" class="btn btn-default" data-dismiss="modal">취소</button>
    </div>
   </div>
  </div>
 </div>
 <!-- Modal - 댓글 쓰기/ 수정 시 사용되는 모달끝  -->
 
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