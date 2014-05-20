package com.qfc.yft.ui.tabs.chat;


import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.ggwork.net.socket.SocketBuild;
import com.qfc.yft.CimConsts;
import com.qfc.yft.R;
import com.qfc.yft.YftApplication;
import com.qfc.yft.YftData;
import com.qfc.yft.YftValues;
import com.qfc.yft.data.DataManager;
import com.qfc.yft.entity.User;
import com.qfc.yft.net.chat.GIMSocketServer;
import com.qfc.yft.ui.AllAdapterControl;
import com.qfc.yft.ui.BuildData;
import com.qfc.yft.ui.ConversationListAdapter;
import com.qfc.yft.ui.TreeNode;
import com.qfc.yft.ui.tabs.ContentAbstractFragment;
import com.qfc.yft.utils.JackUtils;

public class TabChatFragment extends ContentAbstractFragment  {
	final String TAG = TabChatFragment.class.getName();
	
	ConversationListAdapter convListAdapter;
	
	@Override
	public void onResume() {
		if(null!=convListAdapter) convListAdapter.notifyDataSetChanged();
		super.onResume();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.i(TAG, "oncreate");
		
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	@Override
	public void initView() {
		mView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_chat, null);
		User mpUser = YftData.data().getMe();
		if(mpUser.getMemberType()==0){//游客
			YftValues.logout();
			getActivity().finish();
			return;
		}
		goConversation();//TODO 先判断是否成功登陆
		/*registerBroadcastReceiver();//main
		getActivity().startService(new Intent(getActivity(), GIMSocketServer.class));*/
		
	}
	
	
	
	/**
	 * do sth to init conversation data here
	 */
	private void goConversation() {
		
		Context context = getActivity();
		//1.createListview
		ListView conversationList =  (ListView)mView.findViewById(R.id.lv_chat_conversation);
		convListAdapter = YftData.data().getConvListAdapter();
		conversationList.setAdapter(convListAdapter);
		TextView tempEmptyTextView = (TextView)mView.findViewById(R.id.tv_empty);
//		tempEmptyTextView.setText("没有数据");
		conversationList.setEmptyView(tempEmptyTextView);
		// 点击
		conversationList.setOnItemClickListener(new OnItemClickListener() {
					public void onItemClick(AdapterView<?> adapterView, View v,
							int childPosition, long nid) {

						TreeNode treeNode = (TreeNode) adapterView
								.getItemAtPosition(childPosition);
						long id = treeNode.getId();
						int type = treeNode.getType();
						if (treeNode != null) {

							if (type == CimConsts.ConnectUserType.SYS) {

								System.out.println("conversation email");

							} else {
								Intent intent = new Intent();
								intent.setClass(getActivity(),
										ChatActivity.class);
								intent.putExtra("userName", treeNode.getTitle());
								intent.putExtra("id", id);
								intent.putExtra("type", type);
								intent.putExtra("faceIndex", treeNode.getFaceIndex());
								startActivity(intent);
								DataManager.getInstance(getActivity())
										.deleteContact(id);
								DataManager.getInstance(getActivity())
										.addContact(id, type);
								System.out.println("chat activity ??");
							}

						}

					}

				});
		//长按
		conversationList.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					final int position, long id) {
				final TreeNode treeNode = (TreeNode) parent
						.getItemAtPosition(position);
				JackUtils.showDialog(getActivity(), "是否要删除会话？",new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						convListAdapter.getRoot().remove(position);
						convListAdapter.notifyDataSetChanged();
						DataManager.getInstance(getActivity())
								.deleteContact(treeNode.getId());
						dialog.dismiss();
					}
				});
				return false;
			}
			
		});
		/*//2.AllAdapterControl
		AllAdapterControl.getInstance().setConversationlistAdapter(convListAdapter);
		//3.buildData
		final List<Long> idArrs = BuildData.getInstance().fullConversation(context, convListAdapter);
		Log.i(TAG, "idArrs:"+idArrs.size());
		new Thread() {
			public void run() {
				SocketBuild.sendConversation(idArrs);
			}
		}.start();*///moved to somewhere
	}
	
	@Override
	public void onHiddenChanged(boolean hidden) {
		Log.i(TAG, "hide:"+hidden);
		if(!hidden){
			/*final List<Long> idArrs = BuildData.getInstance().fullConversation(getActivity(), convListAdapter);
			new Thread() {
				public void run() {
					SocketBuild.sendConversation(idArrs);
				}
			}.start();
			convListAdapter.notifyDataSetChanged();*/
		}
		super.onHiddenChanged(hidden);
	}
	
}
