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
import com.mpos.transactions.model.SLMN_TXN_Model;

public class SLMN_TXNDBHandler extends DBHandler {

	public static final String TAG = Constants.APP_TAG
			+ SLMN_TXNDBHandler.class.getSimpleName();

	public SLMN_TXNDBHandler(HandlerInf handlerInf, String[] dbQueryParam,
			String tableName, int dBOperation, int resultCode,
			boolean isUpdateDB) {
		super(handlerInf, dbQueryParam, tableName, dBOperation, resultCode,
				isUpdateDB);
	}

	public SLMN_TXNDBHandler(HandlerInf handlerInf, Object model,
			String tableName, int dBOperation, int resultCode,
			boolean isUpdateDB) {
		super(handlerInf, model, tableName, dBOperation, resultCode, isUpdateDB);
	}

	private String whereQuery = null;

	public void setWhereQuery(String whereQuery) {
		this.whereQuery = whereQuery;
	}

	@Override
	protected void getDatafromDataBase() {
		Logger.d(TAG, "getDatafromDataBase");
		Cursor cursor = null;

		cursor = getSalsManTxnDetails(mOperationalResult
				.getDBOperationalParam().getQueryParam());

		if (cursor.getCount() > 0) {
			Bundle bundle = new Bundle();
			ArrayList<SLMN_TXN_Model> slmnTxnModels = parseCursor(cursor);
			Logger.d(TAG, "getDatafromDataBase:" + slmnTxnModels.toString());

			bundle.putSerializable(Constants.RESULTCODEBEAN, slmnTxnModels);
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
	private Cursor getSalsManTxnDetails(String[] queryParam) {
		Logger.d(TAG, "getSalsManTxnDetails");

		Cursor c = mSQLiteDB.query(mOperationalResult.getTableName(), null,
				whereQuery, queryParam, null, null, null);
		return c;
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
		
		SLMN_TXN_Model dataModel = (SLMN_TXN_Model) model;
		Logger.v(TAG, "slaes man txn"+model);
		if (dataModel != null) {
			if (checkDataAlreadyPresent(dataModel)) {
				updateInfo(dataModel);
				return 1;
			} else {
				Logger.d(TAG, "insertinfo");
			ContentValues initValues = SLMN_TXN_Model
					.getcontentvalues(dataModel);
			long row = mSQLiteDB.insert(mOperationalResult.getTableName(),
					null, initValues);
			return row;
			}
		}
		return -1;
	}
	
	private long updateInfo(SLMN_TXN_Model model) {
		Logger.d(TAG, "updateInfo:" + model.toString());

		String whrQry = DBConstants.BILL_SCNCD + "=?";
		String[] whrArg = { model.getBill_scncd() };

		ContentValues initValues = SLMN_TXN_Model
				.getcontentvalues(model);

		long row = mSQLiteDB.update(mOperationalResult.getTableName(),
				initValues, whrQry, whrArg);

		return row;
	}

	private boolean checkDataAlreadyPresent(SLMN_TXN_Model model) {
		String whrQry = DBConstants.BILL_SCNCD + "=?";
		String[] whrArg = { model.getBill_scncd() };

		Cursor c = mSQLiteDB.query(mOperationalResult.getTableName(), null,
				whrQry, whrArg, null, null, null);
		Logger.d(TAG, "checkDataAlreadyPresent:" +c.getCount());
		if (c != null && c.getCount() == 1) {
			return true;
		}
		return false;
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
	private ArrayList<SLMN_TXN_Model> parseCursor(Cursor cursor) {
		Logger.d(TAG, "parseCursor");

		int indexTxnCmpnyCode = cursor.getColumnIndex(DBConstants.CMPNY_CODE);
		int indexTxnBrnchCode = cursor.getColumnIndex(DBConstants.BRNCH_CODE);
		int indexTxnBilScncd = cursor.getColumnIndex(DBConstants.BILL_SCNCD);
		int indexTxnSlmCode = cursor.getColumnIndex(DBConstants.Slm_Code);
		int indexTxnSysRunDte = cursor.getColumnIndex(DBConstants.Sys_run_date);
		int indexTxTillNo = cursor.getColumnIndex(DBConstants.TILL_NO);
		int indexTxnUsrName = cursor.getColumnIndex(DBConstants.User_Name);

		ArrayList<SLMN_TXN_Model> slmnTxnModelArr = new ArrayList<SLMN_TXN_Model>();
		if (cursor.moveToFirst()) {
			do {
				SLMN_TXN_Model slmnTxnModel = new SLMN_TXN_Model();
				slmnTxnModel.setBill_scncd(cursor.getString(indexTxnBilScncd));
				slmnTxnModel.setBranchCode(cursor.getString(indexTxnBrnchCode));
				slmnTxnModel
						.setCompanyCode(cursor.getString(indexTxnCmpnyCode));

				slmnTxnModel.setSlm_code(cursor.getString(indexTxnSlmCode));
				slmnTxnModel.setSys_run_date(cursor
						.getString(indexTxnSysRunDte));
				slmnTxnModel.setTill_num(cursor.getString(indexTxTillNo));
				slmnTxnModel.setUsername(cursor.getString(indexTxnUsrName));

				slmnTxnModelArr.add(slmnTxnModel);
			} while (cursor.moveToNext());
		}
		return slmnTxnModelArr;
	}

}
