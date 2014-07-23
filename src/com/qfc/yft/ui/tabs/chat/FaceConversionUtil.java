package com.qfc.yft.ui.tabs.chat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.util.Log;

import com.qfc.yft.R;
import com.qfc.yft.util.JackUtils;


/**
 * 
 ****************************************** 
 * 
 * @�ļ����� : FaceConversionUtil.java
 * @����ʱ�� : 2013-1-27 ����02:34:09
 * @�ļ����� : �����D�Q����
 ****************************************** 
 */
public class FaceConversionUtil {

	/** ÿһҳ����ĸ��� */
	private int pageSize = 20;

	private static FaceConversionUtil mFaceConversionUtil;

	/** �������ڴ��еı���HashMap */
	private HashMap<String, String> emojiMap = new HashMap<String, String>();

	/** �������ڴ��еı��鼯�� */
	private List<ChatEmoji> emojis = new ArrayList<ChatEmoji>();

	/** �����ҳ�Ľ������ */
	public List<List<ChatEmoji>> emojiLists = new ArrayList<List<ChatEmoji>>();

	private FaceConversionUtil() {

	}

	public static FaceConversionUtil getInstace() {
		if (mFaceConversionUtil == null) {
			mFaceConversionUtil = new FaceConversionUtil();
		}
		return mFaceConversionUtil;
	}

	public void calec() {
		mFaceConversionUtil = null;
	}

	/**
	 * �õ�һ��SpanableString����ͨ��������ַ���,�����������ж�
	 * 
	 * @param context
	 * @param str
	 * @return
	 */
	public SpannableString getExpressionString(Context context, String str) {
		SpannableString spannableString = new SpannableString(str);
		// ������ʽ�����ַ������Ƿ��б��飬�磺 �Һ�[����]��
		String zhengze = "\\[[^\\]]+\\]";
		// ͨ�������������ʽ������һ��pattern
		Pattern sinaPatten = Pattern.compile(zhengze, Pattern.CASE_INSENSITIVE);
		try {
			dealExpression(context, spannableString, sinaPatten, 0);
		} catch (Exception e) {
			Log.e("dealExpression", e.getMessage());
			e.printStackTrace();
		}
		return spannableString;
	}

	/**
	 * ��ӱ���
	 * 
	 * @param context
	 * @param imgId
	 * @param spannableString
	 * @return
	 */
	public SpannableString addFace(Context context, int imgId,
			String spannableString) {
		if (TextUtils.isEmpty(spannableString)) {
			return null;
		}
		Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),
				imgId);
		bitmap = Bitmap.createScaledBitmap(bitmap, 35, 35, true);
		ImageSpan imageSpan = new ImageSpan(context, bitmap);
		SpannableString spannable = new SpannableString(spannableString);
		spannable.setSpan(imageSpan, 0, spannableString.length(),
				Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		return spannable;
	}

	/**
	 * ��spanableString���������жϣ��������Ҫ�����Ա���ͼƬ����
	 * 
	 * @param context
	 * @param spannableString
	 * @param patten
	 * @param start
	 * @throws Exception
	 */
	private void dealExpression(Context context,
			SpannableString spannableString, Pattern patten, int start)
			throws Exception {
		Matcher matcher = patten.matcher(spannableString);
		while (matcher.find()) {
			String key = matcher.group();
			// ���ص�һ���ַ����������ı�ƥ������������ʽ,ture ������ݹ�
			if (matcher.start() < start) {
				continue;
			}
			String value = emojiMap.get(key);
			if (TextUtils.isEmpty(value)) {
				continue;
			}
			// int resId = context.getResources().getIdentifier(value,
			// "drawable",
			// context.getPackageName());

			int resId = Integer.parseInt(value);
			// ͨ������ƥ��õ����ַ���������ͼƬ��Դid
			// Field field=R.drawable.class.getDeclaredField(value);
			// int resId=Integer.parseInt(field.get(null).toString());
			if (resId != 0) {
				Bitmap bitmap = BitmapFactory.decodeResource(
						context.getResources(), resId);
				bitmap = Bitmap.createScaledBitmap(bitmap, 50, 50, true);
				// ͨ��ͼƬ��Դid���õ�bitmap����һ��ImageSpan����װ
				ImageSpan imageSpan = new ImageSpan(bitmap);
				// �����ͼƬ���ֵĳ��ȣ�Ҳ����Ҫ�滻���ַ����ĳ���
				int end = matcher.start() + key.length();
				// ����ͼƬ�滻�ַ����й涨��λ����
				spannableString.setSpan(imageSpan, matcher.start(), end,
						Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
				if (end < spannableString.length()) {
					// ��������ַ�����δ��֤�꣬���������
					dealExpression(context, spannableString, patten, end);
				}
				break;
			}
		}
	}

	public void getFileText(Context context) {
		ParseData(FileUtils.getEmojiFile(context), context);
	}

	/**
	 * �����ַ�
	 * 
	 * @param data
	 */
	private void ParseData(List<String> data, Context context) {
		if (data == null) {
			return;
		}
		ChatEmoji emojEentry;
		try {
			// for (String str : data) {
			// String[] text = str.split(",");
			// String fileName = text[0]
			// .substring(0, text[0].lastIndexOf("."));
			// emojiMap.put(text[1], fileName);
			// int resID = context.getResources().getIdentifier(fileName,
			// "drawable", context.getPackageName());
			//
			// if (resID != 0) {
			// emojEentry = new ChatEmoji();
			// emojEentry.setId(resID);
			// emojEentry.setCharacter(text[1]);
			// emojEentry.setFaceName(fileName);
			// emojis.add(emojEentry);
			// }
			// }
			int[] resIds = JackUtils.resIds();
			int top = 0;
			for (int resID : resIds) {
				top++;
				emojiMap.put("[" + (top - 1) + "]", String.valueOf(resID));
				if (resID != 0) {
					emojEentry = new ChatEmoji();
					emojEentry.setId(resID);
					emojEentry.setCharacter("[" + (top - 1) + "]");
					emojEentry.setFaceName(String.valueOf(resID));
					emojis.add(emojEentry);
				}
			}
			int pageCount = (int) Math.ceil(emojis.size() / 20 + 0.1);
			for (int i = 0; i < pageCount; i++) {
				emojiLists.add(getData(i));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ��ȡ��ҳ����
	 * 
	 * @param page
	 * @return
	 */
	private List<ChatEmoji> getData(int page) {
		int startIndex = page * pageSize;
		int endIndex = startIndex + pageSize;

		if (endIndex > emojis.size()) {
			endIndex = emojis.size();
		}
		// ����ôд������viewpager�����б����ϲ����쳣����Ҳ��֪��Ϊʲô
		List<ChatEmoji> list = new ArrayList<ChatEmoji>();
		list.addAll(emojis.subList(startIndex, endIndex));
		if (list.size() < pageSize) {
			for (int i = list.size(); i < pageSize; i++) {
				ChatEmoji object = new ChatEmoji();
				list.add(object);
			}
		}
		if (list.size() == pageSize) {
			ChatEmoji object = new ChatEmoji();
			object.setId(R.drawable.face_del_icon);
			list.add(object);
		}
		return list;
	}
}