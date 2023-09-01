package com.javaex.phonebook1;

public class PersonVo {

	// 필드
	private int person_id;
	private String name;
	private String hp;
	private String company;
//	private int authorId;
//	private String authorName;

	// 생성자
	public PersonVo() {
		
	}



	public PersonVo(int person_id, String name, String hp, String company) {
	super();
	this.person_id = person_id;
	this.name = name;
	this.hp = hp;
	this.company = company;
}



	// 메소드-gs
	public int getPerson_id() {
		return person_id;
	}



	public void setPerson_id(int person_id) {
		this.person_id = person_id;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getHp() {
		return hp;
	}



	public void setHp(String hp) {
		this.hp = hp;
	}



	public String getCompany() {
		return company;
	}



	public void setCompany(String company) {
		this.company = company;
	}
	
	
	
	// 메소드-일반
	
	@Override
	public String toString() {
		return person_id + ".   " + name + "   " + hp + "    " + company ;
			
	}




	

}