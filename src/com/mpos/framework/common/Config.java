package com.mpos.framework.common;

import java.util.ArrayList;

public class Config {
	/** Application URLs **/

	public static final String TAG = Constants.APP_TAG
			+ Config.class.getSimpleName();

	public static int ORINTATION = 0;
	public static int BILL_PANEL_POSITION = 1;
	public static int SESSION_TIMEOUT = 2;

	public static String BRANCH_CODE = "1001";
	public static String COMPANY_CODE = "4001";
	public static String COMPANY_NAME = "Compulynx";
	public static String TAB_NO = "01";
	public static String TABLE_NO = "T001";
	public static String TXN_MODE = "POS";
	public static Integer NEAREST_ROUNDING_FO = 0;
	public static Integer DECIMAL_ROUNDING_FO = 2;
	public static String MAX_Z_NO = "0";
	public static String RUN_DTE = "";
	public static String COMPANY_TELE = "";
	public static String COMPANY_ADDRESS = "";
	public static String PRINT_URL ="dummy";
	public static String PRINTER_METHOD="dummy";
	public static String SOAP_ACTION ="dummy";
	
	public static String WS_PORT ="not_set";
	public static String WS_PORTTYPE ="not_set";
	public static String WS_PRINTERTYPE ="not_set";
	public static String WS_PRINTERMODEL ="not_set";
	public static ArrayList<String> WS_FLXIBLE = new ArrayList<String>();
	public static ArrayList<String> WS_FLXIBLE_LABEL = new ArrayList<String>();

	public static final String POST_SUCCESS = "200";
	public static final String POST_ERROR = "500";

	public static final String HTTP = "http://";
	public static String DNS_NAME = "";

	/** Generic URL's **/
	public static String GET_PRDCT_MST = "prdcts";
	public static String GET_GRP_MST = "grps";
	public static String GET_DPT_MST = "dpts";
	public static String GET_ANALYS_MST = "anlys";
	public static String GET_BINLOC_MST = "binloc";
	public static String GET_CURRENCY_MST = "currency";
	public static String GET_DISCOUNT_MST = "dscnts";
	public static String GET_DENOMINATION_MST = "dnmtn";
	public static String GET_INSTRUCTIONS_MST = "instrctn";
	public static String GET_PRDCT_INSTRUCTIONS_MST = "prdctinstrctn";
	public static String GET_LINK_MST = "link";
	public static String GET_PRICE_MST = "prcs";
	public static String GET_PRDCT_RECEIPE_MST = "prdctrecipe";
	public static String GET_SALESMAN_MST = "slmn";
	public static String GET_UOM_MST = "uom";
	public static String GET_UOMSLAB_MST = "uomslab";
	public static String GET_USRRIGHT_MST = "usrrghts";
	public static String GET_USR_MST = "usr";
	public static String GET_VAT_MST = "vat";
	public static String GET_SCANCODES_MST = "scancodes";
	public static String GET_CRDTCARD_MST = "crdtcard";
	public static String GET_USER_ASSGND_RGTS = "usrrghts";
	public static String GET_USER_MST = "usr";
	public static String GET_PRDCT_IMG_MST = "getimgs/";
	public static String GET_FLX_POS = "flxpos";
	public static String GET_CREDIT_NOTE_DETAILS = "balance/"; //creditnote
	public static String PrdctInstrctn = "prdctinstrctn";

	public static String APP_CONFIG = "config/";
	public static String TXN_UPLOAD = "txns";
	public static final String PTY_FLT_UPLOAD = "ptyflt";
	public static final String ZREPORT_UPLOAD = "tillzhstry";

}
