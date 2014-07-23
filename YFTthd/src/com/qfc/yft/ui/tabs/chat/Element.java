package com.qfc.yft.ui.tabs.chat;

public abstract class Element {

	protected String label;
	protected boolean selected = false;
	protected long id;
	protected int index;

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	protected String faceIndex;

	public String getFaceIndex() {
		return faceIndex;
	}

	public void setFaceIndex(String faceIndex) {
		this.faceIndex = faceIndex;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Element(String str) {
		label = str;
	}

	public Element() {
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String s) {
		label = s;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean b) {
		selected = b;
	}

}
