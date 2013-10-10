package com.mpos.payment.helper;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;

import com.mpos.framework.common.Constants;
import com.mpos.framework.common.DBConstants;
import com.mpos.framework.common.DBOperationlParameter;
import com.mpos.framework.common.Logger;
import com.mpos.framework.common.OperationalResult;
import com.mpos.framework.handler.ActivityHandler;
import com.mpos.framework.helper.ActivityHelper;
import com.mpos.framework.inf.BaseResponseListener;
import com.mpos.master.DBhandler.BILL_MST_DBHandler;
import com.mpos.master.DBhandler.Currency_MstDBHandler;
import com.mpos.master.NTHandler.CRDCT_CardNTHandler;
import com.mpos.payment.DBHandler.MULTI_CHNG_MST_DBHandler;
import com.mpos.payment.DBHandler.MULTY_PYMNTS_MSTDBHandler;
import com.mpos.payment.DBHandler.PYMNTS_MSTDBHandler;
import com.mpos.payment.model.MULTI_CHNG_MST_Model;
import com.mpos.payment.model.MULTY_PMT_MST_Model;
import com.mpos.payment.model.PAYMT_MST_MODEL;
import com.mpos.transactions.DBHandler.BIllTxnDBHandler;
import com.mpos.transactions.DBHandler.BillInstrctnTxnDBHandler;
import com.mpos.transactions.DBHandler.FLX_POS_TXNDBHandler;
import com.mpos.transactions.DBHandler.Held_Txn_DBHandler;
import com.mpos.transactions.DBHandler.PRDCT_CNSMPTN_DBHandler;
import com.mpos.transactions.DBHandler.SLMN_TXNDBHandler;
import com.mpos.transactions.DBHandler.TempTxnDBHandler;
import com.mpos.transactions.NTHandler.CreditNoteNTHandler;
import com.mpos.transactions.model.TempTXNModel;

public class PaymentHelper extends ActivityHelper {

	public static final String TAG = Constants.APP_TAG
			+ PaymentHelper.class.getSimpleName();

	private BaseResponseListener mBaseResponseListener = null;

	public PaymentHelper(BaseResponseListener responseListener) {
		super(responseListener);

		this.mBaseResponseListener = responseListener;
	}

	@Override
	protected void responseHandle(OperationalResult result) {
		// Error Code
		switch (result.getRequestResponseCode()) {
		case OperationalResult.NETWORK_ERROR:
		case OperationalResult.ERROR:
			result.setResultCode(Constants.MASTER_UPLOAD_ERROR);
			this.mBaseResponseListener.executePostAction(result);
			break;
		case OperationalResult.DB_ERROR:
			// DB error for table
			Logger.d(TAG, "DB_ERROR");
			this.mBaseResponseListener.errorReceived(result);
			break;

		default:
			break;
		}

		// Success Code
		switch (result.getResultCode()) {
		default:
			if (result.getRequestResponseCode() != OperationalResult.DB_ERROR) {
				// Pass UI updation response back to activity
				this.mBaseResponseListener.executePostAction(result);
			}
			break;
		}
	}

	public void fetchAllCurrenciesFromDB() {
		Logger.d(TAG, "selectGRPRecords");
		mIsContinueProgressbar = true;

		ActivityHandler activityHandler = new Currency_MstDBHandler(this, null,
				DBConstants.dbo_Currency_Mst_TABLE,
				DBOperationlParameter.SELECT, Constants.FETCH_CURRENCIES, false);
		// Start AsyncTask
		getResult(activityHandler);
	}

	public void insertAllMULTI_PMT_ENTRIES(
			ArrayList<MULTY_PMT_MST_Model> MULTI_PMT_ARR) {
		mIsContinueProgressbar = true;
		if (MULTI_PMT_ARR != null && MULTI_PMT_ARR.size() > 0) {
			mIsContinueProgressbar = true;
			ActivityHandler activityHandler = new MULTY_PYMNTS_MSTDBHandler(
					this, MULTI_PMT_ARR,
					DBConstants.dbo_MULTI_PYMNTS_MST_TABLE,
					DBOperationlParameter.INSERT,
					Constants.INSERT_MULTI_PMT_RECORDS, false);
			// Start AsyncTask
			getResult(activityHandler);
		}
	}

