package com.qfc.yft.entity.offline;

import android.content.Context;
import android.content.SharedPreferences;

import com.qfc.yft.R;

public class OfflineDataKeeper {

	private static final String PREF_OFFLINE_BASE_ = ".offline";
	
	private static SharedPreferences offlinePref ;
	
	public static int setCurrentUserOfflinePreference(Context context,int shopId){
		offlinePref= context.getSharedPreferences(PREF_OFFLINE_BASE_+shopId, Context.MODE_PRIVATE);
		return 0;
	}
}
