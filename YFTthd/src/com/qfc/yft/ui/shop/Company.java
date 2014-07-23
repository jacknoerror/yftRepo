package com.qfc.yft.ui.shop;

import org.json.JSONException;
import org.json.JSONObject;

import com.qfc.yft.data.Const;

import android.util.Log;

public class Company {
	private final String TAG = Company.class.getSimpleName();
	/*/
	 * {"region":"浙江-绍兴-绍兴县","compModel":"制造商、工厂,贸易公司,采购办事处","compPurchaseProduct":"涤纶,人棉巴里纱",
	"compPicString":"01|galary|10252.jpg&:&http://img-i.qfc.cn/upload/01/galary/fe/00/10252.jpg&;&01|galary|9972.jpg
		&:&http://img-i.qfc.cn/upload/01/galary/6e/a3/9972.jpg&;&01|galary|7832.jpg&:&http://img-i.qfc.cn/upload/01/galary/b2/16/7832.jpg
		&;&01|galary|10245.jpg&:&http://img-i.qfc.cn/upload/01/galary/3c/7e/10245.jpg
		&;&01|galary|10251.jpg&:&http://img-i.qfc.cn/upload/01/galary/40/1c/10251.jpg
		&;&01|galary|10244.jpg&:&http://img-i.qfc.cn/upload/01/galary/14/c1/10244.jpg
		&;&01|galary|10247.jpg&:&http://img-i.qfc.cn/upload/01/galary/b4/b1/10247.jpg
		&;&01|galary|10249.jpg&:&http://img-i.qfc.cn/upload/01/galary/b4/60/10249.jpg
		&;&01|galary|7831.jpg&:&http://img-i.qfc.cn/upload/01/galary/fa/12/7831.jpg
		...,
	"compName":"轻纺城移动商铺测试IPAD123456789123456789",
	"compContacter":"王名女士","memberType":3,
	"compMobile":"13588104052",
	"compPicName":"#&:&#&:&#&:&#&:&#&:&#&:&#&:&#&:&#&:&#&:&#&:&#&:&#&:&#&:&#&:&#&:&#&:&#&:&#&:&#&:&#&:&#&:&#",
	"certString":"01|certificate|9526.jpg&:&http://img-i.qfc.cn/upload/01/certificate/11/fd/9526.jpg&;&01|certificate|9991.jpg&:&http://img-i.qfc.cn/upload/01/certificate/e1/40/9991.jpg&;&01|certificate|9528.jpg&:&http://img-i.qfc.cn/upload/01/certificate/36/de/9528.jpg&;&01|certificate|9531.jpg&:&http://img-i.qfc.cn/upload/01/certificate/fe/f0/9531.jpg",
	"compCorp":"郭靖",
	"compAddress":"浙江绍兴绍兴县天汇市场1层B区1002号,北市场1层三区122号",
	"compCorpMobile":"13588104052",
	"compProfession":"服装服饰",
	"shopName":"轻纺城移动商铺测试IPAD123456789123456789",
	"compUrl":"sp00000566.qfc.test.ctcn.com.cn",
	"compFax":"86-573-65882677",
	"compIntro":"我公司面向省内外服装厂家、纺织企业大量回收各种库存多年的布料：棉布、针织布、梭织布、牛仔布、印花布、格仔布、提花布、缎纹布、花纤布、灯芯绒、斜纹布、平纹布、罗纹布、双面布、锦纶布、涤纶布、色丁布、毛料、里布、网布、网眼布、丝稠及各种胚布等；服装：纯棉T恤衫、衬衫、棉衣、茄克、秋装、运动服、衬衫、女装、时装、休闲服、运动服、男装、牛仔裤、休闲裤、羽绒服、针织童装，纱线，辅料，设备等我们能自己拍板成交，中介重酬。竭诚欢迎您来电洽谈！！！",
	"memberId":36607,
	"compMainProduct":"鞋子，帽子，坯布",
	"compPhone":"86-0575-89400212",
	"shopBanner":"01|company|11537.jpg&:&http://img-i.qfc.cn/upload/01/company/0f/07/11537.jpg",
	"compRemarks":"很好，公司很大和靠谱",
	"shopLogoImage":"01|company|7783.jpg&:&http://img-i.qfc.cn/upload/01/company/96/53/7783.jpg"}}

	 */
	/*
	 * 新增
	 * mainProducts(主要产品), service(服务)
	 */
	/*
	 ** findcompany **
	"compLogoImg": "01|company|637764.jpg",
    "compId": 402745,
    "compXxiangImg": "01|company|637762.jpg",
    "shopType": 3,
    "compName": "德泓（宁夏）国际纺织有限公司",
    "mainAccountId": 2035186, //
    "compMainProduct": "羊绒精纺面料,羊绒混纺面料,羊毛混纺面料,羊绒纱线,羊绒条,成衣"
	 */
	String region="";
	String compModel="";
	String compPurchaseProduct="";
	String[] compicStrs;//=new String[]{};//预加载？
	String compName="";
	String compContacter="";
	int memberType=-1;//shopType  
	String compMobile="";
	String compPicName="";
	String[] certString;//=new String[]{};
	String compCorp="";
	String compAddress="";
	String compCorpMobile="";
	String compProfession="";
	String shopName="";
	String compUrl="";
	String compFax="";
	String compIntro="";
	int memberId=-1;//mainAccountId //  ???
	String compMainProduct="";
	String compPhone="";
	String shopBanner="";
	String compRemarks="";
	String shopLogoImage="";//compLogoImg,compXxiangImg // 
	
