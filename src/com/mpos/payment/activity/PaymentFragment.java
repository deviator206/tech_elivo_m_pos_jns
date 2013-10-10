package com.mpos.payment.activity;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.mpos.R;
import com.mpos.application.BaseActivity;
import com.mpos.framework.Util.UserSingleton;
import com.mpos.framework.common.Config;
import com.mpos.framework.common.Constants;
import com.mpos.framework.common.Logger;
import com.mpos.framework.common.OperationalResult;
import com.mpos.framework.inf.BaseResponseListener;
import com.mpos.home.fragment.AlertDialogFragment;
import com.mpos.home.fragment.BillFragment;
import com.mpos.login.activity.LogInActivity;
import com.mpos.master.model.CURRENCY_MST_MODEL;
import com.mpos.payment.adapter.CurencySpinnerAdapter;
import com.mpos.payment.helper.PaymentHelper;
import com.mpos.payment.model.CashPMTModel;
import com.mpos.payment.model.MULTI_CHNG_MST_Model;
import com.mpos.payment.model.MULTY_PMT_MST_Model;
import com.mpos.payment.model.PAYMT_MST_MODEL;
import com.mpos.transactions.activity.OfflineTxnUploadHelper;

public class PaymentFragment extends BaseActivity implements
		BaseResponseListener {

	public static final String TAG = Constants.APP_TAG
			+ PaymentFragment.class.getSimpleName();

	private final String ALERT_FRAGMENT = "ALERT_FRAGMENT";

	// private DecimalFormat df = new DecimalFormat("0.00");
	private DecimalFormat df = Constants.getDecimalFormat();
	private RadioGroup mRadioGroup;
	private RadioButton mRadioButton;
	private final int PYMNTS = 0;
	private final int ISSUE_CHNG = 1;

	private ScrollView mPaymentsScrollView;
	private RelativeLayout mIssueChngRL;
	private Spinner mChngCurrencySpinner;
	private EditText mIssueAmtEt;
	private Button mVoucherBtn;
	private Button mCrediNoteBtn;

	private LinearLayout mPaymentLinearLayout;
	private View paymentView;
	private PaymentTypeEnum mPaymentType;
	private ArrayList<CURRENCY_MST_MODEL> mCurrencyList;

	private CURRENCY_MST_MODEL mBaseCurrency;
	private ArrayList<MULTI_CHNG_MST_Model> mIssueChngList = new ArrayList<MULTI_CHNG_MST_Model>();
	// private ArrayList<MULTI_CHNG_MST_Model> mDBIssueChngList = new
	// ArrayList<MULTI_CHNG_MST_Model>();

	// private ArrayList<PAYMT_MST_MODEL> mPMT_List = new
	// ArrayList<PAYMT_MST_MODEL>();
	// private ArrayList<MULTY_PMT_MST_Model> mMULTY_PMT_List = new
	// ArrayList<MULTY_PMT_MST_Model>();
	private ArrayList<PAYMT_MST_MODEL> mTemp_PMT_List = new ArrayList<PAYMT_MST_MODEL>();
	private ArrayList<MULTY_PMT_MST_Model> mTemp_MULTY_PMT_List = new ArrayList<MULTY_PMT_MST_Model>();
	private double mTotalPaymentMadeByCustomer;
	private double mTotalChangeIssuedToCustomer;

	private double mTotalPayment = 0.0;
	private double mTotalRemaining = 0.0;
	private double mNonRoundedTotalPayment = 0.0;
	private int numberOFRequests = 0;

	private boolean isInputPaymentCorrect = true;

	private enum PaymentTypeEnum {
		PMT_CASH(0), PMT_CREDIT_CARD(1), PMT_CHEQUE(2), PMT_VOUCHER(3), PMT_CREDIT_NOTE(
				4);

		final int paymentType;

		private PaymentTypeEnum(int num) {
			this.paymentType = num;
		}

		public int getValue() {
			return this.paymentType;
		}
	};

	private OnCheckedChangeListener mCheckChangeListener = new OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			Logger.d(TAG, "onCheckedChanged");
			switch (checkedId) {
			case R.id.radioPaymnts:
				showPaymentsView();
				break;

			case R.id.radioIssueChange:
				showIssueChngView();
				break;

			default:
				break;
			}
		}
	};

	public void onBackPressed() {
		showAlertMessage("", "This action will remove all the payment done",
				true);
	};

	private OnClickListener mEqualBtnOnClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (v.getId() == R.id.equalBtn) {
				// TextView amountText = (TextView)
				// cashView.findViewById(R.id.amount_ET);
				// amountText.setText(mTotalRemaining.toString(),TextView.BufferType.EDITABLE);
				View mView;
				TextView amountText;
				if (getSelectedRadioOption(v) != ISSUE_CHNG) {
					switch (mPaymentType) {
					case PMT_CASH:
						mView = findViewById(R.id.cashLayout);
						amountText = (TextView) mView
								.findViewById(R.id.amount_ET);
						;
						amountText.setText(String.valueOf(mTotalRemaining),
								TextView.BufferType.EDITABLE);
						break;

					case PMT_CREDIT_CARD:
						mView = findViewById(R.id.ccLayout);
						amountText = (TextView) mView
								.findViewById(R.id.amount_ET);
						;
						amountText.setText(String.valueOf(mTotalRemaining),
								TextView.BufferType.EDITABLE);

						break;

					case PMT_CHEQUE:
						mView = findViewById(R.id.chequeLayout);
						amountText = (TextView) mView
								.findViewById(R.id.amount_ET);
						;
						amountText.setText(String.valueOf(mTotalRemaining),
								TextView.BufferType.EDITABLE);

						break;

					case PMT_VOUCHER:
						mView = findViewById(R.id.voucherLayout);
						amountText = (TextView) mView
								.findViewById(R.id.amount_ET);
						;
						amountText.setText(String.valueOf(mTotalRemaining),
								TextView.BufferType.EDITABLE);

						break;

					case PMT_CREDIT_NOTE:
						mView = findViewById(R.id.cnLayout);
						amountText = (TextView) mView
								.findViewById(R.id.amount_ET);
						amountText.setText(String.valueOf(mTotalRemaining),
								TextView.BufferType.EDITABLE);

						break;
					}
				}
				else
				{
					mIssueAmtEt.setText(String.valueOf(mTotalRemaining),
							TextView.BufferType.EDITABLE);

				}

			}
		}
	};

	// Add btn onclick
	private OnClickListener mAddBtnOnClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {

			InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			if (getCurrentFocus() != null
					&& getCurrentFocus().getWindowToken() != null) {
				inputManager.hideSoftInputFromWindow(getCurrentFocus()
						.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
			}
			Logger.v(TAG, "payment typew " + mPaymentType);

			// Chk for PAyments
			if (getSelectedRadioOption(v) == PYMNTS) {
				Logger.d(TAG, "Payments Add Btn clicked");
				if (v.getId() == R.id.addBtn) {
					switch (mPaymentType) {
					case PMT_CASH:
						setCashModel();
						break;

					case PMT_CREDIT_CARD:
						setCreditCardModel();
						break;

					case PMT_CHEQUE:

						setChequeModel();
						break;

					case PMT_VOUCHER:
						setVoucherModel();
						break;

					case PMT_CREDIT_NOTE:
						setCreditNoteModel();
						break;
					}
				}
			} else if (getSelectedRadioOption(v) == ISSUE_CHNG) { // chk for
																	// Issue
																	// change
				Logger.d(TAG, "Issue Chng Add Btn clicked");
				if (mIssueAmtEt.getText().toString().trim().length() > 0) {
					Double totalChange = Double
							.parseDouble(df
									.format((mTotalPaymentMadeByCustomer - mTotalPayment)));

					/*
					 * Double changeByUser = Double.parseDouble(df.format(Double
					 * .parseDouble(mIssueAmtEt.getText().toString())));
					 */

					// Decide ExRate and Operator
					String operator = null;
					double exRate = 0.0;
					for (CURRENCY_MST_MODEL model : mCurrencyList) {
						if (model.getCurr_abbrName().equalsIgnoreCase(
								"" + mChngCurrencySpinner.getSelectedItem())) {
							exRate = model.getCurr_exrate();
							operator = model.getCurr_operator();
							break;
						}
					}

					exRate = Double.parseDouble(df.format(exRate));
					double changeByUser = Double.parseDouble(df.format(Double
							.parseDouble(mIssueAmtEt.getText().toString())));
					double issueLocAMt = 0.0;
					if (operator.equalsIgnoreCase("*")) {
						issueLocAMt = changeByUser * exRate;
					} else if (operator.equalsIgnoreCase("/")) {
						issueLocAMt = changeByUser / exRate;
					}

					if (totalChange <= 0) {
						showAlertMessage("", "No change is due", false);
					} else if (issueLocAMt > (totalChange - mTotalChangeIssuedToCustomer)) {
						showAlertMessage(
								"",
								"Extra change being issued. Please check again",
								false);
					} else {
						setIssueAmout(issueLocAMt);// set amt
					}
				} else {
					// If not, Open Alert Dialog and show alert
					showAlertDialog(getResources().getString(
							R.string.alert_wrong_amt));
				}
			}
			// Call to update Payments and Issue changes
			showPaymentDetailsFromTable();

			// Call to clear All EditText
			if (isInputPaymentCorrect) {
				clearAllEditText();
			}
			isInputPaymentCorrect = true;
		}
	};

	/**
	 * Method- To return selected radio option
	 * 
	 * @param v
	 * @return
	 */
	private int getSelectedRadioOption(View v) {
		int selectedId = mRadioGroup.getCheckedRadioButtonId();
		// find the radiobutton by returned id
		mRadioButton = (RadioButton) mRadioGroup.findViewById(selectedId);
		String optionSelected = mRadioButton.getText().toString().trim();
		if (optionSelected.equalsIgnoreCase(getResources().getString(
				R.string.radio_paymnts))) {
			return PYMNTS;
		} else if (optionSelected.equalsIgnoreCase(getResources().getString(
				R.string.radio_issuChng))) {
			return ISSUE_CHNG;
		}
		return -1;
	}

	/**
	 * Method- To Show Payments UI
	 */
	protected void showIssueChngView() {
		mIssueChngRL.setVisibility(View.VISIBLE);
		mPaymentsScrollView.setVisibility(View.GONE);
	}

	/**
	 * Method- To Show Issue Change UI
	 */
	protected void showPaymentsView() {
		mIssueChngRL.setVisibility(View.GONE);
		mPaymentsScrollView.setVisibility(View.VISIBLE);
	}

	/**
	 * Method- To create issue change
	 * 
	 * @param issueLocAMt
	 */
	protected void setIssueAmout(double issueLocAMt) {
		MULTI_CHNG_MST_Model issueCashModel = new MULTI_CHNG_MST_Model();

		// amount in selected currency
		double chngAmt = Double.parseDouble(df.format(Double
				.parseDouble(mIssueAmtEt.getText().toString().trim())));

		issueCashModel.setChngAmt(chngAmt);

		// Currency Abbr
		issueCashModel.setCurrAbbr("" + mChngCurrencySpinner.getSelectedItem());
		// ExRate and Operator
		for (CURRENCY_MST_MODEL model : mCurrencyList) {
			if (model.getCurr_abbrName().equalsIgnoreCase(
					issueCashModel.getCurrAbbr())) {
				issueCashModel.setExRate(Double.parseDouble(df.format(model
						.getCurr_exrate())));
				issueCashModel.setOperator(model.getCurr_operator());
				break;
			}
		}
		// amount in base currency
		if (issueCashModel.getOperator().equalsIgnoreCase("*")) {
			issueCashModel.setLocAmt(issueCashModel.getChngAmt()
					* issueCashModel.getExRate());
		} else if (issueCashModel.getOperator().equalsIgnoreCase("/")) {
			issueCashModel.setLocAmt(issueCashModel.getChngAmt()
					/ issueCashModel.getExRate());
		}
		// Payment No.
		issueCashModel
				.setPaymentNo(UserSingleton.getInstance(getContext()).mUniqueTrasactionNo);

		if (mIssueChngList.contains(issueCashModel)) {
			int index = mIssueChngList.indexOf(issueCashModel);
			mIssueChngList.get(index).setChngAmt(
					mIssueChngList.get(index).getChngAmt()
							+ issueCashModel.getChngAmt());
			mIssueChngList.get(index).setLocAmt(
					mIssueChngList.get(index).getLocAmt()
							+ issueCashModel.getLocAmt());
		} else {
			mIssueChngList.add(issueCashModel);
		}

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.payment_refund);

		paymentView = findViewById(R.id.paymentLayout);
		numberOFRequests = 1;

		// DecimalFormat df = Constants.getDecimalFormat();
		// df = new DecimalFormat(newDF);

		startProgress();
		PaymentHelper paymentHelper = new PaymentHelper(this);
		paymentHelper.fetchAllCurrenciesFromDB();

		componentInitialisation(paymentView);

		mTotalPaymentMadeByCustomer = 0;

		mNonRoundedTotalPayment = (Double.parseDouble(df.format(getIntent()
				.getDoubleExtra(BillFragment.BILL_TOTAL, 0))));
		mTotalPayment = roundToNearest(Double.parseDouble(df.format(getIntent()
				.getDoubleExtra(BillFragment.BILL_TOTAL, 0))));
	}

	private void componentInitialisation(View view) {
		mPaymentsScrollView = (ScrollView) view
				.findViewById(R.id.paymntsScrollView);
		mPaymentsScrollView.setVisibility(View.VISIBLE);
		mIssueChngRL = (RelativeLayout) view
				.findViewById(R.id.issueChngTypeLayout);
		mIssueChngRL.setVisibility(View.GONE);

		mChngCurrencySpinner = (Spinner) view
				.findViewById(R.id.chngCurrencySpinner);
		mIssueAmtEt = (EditText) view.findViewById(R.id.issue_amount_ET);

		mRadioGroup = (RadioGroup) view.findViewById(R.id.radioGroup);
		mRadioGroup.setOnCheckedChangeListener(mCheckChangeListener);
		mRadioGroup.check(R.id.radioPaymnts);

		view.findViewById(R.id.cashBtn).setOnClickListener(mOnClickListener);
		view.findViewById(R.id.credit_cardBtn).setOnClickListener(
				mOnClickListener);
		view.findViewById(R.id.chequeBtn).setOnClickListener(mOnClickListener);

		mVoucherBtn = (Button) view.findViewById(R.id.voucherBtn);
		mVoucherBtn.setOnClickListener(mOnClickListener);

		view.findViewById(R.id.doneBtn).setOnClickListener(mOnClickListener);
		view.findViewById(R.id.cancelBtn).setOnClickListener(mOnClickListener);

		mCrediNoteBtn = (Button) view.findViewById(R.id.credit_noteBtn);
		mCrediNoteBtn.setOnClickListener(mOnClickListener);

		view.findViewById(R.id.selectCCDateBtn).setOnClickListener(
				mOnClickListener);
		view.findViewById(R.id.selectcqDateBtn).setOnClickListener(
				mOnClickListener);

		mPaymentLinearLayout = (LinearLayout) view.findViewById(R.id.paymentLL);
		view.findViewById(R.id.addBtn).setOnClickListener(
				mAddBtnOnClickListener);

		view.findViewById(R.id.equalBtn).setOnClickListener(
				mEqualBtnOnClickListener);

		setCashUIComponents();

		// Enable
		if (UserSingleton.getInstance(this).isNetworkAvailable) {
			mVoucherBtn.setClickable(true);
			mCrediNoteBtn.setClickable(true);
			mVoucherBtn
					.setBackgroundResource(R.drawable.payment_voucher_selector);
			mCrediNoteBtn
					.setBackgroundResource(R.drawable.payment_creditnote_selector);
		} else {// Disable
			mVoucherBtn.setClickable(false);
			mCrediNoteBtn.setClickable(false);

			mVoucherBtn.getBackground().setColorFilter(0xFFA3A3A3,
					PorterDuff.Mode.MULTIPLY);
			mCrediNoteBtn.getBackground().setColorFilter(0xFFA3A3A3,
					PorterDuff.Mode.MULTIPLY);
			// mVoucherBtn.setBackgroundResource(R.drawable.btn_update_db);
			// mCrediNoteBtn.setBackgroundResource(R.drawable.btn_zed_db);
		}

		InputMethodManager inputManager = (InputMethodManager) this
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		inputManager.hideSoftInputFromWindow(this.getCurrentFocus()
				.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
	}

	private OnClickListener mOnClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {

			InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			if (getCurrentFocus() != null
					&& getCurrentFocus().getWindowToken() != null) {
				inputManager.hideSoftInputFromWindow(getCurrentFocus()
						.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
			}

			switch (v.getId()) {
			case R.id.cashBtn:
				setCashUIComponents();
				break;

			case R.id.credit_cardBtn:
				setCreditCardUIComponents();
				break;
			case R.id.credit_noteBtn:
				setCreditNoteUIComponents();
				break;
			case R.id.voucherBtn:
				setVoucherUIComponents();
				break;
			case R.id.chequeBtn:
				setChequeUIComponents();
				break;

			case R.id.doneBtn:
				if (validatePaymentDone()) {
					numberOFRequests = 0;
					addAllPaymentDetailsInRespectiveTable();
					insertMultiChngeInDB();
				}
				break;
			case R.id.cancelBtn:
				showAlertMessage("",
						"This action will remove all the payment done", true);
				break;

			case R.id.selectCCDateBtn:
			case R.id.selectcqDateBtn:
				DialogFragment newFragment = new DateDialogFragment(v,
						PaymentFragment.this);
				newFragment.show(
						PaymentFragment.this.getSupportFragmentManager(),
						"DatePicker");
				break;
			}
		}
	};

	private ProgressDialog mProgressDialog;

	/**
	 * insert each payment/bill in PYMNT_MST_TABLE
	 */
	private void insertPaymentInfoInPAYMT_MST_Table() {
		PaymentHelper helper = new PaymentHelper(this);
		if (mTemp_MULTY_PMT_List.size() == 1) {
			PAYMT_MST_MODEL paymentMSTModel = createPaymentMSTModel();
			paymentMSTModel.setPAY_MODE("CA");
			paymentMSTModel.setPAY_DTLS("CASH");
			paymentMSTModel.setPAY_AMNT(mTemp_MULTY_PMT_List.get(0)
					.getLOC_AMNT());
			mTemp_PMT_List.add((paymentMSTModel));
			if (!mTemp_MULTY_PMT_List.get(0).getCURR_ABBR()
					.equalsIgnoreCase(mBaseCurrency.getCurr_abbrName())) {
				helper.insertAllMULTI_PMT_ENTRIES(mTemp_MULTY_PMT_List);
			}

		} else if (mTemp_MULTY_PMT_List.size() > 0) {
			helper.insertAllMULTI_PMT_ENTRIES(mTemp_MULTY_PMT_List);
			PAYMT_MST_MODEL paymentMSTModel = createPaymentMSTModel();
			paymentMSTModel.setPAY_MODE("CA");
			paymentMSTModel.setPAY_DTLS("CASH");
			double totalCash = 0;
			for (MULTY_PMT_MST_Model cashModel : mTemp_MULTY_PMT_List) {
				totalCash += cashModel.getLOC_AMNT();
			}
			paymentMSTModel.setPAY_AMNT(totalCash);
			mTemp_PMT_List.add((paymentMSTModel));
		}

		if (mTemp_PMT_List != null && mTemp_PMT_List.size() > 0) {
			numberOFRequests++;
			helper.insertPaymentInfoInPAYMT_MST_TABLE(mTemp_PMT_List);
		}
	}

	protected boolean validatePaymentDone() {
		double totalPaymentDone = 0.0;
		double totalchange = 0.0;

		for (MULTY_PMT_MST_Model multyPmtModel : mTemp_MULTY_PMT_List) {
			totalPaymentDone += multyPmtModel.getLOC_AMNT();
		}

		for (PAYMT_MST_MODEL pmtModel : mTemp_PMT_List) {
			totalPaymentDone += pmtModel.getPAY_AMNT();
		}
		if (totalPaymentDone >= mTotalPayment) {
			for (MULTI_CHNG_MST_Model multiChangeModel : mIssueChngList) {
				totalchange += multiChangeModel.getLocAmt();
			}

			// totalchange = roundToNearest(totalchange);
			mTotalPayment = Double.parseDouble(df.format(mTotalPayment));
			totalPaymentDone = Double.parseDouble(df.format(totalPaymentDone));

			totalchange = Double.parseDouble(df.format(totalchange));
			double remainingChange = (totalPaymentDone - mTotalPayment)
					- totalchange;
			remainingChange = Double.parseDouble(df.format(remainingChange));
			Logger.v(TAG, "change: " + remainingChange);
			if (remainingChange > 0.0) {
				// return true;

				showAlertDialog("Some change is still due");
			} else if (remainingChange == 0.0) {
				return true;
			} else if (remainingChange < 0.0) {
				// return true;
				// return true;
				showAlertDialog("Excess change issued. Please check again");
			}
		} else {
			showAlertDialog("Insufficient payment. Please check again.");
		}

		return false;
	}

	private PAYMT_MST_MODEL createPaymentMSTModel() {
		loadAllConfigData();

		PAYMT_MST_MODEL paymentMSTModel = new PAYMT_MST_MODEL();
		paymentMSTModel.setBRNCH_CODE(Config.BRANCH_CODE);
		paymentMSTModel.setCMPNY_CODE(Config.COMPANY_CODE);
		paymentMSTModel
				.setPYMNT_NMBR(UserSingleton.getInstance(this).mUniqueTrasactionNo);

		SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT);
		String currentDateandTime = sdf.format(new Date());
		paymentMSTModel.setPAY_SYS_DATE(currentDateandTime);
		paymentMSTModel.setPAY_RUN_DATE(getServerRunDate());

		paymentMSTModel.setTXN_TYPE(Constants.BILLING_TXN);
		paymentMSTModel.setPAY_AMNT(mTotalPayment);
		paymentMSTModel.setTILL_NO(Config.TAB_NO);
		paymentMSTModel.setPNTS_AWARDED("");
		paymentMSTModel.setPNTS_REDEEMED("");

		if (UserSingleton.getInstance(this).mUserDetail != null) {
			paymentMSTModel
					.setUSR_NAME(UserSingleton.getInstance(this).mUserDetail
							.getUSR_NM());
			paymentMSTModel.setUSR_NAME_SCND("N");// Hard Coded
		}
		return paymentMSTModel;
	}

	/**
	 * This method take care of inserting all the payment detail data in
	 * respective tables
	 */

	protected void addAllPaymentDetailsInRespectiveTable() {
		insertPaymentInfoInPAYMT_MST_Table();
	}

	/**
	 * Method- To insert all change values in DB
	 */
	protected void insertMultiChngeInDB() {
		if (mIssueChngList != null && mIssueChngList.size() > 0) {
			numberOFRequests++;
			PaymentHelper paymentHelper = new PaymentHelper(this);
			paymentHelper.insert_MULTI_CHNG_ENTRIES(mIssueChngList);
		}
	}

	/**
	 * insert cash payments with multiple currencies in MULTI_PYMT_TABLE
	 * 
	 * @param cashList
	 * @return
	 */
	private ArrayList<MULTY_PMT_MST_Model> getPaymentInfoInMULTY_PYMNT_MST_TABLE(
			ArrayList<CashPMTModel> cashList) {
		ArrayList<MULTY_PMT_MST_Model> multi_PMT_ARRAY = new ArrayList<MULTY_PMT_MST_Model>();
		for (CashPMTModel cashPMTModel : cashList) {

			MULTY_PMT_MST_Model multi_PYMNT_Model = new MULTY_PMT_MST_Model();
			double PYMNT_AMT = cashPMTModel.getmAmount();
			double LOC_AMT = 0;
			String operator = mBaseCurrency.getCurr_operator();
			double exRate = mBaseCurrency.getCurr_exrate();
			if (operator.equalsIgnoreCase("*")) {
				LOC_AMT = PYMNT_AMT * mBaseCurrency.getCurr_exrate();
			} else if (operator.equalsIgnoreCase("*")) {
				LOC_AMT = PYMNT_AMT / mBaseCurrency.getCurr_exrate();
			}
			multi_PYMNT_Model.setPYMNT_NMBR(UserSingleton
					.getInstance(getContext()).mUniqueTrasactionNo);
			multi_PYMNT_Model.setLOC_AMNT(LOC_AMT);
			multi_PYMNT_Model.setPAY_AMNT(PYMNT_AMT);
			multi_PYMNT_Model.setCURR_ABBR(cashPMTModel.getmCurrency());
			multi_PYMNT_Model.setEX_RATE(exRate);
			multi_PYMNT_Model.setOPERATOR(operator);
			multi_PMT_ARRAY.add(multi_PYMNT_Model);
		}
		return multi_PMT_ARRAY;
	}

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

	private void setCreditNoteUIComponents() {
		mPaymentType = PaymentTypeEnum.PMT_CREDIT_NOTE;
		mPaymentLinearLayout.findViewById(R.id.cashLayout).setVisibility(
				View.GONE);
		mPaymentLinearLayout.findViewById(R.id.ccLayout).setVisibility(
				View.GONE);
		mPaymentLinearLayout.findViewById(R.id.cnLayout).setVisibility(
				View.VISIBLE);
		mPaymentLinearLayout.findViewById(R.id.voucherLayout).setVisibility(
				View.GONE);
		mPaymentLinearLayout.findViewById(R.id.chequeLayout).setVisibility(
				View.GONE);
	}

	private void setVoucherUIComponents() {
		mPaymentType = PaymentTypeEnum.PMT_VOUCHER;
		mPaymentLinearLayout.findViewById(R.id.cashLayout).setVisibility(
				View.GONE);
		mPaymentLinearLayout.findViewById(R.id.ccLayout).setVisibility(
				View.GONE);
		mPaymentLinearLayout.findViewById(R.id.cnLayout).setVisibility(
				View.GONE);
		mPaymentLinearLayout.findViewById(R.id.voucherLayout).setVisibility(
				View.VISIBLE);
		mPaymentLinearLayout.findViewById(R.id.chequeLayout).setVisibility(
				View.GONE);
	}

	private void setChequeUIComponents() {
		mPaymentType = PaymentTypeEnum.PMT_CHEQUE;
		mPaymentLinearLayout.findViewById(R.id.cashLayout).setVisibility(
				View.GONE);
		mPaymentLinearLayout.findViewById(R.id.ccLayout).setVisibility(
				View.GONE);
		mPaymentLinearLayout.findViewById(R.id.cnLayout).setVisibility(
				View.GONE);
		mPaymentLinearLayout.findViewById(R.id.voucherLayout).setVisibility(
				View.GONE);
		mPaymentLinearLayout.findViewById(R.id.chequeLayout).setVisibility(
				View.VISIBLE);
	}

	private void setCreditCardUIComponents() {
		mPaymentType = PaymentTypeEnum.PMT_CREDIT_CARD;
		mPaymentLinearLayout.findViewById(R.id.cashLayout).setVisibility(
				View.GONE);
		mPaymentLinearLayout.findViewById(R.id.ccLayout).setVisibility(
				View.VISIBLE);
		mPaymentLinearLayout.findViewById(R.id.cnLayout).setVisibility(
				View.GONE);
		mPaymentLinearLayout.findViewById(R.id.voucherLayout).setVisibility(
				View.GONE);
		mPaymentLinearLayout.findViewById(R.id.chequeLayout).setVisibility(
				View.GONE);
	}

	protected void setCashModel() {
		View cashView = findViewById(R.id.cashLayout);
		MULTY_PMT_MST_Model cashModel = new MULTY_PMT_MST_Model();

		TextView amountText = (TextView) cashView.findViewById(R.id.amount_ET);

		if (amountText.getText().toString().trim().length() > 0) {

			double PYMNT_AMT = Double.parseDouble(amountText.getText()
					.toString().trim());
			PYMNT_AMT = Double.parseDouble(df.format(PYMNT_AMT));

			cashModel.setPAY_AMNT(PYMNT_AMT);

			Spinner currencySpinner = (Spinner) cashView
					.findViewById(R.id.currencySpinner);

			MULTY_PMT_MST_Model multi_PYMNT_Model = new MULTY_PMT_MST_Model();
			double LOC_AMT = 0.0;
			double exRate = 0.0;
			for (CURRENCY_MST_MODEL model : mCurrencyList) {
				if (model.getCurr_abbrName().equalsIgnoreCase(
						"" + currencySpinner.getSelectedItem())) {
					exRate = Double.parseDouble(df.format(model
							.getCurr_exrate()));
					multi_PYMNT_Model.setEX_RATE(exRate);
					multi_PYMNT_Model.setOPERATOR(model.getCurr_operator());
					Logger.v(TAG, "multi_PYMNT_Model" + multi_PYMNT_Model);
					Logger.v(TAG, "selected CURRENCY_MST_MODEL" + model);
					break;
				}
			}

			String operator = multi_PYMNT_Model.getOPERATOR();
			if (operator.equalsIgnoreCase("*")) {
				LOC_AMT = PYMNT_AMT * exRate;
			} else if (operator.equalsIgnoreCase("/")) {
				LOC_AMT = PYMNT_AMT / exRate;
			}

			multi_PYMNT_Model.setPYMNT_NMBR(UserSingleton
					.getInstance(getContext()).mUniqueTrasactionNo);
			multi_PYMNT_Model.setLOC_AMNT(LOC_AMT);
			multi_PYMNT_Model.setPAY_AMNT(PYMNT_AMT);
			multi_PYMNT_Model.setCURR_ABBR(""
					+ currencySpinner.getSelectedItem());

			if (mTemp_MULTY_PMT_List.contains(multi_PYMNT_Model)) {
				int index = mTemp_MULTY_PMT_List.indexOf(multi_PYMNT_Model);
				mTemp_MULTY_PMT_List.get(index).setPAY_AMNT(
						mTemp_MULTY_PMT_List.get(index).getPAY_AMNT()
								+ PYMNT_AMT);
				mTemp_MULTY_PMT_List.get(index)
						.setLOC_AMNT(
								mTemp_MULTY_PMT_List.get(index).getLOC_AMNT()
										+ LOC_AMT);
			} else {
				mTemp_MULTY_PMT_List.add(multi_PYMNT_Model);
			}

		} else {
			// If not, Open Alert Dialog and show alert
			showAlertDialog(getResources().getString(R.string.alert_wrong_amt));
		}
	}

	/**
	 * Method- To show alert dialog
	 */
	private void showAlertDialog(String message) {
		FragmentManager fm = this.getSupportFragmentManager();
		AlertDialogFragment alertDialogFragment = new AlertDialogFragment();
		Bundle bundle = new Bundle();
		bundle.putString(Constants.KEY, message);
		alertDialogFragment.setArguments(bundle);
		alertDialogFragment.show(fm, ALERT_FRAGMENT);
	}

	protected void setCashUIComponents() {
		mPaymentType = PaymentTypeEnum.PMT_CASH;

		Spinner currencySpinner = (Spinner) mPaymentLinearLayout
				.findViewById(R.id.currencySpinner);
		if (mCurrencyList != null) {
			CurencySpinnerAdapter adapter = new CurencySpinnerAdapter(this,
					android.R.layout.simple_spinner_item, mCurrencyList);
			currencySpinner.setAdapter(adapter);
		}

		mPaymentLinearLayout.findViewById(R.id.cashLayout).setVisibility(
				View.VISIBLE);
		((EditText) (mPaymentLinearLayout.findViewById(R.id.cashLayout))
				.findViewById(R.id.amount_ET)).requestFocus();

		mPaymentLinearLayout.findViewById(R.id.ccLayout).setVisibility(
				View.GONE);
		mPaymentLinearLayout.findViewById(R.id.cnLayout).setVisibility(
				View.GONE);
		mPaymentLinearLayout.findViewById(R.id.voucherLayout).setVisibility(
				View.GONE);
		mPaymentLinearLayout.findViewById(R.id.chequeLayout).setVisibility(
				View.GONE);
	}

	// creditnote
	protected void setCreditNoteModel() {
		View cnView = findViewById(R.id.cnLayout);
		EditText creditNoteNumberET = (EditText) cnView
				.findViewById(R.id.credit_note_number_ET);
		EditText amountET = (EditText) cnView.findViewById(R.id.amount_ET);
		if (creditNoteNumberET.getText().toString().trim().length() > 0
				&& amountET.getText().toString().trim().length() > 0) {

			PaymentHelper pHelper = new PaymentHelper(this);
			pHelper.checkIfValidCreditNote(Config.GET_CREDIT_NOTE_DETAILS + ""
					+ creditNoteNumberET.getText().toString() + ","
					+ Config.BRANCH_CODE, null, true);

			startProgress();

			// if (mTemp_PMT_List.contains(paymentMSTModel)) {
			// int index = mTemp_PMT_List.indexOf(paymentMSTModel);
			// mTemp_PMT_List.get(index).setPAY_AMNT(
			// mTemp_PMT_List.get(index).getPAY_AMNT() + amount);
			// } else {
			// mTemp_PMT_List.add(paymentMSTModel);
			// }

		} else {
			showAlertDialog(getResources().getString(
					R.string.alert_wrong_inputs));
		}
	}

	// creditnote
	private void populatePaymentListOnCreditNoteResponse(boolean successNote,
			String reason) {

		try {
			JSONObject respone = new JSONObject(reason);
			if (successNote) {

				JSONObject creditDetails = respone
						.getJSONObject("creditNoteDetails");

				Logger.d(TAG,
						"populatePaymentListOnCreditNoteResponse creditNoteDetails::amountEncashed:"
								+ creditDetails.getString("amountEncashed"));

				showAlertDialog("Payment by Credit Note was successful Details:"
						+ creditDetails.toString());

				View cnView = findViewById(R.id.cnLayout);
				EditText creditNoteNumberET = (EditText) cnView
						.findViewById(R.id.credit_note_number_ET);

				/*
				 * EditText amountET = (EditText)
				 * cnView.findViewById(R.id.amount_ET); float amount = 0; if
				 * (!amountET.getText().toString().equalsIgnoreCase("")) {
				 * amount = Float.parseFloat(amountET.getText().toString()); }
				 */
				float amount = Float.parseFloat(creditDetails
						.getString("amountEncashed"));

				// if(amount){
				PAYMT_MST_MODEL paymentMSTModel = createPaymentMSTModel();
				paymentMSTModel.setPAY_AMNT(amount);
				paymentMSTModel.setPAY_MODE_REF_NO(creditNoteNumberET.getText()
						.toString());
				paymentMSTModel.setPAY_MODE_REF_CODE(creditNoteNumberET
						.getText().toString());
				paymentMSTModel.setPAY_DTLS("Credit Note");
				paymentMSTModel.setPAY_MODE("CN");
				mTemp_PMT_List.add(paymentMSTModel);

				showPaymentDetailsFromTable();
				// }else{

				// }

			} else {
				showAlertDialog("Payment by Credit Note was unsuccessful:"
						+ respone.getString("serverResponse"));
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		stopProgress();

	}

	protected void setVoucherModel() {
		View voucherView = findViewById(R.id.voucherLayout);
		EditText voucherET = (EditText) voucherView
				.findViewById(R.id.voucher_number_ET);
		EditText amountET = (EditText) voucherView.findViewById(R.id.amount_ET);

		float amount = 0;
		if (voucherET.getText().toString().trim().length() > 0
				&& amountET.getText().toString().trim().length() > 0) {

			if (!amountET.getText().toString().equalsIgnoreCase("")) {
				amount = Float.parseFloat(amountET.getText().toString());
			}
			PAYMT_MST_MODEL paymentMSTModel = createPaymentMSTModel();
			paymentMSTModel.setPAY_MODE_REF_NO(voucherET.getText().toString());
			paymentMSTModel
					.setPAY_MODE_REF_CODE(voucherET.getText().toString());
			paymentMSTModel.setPAY_DTLS("Voucher");
			paymentMSTModel.setPAY_MODE("VO");
			paymentMSTModel.setPAY_AMNT(amount);
			// if (mTemp_PMT_List.contains(paymentMSTModel)) {
			// int index = mTemp_PMT_List.indexOf(paymentMSTModel);
			// mTemp_PMT_List.get(index).setPAY_AMNT(
			// mTemp_PMT_List.get(index).getPAY_AMNT() + amount);
			// } else {
			// mTemp_PMT_List.add(paymentMSTModel);
			// }
			mTemp_PMT_List.add(paymentMSTModel);
		} else {
			showAlertDialog(getResources().getString(
					R.string.alert_wrong_inputs));
		}
	}

	protected void setChequeModel() {
		View chequeView = findViewById(R.id.chequeLayout);
		EditText chequeNumberET = (EditText) chequeView
				.findViewById(R.id.cheque_number_ET);
		EditText amountET = (EditText) chequeView.findViewById(R.id.amount_ET);
		EditText chequeDateET = (EditText) chequeView
				.findViewById(R.id.cqExpDateET);

		float amount = 0;
		if (chequeNumberET.getText().toString().trim().length() > 0
				&& amountET.getText().toString().trim().length() > 0
				&& chequeDateET.getText().toString().trim().length() > 0) {
			if (!amountET.getText().toString().equalsIgnoreCase("")) {
				amount = Float.parseFloat(amountET.getText().toString());
			}

			if (amount > (mTotalPayment - mTotalPaymentMadeByCustomer)) {
				showAlertDialog(getResources().getString(
						R.string.alert_extra_amt));
				isInputPaymentCorrect = false;
				return;
			}

			if (dateCompare(chequeDateET.getText().toString().trim())) {
				showAlertDialog(getResources().getString(
						R.string.alert_invalid_date));
				isInputPaymentCorrect = false;
				return;
			}

			PAYMT_MST_MODEL paymentMSTModel = createPaymentMSTModel();
			paymentMSTModel.setPAY_AMNT(amount);
			paymentMSTModel.setPAY_MODE_REF_NO(chequeNumberET.getText()
					.toString());
			paymentMSTModel.setPAY_MODE_REF_CODE(chequeNumberET.getText()
					.toString());
			paymentMSTModel.setPAY_DTLS("Cheque");
			paymentMSTModel.setPAY_MODE("CQ");
			// if (mTemp_PMT_List.contains(paymentMSTModel)) {
			// int index = mTemp_PMT_List.indexOf(paymentMSTModel);
			// mTemp_PMT_List.get(index).setPAY_AMNT(
			// mTemp_PMT_List.get(index).getPAY_AMNT() + amount);
			// } else {
			// mTemp_PMT_List.add(paymentMSTModel);
			// }
			mTemp_PMT_List.add(paymentMSTModel);

		} else {
			showAlertDialog(getResources().getString(
					R.string.alert_wrong_inputs));
		}
	}

	public Boolean dateCompare(String enteredText) {
		Boolean bReturn = false;
		String sSplitter = "/";
		if (enteredText.contains(".")) {
			sSplitter = ".";
		} else if (enteredText.contains("-")) {
			sSplitter = "-";
		} else if (enteredText.contains("/")) {
			sSplitter = "/";
		}
		DateFormat dateFormat = new SimpleDateFormat("MM" + sSplitter + "dd"
				+ sSplitter + "yyyy");
		// get current date time with Date()
		Date date = new Date();
		Date date2;
		SimpleDateFormat sdf = new SimpleDateFormat("MM" + sSplitter + "dd"
				+ sSplitter + "yyyy");
		try {
			date2 = sdf.parse(enteredText);
			Calendar cal1 = Calendar.getInstance();
			Calendar cal2 = Calendar.getInstance();
			cal1.setTime(date);
			cal2.setTime(date2);
			if (cal1.after(cal2)) {
				bReturn = false;

			}

			if (cal1.before(cal2)) {
				bReturn = true;

			}

			if (cal1.equals(cal2)) {
				bReturn = false;

			}

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return bReturn;

	}

	protected void setCreditCardModel() {
		View creditView = findViewById(R.id.ccLayout);
		float amount = 0;
		EditText cardNumberET = (EditText) this
				.findViewById(R.id.card_number_ET);
		EditText expiryDateET = (EditText) creditView
				.findViewById(R.id.ccExpDateET);
		EditText authorizationET = (EditText) this
				.findViewById(R.id.authorization_code_ET);
		EditText amountET = (EditText) creditView.findViewById(R.id.amount_ET);

		if (cardNumberET.getText().toString().trim().length() > 0
				&& expiryDateET.getText().toString().trim().length() > 0
				&& authorizationET.getText().toString().trim().length() > 0
				&& amountET.getText().toString().trim().length() > 0
				&& dateCompare(expiryDateET.getText().toString().trim())) {

			if (!amountET.getText().toString().trim().equalsIgnoreCase("")) {
				amount = Float.parseFloat(amountET.getText().toString());
			}

			if (amount > (mTotalPayment - mTotalPaymentMadeByCustomer)) {
				showAlertDialog(getResources().getString(
						R.string.alert_extra_amt));
				isInputPaymentCorrect = false;
				return;
			}

			PAYMT_MST_MODEL paymentMSTModel = createPaymentMSTModel();
			paymentMSTModel.setCARDNO(cardNumberET.getText().toString());
			paymentMSTModel.setCCAuthCode(authorizationET.getText().toString());
			paymentMSTModel.setCCDt(expiryDateET.getText().toString());
			paymentMSTModel.setPAY_MODE_REF_NO(cardNumberET.getText()
					.toString());
			paymentMSTModel.setPAY_MODE_REF_CODE(cardNumberET.getText()
					.toString());
			paymentMSTModel.setPAY_DTLS("Credit Card");
			paymentMSTModel.setPAY_MODE("CC");
			paymentMSTModel.setPAY_AMNT(amount);
			mTemp_PMT_List.add(paymentMSTModel);
			// if (mTemp_PMT_List.contains(paymentMSTModel)) {
			// int index = mTemp_PMT_List.indexOf(paymentMSTModel);
			// mTemp_PMT_List.get(index).setPAY_AMNT(
			// mTemp_PMT_List.get(index).getPAY_AMNT() + amount);
			// } else {
			// mTemp_PMT_List.add(paymentMSTModel);
			// }
		} else {
			showAlertDialog(getResources().getString(
					R.string.alert_wrong_inputs));
		}
	}

	/**
	 * Method- Issue chng currency spinner
	 */
	protected void setIsuChngCurrencyUI() {
		if (mCurrencyList != null) {
			CurencySpinnerAdapter adapter = new CurencySpinnerAdapter(
					getContext(), android.R.layout.simple_spinner_item,
					mCurrencyList);
			mChngCurrencySpinner.setAdapter(adapter);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void executePostAction(OperationalResult opResult) {
		switch (opResult.getResultCode()) {
		case Constants.FETCH_CURRENCIES:
			mCurrencyList = (ArrayList<CURRENCY_MST_MODEL>) opResult
					.getResult().getSerializable(Constants.RESULTCODEBEAN);
			Logger.v(TAG, "FETCH_CURRENCIES" + mCurrencyList);
			mBaseCurrency = getBaseCurrency();
			setCashUIComponents();

			// set up issue change currency spinner
			setIsuChngCurrencyUI();

			numberOFRequests--;
			if (numberOFRequests == 0) {
				stopProgress();
				showPaymentDetailsFromTable();
				numberOFRequests = 0;
			}
			break;

		case Constants.INSERT_PAYMT_RECORD:
			numberOFRequests--;
			checkPaymentInserted();
			break;

		case Constants.INSERT_MULTI_PMT_RECORDS:
			numberOFRequests--;
			checkPaymentInserted();
			break;

		// Upload data handling

		case Constants.INSERT_MULTI_CHNG_RECORDS:
			Logger.d(TAG, "INSERT_MULTI_CHNG_RECORDS Done");
			numberOFRequests--;
			checkPaymentInserted();
			break;

		// creditnote response
		case Constants.GET_CREDIT_NOTE_RESPONSE:
			Logger.d(
					TAG,
					"CREDITNOTE RESPONSE WE GET IS::"
							+ opResult.getResult().getBoolean(
									"creditNoteResponse", false));

			populatePaymentListOnCreditNoteResponse(opResult.getResult()
					.getBoolean("creditNoteResponse", false), opResult
					.getResult().getString("serverResponse"));

			break;
		default:
			Logger.d(TAG, "Default Result Code:" + opResult.getResultCode());
			break;

		}
	}

	/**
	 * Method- To inflate all details in bill panel
	 */
	private void showPaymentDetailsFromTable() {
		mTotalPaymentMadeByCustomer = 0;
		mTotalChangeIssuedToCustomer = 0;

		LinearLayout paymentListView = (LinearLayout) findViewById(R.id.addPaymentDetailLL);
		paymentListView.removeAllViews();

		// ================ Payment UI Start ======================== //
		LinearLayout nonRoundedLL = (LinearLayout) LayoutInflater.from(this)
				.inflate(R.layout.payment_detail_row, null);
		((TextView) nonRoundedLL.findViewById(R.id.paymentText))
				.setText(getResources().getString(R.string.payment_due));
		((TextView) nonRoundedLL.findViewById(R.id.paymentText))
				.setTextColor(getResources().getColor(R.color.pymnts_orange));
		((TextView) nonRoundedLL.findViewById(R.id.paymentamount)).setText(""
				+ (mNonRoundedTotalPayment));
		paymentListView.addView(nonRoundedLL);

		View paymentDuewRow = LayoutInflater.from(this).inflate(
				R.layout.payment_detail_row, null);
		((TextView) paymentDuewRow.findViewById(R.id.paymentText))
				.setText(getResources().getString(R.string.payment_due_rounded));
		((TextView) paymentDuewRow.findViewById(R.id.paymentText))
				.setTextColor(getResources().getColor(R.color.pymnts_orange));
		((TextView) paymentDuewRow.findViewById(R.id.paymentText)).setTypeface(
				null, Typeface.BOLD);

		if (mTotalPaymentMadeByCustomer < mTotalPayment) {
			((TextView) paymentDuewRow.findViewById(R.id.paymentamount))
					.setText("" + (mTotalPayment));
		} else {
			((TextView) paymentDuewRow.findViewById(R.id.paymentamount))
					.setText("0");
		}
		paymentListView.addView(paymentDuewRow);

		LinearLayout blankRow2 = (LinearLayout) LayoutInflater.from(this)
				.inflate(R.layout.payment_blank_row, null);
		((TextView) blankRow2.findViewById(R.id.paymentText))
				.setText(getResources().getString(R.string.payment_due));
		blankRow2.findViewById(R.id.paymentText).setVisibility(View.INVISIBLE);
		paymentListView.addView(blankRow2);

		if (mTemp_MULTY_PMT_List != null) {
			for (final MULTY_PMT_MST_Model paymentModel : mTemp_MULTY_PMT_List) {
				View paymentRow = LayoutInflater.from(this).inflate(
						R.layout.payment_detail_row, null);
				((TextView) paymentRow.findViewById(R.id.paymentText))
						.setText("Cash " + paymentModel.getCURR_ABBR() + " "
								+ paymentModel.getPAY_AMNT());
				((TextView) paymentRow.findViewById(R.id.paymentText))
						.setTextColor(getResources().getColor(
								R.color.pymnts_green));

				((TextView) paymentRow.findViewById(R.id.paymentamount))
						.setText("" + paymentModel.getLOC_AMNT());
				mTotalPaymentMadeByCustomer += paymentModel.getLOC_AMNT();
				paymentListView.addView(paymentRow);
			}
		}

		LinearLayout blankRow3 = (LinearLayout) LayoutInflater.from(this)
				.inflate(R.layout.payment_blank_row, null);
		((TextView) blankRow3.findViewById(R.id.paymentText))
				.setText(getResources().getString(R.string.payment_due));
		blankRow3.findViewById(R.id.paymentText).setVisibility(View.INVISIBLE);
		paymentListView.addView(blankRow3);

		if (mTemp_PMT_List != null) {
			for (final PAYMT_MST_MODEL paymentModel : mTemp_PMT_List) {
				View paymentRow = LayoutInflater.from(this).inflate(
						R.layout.payment_detail_row, null);
				((TextView) paymentRow.findViewById(R.id.paymentText))
						.setText(paymentModel.getPAY_DTLS());
				((TextView) paymentRow.findViewById(R.id.paymentText))
						.setTextColor(getResources().getColor(
								R.color.pymnts_green));

				((TextView) paymentRow.findViewById(R.id.paymentamount))
						.setText("" + paymentModel.getPAY_AMNT());
				mTotalPaymentMadeByCustomer += paymentModel.getPAY_AMNT();
				paymentListView.addView(paymentRow);

			}
		}

		LinearLayout blankRow4 = (LinearLayout) LayoutInflater.from(this)
				.inflate(R.layout.payment_blank_row, null);
		((TextView) blankRow4.findViewById(R.id.paymentText))
				.setText(getResources().getString(R.string.payment_due));
		blankRow4.findViewById(R.id.paymentText).setVisibility(View.INVISIBLE);
		paymentListView.addView(blankRow4);

		View totalPaymentRow = LayoutInflater.from(this).inflate(
				R.layout.payment_detail_row, null);
		((TextView) totalPaymentRow.findViewById(R.id.paymentText))
				.setText(getResources().getString(R.string.total_payments));
		((TextView) totalPaymentRow.findViewById(R.id.paymentText))
				.setTextColor(getResources().getColor(R.color.pymnts_red));
		String customerPMT = df.format(mTotalPaymentMadeByCustomer);
		double customerTotal = Double.parseDouble(customerPMT);
		((TextView) totalPaymentRow.findViewById(R.id.paymentamount))
				.setText(mBaseCurrency.getCurr_abbrName() + " - "
						+ customerTotal);
		paymentListView.addView(totalPaymentRow);

		View totalPaymentRemainingRow = LayoutInflater.from(this).inflate(
				R.layout.payment_detail_row, null);
		((TextView) totalPaymentRemainingRow.findViewById(R.id.paymentText))
				.setText(getResources().getString(
						R.string.total_payments_remaining));
		((TextView) totalPaymentRemainingRow.findViewById(R.id.paymentText))
				.setTextColor(getResources().getColor(R.color.pymnts_red));
		((TextView) totalPaymentRemainingRow.findViewById(R.id.paymentText))
				.setTypeface(null, Typeface.BOLD);

		String remainingPMT = df.format(mTotalPayment
				- mTotalPaymentMadeByCustomer);
		double remainingTotal = Double.parseDouble(remainingPMT);

		mTotalRemaining = remainingTotal;

		if (mTotalPayment > mTotalPaymentMadeByCustomer) {
			((TextView) totalPaymentRemainingRow
					.findViewById(R.id.paymentamount)).setText(mBaseCurrency
					.getCurr_abbrName() + " - " + remainingTotal);
		} else {
			((TextView) totalPaymentRemainingRow
					.findViewById(R.id.paymentamount)).setText(mBaseCurrency
					.getCurr_abbrName() + " - " + 0.0);
		}
		paymentListView.addView(totalPaymentRemainingRow);

		View changeDueRow = LayoutInflater.from(this).inflate(
				R.layout.payment_detail_row, null);
		((TextView) changeDueRow.findViewById(R.id.paymentText))
				.setText(getResources().getString(R.string.change_due));
		((TextView) changeDueRow.findViewById(R.id.paymentText))
				.setTextColor(getResources().getColor(R.color.pymnts_red));
		if (mTotalPaymentMadeByCustomer > mTotalPayment) {

			String totalS = df.format(mTotalPaymentMadeByCustomer
					- mTotalPayment);
			double finalTotal = Double.parseDouble(totalS);

			((TextView) changeDueRow.findViewById(R.id.paymentamount))
					.setText(mBaseCurrency.getCurr_abbrName() + " "
							+ finalTotal);
		} else {
			((TextView) changeDueRow.findViewById(R.id.paymentamount))
					.setText("0");
		}
		paymentListView.addView(changeDueRow);

		// ================ Payment UI End ============================= //

		// ================ Issue Change UI Start ======================//

		LinearLayout blankRow5 = (LinearLayout) LayoutInflater.from(
				getContext()).inflate(R.layout.payment_blank_row, null);
		((TextView) blankRow5.findViewById(R.id.paymentText))
				.setText(getResources().getString(R.string.payment_due));
		blankRow5.findViewById(R.id.paymentText).setVisibility(View.INVISIBLE);
		paymentListView.addView(blankRow5);

		// From current local list
		if (mIssueChngList != null && mIssueChngList.size() > 0) {
			for (MULTI_CHNG_MST_Model issueModel : mIssueChngList) {
				View paymentRow = LayoutInflater.from(getContext()).inflate(
						R.layout.payment_detail_row, null);

				((TextView) paymentRow.findViewById(R.id.paymentText))
						.setText(getResources().getString(
								R.string.change_issued)
								+ issueModel.getCurrAbbr() // Selected Curr
								+ " " + issueModel.getChngAmt()); // AMt in Base
																	// curr

				String amtStr = df.format(issueModel.getLocAmt());
				double issueChange = Double.parseDouble(amtStr);

				((TextView) paymentRow.findViewById(R.id.paymentamount))
						.setText("" + issueChange);

				paymentListView.addView(paymentRow);
			}
		}

		LinearLayout blankRow6 = (LinearLayout) LayoutInflater.from(
				getContext()).inflate(R.layout.payment_blank_row, null);
		((TextView) blankRow6.findViewById(R.id.paymentText))
				.setText(getResources().getString(R.string.payment_due));
		blankRow6.findViewById(R.id.paymentText).setVisibility(View.INVISIBLE);
		paymentListView.addView(blankRow6);

		// Total chage chnAMount Issued in base curr
		View totalIssueRow = LayoutInflater.from(getContext()).inflate(
				R.layout.payment_detail_row, null);
		((TextView) totalIssueRow.findViewById(R.id.paymentText))
				.setText(getResources().getString(R.string.total_change_issued)
						+ mBaseCurrency.getCurr_abbrName());
		((TextView) totalIssueRow.findViewById(R.id.paymentText))
				.setTextColor(getResources().getColor(R.color.pymnts_velvet));
		// double totalChangeIssued = 0.0;
		for (MULTI_CHNG_MST_Model chngModel : mIssueChngList) {
			mTotalChangeIssuedToCustomer += chngModel.getLocAmt();
		}

		String totalChangeStr = df.format(mTotalChangeIssuedToCustomer);
		double totalChange = Double.parseDouble(totalChangeStr);
		((TextView) totalIssueRow.findViewById(R.id.paymentamount)).setText(""
				+ totalChange);
		paymentListView.addView(totalIssueRow);

		// Change Issue still Due
		View changeStillDueRow = LayoutInflater.from(getContext()).inflate(
				R.layout.payment_detail_row, null);
		((TextView) changeStillDueRow.findViewById(R.id.paymentText))
				.setText(getResources().getString(R.string.change_still_due)
						+ " " + mBaseCurrency.getCurr_abbrName());
		((TextView) changeStillDueRow.findViewById(R.id.paymentText))
				.setTextColor(getResources().getColor(R.color.pymnts_velvet));
		((TextView) changeStillDueRow.findViewById(R.id.paymentText))
				.setTypeface(null, Typeface.BOLD);

		double issueAMtStillDue = ((mTotalPaymentMadeByCustomer - mTotalPayment) - mTotalChangeIssuedToCustomer);
		String changeDueStr = df.format(issueAMtStillDue);
		double changeDue = Double.parseDouble(changeDueStr);

		if (mTotalPaymentMadeByCustomer > mTotalPayment) {
			((TextView) changeStillDueRow.findViewById(R.id.paymentamount))
					.setText("" + changeDue);
		} else {
			((TextView) changeStillDueRow.findViewById(R.id.paymentamount))
					.setText("0");
		}
		paymentListView.addView(changeStillDueRow);

		// ================ Issue Change UI End ============================= //

		// mTotalPaymentMadeByCustomer =
		// roundToNearest(mTotalPaymentMadeByCustomer);
		// mTotalPayment = roundToNearest(mTotalPayment);
	}

	/**
	 * Method- To receive error if any
	 */
	@Override
	public void errorReceived(OperationalResult opResult) {
		switch (opResult.getResultCode()) {

		case OperationalResult.MASTER_ERROR:
			break;

		case OperationalResult.ERROR:
			break;
		}
	}

	private void checkPaymentInserted() {
		Logger.d(TAG, "checkPaymentInserted");
		if (numberOFRequests == 0) {
			Logger.d(TAG, "checkPaymentInserted Inside");
			// get all data
			OfflineTxnUploadHelper txnUploadHelper = new OfflineTxnUploadHelper();
			txnUploadHelper.uploadJsonData(UserSingleton
					.getInstance(getContext()).mUniqueTrasactionNo);
			UserSingleton.getInstance(getContext()).isPaymentDone = true;
			this.finish(); // commented
		}
	}

	@Override
	public Context getContext() {
		return this;
	}

	/**
	 * This method will start ProgressDialog indicating background activity.
	 */
	public void startProgress() {
		if (mProgressDialog == null) {
			mProgressDialog = new ProgressDialog(this);
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

	public void populateSetDate(View mSelectedView, int year, int month, int day) {
		switch (mSelectedView.getId()) {
		case R.id.selectCCDateBtn:
			EditText expiryDateET = (EditText) this
					.findViewById(R.id.ccExpDateET);
			expiryDateET.setText(month + "/" + day + "/" + year);
			break;
		case R.id.selectcqDateBtn:
			EditText cqDateET = (EditText) this.findViewById(R.id.cqExpDateET);
			cqDateET.setText(month + "/" + day + "/" + year);
			break;
		}
	}

	private double roundToNearest(double numberToBeRounded) {
		SharedPreferences prefs = this.getSharedPreferences("com.mpos",
				Context.MODE_PRIVATE);
		int nearestRndValue = prefs.getInt(LogInActivity.NEAREST_ROUND_VAL, 0);

		Config.NEAREST_ROUNDING_FO = nearestRndValue;

		// Temp Var for emulator
		// Config.NEAREST_ROUNDING_FO = 50;

		Logger.v(TAG, "Config.NEAREST_ROUNDING_FO "
				+ Config.NEAREST_ROUNDING_FO);

		double roundedValue = 0.0;

		// For 25 and 50 rounding
		if (Config.NEAREST_ROUNDING_FO == 25
				|| Config.NEAREST_ROUNDING_FO == 50) {
			int value = (int) (numberToBeRounded * 100.00);
			int dcValue = value % 100;

			int LB = 0;
			int UB = 0;
			if (dcValue < 50) {
				LB = 0;
				UB = (Config.NEAREST_ROUNDING_FO == 25) ? 50 : 100;
			} else {
				LB = (Config.NEAREST_ROUNDING_FO == 25) ? 50 : 0;
				UB = 100;
			}

			int diff = dcValue - LB;
			if (diff == 0) {
				roundedValue = value;
			} else if ((diff != 0) && (diff < Config.NEAREST_ROUNDING_FO)) {
				roundedValue = value - diff;
			} else {
				roundedValue = value + (UB - dcValue);
			}
			roundedValue = roundedValue / 100;

		} else { // For 5 and 10 rounding
			int value = (int) (numberToBeRounded * 100.00);
			int dcValue = value % 100;

			int quotient = (int) (dcValue / Config.NEAREST_ROUNDING_FO);

			int LB = quotient * Config.NEAREST_ROUNDING_FO;
			int UB = LB + Config.NEAREST_ROUNDING_FO;
			int diff = dcValue - LB;
			if ((Config.NEAREST_ROUNDING_FO - diff) > diff) {
				// if (diff < (Config.NEAREST_ROUNDING_FO / 2)) {
				roundedValue = (double) (value - diff) / 100.00;
			} else {
				roundedValue = (double) (value + (UB - LB - diff)) / 100.00;
			}
		}

		double prefValue = Double.parseDouble(UserSingleton.getInstance(
				getContext()).getTotalRounding());
		double totalRounding = (roundedValue - numberToBeRounded) + prefValue;
		UserSingleton.getInstance(getContext()).setTotalRounding(
				"" + totalRounding);

		// Update and Save in shared pref forZReport Use

		double totalZRounding = 0.0;
		if (!prefs.getString("Z_ROUND_OFF", "").equalsIgnoreCase("")) {
			totalZRounding = Double.parseDouble(prefs.getString("Z_ROUND_OFF",
					""));
		}
		totalZRounding = totalZRounding + totalRounding;

		Editor prefEditor = prefs.edit();
		prefEditor.putString("Z_ROUND_OFF", String.valueOf(totalZRounding));
		prefEditor.commit();

		Logger.v(TAG, " total ronding done:  " + totalRounding);
		Logger.v(TAG, " total payment after rounding:  " + roundedValue);

		return roundedValue;

	}

	private void clearAllEditText() {

		View cashView = findViewById(R.id.cashLayout);
		EditText amountText = (EditText) cashView.findViewById(R.id.amount_ET);
		amountText.setText("");

		View creditView = findViewById(R.id.ccLayout);
		EditText cardNumberET = (EditText) this
				.findViewById(R.id.card_number_ET);
		cardNumberET.setText("");
		EditText expiryDateET = (EditText) creditView
				.findViewById(R.id.ccExpDateET);
		expiryDateET.setText("");
		EditText authorizationET = (EditText) this
				.findViewById(R.id.authorization_code_ET);
		authorizationET.setText("");
		EditText amountET = (EditText) creditView.findViewById(R.id.amount_ET);
		amountET.setText("");

		View chequeView = findViewById(R.id.chequeLayout);
		EditText chequeNumberET = (EditText) chequeView
				.findViewById(R.id.cheque_number_ET);
		chequeNumberET.setText("");
		EditText amount = (EditText) chequeView.findViewById(R.id.amount_ET);
		amount.setText("");
		EditText chequeDateET = (EditText) chequeView
				.findViewById(R.id.cqExpDateET);
		chequeDateET.setText("");

		View voucherView = findViewById(R.id.voucherLayout);
		EditText voucherET = (EditText) voucherView
				.findViewById(R.id.voucher_number_ET);
		voucherET.setText("");
		EditText voucherAmount = (EditText) voucherView
				.findViewById(R.id.amount_ET);
		voucherAmount.setText("");

		View cnView = findViewById(R.id.cnLayout);
		EditText creditNoteNumberET = (EditText) cnView
				.findViewById(R.id.credit_note_number_ET);
		creditNoteNumberET.setText("");
		EditText creditAmount = (EditText) cnView.findViewById(R.id.amount_ET);
		creditAmount.setText("");

	}

}
