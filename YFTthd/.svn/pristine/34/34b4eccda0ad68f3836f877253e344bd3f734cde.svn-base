package com.qfc.yft.util;

import java.util.regex.Pattern;

import android.widget.EditText;

import com.qfc.yft.data.Const;

public class JackRexUtil {
	public interface JackReRules{
		public static final String RULE_ = "";
		public static final String RE_RULE_EMAIL = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
		public static final String RE_RULE_HAS_DIGIT = "[0-9]+?";
		public static final String RE_RULE_HAS_AZ_LOW = "[a-z]+?";
		public static final String RE_RULE_HAS_AZ = "[A-Z]+?";
		public static final String RE_RULE_HAS_SPECIAL = "[!,.@#$%^&*?_~-]+?";
		public static final String RE_RULE_HAS_AZ_BOTH = "[a-zA-Z]+?";
		public static final String RE_RULE_A_Z_A_Z0_9_$_SIZE = "[a-zA-Z0-9!,.@#$%^&*?_~-]{6,20}$";
		public static final String RE_RULE_SIMPLE_LENGTH = "[a-zA-Z0-9_]{4,15}$";
		public static final String RE_RULE_IDCARD = "^\\d{15}|\\d{18}[a-zA-Z]?$";
		public static final String RE_RULE_ONLY_CHINESE = "^[\u4e00-\u9fa5]{0,}$";
		public static final String RE_RULE_ONLY_DIGIT = "[0-9]*";
		
		public static final String RE_INFO_1 = "^http://m.tnc.com.cn/info/c-([0-9]*)-d-([0-9]+).html$";//^/info/c-([0-9]*)-d-([0-9]+).html$
		public static final String RE_INFO_2 = "^http://m.tnc.com.cn/info/rc-([0-9]*)-d-([0-9]+).html$";//^/info/rc-([0-9]*)-d-([0-9]+).html$
		public static final String RE_INFO_3 = "^http://m.tnc.com.cn/expo/d([0-9]+).html$";//^/expo/d([0-9]+).html$
		public static final String RE_INFO_4 = "^http://m.tnc.com.cn/market/d-([0-9]+)-p-([0-9]*).html$";//^/market/d-([0-9]+)-p-([0-9]*).html$
		// 只允许字母和数字       
//		String   regEx  =  "[^a-zA-Z0-9]";                     
	}
	
	public static boolean checkRE(String rule,String content){
		return content.matches(rule);
	}
	public static boolean checkRE(String rule,EditText editText,String errormsg){
		if(null==editText) return false;
		if(!checkRE(rule, editText.getText().toString())){
			editText.setError(errormsg);
			editText.requestFocus();
			return false;
		}
		return true;
	}
	public static boolean hasRE(String rule, String content) {
		return Pattern.compile(rule).matcher(content).find();
	}
	
	/**
	 * @param edit_pwd2
	 *//*
	public static boolean checkPwdComplex(EditText edit_pwd2) {
		if(null==edit_pwd2) return false;
		String string = edit_pwd2.getText().toString();
		if(!checkPwdComplex(string)){
				edit_pwd2.setError("密码过于简单。请输入包含大、小写字母、数字等6-20个字符密码，加强密码强度");
				edit_pwd2.requestFocus();
				return false;
		}
		
		return true;
	}*/
	
	/*public static boolean checkPwdComplex(String string){
		int complex = 0;
		if(JackRexUtil.hasRE(Const.RE_RULE_HAS_AZ_LOW, string))	complex+=1;
		if(JackRexUtil.hasRE(Const.RE_RULE_HAS_AZ, string))	complex+=5;
		if(JackRexUtil.hasRE(Const.RE_RULE_HAS_DIGIT, string))	complex+=5;
		if(JackRexUtil.checkRE("(\\d.*\\d.*\\d)",string)) complex+=2;
		if(JackRexUtil.hasRE(Const.RE_RULE_HAS_SPECIAL, string))	complex+=5;
		if(JackRexUtil.hasRE("([!,@#$%^&*?_~].*[!,@#$%^&*?_~])", string))	complex+=5;
		if(JackRexUtil.hasRE(Const.RE_RULE_HAS_AZ_BOTH, string))	complex+=2;
		if(JackRexUtil.hasRE("\\d\\D", string))	complex+=2;
		if(JackRexUtil.hasRE(Const.RE_RULE_HAS_AZ_LOW, string)&&JackRexUtil.hasRE(Const.RE_RULE_HAS_AZ, string)&&JackRexUtil.hasRE(Const.RE_RULE_HAS_DIGIT, string)) complex+=2;
		return complex>=10;
	}*/
}
