/*
 * Copyright (C) 2013  All Rights Reserved.
 *  PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mpos.home.helper;

import java.util.ArrayList;

import com.mpos.application.MPOSApplication;
import com.mpos.framework.Util.UserSingleton;
import com.mpos.framework.common.Constants;
import com.mpos.framework.common.DBConstants;
import com.mpos.framework.common.DBOperationlParameter;
import com.mpos.framework.common.Logger;
import com.mpos.framework.common.OperationalResult;
import com.mpos.framework.handler.ActivityHandler;
import com.mpos.framework.helper.ActivityHelper;
import com.mpos.framework.inf.BaseResponseListener;
import com.mpos.home.DBhandler.FloatPettyPickupCurrencyDBHandler;
import com.mpos.home.model.BillTransactionModel;
import com.mpos.home.model.FloatPickupPettyCurrencyModel;
import com.mpos.master.DBhandler.BILL_MST_DBHandler;
import com.mpos.master.DBhandler.ModifierDBHandler;
import com.mpos.master.DBhandler.PRDCT_RECIPE_DTL_MST_DBHandler;
import com.mpos.master.DBhandler.PTY_FLT_MST_DBHandler;
import com.mpos.master.DBhandler.Prdct_Instruc_DBHandler;
import com.mpos.master.DBhandler.UomDBHandler;
import com.mpos.master.DBhandler.Uom_Slab_DBHandler;
import com.mpos.master.DBhandler.VATDBHandler;
import com.mpos.master.model.BILL_Mst_Model;
import com.mpos.master.model.MULTI_PTY_FLT_MST_Model;
import com.mpos.master.model.Multi_Pty_Flt_MST_DBHandler;
import com.mpos.master.model.Petty_Float_Model;
import com.mpos.payment.DBHandler.MULTY_PYMNTS_MSTDBHandler;
import com.mpos.payment.model.MULTY_PMT_MST_Model;
import com.mpos.transactions.DBHandler.BIllTxnDBHandler;
import com.mpos.transactions.DBHandler.BillInstrctnTxnDBHandler;
import com.mpos.transactions.DBHandler.Held_Txn_DBHandler;
import com.mpos.transactions.DBHandler.PRDCT_CNSMPTN_DBHandler;
import com.mpos.transactions.DBHandler.TempTxnDBHandler;
import com.mpos.transactions.model.Bill_INSTRCTN_TXN_Model;
import com.mpos.transactions.model.HeldTransactionModel;
import com.mpos.transactions.model.PRDCT_CNSMPTN_TXNModel;

/**
 * Description-
 * 
 */

public class HomeHelper extends ActivityHelper {

	public static final String TAG = Constants.APP_TAG
			+ HomeHelper.class.getSimpleName();

	private BaseResponseListener mBaseResponseListener = null;

	/**
	 * @param responseListener
	 */
	public HomeHelper(BaseResponseListener responseListener) {
		super(responseListener);
		// TODO Auto-generated constructor stub
		this.mBaseResponseListener = responseListener;
	}

	/**
	 * Method- To select UOM_SLAB data
	 * 
	 * @param classLevelSelectionArg
	 * @param tableName
	 * @param showProgress
	 */
	public void selectPTY_FLT_MSTRecords(String classLevelSelectionArg,
			String tableName, boolean showProgress) {
		Logger.d(TAG, "selectUOM_SLABRecords");
		mIsContinueProgressbar = showProgress;

		String[] queryParams = { classLevelSelectionArg };

		ActivityHandler activityHandler = new PTY_FLT_MST_DBHandler(this,
				queryParams, tableName, DBOperationlParameter.SELECT,
				Constants.FETCH_PTY_FLT_MST_RECORD, false);
		// Start AsyncTask
		getResult(activityHandler);
	}

	/**
	 * Method- To select UOM_SLAB data
	 * 
	 * @param classLevelSelectionArg
	 * @param tableName
	 * @param showProgress
	 */
	public void selectUOM_SLABRecords(String classLevelSelectionArg,
			String tableName, boolean showProgress) {
		Logger.d(TAG, "selectUOM_SLABRecords");
		mIsContinueProgressbar = showProgress;

		String[] queryParams = { classLevelSelectionArg };

		ActivityHandler activityHandler = new Uom_Slab_DBHandler(this,
				queryParams, tableName, DBOperationlParameter.SELECT,
				Constants.FETCH_UOM_SLAB_RECORD, false);
		// Start AsyncTask
		getResult(activityHandler);
	}

