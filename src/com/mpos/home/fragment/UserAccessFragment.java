package com.mpos.home.fragment;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.mpos.R;
import com.mpos.framework.common.Constants;
import com.mpos.framework.common.DBConstants;
import com.mpos.framework.common.Logger;
import com.mpos.framework.common.OperationalResult;
import com.mpos.framework.inf.ActivityFragmentCommunicationListener;
import com.mpos.framework.inf.BaseResponseListener;
import com.mpos.login.helper.LoginHelper;
import com.mpos.login.model.USER_MST_Model;
import com.mpos.master.helper.MasterHelper;
import com.mpos.master.model.USR_ASSGND_RGHTSModel;
import com.mpos.master.model.UserRightsModel;

public class UserAccessFragment extends DialogFragment implements
		BaseResponseListener {

	public static final int USER_ACCESS = 100;
	public static final int USER_SUCCESS = 101;
	public static final int USER_ERROR = 102;
	private static String message;
	private static String title;
	private EditText userNameET;
	private EditText passwordET;
	private View view;
	private final String ALERT_FRAGMENT = "ALERT_FRAGMENT";

	private ActivityFragmentCommunicationListener communicationListener;
	private OnClickListener mOnClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {

			InputMethodManager inputManager = (InputMethodManager) getActivity()
					.getSystemService(Context.INPUT_METHOD_SERVICE);

			inputManager.hideSoftInputFromWindow(getActivity()
					.getCurrentFocus().getWindowToken(),
					InputMethodManager.HIDE_NOT_ALWAYS);
			switch (v.getId()) {
			case R.id.enterButton:
				authenticateAndCheckRightsForUser();
				break;

			case R.id.cancelBtn:
				
				Intent intent = new Intent();
				intent.putExtra(Constants.KEY, "Error");
				getTargetFragment().onActivityResult(USER_ACCESS, USER_ERROR,
						intent);
				dismiss();
				break;
			}

		}
	};

	public static UserAccessFragment newInstance(String title, String message) {
		UserAccessFragment frag = new UserAccessFragment();
		UserAccessFragment.message = message;
		UserAccessFragment.title = title;
		return frag;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = LayoutInflater.from(getActivity()).inflate(
				R.layout.user_access_dialog, null);
		TextView messageText = (TextView) view.findViewById(R.id.messagetext);
		messageText.setText(message);
		userNameET = (EditText) view.findViewById(R.id.userNameET);
		passwordET = (EditText) view.findViewById(R.id.passwordET);
		view.findViewById(R.id.useraccesslayout).setVisibility(View.VISIBLE);
		view.findViewById(R.id.enterButton)
				.setOnClickListener(mOnClickListener);
		view.findViewById(R.id.cancelBtn).setOnClickListener(mOnClickListener);

		return view;
	}

	protected void authenticateAndCheckRightsForUser() {
		LoginHelper helper = new LoginHelper(this);
		// helper.getUSERDetailsFromUSERMSTTable("ADMIN");
		helper.fetchUSERDetailsFromUSERMSTTable(userNameET.getText().toString());
	}

	@Override
	public void executePostAction(OperationalResult opResult) {

		switch (opResult.getResultCode()) {

		case Constants.FETCH_USER_DETAILS_MST:
			checkUSERPresentInMSTTable(opResult);
			break;

		case Constants.FETCH_USER_RIGHT_RECORD:
			createUserRightsModel(opResult);
			break;

		case Constants.INSERT_USER_MST_RECORDS:

			break;
		}
	}

	private void createUserRightsModel(OperationalResult opResult) {
		ArrayList<USR_ASSGND_RGHTSModel> mUser_Assgnd_Rghts_Models = (ArrayList<USR_ASSGND_RGHTSModel>) opResult
				.getResult().getSerializable(Constants.RESULTCODEBEAN);

		UserRightsModel userRights = new UserRightsModel();
		userRights = userRights.createUserRIghts(mUser_Assgnd_Rghts_Models);
		Log.v("userrightsmodel", ":" + userRights);
		if(userRights==null){
			showAlertDialog("Invalid User Rights.");
			dismiss();
		}
		if (getTargetFragment() != null) {
			Intent intent = new Intent();
			intent.putExtra(Constants.KEY, userRights);
			getTargetFragment().onActivityResult(USER_ACCESS, USER_SUCCESS,
					intent);
			dismiss();
		}
		if (communicationListener != null) {
			communicationListener.userAuthenticationSuccess(userRights);
			dismiss();
		}

	}

	@Override
	public void errorReceived(OperationalResult opResult) {

	}

	@Override
	public Context getContext() {

		return getActivity();
	}

	private void checkUSERPresentInMSTTable(OperationalResult opResult) {
		ArrayList<USER_MST_Model> mUser_MST_Models = (ArrayList<USER_MST_Model>) opResult
				.getResult().getSerializable(Constants.RESULTCODEBEAN);
		Logger.v("useraccessfragment", "checkUSERPresentInMSTTable"
				+ mUser_MST_Models);
		boolean isUSERAuthenticated = false;
		USER_MST_Model authenticatedUserModel = null;
		if (mUser_MST_Models != null) {

			for (USER_MST_Model user_MST_Model : mUser_MST_Models) {
				if (user_MST_Model.getUSR_NM().equalsIgnoreCase(
						userNameET.getText().toString())
						&& user_MST_Model.getUSR_PWD().equalsIgnoreCase(
								passwordET.getText().toString())) {
					// user_MST_Model.getUSR_PWD().equalsIgnoreCase(passwordET.getText().toString())
					isUSERAuthenticated = true;
					authenticatedUserModel = user_MST_Model;
					break;
				}
			}
		}

		if (isUSERAuthenticated) {
			// take respective action
			Logger.v("useraccessfragment", "user authenticated");
			MasterHelper helper = new MasterHelper(this, false);
			helper.selectUserRightsFromDB(
					authenticatedUserModel.getUSR_GRP_ID(),
					DBConstants.dbo_USR_ASSGND_RGHTS_TABLE, true);
		} else {
			Logger.v("useraccessfragment", "user not authenticated");
			if (getTargetFragment() != null) {
				Intent intent = new Intent();
				intent.putExtra(Constants.KEY, "Error");
				getTargetFragment().onActivityResult(USER_ACCESS, USER_ERROR,
						intent);
			}
			showAlertDialog(getResources().getString(
					R.string.unauthenticated_user_text));
			dismiss();
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

	public ActivityFragmentCommunicationListener getCommunicationListener() {
		return communicationListener;
	}

	public void setCommunicationListener(
			ActivityFragmentCommunicationListener communicationListener) {
		this.communicationListener = communicationListener;
	}

}
