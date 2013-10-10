package com.mpos.home.adapter;

import java.util.ArrayList;

import android.app.Service;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mpos.R;
import com.mpos.framework.Util.MPOSBase64;
import com.mpos.framework.common.Logger;
import com.mpos.master.model.BaseModel;
import com.mpos.master.model.PRDCT_ANALYS_MST_Model;
import com.mpos.master.model.PRDCT_BINLOC_MST_Model;
import com.mpos.master.model.PRDCT_DPT_MST_Model;
import com.mpos.master.model.PRDCT_GRP_MST_Model;
import com.mpos.master.model.PRDCT_MST_Model;

public class GridAdapter extends BaseAdapter {

	private int classType;
	private Context mContext;
	private ArrayList<BaseModel> dataArr;

	public GridAdapter(ArrayList<BaseModel> dataArr, int type, Context context) {
		this.mContext = context;
		this.classType = type;
		this.dataArr = dataArr;
	}

	@Override
	public int getCount() {
		return dataArr.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	/**
	 * ViewHolder.
	 */
	private class ViewHolder {
		public ImageView imageView;
		public TextView titleTextView;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder;
		// if (convertView == null) {
		LayoutInflater inflater = (LayoutInflater) mContext
				.getSystemService(Service.LAYOUT_INFLATER_SERVICE);
		convertView = inflater.inflate(R.layout.item_row, parent, false);
		holder = new ViewHolder();
		holder.imageView = (ImageView) convertView
				.findViewById(R.id.image_view);
		holder.titleTextView = (TextView) convertView
				.findViewById(R.id.title_text_view);
		convertView.setTag(holder);
		// } else {
		// holder = (ViewHolder) convertView.getTag();
		// }

		// holder.imageView.setVisibility(View.GONE);
		switch (classType) {
		case 1:// Group
			holder.titleTextView.setText(((PRDCT_GRP_MST_Model) dataArr
					.get(position)).getGrp_name());
			break;

		case 2:// Department
			holder.titleTextView.setText(((PRDCT_DPT_MST_Model) dataArr
					.get(position)).getDpt_name());
			break;

		case 3:// Analysis
			holder.titleTextView.setText(((PRDCT_ANALYS_MST_Model) dataArr
					.get(position)).getAnalys_name());
			break;

		case 4:// BinLocation
			holder.titleTextView.setText(((PRDCT_BINLOC_MST_Model) dataArr
					.get(position)).getBinloc_name());
			break;

		case 5:// Products

			PRDCT_MST_Model prdctMstModel = (PRDCT_MST_Model) dataArr
					.get(position);

			holder.titleTextView.setText(((PRDCT_MST_Model) dataArr
					.get(position)).getPRDCT_SHRT_DSCRPTN());

			if (prdctMstModel.getPRDCT_IMG() != null
					&& prdctMstModel.getPRDCT_IMG().length() > 0) {
				// Logger.d("Grid", "Inside Image");
				String encodedImage = ((PRDCT_MST_Model) dataArr.get(position))
						.getPRDCT_IMG();
				byte[] bytarray = MPOSBase64.decodeBase64(encodedImage
						.getBytes());
				Bitmap bmimage = BitmapFactory.decodeByteArray(bytarray, 0,
						bytarray.length);
			
				Bitmap scaled = Bitmap
						.createScaledBitmap(bmimage, 50, 50, true);

				holder.imageView.setImageBitmap(scaled);

			} // If image not available
			else if (prdctMstModel.getPRDCT_CLR() != null
					&& prdctMstModel.getPRDCT_CLR().length() > 0
					&& !prdctMstModel.getPRDCT_CLR().equalsIgnoreCase("null")) {
				// Logger.d("Grid", "Inside Color");
				String color = "#" + prdctMstModel.getPRDCT_CLR();
				System.out.println("#@@@ " + color);
				try {
					holder.imageView
							.setBackgroundColor(Color.parseColor(color));
				} catch (IllegalArgumentException e) {
					holder.imageView.setBackgroundColor(Color
							.parseColor("#FF1212"));
				}
				// holder.imageView.setBackgroundResource(R.drawable.grid_background_gradient);

			} // If nothing available
			else {
				holder.imageView
						.setBackgroundResource(R.drawable.grid_background_gradient);
			}
			break;

		default:
			break;
		}

		return convertView;
	}

}
