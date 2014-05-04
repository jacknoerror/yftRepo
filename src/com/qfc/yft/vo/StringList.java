package com.qfc.yft.vo;

import java.util.ArrayList;
import java.util.List;

public class StringList {
	private List<String> items = new ArrayList<String>();

	public String get(int index) {
		return items.get(index);
	}

	public int size() {
		return items.size();
	}

	public int getIndexByName(String name) {
		String ne = name + "=";
		for (int i = 0; i < size(); i++) {
			if (get(i).startsWith(ne)) {
				return i;
			}
		}
		return -1;
	}

	public int getIndexByValue(String value) {
		String ne = "=" + value;
		for (int i = 0; i < size(); i++) {
			if (get(i).endsWith(ne)) {
				return i;
			}
		}
		return -1;
	}

	public String getName(int index) {
		String s = get(index);
		return s.substring(0, s.indexOf('='));
	}

	public String getValue(int index) {
		String s = get(index);
		return s.substring(s.indexOf('=') + 1);
	}

	public String getValue(String name) {
		int index = getIndexByName(name);
		if (index >= 0) {
			return getValue(index);
		}
		return "";
	}

	public void put(String name, String value) {
		int index = getIndexByName(name);
		if (index < 0) {
			items.add(name + "=" + value);
		} else {

			items.set(index, name + "=" + value);
		}
	}
}
