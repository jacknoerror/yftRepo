package com.ggwork.net.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

import android.util.Log;

import com.ggwork.net.socket.message.CimAbstractMessage;
import com.ggwork.net.socket.message.CimMessageFactory;
import com.ggwork.net.socket.message.CimMessageListener;
import com.qfc.yft.data.Const;
import com.qfc.yft.vo.chat.SystemParams;

/**
 * socket 消息发送封装
 * 
 * @author zw.Bai
 * 
 */
public class CimSocket {
	private static CimSocket instance = null;
	private CimMessageListener messageListener = null;
	private Sender sender;
	private Recieve recieve = null;
	private Socket socket = null;
	private InputStream is;
	private OutputStream os;
	private InetSocketAddress isa = null;

	public CimSocket() {
		sender = new Sender(this);
		recieve = new Recieve(this);
	}

	/**
	 * socket 连接
	 */
	public synchronized void connect() {

		if (socket == null) {
			try {
				// Log.d("connectStart", "socket开始重连");
//				InetSocketAddress isap = new InetSocketAddress(YftValues.PROXY_HOST, 80);
//				Proxy proxy = new Proxy(Proxy.Type.SOCKS, isap);
//				Socket tmpSocket = new Socket(proxy);
				Socket tmpSocket = new Socket();
				isa = new InetSocketAddress(Const.SOCKET_URL,
						Const.SOCKET_PORT);
				tmpSocket.connect(isa);
				socket = tmpSocket;
				os = socket.getOutputStream();
				is = socket.getInputStream();
				// Log.d("connectStart", "socket重连成功");
			} catch (Exception e) {
				e.printStackTrace();
				close();
			}
			if (socket != null) {
				recieve.runNotify();
				sendLoginXml();
				// Log.d("connectStart", "socket重连结束");
			}
		}
	}

	public boolean isConnected() {
		return socket != null;
	}

	/**
	 * 消息写入流
	 * 
	 * @param ms
	 * @throws IOException
	 * @throws Exception
	 */
	public void write(String ms) throws Exception {
		if (os != null) {
			os.write(ms.getBytes());
		}

	}

	private StringBuffer recvBuffer = new StringBuffer();
	private byte[] buffer = new byte[8192];

	/**
	 * 接收消息
	 * 
	 * @throws IOException
	 * 
	 * @throws Exception
	 * 
	 * 
	 */

	public void recieve() {

		if (is != null) {
			try {
				// while ((c = is.read()) > 0) {
				// if (c == '\'') {
				// sb.append('"');
				// } else {
				// sb.append((char) c);
				// }
				// }
				// if (c == -1) {
				// close();
				// } else if (sb.length() > 0) {
				// recieveMessage(sb.toString());
				// Log.d("recieveMessage", sb.toString());
				// sb.setLength(0);
				// }

				int readBytes = is.read(buffer);

				if (readBytes >= 0) {
					for (int i = 0; i < readBytes; i++) {
						byte b = buffer[i];
						if (b == 0) {
							ewciwveMess(recvBuffer);
						} else {
							recvBuffer.append((char) b);
						}
					}
				} else {
					close();
				}

			} catch (Exception e) {
				close();
				Log.e("recieve", "exception");
				e.printStackTrace();
			}

		}

	}

	private void ewciwveMess(StringBuffer sb) {
		if (sb.length() > 0) {
			System.out.println("recieveMessage :" + sb.toString());

			// System.out.println("recieveMessage :"
			// + CimUtils.recievTop++);
			recieveMessage(sb.toString());
			sb.setLength(0);
		}
	}

	/**
	 * 关闭
	 */
	public synchronized void close() {
		Log.d("closeStart", "socket关闭开始");
		try {
			if (is != null) {
				is.close();
			}
			if (os != null) {
				os.close();
			}
		} catch (Exception ingore) {

			ingore.printStackTrace();
		} finally {
			is = null;
			os = null;
			if (socket != null) {
				try {
					socket.close();
				} catch (Exception ingore) {
					ingore.printStackTrace();
				} finally {
					socket = null;
					isa = null;
				}
			}
			Log.d("closeStart", "socket关闭成功");
		}

		if (!isConnected()) {
			// recieveMessage(CimUtils.loginCode);
		}

	}

	public void initMessageListener(CimMessageListener messageListener) {
		this.messageListener = messageListener;
	}

	public void recieveMessage(String msgText) {
		CimAbstractMessage cam = CimMessageFactory.createMessage(msgText);
		if (messageListener != null && cam != null) {
			messageListener.dealMessage(cam);
		}
	}

	/**
	 * 发送消息
	 * 
	 * @param msgText
	 */
	public void sendMsg(String msgText) {
		sender.send(msgText);
	}

	/**
	 * 停止
	 */
	public void stop() {

		// sendMsg("<cim client=\"cs\" type=\"setStatus\"><user status=\""
		// + (short) 50 + "\"/></cim>");
		try {
			if (sender != null) {
				sender.termniate();
				sender = null;
			}

			if (recieve != null) {
				recieve.termniate();
				recieve = null;
			}

		} finally {
			close();
			sender = null;
			recieve = null;
			instance = null;
		}

	}

	public static CimSocket getInstance() {
		if (instance == null) {
			instance = new CimSocket();
		}
		return instance;
	}

	/**
	 * socket登录
	 */
	public void sendLoginXml() {

		String xml = SocketProtocol.scoketLogInXML(SystemParams.getInstance()
				.getSessionId());
		sendMsg(xml);

	}
}
