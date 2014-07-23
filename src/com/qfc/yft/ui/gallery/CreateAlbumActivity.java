package com.qfc.yft.ui.gallery;

import org.json.JSONException;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.qfc.yft.R;
import com.qfc.yft.data.Const;
import com.qfc.yft.data.MyData;
import com.qfc.yft.net.action.ActionBuilder;
import com.qfc.yft.net.action.ActionReceiverImpl;
import com.qfc.yft.net.action.ActionStrategies;
import com.qfc.yft.net.action.album.AddAlbumReq;
import com.qfc.yft.ui.MyTitleActivity;
import com.qfc.yft.util.JackButtonColorFilter;
import com.qfc.yft.util.JackUtils;

public class CreateAlbumActivity extends MyTitleActivity implements ActionReceiverImpl{
	EditText etN,etD;

	@Override
	public int getLayoutRid() {
		return R.layout.activity_createalbum;
	}

	@Override
	public void initView() {
		titleManager.setTitleName(getString(R.string.titlename_album_lc));
		titleManager.initTitleBack();

		etN = (EditText)this.findViewById(R.id.et_albumcreate_name);
		etD = (EditText)this.findViewById(R.id.et_albumcreate_desc);
		
		Button btnSave = (Button)this.findViewById(R.id.btn_albumcreate);
		JackButtonColorFilter.setButtonFocusChanged(btnSave);
		btnSave.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String name = etN.getText().toString();
				if(name.length()<=2) {
					JackUtils.showToast(CreateAlbumActivity.this, "Ãû×ÖÌ«¶ÌÁË");//ºº×Ó£¿
					return;
				}
				ActionBuilder.getInstance().request(new AddAlbumReq(MyData.data().getMe().getShopId(), name, etD.getText().toString()), CreateAlbumActivity.this);
				
				
			}
		});
	}

	@Override
	public boolean response(String result) throws JSONException {
		if(ActionStrategies.getResultBoolean(result)){
			setResult(Activity.RESULT_OK);
			finish();
		}
		return false;
	}

	@Override
	public Context getReceiverContext() {
		return this;
	}

}
