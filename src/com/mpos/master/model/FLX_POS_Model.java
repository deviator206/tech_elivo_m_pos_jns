package com.mpos.master.model;

import java.io.Serializable;

import android.content.ContentValues;

import com.mpos.framework.common.DBConstants;

public class FLX_POS_Model implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String BILLHEADING1;
	private String BILLHEADING2;
	private String BILLHEADING3;
	private String BILLHEADING4;
	private String BILLHEADING5;
	private String BILLHEADING6;
	private String BILLHEADING7;
	private String BILLHEADING8;
	public String getBILLHEADING1() {
		return BILLHEADING1;
	}
	public void setBILLHEADING1(String bILLHEADING1) {
		BILLHEADING1 = bILLHEADING1;
	}
	public String getBILLHEADING2() {
		return BILLHEADING2;
	}
	public void setBILLHEADING2(String bILLHEADING2) {
		BILLHEADING2 = bILLHEADING2;
	}
	public String getBILLHEADING3() {
		return BILLHEADING3;
	}
	public void setBILLHEADING3(String bILLHEADING3) {
		BILLHEADING3 = bILLHEADING3;
	}
	public String getBILLHEADING4() {
		return BILLHEADING4;
	}
	public void setBILLHEADING4(String bILLHEADING4) {
		BILLHEADING4 = bILLHEADING4;
	}
	public String getBILLHEADING5() {
		return BILLHEADING5;
	}
	public void setBILLHEADING5(String bILLHEADING5) {
		BILLHEADING5 = bILLHEADING5;
	}
	public String getBILLHEADING6() {
		return BILLHEADING6;
	}
	public void setBILLHEADING6(String bILLHEADING6) {
		BILLHEADING6 = bILLHEADING6;
	}
	public String getBILLHEADING7() {
		return BILLHEADING7;
	}
	public void setBILLHEADING7(String bILLHEADING7) {
		BILLHEADING7 = bILLHEADING7;
	}
	public String getBILLHEADING8() {
		return BILLHEADING8;
	}
	public void setBILLHEADING8(String bILLHEADING8) {
		BILLHEADING8 = bILLHEADING8;
	}
	
	public static ContentValues getcontentvalues(FLX_POS_Model dataModel) {
		
		ContentValues values = new ContentValues();
		values.put(DBConstants.Bill_Heading1, dataModel.getBILLHEADING1());
		values.put(DBConstants.Bill_Heading2, dataModel.getBILLHEADING2());
		values.put(DBConstants.Bill_Heading3, dataModel.getBILLHEADING3());
		values.put(DBConstants.Bill_Heading4, dataModel.getBILLHEADING4());
		values.put(DBConstants.Bill_Heading5, dataModel.getBILLHEADING5());
		values.put(DBConstants.Bill_Heading6, dataModel.getBILLHEADING6());
		values.put(DBConstants.Bill_Heading7, dataModel.getBILLHEADING7());
		values.put(DBConstants.Bill_Heading8, dataModel.getBILLHEADING8());
		return values;
	}

	
}
