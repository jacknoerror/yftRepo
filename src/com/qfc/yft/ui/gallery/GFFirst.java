package com.qfc.yft.ui.gallery;

import android.view.View;
import android.widget.AdapterView;

import com.qfc.yft.R;
import com.qfc.yft.ui.custom.list.ListItemImpl.Type;
import com.qfc.yft.ui.custom.list.MyJackListView;

public class GFFirst extends CompoundRadiosFragment {


	

	@Override
	public void initView() {
		super.initView();
		mCompoundTitleManager.setTitleName("…Ã∆Ãœ‡≤·2");
		
		MyJackListView mListView = new MyJackListView(getActivity(), Type.ALBUM);
		mFrame.addView(mListView);
		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				mCompoundFragmentManager.beginTransaction().replace(R.id.frame_common, new GFGrids()).addToBackStack(GFFirst.class.getSimpleName()).commit();
				//?addtobackstack?
			}
			
		});
		
		mListView.setup();
		
	}

	

	

}
