package com.qfc.yft.ui.tabs.work;

import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qfc.yft.R;
import com.qfc.yft.data.MyData;
import com.qfc.yft.net.action.ActJobRcv;
import com.qfc.yft.net.action.ActionBuilder;
import com.qfc.yft.net.action.ActionReceiverImpl;
import com.qfc.yft.net.action.ActionRequestImpl;
import com.qfc.yft.net.action.ActionStrategies;
import com.qfc.yft.net.action.BareReceiver;
import com.qfc.yft.net.action.product.FindAllSeriesReq;
import com.qfc.yft.ui.MyTitleActivity;
import com.qfc.yft.ui.custom.JackExpandableMutilineLayout;
import com.qfc.yft.ui.custom.JackMultiLineLayout;
import com.qfc.yft.vo.User;

public class ProductManageFilterActivity extends MyTitleActivity {

//	private Diad adapter;

	private LinearLayout container;
	private SparseArray<String[]> itemMap;
	private SparseArray<JackExpandableMutilineLayout> jemMap;
	private String[] groups ;

	@Override
	public int getLayoutRid() {
		return R.layout.activity_pm_filter;
	}
/**
	----auditStatus - <Integer>  - 审核状态 待审核 = 0; 审核通过 = 1;   审核驳回 = 2;   暂不审核= 3;
	----isPrivate - <Integer>  - 是否私有 0：非私有， 1：私有
	----productStatus - <Integer>  - 产品状态   1:上线 0:下线 -1:删除
 */
	
	@Override
	public void initView() {
		titleManager.setTitleName(getString(R.string.titlename_album_create));
		titleManager.initTitleBack();
		
		jemMap = new SparseArray<JackExpandableMutilineLayout>();
		itemMap = new SparseArray<String[]>();
		container = (LinearLayout) this.findViewById(R.id.layout_pmf);
		groups= getResources().getStringArray(R.array.pmf_group);
		
		User me = MyData.data().getMe();
		if(null==me) return;
		ActionRequestImpl actReq = new FindAllSeriesReq(me.getShopId());
		ActionReceiverImpl actRcv = new BareReceiver(this){
			@Override
			public boolean response(String result) throws JSONException {
				boolean response = super.response(result);
				JSONArray jar;
				if(response&&(jar  = ActionStrategies.getResultArray(result))!=null&&jar.length()>0){
					int length = jar.length();
					String[] nameStrs = new String[length];
					for(int i=0;i<length;i++){
						String name = jar.getJSONObject(i).optString("productSeriesName");
							nameStrs[i] = name;
					}
					itemMap.put(3, nameStrs);
					initExpandLayout(3, groups[3] , null);
				}
				return response;
			}
		};
		ActionBuilder.getInstance().request(actReq, actRcv);
		
		itemMap.put(0,  getResources().getStringArray(R.array.pmf_pstat));
		itemMap.put(1,  getResources().getStringArray(R.array.pmf_astat));
		itemMap.put(2,  getResources().getStringArray(R.array.pmf_private));
		for(int i=0;i<groups.length;i++){
			initExpandLayout(i, groups[i],""+i);
		}
		
		findViewById(R.id.btn_pmf_cls).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				for(int i=0;i<groups.length;i++){
					JackExpandableMutilineLayout jem = jemMap.get(i);
					if(null!=jem) jem.reset();
				}
				
			}
		});
		findViewById(R.id.btn_pmf_cfm).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent data = getIntent();
				Bundle bundle = new Bundle();
				for(int i=0;i<groups.length;i++){
					JackExpandableMutilineLayout jem = jemMap.get(i);
					if(null!=jem){
						bundle.putString(groups[i],jem.getSelectedIdStr());
					}
				}
				data.putExtras(bundle);
				setResult(RESULT_OK, data);
				finish();
			}
		});
	}
/**
 * @param i
 * @param title
 */
public void initExpandLayout(int i, String title,String idStr) {
	JackExpandableMutilineLayout jem  = new JackExpandableMutilineLayout(this);
	jem.setTitle(title);
	String[] itemStrs = itemMap.get(i);
	if(null==itemStrs) return;
	for(String itemContent:itemStrs){
		jem.addItem(itemContent, idStr!=null?idStr:itemContent);
	}
	container.addView(jem);
	jemMap.put(i, jem);
}

}
