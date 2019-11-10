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
public class UserDTO {

	private String u_code;	//	VARCHAR2(6 BYTE)
	private String u_name;	//	NVARCHAR2(125 CHAR)
	private String u_tel;	//	NVARCHAR2(125 CHAR)
	private String u_addr;	//	NVARCHAR2(125 CHAR)
}
