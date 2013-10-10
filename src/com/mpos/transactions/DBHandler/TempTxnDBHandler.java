/*
 * Copyright (C) 2013 USAP Pvt. Ltd. All Rights Reserved.
 * jaggarnaut-studios pvt. ltd. PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.mpos.transactions.DBHandler;

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
import com.mpos.transactions.model.TempTXNModel;

/**
 * Description-
 * 
 * @author jaggarnaut-studios
 */

public class TempTxnDBHandler extends DBHandler {

	public static final String TAG = Constants.APP_TAG
			+ TempTxnDBHandler.class.getSimpleName();

	public TempTxnDBHandler(HandlerInf handlerInf, String[] dbQueryParam,
			String tableName, int dBOperation, int resultCode,
			boolean isUpdateDB) {
		super(handlerInf, dbQueryParam, tableName, dBOperation, resultCode,
				isUpdateDB);

	}

	public TempTxnDBHandler(HandlerInf handlerInf, Object model,
			String tableName, int dBOperation, int resultCode,
			boolean isUpdateDB) {
		super(handlerInf, model, tableName, dBOperation, resultCode, isUpdateDB);
	}

	@Override
	protected void getDatafromDataBase() {
		Logger.d(TAG, "getDatafromDataBase");
		Cursor cursor = null;

		cursor = getTempTxnDetails(mOperationalResult.getDBOperationalParam()
				.getQueryParam());

		if (cursor.getCount() > 0) {

			Bundle bundle = new Bundle();
			ArrayList<BaseModel> tempTxnModelArr = parseCursor(cursor);
			Logger.d(TAG, "getDatafromDataBase:" + tempTxnModelArr.toString());

			bundle.putSerializable(Constants.RESULTCODEBEAN, tempTxnModelArr);
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
	private Cursor getTempTxnDetails(String[] queryParam) {
		Logger.d(TAG, "getTempTxnDetails");
		Cursor c = null;

		String whereQuery = DBConstants.TEMP_TXN_UPLOAD_FLAG + "=?";

		c = mSQLiteDB.query(mOperationalResult.getTableName(), null,
				whereQuery, queryParam, null, null, null);

		return c;
	}

	@Override
	protected void updateDataIntoDatabase() {

	}

	@Override
	protected void deleteDatafromDatabse() {
		int lRowAffected = mSQLiteDB.delete(mOperationalResult.getTableName(),
				"1", null);

		this.mOperationalResult.setOperationalCode(Constants.DELETE);
		if (lRowAffected > 0) {
			Logger.d(TAG, "No.Of Rows Deleted: "+lRowAffected);
			this.mOperationalResult
					.setRequestResponseCode(OperationalResult.SUCCESS);
		} else {
			this.mOperationalResult
					.setRequestResponseCode(OperationalResult.DB_ERROR);
		}
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
	private long insertInfo(Object model) {
		Logger.d(TAG, "insertInfo:" + model.toString());

		TempTXNModel dataModels = (TempTXNModel) model;
		ContentValues initValues = TempTXNModel.getcontentvalues(dataModels);
		
		long row =mSQLiteDB.insertWithOnConflict(mOperationalResult.getTableName(), BaseColumns._ID, initValues, SQLiteDatabase.CONFLICT_REPLACE);
		
		/*long row = mSQLiteDB.insert(mOperationalResult.getTableName(), null,
				initValues);*/

		return row;

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
		int indexTxnCode = cursor
				.getColumnIndex(DBConstants.TEMP_TXN_BILL_SCNCD);
		int indexTxnUpload = cursor
				.getColumnIndex(DBConstants.TEMP_TXN_UPLOAD_FLAG);

		ArrayList<BaseModel> txnModelArr = new ArrayList<BaseModel>();
		if (cursor.moveToFirst()) {
			do {
				TempTXNModel txnModel = new TempTXNModel();
				txnModel.setTxn_no(cursor.getString(indexTxnCode));
				txnModel.setIsUploaded(cursor.getString(indexTxnUpload));

				txnModelArr.add(txnModel);
			} while (cursor.moveToNext());
		}
		return txnModelArr;
	}

}
