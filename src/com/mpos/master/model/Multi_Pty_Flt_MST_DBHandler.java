/*
 * Copyright (C) 2013 USAP Pvt. Ltd. All Rights Reserved.
 * jaggarnaut-studios pvt. ltd. PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.mpos.master.model;

import java.util.ArrayList;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.BaseColumns;

import com.mpos.framework.common.Constants;
import com.mpos.framework.common.DBConstants;
import com.mpos.framework.common.Logger;
import com.mpos.framework.common.OperationalResult;
import com.mpos.framework.handler.DBHandler;
import com.mpos.framework.inf.HandlerInf;

/**
 * Description-
 * 
 * @author jaggarnaut-studios
 */

public class Multi_Pty_Flt_MST_DBHandler extends DBHandler {

	public static final String TAG = Constants.APP_TAG
			+ Multi_Pty_Flt_MST_DBHandler.class.getSimpleName();

	public Multi_Pty_Flt_MST_DBHandler(HandlerInf handlerInf,
			String[] dbQueryParam, String tableName, int dBOperation,
			int resultCode, boolean isUpdateDB) {
		super(handlerInf, dbQueryParam, tableName, dBOperation, resultCode,
				isUpdateDB);

	}

	public Multi_Pty_Flt_MST_DBHandler(HandlerInf handlerInf, Object model,
			String tableName, int dBOperation, int resultCode,
			boolean isUpdateDB) {
		super(handlerInf, model, tableName, dBOperation, resultCode, isUpdateDB);
	}

	@Override
	protected void getDatafromDataBase() {
		Logger.d(TAG, "getDatafromDataBase");
		Cursor cursor = null;

		cursor = getMPFMSTDetails(mOperationalResult.getDBOperationalParam()
				.getQueryParam());

		if (cursor.getCount() > 0) {

			Bundle bundle = new Bundle();
			ArrayList<BaseModel> mpfModelArr = parseCursor(cursor);
			Logger.d(TAG, "getDatafromDataBase:" + mpfModelArr.toString());

			bundle.putSerializable(Constants.RESULTCODEBEAN, mpfModelArr);
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
	private Cursor getMPFMSTDetails(String[] queryParam) {
		Logger.d(TAG, "getMPFMSTDetails");

		Cursor c = mSQLiteDB.query(mOperationalResult.getTableName(), null,
				null, null, null, null, null);
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
		MULTI_PTY_FLT_MST_Model dataModel = (MULTI_PTY_FLT_MST_Model) model;

		ContentValues initValues = MULTI_PTY_FLT_MST_Model
				.getcontentvalues(dataModel);

		/*long row = mSQLiteDB.insert(mOperationalResult.getTableName(), null,
				initValues);*/
		long row =mSQLiteDB.insertWithOnConflict(mOperationalResult.getTableName(), BaseColumns._ID, initValues, SQLiteDatabase.CONFLICT_REPLACE);
		
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

		int indexPfBrnCode = cursor.getColumnIndex(DBConstants.MPF_BRNCH_CODE);
		int indexPfCmpCode = cursor.getColumnIndex(DBConstants.MPF_CMPNY_CODE);
		int indexPfRunDate = cursor.getColumnIndex(DBConstants.MPF_RUN_DATE);
		int indexPfSysDate = cursor.getColumnIndex(DBConstants.MPF_SYS_DATE);
		int indexPfTillNo = cursor.getColumnIndex(DBConstants.MPF_TILL_NO);
		int indexPfTxnNo = cursor.getColumnIndex(DBConstants.MPF_TXN_NO);
		int indexPfType = cursor.getColumnIndex(DBConstants.MPF_TYPE);
		int indexPfUSerNm = cursor.getColumnIndex(DBConstants.MPF_USERNM);
		int indexPfUseScnd = cursor.getColumnIndex(DBConstants.MPF_USR_NM_SCND);
		int indexPfSrNo = cursor.getColumnIndex(DBConstants.MPTY_SR_NO);

		int indexPfCurAbbr = cursor
				.getColumnIndex(DBConstants.MPF_PTY_FLT_CUR_ABBR);
		int indexPfCurAmt = cursor
				.getColumnIndex(DBConstants.MPF_PTY_FLT_CUR_AMT);
		int indexPfExRt = cursor.getColumnIndex(DBConstants.MPF_PTY_FLT_EX_RT);
		int indexPfLocAMt = cursor
				.getColumnIndex(DBConstants.MPF_PTY_FLT_LOC_AMT);
		int indexPfOpetator = cursor
				.getColumnIndex(DBConstants.MPF_PTY_FLT_OPERATOR);

		ArrayList<BaseModel> mpfModelArr = new ArrayList<BaseModel>();
		if (cursor.moveToFirst()) {
			do {
				MULTI_PTY_FLT_MST_Model pfModel = new MULTI_PTY_FLT_MST_Model();
				pfModel.setBranch_code(cursor.getString(indexPfBrnCode));
				pfModel.setCmpny_code(cursor.getString(indexPfCmpCode));
				pfModel.setPty_flt_run_date(cursor.getString(indexPfRunDate));
				pfModel.setPty_flt_type(cursor.getString(indexPfType));
				pfModel.setPty_sr_no(Integer.parseInt(cursor
						.getString(indexPfSrNo)));
				pfModel.setPty_sys_date(cursor.getString(indexPfSysDate));
				pfModel.setTill_no(cursor.getString(indexPfTillNo));
				pfModel.setTransca_no(cursor.getString(indexPfTxnNo));
				pfModel.setUser_name(cursor.getString(indexPfUSerNm));
				pfModel.setUser_name_scnd(cursor.getString(indexPfUseScnd));

				pfModel.setCur_abbr(cursor.getString(indexPfCurAbbr));
				pfModel.setCurAmt(Double.parseDouble(cursor
						.getString(indexPfCurAmt)));
				pfModel.setEx_operator(cursor.getString(indexPfOpetator));
				pfModel.setEx_rate(Double.parseDouble(cursor
						.getString(indexPfExRt)));
				pfModel.setLoc_amt(Double.parseDouble(cursor
						.getString(indexPfLocAMt)));

				mpfModelArr.add(pfModel);
			} while (cursor.moveToNext());
		}
		return mpfModelArr;
	}

}
