package com.mpos.print;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import android.R.bool;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

import com.mpos.R;
import com.mpos.application.MPOSApplication;
import com.mpos.framework.Util.UserSingleton;
import com.mpos.framework.common.Config;
import com.mpos.framework.common.Constants;
import com.mpos.framework.common.DBConstants;
import com.mpos.framework.common.Logger;
import com.mpos.home.fragment.AlertDialogFragment;
import com.mpos.home.fragment.BillFragment;
import com.mpos.home.fragment.PrintContentDialogFragment;
import com.mpos.home.model.BillTransactionModel;

public class KitchenPrintDialog extends DialogFragment implements Runnable {

	public static final String TAG = Constants.APP_TAG
			+ KitchenPrintDialog.class.getSimpleName();

	// private boolean isKItchenPrint;
	private UUID applicationUUID = UUID
			.fromString("00001101-0000-1000-8000-00805F9B34FB");
	private View kitchenBillPrinterView;
	private Spinner kitchenSpinner;
	private Spinner printbillSpinner;
	private BluetoothDevice mBluetoothDevice;
	private BluetoothSocket mBluetoothSocket = null;
	private ProgressDialog mBluetoothConnectProgressDialog;
	private ArrayList<BillTransactionModel> mBillTranModels = null;
	private BluetoothAdapter mBluetoothAdapter;
	private static final int REQUEST_ENABLE_BT = 1;

	public static final int PRINT_BILL = 0;
	public static final int PRINT = 3;
	public static final int DISMISS_PRINT_BILL = 2;

	public static final String ISKITCHEN = "iskitchen";
	private ArrayAdapter<String> billPrintAdapter = null;
	private ArrayAdapter<String> kitchenAdapter;
	private HashMap<String, String> mDeviceMap = null;
	private boolean isKitchenPrint;
	private boolean isBillPrint;
	private boolean isProcessedItem;

	private Button mBillPrnBtn;
	private Button mKitchenPrntBtn;

	private String mBillPrintAddress;
	private DecimalFormat df = Constants.getDecimalFormat();

	@SuppressWarnings("unchecked")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		kitchenBillPrinterView = inflater.inflate(
				R.layout.bill_kitchen_print_dialog, null);
		getDialog().setTitle("Print Bill");

		Bundle bundle = getArguments();
		mBillTranModels = (ArrayList<BillTransactionModel>) bundle
				.getSerializable(BillFragment.BILL_TXN_LIST);

