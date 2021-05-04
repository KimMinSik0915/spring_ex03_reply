package org.zerock.util.interceptor;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.zerock.member.vo.LoginVO;

import lombok.extern.log4j.Log4j;

@Log4j
public class AuthorityInterceptor extends HandlerInterceptorAdapter {

	// url에 대한 권한 정보를 저장하는 MAP
	// 서버가 로딩될 때 정해져야 한다. Init.init()에서 처리를 해 준다.
	private static Map<String, Integer> authMap = new HashMap<>();	//Map<url, gradeNo>
	
	// class 안에서만 사용하는 변수
	private String url = null;
	
	// 페이지에 대한 등급 정보 저장하는 method
	{		// 데이터를 넣는방법 : AuthorityFilter.put(url, gradeNo)
		
		// 공지사항 - 등록, 수정, 삭제 = 관리자(9등급)
		authMap.put("/notice/write", 9);
		authMap.put("/notice/update", 9);
		authMap.put("/notice/delete", 9);
		 
		// message : list, view, write, delete = 1
		authMap.put("/message/list", 1);
		authMap.put("/message/view", 1);
		authMap.put("/message/write", 1);
		authMap.put("/message/delete", 1);
		authMap.put("/ajax/getMessageCnt", 1);
		
		// qna
//		authMap.put("/qna/list.do", 1);
		authMap.put("/qna/view", 1);
		authMap.put("/qna/question", 1);
		authMap.put("/qna/answer", 1);
		authMap.put("/qna/update", 1);
		authMap.put("/qna/delete", 1);
		
		// image : 등록 , 수정, 삭제 : 회원(1)
		authMap.put("/image/write", 1);
		authMap.put("/image/updateFile", 1);
		authMap.put("/image/update", 1);
		authMap.put("/image/delete", 1);
		
	}
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		log.info("+--[AuthorityInterceptor]-----------------------------------------------------------------+");
		
		url = request.getServletPath();
		
		log.info("+-- Authority.doFilter.url : " + url);
		
		// 로그인 객체 꺼내기
		// 로그인 정보는 session에 있다. session이 안보인다. => request에서 꺼낼 수 있다.
		HttpSession session = request.getSession();
		
		LoginVO vo = (LoginVO)session.getAttribute("login");
		
		// 새로운 메시지 갯수 처리 중 로그인이 되어 있지 않는 경우 바로 로그인 페이지로 이동 시킨다
		if(url.equals("/ajax/getMessageCnt.do") && vo == null) {		
			
			response.sendRedirect("/member/login.do");
			
			return false;
			
		}
		
		// 권한이 없는 경우의 처리
		if(!checkAuthority(vo)) {	// 브라우저에서 페이지 이동 없이 가만히 놔둔 상태로 일정 시간이 지나면 session이 사라진다. 혹은 Server가 restart 되면 session이 사라진다.
			
			// 오류 페이지로 이동시킨다
			response.sendRedirect(request.getContextPath() + "/error/auth_error.do");
			
			// 호출한 쪽으로 되돌아 간다. : 없으면 계속 아래로 실행이 된다.
			return false;
			
		}
		
		return super.preHandle(request, response, handler);		// 요청한 내용을 계속해서 진행을 시킨다.
		
	}	// end of preHandle
	
	private boolean checkAuthority(LoginVO vo) {
		
		// url 정보가 authMap에 있는지 확인한다.
		// 데이터가 없으면(null) 권한체크가 필요 없는 페이지 요청
		
		Integer pageGradeNo = authMap.get(url);

		if(pageGradeNo == null) {
			
			log.info("+-- AuthorityFilter.checkAuthority() : 권한이 필요없는 페이지 입니다.");
			return true;
			
		}
		
		if(vo == null) {
			
			log.info("+-- AuthorityFilter.doFilter)_: 로그인이 필요합니다.");
			return false;
			
		}
		
		// 여기서 부터는 로그인이 필요한 처리 입니다.(VO != null)
		log.info("+-- AuthorityFilter.checkAuthority().pageGradeNo : " + pageGradeNo);
		log.info("+-- AuthorityFilter.checkAuthority().userGradeNo : " + vo.getGradeNo());
	
		// 권한이 없는 페이지 요청에 대한 처리
		if(pageGradeNo > vo.getGradeNo()) { 
			
			log.info("+-- AuthorityFilter.checkAuthority() : 권한이 없습니다.");
			return false;
			
		}
		
		log.info("+-- AuthorityFilter.checkAuthority() : 권한이 있습니다.");
		return true;
		
	}
	
}
