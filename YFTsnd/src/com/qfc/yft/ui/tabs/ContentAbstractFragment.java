package com.qfc.yft.ui.tabs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class ContentAbstractFragment extends Fragment {
	LayoutInflater mInflator;
	View mView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mInflator= inflater;
		initView( );
		
		return mView;
	}

	public abstract void initView() ;
	
}
