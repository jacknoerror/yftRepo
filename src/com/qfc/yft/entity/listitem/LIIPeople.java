package com.qfc.yft.entity.listitem;

import com.qfc.yft.ui.custom.list.ListAbsAdapter.ListItemImpl;

public class LIIPeople implements ListItemImpl{
	/*
	 * {"phoneDdd":"0575","contactVisible":0,"accountId":9,
	 * "department":null,"college":null,"phoneIdd":"86",
	 * "contact":null,"wangwang":null,"phoneTel":"89976",
	 * "webSite":"http://sp00000021.qfc.cn/","compProv":12,
	 * "compProfession":"08","compCounty":840,"userName":"zhouqiang1",
	 * "compMainProduct":",,,,,","qq":"10708018",
	 * "memberPosition":"JAVA工程师","status":0,"compName":"测试公司",
	 * "compCity":130,"privateSignature":null,"headIcon":null,
	 * "memberSex":1,"compAddress":"浙江杭州","email":null,"realName":"周强",
	 * "texTalk":null,"wechat":null,"mobile":null}
	 */
	public String phoneDdd="";
	public int contactVisible;
	public int accountId;
	public String department="";
	public String college="";
	public int phoneIdd;
	public String contact="";//
	public String wangwang="";
	public String phoneTel="";
	public String webSite="";
	public int compProv;
	public String compProfession="";
	public int compCounty;
	public String userName="";
	public String compMainProduct="";
	public String qq="";
	public String memberPosition="";
	public int status;
	public String compName="";
	public int compCity;
	public String privateSignature="";
	public String headIcon="";
	public int memberSex;
	public String compAddress="";
	public String email="";
	public String realName="";
	public Long texTalk;
	public String wechat="";
	public String mobile="";
	
	public String region="";
	
	public String getName(){
		if(realName.equals("")||realName.equals("null")){
			return userName;
		}
		return realName;
	}
	
	/*
	 	account_id bigint(20) (NULL) NO PRI (NULL) auto_increment select,insert,update,references 账户ID 
		real_name varchar(50) gbk_chinese_ci YES (NULL) select,insert,update,references 真实姓名 
		member_sex int(1) (NULL) YES (NULL) select,insert,update,references 会员性别（0、女，1、男） 
		member_position varchar(50) gbk_chinese_ci YES (NULL) select,insert,update,references 会员职位 
		phone_idd varchar(5) gbk_chinese_ci YES (NULL) select,insert,update,references 国家 
		phone_ddd varchar(5) gbk_chinese_ci YES (NULL) select,insert,update,references 地区 
		phone_tel varchar(30) gbk_chinese_ci YES (NULL) select,insert,update,references 电话 
		qq varchar(15) gbk_chinese_ci YES (NULL) select,insert,update,references qq 
		wechat varchar(50) gbk_chinese_ci YES (NULL) select,insert,update,references 微信 
		mobile varchar(11) gbk_chinese_ci YES (NULL) select,insert,update,references 手机号码 
		email varchar(50) gbk_chinese_ci YES (NULL) select,insert,update,references 邮箱 
		wangwang varchar(25) gbk_chinese_ci YES (NULL) select,insert,update,references 旺旺账号 
		comp_name varchar(100) gbk_chinese_ci YES (NULL) select,insert,update,references 网上公司名称 
		comp_region varchar(110) gbk_chinese_ci YES CN select,insert,update,references 国家 
		comp_prov int(11) (NULL) YES (NULL) select,insert,update,references 司公所在省 
		comp_city int(11) (NULL) YES (NULL) select,insert,update,references 公司所在市 
		comp_county int(11) (NULL) YES (NULL) select,insert,update,references 公司所在区 
		comp_address varchar(500) gbk_chinese_ci YES (NULL) select,insert,update,references 细详地址 
		college varchar(255) gbk_chinese_ci YES (NULL) select,insert,update,references 毕业高校 
		private_signature varchar(500) gbk_chinese_ci YES (NULL) select,insert,update,references 个性签名 
		comp_profession varchar(4) gbk_chinese_ci YES (NULL) select,insert,update,references 所属行业 
		head_icon varchar(100) gbk_chinese_ci YES (NULL) select,insert,update,references 头像 
		web_site varchar(255) gbk_chinese_ci YES (NULL) select,insert,update,references 公司网址 
		contact text gbk_chinese_ci YES (NULL) select,insert,update,references 工作信息json[{"memberPosition":"XX","webSite":"XX","department":"XX","compName":"XX"}] 
		comp_main_product varchar(255) gbk_chinese_ci YES (NULL) select,insert,update,references 主营产品 
		department varchar(255) gbk_chinese_ci YES (NULL) select,insert,update,references 部门 
		add_time datetime (NULL) YES (NULL) select,insert,update,references 创建时间 
		update_time datetime (NULL) YES (NULL) select,insert,update,references 更新时间 
		contact_visible tinyint(1) (NULL) YES 0 select,insert,update,references 关注后好友可见（0、否，1、是） 

	 */
}
