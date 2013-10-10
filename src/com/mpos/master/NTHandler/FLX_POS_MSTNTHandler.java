package com.mpos.master.NTHandler;

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
import com.mpos.master.model.FLX_POS_Model;

public class FLX_POS_MSTNTHandler extends NTHandler {

	public FLX_POS_MSTNTHandler(HandlerInf handlerInf,
			List<NameValuePair> params, String url, int resultCode) {
		super(handlerInf, params, url, resultCode);
		setParser();
	}

	private static final String BILLHEADING1 = "billHeading1";
	private static final String BILLHEADING2 = "billHeading2";
	private static final String BILLHEADING3 = "billHeading3";
	private static final String BILLHEADING4 = "billHeading4";
	private static final String BILLHEADING5 = "billHeading5";
	private static final String BILLHEADING6 = "billHeading6";
	private static final String BILLHEADING7 = "billHeading7";
	private static final String BILLHEADING8 = "billHeading8";

	@Override
	protected void setParser() {
		mNTParser = this;
	}

	/**
	 * Method- To create SLMN_MST models and fill it in array
	 * 
	 * @param jArr
	 * @return
	 * @throws JSONException
	 */
	private FLX_POS_Model getFLXPOSModel(JSONArray jArr) throws JSONException {
		

		FLX_POS_Model model = new FLX_POS_Model();
		JSONObject jObj = jArr.getJSONObject(0);
		if(jObj!=null){
			model.setBILLHEADING1(jObj.optString(BILLHEADING1));
			model.setBILLHEADING2(jObj.optString(BILLHEADING2));
			model.setBILLHEADING3(jObj.optString(BILLHEADING3));
			model.setBILLHEADING4(jObj.optString(BILLHEADING4));
			model.setBILLHEADING5(jObj.optString(BILLHEADING5));
			model.setBILLHEADING6(jObj.optString(BILLHEADING6));
			model.setBILLHEADING7(jObj.optString(BILLHEADING7));
			model.setBILLHEADING8(jObj.optString(BILLHEADING8));
		}
		return model;
	}

	/**
	 * Method- To parse WS response data
	 */
	@Override
	protected void getParsedData(String responseData) {
		String response = responseData.toString();
		FLX_POS_Model dataArr;
		try {
			JSONArray jArr = new JSONArray(response);
			dataArr = getFLXPOSModel(jArr);
			bundle.putSerializable(Constants.RESULTCODEBEAN, dataArr);
			mOperationalResult.setResult(bundle);
		} catch (JSONException e) {
			e.printStackTrace();
			Logger.w(TAG, e.toString());
			//Log.d(TAG, "## pulsar ## FLX_POS_MSNT_HANDLER");
			//mOperationalResult.setRequestResponseCode(OperationalResult.ERROR);
			mOperationalResult.setApiName("FIX_POS_MST");
			mOperationalResult.setRequestResponseCode(OperationalResult.DATA_PROTOCOL_ERROR);
		}
	}
}
