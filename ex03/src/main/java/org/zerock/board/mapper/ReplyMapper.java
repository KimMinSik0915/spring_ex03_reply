package org.zerock.board.mapper;

import java.util.List;
import java.util.Map;

import org.zerock.board.vo.ReplyVO;

public interface ReplyMapper {

	// 1. 댓글 리스트 : 페이지 처리 ( 화면에 보여주는 pageNateion) : Mybatis의 method들은 parameter의 개수를 1개로 제한
	// list(long no, PageObjcet pageObjcet)
	// Map - "pageObjcet" : pageObjcet, "no":no
	public List<ReplyVO> list(Map<String, Object> map) throws Exception;
	
	// 1-1 댓글 전체 데이터 가져오기 : Page 처리르 위해서
	public long getTotalRow() throws Exception;
	
	// 2. 댓글 보기 생략 (List에 표시가 되므로)
	
	// 3. 댓글 쓰기
	public int write(ReplyVO vo) throws Exception;
	
	// 4. 댓글 수정
	public int update(ReplyVO vo) throws Exception;
	
	// 5. 댓글 삭제
	public int delete(ReplyVO vo) throws Exception;
	
}
