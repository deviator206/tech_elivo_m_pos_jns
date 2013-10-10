package com.mpos.framework.common;

public class DBConstants {

	// FK: Foreign Key
	// PK: Primary Key

	public static final String DB_TIME_STAMP = "time_stamp";

	// public static final String DB_PATH = "/sdcard/mpos/";
	// public static final String DB_PATH = "/data/data/com.mpos/";
	//public static final String DATABASE_NAME = "/sdcard/mpos/com.js.mpos.db";
	public static final String DATABASE_NAME = "com.js.mpos.db";
	public static final String ZREPORT_FILE = "/data/data/com.mpos/zreport.csv";
	public static final String KITCHEN_FILE = "/data/data/com.mpos/kitchen.csv";
	public static final String BILL_FILE = "/data/data/com.mpos/bill.csv";

	public static final int DATABASE_VERSION = 1;

	// Update Master table declaration
	public static final String dbo_FLOAT_PYMNTS_MST_TABLE = "FLOAT_PYMNTS_MST";
	public static final String dbo_UPDATE_TILL_PRDCT_MST_TABLE = "UPDATE_TILL_PRDCT_MST";
	public static final String dbo_UPDATE_PRDCT_GRP_MST_TABLE = "UPDATE_PRDCT_GRP_MST";
	public static final String dbo_UPDATE_DPT_MST_TABLE = "UPDATE_DPT_MST";
	public static final String dbo_UPDATE_ANALYS_MST_TABLE = "UPDATE_ANALYS_MST";
	public static final String dbo_UPDATE_BIN_LOC_MST_TABLE = "UPDATE_BIN_LOC_MST";
	public static final String dbo_UPDATE_USER_MST_TABLE = "UPDATE_USER_MST";
	public static final String dbo_UPDATE_USR_ASSGND_RGHTS_TABLE = "UPDATE_USER_ASSGND_RGHTS";
	public static final String dbo_UPDATE_Currency_Mst_TABLE = "UPDATE_Currency_Mst";
	public static final String dbo_UPDATE_UOM_SLAB_MST_TABLE = "UPDATE_UOM_SLAB_MST";
	public static final String dbo_UPDATE_UOM_TABLE = "UPDATE_UOM";
	public static final String dbo_UPDATE_FLX_POS_MST_TABLE = "UPDATE_FLX_POS_MST";
	public static final String dbo_UPDATE_VAT_MST_TABLE = "UPDATE_dbo_UPDATE_VAT_MST";
	public static final String dbo_UPDATE_SCAN_CODS_MST_TABLE = "UPDATE_SCAN_CODS_MST";
	public static final String dbo_UPDATE_SLMN_MST_TABLE = "UPDATE_SLMN_MST";
	public static final String dbo_UPDATE_DNMTN_MST_TABLE = "UPDATE_DNMTN_MST";
	public static final String dbo_UPDATE_PRDCT_RECEIPE_MST_TABLE = "UPDATE_PRDCT_RECIPE_DTL";
	public static final String dbo_UPDATE_INSTRCTN_MST_TABLE = "UPDATE_INSTRCTN_MST";
	public static final String dbo_UPDATE_PRDCT_INSTRCTN_MST_TABLE = "UPDATE_PRDCT_INSTRCTN_MST";

	// Master table declaration
	public static final String dbo_TILL_PRDCT_MST_TABLE = "TILL_PRDCT_MST";
	public static final String dbo_PRDCT_GRP_MST_TABLE = "PRDCT_GRP_MST";
	public static final String dbo_DPT_MST_TABLE = "DPT_MST";
	public static final String dbo_ANALYS_MST_TABLE = "ANALYS_MST";
	public static final String dbo_BIN_LOC_MST_TABLE = "BIN_LOC_MST";
	public static final String dbo_USER_MST_TABLE = "USER_MST";
	public static final String dbo_USR_ASSGND_RGHTS_TABLE = "USER_ASSGND_RGHTS";
	public static final String dbo_Currency_Mst_TABLE = "Currency_Mst";
	public static final String dbo_UOM_SLAB_MST_TABLE = "UOM_SLAB_MST";
	public static final String dbo_UOM_TABLE = "UOM";
	public static final String dbo_FLX_POS_MST_TABLE = "FLX_POS_MST";
	public static final String dbo_VAT_MST_TABLE = "dbo_VAT_MST";
	public static final String dbo_SCAN_CODS_MST_TABLE = "SCAN_CODS_MST";
	public static final String dbo_SLMN_MST_TABLE = "SLMN_MST";
	public static final String dbo_DNMTN_MST_TABLE = "DNMTN_MST";
	public static final String dbo_PRDCT_RECEIPE_MST_TABLE = "PRDCT_RECIPE_DTL";

	public static final String dbo_MULTI_PYMNTS_MST_TABLE = "MULTI_PYMNTS_MST";
	public static final String dbo_PYMNTS_MST_TABLE = "PYMNTS_MST";
	public static final String dbo_INSTRCTN_MST_TABLE = "dbo_INSTRCTN_MST";
	public static final String dbo_BILL_MST_TABLE = "BILL_MST";
	public static final String dbo_PTY_FLT_MST_TABLE = "PTY_FLT_MST";
	public static final String dbo_MULI_PTY_FLT_MST_TABLE = "MULTI_PTY_FLT_MST";
	public static final String dbo_MULTI_CHNG_MST_TABLE = "MULTI_CHNG_MST";
	public static final String dbo_PRDCT_INSTRCTN_MST_TABLE = "dbo_PRDCT_INSTRCTN_MST";

	public static final String dbo_CONFIG_MST_TABLE = "CONFIG";

	// Transaction Tables
	public static final String dbo_HELD_TXN = "dbo_HELD_TXN";
	public static final String dbo_BILL_TXN_TABLE = "BILL_TXN";
	public static final String dbo_CNSMPTN_TXN = "CNSMPTN_TXN";
	public static final String dbo_BILL_INSTRCTN_TXN_TABLE = "BILL_INSTRCTN_TXN";
	public static final String dbo_SLMN_TXN_TABLE = "SLMN_TXN";
	public static final String dbo_FLX_POS_TXN_TABLE = "FLX_POS_TXN";
	public final static String dbo_TEMP_TRANSACTION_TXN_TABLE = "TEMP_TRANSACTION_TXN";
	public final static String dbo_TEMP_FLT_PTY_PKUP_TXN_TABLE = "TEMP_FLT_PTY_PKUP_TXN";

	// TILL_PRDCT_MST table column names
	public final static String PRDCT_VAT_CODE = "PRDCT_VAT_CODE";
	public final static String PRDCT_SUB_CLASS = "PRDCT_SUB_CLASS";
	public final static String PRDCT_UNIT = "PRDCT_UNIT";
	public final static String PRDCT_QTY = "PRDCT_QTY";
	public final static String PRDCT_SELL_PRCE = "PRDCT_SELL_PRCE";
	public final static String PRDCT_SHRT_DSCRPTN = "PRDCT_SHRT_DSCRPTN";
	public final static String PRDCT_SPPLR = "PRDCT_SPPLR";
	public final static String PRDCT_DSCRPTN = "PRDCT_DSCRPTN";
	public final static String PRDCT_FIX_QTY = "PRDCT_FIX_QTY";
	public final static String PRDCT_FIXED_PRCE = "PRDCT_FIXED_PRCE";
	public final static String PRDCT_GRP_CODE = "PRDCT_GRP_CODE";
	public final static String PRDCT_IMG = "PRDCT_IMG";
	public final static String PRDCT_LNG_DSCRPTN = "PRDCT_LNG_DSCRPTN";
	public final static String PRDCT_MIN_QTY = "PRDCT_MIN_QTY";
	public final static String PRDCT_PCKNG = "PRDCT_PCKNG";
	public final static String DISP_ORDER_NO = "DISP_ORDER_NO";
	public final static String PRDCT_ANLYS_CODE = "PRDCT_ANLYS_CODE";
	public final static String PRDCT_BIN_LOC_CODE = "PRDCT_BIN_LOC_CODE";
	public final static String PRDCT_CLR = "PRDCT_CLR";
	public final static String PRDCT_CODE = "PRDCT_CODE";
	public final static String PRDCT_COST_PRCE = "PRDCT_COST_PRCE";
	public final static String PRDCT_CP_VLTN = "PRDCT_CP_VLTN";
	public final static String PRDCT_DPT_CODE = "PRDCT_DPT_CODE";

