package com.mpos.payment.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mpos.R;

public class PaymentTypeAdapter extends BaseAdapter {

	private ArrayList<String> mPaymentTypeArr;
	private Context mContext;

	public PaymentTypeAdapter(ArrayList<String> paymentTypeArr, Context context) {
		mPaymentTypeArr = paymentTypeArr;
		mContext = context;
	}

	@Override
	public int getCount() {
		
		return mPaymentTypeArr.size();
	}

	@Override
	public Object getItem(int arg0) {
		
		return arg0;
	}

	@Override
	public long getItemId(int position) {
		
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if(convertView==null){
			LayoutInflater inflater = LayoutInflater.from(mContext);
			convertView = inflater.inflate(R.layout.payment_item, null);
		}
		
		TextView paymentText = (TextView) convertView.findViewById(R.id.paymentTypeText);
		paymentText.setText(mPaymentTypeArr.get(position));
		
		return convertView;
	}

}
