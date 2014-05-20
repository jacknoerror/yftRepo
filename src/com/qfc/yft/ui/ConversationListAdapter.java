package com.qfc.yft.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.qfc.yft.CimConsts;
import com.qfc.yft.R;
import com.qfc.yft.data.CachMsg;
import com.qfc.yft.net.CimDownloadheadImg;
import com.qfc.yft.net.chat.Config;
import com.qfc.yft.utils.JackUtils;
import com.qfc.yft.vo.SystemParams;


public class ConversationListAdapter extends BaseAdapter {
	private TreeNode root = new TreeNode(true);
	private Context context = null;

	public TreeNode getRoot() {
		return root;
	}

	public ConversationListAdapter(Context context) {
		this.context = context;

		this.root.setExpand(true);
	}

	public int getCount() {
		return root.size();
	}

	public Object getItem(int position) {
		return root.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		TreeViewHolder holder = null;
		if (convertView == null) {
			LayoutInflater mInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = mInflater.inflate(R.layout.contact_tree, null);
			holder = new TreeViewHolder(convertView);
			convertView.setPadding(10, 5, 0, 0);
			convertView.setTag(holder);
		} else {
			holder = (TreeViewHolder) convertView.getTag();
		}
		final TreeNode treeNode = root.get(position);
		if (treeNode.getType() != -1) {
			showUserHead(convertView, holder, treeNode);
		}
		if (CachMsg.getInstance().getCount(treeNode.id) > 0) {
			holder.getChatBadge().setVisibility(View.VISIBLE);
			holder.getChatBadge().setText(String.valueOf(treeNode.getBadge()));
		} else {
			holder.getChatBadge().setVisibility(View.GONE);
		}
		holder.getTime().setVisibility(View.VISIBLE);
		holder.getTime().setText(treeNode.getTime());
		holder.getTitle().setText(treeNode.getTitle());
		
		String description = treeNode.getDescription();
		if(description.length()>15) description = description.substring(0, 14);
		holder.getInfo().setText(
				Html.fromHtml(description,
						JackUtils.getImageGetter(context), null));
		return convertView;
	}

	private void showUserHead(View convertView, TreeViewHolder holder,
			final TreeNode node) {

		Bitmap bitmap = null;
		if (node.getType() == CimConsts.ConnectUserType.GROUP) {
			bitmap = BitmapFactory.decodeResource(convertView.getResources(),
//					R.drawable.list_grouphead_normal);
					R.drawable.ic_launcher);
		} else if (node.getType() == CimConsts.ConnectUserType.SYS) {
			bitmap = BitmapFactory.decodeResource(convertView.getResources(),
//					R.drawable.gg);
					R.drawable.ic_launcher);
		} else {
			bitmap = BitmapFactory.decodeFile(JackUtils.getUserHeadPath(node
					.getFaceIndex()));

			if (bitmap == null) {
				bitmap = BitmapFactory.decodeResource(context.getResources(),
//						R.drawable.header);
						R.drawable.ic_launcher);
				if (node.getFaceIndex() != null
						&& !node.getFaceIndex().equals("0")) {
					CimDownloadheadImg.getHeadImg(node.getFaceIndex(), null);
				}
			}
			bitmap = JackUtils.getUserHead(context, bitmap, node.getStatus());
		}

		ImageView imageView = holder.getImg();
		imageView.setImageBitmap(bitmap);
		imageView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				if (node.getType() != CimConsts.ConnectUserType.GROUP) {

					/*StringBuffer stringBuffer = new StringBuffer(
							Config.FriendMaterial);
					stringBuffer.append("?sessionId=")
							.append(SystemParams.getInstance().getSessionId())
							.append("&userId=")
							.append(String.valueOf(node.getId()));
					Intent intent = new Intent();
					intent.setClass(context, FriendInfoActivity.class);
					intent.putExtra("userName", node.getTitle());
					intent.putExtra("url", stringBuffer.toString());
					context.startActivity(intent);*/
					System.out.println("click sth |ListAdapter");
				}
			}
		});
	}
}
