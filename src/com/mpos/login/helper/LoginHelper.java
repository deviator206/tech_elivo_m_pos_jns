/*
 * Copyright (C) 2013  All Rights Reserved.
 *  PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mpos.login.helper;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;

import android.content.Context;
import android.util.Log;

import com.mpos.framework.common.Constants;
import com.mpos.framework.common.DBConstants;
import com.mpos.framework.common.DBOperationlParameter;
import com.mpos.framework.common.Logger;
import com.mpos.framework.common.OperationalResult;
import com.mpos.framework.handler.ActivityHandler;
import com.mpos.framework.helper.ActivityHelper;
import com.mpos.framework.inf.BaseResponseListener;
import com.mpos.home.DBhandler.ConfigDBHandler;
import com.mpos.home.NThandler.AppConfigNTHandler;
import com.mpos.home.model.Company_PolicyModel;
import com.mpos.login.NThandler.UserMasterNTHandler;
import com.mpos.login.model.USER_MST_Model;
import com.mpos.master.DBhandler.GRP_MST_DBHandler;
import com.mpos.master.DBhandler.PRDCT_MST_DBHandler;
import com.mpos.master.DBhandler.USER_MST_DBHandler;
import com.mpos.master.DBhandler.USR_ASSGND_RGHTS_DBHandler;

/**
 * Description-
 * 

 */
public class LoginHelper extends ActivityHelper {

	public static final String TAG = Constants.APP_TAG
			+ LoginHelper.class.getSimpleName();

	private Context mContext;
	private BaseResponseListener mBaseResponseListener = null;

	public LoginHelper(BaseResponseListener responseListener) {
		super(responseListener);

		this.mBaseResponseListener = responseListener;
	}

	public LoginHelper(Context context, BaseResponseListener responseListener) {
		super(responseListener);
		mContext = context;
		this.mBaseResponseListener = responseListener;
	}

	@Override
	protected void responseHandle(OperationalResult result) {
		Logger.v(TAG, "responseHandle " + result.getResultCode());

		switch (result.getRequestResponseCode()) {

		default:
			this.mBaseResponseListener.errorReceived(result);
			break;
		}

		switch (result.getResultCode()) { // DB scenario handling

		case Constants.INSERT:
			// Insert data in DB handling
			handleDataInsertinDB(result);
			break;

		case Constants.UPDATE:
			// Update data in DB handling
			break;

		case Constants.SELECT:
			// Delete data in DB handling
			handleDataSelectinDB(result);
			break;
		case Constants.DELETE:
			// Get all data in DB handling
			break;

		case Constants.CLEAR_CACHE:
			// Handle DB table clear cache
			break;

		case Constants.GET_USER_MST:
			Logger.v(TAG, "responseHandle GET_USER_MST");
			saveAllUserDataInDB(result);
			break;

		case Constants.GET_APP_CONFIG:
			Logger.v(TAG, "GET_APP_CONFIG");
			this.mBaseResponseListener.executePostAction(result);
			break;

		default: // Pass UI updation response back to activity
			this.mBaseResponseListener.executePostAction(result);
			break;
		}
	}

	/**
	 * Method- Handles the data insertion in DB.
	 * 
	 * @param result
	 */
	private void handleDataInsertinDB(OperationalResult result) {
		mIsContinueProgressbar = false;
		if (result.getRequestResponseCode() == OperationalResult.DB_ERROR) {
			Logger.d(TAG, "Record not added in table.");
			showAlertMessage(Constants.ALERT, "Record not added in table.",
					false);
		} else {
			Logger.d(TAG, "Record added in table.");
			this.mBaseResponseListener.executePostAction(result);
		}
	}

	/**
	 * Method- Handles the data selection in DB.
	 * 
	 * @param result
	 */
	private void handleDataSelectinDB(OperationalResult result) {
		mIsContinueProgressbar = false;
		if (result.getRequestResponseCode() == OperationalResult.DB_ERROR) {
			Logger.d(TAG, "Record fetch failed from table.");
			showAlertMessage(Constants.ALERT,
					"Record fetch failed from table.", false);
		} else {
			Logger.d(TAG, "Record fetched from table.");
			this.mBaseResponseListener.executePostAction(result);
		}
	}

