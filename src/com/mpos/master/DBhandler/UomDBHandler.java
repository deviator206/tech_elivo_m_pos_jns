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
import com.mpos.master.model.UOM_MST_Model;

/**
 * Description-
 * 
 * @author jaggarnaut-studios
 */

public class UomDBHandler extends DBHandler {
	public static final String TAG = Constants.APP_TAG
			+ UomDBHandler.class.getSimpleName();

	public UomDBHandler(HandlerInf handlerInf, String[] dbQueryParam,
			String tableName, int dBOperation, int resultCode, boolean isUpdateDB) {
		super(handlerInf, dbQueryParam, tableName, dBOperation, resultCode,isUpdateDB);
		// TODO Auto-generated constructor stub
	}

	public UomDBHandler(HandlerInf handlerInf, Object model, String tableName,
			int dBOperation, int resultCode, boolean isUpdateDB) {
		super(handlerInf, model, tableName, dBOperation, resultCode,isUpdateDB);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void getDatafromDataBase() {
		Logger.d(TAG, "getDatafromDataBase");
		Cursor cursor = null;

		cursor = getUomMSTDetails(mOperationalResult.getDBOperationalParam()
				.getQueryParam());

		if (cursor.getCount() > 0) {

			Bundle bundle = new Bundle();
			ArrayList<UOM_MST_Model> uomModelArr = parseCursor(cursor);
			Logger.d(TAG, "getDatafromDataBase:" + uomModelArr.toString());

			bundle.putSerializable(Constants.RESULTCODEBEAN, uomModelArr);
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
	private Cursor getUomMSTDetails(String[] queryParam) {
		Logger.d(TAG, "getUomMSTDetails");

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

		ArrayList<UOM_MST_Model> dataModels = (ArrayList<UOM_MST_Model>) model;
		int numberOFRowsCreated = 0;

		for (UOM_MST_Model uom_MST_Model : dataModels) {
			ContentValues initValues = UOM_MST_Model
					.getcontentvalues(uom_MST_Model);

			long row = mSQLiteDB.insert(mOperationalResult.getTableName(),
					null, initValues);
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
	private ArrayList<UOM_MST_Model> parseCursor(Cursor cursor) {
		Logger.d(TAG, "parseCursor");
		int indexUomCode = cursor.getColumnIndex(DBConstants.CODE);
		int indexUomName = cursor.getColumnIndex(DBConstants.NAME);
		int indexUomUserName = cursor.getColumnIndex(DBConstants.USR_NAME);

		ArrayList<UOM_MST_Model> uomModelArr = new ArrayList<UOM_MST_Model>();
		if (cursor.moveToFirst()) {
			do {
				UOM_MST_Model uomModel = new UOM_MST_Model();
				uomModel.setUom_code(cursor.getString(indexUomCode));
				uomModel.setUom_name(cursor.getString(indexUomName));
				uomModel.setUom_user_name(cursor.getString(indexUomUserName));

				uomModelArr.add(uomModel);
			} while (cursor.moveToNext());
		}
		return uomModelArr;
	}

}
