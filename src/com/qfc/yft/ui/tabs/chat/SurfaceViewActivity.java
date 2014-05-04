package com.qfc.yft.ui.tabs.chat;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.qfc.yft.R;
import com.qfc.yft.net.CimDownloadFile;
import com.qfc.yft.ui.custom.DragImageView;
import com.qfc.yft.utils.BitmapUtil;
import com.qfc.yft.utils.JackUtils;
import com.qfc.yft.vo.SystemParams;


/**
 * @author zw.Bai
 * @version 2011-7-8 下午03:48:30
 * 
 */
public class SurfaceViewActivity extends Activity implements OnClickListener {

	private ProgressDialog dialog;
	private String fileName;
	private String fileId;
	private int angle = 0;

	private int window_width, window_height;// 控件宽度
	private DragImageView dragImageView;// 自定义控件
	private int state_height;// 状态栏的高度

	private ViewTreeObserver viewTreeObserver;
	private Bitmap bm;
	private ImageView rotate_butt;
	private Button sendButt;
	private int type = 0;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_surface);
		Bundle bundle = getIntent().getExtras();
		fileName = bundle.getString("fileName");
		fileId = bundle.getString("fileId");
		type = bundle.getInt("type");

		WindowManager manager = getWindowManager();
		window_width = manager.getDefaultDisplay().getWidth();
		window_height = manager.getDefaultDisplay().getHeight();
		dragImageView = (DragImageView) findViewById(R.id.image);
		sendButt = (Button) findViewById(R.id.complete_butt);
		sendButt.setOnClickListener(this);
		setImageProperty();
		if (type == 0) {
			sendButt.setVisibility(View.VISIBLE);
		}
		rotate_butt = (ImageView) findViewById(R.id.rotate_butt);
		rotate_butt.setOnClickListener(this);
		bm = BitmapUtil.ReadBitmapPath(this, JackUtils.file_path
				+ SystemParams.getInstance().getLoginId() + "/images/"
				+ fileName, window_width, window_height);
		if (bm == null) {
			CimDownloadFile cimDownloadFile = new CimDownloadFile(fileId,
					fileName, this);
			cimDownloadFile.start();
			showProDialog();

		} else {
			dragImageView.setImageBitmap(bm);

		}

	}

	private void setImageProperty() {
		dragImageView.setmActivity(this);// 注入Activity.
		/** 测量状态栏高度 **/
		viewTreeObserver = dragImageView.getViewTreeObserver();
		viewTreeObserver
				.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

					@Override
					public void onGlobalLayout() {
						if (state_height == 0) {
							// 获取状况栏高度
							Rect frame = new Rect();
							getWindow().getDecorView()
									.getWindowVisibleDisplayFrame(frame);
							state_height = frame.top;
							dragImageView.setScreen_H(window_height
									- state_height);
							dragImageView.setScreen_W(window_width);
						}

					}
				});
	}

	public void showProDialog() {
		dialog = ProgressDialog.show(this,
				getResources().getString(R.string.app_name), "downloading", true, true);
	}

	public void dismissProDialog() {
		dialog.dismiss();
	}

	public void setImg() {
		bm = BitmapUtil.ReadBitmapPath(this, JackUtils.file_path
				+ SystemParams.getInstance().getLoginId() + "/images/"
				+ fileName, window_width, window_height);
		if (bm != null) {
			// setImageProperty();
			dragImageView.setImageBitmap(bm);
			JackUtils.saveFullImage(JackUtils.file_path
					+ SystemParams.getInstance().getLoginId() + "/images/",
					fileName);
		}

	}

	public void onDestroy() {
		if (bm != null && !bm.isRecycled()) {
			bm.recycle();
		}
		super.onDestroy();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rotate_butt:
			angle += 90;
			if (angle == 360) {
				angle = 0;
			}
			dragImageView
					.setImageBitmap(BitmapUtil.rotaingImageView(angle, bm));
			break;
		case R.id.complete_butt:
			BitmapUtil.saveImage(BitmapUtil.rotaingImageView(angle, bm),
					JackUtils.file_path
							+ SystemParams.getInstance().getLoginId()
							+ "/images/", fileName);
			SurfaceViewActivity.this.finish();

			break;
		}

	}
}
