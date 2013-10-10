package com.mpos.framework.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.DecimalFormat;

import android.R;

public class Constants {

	public static final String TAG = Constants.APP_TAG
			+ Constants.class.getSimpleName();
	public static final int LEFT = 0;
	public static final int RIGHT = 1;
	public static final int LANDSCAPE = 2;
	public static final int PORTRAITFT = 3;

	/** Common Codes **/
	public static final String APP_TAG = "MPOS:";
	public static final String BUNDLE_KEY = "BUNDLE";
	public static final String KEY = "KEY";
	public static final String VALUE = "VALUE";
	public static final String BUNDLE_NEED_ACTIVITY_FINISH = "FINISH";
	public static final String RESULTCODEBEAN = "RESULTCODEBEAN";

	/** Transaction Types **/
	public static final String HELD_TXN = "HE";
	public static final String BILLING_TXN = "BI";
	public static final String ITEM_VOIDED = "Y";
	public static final String ITEM_NOT_VOIDED = "N";
	public static final String BILL_PRINTED = "Y";
	public static final String BILL_NOT_PRINTED = "N";

	/** App Constants **/
	public static final String staticNo = "9";
	public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String DATE_FORMAT_mm = "yyyy-MM-dd HH:mm";
	public static final String DATE_FORMAT_dte = "yyyy-MM-dd";
	public static final String TIME_FORMAT = "HH:mm";

	/** Error Messages **/
	public static final String ALERT = "Alert";
	public static final String INITIAL_OPERATION_PARAM = "OPERATION";
	public static boolean NOT_CONNECTED = false;

	/** DB Operation Codes **/
	public static final int INSERT = 1001;
	public static final int UPDATE = 1002;
	public static final int SELECT = 1003;
	public static final int DELETE = 1004;
	public static final int CLEAR_CACHE = 1005;
	public static final int COUNT = 1006;

	/** Individual DB operation codes **/
	public static final int FETCH_GRP_RECORDS = 2001;
	public static final int FETCH_DPT_RECORDS = 2002;
	public static final int FETCH_ANALYS_RECORDS = 2003;
	public static final int FETCH_BINLOC_RECORDS = 2004;
	public static final int FETCH_PRDCT_RECORDS = 2005;
	public static final int FETCH_USER_DETAILS_MST = 2006;
	public static final int FETCH_USER_ASSIGND_RGHTS_MST = 2007;
	public static final int FETCH_CURRENCIES = 2008;
	public static final int FETCH_UOM_RECORD = 2009;
	public static final int FETCH_UOM_SLAB_RECORD = 2010;
	public static final int FETCH_MODIFIER_RECORD = 2011;
	public static final int FETCH_USER_RIGHT_RECORD = 2012;
	public static final int FETCH_VAT_RECORD = 2013;
	public static final int FETCH_BILL_MST_RECORD = 2014;
	public static final int FETCH_BILL_TXN_RECORD = 2015;
	public static final int FETCH_SINGLE_PRDCT_RECORD = 2016;
	public static final int FETCH_SCANCODES_RECORD = 2017;
	public static final int FETCH_BILL_INSTRCTN_TXN_RECORD = 2018;
	public static final int FETCH_PYMNT_MST_RECORD = 2019;
	public static final int FETCH_MULTY_PYMNT_MST_RECORD = 2020;
	public static final int FETCH_SLMN_MST_RECORD = 2021;
	public static final int FETCH_INGREDIENT_RECORD = 2022;
	public static final int FETCH_CNSMPTN_RECORD = 2023;
	public static final int FETCH_FLX_POS_RECORD = 2024;
	public static final int FETCH_FLX_POS_TXN = 2025;
	public static final int FETCH_SINGLE_CNSMPTN_RECORD = 2026;
	public static final int FETCH_SINGLE_BILL_INSTRCTN_TXN_RECORD = 2027;
	public static final int FETCH_DNMTN_MST = 2028;
	public static final int FETCH_PTY_FLT_MST_RECORD = 2029;
	public static final int FETCH_MULTI_PTY_FLT_MST_RECORD = 2030;
	public static final int FETCH_SALSMAN_TXN_RECORD = 2031;
	public static final int FETCH_HELD_TXN_RECORD = 2032;
	public static final int FETCH_MULTI_CHNG_MST_RECORD = 2033;
	public static final int FETCH_OFFLINE_TXN = 2034;
	public static final int FETCH_MULTI_CHNG_MST = 2035;
	public static final int FETCH_SINGLE_CMP_PLCY_RECORD = 2036;
	public static final int FETCH_BILL_MST_TXNNO_RECORD = 2037;
	public static final int FETCH_PRDCT_INSTRC_CODE_RECORD = 2038;
	public static final int FETCH_MAX_ID_BILL_TXN_RECORD = 2039;
	public static final int FETCH_FLOAT_CURRENCT_RECORDS = 2040;
	
