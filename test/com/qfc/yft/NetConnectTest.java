package com.qfc.yft;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.net.UnknownHostException;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.test.AndroidTestCase;
import android.util.Log;

import com.qfc.yft.data.MyData;
import com.qfc.yft.data.TestConst;
import com.qfc.yft.net.HttpRequestTask;
import com.qfc.yft.net.NetStrategies;
import com.qfc.yft.net.action.ActionRequestImpl;
import com.qfc.yft.net.action.album.SearchAlbumReq;
import com.qfc.yft.net.action.album.SearchPicsByAlbumIdReq;
import com.qfc.yft.net.action.collection.IsCollectByProductIdReq;
import com.qfc.yft.net.action.member.PointVerifyForIMReq;
import com.qfc.yft.net.action.member.SearchCardsByKeywordReq;
import com.qfc.yft.net.action.member.SearchManageProductReq;
import com.qfc.yft.net.action.product.FindAllSeriesReq;
import com.qfc.yft.net.action.product.FindSeriesByShopIdForIphoneReq;
import com.qfc.yft.net.action.product.GetProductForMotion1Req;
import com.qfc.yft.net.action.product.SearchProductByShopIdAndSeriesIdForIphoneReq;
import com.qfc.yft.net.action.product.SearchProductReq;
import com.qfc.yft.net.action.trade.GetBuyerOrdersReq;
import com.qfc.yft.net.action.upload.UploadToAlbumReq;
import com.qfc.yft.util.JackRexUtil;
import com.qfc.yft.util.JackRexUtil.JackReRules;
import com.qfc.yft.util.JackUtils;

