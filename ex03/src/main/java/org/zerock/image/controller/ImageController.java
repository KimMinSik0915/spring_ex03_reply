package org.zerock.image.controller;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.zerock.image.service.ImageService;
import org.zerock.image.vo.ImageVO;
import org.zerock.member.vo.LoginVO;

import com.webjjang.util.PageObject;
import com.webjjang.util.file.FileUtil;

import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping("/image")
@Log4j
public class ImageController {

	private final String MODUEL = "image";
	
	@Autowired
	@Qualifier("isi")
	private ImageService service;
	
	// 저장할 위치(상대위치) : 운영되는 Server에서 부터 찾는 상대 위치
	String path = "/upload/image/";
	
	// Image 갤러리 List
	@GetMapping("/list")
	public String list(@ModelAttribute PageObject pageObject, Model model, HttpSession session) throws Exception {
		
		log.info("list() [pageObjcet] : " + pageObject);
			
		if(pageObject.getPerPageNum() == 10) {
			
			pageObject.setPerPageNum(8);
			
		}
		
		LoginVO vo = new LoginVO();
		
		vo.setId("test");
		vo.setGradeNo(1);
		
		session.setAttribute("login", vo);
		
		model.addAttribute("list", service.list(pageObject));
		
		return MODUEL + "/list";	// JSP의 정보를 리턴
		
	}
	
	// 2. Image View
	@GetMapping("/view")
	public String view(@ModelAttribute PageObject pageObject, long no, Model model) throws Exception {
		
		log.info("view() [no] : " + no);
		
		model.addAttribute("vo", service.view(no));
		
		return MODUEL + "/view";		// JSP의 정보를 리턴
		
	}
	
	// 3. Image RegisterForm
	@GetMapping("write")
	public String writeForm() {
		
		log.info("writeForm().......");
		
		return MODUEL + "/write";
		
	}
	
	// 4. Image Register
	@PostMapping("write")
	public String write(ImageVO vo, HttpSession session, long perPageNum, HttpServletRequest request) throws Exception {
		
		log.info("write()......");		// 이미지 등록에서 새로고침을 하지 말고 리스트에서 새로 고침해서 test해야 한다.
		
		// 실제적으로 저장이 되는 위치(절대 위치)
		String realPath = request.getServletContext().getRealPath(path);
		
		LoginVO  loginVO = (LoginVO) session.getAttribute("login");
		
		log.info("write( [realpath] : " + realPath);
		log.info(loginVO);
		
		// 중복체크
		
		// 전달 받지 않는 data의 수집
		String fileName = vo.getMultipartFile().getOriginalFilename();
		
		File imageFile = FileUtil.noDuplicate(realPath + fileName);
		
		vo.setFileName(path + imageFile.getName());
		vo.setId(loginVO.getId());
		
		log.info("write() [vo] : " + vo);
		
		log.info("write [vo.getMultipartFile.name] : " + vo.getMultipartFile().getOriginalFilename());
		
		// 전달된 파일을 저장하는 처리
		vo.getMultipartFile().transferTo(imageFile);
		
		// DB에 정보(vo) 저장하기
		service.write(vo);
		
		return "redirect:list?perPageNum=" + perPageNum;
		
	}

	// 5. Image File Modify
	@PostMapping("updateFile")
	public String updateFile(ImageVO vo, PageObject pageObject, String deleteFile, HttpServletRequest request) throws Exception {
		
		String realPath = request.getServletContext().getRealPath(path);
		
		String deleteFileRealPath = request.getServletContext().getRealPath(deleteFile);
		
		String fileName = vo.getMultipartFile().getOriginalFilename();
		
		File imageFile = FileUtil.noDuplicate(realPath + fileName);
		
		// 넘어오는 정보 확인
		log.info("updateFile [vo] : " + vo);
		log.info("updateFile [pageObject] : " + pageObject);
		log.info("updateFile [deleteFile] : " + deleteFile);
		log.info("updateFile [deleteFileRealPath] : " + deleteFileRealPath);
		
		// 원본 File 삭제
		FileUtil.delete(deleteFileRealPath);
		
		// 전달된 File을 중복 배제 후 저장
		vo.setFileName(path + imageFile.getName());
		
		vo.getMultipartFile().transferTo(imageFile);
		
		// DB에 File 정보를 수정한다.
		service.updateFile(vo);
		
		return "redirect:view?no=" + vo.getNo()+ "&page=" + pageObject.getPage()+ "&perPageNum=" + pageObject.getPerPageNum();
		
	}
	
	// 6. Image Info Modify Form
	@GetMapping("/update")
	public String updateForm(long no, Model model) throws Exception {
		
		// DB에서 no에 맞는 정보를 가져와서 Model에 넣어준다.
		model.addAttribute("vo", service.view(no));
		
		return MODUEL + "/update";
		
	}
	
	// 7. Image Info Modify 
	@PostMapping("/update")
	public String update(ImageVO vo, PageObject pageObject) throws Exception {
		
		// DB에 이미지 정보 수정(제목, 내용) : 글 번호에 맞는 것만
		service.update(vo);
		
		// 정보가 수정이 되면 바로 Image view로 이동시켜 준다.
		return "redirect:view?no=" + vo.getNo() + "&page=" + pageObject.getPage() + "&perPageNum=" + pageObject.getPerPageNum();
		
	}
	
	
}
