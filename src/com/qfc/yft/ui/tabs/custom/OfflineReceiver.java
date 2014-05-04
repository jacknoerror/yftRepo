package com.qfc.yft.ui.tabs.custom;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;

import com.qfc.yft.YftApplication;
import com.qfc.yft.YftData;
import com.qfc.yft.YftValues;
import com.qfc.yft.entity.User;
import com.qfc.yft.entity.offline.IOfflineConst;
import com.qfc.yft.entity.offline.OfflineData;
import com.qfc.yft.net.HttpReceiver;
import com.qfc.yft.utils.JackUtils;

public class OfflineReceiver implements HttpReceiver {
	Context mContext;
	DialogInterface.OnClickListener mListener;
	User mUser;
	OfflineData mOfflineData;
	
	public OfflineReceiver (Context context, DialogInterface.OnClickListener listener){
		this.mContext = context;
		this.mListener = listener;
		mUser = YftData.data().getMe();
	}

	@Override
	public void response(String result) {
		try {
			JSONObject jsonObj = new JSONObject(result);
			String resultStr = jsonObj.getString(YftValues.RESULT_OBJECT);
//			JackUtils.writeToSomeWhere(this, temp);
			JSONObject job = new JSONObject(resultStr);
			mOfflineData = new OfflineData(job);//应当对此od进行处理 、、TODO 应当处理，否则cancel后数据会不对
//			InfoHouse.getInstance().setOffline(mOfflineData);//存入infohouse 、、 //1209改为在下载开始后发生
//			InfoHouse.getInstance().commitOffPref();//提交offlinedata //1204
			seeODStatus(mOfflineData);
			
//			JackUtils.writeToSomeWhere(mContext, job.toString());//
		} catch (JSONException e) {
			JackUtils.showToast(getReceiverContext(), "获取更新数据失败");
			e.printStackTrace();
		}

	}

	@Override
	public Context getReceiverContext() {
		return mContext;
	}
	
	public OfflineData getOfflineData(){
		return mOfflineData;
	}
	
	
	private void seeODStatus(OfflineData od) {
		int status = od.getDownloadStatus().getStatus();
		switch (status) {
		case IOfflineConst.OFFSTATUS_NEED_DOWNLOAD:
				
			showOfflineGoOrNotDialog();
			break;
		case IOfflineConst.OFFSTATUS_DOWNLOADING:
			
			break;
		case IOfflineConst.OFFSTATUS_NEED_DELETE:
			
			break;
		case IOfflineConst.OFFSTATUS_HAS_DELETE:
			
			break;
		case IOfflineConst.OFFSTATUS_COMMON_STATUS:
			if(YftData.data().getHostTab().getCurrentTab()==3) JackUtils.showToast(mContext, "您的离线数据已经是最新的了");
			break;
		case IOfflineConst.OFFSTATUS_EMPTY_DATA:
			break;

		default:
			break;
		}
		
	}
	
	private void showOfflineGoOrNotDialog() {
		AlertDialog.Builder builder = new Builder(mContext);
		  builder.setMessage((mUser.getRealName().isEmpty()?mUser.getUserName():mUser.getRealName())+"您好，系统检测到您有新的数据需要下载，是否现在下载？");

		  builder.setTitle("提示");

		  builder.setPositiveButton("开始下载", mListener);
		  
		  builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

		   @Override
		   public void onClick(DialogInterface dialog, int which) {
		    dialog.dismiss();
		   }
		  });
		  
		  builder.show();
		
	}
}
