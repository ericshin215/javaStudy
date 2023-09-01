package com.javaex.phonebook1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



public class PersonDao {

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
	public int personInsert(String name, String hp, String company ) {
		int count = -1;
		this.getConnect();

		try {
			// 3. SQL문 준비 / 바인딩 / 실행

			// (1) SQL문 준비
			String query = "";
			query += " INSERT INTO person ";
			query += " VALUES (seq_person_id.nextval, ?, ?, ?)";

			// (2) 바인딩 (값을 쿼리문문자열 안에 매칭시키기)
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, name); // (첫번째 물음표에는 문자열 title 매칭하기)
			pstmt.setString(2, hp); // (두번째 물음표에는 문자열 pubs 매칭하기)
			pstmt.setString(3, company); // (세번째 물음표에는 문자열 pub_date 매칭하기)
//			pstmt.setInt(4, authorId); // (네번째 물음표에는 문자열 author_id 매칭하기)
			// (3) 실행
			count = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		this.close();
		return count;
	}

	// (3) 책수정 메소드
	public int personUpdate(String title, String author_id) {
		int count = -1;
		this.getConnect();

		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "";
			query += " UPDATE person ";
			query += " set  = ? ";
			query += " where title = ? ";

			pstmt = conn.prepareStatement(query);

			// (2) 바인딩 (값을 쿼리문문자열 안에 매칭시키기)
			pstmt.setString(1, author_id);
			pstmt.setString(2, title);

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
	public int personDelete(int person_id) {
		int count = -1;
		this.getConnect();

		try {
			String query = "";
			query += " DELETE FROM person ";
			query += " WHERE person_id = ? ";

			// (2) 바인딩 (값을 쿼리문문자열 안에 매칭시키기)
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, person_id); // (첫번째 물음표에는 정수형 no 매칭하기)

			// (3) 실행
			count = pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		this.close();
		return count;
	}

	// (5) 책출력 메소드
	public List<PersonVo> personSelect() {

		List<PersonVo> personList = new ArrayList<PersonVo>(); // 리스트 생성

		this.getConnect();

		try {
			// 3. SQL문 준비 / 바인딩 / 실행

			// (1) SQL문 준비
			String query = "";
			query += " SELECT   person_id ";
			query += "          ,name ";
			query += "          ,hp ";
			query += "          ,company ";
			query += " FROM person ";

			pstmt = conn.prepareStatement(query); // 변환
			// (2) 바인딩 -- > 물음표매칭 X
			
			// (3) 실행
			rs = pstmt.executeQuery();
			// 4.결과처리
			while (rs.next()) { // 값의 수를 모르기 무한 반복문 while 사용
				int person_id = rs.getInt(1);
				String name = rs.getString(2);
				String hp = rs.getString(3); // 쿼리문날려서 받아온 값 매칭
				String company = rs.getString(4); // 쿼리문날려서 받아온 값 매칭

				PersonVo personVo = new PersonVo(); // 작가 객체 생성
				personVo.setPerson_id(person_id);
				personVo.setName(name);
				personVo.setHp(hp); // 각각 값넣기
				personVo.setCompany(company); // 각각 값넣기

				personList.add(personVo); // 리스트에 넣기
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		this.close();
		return personList;
	}// authorSelect
	
	
	public List<PersonVo> personSearch1(String search) {
//		int count = -1;
		List<PersonVo> personList = new ArrayList<PersonVo>(); // 리스트 생성

		this.getConnect();

		try {
			String query = "";
			query += " SELECT  person_id ";
			query += "         ,name ";
			query += "         ,hp ";
			query += "         ,company ";
			query += " FROM person ";
			query += " where name like '%'||?||'%'  ";

			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, search);

//			count = pstmt.executeUpdate();

			rs = pstmt.executeQuery();

			while (rs.next()) { // 값의 수를 모르기 무한 반복문 while 사용
				int personId = rs.getInt(1);
				String Name = rs.getString(2);
				String hp = rs.getString(3);
				String company = rs.getString(4);

				PersonVo personVo = new PersonVo(); // 사람 객체 생성
				personVo.setPerson_id(personId);
				personVo.setName(Name);
				personVo.setHp(hp);
				personVo.setCompany(company);

				personList.add(personVo); // 리스트에 넣기
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		this.close();

		return personList;
	}// personSelect

	
	
	public List<PersonVo> personSearch(String searchName) {

		List<PersonVo> personList = new ArrayList<PersonVo>(); // 리스트 생성

		this.getConnect();

		try {
			
			String query = "";
			query += " SELECT   person_id ";
			query += "          ,name ";
			query += "          ,hp ";
			query += "          ,company ";
			query += " FROM person ";
			query += " where name Like '%'||?||'%' ";
			
			
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, searchName);
			
			
			
			rs = pstmt.executeQuery();
			// 4.결과처리
			while (rs.next()) { 
				int person_id = rs.getInt(1);
				String name = rs.getString(2);
				String hp = rs.getString(3); 
				String company = rs.getString(4);

				PersonVo personVo = new PersonVo();
				personVo.setPerson_id(person_id);
				personVo.setName(name);
				personVo.setHp(hp);
				personVo.setCompany(company);

				personList.add(personVo);
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		this.close();
		return personList;
	}// authorSelect
}