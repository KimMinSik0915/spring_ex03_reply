/**

* 게시판 댓글 처리에 필요한 JS(jQuery)

*/



// 브라우저의 consol tab에 출력한다. - alert() 사용하셔도 됩니다.

console.log("Reply Module ..............");



// replyService 변수 - 타입은 Object - JSON {} - {k:v, K:v, k:func()}

// replyService 처리하면 결과 function으로 나온다. 결과뒤에 ()를 붙이면 실행이된다.

var replyService = (

	function() {

		// list

		// param : JSON {no:2, repPage:1, repPerPageNum:5}

		// list(JSON-넘겨줄데이터, 데이터를 성공적으로 가져왔을때의 실행함수, 실패했을때의 실행함수)

		function list(param, callback, error) {

			var no = param.no;

			// param.repPage || 1 -> param.repPage의 값이 없으면 1을 사용

			var page = param.repPage || 1;

			// param.repPerPageNum || 5 -> param.repPerPageNum의 값이 없으면 5을 사용

			var perPageNum = param.repPerPageNum || 5;



			// Ajax를 이용한 데이터 가져오기 -> Ajax 해야합니다. 형식에 맞으면 Ajax를 한다.

			// Ajax 함수 : $.getJSON() - $.ajax()를 통해서 JSON 데이터를 받아오게 만든함수

			$.getJSON(

				// ajax로 호출하는 url

				"/replies/list.do?no=" + no,

				// success(성공) 상태일 때 처리되는 함수

				// 데이터 처리가 성공해서 데이터를 가져오면 data로 넣어준다. list이므로 배열이 넘어온다.

				function(data) {

					// alert(data);

					// callback : 데이터를 가져오면 처리하는 함수 

					// - 가져온 list를 HTML만듦. 지정한 곳에 넣어준다.

					if (callback) {

						callback(data);

					}

				}

			)

				// error(실패) 상태일 때의 처러함수

				.fail(

					function(xhr, status, err) {

						//오류 함수가 있으면 오류 함수 실행

						if (error) {

							error();

						} else {

							// 오류 출력 -{}, [] 구조 OK, <> - JSON데이터가 아니므로 오류

							alert(err);

						}

					}

				); // $.getJSON().fail()의 끝

		} // list() 의 끝



		return {

			// replyService.list(param,callback, error)

			list: list,

			// util.js 파일에서 displyTime 함수를 찾는다. 앞에 있어야 한다.

			displyTime: displyTime

		}

	}



)();

console.log(replyService);