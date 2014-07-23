package com.qfc.yft.util;

import java.net.URL;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.qfc.yft.R;

public class UpgradeUtils {
	
	public static void upgrade(Context context) {
		
		new UpgradeUtils.UpgradeTask(context).execute(new String[]{});
	}
	
	static class UpgradeTask extends AsyncTask<String, Integer, String> {
		
		public static final String TAG = "YFT";
		public static final String APK_DOWNLOAD_PAGE = "http://192.168.199.101:8080/test";
		public static final String APK_DOWNLOAD_URL = "http://192.168.199.101:8080/test/yft.apk";
		public static final String VERSION_INFO_URL = "http://192.168.199.101:8080/test/yft.xml";
		public static final String APP_NAME = "yft";
		public static final String PACKAGE_NAME = "com.qfc.yft";
		
		private Context context;
		private ProgressDialog progressDialog;
		
		public UpgradeTask(Context context) {
			this.context = context;
		}
		
		// Do the long-running work in here
		@Override
		protected String doInBackground(String... params) {
			
			try {
				checkRemoteAppName();
				
				// Escape early if cancel() is called
				if(isCancelled()) {
					return "NEEDLESS";
				}
				
				if(isLatestVersion()) {
					return "NEED";
				} else {
					return "NEEDLESS";
				}
			} catch(Exception ex) {
				Log.e(TAG, "UpgradeTask.doInBackground fail", ex);
				return "EXCEPTION";
			}
		}
		
		@Override
		protected void onPreExecute() {
			progressDialog = JackUtils.showProgressDialog(context, "");
		}
		
		// This is called each time you call publishProgress()
	    protected void onProgressUpdate(Integer... progress) {
	    	
	    }
	    
	    // This is called when doInBackground() is finished
	    protected void onPostExecute(String result) {
	    	try {
	    		if("NEEDLESS".equals(result)) {
	    			alert(R.string.upgrade_alert_latest, false, true);
	    		} else if("NEED".equals(result)) {
					alert(R.string.upgrade_alert_deprecated, true, true);
				} else {
					alert(R.string.upgrade_alert_error, false, true);
				}
			} catch(Exception ex) {
				Log.e(TAG, "UpgradeTask.onPostExecute fail", ex);
				alert(R.string.upgrade_alert_error, false, true);
			} finally {
				if(null != progressDialog) {
					progressDialog.dismiss();
					progressDialog = null;
				}
			}
	    }
	    
	    private Element getVersionInfoRootElement() throws Exception {
			
			SAXReader reader = new SAXReader();
			Document document = reader.read(new URL(VERSION_INFO_URL));
			
			return document.getRootElement();
		}
		
		private void checkRemoteAppName() throws Exception {
			
			Element root = getVersionInfoRootElement();
			String appName = root.element("appName").getText();
			
			if(!APP_NAME.equals(appName)) {
				throw new Exception("app name is incorrect");
			}
		}
		
		private int getRemoteVersionCode() throws Exception {
			
			Element root = getVersionInfoRootElement();
			
			return Integer.parseInt(root.element("versionCode").getText());
		}
		
		private int getLocalVersionCode(Context context) throws Exception {
			
			PackageManager packageManager = context.getPackageManager();
			
			return packageManager.getPackageInfo(PACKAGE_NAME, 0).versionCode;
		}
		
		private boolean isLatestVersion() throws Exception {
			
			if(getRemoteVersionCode() > getLocalVersionCode(context)) {
				return true;
			}
			
			return false;
		}
		
		private void download() {
			
			Uri uri = Uri.parse(APK_DOWNLOAD_URL);
			
			Intent intent = new Intent(Intent.ACTION_VIEW, uri);
			
			context.startActivity(intent);
		}
		
		private void alert(int messageId, boolean positive, boolean negative) {
			
			AlertDialog.Builder dialog = new AlertDialog.Builder(context);
			dialog.setMessage(messageId);
			
			if(positive) {
				dialog.setPositiveButton(R.string.upgrade_btn_ok, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						download();
						dialog.dismiss();
					}
				});
			}
			
			if(negative) {
				dialog.setNegativeButton(R.string.upgrade_btn_cancel, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});
			}
			
			dialog.show();
		}
	}
}
