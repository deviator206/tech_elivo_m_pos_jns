package com.mpos.zreport.activity;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

import com.mpos.R;
import com.mpos.application.BaseActivity;
import com.mpos.framework.Util.UserSingleton;
import com.mpos.framework.common.Config;
import com.mpos.framework.common.Constants;
import com.mpos.framework.common.DBConstants;
import com.mpos.framework.common.Logger;
import com.mpos.framework.common.OperationalResult;
import com.mpos.framework.inf.ActivityFragmentCommunicationListener;
import com.mpos.framework.inf.BaseResponseListener;
import com.mpos.home.fragment.MyAlertDialogFragment;
import com.mpos.home.fragment.UserAccessFragment;
import com.mpos.home.helper.HomeHelper;
import com.mpos.home.model.FloatPickupPettyCurrencyModel;
import com.mpos.login.activity.LogInActivity;
import com.mpos.login.model.ResponseModel;
import com.mpos.master.model.BaseModel;
import com.mpos.master.model.CURRENCY_MST_MODEL;
import com.mpos.master.model.DNMTN_MSTModel;
import com.mpos.master.model.Petty_Float_Model;
import com.mpos.master.model.UserRightsModel;
import com.mpos.payment.adapter.CurencySpinnerAdapter;
import com.mpos.payment.helper.PaymentHelper;
import com.mpos.payment.model.MULTY_PMT_MST_Model;
import com.mpos.payment.model.PAYMT_MST_MODEL;
import com.mpos.transactions.activity.OfflineTxnUploadHelper;
import com.mpos.zreport.adapter.DenominationAdapter;
import com.mpos.zreport.helper.ZReportActivityHelper;

