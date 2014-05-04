package com.qfc.yft.ui.shop;


import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.Html;
import android.text.TextPaint;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidumap.GeoCoderActivity;
import com.qfc.yft.R;
import com.qfc.yft.YftData;
import com.qfc.yft.YftValues;
import com.qfc.yft.YftValues.RequestType;
import com.qfc.yft.data.CompanyHttpHelper;
import com.qfc.yft.entity.SimpleShopInfo;
import com.qfc.yft.net.HttpReceiver;
import com.qfc.yft.net.HttpRequestTask;
import com.qfc.yft.utils.FavUtil;
import com.qfc.yft.utils.JackUtils;

public class ShActivityInfo extends ShopTabAbsActivity {
	private final String TAG = ShActivityInfo.class.getSimpleName();
	
	
	SimpleShopInfo favedShopInfo;
	
	TextView tv_CompName,tv_compProfession,tv_compModel,tv_region,tv_compMainProduct,tv_compPurchaseProduct,tv_compAddrdss,tv_compIntro;
	TextView tv_showMap;
//	CheckBox btn_fav;
	ImageView btn_fav;
	
	@Override
	public int getLayoutResId() {
		return R.layout.layout_sh_info;
	}
	@Override
	public void initUI(){
		tv_CompName = (TextView)findViewById(R.id.tv_info1_name);
		tv_compProfession	= (TextView)findViewById(R.id.tv_info21_field );
		tv_compModel		= (TextView)findViewById(R.id.tv_info22_mode );
		tv_region			= (TextView)findViewById(R.id.tv_info23_area );
		tv_compMainProduct	= (TextView)findViewById(R.id.tv_info31_service );
		tv_compPurchaseProduct= (TextView)findViewById(R.id.tv_info32_purchase );
		tv_compAddrdss		= (TextView)findViewById(R.id.tv_info33_address );
		tv_compIntro		= (TextView)findViewById(R.id.tv_info_desc); 
		
		tv_showMap = (TextView)findViewById(R.id.tv_info33_showmap);
		tv_showMap.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(ShActivityInfo.this, GeoCoderActivity.class);
				intent.putExtra("lat", shHelper.dfCompany.getCompLatitude());
				intent.putExtra("lon", shHelper.dfCompany.getCompLongitude());
				intent.putExtra("searchkey", shHelper.dfCompany.getCompAddress());
				startActivity(intent);
			}
		});
		initFavBtn();
	}
	
	@Override
	public void updateUI() {
		//compProfession compModel region compMainProduct compPurchaseProduct compAddress compIntro

		tv_CompName			.setText(shHelper.dfCompany.getCompName());
		TextPaint tp = tv_CompName.getPaint();tp.setFakeBoldText(true);//加粗
		tv_compProfession	.setText(shHelper.dfCompany.getCompProfession());//
		tv_compModel		.setText(shHelper.dfCompany.getCompModel());
		tv_region			.setText(shHelper.dfCompany.getRegion());
		tv_compMainProduct	.setText(shHelper.dfCompany.getCompMainProduct());
		tv_compPurchaseProduct.setText(shHelper.dfCompany.getCompPurchaseProduct());
		tv_compAddrdss		.setText(shHelper.dfCompany.getCompAddress());
		tv_compIntro		.setText("\t\t\t\t\t\t"+shHelper.dfCompany.getCompIntro());
		tv_compIntro.setText(Html.fromHtml("<B>公司简介：</B> "+shHelper.dfCompany.getCompIntro()));
		
		updatefav();
	}
	private void updatefav() {
		new HttpRequestTask(new HttpReceiver() {
			boolean isCollect;
			@Override
			public void response(String result) throws JSONException {
				JSONObject job = YftValues.getResultObject(result);
				if(null!=job){
					isCollect = job.getBoolean("isCollect");
					if(isCollect){
						btn_fav.setSelected(true);
					}
				}
				
			}
			
			@Override
			public Context getReceiverContext() {
				return ShActivityInfo.this.getReceiverContext();//
			}
		}).execute(YftValues.getHTTPBodyString(RequestType.ISCOLLECTEDSHOP, 
				YftData.data().getCurrentUser().getShopId()+"",
				YftData.data().getMe().getId()+""));
	}
	
	
	private void initFavBtn(){
		btn_fav				= (ImageView)findViewById(R.id.btn_info_heart);
		if(noMe()||isMe()||!YftValues.isopen(this)) {//1113 0429无网络
			btn_fav.setVisibility(View.INVISIBLE);
			return;
		}
		btn_fav.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				new HttpRequestTask(new HttpReceiver() {
					
					@Override
					public void response(String result) throws JSONException {
						if(result.contains("true")){
							if(!btn_fav.isSelected()){
								JackUtils.showToast(getReceiverContext(), "收藏成功");
								btn_fav.setSelected(true);
							}else{
								JackUtils.showToast(getReceiverContext(), "取消收藏成功");
								btn_fav.setSelected(false);
							}
						}else{
							JackUtils.showToast(getReceiverContext(), "操作失败");
						}
						
					}
					
					@Override
					public Context getReceiverContext() {
						return ShActivityInfo.this;
					}
				}).execute(YftValues.getHTTPBodyString(v.isSelected()?RequestType.COLLECTION_DELETE:RequestType.COLLECTION_SAVE, 
						YftData.data().getMe().getId()+"",
						YftData.data().getCurrentUser().getShopId()+"",
						"3","wtf"));
				
			}
		});
		
	}
	boolean canShow;
	private void showDialog(String text){
		if(!canShow) return;
		 AlertDialog.Builder builder = new Builder(getReceiverContext());
		  builder.setMessage(text);

		  builder.setTitle("提示");

		  builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		  
		  builder.show();
	}
	private boolean noMe(){//TODO 这个版本是否还有这种情况？考虑删除
		return YftData.data().getMe()==null;
	}
	private boolean isMe(){
		return !noMe()&&YftData.data().isMe();//shHelper.dfCompany.getMemberId()==YftData.data().getMe().getId();
	}
	/*private void updateFav() {
		if(noMe()) return;//1113
		if(isMe()){//自己人收藏个蛋
			btn_fav.setVisibility(View.INVISIBLE);
			return ;
		}
//		initPref();
		int cid = shHelper.dfUser.getShopId();
//		favedShopStr = favedShopPref.getString(cid+"", "");
		String favedShopStr = FavUtil.findFav(cid+"");
		if(favedShopStr.isEmpty()){//没收藏 保持现状
//			return; 
		}else{
//			btn_fav.setChecked(true);
		}
		canShow=true;
		
	}*/


	
}
