package com.qfc.yft.ui.offline;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask.Status;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TabHost;

import com.qfc.yft.data.MyData;
import com.qfc.yft.net.action.ActionBuilder;
import com.qfc.yft.net.action.ActionReceiverImpl;
import com.qfc.yft.net.action.ActionRequestImpl;
import com.qfc.yft.net.action.ActionStrategies;
import com.qfc.yft.net.action.basic.ParseOffLineDataForAndriodReq;
import com.qfc.yft.ui.offline.entity.OfflineData;
import com.qfc.yft.util.JackUtils;
import com.qfc.yft.vo.User;

public class OfflineHelper implements ActionReceiverImpl{
	private static OfflineHelper offlinehelper;
	private OfflineHelper(){}
	public static OfflineHelper getInstance(){
	   if(null==offlinehelper){
	      offlinehelper = new OfflineHelper();
	   }
	   return offlinehelper;
	}

	/*public boolean needUpdate(){
		return false;
	}*/
	Activity oActivity;
	int shopId;
	private OfflineData latestOD;
	private boolean showToast;
	
	public OfflineHelper init(Activity activity){
		this.oActivity = activity;
		return this;
	}
	public OfflineHelper initKeeper(int shopId){
		this.shopId = shopId;
		OfflineDataKeeper.init( shopId);
		return this;
	}
	
	public void checkUpdateStatus(boolean showToastIfNotNeed){
		if(null==oActivity) throw new IllegalStateException("offlineHelper not inited");
		//is it downloading?
		if(OfflineDownloadBuilder.getInstance().getStatus() == Status.RUNNING){
			JackUtils.showDialog(oActivity, "��Ҫȡ����������������", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					OfflineDownloadBuilder downloader = OfflineDownloadBuilder.getInstance();
					if(downloader.getStatus() == Status.RUNNING){
						downloader.cancel();
					}
					dialog.dismiss();
				}
			});
		}else {		
			//��ñ���Data��JsonString
			String wholeLocalData = OfflineDataKeeper.getWholeLocalData();
			//������ش�δ��ʼ��Data����ó�ʼ����JsonString
			if(wholeLocalData.isEmpty()) wholeLocalData = OfflineUtil.getEmptyOfflineString(shopId+"");
			//��������
			ActionRequestImpl actReq = new ParseOffLineDataForAndriodReq(wholeLocalData);
			ActionBuilder.getInstance().request(actReq, this);
			showToast = showToastIfNotNeed;
		}
		
		
	}
	private void startDownload() {
		//�л����浽��������
		TabHost tabHost = MyData.data().getTabHost();
		tabHost.setCurrentTab(tabHost.getTabWidget().getTabCount()-1);//ע��ֵ
		//����builder
		OfflineDownloadBuilder.getInstance().execute(latestOD);
	}
	private void showDownloadDialog() {
		AlertDialog.Builder builder = new Builder(oActivity);
		User  mUser = MyData.data().getMe();
		builder.setMessage((mUser .getRealName().isEmpty()?mUser.getUserName():mUser.getRealName())+"���ã�ϵͳ��⵽�����µ�������Ҫ���أ��Ƿ��������أ�");
		  builder.setTitle("��ʾ");
		  builder.setPositiveButton("��ʼ����", new DialogInterface.OnClickListener() {

			   @Override
			   public void onClick(DialogInterface dialog, int which) {
			    dialog.dismiss();
			    startDownload();
			   }
			  });
		  builder.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {

		   @Override
		   public void onClick(DialogInterface dialog, int which) {
		    dialog.dismiss();
		   }
		  });
		  builder.show();
		
	}
	@Override
	public boolean response(String result) throws JSONException {
		JSONObject job = new JSONObject(ActionStrategies.getResultString(result));
		if(null!=job){
			latestOD = new OfflineData(job);
			if(OfflineUtil.needUpdate(latestOD)){
				//show dialog
				showDownloadDialog();
			}else{
				if(showToast)JackUtils.showToast(oActivity, "�������������Ѿ������µ���");//�л����棿
			}
		}
//			JackUtils.showToast(oActivity, "�Բ���,��ȡ��������ʧ��");
		
		return false;
	}
	@Override
	public Context getReceiverContext() {
		return oActivity;
	}
	
	
}
