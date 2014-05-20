package com.qfc.yft.ui.tabs.chat;

import java.io.File;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ggwork.net.socket.SocketProtocol;
import com.qfc.yft.CimConsts;
import com.qfc.yft.R;
import com.qfc.yft.YftData;
import com.qfc.yft.YftValues;
import com.qfc.yft.YftValues.RequestType;
import com.qfc.yft.data.CachMsg;
import com.qfc.yft.entity.SimpleCompany;
import com.qfc.yft.net.HttpReceiver;
import com.qfc.yft.net.HttpRequestTask;
import com.qfc.yft.net.chat.Config;
import com.qfc.yft.net.chat.IWSCallback;
import com.qfc.yft.ui.AllAdapterControl;
import com.qfc.yft.ui.BuildData;
import com.qfc.yft.ui.ConversationListAdapter;
import com.qfc.yft.ui.TreeNode;
import com.qfc.yft.ui.YftActivityGate;
import com.qfc.yft.ui.account.ChatLoginHelper;
import com.qfc.yft.ui.tabs.HubActivity;
import com.qfc.yft.utils.JackUtils;
import com.qfc.yft.vo.CimGroup;
import com.qfc.yft.vo.CimWSReturn;
import com.qfc.yft.vo.SystemParams;


public class ChatActivity extends Activity implements OnClickListener {
	
	private Button mBtnSend;
	private EditText mEditTextContent;
	private ListView mListView;
	private ChatMsgAdapter mAdapter;
	ImageView shopBtn;
//	private ImageButton addButt;

	TextView header;
	ImageView mDialogText;
	private String userName;// 用户名
	private long id;// 用户Id
	private int type;// 用户类型
	private String faceIndex;
	
	SimpleCompany sc;//0319

