package org.microMVC.web.aop;

import java.lang.reflect.Method;
import java.util.Map;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import org.microMVC.web.util.BeanUtil;


/**   
* @Description: 动态代理实现类，实现对切面的增强
* @author tanjq
* @version V1.0   
*/
public class CgLibProxy extends BaseProxy implements MethodInterceptor {

	public CgLibProxy(Map<String, AopAspect> aopAspectMap) {
		super(aopAspectMap);
	}
	
	public Object getInstance(Object targetObj) {
		setTargetObj(targetObj);
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(targetObj.getClass());
		enhancer.setCallback(this);
		Object proxyObj =  enhancer.create();
		BeanUtil.copyBeanProperties(targetObj, proxyObj);
		return proxyObj;
	}
	public Object getInstance(Class<? extends Object> targetClass) {
		setTargetClass(targetClass);
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(targetClass);
		enhancer.setCallback(this);
		Object proxyObj =  enhancer.create();
		return proxyObj;
	}

	@Override
	public Object intercept(Object proxyObj, Method method, Object[] args,
			MethodProxy proxy) throws Throwable {
		initAdvices(getTargetClass(), proxyObj, method, args);
		
		execBeforeAdvice(getTargetClass(), proxyObj, method, args);
		
		execAroundBeforeAdvice(getTargetClass(), proxyObj, method, args);
		
		Object resultObj = proxy.invokeSuper(proxyObj, args);
		
		execAroundAfterAdvice(getTargetClass(), proxyObj, method, args);
		
		execAfterAdvice(getTargetClass(), proxyObj, method, args);
		return resultObj;
	}
}
