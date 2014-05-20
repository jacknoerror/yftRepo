package com.qfc.yft;

import java.io.File;

import android.app.Application;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.MKGeneralListener;
import com.baidu.mapapi.map.MKEvent;
import com.qfc.yft.ui.account.ChatLoginHelper.UIBadgeReceiver;
import com.qfc.yft.ui.tabs.chat.FaceConversionUtil;
import com.qfc.yft.utils.JackUtils;
import com.umeng.analytics.MobclickAgent;



public class YftApplication extends Application {
	
	private static YftApplication yApp;
	
	public boolean m_bKeyRight = true;
    public BMapManager mBMapManager = null;
	
	public static YftApplication getApp(){
		return yApp;
	}
	
	
	//测试用key
	public static final String strKey = "A228ee673a413a045b292d67593e791f";
	//正式版本key
//	public static final String strKey = "nAzNjjOTAtlmz5uYRdtISNzp";
	
	
	@Override
	public void onCreate() {
		super.onCreate();
		yApp = this;
		initScreenData();
		initEngineManager(this);
//		InfoHouse.getApp().setApplication(this);
		new Thread(){//0211
			@Override
			public void run() {
				FaceConversionUtil.getInstace().calec();
				FaceConversionUtil.getInstace().getFileText(YftApplication.this);
//				JackUtils.createExists();
				File f = null;
				try {
					if (f == null) {
						f = new File(JackUtils.file_path);
					}
					if (!f.exists()) {
						f.mkdir();

					}

					f = new File(JackUtils.log_path);
					if (!f.exists()) {
						f.mkdir();

					}
				} catch (Exception e) {
					e.printStackTrace();

				} finally {

				}
				Log.i("myApplication", "face thread done"); 
			};
			
		}.start();
		
		MobclickAgent.updateOnlineConfig( this );
	}
	
	
	public void initEngineManager(Context context) {
        if (mBMapManager == null) {
            mBMapManager = new BMapManager(context);
        }

        if (!mBMapManager.init(strKey,new MyGeneralListener())) {
            Toast.makeText(YftApplication.getApp().getApplicationContext(), 
                    "BMapManager  初始化错误!", Toast.LENGTH_LONG).show();
        }
	}
	
	private void initScreenData(){
		DisplayMetrics dm = getResources().getDisplayMetrics();
		YftValues.SCREEN_WIDTH = dm.widthPixels;
		YftValues.SCREEN_HEIGHT= dm.heightPixels;
		Log.i("yft_app", dm.densityDpi+":dpi+=+desi:"+dm.density);
	}
	
	
	// 常用事件监听，用来处理通常的网络错误，授权验证错误等
    static class MyGeneralListener implements MKGeneralListener {
        
        @Override
        public void onGetNetworkState(int iError) {
            if (iError == MKEvent.ERROR_NETWORK_CONNECT) {
                Toast.makeText(YftApplication.getApp().getApplicationContext(), "您的网络出错啦！",
                    Toast.LENGTH_LONG).show();
            }
            else if (iError == MKEvent.ERROR_NETWORK_DATA) {
                Toast.makeText(YftApplication.getApp().getApplicationContext(), "输入正确的检索条件！",
                        Toast.LENGTH_LONG).show();
            }
            // ...
        }

        @Override
        public void onGetPermissionState(int iError) {
                //授权Key错误：
        	if (iError ==  MKEvent.ERROR_PERMISSION_DENIED) {
                Toast.makeText(YftApplication.getApp().getApplicationContext(), 
                        "百度地图授权Key有错误！", Toast.LENGTH_LONG).show();
                YftApplication.getApp().m_bKeyRight = false;
            }
        }
    }
    
    //fzl
    UIBadgeReceiver receiver;
    public void setUIBadgeReceiver(UIBadgeReceiver receiver){
    	this.receiver = receiver;
    }
    public UIBadgeReceiver getUIBadgeReceiver(){
    	return receiver;
    }
}
