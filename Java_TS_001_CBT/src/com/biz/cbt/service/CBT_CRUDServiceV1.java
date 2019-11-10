package com.biz.cbt.service;

import com.biz.cbt.persistence.CBTDTO;

public class CBT_CRUDServiceV1 extends CBTServiceV1 {

	public void inputMenu() {
		System.out.println("=========================");
		System.out.println("1.문제등록  2.문제수정  3.문제삭제  0.종료");
		System.out.println("=========================");
		System.out.print("선택 >> ");
		String strMenu = scan.nextLine();
		try {
			int intMenu = Integer.valueOf(strMenu);
			if(intMenu == 0) return;
			else if(intMenu == 1) {
				this.insert();
			} else if(intMenu == 2) {
				this.update();
			} else if(intMenu == 3) {
				this.delete();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	private CBTDTO viewDetail(String strCode) {
		CBTDTO dto = dao.findById(strCode);
		if(dto == null)
			return null;
		System.out.println("=====================================");
		System.out.printf("문제코드 : %s\n",dto.getCb_code());
		System.out.printf("문제 : %d\n", dto.getCb_quiz());
		System.out.printf("보기1 : %d\n", dto.getCb_ex01());
		System.out.printf("보기2 : %d\n", dto.getCb_ex02());
		System.out.printf("보기3 : %d\n", dto.getCb_ex03());
		System.out.printf("보기4 : %d\n", dto.getCb_ex04());
		System.out.printf("정답 : %d\n", dto.getCb_answer());
		System.out.println("=====================================");
		return dto;
		
	}

	private void delete() {
		
		while(true) {
			System.out.println("삭제할 문제 코드를 입력하세요(-Q:quit)");
			System.out.println(" >> ");
			String strCode = scan.nextLine();
			
			if(strCode.equals("-Q")) break;
			
			CBTDTO dto = dao.findById(strCode);
			if(dto == null) {
				System.out.println("삭제할 문제 코드가 없음");
				continue;
			}
			this.viewDetail(strCode);
			
			System.out.print("삭제하시겠습니까? Enter:실행");
			String strYesNo = scan.nextLine();
			if(strYesNo.trim().isEmpty()) {
				int ret = dao.delete(strCode);
				if(ret > 0) {
					System.out.println("삭제완료!!");
					break;
				} else
					System.out.println("삭제실패!!");
			}
		}
	}

	private void update() {
		
		System.out.println("수정할 문제코드(-Q:quit) >> ");
		String strCode = scan.nextLine();
		
		CBTDTO dto = dao.findById(strCode);
		if(dto == null) {
			System.out.println("도서코드가 없음");
			return;
		}
		this.viewDetail(strCode);
		
		System.out.printf("문제(%s)\n >> ", dto.getCb_quiz());
		String strQuiz = scan.nextLine();
		if(!strQuiz.trim().isEmpty())
			dto.setCb_quiz(strQuiz);
		
		System.out.printf("보기1(%s)\n >> ", dto.getCb_ex01());
		String strEx01 = scan.nextLine();
		if(!strEx01.trim().isEmpty())
			dto.setCb_ex01(strEx01);
		
		System.out.printf("보기2(%s)\n >> ", dto.getCb_ex02());
		String strEx02 = scan.nextLine();
		if(!strEx02.trim().isEmpty())
			dto.setCb_ex02(strEx02);
		
		System.out.printf("보기3(%s)\n >> ", dto.getCb_ex03());
		String strEx03 = scan.nextLine();
		if(!strEx03.trim().isEmpty())
			dto.setCb_ex03(strEx03);
		
		System.out.printf("보기4(%s)\n >> ", dto.getCb_ex04());
		String strEx04 = scan.nextLine();
		if(!strEx04.trim().isEmpty())
			dto.setCb_ex04(strEx04);
		
		System.out.printf("정답(%s)\n >> ", dto.getCb_answer());
		String strAnswer = scan.nextLine();
		if(!strAnswer.trim().isEmpty())
			dto.setCb_answer(strAnswer);
		
		int ret = dao.update(dto);
		if(ret > 0)
			System.out.println("수정성공!!");
		else
			System.out.println("수정실패!!");
	} 

	private void insert() {
		
		CBTDTO dto = new CBTDTO();
		while(true) {
			
			System.out.println("문제코드(Enter:자동생성), (-Q:quit)");
			String strCode = scan.nextLine();
			if(strCode.equals("-Q")) break;
			if(strCode.trim().isEmpty()) {
				String strTMPCode = dao.getMaxBCode();
				int intCode = Integer.valueOf(strTMPCode);
				intCode ++;
				
				System.out.println("생성된코드 : " + intCode);
				System.out.println("사용하시겠습니까?(Enter:Yes");
				String strYesNo = scan.nextLine();
				if(strYesNo.trim().isEmpty()) {
					dto.setCb_code(intCode);
					break;
				} else continue;
			}
			if(dao.findById(strCode) != null) {
				System.out.println("이미 사용중인 코드!!");
				continue;
			}
			break;
		}
		
		while(true) {
			System.out.println("추가할 문제 입력(-Q:quit)\n >> ");
			String strQuiz = scan.nextLine();
			if(strQuiz.equals("-Q")) break;
			if(strQuiz.trim().isEmpty()) {
				System.out.println("문제는 반드시 입력해야함");
				continue;
			}
			dto.setCb_quiz(strQuiz);
			break;
		}
		
		while(true) {
			System.out.print("보기1 입력 >> " );
			String strEx01 = scan.nextLine();
			if(strEx01.equals("-Q")) break;
			if(strEx01.trim().isEmpty()) {
				System.out.println("보기는 반드시 입력해야함");
				continue;
			}
			dto.setCb_ex01(strEx01);
			break;
		}
		
		while(true) {
			System.out.print("보기2 입력 >> " );
			String strEx02 = scan.nextLine();
			if(strEx02.equals("-Q")) break;
			if(strEx02.trim().isEmpty()) {
				System.out.println("보기는 반드시 입력해야함");
				continue;
			}
			dto.setCb_ex02(strEx02);
			break;
		}
		
		while(true) {
			System.out.print("보기3 입력 >> " );
			String strEx03 = scan.nextLine();
			if(strEx03.equals("-Q")) break;
			if(strEx03.trim().isEmpty()) {
				System.out.println("보기는 반드시 입력해야함");
				continue;
			}
			dto.setCb_ex03(strEx03);
			break;
		}
		
		while(true) {
			System.out.print("보기4 입력 >> " );
			String strEx04 = scan.nextLine();
			if(strEx04.equals("-Q")) break;
			if(strEx04.trim().isEmpty()) {
				System.out.println("보기는 반드시 입력해야함");
				continue;
			}
			dto.setCb_ex04(strEx04);
			break;
		}
		
		while(true) {
			System.out.print("정답 입력 >> " );
			String strAnswer = scan.nextLine();
			if(strAnswer.equals("-Q")) break;
			if(strAnswer.trim().isEmpty()) {
				System.out.println("정답은 반드시 입력해야함");
				continue;
			}
			dto.setCb_answer(strAnswer);
			break;
		}
	}
}