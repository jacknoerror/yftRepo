package com.qfc.yft.ui.gallery;

import java.util.ArrayList;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.qfc.yft.R;
import com.qfc.yft.data.Const;
import com.qfc.yft.data.NetConst;
import com.qfc.yft.ui.MyPortal;
import com.qfc.yft.ui.adapter.mj.ListAdapterAlbumLc;
import com.qfc.yft.ui.custom.list.ListItemImpl;
import com.qfc.yft.ui.custom.list.MyJackListView;
import com.qfc.yft.ui.custom.list.ListItemImpl.Type;
import com.qfc.yft.util.JackUtils;
import com.qfc.yft.vo.Album;

public class GFSecondShop extends JackAbsCompTitleFragment {

	MyJackListView mListView;
	private ListAdapterAlbumLc mAdapter;
	private ArrayList<Album> albums;
	
	@Override
	public int getLayoutRid() {
		return 0;
	}

	@Override
	public void initView() {
		super.initView();
		
		mView = mListView =   new MyJackListView(getActivity(), Type.CHOOSE);
		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Fragment fragment = mCompoundFragmentManager.findFragmentByTag(GFUpload.class.getSimpleName());
				if(null==fragment ) return;
				Bundle bundle=fragment.getArguments();
				Album item = (Album) parent.getItemAtPosition(position);
				bundle.putInt(NetConst.EXTRAS_TOALBUMID, (int) item.getAlbumId());
				bundle.putString(NetConst.EXTRAS_TOALBUMNAME, item.getAlbumName());
				mCompoundFragmentManager.popBackStack();
			}
			
		});
		
		mListView.setup();
	}

	/**
	 * 
	 */
	protected void handleTitle() {
		super.handleTitle();
		mCompoundTitleManager.setTitleName("选择相册");//titlename_album_sh
		mCompoundTitleManager.setRightText("取消", new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mCompoundFragmentManager.popBackStack();//
			}
		});
		mCompoundTitleManager.setLeftText("+", new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				MyPortal.goCreateAlbum(getActivity());
				
			}
		});
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == Activity.RESULT_OK&&requestCode == Const.AR_UP_CREATE){
			mListView.setup();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	
}
