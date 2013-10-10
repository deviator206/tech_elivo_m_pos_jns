/*
 * Copyright (C) 2013 USAP Pvt. Ltd. All Rights Reserved.
 * USAP PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mpos.framework.helper;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;

import com.mpos.framework.common.Constants;
import com.mpos.framework.common.Logger;
import com.mpos.framework.common.OperationalResult;
import com.mpos.framework.handler.ActivityHandler;
import com.mpos.framework.inf.BaseResponseListener;
import com.mpos.framework.inf.HandlerInf;
import com.mpos.framework.inf.OnAsyncTaskCompletionInf;

/**
 * Description-
 * 
 * 
 */
public abstract class ActivityHelper implements HandlerInf {

	public static final String TAG = Constants.APP_TAG
			+ ActivityHelper.class.getSimpleName();

	protected BaseResponseListener responseListener = null;

	private static ProgressDialog mProgressDialog = null;
	protected boolean mIsDisabledBackButton = false;
	protected boolean mIsContinueProgressbar = false;
	public static boolean NETWORK_NOT_AVAILABLE_POPUP_SHOWN = false;

	// Abstract method to implemented by all child classes
	abstract protected void responseHandle(OperationalResult result);

	private OnAsyncTaskCompletionListener onTaskCompletionListener = new OnAsyncTaskCompletionListener();
	private int onCompletionResultCode = -890890;
	private int taskCount = 0;

	public ActivityHelper(BaseResponseListener responseListener) {
		this.responseListener = responseListener;
	}

	/**
	 * Method- To begin AsyncTask and get response data
	 * 
	 * @return
	 */
	protected void getResult(ActivityHandler mActivityHandler) {
		Logger.d(TAG, "getResult");
		startProgressbar();
		mActivityHandler.execute();
	}

	/**
	 * Method- method will handle all error codes and return back to child
	 * helper only valid response data
	 * 
	 * @param result
	 */
	@Override
	public void handleResponseData(OperationalResult result) {
		Logger.d(TAG, "code:" + result.getRequestResponseCode());

		switch (result.getRequestResponseCode()) {
		case OperationalResult.SUCCESS:
			// Method will handle all sucess codes. Also the default case will
			// be the response which will be returned to activity for UI
			// updation.
			stopProgressBar();
			handleSuccessCodes(result);
			break;

		case OperationalResult.MASTER_ERROR:
			stopProgressBar();
			Logger.d(TAG, "Result Code: " + result.getResultCode());
			result.setResultCode(OperationalResult.MASTER_ERROR);
			handleSuccessCodes(result);
			break;

		case OperationalResult.DB_ERROR:
			// Method will handle all DB error codes. Also the default case will
			// be the response which will be returned to activity for UI
			// updation.
			stopProgressBar();
			Logger.d(TAG, "DB_Error:" + result.getResultCode());
			handleSuccessCodes(result);
			break;

		case OperationalResult.DATA_PROTOCOL_ERROR:
			responseHandle(result);
			showAlertMessage(
					Constants.ALERT,
					"Invalid data received from module : "
							+ result.getApiName(), false);

			break;
		case OperationalResult.ERROR:
			stopProgressBar();
			responseHandle(result);
			/*
			 * stopProgressBar(); if(!NETWORK_NOT_AVAILABLE_POPUP_SHOWN) {
			 * NETWORK_NOT_AVAILABLE_POPUP_SHOWN = true;
			 * showAlertMessage(Constants.ALERT,
			 * "Network Not Available. Please check your network connection.",
			 * false);
			 * 
			 * }
			 */

			break;

		case OperationalResult.NETWORK_ERROR:
			stopProgressBar();
			// responseHandle(result);
			// stopProgressBar();
			if (!NETWORK_NOT_AVAILABLE_POPUP_SHOWN) {
				NETWORK_NOT_AVAILABLE_POPUP_SHOWN = true;
				showAlertMessage(
						Constants.ALERT,
						"Network Not Available. Please check your network connection.",
						false);

			}
			break;

		default:
			stopProgressBar();
			showAlertMessage(
					Constants.ALERT,
					"We are not quite sure what happened! Please try again later.",
					false);
			break;
		}
	}

