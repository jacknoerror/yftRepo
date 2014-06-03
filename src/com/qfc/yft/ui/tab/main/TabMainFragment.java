package com.qfc.yft.ui.tab.main;

import android.view.View;

import com.qfc.yft.R;
import com.qfc.yft.ui.tab.ContentAbstractFragment;

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
		switch (v.getId()) {
		case R.id.et_home:
			//go search
			break;
		case R.id.tvbtn_a1:
			
			break;
		case R.id.tvbtn_c1:
			
			break;
		default:
			break;
		}
		
	}

}
