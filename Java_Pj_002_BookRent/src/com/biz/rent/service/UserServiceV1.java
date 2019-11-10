package com.biz.rent.service;

import java.util.List;
import java.util.Scanner;

import com.biz.rent.config.DBConnection;
import com.biz.rent.dao.UserDao;
import com.biz.rent.persistence.UserDTO;

public class UserServiceV1 {

	protected UserDao userDao;
	Scanner scanner;

	public UserServiceV1() {
		userDao = DBConnection.getSqlSessionFactory().openSession(true).getMapper(UserDao.class);
		scanner = new Scanner(System.in);
	}

	public void viewAllList() {
		List<UserDTO> UserList = userDao.selectAll();
		if (UserList == null || UserList.size() < 1) {
			System.out.println("리스트가 없음");
		} else {
			this.viewList(UserList);
		}
	}

	public void viewNameList() {
		System.out.println("====================");
		System.out.println("이름 검색");
		System.out.println("====================");
		System.out.print("이름 >> ");
		String strUName = scanner.nextLine();

		List<UserDTO> userList = null;

		if (strUName.trim().isEmpty()) {
			userList = userDao.selectAll();
		} else {
			userList = userDao.findByName(strUName);
		}
		this.viewList(userList);
	}

	public void viewTelList() {
		System.out.println("====================");
		System.out.println("전화번호 검색");
		System.out.println("====================");
		System.out.print("전화번호 >> ");
		String strUtel = scanner.nextLine();

		List<UserDTO> userList = null;

		if (strUtel.trim().isEmpty()) {
			userList = userDao.selectAll();
		} else {
			userList = userDao.findByTel(strUtel);
		}
		this.viewList(userList);

	}

	protected void viewList(UserDTO userDTO) {

		System.out.print(userDTO.getU_code() + "\t");
		System.out.print(userDTO.getU_name() + "\t");
		System.out.print(userDTO.getU_tel() + "\t");
		System.out.print(userDTO.getU_addr() + "\n");
	}

	// List를 받아서 출력할때 사용할 method
	protected void viewList(List<UserDTO> userList) {

		System.out.println("============================================================");
		System.out.println("고객정보리스트");
		System.out.println("============================================================");
		System.out.println("고객코드\t고객명\t고객전화\t고객주소");
		System.out.println("------------------------------------------------------------");
		for (UserDTO userDTO : userList) {
			this.viewList(userDTO);
		}
		System.out.println("============================================================");
	}

	private UserDTO viewBDetail(String strUCode) {

		UserDTO userDTO = userDao.findById(strUCode);

		if (userDTO == null)
			return null;

		System.out.println("==================================");
		System.out.printf("고객코드 : %s\n", userDTO.getU_code());
		System.out.printf("고객명 : %s\n", userDTO.getU_name());
		System.out.printf("고객전화 : %s\n", userDTO.getU_tel());
		System.out.printf("고객주소 : %s\n", userDTO.getU_addr());
		System.out.println("==================================");

		return userDTO;

	}

