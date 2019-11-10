package com.biz.rent.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import org.apache.ibatis.ognl.ParseException;
import org.apache.ibatis.session.SqlSession;

import com.biz.rent.config.DBConnection;
import com.biz.rent.dao.BookDao;
import com.biz.rent.dao.RentBookDao;
import com.biz.rent.dao.UserDao;
import com.biz.rent.persistence.BookDTO;
import com.biz.rent.persistence.RentBookDTO;
import com.biz.rent.persistence.UserDTO;

public class RentBookServiceV1 {

	protected SqlSession sqlSession = null;
	protected BookDao bookDao = null;
	protected UserDao userDao = null;
	protected RentBookDao rentDao = null;
	protected Scanner scan = null;

	public RentBookServiceV1() {
		sqlSession = DBConnection.getSqlSessionFactory().openSession(true);
		bookDao = sqlSession.getMapper(BookDao.class);
		userDao = sqlSession.getMapper(UserDao.class);
		rentDao = sqlSession.getMapper(RentBookDao.class);
		scan = new Scanner(System.in);
	}

	public void viewRentListAll() {
		List<RentBookDTO> rentList = rentDao.selectAll();
		this.viewList(rentList);
	}

	protected void viewList(RentBookDTO rentDTO) {

		System.out.print(rentDTO.getRent_seq() + "\t");
		System.out.print(rentDTO.getRent_date() + "\t");
		System.out.print(rentDTO.getRent_return_date() + "\t");
		System.out.print(rentDTO.getRent_bcode() + "\t");
		System.out.print(rentDTO.getRent_ucode() + "\t");
		System.out.print(rentDTO.getRent_retur_yn() + "\t");
		System.out.print(rentDTO.getRent_point() + "\n");
	}

	// List를 받아서 출력할때 사용할 method
	protected void viewList(List<RentBookDTO> rentList) {

		System.out.println("========================================");
		System.out.println("렌탈 리스트");
		System.out.println("========================================");
		System.out.println("번호\t대여일자\t반납일자\t도서코드\t고객코드\t반납여부\t포인트점수");
		System.out.println("----------------------------------------");
		for (RentBookDTO rentDTO : rentList) {
			this.viewList(rentDTO);
		}
		System.out.println("========================================");
	}

	public void rentMenu() {
		while (true) {
			System.out.println("========================================");
			System.out.println("도서 렌탈 서비스");
			System.out.println("========================================");
			System.out.print("1.도서대출 2.도서반납 3.대여리스트 4.대여리스트삭제 0.종료 \n");
			System.out.println("========================================");
			System.out.print("업무 선택 >> ");
			String strMenu = scan.nextLine();
			int intMenu = 0;
			try {
				intMenu = Integer.valueOf(strMenu);
				if (intMenu == 0)
					return;
				else if (intMenu == 1) {
					this.rentInsert();
				} else if (intMenu == 2) {
					this.bookReturnUpdate();
				} else if (intMenu == 3) {
					this.viewRentListAll();
				} else if (intMenu == 4) {
					this.bookDelete();
				}
			} catch (Exception e) {
				System.out.println("숫자만 입력해주세요.");
				continue;
				// TODO: handle exception
			}
		}
	}

	private void rentInsert() {
		// TODO 도서대출
		System.out.println("===========================================");
		System.out.println("도서 대출 등록");
		System.out.println("===========================================");
		RentBookDTO rentDTO = new RentBookDTO();
		while (true) { // 도서 이름으로 검색
			System.out.print("도서명 검색 (-Q : 종료 ) >> ");
			String strBName = scan.nextLine();
			if (strBName.equals("-Q"))
				return;

			List<BookDTO> bookList = bookDao.findByName(strBName);
			this.viewBookList(bookList);

			if (bookList.size() < 1) {
				System.out.println("검색 결과가 없습니다. 다시 입력해주세요.");
				continue;
			}
			break;
		}

		while (true) { // 도서 코드 입력
			System.out.print("도서 코드 입력 (-Q : 종료 ) >> ");
			String strBCode = scan.nextLine();
			BookDTO bookDTO = bookDao.findById(strBCode);
			if (strBCode.equals("-Q"))
				return;

			if (bookDTO == null) {
				System.out.println("등록되지 않은 코드 입니다.");
				continue;
			}

			List<RentBookDTO> rentList = rentDao.checkRent(strBCode);
			if (rentList.size() > 0) {
				System.out.println("이미 대출된 도서입니다.");
				this.viewRentList(rentList);
				continue;
			}

			rentDTO.setRent_bcode(strBCode);
			break;
		}

		while (true) { // 회원 이름으로 검색
			System.out.print("회원 이름 검색 (-Q : 종료 ) >> ");
			String u_name = scan.nextLine();
			if (u_name.equals("-Q"))
				return;
			List<UserDTO> userList = userDao.findByName(u_name);

			if (userList.size() < 1) {
				System.out.println("검색 결과가 없습니다. 다시 입력해주세요.");
				continue;
			}

			this.viewUserList(userList);
			break;
		}

		while (true) { // 회원 코드 입력
			System.out.print("회원 코드 입력 (-Q : 종료 ) >> ");
			String u_code = scan.nextLine();
			if (u_code.equals("-Q"))
				return;
			UserDTO userDTO = userDao.findById(u_code);

			if (userDTO == null) {
				System.out.println("등록되지 않은 코드 입니다.");
				continue;
			}

			rentDTO.setRent_ucode(u_code);
			break;

		}

		Calendar cal = Calendar.getInstance();
		Date date = new Date(System.currentTimeMillis());
		cal.setTime(date);
		cal.add(Calendar.DATE, 14);
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
		String rentDate = sd.format(date);
		String returnDate = sd.format(cal.getTime());

		rentDTO.setRent_date(rentDate);
		rentDTO.setRent_return_date(returnDate);

		int ret = rentDao.insert(rentDTO);

		if (ret > 0) {
			System.out.println("대출 등록 성공");
		} else {
			System.out.println("대출 등록 실패");
		}

	}

