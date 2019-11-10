package com.biz.iolist.service.dept;

import com.biz.iolist.persistence.DeptDTO;

public class DeptServiceV2 extends DeptServiceV1{
	
	public void deptMenu() {
		System.out.println("==================================");
		System.out.println("거래처 정보 관리");
		System.out.println("==================================");
		System.out.println("1.등록 2.수정 3.삭제 4.검색 0.종료");
		System.out.println("----------------------------------");
		System.out.print("업무선택 >> ");
		String strMenu = scanner.nextLine();
		try {
			int intMenu = Integer.valueOf(strMenu);
			
			/*
			 * else를 사용하지 않으면
			 * 모든 if 문을 다 검사하는 방식으로 코드가 실행되지만
			 * else를 사용하면
			 * 해당하는 코드에서 true가 나오면 이후 코드는 건너뛴다
			 */
			if(intMenu == 0) return;
			else if(intMenu == 1) {
				
			} else if(intMenu == 2) {
				
				this.viewNameList();
				
				this.deptUpdate();
				
			} else if(intMenu == 3) {
				// 상호로 검색을 해서 리스트를 보여주고
				this.viewNameList();
				
				// 보여준 리스트 중에서
				// 거래처 코드를 입력받아서 삭제
				this.deptDelete();
			} else if(intMenu == 4) {
				this.viewNameAndCeoList();
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public void deptDelete() {
		
		while(true) {
			System.out.print("삭제할 거래처 코드(-Q:quit) >> ");
			String strDCode = scanner.nextLine();
			
			if(strDCode.equals("-Q")) break;
			
			DeptDTO deptDTO = deptDao.findById(strDCode);
			if(deptDTO == null) {
				System.out.println("삭제할 거래처코드가 없음");
				continue;
			}
			this.viewDetail(deptDTO);
			
			System.out.print("정말 삭제? Enter:실행");
			String strYesNo = scanner.nextLine();
			if(strYesNo.trim().isEmpty()) {
				int ret = deptDao.delete(strDCode);
				if(ret > 0) {
					System.out.println("삭제완료");
					break;
				}
				else System.out.println("삭제실패");
			}
		}
		
		
	} // end delete
	public void deptUpdate() {
		
		System.out.print("수정할 거래처 코드 (-Q:quit) >> ");
		String strDCode = scanner.nextLine();
		
		DeptDTO deptDTO = deptDao.findById(strDCode);
		if(deptDTO == null) {
			System.out.println("거래처코드가 없음");
			return;
		}
		
		this.viewDetail(deptDTO);
		
		System.out.printf("거래처명(%s) \n", deptDTO.getD_name());
		String strDName = scanner.nextLine();
		if(strDName.trim().isEmpty()) {
			// 그냥 Enter만 눌렀으면 건너 뛰기
		} else {
			
			// 값을 입력했으면 입력한 값으로 변경하기
			deptDTO.setD_name(strDName);
		}
				
		System.out.printf("대표(%s) \n", deptDTO.getD_ceo());
		String strDCeo = scanner.nextLine();
		if(!strDCeo.trim().isEmpty())
			deptDTO.setD_ceo(strDCeo);
		
		System.out.printf("전화번호(%s) \n", deptDTO.getD_tel());
		String strDTel = scanner.nextLine();
		if(!strDTel.trim().isEmpty())
			deptDTO.setD_ceo(strDTel);
		
		System.out.printf("주소(%s) \n", deptDTO.getD_addr());
		String strDAddr = scanner.nextLine();
		if(!strDAddr.trim().isEmpty())
			deptDTO.setD_ceo(strDAddr);
		
		int ret = deptDao.update(deptDTO);
		if(ret >0) System.out.println("수정성공");
		else System.out.println("수정실패");
	
	}
	
}
