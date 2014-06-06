package com.qfc.yft.ui.gallery;

import android.content.Intent;
import android.widget.GridView;

import com.qfc.yft.R;
import com.qfc.yft.data.NetConst;
import com.qfc.yft.ui.MyTitleActivity;

/**
 * ÉÏ´«ÕÕÆ¬
 * @author taotao
 * @Date 2014-6-6
 */
public class UploadActivity extends MyTitleActivity {

	private static final int UP_LOCAL = 0x011;
	private static final int UP_PHOTO = 0x010;

	@Override
	public int getLayoutRid() {
		return R.layout.activity_upload;
	}

	@Override
	public void initView() {
		titleManager.setTitleName(getString(R.string.titlename_work));
		int a = getIntent().getIntExtra(NetConst.EXTRAS_ALBUM_TYPE, 0);

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == RESULT_OK){
			switch (requestCode) {
			case UP_PHOTO:
				
				break;
			case UP_LOCAL:
				
				break;
			default:
				break;
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
}
