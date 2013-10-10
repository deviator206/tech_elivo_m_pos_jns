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
import com.mpos.master.model.BILL_Mst_Model;

/**
 * Description-
 * 
 * @author jaggarnaut-studios
 */

public class BILL_MST_DBHandler extends DBHandler {

	public static final String TAG = Constants.APP_TAG
			+ BILL_MST_DBHandler.class.getSimpleName();

	public BILL_MST_DBHandler(HandlerInf handlerInf, String[] dbQueryParam,
			String tableName, int dBOperation, int resultCode,
			boolean isUpdateDB) {
		super(handlerInf, dbQueryParam, tableName, dBOperation, resultCode,
				isUpdateDB);
		// TODO Auto-generated constructor stub
	}

	public BILL_MST_DBHandler(HandlerInf handlerInf, Object model,
			String tableName, int dBOperation, int resultCode,
			boolean isUpdateDB) {
		super(handlerInf, model, tableName, dBOperation, resultCode, isUpdateDB);
		// TODO Auto-generated constructor stub
	}

	private String whereQuery = null;
	private boolean isSelectAll = false;

	public void setWhereQuery(String whereQuery) {
		this.whereQuery = whereQuery;
	}

	public void setSelectAll(boolean isSelectAll) {
		this.isSelectAll = isSelectAll;
	}

