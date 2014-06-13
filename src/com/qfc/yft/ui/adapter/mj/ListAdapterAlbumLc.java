package com.qfc.yft.ui.adapter.mj;

import java.util.List;

import android.widget.ImageView;
import android.widget.TextView;

import com.qfc.yft.R;
import com.qfc.yft.ui.ImageLoaderHelper;
import com.qfc.yft.ui.custom.list.ListItemImpl;
import com.qfc.yft.ui.custom.list.MspAdapter;
import com.qfc.yft.util.JackImageLoader;
import com.qfc.yft.vo.Album;

/**
 * 
 * @author taotao
 * @Date 2014-6-10
 */
public class ListAdapterAlbumLc extends MspAdapter {


	public ListAdapterAlbumLc() {
		super();
	}
	

	public ListAdapterAlbumLc(List contentList) {
		super(contentList);
	}


	class AlbumLcViewHolder extends ViewHolderImpl{
		ImageView icon;
		TextView tv_name,tv_count;

		@Override
		public void init() {
			icon = (ImageView)getHolderView().findViewById(R.id.img_item_lc);
			tv_name = (TextView)getHolderView().findViewById(R.id.tv_item_lc_name);
			tv_count = (TextView)getHolderView().findViewById(R.id.tv_item_lc_cap);
			
		}

		@Override
		public void setup(int position) {
			Album itm = (Album)getItem(position);
			tv_name.setText(itm.getAlbumName());
			tv_count.setText("("+itm.getPictureNum()+")");
//			JackImageLoader.justSetMeImage(itm.getAlbumBgImgUrl(), icon);
			String albumBgImgUrl = itm.getAlbumBgImgUrl();
			if(!albumBgImgUrl.startsWith("http")&&!albumBgImgUrl.startsWith("file://")) albumBgImgUrl = "file://"+albumBgImgUrl;//XXX 
			ImageLoaderHelper.imageLoader.displayImage(albumBgImgUrl, icon);
		}

		@Override
		public int getLayoutId() {
			return R.layout.item_album_lc;
		}
		
	}
	
	@Override
	public ViewHolderImpl getHolderInstance() {
		return new AlbumLcViewHolder();
	}

}
