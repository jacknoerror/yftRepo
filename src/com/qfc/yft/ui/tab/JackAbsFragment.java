package com.qfc.yft.ui.tab;

import com.qfc.yft.ui.JackInitViewImpl;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 *用于FragmentTabhost切换的Fragment顶级抽象类
 * @author taotao
 */
public abstract class JackAbsFragment extends Fragment implements JackInitViewImpl{
	protected LayoutInflater mInflator;
	protected View mView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mInflator= inflater;
//		if(!inflate()) return null;
		inflate();//如果是0,别停 0404
		initView( );
		return mView;
	}

	private boolean inflate(){
		int rid = getLayoutRid();
		if(rid>0){
			mView =  mInflator.inflate(getLayoutRid(), null);
		}
		return rid>0;
	}
	
	
}
