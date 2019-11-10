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
public class RentBookDTO {
	
	private long rent_seq;			//	NUMBER
	private String rent_date;			//	VARCHAR2(10 BYTE)
	private String rent_return_date;	//	VARCHAR2(10 BYTE)
	private String rent_bcode;			//	VARCHAR2(6 BYTE)
	private String rent_ucode;			//	VARCHAR2(6 BYTE)
	private String rent_retur_yn;		//	VARCHAR2(1 BYTE)
	private int rent_point;			//	NUMBER
	
}
