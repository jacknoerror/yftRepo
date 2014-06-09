package com.qfc.yft.ui.gallery;

import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.qfc.yft.ui.TitleManager;
import com.qfc.yft.ui.tab.JackAbsFragment;


public abstract class JackAbsCompoundFragment extends JackAbsFragment {

	protected FragmentManager mCompoundFragmentManager;
	protected TitleManager mCompoundTitleManager;

	@Override
	public void initView() {
		mCompoundFragmentManager = getFragmentManager();
		mCompoundTitleManager = new TitleManager(getActivity());
		Log.i(TAG, "init");
	}
	
	
}
