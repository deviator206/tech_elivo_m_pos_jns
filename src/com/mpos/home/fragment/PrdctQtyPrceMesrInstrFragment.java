/*
 * Copyright (C) 2013 jaggarnaut-studios Pvt. Ltd. All Rights Reserved.
 * jaggarnaut-studios pvt. ltd. PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.mpos.home.fragment;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.mpos.R;
import com.mpos.application.BaseActivity;
import com.mpos.application.MPOSApplication;
import com.mpos.framework.Util.UserSingleton;
import com.mpos.framework.common.Config;
import com.mpos.framework.common.Constants;
import com.mpos.framework.common.DBConstants;
import com.mpos.framework.common.Logger;
import com.mpos.framework.common.OperationalResult;
import com.mpos.framework.inf.BaseResponseListener;
import com.mpos.home.helper.HomeHelper;
import com.mpos.home.model.BillTransactionModel;
import com.mpos.home.model.Company_PolicyModel;
import com.mpos.login.helper.LoginHelper;
import com.mpos.master.model.BILL_Mst_Model;
import com.mpos.master.model.INSTRCTN_MST_Model;
import com.mpos.master.model.PRDCT_INSTRC_MST_Model;
import com.mpos.master.model.PRDCT_MST_Model;
import com.mpos.master.model.UOM_MST_Model;
import com.mpos.master.model.UOM_SLAB_MST_MODEL;
import com.mpos.master.model.UserRightsModel;
import com.mpos.master.model.VAT_MST_Model;
import com.mpos.transactions.model.Bill_INSTRCTN_TXN_Model;
import com.mpos.transactions.model.PRDCT_CNSMPTN_TXNModel;
import com.mpos.transactions.model.PRDCT_RECIPE_DTL_TXN_Model;

/**
 * Description-
 * 
 * @author jaggarnaut-studios
 */

