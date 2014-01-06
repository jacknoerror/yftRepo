 package com.qfc.yft.ui.tabs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

import com.qfc.yft.R;
import com.qfc.yft.YftData;

public class HubActivity extends FragmentActivity {
	final String TAG = HubActivity.class.getName();
	final int[]	ICONS = new int[]{R.drawable.selector_tab_home ,
								R.drawable.selector_tab_search ,
								R.drawable.selector_tab_chat ,
								R.drawable.selector_tab_person  };
	final String[] TITLES= new String[]{"首页","搜索","纺织聊","个人中心"};
	final Class[] CLAZZZ=new Class[]{
			TabMainFragment.class,
			TabSearchFragment.class,
			TabChatFragment.class,
			TabPersonFragment.class
	};
	
    private FragmentTabHost mTabHost;

    @Override
    protected void onDestroy() {
    	mTabHost =null;//
    	super.onDestroy();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_tabs);
        mTabHost = (FragmentTabHost)findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);
        
        for(int i=0;i<4;i++){
        	addTab(i);
        }
        YftData.data().setFtHost(mTabHost);
    }
    
    private void addTab(int index){
    	if(index>=TITLES.length||index>=ICONS.length) return;
    	View view = LayoutInflater.from(this).inflate(R.layout.layout_tab, null);
    	ImageView img = (ImageView)view.findViewById(R.id.img_tab);
    	TextView text = (TextView)view.findViewById(R.id.tv_tab);
    	text.setTextColor(getResources().getColorStateList(R.color.selector_tab_textcolor));
//    	text.setCompoundDrawablesWithIntrinsicBounds(0, ICONS[index], 0, 0);
    	img.setImageResource(ICONS[index]);
    	img.setScaleType(ScaleType.CENTER_INSIDE);
    	text.setText(TITLES[index]);
    	mTabHost.addTab(mTabHost.newTabSpec(TITLES[index]).setIndicator(view),
               CLAZZZ[index], null);
//    	mTabHost.addTab(mTabHost.newTabSpec(TITLES[index]).setIndicator(
//    			TITLES[index],getResources().getDrawable(ICONS[index])),CLAZZZ[index],null);
    }
}
