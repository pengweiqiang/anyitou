package cn.com.anyitou.entity;

import java.util.List;

public class LableData {

	private String label;
	private List<LableValue> data;
	//{"firstpage":[{"label":"年化收益率","value":"10%＋3%"},{"label":"投资期","value":"1个月"},{"label":"融资金额","value":"100万"},{"label":"已投金额","value":"0.33万"},{"label":"融资进度","value":0},{"label":"title","value":"房产抵押贷 ios10001"},{"label":"还款方式","value":"先息后本"},{"label":"截止时间","value":"2015-05-29 17:09:00"},{"label":"xmqx","value":"30"},{"label":"rate","value":13}],"secondpage":[{"label":"项目描述","data":[{"label":"小区名称","value":"上海市卢湾区建国路18号院"},{"label":"建筑面积","value":"79.01 平方米"},{"label":"使用年限","value":"70 年"},{"label":"评估价格","value":"200万元"},{"label":"参考价格","value":"220万元"},{"label":"detial","value":"短期周转"}]},{"label":"风险控制","data":[{"label":"全面的尽职调查","value":"　　专业的尽调团队对融资项目进行全方位的实地尽职调查，实现立体化多层级的数据采集，以确保项目 及融资需求真实、合法，为风险控制提供可信依据。尽职调查的内容包括实地调查、人民银行征信系统查询、工商局系统数据查询、房屋管理系统查询、法院系统查询、银行流水查询等。1818风控团队会在这些数据的基础上进行评审，符合标准的项目方可上线融资。"},{"label":"担保公司担保","value":"　　由经过严格审核的担保公司提供担保，担保公司会依据风险审核结论为每一位融资人量身定制担保措施，同时要求融资人提供相应的反担保措施，当项目出现风险后担保公司将及时履行代偿义务。"},{"label":"风险保证金制度","value":"　　担保公司依照其担保项目金额的一定比例向1818平台缴纳风险保证金，并由第三方进行监管。保证金制度是补充、优化担保公司代偿能力的有效手段，在担保公司 出现代偿不及时的情形下，1818平台可第一时间启动保证金代偿制度对投资人进行代偿，并在约定时间内要求担保方补足保证金，确保投资人的每一笔项目及时回款。"},{"label":"担保公司","value":"　　中诚宏达资产管理（北京）有限公司"}]},{"label":"还款计划","data":[]},{"label":"投资列表","data":[{"username":"189***69","time":"1431595035","money":"2600.00"},{"username":"188***81","time":"1431596465","money":"100.00"},{"username":"189***69","time":"1431657886","money":"300.00"},{"username":"188***81","time":"1431658471","money":"300.00"}]}]}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public List<LableValue> getData() {
		return data;
	}
	public void setData(List<LableValue> data) {
		this.data = data;
	}

	

	
}
