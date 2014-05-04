package com.qfc.yft.ui.account;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.qfc.yft.CimConsts;
import com.qfc.yft.R;
import com.qfc.yft.YftApplication;
import com.qfc.yft.YftData;
import com.qfc.yft.YftValues;
import com.qfc.yft.net.chat.Config;
import com.qfc.yft.net.chat.GIMSocketServer;
import com.qfc.yft.net.chat.IWSCallback;
import com.qfc.yft.net.chat.WSCaller;
import com.qfc.yft.ui.tabs.HubActivity;
import com.qfc.yft.utils.JackUtils;
import com.qfc.yft.vo.CimWSReturn;
import com.qfc.yft.vo.SystemParams;

public class ChatLoginHelper implements IWSCallback {
	public static String BADGE_ACTION = "com.ggwork.ui.BADGE_ACTION";//TODO ?
	
	private Context mContext;
	private static ChatLoginHelper helper;
	private ChatLoginHelper(){}
	public static ChatLoginHelper getInstance(){
		if(null==helper) helper = new ChatLoginHelper();
		helper.mContext = YftApplication.getApp();
		return helper;
	}
	
	
	private Handler splashHandler = new Handler() {
		public void handleMessage(Message msg) {

			switch (msg.what) {
			case 0:
				/*DataManager.getInstance(LoginActivity.this).setPassword(
						userPassword);*/
				if(null!=msg.obj){
					String userName = ((CimWSReturn)(msg.obj)).getUserName();
					YftData.data().setMyChatName(userName);
					createUserExists(userName);
				}
				JackUtils.showToast(YftApplication.getApp(), "纺织聊登录成功");//TODO 标志位？
				YftValues.FZL_RELOGIN=false;//0416
				registerBroadcastReceiver();//main
				mContext.startService(new Intent(mContext, GIMSocketServer.class));
//				goConversation();
//				stopProgressDialog();
				YftData.data().getConvListAdapter();//0422
				break;

			/*
				getVersion();
				showUpData();
				getGroup();
				getGuest();
				stopProgressDialog();*/
			default:
				String errorMsg="纺织聊登陆失败";
				if(null!=msg.obj){
					errorMsg = ((CimWSReturn)msg.obj).getMessage();
				}
				JackUtils.showToast(mContext, errorMsg);
				break;
			}

		}
	};
	
	public void logInChatGo(String loginId, String password) {
		JackUtils.showToast(YftApplication.getApp(), "纺织聊正在登录...");
		String encryptPassword = "";
		String passwordParam = "password";
//		if (Config.encryptType == 0) {
//			encryptPassword = MD5.toMD5(password);
//		} else if (Config.encryptType == 2) {
			encryptPassword = JackUtils.encryptionPassword(password);
			passwordParam = "newPassword";
//		} else {
//			encryptPassword = password;
//		}
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("loginId", loginId + Config.defaultSite);
		param.put(passwordParam, encryptPassword);
		param.put("clientType", 1);
		param.put("includeFriend", true);
		param.put("includeShop", true);
		WSCaller.call("login", param, this);

	}

	@Override
	public void callback(CimWSReturn wsReturn) {

		if (wsReturn == null) {
//			loginError = this.getString(R.string.login_error_net);
			Message m = splashHandler.obtainMessage(2);
			splashHandler.sendMessage(m);
		} else {
//			Log.i(TAG, "getCode:"+wsReturn.getCode());
			if (wsReturn.getCode() != 0) {
//				loginError = wsReturn.getMessage();
				Message m = splashHandler.obtainMessage(1);
				m.obj= wsReturn;
				splashHandler.sendMessage(m);
			} else {
				SystemParams sp = SystemParams.getInstance();
				sp.decodeFromLoginReturn(wsReturn);
				Message m = splashHandler.obtainMessage(0);//5 taotao
				m.obj = wsReturn;
				splashHandler.sendMessage(m);
			}

		}
	}
	private static void createUserExists(String loginId) {
		File f = null;
		try {

			f = new File(JackUtils.file_path + loginId);
			if (!f.exists()) {
				f.mkdir();
			}
			f = new File(JackUtils.file_path + loginId + "/images");
			if (!f.exists()) {
				f.mkdir();
			}
			f = new File(JackUtils.file_path + loginId + "/UserHead");
			if (!f.exists()) {
				f.mkdir();
			}

		} catch (Exception e) {
			e.printStackTrace();

		} finally {

		}

	}
	
	/**
	 * 注册广播
	 */
	private void registerBroadcastReceiver() {
		UIBadgeReceiver receiver = new UIBadgeReceiver();
		IntentFilter filter = new IntentFilter(BADGE_ACTION);
		mContext.registerReceiver(receiver, filter);
		YftApplication.getApp().setUIBadgeReceiver(receiver);
	}
	
	public static Notification notification;
public static PendingIntent pt;
	
	public static void showNotification(int icon, String tickertext,
			String title, String content) {
		notification = new Notification();
		notification.icon = icon;
		notification.tickerText = tickertext;
		pt = PendingIntent.getActivity(YftApplication.getApp(), 0, new Intent(YftApplication.getApp(),
				HubActivity.class), Intent.FLAG_ACTIVITY_CLEAR_TOP);
		notification.setLatestEventInfo(YftApplication.getApp(), title, content, pt);
		notification.flags = Notification.FLAG_NO_CLEAR;
		notification.defaults |= Notification.DEFAULT_LIGHTS;
		notification.number = 1;
//		if(null==nm)nm = (NotificationManager)YftApplication.getApp(). getSystemService(Context.NOTIFICATION_SERVICE);
		YftData.data().getNotificationManager().notify(YftValues.notification_id, notification);

	}
	public static class UIBadgeReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (BADGE_ACTION.equals(action)) {
				Bundle bundle = intent.getExtras();
				short type = bundle.getShort("type");
				int BADGE = bundle.getInt("BADGE");
				if (type == CimConsts.HandlerType.EPSERVER_OFFLONE) {
//					showOfflong(TabChatFragment.this);
					System.out.println("重新登录?");
//					JackUtils.showDialog(context, "您的纺织聊账号从异地登陆了!", null);
					YftValues.FZL_RELOGIN=true;//0416
				} else if (type == 0) {
					HubActivity.updateChatTabCount();//内部有循环 注意效率
				//TODO notification?
					if (!JackUtils.getDisplayClassName(context).startsWith(
							"com.qfc.yft")) { //getRunningTasks(1).get(0).topActivity

						if (notification != null) {
							notification.tickerText = "有"
									+ String.valueOf(BADGE) + "条新消息";
							notification.setLatestEventInfo(context,
									SystemParams.getInstance().getUserName(),
									"未读消息" + String.valueOf(BADGE) + "条", pt);
							notification.icon = R.drawable.ic_launcher;//notify_newmessage;
							YftData.data().getNotificationManager().notify(YftValues.notification_id, notification);
						} else {
							showNotification(R.drawable.ic_launcher, "有"
									+ String.valueOf(BADGE) + "条新消息",
									SystemParams.getInstance().getUserName(),
									"未读消息" + String.valueOf(BADGE) + "条");
						}
					}
//					System.out.println("有消息？");
				}

			}
		}
	}
}
