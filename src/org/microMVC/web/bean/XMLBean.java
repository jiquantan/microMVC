package org.microMVC.web.bean;

import java.util.HashMap;
import java.util.Map;

import org.microMVC.web.util.Constants;

/**   
* @Description: 保存xml中的数据结构，类似于Spring中的beanDefinition.其中的map属性用于保存依赖的bean.
* @author tanjq
* @version V1.0   
*/
public class XMLBean {
	private String name;
	private String className;
	private String scope;
	private Map<String, XMLBeanProperty> propertyMap = new HashMap<String, XMLBeanProperty>();

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

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public boolean hasProperties() {
		if (propertyMap == null || propertyMap.size() == 0) {
			return false;
		} else {
			return true;
		}
	}

	public void addProperty(String propertyName, XMLBeanProperty beanProperty) {
		propertyMap.put(propertyName, beanProperty);
	}

	public Map<String, XMLBeanProperty> getPropertyMap() {
		return propertyMap;
	}

	public boolean isSingleton() {
		if (Constants.SCOPE_SINGLETON.equals(scope)) {
			return true;
		} else {
			return false;
		}
	}
}
