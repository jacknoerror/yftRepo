package com.qfc.yft.ui.gallery;

import java.util.ArrayList;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.qfc.yft.R;
import com.qfc.yft.ui.adapter.mj.ListAdapterAlbumLc;
import com.qfc.yft.ui.custom.list.ListItemImpl;
import com.qfc.yft.util.JackUtils;
import com.qfc.yft.vo.AlbumInShop;

public class GFSecondLocal extends JackAbsCompoundFragment {

	ListView mListView;
	private ListAdapterAlbumLc mAdapter;
	private ArrayList<AlbumInShop> albums;
	
	@Override
	public int getLayoutRid() {
		return 0;
	}

	@Override
	public void initView() {
		super.initView();
		mCompoundTitleManager.setTitleName(getString(R.string.titlename_album_lc));
		mCompoundTitleManager.setRightText("取消", new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				getActivity().finish();//
			}
		});
		
		mView = mListView = new ListView(getActivity());
		if(null==albums)albums = getAlbums();
		mAdapter = new ListAdapterAlbumLc(albums);
		mListView.setAdapter(mAdapter);
		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				GFGrids gfGrids = new GFGrids();
				Bundle arguments = new Bundle();
				AlbumInShop item = (AlbumInShop) mAdapter.getItem(position);
				arguments.putString(GFGrids.EXTRAS_GRIDALBUMNAME, item.getAlbumName()	);
				gfGrids.setArguments(arguments);
				mCompoundFragmentManager.beginTransaction().replace(R.id.frame_common, gfGrids).addToBackStack(getClass().getSimpleName()).commit();;
			}
		});
	}

	
	public ArrayList<AlbumInShop> getAlbums()
    {
        ArrayList<AlbumInShop> albums = new ArrayList<AlbumInShop>();
        ContentResolver contentResolver = getActivity().getContentResolver();
        String[] projection = new String[] { MediaStore.Images.Media.DATA };
        Cursor cursor = contentResolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, null,
                null, MediaStore.Images.Media.DEFAULT_SORT_ORDER);
        cursor.moveToFirst();
        int fileNum = cursor.getCount();

        for (int counter = 0; counter < fileNum; counter++) {
            Log.w("tag", "---file is:" + cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA)));
            String path = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
            //获取路径中文件的目录
            String file_dir = JackUtils.getDir(path);

            //判断该目录是否已经存在于albums中，如果存在，则不添加到albums中；不存在则添加。
            boolean in_albums = false;//默认不存在于albums中
            for (AlbumInShop temp_album : albums)
            {
                if(temp_album.getAlbumName().equals(file_dir))
                {
                    //存在于albums中
                    in_albums = true;
                    break;
                }
            }

            if(!in_albums)
            {
                AlbumInShop album = new AlbumInShop();
                album.setAlbumName( JackUtils.getDir(path));
                album.setAlbumCapacity( getPicNum(album.getAlbumName()) );
                album.setAlbumBgImgUrl(path);
                albums.add(album);
            }
            cursor.moveToNext();
        }
        cursor.close();

        return albums;
    }
	
	public int getPicNum(String album_file_dir)
    {
        ContentResolver contentResolver = getActivity().getContentResolver();
        String[] projection = new String[] { MediaStore.Images.Media.DATA };
        Cursor cursor = contentResolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, null,
                null, MediaStore.Images.Media.DEFAULT_SORT_ORDER);
        cursor.moveToFirst();
        int fileNum = cursor.getCount();

        int photo_num = 0;
        for (int counter = 0; counter < fileNum; counter++) {
            Log.w("tag", "---file is:" + cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA)));
            String path = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
            //获取路径中文件的目录
            String file_dir = JackUtils.getDir(path);

            if(album_file_dir.equals(file_dir))
                photo_num++;
            cursor.moveToNext();
        }
        cursor.close();
        return photo_num;
    }
}
