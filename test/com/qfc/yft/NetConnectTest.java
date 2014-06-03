package com.qfc.yft;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.test.AndroidTestCase;
import android.util.Log;

import com.qfc.yft.data.MyData;
import com.qfc.yft.net.NetStrategies;
import com.qfc.yft.net.action.ActionRequestImpl;
import com.qfc.yft.net.action.album.SearchAlbumReq;
import com.qfc.yft.net.action.collection.IsCollectByProductIdReq;
import com.qfc.yft.net.action.member.SearchCardsByKeywordReq;
import com.qfc.yft.net.action.product.GetProductForMotion1Req;
import com.qfc.yft.net.action.product.GetProductReq;
import com.qfc.yft.net.action.product.SearchProductByShopIdAndSeriesIdForIphoneReq;
import com.qfc.yft.net.action.product.SearchProductReq;
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

		final String usercode = "de63e674ab5c4204988f01a89714efe2";//ydspipad1
		
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
			ari = new GetProductReq(20056);
			break;
		case 6://相册列表
			MyData.data().setUserCode(usercode);
			ari = new SearchAlbumReq(14843, 1, 10);
			break;
		default:
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
	public void testSayYes() throws SocketTimeoutException,
			UnknownHostException, IOException {
		String result = "";

		// login:26 ;
		result = test(6);
		Log.i(TAG, "result=>" + result);
		// Log.i(TAG,"mtime2=>"+System.currentTimeMillis());
	}

	public void testSayNo() {
	}

	/*
	 * public void testRE(){ boolean check; // check =
	 * JackRexUtil.checkRE(JackReRules.RE_RULE_EMAIL, "o@k.cn"); // check =
	 * "555d".matches("[0-9]+?"); // check =
	 * JackRexUtil.hasRE(JackReRules.RE_RULE_HAS_DIGIT,"o@k.cn44444"); check =
	 * JackRexUtil
	 * .checkRE(JackReRules.RE_RULE_A_Z_A_Z0_9_$_SIZE,"12345123456789067890-");
	 * // check = JackRexUtil.checkPwdComplex("wdsr-1216"); assertTrue(check); }
	 */

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

}
