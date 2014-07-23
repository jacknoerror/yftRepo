package com.ggwork.net.socket;

import java.util.LinkedList;
import java.util.Queue;



import android.util.Log;

/**
 * 消息发送类
 * 
 * @author zw.Bai
 * 
 */
public class Sender extends Thread {

	private boolean stop;
	private CimSocket socket;
	private Object event = new Object();
	private Queue<String> queue = new LinkedList<String>();

	public Sender(CimSocket socket) {
		this.socket = socket;
		this.start();
	}

	public void send(String msg) {
		synchronized (event) {
			String message = msg + "\0";
			Log.d("send", message);
			queue.offer(message);
			event.notifyAll();
		}
	}

	public void run() {
		while (!stop) {
			try {
				synchronized (event) {
					if (queue.isEmpty()) {
						event.wait();
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
				isStop();
			}
			try {
				if (socket.isConnected()) {
					while (!queue.isEmpty()) {
						socket.write(queue.poll());
					}
				} else {
					Log.d("connectStart", "socket准备重连");
					if (!queue.isEmpty()) {
						queue.clear();
						socket.connect();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				isStop();
			}
		}

	}

	private void isStop() {
		if (!stop) {
			socket.close();
		}
	}

	public void termniate() {
		stop = true;
		queue.clear();
		// notify();
	}

}
