package com.javaex.phonebook1;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class draft {
	
	public List<PersonVo> personSearch(String searchName) {

		List<PersonVo> personList = new ArrayList<PersonVo>(); // 리스트 생성

		this.getConnect();

		try {
			
			String query = "";
			query += " SELECT   person_id ";
			query += "          ,name ";
			query += "          ,hp ";
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

}
