/*
 * Copyright (C) 2013  All Rights Reserved.
 *  PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mpos.framework.handler;

import android.os.AsyncTask;
import android.os.Bundle;

import com.mpos.framework.common.Constants;
import com.mpos.framework.common.Logger;
import com.mpos.framework.common.OperationalResult;
import com.mpos.framework.inf.HandlerInf;
import com.mpos.framework.inf.OnAsyncTaskCompletionInf;

/**
 * Description-
 * 

 * 
 */

public abstract class ActivityHandler extends AsyncTask<Void, Void, Void> {

	public static final String TAG = Constants.APP_TAG
			+ ActivityHandler.class.getSimpleName();

	protected Bundle bundle = new Bundle();
	protected HandlerInf handlerInf;
	protected OperationalResult mOperationalResult = new OperationalResult();
	protected boolean mIsDisabledBackButton = false;
	private OnAsyncTaskCompletionInf onAsyncCompletionInf = null;

	public ActivityHandler(HandlerInf handlerInf) {
		this.handlerInf = handlerInf;
	}

	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		Logger.d(TAG, "onPostExecute");
		// This method will handle all error codes and return back to child
		// helper only valid response data
		handlerInf.handleResponseData(mOperationalResult);
		if (onAsyncCompletionInf != null) {
			onAsyncCompletionInf.OnTaskCompletion(mOperationalResult);
		}
	}

	public void setOnCompletionListener(
			OnAsyncTaskCompletionInf onAsyncCompletionInf) {
		this.onAsyncCompletionInf = onAsyncCompletionInf;
	}
}