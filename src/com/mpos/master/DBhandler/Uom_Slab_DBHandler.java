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
import com.mpos.master.model.UOM_SLAB_MST_MODEL;

/**
 * Description-
 * 
 * @author jaggarnaut-studios
 */

public class Uom_Slab_DBHandler extends DBHandler {

	public static final String TAG = Constants.APP_TAG
			+ Uom_Slab_DBHandler.class.getSimpleName();

	public Uom_Slab_DBHandler(HandlerInf handlerInf, String[] dbQueryParam,
			String tableName, int dBOperation, int resultCode, boolean isUpdateDB) {
		super(handlerInf, dbQueryParam, tableName, dBOperation, resultCode,isUpdateDB);
		// TODO Auto-generated constructor stub
	}

	public Uom_Slab_DBHandler(HandlerInf handlerInf, Object model,
			String tableName, int dBOperation, int resultCode, boolean isUpdateDB) {
		super(handlerInf, model, tableName, dBOperation, resultCode,isUpdateDB);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void getDatafromDataBase() {
		Logger.d(TAG, "getDatafromDataBase");
		Cursor cursor = null;

		cursor = getUomSlabMSTDetails(mOperationalResult
				.getDBOperationalParam().getQueryParam());

		if (cursor.getCount() > 0) {
			Bundle bundle = new Bundle();
			ArrayList<UOM_SLAB_MST_MODEL> uomSlabModelArr = parseCursor(cursor);
			Logger.d(TAG, "getDatafromDataBase:" + uomSlabModelArr.toString());

			bundle.putSerializable(Constants.RESULTCODEBEAN, uomSlabModelArr);
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
	private Cursor getUomSlabMSTDetails(String[] queryParam) {
		Logger.d(TAG, "getUomSlabMSTDetails");

		String whereQuery = DBConstants.PRDCT_CODE + " =?";

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

		ArrayList<UOM_SLAB_MST_MODEL> dataModels = (ArrayList<UOM_SLAB_MST_MODEL>) model;
		int numberOFRowsCreated = 0;

		for (UOM_SLAB_MST_MODEL uom_Slab_MST_Model : dataModels) {
			ContentValues initValues = UOM_SLAB_MST_MODEL
					.getcontentvalues(uom_Slab_MST_Model);

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
	private ArrayList<UOM_SLAB_MST_MODEL> parseCursor(Cursor cursor) {
		Logger.d(TAG, "parseCursor");
		int indexUomSlabPRDCTCode = cursor
				.getColumnIndex(DBConstants.PRDCT_CODE);
		int indexUomSlabCodeName = cursor.getColumnIndex(DBConstants.UOM_CODE);
		int indexUomSlabCost = cursor
				.getColumnIndex(DBConstants.PRDCT_COST_PRCE);
		int indexUomSlabSell = cursor
				.getColumnIndex(DBConstants.PRDCT_SELL_PRCE);

		ArrayList<UOM_SLAB_MST_MODEL> uomSlabModelArr = new ArrayList<UOM_SLAB_MST_MODEL>();
		if (cursor.moveToFirst()) {
			do {
				UOM_SLAB_MST_MODEL uomSlabModel = new UOM_SLAB_MST_MODEL();
				uomSlabModel.setPrdct_Code(cursor
						.getString(indexUomSlabPRDCTCode));
				uomSlabModel
						.setUom_Code(cursor.getString(indexUomSlabCodeName));
				uomSlabModel.setPrdct_Cost_Price(cursor
						.getString(indexUomSlabCost));
				uomSlabModel.setPrdct_Sell_Price(cursor
						.getString(indexUomSlabSell));

				uomSlabModelArr.add(uomSlabModel);
			} while (cursor.moveToNext());
		}
		return uomSlabModelArr;
	}

	
}
