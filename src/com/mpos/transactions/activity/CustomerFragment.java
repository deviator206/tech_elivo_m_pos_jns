package com.mpos.transactions.activity;

import java.util.ArrayList;

import org.json.JSONObject;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.mpos.R;
import com.mpos.application.BaseActivity;
import com.mpos.framework.Util.UserSingleton;
import com.mpos.framework.common.Config;
import com.mpos.framework.common.Constants;
import com.mpos.framework.common.DBConstants;
import com.mpos.framework.common.OperationalResult;
import com.mpos.framework.inf.BaseResponseListener;
import com.mpos.home.fragment.MyAlertDialogFragment;
import com.mpos.master.helper.MasterHelper;
import com.mpos.master.model.FLX_POS_Model;
import com.mpos.payment.helper.PaymentHelper;
import com.mpos.transactions.helper.TransactionHelper;
import com.mpos.transactions.model.FLX_POS_TXNModel;

public class CustomerFragment extends DialogFragment implements
		BaseResponseListener {

	private View customerView;
	private EditText billHeadingET1;
	private EditText billHeadingET2;
	private EditText billHeadingET3;
	private EditText billHeadingET4;
	private EditText billHeadingET5;
	private EditText billHeadingET6;
	private EditText billHeadingET7;
	private EditText billHeadingET8;
	private int numOfReq = 0;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		customerView = inflater.inflate(R.layout.customer, container, false);
		componentInitialisation();
		this.getDialog().setTitle(
				getActivity().getResources().getString(R.string.customer));
		numOfReq = 2;
		((BaseActivity) getActivity()).startProgress();
		getFLXPOSTValues();
		getFLXPOSTXNValues();
		return customerView;

	}

	private void getFLXPOSTXNValues() {
		PaymentHelper masterHelper = new PaymentHelper(this);
		String[] queryParams = { UserSingleton.getInstance(getContext()).mUniqueTrasactionNo };
		masterHelper.selectRunningFLXPOSTxnRecords(queryParams,
				DBConstants.dbo_FLX_POS_TXN_TABLE, true);
	}

	private void getFLXPOSTValues() {
		MasterHelper masterHelper = new MasterHelper(this, false);
		masterHelper.selectFLXPOSRecords(null,
				DBConstants.dbo_FLX_POS_MST_TABLE, true);
	}

	private void componentInitialisation() {
		customerView.findViewById(R.id.cancelBtn).setOnClickListener(
				mOnclickListener);
		customerView.findViewById(R.id.doneBtn).setOnClickListener(
				mOnclickListener);
		billHeadingET1 = (EditText) customerView.findViewById(R.id.labelET1);
		billHeadingET2 = (EditText) customerView.findViewById(R.id.labelET2);
		billHeadingET3 = (EditText) customerView.findViewById(R.id.labelET3);
		billHeadingET4 = (EditText) customerView.findViewById(R.id.labelET4);
		billHeadingET5 = (EditText) customerView.findViewById(R.id.labelET5);
		billHeadingET6 = (EditText) customerView.findViewById(R.id.labelET6);
		billHeadingET7 = (EditText) customerView.findViewById(R.id.labelET7);
		billHeadingET8 = (EditText) customerView.findViewById(R.id.labelET8);
		customerView.findViewById(R.id.dummy_id).requestFocus();
	}

	private OnClickListener mOnclickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
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
				dismiss();
				break;

			case R.id.doneBtn:
				makeEntryInFLX_POS_TXN_Table();
				break;
			}
		}

	};

	private void makeEntryInFLX_POS_TXN_Table() {
		TransactionHelper helper = new TransactionHelper(this);
		FLX_POS_TXNModel flx_pos_txn = new FLX_POS_TXNModel();
		flx_pos_txn
				.setBILL_SCNCD(UserSingleton.getInstance(getActivity()).mUniqueTrasactionNo);

		flx_pos_txn.setBILLHEADING1(billHeadingET1.getText().toString());
		flx_pos_txn.setBILLHEADING2(billHeadingET2.getText().toString());

		flx_pos_txn.setBILLHEADING3(billHeadingET3.getText().toString());
		flx_pos_txn.setBILLHEADING4(billHeadingET4.getText().toString());
		flx_pos_txn.setBILLHEADING5(billHeadingET5.getText().toString());
		flx_pos_txn.setBILLHEADING6(billHeadingET6.getText().toString());
		flx_pos_txn.setBILLHEADING7(billHeadingET7.getText().toString());
		flx_pos_txn.setBILLHEADING8(billHeadingET8.getText().toString());

		Config.WS_FLXIBLE = new ArrayList<String>();
		
		Config.WS_FLXIBLE.add(billHeadingET1.getText().toString());
		Config.WS_FLXIBLE.add(billHeadingET2.getText().toString());
		Config.WS_FLXIBLE.add(billHeadingET3.getText().toString());
		Config.WS_FLXIBLE.add(billHeadingET4.getText().toString());
		Config.WS_FLXIBLE.add(billHeadingET5.getText().toString());
		Config.WS_FLXIBLE.add(billHeadingET6.getText().toString());
		Config.WS_FLXIBLE.add(billHeadingET7.getText().toString());
		Config.WS_FLXIBLE.add(billHeadingET8.getText().toString());
		
		
		boolean isDataUnAvailable = flx_pos_txn.getBILLHEADING1()
				.equalsIgnoreCase("")
				&& flx_pos_txn.getBILLHEADING2().equalsIgnoreCase("")
				&& flx_pos_txn.getBILLHEADING3().equalsIgnoreCase("")
				&& flx_pos_txn.getBILLHEADING4().equalsIgnoreCase("")
				&& flx_pos_txn.getBILLHEADING5().equalsIgnoreCase("")
				&& flx_pos_txn.getBILLHEADING6().equalsIgnoreCase("")
				&& flx_pos_txn.getBILLHEADING7().equalsIgnoreCase("")
				&& flx_pos_txn.getBILLHEADING8().equalsIgnoreCase("");
		if (isDataUnAvailable) {
			showAlertMessage(getResources().getString(R.string.alert_message),
					getResources().getString(R.string.alert_wrong_inputs));
		} else {
			helper.insertFLX_POS_TXN_Records(flx_pos_txn,
					DBConstants.dbo_FLX_POS_TXN_TABLE, true);
		}
	}

	@Override
	public void executePostAction(OperationalResult opResult) {
		switch (opResult.getResultCode()) {
		case Constants.FETCH_FLX_POS_RECORD:
			numOfReq--;
			FLX_POS_Model flxPosModel = (FLX_POS_Model) opResult.getResult()
					.getSerializable(Constants.RESULTCODEBEAN);
			setUIComponents(flxPosModel);
			if (numOfReq == 0) {
				((BaseActivity) getActivity()).stopProgress();
			}
			break;

		case Constants.INSERT_FLX_POS_TXN:
			CustomerFragment.this.dismiss();
			break;

		case Constants.FETCH_FLX_POS_TXN:
			numOfReq--;
			FLX_POS_TXNModel mFlxPosTxnModel = (FLX_POS_TXNModel) opResult
					.getResult().getSerializable(Constants.RESULTCODEBEAN);
			if (mFlxPosTxnModel != null) {
				setCustomerData(mFlxPosTxnModel);
			}
			if (numOfReq == 0) {
				((BaseActivity) getActivity()).stopProgress();
			}
			break;
		}
	}

	private void setCustomerData(FLX_POS_TXNModel mFlxPosTxnModel) {
		billHeadingET1.setText(mFlxPosTxnModel.getBILLHEADING1());
		billHeadingET2.setText(mFlxPosTxnModel.getBILLHEADING2());
		billHeadingET3.setText(mFlxPosTxnModel.getBILLHEADING3());
		billHeadingET4.setText(mFlxPosTxnModel.getBILLHEADING4());
		billHeadingET5.setText(mFlxPosTxnModel.getBILLHEADING5());
		billHeadingET6.setText(mFlxPosTxnModel.getBILLHEADING6());
		billHeadingET7.setText(mFlxPosTxnModel.getBILLHEADING7());
		billHeadingET8.setText(mFlxPosTxnModel.getBILLHEADING8());
	}

	private void setUIComponents(FLX_POS_Model flxPosModel) {

		Config.WS_FLXIBLE_LABEL = new ArrayList<String>();
		if (!flxPosModel.getBILLHEADING1().equalsIgnoreCase("")) {
			TextView billheading1 = (TextView) customerView
					.findViewById(R.id.billHeading1);
			billheading1.setText(flxPosModel.getBILLHEADING1());
			customerView.findViewById(R.id.billHeadinglayout1).setVisibility(
					View.VISIBLE);
			Config.WS_FLXIBLE_LABEL.add(flxPosModel.getBILLHEADING1());
		} else {
			customerView.findViewById(R.id.billHeadinglayout1).setVisibility(
					View.GONE);
		}

		if (!flxPosModel.getBILLHEADING2().equalsIgnoreCase("")) {
			TextView billheading2 = (TextView) customerView
					.findViewById(R.id.billHeading2);
			billheading2.setText(flxPosModel.getBILLHEADING2());
			customerView.findViewById(R.id.billHeadinglayout2).setVisibility(
					View.VISIBLE);
			Config.WS_FLXIBLE_LABEL.add(flxPosModel.getBILLHEADING2());
		} else {
			customerView.findViewById(R.id.billHeadinglayout2).setVisibility(
					View.GONE);
		}

		if (!flxPosModel.getBILLHEADING3().equalsIgnoreCase("")) {
			TextView billheading3 = (TextView) customerView
					.findViewById(R.id.billHeading3);
			billheading3.setText(flxPosModel.getBILLHEADING3());
			customerView.findViewById(R.id.billHeadinglayout3).setVisibility(
					View.VISIBLE);
			Config.WS_FLXIBLE_LABEL.add(flxPosModel.getBILLHEADING3());
		} else {
			customerView.findViewById(R.id.billHeadinglayout3).setVisibility(
					View.GONE);
		}

		if (!flxPosModel.getBILLHEADING4().equalsIgnoreCase("")) {
			TextView billheading4 = (TextView) customerView
					.findViewById(R.id.billHeading4);
			billheading4.setText(flxPosModel.getBILLHEADING4());
			customerView.findViewById(R.id.billHeadinglayout4).setVisibility(
					View.VISIBLE);
			Config.WS_FLXIBLE_LABEL.add(flxPosModel.getBILLHEADING4());
		} else {
			customerView.findViewById(R.id.billHeadinglayout4).setVisibility(
					View.GONE);
		}

		if (!flxPosModel.getBILLHEADING5().equalsIgnoreCase("")) {
			TextView billheading5 = (TextView) customerView
					.findViewById(R.id.billHeading5);
			billheading5.setText(flxPosModel.getBILLHEADING5());
			customerView.findViewById(R.id.billHeadinglayout5).setVisibility(
					View.VISIBLE);
			Config.WS_FLXIBLE_LABEL.add(flxPosModel.getBILLHEADING5());
		} else {
			customerView.findViewById(R.id.billHeadinglayout5).setVisibility(
					View.GONE);
		}

		if (!flxPosModel.getBILLHEADING6().equalsIgnoreCase("")) {
			TextView billheading6 = (TextView) customerView
					.findViewById(R.id.billHeading6);
			billheading6.setText(flxPosModel.getBILLHEADING6());
			customerView.findViewById(R.id.billHeadinglayout6).setVisibility(
					View.VISIBLE);
			Config.WS_FLXIBLE_LABEL.add(flxPosModel.getBILLHEADING6());
		} else {
			customerView.findViewById(R.id.billHeadinglayout6).setVisibility(
					View.GONE);
		}

		if (!flxPosModel.getBILLHEADING7().equalsIgnoreCase("")) {
			TextView billheading7 = (TextView) customerView
					.findViewById(R.id.billHeading7);
			billheading7.setText(flxPosModel.getBILLHEADING7());
			customerView.findViewById(R.id.billHeadinglayout7).setVisibility(
					View.VISIBLE);
			Config.WS_FLXIBLE_LABEL.add(flxPosModel.getBILLHEADING7());
		} else {
			customerView.findViewById(R.id.billHeadinglayout7).setVisibility(
					View.GONE);
		}

		if (!flxPosModel.getBILLHEADING8().equalsIgnoreCase("")) {
			TextView billheading8 = (TextView) customerView
					.findViewById(R.id.billHeading8);
			billheading8.setText(flxPosModel.getBILLHEADING8());
			customerView.findViewById(R.id.billHeadinglayout8).setVisibility(
					View.VISIBLE);
			Config.WS_FLXIBLE_LABEL.add(flxPosModel.getBILLHEADING8());
		} else {
			customerView.findViewById(R.id.billHeadinglayout8).setVisibility(
					View.GONE);
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
		DialogFragment newFragment = MyAlertDialogFragment.newInstance(szAlert,
				szMessage);
		newFragment.show(getFragmentManager(), "dialog");

	}

}
