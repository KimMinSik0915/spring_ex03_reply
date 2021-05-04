package org.zerock.image.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.zerock.image.mapper.ImageMapper;
import org.zerock.image.vo.ImageVO;

import com.webjjang.util.PageObject;

@Service
@Qualifier("isi")
public class ImageServiceImpl implements ImageService {		// 실행하면 Interface Type과 ServuceImpl Type 두개가 나온다. @Qualifier를 선언해 주지 않으면 두개가 충돌을 일으켜 서버가 실행되지 않는다.

	@Inject		// 자동 DI(Dependency Inject)
	private ImageMapper mapper;
	
	// 1. Image List
	@Override
	public List<ImageVO> list(PageObject pageObject) throws Exception {
		// TODO Auto-generated method stub
		
		// pageObject에 전체 데이터 받아서 세팅 해주기
		pageObject.setTotalRow(mapper.getTotalRow(pageObject));		// 화면의 pagination 때문에 사용
		
		return mapper.list(pageObject);
		
	}
	
	// Image View
	@Override
	public ImageVO view(long no) throws Exception {
		
		return mapper.view(no);
		
	}

	// Image Register
	@Override
	public int write(ImageVO vo) throws Exception {
		// TODO Auto-generated method stub
		
		return mapper.write(vo);
		
	}

	// Image File Modify
	@Override
	public int updateFile(ImageVO vo) throws Exception {
		// TODO Auto-generated method stub
		
		return mapper.updateFile(vo);
		
	}

	// Image Info Modify
	@Override
	public int update(ImageVO vo) throws Exception {
		// TODO Auto-generated method stub
		
		return mapper.update(vo);
		
	}

}
