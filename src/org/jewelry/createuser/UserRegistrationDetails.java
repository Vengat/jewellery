package org.jewelry.createuser;

import org.jewelry.utilities.RegexMatch;
import java.util.*;
import java.text.*;

import org.apache.commons.lang3.time.DateUtils;

public class UserRegistrationDetails {
	
	private String userName;
	private String password;
	private String firstName;
	private String lastName;
	private java.util.Date dob;
	private String email;
	private String houseNo;
	private String streetName;
	private String areaName;
	private String city;
	private String state;
	private String pincode;
	private String country;
	
	
	public UserRegistrationDetails(){}
	
	public void setUsername(String userName){
		this.userName=userName;		 
	}
	
	public String getUsername(){
		return this.userName;
	}
	
	public void setPassword(String password){
		this.password = password;
	}
	
	public void setFirstname(String firstName){
		this.firstName = firstName;
	}
	
	public String getFirstname(){
		return this.firstName;
	}
	
	public void setLastname(String lastName){
		this.lastName = lastName;
	}
	
	public String getLastname(){
		return this.lastName;
	}
	
	public void setDob(java.util.Date dob){
		if(isDate(dob))this.dob = dob;
	}
	
	public java.util.Date getDob(){
		return dob;
	}
	
	public boolean isDate(java.util.Date dob){
		String dateofBirth;
		DateFormat formatter ;
    	formatter = new SimpleDateFormat("dd/mm/yyyy");
    	dateofBirth = formatter.format(dob);
		if(dateofBirth.matches("(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((19|20)\\d\\d)")) return true;
		return false;
	}
	
	public void setEmail(String email){
		if(RegexMatch.isEmail(email)){
			this.email=email;
		}else{
			this.email="";
		}
		
	}
	
	public String getEmail(){
		return this.email;
	}
	
	public void setHousedetails(String houseNo){
		this.houseNo = houseNo;
	}
	
	public String getHousedetails(){
		return this.houseNo;
	}
	
	public void setStreetname(String streetName){
		this.streetName = streetName;
	}
	
	public String getStreetname(){
		return this.streetName;
	}
	
	public void setAreaname(String areaName){
		this.areaName = areaName;
	}
	
	public String getAreaname(){
		return this.areaName;
	}
	
	public void setCity(String city){
		this.city=city;
	}
	
	public String getCity(){
		return this.city;
	}
	
	public void setState(String state){
		this.state = state;
	}
	
	public String getState(){
		return this.state;
	}
	
	public void setPincode(String pincode){
		this.pincode=pincode;
	}
	
	public String getPincode(){
		return this.pincode;
	}
	
	public void setCountry(String country){
		this.country=country;
	}
	
	public String getCountry(){
		return country;
	}
	

}
