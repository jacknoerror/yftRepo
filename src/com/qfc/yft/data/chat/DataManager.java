package com.qfc.yft.data.chat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.qfc.yft.ui.tabs.chat.ChatMsgEntity;
import com.qfc.yft.util.JackUtils;
import com.qfc.yft.vo.chat.CimColumn;


public class DataManager {
	private String password;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	private static DataManager instance;
	private SQLiteDatabase mSQLiteDatabase = null;
	private static final String DATABASE_NAME = "cim";

	public void clear() {
		instance = null;
	}

	public static DataManager getInstance(Context ctx) {
		if (instance == null) {
			instance = new DataManager(ctx);
		}
		return instance;
	}

	public DataManager(Context ctx) {
		try {
			if (mSQLiteDatabase == null) {
				mSQLiteDatabase = ctx.openOrCreateDatabase(DATABASE_NAME,
						Context.MODE_PRIVATE, null);
				createTable("CREATE TABLE IF NOT EXISTS cimuser ( userName varchar (20),usePassword  varchar (50) )");

				// 最近聊过天的用户（包括群）

				createTable("CREATE TABLE IF NOT EXISTS cimlately ( laUserId  bigint(20) , laType  int(8),  laTime bigint(20) NOT NULL, laMess  text,  PRIMARY KEY (laUserId ) )");
				createTable("CREATE TABLE IF NOT EXISTS cimusermessagelog ("
						+ " umesId bigint(20) NOT NULL,"
						+ " umesuserId bigint(20) NOT NULL,"
						+ " umesReId bigint(20) NOT NULL, "
						+ "	umesOpUserId bigint(20) NOT NULL, "
						+ " umesSendTime  varchar(20) NOT NULL, "
						+ " umesMessageText  text,"
						+ " umesFileImg text, umesFileName text , "
						+ " umesType  int(8) , " + " PRIMARY KEY (umesId)) ");

				createTable("CREATE TABLE IF NOT EXISTS ec_column ( ecUserId  bigint(20) , typeId  varchar(50),  typeName varchar(50) , type  int(8), allowsAdd int(8) )");

			}

		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	public void addEcColumn(CimColumn cimColumn, long userId) {
		// String sql = "DELETE FROM ec_column WHERE   ecUserId =?";
		// deleteItem(sql, new Object[] { userId });
		String sql = "INSERT INTO ec_column (ecUserId,typeId,typeName,type,allowsAdd) VALUES (?,?,?,?,?)";
		inser(sql,
				new Object[] { userId, cimColumn.getTypeId(),
						cimColumn.getTypeName(), cimColumn.getType(),
						cimColumn.getAllowsAdd() });

	}

	public void delEcColumn(long userId) {
		String sql = "DELETE FROM ec_column WHERE   ecUserId =?";
		deleteItem(sql, new Object[] { userId });
	}

	public ArrayList<CimColumn> queryEcColumn(long userId) {
		String sql = "SELECT typeId,typeName,type,allowsAdd FROM ec_column WHERE ecUserId =?";
		Cursor cursor = query(sql, new String[] { String.valueOf(userId) });
		ArrayList<CimColumn> cimColumns = new ArrayList<CimColumn>();
		try {
			while (cursor.moveToNext()) {
				int top = 0;
				CimColumn cimColumn = new CimColumn();
				cimColumn.setTypeId(cursor.getString(top++));
				cimColumn.setTypeName(cursor.getString(top++));
				cimColumn.setAllowsAdd(cursor.getInt(top++));
				cimColumn.setType(cursor.getInt(top++));
				cimColumns.add(cimColumn);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cursor.close();
		}
		return cimColumns;

	}

	public void updateCimlately(long time, long userId) {
		String sql = "UPDATE cimlately SET laTime=?WHERE laUserId=? ";
		mSQLiteDatabase.execSQL(sql, new Object[] { time, userId });

	}

	public void addLoginName(String userName, String userPassword) {
		String sql = "insert into cimuser values(?,?)";
		inser(sql, new Object[] { userName, userPassword });
	}

	/**
	 * 查询
	 */
	public Cursor cur(String table, String[] columns) {
		Cursor cur = null;
		try {

			cur = mSQLiteDatabase.query(table, columns, null, null, null, null,
					null);
			// mSQLiteDatabase.query(table, columns, selection, selectionArgs,
			// groupBy, having, orderBy)

		} catch (Exception e) {
			e.printStackTrace();
		}

		return cur;
	}

	/**
	 * 查询
	 */
	public Cursor cur(String table, String[] columns, String where,
			String[] whereArgs) {
		Cursor cur = null;
		try {

			cur = mSQLiteDatabase.query(table, columns, where, whereArgs, null,
					null, null);
			// mSQLiteDatabase.query(table, columns, selection, selectionArgs,
			// groupBy, having, orderBy)

		} catch (Exception e) {
			e.printStackTrace();
		}

		return cur;
	}

	/**
	 * 创建表
	 */
	public void createTable(String sql) {

		try {
			mSQLiteDatabase.execSQL(sql);

		} catch (SQLException ex) {
			ex.printStackTrace();
		}

	}

	public void addChatNotes(ChatMsgEntity msgEntity, long userId, long umesReId) {
		String sql = "INSERT INTO cimusermessagelog (umesId,umesuserId ,umesReId,umesOpUserId,umesSendTime,umesMessageText,umesFileImg,umesFileName,umesType)  VALUES(?,?,?,?,?,?,?,?,?)";
		List<Object> list = new ArrayList<Object>();
		list.add(System.currentTimeMillis());
		list.add(userId);
		list.add(umesReId);
		list.add(msgEntity.getId());
		list.add(msgEntity.getDate());
		list.add(msgEntity.getText());
		list.add(msgEntity.getFileImg());
		list.add(msgEntity.getFileName());
		if (msgEntity.getMsgType() == false) {
			list.add(0);
		} else {
			list.add(1);
		}
		inser(sql, list.toArray());
		updateCimlately(System.currentTimeMillis(), umesReId);

	}

	public void updateChatImg(long userId, long umesReId, String imgName,
			String fileId) {
		String querySql = "SELECT umesId, umesMessageText FROM cimusermessagelog WHERE umesuserId=? AND umesReId =? AND umesMessageText like  '%"
				+ imgName + "%'";
		Cursor cursor = query(querySql, new String[] { String.valueOf(userId),
				String.valueOf(umesReId) });
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			while (cursor.moveToNext()) {
				Map<String, Object> map = new HashMap<String, Object>();
				int top = 0;
				long id = cursor.getLong(top++);
				String msg = cursor.getString(top++);
				map.put("id", id);
				map.put("msg", msg);
				list.add(map);

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cursor.close();
		}

		for (Map<String, Object> map : list) {
			String msg = map.get("msg").toString();
			long id = Long.parseLong(map.get("id").toString());
			String s = msg.replaceAll(JackUtils.srcRegEx, imgName);
			String sql = "UPDATE cimusermessagelog SET umesFileImg='" + fileId
					+ "',   umesFileName='" + imgName
					+ "',  umesMessageText ='" + s + "' WHERE umesId = " + id;
			updateItem(sql);
		}
	}

	public List<ChatMsgEntity> getChatHistory(long userId, long umesReId) {
		String querySql = "SELECT umesOpUserId,umesSendTime,umesMessageText,umesFileImg,umesFileName ,umesType FROM cimusermessagelog WHERE umesuserId=? AND umesReId =? ";
		List<ChatMsgEntity> msgEntities = new ArrayList<ChatMsgEntity>();
		Cursor cursor = query(querySql, new String[] { String.valueOf(userId),
				String.valueOf(umesReId) });
		try {

			while (cursor.moveToNext()) {
				int top = 0;
				ChatMsgEntity chatMsgEntity = new ChatMsgEntity();
				chatMsgEntity.setId(cursor.getLong(top++));
				chatMsgEntity.setDate(cursor.getString(top++));
				chatMsgEntity.setText(cursor.getString(top++));
				chatMsgEntity.setFileImg(cursor.getString(top++));
				chatMsgEntity.setFileName(cursor.getString(top++));
				int type = cursor.getInt(top++);
				if (type == 0) {
					chatMsgEntity.setMsgType(false);
				} else {
					chatMsgEntity.setMsgType(true);
				}
				msgEntities.add(chatMsgEntity);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cursor.close();
		}

		return msgEntities;

	}

	public void delChatHistory(long userId, long umesReId) {
		String sql = "DELETE FROM cimusermessagelog WHERE umesuserId=? AND umesReId =? ";
		deleteItem(
				sql,
				new String[] { String.valueOf(userId), String.valueOf(umesReId) });

	}

	public void inser(String sql, Object[] bindArgs) {
		try {
			mSQLiteDatabase.execSQL(sql, bindArgs);
		} catch (SQLException ex) {
			ex.printStackTrace();

		}
	}

	public void addContact(long id, int type) {
		String sql = "insert into cimlately (laUserId,laType ,laTime) VALUES( ?,?,?)";
		inser(sql, new Object[] { id, type, System.currentTimeMillis() });
	}

	public void deleteContact(long id) {
		deleteItem("delete from cimlately where laUserId=?  ",
				new Object[] { id });
	}

	public Cursor query(String sql, String[] selectionArgs) {
		Cursor cursor = null;
		try {
			cursor = mSQLiteDatabase.rawQuery(sql, selectionArgs);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return cursor;

	}

	public int queryCount(String sql, String[] selectionArgs) {
		int count = 0;
		Cursor cursor = mSQLiteDatabase.rawQuery(sql, selectionArgs);
		if (cursor.moveToNext()) {
			count = cursor.getInt(0);
		}
		cursor.close();
		return count;

	}

	/**
	 * 删除记录
	 */
	public void deleteItem(String sql, Object[] bindArgs) {
		try {
			mSQLiteDatabase.execSQL(sql, bindArgs);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 修改记录
	 */
	public void updateItem(String sql) {

		mSQLiteDatabase.execSQL(sql);

	}

	/**
	 * 关闭数据库
	 */
	public void close() {
		if (mSQLiteDatabase != null) {
			try {
				mSQLiteDatabase.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

}
