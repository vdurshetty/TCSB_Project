package com.sri.tcsb.db.dto;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table (name="users")
public class User {
			
	@GenericGenerator(name="generator", strategy="increment")
	@GeneratedValue(generator="generator")
	@Column(name = "uid")
	@Id
	private int  uid;
	
	@Column(name = "UserName")
    private String userName;
	
	@Column(name = "FullName")
	private String fullName;
	

	@Column(name = "email")
	private String email;
	
	@Column(name = "phone")
	private String phone;
	
	
	@Column(name = "pwd")
	private String pwd;
	
	@OneToMany (mappedBy="pUser", cascade=CascadeType.ALL, fetch = FetchType.LAZY) 
	private List<UserPermissions> userPermissions;
	
	
	public User() {
		super();
	}


	public int getUid() {
		return uid;
	}


	public void setUid(int uid) {
		this.uid = uid;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getFullName() {
		return fullName;
	}


	public void setFullName(String fullName) {
		this.fullName = fullName;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}



	public String getPwd() {
		return pwd;
	}


	public void setPwd(String pwd) {
		this.pwd = pwd;
	}


	public List<UserPermissions> getUserPermissions() {
		return userPermissions;
	}


	public void setUserPermissions(List<UserPermissions> userPermissions) {
		this.userPermissions = userPermissions;
	}



	
	
}