package com.biz.iolist.dao;

import java.util.List;

import com.biz.iolist.persistence.IolistVO;

public interface IolistViewDao {

	public List<IolistVO> selectAll();
	public IolistVO findById(long io_seq);
	
	public List<IolistVO> findByDcode(String io_dcode);
	public List<IolistVO> findByPcode(String io_pcode);
	
	public List<IolistVO> findByDname(String io_dname);
	public List<IolistVO> findByPname(String io_pname);
	
}