	public static final int INSERT_GRP_RECORDS = 3001;
	public static final int INSERT_DPT_RECORDS = 3002;
	public static final int INSERT_ANALYS_RECORDS = 3003;
	public static final int INSERT_BINLOC_RECORDS = 3004;
	public static final int INSERT_PRDCT_RECORDS = 3005;
	public static final int INSERT_USER_MST_RECORDS = 3006;
	public static final int INSERT_USER_ASSGND_RGHTS = 3007;
	public static final int INSERT_CURRENCY = 3008;
	public static final int INSERT_MULTI_PMT_RECORDS = 3009;
	public static final int INSERT_PAYMT_RECORD = 3010;
	public static final int INSERT_UOM_RECORD = 3011;
	public static final int INSERT_UOM_SLAB_RECORD = 3012;
	public static final int INSERT_MODIFIER_RECORDS = 3013;
	public static final int INSERT_VAT_RECORDS = 3014;
	public static final int INSERT_BILL_MST_RECORDS = 3015;
	public static final int INSERT_BILL_TXN_RECORDS = 3016;
	public static final int INSERT_SCANCODE_RECORDS = 3017;
	public static final int INSERT_BILL_INSTRCTN_TXN_RECORDS = 3018;
	public static final int INSERT_SLMN_MST = 3019;
	public static final int INSERT_INGREDIENT_RECORD = 3020;
	public static final int INSERT_CNSMPTN_RECORD = 3021;
	public static final int INSERT_FLX_POS = 3022;
	public static final int INSERT_FLX_POS_TXN = 3023;
	public static final int INSERT_SLMN_TXN = 3024;
	public static final int INSERT_PTY_FLT_MST_RECORD = 3025;
	public static final int INSERT_MULTI_PTY_FLT_MST_RECORD = 3026;
	public static final int INSERT_DNMTN_MST = 3027;
	public static final int INSERT_HELD_TXN = 3028;
	public static final int INSERT_MULTI_CHNG_RECORDS = 3029;
	public static final int INSERT_TEMP_TXN_RECORD = 3030;
	public static final int INSERT_CONFIG_CMP_PLY_RECORDS = 3031;
	public static final int INSERT_PRDCT_INSTRC_RECORDS = 3032;
	public static final int INSERT_FLOAT_PMT_RECORDS = 3033; 
	
	/** Individual NT operation codes **/
	public static final int GET_APP_CONFIG = 4000;
	public static final int GET_GRP_RECORDS = 4001;
	public static final int GET_DPT_RECORDS = 4002;
	public static final int GET_ANALYS_RECORDS = 4003;
	public static final int GET_BINLOC_RECORDS = 4004;
	public static final int GET_PRDCT_RECORDS = 4005;
	public static final int GET_USER_MST = 4006;
	public static final int GET_USR_ASSGND_RGHTS = 4007;
	public static final int GET_CURRENCY_RECORDS = 4008;
	public static final int GET_SALESMAN_RECORDS = 4009;
	public static final int GET_UOM_RECORDS = 4010;
	public static final int GET_VAT_RECORDS = 4011;
	public static final int GET_SCAN_CODE_RECORDS = 4012;
	public static final int GET_CRDCT_CARD_RECORDS = 4013;
	public static final int GET_BILL_MST_RECORDS = 4014;
	public static final int GET_UOM_RECORD = 4015;
	public static final int GET_UOM_SLAB_RECORD = 4016;
	public static final int GET_MODIFIER_RECORD = 4017;
	public static final int GET_BILL_TXN_RECORDS = 4018;
	public static final int GET_BILL_INSTRCTN_TXN_TXN_RECORDS = 4019;
	public static final int GET_INGREDIENT_RECORD = 4020;
	public static final int GET_CNSMPTN_RECORD = 4021;
	public static final int GET_FLX_POS = 4022;
	public static final int GET_FLX_POS_TXN = 4023;
	public static final int GET_PRDCT_IMGES = 4024;
	public static final int UPDATE_PRDCT_IMG_MST_RECORDS = 4025;
	public static final int GET_DNMTN_MST = 4026;
	public static final int GET_BILL_MST_COUNT = 4027;
	public static final int GET_PRDCT_INSTRC_RECORD = 4028;
	public static final int GET_CREDIT_NOTE_RESPONSE = 4029;//creditnote

	public static final int MASTER_UPLOAD_SUCCESS = 5000;
	public static final int MASTER_UPLOAD_ERROR = 5001;

	public static final int UPDATE_BILL_TXN_RECORDS = 6001;
	public static final int UPDATE_CNSMPTN_TXN_RECORDS = 6002;
	public static final int UPDATE_BILL_INSTRCTN_TXN_RECORDS = 6003;
	public static final int UPDATE_BILL_MST_TXN_TYPE_RECORD = 6004;
	public static final int UPDATE_BILL_MST_RECORD = 6005;
	public static final int UPDATE_BILL_TXN_TYPE_RECORD = 6006;

	public static final int DELETE_BILL_MST_RECORDS = 7001;
	public static final int DELETE_BILL_TXN_RECORDS = 7002;
	public static final int DELETE_BILL_INSTRCTN_TXN_RECORDS = 7003;
	public static final int DELETE_CONSUMPTION_TXN_RECORDS = 7004;
	public static final int DELETE_BILL_MST_HELD_RECORD = 7005;

