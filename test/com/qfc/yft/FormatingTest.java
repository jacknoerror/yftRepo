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
		final String jsonStr = "{\"propMap\":{\"织物组织\":\"斜纹\",\"经纱纱支\":\"40\",\"纬纱密度\":\"666\",\"布边\":\"光边\",\"主要用途\":\"家纺\",\"用途\":\"漂白\",\"克重（g/㎡）\":\"1245kg\",\"纬纱纱支\":\"30\",\"经纱密度\":\"555\",\"织机\":\"片梭\",\"品名\":\"圈绒梭织\",\"密度偏差\":\"10%\",\"门幅\":\"71\",\"缩水率\":\"15\",\"编织方式\":\"梭织\",\"经纱成分\":\"T30/C70 T35/C65\",\"纬纱成分\":\"T30/C70 T35/C65\"},\"sellStatus\":0,\"tradeStatus\":0,\"statusStr\":\"已上架\",\"productUnit\":\"件\",\"productId\":15590,\"imageNum\":\"MDF8cHJvZHVjdHw3OTQzLmpwZw==\",\"city\":271,\"productNumber\":\"cp0000003103\",\"propStr\":\"38:7:布边:光边;43:0:缩水率:15;9:279:门幅:71;11:0:克重（g/㎡）:1245kg;12:2:编织方式:梭织;44:492:织机:片梭;45:477:用途:漂白;15:0:经纱成分:T30/C70 T35/C65;85:0:品名:圈绒梭织;16:0:纬纱成分:T30/C70 T35/C65;50:0:纬纱密度:666;19:251:经纱纱支:40;49:421:纬纱纱支:30;92:511:主要用途:家纺;24:0:经纱密度:555;94:0:密度偏差:10%;26:499:织物组织:斜纹;\",\"auditStatus\":1,\"productStatus\":1,\"county\":3784,\"isPrivate\":false,\"productStatusQuo\":0,\"province\":23,\"companyId\":14843,\"sellEndTime\":\"\",\"productPrice\":\"面议\",\"productName\":\"圈绒梭织 T30/C70 T35/C65 /T30/C70 T35/C65  40*30 555*666 71\",\"addTime\":{\"nanos\":0,\"time\":1369877010000,\"minutes\":23,\"seconds\":30,\"hours\":9,\"month\":4,\"timezoneOffset\":-480,\"year\":113,\"day\":4,\"date\":30}}";
		JSONObject job = new JSONObject(jsonStr);
		Iterator<String> it = job.keys();
		StringBuffer sb = new StringBuffer();
		while(it.hasNext()){
			String name = it.next();
			String line = String.format("private String %s;", name);
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
