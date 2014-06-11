package com.qfc.yft.ui.gallery;

import android.content.Intent;
import android.provider.MediaStore;
import android.widget.GridView;

import com.qfc.yft.R;
import com.qfc.yft.data.ParamConst;
import com.qfc.yft.data.NetConst;
import com.qfc.yft.ui.MyTitleActivity;

/**
 * ÉÏ´«ÕÕÆ¬
 * @author taotao
 * @Date 2014-6-6
 */
public class UploadActivity extends MyTitleActivity {

	@Override
	public int getLayoutRid() {
		return R.layout.activity_upload;
	}

	@Override
	public void initView() {
		titleManager.setTitleName(getString(R.string.titlename_work));
		int a = getIntent().getIntExtra(NetConst.EXTRAS_ALBUM_TYPE, 0);
		if(a==ParamConst.AR_UP_PHOTO){
			
			Intent i = new Intent(
					MediaStore.ACTION_IMAGE_CAPTURE);
			startActivityForResult(i, ParamConst.AR_UP_PHOTO);//
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == RESULT_OK){
			switch (requestCode) {
			case ParamConst.AR_UP_PHOTO:
				//
				break;
			case ParamConst.AR_UP_LOCAL:
				
				break;
			default:
				break;
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
}
