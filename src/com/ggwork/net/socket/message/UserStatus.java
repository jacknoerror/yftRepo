package com.ggwork.net.socket.message;

/*
 * �û�״̬���û�״̬��Ϣʹ��
 */
public class UserStatus {
	private long id;
	private int status;
	
	public UserStatus(long id, int status) {
		this.id = id;
		this.status = status;
	}
	
	public long getId() {
		return id;
	}
	
	public int getStatus() {
		return status;
	}
}
