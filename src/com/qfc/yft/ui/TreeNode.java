package com.qfc.yft.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.qfc.yft.CimConsts;


public class TreeNode extends Element {
	// 状态
	public short status = (short) 50;
	private Object data = null;
	private int sex;
	public boolean isclick;
	private int type;
	private String time = "";
	private String title;
	private String imgUrl;
	private String description;
	private int badge;

	public int getBadge() {
		return badge;
	}

	public void setBadge(int badge) {
		this.badge = badge;
	}

	// 是否闪烁
	private boolean blink = false;

	protected boolean expand = false;

	// 子节点
	protected List<TreeNode> children;

	public TreeNode() {

	}

	public TreeNode(boolean allowChildren) {
		this(null, allowChildren);
	}

	public TreeNode(String title, boolean allowChildren) {
		this.title = title;
		if (allowChildren) {
			children = new ArrayList<TreeNode>();
		}
	}

	public void add(TreeNode child) {
		children.add(child);
	}

	public boolean allowChild() {
		return children != null;
	}

	public void clear() {
		this.children.clear();
	}

	/**
	 * 用户查找
	 * 
	 * @param userId
	 * @return
	 */

	public TreeNode find(long id) {

		TreeNode result = null;
		if (id == this.id)
			result = this;
		if (children != null) {
			for (int i = 0; i < children.size() && result == null; i++) {
				TreeNode node = children.get(i);
				result = node.find(id);
			}

		}

		return result;
	}

	public int folderSize() {
		int result = 0;
		if (this.expand && children != null) {
			for (TreeNode folder : children) {
				if (folder.allowChild()) {
					result += folder.folderSize() + 1;
				}
			}
		}
		return result;
	}

	public TreeNode get(int index) {
		if (children != null) {
			return children.get(index);
		}

		return null;
	}

	public Object getData() {
		return data;
	}

	public String getDescription() {
		return description;
	}

	public TreeNode getFolder(int index) {
		TreeNode result = null;
		if (this.expand) {
			for (int i = 0; i < children.size(); i++) {
				TreeNode folder = children.get(i);
				if (folder.allowChild()) {
					if (index <= 0) {
						result = folder;
						break;
					}
					result = folder.getFolder(--index);
					if (result != null) {
						break;
					}
				}
			}
		}

		return result;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public String getGroupLabel() {
		StringBuffer sb = new StringBuffer(title);
		if (children != null) {
			sb.append("  [").append(children.size()).append("]");
		}
		return sb.toString();
	}

	public String getLabel() {
		StringBuffer sb = new StringBuffer(title);
		int onlineNum = 0;
		int status = 0;
		if (children != null) {
			for (int i = 0; i < children.size(); i++) {
				status = children.get(i).status;
				if ((status != CimConsts.UserStatus.US_OFFLINE)
						&& (status != CimConsts.UserStatus.US_HIDE)) {
					onlineNum++;
				}
			}
			sb.append("  [").append(onlineNum).append("/")
					.append(children.size()).append("]");

		}
		return sb.toString();
	}

	public TreeNode getLeaf(int index) {
		if (children != null) {
			return children.get(index);
		}
		return null;
	}

	public int getSex() {
		return sex;
	}

	public short getState() {
		return status;
	}

	public short getStatus() {
		return status;
	}

	public String getTime() {
		return time;
	}

	public String getTitle() {
		if (type == 1) {
			return getGroupLabel();
		} else {
			return getLabel();
		}

	}

	public int getType() {
		return type;
	}

	public boolean includeFolder() {
		if (children != null) {
			for (TreeNode folder : this.children) {
				if (folder.allowChild()) {
					return true;
				}
			}
		}

		return false;
	}

	public boolean isBlink() {
		return blink;
	}

	public boolean isExpand() {
		return expand;
	}

	public boolean isIsclick() {
		return isclick;
	}

	public int leafSize() {
		int result = 0;
		if (children != null) {
			for (TreeNode folder : this.children) {
				if (!folder.allowChild()) {
					result++;
				}
			}
		}

		return result;
	}

	public String nodeName() {
		return title;
	}

	public TreeNode remove(int index) {

		return this.children.remove(index);
	}

	public boolean remove(TreeNode child) {

		return this.children.remove(child);
	}

	public void removeNode(long id) {
		TreeNode removeNode = null;
		boolean result = false;

		for (TreeNode node : children) {
			if (node.getId() == id) {
				result = true;
				removeNode = node;
				break;
			}
		}
		if (result) {

			this.children.remove(removeNode);
		}

	}

	public void setAllStatus() {
		if (children != null) {
			for (int i = 0; i < children.size(); i++) {
				children.get(i).setStatus(CimConsts.UserStatus.US_OFFLINE);
			}
		}

	}

	public void setBlink(boolean blink) {
		this.blink = blink;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setExpand(boolean expand) {
		this.expand = expand;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public void setIsclick(boolean isclick) {
		this.isclick = isclick;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public boolean setStatus(short status) {
		boolean result = false;
		if (this.status != status) {
			if (status == CimConsts.UserStatus.US_HIDE || status == (short) 0
					|| status == CimConsts.UserStatus.US_OFFLINE) {
				status = CimConsts.UserStatus.US_OFFLINE;
			}
			this.status = status;
			result = true;
		}
		return result;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int size() {
		if (this.children != null) {
			return this.children.size();
		}

		return 0;
	}

	public void sortByState() {
		if (children != null) {
			boolean hasFolder = false;
			for (TreeNode folder : this.children) {
				if (folder.allowChild()) {
					folder.sortByState();
					hasFolder = true;
				}
			}
			if (!hasFolder) {
				Collections.sort(children, new Comparator<TreeNode>() {
					public int compare(TreeNode node1, TreeNode node2) {
						int result = node1.getState() - node2.getState();
						if (result == 0) {
							result = node1.getLabel().compareTo(
									node2.getLabel());
						}
						return result;
					}

				});
			}
		}

	}

	public void sortByIndex() {
		if (children != null) {
			boolean hasFolder = false;
			for (TreeNode folder : this.children) {
				if (folder.allowChild()) {
					folder.sortByIndex();
					hasFolder = true;
				}
			}
			if (!hasFolder) {
				Collections.sort(children, new Comparator<TreeNode>() {
					public int compare(TreeNode node1, TreeNode node2) {
						int result = node2.getIndex() - node1.getIndex();
						if (result == 0) {
							result = node2.getLabel().compareTo(
									node1.getLabel());
						}
						return result;
					}

				});
			}
		}

	}

	public void sortByTime() {
		if (children != null) {
			boolean hasFolder = false;
			for (TreeNode folder : this.children) {
				if (folder.allowChild()) {
					folder.sortByIndex();
					hasFolder = true;
				}
			}
			if (!hasFolder) {
				Collections.sort(children, new Comparator<TreeNode>() {
					public int compare(TreeNode node1, TreeNode node2) {
						int result = node2.getTime().compareTo(node1.getTime());
						if (result == 0) {
							result = node2.getLabel().compareTo(
									node1.getLabel());
						}
						return result;
					}

				});
			}
		}

	}

}
