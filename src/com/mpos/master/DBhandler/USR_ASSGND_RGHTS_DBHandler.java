/*
 * Copyright (C) 2013  All Rights Reserved.
 *  PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
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
import com.mpos.master.model.USR_ASSGND_RGHTSModel;

/**
 * Description- 
 * 

 */

public class USR_ASSGND_RGHTS_DBHandler extends DBHandler {

	public static final String TAG = Constants.APP_TAG
			+ USR_ASSGND_RGHTS_DBHandler.class.getSimpleName();
	
	public USR_ASSGND_RGHTS_DBHandler(HandlerInf handlerInf, String[] dbQueryParam,
			String tableName, int dBOperation, int resultCode, boolean isUpdateDB) {
		super(handlerInf, dbQueryParam, tableName, dBOperation, resultCode,isUpdateDB);
	}

	public USR_ASSGND_RGHTS_DBHandler(HandlerInf handlerInf, Object model,
			String tableName, int dBOperation, int resultCode, boolean isUpdateDB) {
		super(handlerInf, model, tableName, dBOperation, resultCode,isUpdateDB);
	}

	@Override
	protected void getDatafromDataBase() {
		Logger.d(TAG, "getDatafromDataBase");

		String whereQuery = DBConstants.RFRNCE_ID + " =?";

		Cursor c = mSQLiteDB.query(mOperationalResult.getTableName(), null,
				whereQuery, mOperationalResult.getDBOperationalParam()
						.getQueryParam(), null, null, null);

		Bundle bundle = new Bundle();
		ArrayList<USR_ASSGND_RGHTSModel> userModelArr = parseCursor(c);
		bundle.putSerializable(Constants.RESULTCODEBEAN, userModelArr);
		this.mOperationalResult.setResult(bundle);
		c.close();

		this.mOperationalResult.setOperationalCode(Constants.SELECT);
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
					.setRequestResponseCode(OperationalResult.DB_ERROR);
		}
	}

	private long insertInfo(Object model) {

		ArrayList<USR_ASSGND_RGHTSModel> dataModel = (ArrayList<USR_ASSGND_RGHTSModel>) model;
		int numberOFRowsCreated = 0;
		for (USR_ASSGND_RGHTSModel user_asgn_rgts_Model : dataModel) {
			ContentValues initValues = USR_ASSGND_RGHTSModel
					.getcontentvalues(user_asgn_rgts_Model);
			long row = mSQLiteDB.insert(mOperationalResult.getTableName(),
					null, initValues);
			if (row > 0) {
				numberOFRowsCreated++;
			}
		}

		if (numberOFRowsCreated == dataModel.size()) {
			return numberOFRowsCreated;
		} else {
			return -1;
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
	 * Method- To get respective column name index
	 * 
	 * @param cursor
	 * @return
	 * 
	 */
	private ArrayList<USR_ASSGND_RGHTSModel> parseCursor(Cursor cursor) {
		if (cursor.getCount() > 0) {
			Logger.d(TAG, "parseCursor");
			int indexUserRFNCID = cursor.getColumnIndex(DBConstants.RFRNCE_ID);
			int indexRGHTNAME = cursor
					.getColumnIndex(DBConstants.RGHT_NAME);
			int indexRGHTVALUE = cursor
					.getColumnIndex(DBConstants.RGHT_VALUE);
			int indexFLDTYPE = cursor.getColumnIndex(DBConstants.FLD_TYPE);

			ArrayList<USR_ASSGND_RGHTSModel> userMSTModelArr = new ArrayList<USR_ASSGND_RGHTSModel>();
			if (cursor.moveToFirst()) {
				do {
					USR_ASSGND_RGHTSModel user_Assgnd_RghtsModel = new USR_ASSGND_RGHTSModel();
					user_Assgnd_RghtsModel.setFLD_TYPE(cursor.getString(indexFLDTYPE));
					user_Assgnd_RghtsModel.setRGHT_NAME(cursor.getString(indexRGHTNAME));
					user_Assgnd_RghtsModel.setRFRNCE_ID(cursor.getString(indexUserRFNCID));
					user_Assgnd_RghtsModel.setRGHT_VALUE(cursor.getString(indexRGHTVALUE));
					userMSTModelArr.add(user_Assgnd_RghtsModel);
				} while (cursor.moveToNext());
			}
			return userMSTModelArr;
		} else {
			return null;
		}
	}


}
