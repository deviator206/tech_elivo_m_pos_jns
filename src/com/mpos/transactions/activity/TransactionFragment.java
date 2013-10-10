/*
 * Copyright (C) 2013  All Rights Reserved.
 *  PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.mpos.transactions.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.mpos.R;
import com.mpos.application.BaseActivity;
import com.mpos.application.MPOSApplication;
import com.mpos.framework.Util.UserSingleton;
import com.mpos.framework.common.Constants;
import com.mpos.framework.common.DBConstants;
import com.mpos.framework.common.Logger;
import com.mpos.framework.common.OperationalResult;
import com.mpos.framework.inf.BaseResponseListener;
import com.mpos.framework.inf.FragmentCommunicationInterface;
import com.mpos.home.activity.HomeActivity;
import com.mpos.home.fragment.AlertDialogFragment;
import com.mpos.home.fragment.UserAccessFragment;
import com.mpos.home.fragment.UserOptionFragment;
import com.mpos.home.helper.HomeHelper;
import com.mpos.home.model.BillTransactionModel;
import com.mpos.master.model.BILL_Mst_Model;
import com.mpos.master.model.UserRightsModel;
import com.mpos.transactions.adapter.HeldTransactionAdapter;
import com.mpos.transactions.model.HeldTransactionModel;

/**
 * Description-
 * 
 */

