package com.qfc.yft.data;

public class URLFactory implements OpenApiConst {
	
	private String url;
	private String app_secret;
	private String app_key;
//	private String register_url;
	
	
	public URLFactory(boolean formal) {
		if(formal){
			url = _URL;
			app_secret = OPEN_API_APP_SECRET;
			app_key = OPEN_API_APP_KEY;
		}else{
			url = _URL0;
			app_secret = OPEN_API_APP_SECRET0;
			app_key = OPEN_API_APP_KEY0;
			
		}
			
	}
	
	public final String getUrl() {
		return url;
	}
	public final String getApp_secret() {
		return app_secret;
	}
	public final String getApp_key() {
		return app_key;
	}
	
	
	
}
