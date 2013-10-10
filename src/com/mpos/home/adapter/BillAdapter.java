package com.mpos.home.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.mpos.R;
import com.mpos.framework.common.Constants;
import com.mpos.framework.common.Logger;
import com.mpos.home.model.BillTransactionModel;
import com.mpos.master.model.INSTRCTN_MST_Model;
import com.mpos.transactions.model.Bill_INSTRCTN_TXN_Model;

public class BillAdapter extends BaseAdapter {

	public static final String TAG = Constants.APP_TAG
			+ BillAdapter.class.getSimpleName();

	private ArrayList<BillTransactionModel> mTransactionArray = null;
	private Context mContext;
	private ArrayList<Bill_INSTRCTN_TXN_Model> mBillInstructionTranModels;
	private ArrayList<INSTRCTN_MST_Model> mINSTRCTNDataList;

	public BillAdapter(ArrayList<BillTransactionModel> transactionArray,
			ArrayList<Bill_INSTRCTN_TXN_Model> mBillInstructionTranModels,
			ArrayList<INSTRCTN_MST_Model> mINSTRCTNDataList, Context context) {
		this.mContext = context;
		this.mTransactionArray = transactionArray;
		this.mBillInstructionTranModels = mBillInstructionTranModels;
		this.mINSTRCTNDataList = mINSTRCTNDataList;
	}

	@Override
	public int getCount() {
		if (this.mTransactionArray != null && this.mTransactionArray.size() > 0)
			return mTransactionArray.size();
		else
			return 0;
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
		Logger.d(TAG, "getView");
		ViewHolder holder = null;
		Logger.v("ConvertView", String.valueOf(position));

		if (convertView == null) {
			LayoutInflater vi = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = vi.inflate(R.layout.bill_list_row_item, null);

			holder = new ViewHolder();
			holder.itemCheck = (CheckBox) convertView.findViewById(R.id.check);
			holder.quantityText = (TextView) convertView
					.findViewById(R.id.item_qtyText);
			holder.descriptionText = (TextView) convertView
					.findViewById(R.id.item_descText);
			holder.codeText = (TextView) convertView
					.findViewById(R.id.item_codeText);
			holder.priceText = (TextView) convertView
					.findViewById(R.id.item_priceText);
			convertView.setTag(holder);

			holder.itemCheck.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					CheckBox cb = (CheckBox) v;
					BillTransactionModel model = (BillTransactionModel) cb
							.getTag();
					model.setVoidChecked(cb.isChecked());
				}
			});
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.itemCheck.setSelected(mTransactionArray.get(position)
				.isVoidChecked());
		holder.itemCheck.setTag(mTransactionArray.get(position));
		holder.codeText
				.setText(mTransactionArray.get(position).getPRDCT_CODE());
		holder.quantityText.setText(String.valueOf(mTransactionArray.get(
				position).getPRDCT_QNTY()));

		String description = mTransactionArray.get(position)
				.getPRDCT_LNG_DSCRPTN();

		if (mBillInstructionTranModels != null && mBillInstructionTranModels.size()>0) {

			Bill_INSTRCTN_TXN_Model instruction = new Bill_INSTRCTN_TXN_Model();
			instruction.setPrdctCode(mTransactionArray.get(position)
					.getPRDCT_CODE());
			instruction.setRow_id(mTransactionArray.get(position).getRow_id());
			instruction.setPrdctVoid(mTransactionArray.get(position)
					.getPRDCT_VOID());
			instruction.setBranchCode(mTransactionArray.get(position)
					.getBRNCH_CODE());
			instruction.setPck(mTransactionArray.get(position).getPACK());
			if (mBillInstructionTranModels.contains(instruction)) {

				int index = mBillInstructionTranModels.indexOf(instruction);
				Logger.v(TAG, "index" + index);
				if (index != -1) {
					description += " - "
							+ mBillInstructionTranModels.get(index).getPck();//Measure data
					
					if(mINSTRCTNDataList!=null && mINSTRCTNDataList.size()>0){
						INSTRCTN_MST_Model instructionCode = new INSTRCTN_MST_Model();
						instructionCode.setInstrctn_code(mBillInstructionTranModels.get(index).getInstrcCode());
						if(mINSTRCTNDataList.contains(instructionCode)){
							int insIndex = mINSTRCTNDataList.indexOf(instructionCode);
							if(insIndex!=-1){
								description += " - "
										+ mINSTRCTNDataList.get(insIndex).getInstrctn_desc();//Modifier data
							}
						}
					}

					if (mBillInstructionTranModels.get(index).getExtraInstrcn() != null
							&& !mBillInstructionTranModels.get(index)
									.getExtraInstrcn().equalsIgnoreCase("")) {
						description += " ("
								+ mBillInstructionTranModels.get(index)
										.getExtraInstrcn() + ")";// Extra instruction data
					}

				}
			}
		}
		
		

		holder.descriptionText.setText(description.toString());

		holder.priceText.setText(String.valueOf(mTransactionArray.get(position)
				.getPRDCT_AMNT()));

		return convertView;
	}

	private class ViewHolder {
		private CheckBox itemCheck;
		private TextView quantityText;
		private TextView descriptionText;
		private TextView codeText;
		private TextView priceText;
	}

}
