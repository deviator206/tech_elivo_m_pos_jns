package com.mpos.master.model;

import java.io.Serializable;
import java.util.ArrayList;

import android.util.Log;

public class UserRightsModel implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String PRICE_CHANGE = "PRICE CHANGE";
	public static final String ALLOW_CASH_PAYMENT_FOR_REFUND = "ALLOW CASH PAYMENT FOR REFUND";
    public static final String ALLOW_CHEQUE_PAYMENT_FOR_BILL= "ALLOW CHEQUE PAYMENT FOR BILL";
    public static final String ALLOW_CHEQUE_PAYM_NT_FOR_REFUND= "ALLOW CHEQUE PAYM_NT FOR REFUND";
    public static final String ALLOW_CREDIT_CARD_PAYMENT_FOR_BILL= "ALLOW CREDIT CARD PAYMENT FOR BILL";
    public static final String ALLOW_CREDIT_CARD_PAYMENT_FOR_REFUND= "ALLOW CREDIT CARD PAYMENT FOR REFUND";
    public static final String ALLOW_CREDIT_NOTE_PAYMENT_FOR_BILL= "ALLOW CREDIT NOTE PAYMENT FOR BILL";
    public static final String ALLOW_CREDIT_NOTE_PAYMENT_FOR_REFUND= "ALLOW CREDIT NOTE PAYMENT FOR REFUND";
    public static final String ALLOW_DEPOSIT_PAYMENT_FOR_BILL= "ALLOW DEPOSIT PAYMENT FOR BILL";
    public static final String ALLOW_INVOICE_PAYMENT_FOR_BILL= "ALLOW INVOICE PAYMENT FOR BILL";
    public static final String ALLOW_LOYALTY_REDEMPTION_PAYMENT_FOR_BILL= "ALLOW LOYALTY REDEMPTION PAYMENT FOR BILL";
    public static final String ALLOW_NEGATIVE_SALE_INFO= "ALLOW NEGATIVE SALE IN FO";
    public static final String ALLOW_PURE_REFUND= "ALLOW PURE REFUND";
    public static final String ALLOW_SMART_CARD_TRANS= "ALLOW SMART CARD TRANS";
    public static final String ALLOW_BILL_TO_RE_PRINT= "ALLOW BILL TO RE-PRINT";
    public static final String ALLOW_CAP_PAYMENT_FOR_BILL= "ALLOW CAP PAYMENT FOR BILL";
    public static final String ALLOW_CASH_PAYMENT_FOR_BILL= "ALLOW CASH PAYMENT FOR BILL";
    public static final String ALLOW_TO_ADD_FLOAT= "ALLOW TO ADD FLOAT";
    public static final String ALLOW_TO_RECEIVE_CREDIT_PAYMENT= "ALLOW TO RECEIVE CREDIT PAYMENT";
    public static final String ALLOW_VOUCHER_PAYMENT_FOR_BILL= "ALLOW VOUCHER PAYMENT FOR BILL";
    public static final String CASH_DRAWER= "CASH DRAWER";
    public static final String CASH_PICKUP= "CASH PICKUP";
    public static final String DELETE_HELD_TRANS= "DELETE HELD TRANS";
    public static final String DEPOSIT_COLLECTION = "DEPOSIT COLLECTION";
    public static final String HELD_LIMIT= "HELD LIMIT";
    public static final String Hold_Terminal= "Hold Terminal";
    public static final String LINE_DISCOUNT= "LINE DISCOUNT";
    public static final String MAX_DISCOUNT= "MAX DISCOUNT";
    public static final String MAX_REFUND_DAYS= "MAX REFUND DAYS";
    public static final String NAVIGATE_TO_POS_SCREEN = "NAVIGATE TO POS SCREEN";
    public static final String PETTY_CASH_PICKUP= "PETTY CASH PICKUP";
    public static final String PRINT_X_REPORT= "PRINT X REPORT";
    public static final String PRINT_Z_REPORT= "PRINT Z REPORT";
    public static final String QUANTITY_CHANGE= "QUANTITY CHANGE";
    public static final String RECEIVE_CAP_PAYMENT= "RECEIVE CAP PAYMENT";
    public static final String REFUND_QUANTITY= "REFUND QUANTITY";
    public static final String SELL_GIFT_VOUCHER= "SELL GIFT VOUCHER";
    public static final String SMART_CARD_SECOND_TRANS= "SMART CARD SECOND TRANS";
    public static final String UPDATE_MASTERS_FO= "UPDATE MASTERS FO";
    public static final String VAT_EXEPMTION= "VAT EXEPMTION";
    public static final String VOID_LIMIT= "VOID LIMIT";
    
    
    public int alllow_CASH_PAYMENT_FOR_REFUND ;
    public int alllow_CHEQUE_PAYMENT_FOR_BILL;
    public int alllow_CHEQUE_PAYM_NT_FOR_REFUND;
    public int alllow_CREDIT_CARD_PAYMENT_FOR_BILL;
    public int alllow_CREDIT_CARD_PAYMENT_FOR_REFUND;
    public int alllow_CREDIT_NOTE_PAYMENT_FOR_BILL;
    public int alllow_CREDIT_NOTE_PAYMENT_FOR_REFUND;
    public int alllow_DEPOSIT_PAYMENT_FOR_BILL;
    public int alllow_INVOICE_PAYMENT_FOR_BILL;
    public boolean alllow_LOYALTY_REDEMPTION_PAYMENT_FOR_BILL;
    public boolean alllow_PRICE_CHANGE;
    public boolean alllow_NEGATIVE_SALE_INFO;
    public boolean alllow_PURE_REFUND;
    public boolean alllow_SMART_CARD_TRANS;
    public boolean alllow_BILL_TO_RE_PRINT;
    public int alllow_CAP_PAYMENT_FOR_BILL;
    public int alllow_CASH_PAYMENT_FOR_BILL;
    public boolean alllow_TO_ADD_FLOAT;
    public boolean alllow_TO_RECEIVE_CREDIT_PAYMENT;
    public int alllow_VOUCHER_PAYMENT_FOR_BILL;
    public boolean CASH_DRAWER_FLAG;
    public boolean CASH_PICKUP_FLAG;
    public boolean DELETE_HELD_TRANS_FLAG;
    public boolean DEPOSIT_COLLECTION_FLAG;
    public int HELD_LIMIT_MAX;
    public boolean Hold_Terminal_FLAG;
    public int LINE_DISCOUNT_MAX;
    public int DISCOUNT;
    public int REFUND_DAYS;
    public boolean NAVIGATE_TO_POS_SCREEN_FLAG;
    public boolean PETTY_CASH_PICKUP_FLAG;
	public boolean PRINT_X_REPORT_FLAG;
	public boolean PRINT_Z_REPORT_FLAG;
    public boolean QUANTITY_CHANGE_FLAG;
    public boolean RECEIVE_CAP_PAYMENT_FLAG;
    public boolean REFUND_QUANTITY_FLAG;
    public boolean SELL_GIFT_VOUCHER_FLAG;
    public boolean SMART_CARD_SECOND_TRANS_FLAG;
    public boolean UPDATE_MASTERS_FO_FLAG;
    public boolean VAT_EXEPMTION_FLAG;
    public int VOID_LIMIT_MAX;
    
    public  UserRightsModel createUserRIghts(ArrayList<USR_ASSGND_RGHTSModel> userAssgndRightsList){
    	UserRightsModel userRights = new UserRightsModel();
    	if(userAssgndRightsList!=null && userAssgndRightsList.size()>0){
    		Log.v("userrightsmodel", "size :"+userAssgndRightsList.size());
    		
			for (int i = 0; i < userAssgndRightsList.size(); i++) {
				USR_ASSGND_RGHTSModel userAssgndRightModel = userAssgndRightsList
						.get(i);

				if (userAssgndRightModel.getFLD_TYPE().equalsIgnoreCase("L")) {
					boolean rightValue;
					if (userAssgndRightModel.getRGHT_VALUE().equalsIgnoreCase(
							"Y")) {
						rightValue = true;
					} else {
						rightValue = false;
					}
    				if(userAssgndRightModel.getRGHT_NAME().equalsIgnoreCase(ALLOW_LOYALTY_REDEMPTION_PAYMENT_FOR_BILL)){
    					userRights.alllow_LOYALTY_REDEMPTION_PAYMENT_FOR_BILL = rightValue;
    				}else if(userAssgndRightModel.getRGHT_NAME().equalsIgnoreCase(ALLOW_NEGATIVE_SALE_INFO)){
    					userRights.alllow_NEGATIVE_SALE_INFO = rightValue;
    				}else if(userAssgndRightModel.getRGHT_NAME().equalsIgnoreCase(ALLOW_PURE_REFUND)){
    					userRights.alllow_PURE_REFUND = rightValue;
    				}else if(userAssgndRightModel.getRGHT_NAME().equalsIgnoreCase(ALLOW_SMART_CARD_TRANS)){
    					userRights.alllow_SMART_CARD_TRANS = rightValue;
    				}else if(userAssgndRightModel.getRGHT_NAME().equalsIgnoreCase(ALLOW_BILL_TO_RE_PRINT)){
    					userRights.alllow_BILL_TO_RE_PRINT = rightValue;
    				}else if(userAssgndRightModel.getRGHT_NAME().equalsIgnoreCase(ALLOW_TO_ADD_FLOAT)){
    					userRights.alllow_TO_ADD_FLOAT = rightValue;
    				}else if(userAssgndRightModel.getRGHT_NAME().equalsIgnoreCase(ALLOW_TO_RECEIVE_CREDIT_PAYMENT)){
    					userRights.alllow_TO_RECEIVE_CREDIT_PAYMENT = rightValue;
    				}else if(userAssgndRightModel.getRGHT_NAME().equalsIgnoreCase(CASH_DRAWER)){
    					userRights.CASH_DRAWER_FLAG = rightValue;
    				}else if(userAssgndRightModel.getRGHT_NAME().equalsIgnoreCase(CASH_PICKUP)){
    					userRights.CASH_PICKUP_FLAG = rightValue;
    				}else if(userAssgndRightModel.getRGHT_NAME().equalsIgnoreCase(DELETE_HELD_TRANS)){
    					userRights.DELETE_HELD_TRANS_FLAG = rightValue;
    				}else if(userAssgndRightModel.getRGHT_NAME().equalsIgnoreCase(DEPOSIT_COLLECTION)){
    					userRights.DEPOSIT_COLLECTION_FLAG = rightValue;
    				}else if(userAssgndRightModel.getRGHT_NAME().equalsIgnoreCase(Hold_Terminal)){
    					userRights.Hold_Terminal_FLAG = rightValue;
    				}else if(userAssgndRightModel.getRGHT_NAME().equalsIgnoreCase(NAVIGATE_TO_POS_SCREEN)){
    					userRights.NAVIGATE_TO_POS_SCREEN_FLAG = rightValue;
    				}else if(userAssgndRightModel.getRGHT_NAME().equalsIgnoreCase(PETTY_CASH_PICKUP)){
    					userRights.PETTY_CASH_PICKUP_FLAG = rightValue;
    				}else if(userAssgndRightModel.getRGHT_NAME().equalsIgnoreCase(PRINT_X_REPORT)){
    					userRights.PRINT_X_REPORT_FLAG = rightValue;
    				}else if(userAssgndRightModel.getRGHT_NAME().equalsIgnoreCase(PRINT_Z_REPORT)){
    					userRights.PRINT_Z_REPORT_FLAG = rightValue;
    				}else if(userAssgndRightModel.getRGHT_NAME().equalsIgnoreCase(QUANTITY_CHANGE)){
    					userRights.QUANTITY_CHANGE_FLAG = rightValue;
    				}else if(userAssgndRightModel.getRGHT_NAME().equalsIgnoreCase(RECEIVE_CAP_PAYMENT)){
    					userRights.RECEIVE_CAP_PAYMENT_FLAG = rightValue;
    				}else if(userAssgndRightModel.getRGHT_NAME().equalsIgnoreCase(REFUND_QUANTITY)){
    					userRights.REFUND_QUANTITY_FLAG = rightValue;
    				}else if(userAssgndRightModel.getRGHT_NAME().equalsIgnoreCase(SELL_GIFT_VOUCHER)){
    					userRights.SELL_GIFT_VOUCHER_FLAG = rightValue;
    				}else if(userAssgndRightModel.getRGHT_NAME().equalsIgnoreCase(SMART_CARD_SECOND_TRANS)){
    					userRights.SMART_CARD_SECOND_TRANS_FLAG = rightValue;
    				}else if(userAssgndRightModel.getRGHT_NAME().equalsIgnoreCase(UPDATE_MASTERS_FO)){
    					userRights.UPDATE_MASTERS_FO_FLAG = rightValue;
    				}else if(userAssgndRightModel.getRGHT_NAME().equalsIgnoreCase(VAT_EXEPMTION)){
    					userRights.VAT_EXEPMTION_FLAG = rightValue;
    				}else if(userAssgndRightModel.getRGHT_NAME().equalsIgnoreCase(PRICE_CHANGE)){
    					userRights.alllow_PRICE_CHANGE = rightValue;
    				}
    				
    			}else if(userAssgndRightModel.getFLD_TYPE().equalsIgnoreCase("N")){
	    			if(!userAssgndRightModel.getRGHT_VALUE().equalsIgnoreCase("")){
	    				if(userAssgndRightModel.getRGHT_NAME().equalsIgnoreCase(ALLOW_CASH_PAYMENT_FOR_REFUND)){
	    					userRights.alllow_CASH_PAYMENT_FOR_REFUND = Integer.parseInt(userAssgndRightModel.getRGHT_VALUE());
	    				}else if(userAssgndRightModel.getRGHT_NAME().equalsIgnoreCase(ALLOW_CHEQUE_PAYMENT_FOR_BILL)){
	    					userRights.alllow_CHEQUE_PAYMENT_FOR_BILL = Integer.parseInt(userAssgndRightModel.getRGHT_VALUE());
	    				}else if(userAssgndRightModel.getRGHT_NAME().equalsIgnoreCase(ALLOW_CHEQUE_PAYM_NT_FOR_REFUND)){
	    					userRights.alllow_CHEQUE_PAYM_NT_FOR_REFUND = Integer.parseInt(userAssgndRightModel.getRGHT_VALUE());
	    				}else if(userAssgndRightModel.getRGHT_NAME().equalsIgnoreCase(ALLOW_CREDIT_CARD_PAYMENT_FOR_BILL)){
	    					userRights.alllow_CREDIT_CARD_PAYMENT_FOR_BILL = Integer.parseInt(userAssgndRightModel.getRGHT_VALUE());
	    				}else if(userAssgndRightModel.getRGHT_NAME().equalsIgnoreCase(ALLOW_CREDIT_CARD_PAYMENT_FOR_REFUND)){
	    					userRights.alllow_CREDIT_CARD_PAYMENT_FOR_REFUND = Integer.parseInt(userAssgndRightModel.getRGHT_VALUE());
	    				}else if(userAssgndRightModel.getRGHT_NAME().equalsIgnoreCase(ALLOW_CREDIT_NOTE_PAYMENT_FOR_BILL)){
	    					userRights.alllow_CREDIT_NOTE_PAYMENT_FOR_BILL = Integer.parseInt(userAssgndRightModel.getRGHT_VALUE());
	    				}else if(userAssgndRightModel.getRGHT_NAME().equalsIgnoreCase(ALLOW_CREDIT_NOTE_PAYMENT_FOR_REFUND)){
	    					userRights.alllow_CREDIT_NOTE_PAYMENT_FOR_REFUND = Integer.parseInt(userAssgndRightModel.getRGHT_VALUE());
	    				}else if(userAssgndRightModel.getRGHT_NAME().equalsIgnoreCase(ALLOW_DEPOSIT_PAYMENT_FOR_BILL)){
	    					userRights.alllow_DEPOSIT_PAYMENT_FOR_BILL = Integer.parseInt(userAssgndRightModel.getRGHT_VALUE());
	    				}else if(userAssgndRightModel.getRGHT_NAME().equalsIgnoreCase(ALLOW_INVOICE_PAYMENT_FOR_BILL)){
	    					userRights.alllow_INVOICE_PAYMENT_FOR_BILL = Integer.parseInt(userAssgndRightModel.getRGHT_VALUE());
	    				}else if(userAssgndRightModel.getRGHT_NAME().equalsIgnoreCase(ALLOW_CAP_PAYMENT_FOR_BILL)){
	    					userRights.alllow_CAP_PAYMENT_FOR_BILL = Integer.parseInt(userAssgndRightModel.getRGHT_VALUE());
	    				}else if(userAssgndRightModel.getRGHT_NAME().equalsIgnoreCase(ALLOW_CASH_PAYMENT_FOR_BILL)){
	    					userRights.alllow_CASH_PAYMENT_FOR_BILL = Integer.parseInt(userAssgndRightModel.getRGHT_VALUE());
	    				}else if(userAssgndRightModel.getRGHT_NAME().equalsIgnoreCase(ALLOW_VOUCHER_PAYMENT_FOR_BILL)){
	    					userRights.alllow_VOUCHER_PAYMENT_FOR_BILL = Integer.parseInt(userAssgndRightModel.getRGHT_VALUE());
	    				}else if(userAssgndRightModel.getRGHT_NAME().equalsIgnoreCase(HELD_LIMIT)){
	    					userRights.HELD_LIMIT_MAX = Integer.parseInt(userAssgndRightModel.getRGHT_VALUE());
	    				}else if(userAssgndRightModel.getRGHT_NAME().equalsIgnoreCase(LINE_DISCOUNT)){
	    					userRights.LINE_DISCOUNT_MAX = Integer.parseInt(userAssgndRightModel.getRGHT_VALUE());
	    				}else if(userAssgndRightModel.getRGHT_NAME().equalsIgnoreCase(MAX_DISCOUNT)){
	    					userRights.DISCOUNT = Integer.parseInt(userAssgndRightModel.getRGHT_VALUE());
	    				}else if(userAssgndRightModel.getRGHT_NAME().equalsIgnoreCase(MAX_REFUND_DAYS)){
	    					userRights.REFUND_DAYS = Integer.parseInt(userAssgndRightModel.getRGHT_VALUE());
	    				}else if(userAssgndRightModel.getRGHT_NAME().equalsIgnoreCase(VOID_LIMIT)){
	    					userRights.VOID_LIMIT_MAX = Integer.parseInt(userAssgndRightModel.getRGHT_VALUE());
						}
	    				}
				}

			}

			Log.v("userrightsmodel", ":" + userRights);
			userRights.alllow_BILL_TO_RE_PRINT = false;

		}
    	return userRights;
    	
    }
    
    
}
