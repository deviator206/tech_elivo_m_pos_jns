/*
 * Copyright (C) 2013 USAP Pvt. Ltd. All Rights Reserved.
 * jaggarnaut-studios pvt. ltd. PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.mpos.transactions.DBHandler;

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
import com.mpos.home.model.BillTransactionModel;

/**
 * Description-
 * 
 * @author jaggarnaut-studios
 */

public class BIllTxnDBHandler extends DBHandler {

	public static final String TAG = Constants.APP_TAG
			+ BIllTxnDBHandler.class.getSimpleName();

	public BIllTxnDBHandler(HandlerInf handlerInf, String[] dbQueryParam,
			String tableName, int dBOperation, int resultCode, boolean isUpdate) {
		super(handlerInf, dbQueryParam, tableName, dBOperation, resultCode,
				isUpdate);

	}

	public BIllTxnDBHandler(HandlerInf handlerInf, Object model,
			String tableName, int dBOperation, int resultCode, boolean isUpdate) {
		super(handlerInf, model, tableName, dBOperation, resultCode, isUpdate);

	}

	private String whereQuery = null;
	private boolean isTxnHoldUpdate = false;// If TXN on hold
	private boolean isTxnHoldRetrieve = false;// If TXn retrieved back to
												// Billing

	public void setWhereQuery(String whereQuery) {
		this.whereQuery = whereQuery;
	}

	/**
	 * @param isTxnHoldUpdate
	 *            the isTxnHoldUpdate to set
	 */
	public void setTxnHoldUpdate(boolean isTxnHoldUpdate) {
		this.isTxnHoldUpdate = isTxnHoldUpdate;
	}

	/**
	 * 
	 * @param isTxnHoldRetrieve
	 */
	public void setTxnHoldRetrieve(boolean isTxnHoldRetrieve) {
		this.isTxnHoldRetrieve = isTxnHoldRetrieve;
	}

	@Override
	protected void getDatafromDataBase() {
		Logger.d(TAG, "getDatafromDataBase");
		Cursor cursor = null;

		cursor = getBillTxnDetails(mOperationalResult.getDBOperationalParam()
				.getQueryParam());

		if (cursor.getCount() > 0) {
			Bundle bundle = new Bundle();
			ArrayList<BillTransactionModel> billTxnModels = parseCursor(cursor);
			Logger.d(TAG, "getDatafromDataBase:" + billTxnModels.toString());

			bundle.putSerializable(Constants.RESULTCODEBEAN, billTxnModels);
			this.mOperationalResult.setResult(bundle);
		} else {
			this.mOperationalResult
					.setRequestResponseCode(OperationalResult.DB_ERROR);
		}

		cursor.close();

		this.mOperationalResult.setOperationalCode(Constants.SELECT);
	}

	/**
	 * Method- To retrieve data
	 * 
	 * @param selectionArgs
	 * 
	 * @return
	 */
	private Cursor getBillTxnDetails(String[] queryParam) {
		Logger.d(TAG, "getBillTxnDetails");

		if (whereQuery == null) {
			whereQuery = DBConstants.BILL_SCNCD + "=? and "
					+ DBConstants.PRDCT_VOID + "=?";
		}

		Logger.d(TAG, "wq:" + whereQuery);
		Logger.d(TAG, "tbN: " + mOperationalResult.getTableName());
		// Logger.d(TAG, "qu:"+queryParam[0] + queryParam[1]);

		Cursor c = null;
		if (whereQuery.equalsIgnoreCase(DBConstants.ROW_ID)) {
			String MY_QUERY = "SELECT * FROM "
					+ mOperationalResult.getTableName();
			c = mSQLiteDB.rawQuery(MY_QUERY, null);
		} else {
			c = mSQLiteDB.query(mOperationalResult.getTableName(), null,
					whereQuery, queryParam, null, null, null);
		}

		return c;
	}

