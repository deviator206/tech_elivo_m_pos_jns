/*
 * Copyright (C) 2013 jaggarnaut-studios Pvt. Ltd. All Rights Reserved.
 * jaggarnaut-studios pvt. ltd. PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.mpos.home.fragment;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.mpos.R;
import com.mpos.framework.Util.UserSingleton;
import com.mpos.framework.common.Constants;
import com.mpos.framework.common.Logger;
import com.mpos.framework.common.OperationalResult;
import com.mpos.framework.inf.BaseResponseListener;
import com.mpos.master.model.CURRENCY_MST_MODEL;
import com.mpos.payment.adapter.CurencySpinnerAdapter;
import com.mpos.payment.helper.PaymentHelper;
import com.mpos.payment.model.MULTI_CHNG_MST_Model;

/**
 * Description-
 * 
 * @author jaggarnaut-studios
 */

public class IsssueChangeFragment extends DialogFragment implements
		BaseResponseListener {

	public static final String TAG = Constants.APP_TAG
			+ IsssueChangeFragment.class.getSimpleName();

	private final String ALERT_FRAGMENT = "ALERT_FRAGMENT";
	private View issueChangeView;
	private Button mAddBtn;
	private Button mDoneBtn;
	private Button mCancleBtn;
	private EditText mAmtEt;
	private Spinner mCurrencySpinner;
	private LinearLayout mIssueChangeLL;
	private ProgressDialog mProgressDialog;

	private double mTotalPaymentMadeByCustomer = 150.0;
	private double mTotalIssueGivenToCustomer = 0.0; // Hard Coded remove
	private double mTotalPayment = 0.0;

	private ArrayList<CURRENCY_MST_MODEL> mCurrencyList = null;
	private ArrayList<MULTI_CHNG_MST_Model> mIssueChngList = null;
	private CURRENCY_MST_MODEL mBaseCurrency = null;

	private OnClickListener mClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {

			InputMethodManager inputManager = (InputMethodManager) getContext()
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			if (getActivity().getCurrentFocus() != null
					&& getActivity().getCurrentFocus().getWindowToken() != null) {
				inputManager.hideSoftInputFromWindow(getActivity()
						.getCurrentFocus().getWindowToken(),
						InputMethodManager.HIDE_NOT_ALWAYS);
			}

			switch (v.getId()) {
			case R.id.addBtn:
				if (mAmtEt.getText().toString().trim().length() > 0) {
					setIssueAmout();
					showIssueChangeDetails();
				} else {
					// If not, Open Alert Dialog and show alert
					showAlertDialog(getResources().getString(
							R.string.alert_wrong_amt));
				}
				break;

			case R.id.doneBtn:
				if (mIssueChngList != null && mIssueChngList.size() > 0) {
					insertMultiChngeInDB();
				} else {
					showAlertDialog(getResources().getString(
							R.string.alert_wrong_inputs));
				}
				break;

			case R.id.cancelBtn:
				// Check while canceling issue change if any change issue made
				// and ask for confirmation.
				if (mIssueChngList != null && mIssueChngList.size() > 0) {
					showAlertMessage(
							getResources().getString(
									R.string.alert_issue_discard),
							getResources().getString(
									R.string.alert_issue_entry_present));
				} else {
					if (getDialog() != null && getDialog().isShowing())
						getDialog().dismiss();
				}
				break;

			default:
				break;
			}
		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		issueChangeView = inflater
				.inflate(R.layout.issue_change_fragment, null);
		getDialog().setTitle(getResources().getString(R.string.issue_change));

		componentInitialization();

		Bundle bundle = getArguments();
		mTotalPayment = bundle.getDouble(BillFragment.BILL_TOTAL);

		startProgress();
		PaymentHelper paymentHelper = new PaymentHelper(this);
		paymentHelper.fetchAllCurrenciesFromDB();

		return issueChangeView;
	}

	/**
	 * Method- To insert all change values in DB
	 */
	protected void insertMultiChngeInDB() {
		PaymentHelper paymentHelper = new PaymentHelper(this);
		paymentHelper.insert_MULTI_CHNG_ENTRIES(mIssueChngList);
	}

	private void componentInitialization() {
		mAddBtn = (Button) issueChangeView.findViewById(R.id.addBtn);
		mDoneBtn = (Button) issueChangeView.findViewById(R.id.doneBtn);
		mCancleBtn = (Button) issueChangeView.findViewById(R.id.cancelBtn);
		mAmtEt = (EditText) issueChangeView.findViewById(R.id.amount_ET);
		mIssueChangeLL = (LinearLayout) issueChangeView
				.findViewById(R.id.issueChangeDetailsLL);
		mCurrencySpinner = (Spinner) issueChangeView
				.findViewById(R.id.currencySpinner);

		mAddBtn.setOnClickListener(mClickListener);
		mDoneBtn.setOnClickListener(mClickListener);
		mCancleBtn.setOnClickListener(mClickListener);

		mIssueChngList = new ArrayList<MULTI_CHNG_MST_Model>();
	}

	protected void setCurrencyUI() {
		if (mCurrencyList != null) {
			CurencySpinnerAdapter adapter = new CurencySpinnerAdapter(
					getContext(), android.R.layout.simple_spinner_item,
					mCurrencyList);
			mCurrencySpinner.setAdapter(adapter);
		}
	}

	/**
	 * Method- To create issue change
	 */
	protected void setIssueAmout() {
		MULTI_CHNG_MST_Model issueCashModel = new MULTI_CHNG_MST_Model();

		// amount in selected currency
		issueCashModel.setChngAmt(Double.parseDouble(mAmtEt.getText()
				.toString().trim()));

		// Currency Abbr
		issueCashModel.setCurrAbbr("" + mCurrencySpinner.getSelectedItem());

		// ExRate and Operator
		for (CURRENCY_MST_MODEL model : mCurrencyList) {
			if (model.getCurr_abbrName().equalsIgnoreCase(
					issueCashModel.getCurrAbbr())) {
				issueCashModel.setExRate(model.getCurr_exrate());
				issueCashModel.setOperator(model.getCurr_operator());
				break;
			}
		}

		// amount in base currency
		if (issueCashModel.getOperator().equalsIgnoreCase("*")) {
			issueCashModel.setLocAmt(issueCashModel.getChngAmt()
					* issueCashModel.getExRate());
		} else {
			issueCashModel.setLocAmt(issueCashModel.getChngAmt()
					/ issueCashModel.getExRate());
		}

		// Payment No.
		issueCashModel
				.setPaymentNo(UserSingleton.getInstance(getContext()).mUniqueTrasactionNo);

		mIssueChngList.add(issueCashModel);

		mTotalIssueGivenToCustomer += issueCashModel.getLocAmt();
	}

	/**
	 * This method is used to show all the issue change details
	 */
	protected void showIssueChangeDetails() {
		LinearLayout issueChngListView = (LinearLayout) issueChangeView
				.findViewById(R.id.issueChangeDetailsLL);
		issueChngListView.removeAllViews();

		// First Blank row
		LinearLayout blankRow1 = (LinearLayout) LayoutInflater.from(
				getContext()).inflate(R.layout.payment_detail_row, null);
		((TextView) blankRow1.findViewById(R.id.paymentText))
				.setText(getResources().getString(R.string.payment_due));
		blankRow1.findViewById(R.id.paymentText).setVisibility(View.INVISIBLE);
		issueChngListView.addView(blankRow1);

		// Issue chage Due row
		View paymentDuewRow = LayoutInflater.from(getContext()).inflate(
				R.layout.payment_detail_row, null);
		((TextView) paymentDuewRow.findViewById(R.id.paymentText))
				.setText(getResources().getString(R.string.change_due));
		// Calculate Amount due
		if (mTotalPaymentMadeByCustomer > mTotalPayment) {
			mTotalIssueGivenToCustomer = mTotalPaymentMadeByCustomer
					- mTotalPayment;
			((TextView) paymentDuewRow.findViewById(R.id.paymentamount))
					.setText("" + mTotalIssueGivenToCustomer);
		} else {
			((TextView) paymentDuewRow.findViewById(R.id.paymentamount))
					.setText("0");
		}
		issueChngListView.addView(paymentDuewRow);

		// Second Blank row
		LinearLayout blankRow2 = (LinearLayout) LayoutInflater.from(
				getContext()).inflate(R.layout.payment_detail_row, null);
		((TextView) blankRow2.findViewById(R.id.paymentText))
				.setText(getResources().getString(R.string.payment_due));
		blankRow2.findViewById(R.id.paymentText).setVisibility(View.INVISIBLE);
		issueChngListView.addView(blankRow2);

		// Change Issued
		if (mIssueChngList != null && mIssueChngList.size() > 0) {
			for (MULTI_CHNG_MST_Model issueModel : mIssueChngList) {
				View paymentRow = LayoutInflater.from(getContext()).inflate(
						R.layout.payment_detail_row, null);

				((TextView) paymentRow.findViewById(R.id.paymentText))
						.setText(getResources().getString(
								R.string.change_issued)
								+ issueModel.getCurrAbbr() // Selected Curr
								+ " " + issueModel.getLocAmt()); // AMt in Base
																	// curr

				((TextView) paymentRow.findViewById(R.id.paymentamount))
						.setText("" + issueModel.getLocAmt());

				issueChngListView.addView(paymentRow);
			}
		}

		LinearLayout blankRow3 = (LinearLayout) LayoutInflater.from(
				getContext()).inflate(R.layout.payment_detail_row, null);
		((TextView) blankRow3.findViewById(R.id.paymentText))
				.setText(getResources().getString(R.string.payment_due));
		blankRow3.findViewById(R.id.paymentText).setVisibility(View.INVISIBLE);
		issueChngListView.addView(blankRow3);

		// Total AMount Issued in base curr
		View totalPaymentRow = LayoutInflater.from(getContext()).inflate(
				R.layout.payment_detail_row, null);
		((TextView) totalPaymentRow.findViewById(R.id.paymentText))
				.setText(getResources().getString(R.string.total_change_issued)
						+ mBaseCurrency.getCurr_abbrName());

		double totalChangeIssued = 0.0;
		for (MULTI_CHNG_MST_Model chngModel : mIssueChngList) {
			totalChangeIssued += chngModel.getLocAmt();
		}

		((TextView) totalPaymentRow.findViewById(R.id.paymentamount))
				.setText("" + totalChangeIssued);
		issueChngListView.addView(totalPaymentRow);

		// Change Issue still Due
		View changeDueRow = LayoutInflater.from(getContext()).inflate(
				R.layout.payment_detail_row, null);
		((TextView) changeDueRow.findViewById(R.id.paymentText))
				.setText(getResources().getString(R.string.change_still_due)
						+ " " + mBaseCurrency.getCurr_abbrName());
		if (mTotalPaymentMadeByCustomer > mTotalPayment) {
			((TextView) changeDueRow.findViewById(R.id.paymentamount))
					.setText(""
							+ (mTotalIssueGivenToCustomer - totalChangeIssued));
		} else {
			((TextView) changeDueRow.findViewById(R.id.paymentamount))
					.setText("0");
		}
		issueChngListView.addView(changeDueRow);

		// If no issue change required, then disable Add button and amount et
		/*if (totalChangeIssued >= mTotalIssueGivenToCustomer) {
			mAddBtn.setClickable(false);
			mAmtEt.setEnabled(false);
			mAmtEt.setText(""); // CLear edittext
		} else {
			mAddBtn.setClickable(true);
			mAmtEt.setEnabled(true);
		}*/
		//mAmtEt.setText(""); // CLear edittext
	}

	@Override
	public Context getContext() {
		return getActivity();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void executePostAction(OperationalResult opResult) {
		Logger.d(TAG, "executePostAction");
		switch (opResult.getResultCode()) {
		case Constants.FETCH_CURRENCIES:
			stopProgress();
			mCurrencyList = (ArrayList<CURRENCY_MST_MODEL>) opResult
					.getResult().getSerializable(Constants.RESULTCODEBEAN);

			if (mCurrencyList != null && mCurrencyList.size() > 0) {
				Logger.d(TAG, "FETCH_CURRENCIES" + mCurrencyList);
				mBaseCurrency = getBaseCurrency();
				setCurrencyUI();
				showIssueChangeDetails();
			}
			break;

		case Constants.INSERT_MULTI_CHNG_RECORDS:
			Logger.d(TAG, "INSERT_MULTI_CHNG_RECORDS Done");
			
			if (getDialog() != null && getDialog().isShowing())
				getDialog().dismiss();
			break;

		default:
			break;
		}
	}

	@Override
	public void errorReceived(OperationalResult opResult) {
		// TODO Auto-generated method stub
		Logger.d(TAG, "errorReceived");
	}

	/**
	 * To get Base currency
	 * 
	 * @return
	 */
	private CURRENCY_MST_MODEL getBaseCurrency() {
		CURRENCY_MST_MODEL baseCurrency = null;
		if (mCurrencyList != null) {
			for (CURRENCY_MST_MODEL currencyModel : mCurrencyList) {
				if (currencyModel.getCurr_base_flag().equalsIgnoreCase("y")) {
					baseCurrency = currencyModel;
					break;
				}
			}
		}
		return baseCurrency;
	}

	/**
	 * This method will start ProgressDialog indicating background activity.
	 */
	public void startProgress() {
		if (mProgressDialog == null) {
			mProgressDialog = new ProgressDialog(getContext());
		}
		if (!mProgressDialog.isShowing()) {
			mProgressDialog.setMessage("Loading");
			mProgressDialog.setCanceledOnTouchOutside(false);
			mProgressDialog.show();
			Logger.d(TAG, "startProgress");
		}

	}

	public void stopProgress() {
		if (mProgressDialog != null && mProgressDialog.isShowing()) {
			Logger.d(TAG, "stopProgressBar");
			mProgressDialog.cancel();
			mProgressDialog = null;
		}
	}

	/**
	 * Method- To show alert dialog
	 */
	private void showAlertDialog(String message) {
		FragmentManager fm = getActivity().getSupportFragmentManager();
		AlertDialogFragment alertDialogFragment = new AlertDialogFragment();
		Bundle bundle = new Bundle();
		bundle.putString(Constants.KEY, message);
		alertDialogFragment.setArguments(bundle);
		alertDialogFragment.show(fm, ALERT_FRAGMENT);
	}

	/**
	 * Method- Show Local Alert
	 * 
	 * @param szAlert
	 * @param szMessage
	 * @param isFinishRequired
	 */
	public void showAlertMessage(String szAlert, String szMessage) {
		if (getDialog() != null && getDialog().isShowing()) {
			AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
			builder.setCancelable(false)
					.setPositiveButton(getResources().getString(R.string.ok),
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									dialog.dismiss();
									getDialog().dismiss();
								}
							})
					.setNegativeButton(
							getResources().getString(R.string.cancel),
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									dialog.dismiss();
								}
							}).setMessage(szMessage).setTitle(szAlert);
			AlertDialog alert = builder.create();
			alert.setCancelable(false);
			alert.show();
		}
	}
}
