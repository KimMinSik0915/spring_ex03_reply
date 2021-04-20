package org.zerock.board.service;

import java.util.List;

import org.zerock.board.vo.ReplyVO;

import com.webjjang.util.PageObject;

public interface ReplyService {

	// 1. 댓글 리스트 : 페이지 처리 ( 화면에 보여주는 pageNateion)
	public List<ReplyVO> list(long no, PageObject pageObject) throws Exception;
	
	// 2. 댓글 보기 생략 (List에 표시가 되므로)
	
	// 3. 댓글 쓰기
	public int write(ReplyVO vo) throws Exception;
	
	// 4. 댓글 수정
	public int update(ReplyVO vo) throws Exception;
	
	// 5. 댓글 삭제
	public int delete(ReplyVO vo) throws Exception;
	
}
