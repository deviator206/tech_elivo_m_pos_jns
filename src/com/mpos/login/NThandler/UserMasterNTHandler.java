/*
 * Copyright (C) 2013  All Rights Reserved.
 *  PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.mpos.login.NThandler;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mpos.framework.common.Constants;
import com.mpos.framework.common.Logger;
import com.mpos.framework.common.OperationalResult;
import com.mpos.framework.handler.NTHandler;
import com.mpos.framework.inf.HandlerInf;
import com.mpos.login.model.USER_MST_Model;

/**
 * Description- 
 * 

 */

public class UserMasterNTHandler extends NTHandler {

	public static final String TAG = Constants.APP_TAG
			+ UserMasterNTHandler.class.getSimpleName();
	
	public UserMasterNTHandler(HandlerInf handlerInf,
			List<NameValuePair> params, String url, int resultCode) {
		super(handlerInf, params, url, resultCode);
		setParser();
	}

	@Override
	protected void setParser() {
		// set parser object to "this", means respective parser will be called
				mNTParser = this;
	}

	private final String USERFULLNAME = "usrFullName";
	private final String  USERSCNCD = "usrScncd";
	private final String USERID = "usrId";
	private final String USERNM = "usrNm";
	private final String USERGRPID = "usrGrpId";
	private final String TABUSERPWD = "tabUsrPwd";
    	
	@Override
	protected void getParsedData(String response) {

		try {
			JSONArray jArr = new JSONArray(response);
			ArrayList<USER_MST_Model> dataArr = new ArrayList<USER_MST_Model>();
			
			for (int i = 0; i < jArr.length(); i++) {
				JSONObject userJSONObj = jArr.getJSONObject(i);
				USER_MST_Model userModel = new USER_MST_Model();
				userModel.setUSR_FULL_NAME(userJSONObj.optString(USERFULLNAME));
				userModel.setUSR_SCNCD(userJSONObj.optString(USERSCNCD));
				userModel.setUSR_ID(userJSONObj.optString(USERID));
				userModel.setUSR_NM(userJSONObj.optString(USERNM));
				userModel.setUSR_GRP_ID(userJSONObj.optString(USERGRPID));
				userModel.setUSR_PWD(userJSONObj.getString(TABUSERPWD));
				userModel.setUSR_BIO("");
				userModel.setUSR_CRTN_DATE("");
				userModel.setUSR_MODFD_BY("");
				dataArr.add(userModel);
			}
			bundle.putSerializable(Constants.RESULTCODEBEAN, dataArr);
			Logger.v(TAG, "user master arr"+dataArr.toString());
			mOperationalResult.setResult(bundle);
			/*
			 * } else { String errorCode = jObject.getString(JSON);
			 * mOperationalResult
			 * .setResponseErrorCode(convertErrorCode(errorCode)); }
			 */
		} catch (JSONException e) {
			e.printStackTrace();
			Logger.w(TAG, e.toString());
			//mOperationalResult.setRequestResponseCode(OperationalResult.ERROR);
			mOperationalResult.setApiName("User Master");
			mOperationalResult.setRequestResponseCode(OperationalResult.DATA_PROTOCOL_ERROR);
		}

	}

}
