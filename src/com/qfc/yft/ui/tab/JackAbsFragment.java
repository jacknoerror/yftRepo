package com.qfc.yft.ui.tab;

import com.qfc.yft.ui.JackInitViewImpl;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 *����FragmentTabhost�л���Fragment����������
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
		inflate();//�����0,��ͣ 0404
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
