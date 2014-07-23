package com.qfc.yft.net.chat;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Vibrator;
import android.util.Log;

import com.ggwork.net.socket.CimSocket;
import com.ggwork.net.socket.SocketBuild;
import com.ggwork.net.socket.SocketProtocol;
import com.ggwork.net.socket.message.CimAbstractMessage;
import com.ggwork.net.socket.message.CimChatMessage;
import com.ggwork.net.socket.message.CimLoginMessage;
import com.ggwork.net.socket.message.CimMessageListener;
import com.ggwork.net.socket.message.CimQueryStatusMessage;
import com.ggwork.net.socket.message.SaturationMessage;
import com.qfc.yft.MyApplication;
import com.qfc.yft.data.chat.CachMsg;
import com.qfc.yft.data.chat.CimConsts;
import com.qfc.yft.ui.account.ChatLoginHelper;
import com.qfc.yft.ui.tabs.chat.AllAdapterControl;
import com.qfc.yft.ui.tabs.chat.BuildData;
import com.qfc.yft.ui.tabs.chat.ChatMsgAdapter;
import com.qfc.yft.ui.tabs.chat.ChatMsgEntity;
import com.qfc.yft.util.JackUtils;
import com.qfc.yft.vo.chat.SystemParams;

public class GIMSocketServer extends Service implements CimMessageListener {
	private int recCount = 0;
	private int sendCount = 0;
	private Timer timer = null;
	private Intent timeIntent = null;
	private Bundle bundle = null;
	private SharedPreferences sp = null;
	private MediaPlayer mediaPlayer = null;
	protected Vibrator vibrator = null;
	private String guestMess[];

	@Override
	public IBinder onBind(Intent arg0) {
		return new ServiceBinder();

	}

	public void onCreate() {
		timeIntent = new Intent();
		bundle = new Bundle();
		if (sp == null) {
			sp = getSharedPreferences("com.fzl_preferences", 0);
		}
		startEpserver();
		Log.i("gimServer", "oncreate");
//		GlobalNotificationDisplay.getGND(this).showNotification();//taotao 20131223
	}

	public void onDestroy() {

		if (timer != null) {
			timer.cancel();
		}
		stopEpserver();
		Log.i("gimServer", "ondestroy");
//		GlobalNotificationDisplay.getGND(this).cancelNotification();//taotao 20131223
	}

	/**
	 * ������
	 */
	private void setVibrator() {
		long[] pattern = { 10, 100 };
		if (vibrator == null) {
			vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		}
		vibrator.vibrate(pattern, -1);
	}

	/**
	 * ��������
	 * 
	 * @param fileName
	 */
	private void playe(String fileName) {
		/*mediaPlayer = null;
		if (mediaPlayer == null) {
			if (fileName.equals("global")) {
				mediaPlayer = MediaPlayer.create(this, 
//						R.drawable.global);
						R.drawable.ic_launcher);
			} else {
				mediaPlayer = MediaPlayer.create(this, 
//						R.drawable.msg);
						R.drawable.ic_launcher);
			}
		}
		mediaPlayer.start(); //TODO ��Ч�ļ�û����
*/	}

	public static class ServiceBinder extends Binder {

	}

	private void setWhat(int what) {
		eventHandler.sendEmptyMessage(what);
	}

	private void setWhat(int what, Object obj) {
		Message message = new Message();
		message.what = what;
		message.obj = obj;
		eventHandler.sendMessage(message);
	}

	@Override
	public void dealMessage(CimAbstractMessage cam) {
		if (cam instanceof CimLoginMessage) {// ���յ�¼��Ϣ
			handleLoginMessage(cam);
		} else if (cam instanceof CimChatMessage) {// �����ı���Ϣ
			handleChatMessage(cam);
		} else if (cam instanceof CimQueryStatusMessage) {// ����״̬��Ϣ
			handleStatusMessage(cam);
		} else if (cam instanceof SaturationMessage) {// ����״̬����Ϣ
			recCount++;
		} else {

		}

	}

