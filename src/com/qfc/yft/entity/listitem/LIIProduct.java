package com.qfc.yft.entity.listitem;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.qfc.yft.entity.page.PageValues;

public class LIIProduct extends JsonLii{
	/*
	 *  		"productProps": [
                    "�ɷ�:1",
                    "���:1"
                ],
                "isPrivate": 0,
                "productRegion": "�㽭.����.������",
                "productPrice": "2.00",
                "productName": "��ҳ��Ա��Ʒ����",
                "productPassword": null,
                "productId": 15209
	 */
	/*
	 *  "propString": "��Ʒ���ϣ���Ƥ��&;&��Ʒͼ������ɫ&;&��Ʒ��񣺼�Լ�����&;&���ö��󣺳���&;&��ɫ����ɫ ��ɫ&;&���1.8M 2.0M&;&֯�칤�գ�б��",
        "imageString": "MDF8cHJvZHVjdHwxMjAyNC5qcGc=&:&http://img-i.qfc.cn/upload/01/product/72/b4/12024_300X300.jpg",
        "productDesc": "<p>�ܺõ���</p><p><br /><img src=\"http://img-i.qfc.cn/upload/01/product/c9/f6/12023.jpg\" alt=\"\" /></p>",
        "isPrivate": 0,
        "productSeries": null,
        "productRegion": "����.����.������",
        "productPrice": "1.00",
        "productName": "��ů����������",
        "mainPic": "http://img-i.qfc.cn/upload/01/product/72/b4/12024_300X0.jpg",
        "productId": 20432
	 */
	
	/* currentseries
	 * {"result":[{
	 * "productDesc":"<p>�ܺã������ܺõ�<\/p><p><br /><\/p><p><img src=\"http://img.qfc.cn/upload/01/product/73/2c/689017.jpg\" alt=\"\" /><img src=\"http://img.qfc.cn/upload/01/product/7d/2d/689018.jpg\" alt=\"\" /><img src=\"http://img.qfc.cn/upload/01/product/8c/15/689019.jpg\" alt=\"\" /><img src=\"http://img.qfc.cn/upload/01/product/6d/91/689020.jpg\" alt=\"\" /><img src=\"http://img.qfc.cn/upload/01/product/4c/2f/689021.jpg\" alt=\"\" /><br /><\/p>",
	 * "isPrivate":1,
	 * "imageString":"MDF8cHJvZHVjdHw2ODkwMjIuanBn&:&http://img.qfc.cn/upload/01/product/c9/8a/689022_300X300.jpg",
	 * "privatePasswd":"49dec5fb8af4eeef7c95e7f5c66c8ae6",//TODO
	 * "productSeries":",406059,",//TODO
	 * "productName":"Ƥ��",
	 * "mainPic":"MDF8cHJvZHVjdHw2ODkwMjIuanBn&:&http://img.qfc.cn/upload/01/product/c9/8a/689022_300X300.jpg",
	 * "productId":162176}],
	 * "nextPage":1,"orderBy":"score,time","pageSize":10,"prePage":1,"hasPre":false,"order":"desc,desc","totalCount":1,"hasNext":false,"pageNo":1,"offset":0,"orderBySetted":true,"autoCount":true,"first":1,"totalPages":1}
	 */
	//mainPicX800
	//
	String productProps;
	boolean isPrivate;
	String productRegion;
	String productPrice;
	String productName;
	String productPassword;
	int productId;
	String productImgs;
	
	String productDesc;
	String mainPic;
	
	String mainPicX800;//0312
	
	public LIIProduct(){
		productProps = "";
		isPrivate = false;
		productRegion="";
		productPrice="";
		productName="";
		productPassword="";
		productImgs="";
		mainPic="";
		productDesc="";
		
		mainPicX800="";
	}
	
	final static String DIVIDER1 = "&:&",DIVIDER2 = "&;&";
	
	public String getMainPicX800() {
		return mainPicX800;
	}



	public void setMainPicX800(String mainPicX800) {
		this.mainPicX800 = mainPicX800;
	}



