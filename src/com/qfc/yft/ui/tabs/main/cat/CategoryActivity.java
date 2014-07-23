package com.qfc.yft.ui.tabs.main.cat;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.qfc.yft.R;
import com.qfc.yft.data.MyData;
import com.qfc.yft.data.NetConst;
import com.qfc.yft.ui.MyTitleActivity;
import com.qfc.yft.vo.Category;

public class CategoryActivity extends MyTitleActivity {
	private static final String STH_TO_DISTINCT = "jack";

	final String TAG = CategoryActivity.class.getSimpleName();
	
	ListView listView1,listView2;
	
	int scid;
	int curLeftIndex;
	Category thisCat;
	Category[] leftCats;
	SparseArray<List<String>> rightCatMap;
	
	
	@Override
	public int getLayoutRid() {
		return R.layout.activity_category;
	}
	@Override
	public void initView() {
		scid = getIntent().getIntExtra(NetConst.EXTRA_CATEGORY_ID, 0);
		thisCat = MyData.data().getCategorySecond(scid);
		if(null!=thisCat)titleManager.setTitleName(thisCat.getCateName());
		titleManager.initTitleBack();
		initViews();
	}




	private void initViews() {
		rightCatMap= new SparseArray<List<String>>();

		listView1 = (ListView)findViewById(R.id.list_category_1);
		listView2 = (ListView)findViewById(R.id.list_category_2);
		
		listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				selectLeft(position,view);
			}

		});
		
		listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				List<String> sl = rightCatMap.get(curLeftIndex);
				if(null!=sl){
					String keyword = sl.get(position);
					goSearchThis(keyword);
				}
			}

		});
		
		loadAllData();
		selectLeft(0,null);
	}
	
	/**
	 * @param keyword
	 */
	private void goSearchThis(String keyword) {
		if(keyword.isEmpty() ) return;
		if(keyword.contains(STH_TO_DISTINCT)) keyword = keyword.replace(STH_TO_DISTINCT, "");
		Intent intent = new Intent();
		intent.setClass(CategoryActivity.this, CateProdListActivity.class);
		intent.putExtra("keyword", keyword);
		startActivity(intent);
	}
	
	
	private void selectLeft(int position,View viewToSelect) {
		updateRightList(position);
		SetViewSelect(viewToSelect);
		curLeftIndex = position;
	}

	private void SetViewSelect(View viewToSelect){
		if(viewToSelect==null) return ;
		View viewSelected = (View)listView1.getTag();
		if(viewSelected!=null){
			viewSelected.setBackgroundResource(R.drawable.selector_field_white_grey);
		}
		viewToSelect.setBackgroundResource(R.drawable.selected_category);
		listView1.setTag(viewToSelect);
		
	}
	

	private void updateRightList(int position) {
		Adapter l2A = listView2.getAdapter();
		if(null==l2A) listView2.setAdapter(new CatSimAdapter(getRightData(position)));
		else ((CatSimAdapter)(l2A)).setList(getRightData(position));
		
		listView2.setSelection(0);
	}

	private void loadAllData() {
		Category sCat = MyData.data().getCategorySecond(scid);
		if(sCat==null||sCat.getChildren()==null) {//没有内容直接搜索二级分类
			if(null!=thisCat) {
				goSearchThis(thisCat.getCateName());
				finish();
			}
			return ;
		}
		leftCats = sCat.getChildren();
		List<String > leftNames=new ArrayList<String>(); 
		List<String > rightAll = new ArrayList<String>();
		for(int i=0;i<leftCats.length;i++){
			leftNames.add(leftCats[i].getCateName());
			Category[] rightChild = leftCats[i].getChildren();
			List<String> rightChildNames=new ArrayList<String>();
			rightChildNames.add(leftCats[i].getCateName()+STH_TO_DISTINCT);
			if(null!=rightChild){
				for(int j=0;j<rightChild.length;j++){
					rightChildNames.add(rightChild[j].getCateName());
					rightAll.add(rightChild[j].getCateName());
				}
			}
			rightCatMap.put(i, rightChildNames);
		}
		listView1.setAdapter(new CatSimAdapter(leftNames));
		
	}
	private List<String> getRightData(int index){
		return rightCatMap.get(index);
	}
	
	public class CatSimAdapter extends BaseAdapter{
		List<String> csStrs;//XXX 应该为Category
		SparseArray<View> views;
		
		public CatSimAdapter(List<String> strs){
			setList(strs);
			views = new SparseArray<View>();
		}
		
		@Override
		public int getCount() {
			
			return csStrs.size();
		}

		@Override
		public Object getItem(int position) {
			return csStrs.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;//
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {//kinda custom
			View view = views.get(position);
			if(null==view){
				view = View.inflate(CategoryActivity.this, R.layout.item_category, null);
				views.put(position, view);
				if(position==0){//ugly default selector
					SetViewSelect(view);
				}
			} 
			String jack = csStrs.get(position);//0310
			((TextView)view).setText(!jack.contains(STH_TO_DISTINCT)?csStrs.get(position):"全部");
			return view;
		}

		
		public void setList(List<String> strs){
			if(null==strs) strs = new ArrayList<String>();
			this.csStrs = strs;
			notifyDataSetChanged();
		}
	}
	
}
