package com.qfc.yft.ui.account;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.qfc.yft.MyApplication;
import com.qfc.yft.R;
import com.qfc.yft.data.Const;
import com.qfc.yft.data.MyData;
import com.qfc.yft.data.chat.CimConsts;
import com.qfc.yft.net.chat.GIMSocketServer;
import com.qfc.yft.net.chat.IWSCallback;
import com.qfc.yft.net.chat.WSCaller;
import com.qfc.yft.ui.tabs.HubActivity;
import com.qfc.yft.util.JackUtils;
import com.qfc.yft.vo.chat.CimWSReturn;
import com.qfc.yft.vo.chat.SystemParams;

public class ChatLoginHelper implements IWSCallback {
	public static String BADGE_ACTION = "com.ggwork.ui.BADGE_ACTION";//filter name
	
	private Context mContext;
	private static ChatLoginHelper helper;
	private ChatLoginHelper(){}
	public static ChatLoginHelper getInstance(){
		if(null==helper) helper = new ChatLoginHelper();
		if(helper.mContext==null)helper.mContext = MyApplication.app();
		return helper;
	}
	
	/**
	 * test to solve broadcast exception
	 * @param context
	 */
	public void setContext(Context context){
		mContext = context;
	}
	
	private Handler splashHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				if(null!=msg.obj){
					String userName = ((CimWSReturn)(msg.obj)).getUserName();
//					MyData.data().setMyChatName(userName);
					createUserExists(userName);
				}
				JackUtils.showToast(MyApplication.app(), "纺织聊登录成功");//
				
//				Const.FZL_RELOGIN=false;//0416 //TODO 标志位 
				
				mContext.startService(new Intent(mContext, GIMSocketServer.class));
				
				registerBroadcastReceiver();// 不用注册？
				
				MyData.data().getConvListAdapter();//0422
				
				break;

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
		JackUtils.showToast(MyApplication.app(), "纺织聊正在登录...");
		String encryptPassword = "";
		String passwordParam = "password";
			encryptPassword = JackUtils.encryptionPassword(password);
			passwordParam = "newPassword";
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("loginId", loginId + Const.defaultSite);
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
		badgeReceiver = new UIBadgeReceiver();
		IntentFilter filter = new IntentFilter(BADGE_ACTION);
		mContext.registerReceiver(badgeReceiver, filter);
//		MyApplication.app().setUIBadgeReceiver(receiver);
	}
	
	public void unregisterReceiver(){
		if(null!=badgeReceiver&&mContext!=null)
			mContext.unregisterReceiver(badgeReceiver);
	}
	
	public static Notification notification;
	public static PendingIntent pt;
	
	public static void showNotification(int icon, String tickertext,
			String title, String content) {
		notification = new Notification();
		notification.icon = icon;
		notification.tickerText = tickertext;
		pt = PendingIntent.getActivity(MyApplication.app(), 0, new Intent(MyApplication.app(),
				HubActivity.class), Intent.FLAG_ACTIVITY_CLEAR_TOP);
		notification.setLatestEventInfo(MyApplication.app(), title, content, pt);
		notification.flags = Notification.FLAG_AUTO_CANCEL;
		notification.defaults |= Notification.DEFAULT_LIGHTS;
		notification.number = 1;
		 ChatLoginHelper.getInstance().getNotificationManager().notify(Const.notification_id, notification);

	}
	NotificationManager nm;
	public NotificationManager getNotificationManager() {
		if(null==nm)nm = (NotificationManager)MyApplication.app(). getSystemService(Context.NOTIFICATION_SERVICE);
		return nm;
	}
	
	static AlertDialog ad ;

	private UIBadgeReceiver badgeReceiver; 
	public static class UIBadgeReceiver extends BroadcastReceiver {

		
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (BADGE_ACTION.equals(action)) {
				Bundle bundle = intent.getExtras();
				short type = bundle.getShort("type");
				int BADGE = bundle.getInt("BADGE");
				if (type == CimConsts.HandlerType.EPSERVER_OFFLONE) {
					createOfflineDialog();
//					Const.FZL_RELOGIN=true;//0416 //TODO
				} else if (type == 0) {
					HubActivity.updateChatTabCount();//内部有循环 注意效率
				// notification?
					if (!JackUtils.getDisplayClassName(context).startsWith(
							"com.qfc.yft")) { //getRunningTasks(1).get(0).topActivity

						if (notification != null) {
							notification.tickerText = "有"
									+ String.valueOf(BADGE) + "条新消息";
							notification.setLatestEventInfo(context,
									SystemParams.getInstance().getUserName(),
									"未读消息" + String.valueOf(BADGE) + "条", pt);
							notification.icon = R.drawable.ic_launcher;//notify_newmessage;
							 ChatLoginHelper.getInstance().getNotificationManager().notify(Const.notification_id, notification);
						} else {
							showNotification(R.drawable.ic_launcher, "有"
									+ String.valueOf(BADGE) + "条新消息",
									SystemParams.getInstance().getUserName(),
									"未读消息" + String.valueOf(BADGE) + "条");
						}
					}
				}

			}
		}
		
		
		
		/**
		 * 
		 */
		private void createOfflineDialog() {
			if(null==ad||!ad.isShowing()){
				final Context context = MyData.data().getTabHost().getContext();
				AlertDialog.Builder builder = new Builder(context);
				builder.setMessage("您的纺织聊账号已在异地登陆，如要继续使用纺织聊功能，请重新登录易纺通账号");

				builder.setTitle("提示");
				DialogInterface.OnClickListener positiveListener = new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent intent = new Intent();
						intent.setClass(context, StartLoginActivity.class);
						intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						context.startActivity(intent);
//						Const.logout();//TODO
					}
				};
				if (null != positiveListener)
					builder.setPositiveButton("重新登录", positiveListener);// 0408

				builder.setNegativeButton("继续浏览", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
				ad = builder.show();
//				ad = JackUtils.showDialog(MyData.data().getHostTab().getContext(), "您的纺织聊账号从异地登陆了!", null);
			}
		}

	}
}