	public String getProductDesc() {
		return productDesc;
	}



	public String getMainPic() {
		return mainPic;
	}



	public void setMainPic(String mainPic) {
		this.mainPic = mainPic;
	}



	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}


	public String[] getNeatProductImgArray(){
		if(productImgs.isEmpty()) return new String[]{};
		if(productImgs.contains(DIVIDER2)){
			String[] result = productImgs.split(DIVIDER2);
			for(int i=0;i<result.length;i++){
				if(result[i].contains(DIVIDER1))result[i]= result[i].split(DIVIDER1)[1]; 
			}
			return result;
		}else{
			String[] result=new String[1];
			result[0]=productImgs.contains(DIVIDER1)?productImgs.split(DIVIDER1)[1]:productImgs;
			return result;
		}
	}
	
	public String getProductImg() {
		return productImgs;
	}
	
	


	public void setProductImg(String productImg) {
		this.productImgs = productImg;
	}



	public String[] getProductPropArray() {
		if(productProps.contains(DIVIDER2))return productProps.split(DIVIDER2);
		else{
			return new String[]{productProps};//0228
		}
	}
	public void setProductProps(String productProps) {
		this.productProps = productProps;
	}
	public boolean isPrivate() {
		return isPrivate;
	}
	public void setPrivate(boolean isPrivate) {
		this.isPrivate = isPrivate;
	}
	public void setPrivate(int isPrivateInt) {
		this.isPrivate = isPrivateInt!=0;
	}
	public String getProductRegion() {
		return productRegion;
	}
	public void setProductRegion(String productRegion) {
		this.productRegion = productRegion;
	}
	public String getProductPrice() {
		if(productPrice.equals("null")) productPrice = "����";
		return productPrice.equals("����")||productPrice.contains("��")||productPrice.isEmpty()?productPrice:"��"+productPrice;
	}
	public void setProductPrice(String productPrice) {
		this.productPrice = productPrice;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductPassword() {
		return productPassword;
	}
	public void setProductPassword(String productPassword) {
		this.productPassword = productPassword;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}





	@Override
	public void initJackJson(JSONObject job) {
		try {
			if(job.has(PageValues.PRODUCT_ISPRIVATE))setPrivate(job.getInt(PageValues.PRODUCT_ISPRIVATE));
			if(job.has(PageValues.PRODUCT_PRODUCTID))setProductId(job.getInt(PageValues.PRODUCT_PRODUCTID));
			if(job.has(PageValues.PRODUCT_PRODUCTNAME))setProductName(job.getString(PageValues.PRODUCT_PRODUCTNAME));
			if(job.has(PageValues.PRODUCT_PRODUCTPRICE))setProductPrice(job.getString(PageValues.PRODUCT_PRODUCTPRICE));
			if(job.has(PageValues.PRODUCT_PRODUCTREGION))setProductRegion(job.getString(PageValues.PRODUCT_PRODUCTREGION));
			if(job.has(PageValues.PRODUCT_PRODUCTIMG))setProductImg(job.getString(PageValues.PRODUCT_PRODUCTIMG));
			if(job.has(PageValues.PRODUCT_MAINPIC))setMainPic(job.getString(PageValues.PRODUCT_MAINPIC));
			if(job.has(PageValues.PRODUCT_PRODUCTDESC))setProductDesc(job.getString(PageValues.PRODUCT_PRODUCTDESC));
			if(job.has(PageValues.PRODUCT_PRODUCTPROPS))setProductProps(job.getString(PageValues.PRODUCT_PRODUCTPROPS));
			if(job.has(PageValues.PRODUCT_PRODUCTPASSWORD))setProductPassword(job.getString(PageValues.PRODUCT_PRODUCTPASSWORD));
			//���ڷ������ֶ�����һ�£����õ��ֶ��� TODO
			if(job.has("privatePasswd"))setProductPassword(job.getString("privatePasswd"));
			//if(job.has("productSeries")) ..
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//
	}
	
	
	
}
