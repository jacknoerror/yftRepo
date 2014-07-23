package com.qfc.yft.ui.tabs.chat;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.qfc.yft.R;
import com.qfc.yft.data.MyData;
import com.qfc.yft.data.chat.CimConsts;
import com.qfc.yft.data.chat.DataManager;
import com.qfc.yft.net.NetStrategies;
import com.qfc.yft.ui.tabs.ContentAbstractFragment;
import com.qfc.yft.util.JackUtils;
import com.qfc.yft.vo.User;

public class TabChatFragment extends ContentAbstractFragment {

	ConversationListAdapter convListAdapter;
	
	@Override
	public void onResume() {
		if(null!=convListAdapter) convListAdapter.notifyDataSetChanged();
		super.onResume();
	}
	
	@Override
	public void initView() {
		initTitleManager();
		titleManager.setTitleName(getString(R.string.titlename_chat));
		User mpUser = MyData.data().getMe();
		if(mpUser.getMemberType()==0){//游客
			NetStrategies.logout();
			getActivity().finish();
			return;
		}
		goConversation();
	}

	@Override
	public int getLayoutRid() {
		return R.layout.fragment_chat;
	}
private void goConversation() {
		
		Context context = getActivity();
		//1.createListview
		ListView conversationList =  (ListView)mView.findViewById(R.id.lv_chat_conversation);
		convListAdapter = MyData.data().getConvListAdapter();
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
								intent.setClass(getActivity(),ChatActivity.class);
								intent.putExtra("userName", treeNode.getTitle());
								intent.putExtra("id", id);
								intent.putExtra("type", type);
								intent.putExtra("faceIndex", treeNode.getFaceIndex());
								startActivity(intent);
								DataManager.getInstance(getActivity())
										.deleteContact(id);
								DataManager.getInstance(getActivity())
										.addContact(id, type);
//								System.out.println("chat activity ??");
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
	}
}
