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
        "productDesc": "<table class=\"table  \" border=\"0\" height=\"164\" width=\"591\"><tbody><tr><td class=\"c1\">织物组织</td>                        <td class=\"c1\">经纱纱支</td>            <td class=\"c2\">&nbsp;40&nbsp;</td>          </tr>          <tr>            <td class=\"c1\">纬纱密度</td>                        <td class=\"c1\">布边</td>            <td class=\"c2\">&nbsp;毛边&nbsp;</td>          </tr>          <tr>            <td class=\"c1\">主要用途</td>                        <td class=\"c1\">用途</td>            <td class=\"c2\">&nbsp;染色&nbsp;</td>          </tr>          <tr>            <td class=\"c1\">克重（g/㎡）</td>                        <td class=\"c1\">纬纱纱支</td>            <td class=\"c2\">&nbsp;32&nbsp;</td>          </tr>          <tr>            <td class=\"c1\">经纱密度</td>                        <td class=\"c1\">品名</td>            <td class=\"c2\">&nbsp;贡缎坯布&nbsp;</td>          </tr>          <tr>            <td class=\"c1\">密度偏差</td>                        <td class=\"c1\">门幅</td>            <td class=\"c2\">&nbsp;63&nbsp;</td>          </tr>          <tr>            <td class=\"c1\">编织方式</td>                        <td class=\"c1\">经纱成分</td>            <td class=\"c2\">&nbsp;C100&nbsp;</td>          </tr>          <tr>            <td class=\"c1\">纬纱成分</td>            </tr></tbody></table>",
        "propString": "布边：毛边&;&门幅：116&;&编织方式：梭织&;&经纱成分：T45/C55&;&用途：染色&;&品名：复合丝高丝宝&;&纬纱成分：T45/C55&;&纬纱密度：31&;&经纱纱支：60&;&纬纱纱支：80&;&经纱密度：45&;&主要用途：服装&;&织物组织：双面斜",
        "isPrivate": 0,
        "privatePasswd": "75e27b6f31c1f9b8e3152e20589e7892",
        "productSeries": ",2722,",
        "productName": "复合丝高丝宝 T45/C55/T45/C55 60*80 45*31 116",
        "productId": 20210
    }
}*/
	
	String updateTime ;
	int status;
	OffImage productImage;
	//product300XImage 和 productX800Image
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
