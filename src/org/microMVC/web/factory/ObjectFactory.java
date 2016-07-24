package org.microMVC.web.factory;

import org.dom4j.DocumentException;


/**   
* @Description: 工厂接口
* @author tanjq
* @version V1.0   
*/
public interface ObjectFactory {
	
	/**
	 * init: 初始化bean
	 * @param configFile: xml文件的路径
	 * @throws DocumentException: void
	 */
	public void init(String configFile) throws DocumentException;
	/**
	 * getInstanceByBeanName: 根据bean的名字得到bean实例
	 * @param beanName： 
	 * @return ： bean实例
	 * @throws Exception: Object
	 */
	public Object getInstanceByBeanName(String beanName) throws Exception;
	/**
	 * getInstance: 已知目标类，采用CgLib生成bean实例
	 * @param obj
	 * @return
	 * @throws Exception: Object
	 */
	public Object getInstance(Object obj) throws Exception;
}

