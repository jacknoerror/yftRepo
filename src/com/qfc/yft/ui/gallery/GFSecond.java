package com.qfc.yft.ui.gallery;

import java.util.ArrayList;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ListView;

import com.qfc.yft.ui.custom.list.MyJackListView;
import com.qfc.yft.ui.custom.list.ListItemImpl.Type;
import com.qfc.yft.util.JackUtils;
import com.qfc.yft.vo.AlbumInShop;

public class GFSecond extends JackAbsCompoundFragment {

	ListView mListView;
	
	@Override
	public int getLayoutRid() {
		return 0;
	}

	@Override
	public void initView() {
		mView = mListView = new ListView(getActivity());
//		mListView.setAdapter(adapter);

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
            //��ȡ·�����ļ���Ŀ¼
            String file_dir = JackUtils.getDir(path);

            //�жϸ�Ŀ¼�Ƿ��Ѿ�������albums�У�������ڣ�����ӵ�albums�У�����������ӡ�
            boolean in_albums = false;//Ĭ�ϲ�������albums��
            for (AlbumInShop temp_album : albums)
            {
                if(temp_album.getAlbumName().equals(file_dir))
                {
                    //������albums��
                    in_albums = true;
                    break;
                }
            }

            if(!in_albums)
            {
                AlbumInShop album = new AlbumInShop();
                album.setAlbumName( JackUtils.getDir(path));
                album.setAlbumCapacity( getPicNum(album.getAlbumName()) );
//                album.mCoverUrl = path;
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
            //��ȡ·�����ļ���Ŀ¼
            String file_dir = JackUtils.getDir(path);

            if(album_file_dir.equals(file_dir))
                photo_num++;
            cursor.moveToNext();
        }
        cursor.close();
        return photo_num;
    }
}
