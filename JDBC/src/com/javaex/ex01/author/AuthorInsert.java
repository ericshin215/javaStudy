package com.javaex.ex01.author;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthorInsert {
	public static void main(String[] args) {
		
		// 0. import java.sql.*;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
		    // 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
		    // 2. Connection 얻어오기
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			conn = DriverManager.getConnection(url, "webdb", "webdb");

		    // 3. SQL문 준비 / 바인딩 / 실행
			
			// (1) SQL문 준비
			String name = "신재근";
			String desc = "하이미디어";
			
			String query = "";
			query += " INSERT INTO author ";
			query += " VALUES (seq_author_id.nextval, ?, ? )";
			
//			System.out.println(query);
			
			// (2) 바인딩 (값을 쿼리문문자열 안에 매칭시키기)
		    pstmt = conn.prepareStatement(query);
		    pstmt.setString(1, name); // (첫번째 물음표에는 문자열 name 매칭하기)
		    pstmt.setString(2, desc); // (두번째 물음표에는 문자열 desc 매칭하기)
		    
		    // (3) 실행
		    int count = pstmt.executeUpdate();
//		    
//		    System.out.println(count + "건 등록되었습니다.");
		    
		    
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