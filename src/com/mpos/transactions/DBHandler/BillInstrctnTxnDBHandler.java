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
import com.mpos.transactions.model.Bill_INSTRCTN_TXN_Model;

/**
 * Description-
 * 
 */

public class BillInstrctnTxnDBHandler extends DBHandler {

	public static final String TAG = Constants.APP_TAG
			+ BillInstrctnTxnDBHandler.class.getSimpleName();

	public BillInstrctnTxnDBHandler(HandlerInf handlerInf,
			String[] dbQueryParam, String tableName, int dBOperation,
			int resultCode, boolean isUpdate) {
		super(handlerInf, dbQueryParam, tableName, dBOperation, resultCode,
				isUpdate);
		// TODO Auto-generated constructor stub
	}

	public BillInstrctnTxnDBHandler(HandlerInf handlerInf, Object model,
			String tableName, int dBOperation, int resultCode, boolean isUpdate) {
		super(handlerInf, model, tableName, dBOperation, resultCode, isUpdate);
		// TODO Auto-generated constructor stub
	}

	private String whereQuery = null;

	public void setWhereQuery(String whereQuery) {
		this.whereQuery = whereQuery;
	}

	@Override
	protected void getDatafromDataBase() {
		Logger.d(TAG, "getDatafromDataBase");
		Cursor cursor = null;

		cursor = getBillInstrctnTxnDetails(mOperationalResult
				.getDBOperationalParam().getQueryParam());

		if (cursor.getCount() > 0) {
			Bundle bundle = new Bundle();
			ArrayList<Bill_INSTRCTN_TXN_Model> billInstrctnTxnModelArr = parseCursor(cursor);
			Logger.d(TAG,
					"getDatafromDataBase:" + billInstrctnTxnModelArr.toString());

			bundle.putSerializable(Constants.RESULTCODEBEAN,
					billInstrctnTxnModelArr);
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
	 * @param queryParam
	 * @return
	 */
	private Cursor getBillInstrctnTxnDetails(String[] queryParam) {
		Logger.d(TAG, "getBillInstrctnTxnDetails");
		Cursor c = null;
		Logger.d(TAG, "wq:" + whereQuery);
		Logger.d(TAG, "tbN: " + mOperationalResult.getTableName());

		if (whereQuery != null) {
			c = mSQLiteDB.query(mOperationalResult.getTableName(), null,
					whereQuery, queryParam, null, null, null);
		} else {
			c = mSQLiteDB.query(mOperationalResult.getTableName(), null, null,
					null, null, null, null);
		}
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
					.setRequestResponseCode(OperationalResult.DB_ERROR);
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

		Bill_INSTRCTN_TXN_Model dataModel = (Bill_INSTRCTN_TXN_Model) model;
		ContentValues initValues = Bill_INSTRCTN_TXN_Model
				.getcontentvalues(dataModel);

		String whrQry = DBConstants.BILL_INSTRCTN_TXN_BILL_SCND + "=? and "
				+ DBConstants.BILL_INSTRCTN_TXN_PRDCT_CODE + "=? and "
				+ DBConstants.BILL_INSTRCTN_TXN_ROW_ID + "=?";

		String[] whrArg = { dataModel.getBillScnd(), dataModel.getPrdctCode(), dataModel.getRow_id() };

		long row = mSQLiteDB.update(mOperationalResult.getTableName(),
				initValues, whrQry, whrArg);

		return row;
	}

	@Override
	protected void deleteDatafromDatabse() {
		long lRowAffected = 0; // default

		String[] whrArgs = this.mOperationalResult.getDBOperationalParam()
				.getQueryParam();
		String whrQry = DBConstants.BILL_INSTRCTN_TXN_BILL_SCND + "=?";

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
	private long insertInfo(Object model) {
		Logger.d(TAG, "insertInfo:" + model.toString());

		Bill_INSTRCTN_TXN_Model dataModel = (Bill_INSTRCTN_TXN_Model) model;

		ContentValues initValues = Bill_INSTRCTN_TXN_Model
				.getcontentvalues(dataModel);

		long row = mSQLiteDB.insert(mOperationalResult.getTableName(), null,
				initValues);

		return row;
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
		Logger.d(TAG, "getrequiredRecordInstructionFromDB");
		Cursor cursor = null;

		cursor = getRecord(this.mOperationalResult.getdBModel());

		if (cursor.getCount() > 0) {
			Bundle bundle = new Bundle();
			ArrayList<Bill_INSTRCTN_TXN_Model> instructionModels = parseCursor(cursor);
			Logger.d(TAG, "getDatafromDataBase:" + instructionModels.toString());

			bundle.putSerializable(Constants.RESULTCODEBEAN, instructionModels);
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
		Logger.d(TAG, "getRecord");

		BillTransactionModel dataModel = (BillTransactionModel) model;

		String whrQry = DBConstants.BILL_INSTRCTN_TXN_BILL_SCND + "=? and "
				+ DBConstants.BILL_INSTRCTN_TXN_PRDCT_CODE + "=? and "
				+ DBConstants.BILL_INSTRCTN_TXN_ROW_ID + "=?";

		String[] whrArg = { dataModel.getBILL_SCNCD(),
				dataModel.getPRDCT_CODE(), dataModel.getRow_id() };

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
	private ArrayList<Bill_INSTRCTN_TXN_Model> parseCursor(Cursor cursor) {
		Logger.d(TAG, "parseCursor");

		int indexBillInstTxn_Rowid = cursor
				.getColumnIndex(DBConstants.BILL_INSTRCTN_TXN_ROW_ID);
		int indexBillInstTxn_CmpCode = cursor
				.getColumnIndex(DBConstants.BILL_INSTRCTN_TXN_CMP_CODE);
		int indexBillInstTxn_BranchCode = cursor
				.getColumnIndex(DBConstants.BILL_INSTRCTN_TXN_BRANCH_CODE);
		int indexBillInstTxnBillScnd = cursor
				.getColumnIndex(DBConstants.BILL_INSTRCTN_TXN_BILL_SCND);
		int indexInstTxnPrdctCode = cursor
				.getColumnIndex(DBConstants.BILL_INSTRCTN_TXN_PRDCT_CODE);
		int indexBillInstrTxn_Pck = cursor
				.getColumnIndex(DBConstants.BILL_INSTRCTN_TXN_PCK);
		int indexBillInstTxn_extraInstrc = cursor
				.getColumnIndex(DBConstants.BILL_INSTRCTN_TXN_EXTRA_INSTRCTN);
		int indexBillInstTxnPrdctVoid = cursor
				.getColumnIndex(DBConstants.BILL_INSTRCTN_TXN_PRDCT_VOID);
		int indexInstTxnIn_InstQty = cursor
				.getColumnIndex(DBConstants.BILL_INSTRCTN_TXN_INSTRCN_QTY);
		int indexInstTxn_InsrtCode = cursor
				.getColumnIndex(DBConstants.BILL_INSTRCTN_TXN_INSTRCTN_CODE);

		ArrayList<Bill_INSTRCTN_TXN_Model> billInstTxnModelArr = new ArrayList<Bill_INSTRCTN_TXN_Model>();
		if (cursor.moveToFirst()) {
			do {
				Bill_INSTRCTN_TXN_Model billInstrTxnModel = new Bill_INSTRCTN_TXN_Model();
				billInstrTxnModel.setRow_id(cursor
						.getString(indexBillInstTxn_Rowid));

				billInstrTxnModel.setBillScnd(cursor
						.getString(indexBillInstTxnBillScnd));
				billInstrTxnModel.setBranchCode(cursor
						.getString(indexBillInstTxn_BranchCode));
				billInstrTxnModel.setCompanyCode(cursor
						.getString(indexBillInstTxn_CmpCode));
				billInstrTxnModel.setExtraInstrcn(cursor
						.getString(indexBillInstTxn_extraInstrc));
				billInstrTxnModel.setInstrcCode(cursor
						.getString(indexInstTxn_InsrtCode));
				billInstrTxnModel.setInstrnQty(Double.parseDouble(cursor
						.getString(indexInstTxnIn_InstQty)));
				billInstrTxnModel.setPck(cursor
						.getString(indexBillInstrTxn_Pck));
				billInstrTxnModel.setPrdctCode(cursor
						.getString(indexInstTxnPrdctCode));
				billInstrTxnModel.setPrdctVoid(cursor
						.getString(indexBillInstTxnPrdctVoid));

				billInstTxnModelArr.add(billInstrTxnModel);
			} while (cursor.moveToNext());
		}
		return billInstTxnModelArr;
	}

}