	public static final int DELETE_GRP_MST_TABLE = 5000;
	public static final int DELETE_PRDT_DPT_MST_TABLE = 5001;
	public static final int DELETE_BINLOC_MST_TABLE = 5002;
	public static final int DELETE_ANALYS_MST_TABLE = 5003;
	public static final int DELETE_PRDT_REC_MST_TABLE = 5004;
	public static final int DELETE_UOM_SLAB_MST_TABLE = 5005;
	public static final int DELETE_UOM_MST_TABLE = 5006;
	public static final int DELETE_CURRENCY_MST_TABLE = 5007;
	public static final int DELETE_USER_ASSGND_RGHTS_MST_TABLE = 5008;
	public static final int DELETE_VAT_DATA_MST_TABLE = 5009;
	public static final int DELETE_SCAN_CODE_MST_TABLE = 5010;
	public static final int DELETE_MASTER = 5011;
	public static final int DELETE_USER_MST_TABLE = 5012;
	public static final int DELETE_SLMN_MST_TABLE = 5013;
	public static final int DELETE_RECPI_DTL_TABLE = 5014;
	public static final int DELETE_FLX_POS_MST = 5015;
	public static final int DELETE_DNMTN_MST = 5016;
	public static final int DELETE_INSTRUC_MST_TABLE = 5017;
	public static final int DELETE_PRDCT_INSTRCT_MST_TABLE = 5018;
	public static final int DELETE_TEMP_TXN_RECORD = 5019;

	public static final int COPY_GRP_MST_TABLE = 6000;
	public static final int COPY_PRDT_DPT_MST_TABLE = 6001;
	public static final int COPY_BINLOC_MST_TABLE = 6002;
	public static final int COPY_ANALYS_MST_TABLE = 6003;
	public static final int COPY_PRDCT_MST_TABLE = 6004;
	public static final int COPY_UOM_SLAB_MST_TABLE = 6005;
	public static final int COPY_UOM_MST_TABLE = 6006;
	public static final int COPY_CURRENCY_MST_TABLE = 6007;
	public static final int COPY_USER_ASSGND_RGHTS_MST_TABLE = 6008;
	public static final int COPY_VAT_DATA_MST_TABLE = 6009;
	public static final int COPY_SCAN_CODE_MST_TABLE = 6010;
	public static final int COPY_MASTER = 6011;
	public static final int COPY_SLMN_TABLE = 6012;
	public static final int COPY_PRDT_RECIPI_DTL_TABLE = 6013;
	public static final int COPY_FLX_POS_TABLE = 6014;
	public static final int COPY_DNMTN_MST_TABLE = 6015;
	public static final int COPY_USR_MST_TABLE = 6016;
	public static final int COPY_INSTRCTION_MST_TABLE = 6017;
	public static final int COPY_PRDCT_INSTRUC_MST_TABLE = 6018;

	public static final int UPDATE_MASTER_COMPLETE = 10000;
	
	


	/*
	 * To get formatted date with exception handling
	 */
	public static String getFormattedDate(String date) {
		String formattedDte = "";
		String orgDate = date;	
		
		SimpleDateFormat dateFormat;
		try {// Normal
			dateFormat = new SimpleDateFormat(Constants.DATE_FORMAT);
			formattedDte = dateFormat.format(dateFormat.parse(orgDate));
		} catch (ParseException e) {
			// e.printStackTrace();
			// 1st Catch
			try {
				orgDate = orgDate.concat(" :00");
				//dateFormat = new SimpleDateFormat(Constants.DATE_FORMAT_mm);
				dateFormat = new SimpleDateFormat(Constants.DATE_FORMAT);
				formattedDte = dateFormat.format(dateFormat.parse(orgDate));
			} catch (ParseException e1) {
				// e1.printStackTrace();
				// 2nd Catch
				try {
					orgDate = date;
					orgDate = orgDate.concat(" 00:00:00");
					//dateFormat = new SimpleDateFormat(Constants.DATE_FORMAT_dte);
					dateFormat = new SimpleDateFormat(Constants.DATE_FORMAT);
					formattedDte = dateFormat.format(dateFormat.parse(orgDate));
				} catch (ParseException e2) {
					// e2.printStackTrace();
					// 3rd and Final catch,show current time
					dateFormat = new SimpleDateFormat(Constants.DATE_FORMAT);
					formattedDte = dateFormat.format(new Date());
				}
			}
		}

		return formattedDte;
	}


	public static DecimalFormat getDecimalFormat() {
		String newDF = "0.";
		for (int i = 0; i < Config.DECIMAL_ROUNDING_FO; i++) {
			newDF += "0";
		}
		Logger.v(TAG, "df: "+newDF.toString());
		Logger.v(TAG, "Config.DECIMAL_ROUNDING_FO"+Config.DECIMAL_ROUNDING_FO);
		return new DecimalFormat(newDF);
	}

}
