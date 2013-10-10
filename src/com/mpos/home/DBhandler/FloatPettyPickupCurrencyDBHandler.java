package com.mpos.home.DBhandler;

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
import com.mpos.home.model.FloatPickupPettyCurrencyModel;
import com.mpos.payment.DBHandler.MULTY_PYMNTS_MSTDBHandler;

public class FloatPettyPickupCurrencyDBHandler extends DBHandler {

	public static final String TAG = Constants.APP_TAG
			+ MULTY_PYMNTS_MSTDBHandler.class.getSimpleName();

	public FloatPettyPickupCurrencyDBHandler(HandlerInf handlerInf,
			String[] dbQueryParam, String tableName, int dBOperation,
			int resultCode, boolean isUpdateDB) {
		super(handlerInf, dbQueryParam, tableName, dBOperation, resultCode,
				isUpdateDB);
	}

	public FloatPettyPickupCurrencyDBHandler(HandlerInf handlerInf, Object model,
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
		Cursor c = null;
		if (whereQuery != null) {
			whereQuery = this.whereQuery + " =?";
			Logger.d(TAG, "wQ: "+whereQuery);

			c = mSQLiteDB.query(mOperationalResult.getTableName(), null,
					whereQuery, mOperationalResult.getDBOperationalParam()
							.getQueryParam(), null, null, null);
		} else {
			c = mSQLiteDB.query(mOperationalResult.getTableName(), null, null,
					null, null, null, null);
		}

		Logger.d(TAG, "getDatafromDataBase" + whereQuery);

		Bundle bundle = new Bundle();
		ArrayList<FloatPickupPettyCurrencyModel> multyPmtModelArr = parseCursor(c);
		bundle.putSerializable(Constants.RESULTCODEBEAN, multyPmtModelArr);
		
		this.mOperationalResult.setResult(bundle);
		c.close();

		this.mOperationalResult.setOperationalCode(Constants.SELECT);
	}

	private ArrayList<FloatPickupPettyCurrencyModel> parseCursor(Cursor cursor) {
		if (cursor.getCount() > 0) {

			int indexCurremcy = cursor.getColumnIndex(DBConstants.CURR_ABBR);
			int indexEXRate = cursor.getColumnIndex(DBConstants.EX_RATE);
			int indexOperator = cursor.getColumnIndex(DBConstants.OPERATOR);
			int indexLOC_AMT = cursor.getColumnIndex(DBConstants.LOC_AMNT);
			int indexPAY_AMT = cursor.getColumnIndex(DBConstants.PAY_AMNT);
			int indexPAY_TYPE = cursor.getColumnIndex(DBConstants.PYMNT_TYPE);

			ArrayList<FloatPickupPettyCurrencyModel> multyPYMNTMSTModelArr = new ArrayList<FloatPickupPettyCurrencyModel>();
			if (cursor.moveToFirst()) {
				do {
					FloatPickupPettyCurrencyModel multyPmtmstModel = new FloatPickupPettyCurrencyModel();
					multyPmtmstModel.setCURR_ABBR(cursor
							.getString(indexCurremcy));
					multyPmtmstModel.setEX_RATE(cursor.getFloat(indexEXRate));
					multyPmtmstModel.setLOC_AMNT(cursor.getFloat(indexLOC_AMT));
					multyPmtmstModel.setOPERATOR(cursor
							.getString(indexOperator));
					multyPmtmstModel.setPAY_AMNT(cursor.getFloat(indexPAY_AMT));
					multyPmtmstModel.setPYMNT_TYPE(cursor
							.getString(indexPAY_TYPE));
					multyPYMNTMSTModelArr.add(multyPmtmstModel);
					Logger.d(TAG, "parseCursor" + multyPmtmstModel);
				} while (cursor.moveToNext());
			}
			return multyPYMNTMSTModelArr;
		} else {
			return null;
		}
	}

	@Override
	protected void updateDataIntoDatabase() {

	}

	@Override
	protected void deleteDatafromDatabse() {

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

	private boolean checkDataAlreadyPresent(FloatPickupPettyCurrencyModel model
			) {
		String whrQry = DBConstants.PYMNT_TYPE + "=? AND " + DBConstants.CURR_ABBR
				+ "=?";
		String[] whrArg = { model.getPYMNT_TYPE(),
				model.getCURR_ABBR() };

		Cursor c = mSQLiteDB.query(mOperationalResult.getTableName(), null,
				whrQry, whrArg, null, null, null);
		if (c != null && c.getCount() == 1) {
			return true;
		}
		return false;
	}

	private long insertInfo(Object model) {

		ArrayList<FloatPickupPettyCurrencyModel> dataModel = (ArrayList<FloatPickupPettyCurrencyModel>) model;
		int numberOFRowsCreated = 0;
		for (FloatPickupPettyCurrencyModel FloatModel : dataModel) {
			boolean isPresent = checkDataAlreadyPresent(FloatModel);
			if (isPresent) {
				updateInfo(FloatModel);
			} else {
				ContentValues initValues = FloatPickupPettyCurrencyModel
						.getcontentvalues(FloatModel);
				/*long row = mSQLiteDB.insert(mOperationalResult.getTableName(),
						null, initValues);*/
				long row =mSQLiteDB.insertWithOnConflict(mOperationalResult.getTableName(), BaseColumns._ID, initValues, SQLiteDatabase.CONFLICT_REPLACE);
				
				if (row > 0) {
					numberOFRowsCreated++;
				}
			}
		}

		if (numberOFRowsCreated == dataModel.size()) {
			return numberOFRowsCreated;
		} else {
			return -1;
		}
	}
	
	private long updateInfo(FloatPickupPettyCurrencyModel model) {
		Logger.d(TAG, "updateInfo:" + model.toString());

		String whrQry = DBConstants.PYMNT_TYPE + "=? AND " + DBConstants.CURR_ABBR + "=?";
		String[] whrArg = { model.getPYMNT_TYPE(), model.getCURR_ABBR() };

		ContentValues initValues = FloatPickupPettyCurrencyModel
				.getcontentvalues(model);

		long row = mSQLiteDB.update(mOperationalResult.getTableName(),
				initValues, whrQry, whrArg);

		return row;
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
