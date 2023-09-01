package com.javaex.ex01.book;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookSelectOne {

	public static void main(String[] args) {
		
		List<BookVo> bookList = new ArrayList<BookVo>();		// 리스트 생성

		// 0. import java.sql.*;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			// 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName("oracle.jdbc.driver.OracleDriver"); // '오라클' 드라이버에 접속

			// 2. Connection 얻어오기
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			conn = DriverManager.getConnection(url, "webdb", "webdb");  // 해당주소값 저장소에 접속

			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "";
			query += " SELECT  b.book_id ";
			query += "          ,b.title ";
			query += "          ,b.pubs ";
			query += "          ,b.pub_date ";
			query += "          ,b.author_id ";
			query += "          ,a.author_name ";
			query += "          ,b.author_id ";
			query += " FROM book b, author a ";	
			query += " WHERE b.author_id = a.author_id ";			
			System.out.println(query);
			
			pstmt = conn.prepareStatement(query);  // 변환
			
			// 4.결과처리
			rs = pstmt.executeQuery();
			
			System.out.println();
			
			while(rs.next()) {  // 값의 수를 모르기 무한 반복문 while 사용
				int bookId = rs.getInt(1);
				String title = rs.getString(2);
				String pubs = rs.getString(3);
				String pubDate = rs.getString(4);
				String authorId = rs.getString(5);
				String authorName = rs.getString(6);
				
//				System.out.println(bookId + ", " + title + ", " + pubs + ", " + pubDate + ", " + authorId  + ", " + authorName);
				
				BookVo bookVo = new BookVo(); // 작가 객체 생성
				bookVo.setBookId(bookId);
				bookVo.setTitle(title);
				bookVo.setPubs(pubs);
				bookVo.setPubDate(pubDate);
				bookVo.setAuthorId(bookId);
				bookVo.setAuthorName(authorName);
				
				bookList.add(bookVo);
				
			}

		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {

			// 5. 자원정리
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}

		}
		
		int i = 0;  //  첫번째 책 정보 출력
		
		System.out.println( " bookId: " + bookList.get(i).getBookId()
						   + " title: " + bookList.get(i).getTitle()
						   + " pubs: " + bookList.get(i).getPubs()
						   + " pubDate: " + bookList.get(i).getPubDate()
						   + " authorId: " + bookList.get(i).getAuthorId()
						   + " authorName: " + bookList.get(i).getAuthorName());
		
		

	}

}
