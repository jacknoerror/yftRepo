package com.qfc.yft.data;

public class MyData {
	private static MyData mydata;
	private MyData(){}
	public static MyData data(){
	   if(null==mydata){
	      mydata = new MyData();
	   }
	   return mydata;
	}

	
	
}
