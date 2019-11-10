package com.biz.rent.persistence;

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
public class BookDTO {

	private String b_code;		//	VARCHAR2(6 BYTE)
	private String b_name;		//	NVARCHAR2(125 CHAR)
	private String b_auther;	//	NVARCHAR2(125 CHAR)
	private String b_comp;		//	NVARCHAR2(125 CHAR)
	private int b_year;		//	NUMBER
	private int b_iprice;	//	NUMBER
	private int b_rprice;	//	NUMBER
}
