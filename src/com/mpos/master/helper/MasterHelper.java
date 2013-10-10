/*
 * Copyright (C) 2013  All Rights Reserved.
 *  PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.mpos.master.helper;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;

import android.app.Activity;

import com.mpos.application.MPOSApplication;
import com.mpos.framework.Util.UserSingleton;
import com.mpos.framework.common.Constants;
import com.mpos.framework.common.DBConstants;
import com.mpos.framework.common.DBOperationlParameter;
import com.mpos.framework.common.DatabaseHelper;
import com.mpos.framework.common.Logger;
import com.mpos.framework.common.OperationalResult;
import com.mpos.framework.handler.ActivityHandler;
import com.mpos.framework.handler.DBHandler;
import com.mpos.framework.helper.ActivityHelper;
import com.mpos.framework.inf.BaseResponseListener;
import com.mpos.login.NThandler.UserMasterNTHandler;
import com.mpos.login.model.USER_MST_Model;
import com.mpos.master.DBhandler.ANALYS_MST_DBHandler;
import com.mpos.master.DBhandler.BINLOC_MST_DBHandler;
import com.mpos.master.DBhandler.Currency_MstDBHandler;
import com.mpos.master.DBhandler.DNMTN_MSTDBHandler;
import com.mpos.master.DBhandler.DPT_MST_DBHandler;
import com.mpos.master.DBhandler.FLX_POS_MSTDBHandler;
import com.mpos.master.DBhandler.GRP_MST_DBHandler;
import com.mpos.master.DBhandler.ModifierDBHandler;
import com.mpos.master.DBhandler.PRDCT_MST_DBHandler;
import com.mpos.master.DBhandler.PRDCT_RECIPE_DTL_MST_DBHandler;
import com.mpos.master.DBhandler.Prdct_Instruc_DBHandler;
import com.mpos.master.DBhandler.SLMN_MSTDBHandler;
import com.mpos.master.DBhandler.ScanCodesDBHandler;
import com.mpos.master.DBhandler.USER_MST_DBHandler;
import com.mpos.master.DBhandler.USR_ASSGND_RGHTS_DBHandler;
import com.mpos.master.DBhandler.UomDBHandler;
import com.mpos.master.DBhandler.Uom_Slab_DBHandler;
import com.mpos.master.DBhandler.VATDBHandler;
import com.mpos.master.NTHandler.AnalysisNTHandler;
import com.mpos.master.NTHandler.BinLocNTHandler;
import com.mpos.master.NTHandler.CRDCT_CardNTHandler;
import com.mpos.master.NTHandler.CurrencyNTHandler;
import com.mpos.master.NTHandler.DNMTN_MST_NTHandler;
import com.mpos.master.NTHandler.DepartmentNTHandler;
import com.mpos.master.NTHandler.FLX_POS_MSTNTHandler;
import com.mpos.master.NTHandler.GroupNTHandler;
import com.mpos.master.NTHandler.Instruction_NTHandler;
import com.mpos.master.NTHandler.PRDCT_Instruction_NTHandler;
import com.mpos.master.NTHandler.PRDCT_RECIPE_DTL_NTHandler;
import com.mpos.master.NTHandler.Prdct_ImgNtHandler;
import com.mpos.master.NTHandler.ProductMstNTHandler;
import com.mpos.master.NTHandler.SalesManNTHandler;
import com.mpos.master.NTHandler.ScanCodeNTHandler;
import com.mpos.master.NTHandler.UomNTHandler;
import com.mpos.master.NTHandler.UomSlabNTHandler;
import com.mpos.master.NTHandler.UserRightsNTHandler;
import com.mpos.master.NTHandler.VatNTHandler;
import com.mpos.master.model.CURRENCY_MST_MODEL;
import com.mpos.master.model.DNMTN_MSTModel;
import com.mpos.master.model.FLX_POS_Model;
import com.mpos.master.model.INSTRCTN_MST_Model;
import com.mpos.master.model.PRDCT_ANALYS_MST_Model;
import com.mpos.master.model.PRDCT_BINLOC_MST_Model;
import com.mpos.master.model.PRDCT_DPT_MST_Model;
import com.mpos.master.model.PRDCT_GRP_MST_Model;
import com.mpos.master.model.PRDCT_INSTRC_MST_Model;
import com.mpos.master.model.PRDCT_MST_Model;
import com.mpos.master.model.PrdctImageMstModel;
import com.mpos.master.model.SCAN_CODE_MST_Model;
import com.mpos.master.model.SLMN_MST_Model;
import com.mpos.master.model.UOM_MST_Model;
import com.mpos.master.model.UOM_SLAB_MST_MODEL;
import com.mpos.master.model.USR_ASSGND_RGHTSModel;
import com.mpos.master.model.VAT_MST_Model;
import com.mpos.transactions.model.PRDCT_RECIPE_DTL_TXN_Model;

/**
 * Description-
 * 
 */

public class MasterHelper extends ActivityHelper {

	public static final String TAG = Constants.APP_TAG
			+ MasterHelper.class.getSimpleName();

	private int mPrdctSearchCnt = 0;

	private boolean isUpdateDb;
	private BaseResponseListener mBaseResponseListener = null;

	ActivityHandler mImageActivityHandler = null;

	public MasterHelper(BaseResponseListener responseListener,
			boolean isUpdateDb) {
		super(responseListener);
		this.mBaseResponseListener = responseListener;
		this.setUpdateDb(isUpdateDb);
	}

	/**
	 * 
	 * @param url
	 * @param params
	 * @param showProgress
	 */
	public void getAllUserAuthenticationData(String url,
			List<NameValuePair> params, boolean showProgress) {
		Logger.d(TAG, "getuserAuthenticationData");
		mIsContinueProgressbar = showProgress;
		// Respective N/T Handler
		ActivityHandler activityHandler = new UserMasterNTHandler(this, params,
				url, Constants.GET_USER_MST);
		// Start AsyncTask
		getResult(activityHandler);

	}

	public void getFLXPOSData(String url, List<NameValuePair> params,
			boolean showProgress) {
		Logger.d(TAG, "getFLXPOSData");
		mIsContinueProgressbar = showProgress;
		// Respective N/T Handler
		ActivityHandler activityHandler = new FLX_POS_MSTNTHandler(this,
				params, url, Constants.GET_FLX_POS);
		// Start AsyncTask
		getResult(activityHandler);

	}

	/**
	 * Method- To get product master data from server
	 * 
	 * @param url
	 * @param params
	 * @param showProgress
	 */
	public void getProductMasterData(String url, List<NameValuePair> params,
			boolean showProgress) {
		// masterUploadCount++;
		Logger.d(TAG, "getProductMasterData");
		mIsContinueProgressbar = showProgress;
		// Respective N/T Handler
		ActivityHandler activityHandler = new ProductMstNTHandler(this, params,
				url, Constants.GET_PRDCT_RECORDS);
		// Start AsyncTask
		getResult(activityHandler);
	}

	/**
	 * Method- To get product image data from server
	 * 
	 * @param url
	 * @param params
	 * @param showProgress
	 */
	public void getProducImageData(String url, List<NameValuePair> params,
			boolean showProgress) {
		Logger.d(TAG, "getProducImageData");
		mIsContinueProgressbar = showProgress;
		// Respective N/T Handler
		// if(mImageActivityHandler == null){
		mImageActivityHandler = new Prdct_ImgNtHandler(this, params, url,
				Constants.GET_PRDCT_IMGES);
		// }
		// Start AsyncTask
		getResult(mImageActivityHandler);
	}

	/**
	 * Method- To get product group data from server
	 * 
	 * @param url
	 * @param params
	 * @param showProgress
	 */
	public void getProductGroupData(String url, List<NameValuePair> params,
			boolean showProgress) {
		// masterUploadCount++;
		Logger.d(TAG, "getProductGroupData");
		mIsContinueProgressbar = showProgress;
		// Respective N/T Handler
		ActivityHandler activityHandler = new GroupNTHandler(this, params, url,
				Constants.GET_GRP_RECORDS);
		// Start AsyncTask
		getResult(activityHandler);
	}

	/**
	 * Method- To get product department data from server
	 * 
	 * @param url
	 * @param params
	 * @param showProgress
	 */
	public void getProductDptData(String url, List<NameValuePair> params,
			boolean showProgress) {
		Logger.d(TAG, "getProductDptData");
		// /masterUploadCount++;
		mIsContinueProgressbar = showProgress;
		// Respective N/T Handler
		ActivityHandler activityHandler = new DepartmentNTHandler(this, params,
				url, Constants.GET_DPT_RECORDS);
		// Start AsyncTask
		getResult(activityHandler);
	}

	/**
	 * Method- To get product analysis data from server
	 * 
	 * @param url
	 * @param params
	 * @param showProgress
	 */
	public void getProductAnalysData(String url, List<NameValuePair> params,
			boolean showProgress) {
		// /masterUploadCount++;
		Logger.d(TAG, "getProductAnalysData");
		mIsContinueProgressbar = showProgress;
		// Respective N/T Handler
		ActivityHandler activityHandler = new AnalysisNTHandler(this, params,
				url, Constants.GET_ANALYS_RECORDS);
		// Start AsyncTask
		getResult(activityHandler);
	}

	/**
	 * Method- To get product bin location data from server
	 * 
	 * @param url
	 * @param params
	 * @param showProgress
	 */
	public void getProductBinLocData(String url, List<NameValuePair> params,
			boolean showProgress) {
		// masterUploadCount++;
		Logger.d(TAG, "getProductBinLocData");
		mIsContinueProgressbar = showProgress;
		// Respective N/T Handler
		ActivityHandler activityHandler = new BinLocNTHandler(this, params,
				url, Constants.GET_BINLOC_RECORDS);
		// Start AsyncTask
		getResult(activityHandler);
	}

	/**
	 * Method- To get currency data from server
	 * 
	 * @param url
	 * @param params
	 * @param showProgress
	 */
	public void getCurrencyData(String url, List<NameValuePair> params,
			boolean showProgress) {
		// /masterUploadCount++;
		Logger.d(TAG, "getCurrencyData");
		mIsContinueProgressbar = showProgress;
		// Respective N/T Handler
		ActivityHandler activityHandler = new CurrencyNTHandler(this, params,
				url, Constants.GET_CURRENCY_RECORDS);
		// Start AsyncTask
		getResult(activityHandler);
	}

	/**
	 * Method- To get salesman data from server
	 * 
	 * @param url
	 * @param params
	 * @param showProgress
	 */
	public void getSalesManData(String url, List<NameValuePair> params,
			boolean showProgress) {
		// masterUploadCount++;
		Logger.d(TAG, "getSalesManData");
		mIsContinueProgressbar = showProgress;
		// Respective N/T Handler
		ActivityHandler activityHandler = new SalesManNTHandler(this, params,
				url, Constants.GET_SALESMAN_RECORDS);
		// Start AsyncTask
		getResult(activityHandler);
	}

