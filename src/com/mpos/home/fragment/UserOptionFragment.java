package com.mpos.home.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.mpos.R;
import com.mpos.application.MPOSApplication;
import com.mpos.framework.Util.UserSingleton;
import com.mpos.framework.common.Constants;
import com.mpos.framework.common.Logger;
import com.mpos.framework.inf.NetworkChangeListener;
import com.mpos.home.activity.HomeActivity;
import com.mpos.home.activity.SettingsAndPrefScreen;
import com.mpos.transactions.activity.TransactionFragment;
import com.mpos.zreport.activity.ZReportActivity;

public class UserOptionFragment extends Fragment implements
		NetworkChangeListener {

	public static final String TAG = Constants.APP_TAG
			+ UserOptionFragment.class.getSimpleName();

	private Button mUpdateMasterBtn;
	private Button mZReportBtn;
	public static UserOptionFragment userFragment;

	public UserOptionFragment() {
		 userFragment = this;
	}
	 public  static UserOptionFragment getInstance(){
         
         if(userFragment== null){
                 userFragment=new UserOptionFragment();
         }
         return userFragment;
	 }

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View optionView = inflater.inflate(R.layout.option_panel, container,
				false);

		componentInitialisation(optionView);

		// Register to Network change listener
		MPOSApplication.mImplementedNetworkLisContexts.put(
				UserOptionFragment.class.getSimpleName(), this);
		
		
		FragmentManager fm = getActivity().getSupportFragmentManager();
		BillFragment billFragment = new BillFragment();
		
		TransactionFragment transactionDialog = new TransactionFragment();
		transactionDialog.setTargetFragment(billFragment,
				PrdctQtyPrceMesrInstrFragment.BILL_DATA);
		transactionDialog.show(fm, "transaction_fragment");
		transactionDialog.dismiss();
		
		return optionView;
	}

	
	public void setZedReportButton(Boolean bCheck)
	{
		if(bCheck)
		{
			mZReportBtn.setClickable(true);
			mZReportBtn.setBackgroundResource(R.drawable.zed_selector);
		}
		else
		{
			mZReportBtn.setClickable(false);
			mZReportBtn.setBackgroundResource(R.drawable.btn_zed_db);
		}
	}
	private void componentInitialisation(View optionView) {
		mUpdateMasterBtn = (Button) optionView
				.findViewById(R.id.update_master_button);
		mZReportBtn = (Button) optionView.findViewById(R.id.zreport_button);

		optionView.findViewById(R.id.calculator_button).setOnClickListener(
				mOnClickListener);
		optionView.findViewById(R.id.hold_txn_button).setOnClickListener(
				mOnClickListener);
		optionView.findViewById(R.id.settings_pref_button).setOnClickListener(
				mOnClickListener);
		optionView.findViewById(R.id.update_master_button).setOnClickListener(
				mOnClickListener);
		optionView.findViewById(R.id.logout_button).setOnClickListener(
				mOnClickListener);
		optionView.findViewById(R.id.switch_user_button).setOnClickListener(
				mOnClickListener);
		optionView.findViewById(R.id.zreport_button).setOnClickListener(
				mOnClickListener);
		optionView.findViewById(R.id.float_pick_petty_button)
				.setOnClickListener(mOnClickListener);

		// Enable
		if (UserSingleton.getInstance(getActivity()).isNetworkAvailable) {
			mUpdateMasterBtn.setClickable(true);
			mZReportBtn.setClickable(true);
			mUpdateMasterBtn.setBackgroundResource(R.drawable.update_selector);
			mZReportBtn.setBackgroundResource(R.drawable.zed_selector);
		} else {// Disable
			mUpdateMasterBtn.setClickable(false);
			mZReportBtn.setClickable(false);
			mUpdateMasterBtn.setBackgroundResource(R.drawable.btn_update_db);
			mZReportBtn.setBackgroundResource(R.drawable.btn_zed_db);
		}

	}

	private OnClickListener mOnClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			FragmentManager fm = getActivity().getSupportFragmentManager();
			switch (v.getId()) {
			case R.id.calculator_button:
				launchCalculator();
				break;

			case R.id.hold_txn_button:
				BillFragment billFragment = new BillFragment();
				TransactionFragment transactionDialog = new TransactionFragment();
				transactionDialog.setTargetFragment(billFragment,
						PrdctQtyPrceMesrInstrFragment.BILL_DATA);
				transactionDialog.show(fm, "transaction_fragment");
				break;
			case R.id.settings_pref_button:
				SettingsAndPrefScreen settingPrefDialog = new SettingsAndPrefScreen();
				settingPrefDialog.show(fm, "setting_pref_fragment");
				break;

			case R.id.zreport_button:
				Intent reportIntent = new Intent(getActivity(),
						ZReportActivity.class);
				startActivity(reportIntent);
				break;

			case R.id.update_master_button:
				showAlertDialog(
						getResources().getString(
								R.string.alert_update_master));
				break;

			case R.id.logout_button:
				showLogoutAlertDialog(getResources().getString(R.string.alert_logout));
				break;

			case R.id.switch_user_button:
				SwitchUserFragment swuserDialog = new SwitchUserFragment();
				swuserDialog.show(fm, "switch_fragment");
				break;

			case R.id.float_pick_petty_button:
				DialogFragment newFragment = FloatPickPettyFragment
						.newInstance();
				newFragment.show(fm, "Float_Petty_PickUp_Fragment");
				break;
			}
		}
	};

	public void launchCalculator() {

		ArrayList<HashMap<String, Object>> items = new ArrayList<HashMap<String, Object>>();
		final PackageManager pm = getActivity().getPackageManager();
		List<PackageInfo> packs = pm.getInstalledPackages(0);
		for (PackageInfo pi : packs) {
			if (pi.packageName.toString().toLowerCase().contains("calcul")) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("appName", pi.applicationInfo.loadLabel(pm));
				map.put("packageName", pi.packageName);
				items.add(map);
			}
		}

		if (items.size() >= 1) {
			String packageName = (String) items.get(0).get("packageName");
			Intent i = pm.getLaunchIntentForPackage(packageName);
			if (i != null)
				startActivity(i);
		} else {
			// Application not found
		}
	}
	
	private void showAlertDialog(String message) {
		// Show a confirmation dialog
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setCancelable(true)
				.setPositiveButton(getResources().getString(R.string.ok),
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
								
								((HomeActivity) getActivity()).fetchAllMasterData(true);
							}
						})
				.setNegativeButton(getResources().getString(R.string.cancel),
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
							}
						}).setMessage(message);
		AlertDialog alert = builder.create();
		alert.show();
	}
	
	
	private void showLogoutAlertDialog(String message) {
		// Show a confirmation dialog
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setCancelable(true)
				.setPositiveButton(getResources().getString(R.string.ok),
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
								UserSingleton.getInstance(getActivity()).mUserDetail = null;
								getActivity().finish();
								
							}
						})
				.setNegativeButton(getResources().getString(R.string.cancel),
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
							}
						}).setMessage(message);
		AlertDialog alert = builder.create();
		alert.show();
	}


	/**
	 * Method- To listen to network change
	 */
	@Override
	public void networkChangeListener(boolean isNetworkAvailable) {
		Logger.d(TAG, "networkChangeListener");
		if (isNetworkAvailable) {// Available
			mUpdateMasterBtn.setClickable(true);
			mZReportBtn.setClickable(true);
			mUpdateMasterBtn.getBackground().clearColorFilter();
			mZReportBtn.getBackground().clearColorFilter();
		} else {// Not Available
			mUpdateMasterBtn.setClickable(false);
			mZReportBtn.setClickable(false);
			mUpdateMasterBtn.getBackground().setColorFilter(0xFFA3A3A3,
					PorterDuff.Mode.MULTIPLY);
			mZReportBtn.getBackground().setColorFilter(0xFFA3A3A3,
					PorterDuff.Mode.MULTIPLY);
		}
	}

}
