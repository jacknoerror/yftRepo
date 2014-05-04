package com.qfc.yft.utils;


import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.qfc.yft.entity.offline.IOfflineConst;
import com.qfc.yft.entity.offline.JsonOffline;
import com.qfc.yft.entity.offline.OfflineData;



public class OfflineUtill implements IOfflineConst{
	final static String TAG = "offlineUtil";
	
	public static String initOfflineJsonStr(String shopid) throws JSONException {
		JSONObject job = new JSONObject();
			JSONObject shopInfoJson = new JSONObject();
			shopInfoJson.put(OFF_SHOPBANNERPIC, new JSONObject());
			shopInfoJson.put(OFF_SHOPCERTARRAY, new JSONArray());
			shopInfoJson.put(OFF_SHOPID, shopid);//
			shopInfoJson.put(OFF_SHOPLOGOPIC,new JSONObject());
			shopInfoJson.put(OFF_SHOPPICSARRAY, new JSONArray());
			shopInfoJson.put(OFF_STATUS, "1");
			
			shopInfoJson.put(OFF_UPDATETIME,JackUtils.getDate());
		job.put(OFF_SHOPINFO, shopInfoJson);
			JSONObject downloadStatusJson = new JSONObject();
			downloadStatusJson.put(OFF_PERCENT, "0.00");
			downloadStatusJson.put(OFF_SIZE, "0.00");
			downloadStatusJson.put(OFF_STATUS, "-1");
		job.put(OFF_DOWNLOADSTATUS, downloadStatusJson);
		
		job.put(OFF_PRODUCTARRAY, new JSONArray());
		job.put(OFF_SERIESARRAY, new JSONArray());
		
		System.out.println(job.toString());
		return job.toString();
	}
	
	public static String getPathUponUrl(int shopId ,String url){
		final String UPLOAD = "upload/",TITLE = "/qfc/imgs";
		String path = "";
		if(shopId>0&&url!=null&&!url.isEmpty()&&url.contains(UPLOAD)){
//			path = TITLE+shopId+url.substring(url.indexOf(UPLOAD)+UPLOAD.length());
			String[] strs = url.substring(url.indexOf(UPLOAD)+UPLOAD.length()).split("/");
			if(strs.length>=3){
				path = String.format("%s/%d/%s/%s/%s", TITLE,shopId,strs[0],strs[1],strs[strs.length-1]);
			}
		}
		return path;
	}
	
	public static boolean needDownload(int stat){
		return stat==OFFSTATUS_NEED_DOWNLOAD||stat==OFFSTATUS_DOWNLOADING;
	}
	public static boolean needDelete(int stat){
		return stat==OFFSTATUS_NEED_DELETE;
	}
	
