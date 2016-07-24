package org.microMVC.web.bean;


/**   
* @Description: 依赖bean的数据结构
* @author tanjq
* @version V1.0   
*/
public class XMLBeanProperty {
	private String name;
	private String refBeanName;
	private String value;

	public XMLBeanProperty() {
	}

	public XMLBeanProperty(String name, String refBeanName, String value) {
		this.name = name;
		this.refBeanName = refBeanName;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRefBeanName() {
		return refBeanName;
	}

	public void setRefBeanName(String refBeanName) {
		this.refBeanName = refBeanName;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public boolean hasRefToOtherBean() {
		if (refBeanName != null)
			return true;
		else
			return false;
	}

}
