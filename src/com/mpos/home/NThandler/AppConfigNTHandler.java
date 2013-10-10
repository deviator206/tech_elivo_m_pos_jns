/*
 * Copyright (C) 2013 USAP Pvt. Ltd. All Rights Reserved.
 * jaggarnaut-studios pvt. ltd. PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.mpos.home.NThandler;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;

import com.mpos.framework.common.Constants;
import com.mpos.framework.common.Logger;
import com.mpos.framework.common.OperationalResult;
import com.mpos.framework.handler.NTHandler;
import com.mpos.framework.inf.HandlerInf;
import com.mpos.home.model.App_Config_Model;
import com.mpos.home.model.Company_Dtls_Models;
import com.mpos.home.model.Company_PolicyModel;
import com.mpos.home.model.Till_Model;
import com.mpos.master.NTHandler.GroupNTHandler;

/**
 * Description-
 * 
 * @author jaggarnaut-studios
 */

public class AppConfigNTHandler extends NTHandler {

	public static final String TAG = Constants.APP_TAG
			+ GroupNTHandler.class.getSimpleName();

	protected Bundle bundle = new Bundle();

	// CMPNY_DTL_MODEL Constants
	private final String CMPNY_DTLS = "cmpnyDtls";
	private final String REG_TYPE = "regType";
	private final String CMPNY_NAME = "cmpnyName";
	private final String CMPNY_PIN = "cmpnyPin";
	private final String CMPNY_FAX = "cmpnyFax";
	private final String CMPNY_BRNACH_NO = "cmpnyBrnchNo";
	private final String CMPNY_RGSTRN_NO = "cmpnyRgstrtnNo";
	private final String CMPNY_ADDRE1 = "cmpnyAdrs1";
	private final String CMPNY_ADDRE2 = "cmpnyAdrs2";
	private final String CMPNY_ADDRE3 = "cmpnyAdrs3";
	private final String CMPNY_TELE = "cmpnyTlphne1";

	// Till MOdel Constants
	private final String TILL = "till";
	private final String CMPNY_CODE = "cmpnyCode";
	private final String TILL_NO = "tillNo";
	private final String TILL_REG_TYPE = "regType";
	private final String MCHNE_NAME = "mchneName";
	private final String MAX_ZED_NO = "maxZedNo";
	private final String RUN_DTE = "runDate";
	private final String SQL_DB_NAME = "sqldatabasename";
	private final String SQL_SERVER_NAME = "sqlservername";
	private final String DEFAULT_BRANCH_CODE = "defaultBrnchCode";
	private final String CURR_SHIFT_CODE = "curShiftCode";
	private final String NEXT_SHIFT_CODE = "nextShiftCode";
	
	private final String WSPORT = "prntPortNum";
	private final String WSPORTTYPE = "prntPort";
	private final String WSPRINTERTYPE = "tillPrntrType";
	private final String WSPRINTERMODEL = "prntPortType";
	

	// Company policy values
	private final String CMPNY_PLY = "cmpnyPlcy";
	private final String CMPNY_PLY_VALUE = "cmpnyPlcyVlue";

	public AppConfigNTHandler(HandlerInf handlerInf,
			List<NameValuePair> params, String url, int resultCode) {
		super(handlerInf, params, url, resultCode);
		setParser();
	}

	@Override
	protected void setParser() {
		// set parser object to "this", means respective parser will be called
		mNTParser = this;
	}

	/**
	 * Method- To parse WS response data
	 */
	@Override
	protected void getParsedData(String responseData) {
		String response = responseData.toString();

		App_Config_Model dataObj;

		try {
			JSONObject jObj = new JSONObject(response);
			dataObj = getAppConfig(jObj);
			bundle.putSerializable(Constants.RESULTCODEBEAN, dataObj);
			mOperationalResult.setResult(bundle);
		} catch (JSONException e) {
			e.printStackTrace();
			//Logger.w(TAG, "## PULSAR ## ERROR::"+e.toString());
			//mOperationalResult.setRequestResponseCode(OperationalResult.ERROR);
			mOperationalResult.setApiName("App Config");
			mOperationalResult
					.setRequestResponseCode(OperationalResult.DATA_PROTOCOL_ERROR);
		}

	}