	/**
	 * Method- To get UOM data from server
	 * 
	 * @param url
	 * @param params
	 * @param showProgress
	 */
	public void getUOMData(String url, List<NameValuePair> params,
			boolean showProgress) {
		// masterUploadCount++;
		Logger.d(TAG, "getUOMData");
		mIsContinueProgressbar = showProgress;
		// Respective N/T Handler
		ActivityHandler activityHandler = new UomNTHandler(this, params, url,
				Constants.GET_UOM_RECORDS);
		// Start AsyncTask
		getResult(activityHandler);
	}

	/**
	 * Method- To get UOM SLab data from server
	 * 
	 * @param url
	 * @param params
	 * @param showProgress
	 */
	public void getUOMSlabData(String url, List<NameValuePair> params,
			boolean showProgress) {
		Logger.d(TAG, "getUOMSlabData");
		mIsContinueProgressbar = showProgress;
		// Respective N/T Handler
		ActivityHandler activityHandler = new UomSlabNTHandler(this, params,
				url, Constants.GET_UOM_SLAB_RECORD);
		// Start AsyncTask
		getResult(activityHandler);
	}

	/**
	 * Method- To get Instruction data from server
	 * 
	 * @param url
	 * @param params
	 * @param showProgress
	 */
	public void getInstrucData(String url, List<NameValuePair> params,
			boolean showProgress) {
		Logger.d(TAG, "getInstrucData");
		mIsContinueProgressbar = showProgress;
		// Respective N/T Handler
		ActivityHandler activityHandler = new Instruction_NTHandler(this,
				params, url, Constants.GET_MODIFIER_RECORD);
		// Start AsyncTask
		getResult(activityHandler);
	}

	/**
	 * Method- To get PRDCT Instruction data from server
	 * 
	 * @param url
	 * @param params
	 * @param showProgress
	 */
	public void getPRDCTInstrucData(String url, List<NameValuePair> params,
			boolean showProgress) {
		Logger.d(TAG, "getPRDCTInstrucData");
		mIsContinueProgressbar = showProgress;
		// Respective N/T Handler
		ActivityHandler activityHandler = new PRDCT_Instruction_NTHandler(this,
				params, url, Constants.GET_PRDCT_INSTRC_RECORD);
		// Start AsyncTask
		getResult(activityHandler);
	}

	/**
	 * Method- To get VAT data from server
	 * 
	 * @param url
	 * @param params
	 * @param showProgress
	 */
	public void getVATData(String url, List<NameValuePair> params,
			boolean showProgress) {
		// masterUploadCount++;
		Logger.d(TAG, "getVATData");
		mIsContinueProgressbar = showProgress;
		// Respective N/T Handler
		ActivityHandler activityHandler = new VatNTHandler(this, params, url,
				Constants.GET_VAT_RECORDS);
		// Start AsyncTask
		getResult(activityHandler);
	}

	/**
	 * Method- To get Scan Codes data from server
	 * 
	 * @param url
	 * @param params
	 * @param showProgress
	 */
	public void getScanCodeData(String url, List<NameValuePair> params,
			boolean showProgress) {
		// masterUploadCount++;
		Logger.d(TAG, "getScanCodeData");
		mIsContinueProgressbar = showProgress;
		// Respective N/T Handler
		ActivityHandler activityHandler = new ScanCodeNTHandler(this, params,
				url, Constants.GET_SCAN_CODE_RECORDS);
		// Start AsyncTask
		getResult(activityHandler);
	}

	/**
	 * Method- To get receipe details data from server
	 * 
	 * @param url
	 * @param params
	 * @param showProgress
	 */
	public void getRecipeDtlData(String url, List<NameValuePair> params,
			boolean showProgress) {
		// masterUploadCount++;
		Logger.d(TAG, "getRecipeDtlData");
		mIsContinueProgressbar = showProgress;
		// Respective N/T Handler
		ActivityHandler activityHandler = new PRDCT_RECIPE_DTL_NTHandler(this,
				params, url, Constants.GET_INGREDIENT_RECORD);
		// Start AsyncTask
		getResult(activityHandler);
	}

	/**
	 * Method- To get CC data from server
	 * 
	 * @param url
	 * @param params
	 * @param showProgress
	 */
	public void getCRDCTCCData(String url, List<NameValuePair> params,
			boolean showProgress) {
		// masterUploadCount++;
		Logger.d(TAG, "getCRDCTCCData");
		mIsContinueProgressbar = showProgress;
		// Respective N/T Handler
		ActivityHandler activityHandler = new CRDCT_CardNTHandler(this, params,
				url, Constants.GET_CRDCT_CARD_RECORDS);
		// Start AsyncTask
		getResult(activityHandler);
	}

	/**
	 * Method- TO insert PRDCT item data
	 * 
	 * @param prdct_Models
	 * @param tableName
	 * @param showProgress
	 */
	public void insertPrdctRecords(ArrayList<PRDCT_MST_Model> prdct_Models,
			String tableName, boolean showProgress) {
		Logger.d(TAG, "insertPrdctRecords");
		mIsContinueProgressbar = showProgress;

		ActivityHandler activityHandler = new PRDCT_MST_DBHandler(this,
				prdct_Models, tableName, DBOperationlParameter.INSERT,
				Constants.INSERT_PRDCT_RECORDS, isUpdateDb);
		// Start AsyncTask
		getResult(activityHandler);
	}

	/**
	 * Method- To insert instructions in DB
	 * 
	 * @param instrc_Models
	 * @param tableName
	 * @param b
	 */
	private void insertInstrucRecords(
			ArrayList<INSTRCTN_MST_Model> instrc_Models, String tableName,
			boolean showProgress) {
		Logger.d(TAG, "insertInstrucRecords");
		mIsContinueProgressbar = showProgress;

		ActivityHandler activityHandler = new ModifierDBHandler(this,
				instrc_Models, tableName, DBOperationlParameter.INSERT,
				Constants.INSERT_MODIFIER_RECORDS, isUpdateDb);
		// Start AsyncTask
		getResult(activityHandler);
	}

	/**
	 * Method- To insert prdct instructions in DB
	 * 
	 * @param instrc_Models
	 * @param tableName
	 * @param b
	 */
	private void insertPrdctInstrucRecords(
			ArrayList<PRDCT_INSTRC_MST_Model> prdct_instrc_Models,
			String tableName, boolean showProgress) {
		Logger.d(TAG, "insertPrdctInstrucRecords");
		mIsContinueProgressbar = showProgress;

		ActivityHandler activityHandler = new Prdct_Instruc_DBHandler(this,
				prdct_instrc_Models, tableName, DBOperationlParameter.INSERT,
				Constants.INSERT_PRDCT_INSTRC_RECORDS, isUpdateDb);
		// Start AsyncTask
		getResult(activityHandler);
	}

	/**
	 * Method- To insert Group Records
	 * 
	 * @param models
	 * @param dboPrdctGrpMstTable
	 * @param b
	 */
	public void insertGRPRecords(ArrayList<PRDCT_GRP_MST_Model> grpModels,
			String tableName, boolean showProgress) {
		Logger.d(TAG, "insertGRPRecords");

		mIsContinueProgressbar = showProgress;
		ActivityHandler activityHandler = new GRP_MST_DBHandler(this,
				grpModels, tableName, DBOperationlParameter.INSERT,
				Constants.INSERT_GRP_RECORDS, isUpdateDb);
		// Start AsyncTask
		getResult(activityHandler);
	}

	/**
	 * Method- To insert PRDCT Department data
	 * 
	 * @param dpt_Models
	 * @param showProgress
	 */
	public void insertDPTRecords(ArrayList<PRDCT_DPT_MST_Model> dpt_Models,
			String tableName, boolean showProgress) {
		Logger.d(TAG, "insertDPTRecords");
		mIsContinueProgressbar = showProgress;
		ActivityHandler activityHandler = new DPT_MST_DBHandler(this,
				dpt_Models, tableName, DBOperationlParameter.INSERT,
				Constants.INSERT_DPT_RECORDS, isUpdateDb);
		// Start AsyncTask
		getResult(activityHandler);
	}

	/**
	 * Method- To insert PRDCT Analysis data
	 * 
	 * @param analys_Models
	 * @param showProgress
	 */
	public void insertANALYSRecords(
			ArrayList<PRDCT_ANALYS_MST_Model> analys_Models, String tableName,
			boolean showProgress) {
		Logger.d(TAG, "insertANALYSRecords");
		mIsContinueProgressbar = showProgress;
		ActivityHandler activityHandler = new ANALYS_MST_DBHandler(this,
				analys_Models, tableName, DBOperationlParameter.INSERT,
				Constants.INSERT_ANALYS_RECORDS, isUpdateDb);
		// Start AsyncTask
		getResult(activityHandler);
	}

	/**
	 * Method- To insert PRDCT Bin location data
	 * 
	 * @param binLoc_Models
	 * @param showProgress
	 */
	public void insertBINLOCRecords(
			ArrayList<PRDCT_BINLOC_MST_Model> binLoc_Models, String tableName,
			boolean showProgress) {
		Logger.d(TAG, "insertBINLOCRecords");
		mIsContinueProgressbar = showProgress;
		ActivityHandler activityHandler = new BINLOC_MST_DBHandler(this,
				binLoc_Models, tableName, DBOperationlParameter.INSERT,
				Constants.INSERT_BINLOC_RECORDS, isUpdateDb);
		// Start AsyncTask
		//
		getResult(activityHandler);
	}

	/**
	 * Method- To select all Product data
	 * 
	 * @param whrArgs
	 * @param tableName
	 * @param showProgress
	 */
	public void selectPRDCTRecords(String[] whrArgs, String tableName,
			boolean showProgress) {
		Logger.d(TAG, "selectPRDCTRecords");
		mIsContinueProgressbar = showProgress;

		ActivityHandler activityHandler = new PRDCT_MST_DBHandler(this,
				whrArgs, tableName, DBOperationlParameter.SELECT,
				Constants.FETCH_PRDCT_RECORDS, isUpdateDb);
		// Start AsyncTask
		//
		getResult(activityHandler);
	}

	public void selectSLMN_MSTRecords(String[] whrArgs, String tableName,
			boolean showProgress) {
		mIsContinueProgressbar = showProgress;

		ActivityHandler activityHandler = new SLMN_MSTDBHandler(this, whrArgs,
				tableName, DBOperationlParameter.SELECT,
				Constants.FETCH_SLMN_MST_RECORD, isUpdateDb);
		// Start AsyncTask
		//
		getResult(activityHandler);
	}

