package org.microMVC.web.aop;

/**   
* @Description: 切面，包含需要增强的目标类及方法，需要增强的通知类型
* @author tanjq
* @version V1.0   
*/
public class AopAspect {
	private String id;
	private String classes;
	private String method;
	private String beforeAdvice;
	private String afterAdvice;
	private String aroundAdvice;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getClasses() {
		return classes;
	}

	public void setClasses(String classes) {
		this.classes = classes;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getBeforeAdvice() {
		return beforeAdvice;
	}

	public void setBeforeAdvice(String beforeAdvice) {
		this.beforeAdvice = beforeAdvice;
	}

	public String getAfterAdvice() {
		return afterAdvice;
	}

	public void setAfterAdvice(String afterAdvice) {
		this.afterAdvice = afterAdvice;
	}

	public String getAroundAdvice() {
		return aroundAdvice;
	}

	public void setAroundAdvice(String aroundAdvice) {
		this.aroundAdvice = aroundAdvice;
	}

}
