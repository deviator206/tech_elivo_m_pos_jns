/*

 * Copyright (C) 2013 jaggarnaut-studios Pvt. Ltd. All Rights Reserved.
 * jaggarnaut-studios pvt. ltd. PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mpos.home.fragment;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mpos.R;
import com.mpos.application.BaseActivity;
import com.mpos.application.MPOSApplication;
import com.mpos.framework.Util.UserSingleton;
import com.mpos.framework.common.Config;
import com.mpos.framework.common.Constants;
import com.mpos.framework.common.DBConstants;
import com.mpos.framework.common.Logger;
import com.mpos.framework.common.OperationalResult;
import com.mpos.framework.inf.BaseResponseListener;
import com.mpos.framework.inf.FragmentCommunicationInterface;
import com.mpos.home.adapter.BillAdapter;
import com.mpos.home.helper.HomeHelper;
import com.mpos.home.model.BillTransactionModel;
import com.mpos.login.model.ResponseModel;
import com.mpos.master.model.BILL_Mst_Model;
import com.mpos.master.model.INSTRCTN_MST_Model;
import com.mpos.master.model.UserRightsModel;
import com.mpos.payment.activity.PaymentFragment;
import com.mpos.print.KitchenPrintDialog;
import com.mpos.transactions.activity.CustomerFragment;
import com.mpos.transactions.activity.OfflineTxnUploadHelper;

import com.mpos.transactions.model.Bill_INSTRCTN_TXN_Model;
import com.mpos.transactions.model.PRDCT_CNSMPTN_TXNModel;

/**
 * Description-
 * 
 * @author jaggarnaut-studios
 */
public class BillFragment extends Fragment implements BaseResponseListener {

	private static String METHOD_NAME = "RctPrint";
	// private static final String METHOD_NAME = "HelloWorld";

	private static String NAMESPACE = "http://tempuri.org/";
	// private static final String NAMESPACE = "http://tempuri.org";

	private static String URL = "http://203.109.87.28:4997/PrintBill.svc";
	// private static final String URL =
	// "http://192.168.0.2:8080/webservice1  /Service1.asmx";

	private static String SOAP_ACTION = "";
	// final String SOAP_ACTION = "http://tempuri.org/HelloWorld";

	StringBuilder sb;

	public static final String TAG = Constants.APP_TAG
			+ BillFragment.class.getSimpleName();

	private final String ALERT_FRAGMENT = "ALERT_FRAGMENT";
	private final String KITCHEN_FRAGMENT = "KITCHEN_FRAGMENT";

	private Button logoutBtn;
	private Button mHeldTransactionBtn;
	// UI Components
	private ListView billListView;
	private BillAdapter billAdapter;
	private View listFooterView;
	private View listHeaderView;
	private Button mPrintBtn;
	private Button mNewSaleBtn;
	private View categoryView;
	private View optionPanelView;
	private FragmentCommunicationInterface mCallBack;
	private ArrayList<Bill_INSTRCTN_TXN_Model> mBillInstructionTranModels;
	// JAva
	public static final String BILL_DATA = "BILL_DATA";
	public static final String BILL_TOTAL = "BILL_TOTAL";
	private int numberOfRequests = 0;
	public static final String BILL_TXN_LIST = "BILL_LIST";
	private double billTotalPrice = 0;
	private ArrayList<BillTransactionModel> mBillTranModels = null;
	private ArrayList<BillTransactionModel> mBillHeldTranModels = null;
	public static String typeOfAction;
	private boolean isHoldTxn = false;
	private String preserverUniqTxnNo = "";
	private String sPRINTER_JSON_DATA ="";
	private BillFragment objBillFrag ;

	public BillFragment() {
		objBillFrag = this;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		categoryView = inflater
				.inflate(R.layout.billfragment, container, false);
		optionPanelView = getActivity().findViewById(R.id.option_panel);
		componentInitialisation();
		updateOptionPanelButtonStates(true);
		HomeHelper helper = new HomeHelper(this);

		Logger.d(TAG, "Trans No:"
				+ UserSingleton.getInstance(getContext()).mUniqueTrasactionNo);
		// retrieve only non-voided items and for current transaction no.
		String[] whrArgs = {
				UserSingleton.getInstance(getContext()).mUniqueTrasactionNo,
				Constants.ITEM_NOT_VOIDED };
		numberOfRequests = 2;
		helper.selectBillTXNRecords(whrArgs, DBConstants.dbo_BILL_TXN_TABLE,
				true);

		URL = Config.PRINT_URL;
		SOAP_ACTION = NAMESPACE + "" + Config.SOAP_ACTION + "/"
				+ Config.PRINTER_METHOD;
		METHOD_NAME = Config.PRINTER_METHOD;

		return categoryView;
	}

