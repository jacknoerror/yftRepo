package com.qfc.yft.ui.gallery;

import com.qfc.yft.R;
import com.qfc.yft.data.Const;
import com.qfc.yft.data.NetConst;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public abstract class CompoundRadiosFragment extends JackAbsCompTitleFragment implements OnCheckedChangeListener{

	protected RadioGroup mRadioGroup;
	protected FrameLayout mFrame;
	
	@Override
	public int getLayoutRid() {
		return R.layout.fragment_gf_first;
	}
	
	@Override
	public void initView() {
		super.initView();
		initRadioGroup();
		initFrame();
		//to be continued
		
	}
	
	/**
	 */
	protected void initFrame() {
		mFrame = (FrameLayout) mView.findViewById(R.id.frame_al_sh);
	}
	
	/**
	 * 
	 */
	protected void initRadioGroup() {
		mRadioGroup = (RadioGroup)mView.findViewById(R.id.radiogroup_al_sh);
		mRadioGroup.setOnCheckedChangeListener(this);
	}
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch (checkedId) {
		case R.id.radio1:
			//go photo
			HowToPutIt(Const.BS_GO_PHOTO);
			break;
		case R.id.radio2:
			//go local
			HowToPutIt(Const.BS_GO_LOCAL);
			break;
		default:
			break;
		}
		//-1
	}

	/**
	 * @param bsgo
	 */
	protected void HowToPutIt(int bsgo) {
		Fragment fragment;
		Bundle bundle;
		String simpleName = getClass().getSimpleName();
		if(null==(fragment= mCompoundFragmentManager.findFragmentByTag(GFSecondLocal.class.getSimpleName()))) fragment = new GFUpload();
		bundle = new Bundle();
		bundle.putInt(NetConst.EXTRAS_UPLOADACTION, bsgo);
		fragment.setArguments(bundle);
		FragmentTransaction beginTransaction1 = mCompoundFragmentManager.beginTransaction();
		if(fragment.isAdded()) beginTransaction1.show(fragment);
		else beginTransaction1.add(R.id.frame_common, fragment, fragment.getClass().getSimpleName());
		if(simpleName.equals(GFFirst.class.getSimpleName()))beginTransaction1.hide(this).addToBackStack(simpleName);
		else if(simpleName.equals(GFGrids.class.getSimpleName()))beginTransaction1.detach(this);
		beginTransaction1.commit();
		mRadioGroup.clearCheck();
	}
}