public class ZReportActivity extends BaseActivity implements
		BaseResponseListener, ActivityFragmentCommunicationListener, Runnable {

	public static final String TAG = Constants.APP_TAG
			+ ZReportActivity.class.getSimpleName();
	private final String WIDI_MAC_ADDRESS = "MAC_ADDRE";
	private final String Z_ROUND_OFF = "Z_ROUND_OFF";

	// Post response codes
	public static final String POST_CODE = "code";
	public static final String POST_RESPONSE = "response";
	public static final String POST_DETAILS = "detail";
	// private DecimalFormat df = new DecimalFormat("0.0000");
	private DecimalFormat df = Constants.getDecimalFormat();
	private RadioButton cashSelection;
	private RadioButton creditcardSelection;
	private RadioButton creditnoteSelection;
	private RadioButton chequeSelection;
	private RadioButton voucherSelection;
	private boolean isVerificationCompleted;
	public static String typeOfAction;

	public static final int REQUEST_DEVICE = 10011;
	private ArrayList<CURRENCY_MST_MODEL> currencyList;
	private CURRENCY_MST_MODEL mBaseCurrency;
	private ArrayList<DNMTN_MSTModel> denominationList;
	HashMap<CURRENCY_MST_MODEL, ArrayList<DNMTN_MSTModel>> currDenominationMap;
	private ArrayList<PaymentDetailModel> cashPYMNTLIst = new ArrayList<ZReportActivity.PaymentDetailModel>();
	private ArrayList<PaymentDetailModel> PYMNTLIst = new ArrayList<ZReportActivity.PaymentDetailModel>();
	private ArrayList<FloatPickupPettyCurrencyModel> mFloatCurrencyList = new ArrayList<FloatPickupPettyCurrencyModel>();
	private int count = 0;
	private Spinner currencySpinner;
	private Spinner denominationSpinner;
	private double totalSale;
	private ArrayList<MULTY_PMT_MST_Model> multiPaymentList = null;
	private ArrayList<PAYMT_MST_MODEL> paymentList = null;
	private ArrayList<BaseModel> floatPaymentList = null;
	private final String FLOAT_T = "Float";
	private final String PETTY_T = "PettyCash";
	private final String PICKUP_T = "PickUp";

	private class PaymentDetailModel {
		protected String paymentType;
		protected double amount;
		protected double amountInCurrency;
		protected String description;
		protected String currency;

		@Override
		public boolean equals(Object o) {
			if (o == null) {
				return false;
			}

			PaymentDetailModel that = (PaymentDetailModel) o;
			if (this.currency != null && that.currency != null) {
				if (that.currency.equalsIgnoreCase(this.currency)) {
					return true;

				} else
					return false;
			} else {
				if (that.description.equalsIgnoreCase(this.description)) {
					return true;

				} else {
					return false;
				}
			}
		}
	}

	public void uploadToServer() {
		uploadAllBillPayments();

		// Upload the Petty Cash\Pickup\Float
		try {
			uploadAllPettyFltData(floatPaymentList);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		// Upload the Zed Data
		createUploadZreportData();

		// post api's will update all data to server

		UserSingleton.getInstance(ZReportActivity.this).mUserDetail = null;
		Intent in = new Intent(ZReportActivity.this, LogInActivity.class);
		in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(in);

	}

	private OnClickListener mOnclickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {

			InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			if (getCurrentFocus() != null
					&& getCurrentFocus().getWindowToken() != null) {
				inputManager.hideSoftInputFromWindow(getCurrentFocus()
						.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
			}
			switch (v.getId()) {
			case R.id.addBtn:
				addEntriesIntoTable();
				break;

			case R.id.view_printBtn:

				if (totalSale == 0) {
					showAlertMessage("", "Please enter the transactions.",
							false);
				} else {

					boolean allowToPrintZReport = true;
					if (UserSingleton.getInstance(getContext()).mUserAssgndRightsModel != null) {
						allowToPrintZReport = UserSingleton
								.getInstance(getContext()).mUserAssgndRightsModel.PRINT_Z_REPORT_FLAG;
					}

					if (allowToPrintZReport) {

						// showAlertMessage("", "Z report is printing.", false);
						verifyValidationAgainstShift(true);

						ZRportDialogFragment dialog = new ZRportDialogFragment(
								ZReportActivity.this);
						dialog.show(getSupportFragmentManager(),
								"zreport-Dialog");

					} else {
						typeOfAction = "z report";
						UserAccessFragment user = new UserAccessFragment();
						user.setCommunicationListener(ZReportActivity.this);
						user.show(ZReportActivity.this
								.getSupportFragmentManager(), "user-fragmet");
					}
				}
				break;

			/*
			 * case R.id.verifyBtn: if (totalSale != 0) { //
			 * getAllPaymentTransactionTotalForLoggedInUser();
			 * verifyValidationAgainstShift(false); } else {
			 * showAlertMessage("", "Please enter the transactions.", false); }
			 * break;
			 */
			case R.id.cancelBtn:
				finish();
				break;

			/*
			 * case R.id.update_logoutBtn:
			 * 
			 * if (isVerificationCompleted) { // Upload Bills and Payment
			 * uploadAllBillPayments();
			 * 
			 * // Upload the Petty Cash\Pickup\Float try {
			 * uploadAllPettyFltData(floatPaymentList); } catch (JSONException
			 * e) { e.printStackTrace(); }
			 * 
			 * // Upload the Zed Data createUploadZreportData();
			 * 
			 * // post api's will update all data to server
			 * 
			 * UserSingleton.getInstance(ZReportActivity.this).mUserDetail =
			 * null; Intent in = new Intent(ZReportActivity.this,
			 * LogInActivity.class);
			 * in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); startActivity(in); }
			 * else { showAlertMessage("", "Please verify all the payments",
			 * false); } break;
			 */
			}
		}
	};

	private OnCheckedChangeListener onCheckChangeListener = new OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			if (isChecked) {
				cashSelection.setChecked(false);
				creditcardSelection.setChecked(false);
				creditnoteSelection.setChecked(false);
				chequeSelection.setChecked(false);
				voucherSelection.setChecked(false);

				switch (buttonView.getId()) {
				case R.id.cashSelection:
					cashSelection.setChecked(true);
					break;

				case R.id.creditnoteSelection:
					creditnoteSelection.setChecked(true);
					break;

				case R.id.creditcardSelection:
					creditcardSelection.setChecked(true);
					break;

				case R.id.voucherSelection:
					voucherSelection.setChecked(true);
					break;

				case R.id.chequeSelection:
					chequeSelection.setChecked(true);
					break;
				}
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.z_report);

		componentInitialization();

		getAllCurrenciesAndDenomination();
		getAllPaymentTransactionTotalForLoggedInUser();
		cashSelection.setChecked(true);
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		isVerificationCompleted = false;
	}

	protected void verifyValidationAgainstShift(boolean createFile) {
		double totalPayment = 0;
		double totalCashPayment = 0;
		double totalCreditcardPayment = 0;
		double totalCreditNotePayment = 0;
		double totalVoucherPayment = 0;
		double totalChequePayment = 0;
		double totalFloatPayment = 0;
		double totalPettyCashPayment = 0;
		double totalPickupPayment = 0;

		double operatortotalPayment = 0;
		double operatortotalCashPayment = 0;
		double operatortotalCreditcardPayment = 0;
		double operatortotalCreditNotePayment = 0;
		double operatortotalVoucherPayment = 0;
		double operatortotalChequePayment = 0;

		HashMap<String, Double> operatorCurrencyList = new HashMap<String, Double>();
		HashMap<String, Double> DBCurrencyList = new HashMap<String, Double>();
		for (CURRENCY_MST_MODEL currencyModel : currencyList) {
			operatorCurrencyList.put(currencyModel.getCurr_abbrName(), 0.0);
			DBCurrencyList.put(currencyModel.getCurr_abbrName(), 0.0);
		}

		for (PaymentDetailModel payment : PYMNTLIst) {
			if (payment.description.equalsIgnoreCase("Credit Note")) {
				operatortotalCreditNotePayment += payment.amount;
			} else if (payment.description.equalsIgnoreCase("Credit Card")) {
				operatortotalCreditcardPayment += payment.amount;
			} else if (payment.description.equalsIgnoreCase("Voucher")) {
				operatortotalVoucherPayment += payment.amount;
			} else if (payment.description.equalsIgnoreCase("Cheque")) {
				operatortotalChequePayment += payment.amount;
			}

		}

		for (PaymentDetailModel cash : cashPYMNTLIst) {
			operatortotalCashPayment += cash.amountInCurrency;
			Double amt = operatorCurrencyList.get(cash.currency);
			amt += cash.amountInCurrency;
			operatorCurrencyList.put(cash.currency, amt);
		}

		if (multiPaymentList != null && multiPaymentList.size() > 0) {
			for (MULTY_PMT_MST_Model multiPayment : multiPaymentList) {
				// totalCashPayment += multiPayment.getPAY_AMNT();
				Double amt = DBCurrencyList.get(multiPayment.getCURR_ABBR());
				amt += multiPayment.getLOC_AMNT();
				DBCurrencyList.put(multiPayment.getCURR_ABBR(), amt);
			}
		}

		if (mFloatCurrencyList != null && mFloatCurrencyList.size() > 0) {
			for (FloatPickupPettyCurrencyModel PTY_FLOAT_Model : mFloatCurrencyList) {
				if (PTY_FLOAT_Model.getPYMNT_TYPE().equalsIgnoreCase(FLOAT_T)) {
					totalCashPayment += PTY_FLOAT_Model.getPAY_AMNT();
					Double amt = DBCurrencyList.get(PTY_FLOAT_Model
							.getCURR_ABBR());
					amt += PTY_FLOAT_Model.getLOC_AMNT();
					DBCurrencyList.put(PTY_FLOAT_Model.getCURR_ABBR(), amt);
				}
			}
		}

		if (mFloatCurrencyList != null && mFloatCurrencyList.size() > 0) {
			for (FloatPickupPettyCurrencyModel PTY_FLOAT_Model : mFloatCurrencyList) {
				if (PTY_FLOAT_Model.getPYMNT_TYPE().equalsIgnoreCase(PETTY_T)
						|| PTY_FLOAT_Model.getPYMNT_TYPE().equalsIgnoreCase(
								PICKUP_T)) {
					operatortotalCashPayment += PTY_FLOAT_Model.getLOC_AMNT();
					Double amt = operatorCurrencyList.get(PTY_FLOAT_Model
							.getCURR_ABBR());
					amt += PTY_FLOAT_Model.getLOC_AMNT();
					operatorCurrencyList.put(PTY_FLOAT_Model.getCURR_ABBR(),
							amt);
				}
			}
		}
		if (paymentList != null && paymentList.size() > 0) {
			for (PAYMT_MST_MODEL payment : paymentList) {
				if (payment.getPAY_MODE().equalsIgnoreCase("CA")) {
					totalCashPayment += payment.getPAY_AMNT();
				} else if (payment.getPAY_MODE().equalsIgnoreCase("CC")) {
					Logger.v(TAG, "CC for loop: " + payment.getPAY_AMNT());
					totalCreditcardPayment += payment.getPAY_AMNT();
				} else if (payment.getPAY_MODE().equalsIgnoreCase("CN")) {
					totalCreditNotePayment += payment.getPAY_AMNT();
				} else if (payment.getPAY_MODE().equalsIgnoreCase("CQ")) {
					totalChequePayment += payment.getPAY_AMNT();
				} else if (payment.getPAY_MODE().equalsIgnoreCase("VO")) {
					totalVoucherPayment += payment.getPAY_AMNT();
				}
			}
		}

		// if (floatPaymentList != null && floatPaymentList.size() > 0) {
		// for (BaseModel base_Model : floatPaymentList) {
		// Petty_Float_Model PTY_FLOAT_Model = (Petty_Float_Model) base_Model;
		// if (PTY_FLOAT_Model.getPty_flt_type().equalsIgnoreCase(FLOAT_T)) {
		// totalFloatPayment += PTY_FLOAT_Model.getPty_flt_amt();
		// } else if (PTY_FLOAT_Model.getPty_flt_type().equalsIgnoreCase(
		// PETTY_T)) {
		// totalPettyCashPayment += PTY_FLOAT_Model.getPty_flt_amt();
		// } else if (PTY_FLOAT_Model.getPty_flt_type().equalsIgnoreCase(
		// PICKUP_T)) {
		// totalPickupPayment += PTY_FLOAT_Model.getPty_flt_amt();
		// }
		// }
		// }

		if (mFloatCurrencyList != null && mFloatCurrencyList.size() > 0) {
			for (FloatPickupPettyCurrencyModel PTY_FLOAT_Model : mFloatCurrencyList) {
				if (PTY_FLOAT_Model.getPYMNT_TYPE().equalsIgnoreCase(FLOAT_T)) {
					totalFloatPayment += PTY_FLOAT_Model.getLOC_AMNT();
				} else if (PTY_FLOAT_Model.getPYMNT_TYPE().equalsIgnoreCase(
						PETTY_T)) {
					totalPettyCashPayment += PTY_FLOAT_Model.getLOC_AMNT();
				} else if (PTY_FLOAT_Model.getPYMNT_TYPE().equalsIgnoreCase(
						PICKUP_T)) {
					totalPickupPayment += PTY_FLOAT_Model.getLOC_AMNT();
				}
			}
		}

		totalPayment = totalCashPayment + totalChequePayment
				+ totalCreditcardPayment + totalCreditNotePayment
				+ totalVoucherPayment;

		// totalSale = totalSale ;//+ totalPettyCashPayment +
		// totalPickupPayment;

		Logger.v(TAG, "totalCreditcardPayment before format : "
				+ totalCreditcardPayment);
		Logger.v(TAG, "totalCashPayment before format : " + totalCashPayment);

		// DecimalFormat df = Constants.getDecimalFormat();
		totalPayment = Double.parseDouble(df.format(totalPayment));
		totalCashPayment = Double.parseDouble(df.format(totalCashPayment));
		totalCreditcardPayment = Double.parseDouble(df
				.format(totalCreditcardPayment));
		totalCreditNotePayment = Double.parseDouble(df
				.format(totalCreditNotePayment));
		totalVoucherPayment = Double
				.parseDouble(df.format(totalVoucherPayment));
		totalChequePayment = Double.parseDouble(df.format(totalChequePayment));
		totalFloatPayment = Double.parseDouble(df.format(totalFloatPayment));
		totalPettyCashPayment = Double.parseDouble(df
				.format(totalPettyCashPayment));
		totalPickupPayment = Double.parseDouble(df.format(totalPickupPayment));
		operatortotalCashPayment = Double.parseDouble(df
				.format(operatortotalCashPayment));
		operatortotalCreditcardPayment = Double.parseDouble(df
				.format(operatortotalCreditcardPayment));
		operatortotalCreditNotePayment = Double.parseDouble(df
				.format(operatortotalCreditNotePayment));
		operatortotalVoucherPayment = Double.parseDouble(df
				.format(operatortotalVoucherPayment));
		operatortotalChequePayment = Double.parseDouble(df
				.format(operatortotalChequePayment));

		Logger.v(TAG, "totalCreditcardPayment after format : "
				+ totalCreditcardPayment);

		if (createFile) {
			createZReportFile(totalPayment, totalCashPayment,
					totalCreditcardPayment, totalCreditNotePayment,
					totalVoucherPayment, totalChequePayment, totalFloatPayment,
					totalPettyCashPayment, totalPickupPayment,
					operatortotalCashPayment, operatortotalCreditcardPayment,
					operatortotalCreditNotePayment,
					operatortotalVoucherPayment, operatortotalChequePayment);
			stopProgress();
			return;
		}

		// totalCashPayment = (totalCashPayment+totalFloatPayment);
		// operatortotalCashPayment = operatortotalCashPayment +
		// totalPettyCashPayment + totalPickupPayment;
		String variance = df.format((totalPayment - totalSale));
		double finalVar = Double.parseDouble(variance);

		if (totalCashPayment == operatortotalCashPayment
				&& totalChequePayment == operatortotalChequePayment
				&& totalCreditcardPayment == operatortotalCreditcardPayment
				&& totalCreditNotePayment == operatortotalCreditNotePayment
				&& totalVoucherPayment == operatortotalVoucherPayment) {
			MyAlertDialogFragment dialog = MyAlertDialogFragment.newInstance(
					"", "All validations verified.");
			dialog.show(getSupportFragmentManager(), "Alert-Dialog");
		} else {

			StringBuffer errorMessage = new StringBuffer("");
			/*
			 * if (totalCashPayment > operatortotalCashPayment) {
			 * errorMessage.append("\nShortage of cash amount by - " +
			 * mBaseCurrency.getCurr_abbrName() + " " +
			 * (operatortotalCashPayment - totalCashPayment)); } else if
			 * (totalCashPayment < operatortotalCashPayment) {
			 * errorMessage.append("\nExcess of cash amount by - " +
			 * mBaseCurrency.getCurr_abbrName() + " " +
			 * (operatortotalCashPayment - totalCashPayment)); }
			 */

			for (CURRENCY_MST_MODEL currencyModel : currencyList) {
				Double totalCurrencyPmt = DBCurrencyList.get(currencyModel
						.getCurr_abbrName());
				Double totalOPeratorCurrencyAmt = operatorCurrencyList
						.get(currencyModel.getCurr_abbrName());

				if (totalCurrencyPmt > totalOPeratorCurrencyAmt) {
					errorMessage
							.append("\nShortage of Cash-"
									+ currencyModel.getCurr_abbrName()
									+ " amount by - "
									+ mBaseCurrency.getCurr_abbrName()
									+ " "
									+ Double.parseDouble(df
											.format((totalOPeratorCurrencyAmt - totalCurrencyPmt))));
				} else if (totalCurrencyPmt < totalOPeratorCurrencyAmt) {
					errorMessage
							.append("\nExcess of cash-"
									+ currencyModel.getCurr_abbrName()
									+ "  amount by - "
									+ mBaseCurrency.getCurr_abbrName()
									+ " "
									+ Double.parseDouble(df
											.format((totalOPeratorCurrencyAmt - totalCurrencyPmt))));
				}

			}

			if (totalChequePayment > operatortotalChequePayment) {
				errorMessage
						.append("\nShortage of cheque amount by - "

								+ Double.parseDouble(df
										.format((operatortotalChequePayment - totalChequePayment))));
			} else if (totalChequePayment < operatortotalChequePayment) {
				errorMessage
						.append("\nExcess of cheque amount by - "

								+ Double.parseDouble(df
										.format((operatortotalChequePayment - totalChequePayment))));
			}

			if (totalCreditcardPayment > operatortotalCreditcardPayment) {
				errorMessage
						.append("\nShortage of credit card amount by - "

								+ Double.parseDouble(df
										.format((operatortotalCreditcardPayment - totalCreditcardPayment))));
			} else if (totalCreditcardPayment < operatortotalCreditcardPayment) {
				errorMessage
						.append("\nExcess of credit card amount by - "

								+ Double.parseDouble(df
										.format((operatortotalCreditcardPayment - totalCreditcardPayment))));
			}

			if (totalCreditNotePayment > operatortotalCreditNotePayment) {
				errorMessage
						.append("\nShortage of credit note amount by - "

								+ Double.parseDouble(df
										.format((operatortotalCreditNotePayment - totalCreditNotePayment))));
			} else if (totalCreditNotePayment < operatortotalCreditNotePayment) {
				errorMessage
						.append("\nExcess of credit note amount by - "

								+ Double.parseDouble(df
										.format((operatortotalCreditNotePayment - totalCreditNotePayment))));
			}

			if (totalVoucherPayment > operatortotalVoucherPayment) {
				errorMessage
						.append("\nShortage of voucher amount by - "

								+ Double.parseDouble(df
										.format((operatortotalVoucherPayment - totalVoucherPayment))));
			} else if (totalVoucherPayment < operatortotalVoucherPayment) {
				errorMessage
						.append("\nExcess of voucher amount by - "

								+ Double.parseDouble(df
										.format((operatortotalVoucherPayment - totalVoucherPayment))));
			}

			if (errorMessage.toString().equalsIgnoreCase("")) {
				isVerificationCompleted = true;
				MyAlertDialogFragment dialog = MyAlertDialogFragment
						.newInstance("", "Verified successfully.");
				dialog.show(getSupportFragmentManager(), "Alert-Dialog");
			} else {
				MyAlertDialogFragment dialog = MyAlertDialogFragment
						.newInstance("", errorMessage.toString());
				dialog.show(getSupportFragmentManager(), "Alert-Dialog");
			}

		}

		stopProgress();
	}

	class ZCurrecy {
		private String currency;
		private String cash;

		public String getCurrency() {
			return currency;
		}

		public void setCurrency(String currency) {
			this.currency = currency;
		}

		public String getCash() {
			return cash;
		}

		public void setCash(String cash) {
			this.cash = cash;
		}

		public JSONObject getJsonData(ZCurrecy model) throws JSONException {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("cash", model.getCash());
			jsonObj.put("currency", model.getCurrency());
			return jsonObj;
		}
	}

	/**
	 * Method- To generate ZReport data
	 * 
	 * @param b
	 */
	protected void createUploadZreportData() {
		Logger.d(TAG, "createUploadZreportData");

		double totalPayment = 0;
		double totalCashPayment = 0;
		double totalCreditcardPayment = 0;
		double totalCreditNotePayment = 0;
		double totalVoucherPayment = 0;
		double totalChequePayment = 0;
		double totalFloatPayment = 0;
		double totalPettyCashPayment = 0;
		double totalPickupPayment = 0;

		double operatortotalPayment = 0;
		double operatortotalCashPayment = 0;
		double operatortotalCreditcardPayment = 0;
		double operatortotalCreditNotePayment = 0;
		double operatortotalVoucherPayment = 0;
		double operatortotalChequePayment = 0;
		for (PaymentDetailModel cash : cashPYMNTLIst) {
			operatortotalCashPayment += cash.amountInCurrency;
		}

		for (PaymentDetailModel payment : PYMNTLIst) {
			if (payment.description.equalsIgnoreCase("Credit Note")) {
				operatortotalCreditNotePayment += payment.amount;
			} else if (payment.description.equalsIgnoreCase("Credit Card")) {
				operatortotalCreditcardPayment += payment.amount;
			} else if (payment.description.equalsIgnoreCase("Voucher")) {
				operatortotalVoucherPayment += payment.amount;
			} else if (payment.description.equalsIgnoreCase("Cheque")) {
				operatortotalChequePayment += payment.amount;
			}

		}

		if (multiPaymentList != null && multiPaymentList.size() > 0) {
			for (MULTY_PMT_MST_Model multiPayment : multiPaymentList) {
				totalCashPayment += multiPayment.getPAY_AMNT();
			}
		}

		if (paymentList != null && paymentList.size() > 0) {
			for (PAYMT_MST_MODEL payment : paymentList) {
				if (payment.getPAY_MODE().equalsIgnoreCase("CA")) {
					totalCashPayment += payment.getPAY_AMNT();
				} else if (payment.getPAY_MODE().equalsIgnoreCase("CC")) {
					totalCreditcardPayment += payment.getPAY_AMNT();
				} else if (payment.getPAY_MODE().equalsIgnoreCase("CN")) {
					totalCreditNotePayment += payment.getPAY_AMNT();
				} else if (payment.getPAY_MODE().equalsIgnoreCase("CQ")) {
					totalChequePayment += payment.getPAY_AMNT();
				} else if (payment.getPAY_MODE().equalsIgnoreCase("VO")) {
					totalVoucherPayment += payment.getPAY_AMNT();
				}
			}
		}

		if (floatPaymentList != null && floatPaymentList.size() > 0) {
			for (BaseModel base_Model : floatPaymentList) {
				Petty_Float_Model PTY_FLOAT_Model = (Petty_Float_Model) base_Model;
				if (PTY_FLOAT_Model.getPty_flt_type().equalsIgnoreCase(FLOAT_T)) {
					totalFloatPayment += PTY_FLOAT_Model.getPty_flt_amt();
				} else if (PTY_FLOAT_Model.getPty_flt_type().equalsIgnoreCase(
						PETTY_T)) {
					totalPettyCashPayment += PTY_FLOAT_Model.getPty_flt_amt();
				} else if (PTY_FLOAT_Model.getPty_flt_type().equalsIgnoreCase(
						PICKUP_T)) {
					totalPickupPayment += PTY_FLOAT_Model.getPty_flt_amt();
				}
			}
		}

		totalPayment = totalCashPayment + totalChequePayment
				+ totalCreditcardPayment + totalCreditNotePayment
				+ totalVoucherPayment + totalFloatPayment;

		totalSale = totalSale + totalPettyCashPayment + totalPickupPayment;

		// Create ZReport JSON Data//
		try {
			// TOp JSON Object
			// Zed data object
			JSONObject zedDataJsonObj = new JSONObject();

			// Add Mac address and Zed No
			SharedPreferences prefs = this.getSharedPreferences("com.mpos",
					Context.MODE_PRIVATE);
			String macAddre = prefs.getString(WIDI_MAC_ADDRESS, "");
			String zedRoundOff = prefs.getString(Z_ROUND_OFF, "");

			zedDataJsonObj.put("tabMacAddress", macAddre);
			zedDataJsonObj.put("zedNo",
					UserSingleton.getInstance(this).mMaxZedNo + 1);

			JSONArray opTotalCashSalesJsonArr = new JSONArray();
			// Create Currency JSOn objects and add in arr
			for (int j = 0; j < cashPYMNTLIst.size(); j++) {
				PaymentDetailModel paymentModel = cashPYMNTLIst.get(j);
				ZCurrecy zModel = new ZCurrecy();
				zModel.setCash(String.valueOf(paymentModel.amountInCurrency));
				zModel.setCurrency(paymentModel.currency);
				JSONObject currJsonObj = zModel.getJsonData(zModel);
				opTotalCashSalesJsonArr.put(j, currJsonObj);
			}
			// Add Currency jsonarr in zedDataJsonObj
			zedDataJsonObj.put("listCashSales", opTotalCashSalesJsonArr);
			// Add other payment details in zed Json data
			zedDataJsonObj.put("actualCrCards", operatortotalCreditcardPayment);
			zedDataJsonObj.put("actualCheques", operatortotalChequePayment);
			zedDataJsonObj.put("actualGv", operatortotalVoucherPayment);
			zedDataJsonObj.put("actualCrNotes", operatortotalCreditNotePayment);
			zedDataJsonObj.put("zedRoundOff", zedRoundOff);// Total round off
															// given

			Logger.d(TAG, "ZReport Json: " + zedDataJsonObj.toString());

			new UploadDataData().execute(Config.HTTP + Config.DNS_NAME
					+ Config.ZREPORT_UPLOAD, zedDataJsonObj.toString());

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Method - To upload all current shift Bill and Payments data
	 */
	protected void uploadAllBillPayments() {
		Logger.d(TAG, "uploadAllBillPayments");
		OfflineTxnUploadHelper txnUploadHelper = new OfflineTxnUploadHelper(
				this, true);
		txnUploadHelper.retrieveAllTransacRecords();
	}

	/**
	 * Method- TO upload all PettyFLt data
	 * 
	 * @param floatPaymentList2
	 * @throws JSONException
	 */
	protected void uploadAllPettyFltData(ArrayList<BaseModel> floatPaymentList)
			throws JSONException {
		Logger.d(TAG, "uploadAllPettyFltData");

		if (floatPaymentList != null && floatPaymentList.size() > 0) {
			JSONObject jsonObject = null;
			JSONArray jArr = null;
			jsonObject = new JSONObject();
			jArr = new JSONArray();
			for (int i = 0; i < floatPaymentList.size(); i++) {
				BaseModel base_Model = floatPaymentList.get(i);
				Petty_Float_Model PTY_FLOAT_Model = (Petty_Float_Model) base_Model;

				try {
					jArr.put(i, Petty_Float_Model.getJsonData(PTY_FLOAT_Model));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			// Add Mac address
			SharedPreferences prefs = this.getSharedPreferences("com.mpos",
					Context.MODE_PRIVATE);
			String macAddre = prefs.getString(WIDI_MAC_ADDRESS, "");
			jsonObject.put("tabMacAddress", macAddre);
			jsonObject.put("listPtyFltMst", jArr);

			Logger.d(TAG, "Final JSON: " + jsonObject.toString());
			new UploadFLTPTYData().execute(jsonObject.toString());
		}
	}

	/**
	 * Async Task to call Post URL
	 * 
	 * @author vinayakh
	 * 
	 */
	class UploadFLTPTYData extends AsyncTask<String, Void, ResponseModel> {

		protected ResponseModel doInBackground(String... urls) {
			try {
				return uploadFLTPTYDataWs(urls[0]);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}

		protected void onPostExecute(ResponseModel feed) {
			// TODO: check this.exception
			// TODO: do something with the feed
			Logger.d(TAG, "UploadFLTPTYData onPostExecute" + feed.toString());
			if (feed.getCode() == Config.POST_SUCCESS) {
				// showAlertMessage(getResources().getString(R.string.alert),
				// feed.getResponse(), false);
			} else {
				// showAlertMessage(getResources().getString(R.string.alert),
				// feed.getResponse(), false);
			}
		}
	}

	/**
	 * Method - To upload json data to server
	 * 
	 * @param json
	 * @return
	 */
	private ResponseModel uploadFLTPTYDataWs(String jsonData) {
		Logger.d(TAG, "uploadFLTPTYDataWs" + jsonData);

		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(Config.HTTP + Config.DNS_NAME
				+ Config.PTY_FLT_UPLOAD);
		JSONObject objres = null;
		ResponseModel responseModel = new ResponseModel();
		try {
			StringEntity entity = new StringEntity(jsonData, HTTP.UTF_8);
			entity.setContentType("application/json");
			post.setEntity(entity);
			HttpResponse response = client.execute(post);

			/*
			 * BufferedReader rd = new BufferedReader(new InputStreamReader(
			 * response.getEntity().getContent())); String line = ""; while
			 * ((line = rd.readLine()) != null) { try { objres = new
			 * JSONObject(line); responseModel.setCode(objres
			 * .optString(ZReportActivity.POST_CODE));
			 * responseModel.setResponse(objres
			 * .optString(ZReportActivity.POST_RESPONSE));
			 * responseModel.setDetails(objres
			 * .optString(ZReportActivity.POST_DETAILS)); } catch (Exception ex)
			 * { ex.printStackTrace(); } }
			 */
		} catch (Exception e) {
			e.printStackTrace();
		}
		// System.out.println("String Object Response>>" + objres.toString());
		return responseModel;
	}

	/**
	 * Async Task to call Post URL
	 * 
	 * @author vinayakh
	 * 
	 */
	class UploadDataData extends AsyncTask<String, Void, ResponseModel> {

		protected ResponseModel doInBackground(String... urls) {
			try {
				return uploadDataPostWs(urls[0], urls[1]);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}

		protected void onPostExecute(ResponseModel feed) {
			// TODO: check this.exception
			// TODO: do something with the feed
			Logger.d(TAG, "UploadDataData onPostExecute" + feed.toString());
			if (feed.getCode() == Config.POST_SUCCESS) {
				// showAlertMessage(getResources().getString(R.string.alert),
				// feed.getResponse(), false);
			} else {
				// showAlertMessage(getResources().getString(R.string.alert),
				// feed.getResponse(), false);
			}
		}
	}

	/**
	 * Method - To upload json data to server
	 * 
	 * @param urls
	 * 
	 * @param json
	 * @return
	 */
	private ResponseModel uploadDataPostWs(String URL, String jsonData) {
		Logger.d(TAG, "uploadDataPostWs" + jsonData);
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(URL);
		JSONObject objres = null;
		ResponseModel responseModel = new ResponseModel();
		try {
			StringEntity entity = new StringEntity(jsonData, HTTP.UTF_8);
			entity.setContentType("application/json");
			post.setEntity(entity);
			HttpResponse response = client.execute(post);

			/*
			 * BufferedReader rd = new BufferedReader(new InputStreamReader(
			 * response.getEntity().getContent())); String line = ""; while
			 * ((line = rd.readLine()) != null) { try { objres = new
			 * JSONObject(line); responseModel.setCode(objres
			 * .optString(ZReportActivity.POST_CODE));
			 * responseModel.setResponse(objres
			 * .optString(ZReportActivity.POST_RESPONSE));
			 * responseModel.setDetails(objres
			 * .optString(ZReportActivity.POST_DETAILS)); } catch (Exception ex)
			 * { ex.printStackTrace(); } }
			 */

		} catch (Exception e) {
			e.printStackTrace();
		}
		return responseModel;
	}

	private static final String CURRENTDATETIME = "$[CURRENTDATETIME]";
	private static final String USER = "&[USER]";
	private static final String TAB = "$[TAB]";
	private static final String ZNUM = "$[ZNUM]";
	private static final String TOTALSALES1 = "$[TOTALSALES1]";
	private static final String VARIANCE = "$[VARIANCE]";
	private static final String CASHSALES = "$[CASHSALES]";
	private static final String CHEQUESALES = "$[CHEQUESALES]";
	private static final String CREDITCARD = "$[CREDITCARD]";
	private static final String GIFTVOUCHER = "$[GIFTVOUCHER]";
	private static final String CREDITNOTE = "$[CREDITNOTE]";
	private static final String TOTALSALES2 = "$[TOTALSALES2]";
	private static final String NETSALES = "$[NETSALES]";
	private static final String FLOATCASH = "$[FLOATCASH]";
	private static final String DEPOSITCASH = "$[DEPOSITCASH]";
	private static final String TOTALPAYMENT = "$[TOTALPAYMENT]";
	private static final String PICKUPS = "$[PICKUPS]";
	private static final String PETTYCASH = "$[PETTYCASH]";
	private static final String TOTALPETTYCASH = "$[TOTALPETTYCASH]";
	private static final String OPCASH = "$[OPCASH]";
	private static final String OPCHEQUE = "$[OPCHEQUE]";
	private static final String OPCREDITCARD = "$[OPCREDITCARD]";
	private static final String OPGIFTVOUCHER = "$[OPGIFTVOUCHER]";
	private static final String OPCREDITNOTE = "$[OPCREDITNOTE]";
	private static final String TOTALINPUT = "$[TOTALINPUT]";
	private static final String VARDESCRIPTION = "[VARDESCRIPTION]";
	private static final String CPLUSD = "$[C+D]";

	private static final String TOTALOTHERCOLLECTION = "$[TOTALOTHERCOLLECTION]";

	private void createZReportFile(double totalPayment,
			double totalCashPayment, double totalCreditcardPayment,
			double totalCreditNotePayment, double totalVoucherPayment,
			double totalChequePayment, double totalFloatPayment,
			double totalPettyCashPayment, double totalPickupPayment,
			double operatortotalCashPayment,
			double operatortotalCreditcardPayment,
			double operatortotalCreditNotePayment,
			double operatortotalVoucherPayment,
			double operatortotalChequePayment) {
		File deletefile = new File(DBConstants.ZREPORT_FILE);
		if (deletefile.exists()) {
			deletefile.delete();
		}

		System.out.println("\n**** readAllExample ****");
		String[] row = null;

		try {
			// InputStream is =
			// this.getResources().openRawResource(R.raw.zreport);
			InputStream is = getAssets().open("zreport.csv");
			Reader reader = new BufferedReader(
					new InputStreamReader(is, "UTF8"));
			CSVReader csvReader = new CSVReader(reader);
			List content = csvReader.readAll();
			File file = new File(DBConstants.ZREPORT_FILE);
			// CSVWriter writer = new CSVWriter(new FileWriter(
			// DBConstants.ZREPORT_FILE));
			CSVWriter writer = new CSVWriter(new FileWriter(
					DBConstants.ZREPORT_FILE), ' ',
					CSVWriter.NO_QUOTE_CHARACTER);
			Time today = new Time(Time.getCurrentTimezone());
			today.setToNow();
			String time = today.monthDay + "-" + today.month + "-" + today.year
					+ " At " + today.hour + ":" + today.minute;
			double var = Double.parseDouble(df.format((totalPickupPayment
					+ totalPettyCashPayment + totalSale)
					- (totalPayment + totalFloatPayment)));

			for (Object object : content) {

				row = (String[]) object;
				String calculation = row[1];

				if (row[0].contains(USER)) {
					row[0] = row[0]
							.replace(
									row[0],
									""
											+ UserSingleton.getInstance(this).mUserDetail
													.getUSR_NM());
				} else if (row[0].contains(TAB)) {
					row[0] = row[0].replace(row[0], "");
				} else if (row[0].contains(ZNUM)) {
					row[0] = row[0].replace(row[0], "");
				} else if (row[0].contains(CURRENTDATETIME)) {
					row[0] = row[0].replace(row[0], "" + time);
				}

				if (calculation.contains(VARIANCE)) {
					row[1] = calculation.replace(calculation, "" + var);
				} else if (calculation.contains(TOTALSALES1)
						|| calculation.contains(NETSALES)
						|| calculation.contains(TOTALSALES2)) {
					row[1] = calculation
							.replace(calculation, "" + totalPayment);
				} else if (calculation.contains(CASHSALES)) {
					row[1] = calculation
							.replace(
									calculation,
									""
											+ df.format((totalCashPayment - totalFloatPayment)));
				} else if (calculation.contains(CREDITCARD)) {
					row[1] = calculation.replace(calculation, ""
							+ totalCreditcardPayment);
				} else if (calculation.contains(CREDITNOTE)) {
					row[1] = calculation.replace(calculation, ""
							+ totalCreditNotePayment);
				} else if (calculation.contains(GIFTVOUCHER)) {
					row[1] = calculation.replace(calculation, ""
							+ totalVoucherPayment);
				} else if (calculation.contains(CHEQUESALES)) {
					row[1] = calculation.replace(calculation, ""
							+ totalChequePayment);
				}/*
				 * else if (calculation.contains(TOTALOTHERCOLLECTION)) { row[1]
				 * = calculation.replace(calculation, "" + (totalFloatPayment));
				 * }
				 */else if (calculation.contains(FLOATCASH)) {
					row[1] = calculation.replace(calculation, ""
							+ totalFloatPayment);
				} else if (calculation.contains(DEPOSITCASH)) {
					row[1] = calculation.replace(calculation, "0.00");
				} else if (calculation.contains(TOTALPAYMENT)) {
					row[1] = calculation.replace(calculation, ""
							+ (totalPayment + totalFloatPayment));
				} else if (calculation.contains(PETTYCASH)) {
					row[1] = calculation.replace(calculation, ""
							+ (totalPettyCashPayment));
				} else if (calculation.contains(PICKUPS)) {
					row[1] = calculation.replace(calculation, ""
							+ (totalPickupPayment));
				} else if (calculation.contains(TOTALPETTYCASH)) {
					row[1] = calculation
							.replace(
									calculation,
									""
											+ df.format((totalPickupPayment + totalPettyCashPayment)));
				} else if (calculation.contains(OPCASH)) {
					row[1] = calculation
							.replace(
									calculation,
									""
											+ df.format((operatortotalCashPayment
													- totalPettyCashPayment - totalPickupPayment)));
				} else if (calculation.contains(OPCHEQUE)) {
					row[1] = calculation.replace(calculation, ""
							+ operatortotalChequePayment);
				} else if (calculation.contains(OPCREDITCARD)) {
					row[1] = calculation.replace(calculation, ""
							+ operatortotalCreditcardPayment);
				} else if (calculation.contains(OPCREDITNOTE)) {
					row[1] = calculation.replace(calculation, ""
							+ operatortotalCreditNotePayment);
				} else if (calculation.contains(OPGIFTVOUCHER)) {
					row[1] = calculation.replace(calculation, ""
							+ operatortotalVoucherPayment);
				} else if (calculation.contains(TOTALINPUT)) {
					row[1] = calculation.replace(calculation, "" + totalSale);
				} else if (calculation.contains(CPLUSD)) {
					row[1] = calculation
							.replace(
									calculation,
									""
											+ df.format((totalPickupPayment
													+ totalPettyCashPayment + totalSale)));
				}

				if (row[2].contains(VARDESCRIPTION)) {
					if (totalSale == totalPayment) {
						row[2] = row[2].replace(VARDESCRIPTION, "");
					} else if (totalPayment > totalSale) {
						row[2] = row[2].replace(VARDESCRIPTION, " Shortage");
					} else if (totalSale > totalPayment) {
						row[2] = row[2].replace(VARDESCRIPTION, " Excess");
					}
				}
				// Log.v(TAG, "row"+" "+row[0]+" "+calculation+" "+row[2]);
				Log.v(TAG, "row" + " " + row[0] + " " + row[1] + " " + row[2]);
				String newrow[] = { row[0], row[1], row[2] };
				writer.writeNext(newrow);
			}

			csvReader.close();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void getAllPaymentTransactionTotalForLoggedInUser() {
		startProgress();
		count += 4;
		String userName = UserSingleton.getInstance(this).mUserDetail
				.getUSR_NM();
		PaymentHelper helper = new PaymentHelper(this);
		helper.selectMultiPaymenMstDetails(userName, null,
				DBConstants.dbo_MULTI_PYMNTS_MST_TABLE, true);
		helper.selectPaymenMstDetails(userName, null,
				DBConstants.dbo_PYMNTS_MST_TABLE, true);
		HomeHelper homeHelper = new HomeHelper(this);
		homeHelper.selectPTY_FLT_MSTRecords(null,
				DBConstants.dbo_PTY_FLT_MST_TABLE, true);
		homeHelper.retrieveAllFloatCurrencies(null,
				DBConstants.dbo_FLOAT_PYMNTS_MST_TABLE, true, null);
	}

	protected void addEntriesIntoTable() {
		if (cashSelection.isChecked()) {

			CURRENCY_MST_MODEL selectedCurrency = null;
			Logger.v(
					TAG,
					"currencySpinner.getSelectedItem()"
							+ currencySpinner.getSelectedItem());
			for (int i = 0; i < currencyList.size(); i++) {

				if (currencyList
						.get(i)
						.getCurr_abbrName()
						.equalsIgnoreCase(
								"" + currencySpinner.getSelectedItem())) {
					selectedCurrency = currencyList.get(i);
					break;
				}
			}

			if (selectedCurrency != null) {

				double denomination = 1.0;

				if (denominationSpinner.getSelectedItem() != null) {
					denomination = Double.parseDouble(""
							+ denominationSpinner.getSelectedItem());
				}

				EditText et = ((EditText) findViewById(R.id.bill_num_ET));
				if (et.getText().toString().contains(".")
						&& et.getText().toString().trim().length() == 1) {
					showAlertMessage("", "Please enter valid amount", false);
					return;
				}

				int numberOfCoin = 0;
				if (!et.getText().toString().equalsIgnoreCase("")) {
					numberOfCoin = Integer.parseInt(et.getText().toString());
				} else {

				}

				Logger.v(TAG, "denominationSpinner.getSelectedItem()"
						+ denominationSpinner.getSelectedItem());
				String operator = mBaseCurrency.getCurr_operator();
				Logger.v(TAG, "operator" + operator);
				double amount = 0;
				if (operator.equalsIgnoreCase("*")) {
					amount = (denomination * numberOfCoin)
							* selectedCurrency.getCurr_exrate();
				} else if (operator.equalsIgnoreCase("/")) {
					amount = (denomination * numberOfCoin)
							/ selectedCurrency.getCurr_exrate();
				}
				Logger.v(TAG, "amount" + amount);
				amount = Double.parseDouble(df.format(amount));
				PaymentDetailModel payment = new PaymentDetailModel();
				payment.amount = (denomination * numberOfCoin);
				payment.currency = selectedCurrency.getCurr_abbrName();
				payment.amountInCurrency = amount;
				payment.description = "Cash - "
						+ selectedCurrency.getCurr_abbrName() + " "
						+ (denomination * numberOfCoin);
				if (cashPYMNTLIst.contains(payment)) {
					int index = cashPYMNTLIst.indexOf(payment);
					cashPYMNTLIst.get(index).amount += payment.amount;
					cashPYMNTLIst.get(index).amountInCurrency += amount;
					cashPYMNTLIst.get(index).description = "Cash - "
							+ selectedCurrency.getCurr_abbrName() + " "
							+ cashPYMNTLIst.get(index).amountInCurrency;
				} else {
					cashPYMNTLIst.add(payment);
					et.setText("");
				}

				if (amount == 0) {
					showAlertMessage("", "Please enter valid amount", false);
					return;
				}
			}

		} else if (creditcardSelection.isChecked()) {
			PaymentDetailModel payment = new PaymentDetailModel();
			EditText ccET = (EditText) findViewById(R.id.ccAmountET);
			if (!ccET.getText().toString().equalsIgnoreCase(""))
				payment.amount = Double.parseDouble(df.format(Double
						.parseDouble(ccET.getText().toString())));
			payment.description = "Credit Card";
			if (payment.amount == 0) {
				showAlertMessage("", "Please enter valid amount", false);
				return;
			}

			if (PYMNTLIst.contains(payment)) {
				int index = PYMNTLIst.indexOf(payment);
				PYMNTLIst.get(index).amount += payment.amount;
				ccET.setText("");
			} else {
				PYMNTLIst.add(payment);
				ccET.setText("");
			}

		} else if (creditnoteSelection.isChecked()) {
			PaymentDetailModel payment = new PaymentDetailModel();
			EditText cnET = (EditText) findViewById(R.id.cnAmountET);
			if (!cnET.getText().toString().equalsIgnoreCase(""))
				payment.amount = Double.parseDouble(df.format(Double
						.parseDouble(cnET.getText().toString())));
			payment.description = "Credit Note";
			if (payment.amount == 0) {
				showAlertMessage("", "Please enter valid amount", false);
				return;
			}
			if (PYMNTLIst.contains(payment)) {
				int index = PYMNTLIst.indexOf(payment);
				PYMNTLIst.get(index).amount += payment.amount;
				cnET.setText("");
			} else {
				PYMNTLIst.add(payment);
				cnET.setText("");
			}
		} else if (voucherSelection.isChecked()) {
			PaymentDetailModel payment = new PaymentDetailModel();
			EditText voET = (EditText) findViewById(R.id.voAmountET);
			if (!voET.getText().toString().equalsIgnoreCase(""))
				payment.amount = Double.parseDouble(df.format(Double
						.parseDouble(voET.getText().toString())));
			payment.description = "Voucher";
			if (payment.amount == 0) {
				showAlertMessage("", "Please enter valid amount", false);
				return;
			}
			if (PYMNTLIst.contains(payment)) {
				int index = PYMNTLIst.indexOf(payment);
				PYMNTLIst.get(index).amount += payment.amount;
				voET.setText("");
			} else {
				PYMNTLIst.add(payment);
				voET.setText("");
			}
		} else if (chequeSelection.isChecked()) {
			PaymentDetailModel payment = new PaymentDetailModel();
			EditText cqET = (EditText) findViewById(R.id.cqAmountET);
			if (!cqET.getText().toString().equalsIgnoreCase(""))
				payment.amount = Double.parseDouble(df.format(Double
						.parseDouble(cqET.getText().toString())));
			payment.description = "Cheque";
			if (payment.amount == 0) {
				showAlertMessage("", "Please enter valid amount", false);
				return;
			}
			if (PYMNTLIst.contains(payment)) {
				int index = PYMNTLIst.indexOf(payment);
				PYMNTLIst.get(index).amount += payment.amount;
				cqET.setText("");
			} else {
				PYMNTLIst.add(payment);
				cqET.setText("");
			}
		} else {
			MyAlertDialogFragment dialog = MyAlertDialogFragment.newInstance(
					"", "Please select the type/amount to be entered");
			dialog.show(getSupportFragmentManager(), "alert-dialog");
			return;
		}

		showPaymentDetails();
	}

	private void componentInitialization() {
		((LinearLayout) findViewById(R.id.dummy_id)).requestFocus();
		currencySpinner = (Spinner) findViewById(R.id.currency_spinner);
		denominationSpinner = (Spinner) findViewById(R.id.denomination_spinner);
		findViewById(R.id.addBtn).setOnClickListener(mOnclickListener);
		findViewById(R.id.view_printBtn).setOnClickListener(mOnclickListener);
		// findViewById(R.id.verifyBtn).setOnClickListener(mOnclickListener);
		// findViewById(R.id.update_logoutBtn).setOnClickListener(mOnclickListener);
		findViewById(R.id.cancelBtn).setOnClickListener(mOnclickListener);
		if (currencyList != null) {
			CurencySpinnerAdapter adapter = new CurencySpinnerAdapter(this,
					android.R.layout.simple_spinner_item, currencyList);
			currencySpinner.setAdapter(adapter);
		}
		cashSelection = (RadioButton) findViewById(R.id.cashSelection);
		chequeSelection = (RadioButton) findViewById(R.id.chequeSelection);
		creditcardSelection = (RadioButton) findViewById(R.id.creditcardSelection);
		creditnoteSelection = (RadioButton) findViewById(R.id.creditnoteSelection);
		voucherSelection = (RadioButton) findViewById(R.id.voucherSelection);
		cashSelection.setOnCheckedChangeListener(onCheckChangeListener);
		creditcardSelection.setOnCheckedChangeListener(onCheckChangeListener);
		creditnoteSelection.setOnCheckedChangeListener(onCheckChangeListener);
		voucherSelection.setOnCheckedChangeListener(onCheckChangeListener);
		chequeSelection.setOnCheckedChangeListener(onCheckChangeListener);

		currencySpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				ArrayList<DNMTN_MSTModel> currdenominationL = null;
				if (currDenominationMap != null) {
					Set<CURRENCY_MST_MODEL> keyset = currDenominationMap
							.keySet();
					for (Iterator iterator = keyset.iterator(); iterator
							.hasNext();) {
						CURRENCY_MST_MODEL currency_MST_MODEL = (CURRENCY_MST_MODEL) iterator
								.next();
						if (currency_MST_MODEL.getCurr_abbrName()
								.equalsIgnoreCase(
										"" + currencySpinner.getSelectedItem())) {
							currdenominationL = currDenominationMap
									.get(currency_MST_MODEL);
						}
					}
				}

				// ArrayList<DNMTN_MSTModel> currdenominationList = new
				// ArrayList<DNMTN_MSTModel>();
				// for (int i = 0; i < denominationList.size(); i++) {
				// if(denominationList.get(i).getCURR_CODE().equalsIgnoreCase(""+currencySpinner.getSelectedItem())){
				// currdenominationList.add(denominationList.get(i));
				// }
				// }
				denominationSpinner
						.setAdapter(new DenominationAdapter(
								ZReportActivity.this,
								android.R.layout.simple_spinner_item,
								currdenominationL));
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});
	}

	private void getAllCurrenciesAndDenomination() {
		if (!this.isFinishing())
			startProgress();
		count = 2;
		ZReportActivityHelper helper = new ZReportActivityHelper(this);
		helper.selectCurrencyRecords(null, DBConstants.dbo_Currency_Mst_TABLE,
				true);
		helper.selectDenominationRecords(null, DBConstants.dbo_DNMTN_MST_TABLE,
				true);
	}

	private HashMap<CURRENCY_MST_MODEL, ArrayList<DNMTN_MSTModel>> createCurrencyDenominationMap() {
		if (currencyList != null && denominationList != null) {
			HashMap<CURRENCY_MST_MODEL, ArrayList<DNMTN_MSTModel>> currencyDenominationMap = new HashMap<CURRENCY_MST_MODEL, ArrayList<DNMTN_MSTModel>>();
			Logger.v(TAG, ":" + currencyList.size());
			Logger.v(TAG, ":" + denominationList.size());
			for (int i = 0; i < currencyList.size(); i++) {
				currencyDenominationMap.put(currencyList.get(i),
						new ArrayList<DNMTN_MSTModel>());
			}

			Set<CURRENCY_MST_MODEL> keyset = currencyDenominationMap.keySet();
			for (Iterator iterator = keyset.iterator(); iterator.hasNext();) {
				CURRENCY_MST_MODEL currency_MST_MODEL = (CURRENCY_MST_MODEL) iterator
						.next();
				String key = currency_MST_MODEL.getCurr_abbrName();
				for (int i = 0; i < denominationList.size(); i++) {
					if (key.equalsIgnoreCase(denominationList.get(i)
							.getCURR_CODE())) {
						currencyDenominationMap.get(currency_MST_MODEL).add(
								denominationList.get(i));
					}
				}
			}
			mBaseCurrency = getBaseCurrency();
			if (!this.isFinishing())
				stopProgress();
			return currencyDenominationMap;
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void executePostAction(OperationalResult opResult) {
		switch (opResult.getResultCode()) {

		// Upload completed successfull
		case OperationalResult.SUCCESS:
			Logger.d(TAG, "ZReport Bill&Payment Upload Success");
			break;

		case Constants.FETCH_CURRENCIES:
			currencyList = (ArrayList<CURRENCY_MST_MODEL>) opResult.getResult()
					.getSerializable(Constants.RESULTCODEBEAN);
			currDenominationMap = createCurrencyDenominationMap();
			count--;
			if (count == 0) {

				componentInitialization();
			}
			break;

		case Constants.FETCH_FLOAT_CURRENCT_RECORDS:
			ArrayList<FloatPickupPettyCurrencyModel> floatCurrList = (ArrayList<FloatPickupPettyCurrencyModel>) opResult
					.getResult().getSerializable(Constants.RESULTCODEBEAN);
			if (mFloatCurrencyList == null) {
				mFloatCurrencyList = new ArrayList<FloatPickupPettyCurrencyModel>();
			}
			mFloatCurrencyList.clear();
			if (floatCurrList != null) {
				mFloatCurrencyList.addAll(floatCurrList);
			}
			count--;
			if (count == 0) {

				componentInitialization();
			}
			break;
		case Constants.FETCH_DNMTN_MST:
			denominationList = (ArrayList<DNMTN_MSTModel>) opResult.getResult()
					.getSerializable(Constants.RESULTCODEBEAN);
			currDenominationMap = createCurrencyDenominationMap();
			count--;
			if (count == 0) {
				componentInitialization();
			}
			break;

		case Constants.FETCH_MULTY_PYMNT_MST_RECORD:
			count--;
			multiPaymentList = (ArrayList<MULTY_PMT_MST_Model>) opResult
					.getResult().getSerializable(Constants.RESULTCODEBEAN);
			if (count == 0) {
				componentInitialization();
				// verifyValidationAgainstShift();
			}
			break;

		case Constants.FETCH_PYMNT_MST_RECORD:
			count--;
			paymentList = (ArrayList<PAYMT_MST_MODEL>) opResult.getResult()
					.getSerializable(Constants.RESULTCODEBEAN);
			if (count == 0) {
				componentInitialization();
				// verifyValidationAgainstShift();
			}
			break;

		case Constants.FETCH_PTY_FLT_MST_RECORD:
			count--;
			floatPaymentList = (ArrayList<BaseModel>) opResult.getResult()
					.getSerializable(Constants.RESULTCODEBEAN);
			if (count == 0) {
				componentInitialization();
				// verifyValidationAgainstShift();
			}
			break;
		}
	}

	@Override
	public void errorReceived(OperationalResult opResult) {

	}

	private CURRENCY_MST_MODEL getBaseCurrency() {
		CURRENCY_MST_MODEL baseCurrency = null;
		if (currencyList != null) {
			for (CURRENCY_MST_MODEL currencyModel : currencyList) {
				if (currencyModel.getCurr_base_flag().equalsIgnoreCase("y")) {
					baseCurrency = currencyModel;
					break;
				}
			}
		}
		return baseCurrency;
	}

	protected void showPaymentDetails() {

		LinearLayout paymentListView = (LinearLayout) findViewById(R.id.addPaymentDetailLL);
		paymentListView.removeAllViews();
		LinearLayout blankRow1 = (LinearLayout) LayoutInflater.from(this)
				.inflate(R.layout.payment_detail_row, null);
		((TextView) blankRow1.findViewById(R.id.paymentText))
				.setText(getResources().getString(R.string.payment_due));
		blankRow1.findViewById(R.id.paymentText).setVisibility(View.INVISIBLE);
		paymentListView.addView(blankRow1);

		totalSale = 0;
		if (cashPYMNTLIst != null) {
			for (final PaymentDetailModel paymentModel : cashPYMNTLIst) {
				View paymentRow = LayoutInflater.from(this).inflate(
						R.layout.payment_detail_row, null);
				((TextView) paymentRow.findViewById(R.id.paymentText))
						.setText(paymentModel.description);

				String amount = df.format(paymentModel.amountInCurrency);
				double finalAmount = Double.parseDouble(amount);

				((TextView) paymentRow.findViewById(R.id.paymentamount))
						.setText("" + finalAmount);
				totalSale += paymentModel.amountInCurrency;
				paymentListView.addView(paymentRow);
				/*
				 * paymentRow.setOnLongClickListener(new OnLongClickListener() {
				 * 
				 * @Override public boolean onLongClick(View v) {
				 * AlertDialog.Builder builder = new AlertDialog.Builder(
				 * ZReportActivity.this); builder.setCancelable(false)
				 * .setNegativeButton("Cancel", new
				 * DialogInterface.OnClickListener() {
				 * 
				 * @Override public void onClick( DialogInterface dialog, int
				 * which) { dialog.dismiss(); } }) .setPositiveButton("OK", new
				 * DialogInterface.OnClickListener() { public void onClick(
				 * DialogInterface dialog, int which) { cashPYMNTLIst
				 * .remove(paymentModel); dialog.dismiss(); } })
				 * .setMessage("Do you want to delete this entry?"); AlertDialog
				 * alert = builder.create(); alert.setCancelable(false);
				 * alert.show(); return true; } });
				 */
			}
		}

		LinearLayout blankRow2 = (LinearLayout) LayoutInflater.from(this)
				.inflate(R.layout.payment_detail_row, null);
		((TextView) blankRow2.findViewById(R.id.paymentText))
				.setText(getResources().getString(R.string.payment_due));
		blankRow2.findViewById(R.id.paymentText).setVisibility(View.INVISIBLE);
		paymentListView.addView(blankRow2);

		if (PYMNTLIst != null) {
			for (final PaymentDetailModel paymentModel : PYMNTLIst) {
				View paymentRow = LayoutInflater.from(this).inflate(
						R.layout.payment_detail_row, null);
				((TextView) paymentRow.findViewById(R.id.paymentText))
						.setText(paymentModel.description);
				((TextView) paymentRow.findViewById(R.id.paymentamount))
						.setText("" + paymentModel.amount);
				totalSale += paymentModel.amount;
				paymentListView.addView(paymentRow);

				/*
				 * paymentRow.setOnLongClickListener(new OnLongClickListener() {
				 * 
				 * @Override public boolean onLongClick(View v) {
				 * AlertDialog.Builder builder = new AlertDialog.Builder(
				 * ZReportActivity.this); builder.setCancelable(false)
				 * .setNegativeButton("Cancel", new
				 * DialogInterface.OnClickListener() {
				 * 
				 * @Override public void onClick( DialogInterface dialog, int
				 * which) { dialog.dismiss(); } }) .setPositiveButton("OK", new
				 * DialogInterface.OnClickListener() { public void onClick(
				 * DialogInterface dialog, int which) {
				 * PYMNTLIst.remove(paymentModel); dialog.dismiss(); } })
				 * .setMessage("Do you want to delete this entry?"); AlertDialog
				 * alert = builder.create(); alert.setCancelable(false);
				 * alert.show(); return true; } });
				 */
			}
		}

		LinearLayout blankRow3 = (LinearLayout) LayoutInflater.from(this)
				.inflate(R.layout.payment_detail_row, null);
		((TextView) blankRow3.findViewById(R.id.paymentText))
				.setText(getResources().getString(R.string.payment_due));
		blankRow3.findViewById(R.id.paymentText).setVisibility(View.INVISIBLE);
		paymentListView.addView(blankRow3);

		View totalPaymentRow = LayoutInflater.from(this).inflate(
				R.layout.payment_detail_row, null);
		((TextView) totalPaymentRow.findViewById(R.id.paymentText))
				.setText(getResources().getString(R.string.z_total) + "-"
						+ mBaseCurrency.getCurr_abbrName());

		String totalS = df.format(totalSale);
		double finalTotal = Double.parseDouble(totalS);

		((TextView) totalPaymentRow.findViewById(R.id.paymentamount))
				.setText(" " + finalTotal);
		paymentListView.addView(totalPaymentRow);

	}

	@Override
	public void userAuthenticationSuccess(UserRightsModel userRights) {
		if (ZReportActivity.typeOfAction.equalsIgnoreCase("z report")) {
			boolean allowToPrintZReport = userRights.PRINT_Z_REPORT_FLAG;
			if (allowToPrintZReport) {
				// showAlertMessage("", "Z report is printing.", false);
				verifyValidationAgainstShift(true);

				ZRportDialogFragment dialog = new ZRportDialogFragment(
						ZReportActivity.this);
				dialog.show(getSupportFragmentManager(), "zreport-Dialog");
			} else {
				showAlertMessage("",
						"This user doesn't have access to print the z Report.",
						false);
			}
		}
	}

	BluetoothAdapter mBluetoothAdapter;
	private UUID applicationUUID = UUID
			.fromString("00001101-0000-1000-8000-00805F9B34FB");
	private ProgressDialog mBluetoothConnectProgressDialog;
	private BluetoothSocket mBluetoothSocket;
	BluetoothDevice mBluetoothDevice;

	public void onActivityResult(int requestCode, int resultCode,
			Intent mDataIntent) {

		switch (requestCode) {

		case REQUEST_DEVICE:
			if (resultCode == Activity.RESULT_OK) {
				Toast.makeText(ZReportActivity.this,
						"RESULT_OK zreportactivity", 2000).show();
				Bundle mExtra = mDataIntent.getExtras();
				String mDeviceAddress = mExtra.getString("DeviceAddress");
				Logger.d(TAG, "Coming incoming address " + mDeviceAddress);
				mBluetoothDevice = mBluetoothAdapter
						.getRemoteDevice(mDeviceAddress);
				mBluetoothConnectProgressDialog = ProgressDialog.show(this,
						"Connecting...", mBluetoothDevice.getName() + " : "
								+ mBluetoothDevice.getAddress(), true, false);
				Thread mBlutoothConnectThread = new Thread(this);
				mBlutoothConnectThread.start();
				// pairToDevice(mBluetoothDevice); This method is replaced by
				// progress dialog with thread
			} else {
				Toast.makeText(ZReportActivity.this, "Bluetooth is disable",
						2000).show();
			}
			break;
		}

	}

	@Override
	public void run() {
		try {
			mBluetoothSocket = mBluetoothDevice
					.createRfcommSocketToServiceRecord(applicationUUID);
			Method m;
			try {

				m = mBluetoothDevice.getClass().getMethod("createRfcommSocket",
						new Class[] { int.class });
				mBluetoothSocket = (BluetoothSocket) m.invoke(mBluetoothDevice,
						Integer.valueOf(1));
				Log.d(TAG, ":" + mBluetoothSocket.getRemoteDevice());

			} catch (SecurityException e) {

				e.printStackTrace();
			} catch (NoSuchMethodException e) {

				e.printStackTrace();
			} catch (IllegalArgumentException e) {

				e.printStackTrace();
			} catch (IllegalAccessException e) {

				e.printStackTrace();
			} catch (InvocationTargetException e) {

				e.printStackTrace();
			}

			mBluetoothAdapter.cancelDiscovery();
			mBluetoothSocket.connect();
			mHandler.sendEmptyMessage(0);
		} catch (IOException eConnectException) {
			mBluetoothConnectProgressDialog.dismiss();
			Log.d(TAG, "CouldNotConnectToSocket", eConnectException);
			closeSocket(mBluetoothSocket);
			return;
		}
	};

	private void closeSocket(BluetoothSocket nOpenSocket) {
		try {
			nOpenSocket.close();
			Log.d(TAG, "SocketClosed");
		} catch (IOException ex) {
			Log.d(TAG, "CouldNotCloseSocket");
		}
	}

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			mBluetoothConnectProgressDialog.dismiss();
			Toast.makeText(ZReportActivity.this,
					"Device Connected and send to the printer for printing.",
					5000).show();
			sendDataToPrinter();
		}
	};

	protected void sendDataToPrinter() {
		Thread t = new Thread() {
			public void run() {
				try {
					OutputStream os = mBluetoothSocket.getOutputStream();

					/*
					 * InputStream is = getContext().openFileInput(
					 * DBConstants.ZREPORT_FILE);
					 */

					File f = new File(DBConstants.ZREPORT_FILE);
					FileInputStream is = new FileInputStream(f);

					ByteArrayOutputStream bos = new ByteArrayOutputStream();
					byte[] b = new byte[1024];
					int bytes = 0;
					while ((bytes = is.read(b)) != -1) {
						bos.write(b, 0, bytes);
					}

					os.write(bos.toByteArray());
					// printer specific code you can comment ==== > End

					// bos.write(0);
					// bos.flush();
					// Logger.d(TAG, new String(b));

					closeSocket(mBluetoothSocket);
				} catch (Exception e) {
					Logger.e(TAG, "Exeception:" + e.toString());
					e.printStackTrace();
				}
			}
		};
		t.start();
	}

	@Override
	public void userAuthenticationError() {
		// TODO Auto-generated method stub

	}

}
