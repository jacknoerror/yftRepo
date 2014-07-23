package com.qfc.yft.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 校验工具类
 * 
 * @author duhanchen
 *
 */
public class ValidateUtils {
	
	/**
	 * 校验手机号
	 * 
	 * @param str 待校验字符串
	 * @return
	 */
	public static boolean validatePhoneNumber(String str) {
		
		// 电信号段：180、181、189、133、153、177
		String telecomRegx = "^(133)[0-9]{8}|(153)[0-9]{8}|(18)[019][0-9]{8}|(177)[0-9]{8}$";
		// 联通号段：130、131、132、155、156、185、186、145、176
		String unicomRegx = "^(13)[012][0-9]{8}|(15)[56][0-9]{8}|(18)[56][0-9]{8}|(145)[0-9]{8}|(176)[0-9]{8}$";
		// 移动号段：134、135、136、137、138、139、147、150、151、152、157、158、159、182、187、188、183、184、178
		String mobileRegx = "^(134)[0-8][0-9]{7}|(13)[5-9][0-9]{8}|(147)[0-9]{8}|(15)[012789][0-9]{8}|(18)[23478][0-9]{8}|(178)[0-9]{8}$";
		// 虚拟运营商：170
		String virtualRegx = "^(170)[0-9]{8}$";
		
		if(validateRegx(telecomRegx, str) || validateRegx(unicomRegx, str) || validateRegx(mobileRegx, str) || validateRegx(virtualRegx, str)) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * 校验所有字母和数字的组合
	 * 
	 * @param str 待校验字符串
	 * @param length 指定正则表达式的字母和数字串长度
	 * @return
	 */
	public static boolean validateAllOfAlphabetsAndNumbers(String str, int length) {
		
		Pattern pattern = Pattern.compile("^[A-Z a-z 0-9]{" + length + "}$");
		
		Matcher matcher = pattern.matcher(str);
		
		return matcher.matches();
	}
	
	/**
	 * 校验指定长度的数字
	 * @param str
	 * @param length
	 * @return
	 */
	public static boolean validateNumbers(String str, int length) {
		
		Pattern pattern = Pattern.compile("^\\d{" + length + "}$");
		
		Matcher matcher = pattern.matcher(str);
		
		return matcher.matches();
	}
	
	/**
	 * 通用的正则表达式校验
	 * 
	 * @param regx 正则表达式
	 * @param str 待校验字符串
	 * @return
	 */
	public static boolean validateRegx(String regx, String str) {
		
		Pattern pattern = Pattern.compile(regx);
		
		Matcher matcher = pattern.matcher(str);
		
		return matcher.matches();
	}
}
