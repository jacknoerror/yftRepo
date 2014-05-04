package com.qfc.yft;

public class CimConsts {
	public static final String remember = "remember";
	public static final String hideLogin = "hideLogin";

	public static class MessageType {
		/** 无 */
		public static final short MT_NONE = 0;
		/** 1v1消息 */
		public static final short MT_CHAT = 1;
		/** 群消息 */
		public static final short MT_GROUP = 2;
		/** 大群消息 */
		public static final short MT_BIG_GROUP = 1004;
		/** 加好友请求 */
		public static final short MT_ADD_FRIEND_REQ = 3;
		/** 加好友应答：确认 */
		public static final short MT_ADD_FRIEND_ACCEPT = 4;
		/** 加好友应答：拒绝 */
		public static final short MT_ADD_FRIEND_REJECT = 5;
		/** 页面切换 */
		public static final short MT_URL = 6;
		/** 关键字 */
		public static final short MT_KEYWORD = 7;
		/** 发文请求 */
		public static final short MT_FILE_SEND_REQ = 8;
		/** 发文应答：接受 */
		public static final short MT_FILE_ACCEPT = 9;
		/** 发文应答：拒绝 */
		public static final short MT_FILE_REJECT = 10;
		/** 视频请求 */
		public static final short MT_VEDIO_REQ = 13;
		/** 视频应答：接受 */
		public static final short MT_VEDIO_ACCEPT = 14;
		/** 视频应答“拒绝 */
		public static final short MT_VEDIO_REJECT = 15;
		/** 请求对方发文 */
		public static final short MT_FILE_REQ = 16;
		/** 视频结束 */
		public static final short MT_VEDIO_END = 17;
		/** 群增加用户 */
		public static final short MT_GROUP_ADD_USER = 18;
		/** 群删除用户 */
		public static final short MT_GROUP_DEL_USER = 19;
		/** 群修改用户资料 */
		public static final short MT_GROUP_EDIT_USER = 20;
		/** 群解散 */
		public static final short MT_GROUP_DEL = 21;
		/** web访客1v1消息 */
		public static final short MT_GUEST_CHAT = 22;
		/** 短信 */
		public static final short MT_SMS = 23;
		/** 好友输入状态 */
		public static final short MT_INPUT_STATUS = 24;
		/** 发送离线文件 */
		public static final short MT_OFFLINE_FILE = 25;
		/** flash视频请求 */
		public static final short MT_FLASH_VEDIO_REQ = 26;
		/** flash视频应答：接受 */
		public static final short MT_FLASH_VEDIO_ACCEPT = 27;
		/** flash视频应答：拒绝 */
		public static final short MT_FLASH_VEDIO_REJECT = 28;
		/** flash视频结束 */
		public static final short MT_FLASH_VEDIO_END = 29;
		/** 给web访客发送自定义表情 */
		public static final short MT_WEB_CUSTOM_FACE_SENT = 30;
		/** 公告消息 */
		public static final short MT_NOTIC = 32;
		/** 广告消息 */
		public static final short MT_AD = 33;
		/**
		 * 临时加群
		 */
		public static final short MI_TEMP_ADD_GROUP = 1002;
		/**
		 * 临时退群
		 */
		public static final short MI_TEMP_OUT_GROUP = 1003;

		/**
		 * 处理群自定义表情
		 */
		public static final short GROUP_WEB_CUSTOM_FACE_SENT = 1008;

		/**
		 * 是否BS消息
		 */
		public static final boolean isWebMessage(short type) {
			return (type == MT_URL || type == MT_KEYWORD
					|| type == MT_GUEST_CHAT || type == MT_INPUT_STATUS
					|| type == MT_OFFLINE_FILE || type == MT_FLASH_VEDIO_ACCEPT
					|| type == MT_FLASH_VEDIO_END
					|| type == MT_FLASH_VEDIO_REJECT
					|| type == MT_FLASH_VEDIO_REQ || type == MT_WEB_CUSTOM_FACE_SENT);
		}
	}

	public static class SystemCode {
		public static final short RESULT_DEL_KIND = 20000;

		// --------------加好友返回的错误码---------------------------------
		/**
		 * 自己没有对外权限
		 */
		public static final int ADD_FRIEND_ERROR_20001 = 20001;
		/**
		 * 好友没有对外权限
		 */
		public static final int ADD_FRIEND_ERROR_20002 = 20002;
		/**
		 * 已是好友
		 */
		public static final int ADD_FRIEND_ERROR_20003 = 20003;
		/**
		 * 已是黑名单
		 */
		public static final int ADD_FRIEND_ERROR_20004 = 20004;
		/**
		 * 被加好友已上限
		 */
		public static final int ADD_FRIEND_ERROR_20005 = 20005;
		/**
		 * 自己好友已上限
		 */
		public static final int ADD_FRIEND_ERROR_20006 = 20006;
		/**
		 * 频繁加好友验证
		 */
		public static final int ADD_FRIEND_ERROR_20007 = 20007;
		/**
		 * 没有查找的用户
		 */
		public static final int ADD_FRIEND_ERROR_20008 = 20008;

		/**
		 * 验证码错误
		 */
		public static final int ADD_FRIEND_ERROR_20009 = 20009;

		/**
		 * 已经是同一企业用户不能加好友
		 */
		public static final short ADD_FRIEND_ERROR_20010 = 20010;

	}

	public static class ConnectUserType {
		public static final int SHOP = 0;
		public static final int FRIEND = 0;
		public static final int GROUP = 1;
		public static final int GUEST = 2;
		public static final int SYS = 3;
		public static final long SYSID = -3L;

	}

	/**
	 * Y用户状态
	 */
	public static class UserStatus {
		/** 在线10 */
		public static final short US_ONLINE = 10;
		/** 忙碌 20 */
		public static final short US_BUSY = 20;
		/** 离开30 */
		public static final short US_LEAVE = 30;
		/** 隐身 40 */
		public static final short US_HIDE = 40;
		/** 离线 50 */
		public static final short US_OFFLINE = 50;
	}

	public static class HandlerType {
		/**
		 * 状态更新
		 */
		public static final short US_ONLINE = 168;
		/**
		 * 头像更新
		 * */
		public static final short US_BUSY = 169;
		/**
		 * 最近列表信息
		 * */
		public static final short US_lately = 170;

		public static final short US_CHAT = 171;
		public static final short us_sys = 172;
		public static final short US_WINK = 173;

		/**
		 * 挤下线
		 */
		public static final short EPSERVER_OFFLONE = 174;

		/**
		 * 访客上线
		 */
		public static final short GUEST = 175;
		/**
		 * socket连接成功
		 */
		public static final short SOCKET_SUCCEED = 176;
		/**
		 * socket连接失败
		 */
		public static final short SOCKET_FAILURE = 178;
		/**
		 * 登录成功
		 */
		public static final short LOGIN_SUCCEED = 200;
		/**
		 * 登录失败
		 */
		public static final short LOGIN_FAILURE = 201;

		/**
		 * 更新会话
		 */
		public static final short Conversation_badge = 202;
		
		public static final short update_img_path = 203;

	}

}