	public void insertPaymentInfoInPAYMT_MST_TABLE(
			ArrayList<PAYMT_MST_MODEL> paymentMSTArr) {
		mIsContinueProgressbar = true;
		ActivityHandler activityHandler = new PYMNTS_MSTDBHandler(this,
				paymentMSTArr, DBConstants.dbo_PYMNTS_MST_TABLE,
				DBOperationlParameter.INSERT, Constants.INSERT_PAYMT_RECORD,
				false);
		// Start AsyncTask
		getResult(activityHandler);
	}

	public void selectPaymenMstDetails(String classLevelSelectionArg,
			String where, String tableName, boolean showProgress) {
		Logger.d(TAG, "selectPaymenMstDetails");
		mIsContinueProgressbar = showProgress;

		String[] queryParams = { classLevelSelectionArg };

		PYMNTS_MSTDBHandler activityHandler = new PYMNTS_MSTDBHandler(this,
				queryParams, tableName, DBOperationlParameter.SELECT,
				Constants.FETCH_PYMNT_MST_RECORD, false);
		activityHandler.setWhereQuery(where);
		// Start AsyncTask
		getResult(activityHandler);
	}

	public void selectMultiPaymenMstDetails(String classLevelSelectionArg,
			String where, String tableName, boolean showProgress) {
		Logger.d(TAG, "selectMultiPaymenMstDetails");
		mIsContinueProgressbar = showProgress;

		String[] queryParams = { classLevelSelectionArg };

		MULTY_PYMNTS_MSTDBHandler activityHandler = new MULTY_PYMNTS_MSTDBHandler(
				this, queryParams, tableName, DBOperationlParameter.SELECT,
				Constants.FETCH_MULTY_PYMNT_MST_RECORD, false);
		activityHandler.setWhereQuery(where);
		// Start AsyncTask
		getResult(activityHandler);
	}

	/**
	 * Method - To insert all multi change records
	 * 
	 * @param MULTI_CHNG_ARR
	 */
	public void insert_MULTI_CHNG_ENTRIES(
			ArrayList<MULTI_CHNG_MST_Model> MULTI_CHNG_ARR) {
		mIsContinueProgressbar = true;
		if (MULTI_CHNG_ARR != null && MULTI_CHNG_ARR.size() > 0) {
			mIsContinueProgressbar = true;
			ActivityHandler activityHandler = new MULTI_CHNG_MST_DBHandler(
					this, MULTI_CHNG_ARR, DBConstants.dbo_MULTI_CHNG_MST_TABLE,
					DBOperationlParameter.INSERT,
					Constants.INSERT_MULTI_CHNG_RECORDS, false);
			// Start AsyncTask
			getResult(activityHandler);
		}
	}

	/*
	 * Method- To get all current transaction no. Issue changes
	 */
	public void retrieveMultiChngEntries(String query, String tableName,
			boolean showProgress) {
		mIsContinueProgressbar = showProgress;

		String[] queryParam = { query };

		MULTI_CHNG_MST_DBHandler activityHandler = new MULTI_CHNG_MST_DBHandler(
				this, queryParam, tableName, DBOperationlParameter.SELECT,
				Constants.FETCH_MULTI_CHNG_MST, false);

		String whrQuery = DBConstants.MCM_PYMNT_NMBR + "=?";
		activityHandler.setWhereQuery(whrQuery);
		// Start AsyncTask
		getResult(activityHandler);
	}

	// ========== Trasaction methods

	/**
	 * Method - To fetch all trasaction nos
	 * 
	 */
	public void selectAllBIllMStTrasNo(String tableName, boolean showProgress) {
		Logger.d(TAG, "selectAllBIllMStTrasNo");
		mIsContinueProgressbar = showProgress;

		BILL_MST_DBHandler activityHandler = new BILL_MST_DBHandler(this, null,
				tableName, DBOperationlParameter.SELECT,
				Constants.FETCH_BILL_MST_TXNNO_RECORD, false);

		activityHandler.setSelectAll(true);
		// Start AsyncTask
		getResult(activityHandler);
	}

	/**
	 * Method - To fetch current running bill mst data, by trasaction no and
	 * txntype BI
	 */
	public void selectRunningBillMSt(String[] queryParams, String tableName,
			boolean showProgress) {
		Logger.d(TAG, "selectRunningBillMSt");
		mIsContinueProgressbar = showProgress;

		BILL_MST_DBHandler activityHandler = new BILL_MST_DBHandler(this,
				queryParams, tableName, DBOperationlParameter.SELECT,
				Constants.FETCH_BILL_MST_RECORD, false);

		String whrQuer = DBConstants.BILL_SCNCD + "=?";

		activityHandler.setWhereQuery(whrQuer);
		// Start AsyncTask
		getResult(activityHandler);
	}