	/**
	 * Method- To select UOM Measure data
	 * 
	 * @param classLevelSelectionArg
	 * @param tableName
	 * @param showProgress
	 */
	public void selectUOMRecords(String classLevelSelectionArg,
			String tableName, boolean showProgress) {
		Logger.d(TAG, "selectUOMRecords");
		mIsContinueProgressbar = showProgress;

		String[] queryParams = { classLevelSelectionArg };

		ActivityHandler activityHandler = new UomDBHandler(this, queryParams,
				tableName, DBOperationlParameter.SELECT,
				Constants.FETCH_UOM_RECORD, false);
		// Start AsyncTask
		getResult(activityHandler);
	}

	/**
	 * Method- To select Modifier/Instruction data
	 * 
	 * @param classLevelSelectionArg
	 * @param tableName
	 * @param showProgress
	 */
	public void selectModifierRecords(String classLevelSelectionArg,
			String tableName, boolean showProgress) {
		Logger.d(TAG, "selectModifierRecords");
		mIsContinueProgressbar = showProgress;

		String[] queryParams = { classLevelSelectionArg };

		ActivityHandler activityHandler = new ModifierDBHandler(this,
				queryParams, tableName, DBOperationlParameter.SELECT,
				Constants.FETCH_MODIFIER_RECORD, false);
		// Start AsyncTask
		getResult(activityHandler);
	}

	/**
	 * Method- To select Modifier/Instruction code for particular prdct
	 * 
	 * @param classLevelSelectionArg
	 * @param tableName
	 * @param showProgress
	 */
	public void getPrdctModifierRecords(String classLevelSelectionArg,
			String tableName, boolean showProgress) {
		Logger.d(TAG, "getPrdctModifierRecords");
		mIsContinueProgressbar = showProgress;

		String[] queryParams = { classLevelSelectionArg };

		ActivityHandler activityHandler = new Prdct_Instruc_DBHandler(this,
				queryParams, tableName, DBOperationlParameter.SELECT,
				Constants.FETCH_PRDCT_INSTRC_CODE_RECORD, false);
		// Start AsyncTask
		getResult(activityHandler);
	}

	/**
	 * Method- To select Vat data
	 * 
	 * @param classLevelSelectionArg
	 * @param tableName
	 * @param showProgress
	 */
	public void selectVatRecords(String classLevelSelectionArg,
			String tableName, boolean showProgress) {
		Logger.d(TAG, "selectVatRecords");
		mIsContinueProgressbar = showProgress;

		String[] queryParams = { classLevelSelectionArg };

		ActivityHandler activityHandler = new VATDBHandler(this, queryParams,
				tableName, DBOperationlParameter.SELECT,
				Constants.FETCH_VAT_RECORD, false);
		// Start AsyncTask
		getResult(activityHandler);
	}

	/**
	 * Method- To select Ingredients data
	 * 
	 * @param classLevelSelectionArg
	 * @param tableName
	 * @param showProgress
	 */
	public void selectIngredientsRecords(String classLevelSelectionArg,
			String tableName, boolean showProgress) {
		Logger.d(TAG, "selectIngredientsRecords");
		mIsContinueProgressbar = showProgress;

		String[] queryParams = { classLevelSelectionArg };

		ActivityHandler activityHandler = new PRDCT_RECIPE_DTL_MST_DBHandler(
				this, queryParams, tableName, DBOperationlParameter.SELECT,
				Constants.FETCH_INGREDIENT_RECORD, false);
		// Start AsyncTask
		getResult(activityHandler);
	}

	/**
	 * Method- To insert Bill record to table
	 * 
	 * @param result
	 */
	public void savePrdctDataInBillMstDB(BILL_Mst_Model model) {
		Logger.d(TAG, "savePrdctDataInBillMstDB");
		mIsContinueProgressbar = true;
		if (model != null) {
			ActivityHandler activityHandler = new BILL_MST_DBHandler(this,
					model, DBConstants.dbo_BILL_MST_TABLE,
					DBOperationlParameter.INSERT,
					Constants.INSERT_BILL_MST_RECORDS, false);
			// Start AsyncTask
			getResult(activityHandler);
		}
	}

	/**
	 * Method- To retrieve any similar present Bill record to table
	 * 
	 * @param transactionNo
	 * 
	 * @param result
	 */
	public void retrievSameBillMstDB(String transactionNo) {
		Logger.d(TAG, "retrievSameBillMstDB");
		mIsContinueProgressbar = true;
		String[] args = { transactionNo };
		ActivityHandler activityHandler = new BILL_MST_DBHandler(this, args,
				DBConstants.dbo_BILL_MST_TABLE,
				DBOperationlParameter.GET_COUNT, Constants.GET_BILL_MST_COUNT,
				false);
		// Start AsyncTask
		getResult(activityHandler);

	}