	public void handleLoginMessage(CimAbstractMessage cam) {
		if (cam.getErrCode() == 9) { //
			stopEpserver();
			sendChangedBroadcast();
		} else if (cam.getErrCode() == 20 || cam.getErrCode() == 1) { // ����socketʧ��
			setWhat(CimConsts.HandlerType.SOCKET_FAILURE);
		} else if (cam.getErrCode() == 0) { // ��¼�ɹ�
			setWhat(CimConsts.HandlerType.SOCKET_SUCCEED);
			startTime();
			SocketBuild.sendMeStatus();
		}

	}

	private void handleStatusMessage(CimAbstractMessage cam) {
		/*long userId = 0;
		int status = 0;
		CimQueryStatusMessage query = (CimQueryStatusMessage) cam;
		for (int i = 0; i < query.getUserCount(); i++) {
			UserStatus user = query.getUserStatus(i);
			userId = user.getId();
			status = user.getStatus();
			AllAdapterControl.getInstance().setUserStatus(userId,
					(short) status);
			setWhat(CimConsts.HandlerType.US_ONLINE);
		}
		AllAdapterControl.getInstance().setUserStatus(677,
				(short) 50);
		setWhat(CimConsts.HandlerType.US_ONLINE);
		AllAdapterControl.getInstance().setUserStatus(678,
				(short) 50);
		setWhat(CimConsts.HandlerType.US_ONLINE);//test!!!!!
*/	}

	/**
	 * ����������Ϣ
	 * 
	 * @param cam
	 */
	private void handleChatMessage(CimAbstractMessage cam) {
		CimChatMessage chatMessage = (CimChatMessage) cam;
		long userId = chatMessage.getFromUserId(); // ��ȡ��Ϣ���û�ID
		boolean result =false;// AllAdapterControl.getInstance().isBlacklist(userId);// ��ȡ��������Ϣ TODO
		if (result) {
			return;
		}
		String time = chatMessage.getTime();
		switch (chatMessage.getKind()) {
		case CimConsts.MessageType.MT_CHAT:

			CachMsg.getInstance().addChatEntity(this, userId, time,
					chatMessage.getRemark(), chatMessage.getHtml(), true);

			ChatMsgEntity entity = addChatMessage(chatMessage, userId, time);

			handleChartMess(userId, entity, CimConsts.ConnectUserType.FRIEND);

			break;
		case CimConsts.MessageType.MT_BIG_GROUP:
			/*if (sp.getBoolean("set_group", true)) {// �Ƿ����Ⱥ��Ϣ
				if (userId != SystemParams.getInstance().getUserId()) {
					CachMsg.getInstance().addChatEntity(this,
							chatMessage.getGroupId(),
							chatMessage.getRemark() + " " + time,
							chatMessage.getRemark(), chatMessage.getHtml(),
							true);

					ChatMsgEntity entity1 = addChatMessage(chatMessage, userId,
							time);

					handleChartMess(chatMessage.getGroupId(), entity1,
							CimConsts.ConnectUserType.GROUP);
				}
			}*/

			break;

		case CimConsts.MessageType.MT_GUEST_CHAT: // �ÿ���Ϣ
			CachMsg.getInstance().addChatEntity(this, userId, time,
					chatMessage.getRemark(), chatMessage.getHtml(), true);
			ChatMsgEntity entity1 = addChatMessage(chatMessage, userId, time);
			handleChartMess(userId, entity1, CimConsts.ConnectUserType.GUEST);

			break;

		case CimConsts.MessageType.MT_URL:// �ÿ�����
			handleGest(chatMessage);

			break;
		case CimConsts.MessageType.MT_WEB_CUSTOM_FACE_SENT:
			handleCustomFace(chatMessage);
			break;
		case CimConsts.MessageType.GROUP_WEB_CUSTOM_FACE_SENT:
			handleGroupCustomFace(chatMessage);
			break;

		case CimConsts.MessageType.MT_ADD_FRIEND_REQ:

		case CimConsts.MessageType.MT_ADD_FRIEND_ACCEPT:

			break;
		case CimConsts.MessageType.MT_ADD_FRIEND_REJECT:

			break;
		case CimConsts.MessageType.MT_AD:
			/*CachMsg.getInstance().addChatEntity(this,
					CimConsts.ConnectUserType.SYSID, time,
					chatMessage.getRemark(), chatMessage.getHtml(), true);
			handleSysMess(chatMessage);*/
			break;
		case CimConsts.MessageType.MT_NOTIC:
			/*CachMsg.getInstance().addChatEntity(this,
					CimConsts.ConnectUserType.SYSID, time,
					chatMessage.getRemark(), chatMessage.getHtml(), true);
			handleSysMess(chatMessage);*/
			break;

		default:
			break;
		}

	}

