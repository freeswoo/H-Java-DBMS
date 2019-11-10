package com.biz.addr.service;

import java.util.List;
import java.util.Scanner;

import com.biz.addr.persistence.AddrDTO;
import com.biz.addr.persistence.dao.AddrDao;
import com.biz.addr.persistence.dao.AddrDaoImp;

public class AddrServiceV1 {
	
	AddrDao addrDao = null;
	Scanner scanner = null;
	String strName = null;
	String strTel = null;
	String strChain = null;
	public AddrServiceV1() {
		
		scanner = new Scanner(System.in);
		addrDao = new AddrDaoImp();

	}
	
	private void viewList(List<AddrDTO> addrList) {
		System.out.println("=================================");
		System.out.println("주소록 리스트 v1");
		System.out.println("================================");
		System.out.println("ID\t이름\t전화번호\t\t관계");
		System.out.println("--------------------------------");
		for(AddrDTO dto : addrList) {
			System.out.printf("%d\t",dto.getId());
			System.out.printf("%s\t",dto.getName());
			System.out.printf("%s\t",dto.getTel());
			System.out.printf("%s\n",dto.getChain());
		}
		System.out.println("=================================");
	}
	
	public void viewAddrList() {
		
		List<AddrDTO> addrList = addrDao.selectAll();
		
		this.viewList(addrList);
	}
	
	public String searchAddrName() {
		
		System.out.println("================");
		System.out.println("이름검색");
		System.out.println("================");
		System.out.print("이름(-Q:quit) >> ");
		strName = scanner.nextLine();
		if(strName.equals("-Q")) return "-Q";
		
		List<AddrDTO> addrList = addrDao.findByName(strName);
		
		if(addrList == null || addrList.size() < 1) {
			System.out.println("찾는 이름이 없음!!");
			return "-Q";
		}
		
		this.viewList(addrList);
		return strName;
	}
	
	public String searchAddrTel() {
		
		System.out.println("================");
		System.out.println("전화검색");
		System.out.println("================");
		System.out.print("전화번호(-Q:quit) >> ");
		strTel = scanner.nextLine();
		if(strTel.equals("-Q")) return "-Q";
		
		List<AddrDTO> addrList = addrDao.findByTel(strTel);
		
		if(addrList == null || addrList.size() < 1) {
			System.out.println("찾는 이름이 없음!!");
			return "-Q";
		}
		
		this.viewList(addrList);
		return strTel;
	}
	
public String searchAddrChain() {
		
		System.out.println("================");
		System.out.println("관계검색");
		System.out.println("================");
		System.out.print("관계(-Q:quit) >> ");
		strChain = scanner.nextLine();
		if(strChain.equals("-Q")) return "-Q";
		
		List<AddrDTO> addrList = addrDao.findByChain(strChain);
		
		if(addrList == null || addrList.size() < 1) {
			System.out.println("찾는 관계 없음!!");
			return "-Q";
		}
		
		this.viewList(addrList);
		return strChain;
	}


}