	/**
	 * Method- To update Bill MST TXN TYPE field record to table
	 * 
	 * @param result
	 */
	public void updateTXNTyprInBillMstDB(BILL_Mst_Model model) {
		Logger.d(TAG, "updateTXNTyprInBillMstDB");
		mIsContinueProgressbar = true;
		if (model != null) {
			ActivityHandler activityHandler = new BILL_MST_DBHandler(this,
					model, DBConstants.dbo_BILL_MST_TABLE,
					DBOperationlParameter.UPDATE,
					Constants.UPDATE_BILL_MST_TXN_TYPE_RECORD, false);
			// Start AsyncTask
			getResult(activityHandler);
		}
	}

	/**
	 * Method- To update Bill TXN TYPE field record to table
	 * 
	 * @param result
	 */
	public void updateTXNTypeInBillTXNDB(
			ArrayList<BillTransactionModel> models, boolean txnType) {
		Logger.d(TAG, "updateTXNTypeInBillTXNDB");
		mIsContinueProgressbar = true;
		if (models != null && models.size() > 0) {
			BIllTxnDBHandler activityHandler = new BIllTxnDBHandler(this,
					models, DBConstants.dbo_BILL_TXN_TABLE,
					DBOperationlParameter.UPDATE,
					Constants.UPDATE_BILL_TXN_TYPE_RECORD, false);

			String whereQ = DBConstants.TXN_BILL_SCNCD + "=?";
			activityHandler.setWhereQuery(whereQ);
			activityHandler.setTxnHoldUpdate(true); // Hold Type update
			activityHandler.setTxnHoldRetrieve(txnType);// True:Hold,
														// False:Billing
			// Start AsyncTask
			getResult(activityHandler);
		}
	}

	/**
	 * Method- To update other columns which are changed, after addition and
	 * deletion of records
	 * 
	 * @param model
	 */
	public void updateBillMStDetails(BILL_Mst_Model model) {
		Logger.d(TAG, "updateBillMStDetails");
		mIsContinueProgressbar = true;
		if (model != null) {
			ActivityHandler activityHandler = new BILL_MST_DBHandler(this,
					model, DBConstants.dbo_BILL_MST_TABLE,
					DBOperationlParameter.UPDATE,
					Constants.UPDATE_BILL_MST_RECORD, false);
			// Start AsyncTask
			getResult(activityHandler);
		}
	}

	/**
	 * Method- To retrieve all held transaction records from bill mst table
	 * 
	 * @param result
	 */
	public void retrieveHeldTransData() {
		Logger.d(TAG, "retrieveHeldTransData");
		// get all held transaction type data
		selectBillMstRecords(Constants.HELD_TXN,
				DBConstants.dbo_BILL_MST_TABLE, true);
	}

	/**
	 * Method- To insert Held Txn deletion record to table
	 * 
	 * @param result
	 */
	public void saveHeldDeletionTxnDB(
			ArrayList<HeldTransactionModel> heldTxnModels) {
		Logger.d(TAG, "saveHeldDeletionTxnDB");
		mIsContinueProgressbar = true;
		if (heldTxnModels != null && heldTxnModels.size() > 0) {
			ActivityHandler activityHandler = new Held_Txn_DBHandler(this,
					heldTxnModels, DBConstants.dbo_HELD_TXN,
					DBOperationlParameter.INSERT, Constants.INSERT_HELD_TXN,
					false);
			// Start AsyncTask
			getResult(activityHandler);
		}
	}

	/**
	 * Method- To remove Bill MST record from table
	 * 
	 * @param transactionNo
	 * 
	 * @param result
	 */
	public void removePrdctrecordFromBillMstDB(String args) {
		Logger.d(TAG, "removePrdctrecordFromBillMstDB");
		mIsContinueProgressbar = true;
		String[] whrArgs = { args };
		ActivityHandler activityHandler = new BILL_MST_DBHandler(this, whrArgs,
				DBConstants.dbo_BILL_MST_TABLE, DBOperationlParameter.DELETE,
				Constants.DELETE_BILL_MST_RECORDS, false);
		// Start AsyncTask
		getResult(activityHandler);
	}

