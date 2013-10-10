/*
 * Copyright (C) 2013  All Rights Reserved.
 *  PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.mpos.master.NTHandler;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.mpos.framework.common.Constants;
import com.mpos.framework.common.Logger;
import com.mpos.framework.common.OperationalResult;
import com.mpos.framework.handler.NTHandler;
import com.mpos.framework.inf.HandlerInf;
import com.mpos.master.model.USR_ASSGND_RGHTSModel;

/**
 * Description-
 * 

 */

public class UserRightsNTHandler extends NTHandler {

	public UserRightsNTHandler(HandlerInf handlerInf,
			List<NameValuePair> params, String url, int resultCode) {
		super(handlerInf, params, url, resultCode);
		setParser();
	}

	@Override
	protected void setParser() {
		mNTParser = this;

	}

	private final String RGHTEDIT="rghtEdit" ;
	private final String FLDTYPE="fldType" ;
	private final String USRID="usrId" ;
	private final String GRPID="grpId" ;
	private final String RFRNCEID="rfrnceId" ;
	private final String RGHTDEL="rghtDel";
	private final String RGHTADD="rghtAdd"; 
	private final String BRNCHCODE="brnchCode"; 
	private final String RGHTVALUE="rghtValue";
	private final String RGHTVIEW="rghtView";
	private final String RGHTNAME="rghtName";

	@Override
	protected void getParsedData(String response) {

		try {
			JSONArray jArr = new JSONArray(response);
			ArrayList<USR_ASSGND_RGHTSModel> dataArr = new ArrayList<USR_ASSGND_RGHTSModel>();

			for (int i = 0; i < jArr.length(); i++) {
				JSONObject userJSONObj = jArr.getJSONObject(i);
				USR_ASSGND_RGHTSModel userAsgnRgtsModel = new USR_ASSGND_RGHTSModel();
				userAsgnRgtsModel.setRFRNCE_ID(userJSONObj.optString(GRPID).trim());
				userAsgnRgtsModel.setFLD_TYPE(userJSONObj.optString(FLDTYPE).trim());
				userAsgnRgtsModel.setRGHT_VALUE(userJSONObj.optString(RGHTVALUE).trim());
				userAsgnRgtsModel.setRGHT_NAME(userJSONObj.optString(RGHTNAME).trim());
				dataArr.add(userAsgnRgtsModel);
			}
			bundle.putSerializable(Constants.RESULTCODEBEAN, dataArr);
			Logger.v(TAG, "user master arr" + dataArr.toString());
			Log.d(TAG, "## pulsar ## UserRights_NT_HANDLER");
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
			mOperationalResult.setApiName("User Rights");
			mOperationalResult.setRequestResponseCode(OperationalResult.DATA_PROTOCOL_ERROR);
		}

	}

}
