package com.qfc.yft.ui.gallery;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.qfc.yft.R;
import com.qfc.yft.ui.TitleManager;
import com.qfc.yft.ui.tab.JackAbsFragment;

import de.greenrobot.event.EventBus;


public abstract class JackAbsCompoundFragment extends JackAbsFragment {

	protected FragmentManager mCompoundFragmentManager;
	protected TitleManager mCompoundTitleManager;
	protected boolean eventBusRegisted;

	@Override
	public void initView() {
		mCompoundFragmentManager = getFragmentManager();
		mCompoundTitleManager = new TitleManager(getActivity());
		handleTitle();
		Log.i(TAG, "init");
	}
	@Override
	public void onStop() {
		unregistEventBus();
		super.onStop();
	}
	/**
	 * 
	 */
	protected void registEventBus() {
		eventBusRegisted = true;
		EventBus.getDefault().register(this);
		Log.i(TAG, "registEventBus");
	}
	/**
	 * 
	 */
	protected void unregistEventBus() {
		if(eventBusRegisted){
			EventBus.getDefault().unregister(this);//why not destroy?
			eventBusRegisted = false;
		}
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
	
	/**
	 * FragmentÌø×ª
	 * @param fm
	 * @param fragmentClass
	 * @param tag
	 * @param args
	 */
	public void turnToFragment(FragmentManager fm, Class<? extends Fragment> fragmentClass, String tag, Bundle args) {
//		mIsCanEixt = false;
		Fragment fragment = fm.findFragmentByTag(tag);
		boolean isFragmentExist = true;
		if (fragment == null) {
			try {
				isFragmentExist = false;
				fragment = fragmentClass.newInstance();
				fragment.setArguments(new Bundle());
			} catch (java.lang.InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		if(fragment.isAdded()){
			return;
		}
		if( args != null && !args.isEmpty() ) {
			fragment.getArguments().putAll(args);
		}
		FragmentTransaction ft = fm.beginTransaction();
		ft.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out,
				android.R.anim.fade_in, android.R.anim.fade_out);
		if( isFragmentExist ) {
			ft.replace(R.id.realtabcontent, fragment);
		} else {
			ft.replace(R.id.realtabcontent, fragment, tag);
		}
		
		ft.addToBackStack(tag);
		ft.commitAllowingStateLoss();
	}
}
