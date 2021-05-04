package org.zerock.image.vo;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class ImageVO {

	// ------------------------ DB에 저장하기 위한 변수들 ------------------------------------------------------- //
	private long no;
	
	private String title, content, id;
	
	// File 한개 첨부
	private String fileName;	
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date writeDate;
	
	// ------------------------ 처리하기 위한 변수 ------------------------------------------------------- //
	private MultipartFile multipartFile;	// 파일 한개 첨부
	
	
}