	/**
	 * Method- To select all Group data
	 * 
	 * @param classLevelSelectionArg
	 * @param tableName
	 * @param showProgress
	 */
	public void selectGRPRecords(String classLevelSelectionArg,
			String tableName, boolean showProgress) {
		Logger.d(TAG, "selectGRPRecords");
		mIsContinueProgressbar = showProgress;

		String[] queryParams = { classLevelSelectionArg };

		ActivityHandler activityHandler = new GRP_MST_DBHandler(this,
				queryParams, tableName, DBOperationlParameter.SELECT,
				Constants.FETCH_GRP_RECORDS, isUpdateDb);
		// Start AsyncTask
		//
		getResult(activityHandler);
	}

	/**
	 * Method- To select all Department data
	 * 
	 * @param classLevelSelectionArg
	 * @param tableName
	 * @param showProgress
	 */
	public void selectDPTRecords(String classLevelSelectionArg,
			String tableName, boolean showProgress) {
		Logger.d(TAG, "selectDPTRecords");
		mIsContinueProgressbar = showProgress;

		String[] queryParams = { classLevelSelectionArg };

		ActivityHandler activityHandler = new DPT_MST_DBHandler(this,
				queryParams, tableName, DBOperationlParameter.SELECT,
				Constants.FETCH_DPT_RECORDS, isUpdateDb);
		// Start AsyncTask
		//
		getResult(activityHandler);
	}

	/**
	 * Method- To select all Analysis data
	 * 
	 * @param classLevelSelectionArg
	 * @param tableName
	 * @param showProgress
	 */
	public void selectANALYSRecords(String classLevelSelectionArg,
			String tableName, boolean showProgress) {
		Logger.d(TAG, "selectANALYSRecords");
		mIsContinueProgressbar = showProgress;

		String[] queryParams = { classLevelSelectionArg };

		ActivityHandler activityHandler = new ANALYS_MST_DBHandler(this,
				queryParams, tableName, DBOperationlParameter.SELECT,
				Constants.FETCH_ANALYS_RECORDS, isUpdateDb);
		// Start AsyncTask
		//
		getResult(activityHandler);
	}

	/**
	 * Method- To select all BinLocation data
	 * 
	 * @param classLevelSelectionArg
	 * @param tableName
	 * @param showProgress
	 */
	public void selectBINLOCRecords(String classLevelSelectionArg,
			String tableName, boolean showProgress) {
		Logger.d(TAG, "selectBINLOCRecords");
		mIsContinueProgressbar = showProgress;

		String[] queryParams = { classLevelSelectionArg };

		ActivityHandler activityHandler = new BINLOC_MST_DBHandler(this,
				queryParams, tableName, DBOperationlParameter.SELECT,
				Constants.FETCH_BINLOC_RECORDS, isUpdateDb);
		// Start AsyncTask
		//
		getResult(activityHandler);
	}

	public void selectFLXPOSRecords(String[] whrArgs, String tableName,
			boolean showProgress) {
		Logger.d(TAG, "selectFLXPOSRecords");
		mIsContinueProgressbar = showProgress;

		ActivityHandler activityHandler = new FLX_POS_MSTDBHandler(this,
				whrArgs, tableName, DBOperationlParameter.SELECT,
				Constants.FETCH_FLX_POS_RECORD, isUpdateDb);
		// Start AsyncTask
		//
		getResult(activityHandler);
	}

	/**
	 * Method- To select particular Product data
	 * 
	 * @param whrArgs
	 * @param tableName
	 * @param showProgress
	 */
	public void selectSearchPRDCTRecord(String queryParam, String tableName,
			boolean showProgress, boolean isPrdctCodeSearch) {
		Logger.d(TAG, "selectSearchPRDCTRecord");
		mIsContinueProgressbar = showProgress;
		mPrdctSearchCnt++;

		String[] whrArgs = { queryParam };

		PRDCT_MST_DBHandler activityHandler = new PRDCT_MST_DBHandler(this,
				whrArgs, tableName, DBOperationlParameter.DEFAULT_VEHICLE,
				Constants.FETCH_SINGLE_PRDCT_RECORD, isUpdateDb);

		if (!isPrdctCodeSearch) {
			String whereQuery = DBConstants.PRDCT_LNG_DSCRPTN + " LIKE '%=?%'";
			activityHandler.setWhrQuery(whereQuery);
		}

		// Start AsyncTask
		getResult(activityHandler);
	}

	/**
	 * Method- To select particular Product id from scan code data
	 * 
	 * @param whrArgs
	 * @param tableName
	 * @param showProgress
	 */
	public void retrieveScanCodesPrdct(String queryParam, String tableName,
			boolean showProgress) {
		Logger.d(TAG, "retrieveScanCodesPrdct");
		mIsContinueProgressbar = showProgress;

		String[] whrArgs = { queryParam };

		ActivityHandler activityHandler = new ScanCodesDBHandler(this, whrArgs,
				tableName, DBOperationlParameter.DEFAULT_VEHICLE,
				Constants.FETCH_SCANCODES_RECORD, isUpdateDb);
		// Start AsyncTask
		getResult(activityHandler);
	}

	/**
	 * Method- To update image column in prdct record to table
	 * 
	 * @param result
	 */
	public void updatePrdctImageInPrdctMstDB(
			ArrayList<PrdctImageMstModel> prdctModels) {
		Logger.d(TAG, "updatePrdctImageInPrdctMstDB");
		mIsContinueProgressbar = true;
		if (prdctModels != null && prdctModels.size() > 0) {

			/*
			 * for (PrdctImageMstModel prdctImageMstModel : prdctModels) { //
			 * Update only if has data if (prdctImageMstModel.getPrdct_img() !=
			 * null && prdctImageMstModel.getPrdct_img().length() > 0 &&
			 * !prdctImageMstModel.getPrdct_img().equalsIgnoreCase( "null")) {
			 * Logger.d(TAG, "Image to update"); ActivityHandler activityHandler
			 * = new PRDCT_MST_DBHandler( this, prdctImageMstModel,
			 * DBConstants.dbo_TILL_PRDCT_MST_TABLE,
			 * DBOperationlParameter.UPDATE,
			 * Constants.UPDATE_PRDCT_IMG_MST_RECORDS, false); // Start
			 * AsyncTask getResult(activityHandler); } else {// No image to
			 * update,call next images Logger.d(TAG,
			 * "NO image to update,call next"); OperationalResult result = new
			 * OperationalResult();
			 * result.setResultCode(Constants.UPDATE_PRDCT_IMG_MST_RECORDS);
			 * this.mBaseResponseListener.executePostAction(result); } }
			 */

			Logger.d(TAG, "Image to update");
			ActivityHandler activityHandler = new PRDCT_MST_DBHandler(this,
					prdctModels, DBConstants.dbo_TILL_PRDCT_MST_TABLE,
					DBOperationlParameter.UPDATE,
					Constants.UPDATE_PRDCT_IMG_MST_RECORDS, false);
			// Start AsyncTask
			getResult(activityHandler);

		}
	}

