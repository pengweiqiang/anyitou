package cn.com.anyitou.entity;

import java.io.Serializable;

/**
 * 借款人审核信息
 * 房贷、车贷详情
 * @author pengweiqiang
 *
 */
public class BorrowerVerifyLogs implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3301772174333021309L;

	
//	"id": "6",
//    "user_id": "590",
//    "type": "1",
//    "status": "1",
//    "verify_time": "2015-01-21 15:33:31",
//    "manager_id": "0",
//    "type_name": "身份证"
	private String id;
	private String user_id;
	private String type;
	private String status;
	private String verify_time;
	private String manager_id;
	private String type_name;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getVerify_time() {
		return verify_time;
	}
	public void setVerify_time(String verify_time) {
		this.verify_time = verify_time;
	}
	public String getManager_id() {
		return manager_id;
	}
	public void setManager_id(String manager_id) {
		this.manager_id = manager_id;
	}
	public String getType_name() {
		return type_name;
	}
	public void setType_name(String type_name) {
		this.type_name = type_name;
	}
	
}
