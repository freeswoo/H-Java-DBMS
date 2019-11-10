package com.biz.rent.service;

import java.util.List;
import java.util.Scanner;

import com.biz.rent.config.DBConnection;
import com.biz.rent.dao.BookDao;
import com.biz.rent.persistence.BookDTO;

public class BookServiceV1 {

	protected BookDao bookDao;
	Scanner scanner;

	public BookServiceV1() {
		bookDao = DBConnection
				.getSqlSessionFactory()
				.openSession(true)
				.getMapper(BookDao.class);
		scanner = new Scanner(System.in);
	}

	public void viewAllList() {
		List<BookDTO> bookList = bookDao.selectAll();
		if (bookList == null || bookList.size() < 1) {
			System.out.println("리스트가 없음");
		} else {
			this.viewList(bookList);
		}
	}

	public void viewNameList() {
		System.out.println("====================");
		System.out.println("제목 검색");
		System.out.println("====================");
		System.out.print("제목 >> ");
		String strBName = scanner.nextLine();

		List<BookDTO> bookList = null;

		if (strBName.trim().isEmpty()) {
			bookList = bookDao.selectAll();
		} else {
			bookList = bookDao.findByName(strBName);
		}
		this.viewList(bookList);
	}

	public void viewAutherList() {
		System.out.println("====================");
		System.out.println("저자 검색");
		System.out.println("====================");
		System.out.print("저자 >> ");
		String strBAuther = scanner.nextLine();

		List<BookDTO> bookList = null;

		if (strBAuther.trim().isEmpty()) {
			bookList = bookDao.selectAll();
		} else {
			bookList = bookDao.findByAuther(strBAuther);
		}
		this.viewList(bookList);

	}

	protected void viewList(BookDTO bookDTO) {

		System.out.print(bookDTO.getB_code() + "\t");
		System.out.print(bookDTO.getB_name() + "\t");
		System.out.print(bookDTO.getB_auther() + "\t");
		System.out.print(bookDTO.getB_comp() + "\t");
		System.out.print(bookDTO.getB_year() + "\t");
		System.out.print(bookDTO.getB_iprice() + "\t");
		System.out.print(bookDTO.getB_rprice() + "\n");
	}

	// List를 받아서 출력할때 사용할 method
	protected void viewList(List<BookDTO> bookList) {

		System.out.println("============================================================");
		System.out.println("도서정보리스트");
		System.out.println("============================================================");
		System.out.println("도서코드\t도서명\t저자\t출판사\t구입연도\t구입가격\t대여가격");
		System.out.println("------------------------------------------------------------");
		for (BookDTO bookDTO : bookList) {
			this.viewList(bookDTO);
		}
		System.out.println("============================================================");
	}

	private BookDTO viewBDetail(String strBCode) {

		BookDTO bookDTO = bookDao.findById(strBCode);

		if (bookDTO == null)
			return null;

		System.out.println("==================================");
		System.out.printf("도서코드 : %s\n", bookDTO.getB_code());
		System.out.printf("도서명 : %s\n", bookDTO.getB_name());
		System.out.printf("저자 : %s\n", bookDTO.getB_auther());
		System.out.printf("출판사 : %s\n", bookDTO.getB_comp());
		System.out.printf("구입년도 : %d\n", bookDTO.getB_year());
		System.out.printf("구입가격 : %d\n", bookDTO.getB_iprice());
		System.out.printf("대여가격 : %d\n", bookDTO.getB_rprice());
		System.out.println("==================================");

		return bookDTO;

	}

