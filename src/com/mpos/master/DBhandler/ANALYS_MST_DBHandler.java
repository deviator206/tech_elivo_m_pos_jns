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
import com.mpos.master.model.PRDCT_ANALYS_MST_Model;

public class ANALYS_MST_DBHandler extends DBHandler {

	public static final String TAG = Constants.APP_TAG
			+ ANALYS_MST_DBHandler.class.getSimpleName();

	public ANALYS_MST_DBHandler(HandlerInf handlerInf, String[] dbQueryParam,
			String tableName, int dBOperation, int resultCode, boolean isUpdateDB) {
		super(handlerInf, dbQueryParam, tableName, dBOperation, resultCode,isUpdateDB);
		// TODO Auto-generated constructor stub
	}

	public ANALYS_MST_DBHandler(HandlerInf handlerInf, Object model,
			String tableName, int dBOperation, int resultCode, boolean isUpdateDB) {
		super(handlerInf, model, tableName, dBOperation, resultCode,isUpdateDB);
	}

	@Override
	protected void getDatafromDataBase() {
		Logger.d(TAG, "getDatafromDataBase");
		Cursor cursor = null;

		cursor = getANALYSDetails(mOperationalResult.getDBOperationalParam()
				.getQueryParam());

		if (cursor.getCount() > 0) {
			Bundle bundle = new Bundle();
			ArrayList<BaseModel> analysModelArr = parseCursor(cursor);
			Logger.d(TAG, "getDatafromDataBase:" + analysModelArr.toString());

			bundle.putSerializable(Constants.RESULTCODEBEAN, analysModelArr);
			this.mOperationalResult.setResult(bundle);
		} else {
			this.mOperationalResult
					.setRequestResponseCode(OperationalResult.DB_ERROR);
		}

		cursor.close();

		this.mOperationalResult.setOperationalCode(Constants.SELECT);
	}

	/**
	 * Method- To retrieve product analysis data as per classification
	 * 
	 * @param selectionArgs
	 * 
	 * @return
	 */
	private Cursor getANALYSDetails(String[] queryParam) {
		Logger.d(TAG, "getANALYSDetails");

		String whereQuery = DBConstants.ANALYS_FK_DPT_CODE + " =?";

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
					.setRequestResponseCode(OperationalResult.DB_ERROR);
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
	 * Method- Insert Info info in Table
	 * 
	 * @param object
	 * @return long rowAffected
	 */
	private long insertInfo(Object model) {
		Logger.d(TAG, "insertInfo:" + model.toString());

		ArrayList<PRDCT_ANALYS_MST_Model> dataModels = (ArrayList<PRDCT_ANALYS_MST_Model>) model;
		int numberOFRowsCreated = 0;

		for (PRDCT_ANALYS_MST_Model prdct_Ana_MST_Model : dataModels) {
			ContentValues initValues = PRDCT_ANALYS_MST_Model
					.getcontentvalues(prdct_Ana_MST_Model);

			/*long row = mSQLiteDB.insert(mOperationalResult.getTableName(),
					null, initValues);*/
			long row =mSQLiteDB.insertWithOnConflict(mOperationalResult.getTableName(), BaseColumns._ID, initValues, SQLiteDatabase.CONFLICT_REPLACE);
			
			if (row > 0) {
				numberOFRowsCreated++;
			}
		}

		if (numberOFRowsCreated == dataModels.size()) {
			Logger.d(TAG, "No. Of Rows:"+numberOFRowsCreated);
			return numberOFRowsCreated;
		} else {
			return -1;
		}

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
		int indexANALYSCode = cursor.getColumnIndex(DBConstants.ANALYS_CODE);
		int indexANALYSName = cursor.getColumnIndex(DBConstants.ANALYA_NAME);
		int indexANALYSFK = cursor
				.getColumnIndex(DBConstants.ANALYS_FK_DPT_CODE);

		ArrayList<BaseModel> analysModelArr = new ArrayList<BaseModel>();
		if (cursor.moveToFirst()) {
			do {
				PRDCT_ANALYS_MST_Model analysModel = new PRDCT_ANALYS_MST_Model();
				analysModel.setAnalys_code(cursor.getString(indexANALYSCode));
				analysModel.setAnalys_name(cursor.getString(indexANALYSName));
				analysModel.setFk_dpt_code(cursor.getString(indexANALYSFK));
				analysModelArr.add(analysModel);
			} while (cursor.moveToNext());
		}
		return analysModelArr;
	}

}