public class PrdctQtyPrceMesrInstrFragment extends DialogFragment implements
		BaseResponseListener {

	public static final String TAG = Constants.APP_TAG
			+ PrdctQtyPrceMesrInstrFragment.class.getSimpleName();

	// Android Variables
	private Button mDoneBtn = null;
	private Button mCancelBtn = null;
	private EditText mQtyEt = null;
	private EditText mPriceEt = null;
	private EditText mExistDescEt = null;
	private EditText mExtraInstrcEt = null;
	private TextView mMeasureHeadingTV = null;
	private TextView mModifierHeadingTV = null;
	private LinearLayout mMeasureLL = null;
	private LinearLayout mModifierLL = null;
	private ArrayList<EditText> mModifierQtyEtList = null;
	private ArrayList<Button> mModifierBtnList = null;
	private ArrayList<Button> mMeasureBtnList = null;

	// Java Variables
	public static int BILL_DATA = 007;
	public static int BILL_SUCCESS = 10;
	private final String ALERT_FRAGMENT = "ALERT_FRAGMENT";
	private final String ALLOW_SELL_BWL_COST_PRCE = "ALLOW TO SELL BELOW CP";
	private final String PROCESSED = "P";
	private int mDataCount = 0;
	private double mTotalAmt = 0.0;
	private boolean isUpdateNeeded = false;
	private boolean isSellBelowCostPriceAllowed = false;
	private int mSelectedModifierIndex = -1;
	private int mSelectedMeasureIndex = -1;
	private View view;
	public static String typeOfAction;
	private HomeHelper mHomeHelper = null;
	private PRDCT_MST_Model mPrdct_MST_Model = null;
	private ArrayList<UOM_SLAB_MST_MODEL> mUOM_SLAB_ModelDataList = null;
	private ArrayList<UOM_MST_Model> mUOM_ModelDataList = null;
	private ArrayList<INSTRCTN_MST_Model> mINSTRCTNDataList = null;
	private ArrayList<PRDCT_INSTRC_MST_Model> mPrdct_Instrc_Models = null;
	private ArrayList<INSTRCTN_MST_Model> mAvailModifierArr = null;

	private ArrayList<PRDCT_RECIPE_DTL_TXN_Model> mPrdct_recipe_dtl_Models = null;

	private VAT_MST_Model mPrdct_Vat_Model = null;
	private BILL_Mst_Model mBill_Mst_Model = null;
	private BillTransactionModel mBill_Txn_Model = null;
	private Bill_INSTRCTN_TXN_Model mBill_INSTRCTN_Txn_Model = null;
	private ArrayList<BillTransactionModel> mBillTransactionModels = null;

	private OnClickListener mClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Boolean mInvalidPrice = false;
			InputMethodManager inputManager = (InputMethodManager) getActivity()
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			if (getActivity().getCurrentFocus() != null
					&& getActivity().getCurrentFocus().getWindowToken() != null) {
				inputManager.hideSoftInputFromWindow(getActivity()
						.getCurrentFocus().getWindowToken(),
						InputMethodManager.HIDE_NOT_ALWAYS);
			}

			switch (v.getId()) {
			case R.id.cancelBtn:
				if (getDialog().isShowing()) {
					getDialog().dismiss();
				}
				break;

			case R.id.doneBtn:
				boolean isGoAheadFlag = true;

				if (mPriceEt.getText().toString().trim().length() == 0) {
					mInvalidPrice = true;
					isGoAheadFlag = false;
					showAlertDialog(getResources().getString(
							R.string.alert_invalid_price));
				}
				if (mPriceEt.isEnabled() == true && !mInvalidPrice) {
					double amtEntered = Double.parseDouble(mPriceEt.getText()
							.toString().trim());
					double costPrice = mPrdct_MST_Model.getPRDCT_COST_PRCE();
					// CHK whether amount entered is below cost price
					if (amtEntered < costPrice) {
						// CHK whether sell below cost price is allowed
						if (isSellBelowCostPriceAllowed) {// Allowed
							// Do nothing, allow to process further
							isGoAheadFlag = true;
						} else {// Not allowed
							isGoAheadFlag = false;
							showAlertDialog(getResources().getString(
									R.string.alert_sell_below_cost));
						}
					}
				}

				if (isGoAheadFlag) {
					// If any measures are present, check whether at lease one
					// is
					// selected
					if (mUOM_ModelDataList != null
							&& mUOM_ModelDataList.size() > 0) {
						if (mSelectedMeasureIndex != -1) {
							isGoAheadFlag = true;
						} else {
							isGoAheadFlag = false;
							showAlertDialog(getResources().getString(
									R.string.alert_invalid_measure));
						}
					}
				}

				if (mQtyEt.getText().toString().contains(".")
						&& mQtyEt.getText().toString().trim().length() == 1) {
					isGoAheadFlag = false;
					showAlertDialog(getResources().getString(
							R.string.alert_wrong_qty));

				}

				// If previous check is true, then only process further
				if (isGoAheadFlag) {
					double userEnteredQty = 0;

					if (mQtyEt.getText().toString().trim().length() > 0) {
						userEnteredQty = Double.parseDouble(mQtyEt.getText()
								.toString());
					}

					if (mPrdct_MST_Model.getPRDCT_FIX_QTY().equalsIgnoreCase(
							"Y")
							&& userEnteredQty < mPrdct_MST_Model.getPRDCT_QTY()) {

						if (UserSingleton.getInstance(getContext()).mUserAssgndRightsModel != null) {

							if (!UserSingleton.getInstance(getContext()).mUserAssgndRightsModel.QUANTITY_CHANGE_FLAG) {
								typeOfAction = "quantity change";
								UserAccessFragment user = new UserAccessFragment();
								user.setTargetFragment(
										PrdctQtyPrceMesrInstrFragment.this,
										UserAccessFragment.USER_ACCESS);
								user.show(getActivity()
										.getSupportFragmentManager(),
										"user-fragmet");
							}
						}
					} else {
						validateInputs();
					}

					// Final validations for quantity and
					// Make entry in Bill_MST.Bill_TXN and BIll_Instrctn
					// and CNSMPTN_TXN Tables
					// for this product transaction
					// validateInputs();
					
				}
				break;

			case R.id.measureBtn: // Measure onClick
				int selectedPosition = (Integer) v.getTag();
				if (selectedPosition >= 0) {
					Logger.d(TAG, ""
							+ mUOM_ModelDataList.get(selectedPosition)
									.toString());

					mSelectedMeasureIndex = selectedPosition;// set measure
																// index for
																// future use

					for (Button btn : mMeasureBtnList) {
						btn.setBackgroundResource(R.drawable.ok);
					}
					(mMeasureBtnList.get(mSelectedMeasureIndex))
							.setBackgroundResource(R.drawable.ok_tap);

					/*
					 * for (Button btn : mMeasureBtnList) {
					 * btn.setBackgroundResource(R.color.grey); }
					 */
					// (mMeasureBtnList.get(mSelectedMeasureIndex)).setSelected(true);
					// (mMeasureBtnList.get(mSelectedMeasureIndex)).setPressed(true);
					/*
					 * (mMeasureBtnList.get(mSelectedMeasureIndex))
					 * .setBackgroundResource(R.color.green);
					 */

					// update the PACK field of Bill_TXN table
					mBill_Txn_Model.setPACK(mUOM_ModelDataList.get(
							selectedPosition).getUom_code());
				}
				break;

			case R.id.modifierBtn: // Modifier onClick
				int position = (Integer) v.getTag();
				if (position >= 0) {
					Logger.d(TAG, "data:"
							+ mAvailModifierArr.get(position).toString());
					mSelectedModifierIndex = position;// set index for future
														// use
					for (Button btn : mModifierBtnList) {
						btn.setBackgroundResource(R.drawable.ok);
					}
					(mModifierBtnList.get(position))
							.setBackgroundResource(R.drawable.ok_tap);
				}
				break;

			default:
				break;
			}
		}
	};

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		Logger.d(TAG, "onActivityResult");
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == UserAccessFragment.USER_ACCESS) {
			if (resultCode == UserAccessFragment.USER_SUCCESS && data != null) {
				UserRightsModel userRights = (UserRightsModel) data
						.getSerializableExtra(Constants.KEY);

				checkUserRightsAndTakeRespectiveAction(userRights);
			} else if (resultCode == UserAccessFragment.USER_ERROR) {
				// mPriceEt.clearFocus();
				// mQtyEt.clearFocus();
				((LinearLayout) view.findViewById(R.id.dummy_id))
						.requestFocus();
			}
		}
	};

	private void checkUserRightsAndTakeRespectiveAction(
			UserRightsModel userRights) {
		if (PrdctQtyPrceMesrInstrFragment.typeOfAction
				.equalsIgnoreCase("quantity change")) {
			if (userRights != null && !userRights.QUANTITY_CHANGE_FLAG) {
				showAlertDialog("This User doesn't have access to change quantity.");
				mPriceEt.clearFocus();
				mQtyEt.clearFocus();
			} else {

				if (mQtyEt.getText().toString().trim().length() > 0) {
					validateInputs();
				}

			}
			// mQtyEt.clearFocus();
		} else if (PrdctQtyPrceMesrInstrFragment.typeOfAction
				.equalsIgnoreCase("price change")) {
			if (userRights != null && !userRights.alllow_PRICE_CHANGE) {
				showAlertDialog("This User doesn't have access to change price.");
				mPriceEt.clearFocus();
				mQtyEt.clearFocus();
			} else {

			}

		}
	}

	/**
	 * Method- To show alert dialog
	 */
	private void showAlertDialog(String message) {
		FragmentManager fm = getActivity().getSupportFragmentManager();
		AlertDialogFragment alertDialogFragment = new AlertDialogFragment();
		Bundle bundle = new Bundle();
		bundle.putString(Constants.KEY, message);
		alertDialogFragment.setArguments(bundle);
		alertDialogFragment.show(fm, ALERT_FRAGMENT);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.qty_prce_msr_instr_fragment, null);

		getDialog().setTitle(getResources().getString(R.string.item_details));
		getDialog().setCanceledOnTouchOutside(false);

		// component initialisation
		((LinearLayout) view.findViewById(R.id.dummy_id)).requestFocus();
		mCancelBtn = (Button) view.findViewById(R.id.cancelBtn);
		mDoneBtn = (Button) view.findViewById(R.id.doneBtn);
		mCancelBtn.setOnClickListener(mClickListener);
		mDoneBtn.setOnClickListener(mClickListener);

		mQtyEt = (EditText) view.findViewById(R.id.qty_et);
		mPriceEt = (EditText) view.findViewById(R.id.price_et);
		mExistDescEt = (EditText) view.findViewById(R.id.desc_et);
		mExtraInstrcEt = (EditText) view.findViewById(R.id.extra_instr_et);

		mMeasureHeadingTV = (TextView) view
				.findViewById(R.id.mesure_heading_tv);
		mModifierHeadingTV = (TextView) view
				.findViewById(R.id.modifier_heading_tv);

		mMeasureLL = (LinearLayout) view.findViewById(R.id.measuerLL);
		mModifierLL = (LinearLayout) view.findViewById(R.id.modifierLL);

		mHomeHelper = new HomeHelper(this);

		Bundle bundle = getArguments();
		mPrdct_MST_Model = (PRDCT_MST_Model) bundle
				.getSerializable(Constants.KEY);

		mPriceEt.setText("" + mPrdct_MST_Model.getPRDCT_SELL_PRCE());
		mExistDescEt.setText(mPrdct_MST_Model.getPRDCT_SHRT_DSCRPTN());

		mBill_Txn_Model = new BillTransactionModel();

		mQtyEt.setText("1");
		// Check for product fix quantity
		if (mPrdct_MST_Model.getPRDCT_FIX_QTY().equalsIgnoreCase("Y")) {
			mQtyEt.setEnabled(true);

			mQtyEt.setOnFocusChangeListener(new OnFocusChangeListener() {

				@Override
				public void onFocusChange(View v, boolean hasFocus) {
					if (v.isFocused()) {
					}
				}
			});
		} else {
			mQtyEt.setEnabled(false);
		}

		// check for product fix price
		if (mPrdct_MST_Model.getPRDCT_FIXED_PRCE() != null
				&& mPrdct_MST_Model.getPRDCT_FIXED_PRCE().equalsIgnoreCase(
						"true")) {
			mPriceEt.setEnabled(true);
			mPriceEt.setOnFocusChangeListener(new OnFocusChangeListener() {

				@Override
				public void onFocusChange(View v, boolean hasFocus) {
					if (v.isFocused()) {
						if (UserSingleton.getInstance(getContext()).mUserAssgndRightsModel != null) {

							if (!UserSingleton.getInstance(getContext()).mUserAssgndRightsModel.alllow_PRICE_CHANGE) {
								typeOfAction = "price change";
								UserAccessFragment user = new UserAccessFragment();
								user.setTargetFragment(
										PrdctQtyPrceMesrInstrFragment.this,
										UserAccessFragment.USER_ACCESS);
								user.show(getActivity()
										.getSupportFragmentManager(),
										"user-fragmet");
							}
						}
					}
				}
			});

			// Allow Description Change
			mExistDescEt.setEnabled(true);
			mExistDescEt.setOnFocusChangeListener(new OnFocusChangeListener() {

				@Override
				public void onFocusChange(View v, boolean hasFocus) {
					if (v.isFocused()) {
						if (UserSingleton.getInstance(getContext()).mUserAssgndRightsModel != null) {

							if (!UserSingleton.getInstance(getContext()).mUserAssgndRightsModel.alllow_PRICE_CHANGE) {
								typeOfAction = "price change";
								UserAccessFragment user = new UserAccessFragment();
								user.setTargetFragment(
										PrdctQtyPrceMesrInstrFragment.this,
										UserAccessFragment.USER_ACCESS);
								user.show(getActivity()
										.getSupportFragmentManager(),
										"user-fragmet");
							}
						}
					}
				}
			});

		} else {
			mPriceEt.setEnabled(false);
			mExistDescEt.setEnabled(false);
		}

		retrieveVatMstDBData();
		retrieveUOM_SLABDBData();

		// Fetch Modifiers only if product has processing
		String typeOfItem = mPrdct_MST_Model.getPRDCT_CP_VLTN();
		if (typeOfItem.contains(PROCESSED)) {
			Logger.d(TAG, "Processed Item:search");
			mBill_INSTRCTN_Txn_Model = new Bill_INSTRCTN_TXN_Model();
			retrieveModifierDBData();
		} else {
			// For Now Load dummy data
			// loadDummyModifierData();
			mModifierHeadingTV.setVisibility(View.GONE);
			mExtraInstrcEt.setVisibility(View.GONE);
		}

		// Chk whether sell below cost price is allowed
		checkPriceSellBelow();

		return view;
	}

	/**
	 * Method- To validate all business constraints satisfied
	 */
	protected void validateInputs() {
		boolean isValidQTY = false;
		if (mQtyEt.getText().toString().trim().length() > 0) {
			isValidQTY = checkPrdctMinQty(Double.parseDouble(mQtyEt.getText()
					.toString().trim()));
		}

		// If valid QTY,dismiss dialog and go for next check
		if (isValidQTY) {

			// Make entry in Bill_MST Table for this product transaction
			retrieveExistingMstRecord();

			// If items is processed item, retrieve all ingredients information
			// from PRDCT_RECIPE_DTL and insert it into CNSMPTN_TXN
			String typeOfItem = mPrdct_MST_Model.getPRDCT_CP_VLTN();
			if (typeOfItem.contains(PROCESSED)) {
				// saveModifierDataInBillInstrcTxn();
				retrievePrdctIngredients(mPrdct_MST_Model.getPRDCT_CODE());
			}
			
			if (getDialog().isShowing()) {
				getDialog().dismiss();
			}
			
		} else {
			// If not, Open Alert Dialog and show alert
			showAlertDialog();
		}
	}

	/**
	 * Method- To show alert dialog
	 */
	private void showAlertDialog() {
		FragmentManager fm = getActivity().getSupportFragmentManager();
		AlertDialogFragment alertDialogFragment = new AlertDialogFragment();
		Bundle bundle = new Bundle();
		bundle.putString(Constants.KEY,
				getResources().getString(R.string.alert_wrong_qty));
		alertDialogFragment.setArguments(bundle);
		alertDialogFragment.show(fm, ALERT_FRAGMENT);
	}

	/**
	 * Method- To Validate that the quantity entered is greater than or equal to
	 * PRDCT_MIN_QTY and a multiple of PRDCT_MIN_QTY
	 * 
	 * @param string
	 * @return
	 */
	private boolean checkPrdctMinQty(double qty) {
		if (mPrdct_MST_Model != null) {
			double prdct_min_qty = mPrdct_MST_Model.getPRDCT_MIN_QTY();
			// Greater or equal to and multiple of min_qty
			Logger.d(TAG, "selectedQty:" + qty + ",MinQty:" + prdct_min_qty);
			if (qty >= prdct_min_qty && (qty % prdct_min_qty) == 0) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	/**
	 * Method- To check whether the price entered is below cost price and
	 * whether it is allowed from Config table
	 * 
	 * @return
	 */
	private void checkPriceSellBelow() {
		LoginHelper helper = new LoginHelper(this);
		helper.fetchCmpPolicyRecord(ALLOW_SELL_BWL_COST_PRCE,
				DBConstants.dbo_CONFIG_MST_TABLE, true);
	}

	/**
	 * Method- To check whether Products Code present in UOM_SLAB
	 */
	private void retrieveUOM_SLABDBData() {
		mDataCount = mDataCount + 1;
		Logger.d(TAG, "count: " + mDataCount);
		mHomeHelper.selectUOM_SLABRecords(mPrdct_MST_Model.getPRDCT_CODE()
				.trim(), DBConstants.dbo_UOM_SLAB_MST_TABLE, true);
	}

	/**
	 * Method- To retrieve Measure data from DB
	 */
	private void retrieceUOMDBData() {
		mDataCount = mDataCount + 1;
		Logger.d(TAG, "count: " + mDataCount);
		mHomeHelper.selectUOMRecords("", DBConstants.dbo_UOM_TABLE, true);
	}

	/**
	 * Method- To retrieve all modifier data present for product
	 */
	private void retrieveModifierDBData() {
		mDataCount = mDataCount + 1;
		Logger.d(TAG, "count: " + mDataCount);
		mHomeHelper.selectModifierRecords("",
				DBConstants.dbo_INSTRCTN_MST_TABLE, true);
	}

	/**
	 * Method- TO get modifier related to prdct from prdct_instruc_mst table
	 * 
	 * @param prdct_CODE
	 */
	private void retrievePrdctModifier(String prdct_CODE) {
		Logger.d(TAG, "retrievePrdctModifier:");
		mHomeHelper.getPrdctModifierRecords(prdct_CODE,
				DBConstants.dbo_PRDCT_INSTRCTN_MST_TABLE, true);
	}

	/**
	 * Method- To get all VAT details of product
	 */
	private void retrieveVatMstDBData() {
		mDataCount = mDataCount + 1;
		Logger.d(TAG, "count: " + mDataCount);
		mHomeHelper.selectVatRecords(mPrdct_MST_Model.getPRDCT_VAT_CODE(),
				DBConstants.dbo_VAT_MST_TABLE, true);
	}

	/**
	 * Method- To get all ingredients for processed item
	 * 
	 * @param prdct_CODE
	 */
	private void retrievePrdctIngredients(String prdct_CODE) {
		mHomeHelper.selectIngredientsRecords(mPrdct_MST_Model.getPRDCT_CODE(),
				DBConstants.dbo_PRDCT_RECEIPE_MST_TABLE, true);
	}

	/**
	 * Method- To insert row for the bill/transaction to the BILL_MST
	 */
	private void savePrdctDataInBillMst() {
		Logger.d(TAG, "savePrdctDataInBillMst");
		BaseActivity.loadAllConfigData();
		String CMPNY_CODE = Config.COMPANY_CODE;
		String BRNCH_CODE = Config.BRANCH_CODE;
		String BILL_SCNCD = UserSingleton.getInstance(getContext())
				.getTransactionNo(); // Transaction no.
		String TXN_TYPE = Constants.BILLING_TXN;

		SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT);
		String currentDateandTime = sdf.format(new Date());
		String BILL_SYS_DATE = currentDateandTime;
		String BILL_RUN_DATE = BaseActivity.getServerRunDate();

		String TILL_NO = Config.TAB_NO; // Config
		String BILL_NO = String.valueOf(UserSingleton.getInstance(getContext())
				.getReceiptCount());

		String BILL_VAT_EXMPT = ""; // Ignore

		double BILL_AMNT = 0.0;
		double AMNT_PAID = 0.0;
		double amt = 0.0;
		if (!mQtyEt.getText().toString().trim().equalsIgnoreCase("")) {
			amt = (Double.parseDouble(mPriceEt.getText().toString().trim()) * Double
					.parseDouble(mQtyEt.getText().toString().trim()));
		} else {

			amt = Double.parseDouble(mPriceEt.getText().toString().trim());
		}

		BILL_AMNT = amt;
		AMNT_PAID = amt;

		double CHNG_GVN = 0.0;

		// Vat calculation
		double BILL_VAT_AMNT = getVatAmountCal();
		DecimalFormat df2 = Constants.getDecimalFormat();
		BILL_VAT_AMNT = Double.valueOf(df2.format(BILL_VAT_AMNT));

		String USR_NAME = UserSingleton.getInstance(getContext()).mUserName;
		String BILL_STATUS = "";
		String BILL_SCND = ""; // Ignore
		String BILL_AMENED = "";// Ignore for now
		String CARDNO = ""; // Ignore
		String SHIFT_CODE = "";
		String TBLE_CODE = Config.TABLE_NO; // Ignoew
		String BILL_AMND_STATUS = ""; // //Ignore
		String BILL_LOCKED = ""; // Ignnore

		mBill_Mst_Model = new BILL_Mst_Model();
		mBill_Mst_Model.setAMNT_PAID(AMNT_PAID);
		mBill_Mst_Model.setBILL_AMENED(BILL_AMENED);
		mBill_Mst_Model.setBILL_AMND_STATUS(BILL_AMND_STATUS);
		mBill_Mst_Model.setBILL_AMNT(BILL_AMNT);
		mBill_Mst_Model.setBILL_LOCKED(BILL_LOCKED);
		mBill_Mst_Model.setBILL_NO(BILL_NO);
		mBill_Mst_Model.setBILL_RUN_DATE(BILL_RUN_DATE);
		mBill_Mst_Model.setBILL_SCNCD(BILL_SCNCD);
		mBill_Mst_Model.setBILL_SCND(BILL_SCND);
		mBill_Mst_Model.setBILL_STATUS(BILL_STATUS);
		mBill_Mst_Model.setBILL_SYS_DATE(BILL_SYS_DATE);
		mBill_Mst_Model.setBILL_VAT_AMNT(BILL_VAT_AMNT);
		mBill_Mst_Model.setBILL_VAT_EXMPT(BILL_VAT_EXMPT);
		mBill_Mst_Model.setBRNCH_CODE(BRNCH_CODE);
		mBill_Mst_Model.setCARDNO(CARDNO);
		mBill_Mst_Model.setCHNG_GVN(CHNG_GVN);
		mBill_Mst_Model.setCMPNY_CODE(CMPNY_CODE);
		mBill_Mst_Model.setSHIFT_CODE(SHIFT_CODE);
		mBill_Mst_Model.setTBLE_CODE(TBLE_CODE);
		mBill_Mst_Model.setTILL_NO(TILL_NO);
		mBill_Mst_Model.setTXN_TYPE(TXN_TYPE);
		mBill_Mst_Model.setUSR_NAME(USR_NAME);

		mHomeHelper.savePrdctDataInBillMstDB(mBill_Mst_Model);
	}

	/**
	 * Method To update existing mst record
	 * 
	 * @param mUniqueTrasactionNo
	 */
	private void updateExistingBillMstRecord(String mUniqueTrasactionNo) {
		Logger.d(TAG, "updateExistingBillMstRecord");
		BaseActivity.loadAllConfigData();
		String CMPNY_CODE = Config.COMPANY_CODE;
		String BRNCH_CODE = Config.BRANCH_CODE;

		SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT);
		String currentDateandTime = sdf.format(new Date());
		String BILL_SYS_DATE = currentDateandTime;
		String BILL_RUN_DATE = BaseActivity.getServerRunDate();

		String TILL_NO = Config.TAB_NO; // Config
		String BILL_NO = String.valueOf(UserSingleton.getInstance(getContext())
				.getReceiptCount());

		String BILL_SCND = ""; // Ignore
		String SHIFT_CODE = "";
		String TBLE_CODE = Config.TABLE_NO; // Ignoew

		String TXN_TYPE = Constants.BILLING_TXN;
		String BILL_VAT_EXMPT = "";

		double BILL_AMNT = mTotalAmt;
		double AMNT_PAID = mTotalAmt;

		double CHNG_GVN = 0.0;
		double BILL_VAT_AMNT = getVatAmountCal();
		String USR_NAME = UserSingleton.getInstance(getContext()).mUserName;
		String BILL_STATUS = "";
		String BILL_AMENED = "";
		String CARDNO = "";
		String BILL_AMND_STATUS = "";
		String BILL_LOCKED = "";

		mBill_Mst_Model = new BILL_Mst_Model();
		mBill_Mst_Model.setCMPNY_CODE(CMPNY_CODE);
		mBill_Mst_Model.setBRNCH_CODE(BRNCH_CODE);
		mBill_Mst_Model.setBILL_RUN_DATE(BILL_RUN_DATE);
		mBill_Mst_Model.setBILL_SYS_DATE(BILL_SYS_DATE);
		mBill_Mst_Model.setTILL_NO(TILL_NO);
		mBill_Mst_Model.setBILL_NO(BILL_NO);
		mBill_Mst_Model.setBILL_SCND(BILL_SCND);
		mBill_Mst_Model.setSHIFT_CODE(SHIFT_CODE);
		mBill_Mst_Model.setTBLE_CODE(TBLE_CODE);

		mBill_Mst_Model
				.setBILL_SCNCD(UserSingleton.getInstance(getContext()).mUniqueTrasactionNo);
		mBill_Mst_Model.setAMNT_PAID(AMNT_PAID);
		mBill_Mst_Model.setBILL_AMENED(BILL_AMENED);
		mBill_Mst_Model.setBILL_AMND_STATUS(BILL_AMND_STATUS);
		mBill_Mst_Model.setBILL_AMNT(BILL_AMNT);
		mBill_Mst_Model.setBILL_LOCKED(BILL_LOCKED);
		mBill_Mst_Model.setBILL_STATUS(BILL_STATUS);
		mBill_Mst_Model.setBILL_VAT_AMNT(BILL_VAT_AMNT);
		mBill_Mst_Model.setBILL_VAT_EXMPT(BILL_VAT_EXMPT);
		mBill_Mst_Model.setCARDNO(CARDNO);
		mBill_Mst_Model.setCHNG_GVN(CHNG_GVN);
		mBill_Mst_Model.setTXN_TYPE(TXN_TYPE);
		mBill_Mst_Model.setUSR_NAME(USR_NAME);

		HomeHelper homeHelper = new HomeHelper(this);
		homeHelper.updateBillMStDetails(mBill_Mst_Model);
	}

	/**
	 * Method- To retrieve any previous MST records present
	 * 
	 * @param UserSingleton
	 * 
	 */
	private void retrieveExistingMstRecord() {
		HomeHelper homeHelper = new HomeHelper(this);
		homeHelper
				.retrievSameBillMstDB(UserSingleton.getInstance(getContext()).mUniqueTrasactionNo);
	}

	/**
	 * Method- To make entry in BILL_TXN table
	 */
	private void savePrdctDataInBillTxnMst() {
		Logger.d(TAG, "savePrdctDataInBillTxnMst");
		BaseActivity.loadAllConfigData();
		String TXN_TYPE = Constants.BILLING_TXN;
		String BRNCH_CODE = Config.BRANCH_CODE;

		String BILL_VAT_EXMPT = "";
		String autoGrnDone = "";
		String billPrinted = Constants.BILL_NOT_PRINTED;
		String CMPNY_CODE = Config.COMPANY_CODE;
		String BILL_SCNCD = UserSingleton.getInstance(getContext())
				.getTransactionNo(); // Transaction no.
		String emptyDone = "";
		double lineDsc = 0.0;
		String packMsr = "";
		double packPrce = 0.0;
		int packQty = 1;
		double prdctDsc = 0.0;
		double paymentModeDesc = 0.0;
		String prdctSCND = "";

		String BILL_RUN_DATE = BaseActivity.getServerRunDate();

		double prdctAmt = 0;
		double prdctQty = 0;
		if (!mQtyEt.getText().toString().trim().equalsIgnoreCase("")) {
			prdctAmt = (Double
					.parseDouble(mPriceEt.getText().toString().trim()) * Double
					.parseDouble(mQtyEt.getText().toString().trim()));
			prdctQty = Double.parseDouble(mQtyEt.getText().toString().trim());
		}

		double prdctCostPrce = mPrdct_MST_Model.getPRDCT_COST_PRCE();
		String prdctCode = mPrdct_MST_Model.getPRDCT_CODE();

		String prdctLngDesc = "";
		// String prdctLngDesc = mPrdct_MST_Model.getPRDCT_LNG_DSCRPTN();
		if (mExistDescEt.getText().toString().trim().length() > 0)
			prdctLngDesc = mExistDescEt.getText().toString().trim();
		else
			prdctLngDesc = mPrdct_MST_Model.getPRDCT_LNG_DSCRPTN();

		double BILL_VAT_AMNT = getVatAmountCal();
		DecimalFormat df2 = Constants.getDecimalFormat();
		BILL_VAT_AMNT = Double.valueOf(df2.format(BILL_VAT_AMNT));

		mBill_Txn_Model.setAUTO_GRN_DONE(autoGrnDone);
		mBill_Txn_Model.setBILL_PRNTD(billPrinted);
		mBill_Txn_Model.setBILL_RUN_DATE(BILL_RUN_DATE);
		mBill_Txn_Model.setBILL_SCNCD(BILL_SCNCD);
		mBill_Txn_Model.setBRNCH_CODE(BRNCH_CODE);
		mBill_Txn_Model.setCMPNY_CODE(CMPNY_CODE);
		mBill_Txn_Model.setEMPTY_DONE(emptyDone);
		mBill_Txn_Model.setLINEDISC(lineDsc);

		if (mBill_Txn_Model.getPACK().length() <= 0)
			mBill_Txn_Model.setPACK(packMsr);

		mBill_Txn_Model.setPACKPRCE(packPrce);
		mBill_Txn_Model.setPACKQTY(packQty);
		mBill_Txn_Model.setPRDCT_AMNT(prdctAmt);
		mBill_Txn_Model.setPRDCT_CODE(prdctCode);
		mBill_Txn_Model.setPRDCT_COST_PRCE(prdctCostPrce);
		mBill_Txn_Model.setPRDCT_DSCNT(prdctDsc);
		mBill_Txn_Model.setPRDCT_LNG_DSCRPTN(prdctLngDesc);
		mBill_Txn_Model.setPRDCT_PRC(mPrdct_MST_Model.getPRDCT_SELL_PRCE());
		mBill_Txn_Model.setPRDCT_PYMNT_MODE_DSCNT(paymentModeDesc);
		mBill_Txn_Model.setPRDCT_QNTY(prdctQty);
		mBill_Txn_Model.setPRDCT_SCND(prdctSCND);
		mBill_Txn_Model.setPRDCT_VAT_AMNT(BILL_VAT_AMNT);
		mBill_Txn_Model.setPRDCT_VAT_CODE(mPrdct_MST_Model.getPRDCT_VAT_CODE());
		mBill_Txn_Model.setPRDCT_VAT_EXMPT(BILL_VAT_EXMPT);
		mBill_Txn_Model.setPRDCT_VOID(Constants.ITEM_NOT_VOIDED);
		mBill_Txn_Model.setSCAN_CODE("");
		mBill_Txn_Model.setSLS_MAN_CODE("");
		mBill_Txn_Model
				.setSR_NO(UserSingleton.getInstance(getContext()).mReceipt_NoCount);
		mBill_Txn_Model.setTXN_TYPE(TXN_TYPE);
		mBill_Txn_Model.setUSR_ANTHNTCTD(UserSingleton
				.getInstance(getContext()).mUsr_Anthntctd);
		mBill_Txn_Model.setZED_NO(Integer.parseInt(Config.MAX_Z_NO) + 1);

		if (mPrdct_MST_Model.getPRDCT_CP_VLTN().contains("P")) {
			mBill_Txn_Model.setProcessedItem(true);
		} else {
			mBill_Txn_Model.setProcessedItem(false);
		}

		mHomeHelper.savePrdctDataInBillTXNDB(mBill_Txn_Model);
	}

	/**
	 * MEthod- To save modifier data in BILL_INSTR_TXN
	 * 
	 * @param billTransactionModel
	 */
	private void saveModifierDataInBillInstrcTxn(
			BillTransactionModel billTransactionModel) {
		Logger.v(TAG, "saveModifierDataInBillInstrcTxn txn"
				+ billTransactionModel);
		BaseActivity.loadAllConfigData();
		String billSCnd = UserSingleton.getInstance(getContext())
				.getTransactionNo();
		String branchCode = Config.BRANCH_CODE;
		String compnyCode = Config.COMPANY_CODE;
		String pck = mBill_Txn_Model.getPACK();
		String prdctCode = mPrdct_MST_Model.getPRDCT_CODE();
		String prdctVoid = Constants.ITEM_NOT_VOIDED;

		// update the INstruction field of Bill_INSTRCTN_TXN table
		if (mSelectedModifierIndex != -1) {
			mBill_INSTRCTN_Txn_Model.setInstrcCode(mAvailModifierArr.get(
					mSelectedModifierIndex).getInstrctn_code());
			if (!mModifierQtyEtList.get(mSelectedModifierIndex).getText()
					.toString().trim().equalsIgnoreCase("")) {
				mBill_INSTRCTN_Txn_Model.setInstrnQty(Double
						.parseDouble(mModifierQtyEtList
								.get(mSelectedModifierIndex).getText()
								.toString().trim()));
			}
			mBill_INSTRCTN_Txn_Model.setExtraInstrcn(mExtraInstrcEt.getText()
					.toString().trim());
		}

		mBill_INSTRCTN_Txn_Model.setRow_id(billTransactionModel.getRow_id());
		mBill_INSTRCTN_Txn_Model.setBillScnd(billSCnd);
		mBill_INSTRCTN_Txn_Model.setBranchCode(branchCode);
		mBill_INSTRCTN_Txn_Model.setCompanyCode(compnyCode);
		mBill_INSTRCTN_Txn_Model.setPck(pck);
		mBill_INSTRCTN_Txn_Model.setPrdctCode(prdctCode);
		mBill_INSTRCTN_Txn_Model.setPrdctVoid(prdctVoid);

		mHomeHelper.saveInBill_INSTRCTN_TXNDB(mBill_INSTRCTN_Txn_Model);
	}

	/**
	 * Method- TO insert recipe_details into consumption table
	 */
	private void insertIntoConsumptionTable() {
		ArrayList<PRDCT_CNSMPTN_TXNModel> cnsmptn_TXNModels = new ArrayList<PRDCT_CNSMPTN_TXNModel>();
		BaseActivity.loadAllConfigData();
		for (PRDCT_RECIPE_DTL_TXN_Model prdct_recipe_TXNModel : mPrdct_recipe_dtl_Models) {

			PRDCT_CNSMPTN_TXNModel cnsmptn_TXNModel = new PRDCT_CNSMPTN_TXNModel();
			cnsmptn_TXNModel.setBranch_code(Config.BRANCH_CODE);
			cnsmptn_TXNModel.setCmp_code(Config.COMPANY_CODE);
			cnsmptn_TXNModel.setPck(mBill_Txn_Model.getPACK());
			cnsmptn_TXNModel.setPrdct_code(mPrdct_MST_Model.getPRDCT_CODE());
			cnsmptn_TXNModel.setPrdct_prce(mPrdct_MST_Model
					.getPRDCT_COST_PRCE());
			cnsmptn_TXNModel.setPrdct_vat_amt(mBill_Mst_Model
					.getBILL_VAT_AMNT());
			cnsmptn_TXNModel.setPrdct_vat_code(mPrdct_MST_Model
					.getPRDCT_VAT_CODE());
			cnsmptn_TXNModel.setPrdct_void(Constants.ITEM_NOT_VOIDED);
			cnsmptn_TXNModel.setRecipe_code(prdct_recipe_TXNModel
					.getRecipe_code());
			cnsmptn_TXNModel.setStk_qty(prdct_recipe_TXNModel.getStk_qty());
			cnsmptn_TXNModel.setStk_uom_code(prdct_recipe_TXNModel
					.getStk_uom_code());
			cnsmptn_TXNModel.setTxn_mode(Config.TXN_MODE);
			cnsmptn_TXNModel.setTxn_no(UserSingleton.getInstance(getContext())
					.getTransactionNo());
			cnsmptn_TXNModel.setTxn_type(Constants.BILLING_TXN);

			cnsmptn_TXNModels.add(cnsmptn_TXNModel);
		}

		mHomeHelper.saveIn_PRDCT_CNSMPTN_TXNDB(cnsmptn_TXNModels);
	}

	/**
	 * Method- To fill all measure data
	 */
	private void fillMeasureData() {
		if (mUOM_ModelDataList != null && mUOM_ModelDataList.size() > 0) {
			mMeasureLL.setVisibility(View.VISIBLE);
			mMeasureLL.removeAllViews();

			for (int j = 0; j < mUOM_ModelDataList.size(); j++) {
				UOM_MST_Model uom_model = mUOM_ModelDataList.get(j);

				View measureView = View.inflate(getActivity(),
						R.layout.prdct_measure_fragment, null);

				Button measureBtn = (Button) measureView
						.findViewById(R.id.measureBtn);
				measureBtn.setText(uom_model.getUom_name());
				measureBtn.setTextColor(getResources().getColor(R.color.white));

				measureBtn.setBackgroundResource(R.drawable.ok);
				measureBtn.setOnClickListener(mClickListener);
				measureBtn.setTag(j);
				mMeasureBtnList.add(measureBtn);

				RelativeLayout.LayoutParams btnLayout = (RelativeLayout.LayoutParams) measureBtn
						.getLayoutParams();
				btnLayout.setMargins(2, 2, 2, 2);
				mMeasureLL.addView(measureView, btnLayout);
				// add the itemView
				/*
				 * mMeasureLL.addView(measureView, new LayoutParams(
				 * LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
				 */
			}

		} else {
			mMeasureLL.setVisibility(View.GONE);
		}
	}

	/**
	 * Method- To fill all modifier data
	 */
	private void fillModifierData() {
		// chek both available modifier for prdct and total modifiers data is
		// not empty
		if (mINSTRCTNDataList != null && mINSTRCTNDataList.size() > 0
				&& mPrdct_Instrc_Models != null
				&& mPrdct_Instrc_Models.size() > 0) {

			mModifierLL.setVisibility(View.VISIBLE);
			mModifierLL.removeAllViews();

			// create temp arr for all available modifiers fpr particular prdct
			mAvailModifierArr = new ArrayList<INSTRCTN_MST_Model>();
			for (int i = 0; i < mINSTRCTNDataList.size(); i++) {
				for (int l = 0; l < mPrdct_Instrc_Models.size(); l++) {
					if (mINSTRCTNDataList
							.get(i)
							.getInstrctn_code()
							.equalsIgnoreCase(
									mPrdct_Instrc_Models.get(l)
											.getInstrctn_code())) {
						mAvailModifierArr.add(mINSTRCTNDataList.get(i));
					}
				}
			}

			if (mAvailModifierArr != null && mAvailModifierArr.size() > 0) {
				Logger.d(TAG, "Fit Modifiers: " + mAvailModifierArr.toString());
				// create view according to new temp array
				for (int j = 0; j < mAvailModifierArr.size(); j++) {
					INSTRCTN_MST_Model instrn_model = mAvailModifierArr.get(j);

					View modifierView = View.inflate(getActivity(),
							R.layout.prdct_modifier_fragment, null);

					Button modifierBtn = (Button) modifierView
							.findViewById(R.id.modifierBtn);
					modifierBtn.setText(instrn_model.getInstrctn_desc());
					modifierBtn.setBackgroundResource(R.drawable.ok);
					modifierBtn.setTextColor(getResources().getColor(
							R.color.white));
					modifierBtn.setOnClickListener(mClickListener);
					modifierBtn.setTag(j);
					mModifierBtnList.add(modifierBtn);

					EditText modifierEt = (EditText) modifierView
							.findViewById(R.id.modifierQtyEt);
					modifierEt.setTag(j);
					modifierEt.setId(j);
					modifierEt.setHint(getContext().getResources().getString(
							R.string.qty));
					mModifierQtyEtList.add(modifierEt);

					// add the itemView
					mModifierLL.addView(modifierView, new LayoutParams(
							LayoutParams.WRAP_CONTENT,
							LayoutParams.WRAP_CONTENT));
				}
			} else {
				mModifierHeadingTV.setVisibility(View.GONE);
				mModifierLL.setVisibility(View.GONE);
			}

		} else {
			mModifierHeadingTV.setVisibility(View.GONE);
			mModifierLL.setVisibility(View.GONE);
		}
	}

	/**
	 * Method- Network/DB Response Handling
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void executePostAction(OperationalResult opResult) {
		Logger.d(TAG, "executePostAction");
		mDataCount = mDataCount - 1;
		Logger.d(TAG, "count: " + mDataCount);

		switch (opResult.getResultCode()) {
		/** UOM_SLAB Transactions **/
		case Constants.FETCH_UOM_SLAB_RECORD:
			mUOM_SLAB_ModelDataList = (ArrayList<UOM_SLAB_MST_MODEL>) opResult
					.getResult().getSerializable(Constants.RESULTCODEBEAN);
			if (mUOM_SLAB_ModelDataList != null
					&& mUOM_SLAB_ModelDataList.size() > 0) {
				Logger.d(TAG,
						"UOM_SLAB_DATA:" + mUOM_SLAB_ModelDataList.toString());
				retrieceUOMDBData();
			} else {
				// Load dummy data for now
				// loadDummyUOMData();
				mMeasureHeadingTV.setText(getResources().getString(
						R.string.prdct_no_measure));
			}
			break;

		case Constants.FETCH_UOM_RECORD:
			mUOM_ModelDataList = (ArrayList<UOM_MST_Model>) opResult
					.getResult().getSerializable(Constants.RESULTCODEBEAN);
			if (mUOM_ModelDataList != null && mUOM_ModelDataList.size() > 0) {
				Logger.d(TAG, "UOM_DATA:" + mUOM_ModelDataList.toString());
				mMeasureHeadingTV.setText(getResources().getString(
						R.string.prdct_measure));
				mMeasureBtnList = new ArrayList<Button>();
				fillMeasureData();
			} else {
				mMeasureHeadingTV.setText(getResources().getString(
						R.string.prdct_no_measure));
			}
			break;

		case Constants.FETCH_MODIFIER_RECORD:
			mINSTRCTNDataList = (ArrayList<INSTRCTN_MST_Model>) opResult
					.getResult().getSerializable(Constants.RESULTCODEBEAN);
			if (mINSTRCTNDataList != null && mINSTRCTNDataList.size() > 0) {
				Logger.d(TAG, "INSTRCTN Data:" + mINSTRCTNDataList.toString());
				// search for particulat prdct modifer record
				retrievePrdctModifier(mPrdct_MST_Model.getPRDCT_CODE());
			} else {
				mModifierHeadingTV.setText(getResources().getString(
						R.string.prdct_no_modifier));
				mExtraInstrcEt.setVisibility(View.GONE);
			}
			break;

		case Constants.FETCH_VAT_RECORD:
			mPrdct_Vat_Model = (VAT_MST_Model) opResult.getResult()
					.getSerializable(Constants.RESULTCODEBEAN);
			if (mPrdct_Vat_Model != null) {
				getVatAmountCal();
			}
			break;

		case Constants.FETCH_BILL_TXN_RECORD:
			Logger.d(TAG, "FETCH_BILL_TXN_RECORD");
			mBillTransactionModels = (ArrayList<BillTransactionModel>) opResult
					.getResult().getSerializable(Constants.RESULTCODEBEAN);
			if (mBillTransactionModels != null
					&& mBillTransactionModels.size() > 0) {
				if (isUpdateNeeded) {
					mTotalAmt = calculateTotlaAmount(mBillTransactionModels);
					updateExistingBillMstRecord(UserSingleton
							.getInstance(getContext()).mUniqueTrasactionNo);
				} else {
					if (getTargetFragment() != null) {
						Intent intent = new Intent();
						intent.putExtra(Constants.KEY, mBillTransactionModels);
						getTargetFragment().onActivityResult(BILL_DATA,
								BILL_SUCCESS, intent);
					}
					if (getDialog() != null && getDialog().isShowing()) {
						getDialog().dismiss();
					}
				}
			}

			break;

		case Constants.FETCH_BILL_INSTRCTN_TXN_RECORD:
			Logger.d(TAG, "FETCH_BILL_INSTRCTN_TXN_RECORD");
			ArrayList<Bill_INSTRCTN_TXN_Model> mBill_Insrtc_Txn_Models = (ArrayList<Bill_INSTRCTN_TXN_Model>) opResult
					.getResult().getSerializable(Constants.RESULTCODEBEAN);
			if (mBill_Insrtc_Txn_Models != null
					&& mBill_Insrtc_Txn_Models.size() > 0) {
				Logger.d(
						TAG,
						"Bill_Insrtctn_Txn:"
								+ mBill_Insrtc_Txn_Models.toString());
			}
			String[] whrArgs = {
					UserSingleton.getInstance(MPOSApplication.getContext()).mUniqueTrasactionNo,
					Constants.ITEM_NOT_VOIDED };
			new HomeHelper(this).selectBillTXNRecords(whrArgs,
					DBConstants.dbo_BILL_TXN_TABLE, true);

			break;

		case Constants.FETCH_INGREDIENT_RECORD:
			mPrdct_recipe_dtl_Models = (ArrayList<PRDCT_RECIPE_DTL_TXN_Model>) opResult
					.getResult().getSerializable(Constants.RESULTCODEBEAN);
			if (mPrdct_recipe_dtl_Models != null
					&& mPrdct_recipe_dtl_Models.size() > 0) {
				Logger.d(TAG, "mPrdct_recipe_dtl_Models:"
						+ mPrdct_recipe_dtl_Models.toString());

				insertIntoConsumptionTable();
			}
			break;

		case Constants.FETCH_PRDCT_INSTRC_CODE_RECORD:
			Logger.d(TAG, "FETCH_PRDCT_INSTRC_CODE_RECORD Done");
			mPrdct_Instrc_Models = (ArrayList<PRDCT_INSTRC_MST_Model>) opResult
					.getResult().getSerializable(Constants.RESULTCODEBEAN);
			if (mPrdct_Instrc_Models != null && mPrdct_Instrc_Models.size() > 0) {
				Logger.d(TAG,
						"pdct_Instc_Models:" + mPrdct_Instrc_Models.toString());

				mModifierHeadingTV.setText(getResources().getString(
						R.string.prdct_modifier));
				mModifierQtyEtList = new ArrayList<EditText>();
				mModifierBtnList = new ArrayList<Button>();
				fillModifierData();
			} else {
				mModifierHeadingTV.setText(getResources().getString(
						R.string.prdct_no_modifier));
				mExtraInstrcEt.setVisibility(View.GONE);
			}
			break;

		case Constants.INSERT_CNSMPTN_RECORD:
			Logger.d(TAG, "INSERT_CNSMPTN_RECORD Completed");
			break;

		case Constants.GET_BILL_MST_COUNT:
			Logger.d(TAG, "GET_BILL_MST_COUNT Completed");
			int count = opResult.getResult().getInt(Constants.RESULTCODEBEAN);
			// If cnt is 1 means record already present, need to update
			if (count > 0) {
				Logger.d(TAG, "existing count:" + count);
				isUpdateNeeded = true;
				savePrdctDataInBillTxnMst();
			} else { // Insert new record
				Logger.d(TAG, "new record:" + count);
				isUpdateNeeded = false;
				savePrdctDataInBillMst();
				savePrdctDataInBillTxnMst();
			}
			break;

		case Constants.UPDATE_BILL_MST_RECORD:
			Logger.d(TAG, "UPDATE_BILL_MST_RECORD Completed");
			if (getTargetFragment() != null) {
				Intent intent = new Intent();
				intent.putExtra(Constants.KEY, mBillTransactionModels);
				getTargetFragment().onActivityResult(BILL_DATA, BILL_SUCCESS,
						intent);
			}
			if (getDialog() != null && getDialog().isShowing()) {
				getDialog().dismiss();
			}
			break;

		case Constants.FETCH_SINGLE_CMP_PLCY_RECORD:
			Logger.d(TAG, "FETCH_SINGLE_CMP_PLCY_RECORD Done");
			Company_PolicyModel policyModel = (Company_PolicyModel) opResult
					.getResult().getSerializable(Constants.RESULTCODEBEAN);
			if (policyModel != null) {
				// Sell below cost price allowed
				if (policyModel.getCmpny_ply().equalsIgnoreCase("Y")) {
					isSellBelowCostPriceAllowed = true;
				} else {// Not Allowed
					isSellBelowCostPriceAllowed = false;
				}
			}
			break;

		case Constants.INSERT_BILL_TXN_RECORDS:
			Logger.d(TAG, "INSERT_BILL_TXN_RECORDS Done");
			break;

		case Constants.FETCH_MAX_ID_BILL_TXN_RECORD:
			Logger.d(TAG, "FETCH_MAX_ID_BILL_TXN_RECORD");
			ArrayList<BillTransactionModel> LastInsertedBill = (ArrayList<BillTransactionModel>) opResult
					.getResult().getSerializable(Constants.RESULTCODEBEAN);
			if (LastInsertedBill != null && LastInsertedBill.size() > 0) {
				Logger.d(
						TAG,
						"FETCH_MAX_ID_BILL_TXN_RECORD"
								+ LastInsertedBill.size());
				String typeOfItem = mPrdct_MST_Model.getPRDCT_CP_VLTN();
				if (typeOfItem.contains(PROCESSED)) {
					saveModifierDataInBillInstrcTxn(LastInsertedBill
							.get(LastInsertedBill.size() - 1));
				} else {
					String[] whrArgs1 = {
							UserSingleton.getInstance(MPOSApplication
									.getContext()).mUniqueTrasactionNo,
							Constants.ITEM_NOT_VOIDED };
					new HomeHelper(this).selectBillTXNRecords(whrArgs1,
							DBConstants.dbo_BILL_TXN_TABLE, true);
				}
			} else {
				String[] whrArgs1 = {
						UserSingleton.getInstance(MPOSApplication.getContext()).mUniqueTrasactionNo,
						Constants.ITEM_NOT_VOIDED };
				new HomeHelper(this).selectBillTXNRecords(whrArgs1,
						DBConstants.dbo_BILL_TXN_TABLE, true);
			}
			break;
		}
	}

	/**
	 * Method- To calculate total amount for particular Bill and update
	 * 
	 * @param mBill_Insrtc_Txn_Models
	 * @return
	 */
	private double calculateTotlaAmount(
			ArrayList<BillTransactionModel> mBill_Txn_Models) {
		double totalAmt = 0.0;
		for (BillTransactionModel bill_TXN_Model : mBill_Txn_Models) {
			/*
			 * totalAmt = totalAmt + (bill_TXN_Model.getPRDCT_QNTY() *
			 * bill_TXN_Model .getPRDCT_COST_PRCE());
			 */

			totalAmt = totalAmt + (bill_TXN_Model.getPRDCT_AMNT());
		}
		return totalAmt;
	}

	/**
	 * Method- To do VAT price calculation depending upon sell price and vat
	 * percentage
	 */
	private double getVatAmountCal() {
		double vatPrcnt = mPrdct_Vat_Model.getVat_prcnt();
		double prdctSellPrice = mPrdct_MST_Model.getPRDCT_SELL_PRCE();
		double prdctQty = Double
				.parseDouble(mQtyEt.getText().toString().trim());

		double vatAmount = (prdctQty * (prdctSellPrice * (vatPrcnt / 100)));
		Logger.d(TAG, "VAT AMT:" + vatAmount);
		return vatAmount;
	}

	/**
	 * Method- To receive error if any
	 */
	@Override
	public void errorReceived(OperationalResult opResult) {
		Logger.d(TAG, "errorReceived");
		mDataCount = mDataCount - 1;
		Logger.d(TAG, "count: " + mDataCount);

		switch (opResult.getResultCode()) {

		case OperationalResult.MASTER_ERROR:
			Logger.d(TAG, "MASTER_ERROR");

			break;

		case OperationalResult.DB_ERROR:
			Logger.d(TAG, "DB_ERROR");
			if (opResult.getResultCode() == Constants.FETCH_SINGLE_CMP_PLCY_RECORD) {
				isSellBelowCostPriceAllowed = true;
			}
			break;

		}
	}

	@Override
	public Context getContext() {
		// TODO Auto-generated method stub
		return getActivity();
	}

}
