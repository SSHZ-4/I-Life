package zx.rpc.protocal;

import java.io.Serializable;

public class Method implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String methodName;
	private Class[] params;//参数列表，而不是数据，参数的数据在invocation中。
	public Method(String name, Class<?>[] parameterTypes) {
		this.methodName = name;
		this.params = parameterTypes;
	}
	/**
	 * @return the methodName
	 */
	public String getMethodName() {
		return methodName;
	}
	/**
	 * @param methodName the methodName to set
	 */
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	/**
	 * @return the params
	 */
	public Class[] getParams() {
		return params;
	}
	/**
	 * @param params the params to set
	 */
	public void setParams(Class[] params) {
		this.params = params;
	}
	
	

}
