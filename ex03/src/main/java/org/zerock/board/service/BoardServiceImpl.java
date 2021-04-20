package org.zerock.board.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.zerock.board.mapper.BoardMapper;
import org.zerock.board.vo.BoardVO;

import com.webjjang.util.PageObject;

import lombok.extern.log4j.Log4j;

@Service
@Qualifier("bsi")
@Log4j
public class BoardServiceImpl implements BoardService {

	@Inject			
	private BoardMapper mapper;		// Mapper를 이용해서 DB처리 자동 DI적용
	
	@Override
	public List<BoardVO> list(PageObject pageObject) throws Exception {
		// TODO Auto-generated method stub
		
		// 페이지 처리를 위한 전체 데이터 가져오기(추후)
		
		pageObject.setTotalRow(mapper.getTotalRow(pageObject));	// startRow와 endRow가 계산이 된다.
		
		log.info("pageObject : " + pageObject);
		
		return mapper.list(pageObject);
		
		
	}

	@Override
	public BoardVO view(Long no, int inc) throws Exception {
		// TODO Auto-generated method stub
		
		if(inc == 1) {
			
			mapper.increase(no);
			
		}
		
		return mapper.view(no);
	}

	@Override
	public int write(BoardVO vo) throws Exception {
		// TODO Auto-generated method stub
		
		return mapper.write(vo);
		
	}

	@Override
	public int update(BoardVO vo) throws Exception {
		// TODO Auto-generated method stub
		
		return mapper.update(vo);
		
	}

	@Override
	public int delete(BoardVO vo) throws Exception {
		// TODO Auto-generated method stub
		
		return mapper.delete(vo);
		
	}

}
