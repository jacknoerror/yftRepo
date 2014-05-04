package com.qfc.yft.entity.offline;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.qfc.yft.entity.JackJson;

public class OffProduct extends JackJson implements IOfflineConst{
	/*
	 * {
    "resultSign": "true",
    "resultObj": {
        "productDesc": "<table class=\"table  \" border=\"0\" height=\"164\" width=\"591\"><tbody><tr><td class=\"c1\">֯����֯</td>                        <td class=\"c1\">��ɴɴ֧</td>            <td class=\"c2\">&nbsp;40&nbsp;</td>          </tr>          <tr>            <td class=\"c1\">γɴ�ܶ�</td>                        <td class=\"c1\">����</td>            <td class=\"c2\">&nbsp;ë��&nbsp;</td>          </tr>          <tr>            <td class=\"c1\">��Ҫ��;</td>                        <td class=\"c1\">��;</td>            <td class=\"c2\">&nbsp;Ⱦɫ&nbsp;</td>          </tr>          <tr>            <td class=\"c1\">���أ�g/�O��</td>                        <td class=\"c1\">γɴɴ֧</td>            <td class=\"c2\">&nbsp;32&nbsp;</td>          </tr>          <tr>            <td class=\"c1\">��ɴ�ܶ�</td>                        <td class=\"c1\">Ʒ��</td>            <td class=\"c2\">&nbsp;��������&nbsp;</td>          </tr>          <tr>            <td class=\"c1\">�ܶ�ƫ��</td>                        <td class=\"c1\">�ŷ�</td>            <td class=\"c2\">&nbsp;63&nbsp;</td>          </tr>          <tr>            <td class=\"c1\">��֯��ʽ</td>                        <td class=\"c1\">��ɴ�ɷ�</td>            <td class=\"c2\">&nbsp;C100&nbsp;</td>          </tr>          <tr>            <td class=\"c1\">γɴ�ɷ�</td>            </tr></tbody></table>",
        "propString": "���ߣ�ë��&;&�ŷ���116&;&��֯��ʽ����֯&;&��ɴ�ɷ֣�T45/C55&;&��;��Ⱦɫ&;&Ʒ��������˿��˿��&;&γɴ�ɷ֣�T45/C55&;&γɴ�ܶȣ�31&;&��ɴɴ֧��60&;&γɴɴ֧��80&;&��ɴ�ܶȣ�45&;&��Ҫ��;����װ&;&֯����֯��˫��б",
        "isPrivate": 0,
        "privatePasswd": "75e27b6f31c1f9b8e3152e20589e7892",
        "productSeries": ",2722,",
        "productName": "����˿��˿�� T45/C55/T45/C55 60*80 45*31 116",
        "productId": 20210
    }
}*/
	
	String updateTime ;
	int status;
	OffImage productImage;
	//product300XImage �� productX800Image
	OffImage product300XImage;
	OffImage productX800Image;
	JSONArray productPicsArray;
	int productId;
	
	public OffProduct(){
		productImage = new OffImage();
		productPicsArray = new JSONArray();
		product300XImage = new OffImage();//0417
		productX800Image = new OffImage();
	}
	public OffProduct(JSONObject job	){
		super(job);
	}
	
	@Override
	public JSONObject toJsonObj() {
		JSONObject job = new JSONObject();
		try {
			job.put(OFF_UPDATETIME, updateTime);
			job.put(OFF_STATUS, status+"");//1209
			job.put(OFF_PRODUCTIMAGE, productImage.toJsonObj());
			job.put(OFF_PRODUCTPICSARRAY, productPicsArray);
			job.put(OFF_PRODUCTID, productId+"");//1209
			
			job.put(OFF_PRODUCT_X800_IMAGE, productX800Image.toJsonObj());//0417
			job.put(OFF_PRODUCT300X_IMAGE, product300XImage.toJsonObj());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return job;
	}
	@Override
	public void initJackJson(JSONObject job) {
		updateTime=job.optString(OFF_UPDATETIME  );
		status=job.optInt(OFF_STATUS  );
		productImage=new OffImage(job.optJSONObject(OFF_PRODUCTIMAGE  ));
		productPicsArray=job.optJSONArray(OFF_PRODUCTPICSARRAY  );
		productId=job.optInt(OFF_PRODUCTID  );
		
		productX800Image =new OffImage(job.optJSONObject(OFF_PRODUCT_X800_IMAGE));//0417
		product300XImage =new OffImage(job.optJSONObject(OFF_PRODUCT300X_IMAGE));
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public OffImage getProductImage() {
		return productImage;
	}
	public void setProductImage(OffImage productImage) {
		this.productImage = productImage;
	}
	public JSONArray getProductPicsArray() {
		return productPicsArray;
	}
	public void setProductPicsArray(JSONArray productPicsArray) {
		this.productPicsArray = productPicsArray;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public final OffImage getProduct300XImage() {
		return product300XImage;
	}
	public final void setProduct300XImage(OffImage product300xImage) {
		product300XImage = product300xImage;
	}
	public final OffImage getProductX800Image() {
		return productX800Image;
	}
	public final void setProductX800Image(OffImage productX800Image) {
		this.productX800Image = productX800Image;
	}
	
	
	
}
