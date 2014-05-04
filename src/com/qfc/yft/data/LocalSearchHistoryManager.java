package com.qfc.yft.data;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.qfc.yft.YftApplication;
import com.qfc.yft.YftValues;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class LocalSearchHistoryManager {
	private static LocalSearchHistoryManager manager;
	private LocalSearchHistoryManager(){
		pref = YftApplication.getApp().getSharedPreferences(YftValues.PREF_LOCAL, Context.MODE_PRIVATE);
		map = new HashMap<String, List<String>>();
	};
	public static LocalSearchHistoryManager getInstance(){
		if(null==manager) manager = new LocalSearchHistoryManager();
		return manager;
	}
	
	private final String DIVIDER = "&:&";
	
	SharedPreferences pref;
	Editor edit;
	Map<String, List<String>> map;
	
	public Editor edit(){
		if(null==edit){
			edit = pref.edit();
		}
		return edit;
	}
	public void add(String word,String type){
		List<String> list = initList(type);
		if(!list.contains(word))list.add(word);
		edit().putString(type, convertListToString(list)).commit();
	}
	
	public List<String> getList(String type){
		return trimList(initList(type));
	}
	public void deleteAll(){
		edit().clear().commit();
		map.clear();
	}
	
	private List<String> initList(String type){
		List<String > list= map.get(type);
		if(null==list) {
			list = convertStringToList(type);
			map.put(type,  list);
		}
		return list;
	}
	private List<String> trimList(List<String> list){
		while(list.size()>10){
			list.remove(0);
		}
		return list;
	}
	private List<String> convertStringToList(String type) {
		List<String> list;
		list = new LinkedList<String>();
		String resultStr = pref.getString(type, "");
		if(!resultStr.isEmpty()){
			for(String s:resultStr.split(DIVIDER)){
				list.add(s);
			}
		}
		return list;
	}
	private String convertListToString(List<String> list){
		String result="";
		for(String s:list){
			result+=s+DIVIDER;
		}
		return result;
	}
}
