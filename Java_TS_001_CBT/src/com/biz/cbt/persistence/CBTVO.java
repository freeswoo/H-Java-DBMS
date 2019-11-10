package com.biz.cbt.persistence;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class CBTVO {

	private int cb_code;		//	number
	private String cb_quiz;
	private String cb_answer;
	private List<String> cb_qnums;
}
