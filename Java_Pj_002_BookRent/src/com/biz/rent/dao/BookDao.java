package com.biz.rent.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.biz.rent.persistence.BookDTO;

public interface BookDao {

	public List<BookDTO> selectAll();
	
	public List<BookDTO> findByName(String b_name);
	public List<BookDTO> findByAuther(String b_auther);
	
	public int insert(BookDTO bookDTO);
	public int update(BookDTO bookDTO);
	public int delete(String b_code);

	public BookDTO findById(String strBCode);

	public String getMaxBCode();

	
	
}
