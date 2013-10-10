/*
 * Copyright (C) 2013  All Rights Reserved.
 *  PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mpos.home.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.client.android.CaptureActivity;
import com.mpos.R;
import com.mpos.application.BaseActivity;
import com.mpos.application.MPOSApplication;
import com.mpos.framework.Util.UserSingleton;
import com.mpos.framework.common.Config;
import com.mpos.framework.common.Constants;
import com.mpos.framework.common.DBConstants;
import com.mpos.framework.common.Logger;
import com.mpos.framework.common.OperationalResult;
import com.mpos.framework.handler.ActivityHandler;
import com.mpos.framework.helper.ActivityHelper;
import com.mpos.framework.inf.ActivityFragmentCommunicationListener;
import com.mpos.framework.inf.BaseResponseListener;
import com.mpos.framework.inf.FragmentCommunicationInterface;
import com.mpos.framework.inf.ListItemSelectedListener;
import com.mpos.framework.inf.NetworkChangeListener;
import com.mpos.home.adapter.NavigationFlowAdpter;
import com.mpos.home.fragment.BillFragment;
import com.mpos.home.fragment.GridFragment;
import com.mpos.home.fragment.PrdctQtyPrceMesrInstrFragment;
import com.mpos.home.fragment.UserOptionFragment;
import com.mpos.home.model.NavigationModel;
import com.mpos.login.model.USER_MST_Model;
import com.mpos.master.helper.MasterHelper;
import com.mpos.master.model.BaseModel;
import com.mpos.master.model.PRDCT_ANALYS_MST_Model;
import com.mpos.master.model.PRDCT_BINLOC_MST_Model;
import com.mpos.master.model.PRDCT_DPT_MST_Model;
import com.mpos.master.model.PRDCT_GRP_MST_Model;
import com.mpos.master.model.PRDCT_MST_Model;
import com.mpos.master.model.PrdctImageMstModel;
import com.mpos.master.model.SCAN_CODE_MST_Model;
import com.mpos.master.model.USR_ASSGND_RGHTSModel;
import com.mpos.master.model.UserRightsModel;
import com.mpos.transactions.activity.OfflineTxnUploadHelper;

/**
 * Description-
 * 
 */
