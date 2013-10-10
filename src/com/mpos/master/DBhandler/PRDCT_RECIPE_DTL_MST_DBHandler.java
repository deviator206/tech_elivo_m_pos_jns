/*
 * Copyright (C) 2013 jaggarnaut-studios Pvt. Ltd. All Rights Reserved.
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
import com.mpos.master.model.BaseModel;
import com.mpos.transactions.model.PRDCT_RECIPE_DTL_TXN_Model;

/**
 * Description-
 * 
 * @author jaggarnaut-studios
 */

public class PRDCT_RECIPE_DTL_MST_DBHandler extends DBHandler {

	public static final String TAG = Constants.APP_TAG
			+ PRDCT_RECIPE_DTL_MST_DBHandler.class.getSimpleName();

	public PRDCT_RECIPE_DTL_MST_DBHandler(HandlerInf handlerInf,
			String[] dbQueryParam, String tableName, int dBOperation,
			int resultCode, boolean isUpdateDB) {
		super(handlerInf, dbQueryParam, tableName, dBOperation, resultCode,isUpdateDB);
		// TODO Auto-generated constructor stub
	}

	public PRDCT_RECIPE_DTL_MST_DBHandler(HandlerInf handlerInf, Object model,
			String tableName, int dBOperation, int resultCode, boolean isUpdateDB) {
		super(handlerInf, model, tableName, dBOperation, resultCode,isUpdateDB);
	}

	@Override
	protected void getDatafromDataBase() {
		Logger.d(TAG, "getDatafromDataBase");
		Cursor cursor = null;

		cursor = getRECIPEMSTDetails(mOperationalResult.getDBOperationalParam()
				.getQueryParam());

		if (cursor.getCount() > 0) {

			Bundle bundle = new Bundle();
			ArrayList<BaseModel> recipeModelArr = parseCursor(cursor);
			Logger.d(TAG, "getDatafromDataBase:" + recipeModelArr.toString());

			bundle.putSerializable(Constants.RESULTCODEBEAN, recipeModelArr);
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
	private Cursor getRECIPEMSTDetails(String[] queryParam) {
		Logger.d(TAG, "getRECIPEMSTDetails");

		String whreQry = DBConstants.PRDCT_CODE + "=?";

		Cursor c = mSQLiteDB.query(mOperationalResult.getTableName(), null,
				whreQry, queryParam, null, null, null);
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

		ArrayList<PRDCT_RECIPE_DTL_TXN_Model> dataModels = (ArrayList<PRDCT_RECIPE_DTL_TXN_Model>) model;
		int numberOFRowsCreated = 0;

		for (PRDCT_RECIPE_DTL_TXN_Model prdct_recipe_MST_Model : dataModels) {
			ContentValues initValues = PRDCT_RECIPE_DTL_TXN_Model
					.getcontentvalues(prdct_recipe_MST_Model);

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
	private ArrayList<BaseModel> parseCursor(Cursor cursor) {
		Logger.d(TAG, "parseCursor");
		int indexRecipeCode = cursor
				.getColumnIndex(DBConstants.PRDCT_RECIPE_CODE);
		int indexRcpMeasure = cursor
				.getColumnIndex(DBConstants.PRDCT_RCP_MEASURE);
		int indexRcpUomCode = cursor
				.getColumnIndex(DBConstants.PRDCT_RCP_UOM_CODE);
		int indexRcpQty = cursor.getColumnIndex(DBConstants.PRDCT_RCP_QTY);
		int indexRcpCost = cursor.getColumnIndex(DBConstants.PRDCT_RECIPE_COST);
		int indexRcpSTkUomCode = cursor
				.getColumnIndex(DBConstants.PRDCT_STK_UOM_CODE);
		int indexRcpStkQty = cursor.getColumnIndex(DBConstants.PRDCT_STK_QTY);
		int indexRcpTlrnceQty = cursor
				.getColumnIndex(DBConstants.PRDCT_TLRNCE_QTY);
		int indexRcpStkTlrnceQty = cursor
				.getColumnIndex(DBConstants.PRDCT_STK_TLRNCE_QTY);
		int indexRcpPrdctCode = cursor.getColumnIndex(DBConstants.PRDCT_CODE);

		ArrayList<BaseModel> rcpModelArr = new ArrayList<BaseModel>();
		if (cursor.moveToFirst()) {
			do {
				PRDCT_RECIPE_DTL_TXN_Model rcpModel = new PRDCT_RECIPE_DTL_TXN_Model();
				rcpModel.setPrdct_code(cursor.getString(indexRcpPrdctCode));
				rcpModel.setRcp_cost(Double.parseDouble(cursor
						.getString(indexRcpCost)));

				rcpModel.setRcp_measure(cursor.getString(indexRcpMeasure));
				rcpModel.setRcp_qty(Double.parseDouble(cursor
						.getString(indexRcpQty)));
				rcpModel.setRcp_uom_code(cursor.getString(indexRcpUomCode));
				rcpModel.setRecipe_code(cursor.getString(indexRecipeCode));
				rcpModel.setStk_qty(Double.parseDouble(cursor
						.getString(indexRcpStkQty)));
				rcpModel.setStk_tlrnce_qty(Double.parseDouble(cursor
						.getString(indexRcpStkTlrnceQty)));

				rcpModel.setStk_uom_code(cursor.getString(indexRcpSTkUomCode));
				rcpModel.setTlrnce_qty(Double.parseDouble(cursor
						.getString(indexRcpTlrnceQty)));

				rcpModelArr.add(rcpModel);
			} while (cursor.moveToNext());
		}
		return rcpModelArr;
	}

}
