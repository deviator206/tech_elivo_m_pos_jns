package com.mpos.payment.model;

import java.io.Serializable;

public class ChequePMTModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String mBankName;
	private String mBranchName;
	private String mChequeNumber;
	private String mBranchCode;
	private String mAccountNumber;
	private String mDateOnCheque;
	private String mNote;
	private float mAmount;
	public String getmBankName() {
		return mBankName;
	}
	public void setmBankName(String mBankName) {
		this.mBankName = mBankName;
	}
	public String getmBranchName() {
		return mBranchName;
	}
	public void setmBranchName(String mBranchName) {
		this.mBranchName = mBranchName;
	}
	public String getmChequeNumber() {
		return mChequeNumber;
	}
	public void setmChequeNumber(String mChequeNumber) {
		this.mChequeNumber = mChequeNumber;
	}
	public String getmBranchCode() {
		return mBranchCode;
	}
	public void setmBranchCode(String mBranchCode) {
		this.mBranchCode = mBranchCode;
	}
	public String getmAccountNumber() {
		return mAccountNumber;
	}
	public void setmAccountNumber(String mAccountNumber) {
		this.mAccountNumber = mAccountNumber;
	}
	public String getmDateOnCheque() {
		return mDateOnCheque;
	}
	public void setmDateOnCheque(String mDateOnCheque) {
		this.mDateOnCheque = mDateOnCheque;
	}
	public String getmNote() {
		return mNote;
	}
	public void setmNote(String mNote) {
		this.mNote = mNote;
	}
	public float getmAmount() {
		return mAmount;
	}
	public void setmAmount(float mAmount) {
		this.mAmount = mAmount;
	}
	@Override
	public String toString() {
		return "ChequePMTModel [mBankName=" + mBankName + ", mBranchName="
				+ mBranchName + ", mChequeNumber=" + mChequeNumber
				+ ", mBranchCode=" + mBranchCode + ", mAccountNumber="
				+ mAccountNumber + ", mDateOnCheque=" + mDateOnCheque
				+ ", mNote=" + mNote + ", mAmount=" + mAmount + "]";
	}
	
	
}