	/**
	 * Method - To fetch current running bill txn data, by trasaction no and
	 * txntype BI
	 */
	public void selectRunningBillTxn(String[] queryParams, String tableName,
			boolean showProgress) {
		Logger.d(TAG, "selectRunningBillTxn");
		mIsContinueProgressbar = showProgress;

		BIllTxnDBHandler activityHandler = new BIllTxnDBHandler(this,
				queryParams, tableName, DBOperationlParameter.SELECT,
				Constants.FETCH_BILL_TXN_RECORD, false);

		String whrQuer = DBConstants.TXN_BILL_SCNCD + "=? and "
				+ DBConstants.TXN_TXN_TYPE + "=?";
		activityHandler.setWhereQuery(whrQuer);
		// Start AsyncTask
		getResult(activityHandler);
	}

	/**
	 * Method- To fetch all customer data associated to that transaction
	 * 
	 * @param object
	 * @param dboFlxPosMstTable
	 * @param b
	 */
	public void selectRunningFLXPOSTxnRecords(String[] queryParams,
			String tableName, boolean showProgress) {
		Logger.d(TAG, "selectRunningFLXPOSRecords");
		mIsContinueProgressbar = showProgress;

		FLX_POS_TXNDBHandler activityHandler = new FLX_POS_TXNDBHandler(this,
				queryParams, tableName, DBOperationlParameter.SELECT,
				Constants.FETCH_FLX_POS_TXN, false);

		String whrQuer = DBConstants.BILL_SCNCD + "=?";

		activityHandler.setWhereQuery(whrQuer);
		// Start AsyncTask
		getResult(activityHandler);
	}

	/**
	 * Method- To fetch all salesman data related to trasaction id
	 * 
	 * @param queryParams
	 * @param tableName
	 * @param showProgress
	 */
	public void selectRunningSalesmanTxnRecords(String[] queryParams,
			String tableName, boolean showProgress) {
		Logger.d(TAG, "selectRunningSalesmanTxnRecords");
		mIsContinueProgressbar = showProgress;

		SLMN_TXNDBHandler activityHandler = new SLMN_TXNDBHandler(this,
				queryParams, tableName, DBOperationlParameter.SELECT,
				Constants.FETCH_SALSMAN_TXN_RECORD, false);

		String whrQuer = DBConstants.BILL_SCNCD + "=?";

		activityHandler.setWhereQuery(whrQuer);
		// Start AsyncTask
		getResult(activityHandler);
	}

	/**
	 * Method- To fetch all Held Transaction data related to trasaction id
	 * 
	 * @param queryParams
	 * @param tableName
	 * @param showProgress
	 */
	public void selectRunningHeldTxnRecords(String[] queryParams,
			String tableName, boolean showProgress) {
		Logger.d(TAG, "selectRunningHeldTxnRecords");
		mIsContinueProgressbar = showProgress;

		Held_Txn_DBHandler activityHandler = new Held_Txn_DBHandler(this,
				queryParams, tableName, DBOperationlParameter.SELECT,
				Constants.FETCH_HELD_TXN_RECORD, false);

		String whrQuer = DBConstants.BILL_SCNCD + "=?";

		activityHandler.setWhereQuery(whrQuer);
		// Start AsyncTask
		getResult(activityHandler);
	}

	/**
	 * Method- To fetch all consumption data as per transaction id
	 * 
	 * @param queryParams
	 * @param tableName
	 * @param showProgress
	 */
	public void selectRunningCnsmptnRecords(String[] queryParams,
			String tableName, boolean showProgress) {
		Logger.d(TAG, "selectRunningCnsmptnRecords");
		mIsContinueProgressbar = showProgress;

		PRDCT_CNSMPTN_DBHandler activityHandler = new PRDCT_CNSMPTN_DBHandler(
				this, queryParams, tableName, DBOperationlParameter.SELECT,
				Constants.FETCH_CNSMPTN_RECORD, false);

		String whrQuer = DBConstants.CNSMPTN_TXN_NO + "=?";

		activityHandler.setWhereQuery(whrQuer);
		// Start AsyncTask
		getResult(activityHandler);
	}

