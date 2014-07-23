package com.qfc.yft.data.chat;

public interface ChatConfig {

	/**
	 * 0、MD5 1、空 2、异或加密
	 */
	public static int encryptType = 2;
	/**
	 * 是否用大群显示
	 */
	public static boolean bigGroup = false;
	/**
	 * 是否显示应用
	 */
	public static boolean SHOWAPP = false;
	/**
	 * 是否显示商机
	 */
	public static boolean SHOW_business = false;
	/**
	 * 是否显示访客
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
	 * 好友链接地址
	 */
	public static final String MyInformation = "http://im.qfc.cn:8080/cimls/ClientPage/MyInformationForMobile.html";
	public static final String FriendMaterial = "http://im.qfc.cn:8080/cimls/ClientPage/FriendMaterialForMobile.html";

	/**
	 * EC空间API
	 */
	public static String newUrl = "http://ec.eckj.cn/interface/ec.ashx";
	/**
	 * 邮件地址
	 */
	public static String ggmail = "http://im1.ggwork.net:8878/ggmail/";

}
