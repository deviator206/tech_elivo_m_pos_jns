/*
 * Copyright (C) 2013  All Rights Reserved.
 *  PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
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
import com.mpos.master.model.PRDCT_GRP_MST_Model;
import com.mpos.master.model.PRDCT_MST_Model;
import com.mpos.master.model.PrdctImageMstModel;

/**
 * Description-
 * 
 */
public class PRDCT_MST_DBHandler extends DBHandler {

	public static final String TAG = Constants.APP_TAG
			+ PRDCT_MST_DBHandler.class.getSimpleName();

	public PRDCT_MST_DBHandler(HandlerInf handlerInf, String[] dbQueryParam,
			String tableName, int dBOperation, int resultCode,
			boolean isUpdateDB) {
		super(handlerInf, dbQueryParam, tableName, dBOperation, resultCode,
				isUpdateDB);
		// TODO Auto-generated constructor stub
	}

	public PRDCT_MST_DBHandler(HandlerInf handlerInf, Object model,
			String tableName, int dBOperation, int resultCode,
			boolean isUpdateDB) {
		super(handlerInf, model, tableName, dBOperation, resultCode, isUpdateDB);
	}

	String whrQuery = null;

	public void setWhrQuery(String whrQuery) {
		this.whrQuery = whrQuery;
	}

	@Override
	protected void getDatafromDataBase() {
		Logger.d(TAG, "getDatafromDataBase");
		Cursor cursor = null;

		cursor = getPRDCTMSTDetails(mOperationalResult.getDBOperationalParam()
				.getQueryParam());

		if (cursor.getCount() > 0) {
			Bundle bundle = new Bundle();
			ArrayList<BaseModel> prdctModelArr = parseCursor(cursor);
			// Logger.d(TAG, "getDatafromDataBase: ## CHECK " + prdctModelArr.toString());

			bundle.putSerializable(Constants.RESULTCODEBEAN, prdctModelArr);
			this.mOperationalResult.setResult(bundle);
		} else {
			this.mOperationalResult
					.setRequestResponseCode(OperationalResult.DB_ERROR);
		}

		cursor.close();

		this.mOperationalResult.setOperationalCode(Constants.SELECT);
	}

	@Override
	protected void updateDataIntoDatabase() {
		long lRowAffected = 0; // default

		lRowAffected = updateInfo(this.mOperationalResult.getdBModel());

		this.mOperationalResult.setOperationalCode(Constants.UPDATE);

		Logger.d(TAG, "Row Sucess: " + lRowAffected);
		this.mOperationalResult
				.setRequestResponseCode(OperationalResult.SUCCESS);

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

		/*
		 * PrdctImageMstModel dataModel = (PrdctImageMstModel) model; String
		 * whrQry = DBConstants.PRDCT_CODE + "=?"; String[] whrArg = {
		 * dataModel.getPrdct_code() }; ContentValues initValues =
		 * PrdctImageMstModel .getcontentvalues(dataModel); long row =
		 * mSQLiteDB.update(mOperationalResult.getTableName(), initValues,
		 * whrQry, whrArg);
		 */

		ArrayList<PrdctImageMstModel> dataModels = (ArrayList<PrdctImageMstModel>) model;
		int numberOFRowsUpdated = 0;

		for (PrdctImageMstModel prdct_image_model : dataModels) {
			Logger.d(TAG, "Update: " + prdct_image_model.toString());
			if (prdct_image_model.getPrdct_img() != null
					&& prdct_image_model.getPrdct_img().length() > 0
					&& !prdct_image_model.getPrdct_img().equalsIgnoreCase(
							"null")) {

				String whrQry = DBConstants.PRDCT_CODE + "=?";
				String[] whrArg = { prdct_image_model.getPrdct_code() };
				ContentValues initValues = PrdctImageMstModel
						.getcontentvalues(prdct_image_model);

				long row = mSQLiteDB.update(mOperationalResult.getTableName(),
						initValues, whrQry, whrArg);

				if (row > 0) {
					numberOFRowsUpdated++;
				}
			}
		}

		Logger.d(TAG, "No. Of Rows:" + numberOFRowsUpdated);

		return numberOFRowsUpdated;
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
		Logger.d(TAG, "getSelectedPRDCTFromDB");
		Cursor cursor = null;

		cursor = getPRDCT(mOperationalResult.getDBOperationalParam()
				.getQueryParam());

		if (cursor.getCount() > 0) {
			Bundle bundle = new Bundle();
			ArrayList<BaseModel> prdctModels = parseCursor(cursor);
			// Logger.d(TAG, "getDatafromDataBase:" + prdctModel.toString());

			bundle.putSerializable(Constants.RESULTCODEBEAN, prdctModels);
			this.mOperationalResult.setResult(bundle);
		} else {
			this.mOperationalResult
					.setRequestResponseCode(OperationalResult.DB_ERROR);
		}

		cursor.close();

		this.mOperationalResult.setOperationalCode(Constants.SELECT);
	}

