package com.qfc.yft.ui.common;

import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;

import com.qfc.yft.R;
import com.qfc.yft.ui.MyTitleActivity;
import com.qfc.yft.ui.custom.list.ListItemImpl.Type;
import com.qfc.yft.ui.custom.list.MyJackListView;

public class MyPeopleActivity extends MyTitleActivity {

	@Override
	public int getLayoutRid() {
		return R.layout.activity_common_frame;
	}

	@Override
	public void initView() {
		titleManager.initTitleBack();
		titleManager.setTitleName(getString(R.string.titlename_mypeop));
		
		FrameLayout frame = (FrameLayout) this.findViewById(R.id.frame_common);
		MyJackListView jlv = new MyJackListView(this, Type.IP_PEOPLE_MY);
		frame.addView(jlv);
		jlv.setup();
		jlv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				//TODO
				
			}

		});

	}

}