	@Override
	protected void getDatafromDataBase() {
		Logger.d(TAG, "getDatafromDataBase");
		Cursor cursor = null;

		cursor = getBillMstDetails(mOperationalResult.getDBOperationalParam()
				.getQueryParam());

		if (cursor.getCount() > 0) {

			Bundle bundle = new Bundle();
			ArrayList<BILL_Mst_Model> billMstModels = null;
			if (!isSelectAll) {
				billMstModels = parseCursor(cursor);
			} else {
				billMstModels = parseNoCursor(cursor);
			}

			Logger.d(TAG, "getDatafromDataBase:" + billMstModels.toString());

			bundle.putSerializable(Constants.RESULTCODEBEAN, billMstModels);
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
	private Cursor getBillMstDetails(String[] queryParam) {
		Logger.d(TAG, "getBillMstDetails");

		if (whereQuery == null) {
			whereQuery = DBConstants.TXN_TYPE + "=?";
		}
		Cursor c = null;
		// select as per query
		if (!isSelectAll) {
			c = mSQLiteDB.query(mOperationalResult.getTableName(), null,
					whereQuery, queryParam, null, null, null);
		} else { // Select All
			String[] coloumns = new String[] { DBConstants.BILL_SCNCD };
			c = mSQLiteDB.query(mOperationalResult.getTableName(), coloumns,
					null, null, null, null, null);
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

			getDatafromDataBase();

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

		BILL_Mst_Model dataModel = (BILL_Mst_Model) model;

		String whrQry = DBConstants.BILL_SCNCD + "=?";
		String[] whrArg = { dataModel.getBILL_SCNCD() };
		ContentValues initValues = null;

		if (mOperationalResult.getResultCode() == Constants.UPDATE_BILL_MST_RECORD) {
			initValues = BILL_Mst_Model.getUpdatevalues(dataModel);
		} else {
			initValues = BILL_Mst_Model.getUpdateTXNTYPEContentvalue(dataModel);
		}

		long row = mSQLiteDB.update(mOperationalResult.getTableName(),
				initValues, whrQry, whrArg);

		return row;
	}

	@Override
	protected void deleteDatafromDatabse() {
		Logger.d(TAG, "deleteDatafromDatabse:");
		long lRowAffected = 0; // default

		String[] whrArgs = this.mOperationalResult.getDBOperationalParam()
				.getQueryParam();
		String whrQry = DBConstants.BILL_SCNCD + "=?";

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

		BILL_Mst_Model dataModel = (BILL_Mst_Model) model;

		ContentValues initValues = BILL_Mst_Model.getcontentvalues(dataModel);

		/*long row = mSQLiteDB.insert(mOperationalResult.getTableName(), null,
				initValues);*/
		long row =mSQLiteDB.insertWithOnConflict(mOperationalResult.getTableName(), BaseColumns._ID, initValues, SQLiteDatabase.CONFLICT_REPLACE);
		

		return row;
	}

	@Override
	protected void isDataAvailableIntoDatabase() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void getDBRecordCount() {
		Logger.d(TAG, "getDBRecordCount");
		Cursor cursor = null;

		String whrQuery = DBConstants.BILL_SCNCD + "=?";

		cursor = mSQLiteDB.query(mOperationalResult.getTableName(), null,
				whrQuery, mOperationalResult.getDBOperationalParam()
						.getQueryParam(), null, null, null);

		Logger.d(TAG, "Existing Record Count:" + cursor.getCount());
		Bundle bundle = new Bundle();
		bundle.putSerializable(Constants.RESULTCODEBEAN, cursor.getCount());
		this.mOperationalResult.setResult(bundle);

		cursor.close();

		this.mOperationalResult.setOperationalCode(Constants.COUNT);
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
	 * Method- to get only transaction no
	 * 
	 * @param cursor
	 * @return
	 */
	private ArrayList<BILL_Mst_Model> parseNoCursor(Cursor cursor) {
		Logger.d(TAG, "parseCursor");
		int indexBillSCNCD = cursor.getColumnIndex(DBConstants.BILL_SCNCD);

		ArrayList<BILL_Mst_Model> billMstModelArr = new ArrayList<BILL_Mst_Model>();
		if (cursor.moveToFirst()) {
			do {
				BILL_Mst_Model billMstModel = new BILL_Mst_Model();
				billMstModel.setBILL_SCNCD(cursor.getString(indexBillSCNCD));
				billMstModelArr.add(billMstModel);
			} while (cursor.moveToNext());
		}
		return billMstModelArr;
	}

	/**
	 * Method- To get respective column name index
	 * 
	 * @param cursor
	 * @return
	 * 
	 */
	private ArrayList<BILL_Mst_Model> parseCursor(Cursor cursor) {
		Logger.d(TAG, "parseCursor");
		int indexAmtPaid = cursor.getColumnIndex(DBConstants.AMNT_PAID);
		int indexBillAmend = cursor.getColumnIndex(DBConstants.BILL_AMENED);
		int indexBillAmendStat = cursor
				.getColumnIndex(DBConstants.BILL_AMND_STATUS);
		int indexBillAmt = cursor.getColumnIndex(DBConstants.BILL_AMNT);
		int indexBillLcked = cursor.getColumnIndex(DBConstants.BILL_LOCKED);
		int indexBillNo = cursor.getColumnIndex(DBConstants.BILL_NO);
		int indexBillRunDte = cursor.getColumnIndex(DBConstants.BILL_RUN_DATE);
		int indexBillSCNCD = cursor.getColumnIndex(DBConstants.BILL_SCNCD);
		int indexBillSCND = cursor.getColumnIndex(DBConstants.BILL_SCND);
		int indexBillStat = cursor.getColumnIndex(DBConstants.BILL_STATUS);
		int indexBillSysDte = cursor.getColumnIndex(DBConstants.BILL_SYS_DATE);
		int indexBillVatAmt = cursor.getColumnIndex(DBConstants.BILL_VAT_AMNT);
		int indexBillVatExmpt = cursor
				.getColumnIndex(DBConstants.BILL_VAT_EXMPT);
		int indexBranchCode = cursor.getColumnIndex(DBConstants.BRNCH_CODE);
		int indexCardNo = cursor.getColumnIndex(DBConstants.CARDNO);
		int indexChngGvn = cursor.getColumnIndex(DBConstants.CHNG_GVN);
		int indexCmpnyCode = cursor.getColumnIndex(DBConstants.CMPNY_CODE);
		int indexShiftCode = cursor.getColumnIndex(DBConstants.SHIFT_CODE);
		int indexTbleCode = cursor.getColumnIndex(DBConstants.TBLE_CODE);
		int indexTillNo = cursor.getColumnIndex(DBConstants.TILL_NO);
		int indexTxnType = cursor.getColumnIndex(DBConstants.TXN_TYPE);
		int indexUsrNm = cursor.getColumnIndex(DBConstants.USR_NAME);

		ArrayList<BILL_Mst_Model> billMstModelArr = new ArrayList<BILL_Mst_Model>();
		if (cursor.moveToFirst()) {
			do {
				BILL_Mst_Model billMstModel = new BILL_Mst_Model();

				billMstModel.setAMNT_PAID(Double.parseDouble(cursor
						.getString(indexAmtPaid)));
				billMstModel.setBILL_AMENED(cursor.getString(indexBillAmend));
				billMstModel.setBILL_AMND_STATUS(cursor
						.getString(indexBillAmendStat));

				billMstModel.setBILL_AMNT(Double.parseDouble(cursor
						.getString(indexBillAmt)));

				billMstModel.setBILL_LOCKED(cursor.getString(indexBillLcked));
				billMstModel.setBILL_NO(cursor.getString(indexBillNo));
				billMstModel
						.setBILL_RUN_DATE(cursor.getString(indexBillRunDte));
				billMstModel.setBILL_SCNCD(cursor.getString(indexBillSCNCD));
				billMstModel.setBILL_SCND(cursor.getString(indexBillSCND));
				billMstModel.setBILL_STATUS(cursor.getString(indexBillStat));
				billMstModel
						.setBILL_SYS_DATE(cursor.getString(indexBillSysDte));

				billMstModel.setBILL_VAT_AMNT(Double.parseDouble(cursor
						.getString(indexBillVatAmt)));

				billMstModel.setBILL_VAT_EXMPT(cursor
						.getString(indexBillVatExmpt));
				billMstModel.setBRNCH_CODE(cursor.getString(indexBranchCode));
				billMstModel.setCARDNO(cursor.getString(indexCardNo));

				billMstModel.setCHNG_GVN(Double.parseDouble(cursor
						.getString(indexChngGvn)));

				billMstModel.setCMPNY_CODE(cursor.getString(indexCmpnyCode));
				billMstModel.setSHIFT_CODE(cursor.getString(indexShiftCode));
				billMstModel.setTBLE_CODE(cursor.getString(indexTbleCode));
				billMstModel.setTILL_NO(cursor.getString(indexTillNo));
				billMstModel.setTXN_TYPE(cursor.getString(indexTxnType));
				billMstModel.setUSR_NAME(cursor.getString(indexUsrNm));

				billMstModelArr.add(billMstModel);
			} while (cursor.moveToNext());
		}
		return billMstModelArr;
	}

}
