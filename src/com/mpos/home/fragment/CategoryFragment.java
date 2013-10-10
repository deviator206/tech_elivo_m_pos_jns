package com.mpos.home.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mpos.R;

public class CategoryFragment extends Fragment {

	public CategoryFragment(){
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View categoryView = inflater.inflate(R.layout.categoryfragment, container, false);
		return categoryView;
	}
}
