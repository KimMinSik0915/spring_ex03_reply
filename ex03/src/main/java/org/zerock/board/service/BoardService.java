package org.zerock.board.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.zerock.board.vo.BoardVO;

import com.webjjang.util.PageObject;

@Service
public interface BoardService {

	// 1. 게시판 리스트
	public List<BoardVO> list(PageObject pageObject) throws Exception;	// 전체 데이터 갯수 + List Data
	
	// 2. 게시판 글 보기
	public BoardVO view(Long no, int inc) throws Exception;	// 조회수 1증가 -> List -> view = + 1 || update -> view = 그대로
	
	// 3. 게시판 글 쓰기
	public int write(BoardVO vo) throws Exception;
	
	// 4. 게시판 글 수정
	public int update(BoardVO vo) throws Exception;
	
	// 5. 게시판 글 삭제
	public int delete(BoardVO vo) throws Exception;
	
}
