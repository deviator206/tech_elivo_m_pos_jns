/*
 * Copyright (C) 2013 USAP Pvt. Ltd. All Rights Reserved.
 * jaggarnaut-studios pvt. ltd. PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.mpos.master.DBhandler;

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
import com.mpos.master.model.PRDCT_INSTRC_MST_Model;

/**
 * Description-
 * 
 * @author jaggarnaut-studios
 */

public class Prdct_Instruc_DBHandler extends DBHandler {

	public static final String TAG = Constants.APP_TAG
			+ Prdct_Instruc_DBHandler.class.getSimpleName();

	public Prdct_Instruc_DBHandler(HandlerInf handlerInf,
			String[] dbQueryParam, String tableName, int dBOperation,
			int resultCode, boolean isUpdateDB) {
		super(handlerInf, dbQueryParam, tableName, dBOperation, resultCode,
				isUpdateDB);
		// TODO Auto-generated constructor stub
	}

	public Prdct_Instruc_DBHandler(HandlerInf handlerInf, Object model,
			String tableName, int dBOperation, int resultCode,
			boolean isUpdateDB) {
		super(handlerInf, model, tableName, dBOperation, resultCode, isUpdateDB);
	}

	@Override
	protected void getDatafromDataBase() {
		Logger.d(TAG, "getDatafromDataBase");
		Cursor cursor = null;

		cursor = getPrdctInstrcDetails(mOperationalResult
				.getDBOperationalParam().getQueryParam());

		if (cursor.getCount() > 0) {
			Bundle bundle = new Bundle();
			ArrayList<PRDCT_INSTRC_MST_Model> prdctInstrctnModelArr = parseCursor(cursor);
			Logger.d(TAG,
					"getDatafromDataBase:" + prdctInstrctnModelArr.toString());

			bundle.putSerializable(Constants.RESULTCODEBEAN,
					prdctInstrctnModelArr);
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
	private Cursor getPrdctInstrcDetails(String[] queryParam) {
		Logger.d(TAG, "getPrdctInstrcDetails");

		String whereQ = DBConstants.PRDCT_PRD_CODE + "=?";

		Logger.d(TAG, "wq:"+ whereQ);
		Logger.d(TAG, "qu:"+ queryParam[0]);
		
		Cursor c = mSQLiteDB.query(mOperationalResult.getTableName(), null,
				whereQ, queryParam, null, null, null);
		return c;
	}

	@Override
	protected void updateDataIntoDatabase() {
		// TODO Auto-generated method stub

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

	/**
	 * Method- Insert Info info in Table
	 * 
	 * @param object
	 * @return long rowAffected
	 */
	@SuppressWarnings("unchecked")
	private long insertInfo(Object model) {
		Logger.d(TAG, "insertInfo:" + model.toString());

		ArrayList<PRDCT_INSTRC_MST_Model> dataModels = (ArrayList<PRDCT_INSTRC_MST_Model>) model;
		int numberOFRowsCreated = 0;

		for (PRDCT_INSTRC_MST_Model instrctn_MST_Model : dataModels) {
			ContentValues initValues = PRDCT_INSTRC_MST_Model
					.getcontentvalues(instrctn_MST_Model);

			/*long row = mSQLiteDB.insert(mOperationalResult.getTableName(),
					null, initValues);*/
			long row =mSQLiteDB.insertWithOnConflict(mOperationalResult.getTableName(), BaseColumns._ID, initValues, SQLiteDatabase.CONFLICT_REPLACE);
			
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
	private ArrayList<PRDCT_INSTRC_MST_Model> parseCursor(Cursor cursor) {
		Logger.d(TAG, "parseCursor");
		int indexInstrctnCode = cursor
				.getColumnIndex(DBConstants.PRDCT_INSTRCTN_CODE);
		int indexInstrctnPrdctCode = cursor
				.getColumnIndex(DBConstants.PRDCT_PRD_CODE);

		ArrayList<PRDCT_INSTRC_MST_Model> instrctnModelArr = new ArrayList<PRDCT_INSTRC_MST_Model>();
		if (cursor.moveToFirst()) {
			do {
				PRDCT_INSTRC_MST_Model instrctnModel = new PRDCT_INSTRC_MST_Model();
				instrctnModel.setInstrctn_code(cursor
						.getString(indexInstrctnCode));
				instrctnModel.setPrdct_code(cursor
						.getString(indexInstrctnPrdctCode));
				instrctnModelArr.add(instrctnModel);
			} while (cursor.moveToNext());
		}
		return instrctnModelArr;
	}

}
