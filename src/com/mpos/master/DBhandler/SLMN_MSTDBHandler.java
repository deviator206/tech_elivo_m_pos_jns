package com.mpos.master.DBhandler;

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
import com.mpos.master.model.SLMN_MST_Model;

public class SLMN_MSTDBHandler extends DBHandler {

	
	public static final String TAG = Constants.APP_TAG
			+ SLMN_MSTDBHandler.class.getSimpleName();

	public SLMN_MSTDBHandler(HandlerInf handlerInf, String[] dbQueryParam,
			String tableName, int dBOperation, int resultCode, boolean isUpdateDB) {
		super(handlerInf, dbQueryParam, tableName, dBOperation, resultCode,isUpdateDB);
	}

	public SLMN_MSTDBHandler(HandlerInf handlerInf, Object model,
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
		ArrayList<SLMN_MST_Model> salesPersonModelArr = parseCursor(c);
		bundle.putSerializable(Constants.RESULTCODEBEAN, salesPersonModelArr);
		this.mOperationalResult.setResult(bundle);
		c.close();

		this.mOperationalResult.setOperationalCode(Constants.SELECT);
	}

	private ArrayList<SLMN_MST_Model> parseCursor(Cursor cursor) {
		if (cursor.getCount() > 0) {
			Logger.d(TAG, "parseCursor");
			int indexbranchCode = cursor.getColumnIndex(DBConstants.BRNCH_CODE);
			int indexCode = cursor
					.getColumnIndex(DBConstants.SM_CODE);
			int indexActive = cursor
					.getColumnIndex(DBConstants.ACTIVE);
			int indexFromDate = cursor.getColumnIndex(DBConstants.FROM_DT);
			int indexToDate = cursor.getColumnIndex(DBConstants.TO_DT);
			int indexName = cursor
					.getColumnIndex(DBConstants.NAME);
			int indexOldCode = cursor.getColumnIndex(DBConstants.OLD_CODE);
			int indexRegionCode = cursor.getColumnIndex(DBConstants.REGION_CODE);
			int indexUploadFlag = cursor.getColumnIndex(DBConstants.Upload_Flg);

			ArrayList<SLMN_MST_Model> slmnMSTModelArr = new ArrayList<SLMN_MST_Model>();
			if (cursor.moveToFirst()) {
				do {
					SLMN_MST_Model slmn_mstModel = new SLMN_MST_Model();
					slmn_mstModel.setSlmn_branch_code(cursor.getString(indexbranchCode));
					slmn_mstModel.setSlmn_code(cursor.getString(indexCode));
					if(cursor.getString(indexActive).equalsIgnoreCase("true")){
						slmn_mstModel.setIs_slmn_active(true);
					}else{
						slmn_mstModel.setIs_slmn_active(false);
					}
					
					if (cursor.getString(indexUploadFlag).equalsIgnoreCase("Y")) {
						slmn_mstModel.setIs_slmn_upload_flg(true);
					} else {
						slmn_mstModel.setIs_slmn_upload_flg(false);
					}
					
					slmn_mstModel.setSlmn_from_dt(cursor.getString(indexFromDate));
					slmn_mstModel.setSlmn_to_dt(cursor.getString(indexToDate));
					slmn_mstModel.setSlmn_name(cursor.getString(indexName));
					slmn_mstModel.setSlmn_old_code(cursor.getString(indexOldCode));
					slmn_mstModel.setSlmn_region_code(cursor.getString(indexRegionCode));
					
					slmnMSTModelArr.add(slmn_mstModel);
				} while (cursor.moveToNext());
			}
			return slmnMSTModelArr;
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

		ArrayList<SLMN_MST_Model> dataModel = (ArrayList<SLMN_MST_Model>) model;
		int numberOFRowsCreated = -1;
		if (dataModel != null) {
			numberOFRowsCreated=0;
			for (SLMN_MST_Model salesMan_Model : dataModel) {
				ContentValues initValues = SLMN_MST_Model
						.getcontentvalues(salesMan_Model);
				long row = mSQLiteDB.insert(mOperationalResult.getTableName(),
						null, initValues);
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
