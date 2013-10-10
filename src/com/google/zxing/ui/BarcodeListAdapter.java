package com.google.zxing.ui;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mpos.R;

/**
 * @author - Vishal Joshi
 */
public class BarcodeListAdapter extends BaseAdapter {
	private DatabaseManager databaseManager;
	// private String[] barcodes;
	private List<ProductsBean> products;

	private Context context;
	private String content;
	// private int row;

	public BarcodeListAdapter(Context context, String content) {
		this.context = context;
		this.content = content;
		databaseManager = DatabaseManager.getDBInstance(context);
		products = databaseManager.getAllProductInfo();
		/*
		 * barcodes = new String[10]; int i = 0; for (ProductsBean product :
		 * products) { barcodes[i] = product.getContent(); i++; }
		 */

	}

	@Override
	public int getCount() {

		// return barcodes != null ? barcodes.length : 0;
		return products != null && !products.isEmpty() ? products.size() : 0;
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

		System.out.println("getView is called");
		if (convertView == null) {
			LayoutInflater layoutInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = layoutInflater.inflate(
					R.layout.barcodes, null);
		}

		products = databaseManager.getAllProductInfo();

		TextView barcodeView = (TextView) convertView
				.findViewById(R.id.barocdes);
		barcodeView.setText(products.get(position).getContent());

		if (products.get(position).isMatched()) {
			System.out.println("Barcode's isMatched is true");
			barcodeView.setTextColor(Color.GREEN);

		} else if (products.get(position).getContent()
				.equalsIgnoreCase(content)) {
			System.out
					.println("Barcode's content matches with the current barcode");
			databaseManager.setProductAsMatched(content);
			barcodeView.setTextColor(Color.GREEN);

		}

		else {
			System.out.println("New barcode found!");
			barcodeView.setTextColor(Color.WHITE);
			ProductsBean product = new ProductsBean();
			product.setContent(content);
			databaseManager.insertProductsInfo(product);
			notifyDataSetChanged();

		}
		return convertView;
	}
}
