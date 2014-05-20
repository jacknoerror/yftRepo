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
 * @Author: 周强
 * @Copyright: 版权所有 (C) 2012 浙江中国轻纺城网络有限公司，并保留所有权利. 
 *
 * 注意：本内容仅限于浙江中国轻纺城网络有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */

public class APIDesUtils {
	public final String DES_KEY = "88211711";//"ipad_motion_key";
	
	private Map<String, Cipher> encryptMap = new HashMap<String, Cipher>();
	private Map<String, Cipher> decryptMap = new HashMap<String, Cipher>();

	/**
	 * 加密字符串
	 * 
	 * @param strIn 需加密的字符串
	 * @return 加密后的字符串
	 * @throws Exception
	 */
	public String encrypt(String strIn, String keyStr) throws Exception {
		return base64Encode(encrypt(strIn.getBytes("UTF-8"), getDesKeyStr(keyStr))).replaceAll("\\+", "@api_param_add@");
	}

	/**
	 * 解密字符串
	 * 
	 * @param strIn 需解密的字符串
	 * @return 解密后的字符串
	 * @throws Exception
	 */
	public String decrypt(String strIn, String keyStr) throws Exception {
		return new String(decrypt(base64Decode(strIn), getDesKeyStr(keyStr)),"UTF-8").replaceAll("@api_param_add@", "+");
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
	 * 指定密钥构造方法
	 * 
	 * @param strKey 指定的密钥
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
	 * 生成规则的DESKEY
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
	 * 从指定字符串生成密钥，密钥所需的字节数组长度为8位 不足8位时后面补0，超出8位只取前8位
	 * 
	 * @param arrBTmp 构成该字符串的字节数组
	 * @return 生成的密钥
	 * @throws java.lang.Exception
	 */
	private Key getKey(byte[] arrBTmp) throws Exception {
		// 创建一个空的8位字节数组（默认值为0）
		byte[] arrB = new byte[8];

		// 将原始字节数组转换为8位
		for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
			arrB[i] = arrBTmp[i];
		}

		// 生成密钥
		Key key = new javax.crypto.spec.SecretKeySpec(arrB, "DES");

		return key;
	}

	public static String base64Encode(byte[] s)  {
		if (s == null)
			return null;

		return new String(Base64.encode(s));
	}

	public static byte[] base64Decode(String s)  {
		if (s == null)
			return null;

		return Base64.decode(s);
	}
	/**
	 * 加密字节数组
	 * 
	 * @param arrB 需加密的字节数组
	 * @return 加密后的字节数组
	 * @throws Exception
	 */
	private byte[] encrypt(byte[] arrB, String keyStr) throws Exception {
		if (keyStr == null)
			throw new Exception("key is null!");
		return encryptMap.get(keyStr).doFinal(arrB);
	}

	/**
	 * 解密字节数组
	 * 
	 * @param arrB 需解密的字节数组
	 * @return 解密后的字节数组
	 * @throws Exception
	 */
	private byte[] decrypt(byte[] arrB, String keyStr) throws Exception {
		if (keyStr == null)
			throw new Exception("key is null!");
		return decryptMap.get(keyStr).doFinal(arrB);
	}

}
