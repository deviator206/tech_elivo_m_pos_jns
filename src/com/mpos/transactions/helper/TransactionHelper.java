package com.mpos.transactions.helper;

import android.content.Context;

import com.mpos.framework.common.Constants;
import com.mpos.framework.common.DBOperationlParameter;
import com.mpos.framework.common.Logger;
import com.mpos.framework.common.OperationalResult;
import com.mpos.framework.handler.ActivityHandler;
import com.mpos.framework.helper.ActivityHelper;
import com.mpos.framework.inf.BaseResponseListener;
import com.mpos.transactions.DBHandler.FLX_POS_TXNDBHandler;
import com.mpos.transactions.DBHandler.SLMN_TXNDBHandler;
import com.mpos.transactions.model.FLX_POS_TXNModel;
import com.mpos.transactions.model.SLMN_TXN_Model;

public class TransactionHelper extends ActivityHelper {

	
	private Context mContext;
	private BaseResponseListener mBaseResponseListener = null;
	
	public TransactionHelper(BaseResponseListener responseListener) {
		super(responseListener);
		this.mBaseResponseListener = responseListener;
	}
	
	public void insertFLX_POS_TXN_Records(FLX_POS_TXNModel flx_pos_txn,
			String tableName, boolean showProgress) {
		Logger.d(TAG, "insertFLX_POS_TXN_Records");
		mIsContinueProgressbar = showProgress;

		ActivityHandler activityHandler = new FLX_POS_TXNDBHandler(this,
				flx_pos_txn, tableName, DBOperationlParameter.INSERT,
				Constants.INSERT_FLX_POS_TXN, false);
		// Start AsyncTask
		getResult(activityHandler);
	}
	
	public void insertSLMN_TXN_Records(SLMN_TXN_Model flx_pos_txn,
			String tableName, boolean showProgress) {
		Logger.d(TAG, "insertSLMN_TXN_Records");
		mIsContinueProgressbar = showProgress;

		ActivityHandler activityHandler = new SLMN_TXNDBHandler(this,
				flx_pos_txn, tableName, DBOperationlParameter.INSERT,
				Constants.INSERT_SLMN_TXN, false);
		// Start AsyncTask
		getResult(activityHandler);
	}

	@Override
	protected void responseHandle(OperationalResult result) {
		switch (result.getResultCode()) {

		default:
			mBaseResponseListener.executePostAction(result);
			break;
		}
	}

}
