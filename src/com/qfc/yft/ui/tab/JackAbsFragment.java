package com.qfc.yft.ui.tab;

import com.qfc.yft.ui.JackInitViewImpl;

import de.greenrobot.event.EventBus;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 *用于FragmentTabhost切换的Fragment顶级抽象类
 * @author taotao
 */
public abstract class JackAbsFragment extends Fragment implements JackInitViewImpl{
	protected final String TAG 	= getClass().getSimpleName();
	protected LayoutInflater mInflator;
	protected View mView;
	protected boolean eventBusRegisted;

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

	@Override
	public void onStop() {
		unregistEventBus();
		super.onStop();
	}

	/**
	 * 
	 */
	protected void registEventBus() {
		eventBusRegisted = true;
		EventBus.getDefault().register(this);
//		Log.i(TAG, "registEventBus");
	}

	/**
	 * 
	 */
	protected void unregistEventBus() {
		if(eventBusRegisted){
			EventBus.getDefault().unregister(this);//why not destroy?
			eventBusRegisted = false;
		}
	}
	
	
}
