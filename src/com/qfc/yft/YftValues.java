package com.qfc.yft;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.util.Log;

import com.qfc.yft.data.CachMsg;
import com.qfc.yft.entity.User;
import com.qfc.yft.entity.offline.JsonOffline;
import com.qfc.yft.entity.offline.OfflineData;
import com.qfc.yft.net.HttpReceiver;
import com.qfc.yft.net.JackRequestManager;
import com.qfc.yft.net.OfflineDownloadBuilder;
import com.qfc.yft.net.chat.GIMSocketServer;
import com.qfc.yft.utils.APIDesUtils;
import com.qfc.yft.utils.JackUtils;
import com.qfc.yft.utils.OfflineUtill;
import com.qfc.yft.vo.SystemParams;

public class YftValues {
	final static String TAG = "YFT_VALUES";

	public static int SCREEN_WIDTH,SCREEN_HEIGHT;
	public static boolean FZL_RELOGIN;
	
	public static final String NO_DATA = "暂无";
	
	//192.168.200.244:80  访问代理
	public static final String PROXY_HOST = "192.168.200.244";//
	public static final int 	PROXY_PORT= 80;
	
	public static final long TIMEOUT_REQUEST = 1000*60*2;
	public static final long TIMEOUT_REQUEST_10 = 1000*60*2;//
	public static final String URL_FORGETPWD="http://member.qfc.cn/my/get-password.action";
	
	//kaifa环境
//	public static final String _URL = "http://open.qfc.test.ctcn.com.cn/invoke/json"; 
////	public static final String _URL = "http://192.168.199.201:8091/invoke/json";
//	public static final String OPEN_API_APP_SECRET = "iPad_shop";
//	public static final String OPEN_API_APP_KEY = "iPad_shop";
//	public static final String URL_REGISTER="http://member.qfc.test.ctcn.com.cn/my/ipad/register.action?registType=android";
	
	public static final boolean DEBUG = true;//测试环境
	//正式环境
	public static final String _URL = "http://open.qfc.cn/invoke/json";
	public static final String OPEN_API_APP_SECRET = "Can3ppGcPQDCYbRy";
	public static final String OPEN_API_APP_KEY = "iPad_MShop";
	public static final String URL_REGISTER="http://member.qfc.cn/my/ipad/register.action?registType=android";
	
	private static final String URL_APPKEY = "openApiAppKey";
	private static final String URL_BUSICODE = "openApiBusiCode";
	private static final String URL_TIMESTAMP = "openApiTimestamp";
	private static final String URL_VALIDCODE = "openApiValidCode";
	private static final String URL_USERNAME = "userName";
	private static final String URL_PASSWORD = "password";
	private static final String URL_USERCODE = "userCode";
	private static final String URL_SHOPID = "shopId";
	private static final String URL_SERIESID = "proSeriesId";
	private static final String URL_PRODUCTID= "productId";//1128
	private static final String URL_RECOMMEND="isRecommend";
	private static final String URL_SEARCH	="keyword";
	private static final String URL_PAGESIZE="pageSize";
	private static final String URL_PAGENO	="pageNo";
	private static final String URL_SYNC 	="jsonString";//="xmlString";
	private static final String URL_ACCOUNT_ID = "accountId";
	private static final String URL_TO_MEMBER = "toMember";
	private static final String URL_FROM_MEMBER = "fromMember";
	private static final String URL_MEMBER_ID = "memberId";
	private static final String URL_FROM_ID = "fromId";
	private static final String URL_COLLECT_TYPE = "collectType";
	
