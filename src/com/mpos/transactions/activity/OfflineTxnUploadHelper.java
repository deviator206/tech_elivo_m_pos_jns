/*
 * Copyright (C) 2013 USAP Pvt. Ltd. All Rights Reserved.
 * jaggarnaut-studios pvt. ltd. PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.mpos.transactions.activity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.mpos.R;
import com.mpos.application.BaseActivity;
import com.mpos.application.MPOSApplication;
import com.mpos.framework.Util.UserSingleton;
import com.mpos.framework.Util.Util;
import com.mpos.framework.common.Config;
import com.mpos.framework.common.Constants;
import com.mpos.framework.common.DBConstants;
import com.mpos.framework.common.Logger;
import com.mpos.framework.common.OperationalResult;
import com.mpos.framework.inf.BaseResponseListener;
import com.mpos.framework.inf.NetworkChangeListener;
import com.mpos.home.fragment.BillFragment;
import com.mpos.home.helper.HomeHelper;
import com.mpos.home.model.BillTransactionModel;
import com.mpos.login.model.ResponseModel;
import com.mpos.master.model.BILL_Mst_Model;
import com.mpos.master.model.Petty_Float_Model;
import com.mpos.payment.helper.PaymentHelper;
import com.mpos.payment.model.MULTI_CHNG_MST_Model;
import com.mpos.payment.model.MULTY_PMT_MST_Model;
import com.mpos.payment.model.PAYMT_MST_MODEL;

import com.mpos.transactions.model.Bill_INSTRCTN_TXN_Model;
import com.mpos.transactions.model.FLX_POS_TXNModel;
import com.mpos.transactions.model.HeldTransactionModel;
import com.mpos.transactions.model.PRDCT_CNSMPTN_TXNModel;
import com.mpos.transactions.model.SLMN_TXN_Model;
import com.mpos.transactions.model.TempTXNModel;
import com.mpos.zreport.activity.ZReportActivity;

/**
 * Description-
 * 
 * @author jaggarnaut-studios
 */

