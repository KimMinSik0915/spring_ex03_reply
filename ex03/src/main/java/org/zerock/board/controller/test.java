package org.zerock.board.controller;

import java.net.URLEncoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.board.service.BoardService;
import org.zerock.board.vo.BoardVO;

import com.webjjang.util.PageObject;

import lombok.extern.log4j.Log4j;
// 자동 생성 - @Controller, @Service, @Repository, @Component, @RestController, 
// @~~Advice -> component-scan 설정 : servelet-context.xml, root-contenxt.xml
@Controller
@RequestMapping("/board")
@Log4j
public class test {
   
   private final String MODULE = "board";
   
   // 자동 DI
   @Autowired
   @Qualifier("bsi")
   private BoardService service;
   
//   1. 게시판 리스트 - 검색 /list.do - get
   @GetMapping("/list.do")
   // @ModelAttribute - 전달 받은 변수의 값을 model에 담아서 JSP까지 보내준다. 변수 이름으로 사용(대소문자 구문 pageObject) 
   public String list(Model model,@ModelAttribute PageObject pageObject) throws Exception {
      log.info("list().pageObject :" + pageObject + "........");
//      log.info("list()................");
      model.addAttribute("list", service.list(pageObject));
      return MODULE + "/list";
   }
   
//   2. 게시판 글보기 /view.do - get
   @GetMapping("/view.do")
   public String view(Model model, Long no, int inc,
         @ModelAttribute PageObject pageObject) throws Exception {
      
      model.addAttribute("vo", service.view(no, inc));
      
      return MODULE + "/view";
   }
   
//   3. 게시판 등록 폼/ write.do - get
   @GetMapping("/write.do")
   public String writeForm() {
      return MODULE + "/write";
   }
   
//   3-1. 게시판 등록 처리 /write.do - post
   @PostMapping("/write.do")
   public String write(BoardVO vo, int perPageNum, RedirectAttributes rttr) throws Exception {
      log.info("write().vo : " + vo);
      
      // db에 데이터 저장하기
      service.write(vo);
      
      rttr.addFlashAttribute("msg", "게시판 글등록이 성공적으로 되었습니다");
      
      return "redirect:list.do?perPageNum=" + perPageNum;
   }
   
//   4. 게시판 글수정 폼 / update.do - get
   @GetMapping("/update.do")
   public String updateForm(Model model,Long no) throws Exception {
      log.info("updateForm().no :" + no);
      
      // jsp에 수정할 데이터를 보내야 한다. 데이터가 DB에 있다. view()
      model.addAttribute("vo", service.view(no, 0));
      
      return MODULE + "/update";
   }
   
//   4. 게시판 글수정 처리 / update.do - post
   @PostMapping("/update.do")
   public String update(BoardVO vo, RedirectAttributes rttr,
         PageObject pageObject) throws Exception {
      
      log.info("update().vo : " + vo);
//      service.update(vo);
      
      int result = service.update(vo);
      
      if(result == 0) throw new Exception("게시판 수정 실패 - 정보를 확인해 주세요");      
      
      log.info("update().result : " + result);
      
      rttr.addFlashAttribute("msg", "게시판 글수정이 성공적으로 되었습니다.");
      
      return "redirect:view.do?no=" + vo.getNo() + "&inc=0"
      + "&page=" + pageObject.getPage() 
      + "&perPageNum=" + pageObject.getPerPageNum()
      + "&key=" + pageObject.getKey()
      // url로 요청되는 경우 서버의 한글이 적용되므로 utf-8로 encode 시켜서 보낸다.
      + "&word=" + URLEncoder.encode(pageObject.getWord(), "utf-8");
      }
   
//   5. 게시판 글삭제 처리 / delete.do - get
   // 비밀번호가 들어가있기 때문에 get방식에서 post방식으로
   @PostMapping("/delete.do")
   public String delete(BoardVO vo, int perPageNum, RedirectAttributes rttr) throws Exception {
      
      log.info("delete().vo : " + vo);
      
      int result = service.delete(vo);
      // result가 0이면 비밀번호가 틀림.
      if(result == 0) throw new Exception("게시판 글삭제 실패 - 비밀번호를 확인해 주세요");
      
      rttr.addFlashAttribute("msg", "게시판 글삭제가 성공적으로 되었습니다.");
      return "redirect:list.do?perPageNum=" + perPageNum;
      }
   

}