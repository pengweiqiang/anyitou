package cn.com.anyitou.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class AnyitouUtils {
	
	/**
	 * 债权状态
	 * @param status
	 * @return
	 */
	public static String getDebtStatusName(String status){
		String statusName = "";
		if("0".equals(status)){
			statusName = "待审核";
		}else if("1".equals(status)){
			statusName = "通过审核";
		}else if("2".equals(status)){
			statusName = "转让完成";
		}else if("3".equals(status)){
			statusName = "关闭";
		}else {
			statusName = status;
		}
			
		return statusName;
	}
	/**
	 *认购债权收益计算公式： 认购份额 * 认购年化收益率 * 投资天数/365  +  折让金额  =  认购收益总额
		折让金额： 认购份额 - 认购价格
		认购价格： 总转让价格 * 认购份额 / 总转让份额     (保留两位小数)
	 * @param price  总转让价格 
	 * @param amount  总转让份额   
	 * @param debtCount 认购份额
	 * @param debtRate 认购年化收益率
	 * @param sellDay  投资天数
	 * @return
	 */
	public static String getDebtPreProfit(String price,String amount,String debtCount,String debtRate,String sellDay){
		if(StringUtils.isEmpty(debtCount)){
			return "0";
		}
		String profit = "";
		try{
			//认购价格
			double purchasePrice = Double.valueOf(price)*Double.valueOf(debtCount)/Double.valueOf(amount);
			BigDecimal b =   new   BigDecimal(purchasePrice);  
			//保留两位小数
			double  purchasePrice2   =   b.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();  
			
			//折让金额
			double discountMoney = Double.valueOf(debtCount) - purchasePrice2;
			
			double futureMoney = Double.valueOf(debtCount) * Double.valueOf(debtRate) *(Double.valueOf(sellDay)/100)/365 + discountMoney;
			DecimalFormat df   = new DecimalFormat("######0.00"); 
			  
			profit = df.format(futureMoney);
		}catch(Exception e){
			
		}
		return profit;
	}
	
}