	@SuppressWarnings("unchecked")
	private void componentInitialisation() {
		categoryView.findViewById(R.id.paymentBtn).setOnClickListener(
				mOnClickListener);
		categoryView.findViewById(R.id.holdBtn).setOnClickListener(
				mOnClickListener);
		categoryView.findViewById(R.id.customerBtn).setOnClickListener(
				mOnClickListener);
		categoryView.findViewById(R.id.salespersonBtn).setOnClickListener(
				mOnClickListener);

		logoutBtn = (Button) optionPanelView.findViewById(R.id.logout_button);
		mHeldTransactionBtn = (Button) optionPanelView.findViewById(R.id.hold_txn_button);

		mNewSaleBtn = (Button) categoryView.findViewById(R.id.cancelsaleBtn);
		mNewSaleBtn.setOnClickListener(mOnClickListener);
		mNewSaleBtn.setClickable(true);

		/*mPrintBtn = (Button) categoryView.findViewById(R.id.printBtn);
		mPrintBtn.setOnClickListener(mOnClickListener);
		mPrintBtn.setClickable(false);
*/
		if (UserSingleton.getInstance(getContext()).isPaymentDone) {
			//mPrintBtn.setClickable(true);
			mNewSaleBtn.setClickable(true);
		}

		categoryView.findViewById(R.id.voidItemBtn).setOnClickListener(
				mOnClickListener);

		billListView = (ListView) categoryView.findViewById(R.id.billListView);
		listFooterView = LayoutInflater.from(getActivity()).inflate(
				R.layout.bill_footer, null);
		billListView.addFooterView(listFooterView, null, false);

		listHeaderView = LayoutInflater.from(getActivity()).inflate(
				R.layout.billl_list_item, null);
		listHeaderView
				.setBackgroundColor(getResources().getColor(R.color.Gray));
		listHeaderView.findViewById(R.id.check).setVisibility(View.GONE);
		billListView.addHeaderView(listHeaderView, null, false);

		if (getArguments() != null) {
			Bundle bundle = getArguments();
			mBillTranModels = (ArrayList<BillTransactionModel>) bundle
					.getSerializable(BILL_DATA);
			if (mBillTranModels != null && mBillTranModels.size() > 0) {
				billListView.setAdapter(new BillAdapter(mBillTranModels,
						mBillInstructionTranModels, mINSTRCTNDataList,
						getActivity().getBaseContext()));
			} else {
				billListView.setAdapter(new BillAdapter(null, null, null,
						getActivity().getBaseContext()));
			}
		} else {
			billListView.setAdapter(new BillAdapter(null, null, null,
					getActivity().getBaseContext()));
		}

		// Once payment is done, disable all not required buttons
		if (UserSingleton.getInstance(getContext()).isPaymentDone) {
			categoryView.findViewById(R.id.paymentBtn).setClickable(false);
			categoryView.findViewById(R.id.paymentBtn).setBackgroundResource(
					R.drawable.btn_payment_db);

			categoryView.findViewById(R.id.holdBtn).setClickable(false);
			categoryView.findViewById(R.id.holdBtn).setBackgroundResource(
					R.drawable.btn_hold_db);

			categoryView.findViewById(R.id.customerBtn).setClickable(false);
			categoryView.findViewById(R.id.customerBtn).setBackgroundResource(
					R.drawable.btn_customer_db);

			categoryView.findViewById(R.id.salespersonBtn).setClickable(false);
			categoryView.findViewById(R.id.salespersonBtn)
					.setBackgroundResource(R.drawable.btn_sales_db);

			categoryView.findViewById(R.id.voidItemBtn).setClickable(false);
			categoryView.findViewById(R.id.voidItemBtn).setBackgroundResource(
					R.drawable.btn_void_db);

			billListView.setClickable(false);
		}

	}

	public void call() {
		try {

			SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

			String strTemp = "{\"billMst\":[{\"brnchCode\":\"02\",\"billNo\":\"5\",\"billAmnt\":\"6792\",\"billScncd\":\"94021409131702535\",\"txnType\":\"HE\",\"cmpnyCode\":\"01\",\"tillNo\":\"4\",\"amntPaid\":\"6792\",\"chngGvn\":\"0\",\"usrName\":\"ADMIN\",\"billScnd\":\"\",\"cardno\":\"\",\"shiftCode\":\"\",\"tbleCode\":\"T001\",\"uploadFlg\":\"Y\",\"billTxn\":[],\"slmnTxn\"\"userName\":null,\"brnchCode\":null,\"billScncd\":null,\"cmpnyCode\":null,\"tillNo\":null,\"uploadFlg\":\"Y\",\"slmCode\":null,\"sysRunDate\":\"2013-09-14 17:04:31\"},\"heldTxn\":[{\"scanCode\":\"\",\"brnchCode\":\"02\",\"billScncd\":\"94021409131702535\",\"txnType\":\"HE\",\"cmpnyCode\":\"01\",\"uploadFlg\":\"Y\",\"prdctCode\":\"13094-900\",\"prdctPrc\":\"849\",\"prdctQnty\":\"8\",\"prdctAmnt\":\"6792\",\"prdctVoid\":\"N\",\"prdctScnd\":\"\",\"billPrntd\":\"N\",\"emptyDone\":\"\",\"zedNo\":\"208\",\"srNo\":\"5\",\"pack\":\"\",\"packqty\":\"1\",\"packprce\":\"0\",\"linedisc\":\"0\",\"heldUsr\":\"\",\"billRunDate\":\"2013-09-08 00:00:00\",\"prdctLngDscrptn\":\"TEAGAN S   BLACK\",\"prdctDscnt\":\"0\",\"prdctPymntModeDscnt\":\"0\",\"prdctVatCode\":\"E\",\"prdctVatAmnt\":\"0\",\"usrAnthntctd\":\"\",\"slsManCode\":\"\",\"prdctVatExmpt\":\"\",\"prdctCostPrce\":\"424.5\",\"autoGrnDone\":\"\",\"billInstrctnTxn\"\"brnchCode\":\"\",\"billScncd\":\"\",\"cmpnyCode\":\"\",\"prdctCode\":\"\",\"prdctVoid\":\"\",\"pack\":\"\",\"instrctnCode\":\"\",\"extraInstrctns\":\"\",\"instrctnQty\":\"\"},\"consmptnTxn\"\"brnchCode\":\"\",\"txnType\":\"\",\"cmpnyCode\":\"\",\"prdctCode\":\"\",\"prdctVoid\":\"\",\"txnNo\":\"\",\"stkQty\":\"\",\"prdctPrce\":\"\",\"txnMode\":\"\",\"pck\":\"\",\"prdctVatCode\":\"\",\"prdctVatAmnt\":\"\",\"stkUomCode\":\"\",\"recipeCode\":\"\"}}],\"billRunDate\":\"2013-09-08 00:00:00\",\"billSysDate\":\"2013-09-14 17:04:29\",\"billVatExmpt\":\"\",\"billVatAmnt\":\"0\",\"billStatus\":\"\",\"billAmened\":\"\",\"billAmndStatus\":\"\",\"billLocked\":\"\",\"flexPosTxn\"\"brnchCode\":\"\",\"billScncd\":\"\",\"cmpnyCode\":\"\",\"uploadFlg\":\"\",\"billHdValue1\":\"\",\"billHdValue2\":\"\",\"billHdValue3\":\"\",\"billHdValue4\":\"\",\"billHdValue5\":\"\",\"billHdValue6\":\"\",\"billHdValue7\":\"\",\"billHdValue8\":\"\"},\"paymentMst\":[]}],\"tabMacAddress\":\"00fa34161bc4\"}";
			String port = "1";

			String portType = "LPT",

			printerType = "THERMAL", printerModel = "EPSON", brName = "WAFI", cmpanyName = "VASCO WORLDWIDE", flxble = "Customer Name: Test Name|Policy No: 12346|Date: 12213";

			request.addProperty("JsonObject", strTemp);
			request.addProperty("port", port);
			request.addProperty("portType", portType);
			request.addProperty("printerType", printerType);
			request.addProperty("printerModel", printerModel);
			request.addProperty("brName", brName);
			request.addProperty("cmpanyName", cmpanyName);
			request.addProperty("flxble", flxble);

			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);
			envelope.dotNet = true;

			envelope.setOutputSoapObject(request);

			HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
			androidHttpTransport.call(SOAP_ACTION, envelope);
			SoapPrimitive result = (SoapPrimitive) envelope.getResponse();

			// to get the data
			String resultData = result.toString();
			// 0 is the first object of data

			sb.append(resultData + "\n");
		} catch (Exception e) {
			e.printStackTrace();
			sb.append("Error:\n" + e.getMessage() + "\n");
		}

