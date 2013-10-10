package com.google.zxing.ui;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * @author - Vishal Joshi
 */
public class DatabaseManager extends SQLiteOpenHelper {
	private SQLiteDatabase checkDB;
	private SQLiteDatabase database;
	private Context context;

	private String pathOfDatabase;

	private static DatabaseManager dataBaseHelper;

	private DatabaseManager(Context context) {
		super(context, "database_compulynx2", null, 1);
		this.context = context;
		pathOfDatabase = "/data/data/com.compulynx.Barcodes/databases/";

		createDatabase();
		openConnection();
	}
	public static DatabaseManager getDBInstance(Context context) {
		if (null == dataBaseHelper) {
			synchronized (DatabaseManager.class) {
				if (dataBaseHelper == null) {
					dataBaseHelper = new DatabaseManager(context);

					Log.d("DB", "new DB object created  " + dataBaseHelper);
				}
			}
		}
		return dataBaseHelper;
	}
	private void createDatabase() {
		// boolean dbExists = checkDbExists();
		boolean dbExists = false;;
		if (dbExists) {

			Log.d("DB", "Database already exists!");

			checkDB.close();
		} else {

			Log.d("DB", "Creating new database!");

			database = this.context.openOrCreateDatabase("database_compulynx2",
					Context.MODE_WORLD_WRITEABLE, null);
			database.execSQL("DROP TABLE IF EXISTS PRODUCTS;");
			database
					.execSQL("CREATE TABLE IF NOT EXISTS PRODUCTS (PRODUCT_BARCODE INTEGER PRIMARY KEY, Matched BOOLEAN);");
			database
					.execSQL("INSERT INTO PRODUCTS(PRODUCT_BARCODE,Matched) VALUES(123456,\"false\") ;");
			database
					.execSQL("INSERT INTO PRODUCTS(PRODUCT_BARCODE,Matched) VALUES(0420006200,\"false\") ;");

			database
					.execSQL("INSERT INTO PRODUCTS(PRODUCT_BARCODE,Matched) VALUES(2801669167,\"false\") ;");

			database.close();
		}
	}

	/*
	 * This method will check whether the DB already exits.
	 * 
	 * @return- returns true if DB exits otherwise false.
	 */
	private boolean checkDbExists() {
		checkDB = null;
		try {
			String myPath = pathOfDatabase + "database_compulynx2";
			checkDB = SQLiteDatabase.openDatabase(myPath, null,
					SQLiteDatabase.OPEN_READONLY);
		} catch (SQLiteException e) {

			Log.e("DB", "In checkDBExists()--SQLiteException obtained!");
			Log.e("DB", Log.getStackTraceString(e));

		}
		Log.d("DB", "In checkDBExists(), DB object is " + checkDB);
		return ((checkDB != null) ? true : false);
	}
	/*
	 * This method will open a writable connection to the database.
	 */
	private void openConnection() throws SQLException {
		try {
			database = this.getWritableDatabase();
			database.execSQL("PRAGMA foreign_keys = ON;");
		} catch (SQLException sqe) {

			Log.e("DB", "SQLException obtained while opening connection! ");
			Log.e("DB", Log.getStackTraceString(sqe));

			database.close();
			/*
			 * IF and exception occurs, the previous connection is closed and
			 * new connection is opened.
			 */
			database = this.getWritableDatabase();

			Log.d("DB", "New DB connection opened! ");

			Log.d("DB", "DB connection is now open! ");
		}

	}
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}
	public long insertProductsInfo(ProductsBean product) {
		ContentValues values = new ContentValues();
		values.put("PRODUCT_BARCODE", Long.parseLong(product.getContent()));
		// values.put("Matched", "true");
		long rowsInserted = database.insert("PRODUCTS", null, values);
		System.out.println("Number of rows inserted: " + rowsInserted);
		return rowsInserted;

	}
	public int getProductInfo(String content) {
		Cursor cursor = database.query("PRODUCTS", new String[]{"MATCHED"},
				"MATCHED=?", new String[]{"true"}, null, null, null);
		if (cursor.getCount() > 0) {
			return 1;

		}

		return 0;
	}
	public void setProductAsMatched(String content) {
		ContentValues values = new ContentValues();
		values.put("Matched", "true");
		int rowsUpdated = database.update("PRODUCTS", values,
				"PRODUCT_BARCODE=?", new String[]{content});
		System.out.println("Number of rows updated=" + rowsUpdated);
	}
	public List<ProductsBean> getAllProductInfo() {
		Cursor cursor = database.query("PRODUCTS", null, null, null, null,
				null, null);
		List<ProductsBean> barcodes = null;
		ProductsBean product = null;
		if (cursor.getCount() > 0) {
			barcodes = new ArrayList<ProductsBean>();
			cursor.moveToFirst();
			while (!cursor.isLast()) {
				product = new ProductsBean();
				product.setContent(cursor.getString(cursor
						.getColumnIndex("PRODUCT_BARCODE")));
				product.setMatched(Boolean.parseBoolean(cursor.getString(cursor
						.getColumnIndex("Matched"))));
				barcodes.add(product);
				cursor.moveToNext();
			}
		}

		return barcodes;
	}

}
