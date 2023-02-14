package com.inpectotpm.springwebtesting;

import java.util.Date;

public class User {

	private int id;
	private String username;
	private String email;
	private Date createdAt;
	private boolean isDeleted;

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getCreatedAt() {
		return this.createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public boolean isDeleted() {
		return this.isDeleted;
	}

	public void setDeleted(boolean deleted) {
		this.isDeleted = deleted;
	}
}