	public static final String REQUEST_PATH_COMPANY_PRO ="open.api.product.findSeriesByShopIdForIphone"; //获取产品系列
	public static final String REQUEST_PATH_COMPANY_SUBPRO ="open.api.product.searchProductByShopIdAndSeriesIdForIphone"; //系列下所有产品
	public static final String REQUEST_PATH_RECOMMEND ="open.api.shop.searchShopForIphone"; //企业推荐
	public static final String REQUEST_PATH_SEARCH ="open.api.shop.searchShopForIphone"; //企业搜索接口
	public static final String REQUEST_PATH_CHECKVERSION ="cn.shop.getIOSVersionConfig"; //检查最新客户端版本
	public static final String REQUEST_PATH_LOGIN ="cn.member.sso.pointVerify"; //验证登录
	public static final String REQUEST_PATH_MEMBER_INFO ="cn.member.getMemberByUserCode";//获取用户信息
	public static final String REQUEST_PATH_COMPANY_INFO ="cn.shop.getShopAndCompanyById";//获取公司信息
//	public static final String REQUEST_PATH_SYNC = "cn.motion.basic.parseOffLineData";//同步 1113
	public static final String REQUEST_PATH_SYNC = "cn.motion.basic.parseOffLineDataForAndriod";//1122
	public static final String REQUEST_PATH_SERIESFORMOTION = "cn.product.series.getSeriesForMotion";//1128
	public static final String REQUEST_PATH_PRODUCTFORMOTION = "cn.product.getProductForMotion1";//1128
	public static final String REQUEST_CATEGORY = "cn.product.category.findAllWithJSON";
	public static final String REQUEST_CARD_SEARCH="cn.member.card.searchCardsByKeyword";//搜索人脉
	public static final String REQUEST_CARD_MY = "cn.member.card.search";//我的人脉
	public static final String REQUEST_SEARCH_PRODCUT = "open.api.product.searchProduct";//搜索产品
	public static final String REQUEST_COLLECTION_SAVE= "cn.collection.saveForOpenAPI"; //收藏新增 
	public static final String REQUEST_IS_COLLECTION_PRODUCT= "cn.collection.isCollectByProductId"; //是否收藏
	public static final String REQUEST_IS_COLLECTION_COMPANY= "cn.collection.isCollectByShopId"; //是否收藏
	public static final String REQUEST_IS_ATTENTION= "cn.attention.isAttention"; //是否收藏
	public static final String REQUEST_COLLECTION_DELETE= "cn.collection.deleteForOpenAPI"; //收藏QUXIAO
	public static final String REQUEST_FIND_COMPANY="cn.collection.findCompany";//收藏的商铺
	public static final String REQUEST_FIND_PRODUCT="cn.collection.findProduct";//收藏的产品
	public static final String REQUEST_CARD_ADD="cn.member.attention.doAttention";//收藏人脉
	public static final String REQUEST_CARD_REMOVE="cn.member.attention.cancelAttention";//取消关注人脉 
	public static final String REQUEST_SEARCH_IMAGINE="open.api.keyword.searchKeyword";
	public static final String REQUEST_GETSHOPBYUID = "cn.attention.getShopByMemberId";//
	
	public static final String DES_KEY = "88211711";//"ipad_motion_key";
	
	public static final String RESULT_SIGN = "resultSign";
	public static final String RESULT_OBJECT="resultObj";
	
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
	public static final String		FAV_MOTION="motion";
	
	public static final String SHOWPIC_PATHS ="pics";
	public static final String SHOWPIC_PAGE	 ="page";
	public static final String EXTRAS_REGISTER_URL = "url";
	public static final String EXTRAS_HUB_TAB = "hubTab";
	public static final String EXTRAS_HUB_KEYWORD = "hubKeyword";
	public static final String EXTRAS_SHOP_NAME = "shopName";
	/*public static final String EXTRAS_MEMBER_TYPE = "memberType";
	public static final String EXTRAS_SHOP_ID = "shopId";*/
	public static final String EXTRAS_PRODUCT_ID = "productId";
	public static final String EXTRAS_SHOP_TAB = "shopTab";
	public static final String EXTRAS_SHOP_MEMBER_TYPE = "shopMemberType";
	public static final String EXTRAS_ACCOUNT_ID = "accountId";
	public static final String EXTRA_CATEGORY_ID = "secondCategoryId";
	
	public static final String PREF_LOCAL = ".local";
	
