package org.zerock.board.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.log4j.Log4j;

@Controller
@Log4j
public class UploadController {

	@GetMapping("/uploadForm")
	public void uploadForm() {
		
		log.info("uploadForm()....");
		
	}
	
	@PostMapping("/uploadFormAction")
	public void uploadFormAction(MultipartFile[] uploadFile, Model model, HttpServletRequest request) throws IllegalStateException, IOException {
		
		// request.getServletContext.realpath() -> DispatcherServlet에서 ServletContext는 주지 않는다. -> request를 받아서 쓴다.
		log.info(request);
		
		// 저장할 상대 위치 정하기 : /webapp/upload. -> servletContext.xml에서 허용되어진 폴더 이어야 한다.
		// 실제적으로 폴더를 만들어 파일 하나를 만들어 놓아야 한다. : 자원이 없으면 폴더를 만들지 않는 경우가 있다.
		String path = "/upload";
		
		// 실제로 저장할 절대 위치 잡기
		String realpath = request.getServletContext().getRealPath(path);
		
		log.info("실제적인 저장위치 : " + realpath);
		
		for(MultipartFile mf : uploadFile) {
			
			log.info("----------------------------------------");
			
			log.info("uploadFileName : " + mf.getOriginalFilename());
			
			log.info("uploadFileSize : " + mf.getSize());
			
			// 저장할 File 객체 생성하기
			File saveFile = new File(realpath, mf.getOriginalFilename());

			if(saveFile.exists()) {
				
				log.info("저장할 File이 존재합니다.");
				
			}
			
			// File을 저장한다
			mf.transferTo(saveFile);
			
		}
		
		//JSP에 첨부파일을 보낸다.
		model.addAttribute("uploadFile", uploadFile);
	}
	
}