public class OfflineTxnUploadHelper implements BaseResponseListener,
		NetworkChangeListener {

	public static final String TAG = Constants.APP_TAG
			+ OfflineTxnUploadHelper.class.getSimpleName();

	private Context mContext;
	private int mUploadDataReceive = 0;
	private int mUploadIndex = 0;
	private boolean isZUpload = false;
	private boolean isHeldBillTxnUpload = false;
	private boolean isOfflineTxn = false;
	private boolean isOfflineFPPTxnUpload = false;// For offline Flt pty pkup
													// upload
	private BaseResponseListener mBaseResponseListener = null;

	// ==========Transaction UPload data
	private ArrayList<BILL_Mst_Model> mUpBillMStModels = new ArrayList<BILL_Mst_Model>();
	private ArrayList<BillTransactionModel> mUpBillTxnModels = new ArrayList<BillTransactionModel>();
	private FLX_POS_TXNModel mFlxPosTxnModel = new FLX_POS_TXNModel();
	private SLMN_TXN_Model mSlmnTxnModel = new SLMN_TXN_Model();
	private ArrayList<HeldTransactionModel> mHeldTxnModels = new ArrayList<HeldTransactionModel>();
	private ArrayList<PRDCT_CNSMPTN_TXNModel> mCnsmptnTxnModelArr = new ArrayList<PRDCT_CNSMPTN_TXNModel>();
	private ArrayList<Bill_INSTRCTN_TXN_Model> mBillInstrctnTxnModelArr = new ArrayList<Bill_INSTRCTN_TXN_Model>();
	private ArrayList<MULTI_CHNG_MST_Model> mMultiChngMstModels = new ArrayList<MULTI_CHNG_MST_Model>();
	private ArrayList<PAYMT_MST_MODEL> mPMT_List = new ArrayList<PAYMT_MST_MODEL>();
	private ArrayList<MULTY_PMT_MST_Model> mMULTY_PMT_List = new ArrayList<MULTY_PMT_MST_Model>();

	private ArrayList<TempTXNModel> mOfflineTmpTxnModels = new ArrayList<TempTXNModel>();
	private ArrayList<Petty_Float_Model> mPtyFltPkUpModels = new ArrayList<Petty_Float_Model>();
	private ArrayList<BILL_Mst_Model> mUpTXNNOBillMStModels = new ArrayList<BILL_Mst_Model>();
	private boolean printFlag=false;
	private BillFragment bfObject ;

	public OfflineTxnUploadHelper() {
		// Register to Network change listener
		MPOSApplication.mImplementedNetworkLisContexts.put(
				OfflineTxnUploadHelper.class.getSimpleName(), this);
	}
	public OfflineTxnUploadHelper(Boolean printFlag, BillFragment class1) {
		// Register to Network change listener
		 this.printFlag=printFlag;
		 this.bfObject = class1;
		MPOSApplication.mImplementedNetworkLisContexts.put(
				OfflineTxnUploadHelper.class.getSimpleName(), this);
	}

	public OfflineTxnUploadHelper(Context context, boolean iszUpload) {
		// Register to Network change listener
		MPOSApplication.mImplementedNetworkLisContexts.put(
				OfflineTxnUploadHelper.class.getSimpleName(), this);
		this.isZUpload = iszUpload;
		this.mBaseResponseListener = (BaseResponseListener) context;
	}

	/**
	 * @param isHeldBillTxnUpload
	 *            the isHeldBillTxnUpload to set
	 */
	public void setHeldBillTxnUpload(boolean isHeldBillTxnUpload) {
		this.isHeldBillTxnUpload = isHeldBillTxnUpload;
	}

	/**
	 * To create Json response
	 */
	public void uploadJsonData(String trasac_No) {
		Logger.d(TAG + "T", "uploadJsonData:" + trasac_No);

		mContext = MPOSApplication.getContext();

		// check for nw connection
		if (UserSingleton.getInstance(MPOSApplication.getContext()).isNetworkAvailable) {// Yes
			// UPload transaction
			Logger.d(TAG, "TNo:" + trasac_No);

			mUploadDataReceive = 10;
			getBillMSTUploadData(trasac_No);
			getMIssueChangeMStUploadData(trasac_No);
			getBillTxnUploadData(trasac_No);
			getFLXPOsUploadData(trasac_No);
			getSalsmanTxnUpoadData(trasac_No);
			getHeldTxnUpoadData(trasac_No);
			getBillInstrUploadData(trasac_No);
			getPrdctCnsmptnUploadData(trasac_No);
			getPaymentMStUploadData(trasac_No);
			getMultiPaymentMStUploadData(trasac_No);

		} else { // No
			// Save the respective transaction no in Temp TXN table.
			// If its zUpload then dnt save it in temp table, as there will be
			// already one entry
			if (!isZUpload) {
				TempTXNModel tempTXNModel = new TempTXNModel();
				tempTXNModel.setTxn_no(trasac_No);
				tempTXNModel.setIsUploaded("N");

				PaymentHelper helper = new PaymentHelper(this);
				helper.saveInTempTXNDB(tempTXNModel,
						DBConstants.dbo_TEMP_TRANSACTION_TXN_TABLE, true);
			} else {
				showAlertMessage(
						MPOSApplication.getContext().getResources()
								.getString(R.string.alert),
						MPOSApplication.getContext().getResources()
								.getString(R.string.alert_network));
			}
		}
	}

	/**
	 * To create Json response// for float petty
	 */
	private void uploadFPPJsonData(String trasac_No) {
		Logger.d(TAG + "T", "uploadFPPJsonData:" + trasac_No);

		mContext = MPOSApplication.getContext();

		// check for nw connection
		if (UserSingleton.getInstance(MPOSApplication.getContext()).isNetworkAvailable) {// Yes
			mUploadDataReceive = 1;
			getFloatPettyPkUpData(trasac_No);
		}
	}

	/**
	 * Method - To get all FPP data
	 * 
	 * @param trasac_No
	 */
	private void getFloatPettyPkUpData(String trasac_No) {
		HomeHelper helper = new HomeHelper(this);
		String[] queryParams = { trasac_No };
		helper.selectFPPRecords(queryParams, DBConstants.dbo_PTY_FLT_MST_TABLE,
				true);
	}

	/**
	 * Method- To fetch all Transaction numbers only
	 */
	public void retrieveAllTransacRecords() {
		PaymentHelper helper = new PaymentHelper(this);
		helper.selectAllBIllMStTrasNo(DBConstants.dbo_BILL_MST_TABLE, true);
	}

	/**
	 * Method - TO get Bill Mst data from DB
	 */
	private void getBillMSTUploadData(String trasac_No) {
		PaymentHelper helper = new PaymentHelper(this);
		String[] queryParams = { trasac_No };
		helper.selectRunningBillMSt(queryParams,
				DBConstants.dbo_BILL_MST_TABLE, true);
	}

	/**
	 * Method - TO get Bill TXN data from DB
	 * 
	 * @param trasac_No
	 */
	private void getBillTxnUploadData(String trasac_No) {
		PaymentHelper helper = new PaymentHelper(this);
		if (isHeldBillTxnUpload) {
			String[] queryParams = { trasac_No, Constants.HELD_TXN };
			helper.selectRunningBillTxn(queryParams,
					DBConstants.dbo_BILL_TXN_TABLE, true);
		} else {
			String[] queryParams = { trasac_No, Constants.BILLING_TXN };
			helper.selectRunningBillTxn(queryParams,
					DBConstants.dbo_BILL_TXN_TABLE, true);
		}
	}

	/**
	 * Method - TO get FlexPos Txn data from DB
	 * 
	 * @param trasac_No
	 */
	private void getFLXPOsUploadData(String trasac_No) {
		PaymentHelper helper = new PaymentHelper(this);
		String[] queryParams = { trasac_No };
		helper.selectRunningFLXPOSTxnRecords(queryParams,
				DBConstants.dbo_FLX_POS_TXN_TABLE, true);
	}

	/**
	 * Method- TO get Salseman Tcn data from dB
	 * 
	 * @param trasac_No
	 */
	private void getSalsmanTxnUpoadData(String trasac_No) {
		PaymentHelper helper = new PaymentHelper(this);
		String[] queryParams = { trasac_No };
		helper.selectRunningSalesmanTxnRecords(queryParams,
				DBConstants.dbo_SLMN_TXN_TABLE, true);
	}

	/**
	 * Method- To get Held Txn data from DB
	 * 
	 * @param trasac_No
	 */
	private void getHeldTxnUpoadData(String trasac_No) {
		PaymentHelper helper = new PaymentHelper(this);
		String[] queryParams = { trasac_No };
		helper.selectRunningHeldTxnRecords(queryParams,
				DBConstants.dbo_HELD_TXN, true);
	}

	/**
	 * Method - To get Prdct Consumption data
	 * 
	 * @param trasac_No
	 */
	private void getPrdctCnsmptnUploadData(String trasac_No) {
		PaymentHelper helper = new PaymentHelper(this);
		String[] queryParams = { trasac_No };
		helper.selectRunningCnsmptnRecords(queryParams,
				DBConstants.dbo_CNSMPTN_TXN, true);
	}

	/**
	 * Method - To get Bill Instruction data
	 * 
	 * @param trasac_No
	 */
	private void getBillInstrUploadData(String trasac_No) {
		PaymentHelper helper = new PaymentHelper(this);
		String[] queryParams = { trasac_No };
		helper.selectRunningBillInstrc_TXNRecords(queryParams,
				DBConstants.dbo_BILL_INSTRCTN_TXN_TABLE, true);
	}

	/**
	 * Method- To get Paymnet MSt data
	 * 
	 * @param trasac_No
	 */
	private void getPaymentMStUploadData(String trasac_No) {
		PaymentHelper helper = new PaymentHelper(this);
		helper.selectPaymenMstDetails(trasac_No, DBConstants.PYMNT_NMBR,
				DBConstants.dbo_PYMNTS_MST_TABLE, true);
	}

	/**
	 * Method- To get multi payments data
	 * 
	 * @param trasac_No
	 */
	private void getMultiPaymentMStUploadData(String trasac_No) {
		PaymentHelper helper = new PaymentHelper(this);
		helper.selectMultiPaymenMstDetails(trasac_No, DBConstants.PYMNT_NMBR,
				DBConstants.dbo_MULTI_PYMNTS_MST_TABLE, true);
	}

	/**
	 * Method- To get change issue Upload data
	 * 
	 * @param trasac_No
	 */
	private void getMIssueChangeMStUploadData(String trasac_No) {
		PaymentHelper helper = new PaymentHelper(this);
		helper.selectMultiChngMstDetails(trasac_No, DBConstants.MCM_PYMNT_NMBR,
				DBConstants.dbo_MULTI_CHNG_MST_TABLE, true);
	}

	/**
	 * Method- TO create Transaction data to upload
	 * 
	 * @throws JSONException
	 */
	private void createUploadTrsacJsonData() throws JSONException {

		// =============== JSON Creation Code Started
		// =================================================================================================================

		// TOp JSON Object
		JSONObject jsonObject = new JSONObject();
		// Array of BillMSt Models
		JSONArray billMStJsonArr = new JSONArray();

		if (mUpBillMStModels != null && mUpBillMStModels.size() > 0) {
			for (int index = 0; index < mUpBillMStModels.size(); index++) {
				BILL_Mst_Model billMstModel = mUpBillMStModels.get(index);

				// get model Json data
				JSONObject jsonObj = BILL_Mst_Model.getJsonData(billMstModel);

				// Bill Transaction Json
				JSONArray billTxnJsonArr = new JSONArray();

				if (mUpBillTxnModels != null && mUpBillTxnModels.size() > 0) {
					for (int i = 0; i < mUpBillTxnModels.size(); i++) {
						BillTransactionModel billTxnModel = mUpBillTxnModels
								.get(i);

						// get model Json data
						JSONObject billTxnJsonObj = BillTransactionModel
								.getJsonData(billTxnModel);

						// Bill Instruction Json OBject //
						if (mBillInstrctnTxnModelArr != null
								&& mBillInstrctnTxnModelArr.size() > 0) {
							// Get particular product bill instruction from
							// array
							Bill_INSTRCTN_TXN_Model bill_Instrc_txn_Model = null;
							for (Bill_INSTRCTN_TXN_Model bill_instr_txn_Model : mBillInstrctnTxnModelArr) {
								if (bill_instr_txn_Model.getPrdctCode()
										.equalsIgnoreCase(
												billTxnModel.getPRDCT_CODE())) {
									bill_Instrc_txn_Model = bill_instr_txn_Model;
									break;
								}
							}
							if (bill_Instrc_txn_Model != null) {
								JSONObject billInstrcJsonObj = Bill_INSTRCTN_TXN_Model
										.getJsonData(bill_Instrc_txn_Model);
								// Place Bill Instruction Json Object in Bill
								// Txn
								// Json
								billTxnJsonObj.put("billInstrctnTxn",
										billInstrcJsonObj);
							}
						} else {
							Logger.d(TAG, "mBillInstrctnTxnModelArr EMpty");
						}

						if (mCnsmptnTxnModelArr != null
								&& mCnsmptnTxnModelArr.size() > 0) {
							// Bill consumption Json OBject //
							PRDCT_CNSMPTN_TXNModel prdct_Cnsmptn_txn_Model = null;
							for (PRDCT_CNSMPTN_TXNModel prdct_cnsmptn_txn_Model : mCnsmptnTxnModelArr) {
								if (prdct_cnsmptn_txn_Model.getPrdct_code()
										.equalsIgnoreCase(
												billTxnModel.getPRDCT_CODE())) {
									prdct_Cnsmptn_txn_Model = prdct_cnsmptn_txn_Model;
									break;
								}
							}
							if (prdct_Cnsmptn_txn_Model != null) {
								JSONObject billConsumptionJsonObj = PRDCT_CNSMPTN_TXNModel
										.getJsonData(prdct_Cnsmptn_txn_Model);
								// Place Bill consumption Json Object in Bill
								// Txn
								// Json
								billTxnJsonObj.put("billInstrctnTxn",
										billConsumptionJsonObj);
							}
						} else {
							Logger.d(TAG, "mCnsmptnTxnModelArr EMpty");
						}

						// Put Bill Txn Json Object in Bill Txn Json Array
						billTxnJsonArr.put(i, billTxnJsonObj);
					}
				} else {
					Logger.d(TAG, "mUpBillTxnModels Empty");
				}

				// If upload is of Held type
				if (isHeldBillTxnUpload) {
					// Put Bill Txn array in Bill Held Json object
					jsonObj.put("heldTxn", billTxnJsonArr);
				} else {// type Billing
						// Put Bill Txn array in Bill TCN Json object
					jsonObj.put("billTxn", billTxnJsonArr);
				}

				// Salesman Txn json object
				JSONObject slsmanTxnJsonObj = null;
				if (mSlmnTxnModel != null) {
					slsmanTxnJsonObj = SLMN_TXN_Model
							.getJsonData(mSlmnTxnModel);
					// Put SLMN Txn json OBj in Bill Mst Json object
					jsonObj.put("slmnTxn", slsmanTxnJsonObj);
				} else {
					Logger.d(TAG, "mSlmnTxnModel Empty");
				}

				// Flex POS TXn json object
				JSONObject flxPosTxnJsonObj = null;
				if (mFlxPosTxnModel != null) {
					flxPosTxnJsonObj = FLX_POS_TXNModel.getJsonData(
							mFlxPosTxnModel, billMstModel);
					// Put FLexPos Txn json OBj in Bill Mst Json object
					jsonObj.put("flexPosTxn", flxPosTxnJsonObj);
				} else {
					Logger.d(TAG, "mFlxPosTxnModel Empty");
				}

				// Payments MSt Json Array
				if (mPMT_List != null && mPMT_List.size() > 0) {
					JSONArray paymentsMstJsonArr = new JSONArray();
					for (int j = 0; j < mPMT_List.size(); j++) {
						PAYMT_MST_MODEL paymt_MST_MODEL = mPMT_List.get(j);

						// Payments MSt Json Object
						JSONObject paymentsMstJsonObject = PAYMT_MST_MODEL
								.getJsonData(paymt_MST_MODEL);

						// Add multi payments data only if model is "Cash"
						if (paymt_MST_MODEL.getPAY_MODE()
								.equalsIgnoreCase("CA")) {
							Logger.d(TAG, "Cash Model loaded.");

							if (mMultiChngMstModels != null
									&& mMultiChngMstModels.size() > 0) {
								// Issue change MSt Json Array
								JSONArray issueChngMstArr = new JSONArray();
								for (int k = 0; k < mMultiChngMstModels.size(); k++) {
									MULTI_CHNG_MST_Model multi_CHNG_MST_Model = mMultiChngMstModels
											.get(k);
									JSONObject issueMltiChangeMst = MULTI_CHNG_MST_Model
											.getJsonData(multi_CHNG_MST_Model,
													billMstModel);

									// Put Multi Chng MSt Json object in
									// Payments
									// MSt
									// arr
									issueChngMstArr.put(k, issueMltiChangeMst);
								}
								// Put Multi chnage Json Arr in Payments Mst
								// Json
								// Object
								paymentsMstJsonObject.put("listChngMsts",
										issueChngMstArr);
							} else {
								Logger.d(TAG, "mMultiChngMstModels EMpty");
							}

							if (mMULTY_PMT_List != null
									&& mMULTY_PMT_List.size() > 0) {
								// Multi Payments Json Array
								JSONArray multiPymntsMstArr = new JSONArray();
								for (int l = 0; l < mMULTY_PMT_List.size(); l++) {
									MULTY_PMT_MST_Model multy_PMT_MST_Model = mMULTY_PMT_List
											.get(l);
									JSONObject MltiPymntMstJsonObj = MULTY_PMT_MST_Model
											.getJsonData(multy_PMT_MST_Model,
													billMstModel);

									// Put Multi Payments Json object in Multi
									// Payments
									// arr
									multiPymntsMstArr.put(l,
											MltiPymntMstJsonObj);
								}
								// Put Multi Payments Json Arr in Payments Mst
								// Json
								// Object
								paymentsMstJsonObject.put(
										"listMultiPymntsMsts",
										multiPymntsMstArr);
							} else {
								Logger.d(TAG, "mMULTY_PMT_List Empty");
							}
						} else {
							Logger.d(TAG, "Other than Cash Model loaded.");
						}

						// Put Payments MSt Json object in Payments MSt arr
						paymentsMstJsonArr.put(j, paymentsMstJsonObject);
					}
					// Put Payments MST json Array in Bill Mst Json object
					jsonObj.put("paymentMst", paymentsMstJsonArr);
				} else {
					Logger.d(TAG, "mPMT_List Empty");
				}

				// Held Txn Json Array
				if (mHeldTxnModels != null && mHeldTxnModels.size() > 0) {
					JSONArray heldTxnJsonArray = new JSONArray();
					for (int m = 0; m < mHeldTxnModels.size(); m++) {
						HeldTransactionModel heldTransactionModel = mHeldTxnModels
								.get(m);

						JSONObject heldTxnJsonObj = HeldTransactionModel
								.getJsonData(heldTransactionModel);

						// Get particular product held bill instruction from
						// array
						Bill_INSTRCTN_TXN_Model held_bill_Instrc_txn_Model = null;
						for (Bill_INSTRCTN_TXN_Model bill_instr_txn_Model : mBillInstrctnTxnModelArr) {
							if (bill_instr_txn_Model.getPrdctCode()
									.equalsIgnoreCase(
											heldTransactionModel
													.getPRDCT_CODE())) {
								held_bill_Instrc_txn_Model = bill_instr_txn_Model;
								break;
							}
						}
						if (held_bill_Instrc_txn_Model != null) {
							// Held Bill Instruction Json OBject //
							JSONObject HeldBillInstrcJsonObj = Bill_INSTRCTN_TXN_Model
									.getJsonData(held_bill_Instrc_txn_Model);
							// Place Held Bill Instruction Json Object in Held
							// Txn
							// Json
							heldTxnJsonObj.put("billInstrctnTxn",
									HeldBillInstrcJsonObj);
						}

						// Get particular product held bill consumption from
						// array
						PRDCT_CNSMPTN_TXNModel held_Prdct_Cnsmptn_txn_Model = null;
						for (PRDCT_CNSMPTN_TXNModel held_prdct_cnsmptn_txn_Model : mCnsmptnTxnModelArr) {
							if (held_prdct_cnsmptn_txn_Model.getPrdct_code()
									.equalsIgnoreCase(
											heldTransactionModel
													.getPRDCT_CODE())) {
								held_Prdct_Cnsmptn_txn_Model = held_prdct_cnsmptn_txn_Model;
								break;
							}
						}
						if (held_Prdct_Cnsmptn_txn_Model != null) {
							// Held Bill consumption Json OBject //
							JSONObject HeldBillConsumptionJsonObj = PRDCT_CNSMPTN_TXNModel
									.getJsonData(held_Prdct_Cnsmptn_txn_Model);
							// Place Held Bill consumption Json Object in Held
							// Txn
							// Json
							heldTxnJsonObj.put("billInstrctnTxn",
									HeldBillConsumptionJsonObj);
						}

						// Put Held json Object in Held Json Array
						heldTxnJsonArray.put(m, heldTxnJsonObj);

					}
					// Put Held Txn array in Bill Mst json object
					jsonObj.put("heldTxn", heldTxnJsonArray);
				} else {
					Logger.d(TAG, "mHeldTxnModels Empty");
				}

				// Put Bill Mst json Object in Bill Mst Json Array
				billMStJsonArr.put(index, jsonObj);
			}
		} else {
			Logger.d(TAG, "mUpBillMStModels Empty");
		}

		// get mac address data from shardPref
		SharedPreferences prefs = mContext.getSharedPreferences("com.mpos",
				Context.MODE_PRIVATE);
		String macAddrss = prefs
				.getString(MPOSApplication.WIFI_MAC_ADDRESS, "");
		// put tablet Mac Address in reponse
		jsonObject.put("tabMacAddress", macAddrss);
		// Put Bill Mst array in top level Json object : Final
		jsonObject.put("billMst", billMStJsonArr);

		// ==================JSON Creation Code
		// Completed==============================================================================================================

		Logger.d(TAG, "Final JSON: " + jsonObject.toString() +" printFlag "+printFlag);

		//Util.writeToSdCard("/sdcard/myJsonfile.txt", jsonObject.toString());

		//new UploadTransData().execute(jsonObject.toString());
		
		if(!printFlag){

            Util.writeToSdCard("/sdcard/myJsonfile.txt", jsonObject.toString());

            	new UploadTransData().execute(jsonObject.toString());

			}else{
				System.out.println("TRIGGERING THE UPLOAD  PRINT DATA");
				//new UploadPrintData(jsonObject.toString());
				(this.bfObject).triggerPrinting(jsonObject.toString());
				}
		
		// uploadTrasactionDataWs(jsonObject.toString());

	}

	/**
	 * Create JSON of FPP record
	 */
	private void createUploadFPPJsonData() {
		BaseActivity.loadAllConfigData();
		JSONObject jsonObject = null;
		try {
			jsonObject = new JSONObject();
			JSONArray jArr = new JSONArray();
			jArr.put(0, Petty_Float_Model.getJsonData(mPtyFltPkUpModels.get(0)));

			// get mac address data from shardPref
			SharedPreferences prefs = MPOSApplication.getContext()
					.getSharedPreferences("com.mpos", Context.MODE_PRIVATE);
			String macAddrss = prefs.getString(
					MPOSApplication.WIFI_MAC_ADDRESS, "");
			jsonObject.put("tabMacAddress", macAddrss);

			jsonObject.put("listMultiPtyFlt", jArr);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Logger.d(TAG, "Final JSON: " + jsonObject.toString());
		new UploadFLTPTYData().execute(jsonObject.toString());
	}

	/**
	 * Async Task to call Post URL
	 * 
	 * @author vinayakh
	 * 
	 */
	class UploadTransData extends AsyncTask<String, Void, ResponseModel> {

		protected ResponseModel doInBackground(String... urls) {
			try {
				return uploadTrasactionDataWs(urls[0]);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}

		protected void onPostExecute(ResponseModel feed) {
			// TODO: check this.exception
			// TODO: do something with the feed
			Logger.d(TAG, "UploadTransData onPostExecute" + feed.toString());
			if (feed.getCode() == Config.POST_SUCCESS) {
				// showAlertMessage(
				// mContext.getResources().getString(R.string.alert),
				// feed.getResponse());
			} else {
				// showAlertMessage(
				// mContext.getResources().getString(R.string.alert),
				// feed.getResponse());
			}
		}
	}

	/**
	 * Method - To upload json data to server
	 * 
	 * @param json
	 * @return
	 */
	private ResponseModel uploadTrasactionDataWs(String jsonData) {
		Logger.d(TAG, "uploadTrasactionDataWs" + jsonData);

		// Clear previous data
		mUpBillMStModels = new ArrayList<BILL_Mst_Model>();
		mUpBillTxnModels = new ArrayList<BillTransactionModel>();
		mFlxPosTxnModel = new FLX_POS_TXNModel();
		mSlmnTxnModel = new SLMN_TXN_Model();
		mHeldTxnModels = new ArrayList<HeldTransactionModel>();
		mCnsmptnTxnModelArr = new ArrayList<PRDCT_CNSMPTN_TXNModel>();
		mBillInstrctnTxnModelArr = new ArrayList<Bill_INSTRCTN_TXN_Model>();
		mMultiChngMstModels = new ArrayList<MULTI_CHNG_MST_Model>();
		mPMT_List = new ArrayList<PAYMT_MST_MODEL>();
		mMULTY_PMT_List = new ArrayList<MULTY_PMT_MST_Model>();

		// Give call to next one to upload.
		if (isZUpload) {
			Logger.d(TAG, "uploadTrasactionDataWs:Again:" + isZUpload);
			createAndUploadONebyONe();
		}
		if (isOfflineTxn) {
			Logger.d(TAG, "uploadOfflineTrasactionDataWs:Again:" + isOfflineTxn);
			createAndUploadOfflineOneByOne();
		}

		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(Config.HTTP + Config.DNS_NAME
				+ Config.TXN_UPLOAD);
		JSONObject objres = null;
		ResponseModel responseModel = new ResponseModel();
		try {
			StringEntity entity = new StringEntity(jsonData, HTTP.UTF_8);
			entity.setContentType("application/json");
			post.setEntity(entity);
			HttpResponse response = client.execute(post);

			BufferedReader rd = new BufferedReader(new InputStreamReader(
					response.getEntity().getContent()));
			String line = "";
			while ((line = rd.readLine()) != null) {
				try {
					objres = new JSONObject(line);
					responseModel.setCode(objres
							.optString(ZReportActivity.POST_CODE));
					responseModel.setResponse(objres
							.optString(ZReportActivity.POST_RESPONSE));
					responseModel.setDetails(objres
							.optString(ZReportActivity.POST_DETAILS));
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		// System.out.println("String Object Response>>" + objres.toString());
		return responseModel;
	}

	/**
	 * Async Task to call Post URL
	 * 
	 * @author vinayakh
	 * 
	 */
	class UploadFLTPTYData extends AsyncTask<String, Void, ResponseModel> {

		protected ResponseModel doInBackground(String... urls) {
			try {
				return uploadFLTPTYDataWs(urls[0]);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}

		protected void onPostExecute(ResponseModel feed) {
			// TODO: check this.exception
			// TODO: do something with the feed
			Logger.d(TAG, "UploadFLTPTYData onPostExecute" + feed.toString());
			if (feed.getCode() == Config.POST_SUCCESS) {
				// showAlertMessage(getResources().getString(R.string.alert),
				// feed.getResponse());
			} else {
				// showAlertMessage(getResources().getString(R.string.alert),
				// feed.getResponse());
			}
		}
	}

	/**
	 * Method - To upload json data to server
	 * 
	 * @param json
	 * @return
	 */
	private ResponseModel uploadFLTPTYDataWs(String jsonData) {
		Logger.d(TAG, "uploadFLTPTYDataWs" + jsonData);

		mPtyFltPkUpModels = new ArrayList<Petty_Float_Model>();
		
		if (isOfflineFPPTxnUpload) {
			Logger.d(TAG, "uploadOfflineFPPDataWs:Again:" + isOfflineFPPTxnUpload);
			creteAndUploadOfflie_FPP_OneByOne();
		}
		

		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(Config.HTTP + Config.DNS_NAME
				+ Config.PTY_FLT_UPLOAD);
		JSONObject objres = null;
		ResponseModel responseModel = new ResponseModel();
		try {
			StringEntity entity = new StringEntity(jsonData, HTTP.UTF_8);
			entity.setContentType("application/json");
			post.setEntity(entity);
			HttpResponse response = client.execute(post);

			BufferedReader rd = new BufferedReader(new InputStreamReader(
					response.getEntity().getContent()));
			String line = "";
			while ((line = rd.readLine()) != null) {
				try {
					objres = new JSONObject(line);
					responseModel.setCode(objres
							.optString(ZReportActivity.POST_CODE));
					responseModel.setResponse(objres
							.optString(ZReportActivity.POST_RESPONSE));
					responseModel.setDetails(objres
							.optString(ZReportActivity.POST_DETAILS));
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		// System.out.println("String Object Response>>" + objres.toString());
		// return objres.toString();
		return responseModel;
	}

	/**
	 * Method - To check upload json data received from tables
	 */
	private void checkIsUploadDataReceived() {
		Logger.d(TAG, "checkIsUploadDataReceived");
		mUploadDataReceive--;
		if (mUploadDataReceive == 0) {
			try {
				createUploadTrsacJsonData();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void executePostAction(OperationalResult opResult) {
		switch (opResult.getResultCode()) {

		// Upload data handling
		case Constants.FETCH_MULTY_PYMNT_MST_RECORD:
			ArrayList<MULTY_PMT_MST_Model> MULTY_PMT_List = (ArrayList<MULTY_PMT_MST_Model>) opResult
					.getResult().getSerializable(Constants.RESULTCODEBEAN);

			if (MULTY_PMT_List != null) {
				mMULTY_PMT_List.addAll(MULTY_PMT_List);
			}
			checkIsUploadDataReceived();
			break;

		case Constants.FETCH_PYMNT_MST_RECORD:
			ArrayList<PAYMT_MST_MODEL> PMT_List = (ArrayList<PAYMT_MST_MODEL>) opResult
					.getResult().getSerializable(Constants.RESULTCODEBEAN);
			if (PMT_List != null) {
				mPMT_List.addAll(PMT_List);
			}
			checkIsUploadDataReceived();
			break;

		case Constants.FETCH_BILL_MST_RECORD:
			Logger.d(TAG, "FETCH_BILL_MST_RECORD");
			mUpBillMStModels = (ArrayList<BILL_Mst_Model>) opResult.getResult()
					.getSerializable(Constants.RESULTCODEBEAN);
			checkIsUploadDataReceived();
			if (mUpBillMStModels != null && mUpBillMStModels.size() > 0) {
				//
			}
			break;

		case Constants.FETCH_BILL_TXN_RECORD:
			Logger.d(TAG, "FETCH_BILL_TXN_RECORD");
			mUpBillTxnModels = (ArrayList<BillTransactionModel>) opResult
					.getResult().getSerializable(Constants.RESULTCODEBEAN);
			checkIsUploadDataReceived();
			if (mUpBillTxnModels != null && mUpBillTxnModels.size() > 0) {
				//
			}
			break;

		case Constants.FETCH_FLX_POS_TXN:
			Logger.d(TAG, "FETCH_FLX_POS_TXN");
			mFlxPosTxnModel = (FLX_POS_TXNModel) opResult.getResult()
					.getSerializable(Constants.RESULTCODEBEAN);
			checkIsUploadDataReceived();
			if (mFlxPosTxnModel != null) {
				//
			}
			break;

		case Constants.FETCH_SALSMAN_TXN_RECORD:
			Logger.d(TAG, "FETCH_FLX_POS_RECORD");
			ArrayList<SLMN_TXN_Model> slmnTcnModels = (ArrayList<SLMN_TXN_Model>) opResult
					.getResult().getSerializable(Constants.RESULTCODEBEAN);
			checkIsUploadDataReceived();
			if (slmnTcnModels != null && slmnTcnModels.size() > 0) {
				mSlmnTxnModel = slmnTcnModels.get(0);
				//
			}
			break;

		case Constants.FETCH_HELD_TXN_RECORD:
			Logger.d(TAG, "FETCH_HELD_TXN_RECORD");
			mHeldTxnModels = (ArrayList<HeldTransactionModel>) opResult
					.getResult().getSerializable(Constants.RESULTCODEBEAN);
			checkIsUploadDataReceived();
			if (mHeldTxnModels != null && mHeldTxnModels.size() > 0) {
				//
			}
			break;

		case Constants.FETCH_CNSMPTN_RECORD:
			Logger.d(TAG, "FETCH_CNSMPTN_RECORD");
			mCnsmptnTxnModelArr = (ArrayList<PRDCT_CNSMPTN_TXNModel>) opResult
					.getResult().getSerializable(Constants.RESULTCODEBEAN);
			checkIsUploadDataReceived();
			if (mCnsmptnTxnModelArr != null && mCnsmptnTxnModelArr.size() > 0) {
				//
			}
			break;

		case Constants.FETCH_BILL_INSTRCTN_TXN_RECORD:
			Logger.d(TAG, "FETCH_BILL_INSTRCTN_TXN_RECORD");
			mBillInstrctnTxnModelArr = (ArrayList<Bill_INSTRCTN_TXN_Model>) opResult
					.getResult().getSerializable(Constants.RESULTCODEBEAN);
			checkIsUploadDataReceived();
			if (mBillInstrctnTxnModelArr != null
					&& mBillInstrctnTxnModelArr.size() > 0) {
				//
			}
			break;

		case Constants.FETCH_MULTI_CHNG_MST_RECORD:
			Logger.d(TAG, "FETCH_MULTI_CHNG_MST_RECORD");
			mMultiChngMstModels = (ArrayList<MULTI_CHNG_MST_Model>) opResult
					.getResult().getSerializable(Constants.RESULTCODEBEAN);
			checkIsUploadDataReceived();
			if (mMultiChngMstModels != null && mMultiChngMstModels.size() > 0) {
				//
			}
			break;

		case Constants.INSERT_TEMP_TXN_RECORD:
			Logger.d(TAG, "INSERT_TEMP_TXN_RECORD Done");
			break;

		case Constants.FETCH_OFFLINE_TXN:// For both offline Bill_TXn and
											// Flt_pty_pkp upload
			Logger.d(TAG, "FETCH_OFFLINE_TXN Done");
			mOfflineTmpTxnModels = (ArrayList<TempTXNModel>) opResult
					.getResult().getSerializable(Constants.RESULTCODEBEAN);
			if (mOfflineTmpTxnModels != null && mOfflineTmpTxnModels.size() > 0) {
				Logger.d(TAG, "Ofline TXNs: " + mOfflineTmpTxnModels.toString());
				mUploadIndex = 0;
				isOfflineTxn = true;
				if (isOfflineFPPTxnUpload) {// Float Petty Upload
					creteAndUploadOfflie_FPP_OneByOne();
				} else {// TXn upload
					createAndUploadOfflineOneByOne();
				}
			}
			break;

		case Constants.FETCH_BILL_MST_TXNNO_RECORD:
			Logger.d(TAG, "FETCH_BILL_MST_TXNNO_RECORD Done");
			mUpTXNNOBillMStModels = (ArrayList<BILL_Mst_Model>) opResult
					.getResult().getSerializable(Constants.RESULTCODEBEAN);
			if (mUpTXNNOBillMStModels != null
					&& mUpTXNNOBillMStModels.size() > 0) {
				Logger.d(TAG,
						"Fetch_TrasNo: " + mUpTXNNOBillMStModels.toString());
				createAndUploadONebyONe();
			}
			break;

		case Constants.FETCH_PTY_FLT_MST_RECORD:
			Logger.d(TAG, "FETCH_PTY_FLT_MST_RECORD Done");
			mPtyFltPkUpModels = (ArrayList<Petty_Float_Model>) opResult
					.getResult().getSerializable(Constants.RESULTCODEBEAN);
			if (mPtyFltPkUpModels != null && mPtyFltPkUpModels.size() > 0) {
				mUploadDataReceive--;
				if (mUploadDataReceive == 0) {
					createUploadFPPJsonData();
				}
			}
			break;

		case Constants.DELETE_TEMP_TXN_RECORD:
			Logger.d(TAG, "DELETE_TEMP_TXN_RECORD Done");
			break;

		default:
			Logger.d(TAG, "Default Result Code:" + opResult.getResultCode());
			break;

		}
	}

	/**
	 * Method- To create and upload one by one trasactions// Online condition
	 */
	private void createAndUploadONebyONe() {
		Logger.d(TAG, "createAndUploadONebyONe:" + mUploadIndex + ", Size: "
				+ mUpTXNNOBillMStModels.size());
		if (mUploadIndex < mUpTXNNOBillMStModels.size()) {
			BILL_Mst_Model trasacNOModel = mUpTXNNOBillMStModels
					.get(mUploadIndex);
			uploadJsonData(trasacNOModel.getBILL_SCNCD());
			mUploadIndex++;
		} else {// Completed, Notify Activity
			Logger.d(TAG, "ZReport:BillMst Cmplete:" + (mUploadIndex - 1));

			OperationalResult opResult = new OperationalResult();
			opResult.setResultCode(OperationalResult.SUCCESS);
			this.mBaseResponseListener.executePostAction(opResult);
		}
	}

	/**
	 * Method- To create and upload one by one trasactions //Offline condition
	 */
	private void createAndUploadOfflineOneByOne() {
		Logger.d(TAG, "createAndUploadONebyONe:" + mUploadIndex + ", Size: "
				+ mOfflineTmpTxnModels.size());
		if (mUploadIndex < mOfflineTmpTxnModels.size()) {
			TempTXNModel trasacNOModel = mOfflineTmpTxnModels.get(mUploadIndex);
			uploadJsonData(trasacNOModel.getTxn_no());
			mUploadIndex++;
		} else {// Upload Completed, Notify Activity
			Logger.d(TAG, "Offline:Upload Cmplete:" + (mUploadIndex - 1));
			// Delete all TEMP TXN records
			PaymentHelper helper = new PaymentHelper(this);
			helper.deleteTempTXNDB(DBConstants.dbo_TEMP_TRANSACTION_TXN_TABLE,
					true);

			getOfflineFPPrecords();

			/*
			 * OperationalResult opResult = new OperationalResult();
			 * opResult.setResultCode(OperationalResult.SUCCESS);
			 * this.mBaseResponseListener.executePostAction(opResult);
			 */
		}
	}

	/**
	 * To get all offline FPP txn no's
	 * @param helper
	 */
	private void getOfflineFPPrecords() {
		// If Bill TXN offline upload complete, Upload Offline FLT_PTY_PKUP
		// records
		if (UserSingleton.getInstance(getContext()).isNetworkAvailable) {
			Logger.d(TAG, "N/W Avail: Upload Offline FLT_PTY_PKUP TXN");
			// GEt all not uploaded FPP from DB
			String uploadFlag = "N";
			isOfflineFPPTxnUpload = true;
			PaymentHelper helper = new PaymentHelper(this);
			helper.retrieveOfflineTempTxnRecordsFrmDB(uploadFlag,
					DBConstants.dbo_TEMP_FLT_PTY_PKUP_TXN_TABLE, true);
		}
	}

	/**
	 * Method- To create and upload one by one FPP //Offline condition
	 */
	private void creteAndUploadOfflie_FPP_OneByOne() {
		Logger.d(TAG, "creteAndUploadOfflie_FPP_OneByOne:" + mUploadIndex
				+ ", Size: " + mOfflineTmpTxnModels.size());
		if (mUploadIndex < mOfflineTmpTxnModels.size()) {
			TempTXNModel trasacNOModel = mOfflineTmpTxnModels.get(mUploadIndex);
			uploadFPPJsonData(trasacNOModel.getTxn_no());
			mUploadIndex++;
		} else {// Completed, Notify Activity
			Logger.d(TAG, "Offline:Upload Cmplete:" + (mUploadIndex - 1));
			// Delete all TEMP TXN records
			PaymentHelper helper = new PaymentHelper(this);
			helper.deleteTempTXNDB(DBConstants.dbo_TEMP_FLT_PTY_PKUP_TXN_TABLE,
					true);

			/*
			 * OperationalResult opResult = new OperationalResult();
			 * opResult.setResultCode(OperationalResult.SUCCESS);
			 * this.mBaseResponseListener.executePostAction(opResult);
			 */
		}
	}

	@Override
	public void errorReceived(OperationalResult opResult) {
		Logger.d(TAG, "errorReceived");
		switch (opResult.getRequestResponseCode()) {

		case OperationalResult.DB_ERROR:
			Logger.d(TAG, "DB_ERROR");
			if(opResult.getResultCode() != Constants.FETCH_OFFLINE_TXN){
				if(mUpBillMStModels != null && mUpBillMStModels.size() > 0)
					checkIsUploadDataReceived();
			}
			
			//Start uploading FPP data
			if(opResult.getResultCode() == Constants.FETCH_OFFLINE_TXN){
				mUploadIndex = 0;
				isOfflineTxn = false;
				isOfflineFPPTxnUpload = true;
				getOfflineFPPrecords();
			}
			break;

		default:
			break;
		}
	}

	@Override
	public Context getContext() {
		// TODO Auto-generated method stub
		return mContext;
	}

	@Override
	public void networkChangeListener(boolean isNetworkAvailable) {
		if (isNetworkAvailable) {
			Logger.d(TAG, "N/W Avail: Upload Offline TXN");
			// GEt all not uploaded transactions from DB
			PaymentHelper helper = new PaymentHelper(this);
			String uploadFlag = "N";
			helper.retrieveOfflineTempTxnRecordsFrmDB(uploadFlag,
					DBConstants.dbo_TEMP_TRANSACTION_TXN_TABLE, true);
		}
	}

	/**
	 * show alert
	 * 
	 * @param szAlert
	 * @param szMessage
	 */
	public void showAlertMessage(String szAlert, String szMessage) {

		AlertDialog.Builder builder = new AlertDialog.Builder(
				MPOSApplication.getContext());
		builder.setCancelable(false)
				.setPositiveButton(
						MPOSApplication.getContext().getResources()
								.getString(R.string.ok),
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
