package cn.com.anyitou.entity;

import java.io.Serializable;
/**
 * 企贷详情信息
 * @author pengweiqiang
 *
 */
public class InvestmentDetail implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8677652759535103184L;
	
	private String description;//项目描述
	private String capital_opration;//资金用途
	private String risk_control;//风险控制
	private String idea_repay;//还款来源
	private String idea_home;//经营状况分析
	private String idea_credit;//信用分析
	private String idea_risk;//担保机构信息
	private String guarantee_opinion;//担保机构意见
	private String company_info;//企业信息
	private String guarantee_info;//担保简介
	
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCapital_opration() {
		return capital_opration;
	}
	public void setCapital_opration(String capital_opration) {
		this.capital_opration = capital_opration;
	}
	public String getRisk_control() {
		return risk_control;
	}
	public void setRisk_control(String risk_control) {
		this.risk_control = risk_control;
	}
	public String getIdea_repay() {
		return idea_repay;
	}
	public void setIdea_repay(String idea_repay) {
		this.idea_repay = idea_repay;
	}
	public String getIdea_home() {
		return idea_home;
	}
	public void setIdea_home(String idea_home) {
		this.idea_home = idea_home;
	}
	public String getIdea_credit() {
		return idea_credit;
	}
	public void setIdea_credit(String idea_credit) {
		this.idea_credit = idea_credit;
	}
	public String getIdea_risk() {
		return idea_risk;
	}
	public void setIdea_risk(String idea_risk) {
		this.idea_risk = idea_risk;
	}
	public String getGuarantee_opinion() {
		return guarantee_opinion;
	}
	public void setGuarantee_opinion(String guarantee_opinion) {
		this.guarantee_opinion = guarantee_opinion;
	}
	public String getCompany_info() {
		return company_info;
	}
	public void setCompany_info(String company_info) {
		this.company_info = company_info;
	}
	public String getGuarantee_info() {
		return guarantee_info;
	}
	public void setGuarantee_info(String guarantee_info) {
		this.guarantee_info = guarantee_info;
	}
	
}