	public void bookMenu() {
		// TODO: MENU
		while (true) {

			System.out.println("==================================");
			System.out.println("도서 정보 관리");
			System.out.println("==================================");
			System.out.println("1.등록 2.수정 3.삭제 4.제목검색 5.저자검색 0.종료");
			System.out.println("----------------------------------");
			System.out.print("업무선택 >> ");
			String strMenu = scanner.nextLine();
			try {
				int intMenu = Integer.valueOf(strMenu);

				if (intMenu == 0)
					return;
				else if (intMenu == 1) {
					this.bookInsert();
				} else if (intMenu == 2) {
					this.bookUpdate();
				} else if (intMenu == 3) {
					this.bookDelete();
				} else if (intMenu == 4) {
					this.viewNameList();
				} else if (intMenu == 5) {
					this.viewAutherList();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void bookUpdate() {
		// TODO: UPDATE
		System.out.print("수정할 도서코드(-Q:quit) >> ");
		String strBCode = scanner.nextLine();

		BookDTO bookDTO = bookDao.findById(strBCode);
		if (bookDTO == null) {
			System.out.println("도서코드가 없음");
			return;
		}

		this.viewBDetail(strBCode);

		System.out.printf("도서명(%s) >> ", bookDTO.getB_name());
		String strBName = scanner.nextLine();
		if (!strBName.trim().isEmpty())
			bookDTO.setB_name(strBName);

		System.out.printf("저자(%s) >> ", bookDTO.getB_auther());
		String strBAuther = scanner.nextLine();
		if (!strBAuther.trim().isEmpty()) {
			bookDTO.setB_auther(strBAuther);
		}

		System.out.printf("출판사(%s) >> ", bookDTO.getB_comp());
		String strBComp = scanner.nextLine();
		if (!strBComp.trim().isEmpty())
			bookDTO.setB_comp(strBComp);

		System.out.printf("구입연도(%s) >> ", bookDTO.getB_year());
		String strBYear = scanner.nextLine();
		if (!strBYear.trim().isEmpty()) {
			int intYear = Integer.valueOf(strBYear);
			bookDTO.setB_year(intYear);
		}

		System.out.printf("구입금액(%d) >> ", bookDTO.getB_iprice());
		String strBIprice = scanner.nextLine();
		if (!strBIprice.trim().isEmpty()) {
			int intIPrice = Integer.valueOf(strBIprice);
			bookDTO.setB_iprice(intIPrice);
		}

		System.out.printf("대여금액(%d) >> ", bookDTO.getB_rprice());
		String strBRprice = scanner.nextLine();
		if (!strBRprice.trim().isEmpty()) {
			int intRPrice = Integer.valueOf(strBRprice);
			bookDTO.setB_rprice(intRPrice);
		}

		int ret = bookDao.update(bookDTO);
		if (ret > 0)
			System.out.println("수정성공!!!");
		else
			System.out.println("수정실패!!!");

	}

	public void bookDelete() {
		// TODO: DELETE
		while (true) {
			System.out.print("삭제할 도서 코드(-Q:quit) >> ");
			String strBCode = scanner.nextLine();

			if (strBCode.equals("-Q"))
				break;

			BookDTO BookDTO = bookDao.findById(strBCode);
			if (BookDTO == null) {
				System.out.println("삭제할 거래처코드가 없음");
				continue;
			}
			this.viewBDetail(strBCode);

			System.out.print("정말 삭제? Enter:실행");
			String strYesNo = scanner.nextLine();
			if (strYesNo.trim().isEmpty()) {
				int ret = bookDao.delete(strBCode);
				if (ret > 0) {
					System.out.println("삭제완료!!!");
					break;
				} else
					System.out.println("삭제실패!!!");
			}
		}
	}

	public void bookInsert() {
		// TODO: INSERT
		BookDTO bookDTO = new BookDTO();
		while (true) {

			System.out.print("도서코드(Enter:자동생성), (-Q:quit)");
			String strBCode = scanner.nextLine();
			if (strBCode.equals("-Q"))
				break;
			if (strBCode.trim().isEmpty()) {
				String strTMPCode = bookDao.getMaxBCode();
				int intBCode = Integer.valueOf(strTMPCode.substring(2));
				intBCode++;
				strBCode = strTMPCode.substring(0, 2);
				strBCode += String.format("%04d", intBCode);

				System.out.println("생성된코드 : " + strBCode);
				System.out.println("사용하시겠습니까?(Enter:Yes");
				String strYesNo = scanner.nextLine();
				if (strYesNo.trim().isEmpty()) {
					bookDTO.setB_code(strBCode);
					break;
				}
				else continue;
			}
			if (strBCode.length() != 6) {
				System.out.println("도서코드 길이 규칙에 맞지 않음");
				continue;
			}

			strBCode = strBCode.toUpperCase();
			if (strBCode.substring(0, 2).equalsIgnoreCase("BK")) {
				System.out.println("도서코드는 BK로 시작되어야 함");
				continue;
			}

			try {
				Integer.valueOf(strBCode.substring(2));
			} catch (Exception e) {
				System.out.println("상품코드3번째 이후는 숫자만 가능함");
				continue;
			}

			if (bookDao.findById(strBCode) != null) {
				System.out.println("이미 사용중인 코드!!");
				continue;
			}
			
			break;
		} // end while

		while (true) {
			System.out.print("도서명(-Q:quit) >> ");
			String strBName = scanner.nextLine();
			if (strBName.equals("-Q"))
				break;
			if (strBName.trim().isEmpty()) {
				System.out.println("도서명은 반드시 입력해야함");
				continue;
			}

			List<BookDTO> bookList = bookDao.findByName(strBName);

			if (bookList.size() > 0) {
				System.out.println("중복되는 도서명이 있습니다. 다시 입력해주세요");
				continue;
			}
			bookDTO.setB_name(strBName);
			break;
		}

		while (true) {
			System.out.print("저자(-Q:quit) >> ");
			String strBAuther = scanner.nextLine();
			if (strBAuther.equals("-Q"))
				break;
			if (strBAuther.trim().isEmpty()) {
				System.out.println("저자는 반드시 입력해야함");
				continue;
			}
			bookDTO.setB_auther(strBAuther);
			break;
		}

		while (true) {
			System.out.print("출판사(-Q:quit) >> ");
			String strBComp = scanner.nextLine();
			if (strBComp.equals("-Q"))
				break;
			bookDTO.setB_comp(strBComp);
			break;
		}

		while (true) {
			System.out.print("구입연도(-Q:quit) >> ");
			String strBYear = scanner.nextLine();
			if (strBYear.equals("-Q"))
				break;
			if (strBYear.trim().isEmpty()) {
				System.out.println("구입연도는 반드시 입력해야함");
				continue;
			}

			try {
				int intBYear = Integer.valueOf(strBYear);
				bookDTO.setB_year(intBYear);
				break;
			} catch (Exception e) {
				System.out.println("구입년도는 숫자만 입력해주세요.");
				continue;
			}
		}

		while (true) {
			System.out.print("구입가격(-Q:quit) >> ");
			String strBPrice = scanner.nextLine();
			if (strBPrice.equals("-Q"))
				break;
			try {
				int intBPrice = Integer.valueOf(strBPrice);
				bookDTO.setB_iprice(intBPrice);
				break;
			} catch (Exception e) {
				System.out.println("구입가격은 숫자만 입력해주세요.");
				continue;
			}

		}

		while (true) {
			System.out.print("대여가격(-Q:quit) >> ");
			String strRprice = scanner.nextLine();
			if (strRprice.equals("-Q"))
				break;
			if (strRprice.trim().isEmpty()) {
				System.out.println("대여가격은 반드시 입력해야함");
				continue;
			}

			try {
				int intRprice = Integer.valueOf(strRprice);
				bookDTO.setB_rprice(intRprice);
				break;
			} catch (Exception e) {
				System.out.println("대여가격은 숫자만 입력해주세요.");
				continue;
			}
		}

		int ret = bookDao.insert(bookDTO);
		if (ret > 0) {
			System.out.println("데이터 등록 성공");
		} else {
			System.out.println("데이터 등록 실패");
		}

	}
}
