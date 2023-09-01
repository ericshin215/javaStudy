package com.javaex.ex02.book;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookDao {

	// 필드
	// 0. import java.sql.*;
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String id = "webdb";
	private String pw = "webdb";

	// 생성자

	// 메소드-gs

	// 메소드-일반

	// (1) 공통사항 빼놓기
	private void getConnect() {

		try {
			// 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName(driver);
			// 2. Connection 얻어오기
			conn = DriverManager.getConnection(url, id, pw);
		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

	}

	private void close() {
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

	// (2) 책등록 메소드
	public int bookInsert(String title, String pubs, String pubDate, int authorId) {
		int count = -1;
		this.getConnect();

		try {
			// 3. SQL문 준비 / 바인딩 / 실행

			// (1) SQL문 준비
			String query = "";
			query += " INSERT INTO book ";
			query += " VALUES (seq_book_id.nextval, ?, ?, ?, ?)";

			// (2) 바인딩 (값을 쿼리문문자열 안에 매칭시키기)
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, title); // (첫번째 물음표에는 문자열 title 매칭하기)
			pstmt.setString(2, pubs); // (두번째 물음표에는 문자열 pubs 매칭하기)
			pstmt.setString(3, pubDate); // (세번째 물음표에는 문자열 pub_date 매칭하기)
			pstmt.setInt(4, authorId); // (네번째 물음표에는 문자열 author_id 매칭하기)
			// (3) 실행
			count = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		this.close();
		return count;
	}

	// (3) 책수정 메소드
	public int bookUpdate(String title, int author_id) {
		int count = -1;
		this.getConnect();

		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "";
			query += " UPDATE book ";
			query += " set title = ? ";
			query += " where author_id = ? ";

			pstmt = conn.prepareStatement(query);

			// (2) 바인딩 (값을 쿼리문문자열 안에 매칭시키기)
			pstmt.setString(1, title);
			pstmt.setInt(2, author_id);

			// (3) 실행
			count = pstmt.executeUpdate();

			System.out.println(count + "건 업데이트되었습니다.");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		this.close();
		return count;
	}

	// (4) 책삭제 메소드
	public int bookDelete(int book_id) {
		int count = -1;
		this.getConnect();

		try {
			String query = "";
			query += " DELETE FROM book ";
			query += " WHERE book_id = ? ";

			// (2) 바인딩 (값을 쿼리문문자열 안에 매칭시키기)
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, book_id); // (첫번째 물음표에는 정수형 no 매칭하기)

			// (3) 실행
			count = pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		this.close();
		return count;
	}

	// (5) 책출력 메소드
	public List<BookVo> bookSelect() {

		List<BookVo> bookList = new ArrayList<BookVo>(); // 리스트 생성

		this.getConnect();

		try {
			String query = "";
			query += " SELECT  b.book_id ";
			query += "          ,b.title ";
			query += "          ,b.pubs ";
			query += "          ,b.pub_date ";
			query += "          ,b.author_id ";
			query += "          ,a.author_name ";
			query += " FROM book b, author a ";
			query += " WHERE b.author_id = a.author_id ";

			pstmt = conn.prepareStatement(query); // 변환

			// (2) 바인딩 -- > 물음표매칭 X

			// (3) 실행
			rs = pstmt.executeQuery();
			while (rs.next()) { // 값의 수를 모르기 무한 반복문 while 사용
				int bookId = rs.getInt(1);
				String title = rs.getString(2);
				String pubs = rs.getString(3); // 쿼리문날려서 받아온 값 매칭
				String pubDate = rs.getString(4);
				int authorId = rs.getInt(5);
				String authorName = rs.getString(6);
				
				BookVo bookVo = new BookVo(); // 작가 객체 생성
				bookVo.setBookId(bookId);
				bookVo.setTitle(title);
				bookVo.setPubs(pubs);
				bookVo.setPubDate(pubDate);
				bookVo.setAuthorId(bookId);
				bookVo.setAuthorName(authorName);
				
				bookList.add(bookVo);
				
			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		this.close();
		return bookList;
	}
}