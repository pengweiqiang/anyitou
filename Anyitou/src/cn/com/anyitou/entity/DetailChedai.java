package cn.com.anyitou.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 车贷
 * @author pengweiqiang
 *
 */
public class DetailChedai implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4992340166231975907L;
	
	private List<BorrowerVerifyLogs> borrower_verify_logs;//借款人审核信息
	private List<ProjectVerifyLogs> project_verify_logs;//项目审核信息
	private CarInfo carInfo;//
	private EvaluationCompanyData evaluationCompanyData;
	
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
	public CarInfo getCarInfo() {
		return carInfo;
	}
	public void setCarInfo(CarInfo carInfo) {
		this.carInfo = carInfo;
	}
	public EvaluationCompanyData getEvaluationCompanyData() {
		return evaluationCompanyData;
	}
	public void setEvaluationCompanyData(EvaluationCompanyData evaluationCompanyData) {
		this.evaluationCompanyData = evaluationCompanyData;
	}
	
	
	
}