	public final static String CREATE_TILL_PRDCT_MST_TB = "CREATE TABLE IF NOT EXISTS "
			+ dbo_TILL_PRDCT_MST_TABLE
			+ " ( "
			+ PRDCT_CODE
			+ " text collate nocase PRIMARY KEY NOT NULL,"
			+ PRDCT_DSCRPTN
			+ " text NOT NULL,"
			+ PRDCT_LNG_DSCRPTN
			+ " text NOT NULL,"
			+ PRDCT_SHRT_DSCRPTN
			+ " text NOT NULL,"
			+ PRDCT_UNIT
			+ " text NOT NULL, "
			+ PRDCT_PCKNG
			+ " double NOT NULL,"
			+ PRDCT_VAT_CODE
			+ " text NOT NULL,"
			+ PRDCT_COST_PRCE
			+ " double NOT NULL,"
			+ PRDCT_SELL_PRCE
			+ " double NOT NULL,"
			+ PRDCT_MIN_QTY
			+ " double NOT NULL,"
			+ PRDCT_FIX_QTY
			+ " text NOT NULL,"
			+ PRDCT_QTY
			+ " double NOT NULL,"
			+ PRDCT_SPPLR
			+ " text,"
			+ PRDCT_BIN_LOC_CODE
			+ " text NOT NULL,"
			+ PRDCT_DPT_CODE
			+ " text NOT NULL,"
			+ PRDCT_CP_VLTN
			+ " text,"
			+ PRDCT_SUB_CLASS
			+ " text,"
			+ PRDCT_GRP_CODE
			+ " text,"
			+ PRDCT_ANLYS_CODE
			+ " text,"
			+ PRDCT_CLR
			+ " text,"
			+ PRDCT_IMG
			+ " text,"
			+ DISP_ORDER_NO
			+ " double,"
			+ PRDCT_FIXED_PRCE
			+ " integer)";

	public final static String CREATE_UPDATE_TILL_PRDCT_MST_TB = "CREATE TABLE IF NOT EXISTS "
			+ dbo_UPDATE_TILL_PRDCT_MST_TABLE
			+ " ( "
			+ PRDCT_CODE
			+ " text collate nocase PRIMARY KEY NOT NULL,"
			+ PRDCT_DSCRPTN
			+ " text NOT NULL,"
			+ PRDCT_LNG_DSCRPTN
			+ " text collate nocase NOT NULL,"
			+ PRDCT_SHRT_DSCRPTN
			+ " text NOT NULL,"
			+ PRDCT_UNIT
			+ " text NOT NULL, "
			+ PRDCT_PCKNG
			+ " double NOT NULL,"
			+ PRDCT_VAT_CODE
			+ " text NOT NULL,"
			+ PRDCT_COST_PRCE
			+ " double NOT NULL,"
			+ PRDCT_SELL_PRCE
			+ " double NOT NULL,"
			+ PRDCT_MIN_QTY
			+ " double NOT NULL,"
			+ PRDCT_FIX_QTY
			+ " text NOT NULL,"
			+ PRDCT_QTY
			+ " double NOT NULL,"
			+ PRDCT_SPPLR
			+ " text,"
			+ PRDCT_BIN_LOC_CODE
			+ " text NOT NULL,"
			+ PRDCT_DPT_CODE
			+ " text NOT NULL,"
			+ PRDCT_CP_VLTN
			+ " text,"
			+ PRDCT_SUB_CLASS
			+ " text,"
			+ PRDCT_GRP_CODE
			+ " text,"
			+ PRDCT_ANLYS_CODE
			+ " text,"
			+ PRDCT_CLR
			+ " text,"
			+ PRDCT_IMG
			+ " text,"
			+ DISP_ORDER_NO
			+ " double," + PRDCT_FIXED_PRCE + " integer)";

	// PRDCT_GRP_MST_TABLE table column names
	public final static String GRP_CODE = "GRP_CODE";
	public final static String GRP_NAME = "GRP_NAME";

	public final static String CREATE_PRDCT_GRP_MST_TB = "CREATE TABLE IF NOT EXISTS "
			+ dbo_PRDCT_GRP_MST_TABLE
			+ " ( "
			+ GRP_CODE
			+ " text,"
			+ GRP_NAME
			+ " text)";

	public final static String CREATE_UPDATE_PRDCT_GRP_MST_TB = "CREATE TABLE IF NOT EXISTS "
			+ dbo_UPDATE_PRDCT_GRP_MST_TABLE
			+ " ( "
			+ GRP_CODE
			+ " text,"
			+ GRP_NAME + " text)";

	// DPT_MST_TABLE table column names
	public final static String DPT_FK_GRP_CODE = "FK_GRP_CODE";
	public final static String DPT_CODE = "DPT_CODE";
	public final static String DPT_NAME = "DPT_NAME";

	public final static String CREATE_DPT_MST_TB = "CREATE TABLE IF NOT EXISTS "
			+ dbo_DPT_MST_TABLE
			+ " ( "
			+ DPT_CODE
			+ " text,"
			+ DPT_NAME
			+ " text, "
			+ DPT_FK_GRP_CODE
			+ " text,"
			+ " FOREIGN KEY("
			+ DPT_FK_GRP_CODE
			+ ") REFERENCES "
			+ dbo_PRDCT_GRP_MST_TABLE
			+ "(GRP_CODE)" + ")";

	public final static String CREATE_UPDATE_DPT_MST_TB = "CREATE TABLE IF NOT EXISTS "
			+ dbo_UPDATE_DPT_MST_TABLE
			+ " ( "
			+ DPT_CODE
			+ " text,"
			+ DPT_NAME
			+ " text, "
			+ DPT_FK_GRP_CODE
			+ " text,"
			+ " FOREIGN KEY("
			+ DPT_FK_GRP_CODE
			+ ") REFERENCES "
			+ dbo_PRDCT_GRP_MST_TABLE
			+ "(GRP_CODE)" + ")";

	// ANALYS_MST_TABLE table column names
	public final static String ANALYS_FK_DPT_CODE = "FK_DPT_CODE";
	public final static String ANALYS_CODE = "ANALYS_CODE";
	public final static String ANALYA_NAME = "ANALYA_NAME";

	public final static String CREATE_ANALYS_MST_TB = "CREATE TABLE IF NOT EXISTS "
			+ dbo_ANALYS_MST_TABLE
			+ " ( "
			+ ANALYS_CODE
			+ " text,"
			+ ANALYA_NAME
			+ " text, "
			+ ANALYS_FK_DPT_CODE
			+ " text,"
			+ " FOREIGN KEY("
			+ ANALYS_FK_DPT_CODE
			+ ") REFERENCES "
			+ dbo_DPT_MST_TABLE + "(DPT_CODE)" + ")";

	public final static String CREATE_UPDATE_ANALYS_MST_TB = "CREATE TABLE IF NOT EXISTS "
			+ dbo_UPDATE_ANALYS_MST_TABLE
			+ " ( "
			+ ANALYS_CODE
			+ " text,"
			+ ANALYA_NAME
			+ " text, "
			+ ANALYS_FK_DPT_CODE
			+ " text,"
			+ " FOREIGN KEY("
			+ ANALYS_FK_DPT_CODE
			+ ") REFERENCES "
			+ dbo_DPT_MST_TABLE + "(DPT_CODE)" + ")";

	// BIN_LOC_MST_TABLE table column names
	public final static String BINLOC_FK_ANALYS_CODE = "FK_ANALYS_CODE";
	public final static String BINLOC_CODE = "BINLOC_CODE";
	public final static String BINLOC_NAME = "BINLOC_NAME";

	public final static String CREATE_BINLOC_MST_TB = "CREATE TABLE IF NOT EXISTS "
			+ dbo_BIN_LOC_MST_TABLE
			+ " ( "
			+ BINLOC_CODE
			+ " text,"
			+ BINLOC_NAME
			+ " text, "
			+ BINLOC_FK_ANALYS_CODE
			+ " text,"
			+ " FOREIGN KEY("
			+ BINLOC_FK_ANALYS_CODE
			+ ") REFERENCES "
			+ dbo_ANALYS_MST_TABLE + "(ANALYS_CODE)" + ")";