	@Override
	protected void updateDataIntoDatabase() {
		long lRowAffected = 0; // default

		lRowAffected = updateInfo(this.mOperationalResult.getdBModel());

		this.mOperationalResult.setOperationalCode(Constants.UPDATE);

		if (lRowAffected > 0) {
			this.mOperationalResult
					.setRequestResponseCode(OperationalResult.SUCCESS);
		} else {
			this.mOperationalResult
					.setRequestResponseCode(OperationalResult.MASTER_ERROR);
		}
	}

	/**
	 * Method- Update Info in Table
	 * 
	 * @param object
	 * @return long rowAffected
	 */
	@SuppressWarnings("unchecked")
	private long updateInfo(Object model) {
		Logger.d(TAG, "updateInfo:" + model.toString());
		long row = -1;

		if (isTxnHoldUpdate) {
			isTxnHoldUpdate = false;
			ArrayList<BillTransactionModel> models = (ArrayList<BillTransactionModel>) model;
			int numberOFRowsCreated = 0;

			for (BillTransactionModel billTxnmodel : models) {

				if (isTxnHoldRetrieve) {
					billTxnmodel.setTXN_TYPE(Constants.HELD_TXN);
				} else {
					billTxnmodel.setTXN_TYPE(Constants.BILLING_TXN);
				}
				Logger.d(TAG, "updateInfo:inside:" + billTxnmodel.toString());

				ContentValues initValues = BillTransactionModel
						.getcontentvalues(billTxnmodel);

				String whrQry = DBConstants.ROW_ID + "=? and "
						+ DBConstants.TXN_BILL_SCNCD + "=? and "
						+ DBConstants.TXN_PRDCT_CODE + "=?";

				// String whrQry = DBConstants.TXN_PRDCT_CODE + "=?";
				String[] whrArg = { billTxnmodel.getRow_id(),
						billTxnmodel.getBILL_SCNCD(),
						billTxnmodel.getPRDCT_CODE() };

				row = mSQLiteDB.update(mOperationalResult.getTableName(),
						initValues, whrQry, whrArg);

				if (row > 0) {
					numberOFRowsCreated++;
				}
			}

			if (numberOFRowsCreated == models.size()) {
				Logger.d(TAG, "No. Of Rows:" + numberOFRowsCreated);
				return numberOFRowsCreated;
			} else {
				return -1;
			}
		} // Item Print desc update and 
		else {// Delete bill panel item case
			BillTransactionModel dataModel = (BillTransactionModel) model;

			ContentValues initValues = BillTransactionModel
					.getcontentvalues(dataModel);

			/*
			 * String whrQry = DBConstants.TXN_BILL_SCNCD + "=? and " +
			 * DBConstants.TXN_PRDCT_CODE + "=?"; String[] whrArg = {
			 * dataModel.getBILL_SCNCD(), dataModel.getPRDCT_CODE() };
			 */

			String whrQry = DBConstants.ROW_ID + "=? and "
					+ DBConstants.TXN_BILL_SCNCD + "=? and "
					+ DBConstants.TXN_PRDCT_CODE + "=?";
			String[] whrArg = { dataModel.getRow_id(),
					dataModel.getBILL_SCNCD(), dataModel.getPRDCT_CODE() };

			row = mSQLiteDB.update(mOperationalResult.getTableName(),
					initValues, whrQry, whrArg);
		}

		return row;
	}

