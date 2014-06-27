package com.qfc.yft.ui.tab.main.cat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.qfc.yft.R;
import com.qfc.yft.data.Const;
import com.qfc.yft.data.MyData;
import com.qfc.yft.data.NetConst;
import com.qfc.yft.net.action.ActionBuilder;
import com.qfc.yft.net.action.ActionReceiverImpl;
import com.qfc.yft.net.action.ActionRequestImpl;
import com.qfc.yft.net.action.product.FindAllWithJSONRcv;
import com.qfc.yft.net.action.product.FindAllWithJSONReq;
import com.qfc.yft.util.JackUtils;

public class CatEgoryFrag extends CatAbsContentFragment {

	final int[] ICS = new int[] { R.drawable.category_fabric,
			R.drawable.category_pb, R.drawable.category_jf,
			R.drawable.category_fz, R.drawable.category_fs,
			R.drawable.category_fl, R.drawable.category_ssx,
			R.drawable.category_fjpj, R.drawable.category_hgzj,
			R.drawable.category_fzyl, R.drawable.category_xbdpj,
			R.drawable.category_pgzp, R.drawable.category_fzgyp,
			R.drawable.category_fzkcp, R.drawable.category_fzfl,
			R.drawable.category_qt };
	final int[] CID = new int[] { 90, 91, 92, 45, 138, 109, 34, 120, 130, 1,
			137, 136, 110, 114, 115, 135 };
	final String[] TTS = new String[] { "面料", "坯布", "家纺", "服装", "服饰", "辅料",
			"纱丝线", "纺机配件", "化工助剂", "纺织原料", "箱包袋皮具", "皮革制品", "纺织工艺品", "纺织库存品",
			"纺织废料", "其他" };

	TableLayout mTable;
	private CatTopBarFrag topFrag;

	@Override
	public void onResume() {
		super.onResume();
		if (!MyData.data().isCatInited()) {
			ActionRequestImpl actReq = new FindAllWithJSONReq();
			ActionReceiverImpl actRcv = new FindAllWithJSONRcv(getActivity());
			ActionBuilder.getInstance().request(actReq, actRcv);
		}

	}

	
	
	@Override
	public void onHiddenChanged(boolean hidden) {
		super.onHiddenChanged(hidden);
		if(!hidden){
			configTop();
		}else{
			if(null!=topFrag)topFrag.getEdit().setOnClickListener(null);
		}
	}

	/**
	 * 
	 */
	public void configTop() {
		if (null == topFrag)return;
		View btnSearch = topFrag.getBtnSearch();
			btnSearch.setVisibility(View.GONE);
			EditText edit = topFrag.getEdit();
			edit.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					Fragment f = fraMana.findFragmentByTag(CatSearchFrag.class.getSimpleName());
					if (null == f) return;
					fraMana.beginTransaction().hide(CatEgoryFrag.this).show(f).commit();
				}
			});
			edit.setCursorVisible(false);
//		configTop();
	}

	@Override
	public int getLayoutRid() {
		return R.layout.fragment_firstcate;
	}

	@Override
	public void initView() {
		super.initView();
		topFrag = (CatTopBarFrag) fraMana.findFragmentByTag(CatTopBarFrag.class
				.getSimpleName());
		mTable = (TableLayout) mView.findViewById(R.id.table_home);
		// mTable.setStretchAllColumns(true); //helpless
		mTable.setShrinkAllColumns(true);
		// giveDividers(mTable, true); //有bug ，见addEndDivider方法
		TableRow tr = null;
		for (int i = 0; i < ICS.length; i++) {
			if (i % 3 == 0) {
				tr = new TableRow(getActivity());
				giveDividers(tr, false);
				if (i != 0)
					mTable.addView(mInflator.inflate(R.layout.view_divider_h,
							null));
				mTable.addView(tr);
				addEndDivider(i, tr);
			}

			tr.addView(getSingleGrid(i));
		}
	}

	@SuppressLint("NewApi")
	private void giveDividers(LinearLayout layout, boolean isHorizontal) {

		try {

			int rdh = R.drawable.divider_h, rdv = R.drawable.divider_v;
			if (JackUtils.getApiLvl() > 10) {
				layout.setDividerDrawable(getResources().getDrawable(
						isHorizontal ? rdh : rdv));
			} else {
			}
			if (JackUtils.getApiLvl() > 10)
				layout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
		} catch (NoSuchMethodError e) {
			Log.e(TAG, "new api --dividers");
		}
	}

	private View getSingleGrid(int index) {
		// init
		View view = mInflator.inflate(R.layout.layout_homegrid, null);
		ImageView img = (ImageView) view.findViewById(R.id.img_homegrid);
		TextView text = (TextView) view.findViewById(R.id.tv_homegrid);
		img.setImageResource(ICS[index]);
		img.setScaleType(ScaleType.FIT_CENTER);
		text.setText(TTS[index]);
		// listener
		view.setTag(CID[index]);
		view.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Integer index = (Integer) v.getTag();
				Intent intent = new Intent();
				intent.putExtra(NetConst.EXTRA_CATEGORY_ID, index);
				intent.setClass(getActivity(), CategoryActivity.class);
				startActivity(intent);
//				startActivityForResult(intent, Const.AR_CATEGORY);

			}
		});
		return view;
	}

	@SuppressLint("NewApi")
	private void addEndDivider(int i, View row) {
		// 解决表格末尾在中间时最后一条竖线不显示的bug
		if (i == ICS.length - 1 && i % 3 < 3) { // 是最后一个且不是行末
			try {
				if (JackUtils.getApiLvl() > 10)
					((LinearLayout) row)
							.setShowDividers(LinearLayout.SHOW_DIVIDER_END);
			} catch (Exception e) {
			}
		}
	}
}