	/**
	 * Method- To select Bill TXN record data
	 * 
	 * @param string
	 * @param dboBillInstrctnTxnTable
	 * @param b
	 */
	public void selectRunningBillInstrc_TXNRecords(String[] queryParams,
			String tableName, boolean showProgress) {
		Logger.d(TAG, "selectRunningBillInstrc_TXNRecords");
		mIsContinueProgressbar = showProgress;

		BillInstrctnTxnDBHandler activityHandler = new BillInstrctnTxnDBHandler(
				this, queryParams, tableName, DBOperationlParameter.SELECT,
				Constants.FETCH_BILL_INSTRCTN_TXN_RECORD, false);

		String whrQuer = DBConstants.BILL_INSTRCTN_TXN_BILL_SCND + "=?";
		activityHandler.setWhereQuery(whrQuer);

		// Start AsyncTask
		getResult(activityHandler);
	}

	/**
	 * Method- To fetch all Multi chnge data related to trasaction id
	 * 
	 * @param queryParams
	 * @param tableName
	 * @param showProgress
	 */
	public void selectMultiChngMstDetails(String queryParams, String where,
			String tableName, boolean showProgress) {
		Logger.d(TAG, "selectMultiChngMstDetails");
		mIsContinueProgressbar = showProgress;
		
		String[] queryParam = { queryParams };

		MULTI_CHNG_MST_DBHandler activityHandler = new MULTI_CHNG_MST_DBHandler(
				this, queryParam, tableName, DBOperationlParameter.SELECT,
				Constants.FETCH_MULTI_CHNG_MST_RECORD, false);

		String whrQuer = DBConstants.MCM_PYMNT_NMBR + "=?";

		activityHandler.setWhereQuery(whrQuer);
		// Start AsyncTask
		getResult(activityHandler);
	}

	/**
	 * Method- To insert Offline TXN records in temp table
	 * 
	 * @param queryParams
	 * @param where
	 * @param tableName
	 * @param showProgress
	 */
	public void saveInTempTXNDB(TempTXNModel txn_Models, String tableName,
			boolean showProgress) {
		Logger.d(TAG, "saveInTempTXNDB");
		mIsContinueProgressbar = showProgress;

		ActivityHandler activityHandler = new TempTxnDBHandler(this,
				txn_Models, tableName, DBOperationlParameter.INSERT,
				Constants.INSERT_TEMP_TXN_RECORD, false);
		// Start AsyncTask
		getResult(activityHandler);
	}
	
	/**
	 * Method- To delete all Offline TXN records from temp table
	 * 
	 * @param queryParams
	 * @param where
	 * @param tableName
	 * @param showProgress
	 */
	public void deleteTempTXNDB(String tableName,
			boolean showProgress) {
		Logger.d(TAG, "deleteTempTXNDB");
		mIsContinueProgressbar = showProgress;

		ActivityHandler activityHandler = new TempTxnDBHandler(this,
				"", tableName, DBOperationlParameter.DELETE,
				Constants.DELETE_TEMP_TXN_RECORD, false);
		// Start AsyncTask
		getResult(activityHandler);
	}

	/**
	 * Method- To retrieve all currency data from db
	 * 
	 * @param queryPAram
	 * @param tableName
	 * @param showProgress
	 */
	public void retrieveOfflineTempTxnRecordsFrmDB(String queryPAram,
			String tableName, boolean showProgress) {
		mIsContinueProgressbar = showProgress;

		String[] queryParam = { queryPAram };

		ActivityHandler activityHandler = new TempTxnDBHandler(this,
				queryParam, tableName, DBOperationlParameter.SELECT,
				Constants.FETCH_OFFLINE_TXN, false);
		// Start AsyncTask
		getResult(activityHandler);
	}

	/**
	 * Method- To credit note is valid
	 * 
	 * @param url
	 * @param params
	 * @param showProgress
	 */
	public void checkIfValidCreditNote(String url, List<NameValuePair> params,
			boolean showProgress) {
		mIsContinueProgressbar = showProgress;

		// Start AsyncTask
		ActivityHandler activityHandler = new CreditNoteNTHandler(this, params,
				url, Constants.GET_CREDIT_NOTE_RESPONSE);
		
		getResult(activityHandler);
	}
}
