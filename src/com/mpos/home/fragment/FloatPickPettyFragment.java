/*
 * Copyright (C) 2013 jaggarnaut-studios Pvt. Ltd. All Rights Reserved.
 * jaggarnaut-studios pvt. ltd. PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.mpos.home.fragment;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;

import com.mpos.R;
import com.mpos.application.BaseActivity;
import com.mpos.application.MPOSApplication;
import com.mpos.framework.Util.UserSingleton;
import com.mpos.framework.common.Config;
import com.mpos.framework.common.Constants;
import com.mpos.framework.common.DBConstants;
import com.mpos.framework.common.Logger;
import com.mpos.framework.common.OperationalResult;
import com.mpos.framework.inf.BaseResponseListener;
import com.mpos.home.helper.HomeHelper;
import com.mpos.home.model.FloatPickupPettyCurrencyModel;
import com.mpos.login.activity.LogInActivity;
import com.mpos.login.model.ResponseModel;
import com.mpos.master.helper.MasterHelper;
import com.mpos.master.model.CURRENCY_MST_MODEL;
import com.mpos.master.model.MULTI_PTY_FLT_MST_Model;
import com.mpos.master.model.Petty_Float_Model;
import com.mpos.payment.adapter.CurencySpinnerAdapter;
import com.mpos.payment.helper.PaymentHelper;
import com.mpos.transactions.model.TempTXNModel;
import com.mpos.zreport.activity.ZReportActivity;

public class FloatPickPettyFragment extends DialogFragment implements
		BaseResponseListener {

	public static final String TAG = Constants.APP_TAG
			+ FloatPickPettyFragment.class.getSimpleName();

	private Button mDoneBtn;
	private Button mCancelBtn;
	private EditText mAmountEt;
	private EditText mDetailsEt;
	private Spinner mCurrSpinner;
	private RadioGroup mRadioGroup;
	private RadioButton mRadioButton;

	private final int FLOAT = 0;
	private final int PETTY = 1;
	private final int PICKUP = 2;

	private int numberOfRequests = 0;
	private final String FLOAT_T = "AF";
	private final String PETTY_T = "P";
	private final String PICKUP_T = "C";
	private DecimalFormat df = Constants.getDecimalFormat();

	private ArrayList<FloatPickupPettyCurrencyModel> mFloatCurrencyList = new ArrayList<FloatPickupPettyCurrencyModel>();
	private ArrayList<CURRENCY_MST_MODEL> mCurrncyMstDataList = null;
	private CURRENCY_MST_MODEL mSelectedCurrencyModel = null;
	private CurencySpinnerAdapter mCurrencyAdapter = null;

	private OnClickListener mClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {

			InputMethodManager inputManager = (InputMethodManager) getActivity()
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			if (getActivity().getCurrentFocus() != null
					&& getActivity().getCurrentFocus().getWindowToken() != null) {
				inputManager.hideSoftInputFromWindow(getActivity()
						.getCurrentFocus().getWindowToken(),
						InputMethodManager.HIDE_NOT_ALWAYS);
			}
			switch (v.getId()) {
			case R.id.doneBtn:
				validateInputs(v);
				break;

			case R.id.cancelBtn:
				if (getDialog() != null && getDialog().isShowing())
					getDialog().dismiss();
				break;

			default:
				break;
			}
		}
	};

	private OnItemSelectedListener mItemSelectionListener = new OnItemSelectedListener() {

		@Override
		public void onItemSelected(AdapterView<?> parentView, View view,
				int position, long arg3) {
			Logger.d(TAG, "position"
					+ mCurrncyMstDataList.get(position).getCurr_name());
			mSelectedCurrencyModel = mCurrncyMstDataList.get(position);
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {

		}
	};

	private OnCheckedChangeListener mCheckChangeListener = new OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			Logger.d(TAG, "onCheckedChanged");
			switch (checkedId) {
			case R.id.radioFloat:
				disableDtlEt();
				break;

			case R.id.radioPeetyCash:
				enableDtlEt();
				break;

			case R.id.radioPickUp:
				disableDtlEt();
				break;

			default:
				break;
			}
		}
	};

	private void enableDtlEt() {
		mDetailsEt.setEnabled(true);
	}

	private void disableDtlEt() {
		mDetailsEt.setEnabled(false);
	}

	static FloatPickPettyFragment newInstance() {
		FloatPickPettyFragment f = new FloatPickPettyFragment();
		return f;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		setStyle(STYLE_NO_FRAME, android.R.style.Theme_NoTitleBar);
		getDialog().setTitle(
				getResources().getString(R.string.float_pty_pckUp_alert_title));

		View view = inflater.inflate(
				R.layout.float_pickup_petty_cash_dialog_fragment, null);

		view.findViewById(R.id.dummy_id).requestFocus();
		mDoneBtn = (Button) view.findViewById(R.id.doneBtn);
		mDoneBtn.setOnClickListener(mClickListener);
		mCancelBtn = (Button) view.findViewById(R.id.cancelBtn);
		mCancelBtn.setOnClickListener(mClickListener);

		mAmountEt = (EditText) view.findViewById(R.id.amtEt);
		mDetailsEt = (EditText) view.findViewById(R.id.dtlsEt);
		mCurrSpinner = (Spinner) view.findViewById(R.id.currncySpinner);

		mRadioGroup = (RadioGroup) view.findViewById(R.id.radioGroup);
		mRadioGroup.setOnCheckedChangeListener(mCheckChangeListener);
		mRadioGroup.check(R.id.radioFloat);

		numberOfRequests = 2;
		MasterHelper masterHelper = new MasterHelper(this, false);
		masterHelper.retrieveAllCurrencyFromDB(
				DBConstants.dbo_Currency_Mst_TABLE, true);

		HomeHelper helper = new HomeHelper(this);
		helper.retrieveAllFloatCurrencies(null,
				DBConstants.dbo_FLOAT_PYMNTS_MST_TABLE, true, null);
		return view;
	}

	protected void validateInputs(View v) {
		String amtData = mAmountEt.getText().toString().trim();
		boolean success = false;
		int option = -1;

		// Check AMount data is correct
		if (amtData != null & amtData.length() > 0) {
			// Check which radio button selected

			option = getSelectedRadioOption(v);
			// If option selected is Petty Cash, then check detail field, should
			// not be empty
			if (option == PETTY) {
				String dtlText = mDetailsEt.getText().toString().trim();
				if (dtlText != null && dtlText.length() > 0) {
					success = true;
				}
			} else {
				success = true;
			}
		}

		if ((mAmountEt.getText().toString().contains(".") && mAmountEt
				.getText().toString().trim().length() == 1)) {
			success = false;
		}
		if (success) {
			InputMethodManager inputManager = (InputMethodManager) getContext()
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			inputManager.hideSoftInputFromWindow(getActivity()
					.getCurrentFocus().getWindowToken(),
					InputMethodManager.HIDE_NOT_ALWAYS);
			numberOfRequests = 3;
			insertPtyFlt_MSTDB(option);
			insertMultiPtyFlt_MSTDB(option);
			insertAllDetailsInFloatPettyPickupCuurencyTable(option);

		} else {
			showAlertMessage(null,
					getResources().getString(R.string.alert_wrong_inputs));
		}
	}

	/**
	 * Method To upload json data to server
	 * 
	 * @param pfMSt_Model
	 */
	private void createJsonReponseUpload(Petty_Float_Model pfMSt_Model) {
		BaseActivity.loadAllConfigData();
		JSONObject jsonObject = null;
		try {
			jsonObject = new JSONObject();
			JSONArray jArr = new JSONArray();
			jArr.put(0, Petty_Float_Model.getJsonData(pfMSt_Model));

			// get mac address data from shardPref
			SharedPreferences prefs = getActivity().getSharedPreferences(
					"com.mpos", Context.MODE_PRIVATE);
			String macAddrss = prefs.getString(
					MPOSApplication.WIFI_MAC_ADDRESS, "");
			jsonObject.put("tabMacAddress", macAddrss);

			jsonObject.put("listMultiPtyFlt", jArr);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Logger.d(TAG, "Final JSON: " + jsonObject.toString());
		new UploadFLTPTYData().execute(jsonObject.toString());
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
				// feed.getResponse());
			} else {
				// showAlertMessage(getResources().getString(R.string.alert),
				// feed.getResponse());
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

			BufferedReader rd = new BufferedReader(new InputStreamReader(
					response.getEntity().getContent()));
			String line = "";
			while ((line = rd.readLine()) != null) {
				try {
					objres = new JSONObject(line);
					responseModel.setCode(objres
							.optString(ZReportActivity.POST_CODE));
					responseModel.setResponse(objres
							.optString(ZReportActivity.POST_RESPONSE));
					responseModel.setDetails(objres
							.optString(ZReportActivity.POST_DETAILS));
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		// System.out.println("String Object Response>>" + objres.toString());
		// return objres.toString();
		return responseModel;
	}

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
				R.string.radio_float))) {
			return FLOAT;
		} else if (optionSelected.equalsIgnoreCase(getResources().getString(
				R.string.radio_petty))) {
			return PETTY;
		} else if (optionSelected.equalsIgnoreCase(getResources().getString(
				R.string.radio_pickup))) {
			return PICKUP;
		}
		return -1;
	}

	/**
	 * Method - To insert into PTY_FLT DB data
	 * 
	 * @param option
	 */
	private void insertPtyFlt_MSTDB(int option) {
		BaseActivity.loadAllConfigData();
		Petty_Float_Model pfMSt_Model = new Petty_Float_Model();
		pfMSt_Model.setBranch_code(Config.BRANCH_CODE);
		pfMSt_Model.setCmpny_code(Config.COMPANY_CODE);

		double amt = Double.parseDouble(df.format(Double.parseDouble(mAmountEt
				.getText().toString().trim())));
		double exRate = mSelectedCurrencyModel.getCurr_exrate();
		double locAmt = 0.0;

		if (mSelectedCurrencyModel.getCurr_operator().equalsIgnoreCase("*")) {
			locAmt = amt * exRate;
		} else if (mSelectedCurrencyModel.getCurr_operator().equalsIgnoreCase(
				"/")) {
			locAmt = amt / exRate;
		}

		pfMSt_Model.setPty_flt_amt(amt);
		pfMSt_Model.setPty_flt_exRate(Double.parseDouble(df.format(exRate)));
		pfMSt_Model.setPty_flt_loc_amt(Double.parseDouble(df.format(locAmt)));
		pfMSt_Model.setPty_flt_operator(mSelectedCurrencyModel
				.getCurr_operator());
		pfMSt_Model.setPty_flt_curr_Abbr(mSelectedCurrencyModel
				.getCurr_abbrName());

		switch (option) {
		case FLOAT:
			pfMSt_Model.setPty_flt_type(FLOAT_T);
			break;

		case PICKUP:
			pfMSt_Model.setPty_flt_type(PICKUP_T);
			break;

		case PETTY:
			pfMSt_Model.setPty_flt_dtls(mDetailsEt.getText().toString().trim());
			pfMSt_Model.setPty_flt_type(PETTY_T);
			break;

		default:
			break;
		}

		SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT);
		String currentDateandTime = sdf.format(new Date());
		pfMSt_Model.setPty_sys_date(currentDateandTime);
		pfMSt_Model.setPty_flt_run_date(BaseActivity.getServerRunDate());

		pfMSt_Model.setPty_sr_no(UserSingleton.getInstance(
				MPOSApplication.getContext()).getFPPCount());
		pfMSt_Model.setTill_no(String.valueOf(Config.TAB_NO));
		pfMSt_Model.setTransca_no(String.valueOf(UserSingleton.getInstance(
				getContext()).getFPPCount()));

		pfMSt_Model
				.setUser_name(UserSingleton.getInstance(getContext()).mUserName);
		pfMSt_Model.setUser_name_scnd("N");// Hard Coded

		HomeHelper pfHelper = new HomeHelper(this);
		pfHelper.saveIn_PTY_FLT_MSTDB(pfMSt_Model);

		// check for nw connection
		if (UserSingleton.getInstance(MPOSApplication.getContext()).isNetworkAvailable) {// Yes
			// To upload json data to server
			createJsonReponseUpload(pfMSt_Model);
		} else {
			// Save the respective transaction no in Temp TXN table.
			TempTXNModel tempTXNModel = new TempTXNModel();
			tempTXNModel.setTxn_no(pfMSt_Model.getTransca_no());
			tempTXNModel.setIsUploaded("N");

			PaymentHelper helper = new PaymentHelper(this);
			helper.saveInTempTXNDB(tempTXNModel,
					DBConstants.dbo_TEMP_FLT_PTY_PKUP_TXN_TABLE, true);
		}

		// Increment FPP cnt for next post upload/: only when upload Success and
		// add entry in Shared pref
		UserSingleton.getInstance(getActivity()).incrementFPPCount();
		SharedPreferences prefs = MPOSApplication.getContext()
				.getSharedPreferences("com.mpos", Context.MODE_PRIVATE);
		Editor et = prefs.edit();
		et.putInt(LogInActivity.FLT_PTY_PKUP_CNT,
				UserSingleton.getInstance(MPOSApplication.getContext())
						.getFPPCount());// put back updated
										// it
										// back
										// in
										// pref
		et.commit();

	}

	/**
	 * Insert all currency details in flotpettypickup table for zed calculation
	 */
	private void insertAllDetailsInFloatPettyPickupCuurencyTable(
			int selectedOption) {
		FloatPickupPettyCurrencyModel model = new FloatPickupPettyCurrencyModel();

		double baseAmt = Double.parseDouble(df.format(Double
				.parseDouble(mAmountEt.getText().toString().trim())));
		double exRate = mSelectedCurrencyModel.getCurr_exrate();
		double amtInForeignCurr = 0.0;

		if (mSelectedCurrencyModel.getCurr_operator().equalsIgnoreCase("*")) {
			amtInForeignCurr = baseAmt * exRate;
		} else if (mSelectedCurrencyModel.getCurr_operator().equalsIgnoreCase(
				"/")) {
			amtInForeignCurr = baseAmt / exRate;
		}

		switch (selectedOption) {
		case FLOAT:
			model.setPYMNT_TYPE(FLOAT_T);
			break;

		case PICKUP:
			model.setPYMNT_TYPE(PICKUP_T);
			break;

		case PETTY:
			model.setPYMNT_TYPE(PETTY_T);
			break;
		}
		model.setLOC_AMNT(Double.parseDouble(df.format(amtInForeignCurr)));
		model.setPAY_AMNT(baseAmt);
		model.setCURR_ABBR("" + mSelectedCurrencyModel.getCurr_abbrName());

		if (mFloatCurrencyList.contains(model)) {
			int index = mFloatCurrencyList.indexOf(model);
			mFloatCurrencyList.get(index).setPAY_AMNT(
					mFloatCurrencyList.get(index).getPAY_AMNT() + baseAmt);
			mFloatCurrencyList.get(index).setLOC_AMNT(
					mFloatCurrencyList.get(index).getLOC_AMNT()
							+ amtInForeignCurr);
		} else {
			mFloatCurrencyList.add(model);
		}

		HomeHelper pfHelper = new HomeHelper(this);
		pfHelper.saveInFloatPettyCurrencyMSTDB(mFloatCurrencyList);
	}

	/**
	 * Method- To insert Multi Payments data in DB
	 * 
	 * @param option
	 */
	private void insertMultiPtyFlt_MSTDB(int option) {
		BaseActivity.loadAllConfigData();
		MULTI_PTY_FLT_MST_Model multi_PTY_FLT_MST_Model = new MULTI_PTY_FLT_MST_Model();

		multi_PTY_FLT_MST_Model.setBranch_code(Config.BRANCH_CODE);
		multi_PTY_FLT_MST_Model.setCmpny_code(Config.COMPANY_CODE);
		SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT);
		String currentDateandTime = sdf.format(new Date());
		multi_PTY_FLT_MST_Model.setPty_sys_date(currentDateandTime);
		multi_PTY_FLT_MST_Model.setPty_flt_run_date(BaseActivity
				.getServerRunDate());

		multi_PTY_FLT_MST_Model.setPty_sr_no(UserSingleton.getInstance(
				getContext()).getReceiptCount());
		multi_PTY_FLT_MST_Model.setTill_no(String.valueOf(Config.TAB_NO));
		multi_PTY_FLT_MST_Model.setTransca_no(UserSingleton.getInstance(
				getContext()).getTransactionNo());
		multi_PTY_FLT_MST_Model.setUser_name(UserSingleton
				.getInstance(getContext()).mUserName);
		multi_PTY_FLT_MST_Model.setUser_name_scnd("N");

		switch (option) {
		case FLOAT:
			multi_PTY_FLT_MST_Model.setPty_flt_type(FLOAT_T);
			break;

		case PICKUP:
			multi_PTY_FLT_MST_Model.setPty_flt_type(PICKUP_T);
			break;

		case PETTY:
			multi_PTY_FLT_MST_Model.setPty_flt_type(PETTY_T);
			break;
		}

		multi_PTY_FLT_MST_Model.setCur_abbr(mSelectedCurrencyModel
				.getCurr_abbrName());
		multi_PTY_FLT_MST_Model.setEx_operator(mSelectedCurrencyModel
				.getCurr_operator());
		multi_PTY_FLT_MST_Model.setEx_rate(mSelectedCurrencyModel
				.getCurr_exrate());
		multi_PTY_FLT_MST_Model.setLoc_amt(Double.parseDouble(mAmountEt
				.getText().toString().trim()));

		double baseAmt = Double.parseDouble(df.format(Double
				.parseDouble(mAmountEt.getText().toString().trim())));
		double exRate = mSelectedCurrencyModel.getCurr_exrate();
		double amtInForeignCurr = 0.0;

		if (mSelectedCurrencyModel.getCurr_operator().equalsIgnoreCase("*")) {
			amtInForeignCurr = baseAmt * exRate;
		} else if (mSelectedCurrencyModel.getCurr_operator().equalsIgnoreCase(
				"/")) {
			amtInForeignCurr = baseAmt / exRate;
		}

		multi_PTY_FLT_MST_Model.setCurAmt(Double.parseDouble(df
				.format(amtInForeignCurr)));

		HomeHelper pfHelper = new HomeHelper(this);
		pfHelper.saveIn_MULTI_PTY_FLT_MSTDB(multi_PTY_FLT_MST_Model);

	}

	/**
	 * Method- To load currcnymul data
	 */
	private void loadCurrcySpinner() {
		mCurrencyAdapter = new CurencySpinnerAdapter(getContext(),
				android.R.layout.simple_spinner_item, mCurrncyMstDataList);
		mCurrSpinner.setAdapter(mCurrencyAdapter);
		mCurrSpinner.setOnItemSelectedListener(mItemSelectionListener);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void executePostAction(OperationalResult opResult) {

		switch (opResult.getResultCode()) {

		case Constants.FETCH_CURRENCIES:
			mCurrncyMstDataList = (ArrayList<CURRENCY_MST_MODEL>) opResult
					.getResult().getSerializable(Constants.RESULTCODEBEAN);
			numberOfRequests--;
			if (numberOfRequests == 0) {
				if (mCurrncyMstDataList != null
						&& mCurrncyMstDataList.size() > 0) {
					loadCurrcySpinner();
				}
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
			numberOfRequests--;
			if (numberOfRequests == 0) {
				if (mCurrncyMstDataList != null
						&& mCurrncyMstDataList.size() > 0) {
					loadCurrcySpinner();
				}
			}
			break;

		case Constants.INSERT_FLOAT_PMT_RECORDS:
			Logger.d(TAG, "INSERT_FLOAT_PMT_RECORDS Done");
			numberOfRequests--;
			if (numberOfRequests == 0) {
				if (getDialog() != null && getDialog().isShowing())
					getDialog().dismiss();
			}
			break;

		case Constants.INSERT_PTY_FLT_MST_RECORD:
			Logger.d(TAG, "INSERT_PTY_FLT_MST_RECORD Done");
			numberOfRequests--;
			if (numberOfRequests == 0) {
				if (getDialog() != null && getDialog().isShowing())
					getDialog().dismiss();
			}
			break;

		case Constants.INSERT_MULTI_PTY_FLT_MST_RECORD:
			Logger.d(TAG, "INSERT_MULTI_PTY_FLT_MST_RECORD Done");
			numberOfRequests--;
			if (numberOfRequests == 0) {
				if (getDialog() != null && getDialog().isShowing())
					getDialog().dismiss();
			}
			break;

		case Constants.INSERT_TEMP_TXN_RECORD:
			Logger.d(TAG, "INSERT_TEMP_TXN_RECORD Done");
			if (getDialog() != null && getDialog().isShowing())
				getDialog().dismiss();
			break;
		}
	}

	@Override
	public void errorReceived(OperationalResult opResult) {

	}

	@Override
	public Context getContext() {

		return getActivity();
	}

	public void showAlertMessage(String szAlert, String szMessage) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
		builder.setCancelable(false)
				.setPositiveButton(getResources().getString(R.string.ok),
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
