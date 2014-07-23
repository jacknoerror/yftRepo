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
//	public static final String strKey = "0mWqe22O9Iv4hojLFe1Azmtm";//v1.1��ʽ��
	public static final String strKey = "A228ee673a413a045b292d67593e791f";//debug.keystore

	public static void initEngineManager(Context context) {
        if (mBMapManager == null) {
            mBMapManager = new BMapManager(context);
        }

        if (!mBMapManager.init(strKey,new MyGeneralListener())) {
            Toast.makeText(MyApplication.app().getApplicationContext(), 
                    "BMapManager  ��ʼ������!", Toast.LENGTH_LONG).show();
        }
	}
	// �����¼���������������ͨ�������������Ȩ��֤�����
    static class MyGeneralListener implements MKGeneralListener {
        
        @Override
        public void onGetNetworkState(int iError) {
            if (iError == MKEvent.ERROR_NETWORK_CONNECT) {
                Toast.makeText(MyApplication.app().getApplicationContext(), "���������������",
                    Toast.LENGTH_LONG).show();
            }
            else if (iError == MKEvent.ERROR_NETWORK_DATA) {
                Toast.makeText(MyApplication.app().getApplicationContext(), "������ȷ�ļ���������",
                        Toast.LENGTH_LONG).show();
            }
            // ...
        }

        @Override
        public void onGetPermissionState(int iError) {
                //��ȨKey����
        	if (iError ==  MKEvent.ERROR_PERMISSION_DENIED) {
                Toast.makeText(MyApplication.app().getApplicationContext(), 
                        "�ٶȵ�ͼ��ȨKey�д���", Toast.LENGTH_LONG).show();
               m_bKeyRight = false;
            }
        }
    }
}
