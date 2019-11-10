package com.biz.cbt.persistence;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class CBTDTO {

	private int cb_code;		//	number
	private String cb_quiz;		//	nvarchar2(125 char)
	private String cb_ex01;		//	nvarchar2(125 char)
	private String cb_ex02;		//	nvarchar2(125 char)
	private String cb_ex03;		//	nvarchar2(125 char)
	private String cb_ex04;		//	nvarchar2(125 char)
	private String cb_answer;	//	nvarchar2(125 char)
	
}
