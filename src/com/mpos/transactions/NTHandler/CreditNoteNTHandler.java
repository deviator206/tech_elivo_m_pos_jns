package com.mpos.transactions.NTHandler;

import java.util.List;
import java.util.Random;

import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import android.os.Bundle;
import com.mpos.framework.common.Constants;
import com.mpos.framework.common.Logger;
import com.mpos.framework.common.OperationalResult;
import com.mpos.framework.handler.NTHandler;
import com.mpos.framework.inf.HandlerInf;

public class CreditNoteNTHandler  extends NTHandler {

	protected Bundle bundle = new Bundle();

	public static final String TAG = Constants.APP_TAG
			+ CreditNoteNTHandler.class.getSimpleName();
	
	public CreditNoteNTHandler(HandlerInf handlerInf,
			List<NameValuePair> params, String url, int resultCode) {
		super(handlerInf, params, url, resultCode);
		setParser();
	}

	@Override
	protected void setParser() {
		mNTParser = this;
		
	}

	@Override
	protected void getParsedData(String response) {
		String responseData = response.toString();
		Logger.d(TAG, "currency note response we get:"+responseData);
		JSONObject creditNoteResponse=new JSONObject();
		
		try {
			creditNoteResponse.put("serverResponse", responseData);
			
			
			
			JSONObject credDetails=new JSONObject(responseData);
			
			/*JSONObject credDetails=new JSONObject();
			
			credDetails.put("amountEncashed", 10000);*/
			
			creditNoteResponse.put("creditNoteDetails", credDetails);
			
			
			bundle.putBoolean("creditNoteResponse",true);
			
		} catch (JSONException e) {
			e.printStackTrace();
			//Logger.w(TAG, e.toString());
			//Log.d(TAG, "## pulsar ## GRPNTPOS_MSNT_HANDLER");
			bundle.putBoolean("creditNoteResponse",false);
			
		}
		bundle.putString("serverResponse", creditNoteResponse.toString());
		mOperationalResult.setResult(bundle);
	}

}
