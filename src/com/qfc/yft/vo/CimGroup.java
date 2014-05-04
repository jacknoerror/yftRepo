package com.qfc.yft.vo;

import net.n3.nanoxml.IXMLElement;

public class CimGroup  extends CimAbstractVo{
//	<cim><result code="0" serverTime="2008-12-22 09:36:03"/>
//	<groups><group id="282701861867476" code="1002" name="网上飞仙" catalog="文学" 
//		type="0" notes="畅想生活 放飞希望相信你自己你行 你可以 你是最棒的！"/>
//	</groups></cim>'
	private long id;
	private String code;
	private String name;
	private String catalog;
	private String type;
	private String notes;
	private int index;
	private long userId;


	public int getIndex() {
		return index;
	}


	public void setIndex(int index) {
		this.index = index;
	}


	public long getUserId() {
		return userId;
	}


	public void setUserId(long userId) {
		this.userId = userId;
	}


	public void decodeFromXmlNode(IXMLElement node) {		
		setCode(node.getAttribute("code",""));
		setName(node.getAttribute("name",""));
		setCatalog(node.getAttribute("catalogId",""));
		setType(node.getAttribute("type",""));
		setNotes(node.getAttribute("notes",""));
		setId(Long.parseLong(node.getAttribute("id", "0")));
		setIndex(Integer.parseInt(node.getAttribute("index", "0")));
		setUserId(Long.parseLong(node.getAttribute("userId", "0")));
	}
	
	
	public void decodeFromXmlRoot(IXMLElement root) {
		
		
	}

	public String getCatalog() {
		return catalog;
	}

	public String getCode() {
		return code;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getNotes() {
		return notes;
	}

	public String getType() {
		return type;
	}

	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public void setType(String type) {
		this.type = type;
	}

}
