/**
 * 
 */
 
 // 브라우저의 console tab에 출력한다 - alert() 사용해도 무방
 console.log("Reply Module-----------------");
 
 // replyService 변수 선언 : Type = Object(JSON) {k:v, k:v, k:function()}
	// replyService 처리하면 결과 functio으로 나온다. 결과 뒤에 ()를 붙이면 실행이된다.
 var replyService = (
	
	function() {
		
		// list
		// param : JSON {no:2, repPage:1, repPerPageNum:5}
		// list(Json-넘겨줄 data, data를 성공적으로 가져왔을때의 실행함수, 실패했을 때의 실행 함수)
		function list(param, callback, error) {
			
			var no = param.no;
			var page = param.repPage || 1;		// repPage가 없으면 기본값으로 1을 setting
			var perPageNum = param.repPerPageNum || 5;
		 
			// Ajax를 이용한 데이터 가져오기 -> Ajax해야 합니다 == 형식에 맞으면 Ajax통신을 한다.
			// Ajax 함수의 종류 : $.getJson() : $.Ajax()를 통해서 JSON 데이터를 받아 오게 만든 함수
			$.getJSON(
			
				"/replies/list?no=" + no,		// Ajax를 호출하는 URL
				
				// success(성공) 상태 일때 처리되는 함수
				
				function(data) {	// Data 처리가 성공해서 Data를 가져오면 data로 넣어준다 : List이므로 배열이 넘어온다.
					
					// alert(data);
					
					if(callback != null) {		// Data를 가져오면 처리하는 함수 : 가져온 list를 가지고 HTML을 만들어 지정한 곳에 넣어준다.
						
						callback(data);
						
					} 
					
				}
				 
			)
			
			// error 상태일 때 처리된는 함수
			.fail(function(xhr, status, err) {		// xhr은 통신 객체
				
				// 오류 함수가 있으면 오류 함수를 실행한다.
				if(error) {
					
					error();
					
				} else {	
						
					alert(err);		// 오류 출력 ( {}, [] 구조는 사용할 수 있음 하지만 <>는 JSON데이터가 아니므로 오류가 발생한다,)
						
				}
				
			});	// end of $.getJSON().fail
		
		}	// end of list
		
		return {

			list : list,		// replyServic.list(param, callback, error)
			
			displayTime : displayTime
			
		}
		
	}
	
)();
 
 console.log(replyService);
 
 
 