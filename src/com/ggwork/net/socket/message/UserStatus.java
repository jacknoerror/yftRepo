package com.ggwork.net.socket.message;

/*
 * 用户状态，用户状态消息使用
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