	public final static String CREATE_UPDATE_BINLOC_MST_TB = "CREATE TABLE IF NOT EXISTS "
			+ dbo_UPDATE_BIN_LOC_MST_TABLE
			+ " ( "
			+ BINLOC_CODE
			+ " text,"
			+ BINLOC_NAME
			+ " text, "
			+ BINLOC_FK_ANALYS_CODE
			+ " text,"
			+ " FOREIGN KEY("
			+ BINLOC_FK_ANALYS_CODE
			+ ") REFERENCES "
			+ dbo_ANALYS_MST_TABLE + "(ANALYS_CODE)" + ")";

	// BILL_MST_TABLE table column names
	public final static String AMNT_PAID = "AMNT_PAID";
	public final static String BILL_AMENED = "BILL_AMENED";
	public final static String BILL_AMND_STATUS = "BILL_AMND_STATUS";
	public final static String BILL_AMNT = "BILL_AMNT";
	public final static String BILL_LOCKED = "BILL_LOCKED";
	public final static String BILL_NO = "BILL_NO";
	public final static String BILL_RUN_DATE = "BILL_RUN_DATE";
	public final static String BILL_SCNCD = "BILL_SCNCD";
	public final static String BILL_SCND = "BILL_SCND";
	public final static String BILL_STATUS = "BILL_STATUS";
	public final static String BILL_SYS_DATE = "BILL_SYS_DATE";
	public final static String BILL_VAT_AMNT = "BILL_VAT_AMNT";
	public final static String BILL_VAT_EXMPT = "BILL_VAT_EXMPT";
	public final static String BRNCH_CODE = "BRNCH_CODE";
	public final static String CARDNO = "CARDNO";
	public final static String CHNG_GVN = "CHNG_GVN";
	public final static String CMPNY_CODE = "CMPNY_CODE";
	public final static String SHIFT_CODE = "SHIFT_CODE";
	public final static String TBLE_CODE = "TBLE_CODE";
	public final static String TILL_NO = "TILL_NO";
	public final static String TXN_TYPE = "TXN_TYPE";
	public final static String USR_NAME = "USR_NAME";

	public final static String CREATE_BILL_MST_TABLE = "CREATE TABLE IF NOT EXISTS "
			+ dbo_BILL_MST_TABLE
			+ " ( "
			+ CMPNY_CODE
			+ " text NOT NULL,"
			+ BRNCH_CODE
			+ " text NOT NULL,"
			+ BILL_SCNCD
			+ " text NOT NULL,"
			+ TXN_TYPE
			+ " text NOT NULL,"
			+ BILL_RUN_DATE
			+ " text NOT NULL, "
			+ TILL_NO
			+ " text NOT NULL,"
			+ BILL_NO
			+ " text NOT NULL,"
			+ BILL_SYS_DATE
			+ " text NOT NULL,"
			+ BILL_VAT_EXMPT
			+ " text NOT NULL,"
			+ BILL_AMNT
			+ " double NOT NULL,"
			+ AMNT_PAID
			+ " double NOT NULL,"
			+ CHNG_GVN
			+ " double NOT NULL,"
			+ BILL_VAT_AMNT
			+ " double NOT NULL,"
			+ USR_NAME
			+ " text NOT NULL,"
			+ BILL_STATUS
			+ " text NOT NULL,"
			+ BILL_SCND
			+ " text NOT NULL,"
			+ BILL_AMENED
			+ " text NOT NULL,"
			+ CARDNO
			+ " text,"
			+ SHIFT_CODE
			+ " text,"
			+ TBLE_CODE
			+ " text,"
			+ BILL_AMND_STATUS
			+ " text,"
			+ BILL_LOCKED
			+ " text,"
			+ PRDCT_FIXED_PRCE
			+ " text, PRIMARY KEY ("
			+ CMPNY_CODE
			+ ","
			+ BRNCH_CODE
			+ ","
			+ BILL_SCNCD
			+ ","
			+ TXN_TYPE
			+ ","
			+ BILL_RUN_DATE + "))";

	// USER_MST table column names
	public final static String USR_SCNCD = "USR_SCNCD";
	public final static String USR_NM = "USR_NM";
	public final static String USR_PWD = "USR_PWD";
	public final static String USR_BIO = "USR_BIO";
	public final static String USR_CRTN_DATE = "USR_CRTN_DATE";
	public final static String USR_FULL_NAME = "USR_FULL_NAME";
	public final static String USR_GRP_ID = "USR_GRP_ID";
	public final static String USR_ID = "USR_ID";
	public final static String USR_MODFD_BY = "USR_MODFD_BY";

	public final static String CREATE_USER_MST_TABLE = "CREATE TABLE IF NOT EXISTS "
			+ dbo_USER_MST_TABLE
			+ " ( "
			+ USR_BIO
			+ " text NOT NULL,"
			+ USR_CRTN_DATE
			+ " text NOT NULL,"
			+ USR_FULL_NAME
			+ " text NOT NULL,"
			+ USR_GRP_ID
			+ " text NOT NULL,"
			+ USR_ID
			+ " text NOT NULL, "
			+ USR_MODFD_BY
			+ " text NOT NULL,"
			+ USR_NM
			+ " text NOT NULL,"
			+ USR_PWD
			+ " text NOT NULL,"
			+ USR_SCNCD
			+ " text NOT NULL" + ")";

	public final static String CREATE_UPDATE_USER_MST_TABLE = "CREATE TABLE IF NOT EXISTS "
			+ dbo_UPDATE_USER_MST_TABLE
			+ " ( "
			+ USR_BIO
			+ " text NOT NULL,"
			+ USR_CRTN_DATE
			+ " text NOT NULL,"
			+ USR_FULL_NAME
			+ " text NOT NULL,"
			+ USR_GRP_ID
			+ " text NOT NULL,"
			+ USR_ID
			+ " text NOT NULL, "
			+ USR_MODFD_BY
			+ " text NOT NULL,"
			+ USR_NM
			+ " text NOT NULL,"
			+ USR_PWD
			+ " text NOT NULL,"
			+ USR_SCNCD
			+ " text NOT NULL" + ")";

	public final static String RFRNCE_ID = "RFRNCE_ID";
	public final static String RGHT_NAME = "RGHT_NAME";
	public final static String FLD_TYPE = "FLD_TYPE";
	public final static String RGHT_VALUE = "RGHT_VALUE";

	public final static String CREATE_USER_ASSGND_RGHTS_TABLE = "CREATE TABLE IF NOT EXISTS "
			+ dbo_USR_ASSGND_RGHTS_TABLE
			+ " ( "
			+ RFRNCE_ID
			+ " text NOT NULL,"
			+ RGHT_NAME
			+ " text NOT NULL,"
			+ FLD_TYPE
			+ " text NOT NULL,"
			+ RGHT_VALUE
			+ " text NOT NULL,"
			+ "PRIMARY KEY (" + RFRNCE_ID + "," + RGHT_NAME + ")" + ")";

	public final static String CREATE_UPDATE_USER_ASSGND_RGHTS_TABLE = "CREATE TABLE IF NOT EXISTS "
			+ dbo_UPDATE_USR_ASSGND_RGHTS_TABLE
			+ " ( "
			+ RFRNCE_ID
			+ " text NOT NULL,"
			+ RGHT_NAME
			+ " text NOT NULL,"
			+ FLD_TYPE
			+ " text NOT NULL,"
			+ RGHT_VALUE
			+ " text NOT NULL,"
			+ "PRIMARY KEY (" + RFRNCE_ID + "," + RGHT_NAME + ")" + ")";

	public final static String PYMNT_NMBR = "PYMNT_NMBR";
	public final static String CURR_ABBR = "CURR_ABBR";
	public final static String PAY_AMNT = "PAY_AMNT";
	public final static String EX_RATE = "EX_RATE";
	public final static String OPERATOR = "OPERATOR";
	public final static String LOC_AMNT = "LOC_AMNT";

	public final static String CREATE_MULTY_PYMNTS_MST_TABLE = "CREATE TABLE IF NOT EXISTS "
			+ dbo_MULTI_PYMNTS_MST_TABLE
			+ " ( "
			+ PYMNT_NMBR
			+ " text NOT NULL,"
			+ CURR_ABBR
			+ " text NOT NULL,"
			+ PAY_AMNT
			+ " double NOT NULL,"
			+ EX_RATE
			+ " double NOT NULL,"
			+ OPERATOR
			+ " text NOT NULL," + LOC_AMNT + " text NOT NULL" + ")";

