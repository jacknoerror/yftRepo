package com.qfc.yft;

import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

import android.test.AndroidTestCase;
import android.util.Log;

import com.qfc.yft.util.HandleStrings;
import com.qfc.yft.util.JackUtils;


public class FormatingTest extends AndroidTestCase {
	private final String TAG  = "UNIT_TEST";

	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		super.setUp();
	}
	
	public void fromJsonstrToCode() throws JSONException{
		convertJsonToCode();
		String result = HandleStrings.readAndConvert3(mContext);
		assertNotNull(result);
		Log.i(TAG, result);
	}
	
	public void convertJsonToCode() throws JSONException{
		final String jsonStr = "{\"addrZipCode\":\"345467\",\"addrConsignee\":\"测试用的\",\"orderIp\":\"127.0.0.1\",\"tradeModel\":0,\"shipFee\":0,\"addrAddress\":\"北京北京东城区erfasdfasd\",\"cashStatus\":0,\"productType\":0,\"shipType\":0,\"orderAmount\":12,\"sellerAccountId\":36745,\"autorecDate\":30,\"orderProducts\":[{\"normValue\":{\"颜色\":\"驼色\",\"规格\":\"1.8M\"},\"mallType\":\"006\",\"productImage\":\"MDF8cHJvZHVjdHwxMjcyNS5qcGc=\",\"normId\":1140,\"productUnit\":\"件\",\"sampleFlag\":0,\"productId\":20435,\"productSn\":\"cp0000003907\",\"productNumber\":6,\"productDesc\":\"<ul style=\\\"PADDING-BOTTOM: 0px; LIST-STYLE-TYPE: none; MARGIN: 0px 0px 8px; PADDING-LEFT: 0px; PADDING-RIGHT: 0px; PADDING-TOP: 0px\\\">\\r\\n\\t<li style=\\\"PADDING-BOTTOM: 0px; MARGIN: 0px 0px 0px 4px; PADDING-LEFT: 0px; PADDING-RIGHT: 0px; FONT-SIZE: 14px; OVERFLOW: visible; FONT-WEIGHT: bold; PADDING-TOP: 0px; list-style-_pos_: inside\\\">\\r\\n\\t\\t品名：100%人棉数码印花面料\\r\\n\\t<\\/li>\\r\\n\\t<li style=\\\"PADDING-BOTTOM: 0px; MARGIN: 0px; PADDING-LEFT: 0px; PADDING-RIGHT: 0px; OVERFLOW: visible; PADDING-TOP: 0px\\\">\\r\\n\\t\\t<p style=\\\"PADDING-BOTTOM: 0px; LINE-HEIGHT: 1.5; MARGIN: 0px; PADDING-LEFT: 0px; PADDING-RIGHT: 0px; CLEAR: both; PADDING-TOP: 0px\\\">\\r\\n\\t\\t\\t成分及含量：100%人棉\\r\\n\\t\\t<\\/p>\\r\\n\\t\\t<p style=\\\"PADDING-BOTTOM: 0px; LINE-HEIGHT: 1.5; MARGIN: 0px; PADDING-LEFT: 0px; PADDING-RIGHT: 0px; CLEAR: both; PADDING-TOP: 0px\\\">\\r\\n\\t\\t\\t纱支：40S*40S\\r\\n\\t\\t<\\/p>\\r\\n\\t\\t<p style=\\\"PADDING-BOTTOM: 0px; LINE-HEIGHT: 1.5; MARGIN: 0px; PADDING-LEFT: 0px; PADDING-RIGHT: 0px; CLEAR: both; PADDING-TOP: 0px\\\">\\r\\n\\t\\t\\t密度：100*80\\r\\n\\t\\t<\\/p>\\r\\n\\t\\t<p style=\\\"PADDING-BOTTOM: 0px; LINE-HEIGHT: 1.5; MARGIN: 0px; PADDING-LEFT: 0px; PADDING-RIGHT: 0px; CLEAR: both; PADDING-TOP: 0px\\\">\\r\\n\\t\\t\\t幅宽：145CM\\r\\n\\t\\t<\\/p>\\r\\n\\t\\t<p style=\\\"PADDING-BOTTOM: 0px; LINE-HEIGHT: 1.5; MARGIN: 0px; PADDING-LEFT: 0px; PADDING-RIGHT: 0px; CLEAR: both; PADDING-TOP: 0px\\\">\\r\\n\\t\\t\\t主要用途：服装\\r\\n\\t\\t<\\/p>\\r\\n\\t\\t<p style=\\\"PADDING-BOTTOM: 0px; LINE-HEIGHT: 1.5; MARGIN: 0px; PADDING-LEFT: 0px; PADDING-RIGHT: 0px; CLEAR: both; PADDING-TOP: 0px\\\">\\r\\n\\t\\t\\t克重：118g/平方米\\r\\n\\t\\t<\\/p>\\r\\n\\t\\t<p style=\\\"PADDING-BOTTOM: 0px; LINE-HEIGHT: 1.5; MARGIN: 0px; PADDING-LEFT: 0px; PADDING-RIGHT: 0px; CLEAR: both; PADDING-TOP: 0px\\\">\\r\\n\\t\\t\\t产地：广州市海珠区\\r\\n\\t\\t<\\/p>\\r\\n\\t\\t<p style=\\\"PADDING-BOTTOM: 0px; LINE-HEIGHT: 1.5; MARGIN: 0px; PADDING-LEFT: 0px; PADDING-RIGHT: 0px; CLEAR: both; PADDING-TOP: 0px\\\">\\r\\n\\t\\t\\t发货时间：2天内\\r\\n\\t\\t<\\/p>\\r\\n\\t<\\/li>\\r\\n<\\/ul>\",\"orderProdId\":3184,\"imageUrl300X\":\"http://img-i.qfc.cn/upload/01/product/14/8e/12725_300X0.jpg\",\"imageUrl300X300\":\"http://img-i.qfc.cn/upload/01/product/14/8e/12725_300X300.jpg\",\"productName\":\"多件套\",\"productPrice\":2,\"orderId\":9355,\"cateCode\":\"006\"}],\"attachInfo\":{\"addrZipCode\":\"345467\",\"addrConsignee\":\"测试用的\",\"addrCounty\":419,\"addrProvince\":2,\"addrCity\":35,\"addrTelephone\":\"135698745621\",\"addrAddress\":\"erfasdfasd\",\"attachId\":22769,\"orderId\":9355},\"companyInfo\":{\"compContacterSex\":0,\"compId\":14495,\"createTime\":{\"nanos\":0,\"time\":1333260700000,\"minutes\":11,\"seconds\":40,\"hours\":14,\"month\":3,\"year\":112,\"timezoneOffset\":-480,\"day\":0,\"date\":1},\"shopValidBeginTime\":{\"nanos\":0,\"time\":1345564800000,\"minutes\":0,\"seconds\":0,\"hours\":0,\"month\":7,\"year\":112,\"timezoneOffset\":-480,\"day\":3,\"date\":22},\"CompMainProduct\":\"企业,,,,,\",\"compTelphone\":\"28974642\",\"compTelIdd\":\"86\",\"compMainSite\":\"www\",\"compProv\":12,\"compProfession\":\"03\",\"compFax\":\"111,111,111111111111\",\"compCounty\":null,\"compNature\":\"\",\"shopValidEndTime\":{\"nanos\":0,\"time\":1408550400000,\"minutes\":0,\"seconds\":0,\"hours\":0,\"month\":7,\"year\":114,\"timezoneOffset\":-480,\"day\":4,\"date\":21},\"compDelete\":0,\"compRemarks\":null,\"compLogoImg\":\"01|company|4816.jpg\",\"compModel\":\"2\",\"compPurchaseProduct\":\",,,,,\",\"privateHallPassword\":null,\"publishTime\":{\"nanos\":0,\"time\":1377594052000,\"minutes\":0,\"seconds\":52,\"hours\":17,\"month\":7,\"year\":113,\"timezoneOffset\":-480,\"day\":2,\"date\":27},\"compEmail\":\"\",\"compCheckTime\":{\"nanos\":0,\"time\":1377594052000,\"minutes\":0,\"seconds\":52,\"hours\":17,\"month\":7,\"year\":113,\"timezoneOffset\":-480,\"day\":2,\"date\":27},\"mallType\":0,\"compName\":\"公共买家账户\",\"compContacter\":\"联系儿女\",\"compCity\":132,\"compMobile\":\"\",\"compTelDdd\":\"575\",\"compChecker\":\"admin\",\"compAddress\":\"创意大厦\",\"shopName\":\"公共买家账户\",\"shopDomain\":\"sp00000224\",\"compStatus\":0,\"compTrustGrade\":1,\"compIntro\":\"12331\",\"shopCreditRating\":2,\"compZipCode\":\"\"},\"isMarster\":0,\"orderOverdueTime\":{\"nanos\":750000000,\"time\":1403938288750,\"minutes\":51,\"seconds\":28,\"hours\":14,\"month\":5,\"year\":114,\"timezoneOffset\":-480,\"day\":6,\"date\":28},\"orderId\":9355,\"orderPayed\":0,\"buyerCompanyName\":\"公共买家账户\",\"orderDiscount\":0,\"sellerCompanyId\":14920,\"shipStatus\":0,\"urgeSign\":0,\"buyerCompanyId\":14495,\"orderStatusCh\":\"买家已下单，等待付款\",\"mallType\":\"006\",\"orderNote\":\"\",\"withdrawAccountId\":11745,\"sellerCompanyName\":\"xiaoya124\",\"sellerMainSite\":\"www\",\"orderTime\":{\"nanos\":470000000,\"time\":1401346602470,\"minutes\":56,\"seconds\":42,\"hours\":14,\"month\":4,\"year\":114,\"timezoneOffset\":-480,\"day\":4,\"date\":29},\"orderStatus\":\"wait_for_confirm\",\"buyerAccountId\":35811,\"judgeStatus\":0,\"orderNo\":\"D2014052900001\",\"payStatus\":0,\"needSellerConfirm\":0,\"payType\":0,\"addrTelephone\":\"135698745621\",\"addrMobile\":null,\"productAmount\":12}";
		JSONObject job = new JSONObject(jsonStr);
		Iterator<String> it = job.keys();
		StringBuffer sb = new StringBuffer();
		while(it.hasNext()){
			String name = it.next();
			String line = String.format("private %s %s;", "String",name);
			sb.append(line);
//			write(name);
		}
//		Log.i(TAG, sb.toString());
		JackUtils.writeToSomeWhere(mContext, sb.toString());
	}

	/**
	 * @param name
	 */
	private void write(String name) {
		JackUtils.writeToFile(getContext(), "Format", name);
	}
	
	
}
