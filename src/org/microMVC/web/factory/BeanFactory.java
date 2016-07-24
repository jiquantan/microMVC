package org.microMVC.web.factory;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.dom4j.DocumentException;
import org.microMVC.web.bean.XMLBean;
import org.microMVC.web.bean.XMLBeanProperty;
import org.microMVC.web.parser.BeanParser;
import org.microMVC.web.util.BeanUtil;

/**   
* @Description: bean工厂，采用反射生产xml中配置的bean实例，此类维持一个hashMap保存servlet容器所有初始化的bean
* @author tanjq
* @version V1.0   
*/
public class BeanFactory implements ObjectFactory {
	private static BeanFactory beanFactory = new BeanFactory();
	private Map<String, Object> singletonMap = new HashMap<String, Object>();
	private Map<String, XMLBean> beanMap = new HashMap<String, XMLBean>();

	protected BeanFactory() {
	}

	/**
	 * getInstance: 获取BeanFactory，采用单例模式
	 * @return: BeanFactory
	 */
	public static BeanFactory getInstance() {
		return beanFactory;
	}

	/**
	 * getBean: 根据bean的名字获得bean实例
	 * @param beanName
	 * @return ： bean实例
	 * @throws Exception: XMLBean
	 */
	public XMLBean getBean(String beanName) throws Exception {
		XMLBean bean = beanMap.get(beanName);
		return bean;
	}
	
	/* (non-Javadoc)
	 * @see org.microMVC.web.factory.ObjectFactory#init(java.lang.String)
	 */
	@Override
	public void init(String configFile) throws DocumentException {
		BeanParser beanParser = new BeanParser();
		beanParser.init(configFile);
		beanMap = beanParser.getBeanMap();
	}

	/* (non-Javadoc)
	 * @see org.microMVC.web.factory.ObjectFactory#getInstance(java.lang.Object)
	 */
	@Override
	public Object getInstance(Object obj) throws Exception {
		return obj;
	}

	/* (non-Javadoc)
	 * @see org.microMVC.web.factory.ObjectFactory#getInstanceByBeanName(java.lang.String)
	 */
	@Override
	public Object getInstanceByBeanName(String beanName) throws Exception {
		if (singletonMap.get(beanName) != null)
			return singletonMap.get(beanName);
		XMLBean bean = beanMap.get(beanName);
		String className = bean.getClassName();
		Object obj = Class.forName(className).newInstance();

		injectObj(bean, obj);

		if (bean.isSingleton()) {
			saveSingletonBean(beanName, obj);
		}
		return obj;
	}

	public void injectObj(XMLBean bean, Object obj) throws Exception {
		if (bean.hasProperties()) {
			Map<String, XMLBeanProperty> map = bean.getPropertyMap();
			for (Entry<String, XMLBeanProperty> entry : map.entrySet()) {
				String propertyName = entry.getKey();
				XMLBeanProperty beanProperty = entry.getValue();
				Object beanInProperty;
				if (beanProperty.hasRefToOtherBean()) {
					beanInProperty = getInstanceByBeanName(beanProperty.getRefBeanName());
				} else {
					beanInProperty = beanProperty.getValue();
				}
				setBean(obj, propertyName, beanInProperty);
			}
		}
	}

	private void saveSingletonBean(String beanName, Object obj) {
		singletonMap.put(beanName, obj);
	}

	public void setBean(Object obj, String fieldName, Object fieldValue)
			throws IllegalArgumentException, IllegalAccessException,
			SecurityException, NoSuchFieldException {
		BeanUtil.setBeanProperty(obj, fieldName, fieldValue);
	}

}
