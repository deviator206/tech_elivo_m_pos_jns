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
import com.mpos.framework.common.Logger;
import com.mpos.master.model.SLMN_MST_Model;

public class SalesPersonAdapter extends BaseAdapter {

	public static final String TAG = Constants.APP_TAG
			+ SalesPersonAdapter.class.getSimpleName();

	private ArrayList<SLMN_MST_Model> mSalesPersonArray = null;
	private Context mContext;

	public SalesPersonAdapter(ArrayList<SLMN_MST_Model> transactionArray,
			Context context) {
		this.mContext = context;
		this.mSalesPersonArray = transactionArray;
	}

	@Override
	public int getCount() {

		return mSalesPersonArray.size();
	}

	@Override
	public Object getItem(int arg0) {

		return arg0;
	}

	@Override
	public long getItemId(int arg0) {

		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		Logger.d(TAG, "getView");
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

		if (mSalesPersonArray.get(position).isSlected()) {
			holder.codeText.setBackgroundResource(R.color.blue);
			holder.nameText.setBackgroundResource(R.color.blue);
		} else {
			holder.codeText.setBackgroundResource(R.color.white);
			holder.nameText.setBackgroundResource(R.color.white);
		}
		holder.codeText.setText(mSalesPersonArray.get(position).getSlmn_code());
		holder.nameText.setText(String.valueOf(mSalesPersonArray.get(position)
				.getSlmn_name()));

		return view;
	}

	private class ViewHolder {
		private TextView codeText;
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
		holder.codeText = (TextView) vi.findViewById(R.id.codeText);
		holder.nameText = (TextView) vi.findViewById(R.id.nameText);
		vi.setTag(holder);
	}
}
