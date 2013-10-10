package com.mpos.payment.model;

import java.io.Serializable;

public class CreditNotePMTModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String mCreditNoteNumber;
	private String mIssueDate;
	private String mExpiryDate;
	private String mNotes;
	private float mAmount;
	public String getmCreditNoteNumber() {
		return mCreditNoteNumber;
	}
	public void setmCreditNoteNumber(String mCreditNoteNumber) {
		this.mCreditNoteNumber = mCreditNoteNumber;
	}
	public String getmIssueDate() {
		return mIssueDate;
	}
	public void setmIssueDate(String mIssueDate) {
		this.mIssueDate = mIssueDate;
	}
	public String getmExpiryDate() {
		return mExpiryDate;
	}
	public void setmExpiryDate(String mExpiryDate) {
		this.mExpiryDate = mExpiryDate;
	}
	public String getmNotes() {
		return mNotes;
	}
	public void setmNotes(String mNotes) {
		this.mNotes = mNotes;
	}
	public float getmAmount() {
		return mAmount;
	}
	public void setmAmount(float mAmount) {
		this.mAmount = mAmount;
	}
	@Override
	public String toString() {
		return "CreditNotePMTModel [mCreditNoteNumber=" + mCreditNoteNumber
				+ ", mIssueDate=" + mIssueDate + ", mExpiryDate=" + mExpiryDate
				+ ", mNotes=" + mNotes + ", mAmount=" + mAmount + "]";
	}
	
}
