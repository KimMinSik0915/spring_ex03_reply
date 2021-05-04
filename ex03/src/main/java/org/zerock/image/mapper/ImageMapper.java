package org.zerock.image.mapper;

import java.util.List;

import org.zerock.image.vo.ImageVO;

import com.webjjang.util.PageObject;

public interface ImageMapper {

	// 1. Image List
	public List<ImageVO> list(PageObject pageObject);
	
	// 1-1. 전체 데이터의 개수 가져오기 : 화면 표시용 : pagination을 사용할 수 있다.
	public long getTotalRow(PageObject pageObject);
	
	// 2. Image View
	public ImageVO view(long no);	
	
	// 3. Image Register
	public int write(ImageVO vo);
	
	// 4. Image File Modify
	public int updateFile(ImageVO vo);

	// 5. Image Info Modify : title, content 수정 할 수 있음.
	public int update(ImageVO vo);
	
}
