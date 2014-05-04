package com.qfc.yft.ui.tabs.chat;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.qfc.yft.CimConsts;
import com.qfc.yft.R;
import com.qfc.yft.utils.JackUtils;
import com.qfc.yft.vo.SystemParams;


/**
 * 
 ****************************************** 
 * 
 * @文件名称 : ChatMsgAdapter.java
 * @创建时间 : 2013-1-27 下午02:33:16
 * @文件描述 : 消息数据填充起
 ****************************************** 
 */
public class ChatMsgAdapter extends BaseAdapter {
	private long id;
	private int type;
	private String faceIndex;

	private List<ChatMsgEntity> coll = new ArrayList<ChatMsgEntity>();

	public List<ChatMsgEntity> getColl() {
		return coll;
	}

	public void add(ChatMsgEntity chatMsgEntity) {
		coll.add(chatMsgEntity);

	}

	public void update(String fileId, String imgName) {

		for (ChatMsgEntity contString : coll) {
			if (contString.getText().contains(imgName.length()<=4?imgName:imgName.substring(0, imgName.length()-4))) {//taotao 0423 小兔变成了gif
				String s = contString.getText().replaceAll(JackUtils.srcRegEx,
						imgName);
				contString.setText(s);
				contString.setFileImg(fileId);
				contString.setFileName(imgName);

			}
		}

	}

	public static interface IMsgViewType {
		int IMVT_COM_MSG = 0;
		int IMVT_TO_MSG = 1;
	}

	public void clear() {
		coll.clear();

	}

	private LayoutInflater mInflater;
	private Context context;

	public ChatMsgAdapter(Context context, long id, int type, String faceIndex) {

		mInflater = LayoutInflater.from(context);
		this.context = context;
		this.id = id;
		this.type = type;
		this.faceIndex = faceIndex;
	}

	public int getCount() {
		return coll.size();
	}

	public Object getItem(int position) {
		return coll.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public int getItemViewType(int position) {
		ChatMsgEntity entity = coll.get(position);

		if (entity.getMsgType()) {
			return IMsgViewType.IMVT_COM_MSG;
		} else {
			return IMsgViewType.IMVT_TO_MSG;
		}

	}

	public int getViewTypeCount() {
		return 2;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		final ChatMsgEntity entity = coll.get(position);
		boolean isComMsg = entity.getMsgType();
		ChatNodeViewHolder viewHolder = null;
		if (convertView == null) {

			if (isComMsg) {
				convertView = mInflater.inflate(
						R.layout.chatting_item_msg_text_left, null);
			} else {
				convertView = mInflater.inflate(
						R.layout.chatting_item_msg_text_right, null);
			}
			viewHolder = new ChatNodeViewHolder(convertView);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ChatNodeViewHolder) convertView.getTag();
		}
		if (isComMsg) {
			viewHolder.getImageViewLeft().setImageBitmap(
					showUserHead(id, type, faceIndex));
		} else {
			viewHolder.getImageViewRight().setImageBitmap(
					showUserHead(SystemParams.getInstance().getUserId(), 0,
							SystemParams.getInstance().getFaceIndex()));
		}
		handlerData(entity, viewHolder);
		return convertView;
	}

	private Bitmap showUserHead(long userId, int type, String faceIndex) {
		Bitmap bitmap = null;

		if (type == CimConsts.ConnectUserType.GROUP) {
			bitmap = BitmapFactory.decodeResource(context.getResources(),
					R.drawable.ic_launcher);
		} else {
			bitmap = BitmapFactory.decodeFile(JackUtils
					.getUserHeadPath(faceIndex));
			if (bitmap == null) {
				bitmap = BitmapFactory.decodeResource(context.getResources(),
						R.drawable.ic_launcher);

			}
			// bitmap = JackUtils
			// .getUserHead(context, bitmap, node.getStatus());
		}

		return bitmap;

	}

	private void handlerData(final ChatMsgEntity entity,
			ChatNodeViewHolder viewHolder) {
		viewHolder.getTvContent().setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				boolean isResult = false;
				String fileId = entity.getFileImg();
				String fileName = entity.getFileName();
				if (fileName == null) { // 处理访客发过来的图片
					String msg = entity.getText();
					int index = msg.indexOf("fileId=");
					if (index >= 0) {
						isResult = true;
						fileId = msg.substring(index + 7, index + 20);
						entity.setFileImg(fileId);
						fileName = fileId + ".jpg";
						entity.setFileName(fileName);
					}
				} else {
					isResult = true;
				}
				if (isResult) {
					Intent intent = new Intent();
					intent.setClass(context, SurfaceViewActivity.class);
					intent.putExtra("type", 1);
					intent.putExtra("fileName", fileName);
					intent.putExtra("fileId", fileId);
					context.startActivity(intent);
				}
			}
		});

		viewHolder.getTvSendTime().setText(entity.getDate());
		viewHolder.getTvContent().setText(
				Html.fromHtml(entity.getText(),
						JackUtils.getImageGetter(context), null));
	}
}
