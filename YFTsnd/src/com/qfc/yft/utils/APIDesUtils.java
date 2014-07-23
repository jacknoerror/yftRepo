package com.qfc.yft.utils;

import java.io.IOException;
import java.security.Key;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.Cipher;



/** 
 *
 *
 * @Project: qfcsoft_qapi
 * @File: APIDesUtils.java 
 * @Date: 2012-9-3 
 * @Author: ��ǿ
 * @Copyright: ��Ȩ���� (C) 2012 �㽭�й���ĳ��������޹�˾������������Ȩ��. 
 *
 * ע�⣺�����ݽ������㽭�й���ĳ��������޹�˾�ڲ����ģ���ֹ��й�Լ�������������ҵĿ�� 
 */

public class APIDesUtils {
	public final String DES_KEY = "88211711";//"ipad_motion_key";
	
	private Map<String, Cipher> encryptMap = new HashMap<String, Cipher>();
	private Map<String, Cipher> decryptMap = new HashMap<String, Cipher>();

	/**
	 * �����ַ���
	 * 
	 * @param strIn ����ܵ��ַ���
	 * @return ���ܺ���ַ���
	 * @throws Exception
	 */
	public String encrypt(String strIn, String keyStr) throws Exception {
		return base64Encode(encrypt(strIn.getBytes("UTF-8"), getDesKeyStr(keyStr)));//.replaceAll("\\+", "@api_param_add@");
	}

	/**
	 * �����ַ���
	 * 
	 * @param strIn ����ܵ��ַ���
	 * @return ���ܺ���ַ���
	 * @throws Exception
	 */
	public String decrypt(String strIn, String keyStr) throws Exception {
		return new String(decrypt(base64Decode(strIn), getDesKeyStr(keyStr)),"UTF-8");//.replaceAll("@api_param_add@", "+")
	}
	
	public APIDesUtils() throws Exception{//taotao
		String keyStr= DES_KEY;
		keyStr = getDesKeyStr(keyStr);
		
		Key key = getKey(keyStr.getBytes("UTF-8"));

		Cipher encryptCipher = Cipher.getInstance("DES");
		encryptCipher.init(Cipher.ENCRYPT_MODE, key);

		Cipher decryptCipher = Cipher.getInstance("DES");
		decryptCipher.init(Cipher.DECRYPT_MODE, key);

		encryptMap.put(keyStr, encryptCipher);
		decryptMap.put(keyStr, decryptCipher);
	}
	/**
	 * ָ����Կ���췽��
	 * 
	 * @param strKey ָ������Կ
	 * @throws Exception
	 */
	protected void generateKey(List<String> keyList) throws Exception {
		for (String keyStr : keyList) {
			
			keyStr = getDesKeyStr(keyStr);
			
			Key key = getKey(keyStr.getBytes("UTF-8"));

			Cipher encryptCipher = Cipher.getInstance("DES");
			encryptCipher.init(Cipher.ENCRYPT_MODE, key);

			Cipher decryptCipher = Cipher.getInstance("DES");
			decryptCipher.init(Cipher.DECRYPT_MODE, key);

			encryptMap.put(keyStr, encryptCipher);
			decryptMap.put(keyStr, decryptCipher);
		}
	}

	/**
	 * ���ɹ����DESKEY
	 * @param key
	 * @return
	 * @throws Exception
	 */
	private String getDesKeyStr(String key) throws Exception {
//		String md5Key = DigestUtils.md5Hex(key.getBytes("UTF-8"));
//		return md5Key.substring(0, 4) + md5Key.substring(md5Key.length() - 4);
//		return NetConstant.getMD5(key);
//		return "123";
//		return NetConstant.DES_KEY;//taotao
		return key;
	}

	/**
	 * ��ָ���ַ���������Կ����Կ������ֽ����鳤��Ϊ8λ ����8λʱ���油0������8λֻȡǰ8λ
	 * 
	 * @param arrBTmp ���ɸ��ַ������ֽ�����
	 * @return ���ɵ���Կ
	 * @throws java.lang.Exception
	 */
	private Key getKey(byte[] arrBTmp) throws Exception {
		// ����һ���յ�8λ�ֽ����飨Ĭ��ֵΪ0��
		byte[] arrB = new byte[8];

		// ��ԭʼ�ֽ�����ת��Ϊ8λ
		for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
			arrB[i] = arrBTmp[i];
		}

		// ������Կ
		Key key = new javax.crypto.spec.SecretKeySpec(arrB, "DES");

		return key;
	}

	private String base64Encode(byte[] s) throws Exception {
		if (s == null)
			return null;

		return new String(Base64.encode(s));
	}

	private byte[] base64Decode(String s) throws IOException {
		if (s == null)
			return null;

		return Base64.decode(s);
	}
	/**
	 * �����ֽ�����
	 * 
	 * @param arrB ����ܵ��ֽ�����
	 * @return ���ܺ���ֽ�����
	 * @throws Exception
	 */
	private byte[] encrypt(byte[] arrB, String keyStr) throws Exception {
		if (keyStr == null)
			throw new Exception("key is null!");
		return encryptMap.get(keyStr).doFinal(arrB);
	}

	/**
	 * �����ֽ�����
	 * 
	 * @param arrB ����ܵ��ֽ�����
	 * @return ���ܺ���ֽ�����
	 * @throws Exception
	 */
	private byte[] decrypt(byte[] arrB, String keyStr) throws Exception {
		if (keyStr == null)
			throw new Exception("key is null!");
		return decryptMap.get(keyStr).doFinal(arrB);
	}

}
