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
 function dateToTimeStr(date, sep) {
	
	// date는 반드시 Date 객체 이어야 한다.
	
	var hh = date.getHours();
	var mi =  date.getMinutes() + 1;	// month는 0부터 11까지 사용한다.
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
	
	// 오늘 날짜 객체 만들기
	var today = new Date();
	
	// 오늘 날짜 TimeStamp에서 비교해야 할 날짜의 TimeStamp를 빼기
	var gap = today.getTime - timeStemp;
	
	if(gap < (1000 * 60 * 60 * 24)) {		// 작성한 날자가 24시간이 지나지 않았으면 시간을, 지났으면 날짜를 문자열로 리턴한다.
		
		return dateToTimeStr(new Date(timeStemp));
		
	} else {
		
		return dateToDateStr(new Date(timeStemp), ".");
		
	}
	
}
 