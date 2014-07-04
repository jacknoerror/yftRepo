package com.qfc.yft.data;



public interface NetConst  {
	
	public static final String NO_DATA = "����";
	
	//192.168.200.244:80  ���ʴ���
	public static final String PROXY_HOST = "192.168.200.244";//
	public static final int 	PROXY_PORT= 80;
	
	public static final long TIMEOUT_REQUEST = 1000*60*2;
	public static final long TIMEOUT_REQUEST_10 = 1000*60*2;//
	public static final String URL_FORGETPWD="http://member.qfc.cn/my/get-password.action";
	
	public static final boolean DEBUG = false;//���Ի���
	public static final boolean FORMAL = false;//��ʽ����
	
	public static final URLFactory _UF = new URLFactory(FORMAL);
	
	public static final String URL_OPENAPI_APPKEY = "openApiAppKey";
	public static final String URL_OPENAPI_BUSICODE = "openApiBusiCode";
	public static final String URL_OPENAPI_TIMESTAMP = "openApiTimestamp";
	public static final String URL_OPENAPI_VALIDCODE = "openApiValidCode";
	public static final String URL_OPENAPI_USERCODE = "openApiUserCode";
	public static final String URL_OPENAPI_SESSIONKEY = "openApiSessionKey";
	
	public static final String PARAMS_USERNAME = "userName";
	public static final String PARAMS_PASSWORD = "password";
	public static final String PARAMS_USERCODE = "userCode";
	public static final String PARAMS_SHOPID = "shopId";
	public static final String PARAMS_SERIESID = "proSeriesId";
	public static final String PARAMS_PRODUCTID= "productId";//1128
	public static final String PARAMS_RECOMMEND="isRecommend";
	public static final String PARAMS_SEARCH	="keyword";
	public static final String PARAMS_PAGESIZE="pageSize";
	public static final String PARAMS_PAGENO	="pageNo";
	public static final String PARAMS_SYNC 	="jsonString";//="xmlString";
	public static final String PARAMS_ACCOUNT_ID = "accountId";
	public static final String PARAMS_TO_MEMBER = "toMember";
	public static final String PARAMS_FROM_MEMBER = "fromMember";
	public static final String PARAMS_MEMBER_ID = "memberId";
	public static final String PARAMS_FROM_ID = "fromId";
	public static final String PARAMS_COLLECT_TYPE = "collectType";
	public static final String PARAMS_SEARCH_TYPE = "searchType";
	public static final String PARAMS_COMP_ID = "compId";
	public static final String PARAMS_COMPANY_ID = "companyId";

	public static final String DES_KEY = "88211711";//"ipad_motion_key";
	
	public static final String RESULT_SIGN = "resultSign";
	public static final String RESULT_OBJECT="resultObj";
	public static final String RESULT_ERROR_MSG="errorMsg";//
	
	public static final String JSON_SERIES_NAME_PRODUCTSERIES = "productSeriesName";
	public static final String JSON_SERIES_NAME_PARENTSERIES  =	"parentSeriesName";
	public static final String JSON_SERIES_ID_PRODUCT		  =	"productSeriesId";
	public static final String JSON_SERIES_PIC				  = "productSeriesPic";
	public static final String JSON_SERIES_ID_PARENT		  = "parentSid";
	
	public static final String LOC_DEFAULT_LAT = "30.100285";
	public static final String LOC_DEFAULT_LON = "120.511389";
	
	public static final String 		FAV_PID="pid";
	public static final String 		FAV_PNAME="pname";
	public static final String 		FAV_PPIC="ppic";
	public static final String 		FAV_PINTRO="pintro";
	public static final String		FAV_MOTION="motion";//TODO 
	
	public static final String EXTRAS_SHOWPIC_PATHS ="pics";
	public static final String EXTRAS_SHOWPIC_PAGE	 ="page";
	public static final String EXTRA_REGISTER_URL = "url";
	public static final String EXTRAS_HUB_TAB = "hubTab";
	public static final String EXTRAS_HUB_KEYWORD = "hubKeyword";
	public static final String EXTRAS_SHOP_NAME = "shopName";
	public static final String EXTRAS_PRODUCT_ID = "productId";
	public static final String EXTRAS_SHOP_TAB = "shopTab";
	public static final String EXTRAS_SHOP_MEMBER_TYPE = "shopMemberType";
	public static final String EXTRAS_ACCOUNT_ID = "accountId";
	public static final String EXTRAS_ALBUM_TYPE = "uploadtype";//

	public static final String EXTRA_CATEGORY_ID = "secondCategoryId";
	
	public static final String PREF_LOCAL = ".local";
	
	public static final int DEFULAT_PAGESIZE = 10;

