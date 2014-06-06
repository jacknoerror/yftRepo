package com.qfc.yft.ui.gallery;


import java.util.ArrayList;
import java.util.List;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.FrameLayout;

import com.qfc.yft.R;
import com.qfc.yft.ui.MyTitleActivity;
import com.qfc.yft.util.JackUtils;
import com.qfc.yft.vo.AlbumInShop;

public class AlbumListSimpleActivity extends MyTitleActivity {

	private static final int REQ_CODE_CAMERA = 0x100;
	private static final int REQ_CODE_PICTURE = 0x200;

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	@Override
	public int getLayoutRid() {
		return R.layout.activity_common_frame;
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		titleManager.setTitleName(getString(R.string.titlename_album_lc));
		
		FrameLayout fm = (FrameLayout) this.findViewById(R.id.frame_common);
		
		List<AlbumInShop> list = getAlbums();//TODO put this in thread
		
		int a = list.size();
		
//		imagelo
	}
	public ArrayList<AlbumInShop> getAlbums()
    {
        ArrayList<AlbumInShop> albums = new ArrayList<AlbumInShop>();
        ContentResolver contentResolver = getContentResolver();
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
        ContentResolver contentResolver = getContentResolver();
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
