package com.mpos.payment.model;

import java.io.Serializable;

public class CashPMTModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private float mAmount;
	private String mCurrency;

	public float getmAmount() {
		return mAmount;
	}

	public void setmAmount(float mAmount) {
		this.mAmount = mAmount;
	}

	public String getmCurrency() {
		return mCurrency;
	}

	public void setmCurrency(String mCurrency) {
		this.mCurrency = mCurrency;
	}

	@Override
	public String toString() {
		return "CashPMTModel [mAmount=" + mAmount + ", mCurrency=" + mCurrency
				+ "]";
	}

}
