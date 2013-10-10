/*
 * Copyright (C) 2013 USAP Pvt. Ltd. All Rights Reserved.
 * USAP PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

/**
 * 
 */
package com.mpos.transactions.DBHandler;

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
import com.mpos.home.model.BillTransactionModel;
import com.mpos.master.model.BaseModel;
import com.mpos.transactions.model.PRDCT_CNSMPTN_TXNModel;

/**
 * Description-
 * 
 */

public class PRDCT_CNSMPTN_DBHandler extends DBHandler {

	public static final String TAG = Constants.APP_TAG
			+ PRDCT_CNSMPTN_DBHandler.class.getSimpleName();

	public PRDCT_CNSMPTN_DBHandler(HandlerInf handlerInf,
			String[] dbQueryParam, String tableName, int dBOperation,
			int resultCode, boolean isUpdate) {
		super(handlerInf, dbQueryParam, tableName, dBOperation, resultCode,
				isUpdate);
		// TODO Auto-generated constructor stub
	}

	public PRDCT_CNSMPTN_DBHandler(HandlerInf handlerInf, Object model,
			String tableName, int dBOperation, int resultCode, boolean isUpdate) {
		super(handlerInf, model, tableName, dBOperation, resultCode, isUpdate);
	}

	private String whereQuery = null;

	public void setWhereQuery(String whereQuery) {
		this.whereQuery = whereQuery;
	}

