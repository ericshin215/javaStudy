package com.javaex.ex01.book;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookDelete {
	
	public static void main(String[] args) {

		// 0. import java.sql.*;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			// 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName("oracle.jdbc.driver.OracleDriver"); // 다른 데이터베이스를 사용하면 바뀔 수 있다

			// 2. Connection 얻어오기
			String url = "jdbc:oracle:thin:@localhost:1521:xe"; // 다른컴퓨터에 쓰면 아이피주소를 넣는다:포트번호도 바뀔 수 있음
			conn = DriverManager.getConnection(url, "webdb", "webdb"); // 위에쓴 주소와 데이터베이스 아이디,비번입력

			// 3. SQL문 준비 / 바인딩 / 실행

			// (1) SQL문 준비
			int no = 1;

			String query = "";
			query += " DELETE FROM book ";
			query += " WHERE book_id = ? ";

			// (2) 바인딩 (값을 쿼리문문자열 안에 매칭시키기)
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, no); // (첫번째 물음표에는 정수형 no 매칭하기)

			// (3) 실행
			int count = pstmt.executeUpdate();

			System.out.println(count + "건 삭제되었습니다.");

			// 4.결과처리
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

	}


}
