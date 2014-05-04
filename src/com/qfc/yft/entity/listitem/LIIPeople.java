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
	 * "memberPosition":"JAVA����ʦ","status":0,"compName":"���Թ�˾",
	 * "compCity":130,"privateSignature":null,"headIcon":null,
	 * "memberSex":1,"compAddress":"�㽭����","email":null,"realName":"��ǿ",
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
	 	account_id bigint(20) (NULL) NO PRI (NULL) auto_increment select,insert,update,references �˻�ID 
		real_name varchar(50) gbk_chinese_ci YES (NULL) select,insert,update,references ��ʵ���� 
		member_sex int(1) (NULL) YES (NULL) select,insert,update,references ��Ա�Ա�0��Ů��1���У� 
		member_position varchar(50) gbk_chinese_ci YES (NULL) select,insert,update,references ��Աְλ 
		phone_idd varchar(5) gbk_chinese_ci YES (NULL) select,insert,update,references ���� 
		phone_ddd varchar(5) gbk_chinese_ci YES (NULL) select,insert,update,references ���� 
		phone_tel varchar(30) gbk_chinese_ci YES (NULL) select,insert,update,references �绰 
		qq varchar(15) gbk_chinese_ci YES (NULL) select,insert,update,references qq 
		wechat varchar(50) gbk_chinese_ci YES (NULL) select,insert,update,references ΢�� 
		mobile varchar(11) gbk_chinese_ci YES (NULL) select,insert,update,references �ֻ����� 
		email varchar(50) gbk_chinese_ci YES (NULL) select,insert,update,references ���� 
		wangwang varchar(25) gbk_chinese_ci YES (NULL) select,insert,update,references �����˺� 
		comp_name varchar(100) gbk_chinese_ci YES (NULL) select,insert,update,references ���Ϲ�˾���� 
		comp_region varchar(110) gbk_chinese_ci YES CN select,insert,update,references ���� 
		comp_prov int(11) (NULL) YES (NULL) select,insert,update,references ˾������ʡ 
		comp_city int(11) (NULL) YES (NULL) select,insert,update,references ��˾������ 
		comp_county int(11) (NULL) YES (NULL) select,insert,update,references ��˾������ 
		comp_address varchar(500) gbk_chinese_ci YES (NULL) select,insert,update,references ϸ���ַ 
		college varchar(255) gbk_chinese_ci YES (NULL) select,insert,update,references ��ҵ��У 
		private_signature varchar(500) gbk_chinese_ci YES (NULL) select,insert,update,references ����ǩ�� 
		comp_profession varchar(4) gbk_chinese_ci YES (NULL) select,insert,update,references ������ҵ 
		head_icon varchar(100) gbk_chinese_ci YES (NULL) select,insert,update,references ͷ�� 
		web_site varchar(255) gbk_chinese_ci YES (NULL) select,insert,update,references ��˾��ַ 
		contact text gbk_chinese_ci YES (NULL) select,insert,update,references ������Ϣjson[{"memberPosition":"XX","webSite":"XX","department":"XX","compName":"XX"}] 
		comp_main_product varchar(255) gbk_chinese_ci YES (NULL) select,insert,update,references ��Ӫ��Ʒ 
		department varchar(255) gbk_chinese_ci YES (NULL) select,insert,update,references ���� 
		add_time datetime (NULL) YES (NULL) select,insert,update,references ����ʱ�� 
		update_time datetime (NULL) YES (NULL) select,insert,update,references ����ʱ�� 
		contact_visible tinyint(1) (NULL) YES 0 select,insert,update,references ��ע����ѿɼ���0����1���ǣ� 

	 */
}
