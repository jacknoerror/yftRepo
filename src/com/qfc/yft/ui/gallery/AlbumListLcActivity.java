package com.qfc.yft.ui.gallery;

import java.io.FileNotFoundException;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;

import com.qfc.yft.R;
import com.qfc.yft.ui.MyTitleActivity;

public class AlbumListLcActivity extends MyTitleActivity {

	@Override
	public int getLayoutRid() {
		return R.layout.activity_common_frame;
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		titleManager.setTitleName(getString(R.string.titlename_album_lc));
		
		Intent intent = new Intent();  
        /* 开启Pictures画面Type设定为image */  
        intent.setType("image/*");  
        /* 使用Intent.ACTION_GET_CONTENT这个Action */  
        intent.setAction(Intent.ACTION_GET_CONTENT);   
        /* 取得相片后返回本画面 */  
        startActivityForResult(intent, 1);  
	}

	 @Override  
	    protected void onActivityResult(int requestCode, int resultCode, Intent data) {  
	        if (resultCode == RESULT_OK) {  
	            Uri uri = data.getData();  
	            Log.e("uri", uri.toString());  
	            ContentResolver cr = this.getContentResolver();  
	            try {  
	                Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));  
//	                ImageView imageView = (ImageView) findViewById(R.id.iv01);  
//	                /* 将Bitmap设定到ImageView */  
//	                imageView.setImageBitmap(bitmap);  
	            } catch (FileNotFoundException e) {  
	                Log.e("Exception", e.getMessage(),e);  
	            }  
	        }  
	        super.onActivityResult(requestCode, resultCode, data);  
	    }  
	
}
