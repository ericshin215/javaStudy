package com.javaex.phonebook1;

import java.util.List;
import java.util.Scanner;

public class Main2 {

	public static void main(String[] args) {

		System.out.println("**********************************************");
		System.out.println("*               전화번호 관리 프로그램              *");
		System.out.println("**********************************************");

		while (true) {
			System.out.println();

			System.out.println("1: 리스트 2: 등록 3: 수정 4: 삭제 5: 종료");
			System.out.println("-----------------------------------");
			System.out.println("> 메뉴번호: ");
			System.out.println();

			PersonDao personDao = new PersonDao();
			List<PersonVo> personList = personDao.personSelect();

			Scanner sc = new Scanner(System.in);

			int counte = 0;
			int num = sc.nextInt();

			String name;
			String hp;
			String company;

//			System.out.println("<4.검색>");
//			System.out.print(">이름 : ");
			sc.nextLine();
			String searchName = sc.nextLine();
			List<PersonVo> personList1 = personDao.personSearch1(searchName);
			for (int i = 0; i < personList1.size(); i++) {
				System.out.println("-------------------------------------------------------------------------");
				System.out.println(personList1.get(i).toString());
			}

		}

	}
}