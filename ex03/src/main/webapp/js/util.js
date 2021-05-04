/**
 *  여러가 utility 함수 선언
 */
 
 // 날자객체를 문자열로 표시하는 함수 - dateToStr(date, sep) - yyyy.MM.dd
 function dateToDateStr(date, sep) {
	
	// date는 반드시 Date 객체 이어야 한다.
	
	var yy = date.getFullYear();
	var mm =  date.getMonth() + 1;	// month는 0부터 11까지 사용한다.
	var dd = date.getDate();
	
	if(!sep) {		// 연 월 일을 구분하는 문자
		
		sep = ".";
		
	}
	
	return addNumZero(yy) + sep + addNumZero(mm) + sep + addNumZero(dd);
	
}

// 날짜 객체를 시간 문자열로 구분하는 함수 - dateToTimeStr
// java 코드에서 java.util.Date 객체를 사용해야 한다.
// java 코드에서 java.sql.Date 객체를 사용하게 되면, 날짜는 있지만 시간이 없다.(날짜만 처리하게 된다)
 function dateToTimeStr(date) {
	
	// date는 반드시 Date 객체 이어야 한다.
	
	// 날짜를 찍어서 확인했을 때 숫자는 나오는데 ~~~~,000 : 시간 정보가 포함되어 있지 않다.
	// Oracle DB : SELECT TO_CHAR(writeDate, 'hh:mi:ss') writeDate FROM board_reply 찍어서 확인해 보기
	// Java 코드 확인 return service.list()
	
	var hh = date.getHours();
	var mi =  date.getMinutes();	// month는 0부터 11까지 사용한다.
	var ss = date.getSeconds();
	
	
	return addNumZero(hh) + ":" + addNumZero(mi) + ":" + addNumZero(ss);
	
}

// 숫자를 2자리로 만들어 주는 함수 : 9보다 작거나 같으면 0을 붙인다.
 function addNumZero(data) {
	
	if(data > 9) {
		
		return data;
		
	} else {
		
		return "0" + 9;
		
	}
	
}

// 날짜 data를 timestemp라는 long 타입의 긴 숫자를 받아서 날짜 계산에 의해 현재 시간 기준으로 24시간이 지났으면 날짜를 표시 그렇지 않으면 숫자 문자열을 돌려주는 함수
function displayTime(timeStemp) {

	console.log(timeStemp);
	
	// 오늘 날짜 객체 만들기
	var today = new Date();
	
	// 오늘 날짜 TimeStamp에서 비교해야 할 날짜의 TimeStamp를 빼기
	var gap = today.getTime() - timeStemp;

	console.log(gap);
	
	
	if(gap < (1000 * 60 * 60 * 24)) {		// 작성한 날자가 24시간이 지나지 않았으면 시간을, 지났으면 날짜를 문자열로 리턴한다.
		
		return dateToTimeStr(new Date(timeStemp));
		
	} else {
		
		return dateToDateStr(new Date(timeStemp), ".");
		
	}
	
}

// Ajax를 이용한 댓글이나 게시판 리스트의 pagination (li tag만 만든다.)
// data한개당 li - a - data 선택된 page는 class='active'로 지정 모든 li 태그에는 class='ajax_page'
// 넘오는 데이터는 pageObject = {"page":1, "perPageNum":5} JSON -> pageObject.page
// rufrhksms pagination의 ul tag 안에 들어갈 li tag들을 문자열로 넘겨준다.
function ajaxPage(pageObject) {
	
	var str = "";	// tag를 만들어서 저장할 변수
	
	// 1 페이지로 이동시키는 버튼
	str += "<li data-page=1 class='reply_nav_li'>";
	
	if(pageObject.page > 1) {
		
		str += "  		<a href='' onclick='return false' title='click to move first page!' data-toggle='tooltip' data-placement='top' class='move' >";
		str += "  			<i class='glyphicon glyphicon-fast-backward'></i>";
		str += "  		</a>";
		
	} else {	// page <= 1
		
		str += "  		<a href='' onclick='return false' title='no move page!'  data-toggle='tooltip' data-placement='top' >";
		str += "  			<i class='glyphicon glyphicon-fast-backward' style='color: #999';></i>";
		str += "  		</a>";
		
	}
		
	str += "</li>"
	
	// 이전 group의 페이지로 이동시키기
	
	str +=	"<li data-page=" + pageObject.startPage + " class='reply_nav_li' >";
	
	if(pageObject.startPage > 1) {
		
		str +=	"<a href='' onclick='return false' title='click to move previous page group!' data-toggle='tooltip' data-placement='top' class='move' >";
		str +=	"<i class='glyphicon glyphicon-step-backward'></i>";
		str +=	"</a>";
		
		
	} else {
		
		str += "<a href='' onclick='return false' title='no move page!'data-toggle='tooltip' data-placement='top' >";
  		str += 	"<i class='glyphicon glyphicon-step-backward' style='color: #999';></i>";
  		str += "</a>";
		
	}
	  		
  	str += "</li>";
	  		
	// startpage ~ endPage 버튼 만들기
	for( i = pageObject.startPage; i <= pageObject.endPage; i++) {
		
		str +=	 "<li data-page='" + i + "' class='reply_nav_li ";
	
		if(pageObject.page == i) {
			
			str += "active";
			
		}
		
		str += "'>'";
		
		if(pageObject.page == i) {
			
			str += "<a href='' onclick='return false' >" + i + "</a>";
			
		} else {
			
			str += "<a href='' onclick='return false' title='click to move " + i + " page' data-toggle='tooltip' data-placement='top' class='move' >" + i + "</a>";
			
		}
		
		str += "</li>";
		
	}
	  		
	  		
	// 다음 group으로 이동 : page를 endPage + 1로 이동시킨다. endpage == totalPage 이동 불가
	str += "<li data-page='" + (pageObject.endPage + 1) + "' class='reply_nav_li'>";
	
	if(pageObject.endPage < pageObject.totalPage) {	// endPage != totalPage : 이동 가능
		
		str += "<a href='' onclick='return false' title='click to move next page group!' data-toggle='tooltip' data-placement='top' class='move' >";
		str += "<i class='glyphicon glyphicon-step-forward'></i>";
		str += "</a>";
		
	} else {	// endPage == totalPage : 이동 불가
		
		str += "<a href='' onclick='return false' title='no move page!'data-toggle='tooltip' data-placement='top'>";
		str += "<i class='glyphicon glyphicon-step-forward' style='color: #999;'></i>";
		str += "</a>";
			
	}
	
	str += "</li>";
	
	// 마지막 페이지로 이동 시키기 : totalPage로 이동 시킨다. page == totalPage 이동 불가.
	str += "<li data-page=" + pageObject.totalPage + " class='reply_nav_li' >";
	  
	if(pageObject.page < pageObject.totalPage) {
		
		str += "<a href='' onclick='return false' title='click to move last page!' data-toggle='tooltip' data-placement='top' class='move' >";
		str += "<i class='glyphicon glyphicon-fast-forward'></i>";
  		str += "</a>";
		
	} else {
		
		str += "<a href='' onclick='return false' title='no move page!'data-toggle='tooltip' data-placement='top' >";
		str += "<i class='glyphicon glyphicon-fast-forward' style='color: #999;'></i>";
		str += "</a>";
		
	}
	  		
	str += "</li>";
	
	  		
	return str;
	
}