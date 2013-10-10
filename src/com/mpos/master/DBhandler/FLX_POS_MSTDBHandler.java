package com.mpos.master.DBhandler;

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
import com.mpos.master.model.FLX_POS_Model;

public class FLX_POS_MSTDBHandler extends DBHandler {

	public static final String TAG = Constants.APP_TAG
			+ FLX_POS_MSTDBHandler.class.getSimpleName();

	public FLX_POS_MSTDBHandler(HandlerInf handlerInf, String[] dbQueryParam,
			String tableName, int dBOperation, int resultCode,
			boolean isUpdateDB) {
		super(handlerInf, dbQueryParam, tableName, dBOperation, resultCode,
				isUpdateDB);
	}

	public FLX_POS_MSTDBHandler(HandlerInf handlerInf, Object model,
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

		Cursor c = mSQLiteDB.query(mOperationalResult.getTableName(), null,
				whereQuery, mOperationalResult.getDBOperationalParam()
						.getQueryParam(), null, null, null);

		Bundle bundle = new Bundle();
		FLX_POS_Model flxPosModel = parseCursor(c);
		bundle.putSerializable(Constants.RESULTCODEBEAN, flxPosModel);
		this.mOperationalResult.setResult(bundle);
		c.close();

		this.mOperationalResult.setOperationalCode(Constants.SELECT);
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

		FLX_POS_Model dataModel = (FLX_POS_Model) model;
		if (dataModel != null) {
			ContentValues initValues = FLX_POS_Model
					.getcontentvalues(dataModel);
			/*long row = mSQLiteDB.insert(mOperationalResult.getTableName(),
					null, initValues);*/
			long row =mSQLiteDB.insertWithOnConflict(mOperationalResult.getTableName(), BaseColumns._ID, initValues, SQLiteDatabase.CONFLICT_REPLACE);
			
			return row;
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

	/**
	 * Method- To get respective column name index
	 * 
	 * @param cursor
	 * @return
	 * 
	 */
	private FLX_POS_Model parseCursor(Cursor cursor) {
		if (cursor.getCount() > 0) {
			int indexbillheading1 = cursor.getColumnIndex(DBConstants.Bill_Heading1);
			int indexbillheading2 = cursor
					.getColumnIndex(DBConstants.Bill_Heading2);
			int indexbillheading3 = cursor
					.getColumnIndex(DBConstants.Bill_Heading3);
			int indexbillheading4 = cursor.getColumnIndex(DBConstants.Bill_Heading4);
			int indexbillheading5 = cursor.getColumnIndex(DBConstants.Bill_Heading5);
			int indexbillheading6 = cursor
					.getColumnIndex(DBConstants.Bill_Heading6);
			int indexbillheading7 = cursor.getColumnIndex(DBConstants.Bill_Heading7);
			int indexbillheading8 = cursor.getColumnIndex(DBConstants.Bill_Heading8);

			
			FLX_POS_Model flxPosModel = new FLX_POS_Model();
			if (cursor.moveToFirst()) {
				do {
					flxPosModel.setBILLHEADING1(cursor.getString(indexbillheading1));
					flxPosModel.setBILLHEADING2(cursor.getString(indexbillheading2));
					flxPosModel.setBILLHEADING3(cursor.getString(indexbillheading3));
					flxPosModel.setBILLHEADING4(cursor.getString(indexbillheading4));
					flxPosModel.setBILLHEADING5(cursor.getString(indexbillheading5));
					flxPosModel.setBILLHEADING6(cursor.getString(indexbillheading6));
					flxPosModel.setBILLHEADING7(cursor.getString(indexbillheading7));
					flxPosModel.setBILLHEADING8(cursor.getString(indexbillheading8));
					
				} while (cursor.moveToNext());
			}
			return flxPosModel;
		} else {
			return null;
		}
	}

}
