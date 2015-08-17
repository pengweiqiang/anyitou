package cn.com.anyitou.entity;

public class ParseModel extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Object apiResult;
	
	private int resultCount; //总数
	
	private String session_id;
	
	private String token;
	
	private String hfRegisterUrl;
	
	private String url;
	
	private int ishfuser;//1代表开通汇付  0代表未开通汇付
	
	private String ordId;
	
	private String otherStr;
	
	
	public Object getApiResult() {
		return apiResult;
	}

	public void setApiResult(Object apiResult) {
		this.apiResult = apiResult;
	}

	public int getResultCount() {
		return resultCount;
	}

	public void setResultCount(int resultCount) {
		this.resultCount = resultCount;
	}

	public String getSession_id() {
		return session_id;
	}

	public void setSession_id(String session_id) {
		this.session_id = session_id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getHfRegisterUrl() {
		return hfRegisterUrl;
	}

	public void setHfRegisterUrl(String hfRegisterUrl) {
		this.hfRegisterUrl = hfRegisterUrl;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getIshfuser() {
		return ishfuser;
	}

	public void setIshfuser(int ishfuser) {
		this.ishfuser = ishfuser;
	}

	public String getOrdId() {
		return ordId;
	}

	public void setOrdId(String ordId) {
		this.ordId = ordId;
	}

	public String getOtherStr() {
		return otherStr;
	}

	public void setOtherStr(String otherStr) {
		this.otherStr = otherStr;
	}
	
	
	
	
}
