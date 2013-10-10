/*
 * Copyright (C) 2013 USAP Pvt. Ltd. All Rights Reserved.
 * USAP PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

/**
 * 
 */
package com.mpos.payment.DBHandler;

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
import com.mpos.master.model.BaseModel;
import com.mpos.payment.model.MULTI_CHNG_MST_Model;

/**
 * Description-
 * 
 */

public class MULTI_CHNG_MST_DBHandler extends DBHandler {

	public static final String TAG = Constants.APP_TAG
			+ MULTI_CHNG_MST_DBHandler.class.getSimpleName();

	public MULTI_CHNG_MST_DBHandler(HandlerInf handlerInf,
			String[] dbQueryParam, String tableName, int dBOperation,
			int resultCode, boolean isUpdateDB) {
		super(handlerInf, dbQueryParam, tableName, dBOperation, resultCode,
				isUpdateDB);

	}

	public MULTI_CHNG_MST_DBHandler(HandlerInf handlerInf, Object model,
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

		cursor = getMultiChngMSTDetails(mOperationalResult
				.getDBOperationalParam().getQueryParam());

		if (cursor.getCount() > 0) {

			Bundle bundle = new Bundle();
			ArrayList<BaseModel> multichngModelArr = parseCursor(cursor);
			Logger.d(
					TAG,
					"getDatafromDataBase:MChange"
							+ multichngModelArr.toString());

			bundle.putSerializable(Constants.RESULTCODEBEAN, multichngModelArr);
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
	private Cursor getMultiChngMSTDetails(String[] queryParam) {
		Logger.d(TAG, "getMultiChngMSTDetails");

		Cursor c = mSQLiteDB.query(mOperationalResult.getTableName(), null,
				whereQuery, queryParam, null, null, null);
		return c;
	}

	@Override
	protected void updateDataIntoDatabase() {

	}

	@Override
	protected void deleteDatafromDatabse() {
		mSQLiteDB.delete(mOperationalResult.getTableName(), null, null);
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

	@Override
	protected void isDataAvailableIntoDatabase() {

	}

	@Override
	protected void getDBRecordCount() {

	}

	@Override
	protected void getDefaultDBVehicle() {

	}

	@Override
	protected void clearCache() {

	}

	/**
	 * Method- Insert Info info in Table
	 * 
	 * @param object
	 * @return long rowAffected
	 */
	@SuppressWarnings("unchecked")
	private long insertInfo(Object model) {
		Logger.d(TAG, "insertInfo:" + model.toString());

		ArrayList<MULTI_CHNG_MST_Model> dataModels = (ArrayList<MULTI_CHNG_MST_Model>) model;
		int numberOFRowsCreated = 0;

		for (MULTI_CHNG_MST_Model multiChnge_MST_Model : dataModels) {
			ContentValues initValues = MULTI_CHNG_MST_Model
										.getcontentvalues(multiChnge_MST_Model);
			
			
			long row =mSQLiteDB.insertWithOnConflict(mOperationalResult.getTableName(), BaseColumns._ID, initValues, SQLiteDatabase.CONFLICT_REPLACE);
			
			/*long row = mSQLiteDB.insert(mOperationalResult.getTableName(),
					null, initValues);*/
			if (row > 0) {
				numberOFRowsCreated++;
			}
		}

		if (numberOFRowsCreated == dataModels.size()) {
			Logger.d(TAG, "No. Of Rows:" + numberOFRowsCreated);
			return numberOFRowsCreated;
		} else {
			return -1;
		}
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
		int indexChngAmt = cursor.getColumnIndex(DBConstants.MCM_CHNG_AMNT);
		int indexCurrAbbr = cursor.getColumnIndex(DBConstants.MCM_CURR_ABBR);
		int indexExRate = cursor.getColumnIndex(DBConstants.MCM_EX_RATE);
		int indexLocAmt = cursor.getColumnIndex(DBConstants.MCM_LOC_AMNT);
		int indexOperator = cursor.getColumnIndex(DBConstants.MCM_OPERATOR);
		int indexPaymeNo = cursor.getColumnIndex(DBConstants.MCM_PYMNT_NMBR);

		ArrayList<BaseModel> multiChngModelArr = new ArrayList<BaseModel>();
		if (cursor.moveToFirst()) {
			do {
				MULTI_CHNG_MST_Model chngModel = new MULTI_CHNG_MST_Model();
				chngModel.setChngAmt(Double.parseDouble(cursor
						.getString(indexChngAmt)));
				chngModel.setCurrAbbr(cursor.getString(indexCurrAbbr));
				chngModel.setExRate(Double.parseDouble(cursor
						.getString(indexExRate)));
				chngModel.setLocAmt(Double.parseDouble(cursor
						.getString(indexLocAmt)));
				chngModel.setOperator(cursor.getString(indexOperator));
				chngModel.setPaymentNo(cursor.getString(indexPaymeNo));

				multiChngModelArr.add(chngModel);
			} while (cursor.moveToNext());
		}
		return multiChngModelArr;
	}

}
