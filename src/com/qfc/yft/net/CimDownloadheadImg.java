package com.qfc.yft.net;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import android.os.Handler;
import android.os.Message;

import com.qfc.yft.net.chat.Config;
import com.qfc.yft.ui.AllAdapterControl;
import com.qfc.yft.ui.tabs.chat.TabChatFragment;
import com.qfc.yft.utils.JackUtils;


/**
 * @author zw.Bai
 * @version 2011-7-18 下午04:20:18 下载头像
 */
public class CimDownloadheadImg extends Thread {
//	private static ConversationActivity conversationActivity;
	private static TabChatFragment tChatFragment;

	String faceIndex = null;
	/*private Handler handler = new Handler() {
		public void handleMessage(Message msg) {// 定义一个Handler，用于处理下载线程与UI间通讯
			switch (msg.what) {
			case 1:
				AllAdapterControl.getInstance().notifyDataSetChanged();
				if (tChatFragment != null) {
//					tChatFragment.setImg(1);
				}
				break;

			}

		}

	};*///taotao 0112

	public CimDownloadheadImg(String faceIndex,
			TabChatFragment tChatFragment) {
		this.tChatFragment = tChatFragment;

		this.faceIndex = faceIndex;

	}

	public static void getHeadImg(String faceIndex,
			TabChatFragment tabChatFragment) {
		CimDownloadheadImg downloadheadImg = new CimDownloadheadImg(faceIndex,
				tabChatFragment);
		downloadheadImg.start();

	}

	public void run() {

		try {
			downImg(faceIndex);

			Thread.sleep(5000);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void sendMsg(int flag) {
		Message msg = new Message();
		msg.what = flag;
//		handler.sendMessage(msg);//taotao 0112 anni
	}

	private void downImg(String faceIndex) throws Exception {
		File file = new File(JackUtils.getUserHeadPath(faceIndex));
		FileOutputStream fos = null;
		if (!file.exists()) {
			file.delete();
		}
		StringBuffer sbUrl = new StringBuffer();
		sbUrl.append(Config.API_URL).append("images/userface/")
				.append(faceIndex).append("-100-10.jpg");
		URL myURL = new URL(sbUrl.toString());
		URLConnection conn = myURL.openConnection();
		conn.setReadTimeout(50000);
		conn.connect();
		InputStream is = conn.getInputStream();
		fos = new FileOutputStream(JackUtils.getUserHeadPath(faceIndex));
		byte buf[] = new byte[1024 * 4];
		do {
			int numread = is.read(buf);
			if (numread == -1) {
				break;
			}
			fos.write(buf, 0, numread);
		} while (true);
		sendMsg(1);
		try {
			is.close();
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
