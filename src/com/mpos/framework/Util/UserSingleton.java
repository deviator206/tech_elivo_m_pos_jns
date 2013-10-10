package com.mpos.framework.Util;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.mpos.framework.common.Config;
import com.mpos.framework.common.Constants;
import com.mpos.framework.common.Logger;
import com.mpos.home.model.NavigationModel;
import com.mpos.login.model.USER_MST_Model;
import com.mpos.master.model.UserRightsModel;

public class UserSingleton implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String TAG = Constants.APP_TAG
			+ UserSingleton.class.getSimpleName();

	public boolean isPaymentDone = false;
	private static final String USER_DATA_AVAILABLE = "USER_DATA_AVAILABLE";
	private static final String USER_LOGIN_TIME = "USER_LOGIN_TIME";
	private static final String MST_UPDATE_TIME = "MST_UPDATE_TIME";
	private static final String SERVER_NAME = "SERVER_NAME";
	private static final String MASTER_SYNC = "MASTER_SYNC";
	private static UserSingleton mUserSingletonObj = null;

	private String totalRounding = "0.0"; // Total rounding cal variable for
											// zReport

	// To check network connection
	public boolean isNetworkAvailable;

	// To Store data related to Live session logged in user
	public USER_MST_Model mUserDetail = null;

	// User Assigned rights
	public UserRightsModel mUserAssgndRightsModel = null;

	// To decide User is signed in or not.
	public boolean isSignedIn = false;

	// To decide whether Master date downloaded or not.
	public boolean isDownloaded = false;

	// Will decide whether all master data is downloaded or not
	public int mMasterDateCount = 0;

	// Will get increment each time user do transaction
	public int mReceipt_NoCount = 000000;

	// FLT_PTY_PKUP code, increment each time user add it.
	public int mFLT_PTY_PKUP_CNT = 0;

	// User who is logged in
	public String mUserName = "";

	// Navigation Data
	public LinkedList<NavigationModel> navigationTagArr = null;

	// Grid Selection Data
	public LinkedList<NavigationModel> selectionTagArr = null;

	// Unique Transaction Number;
	public String mUniqueTrasactionNo = "";

	// Username of User who authenticated the particular restricted process
	public String mUsr_Anthntctd = "";

	public int mvoidLimitCount = 0;

	// number of held transaction
	public int mheldLimitCount = 0;

	// Max Zed No
	public String mMaxZedNo = "0";

	// Total product records count
	public int mNoOfRecords = 0;

	// Constructor
	private UserSingleton() {
		navigationTagArr = new LinkedList<NavigationModel>();
		selectionTagArr = new LinkedList<NavigationModel>();
	}

	private static Context mContext;

	public static synchronized UserSingleton getInstance(Context context) {
		if (mUserSingletonObj == null) {
			mUserSingletonObj = new UserSingleton();
		}
		mContext = context;
		return mUserSingletonObj;
	}

	/**
	 * Method- To decide whether Master data is synchronized are not
	 * 
	 * @return
	 */
	public boolean isMasterDataSynchronised() {
		Logger.d(TAG, "isMasterDataSynchronised:" + mMasterDateCount);
		// if (mMasterDateCount == 9) {
		if (mMasterDateCount == 18) {
			isDownloaded = true;
		} else {
			isDownloaded = false;
		}
		return isDownloaded;
	}

	public int incrementMstCount() {
		mMasterDateCount = mMasterDateCount + 1;
		Logger.d(TAG, "MSTCount:" + mMasterDateCount);
		return mMasterDateCount;
	}

	public int getMStCount() {
		return mMasterDateCount;
	}

	public void storeCheckForUserData(boolean flag) {
		SharedPreferences prefs = mContext.getSharedPreferences("com.mpos",
				Context.MODE_PRIVATE);
		Editor prefEditor = prefs.edit();
		prefEditor.putBoolean(USER_DATA_AVAILABLE, flag);
		prefEditor.commit();
	}

	public boolean checkUserDataAvailable() {
		SharedPreferences prefs = mContext.getSharedPreferences("com.mpos",
				Context.MODE_PRIVATE);
		return prefs.getBoolean(USER_DATA_AVAILABLE, false);
	}

	/**
	 * Method- To store in shared pref, Master data synchronized
	 */
	public void storeInSharedPref(boolean isMasterSync) {
		SharedPreferences prefs = mContext.getSharedPreferences("com.mpos",
				Context.MODE_PRIVATE);
		Editor prefEditor = prefs.edit();
		Logger.d(TAG, "storeInSharedPref:");
		prefEditor.putBoolean(MASTER_SYNC, isMasterSync);
		prefEditor.commit();
	}

	/**
	 * Method- To check master data synced previously or not
	 * 
	 * @return
	 */
	public boolean getSharedPref() {
		SharedPreferences prefs = mContext.getSharedPreferences("com.mpos",
				Context.MODE_PRIVATE);
		Logger.d(TAG, "getSharedPref:" + prefs.getBoolean(MASTER_SYNC, false));
		return prefs.getBoolean(MASTER_SYNC, false);
	}

	public int incrementReceiptCount() {
		mReceipt_NoCount = mReceipt_NoCount + 1;
		Logger.d(TAG, "BillCount:" + mReceipt_NoCount);
		return mReceipt_NoCount;
	}

	public int getReceiptCount() {
		return mReceipt_NoCount;
	}

	public int incrementFPPCount() {
		mFLT_PTY_PKUP_CNT = mFLT_PTY_PKUP_CNT + 1;
		Logger.d(TAG, "FPPCount:" + mFLT_PTY_PKUP_CNT);
		return mFLT_PTY_PKUP_CNT;
	}

	public int getFPPCount() {
		return mFLT_PTY_PKUP_CNT;
	}

	public void setUserLoginTimeInPref(String currentDateandTime) {
		SharedPreferences prefs = mContext.getSharedPreferences("com.mpos",
				Context.MODE_PRIVATE);
		Editor prefEditor = prefs.edit();
		prefEditor.putString(USER_LOGIN_TIME, currentDateandTime);
		prefEditor.commit();
	}

	public String getUserLoginTime() {
		SharedPreferences prefs = mContext.getSharedPreferences("com.mpos",
				Context.MODE_PRIVATE);
		return prefs.getString(USER_LOGIN_TIME, "");
	}

	public void setMstUpdateTimeInPref(String currentDateandTime) {
		SharedPreferences prefs = mContext.getSharedPreferences("com.mpos",
				Context.MODE_PRIVATE);
		Editor prefEditor = prefs.edit();
		prefEditor.putString(MST_UPDATE_TIME, currentDateandTime);
		prefEditor.commit();
	}

	public String getMstUpdateTime() {
		SharedPreferences prefs = mContext.getSharedPreferences("com.mpos",
				Context.MODE_PRIVATE);
		return prefs.getString(MST_UPDATE_TIME, "");
	}

	public void setServerNameInPref(String serverName) {
		SharedPreferences prefs = mContext.getSharedPreferences("com.mpos",
				Context.MODE_PRIVATE);
		Editor prefEditor = prefs.edit();
		prefEditor.putString(SERVER_NAME, serverName);
		prefEditor.commit();
	}

	public String getServerName() {
		SharedPreferences prefs = mContext.getSharedPreferences("com.mpos",
				Context.MODE_PRIVATE);
		System.out.println("==>>SERVER NAME::"+prefs.getString(SERVER_NAME, ""));
		
		return prefs.getString(SERVER_NAME, "");
	}

	public String generateUniqueTransactionNo() {
		mUniqueTrasactionNo = "";
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyHHmmss");
		String currentDateandTime = sdf.format(new Date());
		Logger.d(TAG, "CurrentTime:" + currentDateandTime);

		mUniqueTrasactionNo = mUniqueTrasactionNo.concat(Constants.staticNo);
		mUniqueTrasactionNo = mUniqueTrasactionNo.concat("" + Config.TAB_NO);
		mUniqueTrasactionNo = mUniqueTrasactionNo.concat(Config.BRANCH_CODE);
		mUniqueTrasactionNo = mUniqueTrasactionNo.concat(currentDateandTime);
		mUniqueTrasactionNo = mUniqueTrasactionNo.concat("" + mReceipt_NoCount);

		Logger.d(TAG, "TrnstnNo: " + mUniqueTrasactionNo);

		return mUniqueTrasactionNo;
	}

	public String getTransactionNo() {
		return mUniqueTrasactionNo;
	}

	/**
	 * @return the totalRounding
	 */
	public String getTotalRounding() {
		return totalRounding;
	}

	/**
	 * @param totalRounding
	 *            the totalRounding to set
	 */
	public void setTotalRounding(String totalRounding) {
		this.totalRounding = totalRounding;
	}

	public int getmNoOfRecords() {
		return mNoOfRecords;
	}

	public void setmNoOfRecords(int mNoOfRecords) {
		this.mNoOfRecords = mNoOfRecords;
		Logger.d(TAG, "Prdct Records: " + mNoOfRecords);
	}

}
