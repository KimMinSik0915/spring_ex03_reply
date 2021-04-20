<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="pageObject" tagdir="/WEB-INF/tags" %>
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

	.dataRow:hover {
	
		background: #eee;
		cursor: pointer;
	
	}

</style>
<script type="text/javascript">

	$(function () {
		
		$(".dataRow").click(function() {
			
			var no = $(this).find(".no").text();
			
			// 페이지 정보 붙이기
			var query = ${(empty pageObject)?"''":"'&page=" += pageObject.page +="&perPageNum=" += pageObject.perPageNum += "'"}
			
			// 검색정보 붙이기
			query +=  ${(empty pageObject.word)?"''":"'&key=" += pageObject.key +="&word=" += pageObject.word += "'"}
			
			location = "view?no=" + no + "&inc=1" + query;
			
		});
		
		${(empty msg)?"":"alert('" += msg += "')"}
		
	});

</script>
<title>List</title>
</head>
<body class="container">
 <h1 class="row">게시판 리스트</h1>
  <form>
   <input name="page" value="${pageObject.page }" type="hidden">
   <input name="perPageNum" value="${pageObject.perPageNum }" type="hidden">
   <div class="input-group">
    <span class="input-group-addon">
     <select name="key">
      <option value="t" ${(pageObject.key == "t")?"selected":"" } >제목</option>
      <option value="c" ${(pageObject.key == "c")?"selected":"" } >내용</option>
      <option value="w" ${(pageObject.key == "w")?"selected":"" } >작성자</option>
      <option value="tc" ${(pageObject.key == "tc")?"selected":"" } >제목/내용</option>
      <option value="tw" ${(pageObject.key == "tw")?"selected":"" } >제목/작성자</option>
      <option value="cw" ${(pageObject.key == "cw")?"selected":"" } >내용/작성자</option>
      <option value="twc"${(pageObject.key == "twc")?"selected":"" } >모두</option>
     </select>
    </span>
    <input type="text" class="form-control" placeholder="Search" name="word" value="${pageObject.word }">
    <div class="input-group-btn">
     <button class="btn btn-default" type="submit"><i class="glyphicon glyphicon-search"></i></button>
    </div>
   </div>
  </form>
 <ul class="list-group">
  <!-- Data가 존재하지 않는 경우 -->
  <c:if test="${empty list }">
   <li class="list-group-item">데이터가 존재하지 않습니다.</li>
  </c:if>
  <c:if test="${!empty list }">
   <c:forEach items="${list }" var="vo">
    <li class="list-group-item dataRow">
     <div>
     <span class="no">${vo.no }</span>. ${vo.title }
     </div>
     ${vo.writer } (<fmt:formatDate value="${vo.writeDate }" pattern="yyyy.MM.dd hh:mm"/>) <span class="badge">${vo.hit }</span>
     </li>
   </c:forEach>
  </c:if>
 </ul>
 <!-- a tag, js : loaction, 주소입력 : get 방식으로 넘어간다 || post방식은 꼭 지정을 해야 한다 -->
 <a href="write?perPageNum=${pageObject.perPageNum }">글 쓰기</a>
 <div>
  <pageObject:pageNav listURI="list.do" pageObject="${pageObject }" query="&key=${pageObject.key }&word=${pageObjcet.word }"/>
 </div>
</body>
</html>