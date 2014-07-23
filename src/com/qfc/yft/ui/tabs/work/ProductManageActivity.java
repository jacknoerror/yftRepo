package com.qfc.yft.ui.tabs.work;

import java.text.Format;
import java.util.Locale;

import org.json.JSONException;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;

import com.qfc.yft.R;
import com.qfc.yft.data.Const;
import com.qfc.yft.data.MyData;
import com.qfc.yft.net.action.ActionBuilder;
import com.qfc.yft.net.action.ActionReceiverImpl;
import com.qfc.yft.net.action.ActionRequestImpl;
import com.qfc.yft.net.action.ActionStrategies;
import com.qfc.yft.net.action.BareReceiver;
import com.qfc.yft.net.action.member.SearchManageProductReq;
import com.qfc.yft.net.action.product.PMOffline;
import com.qfc.yft.net.action.product.PMOnline;
import com.qfc.yft.net.action.product.PMRepublish;
import com.qfc.yft.ui.MyTitleActivity;
import com.qfc.yft.ui.custom.list.MspAdapter;
import com.qfc.yft.ui.custom.list.MyJackListView;
import com.qfc.yft.ui.custom.list.ListItemImpl.Type;
import com.qfc.yft.ui.tabs.HubActivity;
import com.qfc.yft.util.JackUtils;
import com.qfc.yft.util.TestDataTracker;
import com.qfc.yft.vo.User;

public class ProductManageActivity extends MyTitleActivity implements OnClickListener {

	private EditText mEdit;
	private User me;
	private MyJackListView mListView;
	private FrameLayout frame;
	private View bottomLayout;
	private ActionReceiverImpl multiFuncRcv;
	ImageView img_tr_e,img_tr_f,
				img_tr_a;

	private  String[] groups;
	private String pStatStr,aStatStr,iPrvtStr,mSrsStr;
	
	@Override
	public int getLayoutRid() {
		return R.layout.activity_search_frame;
	}

	@Override
	public void initView() {
		me = MyData.data().getMe();
		if(null==me) return;
		
		titleManager.setTitleName(getString(R.string.titlename_pm));
		titleManager.initTitleBack();
		img_tr_e = getImageView(R.drawable.title_edit);
		img_tr_f = getImageView(R.drawable.title_filter);
		img_tr_a = getImageView(R.drawable.node_select_all);
		img_tr_a.setVisibility(View.GONE);
		img_tr_f.setPadding(15, 0, 0, 0);
		titleManager.addToRightLayout(img_tr_e);
		titleManager.addToRightLayout(img_tr_f);
		titleManager.addToRightLayout(img_tr_a);
		
		groups= getResources().getStringArray(R.array.pmf_group);
		Intent intent = getIntent();
		handleExtras(intent.getExtras());//暂时不会有参数，只在activityResult时需要
		
		mListView = new MyJackListView(this, Type.PM);
		mListView.setOnGetPageListener(new MyJackListView.OnGetPageListener() {
			
			@Override
			public void page(MyJackListView qListView, int pageNo) {
				ActionBuilder.getInstance().request(new SearchManageProductReq(aStatStr,iPrvtStr,pStatStr,mSrsStr,me.getShopId(),pageNo), qListView);
//				TestDataTracker.simulateConnection(qListView, req.getApiName());//
			}
		});
		mListView.setup();
		mListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		mListView.setTag(this);//for dialog context in item
//		mListView.setDescendantFocusability(ListView.FOCUS_AFTER_DESCENDANTS);
//		mListView.setSelected(true);
		
		frame = (FrameLayout)this.findViewById(R.id.frame_common);
		frame.addView(mListView);

		mEdit = (EditText) this.findViewById(R.id.et_searchbar);
		findViewById(R.id.btn_search_searchbar).setOnClickListener(this);
		mEdit.clearFocus();//
		
		bottomLayout = findViewById(R.id.layout_pm_bottom);
		findViewById(R.id.btn_pm_on).setOnClickListener(this);
		findViewById(R.id.btn_pm_off).setOnClickListener(this);
		findViewById(R.id.btn_pm_re).setOnClickListener(this);
		
		multiFuncRcv = new BareReceiver(this){
			@Override
			public boolean response(String result) throws JSONException {
				boolean response = super.response(result);
				if(response&&ActionStrategies.getResultBoolean(result)){
					JackUtils.showToast(ProductManageActivity.this, "操作成功");
					showEdit(false);
					mListView.clearChoices();
					//TODO 更改状态
				}
				return response;
			}
		};
	}

	/**
	 * @param intent
	 */
	public void handleExtras(Bundle extras) {
		if(null==extras) return;
		pStatStr = extras.getString(groups[0]);
		aStatStr = extras.getString(groups[1]);
		iPrvtStr = extras.getString(groups[2]);
		mSrsStr = extras.getString(groups[3]);
	}

	private ImageView getImageView(int rid){
		ImageView img = new ImageView(this);
		img.setId(rid);
		img.setImageResource(rid);
		img.setOnClickListener(this);
		return img;
	}
	@Override
	public void onClick(View v) {
		ActionRequestImpl actReq = null;
		switch (v.getId()) {
		case R.drawable.node_select_all:
			boolean selectedNotEnough = mListView.getCheckedItemIds().length<mListView.getCount();
			for(int i=0;i<mListView.getCount();i++) {
				mListView.setItemChecked(i, selectedNotEnough);
			}
			frame.removeView(mListView);
			frame.addView(mListView);
			break;
		case R.drawable.title_edit:
			showEdit( !mListView.isSelected());
			break;
		case R.drawable.title_filter:
			Intent intent = new Intent();
			intent.setClass(this, ProductManageFilterActivity.class);
			startActivityForResult(intent, Const.AR_PM_FILTER);
			break;
		case R.id.btn_search_searchbar:
			mListView.setup();
			break;
		case R.id.btn_pm_on:
			actReq = new PMOnline(me.getShopId(), mListView.getCheckedItemIds());
			ActionBuilder.getInstance().request(actReq, multiFuncRcv);
			break;
		case R.id.btn_pm_off:
			actReq = new PMOffline(me.getShopId(), mListView.getCheckedItemIds());
			ActionBuilder.getInstance().request(actReq, multiFuncRcv);
			break;
		case R.id.btn_pm_re:
			actReq = new PMRepublish(me.getShopId(), mListView.getCheckedItemIds());
			ActionBuilder.getInstance().request(actReq, multiFuncRcv);
			break;
		default:
			break;
		}
		
	}

	/**
	 * @param showEdit
	 */
	public void showEdit(boolean showEdit) {
		mListView.setSelected(showEdit);
		frame.removeView(mListView);
		frame.addView(mListView);
		bottomLayout.setVisibility(mListView.isSelected()?View.VISIBLE:View.GONE);
		//改变标题右上角
		img_tr_a.setVisibility(showEdit?View.VISIBLE:View.GONE);
		img_tr_e.setVisibility(showEdit?View.GONE:View.VISIBLE);
		img_tr_f.setVisibility(showEdit?View.GONE:View.VISIBLE);
		
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode==RESULT_OK&&requestCode== Const.AR_PM_FILTER){
			handleExtras(data.getExtras());
			mListView.setup();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode==KeyEvent.KEYCODE_BACK ){
			if( mListView.isSelected()){
				showEdit(false);
				return true;
			}
		};
		return super.onKeyDown(keyCode, event);
	}
}
