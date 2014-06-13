package com.qfc.yft.ui.gallery;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.qfc.yft.R;
import com.qfc.yft.data.Const;
import com.qfc.yft.data.NetConst;
import com.qfc.yft.ui.JackInitViewImpl;
import com.qfc.yft.ui.TitleManager;

public class AlbumFragmentActivity extends FragmentActivity implements
		JackInitViewImpl {

	protected final String TAG = getClass().getSimpleName();

//	protected TitleManager titleManager;

	private FragmentManager mSupportFragmentManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getLayoutRid() > 0)
			setContentView(getLayoutRid());
//		titleManager = new TitleManager(this);
		initView();

	}

	@Override
	public int getLayoutRid() {
		return R.layout.activity_common_frame;
	}

	@Override
	public void initView() {
		mSupportFragmentManager = getSupportFragmentManager();
		JackAbsCompoundFragment fragment ;
		Bundle bundle = getIntent().getExtras();
		int a = null==bundle?0:bundle.getInt(NetConst.EXTRAS_ALBUMFIRSTTYPE);
		switch (a) {
		case Const.BS_GO_PHOTO:
		case Const.BS_GO_LOCAL:
//			fragment = new GFSecondLocal(); //实际先去上传界面
			fragment = new GFUpload();
			bundle.putInt(NetConst.EXTRAS_UPLOADACTION, a);
			fragment.setArguments(bundle);
			break;
		default:
			fragment= new GFFirst();
			break;
		}
		mSupportFragmentManager.beginTransaction().add(R.id.frame_common,fragment,fragment.getClass().getSimpleName() ).commit();//
		
		
	}


	protected void onActivityResult(int requestCode, int resultCode, android.content.Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		for(Fragment f: mSupportFragmentManager.getFragments()){
			if(null!=f)f.onActivityResult(requestCode, resultCode, data);
		}
	};
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
}
