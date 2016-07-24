package org.microMVC.web.factory;

import java.util.Map;

import org.dom4j.DocumentException;
import org.microMVC.web.aop.AopAspect;
import org.microMVC.web.aop.BaseProxy;
import org.microMVC.web.aop.BeanParserWithAOP;
import org.microMVC.web.aop.CgLibProxy;
import org.microMVC.web.bean.XMLBean;

/**   
* @Description: AOP代理工厂，采用单例模式
* @author tanjq
* @version V1.0   
*/
public class AopProxyFactory implements ObjectFactory {
	private static AopProxyFactory aopProxyFactory = new AopProxyFactory();
	/*private Map<String, Object> singletonMap = new HashMap<String, Object>();*/
	private BaseProxy baseProxy;
	private BeanFactory beanFactory = BeanFactory.getInstance();

	protected AopProxyFactory() {
	}

	/**
	 * getInstance: 获取实例工厂，采用单例模式
	 * @return: AopProxyFactory
	 */
	public static AopProxyFactory getInstance() {
		return aopProxyFactory;
	}

	/* (non-Javadoc)
	 * @see org.microMVC.web.factory.ObjectFactory#init(java.lang.String)
	 */
	@Override
	public void init(String configFile) throws DocumentException {
		beanFactory.init(configFile);
		BeanParserWithAOP beanParserWithAOP = new BeanParserWithAOP();
		beanParserWithAOP.init(configFile);
		Map<String, AopAspect> aopAspectMap = beanParserWithAOP.getAopAspectMap();
		baseProxy = getProxy(aopAspectMap);
	}

	/* (non-Javadoc)
	 * @see org.microMVC.web.factory.ObjectFactory#getInstanceByBeanName(java.lang.String)
	 */
	@Override
	public Object getInstanceByBeanName(String beanName) throws Exception {
		XMLBean bean = beanFactory.getBean(beanName);
		String className = bean.getClassName();
		Class<?> clazz = Class.forName(className);
		Object obj = null;
		if (baseProxy.isClassAccepted(clazz)) {
			obj = baseProxy.getInstance(clazz);
			beanFactory.injectObj(bean, obj);
		} else {
			obj = beanFactory.getInstanceByBeanName(beanName);
		}
		return obj;
	}

	/* (non-Javadoc)
	 * @see org.microMVC.web.factory.ObjectFactory#getInstance(java.lang.Object)
	 */
	@Override
	public Object getInstance(Object obj) throws Exception {
		if (baseProxy.isClassAccepted(obj.getClass())) {
			obj = baseProxy.getInstance(obj);
		}
		return obj;
	}

	public BaseProxy getProxy(Map<String, AopAspect> aopAspectMap) {
		return new CgLibProxy(aopAspectMap);
	}

}