	public static final int DEFULAT_PAGESIZE = 10;
	
	
	
	public enum RequestType{
			NONE,POINT_VERIFY(REQUEST_PATH_LOGIN),MEMBER_INFO(REQUEST_PATH_MEMBER_INFO),
			SHOP_INFO(REQUEST_PATH_COMPANY_INFO),SERIES_INFO(REQUEST_PATH_COMPANY_PRO),
			PRODUCT_INFO(REQUEST_PATH_COMPANY_SUBPRO),SEARCH_RECOMMEND(REQUEST_PATH_RECOMMEND),
			SEARCH(REQUEST_PATH_SEARCH),UPDATE(REQUEST_PATH_CHECKVERSION),
			SYNC(REQUEST_PATH_SYNC),SM(REQUEST_PATH_SERIESFORMOTION),PM(REQUEST_PATH_PRODUCTFORMOTION),
			CATEGORY_ALL(REQUEST_CATEGORY),CARDSEARCH(REQUEST_CARD_SEARCH),CARDMY(REQUEST_CARD_MY),
			SEARCH_PRODUCT(REQUEST_SEARCH_PRODCUT),COLLECTION_SAVE(REQUEST_COLLECTION_SAVE),COLLECTION_DELETE(REQUEST_COLLECTION_DELETE),
			FIND_COMPANY(REQUEST_FIND_COMPANY),FIND_PRODUCT(REQUEST_FIND_PRODUCT),
			CARD_ADD(REQUEST_CARD_ADD),CARD_REMOVE(REQUEST_CARD_REMOVE),SEARCH_IMAGINE(REQUEST_SEARCH_IMAGINE),
			ISCOLLECTEDSHOP(REQUEST_IS_COLLECTION_COMPANY),ISATTENTION(REQUEST_IS_ATTENTION),
			ISCOLLECTEDPRODUCT(REQUEST_IS_COLLECTION_PRODUCT),USERSHOP(REQUEST_GETSHOPBYUID)
			;
			
			
			
