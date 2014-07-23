package com.qfc.yft.wxapi;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.qfc.yft.MyApplication;
import com.qfc.yft.util.JackUtils;
import com.tencent.mm.sdk.openapi.BaseReq;
import com.tencent.mm.sdk.openapi.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler{
	// IWXAPI 是第三方app和微信通信的openapi接口
//    private IWXAPI api;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        api = WXAPIFactory.createWXAPI(this, Const.AppID, false);
//        api.handleIntent(getIntent(), this);
        super.onCreate(savedInstanceState);
        finish();
    }
    
    @Override
    public void onReq(BaseReq arg0) { 
    	Log.i("WXEntryActivity", "transaction"+arg0.transaction);
    	 finish();
    }
 
    @Override
    public void onResp(BaseResp resp) {
    	Log.i("WXEntryActivity", "resp");
//    	LogManager.show(TAG, resp.errCode: + resp.errCode + ,resp.errStr:
//            + resp.errStr, 1);
        switch (resp.errCode) {
        case BaseResp.ErrCode.ERR_OK:
            //分享成功
        	JackUtils.showToast(MyApplication.app(),"分享成功");
            break;
        case BaseResp.ErrCode.ERR_USER_CANCEL:
            //分享取消
            break;
        case BaseResp.ErrCode.ERR_AUTH_DENIED:
            //分享拒绝
            break;
        }
        finish();
    }
}