	/**
	 * Method - TO retrieve particular record
	 * 
	 * @param queryParam
	 * @return
	 */
	private Cursor getPRDCT(String[] queryParam) {
		Logger.d(TAG, "getPRDCT");
		Cursor c = null;
		if (whrQuery == null) {
			whrQuery = DBConstants.PRDCT_CODE + " =?";
			c = mSQLiteDB.query(mOperationalResult.getTableName(), null,
					whrQuery, queryParam, null, null, null);
		} else {
			c = mSQLiteDB.query(true, mOperationalResult.getTableName(), null,
					DBConstants.PRDCT_LNG_DSCRPTN + " LIKE '%" + queryParam[0]
							+ "%'", null, null, null, null, null);
		}

		return c;
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

		ArrayList<PRDCT_MST_Model> dataModels = (ArrayList<PRDCT_MST_Model>) model;
		int numberOFRowsCreated = 0;

		for (PRDCT_MST_Model prdct_MST_Model : dataModels) {
			ContentValues initValues = PRDCT_MST_Model
					.getcontentvalues(prdct_MST_Model);

			/*long row = mSQLiteDB.insert(mOperationalResult.getTableName(),
					null, initValues);*/
			long row =mSQLiteDB.insertWithOnConflict(mOperationalResult.getTableName(), BaseColumns._ID, initValues, SQLiteDatabase.CONFLICT_REPLACE);
			
			if (row > 0) {
				numberOFRowsCreated++;
			}
		}

		if (numberOFRowsCreated == dataModels.size()) {
			Logger.d(TAG, "No. Of Rows:" + numberOFRowsCreated);
			return numberOFRowsCreated;
		} else {
			return -1;
		}

	}