	private ChatMsgEntity addChatMessage(CimChatMessage chatMessage,
			long userId, String time) {
		ChatMsgEntity entity = new ChatMsgEntity();
		entity.setDate(time);
		entity.setId(userId);
		entity.setName(chatMessage.getRemark());
		entity.setMsgType(true);
		entity.setText(chatMessage.getHtml());
		return entity;
	}

	/**
	 * �����Զ�����
	 * 
	 * @param chatMessage
	 */
	private void handleCustomFace(CimChatMessage chatMessage) {
		/*long id = chatMessage.getFromUserId();
		String[] file = chatMessage.getText().split(",");
		CachMsg.getInstance().holdIMGChatEntity(id, file[2] + ".jpg", file[0]);
		CachMsg.getInstance()
				.updateChatImg(this, file[2] + ".jpg", file[0], id);
		setWhat(CimConsts.HandlerType.update_img_path, file);*/
	}

	private void handleGroupCustomFace(CimChatMessage chatMessage) {
		/*long id = chatMessage.getGroupId();
		String[] file = chatMessage.getText().split(",");
		CachMsg.getInstance().holdIMGChatEntity(id, file[2], file[0]);
		CachMsg.getInstance().updateChatImg(this, file[2], file[0], id);
		setWhat(CimConsts.HandlerType.update_img_path, file);*/
	}

	/**
	 * ����ϵͳ��Ϣ
	 */
	private void handleSysMess(CimChatMessage chatMessage) {
		/*AllAdapterControl.getInstance().setSysBadge(this,
				CimConsts.ConnectUserType.SYSID, CimConsts.ConnectUserType.SYS);
		sendBadgeChangedBroadcast();
		setWhat(CimConsts.HandlerType.Conversation_badge);
		if (!sp.getBoolean("toggle_sound", false)) {
			sysPlayer();
		}
		if (sp.getBoolean("vibration", true)) {
			setVibrator();
		}*/

	}

