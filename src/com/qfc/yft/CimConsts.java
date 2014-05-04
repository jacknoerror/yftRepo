package com.qfc.yft;

public class CimConsts {
	public static final String remember = "remember";
	public static final String hideLogin = "hideLogin";

	public static class MessageType {
		/** �� */
		public static final short MT_NONE = 0;
		/** 1v1��Ϣ */
		public static final short MT_CHAT = 1;
		/** Ⱥ��Ϣ */
		public static final short MT_GROUP = 2;
		/** ��Ⱥ��Ϣ */
		public static final short MT_BIG_GROUP = 1004;
		/** �Ӻ������� */
		public static final short MT_ADD_FRIEND_REQ = 3;
		/** �Ӻ���Ӧ��ȷ�� */
		public static final short MT_ADD_FRIEND_ACCEPT = 4;
		/** �Ӻ���Ӧ�𣺾ܾ� */
		public static final short MT_ADD_FRIEND_REJECT = 5;
		/** ҳ���л� */
		public static final short MT_URL = 6;
		/** �ؼ��� */
		public static final short MT_KEYWORD = 7;
		/** �������� */
		public static final short MT_FILE_SEND_REQ = 8;
		/** ����Ӧ�𣺽��� */
		public static final short MT_FILE_ACCEPT = 9;
		/** ����Ӧ�𣺾ܾ� */
		public static final short MT_FILE_REJECT = 10;
		/** ��Ƶ���� */
		public static final short MT_VEDIO_REQ = 13;
		/** ��ƵӦ�𣺽��� */
		public static final short MT_VEDIO_ACCEPT = 14;
		/** ��ƵӦ�𡰾ܾ� */
		public static final short MT_VEDIO_REJECT = 15;
		/** ����Է����� */
		public static final short MT_FILE_REQ = 16;
		/** ��Ƶ���� */
		public static final short MT_VEDIO_END = 17;
		/** Ⱥ�����û� */
		public static final short MT_GROUP_ADD_USER = 18;
		/** Ⱥɾ���û� */
		public static final short MT_GROUP_DEL_USER = 19;
		/** Ⱥ�޸��û����� */
		public static final short MT_GROUP_EDIT_USER = 20;
		/** Ⱥ��ɢ */
		public static final short MT_GROUP_DEL = 21;
		/** web�ÿ�1v1��Ϣ */
		public static final short MT_GUEST_CHAT = 22;
		/** ���� */
		public static final short MT_SMS = 23;
		/** ��������״̬ */
		public static final short MT_INPUT_STATUS = 24;
		/** ���������ļ� */
		public static final short MT_OFFLINE_FILE = 25;
		/** flash��Ƶ���� */
		public static final short MT_FLASH_VEDIO_REQ = 26;
		/** flash��ƵӦ�𣺽��� */
		public static final short MT_FLASH_VEDIO_ACCEPT = 27;
		/** flash��ƵӦ�𣺾ܾ� */
		public static final short MT_FLASH_VEDIO_REJECT = 28;
		/** flash��Ƶ���� */
		public static final short MT_FLASH_VEDIO_END = 29;
		/** ��web�ÿͷ����Զ������ */
		public static final short MT_WEB_CUSTOM_FACE_SENT = 30;
		/** ������Ϣ */
		public static final short MT_NOTIC = 32;
		/** �����Ϣ */
		public static final short MT_AD = 33;
		/**
		 * ��ʱ��Ⱥ
		 */
		public static final short MI_TEMP_ADD_GROUP = 1002;
		/**
		 * ��ʱ��Ⱥ
		 */
		public static final short MI_TEMP_OUT_GROUP = 1003;

		/**
		 * ����Ⱥ�Զ������
		 */
		public static final short GROUP_WEB_CUSTOM_FACE_SENT = 1008;

		/**
		 * �Ƿ�BS��Ϣ
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

		// --------------�Ӻ��ѷ��صĴ�����---------------------------------
		/**
		 * �Լ�û�ж���Ȩ��
		 */
		public static final int ADD_FRIEND_ERROR_20001 = 20001;
		/**
		 * ����û�ж���Ȩ��
		 */
		public static final int ADD_FRIEND_ERROR_20002 = 20002;
		/**
		 * ���Ǻ���
		 */
		public static final int ADD_FRIEND_ERROR_20003 = 20003;
		/**
		 * ���Ǻ�����
		 */
		public static final int ADD_FRIEND_ERROR_20004 = 20004;
		/**
		 * ���Ӻ���������
		 */
		public static final int ADD_FRIEND_ERROR_20005 = 20005;
		/**
		 * �Լ�����������
		 */
		public static final int ADD_FRIEND_ERROR_20006 = 20006;
		/**
		 * Ƶ���Ӻ�����֤
		 */
		public static final int ADD_FRIEND_ERROR_20007 = 20007;
		/**
		 * û�в��ҵ��û�
		 */
		public static final int ADD_FRIEND_ERROR_20008 = 20008;

		/**
		 * ��֤�����
		 */
		public static final int ADD_FRIEND_ERROR_20009 = 20009;

		/**
		 * �Ѿ���ͬһ��ҵ�û����ܼӺ���
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
	 * Y�û�״̬
	 */
	public static class UserStatus {
		/** ����10 */
		public static final short US_ONLINE = 10;
		/** æµ 20 */
		public static final short US_BUSY = 20;
		/** �뿪30 */
		public static final short US_LEAVE = 30;
		/** ���� 40 */
		public static final short US_HIDE = 40;
		/** ���� 50 */
		public static final short US_OFFLINE = 50;
	}

	public static class HandlerType {
		/**
		 * ״̬����
		 */
		public static final short US_ONLINE = 168;
		/**
		 * ͷ�����
		 * */
		public static final short US_BUSY = 169;
		/**
		 * ����б���Ϣ
		 * */
		public static final short US_lately = 170;

		public static final short US_CHAT = 171;
		public static final short us_sys = 172;
		public static final short US_WINK = 173;

		/**
		 * ������
		 */
		public static final short EPSERVER_OFFLONE = 174;

		/**
		 * �ÿ�����
		 */
		public static final short GUEST = 175;
		/**
		 * socket���ӳɹ�
		 */
		public static final short SOCKET_SUCCEED = 176;
		/**
		 * socket����ʧ��
		 */
		public static final short SOCKET_FAILURE = 178;
		/**
		 * ��¼�ɹ�
		 */
		public static final short LOGIN_SUCCEED = 200;
		/**
		 * ��¼ʧ��
		 */
		public static final short LOGIN_FAILURE = 201;

		/**
		 * ���»Ự
		 */
		public static final short Conversation_badge = 202;
		
		public static final short update_img_path = 203;

	}

}
