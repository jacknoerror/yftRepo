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
			JackUtils.showDialog(oActivity, "您要取消离线下载任务吗", new DialogInterface.OnClickListener() {
				
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
			//获得本地Data的JsonString
			String wholeLocalData = OfflineDataKeeper.getWholeLocalData();
			//如果本地从未初始化Data，获得初始化的JsonString
			if(wholeLocalData.isEmpty()) wholeLocalData = OfflineUtil.getEmptyOfflineString(shopId+"");
			//发送请求
			ActionRequestImpl actReq = new ParseOffLineDataForAndriodReq(wholeLocalData);
			ActionBuilder.getInstance().request(actReq, this);
			showToast = showToastIfNotNeed;
		}
		
		
	}
	private void startDownload() {
		//切换界面到个人中心
		TabHost tabHost = MyData.data().getTabHost();
		tabHost.setCurrentTab(tabHost.getTabWidget().getTabCount()-1);//注意值
		//启动builder
		OfflineDownloadBuilder.getInstance().execute(latestOD);
	}
	private void showDownloadDialog() {
		AlertDialog.Builder builder = new Builder(oActivity);
		User  mUser = MyData.data().getMe();
		builder.setMessage((mUser .getRealName().isEmpty()?mUser.getUserName():mUser.getRealName())+"您好，系统检测到您有新的数据需要下载，是否现在下载？");
		  builder.setTitle("提示");
		  builder.setPositiveButton("开始下载", new DialogInterface.OnClickListener() {

			   @Override
			   public void onClick(DialogInterface dialog, int which) {
			    dialog.dismiss();
			    startDownload();
			   }
			  });
		  builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

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
				if(showToast)JackUtils.showToast(oActivity, "您的离线数据已经是最新的了");//切换界面？
			}
		}
//			JackUtils.showToast(oActivity, "对不起,获取离线数据失败");
		
		return false;
	}
	@Override
	public Context getReceiverContext() {
		return oActivity;
	}
	
	
}