	/**
	 * Method- To remove Bill TXN record from table
	 * 
	 * @param transactionNo
	 * 
	 * @param result
	 */
	public void removePrdctrecordFromBillTxnDB(String args) {
		Logger.d(TAG, "removePrdctrecordFromBillTxnDB");
		mIsContinueProgressbar = true;
		String[] whrArgs = { args };
		ActivityHandler activityHandler = new BIllTxnDBHandler(this, whrArgs,
				DBConstants.dbo_BILL_TXN_TABLE, DBOperationlParameter.DELETE,
				Constants.DELETE_BILL_TXN_RECORDS, false);
		// Start AsyncTask
		getResult(activityHandler);
	}

	/**
	 * Method- To remove Bill INSTRCTN record from table
	 * 
	 * @param transactionNo
	 * 
	 * @param result
	 */
	public void removePrdctrecordFromBillInstructionDB(String args) {
		Logger.d(TAG, "removePrdctrecordFromBillInstructionDB");
		mIsContinueProgressbar = true;
		String[] whrArgs = { args };
		ActivityHandler activityHandler = new BillInstrctnTxnDBHandler(this,
				whrArgs, DBConstants.dbo_BILL_INSTRCTN_TXN_TABLE,
				DBOperationlParameter.DELETE,
				Constants.DELETE_BILL_INSTRCTN_TXN_RECORDS, false);
		// Start AsyncTask
		getResult(activityHandler);
	}

	/**
	 * Method- To remove Bill Consumption record from table
	 * 
	 * @param transactionNo
	 * 
	 * @param result
	 */
	public void removePrdctrecordFromConsumptionDB(String args) {
		Logger.d(TAG, "removePrdctrecordFromConsumptionDB");
		mIsContinueProgressbar = true;
		String[] whrArgs = { args };
		ActivityHandler activityHandler = new PRDCT_CNSMPTN_DBHandler(this,
				whrArgs, DBConstants.dbo_CNSMPTN_TXN,
				DBOperationlParameter.DELETE,
				Constants.DELETE_CONSUMPTION_TXN_RECORDS, false);
		// Start AsyncTask
		getResult(activityHandler);
	}

	/**
	 * Method- To insert Bill record to table
	 * 
	 * @param result
	 */
	public void savePrdctDataInBillTXNDB(BillTransactionModel model) {
		Logger.d(TAG, "savePrdctDataInBillTXNDB");
		mIsContinueProgressbar = true;
		if (model != null) {
			ActivityHandler activityHandler = new BIllTxnDBHandler(this, model,
					DBConstants.dbo_BILL_TXN_TABLE,
					DBOperationlParameter.INSERT,
					Constants.INSERT_BILL_TXN_RECORDS, false);
			// Start AsyncTask
			getResult(activityHandler);
		}
	}

	/**
	 * Method- To update Bill TXN record to table
	 * 
	 * @param result
	 */
	public void updatePrdctDataInBillTXNDB(BillTransactionModel model) {
		Logger.d(TAG, "updatePrdctDataInBillTXNDB");
		mIsContinueProgressbar = true;
		if (model != null) {
			ActivityHandler activityHandler = new BIllTxnDBHandler(this, model,
					DBConstants.dbo_BILL_TXN_TABLE,
					DBOperationlParameter.UPDATE,
					Constants.UPDATE_BILL_TXN_RECORDS, false);
			// Start AsyncTask
			getResult(activityHandler);
		}
	}
	
	/**
	 * Method- To update Bill TXN record for any modifier and measure column to table for print
	 * 
	 * @param result
	 */
	public void updatePrintDescrInBillTXNDB(BillTransactionModel model) {
		Logger.d(TAG, "updatePrintDescrInBillTXNDB");
		mIsContinueProgressbar = true;
		if (model != null) {
			ActivityHandler activityHandler = new BIllTxnDBHandler(this, model,
					DBConstants.dbo_BILL_TXN_TABLE,
					DBOperationlParameter.UPDATE,
					Constants.UPDATE_BILL_TXN_RECORDS, false);
			// Start AsyncTask
			getResult(activityHandler);
		}
	}

	/**
	 * Method- To update consumption record to table
	 * 
	 * @param result
	 */
	public void updatePrdctDataInCnsmptnTXNDB(PRDCT_CNSMPTN_TXNModel updateModel) {
		Logger.d(TAG, "updatePrdctDataInCnsmptnTXNDB");
		mIsContinueProgressbar = true;
		if (updateModel != null) {
			ActivityHandler activityHandler = new PRDCT_CNSMPTN_DBHandler(this,
					updateModel, DBConstants.dbo_CNSMPTN_TXN,
					DBOperationlParameter.UPDATE,
					Constants.UPDATE_CNSMPTN_TXN_RECORDS, false);
			// Start AsyncTask
			getResult(activityHandler);
		}
	}

