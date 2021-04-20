package org.zerock.board.mapper;

import java.util.List;

import org.zerock.board.vo.BoardVO;

import com.webjjang.util.PageObject;

// 검색, page에 필요한 것이 있으면 pageObject를 만들어서 추가해야 한다.
public interface BoardMapper {
	
	// 1. 게시판 리스트
	public List<BoardVO> list(PageObject pageObject);
	
	// 1-1. 게시판 리스트 페이지 처리를 위한 전체 데이터 개수 가져오기
	public Long getTotalRow(PageObject pageObject);

	// 2. 게시판 글 보기
	public BoardVO view(Long no);
	
	// 2-1 조회수 1 증가
	public int increase(Long no);
	
	// 3. 게시판 글 쓰기
	public int write(BoardVO vo);
	
	// 4. 게시판 글 수정
	public int update(BoardVO vo);
	
	// 5. 게시판 글 삭제 : Long no와 String pw를 받아야 한다.
	// public int delete(Long no, String pw)로 사용해도 무방
	public int delete(BoardVO vo);
	
}
