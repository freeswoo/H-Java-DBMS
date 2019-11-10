package com.biz.addr.exec;

import com.biz.addr.service.AddrCUDServiceV1;
import com.biz.addr.service.AddrServiceV1;

public class AddrUpdateEx_01 {

	public static void main(String[] args) {
		
		AddrServiceV1 as = new AddrServiceV1();
		AddrCUDServiceV1 aC = new AddrCUDServiceV1();
		
		String strName = as.searchAddrName();
		if(strName.equals("-Q")) {
			System.out.println("주소록 변경 종료");
		} else {
			aC.updateAddr();
			as.searchAddrName();
		}

	}

}