	/**
	 * ϵͳ��Ϣ��������
	 */
	private void sysPlayer() {
		MediaPlayer mp = new MediaPlayer();
		mp.reset();
		try {
			mp.setDataSource(this, RingtoneManager
					.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			mp.prepare();
		} catch ( Exception e) {
			e.printStackTrace();
		}
		mp.start();
	}

	/**
	 * ����ÿ�����
	 * 
	 * @param chatMessage
	 */
	private void handleGest(CimChatMessage chatMessage) {
		String chatMess = chatMessage.getText();
		if (sp.getBoolean("set_guest", true)) {
			if (sp.getBoolean("web_sound", true)) {
				playe("global");
			}
		}
		guestMess = chatMess.split("\\{\\}");
		BuildData.getInstance().dynamicAddGuestNode((short) 10, 42l,
				guestMess[4], guestMess[3], chatMessage.getFromUserId());
		setWhat(CimConsts.HandlerType.GUEST);
	}

	/**
	 * ����������Ϣ
	 * 
	 * @param userId
	 * @param type
	 */
	private void handleChartMess(long userId, ChatMsgEntity chatMsgEntity,
			int type) {
		if (JackUtils.getDisplayClassName(this).equals(
				"com.qfc.yft.ui.tab.chat.ChatActivity")
				&& userId == CachMsg.getInstance().getUserId()) {
			setWhat(CimConsts.HandlerType.US_CHAT, chatMsgEntity);
		} else {
			AllAdapterControl.getInstance().setBadge(this, userId, type);
			sendBadgeChangedBroadcast();//���chatActivity�Ͳ��ø�badge���𣿣�
			setWhat(CimConsts.HandlerType.Conversation_badge);
		}
		if (!sp.getBoolean("toggle_sound", false)) {
			playe("msg");
		}
		if (sp.getBoolean("vibration", true)) {
			setVibrator();
		}
	}

	private void startTime() {
		if (timer == null) {
			timer = new Timer();
			timer.schedule(new TreeTask(), 1000, 20000);

		}
	}

	/**
	 * ���͹㲥��֪ͨUI��BADGE�Ѹı�
	 */
	private void sendBadgeChangedBroadcast() {
		bundle.putInt("BADGE", CachMsg.getInstance().fullCount());
		bundle.putShort("type", (short) 0);//type?
		timeIntent.putExtras(bundle);
		timeIntent.setAction(ChatLoginHelper.BADGE_ACTION);
		
		MyApplication.app().
			sendBroadcast(timeIntent);//TODO ��Ǹ�������������������˳�
	}

	/**
	 * ���͹㲥��֪ͨUI�������Ѹı�
	 */
	private void sendChangedBroadcast() {//TODO
		/*bundle.putShort("type", CimConsts.HandlerType.EPSERVER_OFFLONE);
		bundle.putInt("BADGE", CachMsg.getInstance().fullCount());
		timeIntent.putExtras(bundle);
		timeIntent.setAction(ChatLoginHelper.BADGE_ACTION);
		sendBroadcast(timeIntent);*/
	}

	public class TreeTask extends TimerTask {
		public void run() {
			if (sendCount - recCount > 5) {
				CimSocket.getInstance().close();
				sendCount = 0;
				recCount = 0;
			} else {
				CimSocket.getInstance().sendMsg(
						SocketProtocol.socketSaturatedXml(SystemParams
								.getInstance().getSessionId()));
				sendCount++;
			}
		}
	}

	/**
	 * ��¼ͨ�ŷ�����
	 */
	public void startEpserver() {
		new Thread(){
			
			@Override
			public void run() {
				CimSocket cimSocket = CimSocket.getInstance();
				cimSocket.initMessageListener(GIMSocketServer.this);
				cimSocket.connect();
				super.run();
			}
		}.start();

	}

	
	/**
	 * ֹͣͨѶ������
	 */
	public void stopEpserver() {
		CimSocket cimSocket = CimSocket.getInstance();
		cimSocket.stop();
		cimSocket = null;
	}

	private Handler eventHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case CimConsts.HandlerType.update_img_path:
				/*ChatMsgAdapter adapter = AllAdapterControl.getInstance()
						.getChatListAdapter();
				if (adapter != null) {
					String[] f = (String[]) msg.obj;
					adapter.update(f[0], f[2] + ".jpg");
					adapter.notifyDataSetChanged();

				}*/

				break;

			case CimConsts.HandlerType.US_ONLINE:
//				AllAdapterControl.getInstance().notifyDataSetChanged();
				break;
			case CimConsts.HandlerType.US_CHAT://����ʱ 

				ChatMsgAdapter chatMsgAdapter = AllAdapterControl.getInstance()
						.getChatListAdapter();
				if (chatMsgAdapter != null) {
					ChatMsgEntity chatMsgEntity = (ChatMsgEntity) msg.obj;
					if (chatMsgEntity != null) {

						chatMsgAdapter.add(chatMsgEntity);
					}
					chatMsgAdapter.notifyDataSetChanged();
					AllAdapterControl
							.getInstance()
							.getChatListView()
							.setSelection(
									AllAdapterControl.getInstance()
											.getChatListView().getCount() - 1);

				}

				break;
			case CimConsts.HandlerType.Conversation_badge://�����б���棿
				AllAdapterControl.getInstance().conversationlistAdapter
						.getRoot().sortByTime();
				AllAdapterControl.getInstance().conversationlistAdapter
						.notifyDataSetChanged();

				break;

			case CimConsts.HandlerType.EPSERVER_OFFLONE:

				break;

			case CimConsts.HandlerType.GUEST:
				/*JackUtils.showToast(GIMSocketServer.this, "����"
						+ guestMess[3] + "�ÿ�����");
				AllAdapterControl.getInstance().getGuestExpandableListAdapter()
						.notifyDataSetChanged();*/
				break;

			default:
				break;
			}

		}
	};

	
	
	
}
