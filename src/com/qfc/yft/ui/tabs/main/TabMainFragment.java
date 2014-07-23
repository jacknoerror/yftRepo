package com.qfc.yft.ui.tabs.main;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.qfc.yft.R;
import com.qfc.yft.data.Const;
import com.qfc.yft.data.MyData;
import com.qfc.yft.data.NetConst;
import com.qfc.yft.ui.MyPortal;
import com.qfc.yft.ui.tabs.ContentAbstractFragment;
import com.qfc.yft.vo.User;

public class TabMainFragment extends ContentAbstractFragment implements View.OnClickListener{

	@Override
	public void initView() {
		mView.findViewById(R.id.tvbtn_a1).setOnClickListener(this);
		mView.findViewById(R.id.tvbtn_a2).setOnClickListener(this);
		mView.findViewById(R.id.tvbtn_a3).setOnClickListener(this);
		mView.findViewById(R.id.tvbtn_b1).setOnClickListener(this);
		mView.findViewById(R.id.tvbtn_b2).setOnClickListener(this);
		mView.findViewById(R.id.tvbtn_b3).setOnClickListener(this);
		mView.findViewById(R.id.tvbtn_b4).setOnClickListener(this);
		mView.findViewById(R.id.tvbtn_b5).setOnClickListener(this);
		mView.findViewById(R.id.tvbtn_b6).setOnClickListener(this);
		mView.findViewById(R.id.tvbtn_c1).setOnClickListener(this);
		mView.findViewById(R.id.tvbtn_c2).setOnClickListener(this);
		mView.findViewById(R.id.tvbtn_c3).setOnClickListener(this);
		
		mView.findViewById(R.id.et_home).setOnClickListener(this);
	}

	@Override
	public int getLayoutRid() {
		return R.layout.fragment_main;
	}

	@Override
	public void onClick(View v) {
		Context context = (Context)getActivity();
		switch (v.getId()) {
		case R.id.et_home:
		case R.id.tvbtn_a2:
			//go search
			MyPortal.goCatnSrch(context,1);
			break;
		case R.id.tvbtn_a1:
			MyPortal.goCatnSrch(context,0);
			break;
		case R.id.tvbtn_a3:
			MyPortal.goCatnSrch(context,2);
			break;
		case R.id.tvbtn_b1:
			goWebView(getString(R.string.title_b1),Const.URL_B1);
			break;
		case R.id.tvbtn_b2:
			goWebView(getString(R.string.title_b2),Const.URL_B2);
			break;
		case R.id.tvbtn_b3:
			goWebView(getString(R.string.title_b3),Const.URL_B3);
			break;
		case R.id.tvbtn_b4:
			goWebView(getString(R.string.title_b4),Const.URL_B4);
			break;
		case R.id.tvbtn_b5:
			goWebView(getString(R.string.title_b5),Const.URL_B5);
			break;
		case R.id.tvbtn_b6:
			goWebView(getString(R.string.title_b6),Const.URL_B6);
			break;
		case R.id.tvbtn_c1:
			User mee = MyData.data().getMe();
			MyPortal.goShop(context, mee.getShopId(), "", mee.getMemberType());
			break;
		case R.id.tvbtn_c2:
			MyPortal.goMyCollection(context);
			break;
		case R.id.tvbtn_c3:
			MyPortal.goMyPeople(context);
			break;
		default:
			break;
		}
		
	}

	private void goWebView(String name, String url) {
		Intent intent = new Intent();
		intent.setClass(getActivity(), WebViewActivity.class);
		intent.putExtra(NetConst.EXTRAS_MARKETNAME, name);
		intent.putExtra(NetConst.EXTRAS_MARKETURL, url);
		startActivity(intent);
	}

}
