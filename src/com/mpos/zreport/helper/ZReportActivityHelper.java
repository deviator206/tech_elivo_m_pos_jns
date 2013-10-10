package com.mpos.zreport.helper;

import com.mpos.framework.common.Constants;
import com.mpos.framework.common.DBOperationlParameter;
import com.mpos.framework.common.Logger;
import com.mpos.framework.common.OperationalResult;
import com.mpos.framework.handler.ActivityHandler;
import com.mpos.framework.helper.ActivityHelper;
import com.mpos.framework.inf.BaseResponseListener;
import com.mpos.master.DBhandler.Currency_MstDBHandler;
import com.mpos.master.DBhandler.DNMTN_MSTDBHandler;

public class ZReportActivityHelper extends ActivityHelper {

	private BaseResponseListener mBaseResponseListener;

	public ZReportActivityHelper(BaseResponseListener responseListener) {
		super(responseListener);
		this.mBaseResponseListener = responseListener;
	}

	@Override
	protected void responseHandle(OperationalResult result) {
		switch (result.getResultCode()) {

		default:
			mBaseResponseListener.executePostAction(result);
			break;
		}
	}

	public void selectCurrencyRecords(String[] whrArgs, String tableName,
			boolean showProgress) {
		Logger.d(TAG, "selectPRDCTRecords");
		mIsContinueProgressbar = showProgress;
		ActivityHandler activityHandler = new Currency_MstDBHandler(this,
				whrArgs, tableName, DBOperationlParameter.SELECT,
				Constants.FETCH_CURRENCIES, false);
		// Start AsyncTask
		getResult(activityHandler);
	}

	public void selectDenominationRecords(String[] whrArgs, String tableName,
			boolean showProgress) {
		Logger.d(TAG, "selectPRDCTRecords");
		mIsContinueProgressbar = showProgress;
		ActivityHandler activityHandler = new DNMTN_MSTDBHandler(this, whrArgs,
				tableName, DBOperationlParameter.SELECT,
				Constants.FETCH_DNMTN_MST, false);
		// Start AsyncTask
		getResult(activityHandler);
	}

}
