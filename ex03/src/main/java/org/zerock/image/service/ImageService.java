package org.zerock.image.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.zerock.image.vo.ImageVO;

import com.webjjang.util.PageObject;

@Service
public interface ImageService {

	// 1. Image List
	public List<ImageVO> list(PageObject object) throws Exception;
	
	// 2. Image View
	public ImageVO view(long no) throws Exception;
	
	// 3. Image Register
	public int write(ImageVO vo) throws Exception;
	
	// 4. Image File Modify
	public int updateFile(ImageVO vo) throws Exception;

	// 5. Image Modify
	public int update(ImageVO vo) throws Exception;
	
}
