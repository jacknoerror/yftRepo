package com.qfc.yft.ui.tabs.main.cat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.qfc.yft.R;
import com.qfc.yft.data.NetConst;

public class CategoryAndSearchActivity extends FragmentActivity {
	public static final int FRAME_ID1 = R.id.frame_catsch_1,
							FRAME_ID2 = R.id.frame_catsch_2;
	private FragmentManager frMana;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_cat_sch);
		/*FrameLayout frame1,frame2;
		frame2 = (FrameLayout) this.findViewById(FRAME_ID2);
		frame1 = (FrameLayout) this.findViewById(FRAME_ID1);*/
		
		Intent intent = getIntent();
		int a = intent.getIntExtra(NetConst.EXTRAS_CATEPAGE, 0); 
		int b = intent.getIntExtra(NetConst.EXTRAS_SEARCH_TYPE_INT, 0); 
		String c = intent.getStringExtra(NetConst.EXTRAS_KEYWORD   ); 
		
		frMana = getSupportFragmentManager();
		
		CatAbsContentFragment fcc = new CatEgoryFrag();
		CatAbsContentFragment fss = new CatSearchFrag();
		CatAbsContentFragment ftb = new CatTopBarFrag();
		Bundle args = new Bundle();
		args.putInt(NetConst.EXTRAS_SEARCH_TYPE_INT, b);
		args.putString(NetConst.EXTRAS_KEYWORD, c);
		fss.setArguments(args);
		frMana.beginTransaction()
			.add(FRAME_ID1,ftb, CatTopBarFrag.class.getSimpleName())
			.add(FRAME_ID2,fss, CatSearchFrag.class.getSimpleName()).hide(fss)
			.add(FRAME_ID2,fcc, CatEgoryFrag.class.getSimpleName()).hide(fcc)
			.commit();
		
		(a==0?(frMana.beginTransaction().show(fcc)):(frMana.beginTransaction().show(fss))).commit();
	}


	
}