		System.out.println("## RESPONSE :: " + sb.toString());
		showAlertDialog("Response Rcvd" + sb.toString());
	}

	private OnClickListener mOnClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			FragmentManager fm = getActivity().getSupportFragmentManager();

			switch (v.getId()) {
			case R.id.voidItemBtn:

				int VOID_LIMIT = -1;

				if (UserSingleton.getInstance(getContext()).mUserAssgndRightsModel != null) {
					VOID_LIMIT = UserSingleton.getInstance(getContext()).mUserAssgndRightsModel.VOID_LIMIT_MAX;
				}

				Logger.v(
						TAG,
						"void limit"
								+ VOID_LIMIT
								+ " "
								+ UserSingleton.getInstance(getContext()).mvoidLimitCount);

				if (VOID_LIMIT == -1
						|| VOID_LIMIT > UserSingleton.getInstance(getContext()).mvoidLimitCount) {

					if (mBillTranModels != null && mBillTranModels.size() > 0) {
						processVoidItem();
					}

				} else {
					typeOfAction = "void item";
					UserAccessFragment user = new UserAccessFragment();
					user.setTargetFragment(BillFragment.this,
							UserAccessFragment.USER_ACCESS);
					user.show(getActivity().getSupportFragmentManager(),
							"user-fragmet");
				}

				break;

			case R.id.holdBtn:
				// Show Transaction Hold confirmation dialog only when there are
				// items
				// in Bill Panel
				int HELD_LIMIT = -1;

				if (UserSingleton.getInstance(getContext()).mUserAssgndRightsModel != null) {
					HELD_LIMIT = UserSingleton.getInstance(getContext()).mUserAssgndRightsModel.VOID_LIMIT_MAX;
				}

				if (HELD_LIMIT == -1
						|| HELD_LIMIT > UserSingleton.getInstance(getContext()).mheldLimitCount) {

					if (mBillTranModels != null && mBillTranModels.size() > 0) {
						showAlertDialog(
								getResources().getString(
										R.string.alert_hold_bill_panel), true);
					}

				} else {
					typeOfAction = "held item";
					UserAccessFragment user = new UserAccessFragment();
					user.setTargetFragment(BillFragment.this,
							UserAccessFragment.USER_ACCESS);
					user.show(getActivity().getSupportFragmentManager(),
							"user-fragmet");
				}
				break;

			case R.id.cancelsaleBtn:
				// Show New sale confirmation dialog only when there are items
				// in Bill Panel
				if (UserSingleton.getInstance(getContext()).isPaymentDone)
				{
					startNewSale();
				
				}
				else
				{
					if (mBillTranModels != null && mBillTranModels.size() > 0)
						showAlertDialog(
								getResources().getString(
										R.string.alert_void_bill_panel), false);
				}
				//mPrintBtn.setClickable(false);
				break;

			case R.id.printBtn:
				if (((Button) v)
						.getText()
						.toString()
						.equalsIgnoreCase(
								getResources().getString(R.string.re_print))) {
					if (!UserSingleton.getInstance(getContext()).mUserAssgndRightsModel.alllow_BILL_TO_RE_PRINT) {
						typeOfAction = "bill reprint";
						UserAccessFragment user = new UserAccessFragment();
						user.setTargetFragment(BillFragment.this,
								UserAccessFragment.USER_ACCESS);
						user.show(getActivity().getSupportFragmentManager(),
								"user-fragmet");
					}
				} else {
					if (mBillTranModels != null && mBillTranModels.size() > 0) {

						// call();
						((BaseActivity) getActivity()).startProgress();
						new UploadPrintData().execute("test");
						
						// printItems();
						// OfflineTxnUploadHelper txnUploadHelper = new OfflineTxnUploadHelper(true, objBillFrag);
                        // txnUploadHelper.uploadJsonData(UserSingleton.getInstance(getContext()).mUniqueTrasactionNo);

//							showAlertDialog(
//									getResources().getString(
//											R.string.alert_void_bill_panel), false);
					} 
					
					mPrintBtn.setClickable(false);
					
//					else {
//						showBillPrintDialog("You don't have any transaction to print.");
//					}
				}
				break;

			case R.id.paymentBtn:
				if (mBillTranModels != null && mBillTranModels.size() > 0) {
					Intent in = new Intent(getActivity(), PaymentFragment.class);
					in.putExtra(BillFragment.BILL_TOTAL, billTotalPrice);
					startActivity(in);
				}
				// PaymentFragment paymentDialog = new PaymentFragment();
				// paymentDialog.show(fm, "payment_fragment");
				break;

			case R.id.salespersonBtn:
				SalesPersonFragment sales_PersontDialog = new SalesPersonFragment();
				sales_PersontDialog.show(fm, "sales_person_fragment");
				break;

			// case R.id.changeBtn:// Issue Change
			// IsssueChangeFragment changeFrag = new IsssueChangeFragment();
			// Bundle bundle = new Bundle();
			// bundle.putDouble(BillFragment.BILL_TOTAL, billTotalPrice);
			// changeFrag.setArguments(bundle);
			// changeFrag.show(fm, "issue_chng_fragment");
			// break;

			case R.id.customerBtn:
				CustomerFragment customerDialog = new CustomerFragment();
				customerDialog.show(fm, "customer_fragment");
				break;
			}
		}

	};

	private ArrayList<INSTRCTN_MST_Model> mINSTRCTNDataList;

	private void getModifierData() {
		Logger.d(TAG + "V", "getModifierData");
		numberOfRequests = 2;
		String[] billInstTxnWhrArgs = { UserSingleton.getInstance(getContext()).mUniqueTrasactionNo };
		HomeHelper helper = new HomeHelper(this);
		helper.selectBillInstrc_TXNRecords(billInstTxnWhrArgs,
				DBConstants.dbo_BILL_INSTRCTN_TXN_TABLE, true,
				DBConstants.BILL_INSTRCTN_TXN_BILL_SCND + "=?");
		retrieveModifierDBData();
	}

	private void printItems() {
		FragmentManager fm = getActivity().getSupportFragmentManager();
		KitchenPrintDialog kitchenDialogFragment = new KitchenPrintDialog();
		Bundle bundle = new Bundle();
		bundle.putSerializable(BillFragment.BILL_TXN_LIST, mBillTranModels);
		kitchenDialogFragment.setArguments(bundle);
		kitchenDialogFragment.show(fm, KITCHEN_FRAGMENT);

		// Intent i = new Intent(Intent.ACTION_VIEW);
		// i.setPackage("com.dynamixsoftware.printershare");
		// i.setDataAndType(Uri.parse("file:///sdcard/"), "text/plain");
		// startActivity(i);
	}

	/**
	 * To handle void item
	 * 
	 * @param v
	 */
	protected void processVoidItem() {
		Logger.d(TAG, "processVoidItem");
		ArrayList<BillTransactionModel> deletedItemArray = new ArrayList<BillTransactionModel>();
		for (int j = 0; j < mBillTranModels.size(); j++) {
			if (mBillTranModels.get(j).isVoidChecked()) {
				deletedItemArray.add(mBillTranModels.get(j));
			}
		}

		System.out.println(" SIZE OF DELETED SIZE :::"+deletedItemArray.size());	
		if (deletedItemArray.size() > 0) {
			for (BillTransactionModel removeModel : deletedItemArray) {
				removeModel.setPRDCT_VOID(Constants.ITEM_VOIDED); // Item Voided
																	// status
				removeModel.setUSR_ANTHNTCTD(UserSingleton
						.getInstance(getContext()).mUsr_Anthntctd); // User who
																	// authenticated
																	// it
				UserSingleton.getInstance(getContext()).mvoidLimitCount++;
				updateBillTxTable(removeModel);
				updateCnsmptnTxTable(removeModel);
				updateBillInstrctnTxTable(removeModel);

				mBillTranModels.remove(removeModel);
			}
		}

		if (mBillTranModels != null && mBillTranModels.size() > 0) {
			// disables button
			// logoutBtn.setEnabled(false);
			// Sets button background. Right now disabled button image is not
			// available
			// logoutBtn.setBackgroundResource(R.drawable.btn_logout_db);
			getModifierData();
		} else {
			// enables button
			// logoutBtn.setEnabled(true);
			// sets background
			// logoutBtn.setBackgroundResource(R.drawable.logout_selector);
			
			//enable the Option Panel buttons
			updateOptionPanelButtonStates(true);
			
			billListView.setAdapter(new BillAdapter(null, null, null,
					getActivity().getBaseContext()));
			/*((TextView) listFooterView.findViewById(R.id.totalqtyText))
					.setText("0");
			((TextView) listFooterView.findViewById(R.id.totalpriceText))
					.setText("0.0");*/
			((TextView) listFooterView.findViewById(R.id.taxpriceText))
					.setText("0.0");
			
			((TextView) categoryView.findViewById(R.id.totalpriceText)).setText("0.0");
			((TextView) categoryView.findViewById(R.id.totalqtyText)).setText("0");
		}
	}

	/**
	 * Method- To update the Bill Instruction Txn table for voided product
	 * 
	 * @param removeModel
	 */
	private void updateBillInstrctnTxTable(BillTransactionModel removeModel) {
		HomeHelper homeHelper = new HomeHelper(this);
		homeHelper.retrieveBillInstrctnData(removeModel);
	}

	/**
	 * Method- To update the CNSMPTN_TXN table for voided product. First
	 * retrieve the particular cnsmptn model, update the model with values and
	 * then update in DB
	 * 
	 * @param removeModel
	 */
	private void updateCnsmptnTxTable(BillTransactionModel removeModel) {
		HomeHelper homeHelper = new HomeHelper(this);
		homeHelper.retrieveCnsmptnData(removeModel);
	}

	/**
	 * Method- To update the Bill_TXN table for voided product
	 * 
	 * @param removeModel
	 */
	private void updateBillTxTable(BillTransactionModel removeModel) {
		HomeHelper homeHelper = new HomeHelper(this);
		homeHelper.updatePrdctDataInBillTXNDB(removeModel);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		Logger.d(TAG, "onActivityResult");
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == PrdctQtyPrceMesrInstrFragment.BILL_DATA) {
			if (resultCode == PrdctQtyPrceMesrInstrFragment.BILL_SUCCESS
					&& data != null) {
				mBillTranModels = (ArrayList<BillTransactionModel>) data
						.getSerializableExtra(Constants.KEY);
				if (mBillTranModels != null) {
					Logger.d(TAG,
							"Bill_Txn_Model:" + mBillTranModels.toString());
					// updateBillFragment();
					getModifierData();
				}
			}
		} else if (requestCode == UserAccessFragment.USER_ACCESS) {
			if (resultCode == UserAccessFragment.USER_SUCCESS && data != null) {
				UserRightsModel userRights = (UserRightsModel) data
						.getSerializableExtra(Constants.KEY);

				checkUserRightsAndTakeRespectiveAction(userRights);
			}
		}
	}

	private void checkUserRightsAndTakeRespectiveAction(
			UserRightsModel userRights) {
		Logger.d(TAG, "BillFragment.typeOfAction " + BillFragment.typeOfAction);
		Logger.d(TAG, "userRights " + userRights);
		if (BillFragment.typeOfAction.equalsIgnoreCase("bill reprint")) {
			if (!userRights.alllow_BILL_TO_RE_PRINT) {
				printItems();
			}

		} else if (BillFragment.typeOfAction.equalsIgnoreCase("void item")) {
			Logger.d(TAG, "userRights.VOID_LIMIT_MAX  "
					+ userRights.VOID_LIMIT_MAX);
			System.out.println(" VOID### CHECK1 ");
			if (userRights.VOID_LIMIT_MAX == -1
					|| userRights.VOID_LIMIT_MAX > 0) {
				System.out.println(" VOID### CHECK2 ");
				processVoidItem();
			} else {
				showAlertDialog("This user has not access to void items");
			}
		} else if (BillFragment.typeOfAction.equalsIgnoreCase("held item")) {
			Logger.d(TAG, "userRights.HELD_LIMIT_MAX  "
					+ userRights.HELD_LIMIT_MAX);
			if (mBillTranModels != null && mBillTranModels.size() > 0) {
				showAlertDialog(
						getResources()
								.getString(R.string.alert_hold_bill_panel),
						true);
			} else {
				showAlertDialog("This user has not access to held items");
			}
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
		alertDialogFragment.show(fm, ALERT_FRAGMENT);
	}

	public void updateOptionPanelButtonStates(Boolean bStatus) {
		logoutBtn.setEnabled(bStatus);
		mHeldTransactionBtn.setEnabled(bStatus);
		if (bStatus) {

			logoutBtn.setBackgroundResource(R.drawable.logout_selector);
			mHeldTransactionBtn.setBackgroundResource(R.drawable.held_selector);

			// session timeout can occur
			SharedPreferences prefs = getActivity().getSharedPreferences(
					"com.mpos", Context.MODE_PRIVATE);
			MPOSApplication.waiter.setPeriod(prefs.getInt(
					MPOSApplication.SESSTION_TIME_OUT, 2));
		} else {
			mHeldTransactionBtn.setBackgroundResource(R.drawable.btn_held_db);
			logoutBtn.setBackgroundResource(R.drawable.btn_logout_db);

			// no session timeout shd occur
			MPOSApplication.waiter.setPeriod(10000000);
		}
	}

	/**
	 * Method -To update bill panel
	 */
	private void updateBillFragment() {
		Logger.v(TAG, "bill instruection" + mBillInstructionTranModels);
		if (mBillTranModels != null && mBillTranModels.size() > 0) {
			updateOptionPanelButtonStates(false);

			if (mBillInstructionTranModels != null
					&& mBillInstructionTranModels.size() > 0) {
				Logger.v(TAG,
						"bill instruection" + mBillInstructionTranModels.size());
				// create prdct desc text
				String print_desc = createTxnModifierMeasureText(
						mBillTranModels, mBillInstructionTranModels,
						mINSTRCTNDataList);
				Logger.d(TAG + "V", "PrintDesc: " + print_desc);
			}

			if (UserSingleton.getInstance(getContext()).isPaymentDone) {
				//mPrintBtn.setClickable(true);
				mNewSaleBtn.setClickable(true);
			}
			else
				//mPrintBtn.setClickable(false);

			billAdapter = new BillAdapter(mBillTranModels,
					mBillInstructionTranModels, mINSTRCTNDataList,
					getActivity());
			billListView.setAdapter(billAdapter);
			billAdapter.notifyDataSetChanged();

			int totalQty = 0;
			int finalTotalQty = 0;
			double totalTaxAmt = 0;
			billTotalPrice = 0;
			double prdctAmt = 0;
			for (BillTransactionModel billTransactionModel : mBillTranModels) {
				totalQty = (int) billTransactionModel.getPRDCT_QNTY();
				finalTotalQty += billTransactionModel.getPRDCT_QNTY();

				totalTaxAmt += billTransactionModel.getPRDCT_VAT_AMNT();
				prdctAmt = billTransactionModel.getPRDCT_AMNT();
				// Inclusive of PrdctAmt and Tax
				billTotalPrice += (prdctAmt);
			}

			Logger.d(TAG, "before:TotalAmt:" + billTotalPrice);
			Logger.d(TAG, "before:TotalTax: " + totalTaxAmt);
			Logger.d(TAG, "before:TotalPrdct: "
					+ (billTotalPrice - totalTaxAmt));

			// DecimalFormat df2 = new DecimalFormat("###.##");
			DecimalFormat df2 = Constants.getDecimalFormat();
			billTotalPrice = Double.valueOf(df2.format(billTotalPrice));
		/*	((TextView) listFooterView.findViewById(R.id.totalqtyText))
					.setText("" + finalTotalQty);
			((TextView) listFooterView.findViewById(R.id.totalpriceText))
					.setText("" + billTotalPrice);*/
			((TextView) categoryView.findViewById(R.id.totalpriceText)).setText("" + billTotalPrice);
			((TextView) categoryView.findViewById(R.id.totalqtyText)).setText("" + finalTotalQty);
			((TextView) listFooterView.findViewById(R.id.taxpriceText))
					.setText("" + df2.format(totalTaxAmt));
		}

	}

	/**
	 * Method- To create print prdct desc text
	 * 
	 * @param billTranModels
	 * @param billInstructionTranModels
	 * @param iNSTRCTNDataList
	 * @return
	 */
	private String createTxnModifierMeasureText(
			ArrayList<BillTransactionModel> billTranModels,
			ArrayList<Bill_INSTRCTN_TXN_Model> billInstructionTranModels,
			ArrayList<INSTRCTN_MST_Model> iNSTRCTNDataList) {
		Logger.d(TAG + "V", "createTxnModifierMeasureText");
		String description = "";

		for (int position = 0; position < billTranModels.size(); position++) {
			if (billInstructionTranModels != null
					&& billInstructionTranModels.size() > 0) {

				Bill_INSTRCTN_TXN_Model instruction = new Bill_INSTRCTN_TXN_Model();
				instruction.setPrdctCode(billTranModels.get(position)
						.getPRDCT_CODE());
				instruction.setRow_id(billTranModels.get(position).getRow_id());
				instruction.setPrdctVoid(billTranModels.get(position)
						.getPRDCT_VOID());
				instruction.setBranchCode(billTranModels.get(position)
						.getBRNCH_CODE());
				instruction.setPck(billTranModels.get(position).getPACK());

				if (billInstructionTranModels.contains(instruction)) {

					int index = billInstructionTranModels.indexOf(instruction);
					Logger.v(TAG, "index" + index);
					if (index != -1) {
						description += " - "
								+ billInstructionTranModels.get(index).getPck();// Measure
																				// data

						if (iNSTRCTNDataList != null
								&& iNSTRCTNDataList.size() > 0) {
							INSTRCTN_MST_Model instructionCode = new INSTRCTN_MST_Model();
							instructionCode
									.setInstrctn_code(billInstructionTranModels
											.get(index).getInstrcCode());

							if (iNSTRCTNDataList.contains(instructionCode)) {
								int insIndex = iNSTRCTNDataList
										.indexOf(instructionCode);
								if (insIndex != -1) {
									description += " - "
											+ iNSTRCTNDataList.get(insIndex)
													.getInstrctn_desc();// Modifier
																		// data
								}
							}
						}

						if (billInstructionTranModels.get(index)
								.getExtraInstrcn() != null
								&& !billInstructionTranModels.get(index)
										.getExtraInstrcn().equalsIgnoreCase("")) {
							description += " ("
									+ billInstructionTranModels.get(index)
											.getExtraInstrcn() + ")";// Extra
																		// instruction
																		// data
						}

					}

					// Update Bill_TXN table with combined text. only if it
					// matches the objects
					HomeHelper helper = new HomeHelper(this);
					billTranModels.get(position).setPRDCT_PRINT_DSCRPTN(
							description);
					Logger.d(TAG, "VData: " + billTranModels.get(position));
					helper.updatePrintDescrInBillTXNDB(billTranModels
							.get(position));
					description = "";
				}

			}
		}

		return description;
	}

	/**
	 * Method- To clear previous sale and begin new transaction
	 */
	protected void startNewSale() {
		// Remove all data only if Payment is not done
		if (!UserSingleton.getInstance(getContext()).isPaymentDone) {
			// Delete current record from Bill_Mst table
			removeRecordFromBill_MSt(UserSingleton.getInstance(getContext())
					.getTransactionNo());
			// Delete current records from Bill_TXn table
			removeRecordsFromBilTxnTable(UserSingleton
					.getInstance(getContext()).getTransactionNo());
			// Delete current records from Bil_Instrctn table
			removeRecordsFromBilInstrctnTxnTable(UserSingleton.getInstance(
					getContext()).getTransactionNo());
			// Delete current records from Prdct_CNsmptn_Txn table
			removeRecordsFromPrdctCnsmptnTable(UserSingleton.getInstance(
					getContext()).getTransactionNo());
		}

		// Generate new transaction no.
		UserSingleton.getInstance(getContext()).generateUniqueTransactionNo();
		UserSingleton.getInstance(getContext()).isPaymentDone = false;

		Bundle bundle = new Bundle();
		bundle.putString(Constants.BUNDLE_KEY, "Home_grid_refresh");
		mCallBack.deliverDataToFragement(bundle);

		// Create clean Bill Panel
		refreshBillPanel();
	}

	/**
	 * Method- To start new panel
	 */
	private void refreshBillPanel() {
		updateOptionPanelButtonStates(true);

		// Create new Transaction No.
		UserSingleton.getInstance(getContext()).generateUniqueTransactionNo();

		Bundle bundle = new Bundle();
		bundle.putString(Constants.BUNDLE_KEY, "Home_grid_refresh");
		mCallBack.deliverDataToFragement(bundle);

		int totalQty = 0;
		double totalTaxAmt = 0;
		billTotalPrice = 0;

		// DecimalFormat df2 = new DecimalFormat("###.##");
		DecimalFormat df2 = Constants.getDecimalFormat();
		billTotalPrice = Double.valueOf(df2.format(billTotalPrice));

		/*((TextView) listFooterView.findViewById(R.id.totalqtyText)).setText(""
				+ totalQty);
		((TextView) listFooterView.findViewById(R.id.totalpriceText))
				.setText("" + billTotalPrice);*/
		
		((TextView) categoryView.findViewById(R.id.totalpriceText)).setText("" + billTotalPrice);
		((TextView) categoryView.findViewById(R.id.totalqtyText)).setText("" + totalQty);
		
		((TextView) listFooterView.findViewById(R.id.taxpriceText)).setText(""
				+ totalTaxAmt);

		// Clear out Bill Panel
		if (mBillTranModels != null) {
			mBillTranModels.clear();
			mBillTranModels = null;
			billListView.setAdapter(new BillAdapter(null, null, null,
					getActivity().getBaseContext()));
		}

		if (!UserSingleton.getInstance(getContext()).isPaymentDone) {
			categoryView.findViewById(R.id.paymentBtn).setClickable(true);
			categoryView.findViewById(R.id.holdBtn).setClickable(true);
			categoryView.findViewById(R.id.customerBtn).setClickable(true);
			categoryView.findViewById(R.id.salespersonBtn).setClickable(true);
			categoryView.findViewById(R.id.voidItemBtn).setClickable(true);

			categoryView.findViewById(R.id.paymentBtn).setBackgroundResource(
					R.drawable.payment_selector);
			categoryView.findViewById(R.id.holdBtn).setBackgroundResource(
					R.drawable.hold_selector);
			categoryView.findViewById(R.id.customerBtn).setBackgroundResource(
					R.drawable.customer_selector);
			categoryView.findViewById(R.id.salespersonBtn)
					.setBackgroundResource(R.drawable.sales_selector);
			categoryView.findViewById(R.id.voidItemBtn).setBackgroundResource(
					R.drawable.void_selector);

			billListView.setClickable(true);
		}

	}

	/**
	 * Method- To remove entries from Prdct Consumption table based on Unique
	 * Trasaction_no.
	 * 
	 * @param transactionNo
	 */
	private void removeRecordsFromPrdctCnsmptnTable(String transactionNo) {
		HomeHelper homeHelper = new HomeHelper(this);
		homeHelper.removePrdctrecordFromConsumptionDB(transactionNo);
	}

	/**
	 * Method- To remove entries from Bill instruction table based on Unique
	 * Trasaction_no.
	 * 
	 * @param transactionNo
	 */
	private void removeRecordsFromBilInstrctnTxnTable(String transactionNo) {
		HomeHelper homeHelper = new HomeHelper(this);
		homeHelper.removePrdctrecordFromBillInstructionDB(transactionNo);
	}

	/**
	 * Method- To remove entries from Bill transaction table based on Unique
	 * Trasaction_no.
	 * 
	 * @param transactionNo
	 */
	private void removeRecordsFromBilTxnTable(String transactionNo) {
		HomeHelper homeHelper = new HomeHelper(this);
		homeHelper.removePrdctrecordFromBillTxnDB(transactionNo);
	}

	/**
	 * Method- To remove entry from BILL_MST based on Unique Trasaction_no.
	 * 
	 * @param transactionNo
	 */
	private void removeRecordFromBill_MSt(String transactionNo) {
		HomeHelper homeHelper = new HomeHelper(this);
		homeHelper.removePrdctrecordFromBillMstDB(transactionNo);
	}

	/**
	 * Method- To retrieve all modifier data present for product
	 */
	private void retrieveModifierDBData() {
		Logger.d(TAG + "V", "retrieveModifierDBData");
		HomeHelper homeHelper = new HomeHelper(this);
		homeHelper.selectModifierRecords("",
				DBConstants.dbo_INSTRCTN_MST_TABLE, true);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void executePostAction(OperationalResult opResult) {
		Logger.d(TAG + "V", "executePostAction" + opResult.getResultCode());
		switch (opResult.getResultCode()) {
		case Constants.FETCH_BILL_TXN_RECORD:
			Logger.d(TAG, "FETCH_BILL_TXN_RECORD");
			if (isHoldTxn) {
				isHoldTxn = false;
				mBillHeldTranModels = (ArrayList<BillTransactionModel>) opResult
						.getResult().getSerializable(Constants.RESULTCODEBEAN);
				if (mBillHeldTranModels != null
						&& mBillHeldTranModels.size() > 0) {
					HomeHelper homeHelper = new HomeHelper(this);
					homeHelper.updateTXNTypeInBillTXNDB(mBillHeldTranModels,
							true);
				}
			} else {
				mBillTranModels = (ArrayList<BillTransactionModel>) opResult
						.getResult().getSerializable(Constants.RESULTCODEBEAN);

				getModifierData();

			}
			break;

		case Constants.FETCH_BILL_INSTRCTN_TXN_RECORD:
			mBillInstructionTranModels = (ArrayList<Bill_INSTRCTN_TXN_Model>) opResult
					.getResult().getSerializable(Constants.RESULTCODEBEAN);
			numberOfRequests--;
			if (numberOfRequests == 0) {
				Logger.d(TAG + "V", "FETCH_BILL_INSTRCTN_TXN_RECORD");
				updateBillFragment();
			}

			break;
		case Constants.FETCH_MODIFIER_RECORD:
			mINSTRCTNDataList = (ArrayList<INSTRCTN_MST_Model>) opResult
					.getResult().getSerializable(Constants.RESULTCODEBEAN);
			numberOfRequests--;
			if (numberOfRequests == 0) {
				Logger.d(TAG + "V", "FETCH_MODIFIER_RECORD");
				updateBillFragment();
			}
			break;

		case Constants.UPDATE_BILL_TXN_RECORDS:
			Logger.d(TAG, "UPDATE_BILL_TXN_RECORDS");
			// getModifierData(); //Code commented to avoid infinite looping:
			// need to check is anything else get broken due to this
			break;

		case Constants.FETCH_SINGLE_CNSMPTN_RECORD:
			// Now update the Consumption record with updated values
			Logger.d(TAG, "FETCH_BILL_TXN_RECORD Done");
			ArrayList<PRDCT_CNSMPTN_TXNModel> consmptnRecords = (ArrayList<PRDCT_CNSMPTN_TXNModel>) opResult
					.getResult().getSerializable(Constants.RESULTCODEBEAN);
			if (consmptnRecords != null && consmptnRecords.size() > 0) {
				HomeHelper homeHelper = new HomeHelper(this);
				for (PRDCT_CNSMPTN_TXNModel updateModel : consmptnRecords) {
					updateModel.setPrdct_void(Constants.ITEM_VOIDED);
					homeHelper.updatePrdctDataInCnsmptnTXNDB(updateModel);
				}
			}
			break;

		case Constants.FETCH_SINGLE_BILL_INSTRCTN_TXN_RECORD:
			// Now update the Consumption record with updated values
			Logger.d(TAG, "FETCH_SINGLE_BILL_INSTRCTN_TXN_RECORD Done");
			ArrayList<Bill_INSTRCTN_TXN_Model> instrctnRecords = (ArrayList<Bill_INSTRCTN_TXN_Model>) opResult
					.getResult().getSerializable(Constants.RESULTCODEBEAN);
			if (instrctnRecords != null && instrctnRecords.size() > 0) {
				HomeHelper homeHelper = new HomeHelper(this);
				for (Bill_INSTRCTN_TXN_Model updateModel : instrctnRecords) {
					updateModel.setPrdctVoid(Constants.ITEM_VOIDED);// change
																	// status to
																	// voided
					homeHelper.updateBillInstrctnData(updateModel);
				}
			}
			break;

		case Constants.UPDATE_CNSMPTN_TXN_RECORDS:
			Logger.d(TAG, "UPDATE_CNSMPTN_TXN_RECORDS done");
			break;

		case Constants.DELETE_BILL_MST_RECORDS:
			Logger.d(TAG, "DELETE_BILL_MST_RECORDS done");
			break;

		case Constants.DELETE_BILL_TXN_RECORDS:
			Logger.d(TAG, "DELETE_BILL_TXN_RECORDS done");
			break;

		case Constants.DELETE_BILL_INSTRCTN_TXN_RECORDS:
			Logger.d(TAG, "DELETE_BILL_INSTRCTN_TXN_RECORDS done");
			break;

		case Constants.DELETE_CONSUMPTION_TXN_RECORDS:
			Logger.d(TAG, "DELETE_CONSUMPTION_TXN_RECORDS done");
			break;

		case Constants.UPDATE_BILL_MST_TXN_TYPE_RECORD:// Hold Transaction
			Logger.d(TAG, "UPDATE_BILL_MST_TXN_TYPE_RECORD done");
			// Create clean Bill Panel
			refreshBillPanel();
			break;

		case Constants.UPDATE_BILL_TXN_TYPE_RECORD:
			Logger.d(TAG, "UPDATE_BILL_TXN_TYPE_RECORD done");
			// Post the hold TXn record to server
			uploadHoldTxnRecordToServer(preserverUniqTxnNo);
			break;

		case Constants.UPDATE_BILL_INSTRCTN_TXN_RECORDS:
			Logger.d(TAG, "UPDATE_BILL_INSTRCTN_TXN_RECORDS done");
			break;

		default:
			break;
		}

	}

	/**
	 * Method- To upload the hold Txn data
	 * 
	 * @param mUniqueTrasactionNo
	 * 
	 */
	private void uploadHoldTxnRecordToServer(String uniqueTrasactionNo) {
		OfflineTxnUploadHelper txnUploadHelper = new OfflineTxnUploadHelper();
		txnUploadHelper.setHeldBillTxnUpload(true);
		txnUploadHelper.uploadJsonData(uniqueTrasactionNo);
		preserverUniqTxnNo = "";
	}

	@Override
	public void errorReceived(OperationalResult opResult) {

	}

	@Override
	public Context getContext() {
		// TODO Auto-generated method stub
		return getActivity();
	}

	private void showAlertDialog(String message, final boolean isHold) {
		// Show a confirmation dialog
		AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
		if (isHold) {
			builder.setCancelable(true)
					.setPositiveButton(getResources().getString(R.string.ok),
							new DialogInterface.OnClickListener() {

								public void onClick(DialogInterface dialog,
										int which) {
									// Place current transaction in Hold, and
									// then
									// clear bill panel
									if (isHold) {
										isHoldTxn = true;
										makeCurrentTransactionToHold(UserSingleton
												.getInstance(getContext()).mUniqueTrasactionNo);
										makeCurrTxnRecordToHold(UserSingleton
												.getInstance(getContext()).mUniqueTrasactionNo);
										preserverUniqTxnNo = UserSingleton
												.getInstance(getContext()).mUniqueTrasactionNo;
										UserOptionFragment hm=(UserOptionFragment.getInstance());
										if(hm != null)
										hm.setZedReportButton(false);
										
									} else {
										// Discard previous sale and start new
										// sale
										startNewSale();
									}
									
									//mPrintBtn.setClickable(false);
								}
							})
					.setNegativeButton(
							getResources().getString(R.string.cancel),
							new DialogInterface.OnClickListener() {

								public void onClick(DialogInterface dialog,
										int which) {
									dialog.dismiss();
								}
							}).setMessage(message);
		} else {
			builder.setCancelable(true)
					.setPositiveButton(
							getResources().getString(R.string.yes_text),
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									// Place current transaction in Hold, and
									// then
									// clear bill panel

									isHoldTxn = true;
									makeCurrentTransactionToHold(UserSingleton
											.getInstance(getContext()).mUniqueTrasactionNo);
									makeCurrTxnRecordToHold(UserSingleton
											.getInstance(getContext()).mUniqueTrasactionNo);
									preserverUniqTxnNo = UserSingleton
											.getInstance(getContext()).mUniqueTrasactionNo;

									// startNewSale();

								}
							})
					.setNeutralButton(
							getResources().getString(R.string.cancel),
							new DialogInterface.OnClickListener() {

								public void onClick(DialogInterface dialog,
										int which) {
									dialog.dismiss();
								}
							})
					.setNegativeButton(
							getResources().getString(R.string.no_text),
							new DialogInterface.OnClickListener() {

								public void onClick(DialogInterface dialog,
										int which) {
									startNewSale();
								}
							}).setMessage(message);
		}

		AlertDialog alert = builder.create();
		alert.show();
	}

	/**
	 * Method- To hold transaction, Update the TXNType field in BILL_MST to Held
	 * 
	 * @param mUniqueTrasactionNo
	 */
	protected void makeCurrentTransactionToHold(String uniqueTrasactionNo) {
		BaseActivity.loadAllConfigData();
		SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT);
		String currentDateandTime = sdf.format(new Date());

		BILL_Mst_Model bill_Mst_Model = new BILL_Mst_Model();
		bill_Mst_Model.setTXN_TYPE(Constants.HELD_TXN);
		bill_Mst_Model.setBILL_SYS_DATE(currentDateandTime); // Update current
																// held time
		bill_Mst_Model.setBILL_SCNCD(uniqueTrasactionNo);

		UserSingleton.getInstance(getActivity()).mheldLimitCount++;

		HomeHelper homeHelper = new HomeHelper(this);
		homeHelper.updateTXNTyprInBillMstDB(bill_Mst_Model);
	}

	/**
	 * Method- To hold transaction, Update the TXNType field in BILL_TXN to Held
	 * 
	 * @param uniqueTrasactionNo
	 * 
	 * */
	protected void makeCurrTxnRecordToHold(String uniqueTrasactionNo) {
		HomeHelper helper = new HomeHelper(this);
		// retrieve All items[void/no-void] and for current transaction no.
		String[] whrArgs = { uniqueTrasactionNo };
		helper.selectBillTXNAllRecords(whrArgs, DBConstants.dbo_BILL_TXN_TABLE,
				true);
	}

	/**
	 * Method- To show alert dialog
	 * 
	 * @param message
	 */
	private void showBillPrintDialog(CharSequence message) {
		FragmentManager fm = getActivity().getSupportFragmentManager();
		AlertDialogFragment alertDialogFragment = new AlertDialogFragment();
		Bundle bundle = new Bundle();
		bundle.putString(Constants.KEY, " " + message);
		alertDialogFragment.setArguments(bundle);
		alertDialogFragment.show(fm, ALERT_FRAGMENT);
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
	
	public void triggerPrinting (String sJSON)
	{
		sPRINTER_JSON_DATA =sJSON;
		new UploadPrintData().execute("test");
	}

	class UploadPrintData extends AsyncTask<String, Void, String> {

		protected String doInBackground(String... urls) {
			try {

				HttpClient client = new DefaultHttpClient();
				HttpPost post = new HttpPost(Config.HTTP + Config.DNS_NAME + "print");
				
				String jsonData = "{\"billScanCode\": \"" + UserSingleton.getInstance(getContext()).mUniqueTrasactionNo + "\"}";
				StringEntity entity = new StringEntity(jsonData, HTTP.UTF_8);
				entity.setContentType("application/json");
				post.setEntity(entity);
				HttpResponse response = client.execute(post);
				
				System.out.println("PRINTER JSON: " + jsonData);
				
				return "true";

			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}

		protected void onPostExecute(String feed) {
			((BaseActivity) getActivity()).stopProgress();
			if (feed != null) {
				if (feed == "true") {
					showAlertDialog(getString(R.string.alert_print_sucess));
					if (mBillTranModels != null && mBillTranModels.size() > 0)
						startNewSale();
				}
				else
					showAlertDialog(getString(R.string.alert_print_failure));
			} else {
				showAlertDialog(getString(R.string.alert_print_communication_error));
			}

		}
	}

}
