/*
 * Copyright (C) 2013 jaggarnaut-studios Pvt. Ltd. All Rights Reserved.
 * jaggarnaut-studios pvt. ltd. PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.mpos.home.fragment;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.mpos.R;
import com.mpos.framework.Util.UserSingleton;
import com.mpos.framework.common.Constants;
import com.mpos.framework.common.Logger;
import com.mpos.framework.inf.ListItemSelectedListener;
import com.mpos.home.adapter.GridAdapter;
import com.mpos.master.model.BaseModel;
import com.mpos.master.model.PRDCT_ANALYS_MST_Model;
import com.mpos.master.model.PRDCT_BINLOC_MST_Model;
import com.mpos.master.model.PRDCT_DPT_MST_Model;
import com.mpos.master.model.PRDCT_GRP_MST_Model;
import com.mpos.master.model.PRDCT_MST_Model;

/**
 * Description-
 * 
 * @author jaggarnaut-studios
 */

public class GridFragment extends Fragment {

	public static final String TAG = Constants.APP_TAG
			+ GridFragment.class.getSimpleName();

	public static final String LIST = "LIST";
	public static final String TYPE = "TYPE";
	
	public static final int HOME_MODEL = 0;
	public static final int GRP_MODEL = 1;
	public static final int DPT_MODEL = 2;
	public static final int ANALYS_MODEL = 3;
	public static final int BINLOC_MODEL = 4;
	public static final int PRDCT_MODEL = 5;

	public static final String GROUP = "GRP";
	public static final String DEPARTMENT = "DPT";
	public static final String ANALYS = "ANALYS";
	public static final String BIN_LOCATION = "BINLOC";
	public static final String PRODUCTS = "PRDCT";

	// UI Components
	private GridView gridView;
	private ArrayList<BaseModel> dataList;
	private int type = -1;

	private OnItemClickListener mItemClickListner = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View view, int position,
				long arg3) {
			if (UserSingleton.getInstance(getActivity()).isPaymentDone) {
				return;
			}
			BaseModel model = dataList.get(position);

			switch (type) {
			case GridFragment.GRP_MODEL:
				PRDCT_GRP_MST_Model grpModel = (PRDCT_GRP_MST_Model) model;
				Logger.d(TAG, grpModel.getGrp_code());
				((ListItemSelectedListener) getActivity()).gridItemSelected(
						GridFragment.GRP_MODEL, grpModel);
				break;

			case GridFragment.DPT_MODEL:
				PRDCT_DPT_MST_Model dptModel = (PRDCT_DPT_MST_Model) model;
				Logger.d(TAG, dptModel.getDpt_code());
				((ListItemSelectedListener) getActivity()).gridItemSelected(
						GridFragment.DPT_MODEL, dptModel);
				break;

			case GridFragment.ANALYS_MODEL:
				PRDCT_ANALYS_MST_Model analysModel = (PRDCT_ANALYS_MST_Model) model;
				Logger.d(TAG, analysModel.getAnalys_code());
				((ListItemSelectedListener) getActivity()).gridItemSelected(
						GridFragment.ANALYS_MODEL, analysModel);
				break;

			case GridFragment.BINLOC_MODEL:
				PRDCT_BINLOC_MST_Model binLocModel = (PRDCT_BINLOC_MST_Model) model;
				Logger.d(TAG, binLocModel.getBinloc_code());
				((ListItemSelectedListener) getActivity()).gridItemSelected(
						GridFragment.BINLOC_MODEL, binLocModel);
				break;

			case GridFragment.PRDCT_MODEL:
				PRDCT_MST_Model prdctModel = (PRDCT_MST_Model) model;
				Logger.d(TAG, prdctModel.getPRDCT_CODE());
				((ListItemSelectedListener) getActivity()).gridItemSelected(
						GridFragment.PRDCT_MODEL, prdctModel);
				break;

			default:
				break;
			}
		}
	};

	public GridFragment() {

	}

	@SuppressWarnings("unchecked")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);

		View view = inflater.inflate(R.layout.grid_fragment, container, false);

		dataList = (ArrayList<BaseModel>) getArguments().get(GridFragment.LIST);
		type = getArguments().getInt(GridFragment.TYPE);

		componentInitialisation(view);
		return view;
	}

	private void componentInitialisation(View view) {
		gridView = (GridView) view.findViewById(R.id.gridView);
		gridView.setAdapter(new GridAdapter(dataList, type, getActivity()));
		gridView.setOnItemClickListener(mItemClickListner);
	}
	
	public void scrollToTop(){
		if(gridView != null){
			Logger.d(TAG, "Grid NOt Null");
			gridView.scrollTo(0, 0);
			gridView.smoothScrollToPosition(0);
		}
	}
	
}
