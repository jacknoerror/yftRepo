package com.qfc.yft.ui.gallery;

import android.content.Intent;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.qfc.yft.R;
import com.qfc.yft.ui.MyTitleActivity;
import com.qfc.yft.ui.custom.list.ListItemImpl.Type;
import com.qfc.yft.ui.custom.list.MyJackListView;

public class AlbumListActivity extends MyTitleActivity implements OnCheckedChangeListener {
	
	private static final int REQ_CODE_CAMERA = 0;
	private RadioGroup mRadioGroup;

	@Override
	protected void onResume() {
		super.onResume();
		if(null!= mRadioGroup) mRadioGroup.clearCheck();
	}

	@Override
	public int getLayoutRid() {
		return R.layout.activity_list_sh_album;
	}

	
	@Override
	public void initView() {
		titleManager.setTitleName(getString(R.string.titlename_album_sh));
		titleManager.initTitleBack();
		titleManager.setRightText("+ ", new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		
		mRadioGroup = (RadioGroup)this.findViewById(R.id.radiogroup_al_sh);
		mRadioGroup.setOnCheckedChangeListener(this);
		FrameLayout mFrame = (FrameLayout) this.findViewById(R.id.frame_al_sh);
		
		MyJackListView mListView = new MyJackListView(this, Type.ALBUM);
		mFrame.addView(mListView);
		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		mListView.setup();
		
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch (checkedId) {
		case R.id.radio1:
			//go photo
			Intent i = new Intent(
					MediaStore.ACTION_IMAGE_CAPTURE);
			startActivityForResult(i, REQ_CODE_CAMERA);//
			break;
		case R.id.radio2:
			//go local
			break;

		default:
			break;
		}
		
	}

}
