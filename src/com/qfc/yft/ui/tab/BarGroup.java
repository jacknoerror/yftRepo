package com.qfc.yft.ui.tab;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask.Status;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qfc.yft.R;
import com.qfc.yft.data.MyData;
import com.qfc.yft.ui.MyPortal;
import com.qfc.yft.ui.common.StartPagerActivity;
import com.qfc.yft.ui.offline.OfflineDownloadBuilder;
import com.qfc.yft.ui.offline.OfflineHelper;
import com.qfc.yft.util.JackUtils;

public class BarGroup extends LinearLayout implements View.OnClickListener {

	LinearLayout bgContainer;
	int count;

	private LayoutInflater bgInflater;

	public BarGroup(Context context) {
		super(context);// use this constructor or be ordinary
		getInflater().inflate(R.layout.layout_custom_bargroup, this);
		bgContainer = (LinearLayout) findViewById(R.id.layout_bargroup_container);
	}

	public void addBar(BarData attrs) {
		if (null == bgContainer)
			return;
		LayoutInflater inflater = getInflater();
		// init
		View barView = inflater.inflate(R.layout.item_person, null);

		ImageView icimg = (ImageView) barView
				.findViewById(R.id.img_item_person_left);
		TextView descText = (TextView) barView
				.findViewById(R.id.tv_item_person);
		ImageView btnimg = (ImageView) barView
				.findViewById(R.id.img_item_person_right);

		if (null != attrs.icId)
			icimg.setImageResource(attrs.icId);
		if (null != attrs.desc)
			descText.setText(attrs.desc);
		if (null != attrs.btnId) {
			btnimg.setImageResource(attrs.btnId);
		}
		barView.setId(attrs.icId);

		// add
		if (count > 0) {// 分割线
		// View divider = inflater.inflate(R.layout.view_divider_person,
		// null);//unhelpfully
			ImageView dividerImg = new ImageView(getContext());
			dividerImg.setImageResource(R.drawable.divider_h_grey);
			dividerImg.setPadding(10, 0, 0, 0);
			bgContainer.addView(dividerImg);
		}
		bgContainer.addView(barView);
		barView.setOnClickListener(this);
		count++;
	}

	private LayoutInflater getInflater() {
		if (bgInflater == null) {
			bgInflater = (LayoutInflater) getContext().getSystemService(
					Context.LAYOUT_INFLATER_SERVICE);
		}
		return bgInflater;
	}

	@Override
	public void onClick(View v) {
		Log.i("BarGroup", "onclick:" + v.getId());

		Context context = getContext();
		switch (v.getId()) {
		case R.drawable.person_myshop:
			 MyPortal.goShop(getContext(),MyData.data().getMe().getShopId(), "我的商铺",MyData.data().getMe().getMemberType());
			break;
		case R.drawable.person_mycollection:
			MyPortal.goMyCollection(context);
			break;
		case R.drawable.person_myfriends:
			MyPortal.goMyPeople(context);
			break;
		case R.drawable.person_offlinemodel:

			break;
		case R.drawable.person_refreshmyshop:
			// OfflineDownloadBuilder.onClick();
			OfflineHelper.getInstance().checkUpdateStatus(true);
			break;
		case R.drawable.person_trash:
			if (OfflineDownloadBuilder.getInstance().getStatus() == Status.FINISHED) {
				((AlertDialog.Builder) getTag()).show();
			} else {
				JackUtils.showToast(getContext(), "正在下载，不能清空离线数据");
			}
			break;
		case R.drawable.person_moveshoprecommanded:
			MyPortal.goRecommend(context);
			break;
		case R.drawable.person_moveshopintro:
			go(StartPagerActivity.class);
			break;
		default:
			break;
		}
	}

	private <T> void go(Class<T> clazz) {
		Intent intent = new Intent();
		intent.setClass(this.getContext(), clazz);
		this.getContext().startActivity(intent);
	}

}
