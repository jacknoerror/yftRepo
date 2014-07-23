package com.qfc.yft.ui.tabs.chat;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.qfc.yft.R;


public class ChatNodeViewHolder {
	public TextView tvSendTime;
	public TextView tvContent;
	public ImageView imageViewLeft;
	public ImageView imageViewRight;
	public boolean isComMsg = true;
	private View view;

	public ChatNodeViewHolder(View view) {
		this.view = view;

	}

	public TextView getTvSendTime() {

		if (tvSendTime == null) {
			tvSendTime = (TextView) view.findViewById(R.id.tv_sendtime);
		}

		return tvSendTime;
	}

	public TextView getTvContent() {
		if (tvContent == null) {
			tvContent = (TextView) view.findViewById(R.id.tv_chatcontent);
		}
		return tvContent;
	}

	public ImageView getImageViewLeft() {
		if (imageViewLeft == null) {
			imageViewLeft = (ImageView) view
					.findViewById(R.id.iv_userhead_left);
		}
		return imageViewLeft;
	}

	public ImageView getImageViewRight() {
		if (imageViewRight == null) {
			imageViewRight = (ImageView) view
					.findViewById(R.id.iv_userhead_right);
		}
		return imageViewRight;
	}

	public boolean isComMsg() {
		return isComMsg;
	}

}