	public final static String PYMNT_TYPE = "PYMNT_TYPE";

	public final static String CREATE_FLOAT_PYMNTS_MST_TABLE = "CREATE TABLE IF NOT EXISTS "
			+ dbo_FLOAT_PYMNTS_MST_TABLE
			+ " ( "
			+ PYMNT_TYPE
			+ " text NOT NULL,"
			+ CURR_ABBR
			+ " text NOT NULL,"
			+ PAY_AMNT
			+ " double NOT NULL,"
			+ EX_RATE
			+ " double NOT NULL,"
			+ OPERATOR
			+ " text NOT NULL," + LOC_AMNT + " text NOT NULL" + ")";

	public final static String PAY_RUN_DATE = "PAY_RUN_DATE";
	public final static String PAY_SYS_DATE = "PAY_SYS_DATE";
	public final static String PAY_MODE = "PAY_MODE";
	public final static String PRPRTNTE_DISC = "PRPRTNTE_DISC";
	public final static String PAY_MODE_REF_CODE = "PAY_MODE_REF_CODE";
	public final static String PAY_MODE_REF_NO = "PAY_MODE_REF_NO";
	public final static String PAY_DTLS = "PAY_DTLS";
	public final static String USR_NAME_SCND = "USR_NAME_SCND";
	public final static String PNTS_AWARDED = "PNTS_AWARDED";
	public final static String PNTS_REDEEMED = "PNTS_REDEEMED";
	public final static String CCDt = "CCDt";
	public final static String CCAuthCode = "CCAuthCode";

	public final static String CREATE_PYMNTS_MST_TABLE = "CREATE TABLE IF NOT EXISTS "
			+ dbo_PYMNTS_MST_TABLE
			+ " ( "
			+ CMPNY_CODE
			+ " text NOT NULL,"
			+ BRNCH_CODE
			+ " text NOT NULL,"
			+ PYMNT_NMBR
			+ " text NOT NULL,"
			+ PAY_RUN_DATE
			+ " text NOT NULL,"
			+ PAY_SYS_DATE
			+ " text NOT NULL,"
			+ TXN_TYPE
			+ " text NOT NULL,"
			+ PAY_MODE
			+ " text NOT NULL,"
			+ PAY_AMNT
			+ " double NOT NULL,"
			+ PRPRTNTE_DISC
			+ " double NOT NULL,"
			+ PAY_MODE_REF_CODE
			+ " text NOT NULL,"
			+ PAY_MODE_REF_NO
			+ " text NOT NULL,"
			+ PAY_DTLS
			+ " text NOT NULL,"
			+ TILL_NO
			+ " text NOT NULL,"
			+ USR_NAME
			+ " text NOT NULL,"
			+ USR_NAME_SCND
			+ " text NOT NULL,"
			+ CARDNO
			+ " text NOT NULL,"
			+ PNTS_AWARDED
			+ " integer NOT NULL,"
			+ PNTS_REDEEMED
			+ " integer NOT NULL,"
			+ CCDt
			+ " text NOT NULL,"
			+ CCAuthCode + " text NOT NULL" + ")";

	public final static String Abbr_Name = "Abbr_Name";
	public final static String Name = "Name";
	public final static String BaseFlag = "BaseFlag";
	public final static String Operator = "Operator";
	public final static String ExRate = "ExRate";
	public final static String Symbol = "Symbol";
	public final static String POSTN = "POSTN";

	public final static String CREATE_Currency_Mst = "CREATE TABLE IF NOT EXISTS "
			+ dbo_Currency_Mst_TABLE
			+ " ( "
			+ Abbr_Name
			+ " PRIMARY KEY NOT NULL,"
			+ Name
			+ " text NOT NULL,"
			+ BaseFlag
			+ " text NOT NULL,"
			+ Operator
			+ " text NOT NULL,"
			+ ExRate
			+ " double NOT NULL,"
			+ Symbol
			+ " text NOT NULL,"
			+ POSTN
			+ " double NOT NULL" + ")";

	public final static String CREATE_UPDATE_Currency_Mst = "CREATE TABLE IF NOT EXISTS "
			+ dbo_UPDATE_Currency_Mst_TABLE
			+ " ( "
			+ Abbr_Name
			+ " PRIMARY KEY NOT NULL,"
			+ Name
			+ " text NOT NULL,"
			+ BaseFlag
			+ " text NOT NULL,"
			+ Operator
			+ " text NOT NULL,"
			+ ExRate
			+ " double NOT NULL,"
			+ Symbol
			+ " text NOT NULL,"
			+ POSTN
			+ " double NOT NULL" + ")";

	// UOM_SLAB_MST table column names
	public final static String UOM_CODE = "UOM_CODE";

	public final static String CREATE_UOM_SLAB_MST_TABLE = "CREATE TABLE IF NOT EXISTS "
			+ dbo_UOM_SLAB_MST_TABLE
			+ " ( "
			+ PRDCT_CODE
			+ " text NOT NULL,"
			+ UOM_CODE
			+ " text NOT NULL,"
			+ PRDCT_COST_PRCE
			+ " text,"
			+ PRDCT_SELL_PRCE + " text)";

	public final static String CREATE_UPDATE_UOM_SLAB_MST_TABLE = "CREATE TABLE IF NOT EXISTS "
			+ dbo_UPDATE_UOM_SLAB_MST_TABLE
			+ " ( "
			+ PRDCT_CODE
			+ " text NOT NULL,"
			+ UOM_CODE
			+ " text NOT NULL,"
			+ PRDCT_COST_PRCE + " text," + PRDCT_SELL_PRCE + " text)";

	// UOM Table column names
	public final static String CODE = "Code";
	public final static String NAME = "Name";

	public final static String CREATE_UOM_TABLE = "CREATE TABLE IF NOT EXISTS "
			+ dbo_UOM_TABLE + " ( " + CODE + " text PRIMARY KEY NOT NULL,"
			+ NAME + " text NOT NULL," + USR_NAME + " text NOT NULL)";

	public final static String CREATE_UPDATE_UOM_TABLE = "CREATE TABLE IF NOT EXISTS "
			+ dbo_UPDATE_UOM_TABLE
			+ " ( "
			+ CODE
			+ " text PRIMARY KEY NOT NULL,"
			+ NAME
			+ " text NOT NULL,"
			+ USR_NAME + " text NOT NULL)";

	// INSTRCTN Table column names
	public final static String INSTRCTN_CODE = "INSTRCTN_CODE";
	public final static String INSTRCTN_DESC = "INSTRCTN_DESC";

	public final static String CREATE_INSTRCTN_MST_TABLE = "CREATE TABLE IF NOT EXISTS "

			+ dbo_INSTRCTN_MST_TABLE
			+ " ( "
			+ INSTRCTN_CODE
			+ " text NOT NULL," + INSTRCTN_DESC + " text NOT NULL)";
	// Update INSTRCTN Table column names
	public final static String CREATE_UPDATE_INSTRCTN_MST_TABLE = "CREATE TABLE IF NOT EXISTS "

			+ dbo_UPDATE_INSTRCTN_MST_TABLE
			+ " ( "
			+ INSTRCTN_CODE
			+ " text NOT NULL," + INSTRCTN_DESC + " text NOT NULL)";

	// INSTRCTN Table column names
	public final static String PRDCT_INSTRCTN_CODE = "INSTRCTN_CODE";
	public final static String PRDCT_PRD_CODE = "PRDCT_CODE";

	public final static String CREATE_PRDCT_INSTRCTN_MST_TABLE = "CREATE TABLE IF NOT EXISTS "

			+ dbo_PRDCT_INSTRCTN_MST_TABLE
			+ " ( "
			+ PRDCT_PRD_CODE
			+ " text NOT NULL," + PRDCT_INSTRCTN_CODE + " text NOT NULL)";
	// Update INSTRCTN Table column names
	public final static String CREATE_UPDATE_PRDCT_INSTRCTN_MST_TABLE = "CREATE TABLE IF NOT EXISTS "

			+ dbo_UPDATE_PRDCT_INSTRCTN_MST_TABLE
			+ " ( "
			+ PRDCT_PRD_CODE
			+ " text NOT NULL," + PRDCT_INSTRCTN_CODE + " text NOT NULL)";

