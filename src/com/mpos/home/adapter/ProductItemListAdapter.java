/*
 * Copyright (C) 2013  All Rights Reserved.
 *  PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.mpos.home.adapter;

import java.util.List;

import android.app.Service;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mpos.R;
import com.mpos.master.model.BaseModel;
import com.mpos.master.model.PRDCT_MST_Model;

/**
 * Description- Product Item GridView Adapter.
 * 

 */
public class ProductItemListAdapter extends ArrayAdapter<BaseModel> {

	public ProductItemListAdapter(Context context, int textViewResourceId,
			List<BaseModel> objects) {
		super(context, textViewResourceId, objects);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		PRDCT_MST_Model item = (PRDCT_MST_Model) getItem(position);

		ViewHolder holder;
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) getContext()
					.getSystemService(Service.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.item_row, parent, false);
			holder = new ViewHolder();
			holder.imageView = (ImageView) convertView
					.findViewById(R.id.image_view);
			holder.titleTextView = (TextView) convertView
					.findViewById(R.id.title_text_view);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.titleTextView.setText(item.getPRDCT_SHRT_DSCRPTN());
		return convertView;
	}

	/**
	 * ViewHolder.
	 */
	private class ViewHolder {
		public ImageView imageView;
		public TextView titleTextView;

	}

}
