package org.zerock.exception;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import lombok.extern.log4j.Log4j;

// Spring의 예외처리
// Servlet-context에 있는 component-scan에 package가 포함되어 있어야 한다.
@ControllerAdvice
@Log4j
public class CommonExceptionAdvice {

	// 어떤 예외를 잡을 것인지 선언해 놓는다. : 500번 전체
	@ExceptionHandler(Exception.class)
	public String Except(Exception ex, Model model) {
		
		log.error("Exception........" + ex.getMessage());
		
		model.addAttribute("exception", ex);
		
		log.error(model);
		
		return "error_page";
		
	}
	
	// 어떤 예외를 잡을 것인지 선언해 놓는다. : 500번 전체
	@ExceptionHandler(NoHandlerFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String handle404(NoHandlerFoundException ex) {
		
		log.error("Exception........" + ex.getMessage());
		
		return "custom404";
		
	}
	
}
