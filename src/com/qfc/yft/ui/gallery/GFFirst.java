package com.qfc.yft.ui.gallery;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.qfc.yft.R;
import com.qfc.yft.data.Const;
import com.qfc.yft.data.NetConst;
import com.qfc.yft.ui.MyPortal;
import com.qfc.yft.ui.custom.list.ListItemImpl.Type;
import com.qfc.yft.ui.custom.list.MyJackListView;
import com.qfc.yft.vo.Album;

public class GFFirst extends CompoundRadiosFragment {


	private MyJackListView mListView;

	@Override
	protected void handleTitle() {
		super.handleTitle();
		mCompoundTitleManager.setTitleName(getString(R.string.titlename_album_sh));
		final View.OnClickListener listener = new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				MyPortal.goCreateAlbum(getActivity());
			}
		};
		mCompoundTitleManager.setRightText("+", listener);
		mCompoundTitleManager.initTitleBack();
	}

	@Override
	public void initView() {
		super.initView();
		
		
		mListView = new MyJackListView(getActivity(), Type.ALBUM);
		mFrame.addView(mListView);
		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				GFGrids gfGrids = new GFGrids();
				Bundle bundle = new Bundle();
				Album album = (Album) parent.getItemAtPosition(position);
				bundle.putInt(NetConst.EXTRAS_GRIDALBUMID, (int)album.getAlbumId());
				bundle.putString(NetConst.EXTRAS_GRIDALBUMNAME, album.getAlbumName());
				gfGrids.setArguments(bundle);
				mCompoundFragmentManager.beginTransaction().hide(GFFirst.this).add(R.id.frame_common, gfGrids).addToBackStack(GFFirst.class.getSimpleName()).commit();
//				mCompoundFragmentManager.beginTransaction().replace(R.id.frame_common, new GFGrids()).addToBackStack(GFFirst.class.getSimpleName()).commit();
				//?addtobackstack?
			}
			
		});
		
		mListView.setup();
		
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == Activity.RESULT_OK&&requestCode == Const.AR_UP_CREATE){
			mListView.setup();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	

}