	public void getAllUserAuthenticationData(String url,
			List<NameValuePair> params, boolean showProgress) {
		Logger.d(TAG, "getuserAuthenticationData");
		mIsContinueProgressbar = showProgress;
		// Respective N/T Handler
		ActivityHandler activityHandler = new UserMasterNTHandler(this, params,
				url, Constants.GET_USER_MST);
		// Start AsyncTask
		getResult(activityHandler);

	}

	@SuppressWarnings("unchecked")
	private void saveAllUserDataInDB(OperationalResult opResult) {
		ArrayList<USER_MST_Model> mUser_MST_Models = (ArrayList<USER_MST_Model>) opResult
				.getResult().getSerializable(Constants.RESULTCODEBEAN);

		Logger.d(TAG, "insertUSER_MSTModel");
		mIsContinueProgressbar = true;

		ActivityHandler activityHandler = new USER_MST_DBHandler(this,
				mUser_MST_Models, DBConstants.dbo_USER_MST_TABLE,
				DBOperationlParameter.INSERT,
				Constants.INSERT_USER_MST_RECORDS, false);
		// Start AsyncTask
		getResult(activityHandler);

	}

	public void fetchUSERDetailsFromUSERMSTTable(String userName) {
		mIsContinueProgressbar = true;
		String[] queryParams = { userName };
		USER_MST_DBHandler userMSTDBHandler = new USER_MST_DBHandler(this,
				queryParams, DBConstants.dbo_USER_MST_TABLE,
				DBOperationlParameter.SELECT, Constants.FETCH_USER_DETAILS_MST,
				false);
		getResult(userMSTDBHandler);
	}

	public void fetchUserAssignedRights(USER_MST_Model authenticatedUserModel) {
		Log.v(TAG, ":fetchUserAssignedRights");
		mIsContinueProgressbar = true;
		String[] queryParams = { authenticatedUserModel.getUSR_GRP_ID() };
		USR_ASSGND_RGHTS_DBHandler userMSTDBHandler = new USR_ASSGND_RGHTS_DBHandler(
				this, queryParams, DBConstants.dbo_USR_ASSGND_RGHTS_TABLE,
				DBOperationlParameter.SELECT,
				Constants.FETCH_USER_ASSIGND_RGHTS_MST, false);
		getResult(userMSTDBHandler);
	}

	/**
	 * Method- To get app config data from server
	 * 
	 * @param url
	 * @param params
	 * @param showProgress
	 */
	public void getAppConfig(String url, List<NameValuePair> params,
			boolean showProgress) {
		Logger.d(TAG, "getAppConfig:" + url);
		mIsContinueProgressbar = showProgress;
		// Respective N/T Handler
		ActivityHandler activityHandler = new AppConfigNTHandler(this, params,
				url, Constants.GET_APP_CONFIG);
		// Start AsyncTask
		getResult(activityHandler);
	}

	/**
	 * Method- To insert Config policy Records
	 * 
	 * @param cmpPlyModels
	 * @param tableName
	 * @param showProgress
	 */
	public void insertCmpPlyRecords(
			ArrayList<Company_PolicyModel> cmpPlyModels, String tableName,
			boolean showProgress) {
		Logger.d(TAG, "insertCmpPlyRecords");

		mIsContinueProgressbar = showProgress;
		ActivityHandler activityHandler = new ConfigDBHandler(this,
				cmpPlyModels, tableName, DBOperationlParameter.INSERT,
				Constants.INSERT_CONFIG_CMP_PLY_RECORDS, false);
		// Start AsyncTask
		getResult(activityHandler);
	}

	/**
	 * Method- To select particular Policy Records
	 * 
	 * @param whrArgs
	 * @param tableName
	 * @param showProgress
	 */
	public void fetchCmpPolicyRecord(String queryParam, String tableName,
			boolean showProgress) {
		Logger.d(TAG, "fetchCmpPolicyRecord");
		mIsContinueProgressbar = showProgress;

		String[] whrArgs = { queryParam };

		ActivityHandler activityHandler = new ConfigDBHandler(this, whrArgs,
				tableName, DBOperationlParameter.DEFAULT_VEHICLE,
				Constants.FETCH_SINGLE_CMP_PLCY_RECORD, false);

		// Start AsyncTask
		getResult(activityHandler);
	}

}
