package cn.com.anyitou.entity;

import java.io.Serializable;

/**
 * 项目审核信息
 * 房贷、车贷详情
 * @author will
 *
 */
public class ProjectVerifyLogs implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9167960336002338936L;
	
	
//	"id": "5",
//    "pid": "59",
//    "type": "chedai_1",
//    "status": "1",
//    "verify_time": "2015-01-20 14:59:31",
//    "type_attrs": {
//        "name": "借款合同"
//    }
	private String id;
	private String pid;
	private String type;
	private String status;
	private String verify_time;
	private TypeAttrs type_attrs;
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}



	public String getPid() {
		return pid;
	}



	public void setPid(String pid) {
		this.pid = pid;
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



	public TypeAttrs getType_attrs() {
		return type_attrs;
	}



	public void setType_attrs(TypeAttrs type_attrs) {
		this.type_attrs = type_attrs;
	}



	public class TypeAttrs{
		private String name;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
		
		
	}
}
