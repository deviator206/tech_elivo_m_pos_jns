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
import com.mpos.master.model.CURRENCY_MST_MODEL;

public class Currency_MstDBHandler extends DBHandler {

	public static final String TAG = Constants.APP_TAG
			+ Currency_MstDBHandler.class.getSimpleName();

	public Currency_MstDBHandler(HandlerInf handlerInf, String[] dbQueryParam,
			String tableName, int dBOperation, int resultCode,
			boolean isUpdateDB) {
		super(handlerInf, dbQueryParam, tableName, dBOperation, resultCode,
				isUpdateDB);
	}

	public Currency_MstDBHandler(HandlerInf handlerInf, Object model,
			String tableName, int dBOperation, int resultCode,
			boolean isUpdateDB) {
		super(handlerInf, model, tableName, dBOperation, resultCode, isUpdateDB);
	}

	@Override
	protected void getDatafromDataBase() {
		Logger.d(TAG, "getDatafromDataBase");

		Cursor c = mSQLiteDB.query(mOperationalResult.getTableName(), null,
				null, null, null, null, null);

		if (c.getCount() > 0) {
			Bundle bundle = new Bundle();
			ArrayList<CURRENCY_MST_MODEL> userModelArr = parseCursor(c);
			bundle.putSerializable(Constants.RESULTCODEBEAN, userModelArr);
			this.mOperationalResult.setResult(bundle);
		} else {
			this.mOperationalResult
					.setRequestResponseCode(OperationalResult.DB_ERROR);
		}
		c.close();

		this.mOperationalResult.setOperationalCode(Constants.SELECT);
	}

	private ArrayList<CURRENCY_MST_MODEL> parseCursor(Cursor cursor) {
		if (cursor.getCount() > 0) {
			Logger.d(TAG, "parseCursor");
			int indexAbbrName = cursor.getColumnIndex(DBConstants.Abbr_Name);
			int indexUserName = cursor.getColumnIndex(DBConstants.Name);
			int indexBaseflag = cursor.getColumnIndex(DBConstants.BaseFlag);
			int indexOperator = cursor.getColumnIndex(DBConstants.Operator);
			int indexExrate = cursor.getColumnIndex(DBConstants.ExRate);
			int indexSymbol = cursor.getColumnIndex(DBConstants.Symbol);
			int indexPOSTN = cursor.getColumnIndex(DBConstants.POSTN);

			ArrayList<CURRENCY_MST_MODEL> currencyArr = new ArrayList<CURRENCY_MST_MODEL>();
			if (cursor.moveToFirst()) {
				do {
					CURRENCY_MST_MODEL currencyModel = new CURRENCY_MST_MODEL();
					currencyModel.setCurr_abbrName(cursor
							.getString(indexAbbrName));
					currencyModel.setCurr_name(cursor.getString(indexUserName));
					currencyModel.setCurr_base_flag(cursor
							.getString(indexBaseflag));
					currencyModel.setCurr_operator(cursor
							.getString(indexOperator));
					currencyModel.setCurr_exrate(cursor.getFloat(indexExrate));
					currencyModel.setCurr_symbol(cursor.getString(indexSymbol));
					currencyModel.setCurr_postn(cursor.getInt(indexPOSTN));
					currencyArr.add(currencyModel);
				} while (cursor.moveToNext());
			}
			return currencyArr;
		} else {
			return null;
		}
	}

	@Override
	protected void updateDataIntoDatabase() {

	}

	private long insertInfo(Object model) {

		ArrayList<CURRENCY_MST_MODEL> dataModel = (ArrayList<CURRENCY_MST_MODEL>) model;
		int numberOFRowsCreated = 0;
		for (CURRENCY_MST_MODEL currency : dataModel) {
			ContentValues initValues = CURRENCY_MST_MODEL
					.getcontentvalues(currency);
			/*long row = mSQLiteDB.insert(mOperationalResult.getTableName(),
					null, initValues);*/
			long row =mSQLiteDB.insertWithOnConflict(mOperationalResult.getTableName(), BaseColumns._ID, initValues, SQLiteDatabase.CONFLICT_REPLACE);
			
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

}
