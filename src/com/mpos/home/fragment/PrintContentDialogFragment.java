package com.mpos.home.fragment;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import com.mpos.R;
import com.mpos.framework.common.Constants;
import com.mpos.framework.common.DBConstants;
import com.mpos.print.DeviceListActivity;
import com.mpos.print.KitchenPrintDialog;
import com.mpos.zreport.activity.ZReportActivity;

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
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import au.com.bytecode.opencsv.CSVReader;

public class PrintContentDialogFragment extends DialogFragment {

	public static final String TAG = Constants.APP_TAG
			+ PrintContentDialogFragment.class.getSimpleName();

	private static final int REQUEST_ENABLE_BT = 1;
	private Button mPrintBtn = null;
	private Button mCancelBtn = null;
	private LinearLayout mPrinttextLayout;

	private OnClickListener mClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.printBtn:
				Intent in = new Intent();
				getTargetFragment().onActivityResult(KitchenPrintDialog.PRINT, KitchenPrintDialog.PRINT_BILL,
						in);
				dismiss();
				break;

			case R.id.cancelBtn:
				Intent intent = new Intent();
				getTargetFragment().onActivityResult(KitchenPrintDialog.PRINT, KitchenPrintDialog.DISMISS_PRINT_BILL,
						intent);
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

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.z_report_dialog, null);

		TextView alertText = (TextView) view.findViewById(R.id.alertTitleTv);
		mPrintBtn = (Button) view.findViewById(R.id.printBtn);
		mPrintBtn.setOnClickListener(mClickListener);
		mPrinttextLayout = (LinearLayout) view
				.findViewById(R.id.printTextLayout);
		getDialog().setTitle(getResources().getString(R.string.bill));
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
		Bundle b = getArguments();
		boolean isKitchenBill = b.getBoolean(KitchenPrintDialog.ISKITCHEN);
		String[] row = null;
		String csvFilename ="";
		if(isKitchenBill){
			csvFilename = DBConstants.KITCHEN_FILE;
			CSVReader csvReader = new CSVReader(new FileReader(csvFilename));
			List content = csvReader.readAll();
		//	Typeface tf = Typeface.createFromAsset(getActivity().getAssets(),
			//		"fonts/slc.TTF");
			
			for (Object object : content) {
				row = (String[]) object;

				System.out.println(row);

				TextView t = new TextView(getActivity());
				t.setText(row[0]);
				t.setTextColor(getResources().getColor(R.color.Black));

				t.setTypeface(Typeface.MONOSPACE);
				mPrinttextLayout.addView(t);
			}
			csvReader.close();

		}else{
			csvFilename = DBConstants.BILL_FILE;
			CSVReader csvReader = new CSVReader(new FileReader(csvFilename));
			List content = csvReader.readAll();
		//	Typeface tf = Typeface.createFromAsset(getActivity().getAssets(),
			//		"fonts/slc.TTF");
			
			for (Object object : content) {
				row = (String[]) object;

				TextView t = new TextView(getActivity());
				t.setText(row[0]);
				t.setTextColor(getResources().getColor(R.color.Black));

				t.setTypeface(Typeface.MONOSPACE);
				mPrinttextLayout.addView(t);
			}
			csvReader.close();

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
		alertDialogFragment.show(fm, "ALERT-FRAGMENT");
	}

}
