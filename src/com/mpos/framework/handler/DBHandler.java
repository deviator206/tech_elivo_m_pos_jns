/*
 * Copyright (C) 2013  All Rights Reserved.
 *  PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mpos.framework.handler;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mpos.application.MPOSApplication;
import com.mpos.framework.common.Constants;
import com.mpos.framework.common.DBOperationlParameter;
import com.mpos.framework.common.DatabaseHelper;
import com.mpos.framework.common.Logger;
import com.mpos.framework.inf.HandlerInf;

/**
 * Description- This is base class for the every DBhandler child class. This
 * class controls basic flow of each DBHandler base class.
 * 

 * 
 */
public abstract class DBHandler extends ActivityHandler {

	public static final String TAG = Constants.APP_TAG
			+ DBHandler.class.getSimpleName();

	// private OperationalResult mOperationalResult = new OperationalResult();
	public SQLiteOpenHelper mDataBaseHelper = null;
	public static SQLiteDatabase mSQLiteDB;
	protected int mQuery = 0;
	protected String[] mQueryParam = null;
	protected String[] mRequestedColumn = null;
	protected boolean isUpdateDb;
	protected DBOperationlParameter mDBOperationalParameter = new DBOperationlParameter();

	abstract protected void getDatafromDataBase();

	abstract protected void updateDataIntoDatabase();

	abstract protected void deleteDatafromDatabse();

	abstract protected void insertDataIntoDatabase();

	abstract protected void isDataAvailableIntoDatabase();

	abstract protected void getDBRecordCount();

	abstract protected void getDefaultDBVehicle();

	abstract protected void clearCache();

	protected void copyTable() {
		String table2 = (String) this.mOperationalResult.getdBModel();
		mSQLiteDB.execSQL("insert into " + table2 + " select * from "
				+ this.mOperationalResult.getTableName());
	}

	/**
	 * This constructor for Query Param
	 * 
	 * @param handlerInf
	 * @param dbQueryParam
	 * @param dBOperation
	 * @param resultCode
	 */
	public DBHandler(HandlerInf handlerInf, String[] dbQueryParam,
			String tableName, int dBOperation, int resultCode,
			boolean isUpdateDB) {
		super(handlerInf);
		DBOperationlParameter dbOperationalParameter = new DBOperationlParameter();
		mOperationalResult.setDBOperationalParam(dbOperationalParameter);

		// Set where query parameters
		mOperationalResult.getDBOperationalParam().setQueryParam(dbQueryParam);
		// Set Table to operate

		// Set operation type
		mOperationalResult.getDBOperationalParam().setOperation(dBOperation);
		mOperationalResult.setResultCode(resultCode);
		this.isUpdateDb = isUpdateDB;
		mDataBaseHelper = new DatabaseHelper(MPOSApplication.getContext());

		mOperationalResult.setTableName(tableName);
		Logger.v(TAG, "tablename: " + tableName);
	}

	/**
	 * This constructor for Model
	 * 
	 * @param handlerInf
	 * @param model
	 * @param tableName
	 * @param dBOperation
	 * @param resultCode
	 */
	public DBHandler(HandlerInf handlerInf, Object model, String tableName,
			int dBOperation, int resultCode, boolean isUpdateDB) {
		super(handlerInf);
		DBOperationlParameter dbOperationalParameter = new DBOperationlParameter();
		mOperationalResult.setDBOperationalParam(dbOperationalParameter);

		// Set insert model data
		mOperationalResult.setdBModel(model);
		// Set Table to operate

		// Set operation type
		mOperationalResult.getDBOperationalParam().setOperation(dBOperation);
		mOperationalResult.setResultCode(resultCode);
		this.isUpdateDb = isUpdateDB;

		mDataBaseHelper = new DatabaseHelper(MPOSApplication.getContext());

		mOperationalResult.setTableName(tableName);
		Logger.v(TAG, "tablename: " + tableName);
	}

	protected void getDataBaseConnection() {
		if (mSQLiteDB == null) {
			if (mDataBaseHelper != null)
				mDataBaseHelper.close();
			mSQLiteDB = mDataBaseHelper.getWritableDatabase();
		} else if (!mSQLiteDB.isOpen()) {
			mSQLiteDB = mDataBaseHelper.getWritableDatabase();
		}

	}

	@Override
	protected Void doInBackground(Void... params) {
		return getResponse();
	}

	public Void getResponse() {
		mQuery = mOperationalResult.getDBOperationalParam().getQuery();
		mQueryParam = mOperationalResult.getDBOperationalParam()
				.getQueryParam();

		getDataBaseConnection();
		// mSQLiteDB.beginTransaction();
		// mSQLiteDB.setTransactionSuccessful();
		executeDBOperation();
		// mSQLiteDB.endTransaction();

		if (isUpdateDb) {
			if (mDataBaseHelper != null) {
				mDataBaseHelper.close();
			}
		} 
		closeDataBaseConnection();

		return null;
	}

	private void executeDBOperation() {
		int dbOperation = mOperationalResult.getDBOperationalParam()
				.getOperation();

		switch (dbOperation) {
		case DBOperationlParameter.SELECT:
			getDatafromDataBase();
			break;

		case DBOperationlParameter.INSERT:
			mSQLiteDB.beginTransaction();
			insertDataIntoDatabase();
			mSQLiteDB.setTransactionSuccessful();
			mSQLiteDB.endTransaction();
			break;

		case DBOperationlParameter.UPDATE:
			updateDataIntoDatabase();
			break;

		case DBOperationlParameter.DELETE:
			deleteDatafromDatabse();
			break;

		case DBOperationlParameter.AVAILABLE:
			isDataAvailableIntoDatabase();
			break;

		case DBOperationlParameter.GET_COUNT:
			getDBRecordCount();
			break;

		case DBOperationlParameter.DEFAULT_VEHICLE:
			getDefaultDBVehicle();
			break;

		case DBOperationlParameter.CLEAR_CACHE:
			clearCache();
			break;

		case DBOperationlParameter.COPY_TABLE:
			/*
			 * try { copyDataBase(); } catch (IOException e) {
			 * e.printStackTrace(); }
			 */
			copyTable();
			break;
		case DBOperationlParameter.DROP_TABLE:

			break;
		}
	}

	protected void closeDataBaseConnection() {
		// mSQLiteDB.close();
	}

}