	@Override
	protected void getDatafromDataBase() {
		Logger.d(TAG, "getDatafromDataBase");
		Cursor cursor = null;

		cursor = getCNSMPTNDetails(mOperationalResult.getDBOperationalParam()
				.getQueryParam());

		if (cursor.getCount() > 0) {

			Bundle bundle = new Bundle();
			ArrayList<BaseModel> cnsmptnModelArr = parseCursor(cursor);
			Logger.d(TAG, "getDatafromDataBase:" + cnsmptnModelArr.toString());

			bundle.putSerializable(Constants.RESULTCODEBEAN, cnsmptnModelArr);
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
	private Cursor getCNSMPTNDetails(String[] queryParam) {
		Logger.d(TAG, "getCNSMPTNDetails");

		Cursor c = mSQLiteDB.query(mOperationalResult.getTableName(), null,
				whereQuery, queryParam, null, null, null);
		return c;
	}

	@Override
	protected void updateDataIntoDatabase() {
		long lRowAffected = 0; // default

		lRowAffected = updateInfo(this.mOperationalResult.getdBModel());

		this.mOperationalResult.setOperationalCode(Constants.UPDATE);

		if (lRowAffected > 0) {
			this.mOperationalResult
					.setRequestResponseCode(OperationalResult.SUCCESS);
		} else {
			this.mOperationalResult
					.setRequestResponseCode(OperationalResult.MASTER_ERROR);
		}
	}

	/**
	 * Method- Update Info in Table
	 * 
	 * @param object
	 * @return long rowAffected
	 */
	private long updateInfo(Object model) {
		Logger.d(TAG, "updateInfo:" + model.toString());

		PRDCT_CNSMPTN_TXNModel dataModel = (PRDCT_CNSMPTN_TXNModel) model;
		ContentValues initValues = PRDCT_CNSMPTN_TXNModel
				.getcontentvalues(dataModel);

		String whrQry = DBConstants.CNSMPTN_TXN_NO + "=? and "
				+ DBConstants.CNSMPTN_PRDCT_CODE + "=?";
		String[] whrArg = { dataModel.getTxn_no(), dataModel.getPrdct_code() };

		long row = mSQLiteDB.update(mOperationalResult.getTableName(),
				initValues, whrQry, whrArg);

		return row;
	}

	@Override
	protected void deleteDatafromDatabse() {
		long lRowAffected = 0; // default

		String[] whrArgs = this.mOperationalResult.getDBOperationalParam()
				.getQueryParam();
		Logger.d(TAG, "whr:" + whrArgs[0]);

		String whrQry = DBConstants.CNSMPTN_TXN_NO + "=?";

		lRowAffected = mSQLiteDB.delete(mOperationalResult.getTableName(),
				whrQry, whrArgs);

		this.mOperationalResult.setOperationalCode(Constants.DELETE);

		if (lRowAffected > 0) {
			this.mOperationalResult
					.setRequestResponseCode(OperationalResult.SUCCESS);
		} else {
			this.mOperationalResult
					.setRequestResponseCode(OperationalResult.MASTER_ERROR);
		}
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

		ArrayList<PRDCT_CNSMPTN_TXNModel> dataModels = (ArrayList<PRDCT_CNSMPTN_TXNModel>) model;
		int numberOFRowsCreated = 0;

		for (PRDCT_CNSMPTN_TXNModel cnsmptn_TXN_Model : dataModels) {
			ContentValues initValues = PRDCT_CNSMPTN_TXNModel
					.getcontentvalues(cnsmptn_TXN_Model);

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

	/**
	 * Method- To get particular record
	 */
	@Override
	protected void getDefaultDBVehicle() {
		Logger.d(TAG, "getrequiredRecordCnsmptnFromDB");
		Cursor cursor = null;

		cursor = getRecord(this.mOperationalResult.getdBModel());

		if (cursor.getCount() > 0) {
			Bundle bundle = new Bundle();
			ArrayList<BaseModel> cnsmptnModels = parseCursor(cursor);
			Logger.d(TAG, "getDatafromDataBase:" + cnsmptnModels.toString());

			bundle.putSerializable(Constants.RESULTCODEBEAN, cnsmptnModels);
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
	 * @param dataModel
	 * @return
	 */
	private Cursor getRecord(Object model) {
		Logger.d(TAG, "getPRDCT");

		BillTransactionModel dataModel = (BillTransactionModel) model;

		String whrQry = DBConstants.CNSMPTN_TXN_NO + "=? and "
				+ DBConstants.CNSMPTN_PRDCT_CODE + "=?";
		String[] whrArg = { dataModel.getBILL_SCNCD(),
				dataModel.getPRDCT_CODE() };

		Cursor c = mSQLiteDB.query(mOperationalResult.getTableName(), null,
				whrQry, whrArg, null, null, null);
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
	private ArrayList<BaseModel> parseCursor(Cursor cursor) {
		Logger.d(TAG, "parseCursor");

		int indexcnsCmpCode = cursor
				.getColumnIndex(DBConstants.CNSMPTN_CMPNY_CODE);
		int indexcnsBranCode = cursor
				.getColumnIndex(DBConstants.CNSMPTN_BRNCH_CODE);
		int indexcnsTxnNo = cursor.getColumnIndex(DBConstants.CNSMPTN_TXN_NO);
		int indexcnsPrdctCode = cursor
				.getColumnIndex(DBConstants.CNSMPTN_PRDCT_CODE);
		int indexcnsStkUomCode = cursor
				.getColumnIndex(DBConstants.CNSMPTN_STK_UOM_CODE);
		int indexcnsStkQty = cursor.getColumnIndex(DBConstants.CNSMPTN_STK_QTY);
		int indexcnsPrdctPrce = cursor
				.getColumnIndex(DBConstants.CNSMPTN_PRDCT_PRCE);
		int indexcnsPrdctVatCode = cursor
				.getColumnIndex(DBConstants.CNSMPTN_PRDCT_VAT_CODE);
		int indexcnsPrdctVatAmt = cursor
				.getColumnIndex(DBConstants.CNSMPTN_PRDCT_VAT_AMNT);
		int indexcnsRecipeCode = cursor
				.getColumnIndex(DBConstants.CNSMPTN_RECIPE_CODE);

		int indexcnsPrdctVoid = cursor
				.getColumnIndex(DBConstants.CNSMPTN_PRDCT_VOID);
		int indexcnsTxnType = cursor
				.getColumnIndex(DBConstants.CNSMPTN_TXN_TYPE);
		int indexcnsTxnMode = cursor
				.getColumnIndex(DBConstants.CNSMPTN_TXN_MODE);
		int indexcnsPck = cursor.getColumnIndex(DBConstants.CNSMPTN_PCK);

		ArrayList<BaseModel> cnsmptnModelArr = new ArrayList<BaseModel>();
		if (cursor.moveToFirst()) {
			do {
				PRDCT_CNSMPTN_TXNModel grpModel = new PRDCT_CNSMPTN_TXNModel();
				grpModel.setBranch_code(cursor.getString(indexcnsBranCode));
				grpModel.setCmp_code(cursor.getString(indexcnsCmpCode));
				grpModel.setPck(cursor.getString(indexcnsPck));
				grpModel.setPrdct_code(cursor.getString(indexcnsPrdctCode));
				grpModel.setPrdct_prce(Double.parseDouble(cursor
						.getString(indexcnsPrdctPrce)));
				grpModel.setPrdct_vat_amt(Double.parseDouble(cursor
						.getString(indexcnsPrdctVatAmt)));
				grpModel.setPrdct_vat_code(cursor
						.getString(indexcnsPrdctVatCode));
				grpModel.setPrdct_void(cursor.getString(indexcnsPrdctVoid));
				grpModel.setRecipe_code(cursor.getString(indexcnsRecipeCode));
				grpModel.setStk_qty(Double.parseDouble(cursor
						.getString(indexcnsStkQty)));
				grpModel.setStk_uom_code(cursor.getString(indexcnsStkUomCode));
				grpModel.setTxn_mode(cursor.getString(indexcnsTxnMode));
				grpModel.setTxn_no(cursor.getString(indexcnsTxnNo));
				grpModel.setTxn_type(cursor.getString(indexcnsTxnType));

				cnsmptnModelArr.add(grpModel);
			} while (cursor.moveToNext());
		}
		return cnsmptnModelArr;
	}

}