	/***
	 * Method- To create APP config models and fill it
	 * 
	 * @param jObj
	 * @return
	 * @throws JSONException
	 */
	private App_Config_Model getAppConfig(JSONObject jObj) throws JSONException {
		App_Config_Model app_Config_Model = new App_Config_Model();

		// GEt Till Data
		if (jObj.optJSONArray(TILL) != null) {
			JSONArray tillJArr = jObj.getJSONArray(TILL);

			if (tillJArr.optJSONObject(0) != null) {
				JSONObject tillJObj = tillJArr.getJSONObject(0);

				Till_Model till_Model = new Till_Model();
				till_Model.setCmpny_code(tillJObj.optString(CMPNY_CODE));
				till_Model.setCurr_shift_Code(tillJObj
						.optString(CURR_SHIFT_CODE));
				till_Model.setDefault_branch_code(tillJObj
						.optString(DEFAULT_BRANCH_CODE));
				till_Model.setMax_zed_no(tillJObj.optString(MAX_ZED_NO));
				till_Model.setMchne_name(tillJObj.optString(MCHNE_NAME));
				till_Model.setNext_shift_code(tillJObj
						.optString(NEXT_SHIFT_CODE));
				till_Model.setReg_type(tillJObj.optString(TILL_REG_TYPE));
				till_Model.setRun_date(tillJObj.optString(RUN_DTE));
				till_Model.setSql_db_name(tillJObj.optString(SQL_DB_NAME));
				till_Model.setSql_server_name(tillJObj
						.optString(SQL_SERVER_NAME));
				till_Model.setTill_no(tillJObj.optString(TILL_NO));
				
				//sandeep : added new till type for printer api
				till_Model.setPort(tillJObj.optString(WSPORT));
				till_Model.setPort_type(tillJObj.optString(WSPORTTYPE));
				till_Model.setPrinter_type(tillJObj.optString(WSPRINTERTYPE));
				till_Model.setPrinter_model(tillJObj.optString(WSPRINTERMODEL));
				

				app_Config_Model.setTill_Model(till_Model);
			}
		}

		// Company Dtls data
		if (jObj.optJSONArray(CMPNY_DTLS) != null) {
			JSONArray dtlsJArr = jObj.getJSONArray(CMPNY_DTLS);

			if (dtlsJArr.optJSONObject(0) != null) {
				JSONObject dtlsJObj = dtlsJArr.getJSONObject(0);

				Company_Dtls_Models dtls_Models = new Company_Dtls_Models();
				dtls_Models
						.setCmpny_Address_1(dtlsJObj.optString(CMPNY_ADDRE1));
				dtls_Models
						.setCmpny_address_2(dtlsJObj.optString(CMPNY_ADDRE2));
				dtls_Models
						.setCmpny_address_3(dtlsJObj.optString(CMPNY_ADDRE3));
				dtls_Models.setCmpny_branch_no(dtlsJObj
						.optString(CMPNY_BRNACH_NO));
				dtls_Models.setCmpny_fax(dtlsJObj.optString(CMPNY_FAX));
				dtls_Models.setCmpny_name(dtlsJObj.optString(CMPNY_NAME));
				dtls_Models.setCmpny_pin(dtlsJObj.optString(CMPNY_PIN));
				dtls_Models.setCmpny_regstrn_no(dtlsJObj
						.optString(CMPNY_RGSTRN_NO));
				dtls_Models.setRegType(dtlsJObj.optString(REG_TYPE));
				dtls_Models.setCmpny_tele(dtlsJObj.optString(CMPNY_TELE));

				app_Config_Model.setCompany_Dtls_Models(dtls_Models);
			}
		}

		// Company Policy data
		if (jObj.optJSONArray(CMPNY_PLY) != null) {
			JSONArray plcyJArr = jObj.getJSONArray(CMPNY_PLY);
			ArrayList<Company_PolicyModel> plyModels = new ArrayList<Company_PolicyModel>();

			for (int i = 0; i < plcyJArr.length(); i++) {
				JSONObject plcyJObj = plcyJArr.getJSONObject(i);
				Company_PolicyModel policyModel = new Company_PolicyModel();

				policyModel.setReg_type(plcyJObj.optString(REG_TYPE));
				policyModel.setCmpny_ply(plcyJObj.optString(CMPNY_PLY));
				policyModel.setCmpny_ply_value(plcyJObj
						.optString(CMPNY_PLY_VALUE));

				plyModels.add(policyModel);
			}

			app_Config_Model.setCmpnyPlcyModels(plyModels);
		}

		return app_Config_Model;
	}

}
