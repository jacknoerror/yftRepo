package com.qfc.yft.ui.common;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.SyncStateContract.Constants;
import android.view.View;
import android.widget.Button;

import com.qfc.yft.R;
import com.qfc.yft.data.Const;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.SendMessageToWX;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.mm.sdk.openapi.WXMediaMessage;
import com.tencent.mm.sdk.openapi.WXWebpageObject;

public class CurProdActivity extends Activity {
	private IWXAPI wxApi;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// ʵ����
		initWxApi();
		
		Button btn = new Button(this);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				share();
				wechatShare(1);
			}
		});
		btn.setText("btn");
		setContentView(btn);
		
		
		btn.performClick();

		
	}

	/**
	 * 
	 */
	private void initWxApi() {
		wxApi = WXAPIFactory.createWXAPI(this, Const.AppID,true);
		wxApi.registerApp(Const.AppID);
	}

	/*private void share() {
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("text/plain");
		intent.putExtra(Intent.EXTRA_SUBJECT, "�������");
		intent.putExtra(Intent.EXTRA_TEXT, "����������ĳǣ������Ʒ����12345");
		// intent.putExtra(Intent.EXTRA_TEXT,
		// "����������ĳǣ������Ʒ����"+cpProduct.getMainPic());
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(Intent.createChooser(intent, getTitle()));
	}*/


	/**
	 * ΢�ŷ��� ��������ṩһ��������ҳ��ʾ����������ο�����ʾ�����룩
	 * 
	 * @param flag(0:����΢�ź��ѣ�1������΢������Ȧ)
	 */
	private void wechatShare(int flag) {
		WXWebpageObject webpage = new WXWebpageObject();
		webpage.webpageUrl = "www.baidu.com";
		WXMediaMessage msg = new WXMediaMessage(webpage);
		msg.title = "������д����";
		msg.description = " ������д����";
		// �����滻һ���Լ��������ͼƬ��Դ
		Bitmap thumb = BitmapFactory.decodeResource(getResources(),
				R.drawable.ic_launcher);
		msg.setThumbImage(thumb);

		SendMessageToWX.Req req = new SendMessageToWX.Req();
		req.transaction = String.valueOf(System.currentTimeMillis());
		req.message = msg;
		req.scene = flag == 0 ? SendMessageToWX.Req.WXSceneSession
				: SendMessageToWX.Req.WXSceneTimeline;
		wxApi.sendReq(req);
	}
	// ����Ҫ����ĵط���Ӵ��룺
	/*
	 * wechatShare(0);//����΢�ź��� wechatShare(1);//����΢������Ȧ
	 */
}
