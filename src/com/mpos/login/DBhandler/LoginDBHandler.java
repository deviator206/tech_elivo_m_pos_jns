/*
 * Copyright (C) 2013  All Rights Reserved.
 *  PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mpos.login.DBhandler;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import com.mpos.framework.common.Constants;
import com.mpos.framework.common.DBConstants;
import com.mpos.framework.common.Logger;
import com.mpos.framework.common.OperationalResult;
import com.mpos.framework.handler.DBHandler;
import com.mpos.framework.inf.HandlerInf;
import com.mpos.master.model.PRDCT_MST_Model;
/**
 * Description-
 * 

 */
public class LoginDBHandler extends DBHandler {

	public static final String TAG = Constants.APP_TAG
			+ LoginDBHandler.class.getSimpleName();
	
	public LoginDBHandler(HandlerInf handlerInf, String[] dbQueryParam,
			String tableName,int dBOperation, int resultCode,boolean isUpdate) {
		super(handlerInf, dbQueryParam, tableName, dBOperation, resultCode,isUpdate);
	}

	public LoginDBHandler(HandlerInf handlerInf, Object model, String tableName, int dBOperation,
			int resultCode,boolean isUpdate) {
		super(handlerInf, model, tableName, dBOperation, resultCode,isUpdate);
	}

	@Override
	protected void getDatafromDataBase() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void updateDataIntoDatabase() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void deleteDatafromDatabse() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void insertDataIntoDatabase() {
		long lRowAffected = 0; // default

		lRowAffected = insertInfo(this.mOperationalResult.getdBModel());

		this.mOperationalResult.setOperationalCode(Constants.INSERT);

		if (lRowAffected > 0) {
			this.mOperationalResult
					.setRequestResponseCode(OperationalResult.SUCCESS);
		} else {
			this.mOperationalResult
					.setRequestResponseCode(OperationalResult.DB_ERROR);
		}
	}

	/**
	 * Method- Insert Info info in Table
	 * 
	 * @param object
	 * @return long rowAffected
	 */
	private long insertInfo(Object model) {
		Logger.d(TAG, "insertInfo:" + model.toString());

		PRDCT_MST_Model dataModel = (PRDCT_MST_Model) model;

		ContentValues initValues = new ContentValues();

		initValues.put(DBConstants.PRDCT_ANLYS_CODE,dataModel.getPRDCT_ANLYS_CODE());
		initValues.put(DBConstants.PRDCT_BIN_LOC_CODE,dataModel.getPRDCT_BIN_LOC_CODE());
		initValues.put(DBConstants.PRDCT_CLR, dataModel.getPRDCT_CLR());
		initValues.put(DBConstants.PRDCT_CODE, dataModel.getPRDCT_CODE());
		initValues.put(DBConstants.PRDCT_COST_PRCE,	dataModel.getPRDCT_COST_PRCE());
		initValues.put(DBConstants.PRDCT_CP_VLTN, dataModel.getPRDCT_CP_VLTN());
		initValues.put(DBConstants.PRDCT_DPT_CODE,dataModel.getPRDCT_DPT_CODE());
		initValues.put(DBConstants.PRDCT_DSCRPTN, dataModel.getPRDCT_DSCRPTN());
		initValues.put(DBConstants.PRDCT_FIX_QTY,dataModel.getPRDCT_FIX_QTY());
		initValues.put(DBConstants.PRDCT_FIXED_PRCE,dataModel.getPRDCT_FIXED_PRCE());
		initValues.put(DBConstants.PRDCT_GRP_CODE,dataModel.getPRDCT_GRP_CODE());
		initValues.put(DBConstants.PRDCT_IMG, dataModel.getPRDCT_IMG());
		initValues.put(DBConstants.PRDCT_LNG_DSCRPTN,dataModel.getPRDCT_LNG_DSCRPTN());
		initValues.put(DBConstants.PRDCT_MIN_QTY, dataModel.getPRDCT_MIN_QTY());
		initValues.put(DBConstants.PRDCT_PCKNG, dataModel.getPRDCT_PCKNG());
		initValues.put(DBConstants.PRDCT_QTY, dataModel.getPRDCT_QTY());
		initValues.put(DBConstants.PRDCT_SELL_PRCE,dataModel.getPRDCT_SELL_PRCE());
		initValues.put(DBConstants.PRDCT_SHRT_DSCRPTN,dataModel.getPRDCT_SHRT_DSCRPTN());
		initValues.put(DBConstants.PRDCT_SPPLR, dataModel.getPRDCT_SPPLR());
		initValues.put(DBConstants.PRDCT_SUB_CLASS,dataModel.getPRDCT_SUB_CLASS());
		initValues.put(DBConstants.PRDCT_UNIT, dataModel.getPRDCT_UNIT());
		initValues.put(DBConstants.PRDCT_VAT_CODE,dataModel.getPRDCT_VAT_CODE());

		long row =mSQLiteDB.insertWithOnConflict(mOperationalResult.getTableName(), BaseColumns._ID, initValues, SQLiteDatabase.CONFLICT_REPLACE);
		
		return row;
		/*return mSQLiteDB.insert(mOperationalResult.getTableName(), null,
				initValues);*/
	}

	private long insertBIllInfo(Object model) {
		return mQuery;
		/*
		 * initValues.put(DBConstants.AMNT_PAID, dataModel.getDISP_ORDER_NO());
		 * initValues.put(DBConstants.BILL_AMENED, object[1]);
		 * initValues.put(DBConstants.BILL_AMND_STATUS, object[2]);
		 * initValues.put(DBConstants.BILL_AMNT, object[3]);
		 * initValues.put(DBConstants.BILL_LOCKED, object[4]);
		 * initValues.put(DBConstants.BILL_NO, object[5]);
		 * initValues.put(DBConstants.BILL_RUN_DATE, object[6]);
		 * 
		 * initValues.put(DBConstants.BILL_SCNCD, object[7]);
		 * initValues.put(DBConstants.BILL_SCND, object[8]);
		 * initValues.put(DBConstants.BILL_STATUS, object[9]);
		 * initValues.put(DBConstants.BILL_SYS_DATE, object[10]);
		 * initValues.put(DBConstants.BILL_VAT_AMNT, object[11]);
		 * 
		 * initValues.put(DBConstants.BILL_VAT_EXMPT, object[12]);
		 * initValues.put(DBConstants.BRNCH_CODE, object[13]);
		 * initValues.put(DBConstants.CARDNO, object[14]);
		 * 
		 * initValues.put(DBConstants.CHNG_GVN, object[12]);
		 * initValues.put(DBConstants.CMPNY_CODE, object[13]);
		 */
	}

	@Override
	protected void isDataAvailableIntoDatabase() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void getDBRecordCount() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void getDefaultDBVehicle() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void clearCache() {
		// TODO Auto-generated method stub

	}

}