	@Override
	protected void responseHandle(OperationalResult result) {

		System.out.println("DEBUG::MASTERHELPER responseHandle CODE==>>"+result.getRequestResponseCode());
		switch (result.getRequestResponseCode()) {
		case OperationalResult.NETWORK_ERROR:
			
			System.out.println("DEBUG::MASTERHELPER NETWORK ERROR");
			
			break;
		case OperationalResult.ERROR:
			result.setResultCode(Constants.MASTER_UPLOAD_ERROR);
			this.mBaseResponseListener.executePostAction(result);
			break;
		case OperationalResult.DB_ERROR:
			// DB error for table
			Logger.d(TAG, "DB_ERROR");
			// Chk for PRDCT free search
			if (result.getResultCode() == Constants.FETCH_SINGLE_PRDCT_RECORD) {
				if (mPrdctSearchCnt == 2) {
					mPrdctSearchCnt = 0;
					this.mBaseResponseListener.errorReceived(result);
				} else {
					String queryParam = result.getDBOperationalParam()
							.getQueryParam()[0];
					selectSearchPRDCTRecord(queryParam,
							DBConstants.dbo_TILL_PRDCT_MST_TABLE, true, false);
				}
			} else {
				// return back to HOmeActivity in case of error,no records
				if (result.getResultCode() == Constants.FETCH_DPT_RECORDS
						|| result.getResultCode() == Constants.FETCH_ANALYS_RECORDS
						|| result.getResultCode() == Constants.FETCH_BINLOC_RECORDS) {
					this.mBaseResponseListener.executePostAction(result);
				} else {
					this.mBaseResponseListener.errorReceived(result);
				}

			}
			break;

		default:
			break;
		}

		System.out.println("DEBUG::MASTERHELPER result CODE==>>"+result.getResultCode());
		switch (result.getResultCode()) { // DB scenario handling

		case OperationalResult.MASTER_ERROR:
			// Common error for Master
			// checkMasterDownloadCompletion(result);
			// this.mBaseResponseListener.executePostAction(result);
			this.mBaseResponseListener.errorReceived(result);
			break;

		case OperationalResult.DB_ERROR:
			// DB error for table
			this.mBaseResponseListener.errorReceived(result);
			break;

		/** Group Transactions **/
		case Constants.GET_GRP_RECORDS:
			// Handle GRp Data
			saveGroupDataInDB(result);
			break;

		case Constants.INSERT_GRP_RECORDS:
			// Handle insert Grp data and fetch inserted records from table

			// selectGRPRecords("", DBConstants.dbo_PRDCT_GRP_MST_TABLE, true);
			// /checkMasterDownloadCompletion(result);
			UserSingleton.getInstance(MPOSApplication.getContext())
					.incrementMstCount();
			this.mBaseResponseListener.executePostAction(result);

			break;

		case Constants.FETCH_GRP_RECORDS:
			this.mBaseResponseListener.executePostAction(result);
			break;

		/** Department Transactions **/
		case Constants.GET_DPT_RECORDS:
			// Handle Dpt Data
			saveDepartmentDataInDB(result);
			break;

		case Constants.GET_DNMTN_MST:
			saveDenominationData(result);
			break;

		case Constants.INSERT_DPT_RECORDS:

			// /checkMasterDownloadCompletion(result);
			UserSingleton.getInstance(MPOSApplication.getContext())
					.incrementMstCount();
			this.mBaseResponseListener.executePostAction(result);

			// Handle insert Dpt data and fetch inserted records from table
			// selectDPTRecords("", DBConstants.dbo_DPT_MST_TABLE, true);
			break;

		case Constants.FETCH_DPT_RECORDS:
			this.mBaseResponseListener.executePostAction(result);
			break;

		/** Analysis Transactions **/
		case Constants.GET_ANALYS_RECORDS:
			// Handle Analys Data
			saveAnalysisDataInDB(result);
			break;

		case Constants.INSERT_ANALYS_RECORDS:

			// /checkMasterDownloadCompletion(result);
			UserSingleton.getInstance(MPOSApplication.getContext())
					.incrementMstCount();
			this.mBaseResponseListener.executePostAction(result);

			// Handle insert Analysis data and fetch inserted records from table
			// selectANALYSRecords("", DBConstants.dbo_ANALYS_MST_TABLE, true);
			break;

		case Constants.FETCH_ANALYS_RECORDS:
			this.mBaseResponseListener.executePostAction(result);
			break;

		/** BinLocation Transactions **/
		case Constants.GET_BINLOC_RECORDS:
			// Handle Binlocation Data
			saveBinLocDataInDB(result);
			break;

		case Constants.INSERT_BINLOC_RECORDS:

			// checkMasterDownloadCompletion(result);
			UserSingleton.getInstance(MPOSApplication.getContext())
					.incrementMstCount();
			this.mBaseResponseListener.executePostAction(result);

			// Handle insert bin location data and fetch inserted records from
			// table
			// selectBINLOCRecords("", DBConstants.dbo_BIN_LOC_MST_TABLE, true);
			break;

		case Constants.FETCH_BINLOC_RECORDS:
			this.mBaseResponseListener.executePostAction(result);
			break;

		case Constants.GET_USER_MST:
			saveAllUserDataInDB(result);
			break;

		/** Product Transactions **/
		case Constants.GET_PRDCT_RECORDS:
			// Handle Prdct Data
			saveProductsDataInDB(result);
			break;

		case Constants.INSERT_PRDCT_RECORDS:
			UserSingleton.getInstance(MPOSApplication.getContext())
					.incrementMstCount();
			this.mBaseResponseListener.executePostAction(result);

			break;

		case Constants.FETCH_PRDCT_RECORDS:
			this.mBaseResponseListener.executePostAction(result);
			break;

		case Constants.FETCH_SINGLE_PRDCT_RECORD:
			if (result.getRequestResponseCode() != OperationalResult.DB_ERROR)
				this.mBaseResponseListener.executePostAction(result);
			break;

		case Constants.GET_PRDCT_IMGES:
			this.mBaseResponseListener.executePostAction(result);
			break;

		case Constants.UPDATE_PRDCT_IMG_MST_RECORDS:
			Logger.d(TAG, "UPDATE_PRDCT_IMG_MST_RECORDS");
			this.mBaseResponseListener.executePostAction(result);
			break;

		/** Salesman **/
		case Constants.GET_SALESMAN_RECORDS:
			saveSalesManData(result);
			break;

		/** UOM Transactions **/
		case Constants.GET_UOM_RECORDS:
			saveUOMDataInDB(result);
			break;

		case Constants.INSERT_UOM_RECORD:
			UserSingleton.getInstance(MPOSApplication.getContext())
					.incrementMstCount();
			this.mBaseResponseListener.executePostAction(result);
			break;

		case Constants.FETCH_UOM_RECORD:
			this.mBaseResponseListener.executePostAction(result);
			break;

		/** UOM_SLAB Transactions **/
		case Constants.GET_UOM_SLAB_RECORD:
			saveUOMSlabDataInDB(result);
			break;

		case Constants.INSERT_UOM_SLAB_RECORD:
			UserSingleton.getInstance(MPOSApplication.getContext())
					.incrementMstCount();
			this.mBaseResponseListener.executePostAction(result);
			break;

		case Constants.FETCH_UOM_SLAB_RECORD:
			this.mBaseResponseListener.executePostAction(result);
			break;

		/** INSTRUCTION MST **/
		case Constants.GET_MODIFIER_RECORD:
			saveInstrucDataInDB(result);
			break;

		case Constants.INSERT_MODIFIER_RECORDS:
			UserSingleton.getInstance(MPOSApplication.getContext())
					.incrementMstCount();
			this.mBaseResponseListener.executePostAction(result);
			break;

		case Constants.GET_PRDCT_INSTRC_RECORD:
			savePrdctInstrucDataInDB(result);
			break;

		case Constants.INSERT_PRDCT_INSTRC_RECORDS:
			UserSingleton.getInstance(MPOSApplication.getContext())
					.incrementMstCount();
			this.mBaseResponseListener.executePostAction(result);
			break;

		/** Currency Transaction **/

		case Constants.GET_CURRENCY_RECORDS:
			storeAllCurrencyINDB(result);
			break;

		case Constants.INSERT_CURRENCY:
			UserSingleton.getInstance(MPOSApplication.getContext())
					.incrementMstCount();
			this.mBaseResponseListener.executePostAction(result);
			break;

		case Constants.FETCH_CURRENCIES:
			this.mBaseResponseListener.executePostAction(result);
			break;

		/** User Rights **/
		case Constants.GET_USR_ASSGND_RGHTS:
			saveAllUserRightsInDB(result);
			break;

		case Constants.INSERT_USER_ASSGND_RGHTS:
			UserSingleton.getInstance(MPOSApplication.getContext())
					.incrementMstCount();

			this.mBaseResponseListener.executePostAction(result);
			break;

		/** VAT Transactions **/
		case Constants.GET_VAT_RECORDS:
			saveVatDataInDB(result);
			break;

		case Constants.INSERT_VAT_RECORDS:
			UserSingleton.getInstance(MPOSApplication.getContext())
					.incrementMstCount();

			this.mBaseResponseListener.executePostAction(result);
			break;

		case Constants.GET_FLX_POS:
			saveFLXPOSDB(result);
			break;

		case Constants.INSERT_DNMTN_MST:
			UserSingleton.getInstance(MPOSApplication.getContext())
					.incrementMstCount();
			this.mBaseResponseListener.executePostAction(result);
			break;

		case Constants.INSERT_FLX_POS:
			UserSingleton.getInstance(MPOSApplication.getContext())
					.incrementMstCount();
			this.mBaseResponseListener.executePostAction(result);
			break;

		/** ScanCodes Transactions **/
		case Constants.GET_SCAN_CODE_RECORDS:
			saveScanCodeDataInDB(result);
			break;

		case Constants.INSERT_SCANCODE_RECORDS:
			UserSingleton.getInstance(MPOSApplication.getContext())
					.incrementMstCount();
			this.mBaseResponseListener.executePostAction(result);
			break;

		case Constants.INSERT_USER_MST_RECORDS:
			UserSingleton.getInstance(MPOSApplication.getContext())
					.incrementMstCount();
			this.mBaseResponseListener.executePostAction(result);
			break;

		case Constants.INSERT_SLMN_MST:
			UserSingleton.getInstance(MPOSApplication.getContext())
					.incrementMstCount();
			this.mBaseResponseListener.executePostAction(result);
			break;

		case Constants.FETCH_SCANCODES_RECORD:
			this.mBaseResponseListener.executePostAction(result);
			break;

		case Constants.DELETE_GRP_MST_TABLE:

		case Constants.DELETE_PRDT_DPT_MST_TABLE:

		case Constants.DELETE_BINLOC_MST_TABLE:

		case Constants.DELETE_ANALYS_MST_TABLE:

		case Constants.DELETE_PRDT_REC_MST_TABLE:

		case Constants.DELETE_UOM_MST_TABLE:

		case Constants.DELETE_UOM_SLAB_MST_TABLE:

		case Constants.DELETE_CURRENCY_MST_TABLE:

		case Constants.DELETE_USER_ASSGND_RGHTS_MST_TABLE:

		case Constants.DELETE_VAT_DATA_MST_TABLE:

		case Constants.DELETE_SCAN_CODE_MST_TABLE:

		case Constants.DELETE_USER_MST_TABLE:
		case Constants.DELETE_PRDCT_INSTRCT_MST_TABLE:
		case Constants.DELETE_INSTRUC_MST_TABLE:
		case Constants.DELETE_SLMN_MST_TABLE:
		case Constants.DELETE_RECPI_DTL_TABLE:
		case Constants.DELETE_FLX_POS_MST:
		case Constants.DELETE_DNMTN_MST:

			Logger.d(TAG, "DELETE_SLMN_MST_TABLE|DELETE_RECPI_DTL_TABLE");
			UserSingleton.getInstance(MPOSApplication.getContext())
					.incrementMstCount();
			OperationalResult opReasult = new OperationalResult();
			opReasult.setResultCode(Constants.DELETE_MASTER);
			this.mBaseResponseListener.executePostAction(opReasult);
			break;

		case Constants.COPY_GRP_MST_TABLE:
			this.copyPRDT_DPT_MSTTable("", DBConstants.dbo_DPT_MST_TABLE, true);
			break;
		case Constants.COPY_PRDT_DPT_MST_TABLE:
			copyBINLOC_MSTTable("", DBConstants.dbo_BIN_LOC_MST_TABLE, true);
			break;

		case Constants.COPY_BINLOC_MST_TABLE:
			copyANALYS_MSTTable("", DBConstants.dbo_ANALYS_MST_TABLE, true);
			break;

		case Constants.COPY_ANALYS_MST_TABLE:
			copyPRDCT_MST_MSTTable("", DBConstants.dbo_TILL_PRDCT_MST_TABLE,
					false);
			break;

		case Constants.COPY_PRDCT_MST_TABLE:
			copyUOM_MSTTable("", DBConstants.dbo_UOM_TABLE, true);
			break;

		case Constants.COPY_UOM_MST_TABLE:
			copyUOMSLAB_MSTTable("", DBConstants.dbo_UOM_SLAB_MST_TABLE, true);
			break;

		case Constants.COPY_UOM_SLAB_MST_TABLE:
			copyCurrency_MSTTable("", DBConstants.dbo_Currency_Mst_TABLE, true);
			break;

		case Constants.COPY_CURRENCY_MST_TABLE:
			copyUSER_ASSGND_RGHTS_MSTTable("",
					DBConstants.dbo_USR_ASSGND_RGHTS_TABLE, true);
			break;

		case Constants.COPY_USER_ASSGND_RGHTS_MST_TABLE:
			copyVAT_DATA_MSTTable("", DBConstants.dbo_VAT_MST_TABLE, true);
			break;

		case Constants.COPY_VAT_DATA_MST_TABLE:
			copyPRDT_RECIPI_DTLTABLE("",
					DBConstants.dbo_PRDCT_RECEIPE_MST_TABLE, true);
			break;

		case Constants.COPY_PRDT_RECIPI_DTL_TABLE:
			copySLMN_MSTTable("", DBConstants.dbo_SLMN_MST_TABLE, true);
			break;

		case Constants.COPY_SLMN_TABLE:
			copySCAN_CODES_MSTTable("", DBConstants.dbo_SCAN_CODS_MST_TABLE,
					false);
			break;

		case Constants.COPY_SCAN_CODE_MST_TABLE:
			copyFLX_POSTABLE("", DBConstants.dbo_FLX_POS_MST_TABLE, false);
			break;

		case Constants.COPY_FLX_POS_TABLE:
			copyUSER_MSTTABLE("", DBConstants.dbo_USER_MST_TABLE, false);
			break;

		case Constants.COPY_USR_MST_TABLE:
			copyDNMTN_MSTTABLE("", DBConstants.dbo_DNMTN_MST_TABLE, false);
			break;

		case Constants.COPY_DNMTN_MST_TABLE:
			copyInstruc_MSTTable("", DBConstants.dbo_INSTRCTN_MST_TABLE, false);
			break;

		case Constants.COPY_INSTRCTION_MST_TABLE:
			copyPRDCT_Instruc_MSTTable("",
					DBConstants.dbo_PRDCT_INSTRCTN_MST_TABLE, false);
			break;

		case Constants.COPY_PRDCT_INSTRUC_MST_TABLE:
			OperationalResult masterCompleteResult1 = new OperationalResult();
			masterCompleteResult1
					.setResultCode(Constants.UPDATE_MASTER_COMPLETE);
			this.mBaseResponseListener.executePostAction(masterCompleteResult1);
			break;

		/** PRDCT Recipe details Transactions **/
		case Constants.GET_INGREDIENT_RECORD:
			saveRecipeDataInDB(result);
			break;

		case Constants.INSERT_INGREDIENT_RECORD:
			UserSingleton.getInstance(MPOSApplication.getContext())
					.incrementMstCount();
			this.mBaseResponseListener.executePostAction(result);
			break;

		case Constants.FETCH_INGREDIENT_RECORD:
			this.mBaseResponseListener.executePostAction(result);
			break;

		default: // Pass UI updation response back to activity
			System.out.println("MYDEBUG::MASTERHELPER::UI UPDATION:::");
			this.mBaseResponseListener.executePostAction(result);
			break;
		}
	}

