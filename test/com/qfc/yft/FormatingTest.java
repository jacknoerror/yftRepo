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
		final String jsonStr = "{\"propMap\":{\"֯����֯\":\"б��\",\"��ɴɴ֧\":\"40\",\"γɴ�ܶ�\":\"666\",\"����\":\"���\",\"��Ҫ��;\":\"�ҷ�\",\"��;\":\"Ư��\",\"���أ�g/�O��\":\"1245kg\",\"γɴɴ֧\":\"30\",\"��ɴ�ܶ�\":\"555\",\"֯��\":\"Ƭ��\",\"Ʒ��\":\"Ȧ����֯\",\"�ܶ�ƫ��\":\"10%\",\"�ŷ�\":\"71\",\"��ˮ��\":\"15\",\"��֯��ʽ\":\"��֯\",\"��ɴ�ɷ�\":\"T30/C70 T35/C65\",\"γɴ�ɷ�\":\"T30/C70 T35/C65\"},\"sellStatus\":0,\"tradeStatus\":0,\"statusStr\":\"���ϼ�\",\"productUnit\":\"��\",\"productId\":15590,\"imageNum\":\"MDF8cHJvZHVjdHw3OTQzLmpwZw==\",\"city\":271,\"productNumber\":\"cp0000003103\",\"propStr\":\"38:7:����:���;43:0:��ˮ��:15;9:279:�ŷ�:71;11:0:���أ�g/�O��:1245kg;12:2:��֯��ʽ:��֯;44:492:֯��:Ƭ��;45:477:��;:Ư��;15:0:��ɴ�ɷ�:T30/C70 T35/C65;85:0:Ʒ��:Ȧ����֯;16:0:γɴ�ɷ�:T30/C70 T35/C65;50:0:γɴ�ܶ�:666;19:251:��ɴɴ֧:40;49:421:γɴɴ֧:30;92:511:��Ҫ��;:�ҷ�;24:0:��ɴ�ܶ�:555;94:0:�ܶ�ƫ��:10%;26:499:֯����֯:б��;\",\"auditStatus\":1,\"productStatus\":1,\"county\":3784,\"isPrivate\":false,\"productStatusQuo\":0,\"province\":23,\"companyId\":14843,\"sellEndTime\":\"\",\"productPrice\":\"����\",\"productName\":\"Ȧ����֯ T30/C70 T35/C65 /T30/C70 T35/C65  40*30 555*666 71\",\"addTime\":{\"nanos\":0,\"time\":1369877010000,\"minutes\":23,\"seconds\":30,\"hours\":9,\"month\":4,\"timezoneOffset\":-480,\"year\":113,\"day\":4,\"date\":30}}";
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
