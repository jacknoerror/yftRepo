package com.qfc.yft.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;

import com.qfc.yft.CimConsts;
import com.qfc.yft.R;
import com.qfc.yft.net.chat.Config;
import com.qfc.yft.vo.SystemParams;


/**
 * 多级树适配器
 * 
 * @author zw.bai
 * 
 */
public class ExpandableListAdapter extends BaseExpandableListAdapter {
	private Context context = null;
	private LayoutInflater mInflater;
	private TreeNode root = new TreeNode(true);
	public TreeViewHolder holder = null;

	public ExpandableListAdapter(Context context) {
		this.context = context;
		this.root.setExpand(true);
		this.mInflater = LayoutInflater.from(context);
	}

	public TreeNode getRoot() {
		return root;
	}

	public TreeNode getFirstNode() {
		return root.getFolder(0);
	}

	public boolean areAllItemsEnabled() {
		return false;
	}

	public Object getChild(int groupPosition, int childPosition) {
		return root.getFolder(groupPosition).getLeaf(childPosition);
	}

	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {/*
		final TreeNode node = root.getFolder(groupPosition).getLeaf(
				childPosition);
		final TreeViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.contact_tree, null);
			holder = new TreeViewHolder(convertView);
			convertView.setPadding(10, 5, 0, 0);
			convertView.setTag(holder);
		} else {
			holder = (TreeViewHolder) convertView.getTag();
		}
		showUserHead(convertView, holder, node);
		holder.getTitle().setText(node.getTitle());
		holder.getInfo().setText(node.getDescription());
		return convertView;
	*/
		return null;
		}

	private void showUserHead(View convertView, TreeViewHolder holder,
			final TreeNode node) {/*
		Bitmap bitmap = null;
		if (node.getType() == CimConsts.ConnectUserType.GROUP) {
			bitmap = BitmapFactory.decodeResource(convertView.getResources(),
					R.drawable.list_grouphead_normal);
		} else {
			bitmap = BitmapFactory.decodeFile(CimUtils.getUserHeadPath(node
					.getFaceIndex()));
			if (bitmap == null) {
				if (node.getFaceIndex() != null
						&& !node.getFaceIndex().equals("0")) {
					CimDownloadheadImg.getHeadImg(node.getFac eIndex(), null);
				}
				bitmap = BitmapFactory.decodeResource(context.getResources(),
						R.drawable.header);
			}
			bitmap = CimUtils.getUserHead(context, bitmap, node.getStatus());
		}

		ImageView imageView = holder.getImg();
		imageView.setImageBitmap(bitmap);
		imageView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (node.getType() != CimConsts.ConnectUserType.GROUP) {

					StringBuffer stringBuffer = new StringBuffer(
							Config.FriendMaterial);
					stringBuffer.append("?sessionId=")
							.append(SystemParams.getInstance().getSessionId())
							.append("&userId=")
							.append(String.valueOf(node.getId()));
					Intent intent = new Intent();
					intent.setClass(context, FriendInfoActivity.class);
					intent.putExtra("userName", node.getTitle());
					intent.putExtra("url", stringBuffer.toString());
					context.startActivity(intent);
				}
			}
		});
	*/}

	public int getChildrenCount(int groupPosition) {
		return root.getFolder(groupPosition).leafSize();
	}

	public Object getGroup(int groupPosition) {
		return root.getFolder(groupPosition);
	}

	public int getGroupCount() {
		return root.folderSize();
	}

	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {/*
		final GoupTreeViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.group_tree, null);
			holder = new GoupTreeViewHolder(convertView);
			convertView.setPadding(10, 5, 0, 0);
			convertView.setTag(holder);
		} else {
			holder = (GoupTreeViewHolder) convertView.getTag();
		}
		TreeNode node = root.getFolder(groupPosition);
		if (node != null) {
			holder.getTitle().setText(node.getTitle());
		}
		return convertView;
	*/
		return null;}

	public boolean isEmpty() {
		return false;
	}

	public void onGroupCollapsed(int groupPosition) {

	}

	public void onGroupExpanded(int groupPosition) {

	}

	public boolean hasStableIds() {
		return false;
	}

	public boolean isChildSelectable(int groupPosition, int childPosition) {

		return true;
	}
}
