package com.baidumap;

import android.content.Context;
import android.widget.Toast;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.MKGeneralListener;
import com.baidu.mapapi.map.MKEvent;
import com.qfc.yft.MyApplication;

public class BDMapIniter {
	public static BMapManager mBMapManager = null;
	public static boolean m_bKeyRight = true;
//	public static final String strKey = "0mWqe22O9Iv4hojLFe1Azmtm";//v1.1正式版
	public static final String strKey = "A228ee673a413a045b292d67593e791f";//debug.keystore

	public static void initEngineManager(Context context) {
        if (mBMapManager == null) {
            mBMapManager = new BMapManager(context);
        }

        if (!mBMapManager.init(strKey,new MyGeneralListener())) {
            Toast.makeText(MyApplication.app().getApplicationContext(), 
                    "BMapManager  初始化错误!", Toast.LENGTH_LONG).show();
        }
	}
	// 常用事件监听，用来处理通常的网络错误，授权验证错误等
    static class MyGeneralListener implements MKGeneralListener {
        
        @Override
        public void onGetNetworkState(int iError) {
            if (iError == MKEvent.ERROR_NETWORK_CONNECT) {
                Toast.makeText(MyApplication.app().getApplicationContext(), "您的网络出错啦！",
                    Toast.LENGTH_LONG).show();
            }
            else if (iError == MKEvent.ERROR_NETWORK_DATA) {
                Toast.makeText(MyApplication.app().getApplicationContext(), "输入正确的检索条件！",
                        Toast.LENGTH_LONG).show();
            }
            // ...
        }

        @Override
        public void onGetPermissionState(int iError) {
                //授权Key错误：
        	if (iError ==  MKEvent.ERROR_PERMISSION_DENIED) {
                Toast.makeText(MyApplication.app().getApplicationContext(), 
                        "百度地图授权Key有错误！", Toast.LENGTH_LONG).show();
               m_bKeyRight = false;
            }
        }
    }
}
