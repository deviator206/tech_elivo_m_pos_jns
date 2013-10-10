package com.mpos.home.adapter;

import java.util.LinkedList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mpos.R;
import com.mpos.home.model.NavigationModel;

public class NavigationFlowAdpter extends BaseAdapter {

	private Activity mContext;
	private LinkedList<NavigationModel> mNavigationDataList;

	public NavigationFlowAdpter() {

	}

	public NavigationFlowAdpter(Activity mContext,
			LinkedList<NavigationModel> navigationTagArr) {
		this.mContext = mContext;
		this.mNavigationDataList = navigationTagArr;
	}

	@Override
	public int getCount() {
		return mNavigationDataList.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater mInflater = mContext.getLayoutInflater();
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.navigation_flow_row, null);
		}
		TextView typeTV = (TextView) convertView.findViewById(R.id.typeTV);
		typeTV.setText(mNavigationDataList.get(position).getNavigation_Name());

		return convertView;
	}

}
