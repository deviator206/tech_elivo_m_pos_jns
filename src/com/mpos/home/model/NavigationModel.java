/*
 * Copyright (C) 2013 jaggarnaut-studios Pvt. Ltd. All Rights Reserved.
 * jaggarnaut-studios pvt. ltd. PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.mpos.home.model;

import java.io.Serializable;

/**
 * Description-
 * 
 * @author jaggarnaut-studios
 */

public class NavigationModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String navigation_Name;
	private String navigation_Code;
	private int navigation_Type;

	public int getNavigation_Type() {
		return navigation_Type;
	}

	public void setNavigation_Type(int navigation_Type) {
		this.navigation_Type = navigation_Type;
	}

	public String getNavigation_Name() {
		return navigation_Name;
	}

	public void setNavigation_Name(String navigation_Name) {
		this.navigation_Name = navigation_Name;
	}

	public String getNavigation_Code() {
		return navigation_Code;
	}

	public void setNavigation_Code(String navigation_Code) {
		this.navigation_Code = navigation_Code;
	}

	@Override
	public String toString() {
		return "NavigationModel [navigation_Name=" + navigation_Name
				+ ", navigation_Code=" + navigation_Code + ", navigation_Type="
				+ navigation_Type + "]";
	}

}
