package cn.com.anyitou.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 房贷
 * @author pengweiqiang
 *
 */
public class DetailFangdai implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4992340166231975907L;
	
	private List<BorrowerVerifyLogs> borrower_verify_logs;//借款人审核信息
	private List<ProjectVerifyLogs> project_verify_logs;//项目审核信息
	private HouseInfo house_info;//
	
	public List<BorrowerVerifyLogs> getBorrower_verify_logs() {
		return borrower_verify_logs;
	}
	public void setBorrower_verify_logs(
			List<BorrowerVerifyLogs> borrower_verify_logs) {
		this.borrower_verify_logs = borrower_verify_logs;
	}
	public List<ProjectVerifyLogs> getProject_verify_logs() {
		return project_verify_logs;
	}
	public void setProject_verify_logs(List<ProjectVerifyLogs> project_verify_logs) {
		this.project_verify_logs = project_verify_logs;
	}
	public HouseInfo getHouse_info() {
		return house_info;
	}
	public void setHouse_info(HouseInfo house_info) {
		this.house_info = house_info;
	}
	
	
	
	
	
}
