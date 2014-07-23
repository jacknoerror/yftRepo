package com.qfc.yft.ui.common;

import java.io.IOException;
import java.util.Vector;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.widget.Button;
import android.widget.TextView;

import com.Zxing.camera.CameraManager;
import com.Zxing.decoding.InactivityTimer;
import com.Zxing.decoding.QRCaptureActivityHandler;
import com.Zxing.view.ViewfinderView;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.qfc.yft.R;
import com.qfc.yft.data.MyData;
import com.qfc.yft.net.HttpRequestTask;
import com.qfc.yft.net.action.ActionBuilder;
import com.qfc.yft.net.action.ActionReceiverImpl;
import com.qfc.yft.net.action.ActionRequestImpl;
import com.qfc.yft.net.action.collection.SaveCollectionReq;
import com.qfc.yft.ui.MyPortal;
import com.qfc.yft.util.JackRexUtil;
import com.qfc.yft.util.JackUtils;
import com.qfc.yft.vo.User;

public class QRCaptureActivity extends Activity implements Callback,ActionReceiverImpl{
	private final String TAG = "QR_Capture";
	
	private QRCaptureActivityHandler handler;
	private ViewfinderView viewfinderView;
	private boolean hasSurface;
	private Vector<BarcodeFormat> decodeFormats;
	private String characterSet;
	private TextView txtResult;
	private InactivityTimer inactivityTimer;
	private MediaPlayer mediaPlayer;
//	private boolean playBeep;
//	private static final float BEEP_VOLUME = 0.10f;
	private boolean vibrate;
	public  Button mButton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.qr_capture);
		//初始化 CameraManager
		CameraManager.init(getApplication());

		viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);
		txtResult = (TextView) findViewById(R.id.txtResult);
		hasSurface = false;
		inactivityTimer = new InactivityTimer(this);
		
