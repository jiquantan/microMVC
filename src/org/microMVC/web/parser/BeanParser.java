package org.microMVC.web.parser;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.microMVC.web.bean.XMLBean;
import org.microMVC.web.bean.XMLBeanProperty;

/**   
* @Description: 解析xml文件中的bean，并且生成相关的数据结构.
* 				整个servlet容器中也只维持一个hashMap，容器初始化时，bean随之初始化
* @author tanjq
* @version V1.0   
*/
public class BeanParser {
	private Map<String, XMLBean> beanMap = new HashMap<String, XMLBean>();

	public Map<String, XMLBean> getBeanMap() {
		return beanMap;
	}
	
	/**
	 * init: 容器启动时初始化，初始化xml配置的所有的bean
	 * @param configFile： xml文件的路径
	 * @throws DocumentException: void
	 */
	public void init(String configFile) throws DocumentException {
		SAXReader reader = new SAXReader();

		File file = new File(configFile);
		Document document = reader.read(file);
		Element root = document.getRootElement();
		for (Iterator<?> i = root.elementIterator("bean"); i.hasNext();) {
			XMLBean bean = new XMLBean();
			Element beanNode = (Element) i.next();
			String beanName = getAttri(beanNode, "name");
			String beanClassName = getAttri(beanNode, "class");
			String scope = getAttri(beanNode, "scope");
			for (Iterator<?> j = beanNode.elementIterator("property"); j.hasNext();) {
				Element propertyNode = (Element) j.next();
				String propertyName = getAttri(propertyNode, "name");
				String refBeanName = getAttri(propertyNode, "ref");
				String value = getAttri(propertyNode, "value");
				XMLBeanProperty beanProperty = new XMLBeanProperty(
						propertyName, refBeanName, value);
				bean.addProperty(propertyName, beanProperty);
			}
			bean.setName(beanName);
			bean.setClassName(beanClassName);
			bean.setScope(scope);
			beanMap.put(beanName, bean);
		}
	}

	private String getAttri(Element node, String attriName) {
		String value = null;
		if (node != null && node.attribute(attriName) != null) {
			value = node.attribute(attriName).getText();
		}
		return value;
	}


}
