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
import com.mpos.login.model.USER_MST_Model;

/**
 * Description-
 * 

 */

public class USER_MST_DBHandler extends DBHandler {

	public static final String TAG = Constants.APP_TAG
			+ USER_MST_DBHandler.class.getSimpleName();

	public USER_MST_DBHandler(HandlerInf handlerInf, String[] dbQueryParam,
			String tableName, int dBOperation, int resultCode, boolean isUpdateDB) {
		super(handlerInf, dbQueryParam, tableName, dBOperation, resultCode,isUpdateDB);
	}

	public USER_MST_DBHandler(HandlerInf handlerInf, Object model,
			String tableName, int dBOperation, int resultCode, boolean isUpdateDB) {
		super(handlerInf, model, tableName, dBOperation, resultCode,isUpdateDB);
	}

	@Override
	protected void getDatafromDataBase() {
		Logger.d(TAG, "getDatafromDataBase");

		String whereQuery = DBConstants.USR_NM + " =?";

		Cursor c = mSQLiteDB.query(mOperationalResult.getTableName(), null,
				whereQuery, mOperationalResult.getDBOperationalParam()
						.getQueryParam(), null, null, null);

		Bundle bundle = new Bundle();
		ArrayList<USER_MST_Model> userModelArr = parseCursor(c);
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

		ArrayList<USER_MST_Model> dataModel = (ArrayList<USER_MST_Model>) model;
		int numberOFRowsCreated = -1;
		if (dataModel != null) {
			numberOFRowsCreated=0;
			for (USER_MST_Model user_MST_Model : dataModel) {
				ContentValues initValues = USER_MST_Model
						.getcontentvalues(user_MST_Model);
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
		return -1;
		
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
	private ArrayList<USER_MST_Model> parseCursor(Cursor cursor) {
		if (cursor.getCount() > 0) {
			Logger.d(TAG, "parseCursor");
			int indexUserBio = cursor.getColumnIndex(DBConstants.USR_BIO);
			int indexUserCtrnDate = cursor
					.getColumnIndex(DBConstants.USR_CRTN_DATE);
			int indexUserFullName = cursor
					.getColumnIndex(DBConstants.USR_FULL_NAME);
			int indexUserGrpID = cursor.getColumnIndex(DBConstants.USR_GRP_ID);
			int indexUserID = cursor.getColumnIndex(DBConstants.USR_ID);
			int indexUserModfdDate = cursor
					.getColumnIndex(DBConstants.USR_MODFD_BY);
			int indexUserName = cursor.getColumnIndex(DBConstants.USR_NM);
			int indexUSER_PWD = cursor.getColumnIndex(DBConstants.USR_PWD);
			int indexUSER_SCNCD = cursor.getColumnIndex(DBConstants.USR_SCNCD);

			ArrayList<USER_MST_Model> userMSTModelArr = new ArrayList<USER_MST_Model>();
			if (cursor.moveToFirst()) {
				do {
					USER_MST_Model user_mstModel = new USER_MST_Model();
					user_mstModel.setUSR_BIO(cursor.getString(indexUserBio));
					user_mstModel.setUSR_CRTN_DATE(cursor
							.getString(indexUserCtrnDate));
					user_mstModel.setUSR_FULL_NAME(cursor
							.getString(indexUserFullName));
					user_mstModel.setUSR_GRP_ID(cursor
							.getString(indexUserGrpID));
					user_mstModel.setUSR_ID(cursor.getString(indexUserID));
					user_mstModel.setUSR_MODFD_BY(cursor
							.getString(indexUserModfdDate));
					user_mstModel.setUSR_NM(cursor.getString(indexUserName));
					user_mstModel.setUSR_SCNCD(cursor
							.getString(indexUSER_SCNCD));
					user_mstModel.setUSR_PWD(cursor.getString(indexUSER_PWD));
					userMSTModelArr.add(user_mstModel);
				} while (cursor.moveToNext());
			}
			return userMSTModelArr;
		} else {
			return null;
		}
	}

}