	public static int parseOfflineResult(JSONObject job){
//		JSONObject infoJob=job.optJSONObject(OFF_SHOPINFO);
//		JSONObject bannerJob = infoJob.optJSONObject(OFF_SHOPBANNERPIC);
//		JsonReader reader ;reader.
		/*try{  
			JSONObject shopInfoJson,downloadStatusJson;
			JSONArray seriesArray, productArray;
			shopInfoJson = job.getJSONObject(OFF_SHOPINFO);
			downloadStatusJson = job.getJSONObject(OFF_DOWNLOADSTATUS);
			seriesArray = job.getJSONArray(OFF_SERIESARRAY);
			productArray = job.getJSONArray(OFF_PRODUCTARRAY);
        }  
        catch(JSONException e){  
            e.printStackTrace();  
        } finally{
        }*/
		JsonOffline od = new OfflineData(job);
		Log.i(TAG, od.toJsonStr());
		return 0;
	}
	/*
	 {
    "seriesArray": [
        {
            "updateTime": "2013-09-06 14:33:25",
            "status": "1",
            "seriesId": "2720"
        },
        {
            "updateTime": "2013-09-06 14:33:25",
            "status": "1",
            "seriesId": "2713"
        },
        {
            "updateTime": "2013-09-06 14:33:25",
            "status": "1",
            "seriesId": "2721"
        },
        {
            "updateTime": "2013-09-06 14:33:25",
            "status": "1",
            "seriesId": "2722"
        },
        {
            "updateTime": "2013-09-06 14:33:25",
            "status": "1",
            "seriesId": "2725"
        },
        {
            "updateTime": "2013-09-06 14:33:25",
            "status": "1",
            "seriesId": "2726"
        },
        {
            "updateTime": "2013-09-06 14:33:25",
            "status": "1",
            "seriesId": "2736"
        },
        {
            "updateTime": "2013-09-06 14:33:25",
            "status": "1",
            "seriesId": "2737"
        },
        {
            "updateTime": "2013-09-06 14:33:25",
            "status": "1",
            "seriesId": "2738"
        },
        {
            "updateTime": "2013-09-06 14:33:25",
            "status": "1",
            "seriesId": "2852"
        },
        {
            "updateTime": "2013-09-06 14:33:25",
            "status": "1",
            "seriesId": "2857"
        },
        {
            "updateTime": "2013-09-06 14:33:25",
            "status": "1",
            "seriesId": "2856"
        },
        {
            "updateTime": "2013-09-06 14:33:25",
            "status": "1",
            "seriesId": "2859"
        },
        {
            "updateTime": "2013-09-06 14:33:25",
            "status": "1",
            "seriesId": "2872"
        },
        {
            "updateTime": "2013-09-06 14:33:25",
            "status": "1",
            "seriesId": "2873"
        },
        {
            "updateTime": "2013-09-06 14:33:25",
            "status": "1",
            "seriesId": "2874"
        },
        {
            "updateTime": "2013-09-06 14:33:25",
            "status": "1",
            "seriesId": "2875"
        },
        {
            "updateTime": "2013-09-06 14:33:25",
            "status": "1",
            "seriesId": "2876"
        },
        {
            "updateTime": "2013-09-06 14:33:25",
            "status": "1",
            "seriesId": "2877"
        },
        {
            "updateTime": "2013-09-06 14:33:25",
            "status": "1",
            "seriesId": "2878"
        },
        {
            "updateTime": "2013-09-06 14:33:25",
            "status": "1",
            "seriesId": "2882"
        },
        {
            "updateTime": "2013-09-06 14:33:25",
            "status": "1",
            "seriesId": "2893"
        }
    ],
    "shopInfo": {
        "shopBannerPic": {
            "imageUrl": "http://img-i.qfc.cn/upload/01/company/0f/07/11537.jpg",
            "percentWeight": "0",
            "status": "1",
            "imageCode": "01|company|11537.jpg"
        },
        "shopCertArray": [
            {
                "status": "1",
                "percentWeight": "0",
                "imageUrl": "http://img-i.qfc.cn/upload/01/certificate/11/fd/9526.jpg",
                "imageCode": "01|certificate|9526.jpg"
            },
            {
                "status": "1",
                "percentWeight": "0",
                "imageUrl": "http://img-i.qfc.cn/upload/01/certificate/e1/40/9991.jpg",
                "imageCode": "01|certificate|9991.jpg"
            },
            {
                "status": "1",
                "percentWeight": "0",
                "imageUrl": "http://img-i.qfc.cn/upload/01/certificate/36/de/9528.jpg",
                "imageCode": "01|certificate|9528.jpg"
            },
            {
                "status": "1",
                "percentWeight": "0",
                "imageUrl": "http://img-i.qfc.cn/upload/01/certificate/fe/f0/9531.jpg",
                "imageCode": "01|certificate|9531.jpg"
            }
        ],
        "status": "1",
        "updateTime": "2013-11-26 10:42:28",
        "shopId": "14843",
        "shopPicsArray": [
            {
                "status": "1",
                "percentWeight": "0",
                "imageUrl": "http://img-i.qfc.cn/upload/01/galary/fa/12/7831.jpg",
                "imageCode": "01|galary|7831.jpg"
            },
            {
                "status": "1",
                "percentWeight": "0",
                "imageUrl": "http://img-i.qfc.cn/upload/01/galary/f0/f2/10255.jpg",
                "imageCode": "01|galary|10255.jpg"
            },
            {
                "status": "1",
                "percentWeight": "0",
                "imageUrl": "http://img-i.qfc.cn/upload/01/galary/b4/b1/10247.jpg",
                "imageCode": "01|galary|10247.jpg"
            },
            {
                "status": "1",
                "percentWeight": "0",
                "imageUrl": "http://img-i.qfc.cn/upload/01/galary/8b/54/10242.jpg",
                "imageCode": "01|galary|10242.jpg"
            },
            {
                "status": "1",
                "percentWeight": "0",
                "imageUrl": "http://img-i.qfc.cn/upload/01/galary/b4/60/10249.jpg",
                "imageCode": "01|galary|10249.jpg"
            },
            {
                "status": "1",
                "percentWeight": "0",
                "imageUrl": "http://img-i.qfc.cn/upload/01/galary/ff/b7/10246.jpg",
                "imageCode": "01|galary|10246.jpg"
            },
            {
                "status": "1",
                "percentWeight": "0",
                "imageUrl": "http://img-i.qfc.cn/upload/01/galary/40/1c/10251.jpg",
                "imageCode": "01|galary|10251.jpg"
            },
            {
                "status": "1",
                "percentWeight": "0",
                "imageUrl": "http://img-i.qfc.cn/upload/01/galary/19/00/10243.jpg",
                "imageCode": "01|galary|10243.jpg"
            },
            {
                "status": "1",
                "percentWeight": "0",
                "imageUrl": "http://img-i.qfc.cn/upload/01/galary/14/c1/10244.jpg",
                "imageCode": "01|galary|10244.jpg"
            },
            {
                "status": "1",
                "percentWeight": "0",
                "imageUrl": "http://img-i.qfc.cn/upload/01/galary/80/38/10256.jpg",
                "imageCode": "01|galary|10256.jpg"
            },
            {
                "status": "1",
                "percentWeight": "0",
                "imageUrl": "http://img-i.qfc.cn/upload/01/galary/0b/60/9973.jpg",
                "imageCode": "01|galary|9973.jpg"
            },
            {
                "status": "1",
                "percentWeight": "0",
                "imageUrl": "http://img-i.qfc.cn/upload/01/galary/81/20/10248.jpg",
                "imageCode": "01|galary|10248.jpg"
            },
            {
                "status": "1",
                "percentWeight": "0",
                "imageUrl": "http://img-i.qfc.cn/upload/01/galary/e8/ad/9971.jpg",
                "imageCode": "01|galary|9971.jpg"
            },
            {
                "status": "1",
                "percentWeight": "0",
                "imageUrl": "http://img-i.qfc.cn/upload/01/galary/80/4c/10254.jpg",
                "imageCode": "01|galary|10254.jpg"
            },
            {
                "status": "1",
                "percentWeight": "0",
                "imageUrl": "http://img-i.qfc.cn/upload/01/galary/b2/16/7832.jpg",
                "imageCode": "01|galary|7832.jpg"
            },
            {
                "status": "1",
                "percentWeight": "0",
                "imageUrl": "http://img-i.qfc.cn/upload/01/galary/eb/f6/10250.jpg",
                "imageCode": "01|galary|10250.jpg"
            },
            {
                "status": "1",
                "percentWeight": "0",
                "imageUrl": "http://img-i.qfc.cn/upload/01/galary/98/8f/10253.jpg",
                "imageCode": "01|galary|10253.jpg"
            },
            {
                "status": "1",
                "percentWeight": "0",
                "imageUrl": "http://img-i.qfc.cn/upload/01/galary/6e/a3/9972.jpg",
                "imageCode": "01|galary|9972.jpg"
            },
            {
                "status": "1",
                "percentWeight": "0",
                "imageUrl": "http://img-i.qfc.cn/upload/01/galary/3c/7e/10245.jpg",
                "imageCode": "01|galary|10245.jpg"
            },
            {
                "status": "1",
                "percentWeight": "0",
                "imageUrl": "http://img-i.qfc.cn/upload/01/galary/fe/00/10252.jpg",
                "imageCode": "01|galary|10252.jpg"
            },
            {
                "status": "1",
                "percentWeight": "0",
                "imageUrl": "http://img-i.qfc.cn/upload/01/galary/58/e3/9970.jpg",
                "imageCode": "01|galary|9970.jpg"
            },
            {
                "status": "1",
                "percentWeight": "0",
                "imageUrl": "http://img-i.qfc.cn/upload/01/galary/91/7f/7830.jpg",
                "imageCode": "01|galary|7830.jpg"
            },
            {
                "status": "1",
                "percentWeight": "0",
                "imageUrl": "http://img-i.qfc.cn/upload/01/galary/82/43/7833.jpg",
                "imageCode": "01|galary|7833.jpg"
            }
        ],
        "shopLogoPic": {
            "imageUrl": "http://img-i.qfc.cn/upload/01/company/96/53/7783.jpg",
            "percentWeight": "0",
            "status": "1",
            "imageCode": "01|company|7783.jpg"
        }
    },
    "productArray": [
        {
            "updateTime": "2013-11-06 11:50:43",
            "status": "1",
            "productImage": {
                "status": "1",
                "percentWeight": "0",
                "imageUrl": "http://img-i.qfc.cn/upload/01/product/0f/c4/7943_300X300.jpg",
                "imageCode": "MDF8cHJvZHVjdHw3OTQzLmpwZw=="
            },
            "productPicsArray": [
                {
                    "status": "1",
                    "percentWeight": "0",
                    "imageUrl": "http://img-i.qfc.cn/upload/01/product/07/b9/7946_300X300.jpg",
                    "imageCode": "MDF8cHJvZHVjdHw3OTQ2LmpwZw=="
                },
                {
                    "status": "1",
                    "percentWeight": "0",
                    "imageUrl": "http://img-i.qfc.cn/upload/01/product/6e/3a/7945_300X300.jpg",
                    "imageCode": "MDF8cHJvZHVjdHw3OTQ1LmpwZw=="
                },
                {
                    "status": "1",
                    "percentWeight": "0",
                    "imageUrl": "http://img-i.qfc.cn/upload/01/product/1f/00/7944_300X300.jpg",
                    "imageCode": "MDF8cHJvZHVjdHw3OTQ0LmpwZw=="
                },
                {
                    "status": "1",
                    "percentWeight": "0",
                    "imageUrl": "http://img-i.qfc.cn/upload/01/product/0f/c4/7943_300X300.jpg",
                    "imageCode": "MDF8cHJvZHVjdHw3OTQzLmpwZw=="
                }
            ],
            "productId": "15590"
        },
        {
            "updateTime": "2013-11-06 11:50:46",
            "status": "1",
            "productImage": {
                "status": "1",
                "percentWeight": "0",
                "imageUrl": "http://img-i.qfc.cn/upload/01/product/1a/43/9408_300X300.jpg",
                "imageCode": "MDF8cHJvZHVjdHw5NDA4LmpwZw=="
            },
            "productPicsArray": [
                {
                    "status": "1",
                    "percentWeight": "0",
                    "imageUrl": "http://img-i.qfc.cn/upload/01/product/59/90/9411_300X300.jpg",
                    "imageCode": "MDF8cHJvZHVjdHw5NDExLmpwZw=="
                },
                {
                    "status": "1",
                    "percentWeight": "0",
                    "imageUrl": "http://img-i.qfc.cn/upload/01/product/1c/11/9410_300X300.jpg",
                    "imageCode": "MDF8cHJvZHVjdHw5NDEwLmpwZw=="
                },
                {
                    "status": "1",
                    "percentWeight": "0",
                    "imageUrl": "http://img-i.qfc.cn/upload/01/product/3d/6e/9409_300X300.jpg",
                    "imageCode": "MDF8cHJvZHVjdHw5NDA5LmpwZw=="
                },
                {
                    "status": "1",
                    "percentWeight": "0",
                    "imageUrl": "http://img-i.qfc.cn/upload/01/product/1a/43/9408_300X300.jpg",
                    "imageCode": "MDF8cHJvZHVjdHw5NDA4LmpwZw=="
                }
            ],
            "productId": "15591"
        },
        {
            "updateTime": "2013-11-06 11:50:44",
            "status": "1",
            "productImage": {
                "status": "1",
                "percentWeight": "0",
                "imageUrl": "http://img-i.qfc.cn/upload/01/product/fe/8f/9420_300X300.jpg",
                "imageCode": "MDF8cHJvZHVjdHw5NDIwLmpwZw=="
            },
            "productPicsArray": [
                {
                    "status": "1",
                    "percentWeight": "0",
                    "imageUrl": "http://img-i.qfc.cn/upload/01/product/75/2f/9423_300X300.jpg",
                    "imageCode": "MDF8cHJvZHVjdHw5NDIzLmpwZw=="
                },
                {
                    "status": "1",
                    "percentWeight": "0",
                    "imageUrl": "http://img-i.qfc.cn/upload/01/product/03/4d/9422_300X300.jpg",
                    "imageCode": "MDF8cHJvZHVjdHw5NDIyLmpwZw=="
                },
                {
                    "status": "1",
                    "percentWeight": "0",
                    "imageUrl": "http://img-i.qfc.cn/upload/01/product/3e/58/9421_300X300.jpg",
                    "imageCode": "MDF8cHJvZHVjdHw5NDIxLmpwZw=="
                },
                {
                    "status": "1",
                    "percentWeight": "0",
                    "imageUrl": "http://img-i.qfc.cn/upload/01/product/fe/8f/9420_300X300.jpg",
                    "imageCode": "MDF8cHJvZHVjdHw5NDIwLmpwZw=="
                }
            ],
            "productId": "15588"
        },
        {
            "updateTime": "2013-11-06 11:50:44",
            "status": "1",
            "productImage": {
                "status": "1",
                "percentWeight": "0",
                "imageUrl": "http://img-i.qfc.cn/upload/01/product/b3/55/9412_300X300.jpg",
                "imageCode": "MDF8cHJvZHVjdHw5NDEyLmpwZw=="
            },
            "productPicsArray": [
                {
                    "status": "1",
                    "percentWeight": "0",
                    "imageUrl": "http://img-i.qfc.cn/upload/01/product/49/60/9415_300X300.jpg",
                    "imageCode": "MDF8cHJvZHVjdHw5NDE1LmpwZw=="
                },
                {
                    "status": "1",
                    "percentWeight": "0",
                    "imageUrl": "http://img-i.qfc.cn/upload/01/product/a5/4c/9414_300X300.jpg",
                    "imageCode": "MDF8cHJvZHVjdHw5NDE0LmpwZw=="
                },
                {
                    "status": "1",
                    "percentWeight": "0",
                    "imageUrl": "http://img-i.qfc.cn/upload/01/product/f3/97/9413_300X300.jpg",
                    "imageCode": "MDF8cHJvZHVjdHw5NDEzLmpwZw=="
                },
                {
                    "status": "1",
                    "percentWeight": "0",
                    "imageUrl": "http://img-i.qfc.cn/upload/01/product/b3/55/9412_300X300.jpg",
                    "imageCode": "MDF8cHJvZHVjdHw5NDEyLmpwZw=="
                }
            ],
            "productId": "15589"
        },
        {
            "updateTime": "2013-11-06 11:50:44",
            "status": "1",
            "productImage": {
                "status": "1",
                "percentWeight": "0",
                "imageUrl": "http://img-i.qfc.cn/upload/01/product/25/c1/9400_300X300.jpg",
                "imageCode": "MDF8cHJvZHVjdHw5NDAwLmpwZw=="
            },
            "productPicsArray": [
                {
                    "status": "1",
                    "percentWeight": "0",
                    "imageUrl": "http://img-i.qfc.cn/upload/01/product/18/e9/9403_300X300.jpeg",
                    "imageCode": "MDF8cHJvZHVjdHw5NDAzLmpwZWc="
                },
                {
                    "status": "1",
                    "percentWeight": "0",
                    "imageUrl": "http://img-i.qfc.cn/upload/01/product/b5/f6/9402_300X300.jpeg",
                    "imageCode": "MDF8cHJvZHVjdHw5NDAyLmpwZWc="
                },
                {
                    "status": "1",
                    "percentWeight": "0",
                    "imageUrl": "http://img-i.qfc.cn/upload/01/product/e7/d5/9401_300X300.jpg",
                    "imageCode": "MDF8cHJvZHVjdHw5NDAxLmpwZw=="
                },
                {
                    "status": "1",
                    "percentWeight": "0",
                    "imageUrl": "http://img-i.qfc.cn/upload/01/product/25/c1/9400_300X300.jpg",
                    "imageCode": "MDF8cHJvZHVjdHw5NDAwLmpwZw=="
                }
            ],
            "productId": "15592"
        },
        {
            "updateTime": "2013-11-06 11:50:44",
            "status": "1",
            "productImage": {
                "status": "1",
                "percentWeight": "0",
                "imageUrl": "http://img-i.qfc.cn/upload/01/product/c3/0c/9396_300X300.jpg",
                "imageCode": "MDF8cHJvZHVjdHw5Mzk2LmpwZw=="
            },
            "productPicsArray": [
                {
                    "status": "1",
                    "percentWeight": "0",
                    "imageUrl": "http://img-i.qfc.cn/upload/01/product/da/85/9399_300X300.jpg",
                    "imageCode": "MDF8cHJvZHVjdHw5Mzk5LmpwZw=="
                },
                {
                    "status": "1",
                    "percentWeight": "0",
                    "imageUrl": "http://img-i.qfc.cn/upload/01/product/6d/aa/9398_300X300.jpg",
                    "imageCode": "MDF8cHJvZHVjdHw5Mzk4LmpwZw=="
                },
                {
                    "status": "1",
                    "percentWeight": "0",
                    "imageUrl": "http://img-i.qfc.cn/upload/01/product/df/02/9397_300X300.jpg",
                    "imageCode": "MDF8cHJvZHVjdHw5Mzk3LmpwZw=="
                },
                {
                    "status": "1",
                    "percentWeight": "0",
                    "imageUrl": "http://img-i.qfc.cn/upload/01/product/c3/0c/9396_300X300.jpg",
                    "imageCode": "MDF8cHJvZHVjdHw5Mzk2LmpwZw=="
                }
            ],
            "productId": "15593"
        },
        {
            "updateTime": "2013-11-06 11:50:44",
            "status": "1",
            "productImage": {
                "status": "1",
                "percentWeight": "0",
                "imageUrl": "http://img-i.qfc.cn/upload/01/product/9b/72/9392_300X300.jpg",
                "imageCode": "MDF8cHJvZHVjdHw5MzkyLmpwZw=="
            },
            "productPicsArray": [
                {
                    "status": "1",
                    "percentWeight": "0",
                    "imageUrl": "http://img-i.qfc.cn/upload/01/product/c9/5a/9395_300X300.jpg",
                    "imageCode": "MDF8cHJvZHVjdHw5Mzk1LmpwZw=="
                },
                {
                    "status": "1",
                    "percentWeight": "0",
                    "imageUrl": "http://img-i.qfc.cn/upload/01/product/92/9a/9394_300X300.jpg",
                    "imageCode": "MDF8cHJvZHVjdHw5Mzk0LmpwZw=="
                },
                {
                    "status": "1",
                    "percentWeight": "0",
                    "imageUrl": "http://img-i.qfc.cn/upload/01/product/87/2d/9393_300X300.jpg",
                    "imageCode": "MDF8cHJvZHVjdHw5MzkzLmpwZw=="
                },
                {
                    "status": "1",
                    "percentWeight": "0",
                    "imageUrl": "http://img-i.qfc.cn/upload/01/product/9b/72/9392_300X300.jpg",
                    "imageCode": "MDF8cHJvZHVjdHw5MzkyLmpwZw=="
                }
            ],
            "productId": "15594"
        },
        {
            "updateTime": "2013-11-06 11:50:44",
            "status": "1",
            "productImage": {
                "status": "1",
                "percentWeight": "0",
                "imageUrl": "http://img-i.qfc.cn/upload/01/product/18/7b/9388_300X300.jpg",
                "imageCode": "MDF8cHJvZHVjdHw5Mzg4LmpwZw=="
            },
            "productPicsArray": [
                {
                    "status": "1",
                    "percentWeight": "0",
                    "imageUrl": "http://img-i.qfc.cn/upload/01/product/54/74/9391_300X300.jpg",
                    "imageCode": "MDF8cHJvZHVjdHw5MzkxLmpwZw=="
                },
                {
                    "status": "1",
                    "percentWeight": "0",
                    "imageUrl": "http://img-i.qfc.cn/upload/01/product/18/0d/9390_300X300.jpg",
                    "imageCode": "MDF8cHJvZHVjdHw5MzkwLmpwZw=="
                },
                {
                    "status": "1",
                    "percentWeight": "0",
                    "imageUrl": "http://img-i.qfc.cn/upload/01/product/ae/89/9389_300X300.jpg",
                    "imageCode": "MDF8cHJvZHVjdHw5Mzg5LmpwZw=="
                },
                {
                    "status": "1",
                    "percentWeight": "0",
                    "imageUrl": "http://img-i.qfc.cn/upload/01/product/18/7b/9388_300X300.jpg",
                    "imageCode": "MDF8cHJvZHVjdHw5Mzg4LmpwZw=="
                }
            ],
            "productId": "15595"
        },
        {
            "updateTime": "2013-11-06 11:50:44",
            "status": "1",
            "productImage": {
                "status": "1",
                "percentWeight": "0",
                "imageUrl": "http://img-i.qfc.cn/upload/01/product/07/e7/9384_300X300.jpg",
                "imageCode": "MDF8cHJvZHVjdHw5Mzg0LmpwZw=="
            },
            "productPicsArray": [
                {
                    "status": "1",
                    "percentWeight": "0",
                    "imageUrl": "http://img-i.qfc.cn/upload/01/product/2f/97/9387_300X300.jpg",
                    "imageCode": "MDF8cHJvZHVjdHw5Mzg3LmpwZw=="
                },
                {
                    "status": "1",
                    "percentWeight": "0",
                    "imageUrl": "http://img-i.qfc.cn/upload/01/product/18/c9/9386_300X300.jpg",
                    "imageCode": "MDF8cHJvZHVjdHw5Mzg2LmpwZw=="
                },
                {
                    "status": "1",
                    "percentWeight": "0",
                    "imageUrl": "http://img-i.qfc.cn/upload/01/product/4f/06/9385_300X300.jpg",
                    "imageCode": "MDF8cHJvZHVjdHw5Mzg1LmpwZw=="
                },
                {
                    "status": "1",
                    "percentWeight": "0",
                    "imageUrl": "http://img-i.qfc.cn/upload/01/product/07/e7/9384_300X300.jpg",
                    "imageCode": "MDF8cHJvZHVjdHw5Mzg0LmpwZw=="
                }
            ],
            "productId": "15596"
        },
        {
            "updateTime": "2013-11-06 11:50:44",
            "status": "1",
            "productImage": {
                "status": "1",
                "percentWeight": "0",
                "imageUrl": "http://img-i.qfc.cn/upload/01/product/9f/8b/9380_300X300.jpg",
                "imageCode": "MDF8cHJvZHVjdHw5MzgwLmpwZw=="
            },
            "productPicsArray": [
                {
                    "status": "1",
                    "percentWeight": "0",
                    "imageUrl": "http://img-i.qfc.cn/upload/01/product/e6/b9/9383_300X300.jpg",
                    "imageCode": "MDF8cHJvZHVjdHw5MzgzLmpwZw=="
                },
                {
                    "status": "1",
                    "percentWeight": "0",
                    "imageUrl": "http://img-i.qfc.cn/upload/01/product/3f/00/9382_300X300.jpg",
                    "imageCode": "MDF8cHJvZHVjdHw5MzgyLmpwZw=="
                },
                {
                    "status": "1",
                    "percentWeight": "0",
                    "imageUrl": "http://img-i.qfc.cn/upload/01/product/f5/96/9381_300X300.jpg",
                    "imageCode": "MDF8cHJvZHVjdHw5MzgxLmpwZw=="
                },
                {
                    "status": "1",
                    "percentWeight": "0",
                    "imageUrl": "http://img-i.qfc.cn/upload/01/product/9f/8b/9380_300X300.jpg",
                    "imageCode": "MDF8cHJvZHVjdHw5MzgwLmpwZw=="
                }
            ],
            "productId": "15597"
        },
        {
            "updateTime": "2013-11-06 11:50:45",
            "status": "1",
            "productImage": {
                "status": "1",
                "percentWeight": "0",
                "imageUrl": "http://img-i.qfc.cn/upload/01/product/55/19/9376_300X300.jpg",
                "imageCode": "MDF8cHJvZHVjdHw5Mzc2LmpwZw=="
            },
            "productPicsArray": [
                {
                    "status": "1",
                    "percentWeight": "0",
                    "imageUrl": "http://img-i.qfc.cn/upload/01/product/a6/d6/9379_300X300.jpg",
                    "imageCode": "MDF8cHJvZHVjdHw5Mzc5LmpwZw=="
                },
                {
                    "status": "1",
                    "percentWeight": "0",
                    "imageUrl": "http://img-i.qfc.cn/upload/01/product/8f/38/9378_300X300.jpg",
                    "imageCode": "MDF8cHJvZHVjdHw5Mzc4LmpwZw=="
                },
                {
                    "status": "1",
                    "percentWeight": "0",
                    "imageUrl": "http://img-i.qfc.cn/upload/01/product/0e/2a/9377_300X300.jpg",
                    "imageCode": "MDF8cHJvZHVjdHw5Mzc3LmpwZw=="
                },
                {
                    "status": "1",
                    "percentWeight": "0",
                    "imageUrl": "http://img-i.qfc.cn/upload/01/product/55/19/9376_300X300.jpg",
                    "imageCode": "MDF8cHJvZHVjdHw5Mzc2LmpwZw=="
                }
            ],
            "productId": "15598"
        },
        {
            "updateTime": "2013-11-06 11:50:44",
            "status": "1",
            "productImage": {
                "status": "1",
                "percentWeight": "0",
                "imageUrl": "http://img-i.qfc.cn/upload/01/product/0e/95/9404_300X300.jpg",
                "imageCode": "MDF8cHJvZHVjdHw5NDA0LmpwZw=="
            },
            "productPicsArray": [
                {
                    "status": "1",
                    "percentWeight": "0",
                    "imageUrl": "http://img-i.qfc.cn/upload/01/product/1c/5d/9407_300X300.jpg",
                    "imageCode": "MDF8cHJvZHVjdHw5NDA3LmpwZw=="
                },
                {
                    "status": "1",
                    "percentWeight": "0",
                    "imageUrl": "http://img-i.qfc.cn/upload/01/product/8e/b3/9406_300X300.jpg",
                    "imageCode": "MDF8cHJvZHVjdHw5NDA2LmpwZw=="
                },
                {
                    "status": "1",
                    "percentWeight": "0",
                    "imageUrl": "http://img-i.qfc.cn/upload/01/product/b0/b1/9405_300X300.jpg",
                    "imageCode": "MDF8cHJvZHVjdHw5NDA1LmpwZw=="
                },
                {
                    "status": "1",
                    "percentWeight": "0",
                    "imageUrl": "http://img-i.qfc.cn/upload/01/product/0e/95/9404_300X300.jpg",
                    "imageCode": "MDF8cHJvZHVjdHw5NDA0LmpwZw=="
                }
            ],
            "productId": "15599"
        },
        {
            "updateTime": "2013-11-06 11:50:44",
            "status": "1",
            "productImage": {
                "status": "1",
                "percentWeight": "0",
                "imageUrl": "http://img-i.qfc.cn/upload/01/product/5f/34/9343_300X300.jpg",
                "imageCode": "MDF8cHJvZHVjdHw5MzQzLmpwZw=="
            },
            "productPicsArray": [
                {
                    "status": "1",
                    "percentWeight": "0",
                    "imageUrl": "http://img-i.qfc.cn/upload/01/product/de/5b/9346_300X300.jpg",
                    "imageCode": "MDF8cHJvZHVjdHw5MzQ2LmpwZw=="
                },
                {
                    "status": "1",
                    "percentWeight": "0",
                    "imageUrl": "http://img-i.qfc.cn/upload/01/product/89/e2/9345_300X300.jpg",
                    "imageCode": "MDF8cHJvZHVjdHw5MzQ1LmpwZw=="
                },
                {
                    "status": "1",
                    "percentWeight": "0",
                    "imageUrl": "http://img-i.qfc.cn/upload/01/product/38/33/9344_300X300.jpg",
                    "imageCode": "MDF8cHJvZHVjdHw5MzQ0LmpwZw=="
                },
                {
                    "status": "1",
                    "percentWeight": "0",
                    "imageUrl": "http://img-i.qfc.cn/upload/01/product/5f/34/9343_300X300.jpg",
                    "imageCode": "MDF8cHJvZHVjdHw5MzQzLmpwZw=="
                }
            ],
            "productId": "15600"
        },
        {
            "updateTime": "2013-11-06 11:50:45",
            "status": "1",
            "productImage": {
                "status": "1",
                "percentWeight": "0",
                "imageUrl": "http://img-i.qfc.cn/upload/01/product/e3/84/7790_300X300.jpg",
                "imageCode": "MDF8cHJvZHVjdHw3NzkwLmpwZw=="
            },
            "productPicsArray": [
                {
                    "status": "1",
                    "percentWeight": "0",
                    "imageUrl": "http://img-i.qfc.cn/upload/01/product/cd/0c/9342_300X300.jpg",
                    "imageCode": "MDF8cHJvZHVjdHw5MzQyLmpwZw=="
                },
                {
                    "status": "1",
                    "percentWeight": "0",
                    "imageUrl": "http://img-i.qfc.cn/upload/01/product/ee/91/9341_300X300.jpg",
                    "imageCode": "MDF8cHJvZHVjdHw5MzQxLmpwZw=="
                },
                {
                    "status": "1",
                    "percentWeight": "0",
                    "imageUrl": "http://img-i.qfc.cn/upload/01/product/87/5a/9340_300X300.jpg",
                    "imageCode": "MDF8cHJvZHVjdHw5MzQwLmpwZw=="
                },
                {
                    "status": "1",
                    "percentWeight": "0",
                    "imageUrl": "http://img-i.qfc.cn/upload/01/product/e3/84/7790_300X300.jpg",
                    "imageCode": "MDF8cHJvZHVjdHw3NzkwLmpwZw=="
                }
            ],
            "productId": "15601"
        },
        {
            "updateTime": "2013-11-06 11:50:45",
            "status": "1",
            "productImage": {},
            "productPicsArray": [],
            "productId": "20202"
        },
        {
            "updateTime": "2013-11-06 11:50:45",
            "status": "1",
            "productImage": {
                "status": "1",
                "percentWeight": "0",
                "imageUrl": "http://img-i.qfc.cn/upload/01/product/6d/be/9905_300X300.png",
                "imageCode": "MDF8cHJvZHVjdHw5OTA1LnBuZw=="
            },
            "productPicsArray": [
                {
                    "status": "1",
                    "percentWeight": "0",
                    "imageUrl": "http://img-i.qfc.cn/upload/01/product/09/0f/7801_300X300.jpg",
                    "imageCode": "MDF8cHJvZHVjdHw3ODAxLmpwZw=="
                },
                {
                    "status": "1",
                    "percentWeight": "0",
                    "imageUrl": "http://img-i.qfc.cn/upload/01/product/6d/be/9905_300X300.png",
                    "imageCode": "MDF8cHJvZHVjdHw5OTA1LnBuZw=="
                }
            ],
            "productId": "20203"
        },
        {
            "updateTime": "2013-11-06 11:50:45",
            "status": "1",
            "productImage": {
                "status": "1",
                "percentWeight": "0",
                "imageUrl": "http://img-i.qfc.cn/upload/01/product/2d/ef/9921_300X300.jpeg",
                "imageCode": "MDF8cHJvZHVjdHw5OTIxLmpwZWc="
            },
            "productPicsArray": [
                {
                    "status": "1",
                    "percentWeight": "0",
                    "imageUrl": "http://img-i.qfc.cn/upload/01/product/2d/ef/9921_300X300.jpeg",
                    "imageCode": "MDF8cHJvZHVjdHw5OTIxLmpwZWc="
                }
            ],
            "productId": "20205"
        },
        {
            "updateTime": "2013-11-06 11:50:45",
            "status": "1",
            "productImage": {},
            "productPicsArray": [],
            "productId": "20210"
        },
        {
            "updateTime": "2013-11-06 11:50:45",
            "status": "1",
            "productImage": {},
            "productPicsArray": [],
            "productId": "20217"
        },
        {
            "updateTime": "2013-11-06 11:50:45",
            "status": "1",
            "productImage": {
                "status": "1",
                "percentWeight": "0",
                "imageUrl": "http://img-i.qfc.cn/upload/01/product/5a/8f/10112_300X300.jpg",
                "imageCode": "MDF8cHJvZHVjdHwxMDExMi5qcGc="
            },
            "productPicsArray": [
                {
                    "status": "1",
                    "percentWeight": "0",
                    "imageUrl": "http://img-i.qfc.cn/upload/01/product/39/f8/10114_300X300.jpg",
                    "imageCode": "MDF8cHJvZHVjdHwxMDExNC5qcGc="
                },
                {
                    "status": "1",
                    "percentWeight": "0",
                    "imageUrl": "http://img-i.qfc.cn/upload/01/product/d7/d8/10113_300X300.jpg",
                    "imageCode": "MDF8cHJvZHVjdHwxMDExMy5qcGc="
                },
                {
                    "status": "1",
                    "percentWeight": "0",
                    "imageUrl": "http://img-i.qfc.cn/upload/01/product/5a/8f/10112_300X300.jpg",
                    "imageCode": "MDF8cHJvZHVjdHwxMDExMi5qcGc="
                }
            ],
            "productId": "20227"
        },
        {
            "updateTime": "2013-11-06 11:50:45",
            "status": "1",
            "productImage": {
                "status": "1",
                "percentWeight": "0",
                "imageUrl": "http://img-i.qfc.cn/upload/01/product/b2/6d/10118_300X300.jpg",
                "imageCode": "MDF8cHJvZHVjdHwxMDExOC5qcGc="
            },
            "productPicsArray": [
                {
                    "status": "1",
                    "percentWeight": "0",
                    "imageUrl": "http://img-i.qfc.cn/upload/01/product/a9/bf/10121_300X300.jpg",
                    "imageCode": "MDF8cHJvZHVjdHwxMDEyMS5qcGc="
                },
                {
                    "status": "1",
                    "percentWeight": "0",
                    "imageUrl": "http://img-i.qfc.cn/upload/01/product/9e/12/10120_300X300.jpg",
                    "imageCode": "MDF8cHJvZHVjdHwxMDEyMC5qcGc="
                },
                {
                    "status": "1",
                    "percentWeight": "0",
                    "imageUrl": "http://img-i.qfc.cn/upload/01/product/38/8b/10119_300X300.jpg",
                    "imageCode": "MDF8cHJvZHVjdHwxMDExOS5qcGc="
                },
                {
                    "status": "1",
                    "percentWeight": "0",
                    "imageUrl": "http://img-i.qfc.cn/upload/01/product/b2/6d/10118_300X300.jpg",
                    "imageCode": "MDF8cHJvZHVjdHwxMDExOC5qcGc="
                }
            ],
            "productId": "20228"
        },
        {
            "updateTime": "2013-11-06 11:50:45",
            "status": "1",
            "productImage": {
                "status": "1",
                "percentWeight": "0",
                "imageUrl": "http://img-i.qfc.cn/upload/01/product/cb/51/10144_300X300.jpg",
                "imageCode": "MDF8cHJvZHVjdHwxMDE0NC5qcGc="
            },
            "productPicsArray": [
                {
                    "status": "1",
                    "percentWeight": "0",
                    "imageUrl": "http://img-i.qfc.cn/upload/01/product/44/3d/10147_300X300.jpg",
                    "imageCode": "MDF8cHJvZHVjdHwxMDE0Ny5qcGc="
                },
                {
                    "status": "1",
                    "percentWeight": "0",
                    "imageUrl": "http://img-i.qfc.cn/upload/01/product/34/bf/10146_300X300.jpg",
                    "imageCode": "MDF8cHJvZHVjdHwxMDE0Ni5qcGc="
                },
                {
                    "status": "1",
                    "percentWeight": "0",
                    "imageUrl": "http://img-i.qfc.cn/upload/01/product/d0/35/10145_300X300.jpg",
                    "imageCode": "MDF8cHJvZHVjdHwxMDE0NS5qcGc="
                },
                {
                    "status": "1",
                    "percentWeight": "0",
                    "imageUrl": "http://img-i.qfc.cn/upload/01/product/cb/51/10144_300X300.jpg",
                    "imageCode": "MDF8cHJvZHVjdHwxMDE0NC5qcGc="
                }
            ],
            "productId": "20229"
        },
        {
            "updateTime": "2013-11-06 11:50:45",
            "status": "1",
            "productImage": {
                "status": "1",
                "percentWeight": "0",
                "imageUrl": "http://img-i.qfc.cn/upload/01/product/8e/62/10153_300X300.jpg",
                "imageCode": "MDF8cHJvZHVjdHwxMDE1My5qcGc="
            },
            "productPicsArray": [
                {
                    "status": "1",
                    "percentWeight": "0",
                    "imageUrl": "http://img-i.qfc.cn/upload/01/product/8e/62/10153_300X300.jpg",
                    "imageCode": "MDF8cHJvZHVjdHwxMDE1My5qcGc="
                }
            ],
            "productId": "20230"
        },
        {
            "updateTime": "2013-11-06 11:50:45",
            "status": "1",
            "productImage": {
                "status": "1",
                "percentWeight": "0",
                "imageUrl": "http://img-i.qfc.cn/upload/01/product/3e/d4/10160_300X300.jpg",
                "imageCode": "MDF8cHJvZHVjdHwxMDE2MC5qcGc="
            },
            "productPicsArray": [
                {
                    "status": "1",
                    "percentWeight": "0",
                    "imageUrl": "http://img-i.qfc.cn/upload/01/product/6d/6e/10163_300X300.jpg",
                    "imageCode": "MDF8cHJvZHVjdHwxMDE2My5qcGc="
                },
                {
                    "status": "1",
                    "percentWeight": "0",
                    "imageUrl": "http://img-i.qfc.cn/upload/01/product/dd/93/10162_300X300.jpg",
                    "imageCode": "MDF8cHJvZHVjdHwxMDE2Mi5qcGc="
                },
                {
                    "status": "1",
                    "percentWeight": "0",
                    "imageUrl": "http://img-i.qfc.cn/upload/01/product/c8/67/10161_300X300.jpg",
                    "imageCode": "MDF8cHJvZHVjdHwxMDE2MS5qcGc="
                },
                {
                    "status": "1",
                    "percentWeight": "0",
                    "imageUrl": "http://img-i.qfc.cn/upload/01/product/3e/d4/10160_300X300.jpg",
                    "imageCode": "MDF8cHJvZHVjdHwxMDE2MC5qcGc="
                }
            ],
            "productId": "20231"
        },
        {
            "updateTime": "2013-11-06 11:50:46",
            "status": "1",
            "productImage": {
                "status": "1",
                "percentWeight": "0",
                "imageUrl": "http://img-i.qfc.cn/upload/01/product/36/1c/10170_300X300.jpg",
                "imageCode": "MDF8cHJvZHVjdHwxMDE3MC5qcGc="
            },
            "productPicsArray": [
                {
                    "status": "1",
                    "percentWeight": "0",
                    "imageUrl": "http://img-i.qfc.cn/upload/01/product/d7/00/10171_300X300.jpg",
                    "imageCode": "MDF8cHJvZHVjdHwxMDE3MS5qcGc="
                },
                {
                    "status": "1",
                    "percentWeight": "0",
                    "imageUrl": "http://img-i.qfc.cn/upload/01/product/36/1c/10170_300X300.jpg",
                    "imageCode": "MDF8cHJvZHVjdHwxMDE3MC5qcGc="
                }
            ],
            "productId": "20232"
        },
        {
            "updateTime": "2013-11-06 11:50:46",
            "status": "1",
            "productImage": {
                "status": "1",
                "percentWeight": "0",
                "imageUrl": "http://img-i.qfc.cn/upload/01/product/50/29/10315_300X300.jpg",
                "imageCode": "MDF8cHJvZHVjdHwxMDMxNS5qcGc="
            },
            "productPicsArray": [
                {
                    "status": "1",
                    "percentWeight": "0",
                    "imageUrl": "http://img-i.qfc.cn/upload/01/product/05/2b/10318_300X300.jpeg",
                    "imageCode": "MDF8cHJvZHVjdHwxMDMxOC5qcGVn"
                },
                {
                    "status": "1",
                    "percentWeight": "0",
                    "imageUrl": "http://img-i.qfc.cn/upload/01/product/79/3f/10317_300X300.jpeg",
                    "imageCode": "MDF8cHJvZHVjdHwxMDMxNy5qcGVn"
                },
                {
                    "status": "1",
                    "percentWeight": "0",
                    "imageUrl": "http://img-i.qfc.cn/upload/01/product/94/10/10316_300X300.jpg",
                    "imageCode": "MDF8cHJvZHVjdHwxMDMxNi5qcGc="
                },
                {
                    "status": "1",
                    "percentWeight": "0",
                    "imageUrl": "http://img-i.qfc.cn/upload/01/product/50/29/10315_300X300.jpg",
                    "imageCode": "MDF8cHJvZHVjdHwxMDMxNS5qcGc="
                }
            ],
            "productId": "20248"
        },
        {
            "updateTime": "2013-11-06 11:50:46",
            "status": "1",
            "productImage": {
                "status": "1",
                "percentWeight": "0",
                "imageUrl": "http://img-i.qfc.cn/upload/01/product/03/33/10354_300X300.jpg",
                "imageCode": "MDF8cHJvZHVjdHwxMDM1NC5qcGc="
            },
            "productPicsArray": [
                {
                    "status": "1",
                    "percentWeight": "0",
                    "imageUrl": "http://img-i.qfc.cn/upload/01/product/f0/ef/10356_300X300.jpg",
                    "imageCode": "MDF8cHJvZHVjdHwxMDM1Ni5qcGc="
                },
                {
                    "status": "1",
                    "percentWeight": "0",
                    "imageUrl": "http://img-i.qfc.cn/upload/01/product/87/2d/10355_300X300.jpg",
                    "imageCode": "MDF8cHJvZHVjdHwxMDM1NS5qcGc="
                },
                {
                    "status": "1",
                    "percentWeight": "0",
                    "imageUrl": "http://img-i.qfc.cn/upload/01/product/03/33/10354_300X300.jpg",
                    "imageCode": "MDF8cHJvZHVjdHwxMDM1NC5qcGc="
                }
            ],
            "productId": "20260"
        },
        {
            "updateTime": "2013-11-06 11:50:45",
            "status": "1",
            "productImage": {
                "status": "1",
                "percentWeight": "0",
                "imageUrl": "http://img-i.qfc.cn/upload/01/product/83/37/10374_300X300.jpg",
                "imageCode": "MDF8cHJvZHVjdHwxMDM3NC5qcGc="
            },
            "productPicsArray": [
                {
                    "status": "1",
                    "percentWeight": "0",
                    "imageUrl": "http://img-i.qfc.cn/upload/01/product/83/37/10374_300X300.jpg",
                    "imageCode": "MDF8cHJvZHVjdHwxMDM3NC5qcGc="
                }
            ],
            "productId": "20263"
        }
    ],
    "downloadStatus": {
        "percent": "0.00",
        "status": "1",
        "size": "0.00"
    }
}
	 */
}