	private void copyDNMTN_MSTTABLE(String string, String tableName,
			boolean showProgress) {
		mIsContinueProgressbar = showProgress;

		ActivityHandler activityHandler = new DNMTN_MSTDBHandler(this,
				DBConstants.dbo_DNMTN_MST_TABLE,
				DBConstants.dbo_UPDATE_DNMTN_MST_TABLE,
				DBOperationlParameter.COPY_TABLE,
				Constants.COPY_DNMTN_MST_TABLE, isUpdateDb);
		// Start AsyncTask
		getResult(activityHandler);
	}

	private void copyUSER_MSTTABLE(String string, String tableName,
			boolean showProgress) {
		mIsContinueProgressbar = showProgress;

		ActivityHandler activityHandler = new USER_MST_DBHandler(this,
				DBConstants.dbo_USER_MST_TABLE,
				DBConstants.dbo_UPDATE_USER_MST_TABLE,
				DBOperationlParameter.COPY_TABLE, Constants.COPY_USR_MST_TABLE,
				isUpdateDb);
		// Start AsyncTask
		getResult(activityHandler);
	}

	private void saveDenominationData(OperationalResult result) {
		ArrayList<DNMTN_MSTModel> denominationModels = (ArrayList<DNMTN_MSTModel>) result
				.getResult().getSerializable(Constants.RESULTCODEBEAN);
		Logger.d(TAG, "saveSalesManData");
		mIsContinueProgressbar = true;
		String tableName = DBConstants.dbo_DNMTN_MST_TABLE;
		if (isUpdateDb()) {
			tableName = DBConstants.dbo_UPDATE_DNMTN_MST_TABLE;
		}
		if (denominationModels != null && denominationModels.size() > 0) {
			ActivityHandler activityHandler = new DNMTN_MSTDBHandler(this,
					denominationModels, tableName,
					DBOperationlParameter.INSERT, Constants.INSERT_DNMTN_MST,
					isUpdateDb);
			// Start AsyncTask
			getResult(activityHandler);
		}
	}

	private void saveFLXPOSDB(OperationalResult result) {
		FLX_POS_Model flxPosModel = (FLX_POS_Model) result.getResult()
				.getSerializable(Constants.RESULTCODEBEAN);
		Logger.d(TAG, "saveFLXPOSDB");
		mIsContinueProgressbar = true;
		String tableName = DBConstants.dbo_FLX_POS_MST_TABLE;
		if (isUpdateDb()) {
			tableName = DBConstants.dbo_UPDATE_FLX_POS_MST_TABLE;
		}
		if (flxPosModel != null) {
			ActivityHandler activityHandler = new FLX_POS_MSTDBHandler(this,
					flxPosModel, tableName, DBOperationlParameter.INSERT,
					Constants.INSERT_FLX_POS, isUpdateDb);
			// Start AsyncTask
			getResult(activityHandler);
		}
	}

	/**
	 * Save sales person data
	 * 
	 * @param result
	 */
	@SuppressWarnings("unchecked")
	private void saveSalesManData(OperationalResult result) {
		ArrayList<SLMN_MST_Model> salesManModels = (ArrayList<SLMN_MST_Model>) result
				.getResult().getSerializable(Constants.RESULTCODEBEAN);
		Logger.d(TAG, "saveSalesManData");
		mIsContinueProgressbar = true;
		String tableName = DBConstants.dbo_SLMN_MST_TABLE;
		if (isUpdateDb()) {
			tableName = DBConstants.dbo_UPDATE_SLMN_MST_TABLE;
		}
		if (salesManModels != null && salesManModels.size() > 0) {
			ActivityHandler activityHandler = new SLMN_MSTDBHandler(this,
					salesManModels, tableName, DBOperationlParameter.INSERT,
					Constants.INSERT_SLMN_MST, isUpdateDb);
			// Start AsyncTask
			getResult(activityHandler);
		}
	}

	/**
	 * Save all the user master data
	 * 
	 * @param opResult
	 */
	@SuppressWarnings("unchecked")
	private void saveAllUserDataInDB(OperationalResult opResult) {
		ArrayList<USER_MST_Model> mUser_MST_Models = (ArrayList<USER_MST_Model>) opResult
				.getResult().getSerializable(Constants.RESULTCODEBEAN);

		Logger.d(TAG, "insertUSER_MSTModel");
		mIsContinueProgressbar = true;
		String tableName = DBConstants.dbo_USER_MST_TABLE;
		if (isUpdateDb()) {
			tableName = DBConstants.dbo_UPDATE_USER_MST_TABLE;
		}
		ActivityHandler activityHandler = new USER_MST_DBHandler(this,
				mUser_MST_Models, tableName, DBOperationlParameter.INSERT,
				Constants.INSERT_USER_MST_RECORDS, false);
		// Start AsyncTask
		getResult(activityHandler);

	}

	/**
	 * Method- To insert all records to table
	 * 
	 * @param result
	 */
	@SuppressWarnings("unchecked")
	private void saveRecipeDataInDB(OperationalResult result) {
		ArrayList<PRDCT_RECIPE_DTL_TXN_Model> recipeModels = (ArrayList<PRDCT_RECIPE_DTL_TXN_Model>) result
				.getResult().getSerializable(Constants.RESULTCODEBEAN);
		Logger.d(TAG, "saveRecipeDataInDB");
		mIsContinueProgressbar = true;
		String tableName = DBConstants.dbo_PRDCT_RECEIPE_MST_TABLE;
		if (isUpdateDb()) {
			tableName = DBConstants.dbo_UPDATE_PRDCT_RECEIPE_MST_TABLE;
		}
		if (recipeModels != null && recipeModels.size() > 0) {
			ActivityHandler activityHandler = new PRDCT_RECIPE_DTL_MST_DBHandler(
					this, recipeModels, tableName,
					DBOperationlParameter.INSERT,
					Constants.INSERT_INGREDIENT_RECORD, isUpdateDb);
			// Start AsyncTask
			getResult(activityHandler);
		}
	}

	/**
	 * Method- To insert all records to table
	 * 
	 * @param result
	 */
	@SuppressWarnings("unchecked")
	private void saveScanCodeDataInDB(OperationalResult result) {
		ArrayList<SCAN_CODE_MST_Model> scanCodesModels = (ArrayList<SCAN_CODE_MST_Model>) result
				.getResult().getSerializable(Constants.RESULTCODEBEAN);
		Logger.d(TAG, "saveScanCodeDataInDB");
		String tableName = DBConstants.dbo_SCAN_CODS_MST_TABLE;
		if (isUpdateDb()) {
			tableName = DBConstants.dbo_UPDATE_SCAN_CODS_MST_TABLE;
		}
		mIsContinueProgressbar = true;
		if (scanCodesModels != null && scanCodesModels.size() > 0) {
			ActivityHandler activityHandler = new ScanCodesDBHandler(this,
					scanCodesModels, tableName, DBOperationlParameter.INSERT,
					Constants.INSERT_SCANCODE_RECORDS, isUpdateDb);
			// Start AsyncTask
			getResult(activityHandler);
		}
	}

	/**
	 * Method- To insert all records to table
	 * 
	 * @param result
	 */
	@SuppressWarnings("unchecked")
	private void saveVatDataInDB(OperationalResult result) {
		ArrayList<VAT_MST_Model> vatModels = (ArrayList<VAT_MST_Model>) result
				.getResult().getSerializable(Constants.RESULTCODEBEAN);
		Logger.d(TAG, "saveVatDataInDB");
		mIsContinueProgressbar = true;
		String tableName = DBConstants.dbo_VAT_MST_TABLE;
		if (isUpdateDb()) {
			tableName = DBConstants.dbo_UPDATE_VAT_MST_TABLE;
		}

		if (vatModels != null && vatModels.size() > 0) {
			ActivityHandler activityHandler = new VATDBHandler(this, vatModels,
					tableName, DBOperationlParameter.INSERT,
					Constants.INSERT_VAT_RECORDS, isUpdateDb);
			// Start AsyncTask
			getResult(activityHandler);
		}
	}

	/**
	 * Method- To insert all records to table
	 * 
	 * @param result
	 */
	@SuppressWarnings("unchecked")
	private void saveUOMSlabDataInDB(OperationalResult result) {
		ArrayList<UOM_SLAB_MST_MODEL> uomSlabModels = (ArrayList<UOM_SLAB_MST_MODEL>) result
				.getResult().getSerializable(Constants.RESULTCODEBEAN);
		Logger.d(TAG, "saveUOMSlabDataInDB");
		mIsContinueProgressbar = true;
		String tableName = DBConstants.dbo_UOM_SLAB_MST_TABLE;
		if (isUpdateDb) {
			tableName = DBConstants.dbo_UPDATE_UOM_SLAB_MST_TABLE;
		}
		if (uomSlabModels != null && uomSlabModels.size() > 0) {
			ActivityHandler activityHandler = new Uom_Slab_DBHandler(this,
					uomSlabModels, tableName, DBOperationlParameter.INSERT,
					Constants.INSERT_UOM_SLAB_RECORD, isUpdateDb);
			// Start AsyncTask
			getResult(activityHandler);
		}
	}

