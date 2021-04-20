package org.zerock.board.controller;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.board.service.BoardService;
import org.zerock.board.vo.BoardVO;
import org.zerock.calendar.datedata.DateData;

import com.webjjang.util.PageObject;

import lombok.extern.log4j.Log4j;

@Controller		// 자동 생성하게 해주는 Annotation : component-scan 설정 : servlet-context.xml || root-context.xml
@RequestMapping("/board")
@Log4j
public class BoardController {

	@Autowired
	@Qualifier("bsi")
	private BoardService service;
	
	private final String MODUEL = "board";
	
	// 1. 게시판 리스트
	@GetMapping("/list")
	// @ModelAttribute : 전달 받은 변수의 값을 model에 담아서 JSP까지 보낸다 => 변수 이름으로 사용한다.
	public String list(Model model, @ModelAttribute PageObject pageObject) throws Exception {
		
		log.info(MODUEL + " list()....");		
		
		log.info(pageObject + " list()....");		
		
		model.addAttribute("list", service.list(pageObject));
		
		return MODUEL + "/list";
		
	}
	
	// 2. 게시판 글 보기
	@GetMapping("/view")
	public String view(Model model, long no, int inc,  @ModelAttribute PageObject pageObject) throws Exception {	// 처리된 Data를 JSP에 전달 || no & inc = 숫자 Type : 원래는 String Type으로 Data 전달, 없으면 null => null을 숫자로 변환하는 과정에서 오류가 발생
		
		log.info(MODUEL + " view -------------------------------");
		
		model.addAttribute("vo", service.view(no, inc));
		
		return MODUEL + "/view";
		
	}
	
	// 3. 게시판 등록 FORM
	@GetMapping("/write")
	public String writeForm() throws Exception {
		
		log.info(MODUEL + " writeForm() -------------------------------");
		
		return MODUEL + "/write";
		
	}
	
	// 3-1 게시판 등록 처리
	@PostMapping("/write")
	public String write(BoardVO vo, RedirectAttributes rttr, int perPageNum) throws Exception {
		
		log.info(MODUEL + " write() -------------------------------");
		
		log.info(vo + " write()-------------------------------");
		
		service.write(vo);
		
		rttr.addFlashAttribute("msg", "게시판 글 등록이 완료되었습니다.");
		
		return "redirect:list.do?perPageNum=" + perPageNum;
		
	}
	
	// 4. 게시판 수정 FORM
	@GetMapping("/update")
	public String updateForm(Model model, Long no) throws Exception {
		
		log.info(MODUEL + " updateForm() -------------------------------");
		
		log.info(no + " updateForm() -------------------------------");
		
		model.addAttribute("vo", service.view(no, 0));
		
		return MODUEL + "/update";
		
	}
	
	// 4-1 게시판 수정 처리
	@PostMapping("/update")
	public String update(BoardVO vo, RedirectAttributes rttr, @ModelAttribute PageObject pageObject) throws Exception {
		
		log.info(MODUEL + " update() -------------------------------");
		
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
	@PostMapping("/delete")
	public String delete(BoardVO vo, RedirectAttributes rttr, int perPageNum) throws Exception {
		
		log.info(MODUEL + " delete() -------------------------------");
		
		int result =  service.delete(vo);
		
		if(result == 0) {
			
			rttr.addFlashAttribute("msg", "비밀번호가 맞지 않습니다. 비밀번호를 확인해 주세요");
			
			return  "redirect:view.do?no=" + vo.getNo() + "&inc=0";
			
		} else {
			
			rttr.addFlashAttribute("msg", "글 삭제가 완료되었습니다.");
			
			return "redirect:list.do?perPageNum=" + perPageNum;
		}
		
		
	}
	
	
	@RequestMapping(value = "calendar.do", method = RequestMethod.GET)
	public String calendar(Model model, HttpServletRequest request, DateData dateData){
		
		Calendar cal = Calendar.getInstance();
		DateData calendarData;
		//검색 날짜
		if(dateData.getDate().equals("")&&dateData.getMonth().equals("")){
			dateData = new DateData(String.valueOf(cal.get(Calendar.YEAR)),String.valueOf(cal.get(Calendar.MONTH)),String.valueOf(cal.get(Calendar.DATE)),null);
		}
		//검색 날짜 end

		Map<String, Integer> today_info =  dateData.today_info(dateData);
		List<DateData> dateList = new ArrayList<>();
		
		//실질적인 달력 데이터 리스트에 데이터 삽입 시작.
		//일단 시작 인덱스까지 아무것도 없는 데이터 삽입
		for(int i=1; i<today_info.get("start"); i++){
			calendarData= new DateData(null, null, null, null);
			dateList.add(calendarData);
		}
		
		//날짜 삽입
		for (int i = today_info.get("startDay"); i <= today_info.get("endDay"); i++) {
			if(i==today_info.get("today")){
				calendarData= new DateData(String.valueOf(dateData.getYear()), String.valueOf(dateData.getMonth()), String.valueOf(i), "today");
			}else{
				calendarData= new DateData(String.valueOf(dateData.getYear()), String.valueOf(dateData.getMonth()), String.valueOf(i), "normal_date");
			}
			dateList.add(calendarData);
		}

		//달력 빈곳 빈 데이터로 삽입
		int index = 7-dateList.size()%7;
		
		if(dateList.size()%7!=0){
			
			for (int i = 0; i < index; i++) {
				calendarData= new DateData(null, null, null, null);
				dateList.add(calendarData);
			}
		}
		System.out.println(dateList);
		
		//배열에 담음
		model.addAttribute("dateList", dateList);		//날짜 데이터 배열
		model.addAttribute("today_info", today_info);
		return MODUEL + "/calendar";
	}
	
	
}
