/*
 * Copyright (C) 2013 USAP Pvt. Ltd. All Rights Reserved.
 * USAP PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

/**
 * 
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
import com.mpos.master.model.SCAN_CODE_MST_Model;

/**
 * Description-
 * 
 */

public class ScanCodesDBHandler extends DBHandler {

	public static final String TAG = Constants.APP_TAG
			+ ScanCodesDBHandler.class.getSimpleName();

	public ScanCodesDBHandler(HandlerInf handlerInf, String[] dbQueryParam,
			String tableName, int dBOperation, int resultCode, boolean isUpdateDB) {
		super(handlerInf, dbQueryParam, tableName, dBOperation, resultCode,isUpdateDB);
		// TODO Auto-generated constructor stub
	}

	public ScanCodesDBHandler(HandlerInf handlerInf, Object model,
			String tableName, int dBOperation, int resultCode, boolean isUpdateDB) {
		super(handlerInf, model, tableName, dBOperation, resultCode,isUpdateDB);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void getDatafromDataBase() {
		Logger.d(TAG, "getDatafromDataBase");
		Cursor cursor = null;

		cursor = getScanCodesMSTDetails(mOperationalResult
				.getDBOperationalParam().getQueryParam());

		if (cursor.getCount() > 0) {

			Bundle bundle = new Bundle();
			ArrayList<SCAN_CODE_MST_Model> scanCodesModelArr = parseCursor(cursor);
			Logger.d(TAG, "getDatafromDataBase:" + scanCodesModelArr.toString());

			bundle.putSerializable(Constants.RESULTCODEBEAN, scanCodesModelArr);
			this.mOperationalResult.setResult(bundle);
		} else {
			this.mOperationalResult
					.setRequestResponseCode(OperationalResult.DB_ERROR);
		}

		cursor.close();

		this.mOperationalResult.setOperationalCode(Constants.SELECT);

		// TODO Auto-generated method stub

	}

	/**
	 * Method- To retrieve data
	 * 
	 * @param selectionArgs
	 * 
	 * @return
	 */
	private Cursor getScanCodesMSTDetails(String[] queryParam) {
		Logger.d(TAG, "getScanCodesMSTDetails");

		String whrQuery = DBConstants.SCAN_CODE + "=?";

		Cursor c = mSQLiteDB.query(mOperationalResult.getTableName(), null,
				whrQuery, queryParam, null, null, null);
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

		ArrayList<SCAN_CODE_MST_Model> dataModels = (ArrayList<SCAN_CODE_MST_Model>) model;
		int numberOFRowsCreated = 0;

		for (SCAN_CODE_MST_Model scanCode_MST_Model : dataModels) {
			ContentValues initValues = SCAN_CODE_MST_Model
					.getcontentvalues(scanCode_MST_Model);

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
		Logger.d(TAG, "getSelectedScanFromDB");
		Cursor cursor = null;

		cursor = getScanCode(mOperationalResult.getDBOperationalParam()
				.getQueryParam());

		if (cursor.getCount() > 0) {
			Bundle bundle = new Bundle();
			ArrayList<SCAN_CODE_MST_Model> scanModels = parseCursor(cursor);
			Logger.d(TAG, "getDatafromDataBase:" + scanModels.toString());

			bundle.putSerializable(Constants.RESULTCODEBEAN, scanModels);
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
	private Cursor getScanCode(String[] queryParam) {
		Logger.d(TAG, "getScanCode");

		String whereQuery = DBConstants.SCAN_CODE + " =?";

		Logger.d(TAG, "WQ:" + whereQuery);
		Logger.d(TAG, "QP:" + queryParam[0]);
		Logger.d(TAG, "TB:" + mOperationalResult.getTableName());

		Cursor c = mSQLiteDB.query(mOperationalResult.getTableName(), null,
				whereQuery, queryParam, null, null, null);
		return c;
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
	private ArrayList<SCAN_CODE_MST_Model> parseCursor(Cursor cursor) {
		Logger.d(TAG, "parseCursor");
		int indexdscnt_cost_price = cursor.getColumnIndex(DBConstants.SCAN_COST_DSCNT_PRCE);
		int indexdscnt_price = cursor.getColumnIndex(DBConstants.SCAN_DSCNT_PRCE);
		int indexqty = cursor.getColumnIndex(DBConstants.SCAN_QTY);
		int indexprdct_code = cursor.getColumnIndex(DBConstants.SCAN_PRDCT_CODE);
		int indexuom = cursor.getColumnIndex(DBConstants.SCAN_UOM);
		int indexscan_code = cursor.getColumnIndex(DBConstants.SCAN_USR_NM);

		ArrayList<SCAN_CODE_MST_Model> scanCodeModelArr = new ArrayList<SCAN_CODE_MST_Model>();
		if (cursor.moveToFirst()) {
			do {
				SCAN_CODE_MST_Model scnCodeModel = new SCAN_CODE_MST_Model();
				scnCodeModel.setDscnt_cost_price(Double.parseDouble(cursor
						.getString(indexdscnt_cost_price)));
				scnCodeModel.setDscnt_price(Double.parseDouble(cursor
						.getString(indexdscnt_price)));
				scnCodeModel.setQty(Double.parseDouble(cursor
						.getString(indexqty)));
				scnCodeModel.setPrdct_code(cursor.getString(indexprdct_code));
				scnCodeModel.setUom(cursor.getString(indexuom));
				scnCodeModel.setScan_code(cursor.getString(indexscan_code));

				scanCodeModelArr.add(scnCodeModel);
			} while (cursor.moveToNext());
		}
		return scanCodeModelArr;
	}

	/**
	 * Method- To get respective column name index
	 * 
	 * @param cursor
	 * @return
	 * 
	 */
	private SCAN_CODE_MST_Model parseModelCursor(Cursor cursor) {
		Logger.d(TAG, "parseCursor");
		int indexdscnt_cost_price = cursor.getColumnIndex(DBConstants.SCAN_COST_DSCNT_PRCE);
		int indexdscnt_price = cursor.getColumnIndex(DBConstants.SCAN_DSCNT_PRCE);
		int indexqty = cursor.getColumnIndex(DBConstants.SCAN_QTY);
		int indexprdct_code = cursor.getColumnIndex(DBConstants.SCAN_PRDCT_CODE);
		int indexuom = cursor.getColumnIndex(DBConstants.SCAN_UOM);
		int indexscan_code = cursor.getColumnIndex(DBConstants.SCAN_USR_NM);

		SCAN_CODE_MST_Model scnCodeModel = null;
		if (cursor.moveToFirst()) {
			do {
				scnCodeModel = new SCAN_CODE_MST_Model();
				scnCodeModel.setDscnt_cost_price(Double.parseDouble(cursor
						.getString(indexdscnt_cost_price)));
				scnCodeModel.setDscnt_price(Double.parseDouble(cursor
						.getString(indexdscnt_price)));
				scnCodeModel.setQty(Double.parseDouble(cursor
						.getString(indexqty)));
				scnCodeModel.setPrdct_code(cursor.getString(indexprdct_code));
				scnCodeModel.setUom(cursor.getString(indexuom));
				scnCodeModel.setScan_code(cursor.getString(indexscan_code));

			} while (cursor.moveToNext());
		}
		return scnCodeModel;
	}

}
