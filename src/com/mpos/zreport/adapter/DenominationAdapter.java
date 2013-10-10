package com.mpos.zreport.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mpos.R;
import com.mpos.master.model.DNMTN_MSTModel;

public class DenominationAdapter extends BaseAdapter {

	private Context mContext;
	ArrayList<DNMTN_MSTModel> mDenominationList;

	public DenominationAdapter(Context context, int resource,
			ArrayList<DNMTN_MSTModel> objects) {
		this.mContext = context;
		this.mDenominationList = objects;
	}

	
	//@Override
	//public View getDropDownView(int position, View convertView, ViewGroup parent) {}

	@Override
	public int getCount() {

		return mDenominationList.size();
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

		DNMTN_MSTModel current = mDenominationList.get(position);

		TextView name = (TextView) row.findViewById(R.id.spinnerText);
		name.setText(""+current.getVALUE());

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
		return mDenominationList.get(position).getVALUE();
	}

}