public class NetConnectTest extends AndroidTestCase {
	final String TAG = "UNIT_TEST";

	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}

	private String test(int apiId) throws SocketTimeoutException,
			UnknownHostException, IOException {
		ActionRequestImpl ari = null;
		String result = null;

		
		switch (apiId) {
		case 0:
//			ari = new GetProductForMotion1Req(162153);
			ari = new GetProductForMotion1Req(20056);
			break;
		case 1:
			ari = new SearchProductByShopIdAndSeriesIdForIphoneReq(402746, 11, 1, 406063);
			break;
		case 2://pid,aid
			ari = new IsCollectByProductIdReq(14886, 20216 );
//			ari = new IsCollectByProductIdReq(2043612, 198407);
			break;
		case 3://搜索产品
			ari = new SearchProductReq("布", 5, 1);
			break;
		case 4://搜索人脉
			ari = new SearchCardsByKeywordReq("丁", 5, 1);
			break;
		case 5://
//			ari = new GetProductReq(20056);
			break;
		case 6://相册列表
//			test(-1);
			MyData.data().setUserCode(TestConst.usercode);
//			MyData.data().setSessionId("e2619fc3af334d6da6db3c089f9e5def");
			ari = new SearchAlbumReq(TestConst.compId, 1, 10);
			break;
		case 7:
			ari = new FindSeriesByShopIdForIphoneReq(402746);//wanyu..
			break;
		case 8://pics in album
			MyData.data().setUserCode(TestConst.usercode);
			ari = new SearchPicsByAlbumIdReq(14843, TestConst.albumId, 1);
			break;
		case 9://获取系列
			ari = new FindAllSeriesReq(14843);
			break;
		case 10://产品管理列表
			ari = new SearchManageProductReq(null, null, null, null, 14843,1);
			break;
		case 11://卖家订单
//			ari = new GetSellerOrdersReq(14843, null, 1, 10);
//			ari = new GetSellerOrdersReq(TestConst.userId, null, 1, 10);
			ari = new GetBuyerOrdersReq(36745, null, 1, 10);
			break;
		default://login
			ari = new PointVerifyForIMReq("ydspipad1", "333333a");
			break;
		}
		result = NetStrategies.doHttpRequest(ari.toHttpBody());
		if (null != ari)
			JackUtils.writeToFile(getContext(), ari.getApiName(), result);

		return result;
	}

	/**
	 * @throws SocketTimeoutException
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public void testSayYes() throws SocketTimeoutException,UnknownHostException, IOException {
		String result = "";

		// login:26 ;
		result = test(8);
		Log.i(TAG, "result=>" + result);
		// Log.i(TAG,"mtime2=>"+System.currentTimeMillis());
		assertTrue(result.contains("true"));
	}

	public void testSayNo() {
	}

	
	  public void testRE(){ 
		  boolean check; // check =
//	  JackRexUtil.checkRE(JackReRules.RE_RULE_EMAIL, "o@k.cn"); // check =
//	  "555d".matches("[0-9]+?"); // check =
//	  JackRexUtil.hasRE(JackReRules.RE_RULE_HAS_DIGIT,"o@k.cn44444"); check =
//	  JackRexUtil.checkRE(JackReRules.RE_RULE_A_Z_A_Z0_9_$_SIZE,"12345123456789067890-");
	   check = JackRexUtil.checkRE(JackRexUtil.JackReRules.RE_RULE_ONLY_DIGIT, "141");
	   assertTrue(check); 
		  
	  }
	 
	public void testRE2(){
		String str = "123$%^abc8_9.0";
		str = str.replaceAll("[^0-9]", "");
		assertEquals(str, "123890");
	}

	public void testNetwork() {
		ConnectivityManager connManager = (ConnectivityManager) MyApplication
				.app().getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = connManager.getActiveNetworkInfo();
		Log.i(TAG, info.getType() + "+" + info.getTypeName());
	}
	
	
	
	public void calDpPx(){
		int a = JackUtils.px2dip(mContext, 135);
		Log.i(TAG, "p2d:"+a);
		int b = JackUtils.dip2px(mContext, 20);
		Log.i(TAG, "d2p:"+b);
		assertNotSame(a*b, 0);
	}
	
	public void testUpload	() throws SocketTimeoutException, UnknownHostException, IOException{

		MyData.data().setUserCode(TestConst.usercode);
		
		Bitmap bitmap = getBitmap(getContext().getResources(),R.drawable.ic_launcher);
		byte[] bytes = Bitmap2Bytes(bitmap);
		
		
		UploadToAlbumReq uploadToAlbumReq = new UploadToAlbumReq(TestConst.albumId, bytes, "没有描述");
		String result = uploadToAlbumReq.doUpload3();
		assertNotNull(result);
		Log.i(TAG, result);
	}

	public void testBitmapBytes() throws UnsupportedEncodingException{
		Bitmap bitmap = getBitmap(getContext().getResources(),R.drawable.ic_launcher);
		byte[] bytes = Bitmap2Bytes(bitmap);
		String string = new String(bytes,"UTF-8");
		Log.i(TAG, "---"+string);
		assertNotSame(string, "");
//		JackUtils.writeToSomeWhere(getContext(), string);
	}
	
	/**
	 * @param res 
	 * @return 
	 * 
	 */
	public Bitmap getBitmap(Resources res, int rid) {
		Bitmap bmp = BitmapFactory.decodeResource(res, rid);
		return bmp;
	}
	
	 public byte[] Bitmap2Bytes(Bitmap bm) {
		 ByteArrayOutputStream baos = new ByteArrayOutputStream();
		 bm.compress(Bitmap.CompressFormat.PNG, 10, baos);
		 return baos.toByteArray();//close?
	 }

	 public void testDictTry() throws SocketTimeoutException, UnknownHostException, IOException{
		 String string = "";
		 
		 string = HttpRequestTask.doHttpRequest("http://dict.cn/mini.php?q=jack", null);
		 Log.i(TAG, "---"+string);
		 assertNotSame(string, "");
		 
	 }
	 
	 
	 public void testURLDecode() throws UnsupportedEncodingException{

		 String result = URLDecoder.decode("%E7%9B%B8%E5%86%8C%E7%A9%BA%E9%97%B4%E4%B8%8D%E5%AD%98%E5%9C%A8", "utf-8");
		 Log.i(TAG, result);
	 }
}
