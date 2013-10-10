package com.mpos.transactions.DBHandler;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;

import com.mpos.framework.common.Constants;
import com.mpos.framework.common.DBConstants;
import com.mpos.framework.common.Logger;
import com.mpos.framework.common.OperationalResult;
import com.mpos.framework.handler.DBHandler;
import com.mpos.framework.inf.HandlerInf;
import com.mpos.transactions.model.FLX_POS_TXNModel;

public class FLX_POS_TXNDBHandler extends DBHandler {

	public static final String TAG = Constants.APP_TAG
			+ FLX_POS_TXNDBHandler.class.getSimpleName();

	public FLX_POS_TXNDBHandler(HandlerInf handlerInf, String[] dbQueryParam,
			String tableName, int dBOperation, int resultCode,
			boolean isUpdateDB) {
		super(handlerInf, dbQueryParam, tableName, dBOperation, resultCode,
				isUpdateDB);
	}

	public FLX_POS_TXNDBHandler(HandlerInf handlerInf, Object model,
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

		Logger.d(TAG, "whrQuery:" + whereQuery);
		Logger.d(TAG, "param:"
				+ mOperationalResult.getDBOperationalParam().getQueryParam()[0]);

		Cursor c = null;
		if (whereQuery != null) {
			c = mSQLiteDB.query(mOperationalResult.getTableName(), null,
					whereQuery, mOperationalResult.getDBOperationalParam()
							.getQueryParam(), null, null, null);
		} else {
			c = mSQLiteDB.query(mOperationalResult.getTableName(), null, null,
					null, null, null, null);
		}

		Bundle bundle = new Bundle();
		FLX_POS_TXNModel flxPosModel = parseCursor(c);
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

		if (lRowAffected >= 0) {
			this.mOperationalResult
					.setRequestResponseCode(OperationalResult.SUCCESS);
		} else {
			this.mOperationalResult
					.setRequestResponseCode(OperationalResult.DB_ERROR);
		}
	}

	private long insertInfo(Object model) {

		FLX_POS_TXNModel dataModel = (FLX_POS_TXNModel) model;
		if (dataModel != null) {
			if (checkDataAlreadyPresent(dataModel)) {
				updateInfo(dataModel);
				return 1;
			} else {

				ContentValues initValues = FLX_POS_TXNModel
						.getcontentvalues(dataModel);
				long row = mSQLiteDB.insert(mOperationalResult.getTableName(),
						null, initValues);
				Logger.d(TAG, "row:" + row);
				return row;
			}
		}
		return -1;
	}

	private long updateInfo(FLX_POS_TXNModel flx_pos_txn_MODEL) {
		Logger.d(TAG, "updateInfo:" + flx_pos_txn_MODEL.toString());

		String whrQry = DBConstants.BILL_SCNCD + "=?";
		String[] whrArg = { flx_pos_txn_MODEL.getBILL_SCNCD() };

		ContentValues initValues = FLX_POS_TXNModel
				.getcontentvalues(flx_pos_txn_MODEL);

		long row = mSQLiteDB.update(mOperationalResult.getTableName(),
				initValues, whrQry, whrArg);

		return row;
	}

	private boolean checkDataAlreadyPresent(FLX_POS_TXNModel flx_pos_txn_MODEL) {
		String whrQry = DBConstants.BILL_SCNCD + "=?";
		String[] whrArg = { flx_pos_txn_MODEL.getBILL_SCNCD() };

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
	private FLX_POS_TXNModel parseCursor(Cursor cursor) {
		if (cursor.getCount() > 0) {
			int indexbillheading1 = cursor
					.getColumnIndex(DBConstants.BILL_HD_VALUE1);
			int indexbillheading2 = cursor
					.getColumnIndex(DBConstants.BILL_HD_VALUE2);
			int indexbillheading3 = cursor
					.getColumnIndex(DBConstants.BILL_HD_VALUE3);
			int indexbillheading4 = cursor
					.getColumnIndex(DBConstants.BILL_HD_VALUE4);
			int indexbillheading5 = cursor
					.getColumnIndex(DBConstants.BILL_HD_VALUE5);
			int indexbillheading6 = cursor
					.getColumnIndex(DBConstants.BILL_HD_VALUE6);
			int indexbillheading7 = cursor
					.getColumnIndex(DBConstants.BILL_HD_VALUE7);
			int indexbillheading8 = cursor
					.getColumnIndex(DBConstants.BILL_HD_VALUE8);
			int indexbillscncd = cursor.getColumnIndex(DBConstants.BILL_SCNCD);

			FLX_POS_TXNModel flxPosModel = new FLX_POS_TXNModel();
			if (cursor.moveToFirst()) {
				do {
					flxPosModel.setBILLHEADING1(cursor
							.getString(indexbillheading1));
					flxPosModel.setBILLHEADING2(cursor
							.getString(indexbillheading2));
					flxPosModel.setBILLHEADING3(cursor
							.getString(indexbillheading3));
					flxPosModel.setBILLHEADING4(cursor
							.getString(indexbillheading4));
					flxPosModel.setBILLHEADING5(cursor
							.getString(indexbillheading5));
					flxPosModel.setBILLHEADING6(cursor
							.getString(indexbillheading6));
					flxPosModel.setBILLHEADING7(cursor
							.getString(indexbillheading7));
					flxPosModel.setBILLHEADING8(cursor
							.getString(indexbillheading8));
					flxPosModel.setBILL_SCNCD(cursor.getString(indexbillscncd));

				} while (cursor.moveToNext());
			}
			return flxPosModel;
		} else {
			return null;
		}
	}

}