//		Log.i(TAG, "qr create");
		setResult(RESULT_OK);//0624
	}
	@Override
	protected void onResume() {
		super.onResume();
		SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
		SurfaceHolder surfaceHolder = surfaceView.getHolder();
		if (hasSurface) {
			initCamera(surfaceHolder);
		} else {
			surfaceHolder.addCallback(this);
			surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		}
		decodeFormats = null;
		characterSet = null;

//		playBeep = true;
//		AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
//		if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
//			playBeep = false;
//		}
//		initBeepSound();
		vibrate = true;
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (handler != null) {
			handler.quitSynchronously();
			handler = null;
		}
		CameraManager.get().closeDriver();
	}

	@Override
	protected void onDestroy() {
		inactivityTimer.shutdown();
		super.onDestroy();
	}

	private void initCamera(SurfaceHolder surfaceHolder) {
		try {
			CameraManager.get().openDriver(surfaceHolder);
		} catch (IOException ioe) {
			return;
		} catch (RuntimeException e) {
			return;
		}
		if (handler == null) {
			handler = new QRCaptureActivityHandler(this, decodeFormats,
					characterSet);
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		if (!hasSurface) {
			hasSurface = true;
			initCamera(holder);
		}

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		hasSurface = false;

	}

	public ViewfinderView getViewfinderView() {
		return viewfinderView;
	}

	public Handler getHandler() {
		return handler;
	}

	public void drawViewfinder() {
		viewfinderView.drawViewfinder();

	}

	public void handleDecode(Result obj, Bitmap barcode) {
		inactivityTimer.onActivity();
		viewfinderView.drawResultBitmap(barcode);
		 playBeepSoundAndVibrate();
		/*txtResult.setText(obj.getBarcodeFormat().toString() + ":"
				+ obj.getText());*/
		goEasy(obj.getText());
	}
	/**
	 * http://m.qfc.cn/products/detail-产品id.html
	 * 
	 * @param qrResult
	 */
	private void goEasy(String qrResult){
		User me = MyData.data().getMe();
		Log.i("qr_result",qrResult );
		String begins = "?data=";
		if(JackRexUtil.checkRE(JackRexUtil.JackReRules.RE_RULE_ONLY_DIGIT, qrResult)){
			handleBarCode(Long.parseLong(qrResult),me);
			return;
		}else if(qrResult.contains(".htm")&&qrResult.startsWith("http://m.qfc.cn/products/detail-")){
			String substring = qrResult.substring(qrResult.indexOf("detail-")+"detail-".length(), qrResult.lastIndexOf(".htm"));
			long parseInt = Long.parseLong(substring);
			handleBarCode(parseInt,me);
			return;
		}else if(!qrResult.contains(begins)) {
			back(false);
			return;
		}else{
			
			String data = qrResult.substring(qrResult.indexOf(begins)+begins.length());
			String splitEx = "-|-";
			String anotherSplitEx = "&";
			if(!data.contains(splitEx)&&data.contains(anotherSplitEx)) splitEx = "&";
			String[] strs = data.split(splitEx);//
			if(strs.length<5) {back(false);return;}
			String shopId = strs[0];
			String shopName=strs[2];
			String shopMott=strs[4];
	//		Log.i("qr_result", shopId+"_"+shopName+"_"+shopMott);
			if(null!=me&&shopId.equals(me.getShopId()+"")){
				AlertDialog.Builder builder = new Builder(getReceiverContext());
				  builder.setMessage("不能收藏自己的商铺哦！");
				  builder.setTitle("提示");
				  builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
				   @Override
				   public void onClick(DialogInterface dialog, int which) {
				    dialog.dismiss();
				     finish();
				   }
				  }).show();
			}else{
				letMeWatchOnThisShopId = shopId;
				if(!letMeWatchOnThisShopId.equals(MyData.data().getMe().getShopId()+"")){
					
					ActionRequestImpl actReq = new SaveCollectionReq(MyData.data().getMe().getId(), Integer.parseInt(letMeWatchOnThisShopId), SaveCollectionReq.COLLECTTYPE_COMPANY, "");
					ActionBuilder.getInstance().request(actReq , this);
				}else{
					AlertDialog.Builder builder = new Builder(this);
					  builder.setMessage("不能收藏自己");
	
					  builder.setTitle("提示");
					  
					  builder.setNegativeButton("确认", new DialogInterface.OnClickListener() {
	
					   @Override
					   public void onClick(DialogInterface dialog, int which) {
					    dialog.dismiss();
					   }
					  });
					  
					  builder.show();
				}
			}
		}
	}
	private void handleBarCode(long parseInt, User me) {
		long productId = parseInt;
		if(productId>0&&null!=me&&me.getId()>0){
			ActionBuilder.getInstance().request(new SaveCollectionReq(me.getId(), productId, 1), this);
		}
		
		
	}
	private String letMeWatchOnThisShopId="";
	private void back(boolean succeed){
		String hintText="";
		if(succeed){
			hintText ="收藏成功！";
		}else{
			hintText ="您已经收藏过了！";//TODO
		}
		AlertDialog.Builder builder = new Builder(getReceiverContext());
		  builder.setMessage(hintText);

		  builder.setTitle("提示");

		  builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {

		   @Override
		   public void onClick(DialogInterface dialog, int which) {
		    dialog.dismiss();

		   }
		  }).show().setOnDismissListener(new DialogInterface.OnDismissListener() {
			
			@Override
			public void onDismiss(DialogInterface dialog) {
			    QRCaptureActivity.this. finish();
			}
		});
	}
	
	
	
	/*private void initBeepSound() {
		if (playBeep && mediaPlayer == null) {
			// The volume on STREAM_SYSTEM is not adjustable, and users found it
			// too loud,
			// so we now play on the music stream.
			setVolumeControlStream(AudioManager.STREAM_MUSIC);
			mediaPlayer = new MediaPlayer();
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			//mediaPlayer.setOnCompletionListener(beepListener);

			AssetFileDescriptor file = getResources().openRawResourceFd(
					R.raw.beep);
			try {
				mediaPlayer.setDataSource(file.getFileDescriptor(),
						file.getStartOffset(), file.getLength());
				file.close();
				mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
				mediaPlayer.prepare();
			} catch (IOException e) {
				mediaPlayer = null;
			}
		}
	}*/

	private static final long VIBRATE_DURATION = 200L;

	private void playBeepSoundAndVibrate() {
		if ( mediaPlayer != null) {//playBeep
			mediaPlayer.start();
		}
		if (vibrate) {
			Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
			vibrator.vibrate(VIBRATE_DURATION);
		}
	

	/**
	 * When the beep has finished playing, rewind to queue up another one.
	 */
	 final OnCompletionListener beepListener = new OnCompletionListener() {
		public void onCompletion(MediaPlayer mediaPlayer) {
			mediaPlayer.seekTo(0);
		}
	};
	
	
    }
	@Override
	public boolean response(String result) {
					back(result.contains("true"));
		return true;
	}
	@Override
	public Context getReceiverContext() {
		return this;
	};
}
