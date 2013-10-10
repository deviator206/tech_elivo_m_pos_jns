/*
 * Copyright (C) 2013 USAP Pvt. Ltd. All Rights Reserved.
 * jaggarnaut-studios pvt. ltd. PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.mpos.master.DBhandler;

import java.util.ArrayList;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;

import com.mpos.framework.common.Constants;
import com.mpos.framework.common.DBConstants;
import com.mpos.framework.common.Logger;
import com.mpos.framework.common.OperationalResult;
import com.mpos.framework.handler.DBHandler;
import com.mpos.framework.inf.HandlerInf;
import com.mpos.master.model.BaseModel;
import com.mpos.master.model.Petty_Float_Model;

/**
 * Description-
 * 
 * @author jaggarnaut-studios
 */

public class PTY_FLT_MST_DBHandler extends DBHandler {

	public static final String TAG = Constants.APP_TAG
			+ PTY_FLT_MST_DBHandler.class.getSimpleName();

	public PTY_FLT_MST_DBHandler(HandlerInf handlerInf, String[] dbQueryParam,
			String tableName, int dBOperation, int resultCode,
			boolean isUpdateDB) {
		super(handlerInf, dbQueryParam, tableName, dBOperation, resultCode,
				isUpdateDB);

	}

	public PTY_FLT_MST_DBHandler(HandlerInf handlerInf, Object model,
			String tableName, int dBOperation, int resultCode,
			boolean isUpdateDB) {
		super(handlerInf, model, tableName, dBOperation, resultCode, isUpdateDB);
	}

	private String whereQuery = null;

	public void setWhereQuery(String whereQuery) {
		this.whereQuery = whereQuery;
	}

	@Override
	protected void getDatafromDataBase() {
		Logger.d(TAG, "getDatafromDataBase");
		Cursor cursor = null;

		cursor = getPFMSTDetails(mOperationalResult.getDBOperationalParam()
				.getQueryParam());

		if (cursor != null && cursor.getCount() > 0) {

			Bundle bundle = new Bundle();
			ArrayList<BaseModel> pfModelArr = parseCursor(cursor);
			Logger.d(TAG, "getDatafromDataBase:" + pfModelArr.toString());

			bundle.putSerializable(Constants.RESULTCODEBEAN, pfModelArr);
			this.mOperationalResult.setResult(bundle);
		} else {
			this.mOperationalResult
					.setRequestResponseCode(OperationalResult.DB_ERROR);
		}

		cursor.close();

		this.mOperationalResult.setOperationalCode(Constants.SELECT);
	}

	/**
	 * Method- To retrieve data
	 * 
	 * @param selectionArgs
	 * 
	 * @return
	 */
	private Cursor getPFMSTDetails(String[] queryParam) {
		Logger.d(TAG, "getPFMSTDetails");
		Cursor c = null;

		if (whereQuery == null) {// Normal case
			c = mSQLiteDB.query(mOperationalResult.getTableName(), null, null,
					null, null, null, null);
		} else {// For offline FLT upload
			c = mSQLiteDB.query(mOperationalResult.getTableName(), null,
					whereQuery, queryParam, null, null, null);
		}
		return c;
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
					.setRequestResponseCode(OperationalResult.MASTER_ERROR);
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
		Petty_Float_Model dataModel = (Petty_Float_Model) model;

		ContentValues initValues = Petty_Float_Model
				.getcontentvalues(dataModel);

		long row = mSQLiteDB.insert(mOperationalResult.getTableName(), null,
				initValues);

		return row;
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

	/**
	 * Method- To get respective column name index
	 * 
	 * @param cursor
	 * @return
	 * 
	 */
	private ArrayList<BaseModel> parseCursor(Cursor cursor) {
		Logger.d(TAG, "parseCursor");
		int indexPfAMt = cursor.getColumnIndex(DBConstants.PF_AMT);
		int indexPfLocAmt = cursor.getColumnIndex(DBConstants.PF_LOC_AMT);
		int indexPfCurrAbbre = cursor.getColumnIndex(DBConstants.PF_CURR_ABBRE);
		int indexPfExRate = cursor.getColumnIndex(DBConstants.PF_EX_RATE);
		int indexPfOperator = cursor.getColumnIndex(DBConstants.PF_OPRATOR);
		int indexPfBrnCode = cursor.getColumnIndex(DBConstants.PF_BRNCH_CODE);
		int indexPfCmpCode = cursor.getColumnIndex(DBConstants.PF_CMPNY_CODE);
		int indexPfDtls = cursor.getColumnIndex(DBConstants.PF_DTLS);
		int indexPfRunDate = cursor.getColumnIndex(DBConstants.PF_RUN_DATE);
		int indexPfSysDate = cursor.getColumnIndex(DBConstants.PF_SYS_DATE);
		int indexPfTillNo = cursor.getColumnIndex(DBConstants.PF_TILL_NO);
		int indexPfTxnNo = cursor.getColumnIndex(DBConstants.PF_TXN_NO);
		int indexPfType = cursor.getColumnIndex(DBConstants.PF_TYPE);
		int indexPfUSerNm = cursor.getColumnIndex(DBConstants.PF_USERNM);
		int indexPfUseScnd = cursor.getColumnIndex(DBConstants.PF_USR_NM_SCND);
		int indexPfSrNo = cursor.getColumnIndex(DBConstants.PTY_SR_NO);

		ArrayList<BaseModel> pfModelArr = new ArrayList<BaseModel>();
		if (cursor.moveToFirst()) {
			do {
				Petty_Float_Model pfModel = new Petty_Float_Model();
				pfModel.setBranch_code(cursor.getString(indexPfBrnCode));
				pfModel.setCmpny_code(cursor.getString(indexPfCmpCode));
				pfModel.setPty_flt_amt(Double.parseDouble(cursor
						.getString(indexPfAMt)));
				pfModel.setPty_flt_loc_amt(Double.parseDouble(cursor
						.getString(indexPfLocAmt)));
				pfModel.setPty_flt_exRate(Double.parseDouble(cursor
						.getString(indexPfExRate)));
				pfModel.setPty_flt_curr_Abbr(cursor.getString(indexPfCurrAbbre));
				pfModel.setPty_flt_operator(cursor.getString(indexPfOperator));

				pfModel.setPty_flt_dtls(cursor.getString(indexPfDtls));
				pfModel.setPty_flt_run_date(cursor.getString(indexPfRunDate));
				pfModel.setPty_flt_type(cursor.getString(indexPfType));
				pfModel.setPty_sr_no(Integer.parseInt(cursor
						.getString(indexPfSrNo)));
				pfModel.setPty_sys_date(cursor.getString(indexPfSysDate));
				pfModel.setTill_no(cursor.getString(indexPfTillNo));
				pfModel.setTransca_no(cursor.getString(indexPfTxnNo));
				pfModel.setUser_name(cursor.getString(indexPfUSerNm));
				pfModel.setUser_name_scnd(cursor.getString(indexPfUseScnd));

				pfModelArr.add(pfModel);
			} while (cursor.moveToNext());
		}
		return pfModelArr;
	}

}
