/*
 * Copyright (C) 2013  All Rights Reserved.
 *  PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mpos.application;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.mpos.R;
import com.mpos.framework.Util.UserSingleton;
import com.mpos.framework.common.Config;
import com.mpos.framework.common.Constants;
import com.mpos.framework.common.Logger;
import com.mpos.login.activity.LogInActivity;

/**
 * Description-
 * 
 */
public class BaseActivity extends FragmentActivity {

	private static final String TAG = Constants.APP_TAG
			+ BaseActivity.class.getSimpleName();

	private static ProgressDialog mProgressDialog = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MPOSApplication.baseActivity = this;
	}

	public Context getContext() {
		return this;
	}

	/**
	 * Gets reference to global Application
	 * 
	 * @return must always be type of ControlApplication! See
	 *         AndroidManifest.xml
	 */
	public MPOSApplication getApp() {
		return (MPOSApplication) this.getApplication();
	}

	@Override
	public void onUserInteraction() {
		super.onUserInteraction();
		getApp().touch();
	}

	public void sessionTimeOut() {
		if (!MPOSApplication.isProcessingForMasterDb) {
			if (!this.getClass().getName()
					.equalsIgnoreCase(LogInActivity.class.getName())) {
				Intent in = new Intent(this, LogInActivity.class);
				UserSingleton.getInstance(this).mUserDetail = null;
				in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(in);
				this.finish();
			}
		}
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

	public void showAlertMessage(String szAlert, String szMessage,
			final boolean isFinishRequired) {
		if (!this.isFinishing()) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setCancelable(false)
					.setNegativeButton(
							getResources().getString(R.string.cancel),
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									dialog.dismiss();
								}
							})
					.setPositiveButton(getResources().getString(R.string.ok),
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									dialog.dismiss();
									if (isFinishRequired) {
										BaseActivity.this.finish();
									}
								}
							}).setMessage(szMessage).setTitle(szAlert);
			AlertDialog alert = builder.create();
			alert.setCancelable(false);
			alert.show();

		}
	}

	/**
	 * Method- To get RunDate
	 * 
	 * @return
	 */
	public static String getServerRunDate() {
		SharedPreferences prefs = MPOSApplication.getContext()
				.getSharedPreferences("com.mpos", Context.MODE_PRIVATE);
		return Constants.getFormattedDate(prefs.getString(
				LogInActivity.RUN_DTE, ""));
	}

	/**
	 * Method- To load all config data
	 */
	public static void loadAllConfigData() {
		SharedPreferences prefs = MPOSApplication.getContext()
				.getSharedPreferences("com.mpos", Context.MODE_PRIVATE);
		Config.BRANCH_CODE = prefs.getString(LogInActivity.CMPNY_BRANCH_CODE,
				"");
		Config.COMPANY_CODE = prefs.getString(LogInActivity.CMPNY_CODE, "");
		Config.COMPANY_NAME = prefs.getString(LogInActivity.CMPNY_NAME, "");
		Config.DECIMAL_ROUNDING_FO = prefs.getInt(
				LogInActivity.DECIMAL_RND_VAL, 0);
		Config.NEAREST_ROUNDING_FO = prefs.getInt(
				LogInActivity.NEAREST_ROUND_VAL, 0);
		Config.TAB_NO = prefs.getString(LogInActivity.TILL_NO, "");
		Config.MAX_Z_NO = prefs.getString(LogInActivity.MAX_ZED_NO, "");
		Config.RUN_DTE = prefs.getString(LogInActivity.RUN_DTE, "");
		Config.COMPANY_ADDRESS = prefs.getString(LogInActivity.CMPNY_ADDRESS,
				"");
		Config.COMPANY_TELE = prefs.getString(LogInActivity.CMPNY_TELE, "");
		
		Config.SOAP_ACTION = prefs.getString(LogInActivity.PRINTER_SOAP_REQUEST_URL, "");
		Config.PRINTER_METHOD = prefs.getString(LogInActivity.PRINTER_METHOD_NAME, "");
		Config.PRINT_URL = prefs.getString(LogInActivity.PRINTER_SERVICE_URL, "");
		
		Config.WS_PORT =   prefs.getString(LogInActivity.WS_PORT, "");
		Config.WS_PORTTYPE=  prefs.getString(LogInActivity.WS_PORTTYPE, "");
		Config.WS_PRINTERTYPE =  prefs.getString(LogInActivity.WS_PRINTERTYPE, "");
		Config.WS_PRINTERMODEL=  prefs.getString(LogInActivity.WS_PRINTERMODEL, "");
		
		
		// Running FPP Count: first time default is 1
		UserSingleton.getInstance(MPOSApplication.getContext()).mFLT_PTY_PKUP_CNT = prefs
				.getInt(LogInActivity.FLT_PTY_PKUP_CNT, 1);
		Editor et = prefs.edit();
		et.putInt(
				LogInActivity.FLT_PTY_PKUP_CNT,
				UserSingleton.getInstance(MPOSApplication.getContext()).mFLT_PTY_PKUP_CNT);// put
																							// it
																							// back
																							// in
																							// pref
		et.commit();
	}

}
