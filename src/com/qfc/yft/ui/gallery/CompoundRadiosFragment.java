package com.qfc.yft.ui.gallery;

import com.qfc.yft.R;
import com.qfc.yft.ui.MyPortal;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public abstract class CompoundRadiosFragment extends JackAbsCompoundFragment implements OnCheckedChangeListener{

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
		Fragment fragment=null;
		switch (checkedId) {
		case R.id.radio1:
			//go photo
//			MyPortal.goUploadPicByCamera(getActivity());
			if(null==(fragment= mCompoundFragmentManager.findFragmentByTag(GFUpload.class.getSimpleName()))) fragment = new GFUpload();
			mCompoundFragmentManager.beginTransaction().replace(R.id.frame_common, fragment, GFUpload.class.getSimpleName()).addToBackStack(GFFirst.class.getSimpleName()).commit();
			MyPortal.goCamera(getActivity());
			mRadioGroup.clearCheck();
			break;
		case R.id.radio2:
			//go local
			if(null==(fragment= mCompoundFragmentManager.findFragmentByTag(GFSecondLocal.class.getSimpleName()))) fragment = new GFSecondLocal();
			mCompoundFragmentManager.beginTransaction().replace(R.id.frame_common, fragment, GFSecondLocal.class.getSimpleName()).addToBackStack(GFFirst.class.getSimpleName()).commit();
			mRadioGroup.clearCheck();
			break;
		default:
			break;
		}
		//-1
	}
}
