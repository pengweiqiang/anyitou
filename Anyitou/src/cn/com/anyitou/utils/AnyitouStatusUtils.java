package cn.com.anyitou.utils;

public class AnyitouStatusUtils {
	
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
}
