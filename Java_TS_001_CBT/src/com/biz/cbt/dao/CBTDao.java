package com.biz.cbt.dao;

import java.util.List;

import com.biz.cbt.persistence.CBTDTO;

public interface CBTDao {

	public List<CBTDTO> selectAll();

	public CBTDTO findById(String strCode);
	public String getMaxBCode();

	public int delete(String strCode);
	public int update(CBTDTO dto);
	public int insert(CBTDTO dto);

	
}
