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
import com.mpos.master.model.DNMTN_MSTModel;

public class DNMTN_MST_NTHandler extends NTHandler {

	public DNMTN_MST_NTHandler(HandlerInf handlerInf,
			List<NameValuePair> params, String url, int resultCode) {
		super(handlerInf, params, url, resultCode);
		setParser();
	}

	@Override
	protected void setParser() {
		mNTParser = this;
	}

	private static final String VALUE = "value";
	private static final String CURR_CODE = "currCode";
	private static final String UNIT = "unit";

	@Override
	protected void getParsedData(String responseData) {
		String response = responseData.toString();
		ArrayList<DNMTN_MSTModel> dataArr;
		try {
			JSONArray jArr = new JSONArray(response);
			dataArr = getDenomination(jArr);
			bundle.putSerializable(Constants.RESULTCODEBEAN, dataArr);
			mOperationalResult.setResult(bundle);
		} catch (JSONException e) {
			e.printStackTrace();
			Logger.w(TAG, e.toString());
			//Log.d(TAG, "## pulsar ## DNMTN_NT_HANDLER");
			//mOperationalResult.setRequestResponseCode(OperationalResult.ERROR);
			mOperationalResult.setApiName("DNMTN_MST");
			mOperationalResult.setRequestResponseCode(OperationalResult.DATA_PROTOCOL_ERROR);
		}
	}

	private ArrayList<DNMTN_MSTModel> getDenomination(JSONArray jArr) throws JSONException {
		ArrayList<DNMTN_MSTModel> denominationList = new ArrayList<DNMTN_MSTModel>();
		if (jArr != null) {
			for (int pos = 0; pos < jArr.length(); pos++) {
				JSONObject obj = jArr.getJSONObject(pos);
				DNMTN_MSTModel denomination = new DNMTN_MSTModel();
				denomination.setCURR_CODE(obj.optString(CURR_CODE));
				denomination.setUNIT(obj.optString(UNIT));
				denomination.setVALUE(obj.optDouble(VALUE));
				denomination.setCreatedOn("");
				denomination.setUserName("");
				denominationList.add(denomination);
			}
		}
		return denominationList;
	}

}