	/**
	 * Method- To retrieve particular record from cnsmptn table
	 * 
	 * @param result
	 */
	public void retrieveCnsmptnData(BillTransactionModel model) {
		Logger.d(TAG, "retrieveCnsmptnData");
		mIsContinueProgressbar = true;
		if (model != null) {
			ActivityHandler activityHandler = new PRDCT_CNSMPTN_DBHandler(this,
					model, DBConstants.dbo_CNSMPTN_TXN,
					DBOperationlParameter.DEFAULT_VEHICLE,
					Constants.FETCH_SINGLE_CNSMPTN_RECORD, false);
			// Start AsyncTask
			getResult(activityHandler);
		}
	}

	/**
	 * Method- To retrieve particular record from bill instruction table
	 * 
	 * @param result
	 */
	public void retrieveBillInstrctnData(BillTransactionModel model) {
		Logger.d(TAG, "retrieveBillInstrctnData");
		mIsContinueProgressbar = true;
		if (model != null) {
			ActivityHandler activityHandler = new BillInstrctnTxnDBHandler(
					this, model, DBConstants.dbo_BILL_INSTRCTN_TXN_TABLE,
					DBOperationlParameter.DEFAULT_VEHICLE,
					Constants.FETCH_SINGLE_BILL_INSTRCTN_TXN_RECORD, false);
			// Start AsyncTask
			getResult(activityHandler);
		}
	}

	// /**
	// * Method- To retrieve all records from bill instruction table
	// *
	// * @param result
	// */
	// public void fetchBillInstrctnData(String txnID) {
	// Logger.d(TAG, "retrieveBillInstrctnData");
	// mIsContinueProgressbar = true;
	//
	// String[] queryParams = { txnID };
	//
	// BillInstrctnTxnDBHandler activityHandler = new
	// BillInstrctnTxnDBHandler(this,
	// queryParams, DBConstants.dbo_BILL_INSTRCTN_TXN_TABLE,
	// DBOperationlParameter.SELECT,
	// Constants.FETCH_BILL_INSTRCTN_TXN_RECORD, false);
	// activityHandler.setWhereQuery(DBConstants.BILL_INSTRCTN_TXN_BILL_SCND);
	// // Start AsyncTask
	// getResult(activityHandler);
	// }
	/**
	 * Method- To update particular record from Bill instruction table
	 * 
	 * @param result
	 */
	public void updateBillInstrctnData(Bill_INSTRCTN_TXN_Model updateModel) {
		Logger.d(TAG, "updatePrdctDataInCnsmptnTXNDB");
		mIsContinueProgressbar = true;
		if (updateModel != null) {
			ActivityHandler activityHandler = new BillInstrctnTxnDBHandler(
					this, updateModel, DBConstants.dbo_BILL_INSTRCTN_TXN_TABLE,
					DBOperationlParameter.UPDATE,
					Constants.UPDATE_BILL_INSTRCTN_TXN_RECORDS, false);
			// Start AsyncTask
			getResult(activityHandler);
		}
	}

	/**
	 * Method- To insert Bill INSTRCTN TXN record to table
	 * 
	 * @param result
	 */
	public void saveInBill_INSTRCTN_TXNDB(
			Bill_INSTRCTN_TXN_Model mBill_INSTRCTN_Txn_Model) {
		Logger.d(TAG, "saveInBill_INSTRCTN_TXNDB");
		mIsContinueProgressbar = true;
		if (mBill_INSTRCTN_Txn_Model != null) {
			ActivityHandler activityHandler = new BillInstrctnTxnDBHandler(
					this, mBill_INSTRCTN_Txn_Model,
					DBConstants.dbo_BILL_INSTRCTN_TXN_TABLE,
					DBOperationlParameter.INSERT,
					Constants.INSERT_BILL_INSTRCTN_TXN_RECORDS, false);
			// Start AsyncTask
			getResult(activityHandler);
		}
	}

	/**
	 * Method- To insert Consumption TXN record to table
	 * 
	 * @param result
	 */
	public void saveIn_PRDCT_CNSMPTN_TXNDB(
			ArrayList<PRDCT_CNSMPTN_TXNModel> cnsmptn_Txn_Models) {
		Logger.d(TAG, "saveIn_PRDCT_CNSMPTN_TXNDB");
		mIsContinueProgressbar = true;
		if (cnsmptn_Txn_Models != null && cnsmptn_Txn_Models.size() > 0) {

			ActivityHandler activityHandler = new PRDCT_CNSMPTN_DBHandler(this,
					cnsmptn_Txn_Models, DBConstants.dbo_CNSMPTN_TXN,
					DBOperationlParameter.INSERT,
					Constants.INSERT_CNSMPTN_RECORD, false);
			// Start AsyncTask
			getResult(activityHandler);
		}
	}

