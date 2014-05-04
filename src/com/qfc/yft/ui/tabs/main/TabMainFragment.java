package com.qfc.yft.ui.tabs.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.qfc.yft.R;
import com.qfc.yft.YftApplication;
import com.qfc.yft.YftData;
import com.qfc.yft.YftValues;
import com.qfc.yft.YftValues.RequestType;
import com.qfc.yft.entity.Category;
import com.qfc.yft.net.HttpReceiver;
import com.qfc.yft.net.HttpRequestTask;
import com.qfc.yft.ui.tabs.ContentAbstractFragment;
import com.qfc.yft.utils.JackUtils;
import com.qfc.yft.utils.TestDataTracker;

public class TabMainFragment extends ContentAbstractFragment implements View.OnClickListener,HttpReceiver{
	final String TAG = TabMainFragment.class.getName();
	final int[] ICS=new int[]{
					R.drawable.category_fabric,
					R.drawable.category_pb,
					R.drawable.category_jf,
					R.drawable.category_fz,
					R.drawable.category_fs,
					R.drawable.category_fl,
					R.drawable.category_ssx,
					R.drawable.category_fjpj,
					R.drawable.category_hgzj,
					R.drawable.category_fzyl,
					R.drawable.category_xbdpj,
					R.drawable.category_pgzp,
					R.drawable.category_fzgyp,
					R.drawable.category_fzkcp,
					R.drawable.category_fzfl,
					R.drawable.category_qt				
				};
	final int[] CID = new int[]{90,91,92,45,138,109,34,120,130,1,137,136,110,114,115,135};
	final String[] TTS=new String[]{
			"面料","坯布","家纺","服装",
			"服饰","辅料","纱丝线","纺机配件",
			"化工助剂","纺织原料","箱包袋皮具",
			"皮革制品","纺织工艺品","纺织库存品",
			"纺织废料","其他"};
	
	
	TableLayout mTable;
	
	
	@Override
	public void onResume() {
		if(!YftData.data().isCatInited())
			new HttpRequestTask(this).execute(YftValues.getHTTPBodyString(RequestType.CATEGORY_ALL, ""));
//		TestDataTracker.simulateConnection(this, RequestType.CATEGORY_ALL.toString());
		super.onResume();
	}//
	
	@Override
	public void initView( ) {
		mView = mInflator.inflate(R.layout.fragment_home, null);
		mTable =(TableLayout)mView.findViewById(R.id.table_home);
//		mTable.setStretchAllColumns(true); //helpless
		mTable.setShrinkAllColumns(true);
//		giveDividers(mTable, true); //有bug ，见addEndDivider方法
		TableRow tr=null;
		for(int i=0;i<ICS.length;i++){
			if(i%3==0){
				tr= new TableRow(getActivity());
				giveDividers(tr, false);
				if(i!=0) mTable.addView(mInflator.inflate(R.layout.view_divider_h, null));
				mTable.addView(tr);
				addEndDivider(i,tr);
			}
			
			/*ImageView tempImg = new ImageView(getActivity());
			tempImg.setImageResource(ICS[i]);*/
			tr.addView(getSingleGrid(i));
		}
		
		EditText goSearch =(EditText)mView.findViewById(R.id.et_home);
		goSearch.setFocusable(false);//cancel default cursor
		goSearch.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				YftData.data().getHostTab().setCurrentTab(1);//exception
			}
		});
	}
	
	@SuppressLint("NewApi")
	private void addEndDivider(int i,View row) {
		// 解决表格末尾在中间时最后一条竖线不显示的bug
		if(i==ICS.length-1&&i%3<3){ //是最后一个且不是行末
			try{
				if(JackUtils.getApiLvl()>10)((LinearLayout)row).setShowDividers(LinearLayout.SHOW_DIVIDER_END);
			}catch(Exception e){
			}
		}
	}

	private View getSingleGrid(  int index){
		//init
		View view = mInflator.inflate(R.layout.layout_homegrid, null);
		ImageView img = (ImageView)view.findViewById(R.id.img_homegrid);
		TextView text = (TextView)view.findViewById(R.id.tv_homegrid);
		img.setImageResource(ICS[index]);
		img.setScaleType(ScaleType.FIT_CENTER);
		text.setText(TTS[index]);
		//listener
		view.setTag(CID[index]);
		view.setOnClickListener(this);
		return view;
	}
	
	@SuppressLint("NewApi")
	private void giveDividers(LinearLayout layout, boolean isHorizontal){
		
		try{
			
			int rdh=R.drawable.divider_h,rdv=R.drawable.divider_v;
			if(JackUtils.getApiLvl()>10){
				layout.setDividerDrawable(getResources().getDrawable(isHorizontal?rdh:rdv));
			}else{
			}
			if(JackUtils.getApiLvl()>10)layout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
		}catch(NoSuchMethodError e){
			Log.e(TAG, "new api --dividers");
		}
	}

	

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
 		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onClick(View v) {
		Integer index = (Integer) v.getTag();
		Intent intent = new Intent();
		intent.putExtra(YftValues.EXTRA_CATEGORY_ID, index);
		intent.setClass(getReceiverContext(), CategoryActivity.class);
		startActivity(intent);
	}

	@Override
	public void response(String result) throws JSONException {
//		TestDataTracker.settleDataString(RequestType.CATEGORY_ALL.toString(), result);
		JSONObject job = YftValues.getResultObject(result);
		if(null!=job){
			JSONArray root = job.getJSONArray("ROOT");
			for(int i=0;i<root.length();i++){//加到yftData
				Category cat = new Category();
				JSONObject catJob = root.getJSONObject(i);
				cat.initJackJson(catJob);
				YftData.data().putCategory(cat);
			}
			
		}
		
	}

	@Override
	public Context getReceiverContext() {
		return getActivity();
	}
}
