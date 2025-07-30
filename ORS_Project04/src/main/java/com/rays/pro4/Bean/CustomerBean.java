package com.rays.pro4.Bean;

public class CustomerBean extends BaseBean {
	
	private String name;
	private String location;
	private String phoneNumber;
	private int importance;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public int getImportance() {
		return importance;
	}
	public void setImportance(int importance) {
		this.importance = importance;
	}
	@Override
	public String getkey() {
		// TODO Auto-generated method stub
		return id+"";
	}
	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return location+"";
	}
	
	
	

}
