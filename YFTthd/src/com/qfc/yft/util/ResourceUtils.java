package com.qfc.yft.util;

import android.content.Context;
import android.util.TypedValue;

public class ResourceUtils {

	private static TypedValue mTmpValue = new TypedValue();

	private ResourceUtils() {
	}

	public static int getXmlDef(Context context, int id) {
		synchronized (mTmpValue) {
			TypedValue value = mTmpValue;
			context.getResources().getValue(id, value, true);
			return (int) TypedValue.complexToFloat(value.data);
		}
	}
}