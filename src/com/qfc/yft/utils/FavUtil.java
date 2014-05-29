package com.qfc.yft.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;

import com.qfc.yft.R;
import com.qfc.yft.YftApplication;
import com.qfc.yft.YftData;
import com.qfc.yft.YftValues;
import com.qfc.yft.entity.Company;
import com.qfc.yft.entity.SimpleShopInfo;
import com.qfc.yft.entity.User;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class FavUtil {
	static final String PREF_HEART=".heart2";
	
	public static final int FAV_SUCCESS=0;
	public static final int FAV_FAILED=1;
	public static final int FAV_ALREADY=2;
	
	static SharedPreferences fPref;
	static Editor fEditor;
	
	private static SharedPreferences fp(){
		if(fPref==null){
			fPref  = YftApplication.app().getSharedPreferences(PREF_HEART, Context.MODE_PRIVATE) ;
		}
		return fPref;
	}
	private static Editor fe(){
		return fp().edit();
	}
	
	public static int addFav(String shopIdStr, Company company){
		String find = findFav(shopIdStr);
		if(!find.isEmpty()) return FAV_ALREADY;
		boolean result = fe().putString(shopIdStr, getFavedStr(shopIdStr, company)).commit();
		return result?FAV_SUCCESS:FAV_FAILED;
	}
	
	
	public static void delFavEdit(String fid){
		fe().remove(fid);
	}
	public static int delFavCommit(){
		return fe().commit()?FAV_SUCCESS:FAV_FAILED;
	}
	public static String findFav(String fid){
		String fav= fp().getString(fid, "");
		return fav;
	}
	
	public static int getCount(){
		return fp().getAll().size();
	}
	
	public static List<SimpleShopInfo> getFavList(){ 
		Set<String> kSet = fp().getAll().keySet();
		List<SimpleShopInfo> list = new ArrayList<SimpleShopInfo>();
		for(String key:kSet){
			String jStr = fp().getString(key, "");
			try {
				JSONObject job = new JSONObject(jStr);
				SimpleShopInfo hic = new SimpleShopInfo();
				hic.shopId = job.getInt(YftValues.FAV_PID);
				if(isMeShop(hic.shopId)) continue;
				hic.shopName = job.getString(YftValues.FAV_PNAME);
				hic.shopLogoImage = job.getString(YftValues.FAV_PPIC);
				hic.compIntro = job.getString(YftValues.FAV_PINTRO);
				hic.hasMotion = job.getInt(YftValues.FAV_MOTION)==3;
				list.add(hic);
			} catch (JSONException e) {
//				Log.i(TAG, "getdata json wrong --pref");
				System.out.println("getdata json wrong --pref");
				e.printStackTrace();
			}
		}
		return list;
	}
	private static boolean isMeShop(int shopId){
		User user = YftData.data().getMe();
		if(user==null) return false;
		return user.getShopId()==shopId;
	}
	private static String getFavedStr(String shopId, Company aiCompany)  {
			JSONObject job = new JSONObject();
			try {
				job.put(YftValues.FAV_PID, shopId);//aiCompany.getMemberId());
				job.put(YftValues.FAV_PNAME, aiCompany.getCompName());
				job.put(YftValues.FAV_PPIC, Company.getSinglePicPath(aiCompany.getShopLogoImage()));//aiCompany.getCompPicName());
				job.put(YftValues.FAV_PINTRO, aiCompany.getCompIntro());
				job.put(YftValues.FAV_MOTION, aiCompany.getMemberType());//1112
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return job.toString();
	}
}
