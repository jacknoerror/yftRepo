package com.qfc.yft.ui.tabs.chat;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;

import com.qfc.yft.R;
import com.qfc.yft.data.chat.DataManager;
import com.qfc.yft.ui.TitleManager;
import com.qfc.yft.vo.chat.SystemParams;


public class ChatHistoryActivity extends Activity  {

	private ListView mListView;
	private ChatMsgAdapter mAdapter;

	private List<ChatMsgEntity> mDataArrays = null;

	private long id;// 用户Id
	private int type;// 用户类型
	private String faceIndex;

//	private ImageButton menuButton;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.chat_history);
		Bundle bundle = getIntent().getExtras();
		id = bundle.getLong("id");
		type = bundle.getInt("type");
		faceIndex = bundle.getString("faceIndex");
		initView();
//		MyApplication.getInstance().addActivity(this);//TODO
		initBuildData();
	}

	public void initView() {
		/*TextView topTilTextView = (TextView) this.findViewById(R.id.topTile);
		topTilTextView.setText("聊天记录");*/
		/*Button backButton = (Button) findViewById(R.id.back_button);
		backButton.setVisibility(View.VISIBLE);*/
		/*menuButton = (ImageButton) findViewById(R.id.menu_butt);
		menuButton.setVisibility(View.VISIBLE);
		menuButton.setImageResource(R.drawable.mm_title_btn_delete_normal);*/
		/*backButton.setOnClickListener(this);
		menuButton.setOnClickListener(this);*/
		TitleManager titleManager = new TitleManager(this);
		titleManager.setTitleName("聊天记录");
		titleManager.initTitleBack();

		mListView = (ListView) findViewById(R.id.listview);
		mAdapter = createTreeView(mListView);
		TextView emptyView = (TextView) findViewById(R.id.empty_text);
		emptyView.setVisibility(View.GONE);
		mListView.setEmptyView(emptyView);

	}

	private Handler eventHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				for (ChatMsgEntity chatMsgEntity : mDataArrays) {
					mAdapter.add(chatMsgEntity);
				}
				notifyDataSetChanged();
				break;

			}
		}
	};

	public void initBuildData() {
		new Thread() {
			public void run() {
				mDataArrays = DataManager.getInstance(ChatHistoryActivity.this)
						.getChatHistory(SystemParams.getInstance().getUserId(),
								id);
				eventHandler.sendEmptyMessage(0);
			}

		}.start();

	}

	public ChatMsgAdapter createTreeView(ListView listView) {

		ChatMsgAdapter mAdapter = new ChatMsgAdapter(this, id, type, faceIndex);
		listView.setAdapter(mAdapter);

		return mAdapter;

	}

	/*@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back_button:
			ChatHistoryActivity.this.finish();
			break;
		case R.id.menu_butt:
			// 删除聊天记录
			showUpData();
			break;
		}
	}*/

	private void showUpData() {
		/*final CustomAlertDialog ad = new CustomAlertDialog(
				ChatHistoryActivity.this);
		ad.setTitle("系统提示");
		ad.setMessage("您确定删除聊天记录?");
		ad.setNegativeButton(R.id.submit_butt, "确定", new OnClickListener() {

			@Override
			public void onClick(View v) {
				mAdapter.clear();
				notifyDataSetChanged();
				DataManager.getInstance(ChatHistoryActivity.this)
						.delChatHistory(SystemParams.getInstance().getUserId(),
								id);
				AllAdapterControl.getInstance().getChatListAdapter().clear();
				CachMsg.getInstance().removeMsg(id);
				CachMsg.getInstance().remove(id);
				AllAdapterControl.getInstance().getChatListAdapter()
						.notifyDataSetChanged();
				AllAdapterControl.getInstance().getConversationlistAdapter()
						.getRoot().removeNode(id);
				AllAdapterControl.getInstance().getConversationlistAdapter()
						.notifyDataSetChanged();
				DataManager.getInstance(ChatHistoryActivity.this)
						.deleteContact(id);
				ad.dismiss();
			}
		});
		ad.setNegativeButton(R.id.cancel_butt, "取消", new OnClickListener() {
			@Override
			public void onClick(View v) {
				ad.dismiss();
			}
		});*///TODO

	}

	public void notifyDataSetChanged() {
		mAdapter.notifyDataSetChanged();
		/*if (mDataArrays.size() <= 0) {
			menuButton.setVisibility(View.GONE);
		} else {
			menuButton.setVisibility(View.VISIBLE);
		}*/
	}

	@Override
	protected void onStart() {
		super.onStart();

	}

}
