package com.javaex.ex02.author;

import java.util.List;
import java.util.Scanner;

public class PerfectApp {

	public static void main(String[] args) {
		
		while (true) {
			System.out.println();
			System.out.println("=========원하는 작업번호를 입력하시오=========");
			System.out.println("1: 리스트 2: 등록 3: 수정 4: 삭제 5: 종료");
			System.out.println("==============================================");
			System.out.println();
			
			AuthorDao authorDao = new AuthorDao();
			List<AuthorVo> authorList = authorDao.authorSelect();
			Scanner sr = new Scanner(System.in);
			
			int counte = 0;
			int num = sr.nextInt();
			
			String authorName;
			String authorDesc;
		
			switch (num) {
			
			case 1: {
				for (int i = 0; i < authorList.size(); i++) {
					System.out.println("-------------------------------------------------------------------------");
					System.out.println(authorList.get(i).toString());
				}
				break;
			}
			case 2: {
				System.out.println("등록하기");
				System.out.print("작가를 입력하십시오 : ");
				sr.nextLine();
				authorName = sr.nextLine();
				System.out.print("작가정보를 입력하십시오 : ");
				authorDesc = sr.nextLine();
				authorDao.authorInsert( authorName , authorDesc );
				System.out.println("등록 되었습니다.");
				break;
			}
			case 3: {
				System.out.println("수정하기");
				System.out.print("작가를 입력하십시오 : ");
				sr.nextLine();
				authorName = sr.nextLine();
				System.out.print("작가정보를 입력하십시오 : ");
				authorDesc = sr.nextLine();
				counte += authorDao.authorUpdate(authorName, authorDesc);
				System.out.println(counte + "건 수정 되었습니다.");
				break;
			}
			case 4: {
				System.out.println("삭제하기");
				System.out.print("작가ID를 입력하십시오 : ");
				int authorId = sr.nextInt();
				counte += authorDao.authorDelete(authorId);
				System.out.println(counte + "건 삭제 되었습니다.");
				break;
			}
			case 5: {
				System.out.println(" 종료됩니다. ");
				return;
			}
			default:
				System.out.println("번호를 다시 입력해 주십시오.");
				;
			}
		}

	}

}