	private void viewUserList(List<UserDTO> userList) {
		System.out.println("=============================================");
		System.out.println("회원코드\t이름\t전화번호\t주소");
		System.out.println("=============================================");
		for (UserDTO userDTO : userList) {
			System.out.print(userDTO.getU_code() + "\t");
			System.out.print(userDTO.getU_name() + "\t");
			System.out.print(userDTO.getU_tel() + "\t");
			System.out.print(userDTO.getU_addr() + "\n");
		}
		System.out.println("=============================================");
	}

	private void viewRentList(List<RentBookDTO> rentList) {
		System.out.println(
				"=============================================================================================");
		System.out.println("SEQ\t대출일\t\t반납예정일\t도서코드\t회원코드\t도서반납여부\t포인트");
		for (RentBookDTO rentBookDTO : rentList) {
			System.out.print(rentBookDTO.getRent_seq() + "\t");
			System.out.print(rentBookDTO.getRent_date() + "\t");
			System.out.print(rentBookDTO.getRent_return_date() + "\t");
			System.out.print(rentBookDTO.getRent_bcode() + "\t\t");
			System.out.print(rentBookDTO.getRent_ucode() + "\t\t");
			System.out.print(rentBookDTO.getRent_retur_yn() + "\t\t");
			System.out.print(rentBookDTO.getRent_point() + "\n");
		}
		System.out.println(
				"=============================================================================================");

	}

	private void viewBookList(List<BookDTO> bookDTOList) {
		System.out.println(
				"========================================================================================================================================");
		System.out.println("도서코드\t도서명\t\t\t\t\t저자\t대여료\t출판년도\t구입가격\t출판사");
		System.out.println(
				"========================================================================================================================================");
		for (BookDTO bookDTO : bookDTOList) {
			System.out.print(bookDTO.getB_code() + "\t");
			System.out.print(bookDTO.getB_name() + "\t");
			System.out.print(bookDTO.getB_auther() + "\t");
			System.out.print(bookDTO.getB_rprice() + "\t");
			System.out.print(bookDTO.getB_year() + "\t");
			System.out.print(bookDTO.getB_iprice() + "\t");
			System.out.print(bookDTO.getB_comp() + "\n");
		}
		System.out.println(
				"========================================================================================================================================");

	}

	private void bookDelete() {

		List<RentBookDTO> list = rentDao.selectAll();
		this.viewRentList(list);
		System.out.print("삭제할 SEQ를 입력하세요 (-Q : 종료 ) >> ");
		String strSeq = scan.nextLine();
		if (strSeq.equals("-Q"))
			return;

		int seq = 0;
		try {
			seq = Integer.valueOf(strSeq);
		} catch (Exception e) {
			System.out.println("숫자만 입력하세요");
			// TODO: handle exception
		}

		int ret = rentDao.delete(seq);

		if (ret > 0) {
			System.out.println("삭제 완료");
		} else {
			System.out.println("삭제 실패");
		}

	}

	public void bookReturnUpdate() throws ParseException {

		System.out.println("========================================================");
		System.out.println("미반납 도서 목록");
		List<RentBookDTO> rentList = rentDao.noReturnBookCheck();
		this.viewRentList(rentList);

		RentBookDTO rentDTO;
		while (true) { // 미반납 확인
			System.out.print("반납할 SEQ 입력 (-Q : 종료 ) >> ");
			String strSeq = scan.nextLine();
			if (strSeq.equals("-Q"))
				return;
			int seq = 0;
			try {
				seq = Integer.valueOf(strSeq);
			} catch (Exception e) {
				System.out.println("숫자만 입력해주세요.");
				continue;
				// TODO: handle exception
			}
			rentDTO = rentDao.findById(seq);
			if (rentDTO == null) {
				System.out.println("대여 리스트에 존재하지 않습니다.");
				continue;
			}

			List<RentBookDTO> checkList = rentDao.checkRent(rentDTO.getRent_bcode());
			if (checkList.size() < 1) {
				System.out.println("미반납 도서가 아닙니다.");
				continue;
			}
			break;
		}

		if (rentDTO.getRent_retur_yn() == null) {
			rentDTO.setRent_retur_yn("Y");
		}

		// 포인트 부분 추가
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date beginDate = new Date(System.currentTimeMillis());
			Date endDate = sd.parse(rentDTO.getRent_return_date());

			long diff = endDate.getTime() - beginDate.getTime();
			if (diff >= 0) {
				rentDTO.setRent_point(5);
			}
		} catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}	

}