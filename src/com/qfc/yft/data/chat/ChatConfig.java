package com.qfc.yft.data.chat;

public interface ChatConfig {

	/**
	 * 0��MD5 1���� 2��������
	 */
	public static int encryptType = 2;
	/**
	 * �Ƿ��ô�Ⱥ��ʾ
	 */
	public static boolean bigGroup = false;
	/**
	 * �Ƿ���ʾӦ��
	 */
	public static boolean SHOWAPP = false;
	/**
	 * �Ƿ���ʾ�̻�
	 */
	public static boolean SHOW_business = false;
	/**
	 * �Ƿ���ʾ�ÿ�
	 */
	public static boolean ShOW_GEST = true;

	 public static String API_URL =	 "http://im-test.qfc.test.ctcn.com.cn:8080/cimls/";
	 public static String SOCKET_URL = "im-test.qfc.test.ctcn.com.cn";
	 public static String defaultSite = "";
	 public static int SOCKET_PORT = 18888;

//	public static String API_URL = "http://im.qfc.cn/cimls/";//:8080
//	public static String SOCKET_URL = "im.qfc.cn";
//	public static String defaultSite = "";
//	public static int SOCKET_PORT = 18888;

	//

	/**
	 * �������ӵ�ַ
	 */
	public static final String MyInformation = "http://im.qfc.cn:8080/cimls/ClientPage/MyInformationForMobile.html";
	public static final String FriendMaterial = "http://im.qfc.cn:8080/cimls/ClientPage/FriendMaterialForMobile.html";

	/**
	 * EC�ռ�API
	 */
	public static String newUrl = "http://ec.eckj.cn/interface/ec.ashx";
	/**
	 * �ʼ���ַ
	 */
	public static String ggmail = "http://im1.ggwork.net:8878/ggmail/";

}
