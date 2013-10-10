/*
 * Copyright (C) 2013 jaggarnaut-studios Pvt. Ltd. All Rights Reserved.
 * jaggarnaut-studios pvt. ltd. PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.mpos.framework.Util;

import java.io.Serializable;

/**
 * Description-
 * 
 * @author jaggarnaut-studios
 */

public class UserDetailModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String grpCode;
	private String userID;
	private String userName;
	private String password;

	public String getGrpCode() {
		return grpCode;
	}

	public void setGrpCode(String grpCode) {
		this.grpCode = grpCode;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "UserDetailModel [grpCode=" + grpCode + ", userID=" + userID
				+ ", userName=" + userName + ", password=" + password + "]";
	}

}
