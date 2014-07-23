package com.qfc.yft.ui.tabs.main.cat;

import android.support.v4.app.FragmentManager;

import com.qfc.yft.ui.tabs.JackAbsFragment;

public abstract class CatAbsContentFragment extends JackAbsFragment {
	FragmentManager fraMana;

	@Override
	public void initView() {
		fraMana = getFragmentManager();
		

	}

}
