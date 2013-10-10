/*
 * Copyright (C) 2013  All Rights Reserved.
 *  PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.mpos.master.model;

import java.io.Serializable;

import android.content.ContentValues;

import com.mpos.framework.common.DBConstants;

/**
 * Description-
 * 

 */

public class USR_ASSGND_RGHTSModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String FLD_TYPE;
	private String RGHT_VALUE;
	private String RFRNCE_ID;
	private String RGHT_NAME;

	private String RGHT_EDIT;
	private String USR_ID;
	private String GRP_ID;
	private String RGHTDEL;
	private String RGHTADD;
	private String BRNCHCODE;
	private String RGHTVIEW;

	public String getRGHT_EDIT() {
		return RGHT_EDIT;
	}

	public void setRGHT_EDIT(String rGHT_EDIT) {
		RGHT_EDIT = rGHT_EDIT;
	}

	public String getUSR_ID() {
		return USR_ID;
	}

	public void setUSR_ID(String uSR_ID) {
		USR_ID = uSR_ID;
	}

	public String getGRP_ID() {
		return GRP_ID;
	}

	public void setGRP_ID(String gRP_ID) {
		GRP_ID = gRP_ID;
	}

	public String getRGHTDEL() {
		return RGHTDEL;
	}

	public void setRGHTDEL(String rGHTDEL) {
		RGHTDEL = rGHTDEL;
	}

	public String getRGHTADD() {
		return RGHTADD;
	}

	public void setRGHTADD(String rGHTADD) {
		RGHTADD = rGHTADD;
	}

	public String getBRNCHCODE() {
		return BRNCHCODE;
	}

	public void setBRNCHCODE(String bRNCHCODE) {
		BRNCHCODE = bRNCHCODE;
	}

	public String getRGHTVIEW() {
		return RGHTVIEW;
	}

	public void setRGHTVIEW(String rGHTVIEW) {
		RGHTVIEW = rGHTVIEW;
	}

	public String getFLD_TYPE() {
		return FLD_TYPE;
	}

	public void setFLD_TYPE(String fLD_TYPE) {
		FLD_TYPE = fLD_TYPE;
	}

	public String getRGHT_VALUE() {
		return RGHT_VALUE;
	}

	public void setRGHT_VALUE(String rGHT_VALUE) {
		RGHT_VALUE = rGHT_VALUE;
	}

	public String getRFRNCE_ID() {
		return RFRNCE_ID;
	}

	public void setRFRNCE_ID(String rFRNCE_ID) {
		RFRNCE_ID = rFRNCE_ID;
	}

	public String getRGHT_NAME() {
		return RGHT_NAME;
	}

	public void setRGHT_NAME(String rGHT_NAME) {
		RGHT_NAME = rGHT_NAME;
	}

	@Override
	public String toString() {
		return "USR_ASSGND_RGHTS [FLD_TYPE=" + FLD_TYPE + ", RGHT_VALUE="
				+ RGHT_VALUE + ", RFRNCE_ID=" + RFRNCE_ID + ", RGHT_NAME="
				+ RGHT_NAME + "]";
	}

	public static ContentValues getcontentvalues(USR_ASSGND_RGHTSModel model) {
		ContentValues values = new ContentValues();
		values.put(DBConstants.RFRNCE_ID, model.getRFRNCE_ID());
		values.put(DBConstants.RGHT_NAME, model.getRGHT_NAME());
		values.put(DBConstants.FLD_TYPE, model.getFLD_TYPE());
		values.put(DBConstants.RGHT_VALUE, model.getRGHT_VALUE());

		return values;
	}

}
