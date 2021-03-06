package com.qfc.yft.ui.tabs.custom;

import java.io.File;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.AsyncTask.Status;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.qfc.yft.R;
import com.qfc.yft.YftData;
import com.qfc.yft.YftValues;
import com.qfc.yft.net.OfflineDownloadBuilder;
import com.qfc.yft.ui.YftActivityGate;
import com.qfc.yft.ui.account.StartPagerActivity;
import com.qfc.yft.ui.shop.ShopActivity;
import com.qfc.yft.ui.tabs.person.FavActivity;
import com.qfc.yft.ui.tabs.person.PeopleActivity;
import com.qfc.yft.ui.tabs.person.RecommandActivity;
import com.qfc.yft.utils.JackUtils;

public class BarGroup extends LinearLayout implements View.OnClickListener{
	
	LinearLayout bgContainer;
	int count;
	
	private LayoutInflater bgInflater;

	public BarGroup(Context context) {
		super(context);//use this constructor or be ordinary
		getInflater().inflate(R.layout.layout_custom_bargroup, this);
		bgContainer = (LinearLayout)findViewById(R.id.layout_bargroup_container);
	}
	
	
	public void addBar(BarData attrs){
		if(null==bgContainer)return;
		LayoutInflater inflater = getInflater();
		//init
		View barView = inflater.inflate(R.layout.item_person, null);
		
		ImageView icimg = (ImageView)barView.findViewById(R.id.img_item_person_left);
		TextView descText= (TextView)barView.findViewById(R.id.tv_item_person);
		ImageView btnimg = (ImageView)barView.findViewById(R.id.img_item_person_right);
		
		if(null!=attrs.icId)icimg.setImageResource(attrs.icId);
		if(null!=attrs.desc)descText.setText(attrs.desc);
		if(null!=attrs.btnId){
			btnimg.setImageResource(attrs.btnId);
		}
		barView.setId(attrs.icId);
		
		//add
		if(count>0){//分割线
//			View divider = inflater.inflate(R.layout.view_divider_person, null);//unhelpfully
			ImageView dividerImg = new ImageView(getContext());
			dividerImg.setImageResource(R.drawable.divider_h_grey);
			dividerImg.setPadding(10, 0, 0, 0);
			bgContainer.addView(dividerImg);
		}
		bgContainer.addView(barView);
		barView.setOnClickListener(this);
		count++; 
	}
	

	private LayoutInflater getInflater( ){
		if(bgInflater==null){
			bgInflater = ( LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}
		return bgInflater;
	}

	/*
	 * final String[] DESC = new String[]{"我的商铺","我的收藏","我的人脉","同步我的商铺","离线模式","清空本地数据","移动商铺介绍","移动商铺企业推荐"};
	final int[] ICS = new int[]{R.drawable.person_myshop,R.drawable.person_mycollection,
			R.drawable.person_myfriends,R.drawable.person_refreshmyshop,R.drawable.person_offlinemodel,
			R.drawable.person_trash,R.drawable.person_moveshopintro,R.drawable.person_moveshoprecommanded
	};
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Log.i("BarGroup", "onclick:"+v.getId());
		switch (v.getId()) {
		case R.drawable.person_myshop:
//			go(ShopActivity.class);
			YftActivityGate.goShop(getContext(), YftData.data().getMe().getShopId(), "我的商铺", YftData.data().getMe().getMemberType());
			break;
		case R.drawable.person_mycollection:
			go(FavActivity.class);
			break;
		case R.drawable.person_myfriends:
			go(PeopleActivity.class);
			break;
		case R.drawable.person_offlinemodel:
			
			break;
		case R.drawable.person_refreshmyshop:
			OfflineDownloadBuilder.onClick();
			break;
		case R.drawable.person_trash:
			if(OfflineDownloadBuilder.shouldStart()){
			  ((AlertDialog.Builder)getTag()).show();
			}else{
				JackUtils.showToast(getContext(), "请先结束离线任务");
			}
			break;
		case R.drawable.person_moveshoprecommanded:
			go(RecommandActivity.class);
			break;
		case R.drawable.person_moveshopintro:
			go(StartPagerActivity.class);
			break;
		default:
			break;
		}
	}

	private <T> void go(Class<T> clazz){
		Intent intent = new Intent();
		intent.setClass(this.getContext(), clazz);
		this.getContext().startActivity(intent);
	}
	
	
}
