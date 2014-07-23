package com.qfc.yft.net.action.upload;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import android.os.Environment;
import android.util.Log;

import com.qfc.yft.MyApplication;
import com.qfc.yft.data.MyData;
import com.qfc.yft.net.NetStrategies;
import com.qfc.yft.net.action.ActionRequestImpl;
import com.qfc.yft.util.JackUtils;
import com.qfc.yft.util.SocketHttpRequester;

/**
 * 与别的actionRequest不同，直接调用doUpload方法运行
 * @author taotao
 * @Date 2014-7-22
 */
public class UploadToAlbumReq implements ActionRequestImpl {

	int albumId;
	byte[] Filedata;
	String userAuth;
	String src;
	String modelCode;
	String picDesc;
	
	
	public UploadToAlbumReq(int albumId, byte[] filedata, String picDesc) {
		super();
		this.albumId = albumId;
		Filedata = filedata;
		src = "app";
		modelCode = "galary";
		userAuth =  "dbVOSwUrI@api_param_add@O3X8DC3hF8ZA==";//TODO
		this.picDesc = picDesc;
	}

	@Override
	public String getApiName() {
//		return "http://192.168.192.107/imgsvc/upload/result.htm";
		return _UF.getUploadUrl();
	}

	@Override
	public String toHttpBody() {
//		NetStrategies.finishTheURL(halfwayParamMap(NetStrategies.getBasicParamMapInstance(getApiName())));
		return finishTheURL(halfwayParamMap(new HashMap<String, String>()));
	}

	@Override
	public Map<String, String> halfwayParamMap(Map<String, String> halfway) {
		halfway.put("albumId",""+albumId);
//		halfway.put("Filedata",""+new String(Filedata));//
		halfway.put("src",""+src);
		halfway.put("modelCode",""+modelCode);
		halfway.put("userAuth",""+userAuth);
		halfway.put("picDesc",""+picDesc);

		return halfway;
	}

	
	public static String finishTheURL(Map<String, String> map){
		if(null==map)return "";//0418
		StringBuffer url;
		url = new StringBuffer();
		//排序
		String[] arrays = new String[]{};
		arrays = map.keySet().toArray(arrays);
		Arrays.sort(arrays);
		//验签
			for(String str : arrays){
					url.append(str)
						.append("=")
						.append("\""+new String(map.get(str))+"\"")
						.append("; ");
			}
		Log.i("UploadToAlbumReq", "url::"+url);
		//拼接
//		url.append(URL_OPENAPI_VALIDCODE).append("=").append(JackUtils.getMD5(valid.toString()));
		return url.toString();
	}
	
	public String doUpload3(){
		
		try {
			String a = SocketHttpRequester.post(getApiName(), 
					halfwayParamMap(new HashMap<String, String>()), 
					new FormFile("filename.png", 
							new File(Environment.getExternalStorageDirectory()+"/qfc/imgs/398201/01/company/882575.png"), 
							"Filedata", null));
			if(a.contains("{")&&a.contains("}")) a = a.substring(a.indexOf("{"),a.indexOf("}"));
			return a;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	
	
	
}
