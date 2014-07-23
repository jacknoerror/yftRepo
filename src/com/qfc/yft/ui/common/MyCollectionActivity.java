package com.qfc.yft.ui.common;

import android.content.Intent;
import android.util.SparseArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.qfc.yft.R;
import com.qfc.yft.data.Const;
import com.qfc.yft.ui.MyPortal;
import com.qfc.yft.ui.MyTitleActivity;
import com.qfc.yft.ui.custom.list.ListItemImpl.Type;
import com.qfc.yft.ui.custom.list.MyJackListView;
import com.qfc.yft.ui.tabs.main.cat.JackRadioViewGroup;
import com.qfc.yft.vo.LIICompany;
import com.qfc.yft.vo.LIIProduct;

public class MyCollectionActivity extends MyTitleActivity implements OnCheckedChangeListener, OnItemClickListener {

	JackRadioViewGroup jRadio;
	MyJackListView currentJlv;
	SparseArray<MyJackListView> jlvMap;
	private RadioGroup jrGroup;
	private FrameLayout frameContainer;
	
	@Override
	public int getLayoutRid() {
		return R.layout.activity_common_frame;
	}

	@Override
	public void initView() {
		titleManager.initTitleBack();
		titleManager.setTitleName(getString(R.string.titlename_myfave));
		
		FrameLayout frameUnderTitle = (FrameLayout)this.findViewById(R.id.frame_common);
		jRadio = new JackRadioViewGroup(this);
		jrGroup = jRadio.initBtns(R.layout.view_radiogroup_my, this);
		frameContainer = jRadio.mFrame;
		frameUnderTitle.addView(jRadio);

		jlvMap = new SparseArray<MyJackListView>();
		
		jrGroup.check(R.id.radio1);//
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		MyJackListView jlv = jlvMap.get(checkedId);
		if(null==jlv) {
			switch (checkedId) {
			case R.id.radio1:
				jlv = initJlv(checkedId, Type.IP_PRODUCT_MY);
				break;
			case R.id.radio2:
				jlv = initJlv(checkedId, Type.IP_COMPANY_MY);
				break;
				
			case R.id.radio3://ɨһɨ
				
				Intent intent = new Intent();
				intent.setClass(this, QRCaptureActivity.class);
				startActivityForResult(intent, Const.AR_QR);
				if(null!=currentJlv) {
					frameContainer.removeView(currentJlv);
					currentJlv = null;
				}
				jrGroup.clearCheck();
				break;
				
			default:
				break;
			}
		}
		else if(jlv==currentJlv) return;//
		if(null!=jlv)switchJlv(jlv);
		
	}

	/**
	 * @param checkedId
	 * @param type
	 * @return 
	 */
	public MyJackListView initJlv(int checkedId, Type type) {
		MyJackListView jlv;
		jlv = new MyJackListView(this, type);
		jlvMap.put(checkedId, jlv);
		
		jlv.setOnItemClickListener(this);
		return jlv;
	}

	/**
	 * @param jlv
	 */
	public void switchJlv(MyJackListView jlv) {
		if(null!=currentJlv) frameContainer.removeView(currentJlv);
		currentJlv= jlv ;
		frameContainer.addView(currentJlv);
		if(!jlv.isSetup())jlv.setup();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		if(null==currentJlv) return;
		Object itemAtPosition = parent.getItemAtPosition(position);
		switch (currentJlv.getType()) {
		case IP_PRODUCT_MY:
			MyPortal.goProduct(this, (LIIProduct) itemAtPosition);
			break;
		case IP_COMPANY_MY:
			LIICompany lc = (LIICompany)itemAtPosition;
			MyPortal.goShop(this, lc.getShopId(), lc.getShopName(), lc.getShopType());
			break;

		default:
			break;
		}
		
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == RESULT_OK){
			if(requestCode == Const.AR_QR){
				if(jrGroup!=null) jrGroup.check(R.id.radio2);
			}else if(requestCode == Const.AR_QPC){
				
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
}
