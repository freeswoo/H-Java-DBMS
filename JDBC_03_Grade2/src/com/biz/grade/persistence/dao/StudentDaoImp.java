package com.biz.grade.persistence.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.biz.grade.config.DBContract;
import com.biz.grade.persistence.domain.StudentDTO;

/*
 * StudentDao 상속받아서 코드를 구체화 하는
 * 실행 class
 */

public class StudentDaoImp extends StudentDao {

	@Override
	public List<StudentDTO> selectAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StudentDTO findById(String st_num) {
		
		PreparedStatement pSt = null;
		String sql = DBContract.SQL.SELECT_STUDENT;
		sql += " WHERE st_num = ? ";
		
		try {
			pSt = dbConn.prepareStatement(sql);
			pSt.setString(1, st_num);
			ResultSet rst = pSt.executeQuery();
			
			StudentDTO stdDTO = null;
			if(rst.next()) {
				stdDTO = this.rst_2_Dto(rst);
			}
			rst.close();
			pSt.close();
			return stdDTO;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	private StudentDTO rst_2_Dto(ResultSet rst) throws SQLException {
		
		return StudentDTO.builder()
				.st_num(rst.getString("ST_NUM"))
				.st_name(rst.getString("ST_NAME"))
				.st_grade(rst.getInt("ST_GRADE"))
				.st_dept(rst.getString("ST_DEPT"))
				.st_tel(rst.getString("ST_TEL"))
				.st_addr(rst.getString("ST_ADDR"))
				.build();
		
	}

	@Override
	public List<StudentDTO> findByName(String st_name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<StudentDTO> findByGrade(int st_grade) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int insert(StudentDTO stdDTO) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(StudentDTO stdDTO) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(String st_num) {
		// TODO Auto-generated method stub
		return 0;
	}

}
