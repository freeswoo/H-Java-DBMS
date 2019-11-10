package com.biz.iolist.exec;

import com.biz.iolist.service.dept.DeptServiceV3;

public class DeptEx_01 {

	public static void main(String[] args) {

		DeptServiceV3 ds = new DeptServiceV3();
		//ds.viewAllList();
		
		System.out.println("==========================");
		//ds.viewNameList();
		
		System.out.println("============================");
		//ds.viewNameAndCeoList();
		//ds.deptUpdate();
		ds.deptInsert();
	}

}
