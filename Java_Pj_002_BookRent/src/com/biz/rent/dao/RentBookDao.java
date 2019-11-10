package com.biz.rent.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.biz.rent.persistence.RentBookDTO;
import com.biz.rent.persistence.UserDTO;

public interface RentBookDao {

	public List<RentBookDTO> selectAll();
	public List<RentBookDTO> checkRent(@Param("rent_bcode") String rent_bcode);
	public List<RentBookDTO> noReturnBookCheck();
	
	public RentBookDTO findById(long rent_seq);
	public RentBookDTO findByRentName(String rent_name);
	
	public int insert(RentBookDTO rentBookDTO);
	public int update(RentBookDTO rentBookDTO);
	public int delete(long rent_seq);
	
	
	
	
	
}