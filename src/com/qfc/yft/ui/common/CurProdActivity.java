package com.qfc.yft.ui.common;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CurProdActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Button btn = new Button(this);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				share();
			}
		});
		setContentView(btn);
		btn.performClick();
	}
	
	
	
	private void share(){
		Intent intent=new Intent(Intent.ACTION_SEND); 
		intent.setType("text/plain"); 
		intent.putExtra(Intent.EXTRA_SUBJECT, "分享标题"); 
		intent.putExtra(Intent.EXTRA_TEXT, "来自网上轻纺城，这件产品不错：12345");  
//		intent.putExtra(Intent.EXTRA_TEXT, "来自网上轻纺城，这件产品不错："+cpProduct.getMainPic());  
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
		startActivity(Intent.createChooser(intent, getTitle())); 
	}
	
	/*private IWXAPI wxApi;
	//实例化
	wxApi = WXAPIFactory.createWXAPI(this, Constants.WX_APP_ID);
	wxApi.registerApp(Constants.WX_APP_ID);
	*//**
	 * 微信分享 （这里仅提供一个分享网页的示例，其它请参看官网示例代码）
	 * @param flag(0:分享到微信好友，1：分享到微信朋友圈)
	 *//*
	private void wechatShare(int flag){
	    WXWebpageObject webpage = new WXWebpageObject();
	    webpage.webpageUrl = 这里填写链接url;
	    WXMediaMessage msg = new WXMediaMessage(webpage);
	    msg.title = 这里填写标题;
	    msg.description = 这里填写内容;
	    //这里替换一张自己工程里的图片资源
	    Bitmap thumb = BitmapFactory.decodeResource(getResources(), R.drawable.share_logo);
	    msg.setThumbImage(thumb);
	     
	    SendMessageToWX.Req req = new SendMessageToWX.Req();
	    req.transaction = String.valueOf(System.currentTimeMillis());
	    req.message = msg;
	    req.scene = flag==0?SendMessageToWX.Req.WXSceneSession:SendMessageToWX.Req.WXSceneTimeline;
	    wxApi.sendReq(req);
	}
	//在需要分享的地方添加代码：
	wechatShare(0);//分享到微信好友
	wechatShare(1);//分享到微信朋友圈
*/}