public class HomeActivity extends BaseActivity implements BaseResponseListener,
		ListItemSelectedListener, FragmentCommunicationInterface,
		ActivityFragmentCommunicationListener, NetworkChangeListener {

	public static final String TAG = Constants.APP_TAG
			+ HomeActivity.class.getSimpleName();

	// Android Variable
	private static boolean isUpdateMasterDB;
	private TextView mUserInfoText;
	private TextView mCmpnyText;
	private TextView mCurrentDateText;
	private static ImageView mOnlineIndicatorIV;
	private TextView mSelectedRefineMents;
	private EditText mSearchET;
	private Button mScanBtn = null;

	private static Boolean alertForNetworkErrorShown = false;

	private FrameLayout mBillRightPanelLayout;
	private FrameLayout mBillLefttPanelLayout;
	private LinearLayout mBillRightPanelLinearLayout;
	private LinearLayout mBillLefttPanelLinearLayout;
	private FrameLayout mDisplayCategoriesLayout;
	private FrameLayout mUserOptionLayout;
	private Dialog navTypeAlert = null;

	private LinearLayout mSelectedCategoryLL = null;
	private FragmentTransaction mFragmentTransaction;
	private Fragment billFragment = null;

	// Java Variables
	public static final String PRDCT_GRP_MST = "PRDCT_GRP_MST";
	public static final String DPT_MST = "DPT_MST";
	public static final String ANLYS_MST = "ANLYS_MST";
	public static final String BIN_LOC_MST = "BIN_LOC_MST";
	private final String YES = "Y";
	private final String QTY_PRCE_MESR_INSTR_FRAGMENT = "QTY_PRCE_MESR_INSTR_FRAGMENT";

	private String HOME_NAME = "Home";
	private String HOME_CODE = "001";

	private Fragment grpGridFragment = null;
	private Fragment dptGridFragment = null;
	private Fragment analysGridFragment = null;
	private Fragment binLocGridFragment = null;
	private Fragment prdctGridFragment = null;

	public static final int SCAN_INTENT = 11;
	public static final int SCAN_SUCCESS = 200;
	private int MST_SUCCESS_VALUE = 18;

	// The page we are fetching
	private int mImagePageIndex = 0;
	// The no. of records we are fetching
	private int mImageBatchCount = 10;// 20;
	// To check image progress
	private boolean isImageProgressOn = false;

	// To check grid selection in progress
	private boolean isGridSelection = false;
	// To check multiple continuous calls are in place
	private boolean isDefaultGridSelection = false;
	// Flag to check whether multiple carry-on selections done
	private boolean isMultipleGridSelectionDone = false;

	// Master helper object
	// private MasterHelper mMasterHelper = null;
	private ArrayList<BaseModel> mBase_Mst_Models = null;
	private int mPreviousBillPanelPosition = -1;

	private OnClickListener mOnClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// mSearchET.clearFocus();
			((LinearLayout) findViewById(R.id.dummy_id)).requestFocus();
			InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			if (getCurrentFocus() != null
					&& getCurrentFocus().getWindowToken() != null) {
				inputManager.hideSoftInputFromWindow(getCurrentFocus()
						.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
			}
			switch (v.getId()) {

			case R.id.search_button:

				if (mSearchET.getText().toString().trim().length() > 0
						&& !mSearchET.getText().toString().contains("'")) {

					String prdctCode = mSearchET.getText().toString().trim();
					isGridSelection = true;
					startProgress();
					retrieveProductbyFreeSearch(prdctCode);
					mSearchET.setText("");
					((LinearLayout) findViewById(R.id.dummy_id)).requestFocus();
					// mSearchET.clearFocus();
				}
				break;

			case R.id.scan_button:
				Intent scanIntent = new Intent(HomeActivity.this,
						CaptureActivity.class);
				startActivityForResult(scanIntent, SCAN_INTENT);
				// startActivity(scanIntent);
				break;

			case R.id.selected_refinements_layout:
				// if (!isFinishing())
				// showNavigationDialog(UserSingleton
				// .getInstance(HomeActivity.this).navigationTagArr);
				break;
			}

		}
	};

	private OnItemClickListener mItemListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View view, int position,
				long arg3) {
			NavigationModel navModel = UserSingleton
					.getInstance(HomeActivity.this).navigationTagArr
					.get(position);
			Logger.e(TAG, "navModel " + navModel);
			navTypeAlert.dismiss();

		}
	};

	/**
	 * Method- To get searched product by its code from DB, if not present
	 * returns respective error message.
	 * 
	 * @param prdctCode
	 */
	private void retrieveProductbyFreeSearch(String prdctCode) {
		MasterHelper mMasterHelper = new MasterHelper(this, false);
		mMasterHelper.selectSearchPRDCTRecord(prdctCode,
				DBConstants.dbo_TILL_PRDCT_MST_TABLE, false, true);
		resetHomeGridUI();
	}

	/**
	 * Method- To update respective image in PRDCT_MST table as per product Id
	 * 
	 * @param prdctImgDataList
	 */
	private void updatePrdctImageInDB(
			ArrayList<PrdctImageMstModel> prdctImgDataList) {
		MasterHelper mMasterHelper = new MasterHelper(this, false);
		mMasterHelper.updatePrdctImageInPrdctMstDB(prdctImgDataList);
	}

	/**
	 * Constructor
	 */
	public HomeActivity() {

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);

		Logger.d(TAG, "onCreate");
		loadAllConfigData();
		componentInitialisation();

		// Register to Network change listener
		MPOSApplication.mImplementedNetworkLisContexts.put(
				HomeActivity.class.getSimpleName(), this);

		// Register to Network change listener: IMP
		OfflineTxnUploadHelper offlineTxnUploadHelper = new OfflineTxnUploadHelper();

		// generate new transaction no.
		generateNewTranactionNo();

		// Check whether data already loaded
		if (UserSingleton.getInstance(this).getSharedPref()) {
			// Load GRP table Data
			startProgress();
			MasterHelper mMasterHelper = new MasterHelper(this, false);
			mMasterHelper.selectGRPRecords("",
					DBConstants.dbo_PRDCT_GRP_MST_TABLE, true);
			mMasterHelper
					.selectUserRightsFromDB(
							UserSingleton.getInstance(this).mUserDetail
									.getUSR_GRP_ID(),
							DBConstants.dbo_USR_ASSGND_RGHTS_TABLE, true);
		} else {
			// Load All Master Data
			fetchAllMasterData(false);
		}
	}

	/**
	 * Method - To fetch all master data Asynchronously
	 * 
	 * @param isUpdateDB
	 */
	public void fetchAllMasterData(boolean isUpdateDB) {

		if (isUpdateDB) {
			UserSingleton.getInstance(HomeActivity.this).mMasterDateCount = 0;
		}
		MPOSApplication.isProcessingForMasterDb = true;
		ActivityHelper.NETWORK_NOT_AVAILABLE_POPUP_SHOWN = false;
		mImagePageIndex = 0;
		isUpdateMasterDB = isUpdateDB;
		MasterHelper mMasterHelper = new MasterHelper(this, isUpdateDB);
		Logger.d(TAG, "fetchAllMasterData");
		startProgress();

		mMasterHelper.getProductGroupData(Config.GET_GRP_MST, null, true);
		mMasterHelper.getProductDptData(Config.GET_DPT_MST, null, true);
		mMasterHelper.getProductAnalysData(Config.GET_ANALYS_MST, null, true);
		mMasterHelper.getProductBinLocData(Config.GET_BINLOC_MST, null, true);
		mMasterHelper.getProductMasterData(Config.GET_PRDCT_MST + "/"
				+ Config.BRANCH_CODE, null, true);

		mMasterHelper.getUOMData(Config.GET_UOM_MST, null, true);
		mMasterHelper.getUOMSlabData(Config.GET_UOMSLAB_MST, null, true);
		mMasterHelper.getInstrucData(Config.GET_INSTRUCTIONS_MST, null, true);
		mMasterHelper.getPRDCTInstrucData(Config.GET_PRDCT_INSTRUCTIONS_MST,
				null, true);

		mMasterHelper.getCurrencyData(Config.GET_CURRENCY_MST + "/"
				+ Config.BRANCH_CODE, null, true);
		mMasterHelper.getUserRights(Config.GET_USER_ASSGND_RGTS + "/"
				+ Config.BRANCH_CODE, null, true);

		mMasterHelper.getVATData(Config.GET_VAT_MST, null, true);
		mMasterHelper.getScanCodeData(Config.GET_SCANCODES_MST, null, true);

		mMasterHelper.getAllUserAuthenticationData(Config.GET_USER_MST, null,
				true);

		mMasterHelper.getSalesManData(Config.GET_SALESMAN_MST, null, true);
		mMasterHelper
				.getRecipeDtlData(Config.GET_PRDCT_RECEIPE_MST, null, true);
		mMasterHelper.getFLXPOSData(Config.GET_FLX_POS, null, true);
		mMasterHelper.getDNMTN_MSTData(Config.GET_DENOMINATION_MST, null, true);
	}

	/**
	 * Method- To fetch prdct image data in background, as soon as images data
	 * gets inserted in DB. starts showing them
	 */
	private void fetchPrdctImageInBack() {
		isImageProgressOn = true;
		// mImageBatchCount = 10;
		// mImagePageIndex = 520;
		MasterHelper mImageMasterHelper = new MasterHelper(this, false);
		mImageMasterHelper.getProducImageData(Config.GET_PRDCT_IMG_MST
				+ mImageBatchCount + "," + mImagePageIndex, null, true);
	}

	/**
	 * Method- To initialize components
	 */
	private void componentInitialisation() {
		mUserInfoText = (TextView) findViewById(R.id.userInfoText);
		mCurrentDateText = (TextView) findViewById(R.id.currentdateText);
		mCmpnyText = (TextView) findViewById(R.id.cmpnyText);

		mOnlineIndicatorIV = (ImageView) findViewById(R.id.online_indicatorIV);

		updateTopInfoBar();

		mBillLefttPanelLayout = (FrameLayout) findViewById(R.id.billLeftFrameLayout);
		mBillRightPanelLayout = (FrameLayout) findViewById(R.id.billRightFrameLayout);
		mBillLefttPanelLinearLayout = (LinearLayout) findViewById(R.id.billLeftPanel);
		mBillRightPanelLinearLayout = (LinearLayout) findViewById(R.id.billRightPanel);
		mDisplayCategoriesLayout = (FrameLayout) findViewById(R.id.displaycategoryFrameLayout);
		mUserOptionLayout = (FrameLayout) findViewById(R.id.userOptionLayout);
		findViewById(R.id.scan_button).setOnClickListener(mOnClickListener);
		findViewById(R.id.search_button).setOnClickListener(mOnClickListener);
		mSearchET = (EditText) findViewById(R.id.searchET);
		// mSearchET.clearFocus();
		((LinearLayout) findViewById(R.id.dummy_id)).requestFocus();

		mScanBtn = (Button) findViewById(R.id.scan_button);
		mScanBtn.setOnClickListener(mOnClickListener);

		mSelectedCategoryLL = (LinearLayout) this
				.findViewById(R.id.selected_refinements_layout);
		mSelectedCategoryLL.setOnClickListener(mOnClickListener);

		// FCMG Fragment
		Fragment userOptionFragment = new UserOptionFragment();
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.add(mUserOptionLayout.getId(), userOptionFragment).commit();

		/*
		 * Thread myThread = null; Runnable runnable = new CountDownRunner();
		 * myThread = new Thread(runnable); myThread.start();
		 */

		resetHomeGridUI();
	}

	public void doWork() {
		runOnUiThread(new Runnable() {
			public void run() {
				try {
					SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
					String time = sdf.format(new Date());
					mCurrentDateText.setText(" Current Time : " + time);
				} catch (Exception e) {
				}
			}
		});
	}

	class CountDownRunner implements Runnable {
		// @Override
		public void run() {
			while (!Thread.currentThread().isInterrupted()) {
				try {
					doWork();
					Thread.sleep(1000 * 60);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				} catch (Exception e) {
				}
			}
		}
	}

	@Override
	protected void onResume() {
		Logger.d(TAG, "onResume");
		super.onResume();
		// mSearchET.clearFocus();

		// if payment is completed then disable all the UI
		if (UserSingleton.getInstance(getContext()).isPaymentDone) {
			setBillFragmentPosition();
			mDisplayCategoriesLayout.setClickable(false);
			mSearchET.setClickable(false);
			mScanBtn.setClickable(false);
			findViewById(R.id.search_button).setClickable(false);
			return;
		} else {
			mDisplayCategoriesLayout.setClickable(true);
			mSearchET.setClickable(true);
			mScanBtn.setClickable(true);
			findViewById(R.id.search_button).setClickable(true);
		}
		setBillFragmentPosition();
		if (UserSingleton.getInstance(this).isNetworkAvailable) {
			mOnlineIndicatorIV.setBackgroundResource(R.drawable.wifi_on);
		} else {
			mOnlineIndicatorIV.setBackgroundResource(R.drawable.wifi_off);
		}
	}

	public void updateTopInfoBar() {
		loadAllConfigData();

		Logger.d(TAG, "updateTopInfoBar");
		USER_MST_Model user = UserSingleton.getInstance(this).mUserDetail;
		String loginTime = UserSingleton.getInstance(this).getUserLoginTime();
		String mstUpdateTime = UserSingleton.getInstance(this)
				.getMstUpdateTime();
		String[] loginData = loginTime.split("\\s+");
		mUserInfoText.setText("User: " + user.getUSR_FULL_NAME()
				+ " | Run Date: " + loginData[0] + " | Login Time: "
				+ loginData[1] + " | Last Update: " + mstUpdateTime);

		mCmpnyText.setText("| " + Config.COMPANY_NAME + " | "
				+ Config.COMPANY_ADDRESS);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		Logger.d(TAG, "onSaveInstanceState");
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		Logger.d(TAG, "onActivityResult");
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == SCAN_INTENT) {
			if (resultCode == SCAN_SUCCESS && data != null) {
				Bundle bundle = data.getBundleExtra(Constants.KEY);
				String value = bundle.getString(Constants.VALUE);
				Logger.d(TAG, "Barcode Value:" + value);
				Toast.makeText(HomeActivity.this, value, Toast.LENGTH_SHORT)
						.show();
				MasterHelper mMasterHelper = new MasterHelper(this, false);
				mMasterHelper.retrieveScanCodesPrdct(value,
						DBConstants.dbo_SCAN_CODS_MST_TABLE, true);
			}
		}
	}

	@Override
	protected void onPostResume() {
		super.onPostResume();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Logger.d(TAG, "onDestroy");
		UserSingleton.getInstance(HomeActivity.this).navigationTagArr = new LinkedList<NavigationModel>();
		UserSingleton.getInstance(HomeActivity.this).isPaymentDone = false;
	}

	@Override
	public void onBackPressed() {
		Logger.d(TAG, "onBackPressed");

		if (UserSingleton.getInstance(this).navigationTagArr != null
				&& UserSingleton.getInstance(this).navigationTagArr.size() > 0) {
			Logger.d(TAG, "Back:No:"
					+ UserSingleton.getInstance(this).navigationTagArr.size());

			if (UserSingleton.getInstance(this).navigationTagArr.size() != 1) {
				UserSingleton.getInstance(this).navigationTagArr.removeLast();
				setCategoryNavigationBar();
			} else {
				Logger.d(TAG, "onBackPressed");
				super.onBackPressed();
				this.finish();
			}
		}
		super.onBackPressed();
	}

	/**
	 * Method- To show navigation flow
	 **/
	private void showNavigationDialog(
			LinkedList<NavigationModel> navigationTagArr) {
		navTypeAlert = new Dialog(HomeActivity.this,
				android.R.style.Theme_Dialog);
		navTypeAlert.requestWindowFeature(Window.FEATURE_NO_TITLE);
		navTypeAlert.setContentView(R.layout.navigation_flow);

		ListView navTypeLV = (ListView) navTypeAlert
				.findViewById(R.id.mNavigationLV);
		navTypeLV.setAdapter(new NavigationFlowAdpter(HomeActivity.this,
				navigationTagArr));
		navTypeLV.setOnItemClickListener(mItemListener);
		navTypeAlert.show();
	}

	/**
	 * Method- To get PRDCT_MST_TBL data from DB
	 * 
	 * @param binLocCode
	 */
	private void retrievePRDCT_MST_Data(String binLocCode) {
		MasterHelper helper = new MasterHelper(this, false);

		LinkedList<String> whrStringArgs = null;
		String[] whrArgs = null;

		/*
		 * if (isMultipleGridSelectionDone) { if
		 * (UserSingleton.getInstance(this).selectionTagArr != null &&
		 * UserSingleton.getInstance(this).selectionTagArr.size() > 0) {
		 * whrStringArgs = new LinkedList<String>();
		 * 
		 * // Iterate leaving Home model, thats why index starts at 1 for (int i
		 * = 1; i < UserSingleton.getInstance(this).selectionTagArr .size();
		 * i++) { NavigationModel navModel =
		 * UserSingleton.getInstance(this).selectionTagArr .get(i);
		 * whrStringArgs.add(navModel.getNavigation_Code()); }
		 * 
		 * whrArgs = new String[whrStringArgs.size()]; whrArgs =
		 * whrStringArgs.toArray(whrArgs); } } else {
		 */
		if (UserSingleton.getInstance(this).navigationTagArr != null
				&& UserSingleton.getInstance(this).navigationTagArr.size() > 0) {
			whrStringArgs = new LinkedList<String>();

			// Iterate leaving Home model, thats why index starts at 1
			for (int i = 1; i < UserSingleton.getInstance(this).navigationTagArr
					.size(); i++) {
				NavigationModel navModel = UserSingleton.getInstance(this).navigationTagArr
						.get(i);
				whrStringArgs.add(navModel.getNavigation_Code());
			}

			whrArgs = new String[whrStringArgs.size()];
			whrArgs = whrStringArgs.toArray(whrArgs);
		}
		// }

		helper.selectPRDCTRecords(whrArgs,
				DBConstants.dbo_TILL_PRDCT_MST_TABLE, true);
	}

	/**
	 * Method- To get PRDCT_DPT_MST_TBL data from DB
	 * 
	 * @param grpCode
	 */
	private void retrieveDPT_MST_Data(String grpCode) {
		MasterHelper helper = new MasterHelper(this, false);
		helper.selectDPTRecords(grpCode, DBConstants.dbo_DPT_MST_TABLE, true);
	}

	/**
	 * Method- To get PRDCT_ANALYS_MST_TBL data from DB
	 * 
	 * @param dptCode
	 */
	private void retrieveANALYS_MST_Data(String dptCode) {

		MasterHelper helper = new MasterHelper(this, false);
		helper.selectANALYSRecords(dptCode, DBConstants.dbo_ANALYS_MST_TABLE,
				true);
	}

	/**
	 * Method- To get PRDCT_BINLOC_MST_TBL data from DB
	 * 
	 * @param analysCode
	 */
	private void retrieveBINLOC_MST_Data(String analysCode) {
		MasterHelper helper = new MasterHelper(this, false);
		helper.selectBINLOCRecords(analysCode,
				DBConstants.dbo_BIN_LOC_MST_TABLE, true);
	}

	/**
	 * Method- To set update grid view with group data
	 * 
	 * @param gRP_Models
	 */
	private void fillGroupDataSet(ArrayList<BaseModel> gRP_Models) {
		Logger.d(TAG, "fillGroupDataSet");

		mFragmentTransaction = getSupportFragmentManager().beginTransaction();
		grpGridFragment = new GridFragment();
		Bundle bundle = new Bundle();
		bundle.putSerializable(GridFragment.LIST, gRP_Models);
		bundle.putSerializable(GridFragment.TYPE, GridFragment.GRP_MODEL);
		grpGridFragment.setArguments(bundle);
		mFragmentTransaction.add(mDisplayCategoriesLayout.getId(),
				grpGridFragment);
		//mFragmentTransaction.commit();
		mFragmentTransaction.commitAllowingStateLoss();

		if (UserSingleton.getInstance(this).navigationTagArr != null
				&& UserSingleton.getInstance(this).navigationTagArr.size() <= 0) {
			// Set up navigation data: Home Tab
			NavigationModel navModel = new NavigationModel();
			navModel.setNavigation_Code(HOME_CODE);
			navModel.setNavigation_Name(HOME_NAME);
			navModel.setNavigation_Type(GridFragment.HOME_MODEL);
			UserSingleton.getInstance(this).navigationTagArr.add(navModel);
			setCategoryNavigationBar();
		}

	}

	/**
	 * Method- To set update grid view with department data
	 * 
	 * @param dPT_Models
	 */
	private void fillDptDataSet(ArrayList<BaseModel> dPT_Models) {
		mFragmentTransaction = getSupportFragmentManager().beginTransaction();
		dptGridFragment = new GridFragment();
		Bundle bundle = new Bundle();
		bundle.putSerializable(GridFragment.LIST, dPT_Models);
		bundle.putSerializable(GridFragment.TYPE, GridFragment.DPT_MODEL);
		dptGridFragment.setArguments(bundle);
		mFragmentTransaction.addToBackStack(GridFragment.DEPARTMENT);
		mFragmentTransaction.add(mDisplayCategoriesLayout.getId(),
				dptGridFragment);
		mFragmentTransaction.commit();
	}

	/**
	 * Method- To set update grid view with analysis data
	 * 
	 * @param aNALYS_Models
	 */
	private void fillAnalysDataSet(ArrayList<BaseModel> aNALYS_Models) {
		mFragmentTransaction = getSupportFragmentManager().beginTransaction();
		analysGridFragment = new GridFragment();
		Bundle bundle = new Bundle();
		bundle.putSerializable(GridFragment.LIST, aNALYS_Models);
		bundle.putSerializable(GridFragment.TYPE, GridFragment.ANALYS_MODEL);
		analysGridFragment.setArguments(bundle);
		mFragmentTransaction.addToBackStack(GridFragment.ANALYS);
		mFragmentTransaction.add(mDisplayCategoriesLayout.getId(),
				analysGridFragment);
		mFragmentTransaction.commit();

	}

	/**
	 * Method- To set update grid view with bin location data
	 * 
	 * @param bINLOC_Models
	 */
	private void fillBinLocDataSet(ArrayList<BaseModel> bINLOC_Models) {
		mFragmentTransaction = getSupportFragmentManager().beginTransaction();
		binLocGridFragment = new GridFragment();
		Bundle bundle = new Bundle();
		bundle.putSerializable(GridFragment.LIST, bINLOC_Models);
		bundle.putSerializable(GridFragment.TYPE, GridFragment.BINLOC_MODEL);
		binLocGridFragment.setArguments(bundle);
		mFragmentTransaction.addToBackStack(GridFragment.BIN_LOCATION);
		mFragmentTransaction.add(mDisplayCategoriesLayout.getId(),
				binLocGridFragment);
		mFragmentTransaction.commit();

	}

	/**
	 * Method- To set update grid view with product data
	 * 
	 * @param pRDCT_Models
	 */
	private void fillPRDCTDataSet(ArrayList<BaseModel> pRDCT_Models) {
		mFragmentTransaction = getSupportFragmentManager().beginTransaction();
		prdctGridFragment = new GridFragment();
		Bundle bundle = new Bundle();
		bundle.putSerializable(GridFragment.LIST, pRDCT_Models);
		bundle.putSerializable(GridFragment.TYPE, GridFragment.PRDCT_MODEL);
		prdctGridFragment.setArguments(bundle);
		mFragmentTransaction.addToBackStack(GridFragment.PRODUCTS);
		mFragmentTransaction.add(mDisplayCategoriesLayout.getId(),
				prdctGridFragment);
		mFragmentTransaction.commit();
	}

	/**
	 * Method- To generate unique transaction no. Also increment running receipt
	 * no.
	 */
	private void generateNewTranactionNo() {
		UserSingleton.getInstance(HomeActivity.this).incrementReceiptCount();
		UserSingleton.getInstance(HomeActivity.this)
				.generateUniqueTransactionNo();
	}

	/**
	 * Methd- To pass data to other fragment via activity
	 */
	@Override
	public void deliverDataToFragement(Bundle bundle) {
		Logger.d(TAG, "deliverDataToFragement");
		if (bundle != null) {
			if (bundle.getString(Constants.BUNDLE_KEY) != null) {// To refresh
																	// grid
				if (bundle.getString(Constants.BUNDLE_KEY).equalsIgnoreCase(
						"Home_grid_refresh")) {
					resetHomeGridUI();
				} else if (bundle.getString(Constants.BUNDLE_KEY)
						.equalsIgnoreCase("Setting_pref")) {
					setBillFragmentPosition();
				}
			} else {
				setBillFragmentPosition();
			}
		}
	}

	private void setBillFragmentPosition() {
		billFragment = new BillFragment();
		FragmentTransaction ft1 = getSupportFragmentManager()
				.beginTransaction();
		if (Config.BILL_PANEL_POSITION == Constants.LEFT) {
			ft1.replace(mBillLefttPanelLayout.getId(), billFragment).commit();
			mBillRightPanelLinearLayout.setVisibility(View.GONE);
			mBillLefttPanelLinearLayout.setVisibility(View.VISIBLE);
		} else if (Config.BILL_PANEL_POSITION == Constants.RIGHT) {
			ft1.replace(mBillRightPanelLayout.getId(), billFragment).commit();
			mBillRightPanelLinearLayout.setVisibility(View.VISIBLE);
			mBillLefttPanelLinearLayout.setVisibility(View.GONE);
		}
		mPreviousBillPanelPosition = Config.BILL_PANEL_POSITION;

		// if payment is completed then enable all the UI
		if (!UserSingleton.getInstance(getContext()).isPaymentDone) {
			mDisplayCategoriesLayout.setClickable(true);
			mSearchET.setClickable(true);
			mScanBtn.setClickable(true);
			findViewById(R.id.search_button).setClickable(true);
			return;
		}
	}

	/**
	 * /** Method- Network/DB Response Handling
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void executePostAction(OperationalResult opResult) {
		Logger.d(
				TAG,
				"excecutePostAction:"
						+ UserSingleton.getInstance(
								MPOSApplication.getContext()).getMStCount());

		// Check whether master data synchronization is complete or not
		if (UserSingleton.getInstance(MPOSApplication.getContext())
				.getMStCount() == MST_SUCCESS_VALUE) {
			UserSingleton.getInstance(HomeActivity.this).mMasterDateCount = 0;

			// Callback after completion of fetching all the master
			// data/Deletion of master data.
			if (isUpdateMasterDB) {
				// Callback after deletion of master data while updating master
				// table to refill with new data.
				if (opResult.getResultCode() == Constants.DELETE_MASTER) {
					updateMasterTableWithNewData();
				} else {
					Logger.d(TAG, "Master update data received");
					deleteMasterTables();
				}
			} else {
				// Load Group Data
				Logger.d(TAG, "Master Synchronized");
				UserSingleton.getInstance(this).storeInSharedPref(true);

				SimpleDateFormat sdf = new SimpleDateFormat(
						Constants.TIME_FORMAT);
				String currentDateandTime = sdf.format(new Date());
				UserSingleton.getInstance(HomeActivity.this)
						.setMstUpdateTimeInPref(currentDateandTime);

				updateTopInfoBar();

				// CAll API call to load all prdct image in background
				isImageProgressOn = true;
				fetchPrdctImageInBack();

				MasterHelper mMasterHelper = new MasterHelper(this, false);
				mMasterHelper.selectGRPRecords("",
						DBConstants.dbo_PRDCT_GRP_MST_TABLE, true);

				mMasterHelper.selectUserRightsFromDB(UserSingleton
						.getInstance(this).mUserDetail.getUSR_GRP_ID(),
						DBConstants.dbo_USR_ASSGND_RGHTS_TABLE, true);
			}

		}

		if (!isFinishing()) {
			switch (opResult.getResultCode()) {

			case Constants.FETCH_PRDCT_RECORDS:
				if (isGridSelection) {
					isGridSelection = false;
					stopProgress();
				}

				mBase_Mst_Models = (ArrayList<BaseModel>) opResult.getResult()
						.getSerializable(Constants.RESULTCODEBEAN);
				if (mBase_Mst_Models != null && mBase_Mst_Models.size() > 0) {
					Logger.d(TAG, "PRDCT_DATA:" + mBase_Mst_Models.toString());
					fillPRDCTDataSet(mBase_Mst_Models);

					// If carry-on selection, update navigation bar
					if (isMultipleGridSelectionDone) {
						isMultipleGridSelectionDone = false;
						handleNavigationTap(0);
					}
				}
				break;

			case Constants.FETCH_GRP_RECORDS:
				System.out.println(" ## FETCH GRP RECORDS ");
				mBase_Mst_Models = (ArrayList<BaseModel>) opResult.getResult()
						.getSerializable(Constants.RESULTCODEBEAN);
				if (mBase_Mst_Models != null && mBase_Mst_Models.size() > 0) {
					Logger.d(TAG, "GRP_DATA:" + mBase_Mst_Models.toString());
					fillGroupDataSet(mBase_Mst_Models);
				}

				if (!isImageProgressOn) {
					stopProgress();
				}
				break;

			case Constants.FETCH_DPT_RECORDS:
				mBase_Mst_Models = (ArrayList<BaseModel>) opResult.getResult()
						.getSerializable(Constants.RESULTCODEBEAN);

				if (mBase_Mst_Models != null && mBase_Mst_Models.size() > 0) {
					Logger.d(TAG, "DPT_DATA:" + mBase_Mst_Models.toString());

					// Only one Record Present,bypass it and goto next record
					// using current records selection by default
					if (mBase_Mst_Models.size() == 1) {
						isDefaultGridSelection = true;
						isMultipleGridSelectionDone = true;
						System.out.println(" ## FETCH DPT RECORDS >>>>");
						PRDCT_DPT_MST_Model dptModel = (PRDCT_DPT_MST_Model) ((PRDCT_DPT_MST_Model) mBase_Mst_Models
								.get(0));
						String dptCode = dptModel.getDpt_code();
						// Set up selection data
						NavigationModel navDptModel = new NavigationModel();
						navDptModel.setNavigation_Code(dptCode);
						navDptModel.setNavigation_Name(dptModel.getDpt_name());
						navDptModel.setNavigation_Type(GridFragment.DPT_MODEL);
						UserSingleton.getInstance(this).navigationTagArr
								.add(navDptModel);

						retrieveANALYS_MST_Data(dptCode);
					} else {
						System.out.println(" ## FETCH DPT RECORDS ####");
						isDefaultGridSelection = false;
						fillDptDataSet(mBase_Mst_Models);
					}
					// isDefaultGridSelection = false;
					// fillDptDataSet(mBase_Mst_Models);
				} else {

					isDefaultGridSelection = false;
					if (UserSingleton.getInstance(this).navigationTagArr != null
							&& UserSingleton.getInstance(this).navigationTagArr
									.size() > 1) {
						UserSingleton.getInstance(this).navigationTagArr
								.removeLast();
						setCategoryNavigationBar();
					}
					showAlertMessage(Constants.ALERT, "No Record present.",
							false);
				}

				if (isGridSelection && !isDefaultGridSelection) {
					isGridSelection = false;
					isDefaultGridSelection = false;
					stopProgress();
				}
				break;

			case Constants.FETCH_ANALYS_RECORDS:
				mBase_Mst_Models = (ArrayList<BaseModel>) opResult.getResult()
						.getSerializable(Constants.RESULTCODEBEAN);
				if (mBase_Mst_Models != null && mBase_Mst_Models.size() > 0) {
					Logger.d(TAG, "ANALYS_DATA:" + mBase_Mst_Models.toString());

					// Only one Record Present,bypass it and goto next record
					// using current records selection by default
					if (mBase_Mst_Models.size() == 1) {
						isDefaultGridSelection = true;
						isMultipleGridSelectionDone = true;

						PRDCT_ANALYS_MST_Model analysModel = (PRDCT_ANALYS_MST_Model) mBase_Mst_Models
								.get(0);
						String analysCode = analysModel.getAnalys_code();
						// Set up selection data
						NavigationModel navANAModel = new NavigationModel();
						navANAModel.setNavigation_Code(analysCode);
						navANAModel.setNavigation_Name(analysModel
								.getAnalys_name());
						navANAModel
								.setNavigation_Type(GridFragment.ANALYS_MODEL);
						UserSingleton.getInstance(this).navigationTagArr
								.add(navANAModel);

						retrieveBINLOC_MST_Data(analysCode);
					} else {
						isDefaultGridSelection = false;
						fillAnalysDataSet(mBase_Mst_Models);
					}
				}

				if (isGridSelection && !isDefaultGridSelection) {
					isGridSelection = false;
					isDefaultGridSelection = false;
					stopProgress();
				}
				break;

			case Constants.FETCH_BINLOC_RECORDS:
				mBase_Mst_Models = (ArrayList<BaseModel>) opResult.getResult()
						.getSerializable(Constants.RESULTCODEBEAN);
				if (mBase_Mst_Models != null && mBase_Mst_Models.size() > 0) {
					Logger.d(TAG, "BINLOC_DATA:" + mBase_Mst_Models.toString());

					// Only one Record Present,bypass it and goto next record
					// using current records selection by default
					if (mBase_Mst_Models.size() == 1) {
						isDefaultGridSelection = true;
						isMultipleGridSelectionDone = true;

						PRDCT_BINLOC_MST_Model binLocModel = (PRDCT_BINLOC_MST_Model) mBase_Mst_Models
								.get(0);
						String binLocCode = binLocModel.getBinloc_code();
						// Set up selection data
						NavigationModel navBINLOCModel = new NavigationModel();
						navBINLOCModel.setNavigation_Code(binLocCode);
						navBINLOCModel.setNavigation_Name(binLocModel
								.getBinloc_name());
						navBINLOCModel
								.setNavigation_Type(GridFragment.BINLOC_MODEL);
						UserSingleton.getInstance(this).navigationTagArr
								.add(navBINLOCModel);

						retrievePRDCT_MST_Data(binLocCode);
					} else {
						isDefaultGridSelection = false;
						fillBinLocDataSet(mBase_Mst_Models);
					}
				}

				if (isGridSelection && !isDefaultGridSelection) {
					isGridSelection = false;
					isDefaultGridSelection = false;
					stopProgress();
				}
				break;

			case Constants.FETCH_USER_RIGHT_RECORD:

				createUserRightModel(opResult);
				break;

			case Constants.FETCH_SINGLE_PRDCT_RECORD:// Scan or Free search
				if (isGridSelection) {
					isGridSelection = false;
					stopProgress();
				}
				ArrayList<BaseModel> prdctModels = (ArrayList<BaseModel>) opResult
						.getResult().getSerializable(Constants.RESULTCODEBEAN);
				if (prdctModels != null && prdctModels.size() > 0) {
					Logger.d(TAG, "ScanData:" + prdctModels.toString());

					// To remove any previous fragments added in View except
					// HOME that's why -1.
					FragmentManager fm = HomeActivity.this
							.getSupportFragmentManager();
					for (int i = 0; i < fm.getBackStackEntryCount() - 1; ++i) {
						Logger.d(TAG, "popBackStack" + i);
						fm.popBackStack();
					}

					fillPRDCTDataSet(prdctModels);
				} else {
					resetHomeGridUI();
					showAlertMessage(Constants.ALERT, "No Record present.",
							false);
				}
				break;

			case Constants.FETCH_SCANCODES_RECORD:
				ArrayList<SCAN_CODE_MST_Model> scanCodeMstDataList = (ArrayList<SCAN_CODE_MST_Model>) opResult
						.getResult().getSerializable(Constants.RESULTCODEBEAN);
				if (scanCodeMstDataList != null
						&& scanCodeMstDataList.size() > 0) {
					// To retrieve product depending on ID returned by scan code
					SCAN_CODE_MST_Model model = scanCodeMstDataList.get(0);
					String prdctCode = model.getPrdct_code();
					retrieveProductbyFreeSearch(prdctCode);
				} else {
					showAlertMessage(Constants.ALERT, "No Record present.",
							false);
				}
				break;

			// Call back after completion of master completion
			case Constants.UPDATE_MASTER_COMPLETE:
				// stopProgress(); //Commented
				SimpleDateFormat sdf = new SimpleDateFormat(
						Constants.DATE_FORMAT);
				String currentDateandTime = sdf.format(new Date());
				UserSingleton.getInstance(HomeActivity.this)
						.setMstUpdateTimeInPref(currentDateandTime);
				updateTopInfoBar();

				// DeleteRecursive(new File(DBConstants.DB_PATH));
				// showAlertMessage(Constants.ALERT, "Master Table updated",
				// false);
				isUpdateMasterDB = false;
				MasterHelper mMasterHelper = new MasterHelper(this, false);
				mMasterHelper.dropAllUPdateMasterTable();
				mMasterHelper.selectGRPRecords("",
						DBConstants.dbo_PRDCT_GRP_MST_TABLE, true);

				mMasterHelper.selectUserRightsFromDB(UserSingleton
						.getInstance(this).mUserDetail.getUSR_GRP_ID(),
						DBConstants.dbo_USR_ASSGND_RGHTS_TABLE, true);

				// CAll API call to load all prdct image in background
				mImagePageIndex = 0;
				fetchPrdctImageInBack();
				break;

			case Constants.GET_PRDCT_IMGES:
				Logger.d(TAG, "GET_PRDCT_IMGES Done");
				ArrayList<PrdctImageMstModel> prdctImgDataList = (ArrayList<PrdctImageMstModel>) opResult
						.getResult().getSerializable(Constants.RESULTCODEBEAN);
				if (prdctImgDataList != null && prdctImgDataList.size() > 0) {
					// To update product image in DB,
					updatePrdctImageInDB(prdctImgDataList);
				}
				break;

			case Constants.UPDATE_PRDCT_IMG_MST_RECORDS:
				Logger.d(
						TAG,
						"UPDATE_PRDCT_IMG_MST_RECORDS Done"
								+ opResult.getRequestResponseCode()
								+ ": No of records: "
								+ UserSingleton.getInstance(HomeActivity.this)
										.getmNoOfRecords());

				mImagePageIndex = mImagePageIndex + 10;// Increment to
				// next

				// Check if image page index reached to total no of products,
				// and decide whether to go ahead or not
				if (isImageProgressOn) {
					if (mImagePageIndex < UserSingleton.getInstance(
							HomeActivity.this).getmNoOfRecords()) {
						Logger.d(TAG, "Fetch Next Images Start:"
								+ mImagePageIndex);
						fetchPrdctImageInBack();// Next page call with updated
												// index
					} else {
						Logger.d(TAG, "Final/Total Master Sync Done");
						MPOSApplication.isProcessingForMasterDb = false;
						isImageProgressOn = false;
						stopProgress();
					}
				}
				break;
			case Constants.MASTER_UPLOAD_ERROR:
				stopProgress();
				break;

			}
		}
	}

	// Update all the master tables from update master db
	private void updateMasterTableWithNewData() {
		Logger.d(TAG, "updateMasterTableWithNewData");
		MasterHelper helper = new MasterHelper(this, false);
		helper.copyGRPMSTTable("", DBConstants.dbo_PRDCT_GRP_MST_TABLE, true);
	}

	// Delete all the master tables while updating DB. And again refill from
	// updated tables
	private void deleteMasterTables() {
		MasterHelper helper = new MasterHelper(this, false);
		helper.deleteGRPMSTTable("", DBConstants.dbo_PRDCT_GRP_MST_TABLE, true);
		helper.deletePRDT_DPT_MSTTable("", DBConstants.dbo_DPT_MST_TABLE, true);
		helper.deleteBINLOC_MSTTable("", DBConstants.dbo_BIN_LOC_MST_TABLE,
				true);
		helper.deleteANALYS_MSTTable("", DBConstants.dbo_ANALYS_MST_TABLE, true);
		helper.deletePRDCT_MST_MSTTable("",
				DBConstants.dbo_TILL_PRDCT_MST_TABLE, true);
		helper.deleteUOM_MSTTable("", DBConstants.dbo_UOM_TABLE, true);
		helper.deleteUOMSLAB_MSTTable("", DBConstants.dbo_UOM_SLAB_MST_TABLE,
				true);
		helper.deleteCurrency_MSTTable("", DBConstants.dbo_Currency_Mst_TABLE,
				true);
		helper.deletePrdctInstr_MSTTable("",
				DBConstants.dbo_PRDCT_INSTRCTN_MST_TABLE, true);

		helper.deleteUSER_ASSGND_RGHTS_MSTTable("",
				DBConstants.dbo_USR_ASSGND_RGHTS_TABLE, true);
		helper.deleteVAT_DATA_MSTTable("", DBConstants.dbo_VAT_MST_TABLE, true);
		helper.deleteSCAN_CODES_MSTTable("",
				DBConstants.dbo_SCAN_CODS_MST_TABLE, true);
		helper.deleteUSER_MSTTable("", DBConstants.dbo_USER_MST_TABLE, true);
		helper.deleteRCEPI_DTLTable("",
				DBConstants.dbo_PRDCT_RECEIPE_MST_TABLE, true);
		helper.deleteSLMN_MSTTable("", DBConstants.dbo_SLMN_MST_TABLE, true);
		helper.deleteflxPOSTable("", DBConstants.dbo_FLX_POS_MST_TABLE, true);
		helper.deleteDNMTN_MSTTable("", DBConstants.dbo_DNMTN_MST_TABLE, true);
		helper.deleteINstruc_MSTTable("", DBConstants.dbo_INSTRCTN_MST_TABLE,
				true);
	}

	@SuppressWarnings("unchecked")
	private void createUserRightModel(OperationalResult opResult) {
		ArrayList<USR_ASSGND_RGHTSModel> mUser_Assgnd_Rghts_Models = (ArrayList<USR_ASSGND_RGHTSModel>) opResult
				.getResult().getSerializable(Constants.RESULTCODEBEAN);
		Log.v("userrightsmodel", ":" + mUser_Assgnd_Rghts_Models);
		UserRightsModel userRights = new UserRightsModel();
		UserSingleton.getInstance(this).mUserAssgndRightsModel = userRights
				.createUserRIghts(mUser_Assgnd_Rghts_Models);
	}

	/**
	 * Method- To receive error if any
	 */
	@Override
	public void errorReceived(OperationalResult opResult) {
		stopProgress();
		Logger.d(TAG, "errorReceived");
		switch (opResult.getRequestResponseCode()) {

		case OperationalResult.MASTER_ERROR:
			stopProgress();
			showAlertMessage(Constants.ALERT,
					"Master Update Failed. Please try Again.", false);
			// Clear all added DB data
			this.deleteDatabase(DBConstants.DATABASE_NAME);
			break;

		case OperationalResult.DB_ERROR:
			Logger.d(TAG, "DB_ERROR");
			if (opResult.getResultCode() == Constants.FETCH_SINGLE_PRDCT_RECORD) {
				showAlertMessage(Constants.ALERT, "No Record present.", false);
			}

			if (opResult.getResultCode() == Constants.FETCH_PRDCT_RECORDS) {
				showAlertMessage(Constants.ALERT, "No Record present.", false);
				moveToHome();
				setCategoryNavigationBar();
			}

			if (UserSingleton.getInstance(this).navigationTagArr != null
					&& UserSingleton.getInstance(this).navigationTagArr.size() > 1) {

				UserSingleton.getInstance(this).navigationTagArr.removeLast();
				setCategoryNavigationBar();
			}

			break;

		default:
			if (opResult.getResultCode() == Constants.GET_PRDCT_IMGES) {
				Logger.d(TAG, "GET_PRDCT_IMGES:Error");
			}
			break;
		}
	}

	public void moveToHome() {
		Log.d(TAG, "## CHECKING THIS HOW COME ");
		int i = 0;
		if (UserSingleton.getInstance(this).navigationTagArr.size() > 1) {
			for (i = 0; i < UserSingleton.getInstance(this).navigationTagArr
					.size(); i++) {
				if (UserSingleton.getInstance(this).navigationTagArr.size() > 1) {
					UserSingleton.getInstance(this).navigationTagArr
							.removeLast();
				}
				Log.d(TAG,
						" REMOVING :: SIZE "
								+ UserSingleton.getInstance(this).navigationTagArr
										.size());

			}

		}
	}

	/**
	 * Method- To retrieve selected model and show next level n Grid View
	 * 
	 * @param model
	 */
	@Override
	public void gridItemSelected(int kind, BaseModel model) {
		System.out.println("## TAB SELECTED AGAIN!!!");
		mSearchET.clearFocus();
		switch (kind) {
		case GridFragment.GRP_MODEL:
			isGridSelection = true;
			startProgress();

			PRDCT_GRP_MST_Model grpModel = (PRDCT_GRP_MST_Model) model;
			String grpCode = grpModel.getGrp_code();

			// Set up navigation data: Group
			NavigationModel navModel = new NavigationModel();
			navModel.setNavigation_Code(grpCode);
			navModel.setNavigation_Name(grpModel.getGrp_name());
			navModel.setNavigation_Type(GridFragment.GRP_MODEL);
			UserSingleton.getInstance(this).navigationTagArr.add(navModel);
			UserSingleton.getInstance(this).selectionTagArr.add(navModel);

			// get department respective to selected group code
			retrieveDPT_MST_Data(grpCode);
			break;

		case GridFragment.DPT_MODEL:
			isGridSelection = true;
			startProgress();

			PRDCT_DPT_MST_Model dptModel = (PRDCT_DPT_MST_Model) model;
			String dptCode = dptModel.getDpt_code();

			// Set up navigation data
			NavigationModel navDptModel = new NavigationModel();
			navDptModel.setNavigation_Code(dptCode);
			navDptModel.setNavigation_Name(dptModel.getDpt_name());
			navDptModel.setNavigation_Type(GridFragment.DPT_MODEL);
			UserSingleton.getInstance(this).navigationTagArr.add(navDptModel);

			// get analysis respective to selected department code
			retrieveANALYS_MST_Data(dptCode);
			break;

		case GridFragment.ANALYS_MODEL:
			isGridSelection = true;
			startProgress();

			PRDCT_ANALYS_MST_Model analysModel = (PRDCT_ANALYS_MST_Model) model;
			String analysCode = analysModel.getAnalys_code();

			// Set up navigation data
			NavigationModel navANAModel = new NavigationModel();
			navANAModel.setNavigation_Code(analysCode);
			navANAModel.setNavigation_Name(analysModel.getAnalys_name());
			navANAModel.setNavigation_Type(GridFragment.ANALYS_MODEL);
			UserSingleton.getInstance(this).navigationTagArr.add(navANAModel);

			// get binlocation respective to selected analysis code
			retrieveBINLOC_MST_Data(analysCode);
			break;

		case GridFragment.BINLOC_MODEL:
			isGridSelection = true;
			startProgress();

			PRDCT_BINLOC_MST_Model binLocModel = (PRDCT_BINLOC_MST_Model) model;
			String binLocCode = binLocModel.getBinloc_code();

			// Set up navigation data
			NavigationModel navBINLOCModel = new NavigationModel();
			navBINLOCModel.setNavigation_Code(binLocCode);
			navBINLOCModel.setNavigation_Name(binLocModel.getBinloc_name());
			navBINLOCModel.setNavigation_Type(GridFragment.BINLOC_MODEL);
			UserSingleton.getInstance(this).navigationTagArr
					.add(navBINLOCModel);

			// get binlocation respective to selected analysis code
			retrievePRDCT_MST_Data(binLocCode);
			break;

		case GridFragment.PRDCT_MODEL:
			PRDCT_MST_Model prdctMstModel = (PRDCT_MST_Model) model;
			//
			handlePrdctTapScenario(prdctMstModel);
			break;

		default:
			break;
		}
		setCategoryNavigationBar();
	}

	/**
	 * Method - To set top navigation bar
	 */
	private void setCategoryNavigationBar() {
		Logger.d(
				TAG,
				"setCategoryNavigationBar"
						+ UserSingleton.getInstance(HomeActivity.this).navigationTagArr
								.size());

		int cnt = 0;
		LinearLayout navigationBar = (LinearLayout) findViewById(R.id.categoryNavBar);
		navigationBar.removeAllViews();

		for (NavigationModel navModel : UserSingleton
				.getInstance(HomeActivity.this).navigationTagArr) {
			TextView navText = (TextView) LayoutInflater.from(this).inflate(
					R.layout.categorynavigation, null);

			Logger.d(TAG, "Navmap " + navModel.toString());
			navText.setTextColor(getResources().getColor(R.color.Black));
			navText.setTypeface(Typeface.DEFAULT_BOLD);
			navText.setText(navModel.getNavigation_Name());
			// navText.setId(Integer.parseInt(navModel.getNavigation_Code()));
			navText.setTag(navModel.getNavigation_Type());

			navText.setOnClickListener(new OnNavigationClickListener(cnt,
					navModel));

			TextView separator = new TextView(this);
			/*
			 * TextView separator = (TextView)
			 * LayoutInflater.from(this).inflate( R.layout.categorynavigation,
			 * null);
			 */
			separator.setTextColor(getResources().getColor(R.color.Black));
			separator.setTypeface(Typeface.DEFAULT_BOLD);
			separator.setText(" \u27A4 ");
			if (cnt > 0) {
				navigationBar.addView(separator);
			}
			navigationBar.addView(navText);
			cnt++;
		}
	}

	class OnNavigationClickListener implements OnClickListener {

		public OnNavigationClickListener(int position, NavigationModel navModel) {
		}

		@Override
		public void onClick(View view) {
			if (UserSingleton.getInstance(getContext()).isPaymentDone) {
				return;
			}
			int position = (Integer) view.getTag();
			Logger.d(TAG, "Index Selected:" + position);

			if (view.getTag().equals(GridFragment.HOME_MODEL)) {
				// Reset home grid
				resetHomeGridUI();
			} else if (view.getTag().equals(GridFragment.GRP_MODEL)) {
				handleNavigationTap(position);
				if (prdctGridFragment != null) {
					getSupportFragmentManager().beginTransaction()
							.remove(prdctGridFragment).commit();
				}
				if (binLocGridFragment != null) {
					getSupportFragmentManager().beginTransaction()
							.remove(binLocGridFragment).commit();
				}
				if (analysGridFragment != null) {
					getSupportFragmentManager().beginTransaction()
							.remove(analysGridFragment).commit();
				}
			} else if (view.getTag().equals(GridFragment.DPT_MODEL)) {
				handleNavigationTap(position);
				if (prdctGridFragment != null) {
					getSupportFragmentManager().beginTransaction()
							.remove(prdctGridFragment).commit();
				}
				if (binLocGridFragment != null) {
					getSupportFragmentManager().beginTransaction()
							.remove(binLocGridFragment).commit();
				}
			} else if (view.getTag().equals(GridFragment.ANALYS_MODEL)) {
				handleNavigationTap(position);
				if (prdctGridFragment != null) {
					getSupportFragmentManager().beginTransaction()
							.remove(prdctGridFragment).commit();
				}
			} else if (view.getTag().equals(GridFragment.BINLOC_MODEL)) {
				handleNavigationTap(position);
			}
		}
	}

	/**
	 * MEthod- To refresh Home UI for Grid
	 */
	private void resetHomeGridUI() {
		// reset top navigation bar
		handleNavigationTap(0);

		// Code for free search multiple prdct fragments add issue
		// resolution
		// To remove any previous fragments added in View except HOME
		FragmentManager fm = HomeActivity.this.getSupportFragmentManager();
		for (int i = 0; i < fm.getBackStackEntryCount(); i++) {
			fm.popBackStack();
		}

		// Remove All Fragments
		if (prdctGridFragment != null) {
			getSupportFragmentManager().beginTransaction()
					.remove(prdctGridFragment).commit();
		}

		if (binLocGridFragment != null) {
			getSupportFragmentManager().beginTransaction()
					.remove(binLocGridFragment).commit();
		}

		if (analysGridFragment != null) {
			getSupportFragmentManager().beginTransaction()
					.remove(analysGridFragment).commit();
		}

		if (dptGridFragment != null) {
			getSupportFragmentManager().beginTransaction()
					.remove(dptGridFragment).commit();
		}

		Logger.d(TAG, "Grid Scroll to top");
		if (grpGridFragment != null)
			((GridFragment) grpGridFragment).scrollToTop();

		// Enable clicks
		mDisplayCategoriesLayout.setClickable(true);
		mSearchET.setClickable(true);
		mScanBtn.setClickable(true);
		findViewById(R.id.search_button).setClickable(true);
		((LinearLayout) findViewById(R.id.dummy_id)).requestFocus();
		// mSearchET.clearFocus();
	}

	/**
	 * Method- To remove respective navigation models from singleton data list
	 * and update UI
	 * 
	 * @param level
	 * @param position
	 * 
	 */
	private void handleNavigationTap(int position) {
		// Remove from NavigationDataList
		int size = UserSingleton.getInstance(HomeActivity.this).navigationTagArr
				.size();
		Logger.d(TAG, "handleNavigationTap:" + position + "Size:" + size);

		if (position == 0) {
			UserSingleton.getInstance(HomeActivity.this).navigationTagArr = new LinkedList<NavigationModel>();
			// Set up navigation data
			NavigationModel navModel = new NavigationModel();
			navModel.setNavigation_Code(HOME_CODE);
			navModel.setNavigation_Name(HOME_NAME);
			navModel.setNavigation_Type(GridFragment.HOME_MODEL);
			UserSingleton.getInstance(this).navigationTagArr.add(navModel);
		} else {
			for (int i = size - 1; i > position; i--) {
				Logger.d(TAG, "for inside:" + i);
				try {
					UserSingleton.getInstance(HomeActivity.this).navigationTagArr
							.remove(i);
				} catch (ArrayIndexOutOfBoundsException e) {
					// TODO: handle exception
					Logger.d(TAG, "Handled Exception: " + e.getMessage());
				}
			}
		}

		setCategoryNavigationBar();
	}

	/**
	 * Method- To handle all business logic on tap of product item
	 * 
	 * @param prdctMstModel
	 */
	private void handlePrdctTapScenario(PRDCT_MST_Model prdctMstModel) {

		// Check whether product has Fix_QTY Flag Y, Open dialog to select QTY
		if (prdctMstModel.getPRDCT_FIX_QTY().equalsIgnoreCase(YES)) {
			// Open Selection Dialog
			FragmentManager fm = this.getSupportFragmentManager();
			PrdctQtyPrceMesrInstrFragment prceMesrInstrFragment = new PrdctQtyPrceMesrInstrFragment();
			Bundle bundle = new Bundle();
			bundle.putSerializable(Constants.KEY, prdctMstModel);
			prceMesrInstrFragment.setArguments(bundle);
			prceMesrInstrFragment.setTargetFragment(billFragment,
					PrdctQtyPrceMesrInstrFragment.BILL_DATA);
			prceMesrInstrFragment.show(fm, QTY_PRCE_MESR_INSTR_FRAGMENT);
		} else { // No Dialog

		}
	}

	public void showAlertMessage(String szAlert, String szMessage) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
		builder.setCancelable(false)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						finish();
					}
				}).setMessage(szMessage).setTitle(szAlert);
		AlertDialog alert = builder.create();
		alert.setCancelable(false);
		alert.show();
	}

	public void callBackForcheckUserAccess(String message, boolean showDialog) {
		if (showDialog) {
			showUserAccessMessage("", message);
		}
	}

	public void showUserAccessMessage(String szAlert, String szMessage) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
		builder.setCancelable(false)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				}).setMessage(szMessage).setTitle(szAlert);
		AlertDialog alert = builder.create();
		alert.setCancelable(false);
		alert.show();

	}

	/**
	 * Method- To listen to network change
	 */
	@Override
	public void networkChangeListener(boolean isNetworkAvailable) {
		Logger.d(TAG, "networkChangeListener");
		if (isNetworkAvailable) {// Available
			// Network Changes for Home Activity
			mOnlineIndicatorIV.setBackgroundResource(R.drawable.wifi_on);
		} else {// Not Available
			// Network Changes for Home Activity
			mOnlineIndicatorIV.setBackgroundResource(R.drawable.wifi_off);
		}
	}

	@Override
	public void userAuthenticationSuccess(UserRightsModel userRights) {
		// TODO Auto-generated method stub

	}

	@Override
	public void userAuthenticationError() {
		// TODO Auto-generated method stub

	}

}
