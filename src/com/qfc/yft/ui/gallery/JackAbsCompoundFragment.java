package com.qfc.yft.ui.gallery;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.qfc.yft.R;
import com.qfc.yft.ui.TitleManager;
import com.qfc.yft.ui.tab.JackAbsFragment;


public abstract class JackAbsCompoundFragment extends JackAbsFragment {

	protected FragmentManager mCompoundFragmentManager;
	protected TitleManager mCompoundTitleManager;

	@Override
	public void initView() {
		mCompoundFragmentManager = getFragmentManager();
		mCompoundTitleManager = new TitleManager(getActivity());
		handleTitle();
		Log.i(TAG, "init");
	}
	
	protected int replaceFragment(Fragment fragment, boolean hasTag, String backName){
		return mCompoundFragmentManager.beginTransaction().replace(R.id.frame_common, fragment,hasTag?fragment.getClass().getSimpleName():null)
				.addToBackStack(backName)
				.commit();
	}
	
	@Override
	public void onHiddenChanged(boolean hidden) {
		if(!hidden){
			handleTitle();
		}
		super.onHiddenChanged(hidden);
	}
	
	protected  void handleTitle(){
		mCompoundTitleManager.hideBothSides();
	};
}
