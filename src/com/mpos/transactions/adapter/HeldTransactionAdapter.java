/*
 * Copyright (C) 2013 USAP Pvt. Ltd. All Rights Reserved.
 * USAP PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

/**
 * 
 */
package com.mpos.transactions.adapter;

import java.util.ArrayList;
import java.util.StringTokenizer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mpos.R;
import com.mpos.framework.common.Constants;
import com.mpos.master.model.BILL_Mst_Model;

public class HeldTransactionAdapter extends BaseAdapter {

	private ArrayList<BILL_Mst_Model> mTransactionArr;
	private Context mContext;

	public HeldTransactionAdapter(ArrayList<BILL_Mst_Model> transactionArr,
			Context context) {
		mTransactionArr = transactionArr;
		mContext = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mTransactionArr.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			LayoutInflater inflater = LayoutInflater.from(mContext);
			convertView = inflater
					.inflate(R.layout.held_transaction_item, null);
		}

		TextView txnText = (TextView) convertView.findViewById(R.id.txnText);
		txnText.setText(mTransactionArr.get(position).getBILL_SCNCD());

		String dateTime = Constants.getFormattedDate(mTransactionArr.get(
				position).getBILL_SYS_DATE());
		StringTokenizer tk = new StringTokenizer(dateTime);

		String date = "";
		String time = "";
		if (tk.countTokens() == 2) {
			date = tk.nextToken(); // <--- yyyy-mm-dd
			time = tk.nextToken(); // <--- hh:mm:ss
		} else if (tk.countTokens() == 1) {
			date = tk.nextToken(); // <--- yyyy-mm-dd
		}

		TextView dateText = (TextView) convertView.findViewById(R.id.dateText);
		dateText.setText(date);

		TextView timeText = (TextView) convertView.findViewById(R.id.timeText);
		timeText.setText(time);

		TextView customerText = (TextView) convertView
				.findViewById(R.id.customerText);
		customerText.setText(mTransactionArr.get(position).getUSR_NAME());

		LinearLayout topHeldLL = (LinearLayout) convertView
				.findViewById(R.id.topHeldLL);

		if (mTransactionArr.get(position).isSelected()) {
			topHeldLL.setBackgroundResource(R.drawable.blue_rectangle_shape);
		} else {
			topHeldLL.setBackgroundResource(R.drawable.rectangle_shape);
		}

		return convertView;
	}

}