	public void UserMenu() {
		// TODO: MENU
		while (true) {

			System.out.println("==================================");
			System.out.println("고객 정보 관리");
			System.out.println("==================================");
			System.out.println("1.등록 2.수정 3.삭제 4.이름검색 5.전화검색 0.종료");
			System.out.println("----------------------------------");
			System.out.print("업무선택 >> ");
			String strMenu = scanner.nextLine();
			try {
				int intMenu = Integer.valueOf(strMenu);

				if (intMenu == 0)
					return;
				else if (intMenu == 1) {
					this.UserInsert();
				} else if (intMenu == 2) {
					this.UserUpdate();
				} else if (intMenu == 3) {
					this.UserDelete();
				} else if (intMenu == 4) {
					this.viewNameList();
				} else if (intMenu == 5) {
					this.viewTelList();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void UserUpdate() {
		// TODO: UPDATE
		System.out.print("수정할 고객코드(-Q:quit) >> ");
		String strBCode = scanner.nextLine();

		UserDTO userDTO = userDao.findById(strBCode);
		if (userDTO == null) {
			System.out.println("유저코드가 없음");
			return;
		}

		this.viewBDetail(strBCode);

		System.out.printf("고객명(%s) >> ", userDTO.getU_name());
		String strBName = scanner.nextLine();
		if (!strBName.trim().isEmpty())
			userDTO.setU_name(strBName);

		System.out.printf("전화번호(%s) >> ", userDTO.getU_tel());
		String strBAuther = scanner.nextLine();
		if (!strBAuther.trim().isEmpty()) {
			userDTO.setU_tel(strBAuther);
		}

		System.out.printf("주소(%s) >> ", userDTO.getU_addr());
		String strBComp = scanner.nextLine();
		if (!strBComp.trim().isEmpty()) {
			userDTO.setU_addr(strBComp);

		}

		int ret = userDao.update(userDTO);
		if (ret > 0)
			System.out.println("수정성공!!!");
		else
			System.out.println("수정실패!!!");

	}

	public void UserDelete() {
		// TODO: DELETE
		while (true) {
			System.out.print("삭제할 고객 코드(-Q:quit) >> ");
			String strUCode = scanner.nextLine();

			if (strUCode.equals("-Q"))
				break;

			UserDTO userDTO = userDao.findById(strUCode);
			if (userDTO == null) {
				System.out.println("삭제할 고객코드가 없음");
				continue;
			}
			this.viewBDetail(strUCode);

			System.out.print("정말 삭제? Enter:실행");
			String strYesNo = scanner.nextLine();
			if (strYesNo.trim().isEmpty()) {
				int ret = userDao.delete(strUCode);
				if (ret > 0) {
					System.out.println("삭제완료!!!");
					break;
				} else
					System.out.println("삭제실패!!!");
			}
		}
	}

	public void UserInsert() {
		// TODO: INSERT
		UserDTO userDTO = new UserDTO();
		while (true) {

			System.out.print("고객코드(Enter:자동생성), (-Q:quit)");
			String strUCode = scanner.nextLine();
			if (strUCode.equals("-Q"))
				break;
			if (strUCode.trim().isEmpty()) {
				String strTMPCode = userDao.getMaxUCode();
				int intBCode = Integer.valueOf(strTMPCode.substring(1));
				intBCode++;
				strUCode = strTMPCode.substring(0, 1);
				strUCode += String.format("%05d", intBCode);

				System.out.println("생성된코드 : " + strUCode);
				System.out.println("사용하시겠습니까?(Enter:Yes");
				String strYesNo = scanner.nextLine();
				if (strYesNo.trim().isEmpty()) {
					userDTO.setU_code(strUCode);
					break;
				}
				else continue;
			}
			if (strUCode.length() != 6) {
				System.out.println("고객코드 길이 규칙에 맞지 않음");
				continue;
			}

			strUCode = strUCode.toUpperCase();
			if (strUCode.substring(0, 1).equalsIgnoreCase("S")) {
				System.out.println("고객코드는 S로 시작되어야 함");
				continue;
			}

			try {
				Integer.valueOf(strUCode.substring(2));
			} catch (Exception e) {
				System.out.println("고객코드 2번째 이후는 숫자만 가능함");
				continue;
			}

			if (userDao.findById(strUCode) != null) {
				System.out.println("이미 사용중인 코드!!");
				continue;
			}
			
			break;
		} // end while

		while (true) {
			System.out.print("고객명(-Q:quit) >> ");
			String strUName = scanner.nextLine();
			if (strUName.equals("-Q"))
				break;
			if (strUName.trim().isEmpty()) {
				System.out.println("고객명은 반드시 입력해야함");
				continue;
			}

			List<UserDTO> userList = userDao.findByName(strUName);

			if (userList.size() > 0) {
				System.out.println("중복되는 도서명이 있습니다. 다시 입력해주세요");
				continue;
			}
			userDTO.setU_name(strUName);
			break;
		}

		while (true) {
			System.out.print("전화번호(-Q:quit) >> ");
			String strUTel = scanner.nextLine();
			if (strUTel.equals("-Q"))
				break;
			userDTO.setU_tel(strUTel);
			break;
		}

		while (true) {
			System.out.print("주소(-Q:quit) >> ");
			String strUAddr = scanner.nextLine();
			if (strUAddr.equals("-Q"))
				break;
			userDTO.setU_addr(strUAddr);
			break;
		}
		
		int ret = userDao.insert(userDTO);
		if (ret > 0) {
			System.out.println("데이터 등록 성공");
		} else {
			System.out.println("데이터 등록 실패");
		}

	}
}
