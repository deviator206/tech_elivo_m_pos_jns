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
import com.mpos.master.model.DNMTN_MSTModel;

public class DNMTN_MSTDBHandler extends DBHandler {

	
	public static final String TAG = Constants.APP_TAG
			+ DNMTN_MSTDBHandler.class.getSimpleName();

	public DNMTN_MSTDBHandler(HandlerInf handlerInf, String[] dbQueryParam,
			String tableName, int dBOperation, int resultCode, boolean isUpdateDB) {
		super(handlerInf, dbQueryParam, tableName, dBOperation, resultCode,isUpdateDB);

	}

	public DNMTN_MSTDBHandler(HandlerInf handlerInf, Object model,
			String tableName, int dBOperation, int resultCode, boolean isUpdateDB) {
		super(handlerInf, model, tableName, dBOperation, resultCode,isUpdateDB);
	}

	@Override
	protected void getDatafromDataBase() {
		Logger.d(TAG, "getDatafromDataBase");

		Cursor c = mSQLiteDB.query(mOperationalResult.getTableName(), null,
				null, mOperationalResult.getDBOperationalParam()
						.getQueryParam(), null, null, null);

		Bundle bundle = new Bundle();
		ArrayList<DNMTN_MSTModel> modelArr = parseCursor(c);
		bundle.putSerializable(Constants.RESULTCODEBEAN, modelArr);
		this.mOperationalResult.setResult(bundle);
		c.close();

		this.mOperationalResult.setOperationalCode(Constants.SELECT);
	}

	private ArrayList<DNMTN_MSTModel> parseCursor(Cursor cursor) {
		if (cursor.getCount() > 0) {
			Logger.d(TAG, "parseCursor");
			int indexcurrcode = cursor.getColumnIndex(DBConstants.CURR_CODE);
			int indexUnit = cursor.getColumnIndex(DBConstants.UNIT);
			int indexValue = cursor.getColumnIndex(DBConstants.VALUE);
			int indexUserName = cursor.getColumnIndex(DBConstants.USER_NAME);
			int indexCreatedOn = cursor.getColumnIndex(DBConstants.CREATED_ON);

			ArrayList<DNMTN_MSTModel> denominationArr = new ArrayList<DNMTN_MSTModel>();
			if (cursor.moveToFirst()) {
				do {
					DNMTN_MSTModel denominationModel = new DNMTN_MSTModel();
					denominationModel.setCreatedOn(cursor.getString(indexCreatedOn));
					denominationModel.setCURR_CODE(cursor.getString(indexcurrcode));
					denominationModel.setVALUE(cursor.getDouble(indexValue));
					denominationModel.setUserName(cursor.getString(indexUserName));
					denominationModel.setUNIT(cursor.getString(indexUnit));
					denominationArr.add(denominationModel);
				} while (cursor.moveToNext());
			}
			return denominationArr;
		} else {
			return null;
		}
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

		ArrayList<DNMTN_MSTModel> dataModel = (ArrayList<DNMTN_MSTModel>) model;
		int numberOFRowsCreated = -1;
		if (dataModel != null) {
			numberOFRowsCreated=0;
			for (DNMTN_MSTModel dnmtn_MST_Model : dataModel) {
				ContentValues initValues = DNMTN_MSTModel
						.getcontentvalues(dnmtn_MST_Model);
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
		return -1;
		
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
