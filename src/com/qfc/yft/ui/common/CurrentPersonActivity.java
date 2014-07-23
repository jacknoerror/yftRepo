package com.qfc.yft.ui.common;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.qfc.yft.R;
import com.qfc.yft.data.MyData;
import com.qfc.yft.data.NetConst;
import com.qfc.yft.net.HttpRequestTask;
import com.qfc.yft.net.action.ActionBuilder;
import com.qfc.yft.net.action.ActionReceiverImpl;
import com.qfc.yft.net.action.ActionRequestImpl;
import com.qfc.yft.net.action.ActionStrategies;
import com.qfc.yft.net.action.attention.IsAttentionReq;
import com.qfc.yft.net.action.member.CancelAttentionReq;
import com.qfc.yft.net.action.member.DoAttentionReq;
import com.qfc.yft.ui.MyPortal;
import com.qfc.yft.util.JackUtils;
import com.qfc.yft.vo.LIIPeople;

public class CurrentPersonActivity extends Activity implements
		ActionReceiverImpl, View.OnClickListener {
	LIIPeople cpPeople;
	CPerReceiver cpr;

	ImageView btn_coo;
	private ImageView btn_shop;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_current_person);
		int pid = getIntent().getIntExtra(NetConst.EXTRAS_ACCOUNT_ID, -1);
		cpPeople = MyData.data().getStoredPerson(pid);
		if (cpPeople != null) {
			init();
		}
	}

	private void init() {
		setContent(R.id.tv_person_name, cpPeople.getName());
		setContent(R.id.tv_person_position, cpPeople.memberPosition);
		setContent(R.id.tv_person_company, cpPeople.compName);
		setContent(R.id.tv_person_customizationaltext,
				cpPeople.privateSignature);

		setContent(R.id.tv_a_1, cpPeople.region);// test
		setContent(R.id.tv_a_2, cpPeople.college);
		setContent(R.id.tv_b_1, cpPeople.mobile);
		setContent(R.id.tv_b_2, cpPeople.phoneDdd);
		setContent(R.id.tv_b_3, cpPeople.email);
		setContent(R.id.tv_b_4, cpPeople.wangwang);
		setContent(R.id.tv_b_5, cpPeople.wechat);
		setContent(R.id.tv_c_1, cpPeople.compProfession);
		setContent(R.id.tv_c_2, cpPeople.compMainProduct);
		setContent(R.id.tv_d_1, cpPeople.memberPosition);
		setContent(R.id.tv_d_2, cpPeople.compName);
		setContent(R.id.tv_d_3, cpPeople.webSite);
		setContent(R.id.tv_d_4, cpPeople.compAddress);

		// cpPeople.memberSex
		((ImageView) findViewById(R.id.img_ic))
				.setImageResource(cpPeople.memberSex == 1 ? R.drawable.photo_boy
						: R.drawable.photo_girl);

		btn_coo = (ImageView) findViewById(R.id.btn_c_coo);
		btn_coo.setOnClickListener(this);// btn_c_fzl
		ImageView btn_fzl = (ImageView) findViewById(R.id.btn_c_fzl);
		btn_fzl.setOnClickListener(this);

		btn_shop = (ImageView) findViewById(R.id.btn_shop);
		btn_shop.setOnClickListener(this);
		((ImageView) findViewById(R.id.btn_back)).setOnClickListener(this);

		cpr = new CPerReceiver(CurrentPersonActivity.this);
		
		ActionRequestImpl actReq = new IsAttentionReq(cpPeople.accountId, MyData.data().getMe().getId());
		ActionBuilder.getInstance().request(actReq, cpr);
		
	}

	@Override
	public void onClick(View v) {
		int id = MyData.data().getMe().getId();
		switch (v.getId()) {
		case R.id.btn_c_coo:
			if (id == cpPeople.accountId) {
				JackUtils.showToast(this, "请不要关注自己");
				break;
			}// 0508
			if (id == 0) {
				MyPortal.goStartLogin(this);
				break;
			}
			
			ActionRequestImpl actReq = null;
			if(!btn_coo.isSelected())actReq = new DoAttentionReq(id,cpPeople.accountId );
			else actReq = new CancelAttentionReq(id,cpPeople.accountId );
			ActionBuilder.getInstance().request(actReq, CurrentPersonActivity.this);
			break;
		case R.id.btn_c_fzl:

//			YftActivityGate.goChat(this, cpPeople.userName, cpPeople.texTalk,CimConsts.ConnectUserType.FRIEND, cpPeople.headIcon);// type? TODO
			break;
		case R.id.btn_shop:
			if (null == cpr)
				return;//
				// user.setId(cpPeople.accountId);
			MyPortal.goShop(this, cpr.companyId, cpPeople.compName,
					cpr.memberType, -1);
			break;
		case R.id.btn_back:
			finish();
			break;
		default:
			break;
		}

	}

	private void setContent(int id, String content) {
		TextView text = (TextView) findViewById(id);
		if (isNoString(content)) {
			content = "暂无";
		}
		text.setText(content);

	}

	/**
	 * @param content
	 * @return
	 */
	private boolean isNoString(String content) {
		return null == content || content.isEmpty() || content.equals("null");
	}

	@Override
	public boolean response(String result) throws JSONException {
		if (result.contains("true")) {// errorCode
			if (btn_coo.isSelected()) {
				JackUtils.showToast(getReceiverContext(), "取消收藏成功");
				btn_coo.setSelected(false);
			} else {
				JackUtils.showToast(getReceiverContext(), "收藏成功");
				btn_coo.setSelected(true);
			}
		} else {
			if (result.contains("已关注"))
				btn_coo.setSelected(true);
			else
				JackUtils.showToast(getReceiverContext(), "操作失败");
		}
		return true;
	}

	@Override
	public Context getReceiverContext() {
		return this;
	}

	public class CPerReceiver implements ActionReceiverImpl {
		boolean isAttention;
		int companyId;
		int memberType;

		Context cContext;

		public CPerReceiver(Context context) {
			cContext = context;
		}

		@Override
		public boolean response(String result) throws JSONException {
			JSONObject job = ActionStrategies.getResultObject(result);
			if (null != job) {
				if (job.has("isAttention"))
					isAttention = job.getBoolean("isAttention");
				if (job.has("companyId") && !job.isNull("companyId"))
					companyId = job.getInt("companyId");
				if (job.has("memberType"))
					memberType = job.getInt("memberType");

				if (isAttention) {
					btn_coo.setSelected(true);
				}

				SimpleCompany lc = new SimpleCompany();// 0318
				lc.userId = cpPeople.accountId;
				lc.shopId = companyId;
				lc.shopName = cpPeople.compName;
				lc.memberType = memberType;
				if (memberType != 2 && memberType != 3) {
					if (null != btn_shop)
						btn_shop.setVisibility(View.INVISIBLE);
				}
//				MyData.data().storeShopById(lc);//TODO
			}
			return false;
		}

		@Override
		public Context getReceiverContext() {
			return CurrentPersonActivity.this;
		}

	}
	
	static public class SimpleCompany {
		public int userId;
		public String shopName;
		public int memberType;
		public int shopId;
	}
}