	/**
	 * Method- To retrieve product master data as per classification
	 * 
	 * @param selectionArgs
	 * 
	 * @return
	 */
	private Cursor getPRDCTMSTDetails(String[] selectionArgs) {
		Logger.d(TAG, "getPRDCTMSTDetails");

		String whereQuery = DBConstants.PRDCT_GRP_CODE + " =? and "
				+ DBConstants.PRDCT_DPT_CODE + " =? and "
				+ DBConstants.PRDCT_ANLYS_CODE + " =? and "
				+ DBConstants.PRDCT_BIN_LOC_CODE + " =? and "
				+ DBConstants.PRDCT_CP_VLTN + " !='I'";

		Logger.d(TAG, "ArgsLength:" + selectionArgs.length);
		for (String tag : selectionArgs) {
			Logger.d(TAG, "Args:" + tag);
		}

		Cursor c = mSQLiteDB.query(mOperationalResult.getTableName(), null,
				whereQuery, selectionArgs, null, null,
				DBConstants.DISP_ORDER_NO + " ASC");
		return c;
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
		int indexDisplayOrderNo = cursor
				.getColumnIndex(DBConstants.DISP_ORDER_NO);
		int indexPrdctAnlysCode = cursor
				.getColumnIndex(DBConstants.PRDCT_ANLYS_CODE);
		int indexBinLocCode = cursor
				.getColumnIndex(DBConstants.PRDCT_BIN_LOC_CODE);
		int indexClr = cursor.getColumnIndex(DBConstants.PRDCT_CLR);
		int indexPrdctCode = cursor.getColumnIndex(DBConstants.PRDCT_CODE);
		int indexPrdctCostPrice = cursor
				.getColumnIndex(DBConstants.PRDCT_COST_PRCE);
		int indexPrdctCpVltn = cursor.getColumnIndex(DBConstants.PRDCT_CP_VLTN);
		int indexPrdctDptCode = cursor
				.getColumnIndex(DBConstants.PRDCT_DPT_CODE);
		int indexPrdctDscrptn = cursor
				.getColumnIndex(DBConstants.PRDCT_DSCRPTN);
		int indexPrdctFixQty = cursor.getColumnIndex(DBConstants.PRDCT_FIX_QTY);
		int indexPrdctFixPrice = cursor
				.getColumnIndex(DBConstants.PRDCT_FIXED_PRCE);
		int indexPrdctGrpCode = cursor
				.getColumnIndex(DBConstants.PRDCT_GRP_CODE);
		int indexPrdctImg = cursor.getColumnIndex(DBConstants.PRDCT_IMG);
		int indexPrdctLngDscrptn = cursor
				.getColumnIndex(DBConstants.PRDCT_LNG_DSCRPTN);
		int indexPrdctMinQty = cursor.getColumnIndex(DBConstants.PRDCT_MIN_QTY);
		int indexPrdctPckng = cursor.getColumnIndex(DBConstants.PRDCT_PCKNG);
		int indexPrdctQty = cursor.getColumnIndex(DBConstants.PRDCT_QTY);
		int indexPrdctSellPrice = cursor
				.getColumnIndex(DBConstants.PRDCT_SELL_PRCE);
		int indexPrdctShrtDscrptn = cursor
				.getColumnIndex(DBConstants.PRDCT_SHRT_DSCRPTN);
		int indexPrdctSpplr = cursor.getColumnIndex(DBConstants.PRDCT_SPPLR);
		int indexPrdctSubClass = cursor
				.getColumnIndex(DBConstants.PRDCT_SUB_CLASS);
		int indexPrdctUnit = cursor.getColumnIndex(DBConstants.PRDCT_UNIT);
		int indexPrdctVatCode = cursor
				.getColumnIndex(DBConstants.PRDCT_VAT_CODE);

		ArrayList<BaseModel> prdctModelArr = new ArrayList<BaseModel>();
		if (cursor.moveToFirst()) {
			do {
				PRDCT_MST_Model prdctModel = new PRDCT_MST_Model();
				prdctModel.setDISP_ORDER_NO(Double.parseDouble(cursor
						.getString(indexDisplayOrderNo)));
				prdctModel.setPRDCT_ANLYS_CODE(cursor
						.getString(indexPrdctAnlysCode));
				prdctModel.setPRDCT_BIN_LOC_CODE(cursor
						.getString(indexBinLocCode));
				System.out.println("## "+cursor.getString(indexClr));
				prdctModel.setPRDCT_CLR(cursor.getString(indexClr));
				prdctModel.setPRDCT_CODE(cursor.getString(indexPrdctCode));
				prdctModel.setPRDCT_COST_PRCE(Double.parseDouble(cursor
						.getString(indexPrdctCostPrice)));
				prdctModel.setPRDCT_CP_VLTN(cursor.getString(indexPrdctCpVltn));
				prdctModel.setPRDCT_DPT_CODE(cursor
						.getString(indexPrdctDptCode));
				prdctModel
						.setPRDCT_DSCRPTN(cursor.getString(indexPrdctDscrptn));
				prdctModel.setPRDCT_FIX_QTY(cursor.getString(indexPrdctFixQty));

				prdctModel.setPRDCT_FIXED_PRCE(cursor
						.getString(indexPrdctFixPrice));

				prdctModel.setPRDCT_GRP_CODE(cursor
						.getString(indexPrdctGrpCode));

				// byte[] blob = cursor.getBlob(indexPrdctImg);
				// if (blob != null)
				String img = cursor.getString(indexPrdctImg);
				if (img != null)
					prdctModel.setPRDCT_IMG(img);
				else
					prdctModel.setPRDCT_IMG("");

				prdctModel.setPRDCT_LNG_DSCRPTN(cursor
						.getString(indexPrdctLngDscrptn));
				prdctModel.setPRDCT_MIN_QTY(cursor.getInt(indexPrdctMinQty));
				prdctModel.setPRDCT_PCKNG(Integer.parseInt(cursor
						.getString(indexPrdctPckng)));
				prdctModel.setPRDCT_QTY(Integer.parseInt(cursor
						.getString(indexPrdctQty)));
				prdctModel.setPRDCT_SELL_PRCE(Double.parseDouble(cursor
						.getString(indexPrdctSellPrice)));
				prdctModel.setPRDCT_SHRT_DSCRPTN(cursor
						.getString(indexPrdctShrtDscrptn));
				prdctModel.setPRDCT_SPPLR(cursor.getString(indexPrdctSpplr));
				prdctModel.setPRDCT_SUB_CLASS(cursor
						.getString(indexPrdctSubClass));
				prdctModel.setPRDCT_UNIT(cursor.getString(indexPrdctUnit));
				prdctModel.setPRDCT_VAT_CODE(cursor
						.getString(indexPrdctVatCode));

				prdctModelArr.add(prdctModel);
			} while (cursor.moveToNext());
		}
		return prdctModelArr;
	}

