package com.mpos.zreport.activity;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import au.com.bytecode.opencsv.CSVReader;

import com.mpos.R;
import com.mpos.framework.common.Constants;
import com.mpos.framework.common.DBConstants;
import com.mpos.framework.common.Logger;
import com.mpos.home.fragment.AlertDialogFragment;
import com.mpos.print.DeviceListActivity;
import com.mpos.transactions.activity.OfflineTxnUploadHelper;

public class ZRportDialogFragment extends DialogFragment {

	public static final String TAG = Constants.APP_TAG
			+ AlertDialogFragment.class.getSimpleName();

	private static final int REQUEST_ENABLE_BT = 1;
	private Button mPrintBtn = null;
	private Button mCancelBtn = null;
	private LinearLayout mPrinttextLayout;
	private ZReportActivity zRA;

	private OnClickListener mClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.printBtn:
				try {
					zRA.uploadToServer();
					BluetoothAdapter mBluetoothAdapter = BluetoothAdapter
							.getDefaultAdapter();
					if (mBluetoothAdapter == null) {
						Toast.makeText(getActivity(), "No devices available.",
								2000).show();
					} else {
						if (!mBluetoothAdapter.isEnabled()) {
							Intent enableBtIntent = new Intent(
									BluetoothAdapter.ACTION_REQUEST_ENABLE);
							startActivityForResult(enableBtIntent,
									REQUEST_ENABLE_BT);
						} else {
							Intent in = new Intent(getActivity(),
									DeviceListActivity.class);
							getActivity().startActivityForResult(in,
									ZReportActivity.REQUEST_DEVICE);
							dismiss();
						}
					}
					// showAlertDialog("Z report is printing.");
				} catch (ActivityNotFoundException ex) {
					showAlertDialog("Please install printer application.");
				}
				break;

			case R.id.cancelBtn:
				dismiss();
				break;

			default:
				break;
			}
		}
	};

	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		switch (requestCode) {

		case REQUEST_ENABLE_BT:
			if (resultCode == Activity.RESULT_OK) {
				// ListPairedDevices();
				Intent in = new Intent(getActivity(), DeviceListActivity.class);
				getActivity().startActivityForResult(in,
						ZReportActivity.REQUEST_DEVICE);
				dismiss();
			} else {
				Toast.makeText(getActivity(), "Bluetooth is disable", 2000)
						.show();
			}
			break;
		}

	};

	public ZRportDialogFragment(ZReportActivity mParent) {
		zRA = mParent;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.z_report_dialog, null);

		TextView alertText = (TextView) view.findViewById(R.id.alertTitleTv);
		mPrintBtn = (Button) view.findViewById(R.id.printBtn);
		mPrintBtn.setOnClickListener(mClickListener);
		mPrinttextLayout = (LinearLayout) view
				.findViewById(R.id.printTextLayout);
		getDialog().setTitle(getResources().getString(R.string.zreport));
		mCancelBtn = (Button) view.findViewById(R.id.cancelBtn);
		mCancelBtn.setOnClickListener(mClickListener);

		// StringBuilder text = createStringFromFile();

		try {
			readCsvFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// try {
		// foo(text.toString());
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		// mPrinttext.setText(text);
		// System.out.println(text);

		return view;
	}

	private void readCsvFile() throws IOException {
		String[] row = null;
		String csvFilename = DBConstants.ZREPORT_FILE;

		CSVReader csvReader = new CSVReader(new FileReader(csvFilename));
		List content = csvReader.readAll();
		// Typeface tf = Typeface.createFromAsset(getActivity().getAssets(),
		// "fonts/slc.TTF");

		for (Object object : content) {
			row = (String[]) object;

			// System.out.println(row[0] + "  " + row[1] + "  " + row[2]);

			TextView t = new TextView(getActivity());
			t.setText(row[0]);
			t.setTextColor(getResources().getColor(R.color.Black));

			t.setTypeface(Typeface.MONOSPACE);
			mPrinttextLayout.addView(t);
		}

		csvReader.close();

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
		alertDialogFragment.show(fm, "ALERT-FRAGMENT");
	}

}
