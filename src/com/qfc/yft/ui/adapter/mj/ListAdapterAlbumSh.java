package com.qfc.yft.ui.adapter.mj;

import java.util.List;

import android.widget.ImageView;
import android.widget.TextView;

import com.qfc.yft.R;
import com.qfc.yft.ui.custom.list.ListItemImpl;
import com.qfc.yft.ui.custom.list.MspAdapter;
import com.qfc.yft.util.JackImageLoader;
import com.qfc.yft.vo.AlbumInShop;

public class ListAdapterAlbumSh extends MspAdapter {


	public ListAdapterAlbumSh() {
		super();
	}

	class AlbumShViewHolder extends ViewHolderImpl{
		ImageView icon;
		TextView tv_name,tv_desc;

		@Override
		public void init() {
			icon = (ImageView)getHolderView().findViewById(R.id.img_item_album_sh);
			tv_name = (TextView)getHolderView().findViewById(R.id.tv_item_album_sh_name);
			tv_desc = (TextView)getHolderView().findViewById(R.id.tv_item_album_sh_desc);
			
		}

		@Override
		public void setup(int position) {
			AlbumInShop itm = (AlbumInShop)getItem(position);
			tv_name.setText(itm.getAlbumName());
			tv_desc.setText(itm.getAlbumDesc());
			JackImageLoader.justSetMeImage(itm.getAlbumBgImgUrl(), icon);
		}

		@Override
		public int getLayoutId() {
			return R.layout.item_album_sh;
		}
		
	}
	
	@Override
	public ViewHolderImpl getHolderInstance() {
		return new AlbumShViewHolder();
	}

}
