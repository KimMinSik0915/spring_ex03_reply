package org.zerock.board.controller;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.board.service.ReplyService;
import org.zerock.board.vo.ReplyVO;

import com.webjjang.util.PageObject;

import lombok.extern.log4j.Log4j;

@RestController		// 자동 생성하게 해주는 Annotation : component-scan 설정 : servlet-context.xml || root-context.xml
@RequestMapping("/replies")
@Log4j
public class ReplyController {

	@Autowired
	@Qualifier("rsi")
	private ReplyService service;
	
	// 1. 게시판 리스트
	@GetMapping(value =  "/list", produces = { 
			MediaType.APPLICATION_XML_VALUE
			,MediaType.APPLICATION_JSON_UTF8_VALUE 
			})
	// @ModelAttribute : 전달 받은 변수의 값을 model에 담아서 JSP까지 보낸다 => 변수 이름으로 사용한다.
	// ResponseEntity : 실행 코드와 함께 크라이언트에게 전달할 때 사용
//	public ResponseEntity<List<ReplyVO>> list( @RequestParam(defaultValue = "1") long repPage, @RequestParam(defaultValue = "5") long repPerPageNum, long no) throws Exception {
	public ResponseEntity<Map<String, Object>> list( @RequestParam(defaultValue = "1") long repPage, @RequestParam(defaultValue = "5") long repPerPageNum, long no) throws Exception {
		
		// 리턴 객체 선언
		Map<String, Object> map = new HashMap<>();
		
		PageObject replyPageObject = new PageObject(repPage, repPerPageNum);
		
		log.info(" list() [replyPageObjcet] : " + replyPageObject);		
		
		log.info(" list() [no] : " + no);		
		
		map.put("pageObject", replyPageObject);
		
		map.put("list", service.list(no, replyPageObject));
		
		// return new ResponseEntity<>(service.list(no, replyPageObject), HttpStatus.OK);
		
		return new ResponseEntity<>(map, HttpStatus.OK);
		
	}
	
	// 2. 게시판 글 보기 : 생략(List에 내용이 모두 나온다)
	
	// 3. 게시판 등록 FORM : /board/view에 포함
	
	// 3-1 게시판 등록 처리
	@PostMapping(value = "/write", consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE }, produces = { "application/text; charset=utf-8" })
	public ResponseEntity<String> write( @RequestBody ReplyVO vo) throws Exception {
		
		log.info(vo + " write()-------------------------------");
		
		service.write(vo);
		
		return new ResponseEntity<String>("댓글이 등록되었습니다" , HttpStatus.OK);
		
	}
	
	// 4. 게시판 수정 FORM : /board/view에 포함
	
	// 4-1 게시판 수정 처리
	@PatchMapping(value = "/update", consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE }, produces = { "application/text; charset=utf-8" })
	public ResponseEntity<String> update( @RequestBody ReplyVO vo) throws Exception {
		
		
		log.info(vo + " update() -------------------------------");
		
		int result = service.update(vo);
		
		// 전달되는 데이터의 선언
		String msg= "게시판 글 수정이 성공적으로 되었습니다.";
		
		HttpStatus status = HttpStatus.OK;
		
		if(result == 0) {
			
			msg = "게시판 수정 실패 - 정보를 확인해 주새요";
			
			status = HttpStatus.NOT_MODIFIED;
			
		}
		
		log.info("update().msg : " + msg);
		
		// URL로 요청되는 경우 서버의 한글이 적용되므로 UTF-8로 encode 시켜서 보내야 한다.
		return new ResponseEntity<String>(msg, status);
		
	}
	
	// 5. 게시판 삭제
	@DeleteMapping( value = "/delete", consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE }, produces = { "application/text; charset=utf-8" } )
	public ResponseEntity<String> delete( @RequestBody ReplyVO vo) throws Exception {
		
		int result =  service.delete(vo);
		
		// 전달되는 데이터의 선언
		String msg= "댓글 삭제가 성공적으로 되었습니다.";
		
		HttpStatus status = HttpStatus.OK;
		
		if(result == 0) {
			
			msg = "댓글 삭제 실패 - 정보를 확인해 주새요";
			
			status = HttpStatus.NOT_MODIFIED;
			
		}
		
		log.info("update().msg : " + msg);
		
		// URL로 요청되는 경우 서버의 한글이 적용되므로 UTF-8로 encode 시켜서 보내야 한다.
		return new ResponseEntity<String>(msg, status);
		
		
	}
	
}