			private RequestType(String loc){
				this.location = loc;
			}
			private RequestType(){
				this("");
			}
			private String location;
			@Override
			public String toString() {
				return location;
			}
			public Map<String, String> getParamMap(String... params){
				Map<String, String> result = new HashMap<String, String>();
				int size = params.length;
				
				switch (this) {
				case POINT_VERIFY:
					String pwdDesStr;
					try {
						pwdDesStr= new APIDesUtils().encrypt(params[1], DES_KEY);
						result.put(URL_USERNAME, params[0]);
						result.put(URL_PASSWORD, pwdDesStr);
					} catch (Exception e) {
						Log.e(TAG, "密码加密有问题");
					}
					break;
				case MEMBER_INFO:
					result.put(URL_USERCODE,params[0] );
					break;
				case SHOP_INFO:
					result.put(URL_SHOPID, params[0] );
					break;
				case SERIES_INFO:
					result.put(URL_SHOPID, params[0]	);
					break;
				case PRODUCT_INFO://shopId,proSeriesId(系列id),pageSize(每次请求的产品数量10),pageNo(每次请求索引从1开始)
					if(size<4) break;
					result.put(URL_SHOPID,params[0]	);
					if(!params[1].isEmpty())result.put(URL_SERIESID,params[1]	);
					result.put(URL_PAGESIZE,params[2]	);
					result.put(URL_PAGENO,params[3]	);
					break;
				case SEARCH_RECOMMEND:
					if(size<3) break;
					result.put(URL_RECOMMEND, params[0]);
					result.put(URL_PAGESIZE, params[1]);
					result.put(URL_PAGENO,params[2]);
					break;
				case SEARCH:
					if(size<3) break;
					result.put(URL_SEARCH, params[0]);
					result.put(URL_PAGESIZE, params[1]);
					result.put(URL_PAGENO,params[2]);
					break;
				case UPDATE:
					break;
				case SYNC:
					result.put(URL_SYNC, params[0]);
					break;
				case SM://1128
					result.put(URL_SERIESID, params[0]);
					break;
				case PM://1128
					result.put(URL_PRODUCTID, params[0]);
					break;
				case CATEGORY_ALL:
					result.put("status","1");
					break;
				case CARDSEARCH://String keyword, Integer pageSize, Integer pageNo
					result.put(URL_SEARCH, params[0]);
					result.put(URL_PAGESIZE, params[1]);
					result.put(URL_PAGENO, params[2]);
					break;
				case CARDMY://Long accountId, Integer pageSize, Integer pageNo
					result.put(URL_ACCOUNT_ID,params[0]);
					if(params.length>1)result.put(URL_PAGESIZE, params[1]);
					if(params.length>2)result.put(URL_PAGENO, params[2]);
					break;
				case SEARCH_PRODUCT:
					result.put(URL_SEARCH,params[0]);//1.	keyword(关键字)
	//				result.put("cateCodes", "001");//2.	cateCodes(分类代码,多个用逗号分开.比如:“001,002”) //TODO
					result.put(URL_PAGESIZE, params[1]);
					result.put(URL_PAGENO, params[2]);
					break;
				case COLLECTION_SAVE:
					result.put(URL_ACCOUNT_ID, params[0]+"");//36662
	
					result.put(URL_FROM_ID, params[1]+"");//14723
	
					result.put(URL_COLLECT_TYPE, params[2]+"");//0 产品 3 商铺
	
					result.put("collectTitle", params[3]);//? 
					break;
				case COLLECTION_DELETE:
					 //Long accountId, Long fromId, Integer collectType 
					result.put(URL_ACCOUNT_ID, params[0]);//36662
	
					result.put(URL_FROM_ID, params[1]);//14723
	
					result.put(URL_COLLECT_TYPE, params[2]);//0 产品 3 商铺
					break;
				case FIND_COMPANY://Long accountId, Integer pageNo, Integer pageSize
					result.put(URL_ACCOUNT_ID,params[0]+"");
					result.put(URL_PAGESIZE, "10");
					result.put(URL_PAGENO, "1");
					break;
				case FIND_PRODUCT://Long accountId, Integer pageNo, Integer pageSize
					result.put(URL_ACCOUNT_ID,params[0]+"");
					result.put(URL_PAGESIZE, "10");
					result.put(URL_PAGENO, "1");
					break;
				case CARD_ADD://Long fromMember,Long toMember,String fromIp
					result.put(URL_FROM_MEMBER,params[0] );
					result.put(URL_TO_MEMBER,params[1]);
					result.put("fromIp","wtf");
					break;
				case CARD_REMOVE:
					result.put(URL_FROM_MEMBER,params[0] );
					result.put(URL_TO_MEMBER,params[1]);
					break;
				case SEARCH_IMAGINE://{keyword=h, pageSize=3, searchType=product}
					result.put(URL_SEARCH, params[0]);
					result.put(URL_PAGESIZE, params[1]);
					result.put("searchType", params[2]);
					break;
				case ISCOLLECTEDPRODUCT://Integer productId, Long accountId
					result.put(URL_PRODUCTID, params[0]);
					result.put(URL_ACCOUNT_ID, params[1]);
					break;
				case ISCOLLECTEDSHOP://Long shopId, Long accountId
					result.put(URL_SHOPID, params[0]);
					result.put(URL_ACCOUNT_ID, params[1]);
					break;
					
				case ISATTENTION://Long memberId, Long accountId
					result.put(URL_MEMBER_ID, params[0]);
					result.put(URL_ACCOUNT_ID, params[1]);
					break;
				case USERSHOP:
					result.put(URL_MEMBER_ID,params[0]);
					break;
				default:
					break;
				}
				
				return result;
			}
		}

