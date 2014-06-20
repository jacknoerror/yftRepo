package com.qfc.yft.ui.tab.main.cat;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;

import com.qfc.yft.R;

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
		
		int a = getIntent().getIntExtra("page", 0);//TODO
		
		frMana = getSupportFragmentManager();
		
		CatAbsContentFragment fss = new CatSearchFrag();
		CatAbsContentFragment ftb = new CatTopBarFrag();
		frMana.beginTransaction()
			.add(FRAME_ID1,ftb, CatTopBarFrag.class.getSimpleName())
			.add(FRAME_ID2,fss, CatSearchFrag.class.getSimpleName())
			.commit();
	}
	
	
}
