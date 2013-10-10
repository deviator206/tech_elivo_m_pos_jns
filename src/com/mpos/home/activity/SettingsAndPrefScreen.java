package com.mpos.home.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.mpos.R;
import com.mpos.application.BaseActivity;
import com.mpos.application.MPOSApplication;
import com.mpos.framework.common.Config;
import com.mpos.framework.common.Constants;
import com.mpos.framework.common.Logger;
import com.mpos.framework.inf.FragmentCommunicationInterface;

public class SettingsAndPrefScreen extends DialogFragment {

	private View settingPrefView;

	private Spinner userInterfaceSpinner;
	private Spinner showbillSpinner;
	private Spinner lockOrientationSpinner;
	private EditText sessionTimeOutET;

	private FragmentCommunicationInterface mCallBack;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.settings_pref, container, false);
		getDialog().setTitle(getResources().getString(R.string.setting_pref));
		settingPrefView = view.findViewById(R.id.set_preflayout);
		componentInitialisation();
		return view;
	}

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

	private void componentInitialisation() {

		settingPrefView.findViewById(R.id.close_Btn).setOnClickListener(
				mOnclickListener);
		
		settingPrefView.findViewById(R.id.new_close_btn).setOnClickListener(
				mOnclickListener);
		userInterfaceSpinner = (Spinner) settingPrefView
				.findViewById(R.id.userInterfaceSpinner);
		showbillSpinner = (Spinner) settingPrefView
				.findViewById(R.id.showbillspinner);
		
		lockOrientationSpinner = (Spinner) settingPrefView
				.findViewById(R.id.lock_orientation_spinner);
		
		sessionTimeOutET = (EditText) settingPrefView
				.findViewById(R.id.sessiontimeoutET);
		// lockOrientationSpinner.setOnItemClickListener(mOnItemClickListener);
		
		SharedPreferences prefs = getActivity().getSharedPreferences("com.mpos",
				Context.MODE_PRIVATE);
		sessionTimeOutET.setText(""+prefs.getInt(MPOSApplication.SESSTION_TIME_OUT, 2));
		
		List<String> list = new ArrayList<String>();
		list.add("English");
		list.add("Arabic");
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(
				getActivity(), R.layout.spinner_layout, list);
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		userInterfaceSpinner.setAdapter(dataAdapter);

		List<String> showbilllist = new ArrayList<String>();
		if(Config.BILL_PANEL_POSITION==Constants.LEFT)
		{
		showbilllist.add("Left");
		showbilllist.add("Right");
		} else{
			showbilllist.add("Right");
			showbilllist.add("Left");
		}
		
		ArrayAdapter<String> showBillAdapter = new ArrayAdapter<String>(
				getActivity(), R.layout.spinner_layout, showbilllist);
		showBillAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		showbillSpinner.setAdapter(showBillAdapter);

		List<String> orientationlist = new ArrayList<String>();
		orientationlist.add("Landscape");
		// orientationlist.add("Portrait");
		ArrayAdapter<String> lockOrientationAdapter = new ArrayAdapter<String>(
				getActivity(), R.layout.spinner_layout, orientationlist);
		lockOrientationAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		lockOrientationSpinner.setAdapter(lockOrientationAdapter);
	}

	private OnItemClickListener mOnItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View view, int position,
				long arg3) {
			switch (view.getId()) {
			case R.id.showbillspinner:
				if (position == 0) {
					Config.BILL_PANEL_POSITION = Constants.LEFT;
				} else {
					Config.BILL_PANEL_POSITION = Constants.RIGHT;
				}
				break;

			case R.id.userInterfaceSpinner:

				break;

			case R.id.lock_orientation_spinner:
				if (position == 0) {

				} else {
					Config.BILL_PANEL_POSITION = Constants.PORTRAITFT;
				}
				break;
			}
		}
	};

	private OnClickListener mOnclickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.close_Btn:

				if(Integer.parseInt(sessionTimeOutET.getText().toString())>=1){
				SharedPreferences prefs = getActivity().getSharedPreferences("com.mpos",
						Context.MODE_PRIVATE);
				Editor prefEditor = prefs.edit();
				
				if (showbillSpinner.getSelectedItem().toString()
						.equalsIgnoreCase("left")) {
					Config.BILL_PANEL_POSITION = Constants.LEFT;
				} else {
					Config.BILL_PANEL_POSITION = Constants.RIGHT;
				}

				if (lockOrientationSpinner.getSelectedItem().toString()
						.equalsIgnoreCase("landscape")) {
					Config.ORINTATION = Constants.LANDSCAPE;
				} else {
					Config.ORINTATION = Constants.PORTRAITFT;
				}

				if(!sessionTimeOutET.getText().toString().equalsIgnoreCase("")){
					prefEditor.putInt(MPOSApplication.SESSTION_TIME_OUT, Integer.parseInt(sessionTimeOutET.getText().toString()));
					if(MPOSApplication.waiter!=null){
						MPOSApplication.waiter.setPeriod(Integer.parseInt(sessionTimeOutET.getText().toString()));
					}
				}
				
				prefEditor.putInt(MPOSApplication.ORIENTATION, Config.ORINTATION);
				prefEditor.putInt(MPOSApplication.BILL_POSITION, Config.BILL_PANEL_POSITION);
				prefEditor.commit();
				
				
				Logger.e("settings", "ORINTATION" + Config.ORINTATION
						+ "BILL_PANEL_POSITION" + Config.BILL_PANEL_POSITION);
				Bundle bundle = new Bundle();
				bundle.putString(Constants.BUNDLE_KEY, "Setting_pref");
				mCallBack.deliverDataToFragement(bundle);
				dismiss();
				} else{
					BaseActivity baseac=(BaseActivity) getActivity();
					baseac.showAlertMessage("ENTER VALID SESSION TIMEOUT ", "SESSION TIMEOUT SHOULD BE GREATER THEN OR MINIMUM 1",false);
				}
				break;
				
			case R.id.new_close_btn:
				dismiss();
				break;

			}
		}
	};

}