	// VAT MST Table column name
	public final static String VAT_CODE = "VAT_CODE";
	public final static String VAT_PRCNT = "VAT_PRCNT";

	public final static String CREATE_VAT_MST_TABLE = "CREATE TABLE IF NOT EXISTS "
			+ dbo_VAT_MST_TABLE
			+ " ( "
			+ VAT_CODE
			+ " text PRIMARY KEY NOT NULL,"
			+ VAT_PRCNT
			+ " double NOT NULL,"
			+ USR_NAME + " text NOT NULL)";

	public final static String CREATE_UPDATE_VAT_MST_TABLE = "CREATE TABLE IF NOT EXISTS "
			+ dbo_UPDATE_VAT_MST_TABLE
			+ " ( "
			+ VAT_CODE
			+ " text PRIMARY KEY NOT NULL,"
			+ VAT_PRCNT
			+ " double NOT NULL,"
			+ USR_NAME + " text NOT NULL)";

	// DFLX POS Mst table column names
	public final static String Bill_Heading1 = "Bill_Heading1";
	public final static String Bill_Heading2 = "Bill_Heading2";
	public final static String Bill_Heading3 = "Bill_Heading3";
	public final static String Bill_Heading4 = "Bill_Heading4";
	public final static String Bill_Heading5 = "Bill_Heading5";
	public final static String Bill_Heading6 = "Bill_Heading6";
	public final static String Bill_Heading7 = "Bill_Heading7";
	public final static String Bill_Heading8 = "Bill_Heading8";

	public final static String CREATE_FLX_POS_MST_TABLE = "CREATE TABLE IF NOT EXISTS "
			+ dbo_FLX_POS_MST_TABLE
			+ " ( "
			+ Bill_Heading1
			+ " TEXT,"
			+ Bill_Heading2
			+ " text,"
			+ Bill_Heading3
			+ " text,"
			+ Bill_Heading4
			+ " text,"
			+ Bill_Heading5
			+ " text,"
			+ Bill_Heading6
			+ " text,"
			+ Bill_Heading7
			+ " text,"
			+ Bill_Heading8 + " text" + ")";

	public final static String CREATE_UPDATE_FLX_POS_MST_TABLE = "CREATE TABLE IF NOT EXISTS "
			+ dbo_UPDATE_FLX_POS_MST_TABLE
			+ " ( "
			+ Bill_Heading1
			+ " TEXT,"
			+ Bill_Heading2
			+ " text,"
			+ Bill_Heading3
			+ " text,"
			+ Bill_Heading4
			+ " text,"
			+ Bill_Heading5
			+ " text,"
			+ Bill_Heading6
			+ " text,"
			+ Bill_Heading7
			+ " text,"
			+ Bill_Heading8 + " text" + ")";

	public final static String BILL_HD_VALUE1 = "BILL_HD_VALUE1";
	public final static String BILL_HD_VALUE2 = "BILL_HD_VALUE2";
	public final static String BILL_HD_VALUE3 = "BILL_HD_VALUE3";
	public final static String BILL_HD_VALUE4 = "BILL_HD_VALUE4";
	public final static String BILL_HD_VALUE5 = "BILL_HD_VALUE5";
	public final static String BILL_HD_VALUE6 = "BILL_HD_VALUE6";
	public final static String BILL_HD_VALUE7 = "BILL_HD_VALUE7";
	public final static String BILL_HD_VALUE8 = "BILL_HD_VALUE8";

	public final static String CREATE_FLX_POS_TXN_TABLE = "CREATE TABLE IF NOT EXISTS "
			+ dbo_FLX_POS_TXN_TABLE
			+ " ( "
			+ BILL_SCNCD
			+ " TEXT,"
			+ BILL_HD_VALUE1
			+ " text,"
			+ BILL_HD_VALUE2
			+ " text,"
			+ BILL_HD_VALUE3
			+ " text,"
			+ BILL_HD_VALUE4
			+ " text,"
			+ BILL_HD_VALUE5
			+ " text,"
			+ BILL_HD_VALUE6
			+ " text,"
			+ BILL_HD_VALUE7 + " text," + BILL_HD_VALUE8 + " text" + ")";

	public final static String PRDCT_PRC = "PRDCT_PRC";
	public final static String PRDCT_QNTY = "PRDCT_QNTY";
	public final static String PRDCT_DSCNT = "PRDCT_DSCNT";
	public final static String PRDCT_AMNT = "PRDCT_AMNT";
	public final static String PRDCT_PYMNT_MODE_DSCNT = "PRDCT_PYMNT_MODE_DSCNT";
	public final static String PRDCT_VAT_AMNT = "PRDCT_VAT_AMNT";
	public final static String PRDCT_VOID = "PRDCT_VOID";
	public final static String USR_ANTHNTCTD = "USR_ANTHNTCTD";
	public final static String PRDCT_SCND = "PRDCT_SCND";
	public final static String SLS_MAN_CODE = "SLS_MAN_CODE";
	public final static String BILL_PRNTD = "BILL_PRNTD";
	public final static String PRDCT_VAT_EXMPT = "PRDCT_VAT_EXMPT";
	public final static String AUTO_GRN_DONE = "AUTO_GRN_DONE";
	public final static String EMPTY_DONE = "EMPTY_DONE";
	public final static String ZED_NO = "ZED_NO";
	public final static String SR_NO = "SR_NO";
	public final static String PACK = "PACK";
	public final static String PACKQTY = "PACKQTY";
	public final static String PACKPRCE = "PACKPRCE";
	public final static String LINEDISC = "LINEDISC";
	public final static String HELD_USR = "HELD_USR";
	public final static String SCAN_CODE = "SCAN_CODE";

	public final static String CREATE_HELD_TXN_TABLE = "CREATE TABLE IF NOT EXISTS "
			+ dbo_HELD_TXN
			+ " ( "
			+ CMPNY_CODE
			+ " TEXT NOT NULL,"
			+ BRNCH_CODE
			+ " text NOT NULL,"
			+ BILL_SCNCD
			+ " text NOT NULL,"
			+ TXN_TYPE
			+ " text NOT NULL,"
			+ BILL_RUN_DATE
			+ " text NOT NULL,"
			+ PRDCT_CODE
			+ " text NOT NULL,"
			+ PRDCT_LNG_DSCRPTN
			+ " text NOT NULL,"
			+ PRDCT_PRC
			+ " double NOT NULL,"
			+ PRDCT_QNTY
			+ " double NOT NULL,"
			+ PRDCT_DSCNT
			+ " double NOT NULL,"
			+ PRDCT_AMNT
			+ " double NOT NULL,"
			+ PRDCT_PYMNT_MODE_DSCNT
			+ " text NOT NULL,"
			+ PRDCT_VAT_CODE
			+ " text NOT NULL,"
			+ PRDCT_VAT_AMNT
			+ " double NOT NULL,"
			+ PRDCT_VOID
			+ " text NOT NULL,"
			+ USR_ANTHNTCTD
			+ " text NOT NULL,"
			+ PRDCT_SCND
			+ " text NOT NULL,"
			+ SLS_MAN_CODE
			+ " text NOT NULL,"
			+ BILL_PRNTD
			+ " text NOT NULL,"
			+ PRDCT_VAT_EXMPT
			+ " text NOT NULL,"
			+ PRDCT_COST_PRCE
			+ " double NOT NULL,"
			+ AUTO_GRN_DONE
			+ " text NOT NULL,"
			+ EMPTY_DONE
			+ " text NOT NULL,"
			+ ZED_NO
			+ " integer NOT NULL,"
			+ SR_NO
			+ " integer NOT NULL,"
			+ PACK
			+ " double NOT NULL,"
			+ PACKQTY
			+ " double NOT NULL,"
			+ PACKPRCE
			+ " double NOT NULL,"
			+ LINEDISC
			+ " text NOT NULL,"
			+ HELD_USR
			+ " text NOT NULL,"
			+ SCAN_CODE
			+ " text" + ")";