	public static final String REQUEST_PATH_COMPANY_PRO ="open.api.product.findSeriesByShopIdForIphone"; //��ȡ��Ʒϵ��
	public static final String REQUEST_PATH_COMPANY_SUBPRO ="open.api.product.searchProductByShopIdAndSeriesIdForIphone"; //ϵ�������в�Ʒ
	public static final String REQUEST_PATH_RECOMMEND ="open.api.shop.searchShopForIphone"; //��ҵ�Ƽ�
//	public static final String REQUEST_PATH_SEARCH ="open.api.shop.searchShopForIphone"; //��ҵ�����ӿ�
	public static final String REQUEST_PATH_CHECKVERSION ="cn.shop.getIOSVersionConfig"; //������¿ͻ��˰汾
	public static final String REQUEST_PATH_LOGIN ="cn.member.sso.pointVerify"; //��֤��¼
	public static final String REQUEST_PATH_LOGIN_IM ="cn.member.sso.pointVerifyForIm"; //��֤��¼(�ƶ���)
	public static final String REQUEST_PATH_MEMBER_INFO ="cn.member.getMemberByUserCode";//��ȡ�û���Ϣ
	public static final String REQUEST_PATH_COMPANY_INFO ="cn.shop.getShopAndCompanyById";//��ȡ��˾��Ϣ
	public static final String REQUEST_PATH_SYNC = "cn.motion.basic.parseOffLineDataForAndriod";//1122
	public static final String REQUEST_PATH_SERIESFORMOTION = "cn.product.series.getSeriesForMotion";//1128
	public static final String REQUEST_PATH_PRODUCTFORMOTION = "cn.product.getProductForMotion1";//1128
	public static final String REQUEST_CATEGORY = "cn.product.category.findAllWithJSON";
	public static final String REQUEST_CARD_SEARCH="cn.member.card.searchCardsByKeyword";//��������
	public static final String REQUEST_CARD_MY = "cn.member.card.search";//�ҵ�����
	public static final String REQUEST_SEARCH_PRODCUT = "open.api.product.searchProduct";//������Ʒ
	public static final String REQUEST_COLLECTION_SAVE= "cn.collection.saveForOpenAPI"; //�ղ����� 
	public static final String REQUEST_IS_COLLECTION_PRODUCT= "cn.collection.isCollectByProductId"; //�Ƿ��ղ�
	public static final String REQUEST_IS_COLLECTION_COMPANY= "cn.collection.isCollectByShopId"; //�Ƿ��ղ�
	public static final String REQUEST_IS_ATTENTION= "cn.attention.isAttention"; //�Ƿ��ղ�
	public static final String REQUEST_COLLECTION_DELETE= "cn.collection.deleteForOpenAPI"; //�ղ�QUXIAO
	public static final String REQUEST_FIND_COMPANY="cn.collection.findCompany";//�ղص�����
	public static final String REQUEST_FIND_PRODUCT="cn.collection.findProduct";//�ղصĲ�Ʒ
	public static final String REQUEST_CARD_ADD="cn.member.attention.doAttention";//�ղ�����
	public static final String REQUEST_CARD_REMOVE="cn.member.attention.cancelAttention";//ȡ����ע���� 
	public static final String REQUEST_SEARCH_IMAGINE="open.api.keyword.searchKeyword";
	public static final String REQUEST_GETSHOPBYUID = "cn.attention.getShopByMemberId";//

	public static final String REQUEST_PATH_SEARCHBYCOMPID = "open.api.album.searchbycompid";
	public static final String REQUEST_OPEN_API_ALBUM_ADD = "open.api.album.add";
	public static final String REQUEST_OPEN_API_PICTURE_DELETE = "open.api.picture.delete";
	public static final String REQUEST_OPEN_API_PRODUCT_SERISE_FINDALL = "open.api.product.serise.findall";

	public static final String EXTRAS_UPLOADACTION = "uploadaction";

	public static final String EXTRAS_ALBUMFIRSTTYPE = "albumfirsttype";

	public static final String EXTRAS_GRIDALBUMNAME = "gridalbumname";

	public static final String EXTRAS_GRIDALBUMID = "gridalbumid";

	public static final String EXTRAS_ALBUM_ID = "albumId";

	public static final String EXTRAS_COMP_ID = "compId";

	public static final String EXTRAS_LOCAL2UPLOAD = "local2upload";

	public static final String EXTRAS_TOALBUMNAME = "toalbumname";

	public static final String EXTRAS_TOALBUMID = "toalbumid";

	public static final String EXTRAS_ALBUM_DESC = "albumDesc";

	public static final String EXTRAS_ALBUM_NAME = "albumName";

	public static final String EXTRAS_PIC_ID = "picId";

	public static final String EXTRAS_MARKETNAME = "marketname";

	public static final String EXTRAS_MARKETURL = "marketurl";

	public static final String PARAMS_PRODUCT_SERIES = "productSeries";

	public static final String PARAMS_PRODUCT_STATUS = "productStatus";

	public static final String PARAMS_IS_PRIVATE = "isPrivate";

	public static final String PARAMS_AUDIT_STATUS = "auditStatus";

	public static final String REQUEST_OPEN_API_PRODUCT_MEMBER_SEARCH = "open.api.product.member.search";

	public static final String REQUEST_OPEN_API_PRODUCT_ONLINE = "open.api.product.online";
	public static final String REQUEST_OPEN_API_PRODUCT_OFFLINE = "open.api.product.offline";
	public static final String REQUEST_OPEN_API_PRODUCT_REPUBLISH = "open.api.product.republish";

	public static final String PARAMS_PRODUCT_IDS = "productId";

	public static final String EXTRAS_SEARCH_TYPE_INT = "searchTypeInt";

	public static final String EXTRAS_CATEPAGE = "catepage";

	public static final String EXTRAS_KEYWORD = "keyword";

	public static final String PARAMS_ORDER_STATUS = "orderStatus";

	public static final String REQUEST_TRADE_API_ORDER_EXT_SEARCH_SELLER_ORDERS = "open.api.trade.search.sellerorders";

	public static final String REQUEST_TRADE_API_ORDER_EXT_SEARCH_BUYER_ORDERS = "open.api.trade.search.buyerorders";

	public static final String REQUEST_OPEN_API_TRADE_GET_ORDERDETAIL = "open.api.trade.get.orderdetail";

	public static final String PARAMS_ORDER_ID = "orderId";

	public static final String PAMAMS_USER_TYPE = "userType";


	

}