	String compLatitude="";
	String compLongitude="";
	
	String mainProducts="";//TODO 区分open.api.shop.searchShopForIphone和cn.shop.getShopAndCompanyById，考虑去留
	Integer service;
	
	JSONObject jsonObj;
	public final static String divider1 = "&;&", divider2 = "&:&";
	
	public Company(JSONObject jObj){
		this.jsonObj = jObj;
	}
	public Company(String json) throws JSONException{
		this(new JSONObject(json));
	}
	
	/**
	 *  以下gets 方法基本思路：查看是否为空或初始值，是的话去json拿数据并存到类中，返回
	 */
	
	private String getStringFromJson(String key){
			try {
				if(jsonObj.has(key))
					return jsonObj.getString(key);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			Log.i(TAG, "company json error!--key:"+key);
//			return "";
			return Const.NO_DATA;
	}
	private boolean isStringNothing(String tt){
		return null==tt||tt.isEmpty();
	}
	private String getThisThingFromItselfOrJson(String tt , String keyForSearch){
		if(isStringNothing(tt)) tt = getStringFromJson(keyForSearch);
		return tt;
	}
	private String[] getStringArrayFromJson(String key){
		//1.区分 2.取子字符串  //1021 先不要取子
		if(!jsonObj.has(key)) return new String[]{};
		String[] resultArr;//=new String[3];
		try {
			String longJsonValue = jsonObj.getString(key);
			resultArr = longJsonValue.split(divider1);
			/*for(int i=0;i<resultArr.length;i++){ //取子字符串
				resultArr[i]= getSinglePicPath(resultArr[i]);
			}*/
			
		} catch (JSONException e) {
			Log.i(TAG, "company json error!");
			resultArr = new String[]{};
			e.printStackTrace();
		}
		
		return resultArr;
	}
	
	
	public static String getSinglePicPath(String string){
		try{
			if(string.contains(divider2)){
				return string.substring(string.indexOf(divider2)+divider2.length());//notice: returns divider2.length-1 if not found
			}else{
				return string ;//1116
			}
		}catch(Exception e){
			e.printStackTrace();
			return "";
		}
	}
	public static String getSinglePicLoc(String string){
		try{
			if(string.contains(divider2)){
				return string.substring(0, string.indexOf(divider2)).replace('|', '/');
			}else{
				return string;//1119
			}
		}catch(Exception e){
			e.printStackTrace();
			return "";
		}
	}
	
	private int getIntFromJson(String key){
		int failed = -1;
		if(!jsonObj.has(key)) return failed;
		try {
			return jsonObj.getInt(key);
		} catch (JSONException e) {
			e.printStackTrace();
			return failed;
		}
	}
	
	public String getRegion() {
		region = getThisThingFromItselfOrJson(region, "region");
		return region;
	}
	public String getCompModel() {
		compModel = getThisThingFromItselfOrJson(compModel, "compModel");
		return compModel;
	}
	public String getCompPurchaseProduct() {
		compPurchaseProduct = getThisThingFromItselfOrJson(compPurchaseProduct, "compPurchaseProduct");
		return compPurchaseProduct;
	}
	public String[] getCompicStrs() { //strs
		if(compicStrs==null||compicStrs.length==0) compicStrs= getStringArrayFromJson("compPicString");
		return compicStrs;
	}
	public String getCompName() {
		compName = getThisThingFromItselfOrJson(compName, "compName");
		return compName;
	}
	public String getCompContacter() {
		compContacter = getThisThingFromItselfOrJson(compContacter, "compContacter");
		return compContacter;
	}
	public int getMemberType() {//
		if(memberType==-1) memberType=getIntFromJson("memberType");
		return memberType;
	}
	public String getCompMobile() {
		compMobile = getThisThingFromItselfOrJson(compMobile, "compMobile");
		return compMobile;
	}
	public String getCompPicName() {
		compPicName = getThisThingFromItselfOrJson(compPicName, "compPicName");
		return compPicName;
	}
	public String[] getCertString() {//strs
		if(certString==null||certString.length==0) certString= getStringArrayFromJson("certString");
		return certString;
	}
	public String getCompCorp() {
		compCorp = getThisThingFromItselfOrJson(compCorp, "compCorp");
		return compCorp;
	}
	public String getCompAddress() {
		compAddress = getThisThingFromItselfOrJson(compAddress, "compAddress");
		return compAddress;
	}
	public String getCompCorpMobile() {
		compCorpMobile = getThisThingFromItselfOrJson(compCorpMobile, "compCorpMobile");
		if(compCorpMobile.equals("null")) compCorpMobile=Const.NO_DATA;//1203
		return compCorpMobile;
	}
	public String getCompProfession() {
		compProfession = getThisThingFromItselfOrJson(compProfession, "compProfession");
		return compProfession;
	}
	public String getShopName() {
		shopName = getThisThingFromItselfOrJson(shopName, "shopName");
		return shopName;
	}
	public String getCompUrl() {
		compUrl = getThisThingFromItselfOrJson(compUrl, "compUrl");
		return compUrl;
	}
	public String getCompFax() {
		compFax = getThisThingFromItselfOrJson(compFax, "compFax");
		return compFax;
	}
	public String getCompMainProduct() {
		compMainProduct = getThisThingFromItselfOrJson(compMainProduct, "compMainProduct");
		return compMainProduct;
	}
	public String getCompIntro() {
		compIntro = getThisThingFromItselfOrJson(compIntro, "compIntro");
		return compIntro;
	}
	public int getMemberId() {//
		if(memberId==-1) memberId=getIntFromJson("memberId");
		return memberId;
	}
	public String getCompPhone() {
		compPhone = getThisThingFromItselfOrJson(compPhone, "compPhone");
		return compPhone;
	}
	public String getShopBanner() {
		shopBanner = getThisThingFromItselfOrJson(shopBanner, "shopBanner");
		return shopBanner;
	}
	public String getCompRemarks() {
		compRemarks = getThisThingFromItselfOrJson(compRemarks, "compRemarks");
		return compRemarks;
	}
	public String getShopLogoImage() {
		shopLogoImage = getThisThingFromItselfOrJson(shopLogoImage, "shopLogoImage");
		return shopLogoImage;
	}
	public String getCompLatitude() {
		compLatitude = getThisThingFromItselfOrJson(compLatitude, "compLatitude");
		if(compLatitude.isEmpty()||compLatitude.equals(Const.NO_DATA)) compLatitude = Const.LOC_DEFAULT_LAT;
		return compLatitude;
	}
	public String getCompLongitude() {
		compLongitude = getThisThingFromItselfOrJson(compLongitude, "compLongitude");
		if(compLongitude.isEmpty()||compLongitude.equals(Const.NO_DATA)) compLongitude = Const.LOC_DEFAULT_LON;
		return compLongitude;
	}
	//0219
	public String getMainProduct(){
		mainProducts = getThisThingFromItselfOrJson(shopLogoImage, "mainProducts");
		return mainProducts;
	}
	public Integer getService(){
		if(service==null) service=getIntFromJson("service");//TODO 测试service为null的
		return service;
	}
	
	
}
