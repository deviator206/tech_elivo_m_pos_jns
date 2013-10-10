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
import com.mpos.master.model.VAT_MST_Model;

/**
 * Description-
 * 
 * @author jaggarnaut-studios
 */

public class VATDBHandler extends DBHandler {

	public static final String TAG = Constants.APP_TAG
			+ VATDBHandler.class.getSimpleName();

	public VATDBHandler(HandlerInf handlerInf, String[] dbQueryParam,
			String tableName, int dBOperation, int resultCode, boolean isUpdateDB) {
		super(handlerInf, dbQueryParam, tableName, dBOperation, resultCode,isUpdateDB);
		// TODO Auto-generated constructor stub
	}

	public VATDBHandler(HandlerInf handlerInf, Object model, String tableName,
			int dBOperation, int resultCode, boolean isUpdateDB) {
		super(handlerInf, model, tableName, dBOperation, resultCode,isUpdateDB);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void getDatafromDataBase() {
		Logger.d(TAG, "getDatafromDataBase");
		Cursor cursor = null;

		cursor = getVatDetails(mOperationalResult.getDBOperationalParam()
				.getQueryParam());

		if (cursor.getCount() > 0) {

			Bundle bundle = new Bundle();
			VAT_MST_Model vatModel = parseCursor(cursor);
			Logger.d(TAG, "getDatafromDataBase:" + vatModel.toString());

			bundle.putSerializable(Constants.RESULTCODEBEAN, vatModel);
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
	private Cursor getVatDetails(String[] queryParam) {
		Logger.d(TAG, "getVatDetails");

		String whereQuery = DBConstants.VAT_CODE + " =?";

		Cursor c = mSQLiteDB.query(mOperationalResult.getTableName(), null,
				whereQuery, queryParam, null, null, null);
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

		ArrayList<VAT_MST_Model> dataModels = (ArrayList<VAT_MST_Model>) model;
		int numberOFRowsCreated = 0;

		for (VAT_MST_Model vat_MST_Model : dataModels) {
			ContentValues initValues = VAT_MST_Model
					.getcontentvalues(vat_MST_Model);

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
	private VAT_MST_Model parseCursor(Cursor cursor) {
		Logger.d(TAG, "parseCursor");
		int indexVatCode = cursor.getColumnIndex(DBConstants.VAT_CODE);
		int indexVatPrcnt = cursor.getColumnIndex(DBConstants.VAT_PRCNT);
		int indexVatUserName = cursor.getColumnIndex(DBConstants.USR_NAME);

		VAT_MST_Model vatModel = null;
		if (cursor.moveToFirst()) {
			do {
				vatModel = new VAT_MST_Model();
				vatModel.setVat_code(cursor.getString(indexVatCode));
				vatModel.setVat_prcnt(Double.parseDouble(cursor
						.getString(indexVatPrcnt)));
				vatModel.setVat_user_name(cursor.getString(indexVatUserName));

			} while (cursor.moveToNext());
		}
		return vatModel;
	}

}
