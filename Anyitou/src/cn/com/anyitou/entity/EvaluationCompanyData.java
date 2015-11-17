package cn.com.anyitou.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 车贷 评估机构信息
 * @author pengweiqiang
 *
 */
public class EvaluationCompanyData implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5795508412097859186L;
//	"id": "8",
//    "name": "北京新世纪行旧机动车经纪有限公司",    //  名称
//    "tel": "",
//    "address": "",   //  地址
//    "link_user": "",
//    "link_mobile": "",
//    "company_no": "XSJ",    //  企业编号
//    "qualification": "",
//    "intro": "",    //  简介
//    "information": "",
//    "background": "",
//    "website": ""
	private String id;
	private String name;
	private String tel;
	private String address;
	private String link_user;
	private String link_mobile;
	private String company_no;
	private String qualification;
	private String intro;
	private String information;
	private String background;
	private String website;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getLink_user() {
		return link_user;
	}
	public void setLink_user(String link_user) {
		this.link_user = link_user;
	}
	public String getLink_mobile() {
		return link_mobile;
	}
	public void setLink_mobile(String link_mobile) {
		this.link_mobile = link_mobile;
	}
	public String getCompany_no() {
		return company_no;
	}
	public void setCompany_no(String company_no) {
		this.company_no = company_no;
	}
	public String getQualification() {
		return qualification;
	}
	public void setQualification(String qualification) {
		this.qualification = qualification;
	}
	public String getIntro() {
		return intro;
	}
	public void setIntro(String intro) {
		this.intro = intro;
	}
	public String getInformation() {
		return information;
	}
	public void setInformation(String information) {
		this.information = information;
	}
	public String getBackground() {
		return background;
	}
	public void setBackground(String background) {
		this.background = background;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	
	
	
	
	
	
	
}
