package org.microMVC.web.bean;

import java.util.Map;

/**   
* @Description: 保存xml中的Handler的数据结构，类似于Spring中的beanDefinition
* @author tanjq
* @version V1.0   
*/
public class XMLHandler {
	private String name;
	private String className;
	private Map<String, String> resultMap;
	private String methodName;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public Map<String, String> getResultMap() {
		return resultMap;
	}

	public void setResultMap(Map<String, String> resultMap) {
		this.resultMap = resultMap;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

}
