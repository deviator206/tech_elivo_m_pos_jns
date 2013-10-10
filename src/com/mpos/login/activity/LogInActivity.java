/*
 * Copyright (C) 2013  All Rights Reserved.
 *  PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mpos.login.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.NameValuePair;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.mpos.R;
import com.mpos.application.BaseActivity;
import com.mpos.application.MPOSApplication;
import com.mpos.framework.Util.UserSingleton;
import com.mpos.framework.common.Config;
import com.mpos.framework.common.Constants;
import com.mpos.framework.common.DBConstants;
import com.mpos.framework.common.DatabaseHelper;
import com.mpos.framework.common.Logger;
import com.mpos.framework.common.OperationalResult;
import com.mpos.framework.handler.DBHandler;
import com.mpos.framework.inf.BaseResponseListener;
import com.mpos.home.activity.HomeActivity;
import com.mpos.home.model.App_Config_Model;
import com.mpos.home.model.Company_PolicyModel;
import com.mpos.login.helper.LoginHelper;
import com.mpos.login.model.USER_MST_Model;

/**
 * Description-
 * 
 */
public class LogInActivity extends BaseActivity implements BaseResponseListener {

	public static final String TAG = Constants.APP_TAG
			+ LogInActivity.class.getSimpleName();

	// Android Variable
	private EditText userNameET;
	private EditText passwordET;
	private EditText serverNameET;
	private Button submitBtn;

	// Java Variable

	private BaseResponseListener mResponseListener = null;
	private ArrayList<USER_MST_Model> mUsetMSTArray = new ArrayList<USER_MST_Model>();

	public static final String CMPNY_CODE = "cmpnyCode";
	public static final String TILL_NO = "tillNo";
	public static final String MCHNE_NAME = "mchneName";
	public static final String MAX_ZED_NO = "maxZedNo";
	public static final String RUN_DTE = "runDate";
	public static final String CMPNY_NAME = "cmpny_code";
	public static final String CMPNY_BRANCH_CODE = "cmpny_branch_code";
	public static final String NEAREST_ROUND_VAL = "NEAREST_ROUNDING_VALUE";
	public static final String DECIMAL_RND_VAL = "DECIMAL_RND_VAL";
	public static final String PRINTER_SERVICE_URL = "PRINTER_SERVICE_URL";
	public static final String PRINTER_METHOD_NAME = "PRINTER_METHOD_NAME";
	public static final String PRINTER_SOAP_REQUEST_URL = "PRINTER_SOAP_REQUEST_URL";

	public static final String WS_PORT = "WS_PORT";
	public static final String WS_PORTTYPE = "WS_PORTTYPE";
	public static final String WS_PRINTERTYPE = "WS_PRINTERTYPE";
	public static final String WS_PRINTERMODEL = "WS_PRINTERMODEL";

