package com.mpos.framework.common;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mpos.application.MPOSApplication;

public class DatabaseHelper extends SQLiteOpenHelper {

	public static final String TAG = Constants.APP_TAG
			+ DatabaseHelper.class.getSimpleName();

	// JAVA Variables
	private SQLiteDatabase mDB = null;
	private static DatabaseHelper mDBInstance;
	private static int mDbCount = 0;

	/**
	 * Method- DatabaseHelper Constructor
	 * 
	 * @param context
	 */
	public DatabaseHelper(Context context) {
		super(context.getApplicationContext(), DBConstants.DATABASE_NAME, null,
				DBConstants.DATABASE_VERSION);
	}

	/**
	 * Method- Synchronized method which returns only one object of DB.
	 * Increment connection count every time
	 * 
	 * @return DatabaseHelper Singleton Object
	 */
	public synchronized static DatabaseHelper getDBInstance() {
		mDbCount += 1;
		/*if (mDBInstance == null) {
			mDBInstance = new DatabaseHelper(MPOSApplication.getContext());
		}*/
		mDBInstance = new DatabaseHelper(MPOSApplication.getContext());
		return mDBInstance;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		this.mDB = db;

		this.mDB.execSQL(DBConstants.CREATE_UPDATE_ANALYS_MST_TB); // PRDCT_MST_Tabl
		this.mDB.execSQL(DBConstants.CREATE_UPDATE_BINLOC_MST_TB); // PRDCT_MST_Table
		this.mDB.execSQL(DBConstants.CREATE_UPDATE_Currency_Mst); // PRDCT_MST_Table
		this.mDB.execSQL(DBConstants.CREATE_UPDATE_DNMTN_MST_TABLE); // PRDCT_MST_Table
		this.mDB.execSQL(DBConstants.CREATE_UPDATE_DPT_MST_TB); // PRDCT_MST_Table
		this.mDB.execSQL(DBConstants.CREATE_UPDATE_FLX_POS_MST_TABLE); // PRDCT_MST_Table
		this.mDB.execSQL(DBConstants.CREATE_UPDATE_PRDCT_GRP_MST_TB); // PRDCT_MST_Table
		this.mDB.execSQL(DBConstants.CREATE_UPDATE_PRDCT_RECIPE_DTL_MST_TABLE); // PRDCT_MST_Table
		this.mDB.execSQL(DBConstants.CREATE_UPDATE_SCAN_CODES_TABLE); // PRDCT_MST_Table
		this.mDB.execSQL(DBConstants.CREATE_UPDATE_SLMN_MST_TABLE); // PRDCT_MST_Table
		this.mDB.execSQL(DBConstants.CREATE_UPDATE_TILL_PRDCT_MST_TB); // PRDCT_MST_Table
		this.mDB.execSQL(DBConstants.CREATE_UPDATE_UOM_SLAB_MST_TABLE); // PRDCT_MST_Table
		this.mDB.execSQL(DBConstants.CREATE_UPDATE_UOM_TABLE); // PRDCT_MST_Table
		this.mDB.execSQL(DBConstants.CREATE_UPDATE_USER_ASSGND_RGHTS_TABLE); // PRDCT_MST_Table
		this.mDB.execSQL(DBConstants.CREATE_UPDATE_USER_MST_TABLE); // PRDCT_MST_Table
		this.mDB.execSQL(DBConstants.CREATE_UPDATE_VAT_MST_TABLE); // PRDCT_MST_Table
		this.mDB.execSQL(DBConstants.CREATE_UPDATE_INSTRCTN_MST_TABLE); // Instruction
																		// table
		this.mDB.execSQL(DBConstants.CREATE_UPDATE_PRDCT_INSTRCTN_MST_TABLE); // PRDCT
																				// Instruction
		// table

		this.mDB.execSQL(DBConstants.CREATE_CONFIG_MST_TABLE); // CONFIG TABLE

		this.mDB.execSQL(DBConstants.CREATE_TILL_PRDCT_MST_TB); // PRDCT_MST_Table
		this.mDB.execSQL(DBConstants.CREATE_BILL_MST_TABLE); // Bill_MST_Table
		this.mDB.execSQL(DBConstants.CREATE_PRDCT_GRP_MST_TB); // PRDCT_GRP_MST_Table
		this.mDB.execSQL(DBConstants.CREATE_DPT_MST_TB); // DPT_MST_Table
		this.mDB.execSQL(DBConstants.CREATE_ANALYS_MST_TB); // ANALYS_MST_Table
		this.mDB.execSQL(DBConstants.CREATE_BINLOC_MST_TB); // BINLOC_MST_Table
		this.mDB.execSQL(DBConstants.CREATE_USER_MST_TABLE); // user
																// master_MST_Table
		this.mDB.execSQL(DBConstants.CREATE_USER_ASSGND_RGHTS_TABLE); // USER
																		// ASSIGNED
																		// RIGHTS
																		// Table
		this.mDB.execSQL(DBConstants.CREATE_MULTY_PYMNTS_MST_TABLE); // Multiple
																		// payments
																		// Table
		this.mDB.execSQL(DBConstants.CREATE_PYMNTS_MST_TABLE); // payments Table
		this.mDB.execSQL(DBConstants.CREATE_Currency_Mst); // currency Table
		this.mDB.execSQL(DBConstants.CREATE_UOM_TABLE); // UOM Table
		this.mDB.execSQL(DBConstants.CREATE_UOM_SLAB_MST_TABLE); // UOM_SLAB_MST
																	// Table
		this.mDB.execSQL(DBConstants.CREATE_INSTRCTN_MST_TABLE); // INSTRCTN_MST
		// Table
		this.mDB.execSQL(DBConstants.CREATE_PRDCT_INSTRCTN_MST_TABLE); // PRDCT
																		// INSTRCTN_MST
		// Table

		this.mDB.execSQL(DBConstants.CREATE_VAT_MST_TABLE); // VAT_MST
		// Table
		this.mDB.execSQL(DBConstants.CREATE_SCAN_CODES_TABLE); // SCAN_CODE_MST
		// Table

		this.mDB.execSQL(DBConstants.CREATE_PRDCT_RECIPE_DTL_MST_TABLE); // PRDCT_RECIPE_DTL_MST
		// Table

		this.mDB.execSQL(DBConstants.CREATE_FLX_POS_MST_TABLE); // customer
																// details Table
		this.mDB.execSQL(DBConstants.CREATE_PTY_FLT_MST_TABLE);// PTY_FLT_MST
																// Table
		this.mDB.execSQL(DBConstants.CREATE_MULTI_PTY_FLT_MST_TABLE);// MULTI_PTY_FLT_MST
		// Table
		this.mDB.execSQL(DBConstants.CREATE_MULTI_CHNG_MST_TABLE);// MULTI_CHNG_MST
																	// table

		this.mDB.execSQL(DBConstants.CREATE_FLX_POS_TXN_TABLE); // customer
																// details after
																// txn Table

		this.mDB.execSQL(DBConstants.CREATE_HELD_TXN_TABLE);// held transaction
															// table
		this.mDB.execSQL(DBConstants.CREATE_BILL_TXN_TABLE); // Bill txn Table
		this.mDB.execSQL(DBConstants.CREATE_BILL_INSTRCTN_TXN_TABLE); // Bill
																		// Instrctn
																		// txn
																		// Table
		this.mDB.execSQL(DBConstants.CREATE_SLMN_MST_TABLE); // Sales person
																// table
		this.mDB.execSQL(DBConstants.CREATE_SLMN_TXN_TABLE);// sales transaction
															// table
		this.mDB.execSQL(DBConstants.CREATE_PRDCT_RECIPE_DTL_MST_TABLE);
		this.mDB.execSQL(DBConstants.CREATE_PRDCT_CNSMPTN_TXN_TABLE); // CNSMPTN_TXN
																		// Table

		this.mDB.execSQL(DBConstants.CREATE_DNMTN_MST_TABLE);// create
																// denomination
																// master table

		this.mDB.execSQL(DBConstants.CREATE_TEMP_TXN_TABLE);// create
		// temp TXN table
		this.mDB.execSQL(DBConstants.CREATE_TEMP_FLT_PTY_PKUP_TABLE);// create
																		// temp
																		// flt_pty_pkup
																		// table
		this.mDB.execSQL(DBConstants.CREATE_FLOAT_PYMNTS_MST_TABLE);// create
																	// table to
																	// store
																	// float
																	// petty
																	// currencies
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		db.execSQL("DROP TABLE IF EXISTS "
				+ DBConstants.dbo_UPDATE_TILL_PRDCT_MST_TABLE);
		db.execSQL("DROP TABLE IF EXISTS "
				+ DBConstants.dbo_UPDATE_PRDCT_GRP_MST_TABLE);
		db.execSQL("DROP TABLE IF EXISTS "
				+ DBConstants.dbo_UPDATE_DPT_MST_TABLE);
		db.execSQL("DROP TABLE IF EXISTS "
				+ DBConstants.dbo_UPDATE_ANALYS_MST_TABLE);
		db.execSQL("DROP TABLE IF EXISTS "
				+ DBConstants.dbo_UPDATE_BIN_LOC_MST_TABLE);
		db.execSQL("DROP TABLE IF EXISTS "
				+ DBConstants.dbo_UPDATE_USER_MST_TABLE);
		db.execSQL("DROP TABLE IF EXISTS "
				+ DBConstants.dbo_UPDATE_USR_ASSGND_RGHTS_TABLE);
		db.execSQL("DROP TABLE IF EXISTS "
				+ DBConstants.dbo_UPDATE_Currency_Mst_TABLE);
		db.execSQL("DROP TABLE IF EXISTS "
				+ DBConstants.dbo_UPDATE_UOM_SLAB_MST_TABLE);
		db.execSQL("DROP TABLE IF EXISTS " + DBConstants.dbo_UPDATE_UOM_TABLE);
		db.execSQL("DROP TABLE IF EXISTS "
				+ DBConstants.dbo_UPDATE_FLX_POS_MST_TABLE);
		db.execSQL("DROP TABLE IF EXISTS "
				+ DBConstants.dbo_UPDATE_VAT_MST_TABLE);
		db.execSQL("DROP TABLE IF EXISTS "
				+ DBConstants.dbo_UPDATE_SCAN_CODS_MST_TABLE);
		db.execSQL("DROP TABLE IF EXISTS "
				+ DBConstants.dbo_UPDATE_SLMN_MST_TABLE);
		db.execSQL("DROP TABLE IF EXISTS "
				+ DBConstants.dbo_UPDATE_DNMTN_MST_TABLE);
		db.execSQL("DROP TABLE IF EXISTS "
				+ DBConstants.dbo_UPDATE_PRDCT_RECEIPE_MST_TABLE);
		db.execSQL("DROP TABLE IF EXISTS " + DBConstants.dbo_CONFIG_MST_TABLE);

		db.execSQL("DROP TABLE IF EXISTS "
				+ DBConstants.dbo_TILL_PRDCT_MST_TABLE);
		db.execSQL("DROP TABLE IF EXISTS " + DBConstants.dbo_BILL_MST_TABLE);
		db.execSQL("DROP TABLE IF EXISTS "
				+ DBConstants.dbo_PRDCT_GRP_MST_TABLE);
		db.execSQL("DROP TABLE IF EXISTS " + DBConstants.dbo_DPT_MST_TABLE);
		db.execSQL("DROP TABLE IF EXISTS " + DBConstants.dbo_ANALYS_MST_TABLE);
		db.execSQL("DROP TABLE IF EXISTS " + DBConstants.dbo_BIN_LOC_MST_TABLE);
		db.execSQL("DROP TABLE IF EXISTS " + DBConstants.dbo_USER_MST_TABLE);
		db.execSQL("DROP TABLE IF EXISTS "
				+ DBConstants.dbo_USR_ASSGND_RGHTS_TABLE);
		db.execSQL("DROP TABLE IF EXISTS "
				+ DBConstants.dbo_MULTI_PYMNTS_MST_TABLE);
		db.execSQL("DROP TABLE IF EXISTS " + DBConstants.dbo_PYMNTS_MST_TABLE);
		db.execSQL("DROP TABLE IF EXISTS " + DBConstants.dbo_Currency_Mst_TABLE);

		db.execSQL("DROP TABLE IF EXISTS " + DBConstants.dbo_UOM_SLAB_MST_TABLE);
		db.execSQL("DROP TABLE IF EXISTS " + DBConstants.dbo_UOM_TABLE);
		db.execSQL("DROP TABLE IF EXISTS " + DBConstants.dbo_INSTRCTN_MST_TABLE);
		db.execSQL("DROP TABLE IF EXISTS "
				+ DBConstants.dbo_PRDCT_INSTRCTN_MST_TABLE);
		db.execSQL("DROP TABLE IF EXISTS " + DBConstants.dbo_VAT_MST_TABLE);
		db.execSQL("DROP TABLE IF EXISTS "
				+ DBConstants.dbo_SCAN_CODS_MST_TABLE);
		db.execSQL("DROP TABLE IF EXISTS "
				+ DBConstants.dbo_PRDCT_RECEIPE_MST_TABLE);
		db.execSQL("DROP TABLE IF EXISTS " + DBConstants.dbo_PTY_FLT_MST_TABLE);
		db.execSQL("DROP TABLE IF EXISTS "
				+ DBConstants.dbo_MULI_PTY_FLT_MST_TABLE);
		db.execSQL("DROP TABLE IF EXISTS "
				+ DBConstants.dbo_MULTI_CHNG_MST_TABLE);

		db.execSQL("DROP TABLE IF EXISTS " + DBConstants.dbo_FLX_POS_MST_TABLE);

		db.execSQL("DROP TABLE IF EXISTS " + DBConstants.dbo_HELD_TXN);
		db.execSQL("DROP TABLE IF EXISTS " + DBConstants.dbo_CNSMPTN_TXN);

		db.execSQL("DROP TABLE IF EXISTS " + DBConstants.dbo_BILL_TXN_TABLE);
		db.execSQL("DROP TABLE IF EXISTS "
				+ DBConstants.dbo_BILL_INSTRCTN_TXN_TABLE);
		db.execSQL("DROP TABLE IF EXISTS " + DBConstants.dbo_SLMN_MST_TABLE);
		db.execSQL("DROP TABLE IF EXISTS " + DBConstants.dbo_FLX_POS_TXN_TABLE);
		db.execSQL("DROP TABLE IF EXISTS "
				+ DBConstants.dbo_PRDCT_RECEIPE_MST_TABLE);
		db.execSQL("DROP TABLE IF EXISTS " + DBConstants.dbo_SLMN_TXN_TABLE);
		db.execSQL("DROP TABLE IF EXISTS " + DBConstants.dbo_DNMTN_MST_TABLE);
		db.execSQL("DROP TABLE IF EXISTS "
				+ DBConstants.dbo_TEMP_TRANSACTION_TXN_TABLE);
		db.execSQL("DROP TABLE IF EXISTS "
				+ DBConstants.dbo_TEMP_FLT_PTY_PKUP_TXN_TABLE);
		db.execSQL("DROP TABLE IF EXISTS "
				+ DBConstants.dbo_FLOAT_PYMNTS_MST_TABLE);

		onCreate(db);
	}

	/**
	 * Method- To check connection count before closing the connection. Close
	 * only when count is one.
	 */
	public synchronized void closeConnection() {
		if (mDbCount != 0) {
			if (mDbCount == 1) {
				this.mDB.close();
			}
			mDbCount--;
		}
	}
}