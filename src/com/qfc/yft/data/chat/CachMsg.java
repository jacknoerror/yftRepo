package com.qfc.yft.data.chat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;

import com.qfc.yft.ui.tabs.chat.ChatMsgEntity;
import com.qfc.yft.util.JackUtils;
import com.qfc.yft.vo.chat.SystemParams;


public class CachMsg {
	private long userId = 0;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	Map<Long, List<ChatMsgEntity>> mapMsg = new HashMap<Long, List<ChatMsgEntity>>();;
	Map<Long, Integer> unreadMap = new HashMap<Long, Integer>();

	private static CachMsg instance = null;

	public static CachMsg getInstance() {
		if (instance == null) {
			instance = new CachMsg();
		}
		return instance;
	}

	public int getCount(long id) {
		Integer count = unreadMap.get(id);
		if (count == null) {
			return 0;
		} else {
			return count;
		}

	}

	public int fullCount() {
		int count = 0;
		for (Object o : unreadMap.keySet()) {
			count += unreadMap.get(o);
		}
		return count;

	}

	public void addCount(long id) {
		Integer count = unreadMap.get(id);
		if (count == null) {
			unreadMap.put(id, 1);
		} else {
			unreadMap.remove(id);
			unreadMap.put(id, count + 1);
		}
	}

	public void remove(long id) {
		unreadMap.remove(id);
	}

	public void removeMsg(long id) {
		mapMsg.remove(id);
	}

	public void addChatEntity(Context context, long id, String time,
			String name, String contString, boolean isSelf) {
		List<ChatMsgEntity> entities = mapMsg.get(id);
		if (entities == null) {
			entities = new ArrayList<ChatMsgEntity>();
			ChatMsgEntity entity = add(id, time, name, contString, isSelf);
			entities.add(entity);
			mapMsg.put(id, entities);
			DataManager.getInstance(context).addChatNotes(entity,
					SystemParams.getInstance().getUserId(), id);
		} else {
			ChatMsgEntity entity = add(id, time, name, contString, isSelf);
			entities.add(entity);
			DataManager.getInstance(context).addChatNotes(entity,
					SystemParams.getInstance().getUserId(), id);
		}

	}

	public void addChatEntity(Context context, long id,
			ChatMsgEntity chatMsgEntity) {
		List<ChatMsgEntity> entities = mapMsg.get(id);
		if (entities == null) {
			entities = new ArrayList<ChatMsgEntity>();
			entities.add(chatMsgEntity);
			mapMsg.put(id, entities);
		} else {
			entities.add(chatMsgEntity);

		}

		DataManager.getInstance(context).addChatNotes(chatMsgEntity,
				SystemParams.getInstance().getUserId(), id);

	}

	public void updateChatImg(Context context, String imgName, String fileId,
			long id) {
		DataManager.getInstance(context).updateChatImg(
				SystemParams.getInstance().getUserId(), id, imgName, fileId);
	}

	public void holdIMGChatEntity(long id, String imgName, String fileId) {
		List<ChatMsgEntity> entities = mapMsg.get(id);
		if (entities != null) {
			for (ChatMsgEntity contString : entities) {
				if (contString.getText().contains(imgName)) {
					String s = contString.getText().replaceAll(
							JackUtils.srcRegEx, imgName);
					contString.setText(s);
					contString.setFileImg(fileId);
					contString.setFileName(imgName);
				}
			}
		}

	}

	private ChatMsgEntity add(long id, String time, String name,
			String contString, boolean isSelf) {
		ChatMsgEntity entity = new ChatMsgEntity();
		entity.setDate(time);
		entity.setId(id);
		entity.setName(name);
		entity.setMsgType(isSelf);
		entity.setText(contString);
		return entity;
	}

	public List<ChatMsgEntity> getUserChatList(long id) {
		List<ChatMsgEntity> chatMsgEntities = null;
		chatMsgEntities = mapMsg.get(id);
		if (chatMsgEntities == null) {
			chatMsgEntities = new ArrayList<ChatMsgEntity>();
			mapMsg.put(id, chatMsgEntities);
		}
		return chatMsgEntities;
	}

	public void clear() {
		instance = null;
	}
}
