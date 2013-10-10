/*
 * Copyright (C) 2013 jaggarnaut-studios Pvt. Ltd. All Rights Reserved.
 * jaggarnaut-studios pvt. ltd. PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.mpos.home.fragment;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.mpos.R;
import com.mpos.framework.common.Constants;

/**
 * Description-
 * 
 * @author jaggarnaut-studios
 */

public class AlertDialogFragment extends DialogFragment {

	public static final String TAG = Constants.APP_TAG
			+ AlertDialogFragment.class.getSimpleName();

	private Button mOkBtn = null;
	private Button mCancelBtn = null;

	private OnClickListener mClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.okBtn:
				if (getDialog().isShowing())
					getDialog().dismiss();
				break;

			case R.id.cancelBtn:
				
				break;

			default:
				break;
			}
		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.alert_dialog_fragment, null);

		getDialog().setTitle(getResources().getString(R.string.alert_message));
		
		TextView alertText = (TextView) view.findViewById(R.id.alertTitleTv);
		mOkBtn = (Button) view.findViewById(R.id.okBtn);
		mOkBtn.setOnClickListener(mClickListener);

		mCancelBtn = (Button) view.findViewById(R.id.cancelBtn);
		mCancelBtn.setVisibility(View.GONE);
		mCancelBtn.setOnClickListener(null);

		Bundle bundle = getArguments();
		String errorMessage = bundle.getString(Constants.KEY);
		if (bundle.get(Constants.VALUE) != null
				&& bundle.getString(Constants.VALUE).equalsIgnoreCase("Y")) {
			mCancelBtn.setVisibility(View.VISIBLE);
			mCancelBtn.setOnClickListener(mClickListener);
		}

		alertText.setText(errorMessage);

		return view;
	}

}
