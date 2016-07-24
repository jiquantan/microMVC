package org.microMVC.web.aop;

import java.lang.reflect.Method;


/**   
* @Description: 后置通知，含有一个回调函数：after
* @author tanjq
* @version V1.0   
*/
public interface AfterAdvice {
	void after(Class<?> targetClass,Object proxyObj, Method method, Object[] args);
}