	/**
	 * Method- To get respective column name index
	 * 
	 * @param cursor
	 * @return
	 * 
	 */
	private BaseModel parsePrdctCursor(Cursor cursor) {
		Logger.d(TAG, "parsePrdctCursor");
		int indexDisplayOrderNo = cursor
				.getColumnIndex(DBConstants.DISP_ORDER_NO);
		int indexPrdctAnlysCode = cursor
				.getColumnIndex(DBConstants.PRDCT_ANLYS_CODE);
		int indexBinLocCode = cursor
				.getColumnIndex(DBConstants.PRDCT_BIN_LOC_CODE);

		int indexClr = cursor.getColumnIndex(DBConstants.PRDCT_CLR);

		int indexPrdctCode = cursor.getColumnIndex(DBConstants.PRDCT_CODE);
		int indexPrdctCostPrice = cursor
				.getColumnIndex(DBConstants.PRDCT_COST_PRCE);
		int indexPrdctCpVltn = cursor.getColumnIndex(DBConstants.PRDCT_CP_VLTN);
		int indexPrdctDptCode = cursor
				.getColumnIndex(DBConstants.PRDCT_DPT_CODE);
		int indexPrdctDscrptn = cursor
				.getColumnIndex(DBConstants.PRDCT_DSCRPTN);
		int indexPrdctFixQty = cursor.getColumnIndex(DBConstants.PRDCT_FIX_QTY);
		int indexPrdctFixPrice = cursor
				.getColumnIndex(DBConstants.PRDCT_FIXED_PRCE);
		int indexPrdctGrpCode = cursor
				.getColumnIndex(DBConstants.PRDCT_GRP_CODE);

		int indexPrdctImg = cursor.getColumnIndex(DBConstants.PRDCT_IMG);

		int indexPrdctLngDscrptn = cursor
				.getColumnIndex(DBConstants.PRDCT_LNG_DSCRPTN);
		int indexPrdctMinQty = cursor.getColumnIndex(DBConstants.PRDCT_MIN_QTY);
		int indexPrdctPckng = cursor.getColumnIndex(DBConstants.PRDCT_PCKNG);
		int indexPrdctQty = cursor.getColumnIndex(DBConstants.PRDCT_QTY);
		int indexPrdctSellPrice = cursor
				.getColumnIndex(DBConstants.PRDCT_SELL_PRCE);
		int indexPrdctShrtDscrptn = cursor
				.getColumnIndex(DBConstants.PRDCT_SHRT_DSCRPTN);
		int indexPrdctSpplr = cursor.getColumnIndex(DBConstants.PRDCT_SPPLR);
		int indexPrdctSubClass = cursor
				.getColumnIndex(DBConstants.PRDCT_SUB_CLASS);
		int indexPrdctUnit = cursor.getColumnIndex(DBConstants.PRDCT_UNIT);
		int indexPrdctVatCode = cursor
				.getColumnIndex(DBConstants.PRDCT_VAT_CODE);

		PRDCT_MST_Model prdctModel = null;
		if (cursor.moveToFirst()) {
			do {
				prdctModel = new PRDCT_MST_Model();
				prdctModel.setDISP_ORDER_NO(Double.parseDouble(cursor
						.getString(indexDisplayOrderNo)));
				prdctModel.setPRDCT_ANLYS_CODE(cursor
						.getString(indexPrdctAnlysCode));
				prdctModel.setPRDCT_BIN_LOC_CODE(cursor
						.getString(indexBinLocCode));
				System.out.println("##@ "+cursor.getString(indexClr));
				prdctModel.setPRDCT_CLR(cursor.getString(indexClr));
				prdctModel.setPRDCT_CODE(cursor.getString(indexPrdctCode));
				prdctModel.setPRDCT_COST_PRCE(Double.parseDouble(cursor
						.getString(indexPrdctCostPrice)));
				prdctModel.setPRDCT_CP_VLTN(cursor.getString(indexPrdctCpVltn));
				prdctModel.setPRDCT_DPT_CODE(cursor
						.getString(indexPrdctDptCode));
				prdctModel
						.setPRDCT_DSCRPTN(cursor.getString(indexPrdctDscrptn));
				prdctModel.setPRDCT_FIX_QTY(cursor.getString(indexPrdctFixQty));
				prdctModel.setPRDCT_FIXED_PRCE(cursor
						.getString(indexPrdctFixPrice));
				prdctModel.setPRDCT_GRP_CODE(cursor
						.getString(indexPrdctGrpCode));

				// byte[] blob = cursor.getBlob(indexPrdctImg);
				// if (blob != null)
				// prdctModel.setPRDCT_IMG(blob.toString());
				// else
				// prdctModel.setPRDCT_IMG("");
				String img = cursor.getString(indexPrdctImg);
				if (img != null)
					prdctModel.setPRDCT_IMG(img);
				else
					prdctModel.setPRDCT_IMG("");

				prdctModel.setPRDCT_LNG_DSCRPTN(cursor
						.getString(indexPrdctLngDscrptn));
				prdctModel.setPRDCT_MIN_QTY(cursor.getInt(indexPrdctMinQty));
				prdctModel.setPRDCT_PCKNG(Integer.parseInt(cursor
						.getString(indexPrdctPckng)));
				prdctModel.setPRDCT_QTY(Integer.parseInt(cursor
						.getString(indexPrdctQty)));
				prdctModel.setPRDCT_SELL_PRCE(Double.parseDouble(cursor
						.getString(indexPrdctSellPrice)));
				prdctModel.setPRDCT_SHRT_DSCRPTN(cursor
						.getString(indexPrdctShrtDscrptn));
				prdctModel.setPRDCT_SPPLR(cursor.getString(indexPrdctSpplr));
				prdctModel.setPRDCT_SUB_CLASS(cursor
						.getString(indexPrdctSubClass));
				prdctModel.setPRDCT_UNIT(cursor.getString(indexPrdctUnit));
				prdctModel.setPRDCT_VAT_CODE(cursor
						.getString(indexPrdctVatCode));

			} while (cursor.moveToNext());
		}
		return prdctModel;
	}

}
