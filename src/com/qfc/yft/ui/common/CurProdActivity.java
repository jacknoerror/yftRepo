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
		intent.putExtra(Intent.EXTRA_SUBJECT, "�������"); 
		intent.putExtra(Intent.EXTRA_TEXT, "����������ĳǣ������Ʒ����12345");  
//		intent.putExtra(Intent.EXTRA_TEXT, "����������ĳǣ������Ʒ����"+cpProduct.getMainPic());  
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
		startActivity(Intent.createChooser(intent, getTitle())); 
	}
	
	/*private IWXAPI wxApi;
	//ʵ����
	wxApi = WXAPIFactory.createWXAPI(this, Constants.WX_APP_ID);
	wxApi.registerApp(Constants.WX_APP_ID);
	*//**
	 * ΢�ŷ��� ��������ṩһ��������ҳ��ʾ����������ο�����ʾ�����룩
	 * @param flag(0:����΢�ź��ѣ�1������΢������Ȧ)
	 *//*
	private void wechatShare(int flag){
	    WXWebpageObject webpage = new WXWebpageObject();
	    webpage.webpageUrl = ������д����url;
	    WXMediaMessage msg = new WXMediaMessage(webpage);
	    msg.title = ������д����;
	    msg.description = ������д����;
	    //�����滻һ���Լ��������ͼƬ��Դ
	    Bitmap thumb = BitmapFactory.decodeResource(getResources(), R.drawable.share_logo);
	    msg.setThumbImage(thumb);
	     
	    SendMessageToWX.Req req = new SendMessageToWX.Req();
	    req.transaction = String.valueOf(System.currentTimeMillis());
	    req.message = msg;
	    req.scene = flag==0?SendMessageToWX.Req.WXSceneSession:SendMessageToWX.Req.WXSceneTimeline;
	    wxApi.sendReq(req);
	}
	//����Ҫ����ĵط���Ӵ��룺
	wechatShare(0);//����΢�ź���
	wechatShare(1);//����΢������Ȧ
*/}