	/**
	 * Method- To insert all records to table
	 * 
	 * @param result
	 */
	@SuppressWarnings("unchecked")
	private void saveUOMDataInDB(OperationalResult result) {
		ArrayList<UOM_MST_Model> uomModels = (ArrayList<UOM_MST_Model>) result
				.getResult().getSerializable(Constants.RESULTCODEBEAN);
		Logger.d(TAG, "saveUOMDataInDB");
		mIsContinueProgressbar = true;
		String tableName = DBConstants.dbo_UOM_TABLE;
		if (isUpdateDb) {
			tableName = DBConstants.dbo_UPDATE_UOM_TABLE;
		}
		if (uomModels != null && uomModels.size() > 0) {

			ActivityHandler activityHandler = new UomDBHandler(this, uomModels,
					tableName, DBOperationlParameter.INSERT,
					Constants.INSERT_UOM_RECORD, isUpdateDb);
			// Start AsyncTask
			getResult(activityHandler);
		}
	}

	/**
	 * Method- To insert all records to table
	 * 
	 * @param result
	 */
	@SuppressWarnings("unchecked")
	private void storeAllCurrencyINDB(OperationalResult result) {
		ArrayList<CURRENCY_MST_MODEL> currency_Models = (ArrayList<CURRENCY_MST_MODEL>) result
				.getResult().getSerializable(Constants.RESULTCODEBEAN);

		Logger.d(TAG, "storeAllCurrencyINDB");
		mIsContinueProgressbar = true;
		String tableName = DBConstants.dbo_Currency_Mst_TABLE;
		if (isUpdateDb) {
			tableName = DBConstants.dbo_UPDATE_Currency_Mst_TABLE;
		}
		if (currency_Models != null && currency_Models.size() > 0) {
			ActivityHandler activityHandler = new Currency_MstDBHandler(this,
					currency_Models, tableName, DBOperationlParameter.INSERT,
					Constants.INSERT_CURRENCY, isUpdateDb);
			// Start AsyncTask
			getResult(activityHandler);
		}
	}

	/**
	 * Method- To retrieve all currency data from db
	 * 
	 * @param string
	 * @param tableName
	 * @param showProgress
	 */
	public void retrieveAllCurrencyFromDB(String tableName, boolean showProgress) {
		mIsContinueProgressbar = showProgress;

		ActivityHandler activityHandler = new Currency_MstDBHandler(this, null,
				tableName, DBOperationlParameter.SELECT,
				Constants.FETCH_CURRENCIES, isUpdateDb);
		// Start AsyncTask
		getResult(activityHandler);
	}

	/**
	 * Method- To insert Instruction records in DB
	 * 
	 * @param result
	 */
	@SuppressWarnings("unchecked")
	private void saveInstrucDataInDB(OperationalResult result) {
		ArrayList<INSTRCTN_MST_Model> instrc_Models = (ArrayList<INSTRCTN_MST_Model>) result
				.getResult().getSerializable(Constants.RESULTCODEBEAN);

		String tableName = DBConstants.dbo_INSTRCTN_MST_TABLE;
		if (isUpdateDb()) {
			tableName = DBConstants.dbo_UPDATE_INSTRCTN_MST_TABLE;
		}
		// If response is not null, insert the records in DB Table
		if (instrc_Models != null && instrc_Models.size() > 0) {
			insertInstrucRecords(instrc_Models, tableName, true);
		}
	}

	/**
	 * Method- To insert PRDCT Instruction records in DB
	 * 
	 * @param result
	 */
	@SuppressWarnings("unchecked")
	private void savePrdctInstrucDataInDB(OperationalResult result) {
		ArrayList<PRDCT_INSTRC_MST_Model> prdct_instrc_Models = (ArrayList<PRDCT_INSTRC_MST_Model>) result
				.getResult().getSerializable(Constants.RESULTCODEBEAN);

		String tableName = DBConstants.dbo_PRDCT_INSTRCTN_MST_TABLE;
		if (isUpdateDb()) {
			tableName = DBConstants.dbo_UPDATE_PRDCT_INSTRCTN_MST_TABLE;
		}
		// If response is not null, insert the records in DB Table
		if (prdct_instrc_Models != null && prdct_instrc_Models.size() > 0) {
			insertPrdctInstrucRecords(prdct_instrc_Models, tableName, true);
		}
	}

	/**
	 * Method- To insert all records to table
	 * 
	 * @param result
	 */
	@SuppressWarnings("unchecked")
	private void saveGroupDataInDB(OperationalResult result) {
		ArrayList<PRDCT_GRP_MST_Model> gRP_Models = (ArrayList<PRDCT_GRP_MST_Model>) result
				.getResult().getSerializable(Constants.RESULTCODEBEAN);

		String tableName = DBConstants.dbo_PRDCT_GRP_MST_TABLE;
		if (isUpdateDb()) {
			tableName = DBConstants.dbo_UPDATE_PRDCT_GRP_MST_TABLE;
		}
		// If response is not null, insert the records in DB Table
		if (gRP_Models != null && gRP_Models.size() > 0) {
			insertGRPRecords(gRP_Models, tableName, true);
		}
	}

	/**
	 * Method- To insert all records to table
	 * 
	 * @param result
	 */
	@SuppressWarnings("unchecked")
	private void saveDepartmentDataInDB(OperationalResult result) {
		ArrayList<PRDCT_DPT_MST_Model> dpt_Models = (ArrayList<PRDCT_DPT_MST_Model>) result
				.getResult().getSerializable(Constants.RESULTCODEBEAN);
		String tableName = DBConstants.dbo_DPT_MST_TABLE;
		if (isUpdateDb()) {
			tableName = DBConstants.dbo_UPDATE_DPT_MST_TABLE;
		}
		// If response is not null, insert the records in DB Table
		if (dpt_Models != null && dpt_Models.size() > 0) {
			insertDPTRecords(dpt_Models, tableName, true);
		}
	}

	/**
	 * Method- To insert all records to table
	 * 
	 * @param result
	 */
	@SuppressWarnings("unchecked")
	private void saveAnalysisDataInDB(OperationalResult result) {
		ArrayList<PRDCT_ANALYS_MST_Model> analys_Models = (ArrayList<PRDCT_ANALYS_MST_Model>) result
				.getResult().getSerializable(Constants.RESULTCODEBEAN);

		String tableName = DBConstants.dbo_ANALYS_MST_TABLE;
		if (isUpdateDb()) {
			tableName = DBConstants.dbo_UPDATE_ANALYS_MST_TABLE;
		}
		// If response is not null, insert the records in DB Table
		if (analys_Models != null && analys_Models.size() > 0) {
			insertANALYSRecords(analys_Models, tableName, true);
		}
	}

	/**
	 * Method- To insert all records to table
	 * 
	 * @param result
	 */
	@SuppressWarnings("unchecked")
	private void saveBinLocDataInDB(OperationalResult result) {
		ArrayList<PRDCT_BINLOC_MST_Model> binLoc_Models = (ArrayList<PRDCT_BINLOC_MST_Model>) result
				.getResult().getSerializable(Constants.RESULTCODEBEAN);
		String tableName = DBConstants.dbo_BIN_LOC_MST_TABLE;
		if (isUpdateDb()) {
			tableName = DBConstants.dbo_UPDATE_BIN_LOC_MST_TABLE;
		}

		// If response is not null, insert the records in DB Table
		if (binLoc_Models != null && binLoc_Models.size() > 0) {
			insertBINLOCRecords(binLoc_Models, tableName, true);
		}
	}

	/**
	 * Method- To insert all records to table
	 * 
	 * @param result
	 */
	@SuppressWarnings("unchecked")
	private void saveProductsDataInDB(OperationalResult result) {
		ArrayList<PRDCT_MST_Model> prdct_Models = (ArrayList<PRDCT_MST_Model>) result
				.getResult().getSerializable(Constants.RESULTCODEBEAN);
		String tableName = DBConstants.dbo_TILL_PRDCT_MST_TABLE;
		if (isUpdateDb()) {
			tableName = DBConstants.dbo_UPDATE_TILL_PRDCT_MST_TABLE;
		}
		// If response is not null, insert the records in DB Table
		if (prdct_Models != null && prdct_Models.size() > 0) {
			// set no of records in user singleton
			UserSingleton.getInstance((Activity) this.mBaseResponseListener)
					.setmNoOfRecords(prdct_Models.size());
			insertPrdctRecords(prdct_Models, tableName, true);
		}
	}

	public void getUserRights(String url, List<NameValuePair> params,
			boolean showProgress) {

		Logger.d(TAG, "getuserAuthenticationData");
		mIsContinueProgressbar = showProgress;
		// Respective N/T Handler
		ActivityHandler activityHandler = new UserRightsNTHandler(this, params,
				url, Constants.GET_USR_ASSGND_RGHTS);
		//
		// Start AsyncTask
		getResult(activityHandler);

		// saveAllUserRightsInDB(createDummyUSER_ASSGND_RIGHTSData());
	}

	@SuppressWarnings("unchecked")
	private void saveAllUserRightsInDB(OperationalResult result) {
		ArrayList<USR_ASSGND_RGHTSModel> userASSGNDRGHTS_Models = (ArrayList<USR_ASSGND_RGHTSModel>) result
				.getResult().getSerializable(Constants.RESULTCODEBEAN);

		String tableName = DBConstants.dbo_USR_ASSGND_RGHTS_TABLE;
		if (isUpdateDb()) {
			tableName = DBConstants.dbo_UPDATE_USR_ASSGND_RGHTS_TABLE;
		}

		if (result != null && userASSGNDRGHTS_Models.size() > 0) {
			mIsContinueProgressbar = true;
			ActivityHandler activityHandler = new USR_ASSGND_RGHTS_DBHandler(
					this, userASSGNDRGHTS_Models, tableName,
					DBOperationlParameter.INSERT,
					Constants.INSERT_USER_ASSGND_RGHTS, isUpdateDb);
			// Start AsyncTask
			getResult(activityHandler);
		}
	}

	public void selectUserRightsFromDB(String classLevelSelectionArg,
			String tableName, boolean showProgress) {
		Logger.d(TAG, "selectUserRightsFromDB");
		mIsContinueProgressbar = showProgress;

		String[] queryParams = { classLevelSelectionArg };

		ActivityHandler activityHandler = new USR_ASSGND_RGHTS_DBHandler(this,
				queryParams, tableName, DBOperationlParameter.SELECT,
				Constants.FETCH_USER_RIGHT_RECORD, isUpdateDb);
		// Start AsyncTask
		getResult(activityHandler);
	}

	public boolean isUpdateDb() {
		return isUpdateDb;
	}

	public void setUpdateDb(boolean isUpdateDb) {
		this.isUpdateDb = isUpdateDb;
	}

	public void deleteGRPMSTTable(String classLevelSelectionArg,
			String tableName, boolean showProgress) {
		mIsContinueProgressbar = showProgress;

		String[] queryParams = { classLevelSelectionArg };

		ActivityHandler activityHandler = new GRP_MST_DBHandler(this,
				queryParams, tableName, DBOperationlParameter.DELETE,
				Constants.DELETE_GRP_MST_TABLE, isUpdateDb);
		// Start AsyncTask
		getResult(activityHandler);
	}

