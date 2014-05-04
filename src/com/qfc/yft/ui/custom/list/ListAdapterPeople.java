package com.qfc.yft.ui.custom.list;

import java.util.List;

import com.qfc.yft.R;
import com.qfc.yft.entity.listitem.LIIPeople;
import com.qfc.yft.ui.YftActivityGate;
import com.qfc.yft.utils.JackImageLoader;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public class ListAdapterPeople extends ListAbsAdapter {
	boolean invisible;
	
	public class ViewHolder extends ViewHolderImpl{
		ImageView icimg;
		TextView name;
		TextView desc;
		TextView comp;
		View right;
		
		@Override
		public int getLayoutId() {
			return R.layout.item_list_people;
		}

		@Override
		public void init() {
			  icimg = (ImageView)getHolderView().findViewById(R.id.img_item_people);
			  name = (TextView)getHolderView().findViewById(R.id.tv_item_name);
			  desc = (TextView)getHolderView().findViewById(R.id.tv_item_desc);
			  comp = (TextView)getHolderView().findViewById(R.id.tv_item_comp);
			  right= getHolderView().findViewById(R.id.view_people_right);
		}

		@Override
		public void setup(int position) {
			final LIIPeople ssi = getItem(position);
//            if(!ssi.headIcon.isEmpty())JackImageLoader.justSetMeImage(ssi.headIcon, icimg);
			icimg.setImageResource(ssi.memberSex==1?R.drawable.photo_boy:R.drawable.photo_girl);
            name.setText(ssi.getName());
            desc.setText(ssi.memberPosition);if(ssi.memberPosition.isEmpty()||ssi.memberPosition.equals("null")) desc.setText("暂无");
            comp.setText(ssi.compName);
            
            if(invisible){
            	right.setVisibility(View.GONE);
            }else{
            	
	            right.setOnClickListener(new View.OnClickListener() {//0220 转入纺织聊
					
					@Override
					public void onClick(View v) {
						YftActivityGate.goChat(v.getContext(), ssi.realName, ssi.texTalk, 0, ssi.headIcon);
					}
				});
            }
		}
		
	}

	/*public static class PeopleItemInfo implements ListItemImpl {
		public String imgPath,name_str,desc_str,comp_str;
		public PeopleItemInfo(){
			imgPath="";
			name_str="";
			desc_str="";
			comp_str="";
		}
		
	}*/
	public ListAdapterPeople( ) {
		super( );
	}
	public ListAdapterPeople(List<ListItemImpl> contentList) {
		super(contentList);
	}
	
	@Override
	public ViewHolderImpl getHolderInstance() {
		return new ViewHolder();
	}

	@Override
	public LIIPeople getItem(int position) {
		return (LIIPeople)super.getItem(position);
	}

	public void setRightInvisible(boolean invisible){
		this.invisible = invisible;
	}
}
