package com.mpos.home.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class MyAlertDialogFragment extends DialogFragment {

	private static String message;
	private static String title;
	public static MyAlertDialogFragment newInstance(String title, String message) {
		MyAlertDialogFragment frag = new MyAlertDialogFragment();
		MyAlertDialogFragment.message = message;
		MyAlertDialogFragment.title =title;
		return frag;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setCancelable(false)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				}).setMessage(MyAlertDialogFragment.message).setTitle(MyAlertDialogFragment.title);
		AlertDialog alert = builder.create();
		alert.setCancelable(false);
		alert.show();
		return alert;

	}
}