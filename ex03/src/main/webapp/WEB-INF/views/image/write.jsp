<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>이미지 등록</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<title>이미지 리스트</title>
<script type="text/javascript">

	// 허용되는 이미지 파일 형식들
	var imageExt = ["JPG","GIF","PNG","JPEG"];
	
	
	$(function () {
		
		$("#writeForm").submit(function () {
			
			//alert($("#title").val());
			//alert($("#content").val());
			
			// C:\fakepath\flower01.jpg
			//alert($("#multipartFile").val());
			
			// 첨부파일이 image파일인지 알아내는 프로그램 작성 -> 파일명의 마지막 "." 이후의 글자
			
			var fileName = $("#multipartFile").val();
			
			// substring(시작, 끝) : 끝 부분은 생략 가능 - 부분 문자열 잘라내기
			// toUpperCase() : 모든 영문자를 모두 대문자로 만들어 준다.
			// toLowerCase() : 모든 영문자를 모두 소문자로 만들어 준다.
			// lastIndexOf("찾는 문자열") : 뒤에서 부터 찾는 문자열의 위치를 알려준다. 찾는 문자열이 없으면 -1이 나온다.
			var ext = fileName.substring(fileName.lastIndexOf(".") + 1).toUpperCase();
			
			//alert(ext);
			
			// 이미지 확장자 인지 확인하는 반복문
			var checkExt = false;	// 지원하지 않는 확장자를 기본으로 세팅
			for(i = 0; i < imageExt.length; i++) {
				
				if(ext == imageExt[i]) {
					
					checkExt = true;	// 지원한는 확장자로 바꾼다.
					
					break;
					
				}
				
			}
			
			// 지원하지 않는 이미지 파일 선택한 경우의 처리
			if(!checkExt) {
			
			alert("지원하지 않는 이미지 파일 입니다.");
			
			$("#multipartFile").focus();
			
			return false;
			
		}
			
			//return false;	// submit을 취소
			
		});
		
		// 취소 버늩을 클릭하면 이전 페이지로 이동		
		$("#cancelBtn").click(function () {	// 전달할 때의 데이터 찍기
			
			history.back();
			
		});
		
	});
</script>
</head>
<body>
 <div class="container">
  <h1>이미지 등록</h1>
  <!-- 파일 첨부를 하는 입력에는 반드시 post 방식이어야 하고 enctype이어야 한다. 
  	   Input tag의 type = file이어야 한다.-->
  <form action="write" method="post" enctype="multipart/form-data" id="writeForm">
   <input name="perPageNum" value="${param.perPageNum }" type="hidden">
   <div class="form-group">
    <label for="title">제목</label>
    <input name="title" id="title" class="form-control">
   </div>
   
   <div class="form-group"> 
    <label for="content">내용</label>
    <textArea name="content" id="content" class="form-control" rows="5"></textArea>
   </div>
   
   <div class="form-group">
    <label for="multipartFile">첨부파일(jpg,jpeg,gif,png 이미지 지원)</label>
    <input name="multipartFile" id="multipartFile" class="form-control btn btn-default" type="file">
   </div>
   
   <div id="imageDiv">
    <img alt="" src="">
   </div>
   
   <button class="btn btn-default">올리기</button>
   <button type="reset" class="btn btn-default">새로 입력</button>
   <button type="button" class="btn btn-default" id="cancelBtn">취소</button>
   
  </form>
 </div>
</body>
</html>