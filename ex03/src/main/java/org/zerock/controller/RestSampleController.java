package org.zerock.controller;


import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.log4j.Log4j;

// 자동생성 : @Controller, @Service, @Repository, @Component, @RestController
// @~Advice : Root-context.xml과 servlet-context.xml의 Component-Scan 설정을 확인해야 한다.
@RestController
@RequestMapping("/restsample")
@Log4j
public class RestSampleController {

	@GetMapping(value = "/getText", produces = "text/plain; charset=UTF-8")
	// 순수한 Data를 보내는 method 지정
	public String getText() {
		
		log.info("MIME TYPE : " + MediaType.TEXT_PLAIN_VALUE);
		
		return "안녕하세요~";
		
	}
	
	// VO객체 리턴
	@GetMapping(value = "/getSample", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE })	// 제공할 Data형식 선언 : JSON, XML 지원 -> 기본은 XML
	// /restsample/getSemple -> XML
	// /restsample/getSemple.json -> JSON
	// /restsample/getSample.xml -> XML
	public SampleVO getSample() {
		
		return new SampleVO(112, "스타", "로드");
		
	}
	
	@GetMapping(value = "/getSample2")	// 선언하지 않은 경우
	public SampleVO getSample2() {
		
		return new SampleVO(113, "로켓", "라쿤");
		
	}
	
	// List - 
	@GetMapping(value = "/getList")	// 선언하지 않은 경우
	public List<SampleVO> getList() {
		
		List<SampleVO> list = new ArrayList<>();
		
		for(int i = 1; i <= 10; i++) {
			
			list.add(new SampleVO(i, "first" + i, "last" + i));
			
		}
		
		return list;
		
	}
	
	// List - 
	// @GetMapping(value = "/check", params = { "height", "weight" })	// 선언하지 않은 경우
	@GetMapping(value = "/check")	// 선언하지 않은 경우
	public ResponseEntity<SampleVO> check(Double height, Double weight) {		// Data + 실행상태
		
		SampleVO vo = new SampleVO(0, "" + height, "" + weight);	// "" + height = height가 Double형식인데 문자열로 바꿔준다.
		
		ResponseEntity<SampleVO> result = null;		// Data 선언
		
		if(height < 150) {
			
			result = ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(vo);
			
		} else {
			
			result = ResponseEntity.status(HttpStatus.OK).body(vo);
			
		}
		
		return result;
		
	}
	
	// URL에 Data 포함시켜서 전달하는 방법
	// localhost/restsample/product/bag/1234
	@GetMapping(value = "/product/{cat}/{pid}")
	public String[] getPath(@PathVariable("cat") String cat, @PathVariable("pid") Integer pid) {
		
		
		
		return new String[] { "category : " + cat, "priductid : " + pid };
		
	}
	
}