	private String path_img = "";
	private String pathFileName = "";
	private int CAMERA = 10;
	private int selectCode = 20;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_chat);
		Bundle bundle = getIntent().getExtras();
		userName = bundle.getString("userName");
		id = bundle.getLong("id");
		type = bundle.getInt("type");
		faceIndex = bundle.getString("faceIndex");
		initView();
		HubActivity.updateChatTabCount();//taotao 0220
	}

	public void initView() {
		TextView topTilTextView = (TextView) this.findViewById(R.id.tv_chat);
		topTilTextView.setText(userName);
		this.findViewById(R.id.btn_titlebackk).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		/*
		Button backButton = (Button) findViewById(R.id.back_button);
		backButton.setVisibility(View.VISIBLE);*/
		/*ImageButton menuButton = (ImageButton) findViewById(R.id.menu_butt);
		menuButton.setVisibility(View.VISIBLE);
		menuButton.setImageResource(R.drawable.top_button_menu_selector);*/
		mBtnSend = (Button) findViewById(R.id.btn_send);
		/*addButt = (ImageButton) findViewById(R.id.btn_add);
		addButt.setOnClickListener(this);*/
		mBtnSend.setOnClickListener(this);
		/*backButton.setOnClickListener(this);
		menuButton.setOnClickListener(this);*/
		mEditTextContent = (EditText) findViewById(R.id.et_sendmessage);
		mListView = (ListView) findViewById(R.id.listview);
		
		initHeader();
		header.setVisibility(View.INVISIBLE);
		mAdapter = createTreeView(mListView);
		AllAdapterControl.getInstance().setChatListView(mListView);
		AllAdapterControl.getInstance().setChatListAdapter(mAdapter);
		CachMsg.getInstance().remove(id);
//		CachActivity.getInstance().put("ChatActivity", this);//TODO
		CachMsg.getInstance().setUserId(id);
		
		shopBtn = (ImageView)findViewById(R.id.btn_goshop);
		sc = YftData.data().getStoredShop((int)id);
		shopBtn.setOnClickListener(this);
		if(sc!=null){//0319
		}else{
			shopBtn.setVisibility(View.INVISIBLE);
			new HttpRequestTask(new HttpReceiver() {
				@Override
				public void response(String result) throws JSONException {
					JSONObject job = YftValues.getResultObject(result);
					if(null!=job){
						if(null==sc)sc = new SimpleCompany();
						sc.shopId=job.optInt("shopId");
						sc.shopName=job.optString("shopName"	);
						sc.memberType=job.optInt("memberType");
						
						if(sc.shopId>0){
							YftData.data().storeShopById(sc);
							shopBtn.setVisibility(View.VISIBLE);
						}
					}
				}
				
				@Override
				public Context getReceiverContext() {
					return null;//
				}
			}).execute(YftValues.getHTTPBodyString(RequestType.USERSHOP, "38316"));//texTalk indeed
		}
	}
	
	@Override
	protected void onResume() {
		loadChatMsgs();
		if(YftValues.FZL_RELOGIN){
			JackUtils.showDialog(this, "您的纺织聊账号未登录或从异地登录了!", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
//					ChatLoginHelper.getInstance().logInChatGo(YftData.data(), password)
					finish();
				}
			});
		}
		super.onResume();
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	
	private void loadChatMsgs(){//taotao 20131225 从create中移到onresume ，确保从后台返回时重新load数据，同时解决了拍完照不重新load的问题
		if(mAdapter==null) return ;
		List<ChatMsgEntity> coll = CachMsg.getInstance().getUserChatList(id);
		mAdapter.clear();
		for (ChatMsgEntity chatMsgEntity : coll) {
			mAdapter.add(chatMsgEntity);
		}
		if(coll.size()>0&&null!=header&&header.getVisibility()!=View.VISIBLE){
			header.setVisibility(View.VISIBLE);
		}
	}

	/**
	 * 
	 */
	private void initHeader() {
		header = new TextView(this	);
		header.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.putExtra("id", id);
				intent.putExtra("type", type);
//					intent.putExtra("userName", userName);
				intent.putExtra("faceIndex", faceIndex);
				intent.setClass(ChatActivity.this, ChatHistoryActivity.class);
				startActivity(intent);
				
			}
		});
		header.setText("更多历史信息");
		header.setGravity(Gravity.CENTER);
		mListView.addHeaderView(header);
	}
	
	public ChatMsgAdapter createTreeView(ListView listView) {

		ChatMsgAdapter mAdapter = new ChatMsgAdapter(this, id, type, faceIndex);
		listView.setAdapter(mAdapter);

		// 支持翻屏
		listView.setOnTouchListener(new View.OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				return false;
			}
		});
		// 点击
		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> adapterView, View v,
					int childPosition, long nid) {

			}
		});

		listView.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> adapterView, View v,
					int childPosition, long nid) {

				return false;
			}

		});

		return mAdapter;

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_send:

			if (JackUtils.detect(ChatActivity.this)&&!YftValues.FZL_RELOGIN) {
				send();
			} else {
				JackUtils.showToast(ChatActivity.this, "发送失败");
			}

			break;
		case R.id.btn_goshop:
			YftActivityGate.goShop(ChatActivity.this, sc.shopId,sc.shopName, sc.memberType);
			break;
		/*case R.id.btn_add:
			photos();
			break;*///没有照相机功能
		/*case R.id.back_button:
			ChatActivity.this.finish();
			break;*/
		/*case R.id.menu_butt:
			Intent intent = new Intent();
			intent.putExtra("id", id);
			intent.putExtra("type", type);
			intent.putExtra("userName", userName);
			intent.putExtra("faceIndex", faceIndex);
			intent.setClass(ChatActivity.this, ChatMenuActivity.class);
			startActivity(intent);

			break;
		*///变成商铺进 
		}
	}

	/*private void photos() {
		pathFileName = String.valueOf(System.currentTimeMillis());
		path_img = pathFileName + ".jpg";
		Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(
				JackUtils.file_path + SystemParams.getInstance().getLoginId()
						+ "/images/", path_img)));
		intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, CAMERA);
		startActivityForResult(intent, CAMERA);
	}*/

	/**
	 * Activity回调
	 *//*
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == CAMERA && resultCode == Activity.RESULT_OK) {
			Intent intent = new Intent();
			intent.putExtra("fileName", path_img);
			intent.putExtra("type", 0);
			intent.setClass(ChatActivity.this, SurfaceViewActivity.class);
			startActivityForResult(intent, selectCode);
		} else if (requestCode == selectCode) {
			sendPhoto();
		}

	}*/

	/**
	 * 拍照回调消息处理
	 */
	/*private void sendPhoto() {
		String path = JackUtils.file_path
				+ SystemParams.getInstance().getLoginId() + "/images/";
		ChatMsgEntity entity = new ChatMsgEntity();
		entity.setDate(JackUtils.getDate());
		entity.setMsgType(false);
		entity.setText("<img  src=\"" + path_img + "\">");
		entity.setFileName(path_img);
		CachMsg.getInstance().addChatEntity(this, id, entity);

		Message message = new Message();
		message.what = 0;
		message.obj = entity;
		eventHandler.sendMessage(message);//taotao 20131225 注释 在onresume中刷新

		// 发送消息
		String ms = "<img  name=\"{small" + pathFileName + "}\"  "
				+ "hspace=1 vspace=1 src=\"{$USER_IMAGE_PATH$}" + "{small"
				+ pathFileName + "}.jpg" + "\">";

		SocketProtocol.socketSendMessXml(id, type, ms);
		// JackUtils.saveFullImage(path, path_img);

		// 上传图片
		CimUploadFile.call(path + "small" + path_img, String.valueOf(id),
				new IWSCallback() {
					@Override
					public void callback(CimWSReturn wsReturn) {
						if (wsReturn != null) {
							String mess = "" + wsReturn.getMessage()
									+ ",{small" + pathFileName + "}.jpg,"
									+ "{small" + pathFileName + "}";
							SocketProtocol
									.socketSendMessXml(
											id,
											CimConsts.MessageType.MT_WEB_CUSTOM_FACE_SENT,
											mess);

						}

					}

				});

	}*/

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK
				&& ((FaceRelativeLayout) findViewById(R.id.FaceRelativeLayout))
						.hideFaceView()) {
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	private Handler eventHandler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == 0) {

				ChatMsgEntity chatMsgEntity = (ChatMsgEntity) msg.obj;
				mAdapter.add(chatMsgEntity);
				mAdapter.notifyDataSetChanged();
				mListView.setSelection(mListView.getCount() - 1);
				try{
					setNode(chatMsgEntity.getText());
				}catch(NullPointerException e){//0326
//					e.printStackTrace();
					JackUtils.showToast(ChatActivity.this	, "用户不存在");//find id nil
				}

			}
		}
	};

	private void send() {

		String contString = mEditTextContent.getText().toString();
		contString = JackUtils.repaceMess(contString);
		contString = JackUtils.html(contString);
		if (contString.length() > 0) {
			mEditTextContent.setText("");
			ChatMsgEntity entity = new ChatMsgEntity();
			entity.setDate(JackUtils.getDate());
			entity.setMsgType(false);
			entity.setText(contString);
			contString = JackUtils.repaceMess(contString);
			SocketProtocol.socketSendMessXml(id, type, contString);
			CachMsg.getInstance().addChatEntity(this, id, entity);
			Message message = new Message();
			message.what = 0;
			message.obj = entity;
			eventHandler.sendMessage(message);
			if(null!=header) header.setVisibility(View.VISIBLE);
		} else {
			JackUtils.showToast(this, "消息内容不能为空");
		}
	}

	private void setNode(String contString) {
		TreeNode treeNode = AllAdapterControl.getInstance().conversationlistAdapter
				.getRoot().find(id);
		if (treeNode == null) {
			ConversationListAdapter listAdapter = AllAdapterControl.getInstance()
					.getConversationlistAdapter();
			TreeNode tn = BuildData.getInstance().addConversationNode(listAdapter, id, type,
					System.currentTimeMillis());
			if(null==tn){//taotao 0505 不限制id身份地添加到conversation中
				AllAdapterControl.getInstance().getFriendInfo(this, contString, id);
//				listAdapter.getRoot().add(tn);
			}

		} else {
			treeNode.setDescription(contString);
			treeNode.setTime(JackUtils.getChatTime(System.currentTimeMillis()));
		}
	}

	@Override
	protected void onStart() {
		super.onStart();
		mAdapter.notifyDataSetChanged();
		if (type == CimConsts.ConnectUserType.GROUP) {
			if (Config.bigGroup) {
				CimGroup cimGroup = SystemParams.getInstance().getGroupList()
						.getGroupById(id);
				if (cimGroup != null) {
					if (cimGroup.getUserId() != SystemParams.getInstance()
							.getUserId()) {
						SocketProtocol.socketAddTempGroupXml(id, type,
								SystemParams.getInstance().getUserName());
					}

				}

			}
		}
	}

	protected void onStop() {
		super.onStop();
		if (type == CimConsts.ConnectUserType.GROUP) {
			if (Config.bigGroup) {
				CimGroup cimGroup = SystemParams.getInstance().getGroupList()
						.getGroupById(id);
				if (cimGroup != null) {
					if (cimGroup.getUserId() != SystemParams.getInstance()
							.getUserId()) {
						SocketProtocol.socketOutTempGroupXml(id, type,
								SystemParams.getInstance().getUserName());
					}
				}

			}
		}
	}
}