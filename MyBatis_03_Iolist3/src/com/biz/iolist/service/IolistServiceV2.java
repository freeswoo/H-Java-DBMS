package com.biz.iolist.service;

import java.util.List;
import java.util.Scanner;

import com.biz.iolist.config.DBConnection;
import com.biz.iolist.dao.IolistDao;
import com.biz.iolist.persistence.IolistDTO;

/*
 * 매입매출 등록
 * 날짜 현재날짜로 자동등록
 * 거래처검색
 * 거래처코드입력
 * 입력한코드 검증
 * 
 * 상품검색
 * 상품코드입력
 * 입력한코드 검증
 * 
 * 매입,매출구분입력
 * 
 * 매입,매출에 따라서
 * 매입단가,매출단가 가져오기(세팅)
 * 
 * 매입합계 또는 매출합계 계산하기
 * 
 * insert
 * 
 * 추가된 데이터 보여주기
 */
public class IolistServiceV2 {

	protected IolistDao iolistDao;
	protected Scanner scanner;
	
	public IolistServiceV2() {
		iolistDao = DBConnection
				.getSqlSessionFactory()
				.openSession(true)
				.getMapper(IolistDao.class);
		scanner = new Scanner(System.in);
		
	}
	
	public void viewAllList() {
		List<IolistDTO> ioList = iolistDao.selectAll();
		if(ioList == null || ioList.size() < 1) {
			System.out.println("리스트가 없음");
		} else {
			this.viewList(ioList);
		}
	}
	protected void viewList(List<IolistDTO> ioList) {
		
	}
	
	protected void viewList(IolistDTO iolistDTO) {
		System.out.println(iolistDTO.getIo_seq() + "\t");
		System.out.println(iolistDTO.getIo_date() + "\t");
		System.out.println(iolistDTO.getIo_inout() + "\t");
		System.out.println(iolistDTO.getIo_qty() + "\t");
		System.out.println(iolistDTO.getIo_price() + "\t");
		System.out.println(iolistDTO.getIo_total() + "\t");
		System.out.println(iolistDTO.getIo_pcode() + "\t");
		System.out.println(iolistDTO.getIo_dcode() + "\n");
	}
	
}