	public void deleteUSER_MSTTable(String classLevelSelectionArg,
			String tableName, boolean showProgress) {
		mIsContinueProgressbar = showProgress;

		String[] queryParams = { classLevelSelectionArg };

		ActivityHandler activityHandler = new GRP_MST_DBHandler(this,
				queryParams, tableName, DBOperationlParameter.DELETE,
				Constants.DELETE_USER_MST_TABLE, isUpdateDb);
		// Start AsyncTask
		getResult(activityHandler);
	}

	public void deletePRDT_DPT_MSTTable(String classLevelSelectionArg,
			String tableName, boolean showProgress) {
		mIsContinueProgressbar = showProgress;

		String[] queryParams = { classLevelSelectionArg };

		ActivityHandler activityHandler = new DPT_MST_DBHandler(this,
				queryParams, tableName, DBOperationlParameter.DELETE,
				Constants.DELETE_PRDT_DPT_MST_TABLE, isUpdateDb);
		// Start AsyncTask
		getResult(activityHandler);
	}

	public void deleteBINLOC_MSTTable(String classLevelSelectionArg,
			String tableName, boolean showProgress) {
		mIsContinueProgressbar = showProgress;

		String[] queryParams = { classLevelSelectionArg };

		ActivityHandler activityHandler = new BINLOC_MST_DBHandler(this,
				queryParams, tableName, DBOperationlParameter.DELETE,
				Constants.DELETE_BINLOC_MST_TABLE, isUpdateDb);
		// Start AsyncTask
		getResult(activityHandler);
	}

	public void deleteANALYS_MSTTable(String classLevelSelectionArg,
			String tableName, boolean showProgress) {
		mIsContinueProgressbar = showProgress;

		String[] queryParams = { classLevelSelectionArg };

		ActivityHandler activityHandler = new ANALYS_MST_DBHandler(this,
				queryParams, tableName, DBOperationlParameter.DELETE,
				Constants.DELETE_ANALYS_MST_TABLE, isUpdateDb);
		// Start AsyncTask
		getResult(activityHandler);
	}

	public void deletePRDCT_MST_MSTTable(String classLevelSelectionArg,
			String tableName, boolean showProgress) {
		mIsContinueProgressbar = showProgress;

		String[] queryParams = { classLevelSelectionArg };

		ActivityHandler activityHandler = new PRDCT_MST_DBHandler(this,
				queryParams, tableName, DBOperationlParameter.DELETE,
				Constants.DELETE_PRDT_REC_MST_TABLE, isUpdateDb);
		// Start AsyncTask
		getResult(activityHandler);
	}

	public void deleteUOM_MSTTable(String classLevelSelectionArg,
			String tableName, boolean showProgress) {
		mIsContinueProgressbar = showProgress;

		String[] queryParams = { classLevelSelectionArg };

		ActivityHandler activityHandler = new UomDBHandler(this, queryParams,
				tableName, DBOperationlParameter.DELETE,
				Constants.DELETE_UOM_MST_TABLE, isUpdateDb);
		// Start AsyncTask
		getResult(activityHandler);
	}

	public void deleteUOMSLAB_MSTTable(String classLevelSelectionArg,
			String tableName, boolean showProgress) {
		mIsContinueProgressbar = showProgress;

		String[] queryParams = { classLevelSelectionArg };

		ActivityHandler activityHandler = new Uom_Slab_DBHandler(this,
				queryParams, tableName, DBOperationlParameter.DELETE,
				Constants.DELETE_UOM_SLAB_MST_TABLE, isUpdateDb);
		// Start AsyncTask
		getResult(activityHandler);
	}

	public void deleteCurrency_MSTTable(String classLevelSelectionArg,
			String tableName, boolean showProgress) {
		mIsContinueProgressbar = showProgress;

		String[] queryParams = { classLevelSelectionArg };

		ActivityHandler activityHandler = new Currency_MstDBHandler(this,
				queryParams, tableName, DBOperationlParameter.DELETE,
				Constants.DELETE_CURRENCY_MST_TABLE, isUpdateDb);
		// Start AsyncTask
		getResult(activityHandler);
	}

	public void deletePrdctInstr_MSTTable(String classLevelSelectionArg,
			String tableName, boolean showProgress) {
		mIsContinueProgressbar = showProgress;

		String[] queryParams = { classLevelSelectionArg };

		ActivityHandler activityHandler = new Prdct_Instruc_DBHandler(this,
				queryParams, tableName, DBOperationlParameter.DELETE,
				Constants.DELETE_PRDCT_INSTRCT_MST_TABLE, isUpdateDb);
		// Start AsyncTask
		getResult(activityHandler);
	}

	public void deleteRCEPI_DTLTable(String classLevelSelectionArg,
			String tableName, boolean showProgress) {
		mIsContinueProgressbar = showProgress;

		ActivityHandler activityHandler = new PRDCT_RECIPE_DTL_MST_DBHandler(
				this, null, tableName, DBOperationlParameter.DELETE,
				Constants.DELETE_RECPI_DTL_TABLE, isUpdateDb);
		// Start AsyncTask
		getResult(activityHandler);
	}

	public void deleteUSER_ASSGND_RGHTS_MSTTable(String classLevelSelectionArg,
			String tableName, boolean showProgress) {
		mIsContinueProgressbar = showProgress;

		String[] queryParams = { classLevelSelectionArg };

		ActivityHandler activityHandler = new USR_ASSGND_RGHTS_DBHandler(this,
				queryParams, tableName, DBOperationlParameter.DELETE,
				Constants.DELETE_USER_ASSGND_RGHTS_MST_TABLE, isUpdateDb);
		// Start AsyncTask
		getResult(activityHandler);
	}

	public void deleteVAT_DATA_MSTTable(String classLevelSelectionArg,
			String tableName, boolean showProgress) {
		mIsContinueProgressbar = showProgress;

		String[] queryParams = { classLevelSelectionArg };

		ActivityHandler activityHandler = new VATDBHandler(this, queryParams,
				tableName, DBOperationlParameter.DELETE,
				Constants.DELETE_VAT_DATA_MST_TABLE, isUpdateDb);
		// Start AsyncTask
		getResult(activityHandler);
	}

	public void deleteSCAN_CODES_MSTTable(String classLevelSelectionArg,
			String tableName, boolean showProgress) {
		mIsContinueProgressbar = showProgress;

		String[] queryParams = { classLevelSelectionArg };

		ActivityHandler activityHandler = new ScanCodesDBHandler(this,
				queryParams, tableName, DBOperationlParameter.DELETE,
				Constants.DELETE_SCAN_CODE_MST_TABLE, isUpdateDb);
		// Start AsyncTask
		getResult(activityHandler);
	}

	public void deleteSLMN_MSTTable(String classLevelSelectionArg,
			String tableName, boolean showProgress) {
		mIsContinueProgressbar = showProgress;

		String[] queryParams = { classLevelSelectionArg };

		ActivityHandler activityHandler = new SLMN_MSTDBHandler(this,
				queryParams, tableName, DBOperationlParameter.DELETE,
				Constants.DELETE_SCAN_CODE_MST_TABLE, isUpdateDb);
		// Start AsyncTask
		getResult(activityHandler);
	}

	public void deleteflxPOSTable(String classLevelSelectionArg,
			String tableName, boolean showProgress) {
		mIsContinueProgressbar = showProgress;

		String[] queryParams = { classLevelSelectionArg };

		ActivityHandler activityHandler = new FLX_POS_MSTDBHandler(this,
				queryParams, tableName, DBOperationlParameter.DELETE,
				Constants.DELETE_FLX_POS_MST, isUpdateDb);
		// Start AsyncTask
		getResult(activityHandler);
	}

	public void copyGRPMSTTable(String string, String tableName,
			boolean showProgress) {
		mIsContinueProgressbar = showProgress;

		ActivityHandler activityHandler = new GRP_MST_DBHandler(this,
				DBConstants.dbo_PRDCT_GRP_MST_TABLE,
				DBConstants.dbo_UPDATE_PRDCT_GRP_MST_TABLE,
				DBOperationlParameter.COPY_TABLE, Constants.COPY_GRP_MST_TABLE,
				isUpdateDb);
		// Start AsyncTask
		getResult(activityHandler);
	}

	public void copyPRDT_DPT_MSTTable(String string, String tableName,
			boolean showProgress) {
		mIsContinueProgressbar = showProgress;

		ActivityHandler activityHandler = new DPT_MST_DBHandler(this,
				DBConstants.dbo_DPT_MST_TABLE,
				DBConstants.dbo_UPDATE_DPT_MST_TABLE,
				DBOperationlParameter.COPY_TABLE,
				Constants.COPY_PRDT_DPT_MST_TABLE, isUpdateDb);
		// Start AsyncTask
		getResult(activityHandler);
	}

	public void copyBINLOC_MSTTable(String string, String tableName,
			boolean showProgress) {
		mIsContinueProgressbar = showProgress;

		ActivityHandler activityHandler = new BINLOC_MST_DBHandler(this,
				DBConstants.dbo_BIN_LOC_MST_TABLE,
				DBConstants.dbo_UPDATE_BIN_LOC_MST_TABLE,
				DBOperationlParameter.COPY_TABLE,
				Constants.COPY_BINLOC_MST_TABLE, isUpdateDb);
		// Start AsyncTask
		getResult(activityHandler);
	}

	public void copyANALYS_MSTTable(String string, String tableName,
			boolean showProgress) {
		mIsContinueProgressbar = showProgress;

		ActivityHandler activityHandler = new ANALYS_MST_DBHandler(this,
				DBConstants.dbo_ANALYS_MST_TABLE,
				DBConstants.dbo_UPDATE_ANALYS_MST_TABLE,
				DBOperationlParameter.COPY_TABLE,
				Constants.COPY_ANALYS_MST_TABLE, isUpdateDb);
		// Start AsyncTask
		getResult(activityHandler);
	}

	public void copyPRDCT_MST_MSTTable(String string, String tableName,
			boolean showProgress) {
		mIsContinueProgressbar = showProgress;

		ActivityHandler activityHandler = new PRDCT_MST_DBHandler(this,
				DBConstants.dbo_TILL_PRDCT_MST_TABLE,
				DBConstants.dbo_UPDATE_TILL_PRDCT_MST_TABLE,
				DBOperationlParameter.COPY_TABLE,
				Constants.COPY_PRDCT_MST_TABLE, isUpdateDb);
		// Start AsyncTask
		getResult(activityHandler);
	}