	// BILL_TXN_TABLE table column names
	public static final String ROW_ID = "ROW_ID";
	public static final String TXN_CMPNY_CODE = "CMPNY_CODE";
	public static final String TXN_BRNCH_CODE = "BRNCH_CODE";
	public static final String TXN_BILL_SCNCD = "BILL_SCNCD";
	public static final String TXN_TXN_TYPE = "TXN_TYPE";
	public static final String TXN_BILL_RUN_DATE = "BILL_RUN_DATE";
	public static final String TXN_PRDCT_CODE = "PRDCT_CODE";
	public static final String TXN_PRDCT_LNG_DSCRPTN = "PRDCT_LNG_DSCRPTN";
	public static final String TXN_PRDCT_MODIFIER_INSTRUC = "PRDCT_MODIFIER_INSTRUC";

	public static final String TXN_PRDCT_VAT_CODE = "PRDCT_VAT_CODE";
	public static final String TXN_PRDCT_COST_PRCE = "PRDCT_COST_PRCE";
	public static final String PROCESSED_ITEM = "PROCESSED_ITEM";
	public final static String CREATE_BILL_TXN_TABLE = "CREATE TABLE IF NOT EXISTS "
			+ dbo_BILL_TXN_TABLE
			+ " ( "
			+ ROW_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT,"
			+ TXN_CMPNY_CODE
			+ " text NOT NULL,"
			+ TXN_BRNCH_CODE
			+ " text NOT NULL,"
			+ TXN_BILL_SCNCD
			+ " text NOT NULL,"
			+ TXN_TXN_TYPE
			+ " text NOT NULL,"
			+ TXN_BILL_RUN_DATE
			+ " text NOT NULL, "
			+ TXN_PRDCT_CODE
			+ " text NOT NULL,"
			+ TXN_PRDCT_LNG_DSCRPTN
			+ " text NOT NULL,"
			+ PRDCT_PRC
			+ " double NOT NULL,"
			+ PRDCT_QNTY
			+ " double NOT NULL,"
			+ PRDCT_DSCNT
			+ " double NOT NULL,"
			+ PRDCT_AMNT
			+ " double NOT NULL,"
			+ PRDCT_PYMNT_MODE_DSCNT
			+ " double NOT NULL,"
			+ TXN_PRDCT_VAT_CODE
			+ " text NOT NULL,"
			+ PRDCT_VAT_AMNT
			+ " double NOT NULL,"
			+ PRDCT_VOID
			+ " text NOT NULL,"
			+ USR_ANTHNTCTD
			+ " text NOT NULL,"
			+ PRDCT_SCND
			+ " text NOT NULL,"
			+ SLS_MAN_CODE
			+ " text,"
			+ BILL_PRNTD
			+ " text NOT NULL,"
			+ PRDCT_VAT_EXMPT
			+ " text NOT NULL,"
			+ TXN_PRDCT_COST_PRCE
			+ " double NOT NULL,"
			+ AUTO_GRN_DONE
			+ " text NOT NULL,"
			+ EMPTY_DONE
			+ " text NOT NULL,"
			+ ZED_NO
			+ " int NOT NULL,"
			+ SR_NO
			+ " int NOT NULL,"
			+ PACK
			+ " text NOT NULL,"
			+ PROCESSED_ITEM
			+ " text NOT NULL,"
			+ PACKQTY
			+ " double NOT NULL,"
			+ PACKPRCE
			+ " double NOT NULL,"
			+ LINEDISC
			+ " double NOT NULL,"
			+ TXN_PRDCT_MODIFIER_INSTRUC + " text," + SCAN_CODE + " text" + ")";

	// SCAN CODES MST table column names
	public static final String SCAN_COST_DSCNT_PRCE = "DSCNT_COST_PRICE";
	public static final String SCAN_DSCNT_PRCE = "DSCNT_PRICE";
	public static final String SCAN_QTY = "QTY";
	public static final String SCAN_PRDCT_CODE = "PRDCT_CODE";
	public static final String SCAN_UOM = "UOM";
	public static final String SCAN_USR_NM = "USR_NAME";

	public final static String CREATE_SCAN_CODES_TABLE = "CREATE TABLE IF NOT EXISTS "
			+ dbo_SCAN_CODS_MST_TABLE
			+ " ( "
			+ SCAN_COST_DSCNT_PRCE
			+ " double,"
			+ SCAN_DSCNT_PRCE
			+ " double,"
			+ SCAN_QTY
			+ " double,"
			+ SCAN_PRDCT_CODE
			+ " text,"
			+ SCAN_UOM
			+ " text,"
			+ SCAN_CODE
			+ " text,"
			+ SCAN_USR_NM
			+ " text,PRIMARY KEY ("
			+ SCAN_PRDCT_CODE
			+ "," + SCAN_CODE + "," + SCAN_QTY + "))";

	public final static String CREATE_UPDATE_SCAN_CODES_TABLE = "CREATE TABLE IF NOT EXISTS "
			+ dbo_UPDATE_SCAN_CODS_MST_TABLE
			+ " ( "
			+ SCAN_COST_DSCNT_PRCE
			+ " double,"
			+ SCAN_DSCNT_PRCE
			+ " double,"
			+ SCAN_QTY
			+ " double,"
			+ SCAN_PRDCT_CODE
			+ " text,"
			+ SCAN_UOM
			+ " text,"
			+ SCAN_CODE
			+ " text,"
			+ SCAN_USR_NM
			+ " text,PRIMARY KEY ("
			+ SCAN_PRDCT_CODE
			+ "," + SCAN_CODE + "," + SCAN_QTY + "))";

	// BIll_INSTRCTN_TXN table column names
	public static final String BILL_INSTRCTN_TXN_CMP_CODE = "CMP_CODE";
	public static final String BILL_INSTRCTN_TXN_BRANCH_CODE = "BRANCH_CODE";
	public static final String BILL_INSTRCTN_TXN_BILL_SCND = "BILL_SCND";
	public static final String BILL_INSTRCTN_TXN_PRDCT_CODE = "PRDCT_CODE";
	public static final String BILL_INSTRCTN_TXN_PCK = "PCK";
	public static final String BILL_INSTRCTN_TXN_INSTRCTN_CODE = "INSTRCTN_CODE";
	public static final String BILL_INSTRCTN_TXN_EXTRA_INSTRCTN = "EXTRA_INSTRCTN";
	public static final String BILL_INSTRCTN_TXN_PRDCT_VOID = "PRDCT_VOID";
	public static final String BILL_INSTRCTN_TXN_INSTRCN_QTY = "INSTRCN_QTY";
	public static final String BILL_INSTRCTN_TXN_ROW_ID = "ROW_ID";

	public final static String CREATE_BILL_INSTRCTN_TXN_TABLE = "CREATE TABLE IF NOT EXISTS "
			+ dbo_BILL_INSTRCTN_TXN_TABLE
			+ " ( "
			+ BILL_INSTRCTN_TXN_ROW_ID
			+ " text,"
			+ BILL_INSTRCTN_TXN_CMP_CODE
			+ " text,"
			+ BILL_INSTRCTN_TXN_BRANCH_CODE
			+ " text,"
			+ BILL_INSTRCTN_TXN_BILL_SCND
			+ " text,"
			+ BILL_INSTRCTN_TXN_PRDCT_CODE
			+ " text,"
			+ BILL_INSTRCTN_TXN_PCK
			+ " text,"
			+ BILL_INSTRCTN_TXN_INSTRCTN_CODE
			+ " text,"
			+ BILL_INSTRCTN_TXN_EXTRA_INSTRCTN
			+ " text,"
			+ BILL_INSTRCTN_TXN_PRDCT_VOID
			+ " text,"
			+ BILL_INSTRCTN_TXN_INSTRCN_QTY + " double" + ")";

	public static final String SM_CODE = "SM_CODE";
	public static final String REGION_CODE = "REGION_CODE";
	public static final String ACTIVE = "ACTIVE";
	public static final String FROM_DT = "FROM_DT";
	public static final String TO_DT = "TO_DT";
	public static final String Upload_Flg = "Upload_Flg";
	public static final String OLD_CODE = "OLD_CODE";

	public final static String CREATE_SLMN_MST_TABLE = "CREATE TABLE IF NOT EXISTS "
			+ dbo_SLMN_MST_TABLE
			+ " ( "
			+ SM_CODE
			+ " text NOT NULL,"
			+ NAME
			+ " text NOT NULL,"
			+ REGION_CODE
			+ " text NOT NULL,"
			+ BRNCH_CODE
			+ " text NOT NULL,"
			+ ACTIVE
			+ " text,"
			+ FROM_DT
			+ " text NOT NULL,"
			+ TO_DT
			+ " text NOT NULL,"
			+ Upload_Flg
			+ " text NOT NULL," + OLD_CODE + " text" + ")";

