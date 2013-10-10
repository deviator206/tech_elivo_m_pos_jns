package com.mpos.payment.model;

import java.io.Serializable;

public class CreditCardPMTModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String mNameonCard;
	private String mCardNumber;
	private String mExpiryDate;
	private String mAuthorizationCode;
	private String mNotes;
	private float mAmount;
	public String getmNameonCard() {
		return mNameonCard;
	}
	public void setmNameonCard(String mNameonCard) {
		this.mNameonCard = mNameonCard;
	}
	public String getmCardNumber() {
		return mCardNumber;
	}
	public void setmCardNumber(String mCardNumber) {
		this.mCardNumber = mCardNumber;
	}
	public String getmExpiryDate() {
		return mExpiryDate;
	}
	public void setmExpiryDate(String mExpiryDate) {
		this.mExpiryDate = mExpiryDate;
	}
	public String getmAuthorizationCode() {
		return mAuthorizationCode;
	}
	public void setmAuthorizationCode(String mAuthorizationCode) {
		this.mAuthorizationCode = mAuthorizationCode;
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
		return "CreditCardPMTModel [mNameonCard=" + mNameonCard
				+ ", mCardNumber=" + mCardNumber + ", mExpiryDate="
				+ mExpiryDate + ", mAuthorizationCode=" + mAuthorizationCode
				+ ", mNotes=" + mNotes + ", mAmount=" + mAmount + "]";
	}

	
}
