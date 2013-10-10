package com.mpos.payment.activity;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.DatePicker;

public class DateDialogFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

	private View mSelectedView;
	private  PaymentFragment mPaymentFragment; 
	public DateDialogFragment(){

	}
	public DateDialogFragment(View selectedView, PaymentFragment paymentFragment){
		super();
		mPaymentFragment = paymentFragment;
		mSelectedView = selectedView;
	}
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		final Calendar calendar = Calendar.getInstance();
		int yy = calendar.get(Calendar.YEAR);
		int mm = calendar.get(Calendar.MONTH);
		int dd = calendar.get(Calendar.DAY_OF_MONTH);
		
		return new DatePickerDialog(getActivity(), this, yy, mm, dd);
	}

	public void onDateSet(DatePicker view, int yy, int mm, int dd) {
		mPaymentFragment.populateSetDate(mSelectedView,yy, mm+1, dd);
	}
}