	public static final String CMPNY_ADDRESS = "CMPNY_ADDRESS";
	public static final String CMPNY_TELE = "CMPNY_TELE";
	public static final String FLT_PTY_PKUP_CNT = "FPP_CNT";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		UserSingleton.getInstance(this).isNetworkAvailable = isNetworkConnectionAvailable();
		Logger.d(TAG, "onCreate");
		UserSingleton.getInstance(this).isNetworkAvailable = isNetworkConnectionAvailable();
		loadAllConfigData();
		componentInit();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	public boolean isNetworkConnectionAvailable() {
		ConnectivityManager connectivity = (ConnectivityManager) this
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity != null) {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null)
				for (int i = 0; i < info.length; i++)
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}

		}
		return false;
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		submitBtn.setClickable(true);
	}

	private void getAllUserAuthenticationData() {
		Logger.d(TAG, "getAllUserAuthenticationData");
		List<NameValuePair> params = new LinkedList<NameValuePair>();
		LoginHelper helper = new LoginHelper(this, this);
		helper.getAllUserAuthenticationData(Config.GET_USER_MST, params, true);
	}

	private void componentInit() {
		userNameET = (EditText) findViewById(R.id.userNameET);
		passwordET = (EditText) findViewById(R.id.passwordET);
		submitBtn = (Button) findViewById(R.id.enterButton);
		submitBtn.setOnClickListener(mClickListener);
		serverNameET = (EditText) findViewById(R.id.serverNameET);
		// serverNameET.setText("windows.technologicle.com");
		serverNameET.setText(UserSingleton.getInstance(this).getServerName());
		findViewById(R.id.dummy_id).requestFocus();
	}

	private OnClickListener mClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {

			case R.id.enterButton:
				// logInUserIntoSystem();
				startProgress();
				InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

				inputManager.hideSoftInputFromWindow(getCurrentFocus()
						.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

				String dns_name = serverNameET.getText().toString(); // "windows.technologicle.com/core/test";
				Logger.v(TAG, "dnsname..." + dns_name);
				if (!dns_name.equalsIgnoreCase("")) {
					Logger.v(
							TAG,
							"dnsname..."
									+ (dns_name.charAt(dns_name.length() - 1)));

					if ((dns_name.charAt(dns_name.length() - 1)) != '/') {
						dns_name = dns_name + "/";
						Logger.v(TAG, "dnsname..." + dns_name);
					}
				}
				Config.DNS_NAME = dns_name;

				Logger.v(TAG, "dnsname"
						+ Config.DNS_NAME
						+ " "
						+ UserSingleton.getInstance(getContext())
								.getServerName());

				if (!Config.DNS_NAME.equalsIgnoreCase("")) {
					if (!Config.DNS_NAME.equalsIgnoreCase(UserSingleton
							.getInstance(getContext()).getServerName())) {
						deleteAllData();
						UserSingleton.getInstance(getContext())
								.storeInSharedPref(false);
						getUserData();
					} else {
						authenticateAndCheckRightsForUser();
					}
				} else {
					showAlertMessage("", "Please enter valid server name");
				}
				UserSingleton.getInstance(getContext()).setServerNameInPref(
						Config.DNS_NAME);
				break;

			default:
				break;
			}
		}
	};

	/**
	 * Method- To validate and login
	 */
	private void logInUserIntoSystem() {
		UserSingleton.getInstance(this).mUserName = userNameET.getText()
				.toString();
		passwordET.setText("");
		userNameET.setText("");
		Intent launchHomeIntent = new Intent(LogInActivity.this,
				HomeActivity.class);
		startActivity(launchHomeIntent);
	}

	protected void getUserData() {
		if (!UserSingleton.getInstance(this).isNetworkAvailable) {
			UserSingleton.getInstance(this).isNetworkAvailable = false;
			showAlertMessage("", "You don't have internet connection.");
			return;
		}
		startProgress();

		// get app config data
		SharedPreferences prefs = this.getSharedPreferences("com.mpos",
				Context.MODE_PRIVATE);
		String macAddrss = prefs
				.getString(MPOSApplication.WIFI_MAC_ADDRESS, "");
		LoginHelper helper = new LoginHelper(this, this);
		helper.getAppConfig(Config.APP_CONFIG + macAddrss, null, true);
		// helper.getAppConfig(Config.APP_CONFIG + "BC4760FEA232", null, true);

		// getAllUserAuthenticationData();
	}

	protected void deleteAllData() {

		DatabaseHelper mDataBaseHelper = DatabaseHelper.getDBInstance();/*
																		 * new
																		 * DatabaseHelper
																		 * (
																		 * MPOSApplication
																		 * .
																		 * getContext
																		 * ());
																		 */
		if (DBHandler.mSQLiteDB == null) {
			DBHandler.mSQLiteDB = mDataBaseHelper.getWritableDatabase();
		} else if (DBHandler.mSQLiteDB != null && !DBHandler.mSQLiteDB.isOpen()) {
			DBHandler.mSQLiteDB = mDataBaseHelper.getWritableDatabase();
		}
		DBHandler.mSQLiteDB.delete(DBConstants.dbo_TILL_PRDCT_MST_TABLE, null,
				null);
		DBHandler.mSQLiteDB.delete(DBConstants.dbo_PRDCT_GRP_MST_TABLE, null,
				null);
		DBHandler.mSQLiteDB.delete(DBConstants.dbo_DPT_MST_TABLE, null, null);
		DBHandler.mSQLiteDB
				.delete(DBConstants.dbo_ANALYS_MST_TABLE, null, null);
		DBHandler.mSQLiteDB.delete(DBConstants.dbo_BIN_LOC_MST_TABLE, null,
				null);
		DBHandler.mSQLiteDB.delete(DBConstants.dbo_USER_MST_TABLE, null, null);
		DBHandler.mSQLiteDB.delete(DBConstants.dbo_USR_ASSGND_RGHTS_TABLE,
				null, null);
		DBHandler.mSQLiteDB.delete(DBConstants.dbo_Currency_Mst_TABLE, null,
				null);
		DBHandler.mSQLiteDB.delete(DBConstants.dbo_UOM_SLAB_MST_TABLE, null,
				null);
		DBHandler.mSQLiteDB.delete(DBConstants.dbo_UOM_TABLE, null, null);
		DBHandler.mSQLiteDB.delete(DBConstants.dbo_VAT_MST_TABLE, null, null);
		DBHandler.mSQLiteDB.delete(DBConstants.dbo_SCAN_CODS_MST_TABLE, null,
				null);
		DBHandler.mSQLiteDB.delete(DBConstants.dbo_PRDCT_RECEIPE_MST_TABLE,
				null, null);
		DBHandler.mSQLiteDB.delete(DBConstants.dbo_FLX_POS_MST_TABLE, null,
				null);
		DBHandler.mSQLiteDB.delete(DBConstants.dbo_SLMN_MST_TABLE, null, null);
		DBHandler.mSQLiteDB.delete(DBConstants.dbo_DNMTN_MST_TABLE, null, null);

		DBHandler.mSQLiteDB.delete(DBConstants.dbo_BILL_INSTRCTN_TXN_TABLE,
				null, null);
		DBHandler.mSQLiteDB.delete(DBConstants.dbo_INSTRCTN_MST_TABLE, null,
				null);
		DBHandler.mSQLiteDB
				.delete(DBConstants.dbo_CONFIG_MST_TABLE, null, null);
		DBHandler.mSQLiteDB.delete(DBConstants.dbo_PTY_FLT_MST_TABLE, null,
				null);
		DBHandler.mSQLiteDB.delete(DBConstants.dbo_BILL_TXN_TABLE, null, null);
		DBHandler.mSQLiteDB.delete(DBConstants.dbo_SLMN_TXN_TABLE, null, null);
		DBHandler.mSQLiteDB.delete(DBConstants.dbo_FLX_POS_TXN_TABLE, null,
				null);
		DBHandler.mSQLiteDB.delete(DBConstants.dbo_MULI_PTY_FLT_MST_TABLE,
				null, null);
		DBHandler.mSQLiteDB.delete(DBConstants.dbo_MULTI_CHNG_MST_TABLE, null,
				null);
		DBHandler.mSQLiteDB.delete(DBConstants.dbo_MULTI_PYMNTS_MST_TABLE,
				null, null);
		DBHandler.mSQLiteDB
				.delete(DBConstants.dbo_PYMNTS_MST_TABLE, null, null);
		DBHandler.mSQLiteDB.delete(DBConstants.dbo_HELD_TXN, null, null);
		DBHandler.mSQLiteDB.delete(DBConstants.dbo_CNSMPTN_TXN, null, null);
		DBHandler.mSQLiteDB.delete(DBConstants.dbo_TEMP_TRANSACTION_TXN_TABLE,
				null, null);
	}

	protected void authenticateAndCheckRightsForUser() {
		startProgress();
		LoginHelper helper = new LoginHelper(this);
		// helper.getUSERDetailsFromUSERMSTTable("ADMIN");
		helper.fetchUSERDetailsFromUSERMSTTable(userNameET.getText().toString()
				.toUpperCase());

		// helper.fetchUSERDetailsFromUSERMSTTable("ADMIN");
	}

	/**
	 * Method- Network/DB Response Handling
	 */
	@Override
	public void executePostAction(OperationalResult opResult) {

		switch (opResult.getResultCode()) {

		case Constants.FETCH_USER_DETAILS_MST:
			// userNameET.setText("ADMIN");
			// passwordET.setText("1234");
			checkUSERPresentInMSTTable(opResult);
			break;

		case Constants.INSERT_USER_MST_RECORDS:
			// stopProgress();
			UserSingleton.getInstance(LogInActivity.this)
					.storeCheckForUserData(true);
			authenticateAndCheckRightsForUser();
			break;

		case Constants.GET_APP_CONFIG:
			App_Config_Model config_Model = (App_Config_Model) opResult
					.getResult().getSerializable(Constants.RESULTCODEBEAN);

			System.out.println(" ##  config_Model " + config_Model.toString());

			if (config_Model != null) {
				storeInSharedPref(config_Model);
				Logger.d(TAG, "Model:" + config_Model.toString());

				// Set Config Constants
				Config.BRANCH_CODE = config_Model.getCompany_Dtls_Models()
						.getCmpny_branch_no();
				Config.COMPANY_CODE = config_Model.getTill_Model()
						.getCmpny_code();
				Config.COMPANY_NAME = config_Model.getCompany_Dtls_Models()
						.getCmpny_name();
				Config.TAB_NO = config_Model.getTill_Model().getTill_no();
				Config.COMPANY_ADDRESS = config_Model.getCompany_Dtls_Models()
						.getCmpny_Address_1();

				

				Config.WS_PORT =  config_Model.getTill_Model().getPort();
				Config.WS_PORTTYPE= config_Model.getTill_Model().getPort_type();
				Config.WS_PRINTERTYPE = config_Model.getTill_Model().getPrinter_type();
				Config.WS_PRINTERMODEL= config_Model.getTill_Model().getPrinter_model();
				
				
				Company_PolicyModel model = new Company_PolicyModel();

				model.setCmpny_ply("NEAREST ROUNDING FO");
				if (config_Model.getCmpnyPlcyModels() != null
						&& config_Model.getCmpnyPlcyModels().contains(model)) {
					int index = config_Model.getCmpnyPlcyModels()
							.indexOf(model);
					Config.NEAREST_ROUNDING_FO = Integer.parseInt(config_Model
							.getCmpnyPlcyModels().get(index)
							.getCmpny_ply_value());
					Logger.d(TAG, "Config:Value:" + Config.NEAREST_ROUNDING_FO);
				}

				model.setCmpny_ply("DECIMAL ROUNDING FO");
				if (config_Model.getCmpnyPlcyModels() != null
						&& config_Model.getCmpnyPlcyModels().contains(model)) {
					int index = config_Model.getCmpnyPlcyModels()
							.indexOf(model);
					Config.DECIMAL_ROUNDING_FO = Integer.parseInt(config_Model
							.getCmpnyPlcyModels().get(index)
							.getCmpny_ply_value());
				}

				System.out.println("## Config.PRINT_URL::  " + Config.PRINT_URL);
				System.out.println("## ACTUAL CONTENT "+ config_Model.getCmpnyPlcyModels());
				
				model.setCmpny_ply("PRINTER SERVICE URL");
				if (config_Model.getCmpnyPlcyModels() != null
						&& config_Model.getCmpnyPlcyModels().contains(model)) {
					int index = config_Model.getCmpnyPlcyModels()
							.indexOf(model);
					createPrinterURL(config_Model.getCmpnyPlcyModels()
							.get(index).getCmpny_ply_value());

				}

				UserSingleton.getInstance(this).mMaxZedNo = config_Model
						.getTill_Model().getMax_zed_no();

				// Save company policy data in DB
				if (config_Model.getCmpnyPlcyModels() != null
						&& config_Model.getCmpnyPlcyModels().size() > 0) {
					LoginHelper helper = new LoginHelper(this);
					helper.insertCmpPlyRecords(
							config_Model.getCmpnyPlcyModels(),
							DBConstants.dbo_CONFIG_MST_TABLE, true);
				}

			}
			getAllUserAuthenticationData();
			break;

		case Constants.INSERT_CONFIG_CMP_PLY_RECORDS:
			Logger.d(TAG, "INSERT_CONFIG_CMP_PLY_RECORDS Done");
			break;
		}
	}

	public static void createPrinterURL(String string) {
		// TODO Auto-generated method stub
		System.out.println("## setting Config.PRINT_URL::  " + Config.PRINT_URL);
		String sTemp = string;
		String[] sT1 = sTemp.split(".svc/");
		
		
		System.out.println(" S1 " + sT1[0] + " S2 " + sT1[1]);
		Config.PRINT_URL = sT1[0] + ".svc"; 
		Config.PRINTER_METHOD = sT1[1];
		String[] sInterfaceName = sT1[0].split("/");
		Config.SOAP_ACTION = "I"+sInterfaceName[sInterfaceName.length-1];
		System.out.println("## Config.PRINT_URL::  " + Config.PRINT_URL
				+ " ##  METHOD" + Config.PRINTER_METHOD
				+ " Config.SOAP_ACTION  " + Config.SOAP_ACTION);
	}
	
	public static ArrayList<String> getPrinterURL(String string) {
		// TODO Auto-generated method stub
		System.out.println("## setting Config.PRINT_URL::  " + Config.PRINT_URL);
		String sTemp = string;
		String[] sT1 = sTemp.split(".svc/");
		
		
		System.out.println(" S1 " + sT1[0] + " S2 " + sT1[1]);
		Config.PRINT_URL = sT1[0] + ".svc"; 
		Config.PRINTER_METHOD = sT1[1];
		String[] sInterfaceName = sT1[0].split("/");
		Config.SOAP_ACTION = "I"+sInterfaceName[sInterfaceName.length-1];
		
		 ArrayList<String> sReturnArray = new  ArrayList<String>();
		sReturnArray.add(Config.PRINT_URL);
		sReturnArray.add(Config.PRINTER_METHOD);
		sReturnArray.add(Config.SOAP_ACTION);
		return sReturnArray;
	}
	

	/**
	 * Method- To store in shared pref, Master data synchronized
	 * 
	 * @param config_Model
	 */
	private void storeInSharedPref(App_Config_Model config_Model) {
		SharedPreferences prefs = this.getSharedPreferences("com.mpos",
				Context.MODE_PRIVATE);
		Editor prefEditor = prefs.edit();

		prefEditor.putString(CMPNY_CODE, config_Model.getTill_Model()
				.getCmpny_code());
		prefEditor.putString(MAX_ZED_NO, config_Model.getTill_Model()
				.getMax_zed_no());
		prefEditor.putString(RUN_DTE, config_Model.getTill_Model()
				.getRun_date());
		prefEditor
				.putString(TILL_NO, config_Model.getTill_Model().getTill_no());
		prefEditor.putString(MCHNE_NAME, config_Model.getTill_Model()
				.getMchne_name());

		prefEditor.putString(CMPNY_BRANCH_CODE, config_Model
				.getCompany_Dtls_Models().getCmpny_branch_no());
		prefEditor.putString(CMPNY_NAME, config_Model.getCompany_Dtls_Models()
				.getCmpny_name());

		prefEditor.putString(CMPNY_ADDRESS, config_Model
				.getCompany_Dtls_Models().getCmpny_Address_1());

		prefEditor.putString(CMPNY_TELE, config_Model.getCompany_Dtls_Models()
				.getCmpny_tele());
		
		prefEditor.putString(WS_PORT, config_Model.getTill_Model().getPort());
		prefEditor.putString(WS_PORTTYPE, config_Model.getTill_Model().getPort_type());
		prefEditor.putString(WS_PRINTERTYPE, config_Model.getTill_Model().getPrinter_type());
		prefEditor.putString(WS_PRINTERMODEL, config_Model.getTill_Model().getPrinter_model());
		

		Company_PolicyModel model = new Company_PolicyModel();

		model.setCmpny_ply("NEAREST ROUNDING FO");
		if (config_Model.getCmpnyPlcyModels() != null
				&& config_Model.getCmpnyPlcyModels().contains(model)) {
			int index = config_Model.getCmpnyPlcyModels().indexOf(model);
			prefEditor.putInt(
					NEAREST_ROUND_VAL,
					Integer.parseInt(config_Model.getCmpnyPlcyModels()
							.get(index).getCmpny_ply_value()));
			Logger.v(
					TAG,
					"NEAREST ROUNDING FO "
							+ Integer.parseInt(config_Model
									.getCmpnyPlcyModels().get(index)
									.getCmpny_ply_value()));
		}

		model.setCmpny_ply("DECIMAL ROUNDING FO");
		if (config_Model.getCmpnyPlcyModels() != null
				&& config_Model.getCmpnyPlcyModels().contains(model)) {
			int index = config_Model.getCmpnyPlcyModels().indexOf(model);
			prefEditor.putInt(
					DECIMAL_RND_VAL,
					Integer.parseInt(config_Model.getCmpnyPlcyModels()
							.get(index).getCmpny_ply_value()));
			Logger.v(
					TAG,
					"DECIMAL ROUNDING FO "
							+ Integer.parseInt(config_Model
									.getCmpnyPlcyModels().get(index)
									.getCmpny_ply_value()));
		}
		
		model.setCmpny_ply("PRINTER SERVICE URL");
		if (config_Model.getCmpnyPlcyModels() != null && config_Model.getCmpnyPlcyModels().contains(model)) {
			int index = config_Model.getCmpnyPlcyModels().indexOf(model);
			System.out.println("## "+config_Model.getCmpnyPlcyModels().get(index).getCmpny_ply_value());
			ArrayList<String> sArray = getPrinterURL(config_Model.getCmpnyPlcyModels().get(index).getCmpny_ply_value());
			prefEditor.putString(PRINTER_SERVICE_URL,sArray.get(0));
			prefEditor.putString(PRINTER_METHOD_NAME,sArray.get(1));
			prefEditor.putString(PRINTER_SOAP_REQUEST_URL,sArray.get(2));
			
		}

		
		
		
		prefEditor.commit();
	}

	/**
	 * Method- To Handle Error
	 */
	@Override
	public void errorReceived(OperationalResult opResult) {
		stopProgress();
		switch (opResult.getResultCode()) {
		case OperationalResult.DB_ERROR:
			showAlertMessage(Constants.ALERT, "DB Issue. Please try Again.",
					false);
			break;
		}
	}

	private void checkUSERPresentInMSTTable(OperationalResult opResult) {
		ArrayList<USER_MST_Model> mUser_MST_Models = (ArrayList<USER_MST_Model>) opResult
				.getResult().getSerializable(Constants.RESULTCODEBEAN);
		boolean isUSERAuthenticated = false;
		USER_MST_Model authenticatedUserModel = null;
		if (mUser_MST_Models != null) {

			for (USER_MST_Model user_MST_Model : mUser_MST_Models) {
				if (user_MST_Model.getUSR_NM().equalsIgnoreCase(
						userNameET.getText().toString())
						&& user_MST_Model.getUSR_PWD().equalsIgnoreCase(
								passwordET.getText().toString())) {
					isUSERAuthenticated = true;
					authenticatedUserModel = user_MST_Model;
					break;
				}
			}
		}
		if (isUSERAuthenticated) {
			// logInUserIntoSystem();
			submitBtn.setClickable(false);
			UserSingleton.getInstance(this).mUserDetail = authenticatedUserModel;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			String currentDateandTime = sdf.format(new Date());
			authenticatedUserModel.setUSR_CRTN_DATE(currentDateandTime);
			UserSingleton.getInstance(this).setUserLoginTimeInPref(
					currentDateandTime);
			logInUserIntoSystem();
		} else {
			UserSingleton.getInstance(this).mUserDetail = null;
			showAlertMessage(null,
					getResources()
							.getString(R.string.unauthenticated_user_text));
		}
		stopProgress();
	}

	public void showAlertMessage(String szAlert, String szMessage) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
		builder.setCancelable(false)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				}).setMessage(szMessage).setTitle(szAlert);
		AlertDialog alert = builder.create();
		alert.setCancelable(false);
		alert.show();

	}

}