	public void copyUOM_MSTTable(String string, String tableName,
			boolean showProgress) {
		mIsContinueProgressbar = showProgress;

		ActivityHandler activityHandler = new UomDBHandler(this,
				DBConstants.dbo_UOM_TABLE, DBConstants.dbo_UPDATE_UOM_TABLE,
				DBOperationlParameter.COPY_TABLE, Constants.COPY_UOM_MST_TABLE,
				isUpdateDb);
		// Start AsyncTask
		getResult(activityHandler);
	}

	public void copyUOMSLAB_MSTTable(String string, String tableName,
			boolean showProgress) {
		mIsContinueProgressbar = showProgress;

		ActivityHandler activityHandler = new Uom_Slab_DBHandler(this,
				DBConstants.dbo_UOM_SLAB_MST_TABLE,
				DBConstants.dbo_UPDATE_UOM_SLAB_MST_TABLE,
				DBOperationlParameter.COPY_TABLE,
				Constants.COPY_UOM_SLAB_MST_TABLE, isUpdateDb);
		// Start AsyncTask
		getResult(activityHandler);
	}

	public void copyCurrency_MSTTable(String string, String tableName,
			boolean showProgress) {
		mIsContinueProgressbar = showProgress;

		ActivityHandler activityHandler = new Currency_MstDBHandler(this,
				DBConstants.dbo_Currency_Mst_TABLE,
				DBConstants.dbo_UPDATE_Currency_Mst_TABLE,
				DBOperationlParameter.COPY_TABLE,
				Constants.COPY_CURRENCY_MST_TABLE, isUpdateDb);
		// Start AsyncTask
		getResult(activityHandler);
	}

	public void copyUSER_ASSGND_RGHTS_MSTTable(String string, String tableName,
			boolean showProgress) {
		mIsContinueProgressbar = showProgress;

		ActivityHandler activityHandler = new USR_ASSGND_RGHTS_DBHandler(this,
				DBConstants.dbo_USR_ASSGND_RGHTS_TABLE,
				DBConstants.dbo_UPDATE_USR_ASSGND_RGHTS_TABLE,
				DBOperationlParameter.COPY_TABLE,
				Constants.COPY_USER_ASSGND_RGHTS_MST_TABLE, isUpdateDb);
		// Start AsyncTask
		getResult(activityHandler);
	}

	public void copyVAT_DATA_MSTTable(String string, String tableName,
			boolean showProgress) {
		mIsContinueProgressbar = showProgress;

		ActivityHandler activityHandler = new VATDBHandler(this,
				DBConstants.dbo_VAT_MST_TABLE,
				DBConstants.dbo_UPDATE_VAT_MST_TABLE,
				DBOperationlParameter.COPY_TABLE,
				Constants.COPY_VAT_DATA_MST_TABLE, isUpdateDb);
		// Start AsyncTask
		getResult(activityHandler);
	}

	public void copySCAN_CODES_MSTTable(String string, String tableName,
			boolean showProgress) {
		mIsContinueProgressbar = showProgress;

		ActivityHandler activityHandler = new ScanCodesDBHandler(this,
				DBConstants.dbo_SCAN_CODS_MST_TABLE,
				DBConstants.dbo_UPDATE_SCAN_CODS_MST_TABLE,
				DBOperationlParameter.COPY_TABLE,
				Constants.COPY_SCAN_CODE_MST_TABLE, isUpdateDb);
		// Start AsyncTask
		getResult(activityHandler);
	}

	public void copySLMN_MSTTable(String string, String tableName,
			boolean showProgress) {
		mIsContinueProgressbar = showProgress;

		ActivityHandler activityHandler = new SLMN_MSTDBHandler(this,
				DBConstants.dbo_SLMN_MST_TABLE,
				DBConstants.dbo_UPDATE_SLMN_MST_TABLE,
				DBOperationlParameter.COPY_TABLE, Constants.COPY_SLMN_TABLE,
				isUpdateDb);
		// Start AsyncTask
		getResult(activityHandler);
	}

	public void copyPRDT_RECIPI_DTLTABLE(String string, String tableName,
			boolean showProgress) {
		mIsContinueProgressbar = showProgress;

		ActivityHandler activityHandler = new PRDCT_RECIPE_DTL_MST_DBHandler(
				this, DBConstants.dbo_PRDCT_RECEIPE_MST_TABLE,
				DBConstants.dbo_UPDATE_PRDCT_RECEIPE_MST_TABLE,
				DBOperationlParameter.COPY_TABLE,
				Constants.COPY_PRDT_RECIPI_DTL_TABLE, isUpdateDb);
		// Start AsyncTask
		getResult(activityHandler);
	}

	public void copyFLX_POSTABLE(String string, String tableName,
			boolean showProgress) {
		mIsContinueProgressbar = showProgress;

		ActivityHandler activityHandler = new FLX_POS_MSTDBHandler(this,
				DBConstants.dbo_FLX_POS_MST_TABLE,
				DBConstants.dbo_UPDATE_FLX_POS_MST_TABLE,
				DBOperationlParameter.COPY_TABLE, Constants.COPY_FLX_POS_TABLE,
				isUpdateDb);
		// Start AsyncTask
		getResult(activityHandler);
	}

	public void copyInstruc_MSTTable(String string, String tableName,
			boolean showProgress) {
		mIsContinueProgressbar = showProgress;

		ActivityHandler activityHandler = new ModifierDBHandler(this,
				DBConstants.dbo_INSTRCTN_MST_TABLE,
				DBConstants.dbo_UPDATE_INSTRCTN_MST_TABLE,
				DBOperationlParameter.COPY_TABLE,
				Constants.COPY_INSTRCTION_MST_TABLE, isUpdateDb);
		// Start AsyncTask
		getResult(activityHandler);
	}

	public void copyPRDCT_Instruc_MSTTable(String string, String tableName,
			boolean showProgress) {
		mIsContinueProgressbar = showProgress;

		ActivityHandler activityHandler = new Prdct_Instruc_DBHandler(this,
				DBConstants.dbo_PRDCT_INSTRCTN_MST_TABLE,
				DBConstants.dbo_UPDATE_PRDCT_INSTRCTN_MST_TABLE,
				DBOperationlParameter.COPY_TABLE,
				Constants.COPY_PRDCT_INSTRUC_MST_TABLE, isUpdateDb);
		// Start AsyncTask
		getResult(activityHandler);
	}

	public void getDNMTN_MSTData(String url, List<NameValuePair> params,
			boolean showProgress) {
		Logger.d(TAG, "getuserAuthenticationData");
		mIsContinueProgressbar = showProgress;
		// Respective N/T Handler
		ActivityHandler activityHandler = new DNMTN_MST_NTHandler(this, params,
				url, Constants.GET_DNMTN_MST);
		//
		// Start AsyncTask
		getResult(activityHandler);

	}

	public void deleteDNMTN_MSTTable(String classLevelSelectionArg,
			String tableName, boolean showProgress) {
		mIsContinueProgressbar = showProgress;

		String[] queryParams = { classLevelSelectionArg };

		ActivityHandler activityHandler = new DNMTN_MSTDBHandler(this,
				queryParams, tableName, DBOperationlParameter.DELETE,
				Constants.DELETE_DNMTN_MST, isUpdateDb);
		// Start AsyncTask
		getResult(activityHandler);
	}

	public void deleteINstruc_MSTTable(String classLevelSelectionArg,
			String tableName, boolean showProgress) {
		mIsContinueProgressbar = showProgress;

		String[] queryParams = { classLevelSelectionArg };

		ActivityHandler activityHandler = new ModifierDBHandler(this,
				queryParams, tableName, DBOperationlParameter.DELETE,
				Constants.DELETE_INSTRUC_MST_TABLE, isUpdateDb);
		// Start AsyncTask
		getResult(activityHandler);
	}

	public void dropAllUPdateMasterTable() {

		DatabaseHelper mDataBaseHelper = DatabaseHelper.getDBInstance();/*new DatabaseHelper(
				MPOSApplication.getContext());*/
		if (DBHandler.mSQLiteDB == null) {
			DBHandler.mSQLiteDB = mDataBaseHelper.getWritableDatabase();
		} else if (DBHandler.mSQLiteDB != null && !DBHandler.mSQLiteDB.isOpen()) {
			DBHandler.mSQLiteDB = mDataBaseHelper.getWritableDatabase();
		}
		// mSQLiteDB.delete(mOperationalResult.getTableName(), null, null, null,
		// null);
		DBHandler.mSQLiteDB.delete(DBConstants.dbo_UPDATE_TILL_PRDCT_MST_TABLE,
				null, null);
		DBHandler.mSQLiteDB.delete(DBConstants.dbo_UPDATE_PRDCT_GRP_MST_TABLE,
				null, null);
		DBHandler.mSQLiteDB.delete(DBConstants.dbo_UPDATE_DPT_MST_TABLE, null,
				null);
		DBHandler.mSQLiteDB.delete(DBConstants.dbo_UPDATE_ANALYS_MST_TABLE,
				null, null);
		DBHandler.mSQLiteDB.delete(DBConstants.dbo_UPDATE_BIN_LOC_MST_TABLE,
				null, null);
		DBHandler.mSQLiteDB.delete(DBConstants.dbo_UPDATE_USER_MST_TABLE, null,
				null);
		DBHandler.mSQLiteDB.delete(
				DBConstants.dbo_UPDATE_USR_ASSGND_RGHTS_TABLE, null, null);
		DBHandler.mSQLiteDB.delete(DBConstants.dbo_UPDATE_Currency_Mst_TABLE,
				null, null);
		DBHandler.mSQLiteDB.delete(DBConstants.dbo_UPDATE_UOM_SLAB_MST_TABLE,
				null, null);
		DBHandler.mSQLiteDB
				.delete(DBConstants.dbo_UPDATE_UOM_TABLE, null, null);
		DBHandler.mSQLiteDB.delete(DBConstants.dbo_UPDATE_VAT_MST_TABLE, null,
				null);
		DBHandler.mSQLiteDB.delete(DBConstants.dbo_UPDATE_SCAN_CODS_MST_TABLE,
				null, null);
		DBHandler.mSQLiteDB.delete(
				DBConstants.dbo_UPDATE_PRDCT_RECEIPE_MST_TABLE, null, null);
		DBHandler.mSQLiteDB.delete(DBConstants.dbo_UPDATE_FLX_POS_MST_TABLE,
				null, null);
		DBHandler.mSQLiteDB.delete(DBConstants.dbo_UPDATE_SLMN_MST_TABLE, null,
				null);
		DBHandler.mSQLiteDB.delete(DBConstants.dbo_UPDATE_DNMTN_MST_TABLE,
				null, null);
		DBHandler.mSQLiteDB.delete(DBConstants.dbo_UPDATE_INSTRCTN_MST_TABLE,
				null, null);
		DBHandler.mSQLiteDB.delete(
				DBConstants.dbo_UPDATE_PRDCT_INSTRCTN_MST_TABLE, null, null);
	}

}