	/**
	 * Method- Method will handle all success codes. Also the default case will
	 * be the response which will be returned to helper for UI updation.
	 * 
	 * @param result
	 */
	private void handleSuccessCodes(OperationalResult result) {
		switch (result.getResponseErrorCode()) {

		default: // Operation Success, return back to helper for further
					// handling
			Logger.d(TAG, "Default");
			// result.setResultCode(OperationalResult.DB_ERROR);
			responseHandle(result);
			break;
		}
	}

	public void showAlertMessage(String szAlert, String szMessage,
			final boolean isFinishRequired) {
		if (!((Activity) responseListener.getContext()).isFinishing()) {
			AlertDialog.Builder builder = new AlertDialog.Builder(
					responseListener.getContext());
			builder.setCancelable(false)
					.setPositiveButton("OK",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									dialog.dismiss();
									if (isFinishRequired) {
										((Activity) responseListener
												.getContext()).finish();
									}
								}
							}).setMessage(szMessage).setTitle(szAlert);
			AlertDialog alert = builder.create();
			alert.setCancelable(false);
			alert.show();

		}
	}

	/**
	 * call this method, when BaseResponseListener require the notification
	 * after completion of async tasks
	 * 
	 * @param alActivityHandlers
	 *            : List of handlers
	 * @param resultcode
	 *            : caller should match this result code in the
	 *            BaseResponseListener
	 * @param isNotifyAnyAsyncError
	 *            : true : require notification even if error occurred in the
	 *            one of the handler.
	 */
	protected void sycnAsyncTasks(
			ArrayList<ActivityHandler> alActivityHandlers, int resultcode) {
		this.onCompletionResultCode = resultcode;
		taskCount = alActivityHandlers.size();

		for (ActivityHandler activityhandler : alActivityHandlers) {
			activityhandler.setOnCompletionListener(onTaskCompletionListener);
		}
	}

	/**
	 * This object of this class is shared with Handlers to notify
	 * BaseResponseListener after completion of all handlers execution.
	 */
	private class OnAsyncTaskCompletionListener implements
			OnAsyncTaskCompletionInf {
		@Override
		public void OnTaskCompletion(OperationalResult opResult) {

			synchronized (this) {
				taskCount--;
				if (taskCount == 0) {
					opResult.setResultCode(onCompletionResultCode);
					responseListener.executePostAction(opResult);
				}
			}
		}
	}

	/**
	 * This method will start ProgressDialog indicating background activity.
	 */
	protected void startProgressbarDisabledBackButton() {

		mProgressDialog = new ProgressDialog(responseListener.getContext()) {
			@Override
			public void onBackPressed() {

			}
		};
		mProgressDialog.setMessage("Loading");
		mProgressDialog.setCancelable(false);
		mProgressDialog.show();
	}

	/**
	 * This method will start ProgressDialog indicating background activity.
	 */
	protected void startProgressbar() {
		if (mProgressDialog == null) {
			mProgressDialog = new ProgressDialog(responseListener.getContext());
		}

		if (!mProgressDialog.isShowing() && !mIsContinueProgressbar) {

			mProgressDialog.setMessage("Loading");

			mProgressDialog.setCanceledOnTouchOutside(false);

			mProgressDialog.show();

			Logger.d(TAG, "startProgress:");

		}

		/*
		 * if (!mIsContinueProgressbar) {
		 * 
		 * mProgressDialog = new ProgressDialog(responseListener.getContext());
		 * mProgressDialog.setMessage("Loading");
		 * mProgressDialog.setCanceledOnTouchOutside(false);
		 * mProgressDialog.show(); Logger.i(TAG, "startProgressbar"); }
		 */

	}

	protected void stopProgressBar() {
		if (!mIsContinueProgressbar) {
			if (mProgressDialog != null && mProgressDialog.isShowing()) {
				Logger.i(TAG, "stopProgressBar");
				mProgressDialog.cancel();
				
			}
		}
	}
}