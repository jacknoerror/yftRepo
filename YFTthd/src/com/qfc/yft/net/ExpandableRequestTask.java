package com.qfc.yft.net;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.UnknownHostException;

import org.json.JSONException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ProgressBar;

import com.baidu.platform.comapi.map.r;
import com.qfc.yft.R;
import com.qfc.yft.data.Const;
import com.qfc.yft.data.NetConst;
import com.qfc.yft.data.OpenApiConst;
import com.qfc.yft.net.action.ActionReceiverImpl;
import com.qfc.yft.net.action.ActionRequestImpl;
import com.qfc.yft.net.action.ActionStrategies;
import com.qfc.yft.net.action.ExpandableActReqImpl;
import com.qfc.yft.util.JackUtils;




public class ExpandableRequestTask extends AsyncTask<Object, Integer, Integer>{
	private final String TAG = ExpandableRequestTask.class.getSimpleName(); 
	private static final int TIMEOUT = 30*1000;
	
//	private ActionReceiverImpl receiver;
	private ExpandableActReqImpl request;
	private Activity activyt;
	private String hintStr;
	public ExpandableRequestTask(ExpandableActReqImpl request , Activity activityToShowDialog ,String hintStr){
		this.request= request;
		this.activyt = activityToShowDialog;
		this.hintStr = hintStr;
//		this.receiver = receiver;
	}
	private ProgressDialog pDialog;
	@Override
	protected Integer doInBackground(Object... params) {
		Integer succussCount = 0;
		for(Object s : params){
			if(isCancelled()) break;
			try {
					if(ActionStrategies.getResultBoolean(doHttpRequest(request.setExpandableParam(s.toString()).toHttpBody() ))) {
						succussCount++;
						publishProgress(succussCount,params.length);
					}
			} catch(UnknownHostException e){
				publishProgress(1);
				e.printStackTrace();
			} catch (SocketTimeoutException e) {
				publishProgress(2);
				e.printStackTrace();
			} catch (IOException e) {
				publishProgress(10);
				e.printStackTrace();
			} catch (JSONException e) {//
				publishProgress(20);
				e.printStackTrace();
			}catch (ClassCastException e) {
				publishProgress(3);
				e.printStackTrace();
			}
			
		}
		return succussCount;
	}

	/**
	 * @param result
	 * @param params
	 * @return
	 */
	public static String doHttpRequest( String... params) throws UnknownHostException,SocketTimeoutException,IOException{
		URL url = null;
		HttpURLConnection connection = null;
		InputStreamReader in = null;
		try {
			
			url = new URL(Const._UF.getUrl());//0506
			if(Const.DEBUG){
				Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(NetConst.PROXY_HOST, 80)); 
				connection = (HttpURLConnection) url.openConnection(proxy);
			}else{
				connection = (HttpURLConnection) url.openConnection();
			}
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			connection.setRequestProperty("Charset", "utf-8");
			//�������ӷ�������ʱʱ��
			connection.setConnectTimeout(TIMEOUT);
			//���ôӷ�������ȡ���ݳ�ʱʱ��
			connection.setReadTimeout(TIMEOUT);
			DataOutputStream dop = new DataOutputStream(
					connection.getOutputStream());
			Log.i("doHttpRequest", params[0]);//
			dop.write(params[0].getBytes("utf-8"));//������ת��utf-8
			dop.flush();
			dop.close();

			in = new InputStreamReader(connection.getInputStream());
			BufferedReader bufferedReader = new BufferedReader(in);
			StringBuffer strBuffer = new StringBuffer();
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				strBuffer.append(line);
			}
			return  strBuffer.toString();
		
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
	}

	/**
	 * �����������û�����Excuteʱ�Ľӿڣ�������ִ��֮ǰ��ʼ���ô˷�����������������ʾ���ȶԻ���
	 */
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		if(activyt!=null){
			pDialog = ProgressDialog.show(activyt, "��ɽ���", "");
			pDialog.setContentView(R.layout.dialog_severalrequest);
			pDialog.setCancelable(false);
			pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//			pDialog.setm
		}
	}

	/**
	 * �൱��Handler����UI�ķ�ʽ�������������ʹ����doInBackground �õ��Ľ���������UI�� �˷��������߳�ִ�У�����ִ�еĽ����Ϊ�˷����Ĳ�������
	 */
	@Override
	protected void onPostExecute(Integer result) {
		super.onPostExecute(result);
		if(pDialog!=null) pDialog.dismiss();
		if(null!=hintStr&&hintStr.contains("%d")){
			JackUtils.showToast(activyt, String.format(hintStr, (int)result));
		}
		
		pDialog=null;
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		super.onProgressUpdate(values);
		Context pContext = activyt;
		
		if(null==pContext) return;//0224
		switch (values[0]) {
		case 0:
			if(null!=pDialog){
				pDialog.setMax(values[1]);
				pDialog.setProgress(values[0]);
			}
			break;
		case 1:
			JackUtils.showToast(pContext, "�����������磬���Ժ�����");
			break;
		case 2:
			JackUtils.showToast(pContext, "���ӳ�ʱ��������");
			break;
		case 20:
			JackUtils.showToast(pContext, "�Բ�������ʧ��");
			break;
		case 3:
			JackUtils.showToast(pContext, "���ݴ���");
			break;
		default:
//			if(pDialog!=null) pDialog.dismiss();
			JackUtils.showToast(pContext, "�Բ�������ʧ�ܣ�������");
			break;
		}
	}

	
}
