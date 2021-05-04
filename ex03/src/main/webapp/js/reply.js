/**
 *  게시판 댓글 처리에 필요한 JS(jQuery)
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
			
				"/replies/list?no=" + no + "&repPage=" + page + "&repPerPageNum=" + perPageNum,		// Ajax를 호출하는 URL
				
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
		
		// write()==========================================================================================
		function write(reply, callback, error) {		// 책에서는 add()
			
			console.log("reply write() --------------------------------------------------------");
			console.log("reply data : " + JSON.stringify(reply));
			
			// Ajax를 이용해서 Data를 Server에 보내준다.
			$.ajax({
				
				type : "post",		// 전송 방법의 Type : get, post, put, patch
				
				url : "/replies/write",	// 요청 URL
				
				data : JSON.stringify(reply),		// 넘어오는 data를 k = v로 바꿔주는 함수
				
				contentType : "application/json; charset=UTF-8",	// 전송되는 data의 type과 encoding type
				
				success : function(result, status, xhr) {		// 정상적으로 댓글 쓰기 성공했을 때의 처리함수.
					
					if(callback) {
						
						callback(result);
						
					} else {
						
						alert("댓글 쓰기 성공");
						
					}
					
				},
				
				error : function() {		// 처리도중 오류(실패)가 발생한 경우 처리하는 함수
					
					if(error) {
						
						error(xhr, status, err);
						
					} else {
						
						alert("댓글 쓰기 중 오류가 발생하였습니다.");
						
					}
					
				}
				
			});	// end of $.ajax()
			
			
			
		}	// end of write
		
		// update() ========================================================================================
		function update(reply, callback, error) {
			
			console.log("reply update() --------------------------------------------------------");
			
			$.ajax({
				
				type : 'patch',	// replyController와 맞추어 주어야 한다.
				url : "/replies/update",
				data : JSON.stringify(reply),
				contentType : "application/json; charset=UTF-8",
				success : function(result, status, xhr) {
					
					if(callback) {
						
						callback(result, status);
						
					} else {
						
						alert("수정에 성공하여습니다 - 새로 고침 하세요");
						
					}
					
				}, error : function(xhr, status, err) {
					
					if(error) {
						
						error(err, status);
						
					} else {
						
						alert(err);
						
					}
					
				}
				
			});
			
		}	// end of update
		
		// deleteReply() ==========================================================================================		delete가 예약어 이므로 변수나 함수로 사용할 수 없다.
		function deleteReply(reply, callback, error) {
			
			console.log("reply deleteReply() --------------------------------------------------------");
			
			$.ajax({
				
				type : "delete",
				url : "/replies/delete",
				data : JSON.stringify(reply),
				contentType : "application/json; charset=UTF-8",
				success : function(result, status, xhr) {
					
					if(callback) {
						
						callback(result, status);
						
					} else {
						
						alert("댓글 삭제 완료 - 새로고침 하세요");
						
					}
					
				}, error : function(xhr, status, err) {
					
					if(error) {
						
						error(err);
						
					} else {
						
						alert(err);
						
					}
					
				}
				
			});			
			
		}	// end of deleteReply
		
		
		return {

			list : list,		// replyServic.list(param, callback, error)
			write : write,
			update : update,
			delete : deleteReply,
			displayTime : displayTime		// util.js 파일에서 displayTime 함수를 찾는다. reply.js보다 앞에 있어야 찾을 수 있다.
			
		}
		
	}
	
)();
 
 console.log(replyService);
 
 
 