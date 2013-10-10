package com.mpos.payment.DBHandler;

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
import com.mpos.payment.model.PAYMT_MST_MODEL;

public class PYMNTS_MSTDBHandler extends DBHandler {

	public static final String TAG = Constants.APP_TAG
			+ PYMNTS_MSTDBHandler.class.getSimpleName();

	public PYMNTS_MSTDBHandler(HandlerInf handlerInf, String[] dbQueryParam,
			String tableName, int dBOperation, int resultCode,
			boolean isUpdateDB) {
		super(handlerInf, dbQueryParam, tableName, dBOperation, resultCode,
				isUpdateDB);
	}

	public PYMNTS_MSTDBHandler(HandlerInf handlerInf, Object model,
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

		Cursor c = null;
		if (whereQuery != null) {
			whereQuery = this.whereQuery + " =?";
			Logger.d(TAG, "wQ:"+whereQuery);
			
			c = mSQLiteDB.query(mOperationalResult.getTableName(), null,
					whereQuery, mOperationalResult.getDBOperationalParam()
							.getQueryParam(), null, null, null);
		} else {
			c = mSQLiteDB.query(mOperationalResult.getTableName(), null, null,
					null, null, null, null);
		}

		Bundle bundle = new Bundle();
		ArrayList<PAYMT_MST_MODEL> pmtArr = parseCursor(c);
		//Logger.d(TAG, "pmtArr: "+pmtArr.toString());
		bundle.putSerializable(Constants.RESULTCODEBEAN, pmtArr);
		this.mOperationalResult.setResult(bundle);
		c.close();

		this.mOperationalResult.setOperationalCode(Constants.SELECT);
	}

	private ArrayList<PAYMT_MST_MODEL> parseCursor(Cursor cursor) {
		if (cursor.getCount() > 0) {
			Logger.d(TAG, "parseCursor");
			int indexbranchcode = cursor.getColumnIndex(DBConstants.BRNCH_CODE);
			int indexcardNum = cursor.getColumnIndex(DBConstants.CARDNO);
			int indexccAuthDate = cursor.getColumnIndex(DBConstants.CCAuthCode);
			int indexccDate = cursor.getColumnIndex(DBConstants.CCDt);
			int indexcompanyCode = cursor
					.getColumnIndex(DBConstants.CMPNY_CODE);
			int indexPAY_AMT = cursor.getColumnIndex(DBConstants.PAY_AMNT);
			int indexPAY_DTLS = cursor.getColumnIndex(DBConstants.PAY_DTLS);
			int indexPAY_MODE = cursor.getColumnIndex(DBConstants.PAY_MODE);
			int indexPAY_MODE_REF_CODE = cursor
					.getColumnIndex(DBConstants.PAY_MODE_REF_CODE);
			int indexPAY_MODE_REF_NUM = cursor
					.getColumnIndex(DBConstants.PAY_MODE_REF_NO);

			int indexPAY_RUNDAte = cursor
					.getColumnIndex(DBConstants.PAY_RUN_DATE);
			int indexPAY_SYSDate = cursor
					.getColumnIndex(DBConstants.PAY_SYS_DATE);
			int indexpntsAwarded = cursor
					.getColumnIndex(DBConstants.PNTS_AWARDED);
			int indexPntsRedeemed = cursor
					.getColumnIndex(DBConstants.PNTS_REDEEMED);
			int indexPrintDisc = cursor
					.getColumnIndex(DBConstants.PRPRTNTE_DISC);
			int indexpay_NUM = cursor.getColumnIndex(DBConstants.PYMNT_NMBR);
			int indexTillNum = cursor.getColumnIndex(DBConstants.TILL_NO);
			int indexTxnType = cursor.getColumnIndex(DBConstants.TXN_TYPE);
			int indexUserName = cursor.getColumnIndex(DBConstants.USR_NAME);
			int indexuserNameScncd = cursor
					.getColumnIndex(DBConstants.USR_NAME_SCND);

			ArrayList<PAYMT_MST_MODEL> pYMNTMSTModelArr = new ArrayList<PAYMT_MST_MODEL>();
			if (cursor.moveToFirst()) {
				do {
					PAYMT_MST_MODEL pmtmstModel = new PAYMT_MST_MODEL();
					pmtmstModel
							.setBRNCH_CODE(cursor.getString(indexbranchcode));
					pmtmstModel.setCARDNO(cursor.getString(indexcardNum));
					pmtmstModel
							.setCCAuthCode(cursor.getString(indexccAuthDate));
					pmtmstModel.setCCDt(cursor.getString(indexccDate));
					pmtmstModel.setCMPNY_CODE(cursor
							.getString(indexcompanyCode));
					pmtmstModel.setPAY_AMNT(cursor.getDouble(indexPAY_AMT));
					pmtmstModel.setPAY_DTLS(cursor.getString(indexPAY_DTLS));
					pmtmstModel.setPAY_MODE(cursor.getString(indexPAY_MODE));
					pmtmstModel.setPAY_MODE_REF_CODE(cursor
							.getString(indexPAY_MODE_REF_CODE));
					pmtmstModel.setPAY_MODE_REF_NO(cursor
							.getString(indexPAY_MODE_REF_NUM));
					pmtmstModel.setPAY_RUN_DATE(cursor
							.getString(indexPAY_RUNDAte));
					pmtmstModel.setPAY_SYS_DATE(cursor
							.getString(indexPAY_SYSDate));
					pmtmstModel.setPNTS_AWARDED(cursor
							.getString(indexpntsAwarded));
					pmtmstModel.setPNTS_REDEEMED(cursor
							.getString(indexPntsRedeemed));
					pmtmstModel.setPRPRTNTE_DISC(cursor
							.getString(indexPrintDisc));
					pmtmstModel.setPYMNT_NMBR(cursor.getString(indexpay_NUM));
					pmtmstModel.setTILL_NO(cursor.getString(indexTillNum));
					pmtmstModel.setTXN_TYPE(cursor.getString(indexTxnType));
					pmtmstModel.setUSR_NAME(cursor.getString(indexUserName));
					pmtmstModel.setUSR_NAME_SCND(cursor
							.getString(indexuserNameScncd));

					pYMNTMSTModelArr.add(pmtmstModel);
					Logger.d(TAG, "parseCursor"+pmtmstModel);
				} while (cursor.moveToNext());
			}
			return pYMNTMSTModelArr;
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

	private long insertInfo(Object model) {

		ArrayList<PAYMT_MST_MODEL> dataModel = (ArrayList<PAYMT_MST_MODEL>) model;

		int numberOFRowsCreated = 0;
		for (PAYMT_MST_MODEL payment_MST_Model : dataModel) {
			ContentValues initValues = PAYMT_MST_MODEL
					.getcontentvalues(payment_MST_Model);
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
