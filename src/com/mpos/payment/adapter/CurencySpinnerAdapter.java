package com.mpos.payment.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.mpos.R;
import com.mpos.master.model.CURRENCY_MST_MODEL;

public class CurencySpinnerAdapter extends BaseAdapter implements
		SpinnerAdapter {

	private Context mContext;
	ArrayList<CURRENCY_MST_MODEL> mCurrencyList;

	public CurencySpinnerAdapter(Context context, int resource,
			ArrayList<CURRENCY_MST_MODEL> objects) {
		this.mContext = context;
		this.mCurrencyList = objects;
	}

	@Override
	public int getCount() {

		return mCurrencyList.size();
	}

	@Override
	public long getItemId(int position) {

		return 0;
	}

	@Override
	public int getItemViewType(int position) {

		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View row = convertView;

		if (row == null) {

			row = LayoutInflater.from(mContext).inflate(
					R.layout.currency_spinner_row, parent, false);

		}

		CURRENCY_MST_MODEL current = mCurrencyList.get(position);

		TextView name = (TextView) row.findViewById(R.id.spinnerText);
		name.setText(current.getCurr_abbrName());

		return row;

	}

	@Override
	public int getViewTypeCount() {
		return 0;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	public void registerDataSetObserver(DataSetObserver observer) {

	}

	@Override
	public void unregisterDataSetObserver(DataSetObserver observer) {

	}

	@Override
	public Object getItem(int position) {
		return mCurrencyList.get(position).getCurr_abbrName();
	}

}