	public final static String CREATE_UPDATE_SLMN_MST_TABLE = "CREATE TABLE IF NOT EXISTS "
			+ dbo_UPDATE_SLMN_MST_TABLE
			+ " ( "
			+ SM_CODE
			+ " text NOT NULL,"
			+ NAME
			+ " text NOT NULL,"
			+ REGION_CODE
			+ " text NOT NULL,"
			+ BRNCH_CODE
			+ " text NOT NULL,"
			+ ACTIVE
			+ " text,"
			+ FROM_DT
			+ " text NOT NULL,"
			+ TO_DT
			+ " text NOT NULL,"
			+ Upload_Flg
			+ " text NOT NULL," + OLD_CODE + " text" + ")";

	// PRDCT_RECEIPE_MST_TABLE table column names
	public static final String PRDCT_RECIPE_CODE = "RECIPE_CODE";
	public static final String PRDCT_RCP_MEASURE = "RCP_MEASURE";
	public static final String PRDCT_RCP_UOM_CODE = "RCP_UOM_CODE";
	public static final String PRDCT_RCP_QTY = "RCP_QTY";
	public static final String PRDCT_RECIPE_COST = "COST";
	public static final String PRDCT_STK_UOM_CODE = "STK_UOM_CODE";
	public static final String PRDCT_STK_QTY = "STK_QTY";
	public static final String PRDCT_TLRNCE_QTY = "TLRNCE_QTY";
	public static final String PRDCT_STK_TLRNCE_QTY = "STK_TLRNCE_QTY";

	public final static String CREATE_PRDCT_RECIPE_DTL_MST_TABLE = "CREATE TABLE IF NOT EXISTS "
			+ dbo_PRDCT_RECEIPE_MST_TABLE
			+ " ( "
			+ PRDCT_RECIPE_CODE
			+ " text NOT NULL,"
			+ PRDCT_RCP_MEASURE
			+ " text,"
			+ PRDCT_CODE
			+ " text NOT NULL,"
			+ PRDCT_RCP_UOM_CODE
			+ " text NOT NULL,"
			+ PRDCT_RCP_QTY
			+ " double NOT NULL,"
			+ PRDCT_RECIPE_COST
			+ " double NOT NULL,"
			+ PRDCT_STK_UOM_CODE
			+ " text NOT NULL,"
			+ PRDCT_STK_QTY
			+ " double NOT NULL,"
			+ PRDCT_TLRNCE_QTY
			+ " double NOT NULL,"
			+ PRDCT_STK_TLRNCE_QTY
			+ " double NOT NULL"
			+ ")";

	public final static String CREATE_UPDATE_PRDCT_RECIPE_DTL_MST_TABLE = "CREATE TABLE IF NOT EXISTS "
			+ dbo_UPDATE_PRDCT_RECEIPE_MST_TABLE
			+ " ( "
			+ PRDCT_RECIPE_CODE
			+ " text NOT NULL,"
			+ PRDCT_RCP_MEASURE
			+ " text,"
			+ PRDCT_CODE
			+ " text NOT NULL,"
			+ PRDCT_RCP_UOM_CODE
			+ " text NOT NULL,"
			+ PRDCT_RCP_QTY
			+ " double NOT NULL,"
			+ PRDCT_RECIPE_COST
			+ " double NOT NULL,"
			+ PRDCT_STK_UOM_CODE
			+ " text NOT NULL,"
			+ PRDCT_STK_QTY
			+ " double NOT NULL,"
			+ PRDCT_TLRNCE_QTY
			+ " double NOT NULL,"
			+ PRDCT_STK_TLRNCE_QTY
			+ " double NOT NULL"
			+ ")";

	// PRDCT_CNSMPTN_MST_TABLE table column names
	public static final String CNSMPTN_CMPNY_CODE = "CMPNY_CODE";
	public static final String CNSMPTN_BRNCH_CODE = "BRNCH_CODE";
	public static final String CNSMPTN_TXN_NO = "TXN_NO";
	public static final String CNSMPTN_PRDCT_CODE = "PRDCT_CODE";
	public static final String CNSMPTN_STK_UOM_CODE = "STK_UOM_COD";
	public static final String CNSMPTN_STK_QTY = "STK_QTY";
	public static final String CNSMPTN_PRDCT_PRCE = "PRDCT_PRCE";
	public static final String CNSMPTN_PRDCT_VAT_CODE = "PRDCT_VAT_CODE";
	public static final String CNSMPTN_PRDCT_VAT_AMNT = "PRDCT_VAT_AMNT";
	public static final String CNSMPTN_RECIPE_CODE = "RECIPE_CODE";
	public static final String CNSMPTN_PRDCT_VOID = "PRDCT_VOID";
	public static final String CNSMPTN_TXN_TYPE = "TXN_TYPE";
	public static final String CNSMPTN_TXN_MODE = "TXN_MODE";
	public static final String CNSMPTN_PCK = "PCK";

	public final static String CREATE_PRDCT_CNSMPTN_TXN_TABLE = "CREATE TABLE IF NOT EXISTS "
			+ dbo_CNSMPTN_TXN
			+ " ( "
			+ CNSMPTN_CMPNY_CODE
			+ " text,"
			+ CNSMPTN_BRNCH_CODE
			+ " text,"
			+ CNSMPTN_TXN_NO
			+ " text NOT NULL,"
			+ CNSMPTN_PRDCT_CODE
			+ " text NOT NULL,"
			+ CNSMPTN_STK_UOM_CODE
			+ " text NOT NULL,"
			+ CNSMPTN_STK_QTY
			+ " double NOT NULL,"
			+ CNSMPTN_PRDCT_PRCE
			+ " double NOT NULL,"
			+ CNSMPTN_PRDCT_VAT_CODE
			+ " text NOT NULL,"
			+ CNSMPTN_PRDCT_VAT_AMNT
			+ " double NOT NULL,"
			+ CNSMPTN_PRDCT_VOID
			+ " text NOT NULL,"
			+ CNSMPTN_RECIPE_CODE
			+ " text NOT NULL,"
			+ CNSMPTN_TXN_TYPE
			+ " text NOT NULL,"
			+ CNSMPTN_TXN_MODE
			+ " text NOT NULL," + CNSMPTN_PCK + " text NOT NULL" + ")";

	/** Salesman **/
	public static final String Slm_Code = "Slm_Code";
	public static final String Sys_run_date = "Sys_run_date";
	public static final String User_Name = "User_Name";

	public final static String CREATE_SLMN_TXN_TABLE = "CREATE TABLE IF NOT EXISTS "
			+ dbo_SLMN_TXN_TABLE
			+ " ( "
			+ CMPNY_CODE
			+ " text NOT NULL,"
			+ BRNCH_CODE
			+ " text,"
			+ BILL_SCNCD
			+ " text NOT NULL,"
			+ Slm_Code
			+ " text NOT NULL,"
			+ Sys_run_date
			+ " text NOT NULL,"
			+ TILL_NO
			+ " text NOT NULL," + User_Name + " text NOT NULL" + ")";

	public static final String VALUE = "VALUE";
	public static final String CURR_CODE = "CURR_CODE";
	public static final String UNIT = "UNIT";
	public static final String USER_NAME = "USER_NAME";
	public static final String CREATED_ON = "CREATED_ON";

	public final static String CREATE_DNMTN_MST_TABLE = "CREATE TABLE IF NOT EXISTS "
			+ dbo_DNMTN_MST_TABLE
			+ " ( "
			+ CURR_CODE
			+ " text NOT NULL,"
			+ UNIT
			+ " text,"
			+ VALUE
			+ " double NOT NULL,"
			+ USER_NAME
			+ " text,"
			+ CREATED_ON
			+ " text,"
			+ "PRIMARY KEY ("
			+ CURR_CODE
			+ ", " + UNIT + ", " + VALUE + "))";

	public final static String CREATE_UPDATE_DNMTN_MST_TABLE = "CREATE TABLE IF NOT EXISTS "
			+ dbo_UPDATE_DNMTN_MST_TABLE
			+ " ( "
			+ CURR_CODE
			+ " text NOT NULL,"
			+ UNIT
			+ " text,"
			+ VALUE
			+ " double NOT NULL,"
			+ USER_NAME
			+ " text NOT NULL,"
			+ CREATED_ON
			+ " text NOT NULL,"
			+ "PRIMARY KEY (" + CURR_CODE + ", " + UNIT + ", " + VALUE + "))";