	/**
	 * Method- To insert Pty Float MST record to table
	 * 
	 * @param result
	 */
	public void saveIn_PTY_FLT_MSTDB(Petty_Float_Model pfMSt_Model) {
		Logger.d(TAG, "saveIn_PTY_FLT_MSTDB");
		mIsContinueProgressbar = true;
		if (pfMSt_Model != null) {
			ActivityHandler activityHandler = new PTY_FLT_MST_DBHandler(this,
					pfMSt_Model, DBConstants.dbo_PTY_FLT_MST_TABLE,
					DBOperationlParameter.INSERT,
					Constants.INSERT_PTY_FLT_MST_RECORD, false);
			// Start AsyncTask
			getResult(activityHandler);
		}
	}
	
	/**
	 * Method- To select Pty Float MST record from table
	 * 
	 * @param result
	 */
	public void selectFPPRecords(String[] queryParams, String tableName,
			boolean showProgress) {
		Logger.d(TAG, "selectFPPRecords");
		mIsContinueProgressbar = showProgress;
		
		PTY_FLT_MST_DBHandler activityHandler = new PTY_FLT_MST_DBHandler(this,
				queryParams, tableName, DBOperationlParameter.SELECT,
				Constants.FETCH_PTY_FLT_MST_RECORD, false);
		
		String whrQuery = DBConstants.PF_TXN_NO + "=?";
		
		activityHandler.setWhereQuery(whrQuery);
		
		// Start AsyncTask
		getResult(activityHandler);
	}
	

	/**
	 * Method- To insert Multi Pty Float MST record to table
	 * 
	 * @param result
	 */
	public void saveIn_MULTI_PTY_FLT_MSTDB(
			MULTI_PTY_FLT_MST_Model multi_PTY_FLT_MST_Model) {
		Logger.d(TAG, "saveIn_MULTI_PTY_FLT_MSTDB");
		mIsContinueProgressbar = true;
		if (multi_PTY_FLT_MST_Model != null) {
			ActivityHandler activityHandler = new Multi_Pty_Flt_MST_DBHandler(
					this, multi_PTY_FLT_MST_Model,
					DBConstants.dbo_MULI_PTY_FLT_MST_TABLE,
					DBOperationlParameter.INSERT,
					Constants.INSERT_MULTI_PTY_FLT_MST_RECORD, false);
			// Start AsyncTask
			getResult(activityHandler);
		}
	}

	/**
	 * Method- To select Bill MST record data
	 * 
	 * @param classLevelSelectionArg
	 * @param tableName
	 * @param showProgress
	 */
	public void selectBillMstRecords(String classLevelSelectionArg,
			String tableName, boolean showProgress) {
		Logger.d(TAG, "selectBillMstRecords");
		mIsContinueProgressbar = showProgress;

		String[] queryParams = { classLevelSelectionArg };

		ActivityHandler activityHandler = new BILL_MST_DBHandler(this,
				queryParams, tableName, DBOperationlParameter.SELECT,
				Constants.FETCH_BILL_MST_RECORD, false);
		// Start AsyncTask
		getResult(activityHandler);
	}

	/**
	 * Method- To delete Held Bill MST record data
	 * 
	 * @param classLevelSelectionArg
	 * @param tableName
	 * @param showProgress
	 */
	public void deleteBillMstRecord(String classLevelSelectionArg,
			String tableName, boolean showProgress) {
		Logger.d(TAG, "deleteBillMstRecord");
		mIsContinueProgressbar = showProgress;

		String[] queryParams = { classLevelSelectionArg };

		ActivityHandler activityHandler = new BILL_MST_DBHandler(this,
				queryParams, tableName, DBOperationlParameter.DELETE,
				Constants.DELETE_BILL_MST_HELD_RECORD, false);
		// Start AsyncTask
		getResult(activityHandler);
	}

	/**
	 * Method- To select Bill TXN Non Void record data
	 * 
	 * @param classLevelSelectionArg
	 * @param tableName
	 * @param showProgress
	 */
	public void selectBillTXNRecords(String[] selectionArg, String tableName,
			boolean showProgress) {
		Logger.d(TAG, "selectBillTXNRecords");
		mIsContinueProgressbar = showProgress;

		ActivityHandler activityHandler = new BIllTxnDBHandler(this,
				selectionArg, tableName, DBOperationlParameter.SELECT,
				Constants.FETCH_BILL_TXN_RECORD, false);
		// Start AsyncTask
		getResult(activityHandler);
	}

