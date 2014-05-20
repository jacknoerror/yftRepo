package com.qfc.yft.ui.current;

import org.json.JSONException;
import org.json.JSONObject;

import com.qfc.yft.CimConsts;
import com.qfc.yft.R;
import com.qfc.yft.YftData;
import com.qfc.yft.YftValues;
import com.qfc.yft.YftValues.RequestType;
import com.qfc.yft.entity.SimpleCompany;
import com.qfc.yft.entity.User;
import com.qfc.yft.entity.listitem.LIICompany;
import com.qfc.yft.entity.listitem.LIIPeople;
import com.qfc.yft.net.HttpReceiver;
import com.qfc.yft.net.HttpRequestTask;
import com.qfc.yft.ui.YftActivityGate;
import com.qfc.yft.ui.shop.ShopActivity;
import com.qfc.yft.utils.JackUtils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.util.Linkify;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class CurrentPersonActivity extends Activity implements HttpReceiver,View.OnClickListener{
	LIIPeople cpPeople;
	CPerReceiver cpr;
	
	ImageView btn_coo;
	private ImageView btn_shop;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_current_person);
		int pid = getIntent().getIntExtra(YftValues.EXTRAS_ACCOUNT_ID,-1);
		cpPeople = YftData.data().getStoredPerson(pid);
//		cpProduct  = getIntent().getParcelableExtra("product");
		if(cpPeople!=null){
			init();
		}
	}
	
	private void init() {
		setContent(R.id.tv_person_name, cpPeople.getName());
		setContent(R.id.tv_person_position, cpPeople.memberPosition);
		setContent(R.id.tv_person_company, cpPeople.compName);
		setContent(R.id.tv_person_customizationaltext, cpPeople.privateSignature);
		
		setContent(R.id.tv_a_1,cpPeople.region);//TODO tobe test 
		setContent(R.id.tv_a_2,cpPeople.college);
		setContent(R.id.tv_b_1,cpPeople.mobile);
		setContent(R.id.tv_b_2,cpPeople.phoneDdd);
		setContent(R.id.tv_b_3,cpPeople.email);
		setContent(R.id.tv_b_4,cpPeople.wangwang);
		setContent(R.id.tv_b_5,cpPeople.wechat);
		setContent(R.id.tv_c_1,cpPeople.compProfession);
		setContent(R.id.tv_c_2,cpPeople.compMainProduct);
		setContent(R.id.tv_d_1,cpPeople.memberPosition);
		setContent(R.id.tv_d_2,cpPeople.compName);
		setContent(R.id.tv_d_3,cpPeople.webSite);
		setContent(R.id.tv_d_4,cpPeople.compAddress);
		
//		cpPeople.memberSex
		((ImageView)findViewById(R.id.img_ic)).setImageResource(cpPeople.memberSex==1?R.drawable.photo_boy:R.drawable.photo_girl);
		
		btn_coo = (ImageView)findViewById(R.id.btn_c_coo);
		btn_coo.setOnClickListener(this);//btn_c_fzl
		ImageView btn_fzl = (ImageView)findViewById(R.id.btn_c_fzl);
		btn_fzl.setOnClickListener(this);
		
		btn_shop = (ImageView)findViewById(R.id.btn_shop);
		btn_shop.setOnClickListener(this);
		((ImageView)findViewById(R.id.btn_back)).setOnClickListener(this);
		
		cpr = new CPerReceiver(CurrentPersonActivity.this);
		new HttpRequestTask(cpr).execute(YftValues.getHTTPBodyString(RequestType.ISATTENTION, 
				cpPeople.accountId+"",
				YftData.data().getMe().getId()+""));
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_c_coo:
			if(YftData.data().getMe().getId()==cpPeople.accountId){JackUtils.showToast(this, "请不要关注自己");break;}//0508
			new HttpRequestTask(CurrentPersonActivity.this).execute(
					YftValues.getHTTPBodyString(v.isSelected()?RequestType.CARD_REMOVE:RequestType.CARD_ADD, 
							YftData.data().getMe().getId()+"",
							""+cpPeople.accountId));
			break;
		case R.id.btn_c_fzl:
			
			YftActivityGate.goChat(this, cpPeople.userName, cpPeople.texTalk	, CimConsts.ConnectUserType.FRIEND, cpPeople.headIcon);//type?
			break;
		case R.id.btn_shop:
			if(null==cpr) return;//
//			user.setId(cpPeople.accountId);
			YftActivityGate.goShop(this, cpr.companyId, cpPeople.compName, cpr.memberType, -1);
			break;
		case R.id.btn_back:
			finish();
			break;
		default:
			break;
		}
		
	}

	private void setContent(int id,String content){
		TextView text = (TextView)findViewById(id);
		if(isNoString(content)){
			content= "暂无";
		}
		text.setText(content);
		
		/*if(id==R.id.tv_b_1||id==R.id.tv_b_2){//
			if(!text.getText().equals("暂无")){
				text.setAutoLinkMask(Linkify.PHONE_NUMBERS);
			}
		}*/
	}

	/**
	 * @param content
	 * @return
	 */
	private boolean isNoString(String content) {
		return null==content||content.isEmpty()||content.equals("null");
	}
	@Override
	public void response(String result) throws JSONException {
		if(result.contains("true")){//errorCode
			if(btn_coo.isSelected()){
				JackUtils.showToast(getReceiverContext(), "取消收藏成功");
				btn_coo.setSelected(false);
			}else{
				JackUtils.showToast(getReceiverContext(), "收藏成功");
				btn_coo.setSelected(true);
			}
		}else{
			if(result.contains("已关注")) btn_coo.setSelected(true);
			else 			JackUtils.showToast(getReceiverContext(), "操作失败");
		}
		
	}
	@Override
	public Context getReceiverContext() {
		return this;
	}
	
	public class CPerReceiver implements HttpReceiver{
		boolean isAttention;
		int companyId;
		int memberType;
		
		Context cContext;
		public CPerReceiver(Context context){
			cContext = context;
		}
		
		@Override
		public void response(String result) throws JSONException {
			JSONObject job = YftValues.getResultObject(result);
			if(null!=job){
				if(job.has("isAttention"))isAttention = job.getBoolean("isAttention");
				if(job.has("companyId")&&!job.isNull("companyId"))companyId = job.getInt("companyId");
				if(job.has("memberType"))memberType= job.getInt("memberType");
				
				if(isAttention){
					btn_coo.setSelected(true);
				}
				
				SimpleCompany lc = new SimpleCompany();//0318
				lc.userId=cpPeople.accountId;
				lc.shopId=companyId;
				lc.shopName=cpPeople.compName;
				lc.memberType=memberType;
				if(memberType!=2&&memberType!=3){
					if(null!=btn_shop) btn_shop.setVisibility(View.INVISIBLE);
				}
				YftData.data().storeShopById(lc);
			}
			
		}
		
		@Override
		public Context getReceiverContext() {
			return CurrentPersonActivity.this;
		}
	
	}
}
