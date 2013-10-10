package com.mpos.home.fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.mpos.R;
import com.mpos.application.BaseActivity;
import com.mpos.framework.Util.UserSingleton;
import com.mpos.framework.common.Config;
import com.mpos.framework.common.Constants;
import com.mpos.framework.common.DBConstants;
import com.mpos.framework.common.Logger;
import com.mpos.framework.common.OperationalResult;
import com.mpos.framework.inf.BaseResponseListener;
import com.mpos.home.adapter.SalesPersonAdapter;
import com.mpos.master.helper.MasterHelper;
import com.mpos.master.model.SLMN_MST_Model;
import com.mpos.payment.helper.PaymentHelper;
import com.mpos.transactions.helper.TransactionHelper;
import com.mpos.transactions.model.SLMN_TXN_Model;

public class SalesPersonFragment extends DialogFragment implements
		BaseResponseListener {

	public static final String TAG = Constants.APP_TAG
			+ SalesPersonFragment.class.getSimpleName();

	private View salesPersonView;
	private ListView salesPersonListView;
	private int previousSelectedPosition = -1;
	private ArrayList<SLMN_MST_Model> SLMN_Mst_Models;
	private SalesPersonAdapter salesAdapter = null;
	private int numberOfRequests = 0;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		salesPersonView = inflater.inflate(R.layout.sales_person, null);
		componentInitialization();
		numberOfRequests = 2;
		getDialog().setTitle(
				getResources().getString(R.string.select_sales_person));
		getSalesPersonData();
		getSelectedSalesPersonForCurrentTransaction();
		return salesPersonView;
	}

	private void getSelectedSalesPersonForCurrentTransaction() {
		PaymentHelper helper = new PaymentHelper(this);
		String[] queryParams = { UserSingleton.getInstance(getContext()).mUniqueTrasactionNo };
		helper.selectRunningSalesmanTxnRecords(queryParams,
				DBConstants.dbo_SLMN_TXN_TABLE, true);
	}

	private void getSalesPersonData() {
		MasterHelper masterHelper = new MasterHelper(this, false);
		masterHelper.selectSLMN_MSTRecords(null,
				DBConstants.dbo_SLMN_MST_TABLE, false);
	}

	private void componentInitialization() {
		salesPersonListView = (ListView) salesPersonView
				.findViewById(R.id.sales_personList);
		View headerView = getActivity().getLayoutInflater().inflate(
				R.layout.sales_person_item, null);
		TextView codeText = (TextView) headerView.findViewById(R.id.codeText);
		codeText.setTextColor(getResources().getColor(R.color.white));
		codeText.setBackgroundResource(R.color.Gray);
		TextView nameText = (TextView) headerView.findViewById(R.id.nameText);
		nameText.setTextColor(getResources().getColor(R.color.white));
		nameText.setBackgroundResource(R.color.Gray);
		salesPersonView.findViewById(R.id.cancelBtn).setOnClickListener(
				mOnClickListener);
		salesPersonView.findViewById(R.id.doneBtn).setOnClickListener(
				mOnClickListener);
		salesPersonListView.addHeaderView(headerView, null, false);
		salesPersonListView.setOnItemClickListener(mOnItemClickListener);
	}

	private OnItemClickListener mOnItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {

			position = position - 1;
			if (previousSelectedPosition != -1)
				SLMN_Mst_Models.get(previousSelectedPosition).setSlected(false);
			previousSelectedPosition = position;
			SLMN_Mst_Models.get(position).setSlected(true);
			salesAdapter.notifyDataSetChanged();
		}
	};

	private OnClickListener mOnClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.cancelBtn:
				dismiss();
				break;

			case R.id.doneBtn:
				if (previousSelectedPosition == -1) {
					showAlertMessage(
							getResources().getString(R.string.alert_message),
							getResources().getString(
									R.string.alert_invalid_saleman));
				} else {
					makeEntryInSLMN_TXN_Table();
				}
				break;
			}
		}

		private void makeEntryInSLMN_TXN_Table() {
			BaseActivity.loadAllConfigData();
			SLMN_MST_Model salesMan = SLMN_Mst_Models
					.get(previousSelectedPosition);
			SLMN_TXN_Model slmn_txn_model = null;

			if (SLMN_Txn_Models != null
					&& SLMN_Txn_Models.get(0).getSlm_code()
							.equalsIgnoreCase(salesMan.getSlmn_code())) {
				// update sales man code
				slmn_txn_model = SLMN_Txn_Models.get(0);
			} else {
				slmn_txn_model = new SLMN_TXN_Model();
				slmn_txn_model.setBill_scncd(UserSingleton
						.getInstance(getActivity()).mUniqueTrasactionNo);
				slmn_txn_model.setBranchCode(Config.BRANCH_CODE);
				slmn_txn_model.setCompanyCode(Config.COMPANY_CODE);
				slmn_txn_model.setSlm_code(salesMan.getSlmn_code());

				slmn_txn_model.setSys_run_date(Constants
						.getFormattedDate(BaseActivity.getServerRunDate()));

				slmn_txn_model.setTill_num(Config.TAB_NO);
				slmn_txn_model.setUsername(UserSingleton
						.getInstance(getActivity()).mUserDetail.getUSR_NM());
			}
			Logger.v(TAG, "slaes man txn" + slmn_txn_model);
			TransactionHelper helper = new TransactionHelper(
					SalesPersonFragment.this);
			helper.insertSLMN_TXN_Records(slmn_txn_model,
					DBConstants.dbo_SLMN_TXN_TABLE, true);
		}
	};

	private ArrayList<SLMN_TXN_Model> SLMN_Txn_Models;

	@SuppressWarnings("unchecked")
	@Override
	public void executePostAction(OperationalResult opResult) {

		switch (opResult.getResultCode()) {
		case Constants.FETCH_SLMN_MST_RECORD:
			numberOfRequests--;
			SLMN_Mst_Models = (ArrayList<SLMN_MST_Model>) opResult.getResult()
					.getSerializable(Constants.RESULTCODEBEAN);
			Logger.v(TAG, "size " + SLMN_Mst_Models.size());
			if (SLMN_Mst_Models != null && SLMN_Mst_Models.size() > 0) {
				Logger.d(TAG, "SLSMAN Data:" + SLMN_Mst_Models.toString());
				salesAdapter = new SalesPersonAdapter(SLMN_Mst_Models,
						getActivity());
				salesPersonListView.setAdapter(salesAdapter);
			}
			if (numberOfRequests == 0) {
				((BaseActivity) getActivity()).stopProgress();
				checkSalesManPresent();
			}
			break;

		case Constants.INSERT_SLMN_TXN:
			dismiss();
			break;

		case Constants.FETCH_SALSMAN_TXN_RECORD:
			numberOfRequests--;
			SLMN_Txn_Models = (ArrayList<SLMN_TXN_Model>) opResult.getResult()
					.getSerializable(Constants.RESULTCODEBEAN);
			if (numberOfRequests == 0) {
				((BaseActivity) getActivity()).stopProgress();
				checkSalesManPresent();
			}
			break;
		}
	}

	private void checkSalesManPresent() {
		if (SLMN_Txn_Models != null && SLMN_Txn_Models.size() == 1) {
			SLMN_TXN_Model salesMan_TXN = SLMN_Txn_Models.get(0);
			int pos = 0;
			for (SLMN_MST_Model salesman : SLMN_Mst_Models) {
				if (salesman.getSlmn_code().equalsIgnoreCase(
						salesMan_TXN.getSlm_code())) {
					previousSelectedPosition = pos;
					salesman.setSlected(true);
					salesAdapter = new SalesPersonAdapter(SLMN_Mst_Models,
							getActivity());
					salesPersonListView.setAdapter(salesAdapter);
					break;
				}
				pos++;
			}
		}
	}

	@Override
	public void errorReceived(OperationalResult opResult) {

	}

	@Override
	public Context getContext() {

		return getActivity();
	}

	public void showAlertMessage(String szAlert, String szMessage) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
		builder.setCancelable(false)
				.setPositiveButton(getResources().getString(R.string.ok),
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
							}
						}).setMessage(szMessage).setTitle(szAlert);
		AlertDialog alert = builder.create();
		alert.setCancelable(false);
		alert.show();

	}

}
