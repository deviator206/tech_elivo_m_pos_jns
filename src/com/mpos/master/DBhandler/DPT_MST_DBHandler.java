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
import com.mpos.master.model.PRDCT_DPT_MST_Model;

public class DPT_MST_DBHandler extends DBHandler {

	public static final String TAG = Constants.APP_TAG
			+ DPT_MST_DBHandler.class.getSimpleName();

	public DPT_MST_DBHandler(HandlerInf handlerInf, String[] dbQueryParam,
			String tableName, int dBOperation, int resultCode, boolean isUpdateDB) {
		super(handlerInf, dbQueryParam, tableName, dBOperation, resultCode,isUpdateDB);
		// TODO Auto-generated constructor stub
	}

	public DPT_MST_DBHandler(HandlerInf handlerInf, Object model,
			String tableName, int dBOperation, int resultCode, boolean isUpdateDB) {
		super(handlerInf, model, tableName, dBOperation, resultCode,isUpdateDB);
	}

	@Override
	protected void getDatafromDataBase() {
		Logger.d(TAG, "getDatafromDataBase");
		Cursor cursor = null;

		cursor = getDPTDetails(mOperationalResult.getDBOperationalParam()
				.getQueryParam());

		if (cursor.getCount() > 0) {
			Bundle bundle = new Bundle();
			ArrayList<BaseModel> dptModelArr = parseCursor(cursor);
			Logger.d(TAG, "getDatafromDataBase: ###CHECK" + dptModelArr.toString());

			bundle.putSerializable(Constants.RESULTCODEBEAN, dptModelArr);
			this.mOperationalResult.setResult(bundle);
		} else {
			this.mOperationalResult.setRequestResponseCode(OperationalResult.DB_ERROR);
		}

		cursor.close();

		this.mOperationalResult.setOperationalCode(Constants.SELECT);
	}

	/**
	 * Method- To retrieve product department data as per classification
	 * 
	 * @param selectionArgs
	 * 
	 * @return
	 */
	private Cursor getDPTDetails(String[] queryParam) {
		Logger.d(TAG, "getDPTDetails");

		String whereQuery = DBConstants.DPT_FK_GRP_CODE + " =?";

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

		ArrayList<PRDCT_DPT_MST_Model> dataModels = (ArrayList<PRDCT_DPT_MST_Model>) model;
		int numberOFRowsCreated = 0;

		for (PRDCT_DPT_MST_Model prdct_Dpt_MST_Model : dataModels) {
			ContentValues initValues = PRDCT_DPT_MST_Model
					.getcontentvalues(prdct_Dpt_MST_Model);

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
		int indexDPTCode = cursor.getColumnIndex(DBConstants.DPT_CODE);
		int indexDPTName = cursor.getColumnIndex(DBConstants.DPT_NAME);
		int indexDPTFK = cursor.getColumnIndex(DBConstants.DPT_FK_GRP_CODE);

		ArrayList<BaseModel> dptModelArr = new ArrayList<BaseModel>();
		if (cursor.moveToFirst()) {
			do {
				PRDCT_DPT_MST_Model dptModel = new PRDCT_DPT_MST_Model();
				dptModel.setDpt_code(cursor.getString(indexDPTCode));
				dptModel.setDpt_name(cursor.getString(indexDPTName));
				dptModel.setFk_grp_code(cursor.getString(indexDPTFK));
				dptModelArr.add(dptModel);
			} while (cursor.moveToNext());
		}
		return dptModelArr;
	}

}
