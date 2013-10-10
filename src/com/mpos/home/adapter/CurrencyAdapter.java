/*
 * Copyright (C) 2013 USAP Pvt. Ltd. All Rights Reserved.
 * jaggarnaut-studios pvt. ltd. PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.mpos.home.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mpos.R;
import com.mpos.framework.common.Constants;
import com.mpos.master.model.CURRENCY_MST_MODEL;

/**
 * Description-
 * 
 * @author jaggarnaut-studios
 */

public class CurrencyAdapter extends BaseAdapter {

	public static final String TAG = Constants.APP_TAG
			+ CurrencyAdapter.class.getSimpleName();

	private ArrayList<CURRENCY_MST_MODEL> mCurrencyDataList = null;
	private Context mContext;

	public CurrencyAdapter(ArrayList<CURRENCY_MST_MODEL> currecnyData,
			Context context) {
		this.mContext = context;
		this.mCurrencyDataList = currecnyData;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mCurrencyDataList.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		View view = convertView;
		ViewHolder holder = null;

		if (convertView == null) {
			view = LayoutInflater.from(mContext).inflate(
					R.layout.sales_person_item, null);
			holder = new ViewHolder();
			createHolder(view, holder, position);
		} else {
			holder = (ViewHolder) view.getTag();
		}

		holder.nameText.setVisibility(View.GONE);

		if (mCurrencyDataList.get(position).isSelected()) {
			holder.currText.setBackgroundResource(R.color.blue);
		} else {
			holder.currText.setBackgroundResource(R.color.white);
		}
		holder.currText.setText(mCurrencyDataList.get(position).getCurr_name());

		return view;
	}

	private class ViewHolder {
		private TextView currText;
		private TextView nameText;
	}

	/**
	 * To Create view holder
	 * 
	 * @param vi
	 * @param holder
	 * @param position
	 */
	private void createHolder(View vi, final ViewHolder holder, int position) {
		holder.currText = (TextView) vi.findViewById(R.id.codeText);
		holder.nameText = (TextView) vi.findViewById(R.id.nameText);
		vi.setTag(holder);
	}

}