	public static String getHTTPBodyString(RequestType type,String... params){
		Map<String, String> paramKV = new HashMap<String, String>();
		paramKV.put(URL_APPKEY,OPEN_API_APP_KEY);
		paramKV.put(URL_BUSICODE, type.toString());
		paramKV.put(URL_TIMESTAMP, JackUtils.getTimeStamp());//TODO 时区
		paramKV.putAll(type.getParamMap(params));
		
		return finishTheURL(paramKV);
		
	}
	
	public static JSONObject getResultObject(String json) throws JSONException{
		JSONObject job = new JSONObject(json);
		if(job.has(RESULT_SIGN)){
			if(job.getBoolean(RESULT_SIGN)){
				return job.getJSONObject(RESULT_OBJECT);
			}else{
				//TODO 请求失败
			}
		}
		return null;
	}
	
	private static String finishTheURL(Map<String, String> map){
		StringBuffer url,valid;
		url = new StringBuffer();
		valid = new StringBuffer();
		//排序
		String[] arrays = new String[]{};
		arrays = map.keySet().toArray(arrays);
		Arrays.sort(arrays);
		//验签
			for(String str : arrays){
				valid.append(str)
					.append(map.get(str));
					url.append(str)
						.append("=")
						.append(new String(map.get(str)))
						.append("&");
			}
		valid.append(OPEN_API_APP_SECRET);
//		Log.i(TAG, "valid::"+valid);
		Log.i(TAG, "url::"+url);
		//拼接
		url.append(URL_VALIDCODE).append("=").append(JackUtils.getMD5(valid.toString()));
		return url.toString();
	}
	
	public static void tryGetOfflineDataHere(HttpReceiver receiver){
   	 if(receiver==null)return;
   	 if(YftData.data().isMe()&&YftData.data().getMe()!=null){
   		 int shopId = YftData.data().getMe().getShopId();
   		 
	 		//try local
	 		String param = YftValues.getOffdataAsParam(shopId);
//	 		JackUtils.writeToSomeWhere(YftApplication.getApp(), param);
	 		//do network
	 		if(!param.isEmpty())JackRequestManager.getInstance().tryRequest(YftValues.getHTTPBodyString(RequestType.SYNC, param), receiver, 3000);
   	 }else{
   		 Log.e(TAG, "try offline data failed");
   	 }
	}
	public static String getOffdataAsParam(int shopId){
   	 JsonOffline od = YftData.data().getOfflineData();
			String param="";
			try {
				if(od==null){
						param = OfflineUtill.initOfflineJsonStr(shopId+"");
				}else{
					param = od.toJsonStr();
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return param;
    }
	
	public static String getMyLocalPath(){
		User user = YftData.data().getMe();
		if(user==null) return "00";
		int shid = user.getShopId();
		return Environment.getExternalStorageDirectory().getPath()+"/qfc/imgs/"+shid;
	}

	
	public static void logout(){
		try{
			YftApplication.getApp().unregisterReceiver(YftApplication.getApp().getUIBadgeReceiver());//TODO tobe test
			YftApplication.getApp().stopService(new Intent(YftApplication.getApp(), GIMSocketServer.class));
			if(!OfflineDownloadBuilder.shouldStart()){
				OfflineDownloadBuilder.cancel();//0421
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		CachMsg.getInstance().clear();//0319
		YftData.data().destroy();
		//fzl
		SystemParams.getInstance().clear();
		CachMsg.getInstance().clear();
	}


	public static final String EXTRAS_SINGLEBACK = "singleback";

	public static final String HINT_NOCHAT = "该用户没有纺织聊账号，请考虑其他联系方式";

	

	//	private static NotificationManager nm;
	public static int notification_id = 19172439;
	
	/**
	 * ugly ,temp
	 */
	public static  boolean isopen(Context context) {
		if(!JackUtils.isOpenNetwork()){
				OfflineData offlineData = YftData.data().getOfflineData();
				if(offlineData!=null){
					JackUtils.showToast(context, "请检查您的网络或切换到离线模式");
				}else{
					JackUtils.showToast(context, "请检查您的网络");
				}
				return false;
			}
		return true;
	}
	
}
