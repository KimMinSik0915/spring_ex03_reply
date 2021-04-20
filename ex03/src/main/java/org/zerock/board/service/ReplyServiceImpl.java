package org.zerock.board.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.zerock.board.mapper.ReplyMapper;
import org.zerock.board.vo.ReplyVO;

import com.webjjang.util.PageObject;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

// 자동생성 : @Controller, @Service, @Repository, @Component, @RestController
// @~Advice : root-context.XML, servlet-context.XML 설정
@Service
@AllArgsConstructor		// private 변수에 대한 자동 DI : 생성자
@Log4j
@Qualifier("rsi")
public class ReplyServiceImpl implements ReplyService {

	private ReplyMapper mapper;
	
	@Override
	public List<ReplyVO> list(long no, PageObject pageObject) throws Exception {
		// TODO Auto-generated method stub
		
		log.info("list() [pageObjcet] : " + pageObject);
		
		log.info("list() [no] : " + no);
		
		// Mybatis에서 parameter로 한개만 받으므로 하나로 묶어준다.
		Map<String, Object> map = new HashMap<>();
		
		map.put("pageObject", pageObject);
		map.put("no", no);
		
		return mapper.list(map);
		
	}

	@Override
	public int write(ReplyVO vo) throws Exception {
		// TODO Auto-generated method stub
		
		log.info("write() [vo] : " + vo);
		
		return mapper.write(vo);
	}

	@Override
	public int update(ReplyVO vo) throws Exception {
		// TODO Auto-generated method stub
		
		log.info("write() [vo] : " + vo);
		
		return mapper.update(vo);

	}

	@Override
	public int delete(ReplyVO vo) throws Exception {
		// TODO Auto-generated method stub
		
		log.info("write() [vo] : " + vo);
		
		return mapper.delete(vo);
		
	}

}
