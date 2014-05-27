package com.qfc.yft.wxapi;


import java.util.logging.LogManager;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.qfc.yft.data.Const;
import com.tencent.mm.sdk.openapi.BaseReq;
import com.tencent.mm.sdk.openapi.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler{
	// IWXAPI �ǵ�����app��΢��ͨ�ŵ�openapi�ӿ�
//    private IWXAPI api;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        api = WXAPIFactory.createWXAPI(this, Const.AppID, false);
//        api.handleIntent(getIntent(), this);
        super.onCreate(savedInstanceState);
    }
    @Override
    public void onReq(BaseReq arg0) { 
    	Log.i("WXEntryActivity", "req");
    }
 
    @Override
    public void onResp(BaseResp resp) {
    	Log.i("WXEntryActivity", "resp");
//    	LogManager.show(TAG, resp.errCode: + resp.errCode + ,resp.errStr:
//            + resp.errStr, 1);
        switch (resp.errCode) {
        case BaseResp.ErrCode.ERR_OK:
            //����ɹ�
            break;
        case BaseResp.ErrCode.ERR_USER_CANCEL:
            //����ȡ��
            break;
        case BaseResp.ErrCode.ERR_AUTH_DENIED:
            //����ܾ�
            break;
        }
    }
}
