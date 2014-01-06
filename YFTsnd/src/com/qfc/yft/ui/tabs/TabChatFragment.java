package com.qfc.yft.ui.tabs;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TabChatFragment extends ContentAbstractFragment {
	final String TAG = TabChatFragment.class.getName();
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.i(TAG, "oncreate");
		TextView tv = new TextView(getActivity());
		tv.setText(TAG);
		return tv;
	}
	@Override
	public void initView() {
		// TODO Auto-generated method stub
		
	}
}
