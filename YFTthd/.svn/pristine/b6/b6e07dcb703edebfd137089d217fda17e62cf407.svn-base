package com.qfc.yft.ui.tab;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.qfc.yft.ui.TitleManager;

public abstract class ContentAbstractFragment extends JackAbsFragment {

	/**
	 * launch initTitleManager() before using it
	 */
	protected TitleManager titleManager;
	
	/**
	 * 
	 */
	protected void initTitleManager() {
		titleManager = new TitleManager(mView);
	}
	
	protected void hideSoftKeyboard( ) {
		try{
			
			InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),InputMethodManager.SHOW_FORCED);
		}catch(Exception e){
		}
	}
	
}
