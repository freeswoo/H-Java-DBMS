package com.biz.cbt.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import com.biz.cbt.config.DBConnection;
import com.biz.cbt.dao.CBTDao;
import com.biz.cbt.persistence.CBTDTO;
import com.biz.cbt.persistence.CBTVO;

public class CBTServiceV1 {

	protected CBTDao dao;
	protected Scanner scan;
	protected int Point = 0;
	CBT_CRUDServiceV1 ccs = new CBT_CRUDServiceV1();
	
	{
		dao = DBConnection
				.getSqlSessionFactory()
				.openSession(true)
				.getMapper(CBTDao.class);
		scan = new Scanner(System.in);
		List<CBTVO> oList = new ArrayList<CBTVO>();
		List<CBTVO> xList = new ArrayList<CBTVO>();
	}
	
	public void Menu() {
		System.out.println("=========================");
		System.out.println("1.문제입력  2.문제풀이  0.종료");
		System.out.println("=========================");
		System.out.print("선택 >> ");
		String strMenu = scan.nextLine();
		try {
			int intMenu = Integer.valueOf(strMenu);
			if(intMenu == 0) return;
			else if(intMenu == 1) {
				ccs.inputMenu();
			} else if(intMenu == 2) {
				this.quiz();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public void quiz() {
		List<CBTDTO> cbtList = dao.selectAll();
		if(cbtList == null || cbtList.size() < 1) {
			System.out.println("풀이할 문제가 없습니다.");
		} else {
			this.viewQuiz(cbtList);
		}
		
	}

	private void viewQuiz(List<CBTDTO> cbtList) {
		System.out.println("==========================");
		System.out.println("정보처리 기사 문제를 시작합니다");
		System.out.println("==========================");
		
		cbtList = dao.selectAll();
		List<CBTVO> qandAList = new ArrayList<CBTVO>();
		
		for(CBTDTO dto : cbtList) {
			CBTVO cbtVO = CBTVO.builder()
					.cb_quiz(dto.getCb_quiz())
					.cb_code(dto.getCb_code())
					.cb_answer(dto.getCb_answer())
					.cb_qnums(new ArrayList<String>()).build();
			
			cbtVO.getCb_qnums().add(dto.getCb_ex01());
			cbtVO.getCb_qnums().add(dto.getCb_ex02());
			cbtVO.getCb_qnums().add(dto.getCb_ex03());
			cbtVO.getCb_qnums().add(dto.getCb_ex04());
			
			qandAList.add(cbtVO);
			
		}
		Collections.shuffle(qandAList);
		int nums = 1;
		for(CBTVO vo : qandAList) {
			System.out.print(nums++ + ":");
			System.out.println(vo.getCb_quiz());
			
			Collections.shuffle(vo.getCb_qnums());
			
			int aNums = 1;
			String[] a = new String[5];
			
			for(String s : vo.getCb_qnums()) {
				a[aNums-1] = s;
				System.out.print(aNums++ + ":");
				System.out.println(s);
			}
			System.out.print("정답입력 >> ");
			String strAns = scan.nextLine();
			try {
				int intAns = Integer.valueOf(strAns);
				intAns++ ;
				if(a[intAns].equals(vo.getCb_answer())) {
					System.out.println("정답입니다!!");
					System.out.println();
					Point += 5;
					
					List<CBTVO> oList = qandAList; // 정답리스트
					System.out.println(oList);// 확인작업 나중에 삭제
				} else {
					System.out.println("틀렸습니다!!");
					System.out.println();
					
					List<CBTVO> xList = qandAList; // 오답리스트
					System.out.println(xList); //확인작업 나중에 삭제
					
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
			
		}
	}
	
	
}
