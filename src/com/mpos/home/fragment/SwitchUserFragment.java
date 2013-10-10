/*
 * Copyright (C) 2013 USAP Pvt. Ltd. All Rights Reserved.
 * jaggarnaut-studios pvt. ltd. PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.mpos.home.fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.mpos.R;
import com.mpos.framework.Util.UserSingleton;
import com.mpos.framework.common.Constants;
import com.mpos.framework.common.DBConstants;
import com.mpos.framework.common.Logger;
import com.mpos.framework.common.OperationalResult;
import com.mpos.framework.inf.BaseResponseListener;
import com.mpos.home.activity.HomeActivity;
import com.mpos.login.helper.LoginHelper;
import com.mpos.login.model.USER_MST_Model;
import com.mpos.master.helper.MasterHelper;
import com.mpos.master.model.USR_ASSGND_RGHTSModel;
import com.mpos.master.model.UserRightsModel;

/**
 * Description-
 * 
 * @author jaggarnaut-studios
 */

public class SwitchUserFragment extends DialogFragment implements
		BaseResponseListener {

	public static final String TAG = Constants.APP_TAG
			+ SwitchUserFragment.class.getSimpleName();

	private Button mLoginBtn;
	private Button mCancelBtn;
	private EditText mUserNameEt;
	private EditText mPasswordEt;

	private OnClickListener mClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.loginBtn:
				validateInputs();
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

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater
				.inflate(R.layout.switch_user_dialog_fragment, null);

		getDialog().setTitle(getResources().getString(R.string.switch_user));

		mLoginBtn = (Button) view.findViewById(R.id.loginBtn);
		mLoginBtn.setOnClickListener(mClickListener);
		mCancelBtn = (Button) view.findViewById(R.id.cancelBtn);
		mCancelBtn.setOnClickListener(mClickListener);

		mUserNameEt = (EditText) view.findViewById(R.id.username_edit);
		mPasswordEt = (EditText) view.findViewById(R.id.password_edit);

		return view;
	}

	protected void validateInputs() {
		String username = mUserNameEt.getText().toString().trim();
		if (username != null & username.length() > 0) {
			InputMethodManager inputManager = (InputMethodManager) getContext()
					.getSystemService(Context.INPUT_METHOD_SERVICE);

			inputManager.hideSoftInputFromWindow(getActivity()
					.getCurrentFocus().getWindowToken(),
					InputMethodManager.HIDE_NOT_ALWAYS);
			authenticateAndCheckRightsForUser(username);
		} else {
			showAlertMessage(null,
					getResources().getString(R.string.alert_wrong_inputs));
		}
	}

	protected void authenticateAndCheckRightsForUser(String username) {
		LoginHelper helper = new LoginHelper(this);
		helper.fetchUSERDetailsFromUSERMSTTable(username);
	}

	@SuppressWarnings("unchecked")
	private void checkUSERPresentInMSTTable(OperationalResult opResult) {
		ArrayList<USER_MST_Model> mUser_MST_Models = (ArrayList<USER_MST_Model>) opResult
				.getResult().getSerializable(Constants.RESULTCODEBEAN);
		Logger.d(TAG, "user size" + mUser_MST_Models);
		boolean isUSERAuthenticated = false;
		USER_MST_Model authenticatedUserModel = null;
		if (mUser_MST_Models != null) {

			for (USER_MST_Model user_MST_Model : mUser_MST_Models) {
				if (user_MST_Model.getUSR_NM().equalsIgnoreCase(
						mUserNameEt.getText().toString().trim())) {
					isUSERAuthenticated = true;
					authenticatedUserModel = user_MST_Model;
					break;
				}
			}
		}

		if (isUSERAuthenticated) {
			// logInUserIntoSystem();
			mLoginBtn.setClickable(false);
			UserSingleton.getInstance(getContext()).mUserDetail = authenticatedUserModel;

			SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT);
			String currentDateandTime = sdf.format(new Date());

			authenticatedUserModel.setUSR_CRTN_DATE(currentDateandTime);
			UserSingleton.getInstance(getContext()).setUserLoginTimeInPref(
					currentDateandTime);
			UserSingleton.getInstance(getContext()).mUserName = mUserNameEt
					.getText().toString().trim();

			MasterHelper mMasterHelper = new MasterHelper(
					SwitchUserFragment.this, false);
			mMasterHelper.selectUserRightsFromDB(UserSingleton
					.getInstance(getActivity()).mUserDetail.getUSR_GRP_ID(),
					DBConstants.dbo_USR_ASSGND_RGHTS_TABLE, true);

			((HomeActivity) getActivity()).updateTopInfoBar();

			if (getDialog() != null && getDialog().isShowing())
				getDialog().dismiss();
		} else {
			UserSingleton.getInstance(getContext()).mUserDetail = null;
			showAlertMessage(null,
					getResources()
							.getString(R.string.unauthenticated_user_text));
		}
	}

	@Override
	public void executePostAction(OperationalResult opResult) {

		switch (opResult.getResultCode()) {

		case Constants.FETCH_USER_DETAILS_MST:
			checkUSERPresentInMSTTable(opResult);
			break;

		case Constants.FETCH_USER_RIGHT_RECORD:
			createUserRightModel(opResult);
			break;
		}
	}

	@SuppressWarnings("unchecked")
	private void createUserRightModel(OperationalResult opResult) {
		ArrayList<USR_ASSGND_RGHTSModel> mUser_Assgnd_Rghts_Models = (ArrayList<USR_ASSGND_RGHTSModel>) opResult
				.getResult().getSerializable(Constants.RESULTCODEBEAN);
		Log.v("userrightsmodel", ":" + mUser_Assgnd_Rghts_Models);
		UserRightsModel userRights = new UserRightsModel();
		UserSingleton.getInstance(getActivity()).mUserAssgndRightsModel = userRights
				.createUserRIghts(mUser_Assgnd_Rghts_Models);
	}

	@Override
	public void errorReceived(OperationalResult opResult) {
		// TODO Auto-generated method stub

	}

	@Override
	public Context getContext() {
		// TODO Auto-generated method stub
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
