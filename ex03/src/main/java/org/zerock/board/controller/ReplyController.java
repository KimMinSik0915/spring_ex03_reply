package org.zerock.board.controller;

import java.net.URLEncoder;
import java.util.List;

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
	public ResponseEntity<List<ReplyVO>> list( @RequestParam(defaultValue = "1") long repPage, @RequestParam(defaultValue = "5") long repPerPageNum, long no) throws Exception {
		
		PageObject replyPageObject = new PageObject(repPage, repPerPageNum);
		
		log.info(" list() [replyPageObjcet] : " + replyPageObject);		
		
		return new ResponseEntity<>(service.list(no, replyPageObject), HttpStatus.OK);
		
	}
	
	// 2. 게시판 글 보기 : 생략(List에 내용이 모두 나온다)
	
	// 3. 게시판 등록 FORM : /board/view에 포함
	
	// 3-1 게시판 등록 처리
	@PostMapping("/write")
	public String write(ReplyVO vo, RedirectAttributes rttr, int perPageNum) throws Exception {
		
		log.info(vo + " write()-------------------------------");
		
		service.write(vo);
		
		rttr.addFlashAttribute("msg", "게시판 글 등록이 완료되었습니다.");
		
		return "redirect:list.do?perPageNum=" + perPageNum;
		
	}
	
	// 4. 게시판 수정 FORM : /board/view에 포함
	
	// 4-1 게시판 수정 처리
	@PatchMapping("/update")
	public String update(ReplyVO vo, RedirectAttributes rttr, @ModelAttribute PageObject pageObject) throws Exception {
		
		
		log.info(vo + " update() -------------------------------");
		
		int result = service.update(vo);
		
		if(result == 0) {
			
			rttr.addFlashAttribute("msg", "비밀번호가 맞지 않습니다. 비밀번호를 확인해 주세요");
			
		} else {
			
			rttr.addFlashAttribute("msg", "글 수정이 완료되었습니다.");
			
		}
		
		log.info(result + " update() -------------------------------");
		
		// URL로 요청되는 경우 서버의 한글이 적용되므로 UTF-8로 encode 시켜서 보내야 한다.
		return  "redirect:view.do?no=" + vo.getNo() + "&inc=0&page=" + pageObject.getPage() + "&perPageNum=" + pageObject.getPerPageNum() + "&key=" + pageObject.getKey() + "&word=" + URLEncoder.encode(pageObject.getWord(), "UTF-8") ;
		
	}
	
	// 5. 게시판 삭제
	@DeleteMapping("/delete")
	public String delete(ReplyVO vo, RedirectAttributes rttr, int perPageNum) throws Exception {
		
		int result =  service.delete(vo);
		
		if(result == 0) {
			
			rttr.addFlashAttribute("msg", "비밀번호가 맞지 않습니다. 비밀번호를 확인해 주세요");
			
			return  "redirect:view.do?no=" + vo.getNo() + "&inc=0";
			
		} else {
			
			rttr.addFlashAttribute("msg", "글 삭제가 완료되었습니다.");
			
			return "redirect:list.do?perPageNum=" + perPageNum;
		}
		
		
	}
	
}
