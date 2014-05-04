package com.qfc.yft.net;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import android.os.Handler;
import android.os.Message;

import com.qfc.yft.net.chat.Config;
import com.qfc.yft.ui.tabs.chat.SurfaceViewActivity;
import com.qfc.yft.utils.JackUtils;
import com.qfc.yft.vo.SystemParams;


/**
 * @author zw.Bai
 * @version 2011-7-13 上午10:50:18
 * 
 */
public class CimDownloadFile extends Thread {

	// private int downLoadFileSize = 0;
	// private String fileEx, fileNa,

	private String filename, fileId;
	private SurfaceViewActivity surfaceViewActivity;

	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {// 定义一个Handler，用于处理下载线程与UI间通讯
			switch (msg.what) {
			case 1:
				surfaceViewActivity.setImg();
				surfaceViewActivity.dismissProDialog();
				break;

			}

		}

	};

	private void sendMsg(int flag) {
		Message msg = new Message();
		msg.what = flag;
		handler.sendMessage(msg);
	}

	public CimDownloadFile(String fileId, String fileName,
			SurfaceViewActivity surfaceViewActivity) {
		this.fileId = fileId;
		this.filename = fileName;
		this.surfaceViewActivity = surfaceViewActivity;

	}

	public void run() {
		try {
			downFile(fileId, filename);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void downFile(String fileId, String fileName) throws IOException {
		StringBuffer url = new StringBuffer(Config.API_URL
				+ "service/UserFileDownload?sessionId=");
		url.append(SystemParams.getInstance().getSessionId());
		url.append("&fileId=").append(fileId);

		URL myURL = new URL(url.toString());
		URLConnection conn = myURL.openConnection();
		conn.setReadTimeout(50000);
		conn.connect();
		InputStream is = conn.getInputStream();
		FileOutputStream fos = null;
		fos = new FileOutputStream(JackUtils.file_path
				+ SystemParams.getInstance().getLoginId() + "/images/"
				+ filename);
		byte buf[] = new byte[1024 * 16];
		do {
			int numread = is.read(buf);
			if (numread == -1) {
				break;
			}
			fos.write(buf, 0, numread);

		} while (true);
		sendMsg(1);
		try {
			if (is != null && fos != null)
				is.close();
			fos.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {

		}

	}
}
