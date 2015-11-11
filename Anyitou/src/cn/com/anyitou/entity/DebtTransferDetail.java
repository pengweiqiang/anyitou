package cn.com.anyitou.entity;

import java.io.Serializable;
/**
 * 债权转让明细
 * @author pengweiqiang
 *
 */
public class DebtTransferDetail implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9144388183668526953L;
	/**
	 * 
	 */
	private DebtTransfer debtData;
	
	private ProjectData projectData;
	
	public class ProjectData implements Serializable{
		
		/**
		 * 
		 */
		private static final long serialVersionUID = -7411353733374008599L;
		private String item_title;
		private String rate_of_interest;
		private String borrower_user_id;
		private String repayment_time;
		public String getItem_title() {
			return item_title;
		}
		public void setItem_title(String item_title) {
			this.item_title = item_title;
		}
		public String getRate_of_interest() {
			return rate_of_interest;
		}
		public void setRate_of_interest(String rate_of_interest) {
			this.rate_of_interest = rate_of_interest;
		}
		public String getBorrower_user_id() {
			return borrower_user_id;
		}
		public void setBorrower_user_id(String borrower_user_id) {
			this.borrower_user_id = borrower_user_id;
		}
		public String getRepayment_time() {
			return repayment_time;
		}
		public void setRepayment_time(String repayment_time) {
			this.repayment_time = repayment_time;
		}
		
	}

	public DebtTransfer getDebtData() {
		return debtData;
	}

	public void setDebtData(DebtTransfer debtData) {
		this.debtData = debtData;
	}

	public ProjectData getProjectData() {
		return projectData;
	}

	public void setProjectData(ProjectData projectData) {
		this.projectData = projectData;
	}
	
	
}