	// PTY_FLT_MST table column names
	public static final String PF_CMPNY_CODE = "CMPNY_CODE";
	public static final String PF_BRNCH_CODE = "BRNCH_CODE";
	public static final String PF_TILL_NO = "TILL_NO";
	public static final String PF_USERNM = "USR_NAME";
	public static final String PF_RUN_DATE = "PTY_FLT_RUN_DATE";
	public static final String PF_SYS_DATE = "PTY_FLT_SYS_DATE";
	public static final String PF_AMT = "PTY_FLT_AMNT";

	public static final String PF_LOC_AMT = "PTY_FLT_LOC_AMNT";
	public static final String PF_OPRATOR = "PTY_FLT_OPERATOR";
	public static final String PF_EX_RATE = "PTY_FLT_EX_RATE";
	public static final String PF_CURR_ABBRE = "PTY_FLT_CURR_ABBRE";

	public static final String PF_DTLS = "PTY_FLT_DTLS";
	public static final String PF_TYPE = "PTY_FLT_TYPE";
	public static final String PF_USR_NM_SCND = "USR_NAME_SCND";
	public static final String PTY_SR_NO = "PtySrNo";
	public static final String PF_TXN_NO = "Trans_No";

	public final static String CREATE_PTY_FLT_MST_TABLE = "CREATE TABLE IF NOT EXISTS "
			+ dbo_PTY_FLT_MST_TABLE
			+ " ( "
			+ PF_CMPNY_CODE
			+ " text NOT NULL,"
			+ PF_BRNCH_CODE
			+ " text NOT NULL,"
			+ PF_TILL_NO
			+ " text NOT NULL,"
			+ PF_USERNM
			+ " text NOT NULL,"
			+ PF_RUN_DATE
			+ " text NOT NULL,"
			+ PF_SYS_DATE
			+ " text NOT NULL,"
			+ PF_AMT
			+ " double NOT NULL,"
			+ PF_LOC_AMT
			+ " double NOT NULL,"
			+ PF_OPRATOR
			+ " text NOT NULL,"
			+ PF_EX_RATE
			+ " double NOT NULL,"
			+ PF_CURR_ABBRE
			+ " text NOT NULL,"
			+ PF_DTLS
			+ " text NOT NULL,"
			+ PF_TYPE
			+ " text NOT NULL,"
			+ PF_USR_NM_SCND
			+ " text,"
			+ PTY_SR_NO + " integer NOT NULL," + PF_TXN_NO + " text" + ")";

	// MULTI_PTY_FLT_MST table column names
	public static final String MPF_CMPNY_CODE = "CMPNY_CODE";
	public static final String MPF_BRNCH_CODE = "BRNCH_CODE";
	public static final String MPF_TILL_NO = "TILL_NO";
	public static final String MPF_USERNM = "USR_NAME";
	public static final String MPF_RUN_DATE = "PTY_FLT_RUN_DATE";
	public static final String MPF_SYS_DATE = "PTY_FLT_SYS_DATE";
	public static final String MPF_TYPE = "PTY_FLT_TYPE";
	public static final String MPF_USR_NM_SCND = "USR_NAME_SCND";
	public static final String MPTY_SR_NO = "PtyMultiSrNo";
	public static final String MPF_TXN_NO = "Trans_No";
	public static final String MPF_PTY_FLT_CUR_ABBR = "PTY_FLT_CUR_ABBR";
	public static final String MPF_PTY_FLT_CUR_AMT = "PTY_FLT_CUR_AMNT";
	public static final String MPF_PTY_FLT_EX_RT = "PTY_FLT_EX_RT";
	public static final String MPF_PTY_FLT_OPERATOR = "PTY_FLT_OPERATOR";
	public static final String MPF_PTY_FLT_LOC_AMT = "PTY_FLT_LOC_AMNT";

	public final static String CREATE_MULTI_PTY_FLT_MST_TABLE = "CREATE TABLE IF NOT EXISTS "
			+ dbo_MULI_PTY_FLT_MST_TABLE
			+ " ( "
			+ MPF_CMPNY_CODE
			+ " text NOT NULL,"
			+ MPF_BRNCH_CODE
			+ " text NOT NULL,"
			+ MPF_TILL_NO
			+ " text NOT NULL,"
			+ MPF_USERNM
			+ " text NOT NULL,"
			+ MPF_RUN_DATE
			+ " text NOT NULL,"
			+ MPF_SYS_DATE
			+ " text NOT NULL,"
			+ MPF_PTY_FLT_CUR_ABBR
			+ " text NOT NULL,"
			+ MPF_PTY_FLT_CUR_AMT
			+ " double NOT NULL,"
			+ MPF_PTY_FLT_EX_RT
			+ " double NOT NULL,"
			+ MPF_PTY_FLT_OPERATOR
			+ " text NOT NULL,"
			+ MPF_PTY_FLT_LOC_AMT
			+ " double NOT NULL,"
			+ MPF_TYPE
			+ " text NOT NULL,"
			+ MPF_USR_NM_SCND
			+ " text,"
			+ MPTY_SR_NO
			+ " integer NOT NULL," + MPF_TXN_NO + " text" + ")";

	// MULTI_CHNG_MST table column names
	public static final String MCM_PYMNT_NMBR = "PYMNT_NMBR";
	public static final String MCM_CURR_ABBR = "CURR_ABBR";
	public static final String MCM_CHNG_AMNT = "CHNG_AMNT";
	public static final String MCM_EX_RATE = "EX_RATE";
	public static final String MCM_OPERATOR = "OPERATOR";
	public static final String MCM_LOC_AMNT = "LOC_AMNT";

	public final static String CREATE_MULTI_CHNG_MST_TABLE = "CREATE TABLE IF NOT EXISTS "
			+ dbo_MULTI_CHNG_MST_TABLE
			+ " ( "
			+ MCM_PYMNT_NMBR
			+ " text NOT NULL,"
			+ MCM_CURR_ABBR
			+ " text NOT NULL,"
			+ MCM_CHNG_AMNT
			+ " double NOT NULL,"
			+ MCM_EX_RATE
			+ " double NOT NULL,"
			+ MCM_OPERATOR
			+ " text NOT NULL,"
			+ MCM_LOC_AMNT + " double NOT NULL" + ")";

	// TEMP_TRANSACTION_TXN_TABLE table column names
	public static final String TEMP_TXN_BILL_SCNCD = "TEMP_SCNCD";
	public static final String TEMP_TXN_UPLOAD_FLAG = "TEMP_UPLOAD_FLAG";

	public final static String CREATE_TEMP_TXN_TABLE = "CREATE TABLE IF NOT EXISTS "
			+ dbo_TEMP_TRANSACTION_TXN_TABLE
			+ " ( "
			+ TEMP_TXN_BILL_SCNCD
			+ " text NOT NULL," + TEMP_TXN_UPLOAD_FLAG + " text" + ")";

	// TEMP_FLT_PTY_PKUP_TXN_TABLE table column names
	public final static String CREATE_TEMP_FLT_PTY_PKUP_TABLE = "CREATE TABLE IF NOT EXISTS "
			+ dbo_TEMP_FLT_PTY_PKUP_TXN_TABLE
			+ " ( "
			+ TEMP_TXN_BILL_SCNCD
			+ " text NOT NULL," + TEMP_TXN_UPLOAD_FLAG + " text" + ")";

	// Config table column names
	public static final String CONFIG_REG_TYPE = "RegType";
	public static final String CONFIG_REG_NAME = "RegName";
	public static final String CONFIG_REG_VALUE = "RegValue";
	public static final String CONFIG_REG_EXT_VALUE = "RegExtValue";

	public final static String CREATE_CONFIG_MST_TABLE = "CREATE TABLE IF NOT EXISTS "
			+ dbo_CONFIG_MST_TABLE
			+ " ( "
			+ CONFIG_REG_TYPE
			+ " text NOT NULL,"
			+ CONFIG_REG_NAME
			+ " text NOT NULL,"
			+ CONFIG_REG_VALUE
			+ " text NOT NULL,"
			+ CONFIG_REG_EXT_VALUE
			+ " text" + ")";

}
