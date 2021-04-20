package org.zerock.board.vo;

import java.util.Date;

import lombok.Data;

@Data
public class ReplyVO {

	private long rno, no;
	
	private String content, writer, pw;
	
	private Date writeDate;
	
}
