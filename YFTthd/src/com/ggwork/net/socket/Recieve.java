package com.ggwork.net.socket;



/**
 * 消息接收类
 * 
 * @author zw.Bai
 * 
 */
public class Recieve extends Thread {

	private boolean stop;
	private CimSocket socket;

	private Object event = new Object();

	public Recieve(CimSocket socket) {
		this.socket = socket;
		this.start();

	}

	public void runNotify() {
		synchronized (event) {
			event.notifyAll();
		}

	}

	public void run() {
		while (!stop) {
			try {
				if (socket.isConnected()) {
					socket.recieve();
				} else {
					synchronized (event) {
						event.wait();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				socket.close();

			}
		}
	}

	public void termniate() {
		stop = true;
	}
}
