package com.qfc.yft.ui.tabs;

import android.annotation.SuppressLint;
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
import com.qfc.yft.YftData;

public class TabMainFragment extends ContentAbstractFragment implements View.OnClickListener{
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
	final String[] TTS=new String[]{
			"面料","坯布","家纺","服装",
			"服饰","辅料","纱丝线","纺机配件",
			"化工助剂","纺织原料","箱包袋皮具",
			"皮革制品","纺织工艺品","纺织库存品",
			"纺织废料","其他"};
	
	
	TableLayout mTable;
	
	
	
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
				YftData.data().getFtHost().setCurrentTab(1);//exception
			}
		});
	}
	
	@SuppressLint("NewApi")
	private void addEndDivider(int i,View row) {
		// 解决表格末尾在中间时最后一条竖线不显示的bug
		if(i==ICS.length-1&&i%3<3){ //是最后一个且不是行末
			((LinearLayout)row).setShowDividers(LinearLayout.SHOW_DIVIDER_END);
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
		view.setTag(index);
		view.setOnClickListener(this);
		return view;
	}
	
	@SuppressLint("NewApi")
	private void giveDividers(LinearLayout layout, boolean isHorizontal){
		try{
			
			int rdh=R.drawable.divider_h,rdv=R.drawable.divider_v;
			layout.setDividerDrawable(getResources().getDrawable(isHorizontal?rdh:rdv));
			layout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
		}catch(Exception e){
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
		switch (index) {
		case 0:
			//TODO
			
			break;

		default:
			break;
		}
	}
}
