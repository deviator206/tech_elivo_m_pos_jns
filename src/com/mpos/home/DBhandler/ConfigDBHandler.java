/*
 * Copyright (C) 2013 USAP Pvt. Ltd. All Rights Reserved.
 * jaggarnaut-studios pvt. ltd. PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.mpos.home.DBhandler;

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
import com.mpos.home.model.Company_PolicyModel;
import com.mpos.master.model.BaseModel;
import com.mpos.master.model.PRDCT_GRP_MST_Model;

/**
 * Description-
 * 
 * @author jaggarnaut-studios
 */

public class ConfigDBHandler extends DBHandler {

	public static final String TAG = Constants.APP_TAG
			+ ConfigDBHandler.class.getSimpleName();

	public ConfigDBHandler(HandlerInf handlerInf, String[] dbQueryParam,
			String tableName, int dBOperation, int resultCode,
			boolean isUpdateDB) {
		super(handlerInf, dbQueryParam, tableName, dBOperation, resultCode,
				isUpdateDB);

	}

	public ConfigDBHandler(HandlerInf handlerInf, Object model,
			String tableName, int dBOperation, int resultCode,
			boolean isUpdateDB) {
		super(handlerInf, model, tableName, dBOperation, resultCode, isUpdateDB);
	}

	@Override
	protected void getDatafromDataBase() {
		Logger.d(TAG, "getDatafromDataBase");
		Cursor cursor = null;

		cursor = getConfigDetails(mOperationalResult.getDBOperationalParam()
				.getQueryParam());

		if (cursor.getCount() > 0) {

			Bundle bundle = new Bundle();
			ArrayList<BaseModel> configModelArr = parseCursor(cursor);
			Logger.d(TAG, "getDatafromDataBase:" + configModelArr.toString());

			bundle.putSerializable(Constants.RESULTCODEBEAN, configModelArr);
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
	private Cursor getConfigDetails(String[] queryParam) {
		Logger.d(TAG, "getConfigDetails");

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
		Logger.d(TAG, "getParticularPlcyRecord");
		Cursor cursor = null;

		cursor = getPolicy(mOperationalResult.getDBOperationalParam()
				.getQueryParam());

		if (cursor.getCount() > 0) {
			Bundle bundle = new Bundle();
			BaseModel policyModelModel = parseSingleCursor(cursor);

			bundle.putSerializable(Constants.RESULTCODEBEAN, policyModelModel);
			this.mOperationalResult.setResult(bundle);
		} else {
			this.mOperationalResult
					.setRequestResponseCode(OperationalResult.DB_ERROR);
		}

		cursor.close();

		this.mOperationalResult.setOperationalCode(Constants.SELECT);
	}

	/**
	 * Method - TO retrieve particular record
	 * 
	 * @param queryParam
	 * @return
	 */
	private Cursor getPolicy(String[] queryParam) {
		Logger.d(TAG, "getPolicy");
		Cursor c = null;
		String whrQuery = DBConstants.CONFIG_REG_NAME + " =?";
		c = mSQLiteDB.query(mOperationalResult.getTableName(), null, whrQuery,
				queryParam, null, null, null);

		return c;
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

		ArrayList<Company_PolicyModel> dataModels = (ArrayList<Company_PolicyModel>) model;
		int numberOFRowsCreated = 0;

		for (Company_PolicyModel cmpny_polcy_Model : dataModels) {
			ContentValues initValues = Company_PolicyModel
					.getcontentvalues(cmpny_polcy_Model);

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

	/**
	 * Method- To get respective column name index
	 * 
	 * @param cursor
	 * @return
	 * 
	 */
	private ArrayList<BaseModel> parseCursor(Cursor cursor) {
		Logger.d(TAG, "parseCursor");
		int indexRegType = cursor.getColumnIndex(DBConstants.CONFIG_REG_TYPE);
		int indexRegName = cursor.getColumnIndex(DBConstants.CONFIG_REG_NAME);
		int indexRegValue = cursor.getColumnIndex(DBConstants.CONFIG_REG_VALUE);
		int indexRegExtValue = cursor
				.getColumnIndex(DBConstants.CONFIG_REG_EXT_VALUE);

		ArrayList<BaseModel> cmpPlyModels = new ArrayList<BaseModel>();
		if (cursor.moveToFirst()) {
			do {
				Company_PolicyModel plyModel = new Company_PolicyModel();
				plyModel.setReg_type(cursor.getString(indexRegType));
				plyModel.setReg_name(cursor.getString(indexRegName));
				plyModel.setCmpny_ply(cursor.getString(indexRegValue));
				plyModel.setCmpny_ply_value(cursor.getString(indexRegExtValue));

				cmpPlyModels.add(plyModel);
			} while (cursor.moveToNext());
		}
		return cmpPlyModels;
	}

	/**
	 * Method- To get respective column name index
	 * 
	 * @param cursor
	 * @return
	 * 
	 */
	private BaseModel parseSingleCursor(Cursor cursor) {
		Logger.d(TAG, "parseSingleCursor");
		int indexRegType = cursor.getColumnIndex(DBConstants.CONFIG_REG_TYPE);
		int indexRegName = cursor.getColumnIndex(DBConstants.CONFIG_REG_NAME);
		int indexRegValue = cursor.getColumnIndex(DBConstants.CONFIG_REG_VALUE);
		int indexRegExtValue = cursor
				.getColumnIndex(DBConstants.CONFIG_REG_EXT_VALUE);

		Company_PolicyModel plyModel = new Company_PolicyModel();
		if (cursor.moveToFirst()) {
			do {
				plyModel.setReg_type(cursor.getString(indexRegType));// Type
				plyModel.setReg_name(cursor.getString(indexRegName)); // Name
				plyModel.setCmpny_ply(cursor.getString(indexRegValue)); // Value
				plyModel.setCmpny_ply_value(cursor.getString(indexRegExtValue));

			} while (cursor.moveToNext());
		}
		return plyModel;
	}

}