	/**
	 * Method- To select Bill TXN All record data
	 * 
	 * @param classLevelSelectionArg
	 * @param tableName
	 * @param showProgress
	 * @param isHeldTxnUpload
	 */
	public void selectBillTXNAllRecords(String[] selectionArg,
			String tableName, boolean showProgress) {
		Logger.d(TAG, "selectBillTXNAllRecords");
		mIsContinueProgressbar = showProgress;

		BIllTxnDBHandler activityHandler = new BIllTxnDBHandler(this,
				selectionArg, tableName, DBOperationlParameter.SELECT,
				Constants.FETCH_BILL_TXN_RECORD, false);
		// activityHandler.setTxnHoldUpdate(true);
		String whereQ = DBConstants.TXN_BILL_SCNCD + "=?";
		activityHandler.setWhereQuery(whereQ);
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

	public void selectBillInstrc_TXNRecords(String[] billInstTxnWhrArgs,
			String tableName, boolean showProgress, String whereQuery) {

		Logger.d(TAG, "selectBillInstrc_TXNRecords");
		mIsContinueProgressbar = showProgress;

		// String[] queryParams = { classLevelSelectionArg };

		BillInstrctnTxnDBHandler activityHandler = new BillInstrctnTxnDBHandler(
				this, billInstTxnWhrArgs, tableName,
				DBOperationlParameter.SELECT,
				Constants.FETCH_BILL_INSTRCTN_TXN_RECORD, false);
		activityHandler.setWhereQuery(whereQuery);
		// Start AsyncTask
		getResult(activityHandler);
	}

	@Override
	protected void responseHandle(OperationalResult result) {

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

		switch (result.getResultCode()) { // DB scenario handling

		case OperationalResult.MASTER_ERROR:
			// Common error for Master
			Logger.d(TAG, "MASTER_ERROR");
			this.mBaseResponseListener.errorReceived(result);
			break;

		case OperationalResult.DB_ERROR:
			// DB error for table
			Logger.d(TAG, "DB_ERROR");
			this.mBaseResponseListener.errorReceived(result);
			break;

		/** UOM_SLAB Transactions **/
		case Constants.FETCH_UOM_SLAB_RECORD:
			this.mBaseResponseListener.executePostAction(result);
			break;

		/** UOM Transactions **/
		case Constants.FETCH_UOM_RECORD:
			this.mBaseResponseListener.executePostAction(result);
			break;

		/** Modifier Transaction **/
		case Constants.FETCH_MODIFIER_RECORD:
			this.mBaseResponseListener.executePostAction(result);
			break;

		/** Prdct modifier Code **/
		case Constants.FETCH_PRDCT_INSTRC_CODE_RECORD:
			this.mBaseResponseListener.executePostAction(result);
			break;

		/** Vat Transaction **/
		case Constants.FETCH_VAT_RECORD:
			this.mBaseResponseListener.executePostAction(result);
			break;

		/** Bill_MST Insert **/
		case Constants.INSERT_BILL_MST_RECORDS:
			this.mBaseResponseListener.executePostAction(result);
			// get all billing transaction type data
			selectBillMstRecords(Constants.BILLING_TXN,
					DBConstants.dbo_BILL_MST_TABLE, true);
			break;

		case Constants.FETCH_BILL_MST_RECORD:
			this.mBaseResponseListener.executePostAction(result);
			break;

		case Constants.DELETE_BILL_MST_RECORDS:
			this.mBaseResponseListener.executePostAction(result);
			break;

		case Constants.DELETE_BILL_MST_HELD_RECORD:
			this.mBaseResponseListener.executePostAction(result);
			break;

		case Constants.UPDATE_BILL_MST_TXN_TYPE_RECORD:
			this.mBaseResponseListener.executePostAction(result);
			break;

		case Constants.GET_BILL_MST_COUNT:
			this.mBaseResponseListener.executePostAction(result);
			break;

		case Constants.UPDATE_BILL_MST_RECORD:
			this.mBaseResponseListener.executePostAction(result);
			break;

		/** Bill_TXN Insert **/
		case Constants.INSERT_BILL_TXN_RECORDS:
			this.mBaseResponseListener.executePostAction(result);
			// retrieve only non-voided items and for current transaction no.
			getLastInsertedRowInBillTxn();
			break;

		case Constants.FETCH_BILL_TXN_RECORD:
			this.mBaseResponseListener.executePostAction(result);
			break;

		case Constants.UPDATE_BILL_TXN_RECORDS:
			this.mBaseResponseListener.executePostAction(result);
			break;

		case Constants.UPDATE_BILL_TXN_TYPE_RECORD:
			this.mBaseResponseListener.executePostAction(result);
			break;

		case Constants.DELETE_BILL_TXN_RECORDS:
			this.mBaseResponseListener.executePostAction(result);
			break;

		/** Bill Instruction TXN **/
		case Constants.INSERT_BILL_INSTRCTN_TXN_RECORDS:
			this.mBaseResponseListener.executePostAction(result);
			selectBillInstrc_TXNRecords(null,
					DBConstants.dbo_BILL_INSTRCTN_TXN_TABLE, true, null);
			break;

		case Constants.FETCH_BILL_INSTRCTN_TXN_RECORD:
			this.mBaseResponseListener.executePostAction(result);
			break;

		case Constants.FETCH_SINGLE_BILL_INSTRCTN_TXN_RECORD:
			this.mBaseResponseListener.executePostAction(result);
			break;

		case Constants.DELETE_BILL_INSTRCTN_TXN_RECORDS:
			this.mBaseResponseListener.executePostAction(result);
			break;

		/** RECIPE DTL **/
		case Constants.FETCH_INGREDIENT_RECORD:
			this.mBaseResponseListener.executePostAction(result);
			break;

		/** CNSMPTN Records **/
		case Constants.INSERT_CNSMPTN_RECORD:
			this.mBaseResponseListener.executePostAction(result);
			break;

		case Constants.FETCH_SINGLE_CNSMPTN_RECORD:
			this.mBaseResponseListener.executePostAction(result);
			break;

		case Constants.UPDATE_CNSMPTN_TXN_RECORDS:
			this.mBaseResponseListener.executePostAction(result);
			break;

		case Constants.DELETE_CONSUMPTION_TXN_RECORDS:
			this.mBaseResponseListener.executePostAction(result);
			break;

		/** PTY_FLT_MST **/
		case Constants.INSERT_PTY_FLT_MST_RECORD:
			this.mBaseResponseListener.executePostAction(result);
			break;

		case Constants.INSERT_MULTI_PTY_FLT_MST_RECORD:
			this.mBaseResponseListener.executePostAction(result);
			break;

		/** Held TXN **/
		case Constants.INSERT_HELD_TXN:
			this.mBaseResponseListener.executePostAction(result);
			break;

		default: // Pass UI updation response back to activity
			this.mBaseResponseListener.executePostAction(result);
			break;
		}
	}

	private void getLastInsertedRowInBillTxn() {
		Logger.d(TAG, "getLastInsertedRowInBillTxn");
		mIsContinueProgressbar = true;

		BIllTxnDBHandler activityHandler = new BIllTxnDBHandler(this, null,
				DBConstants.dbo_BILL_TXN_TABLE, DBOperationlParameter.SELECT,
				Constants.FETCH_MAX_ID_BILL_TXN_RECORD, false);
		activityHandler.setWhereQuery(DBConstants.ROW_ID);
		// Start AsyncTask
		getResult(activityHandler);
	}

	public void saveInFloatPettyCurrencyMSTDB(
			ArrayList<FloatPickupPettyCurrencyModel> model) {
		mIsContinueProgressbar = true;
		if (model != null && model.size() > 0) {
			mIsContinueProgressbar = true;
			ActivityHandler activityHandler = new FloatPettyPickupCurrencyDBHandler(
					this, model, DBConstants.dbo_FLOAT_PYMNTS_MST_TABLE,
					DBOperationlParameter.INSERT,
					Constants.INSERT_FLOAT_PMT_RECORDS, false);
			// Start AsyncTask
			getResult(activityHandler);
		}
	}

	public void retrieveAllFloatCurrencies(String[] whreArgs,
			String tableName, boolean showProgress, String whereQuery) {

		Logger.d(TAG, "retrieveAllFloatCurrencies");
		mIsContinueProgressbar = showProgress;

		// String[] queryParams = { classLevelSelectionArg };

		FloatPettyPickupCurrencyDBHandler activityHandler = new FloatPettyPickupCurrencyDBHandler(
				this, whreArgs, tableName,
				DBOperationlParameter.SELECT,
				Constants.FETCH_FLOAT_CURRENCT_RECORDS, false);
		activityHandler.setWhereQuery(whereQuery);
		// Start AsyncTask
		getResult(activityHandler);
	}

}
