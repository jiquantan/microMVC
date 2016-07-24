package org.microMVC.web.aop;

import java.lang.reflect.Method;


/**   
* @Description: 前置通知，含有一个回调函数：before
* @author tanjq
* @version V1.0   
*/
public interface BeforeAdvice {
	void before(Class<?> targetClass,Object proxyObj, Method method, Object[] args);
}
