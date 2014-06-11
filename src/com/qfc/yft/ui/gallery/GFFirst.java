package com.qfc.yft.ui.gallery;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.qfc.yft.R;
import com.qfc.yft.ui.custom.list.ListItemImpl.Type;
import com.qfc.yft.ui.custom.list.MyJackListView;

public class GFFirst extends CompoundRadiosFragment {


	@Override
	protected void handleTitle() {
		super.handleTitle();
		mCompoundTitleManager.setTitleName(getString(R.string.titlename_album_sh));
		final View.OnClickListener listener = new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.e(TAG, "init listener");
			}
		};
		mCompoundTitleManager.setRightText("+", listener);
		mCompoundTitleManager.initTitleBack();
	}

	@Override
	public void initView() {
		super.initView();
		
		
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
