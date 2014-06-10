package com.qfc.yft.ui.gallery;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.qfc.yft.R;
import com.qfc.yft.ui.JackInitViewImpl;
import com.qfc.yft.ui.TitleManager;

public class AlbumFragmentActivity extends FragmentActivity implements
		JackInitViewImpl {

	public static final String EXTRAS_ALBUMFIRSTTYPE = "albumfirsttype";

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
		int a = null==bundle?0:bundle.getInt(EXTRAS_ALBUMFIRSTTYPE);
		switch (a) {
		case 1:
			fragment = new GFSecondLocal();
			break;

		default:
			fragment= new GFFirst();
			break;
		}
		mSupportFragmentManager.beginTransaction().add(R.id.frame_common,fragment,fragment.getClass().getSimpleName() ).commit();//
		
//		mSupportFragmentManager.beginTransaction().hide(arg0)
		
	}


	protected void onActivityResult(int requestCode, int resultCode, android.content.Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		for(Fragment f: mSupportFragmentManager.getFragments()){
			f.onActivityResult(requestCode, resultCode, data);
		}
	};
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
}