public class TransactionFragment extends DialogFragment implements
		BaseResponseListener {

	public static final String TAG = Constants.APP_TAG
			+ TransactionFragment.class.getSimpleName();

	private ListView transactionListview;

	private ArrayList<BILL_Mst_Model> mHeldBillMStDataList = null;
	private ArrayList<BillTransactionModel> mBillTransactionModels = null;
	private BILL_Mst_Model mSelectedBillModel = null;
	private HeldTransactionAdapter mTranscAdapter = null;
	private int previousSelectedPosition = -1;
	private boolean isDeleteHeld = false;
	private boolean isBillTxnTypeUpdateComplete = false;
	private static String typeOfAction;
	public FragmentCommunicationInterface mCallBack;

	private OnClickListener mOnClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.close_Btn:
				if (getDialog() != null && getDialog().isShowing())
					getDialog().dismiss();
				break;

			case R.id.load_Btn:
				if (mHeldBillMStDataList != null
						&& mHeldBillMStDataList.size() > 0
						&& previousSelectedPosition >= 0) {
					mSelectedBillModel = mHeldBillMStDataList
							.get(previousSelectedPosition);
					Logger.d(TAG, "Model: " + mSelectedBillModel.toString());
					// Make current selected transaction Billing
					makeCurrentTransactionToBilling(mSelectedBillModel);
				}
				break;

			case R.id.delete_Btn:

				boolean allowToDeleteHeldTran = true;
				if (UserSingleton.getInstance(getActivity()).mUserAssgndRightsModel != null) {
					allowToDeleteHeldTran = UserSingleton
							.getInstance(getContext()).mUserAssgndRightsModel.DELETE_HELD_TRANS_FLAG;
				}

				if (allowToDeleteHeldTran) {
					if (mHeldBillMStDataList != null
							&& mHeldBillMStDataList.size() > 0 && previousSelectedPosition != -1) {
						
					System.out.println("### "+mHeldBillMStDataList.size()+" ## previousSelectedPosition :: "+previousSelectedPosition);
						mSelectedBillModel = mHeldBillMStDataList
								.get(previousSelectedPosition);
						Logger.d(TAG, "Model: " + mSelectedBillModel.toString());

						isDeleteHeld = true;
						retrieveBillTxnFromDB(mSelectedBillModel
								.getBILL_SCNCD());
						// Make current selected transaction Billing
						// deleteSelectedModelFromList(mSelectedBillModel);
					}

				} else {
					typeOfAction = "delete held transaction";
					UserAccessFragment user = new UserAccessFragment();
					user.setTargetFragment(TransactionFragment.this,
							UserAccessFragment.USER_ACCESS);
					user.show(getActivity().getSupportFragmentManager(),
							"user-fragmet");
				}

				break;
			}
		}
	};

	public void onActivityResult(int requestCode, int resultCode,
			android.content.Intent data) {
		Logger.d(TAG, "onActivityResult");
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == UserAccessFragment.USER_ACCESS) {
			if (resultCode == UserAccessFragment.USER_SUCCESS && data != null) {
				UserRightsModel userRights = (UserRightsModel) data
						.getSerializableExtra(Constants.KEY);

				checkUserRightsAndTakeRespectiveAction(userRights);
			}
		}
	};

	private void checkUserRightsAndTakeRespectiveAction(
			UserRightsModel userRights) {
		Logger.d(TAG, "TransactionFragment.typeOfAction "
				+ TransactionFragment.typeOfAction);
		Logger.d(TAG, "userRights " + userRights);
		if (TransactionFragment.typeOfAction
				.equalsIgnoreCase("delete held transaction")) {
			if (userRights.DELETE_HELD_TRANS_FLAG) {
				if (mHeldBillMStDataList != null
						&& mHeldBillMStDataList.size() > 0) {
					mSelectedBillModel = mHeldBillMStDataList
							.get(previousSelectedPosition);
					Logger.d(TAG, "Model: " + mSelectedBillModel.toString());

					isDeleteHeld = true;
					retrieveBillTxnFromDB(mSelectedBillModel.getBILL_SCNCD());
					// Make current selected transaction Billing
					// deleteSelectedModelFromList(mSelectedBillModel);
				}

			} else {
				showAlertDialog("This user has not access to delete held transactions.");
			}
		}
	}

	private final String ALERT_FRAGMENT = "ALERT_FRAGMENT";

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

	/**
	 * Method- To delete selected record
	 * 
	 * @param mSelectedBillModel2
	 */
	protected void deleteSelectedModelFromList() {
		HomeHelper homeHelper = new HomeHelper(this);
		ArrayList<HeldTransactionModel> heldTxnModels = new ArrayList<HeldTransactionModel>();

		for (BillTransactionModel billtxnModel : mBillTransactionModels) {

			HeldTransactionModel heldTransactionModel = new HeldTransactionModel();

			heldTransactionModel.setAUTO_GRN_DONE(billtxnModel
					.getAUTO_GRN_DONE());
			heldTransactionModel.setBILL_PRNTD(billtxnModel.getBILL_PRNTD());

			heldTransactionModel.setBILL_RUN_DATE(BaseActivity
					.getServerRunDate());

			heldTransactionModel.setBILL_SCNCD(billtxnModel.getBILL_SCNCD());
			heldTransactionModel.setBRNCH_CODE(billtxnModel.getBRNCH_CODE());
			heldTransactionModel.setCMPNY_CODE(billtxnModel.getCMPNY_CODE());
			heldTransactionModel.setEMPTY_DONE(billtxnModel.getEMPTY_DONE());
			heldTransactionModel.setHELD_USR(billtxnModel.getUSR_ANTHNTCTD());
			heldTransactionModel.setLINEDISC(billtxnModel.getLINEDISC());
			heldTransactionModel.setPACK(billtxnModel.getPACK());
			heldTransactionModel.setPACKPRCE(billtxnModel.getPACKPRCE());
			heldTransactionModel.setPACKQTY(billtxnModel.getPACKQTY());
			heldTransactionModel.setPRDCT_AMNT(billtxnModel.getPRDCT_AMNT());
			heldTransactionModel.setPRDCT_CODE(billtxnModel.getPRDCT_CODE());
			heldTransactionModel.setPRDCT_COST_PRCE(billtxnModel
					.getPRDCT_COST_PRCE());
			heldTransactionModel.setPRDCT_DSCNT(billtxnModel.getPRDCT_DSCNT());
			heldTransactionModel.setPRDCT_LNG_DSCRPTN(billtxnModel
					.getPRDCT_LNG_DSCRPTN());
			heldTransactionModel.setPRDCT_PRC(billtxnModel.getPRDCT_PRC());
			heldTransactionModel.setPRDCT_PYMNT_MODE_DSCNT(billtxnModel
					.getPRDCT_PYMNT_MODE_DSCNT());
			heldTransactionModel.setPRDCT_QNTY(billtxnModel.getPRDCT_QNTY());
			heldTransactionModel.setPRDCT_SCND(billtxnModel.getPRDCT_SCND());
			heldTransactionModel.setPRDCT_VAT_AMNT(billtxnModel
					.getPRDCT_VAT_AMNT());
			heldTransactionModel.setPRDCT_VAT_CODE(billtxnModel
					.getPRDCT_VAT_CODE());
			heldTransactionModel.setPRDCT_VAT_EXMPT(billtxnModel
					.getPRDCT_VAT_EXMPT());
			heldTransactionModel.setPRDCT_VOID(billtxnModel.getPRDCT_VOID());
			heldTransactionModel.setSCAN_CODE(billtxnModel.getSCAN_CODE());
			heldTransactionModel
					.setSLS_MAN_CODE(billtxnModel.getSLS_MAN_CODE());
			heldTransactionModel.setSR_NO(billtxnModel.getSR_NO());
			heldTransactionModel.setTXN_TYPE(billtxnModel.getTXN_TYPE());
			heldTransactionModel.setUSR_ANTHNTCTD(billtxnModel
					.getUSR_ANTHNTCTD());
			heldTransactionModel.setZED_NO(billtxnModel.getZED_NO());

			heldTxnModels.add(heldTransactionModel);
		}

		homeHelper.saveHeldDeletionTxnDB(heldTxnModels);
	}

	/**
	 * Method- To retrieve all bill txn records for particular transaction no.
	 * 
	 * @param bill_SCNCD
	 */
	private void retrieveBillTxnFromDB(String bill_SCNCD) {
		HomeHelper homeHelper = new HomeHelper(this);
		String[] whrArgs = { bill_SCNCD, Constants.ITEM_NOT_VOIDED };
		homeHelper.selectBillTXNRecords(whrArgs,
				DBConstants.dbo_BILL_TXN_TABLE, true);
	}

	private OnItemClickListener mOnItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			 //position = position - 1;
			
			if (previousSelectedPosition != -1 && previousSelectedPosition <= mHeldBillMStDataList.size()-1)
			{
				
				mHeldBillMStDataList.get(previousSelectedPosition).setSelected(
						false);
			}
			
				
			previousSelectedPosition = position;
			
			mHeldBillMStDataList.get(position).setSelected(true);
			mTranscAdapter.notifyDataSetChanged();
		}
	};

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		// This makes sure that the container activity has implemented
		// the callback interface. If not, it throws an exception
		try {
			mCallBack = (FragmentCommunicationInterface) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement FragmentCommunicationInterface");
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		RelativeLayout view = (RelativeLayout) inflater.inflate(
				R.layout.transaction, null);

		getDialog().setTitle(
				getResources().getString(R.string.held_transactions_title));

		componentInitialisation(view);

		retrieveHeldTransactionsFromDB();

		return view;
	}

	/**
	 * Method- To load selected model in Bill Panel and make its TXNTYPE Billing
	 * 
	 * @param model
	 */
	protected void loadSelectedModelInBillPanel(BILL_Mst_Model model) {
		// Select all BIll_TXN records for selected Transaction No.
		// retrieve only non-voided items and for current transaction no.
		Logger.d(TAG, "BillScnd:" + model.getBILL_SCNCD());
		String[] whrArgs = { model.getBILL_SCNCD(), Constants.ITEM_NOT_VOIDED };
		HomeHelper homeHelper = new HomeHelper(this);
		homeHelper.selectBillTXNRecords(whrArgs,
				DBConstants.dbo_BILL_TXN_TABLE, true);
	}

	/**
	 * Method- To hold transaction, Update the TXNType field in BILL_MST to Held
	 * 
	 * @param selectedModel
	 */
	protected void makeCurrentTransactionToBilling(BILL_Mst_Model selectedModel) {
		SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT);
		String currentDateandTime = sdf.format(new Date());

		BILL_Mst_Model bill_Mst_Model = new BILL_Mst_Model();
		bill_Mst_Model.setTXN_TYPE(Constants.BILLING_TXN);
		bill_Mst_Model.setBILL_SYS_DATE(currentDateandTime);// Update time

		bill_Mst_Model.setBILL_SCNCD(selectedModel.getBILL_SCNCD());

		HomeHelper homeHelper = new HomeHelper(this);
		homeHelper.updateTXNTyprInBillMstDB(bill_Mst_Model);
	}

	/**
	 * Method- To show all held transactions from BillMST table with TXN_TYPE as
	 * "HE"
	 */
	private void retrieveHeldTransactionsFromDB() {
		HomeHelper homeHelper = new HomeHelper(this);
		homeHelper.selectBillMstRecords(Constants.HELD_TXN,
				DBConstants.dbo_BILL_MST_TABLE, true);
	}

	/**
	 * Method- To remove respective trasca from BillMSt
	 * 
	 * @param mSelectedBillModel2
	 */
	private void removeFromBillMSt(BILL_Mst_Model selectedBillModel) {
		HomeHelper homeHelper = new HomeHelper(this);
		homeHelper.deleteBillMstRecord(selectedBillModel.getBILL_SCNCD(),
				DBConstants.dbo_BILL_MST_TABLE, true);
	}

	private void componentInitialisation(RelativeLayout layout) {
		transactionListview = (ListView) layout
				.findViewById(R.id.held_Txn_Table);
		layout.findViewById(R.id.close_Btn)
				.setOnClickListener(mOnClickListener);
		layout.findViewById(R.id.load_Btn).setOnClickListener(mOnClickListener);
		layout.findViewById(R.id.delete_Btn).setOnClickListener(
				mOnClickListener);

		transactionListview.setOnItemClickListener(mOnItemClickListener);
	}

	/**
	 * Method- To load Held Transactions
	 */
	private void fillDataSet() {
		mTranscAdapter = new HeldTransactionAdapter(mHeldBillMStDataList,
				getActivity());
		transactionListview.setAdapter(mTranscAdapter);
		mTranscAdapter.notifyDataSetChanged();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void executePostAction(OperationalResult opResult) {
		Logger.d(TAG, "executePostAction");
		switch (opResult.getResultCode()) {

		case Constants.FETCH_BILL_MST_RECORD:
			
			mHeldBillMStDataList = (ArrayList<BILL_Mst_Model>) opResult
					.getResult().getSerializable(Constants.RESULTCODEBEAN);
			if (mHeldBillMStDataList != null && mHeldBillMStDataList.size() > 0) {
				MPOSApplication.bHeldTxnAvailable = true;
				
				UserOptionFragment hm=(UserOptionFragment.getInstance());
				if(hm != null)
				hm.setZedReportButton(false);
				Logger.d(TAG,
						"HELD_BILL REcords:" + mHeldBillMStDataList.toString());
				fillDataSet();
			} else {

			}
			break;

		case Constants.FETCH_BILL_TXN_RECORD:
			Logger.d(TAG, "FETCH_BILL_TXN_RECORD");
			mBillTransactionModels = (ArrayList<BillTransactionModel>) opResult
					.getResult().getSerializable(Constants.RESULTCODEBEAN);
			if (mBillTransactionModels != null
					&& mBillTransactionModels.size() > 0) {
				
				
				if (isDeleteHeld) {
					deleteSelectedModelFromList();
				} else {
					
					
					if (isBillTxnTypeUpdateComplete) {
						isBillTxnTypeUpdateComplete = false;
						UserSingleton.getInstance(getContext()).mUniqueTrasactionNo = mSelectedBillModel
								.getBILL_SCNCD();
						Bundle bundle = new Bundle();
						bundle.putSerializable(Constants.BUNDLE_KEY,
								mBillTransactionModels);
						mCallBack.deliverDataToFragement(bundle);
						if (getDialog() != null && getDialog().isShowing()) {
							getDialog().dismiss();
						}
					} else {
						// Update TXn Type
						updateTxnTypeAllBillTxnRecords();
					}
				}
			}
			break;

		case Constants.UPDATE_BILL_TXN_TYPE_RECORD:
			Logger.d(TAG, "UPDATE_BILL_TXN_TYPE_RECORD");

			// Fetch updated Bill Tn records and then refresh bill panel
			isBillTxnTypeUpdateComplete = true;
			loadSelectedModelInBillPanel(mSelectedBillModel);
			break;

		case Constants.UPDATE_BILL_MST_TXN_TYPE_RECORD:// Hold Transaction make
														// billing
			Logger.d(TAG, "UPDATE_BILL_MST_TXN_TYPE_RECORD done");
			loadSelectedModelInBillPanel(mSelectedBillModel);
			break;

		case Constants.INSERT_HELD_TXN:
			Logger.d(TAG, "INSERT_HELD_TXN done");

			// Remove the respective HEld trasaction fromBill_MST
			if (mSelectedBillModel != null)
				removeFromBillMSt(mSelectedBillModel);
			else
				removeFromBillMSt(mHeldBillMStDataList
						.get(previousSelectedPosition));

			break;

		case Constants.DELETE_BILL_MST_HELD_RECORD:
			Logger.d(TAG, "DELETE_BILL_MST_HELD_RECORD done");
			mHeldBillMStDataList.remove(previousSelectedPosition);
			if (mHeldBillMStDataList != null && mHeldBillMStDataList.size() > 0)
				mTranscAdapter.notifyDataSetChanged();
			else
			{
				MPOSApplication.bHeldTxnAvailable = false;
				UserOptionFragment hm=(UserOptionFragment.getInstance());
				if(hm != null)
				hm.setZedReportButton(true);
				
				getDialog().dismiss();
				
			}
			break;

		}
	}

	/**
	 * Method- To update txn_type of all bill txn records of particular txn no.
	 */
	private void updateTxnTypeAllBillTxnRecords() {
		if (mBillTransactionModels != null && mBillTransactionModels.size() > 0) {
			HomeHelper homeHelper = new HomeHelper(this);
			homeHelper.updateTXNTypeInBillTXNDB(mBillTransactionModels, false);
		}
	}

	@Override
	public void errorReceived(OperationalResult opResult) {
		// TODO Auto-generated method stub

	}

	@Override
	public Context getContext() {
		// TODO Auto-generated method stub
		return getActivity();
	}
}
