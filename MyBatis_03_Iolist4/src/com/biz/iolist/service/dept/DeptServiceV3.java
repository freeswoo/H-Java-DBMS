package com.biz.iolist.service.dept;

import java.util.List;

import com.biz.iolist.dao.DeptDao;
import com.biz.iolist.persistence.DeptDTO;

public class DeptServiceV3 extends DeptServiceV2{

	/*
	 * 키보드에서 정보를 입력받아 DB 에 추가하기
	 * 1. 거래처코드 : 자동생성
	 * 		기존 코드가 있으면 추가 금지
	 * 2. 상호는 같은 상호가 있더라도
	 * 		대표자명이 다르면 입력 가능하도록
	 */
	public void deptInsert() {
		
		String strPCode;
		while(true) {
			
			System.out.print("거래처코드(Enter:자동생성), (-Q:quit)");
			strPCode = scanner.nextLine();
			if(strPCode.equals("-Q")) break;
			if(strPCode.trim().isEmpty()) {
				String strTMPCode = deptDao.getMaxPCode();
				int intPCode = Integer.valueOf(strTMPCode.substring(1));
				intPCode ++;
				strPCode = strTMPCode.substring(0,1);
				strPCode += String.format("%04d", intPCode);
				
				System.out.println("생성된코드 : " + strPCode);
				System.out.println("사용하시겠습니까?(Enter:Yes");
				String strYesNo = scanner.nextLine();
				if(strYesNo.trim().isEmpty()) break;
				else continue;
			}
			
			if(strPCode.length() != 5) {
				System.out.println("거래처코드 길이 규칙에 맞지 않음");
				continue;
			}
			
			strPCode = strPCode.toUpperCase();
			if(strPCode.substring(0,1).equalsIgnoreCase("D")) {
				System.out.println("거래처코드는 첫 글자가 D로 시작되어야 함");
				continue;
			}
			
			try {
				Integer.valueOf(strPCode.substring(1));
			} catch (Exception e) {
				System.out.println("상품코드2번째 이후는 숫자만 가능함");
				continue;
			}
			
			if(deptDao.findById(strPCode) != null) {
				System.out.println("이미 사용중인 코드!!");
				continue;
			}
			break;
		} // end while
		if(strPCode.equals("-Q")) return;
		
		String strPName ;
		while(true) {
			System.out.print("상호명(-Q:quit) >> ");
			strPName = scanner.nextLine();
			if(strPName.equals("-Q")) break;
			if(strPName.trim().isEmpty()) {
				System.out.println("상호명은 반드시 입력해야함");
				continue;
			}
			List<DeptDTO> deptList = deptDao.findByName(strPName);
		}
		if(strPName.equals("-Q")) return;
		
		String strPCeo ;
		while(true) {
			System.out.print("대표자명(-Q:quit) >> ");
			strPCeo = scanner.nextLine();
			if(strPCeo.equals("-Q")) break;
			if(strPCeo.trim().isEmpty()) {
				System.out.println("대표자명은 반드시 입력해야함");
				continue;
			}
			
			List<DeptDTO> deptList = deptDao.findByCeo(strPCeo);
			for(DeptDTO deptDTO : deptList) {
				if(deptList != null) {
					System.out.println("===========================");
					System.out.println("같은 이름의 대표자명 있음!!");
					System.out.println("---------------------------");
					System.out.println("거래처코드 : " + deptDTO.getD_code());
					System.out.println("상호명 : " + deptDTO.getD_name());
					System.out.println("대표자명 : " + deptDTO.getD_ceo());
					System.out.println("전화 : " + deptDTO.getD_tel());
					System.out.println("주소 : " + deptDTO.getD_addr());
					System.out.println("---------------------------");
					System.out.print("사용하시겠습니까?(Enter:사용,NO:다시입력");
					String YesNo = scanner.nextLine();
					if(YesNo.trim().isEmpty()) break;
					continue;
				}
			}
			
		}
	}
	
}
