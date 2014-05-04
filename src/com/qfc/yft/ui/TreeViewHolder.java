package com.qfc.yft.ui;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.qfc.yft.R;


public final class TreeViewHolder {
	private ImageView headImg;
	private TextView title;
	private TextView info;
	private boolean isSharing;
	private TextView time;
	private TextView chatBadge;
	private Button button;

	public TextView getChatBadge() {
		if (chatBadge == null) {
			chatBadge = (TextView) view.findViewById(R.id.chat_badge);
		}
		return chatBadge;
	}

	public boolean isSharing() {
		return isSharing;
	}

	public void setSharing(boolean isSharing) {
		this.isSharing = isSharing;
	}

	private View view;

	public TreeViewHolder(View view) {
		this.view = view;

	}

	public TextView getButton() {

		if (button == null) {
//			button = (Button) view.findViewById(R.id.add_butt);
			System.out.println("oh oh tree view holder");
		}
		return button;
	}

	public TextView getTime() {

		if (time == null) {
			time = (TextView) view.findViewById(R.id.time);
		}
		return time;

	}

	public ImageView getImg() {
		if (headImg == null) {
			headImg = (ImageView) view.findViewById(R.id.head_img);
		}
		return headImg;
	}

	public TextView getTitle() {
		if (title == null) {
			title = (TextView) view.findViewById(R.id.title);
		}

		return title;
	}

	public TextView getInfo() {
		if (info == null) {
			info = (TextView) view.findViewById(R.id.info);
		}

		return info;
	}

}
