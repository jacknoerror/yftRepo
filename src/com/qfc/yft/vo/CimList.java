package com.qfc.yft.vo;

import java.util.ArrayList;
import java.util.List;

import net.n3.nanoxml.IXMLElement;

public abstract class CimList {
	private List<CimAbstractVo> items = new ArrayList<CimAbstractVo>();

	private boolean loaded = false;

	public CimList() {
	}

	public void add(CimAbstractVo vo) {
		if (vo != null)

			items.add(vo);
	}

	public CimAbstractVo get(int index) {
		CimAbstractVo cimAbstractVo = null;
		if (items.size() > 0) {
			cimAbstractVo = (CimAbstractVo) items.get(index);
		}
		return cimAbstractVo;
	}

	public void set(CimAbstractVo vo, int index) {

		items.set(index, vo);
	}

	public int size() {
		return items.size();
	}

	public void remove(CimAbstractVo vo) {
		items.remove(vo);
	}

	public void remove(int index) {
		items.remove(index);
	}

	public void clear() {
		items.clear();
	}

	public int getIndexById(long id) {
		for (int i = 0; i < size(); i++) {
			if (get(i).getId() == id) {
				return i;
			}
		}
		return -1;
	}

	public CimAbstractVo getById(long id) {
		int i = getIndexById(id);
		if (i >= 0)
			return get(i);
		return null;
	}

	public void decodeFromReturn(CimWSReturn wsReturn) {
		if (wsReturn != null) {
			if (wsReturn.isSuccess()) {
				loaded = true;
				decodeFromRoot(wsReturn.getRoot());
			}
		}

	}

	protected abstract void decodeFromRoot(IXMLElement root);

	public boolean isLoaded() {
		return loaded;
	}
}
