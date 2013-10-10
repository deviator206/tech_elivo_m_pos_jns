/*
 * Copyright (C) 2013  All Rights Reserved.
 *  PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
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
import com.mpos.master.model.BaseModel;
import com.mpos.master.model.PRDCT_GRP_MST_Model;

/**
 * Description-
 * 

 */
public class GRP_MST_DBHandler extends DBHandler {

	public static final String TAG = Constants.APP_TAG
			+ GRP_MST_DBHandler.class.getSimpleName();

	public GRP_MST_DBHandler(HandlerInf handlerInf, String[] dbQueryParam,
			String tableName, int dBOperation, int resultCode, boolean isUpdateDB) {
		super(handlerInf, dbQueryParam, tableName, dBOperation, resultCode,isUpdateDB);
		
	}

	public GRP_MST_DBHandler(HandlerInf handlerInf, Object model,
			String tableName, int dBOperation, int resultCode, boolean isUpdateDB) {
		super(handlerInf, model, tableName, dBOperation, resultCode,isUpdateDB);
	}

	@Override
	protected void getDatafromDataBase() {
		Logger.d(TAG, "getDatafromDataBase");
		Cursor cursor = null;

		cursor = getGRPMSTDetails(mOperationalResult.getDBOperationalParam()
				.getQueryParam());

		if (cursor.getCount() > 0) {

			Bundle bundle = new Bundle();
			ArrayList<BaseModel> grpModelArr = parseCursor(cursor);
			Logger.d(TAG, "getDatafromDataBase:" + grpModelArr.toString());

			bundle.putSerializable(Constants.RESULTCODEBEAN, grpModelArr);
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
	private Cursor getGRPMSTDetails(String[] queryParam) {
		Logger.d(TAG, "getGRPMSTDetails");

		Cursor c = mSQLiteDB.query(mOperationalResult.getTableName(), null,
				null, null, null, null, null);
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

		ArrayList<PRDCT_GRP_MST_Model> dataModels = (ArrayList<PRDCT_GRP_MST_Model>) model;
		int numberOFRowsCreated = 0;

		for (PRDCT_GRP_MST_Model prdct_GRP_MST_Model : dataModels) {
			ContentValues initValues = PRDCT_GRP_MST_Model
					.getcontentvalues(prdct_GRP_MST_Model);

			long row =mSQLiteDB.insertWithOnConflict(mOperationalResult.getTableName(), BaseColumns._ID, initValues, SQLiteDatabase.CONFLICT_REPLACE);
			
			/*long row = mSQLiteDB.insert(mOperationalResult.getTableName(),
					null, initValues);*/
			if (row > 0) {
				numberOFRowsCreated++;
			}
		}

		if (numberOFRowsCreated == dataModels.size()) {
			Logger.d(TAG, "No. Of Rows:"+numberOFRowsCreated);
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
		int indexGRPCode = cursor.getColumnIndex(DBConstants.GRP_CODE);
		int indexGRPName = cursor.getColumnIndex(DBConstants.GRP_NAME);

		ArrayList<BaseModel> grpModelArr = new ArrayList<BaseModel>();
		if (cursor.moveToFirst()) {
			do {
				PRDCT_GRP_MST_Model grpModel = new PRDCT_GRP_MST_Model();
				grpModel.setGrp_code(cursor.getString(indexGRPCode));
				grpModel.setGrp_name(cursor.getString(indexGRPName));

				grpModelArr.add(grpModel);
			} while (cursor.moveToNext());
		}
		return grpModelArr;
	}

}
