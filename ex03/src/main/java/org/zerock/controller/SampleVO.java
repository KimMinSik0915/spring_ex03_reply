package org.zerock.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor	// 생성자를 이용한 데이터 채우기 -> SampleVO(Integer mno, String firstName, String lastName)
@NoArgsConstructor	// 기본 생성자 -> SampleVO()
public class SampleVO {

	private Integer mno;
	private String firstName, lastName;
	
}