	@Override
	protected void deleteDatafromDatabse() {
		long lRowAffected = 0; // default

		String[] whrArgs = this.mOperationalResult.getDBOperationalParam()
				.getQueryParam();
		String whrQry = DBConstants.TXN_BILL_SCNCD + "=?";

		lRowAffected = mSQLiteDB.delete(mOperationalResult.getTableName(),
				whrQry, whrArgs);

		this.mOperationalResult.setOperationalCode(Constants.DELETE);

		if (lRowAffected > 0) {
			this.mOperationalResult
					.setRequestResponseCode(OperationalResult.SUCCESS);
		} else {
			this.mOperationalResult
					.setRequestResponseCode(OperationalResult.MASTER_ERROR);
		}
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

	/**
	 * Method- Insert Info info in Table
	 * 
	 * @param object
	 * @return long rowAffected
	 */
	private long insertInfo(Object model) {
		Logger.d(TAG, "insertInfo:" + model.toString());

		BillTransactionModel dataModel = (BillTransactionModel) model;

		ContentValues initValues = BillTransactionModel
				.getcontentvalues(dataModel);

		long row = mSQLiteDB.insert(mOperationalResult.getTableName(), null,
				initValues);

		return row;
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
	 * Method- To get respective column name index
	 * 
	 * @param cursor
	 * @return
	 * 
	 */
	private ArrayList<BillTransactionModel> parseCursor(Cursor cursor) {
		Logger.d(TAG, "parseCursor");

		int indexRowId = cursor.getColumnIndex(DBConstants.ROW_ID);
		int indexTxnCmpnyCode = cursor
				.getColumnIndex(DBConstants.TXN_CMPNY_CODE);
		int indexTxnBrnchCode = cursor
				.getColumnIndex(DBConstants.TXN_BRNCH_CODE);
		int indexTxnBilScncd = cursor
				.getColumnIndex(DBConstants.TXN_BILL_SCNCD);
		int indexTxnType = cursor.getColumnIndex(DBConstants.TXN_TXN_TYPE);
		int indexTxnBilRunDte = cursor
				.getColumnIndex(DBConstants.TXN_BILL_RUN_DATE);
		int indexTxnPrdctCode = cursor
				.getColumnIndex(DBConstants.TXN_PRDCT_CODE);
		int indexPrdctLngDesc = cursor
				.getColumnIndex(DBConstants.TXN_PRDCT_LNG_DSCRPTN);
		int indexPrdctPrc = cursor.getColumnIndex(DBConstants.PRDCT_PRC);
		int indexPrdctQty = cursor.getColumnIndex(DBConstants.PRDCT_QNTY);
		int indexPrdctDisc = cursor.getColumnIndex(DBConstants.PRDCT_DSCNT);
		int indexPrdctAmnt = cursor.getColumnIndex(DBConstants.PRDCT_AMNT);
		int indexPymntModeDscnt = cursor
				.getColumnIndex(DBConstants.PRDCT_PYMNT_MODE_DSCNT);
		int indexTxnPrdctVatCode = cursor
				.getColumnIndex(DBConstants.TXN_PRDCT_VAT_CODE);
		int indexPrdctVatAmt = cursor
				.getColumnIndex(DBConstants.PRDCT_VAT_AMNT);
		int indexPrdctVoid = cursor.getColumnIndex(DBConstants.PRDCT_VOID);
		int indexUsrAuthCtd = cursor.getColumnIndex(DBConstants.USR_ANTHNTCTD);
		int indexPrdctScnd = cursor.getColumnIndex(DBConstants.PRDCT_SCND);
		int indexSlsManCode = cursor.getColumnIndex(DBConstants.SLS_MAN_CODE);
		int indexBillPrnted = cursor.getColumnIndex(DBConstants.BILL_PRNTD);
		int indexPrdctVatExmpt = cursor
				.getColumnIndex(DBConstants.PRDCT_VAT_EXMPT);
		int indexTxnPrdctCostPrce = cursor
				.getColumnIndex(DBConstants.TXN_PRDCT_COST_PRCE);
		int indexAutoGrnDone = cursor.getColumnIndex(DBConstants.AUTO_GRN_DONE);
		int indexEmptyDone = cursor.getColumnIndex(DBConstants.EMPTY_DONE);
		int indexZedNo = cursor.getColumnIndex(DBConstants.ZED_NO);
		int indexSrNo = cursor.getColumnIndex(DBConstants.SR_NO);
		int indexPck = cursor.getColumnIndex(DBConstants.PACK);
		int indexPckQty = cursor.getColumnIndex(DBConstants.PACKQTY);
		int indexPckPrce = cursor.getColumnIndex(DBConstants.PACKPRCE);
		int indexLineDisc = cursor.getColumnIndex(DBConstants.LINEDISC);
		int indexScanCode = cursor.getColumnIndex(DBConstants.SCAN_CODE);
		int indexProcessedItem = cursor
				.getColumnIndex(DBConstants.PROCESSED_ITEM);

		ArrayList<BillTransactionModel> billTxnModelArr = new ArrayList<BillTransactionModel>();
		if (cursor.moveToFirst()) {
			do {
				BillTransactionModel billTxnModel = new BillTransactionModel();

				billTxnModel
						.setRow_id(String.valueOf(cursor.getInt(indexRowId)));
				billTxnModel.setAUTO_GRN_DONE(cursor
						.getString(indexAutoGrnDone));
				billTxnModel.setBILL_PRNTD(cursor.getString(indexBillPrnted));
				billTxnModel.setBILL_RUN_DATE(cursor
						.getString(indexTxnBilRunDte));
				billTxnModel.setBILL_SCNCD(cursor.getString(indexTxnBilScncd));

				billTxnModel.setBRNCH_CODE(cursor.getString(indexTxnBrnchCode));
				billTxnModel.setCMPNY_CODE(cursor.getString(indexTxnCmpnyCode));
				billTxnModel.setEMPTY_DONE(cursor.getString(indexEmptyDone));
				billTxnModel.setLINEDISC(Double.parseDouble(cursor
						.getString(indexLineDisc)));
				billTxnModel.setPACK(cursor.getString(indexPck));
				billTxnModel.setPACKPRCE(Double.parseDouble(cursor
						.getString(indexPckPrce)));
				billTxnModel.setPACKQTY(Double.parseDouble(cursor
						.getString(indexPckQty)));
				billTxnModel.setPRDCT_AMNT(Double.parseDouble(cursor
						.getString(indexPrdctAmnt)));
				billTxnModel.setPRDCT_CODE(cursor.getString(indexTxnPrdctCode));
				billTxnModel.setPRDCT_COST_PRCE(Double.parseDouble(cursor
						.getString(indexTxnPrdctCostPrce)));
				billTxnModel.setPRDCT_DSCNT(Double.parseDouble(cursor
						.getString(indexPrdctDisc)));
				billTxnModel.setPRDCT_LNG_DSCRPTN(cursor
						.getString(indexPrdctLngDesc));
				billTxnModel.setPRDCT_PRC(Double.parseDouble(cursor
						.getString(indexPrdctPrc)));
				billTxnModel.setPRDCT_PYMNT_MODE_DSCNT(Double
						.parseDouble(cursor.getString(indexPymntModeDscnt)));
				billTxnModel.setPRDCT_QNTY(Double.parseDouble(cursor
						.getString(indexPrdctQty)));
				billTxnModel.setPRDCT_SCND(cursor.getString(indexPrdctScnd));
				billTxnModel.setPRDCT_VAT_AMNT(Double.parseDouble(cursor
						.getString(indexPrdctVatAmt)));
				billTxnModel.setPRDCT_VAT_CODE(cursor
						.getString(indexTxnPrdctVatCode));
				billTxnModel.setPRDCT_VAT_EXMPT(cursor
						.getString(indexPrdctVatExmpt));
				billTxnModel.setPRDCT_VOID(cursor.getString(indexPrdctVoid));

				billTxnModel.setSCAN_CODE(cursor.getString(indexScanCode));
				billTxnModel.setSLS_MAN_CODE(cursor.getString(indexSlsManCode));
				billTxnModel.setSR_NO(Integer.parseInt(cursor
						.getString(indexSrNo)));

				billTxnModel.setTXN_TYPE(cursor.getString(indexTxnType));
				billTxnModel
						.setUSR_ANTHNTCTD(cursor.getString(indexUsrAuthCtd));
				billTxnModel.setZED_NO(Integer.parseInt(cursor
						.getString(indexZedNo)));
				String isProcessed = cursor.getString(indexProcessedItem);
				if (isProcessed.equalsIgnoreCase("true")) {
					billTxnModel.setProcessedItem(true);
				} else {
					billTxnModel.setProcessedItem(false);
				}

				billTxnModelArr.add(billTxnModel);
			} while (cursor.moveToNext());
		}
		return billTxnModelArr;
	}

}