		for (BillTransactionModel model : mBillTranModels) {
			if (model.isProcessedItem()) {
				isProcessedItem = true;
				break;
			}
		}
		//componentInitialization();
		//setPrinterList();
		//sandeep
		createKitchenOrderBill1();
		return kitchenBillPrinterView;
	}

	private void setPrinterList() {
		kitchenAdapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_spinner_item);

		billPrintAdapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_spinner_item);

		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

		if (mBluetoothAdapter != null) {
			Set<BluetoothDevice> mPairedDevices = mBluetoothAdapter
					.getBondedDevices();

			if (mPairedDevices.size() > 0) {
				for (BluetoothDevice mDevice : mPairedDevices) {

					kitchenAdapter.add(mDevice.getName());
					billPrintAdapter.add(mDevice.getName());
					// kitchenAdapter.add(mDevice.getAddress());
					// billPrintAdapter.add(mDevice.getAddress());

					// Add entry in device map
					mDeviceMap.put(mDevice.getName(), mDevice.getAddress());
				}
			} else {
				Logger.v("KItchenPritDialog", "mNoDevices");
				String mNoDevices = "None Paired";// getResources().getText(R.string.none_paired).toString();
				kitchenAdapter.add(mNoDevices);
				billPrintAdapter.add(mNoDevices);
			}
			Logger.v("KItchenPritDialog", "bluetoothadapter null"
					+ kitchenAdapter.getCount());
			if (isProcessedItem) {
				kitchenSpinner.setAdapter(kitchenAdapter);
			}
			printbillSpinner.setAdapter(billPrintAdapter);
		}
	}

	private void componentInitialization() {
		kitchenBillPrinterView.findViewById(R.id.cancelBtn).setOnClickListener(
				mOnclickListener);

		mDeviceMap = new HashMap<String, String>();
/*
		mKitchenPrntBtn = (Button) kitchenBillPrinterView
				.findViewById(R.id.kitchenPrintBtn);
		mKitchenPrntBtn.setOnClickListener(mOnclickListener);

		mBillPrnBtn = (Button) kitchenBillPrinterView
				.findViewById(R.id.billPrintBtn);
		mBillPrnBtn.setOnClickListener(mOnclickListener);

		kitchenSpinner = (Spinner) kitchenBillPrinterView
				.findViewById(R.id.kitchen_printer_spinner);
		if (isProcessedItem) {
			kitchenSpinner.setEnabled(true);
		} else {
			kitchenSpinner.setEnabled(false);
		}
		printbillSpinner = (Spinner) kitchenBillPrinterView
				.findViewById(R.id.bill_printer_spinner);
				*/
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == KitchenPrintDialog.PRINT) {
			if (resultCode == KitchenPrintDialog.PRINT_BILL) {
				if (isKitchenPrint) {
					// Remember device address for bill print
					if (kitchenSpinner.getSelectedItem() != null
							&& !kitchenSpinner
									.getSelectedItem()
									.toString()
									.equalsIgnoreCase(
											getResources().getString(
													R.string.no_print))) {
						printKitchenOrder();
					} else {
						showAlertDialog("No Printer selected.");
					}

				} else {
					// Remember device address for bill print
					if (printbillSpinner.getSelectedItem() != null
							&& !printbillSpinner
									.getSelectedItem()
									.toString()
									.equalsIgnoreCase(
											getResources().getString(
													R.string.no_print))) {
						isBillPrint = true;
						String mBillPrintName = printbillSpinner
								.getSelectedItem().toString();

						mBillPrintAddress = mDeviceMap.get(mBillPrintName);
						if (mBillPrintAddress != null)
							printBill();
					} else {
						showAlertDialog("No Printer selected.");
					}
				}

			} else if (resultCode == KitchenPrintDialog.DISMISS_PRINT_BILL) {
				// dismiss();
			}
		}
	}

	private OnClickListener mOnclickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.cancelBtn:
				dismiss();
				break;

		

			}
		}
	};

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

	protected void showPrintConetentDialog(boolean b) {
		isKitchenPrint = b;
		PrintContentDialogFragment dialog = new PrintContentDialogFragment();
		Bundle bundle = new Bundle();
		bundle.putBoolean(ISKITCHEN, b);
		dialog.setArguments(bundle);
		dialog.setTargetFragment(KitchenPrintDialog.this, PRINT_BILL);
		dialog.show(getActivity().getSupportFragmentManager(),
				"kitchen-fragmet");
	}

	protected void printBill() {
		/*
		 * if (!printbillSpinner.getSelectedItem().toString()
		 * .equalsIgnoreCase(getResources().getString(R.string.no_print))) {
		 * isBillPrint = true; createBill();
		 * 
		 * String mDeviceAddress =
		 * printbillSpinner.getSelectedItem().toString(); mBluetoothDevice =
		 * mBluetoothAdapter .getRemoteDevice(mDeviceAddress);
		 * mBluetoothConnectProgressDialog = ProgressDialog.show( getActivity(),
		 * "Printing Bill...", mBluetoothDevice.getName() + " : " +
		 * mBluetoothDevice.getAddress(), true, false);
		 * 
		 * startPrinterThread(); } else { dismiss(); }
		 */

		if (isBillPrint) {
			// createBill();
			mBluetoothDevice = mBluetoothAdapter
					.getRemoteDevice(mBillPrintAddress);

			mBluetoothConnectProgressDialog = ProgressDialog.show(
					getActivity(),
					"Printing Bill...",
					mBluetoothDevice.getName() + " : "
							+ mBluetoothDevice.getAddress(), true, false);

			startPrinterThread();
		} else {
			dismiss();
		}

	}

	protected void printKitchenOrder() {
		if (!kitchenSpinner.getSelectedItem().toString()
				.equalsIgnoreCase(getResources().getString(R.string.no_print))) {
			// createKitchenOrderBill();
			String mDeviceName = kitchenSpinner.getSelectedItem().toString();

			String mDeviceAddress = mDeviceMap.get(mDeviceName);
			Log.v(TAG, "Coming incoming address " + mDeviceAddress);

			if (mDeviceAddress != null) {
				mBluetoothDevice = mBluetoothAdapter
						.getRemoteDevice(mDeviceAddress);
				mBluetoothConnectProgressDialog = ProgressDialog.show(
						getActivity(),
						"Printing to Kitchen...",
						mBluetoothDevice.getName() + " : "
								+ mBluetoothDevice.getAddress(), true, false);
				isKitchenPrint = true;
				startPrinterThread();
			}
			// pairToDevice(mBluetoothDevice); This method is replaced by
			// progress dialog with thread

		} /*
		 * else { printBill(); }
		 */
	}

	private void startPrinterThread() {
		Thread mBlutoothConnectThread = new Thread(this);
		mBlutoothConnectThread.start();
	}

	private static final String TXN_NUM = "$[TXN_NUM]";
	private static final String CMPNY_NAME = "$[CMPY_NAME]";
	private static final String USER = "$[USER]";
	private static final String DATE = "$[DATE]";

	private void createKitchenOrderBill1() {
		//String[] row = null;
		String printableContent="";
		String printableItems ="";
		//File kitchenFile = new File(DBConstants.KITCHEN_FILE);
		//CSVWriter csvWriter;
			/*
			InputStream is = getActivity().getAssets().open("kitchen.csv");
			Reader reader = new BufferedReader(
					new InputStreamReader(is, "UTF8"));
			Log.v("kitchen fragment", "row" + " " + is.toString());
			CSVReader csvReader = new CSVReader(reader);
			List content = csvReader.readAll();
			csvReader.close();
			
			csvWriter = new CSVWriter(new FileWriter(kitchenFile), ' ',
					CSVWriter.NO_QUOTE_CHARACTER);

			for (Object object : content) {
				row = (String[]) object;

				if (row[0].contains(CMPNY_NAME)) {
					csvWriter.writeNext(new String[] {"!" + Config.COMPANY_NAME + " : kitchen order"});
				} else if (row[0].contains(TXN_NUM)) {
					csvWriter.writeNext(new String[] {String.format("!%1$31s", "Tx no: " + UserSingleton.getInstance(getActivity()).mUniqueTrasactionNo)});
				} else if (row[0].contains(DATE)) {
					csvWriter.writeNext(new String[] {String.format("!%1$31s", "Date: " + (new SimpleDateFormat(Constants.DATE_FORMAT_mm)).format(new Date()))});
				} else if (row[0].contains(USER)) {
					csvWriter.writeNext(new String[] {String.format("!%1$20s %2$10s", "Cashier: " + UserSingleton.getInstance(getActivity()).mUserName, "Tab: " + Config.TAB_NO)});
				} else {
					csvWriter.writeNext(row);
				}
			}
			*/
			
			printableContent = "Company Name :"+Config.COMPANY_NAME;
			printableContent = printableContent +"|Tx no:"+UserSingleton.getInstance(getActivity()).mUniqueTrasactionNo;
			printableContent = printableContent +"|Date:"+(new SimpleDateFormat(Constants.DATE_FORMAT_mm)).format(new Date());
			printableContent = printableContent +"|Cashier:"+UserSingleton.getInstance(getActivity()).mUserName;
			printableContent = printableContent +"|Tab: " + Config.TAB_NO;
			
			
			//csvWriter.writeNext(new String[] {"!desc              code      qty"});
			//csvWriter.writeNext(new String[] {"!-------------------------------"});

			for (int i = 0; i < mBillTranModels.size(); i++) {
					
			
				//if (mBillTranModels.get(i).isProcessedItem()) {
					String printItemDescription ="Description:"+ mBillTranModels.get(i).getPRDCT_LNG_DSCRPTN() + mBillTranModels.get(i).getPRDCT_PRINT_DSCRPTN().trim();
					//int start = 0;
					//boolean done = false;
					//String str = printItemDescription.substring(start);
					printableItems = printableItems +"|"+ printItemDescription +"|Product Code: "+mBillTranModels.get(i).getPRDCT_CODE()+"|Product Quantity:"+ mBillTranModels.get(i).getPRDCT_QNTY();
					/*do {
						if(str.length() <= 32) {
							String insert[] = new String[] {str};
							csvWriter.writeNext(insert);
							done = true;
						} else {
							String insert[] = new String[] {str.substring(0, 32)};
							csvWriter.writeNext(insert);
							str = str.substring(32);
						}
					}while (done == false);*/
					
					
					/*StringBuffer line = new StringBuffer(String.format("               %1$10s %2$5s", 
							mBillTranModels.get(i).getPRDCT_CODE(), 
							(int)mBillTranModels.get(i).getPRDCT_QNTY()
							));
					String insert[] = new String[] {line.toString()};*/
					//csvWriter.writeNext(insert);
				//}
			}

			System.out.println("## PRINTABLE CONTENT HEADER "+printableContent);
			System.out.println("## PRINTABLE CONTENT ITEMS "+printableItems);
			//csvWriter.close();
		
	}

	
	private void createKitchenOrderBill() {
		String[] row = null;

		File kitchenFile = new File(DBConstants.KITCHEN_FILE);
		CSVWriter csvWriter;
		try {
			InputStream is = getActivity().getAssets().open("kitchen.csv");
			Reader reader = new BufferedReader(
					new InputStreamReader(is, "UTF8"));
			Log.v("kitchen fragment", "row" + " " + is.toString());
			CSVReader csvReader = new CSVReader(reader);
			List content = csvReader.readAll();
			csvReader.close();
			
			csvWriter = new CSVWriter(new FileWriter(kitchenFile), ' ',
					CSVWriter.NO_QUOTE_CHARACTER);

			for (Object object : content) {
				row = (String[]) object;

				if (row[0].contains(CMPNY_NAME)) {
					csvWriter.writeNext(new String[] {"!" + Config.COMPANY_NAME + " : kitchen order"});
				} else if (row[0].contains(TXN_NUM)) {
					csvWriter.writeNext(new String[] {String.format("!%1$31s", "Tx no: " + UserSingleton.getInstance(getActivity()).mUniqueTrasactionNo)});
				} else if (row[0].contains(DATE)) {
					csvWriter.writeNext(new String[] {String.format("!%1$31s", "Date: " + (new SimpleDateFormat(Constants.DATE_FORMAT_mm)).format(new Date()))});
				} else if (row[0].contains(USER)) {
					csvWriter.writeNext(new String[] {String.format("!%1$20s %2$10s", "Cashier: " + UserSingleton.getInstance(getActivity()).mUserName, "Tab: " + Config.TAB_NO)});
				} else {
					csvWriter.writeNext(row);
				}
			}

			csvWriter.writeNext(new String[] {"!desc              code      qty"});
			csvWriter.writeNext(new String[] {"!-------------------------------"});

			for (int i = 0; i < mBillTranModels.size(); i++) {

				if (mBillTranModels.get(i).isProcessedItem()) {
					String printItemDescription = mBillTranModels.get(i).getPRDCT_LNG_DSCRPTN() + mBillTranModels.get(i).getPRDCT_PRINT_DSCRPTN().trim();
					int start = 0;
					boolean done = false;
					String str = printItemDescription.substring(start);
					do {
						if(str.length() <= 32) {
							String insert[] = new String[] {str};
							csvWriter.writeNext(insert);
							done = true;
						} else {
							String insert[] = new String[] {str.substring(0, 32)};
							csvWriter.writeNext(insert);
							str = str.substring(32);
						}
					}while (done == false);
					
					StringBuffer line = new StringBuffer(String.format("               %1$10s %2$5s", 
							mBillTranModels.get(i).getPRDCT_CODE(), 
							(int)mBillTranModels.get(i).getPRDCT_QNTY()
							));
					String insert[] = new String[] {line.toString()};
					csvWriter.writeNext(insert);
				}
			}

			csvWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static final String STORE_NAME = "$[STORE_NAME]";
	private static final String STOREADDRESS = "$[STORE_ADDRESS]";
	private static final String PHONENUM = "$[PHONE_NUM]";
	private static final String TRANS = "$[TRANS]";
	private static final String LINE = "--------------------------------";

	private void createBill() {
		String[] row = null;

		File bill_PrintFile = new File(DBConstants.BILL_FILE);
		CSVWriter csvWriter;
		try {
			InputStream is = MPOSApplication.getContext().getAssets()
					.open("bill_print.csv");
			Reader reader = new BufferedReader(
					new InputStreamReader(is, "UTF8"));
			CSVReader csvReader = new CSVReader(reader);
			List content = csvReader.readAll();
			csvReader.close();
			
			csvWriter = new CSVWriter(new FileWriter(bill_PrintFile), ' ',
					CSVWriter.NO_QUOTE_CHARACTER);

			for (Object object : content) {
				row = (String[]) object;

				String column1 = row[1];
				if (column1.contains(TRANS)) {
					column1 = column1.replace(column1, UserSingleton.getInstance(getActivity()).mUniqueTrasactionNo);
				} else if (column1.contains(STORE_NAME)) {
					column1 = column1.replace(column1, Config.COMPANY_NAME);
				} else if (column1.contains(PHONENUM)) {
					column1 = column1.replace(column1, Config.COMPANY_TELE);
				} else if (column1.contains(STOREADDRESS)) {
					column1 = column1.replace(column1,
							Config.COMPANY_ADDRESS.trim());
				}
				String column2 = row[2];
				String column0 = row[0];
				String column3 = row[3];
				String insert[] = { column0, column1, column2, column3 };
				csvWriter.writeNext(insert);
			}
			
			double totalAmount = 0;
			double totalQuantity = 0;
			double totalTax = 0;
			for (int i = 0; i < mBillTranModels.size(); i++) {

				totalAmount += mBillTranModels.get(i).getPRDCT_AMNT();
				totalQuantity += mBillTranModels.get(i).getPRDCT_QNTY();
				totalTax += mBillTranModels.get(i).getPRDCT_VAT_AMNT();

				String printItemDescription = mBillTranModels.get(i).getPRDCT_LNG_DSCRPTN() + mBillTranModels.get(i).getPRDCT_PRINT_DSCRPTN().trim();
				int start = 0;
				boolean done = false;
				String str = printItemDescription.substring(start);
				do {
					if(str.length() <= 32) {
						String insert[] = new String[] {str};
						csvWriter.writeNext(insert);
						done = true;
					} else {
						String insert[] = new String[] {str.substring(0, 32)};
						csvWriter.writeNext(insert);
						str = str.substring(32);
					}
				}while (done == false);
				
				StringBuffer line = new StringBuffer(String.format("%1$11s %2$4s %3$6s %4$8s", 
						mBillTranModels.get(i).getPRDCT_CODE(), 
						(int)mBillTranModels.get(i).getPRDCT_QNTY(),
						df.format(mBillTranModels.get(i).getPRDCT_PRC()),
						df.format(mBillTranModels.get(i).getPRDCT_AMNT())
						));
				String insert[] = new String[] {line.toString()};
				csvWriter.writeNext(insert);
			}
			
			csvWriter.writeNext(new String[] {LINE});
			csvWriter.writeNext(new String[] {String.format("%1$32s", "Tax: " + totalTax)});
			csvWriter.writeNext(new String[] {String.format("Total       %1$4s      %2$10s", (int)totalQuantity, totalAmount)});
			csvWriter.writeNext(new String[] {LINE});
			csvWriter.writeNext(new String[] {String.format("POS# : %1$8s Branch: %2$8s",  Config.TAB_NO, Config.BRANCH_CODE)});
			csvWriter.writeNext(new String[] {"Date: " + (new SimpleDateFormat(Constants.DATE_FORMAT_mm)).format(new Date())});
			csvWriter.writeNext(new String[] {LINE});
			csvWriter.writeNext(new String[] {"No cash refunds.  Please present"});
			csvWriter.writeNext(new String[] {"original receipt for exchange of"});
			csvWriter.writeNext(new String[] {"unused items  within 14 days. No"});
			csvWriter.writeNext(new String[] {"return / exchange for 'As Is' or"});
			csvWriter.writeNext(new String[] {"Special Price."});
			csvWriter.writeNext(new String[] {LINE});
			csvWriter.writeNext(new String[] {"***Thank You! for shopping at***"});
			csvWriter.writeNext(new String[] {"          VASCO Retail          "});
			csvWriter.writeNext(new String[] {LINE});
			csvWriter.writeNext(new String[] {""});
			csvWriter.writeNext(new String[] {LINE});
			csvWriter.writeNext(new String[] {"   Designed and Developed by    "});
			csvWriter.writeNext(new String[] {"    COMPULYNX:+254203747060     "});
			csvWriter.writeNext(new String[] {LINE});

			csvWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void run() {
		try {
			mBluetoothSocket = mBluetoothDevice
					.createRfcommSocketToServiceRecord(applicationUUID);
			Method m;
			try {

				m = mBluetoothDevice.getClass().getMethod("createRfcommSocket",
						new Class[] { int.class });
				mBluetoothSocket = (BluetoothSocket) m.invoke(mBluetoothDevice,
						Integer.valueOf(1));
				Log.d(TAG, ":" + mBluetoothSocket.getRemoteDevice());

			} catch (SecurityException e) {

				e.printStackTrace();
			} catch (NoSuchMethodException e) {

				e.printStackTrace();
			} catch (IllegalArgumentException e) {

				e.printStackTrace();
			} catch (IllegalAccessException e) {

				e.printStackTrace();
			} catch (InvocationTargetException e) {

				e.printStackTrace();
			}

			mBluetoothAdapter.cancelDiscovery();
			mBluetoothSocket.connect(); // Commented,Uncomment it
			mHandler.sendEmptyMessage(0);
		} catch (IOException eConnectException) {
			mBluetoothConnectProgressDialog.dismiss();
			Log.d(TAG, "CouldNotConnectToSocket", eConnectException);
			closeSocket(mBluetoothSocket);
			return;
		}
	}

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (mBluetoothConnectProgressDialog != null
					&& mBluetoothConnectProgressDialog.isShowing())
				mBluetoothConnectProgressDialog.dismiss();
			/*
			 * Toast.makeText(getActivity(),
			 * "Device Connected and send to the printer for printing.",
			 * 5000).show();
			 */
			sendDataToPrinter();
		}
	};

	private Handler mPrintHandler = new Handler() {
		@SuppressLint("NewApi")
		@Override
		public void handleMessage(Message msg) {
			if (isKitchenPrint) {
				// printBill();
				isKitchenPrint = false;
			} else if (isBillPrint) {
				isBillPrint = false;
				// dismiss();
			}
			// close the socket once bill is printed
			// if (mBluetoothSocket != null && mBluetoothSocket.isConnected())
			closeSocket(mBluetoothSocket);
		}
	};

	protected void sendDataToPrinter() {
		Thread t = new Thread() {
			public void run() {
				try {
					OutputStream os = mBluetoothSocket.getOutputStream();
					String fileName = "";
					if (isKitchenPrint) {
						fileName = DBConstants.KITCHEN_FILE;
					} else if (isBillPrint) {
						fileName = DBConstants.BILL_FILE;
					}

					Logger.d(TAG, "Context:" + MPOSApplication.getContext());

					Logger.d(TAG, "FileName:" + fileName);

					File f = new File(fileName);
					FileInputStream is = new FileInputStream(f);

					// InputStream is =
					// MPOSApplication.getContext().openFileInput(fileName);
					ByteArrayOutputStream bos = new ByteArrayOutputStream();
					byte[] b = new byte[1024];
					int bytes = 0;
					while ((bytes = is.read(b)) != -1) {
						bos.write(b, 0, bytes);
					}
					// bos.write(0);
					// bos.flush();
					// Logger.d(TAG, new String(b));

					os.write(bos.toByteArray());
					mPrintHandler.sendEmptyMessage(0);
					// printer specific code you can comment ==== > End
				} catch (Exception e) {
					Log.e("Main", "Exe ", e);
					e.printStackTrace();
				}
			}
		};
		t.start();
	}

	private void closeSocket(BluetoothSocket nOpenSocket) {
		try {
			nOpenSocket.close();
			Log.d(TAG, "SocketClosed");
		} catch (IOException ex) {
			Log.d(TAG, "CouldNotCloseSocket");
		}
	}
}
