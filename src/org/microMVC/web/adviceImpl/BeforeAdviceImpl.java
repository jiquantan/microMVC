package org.microMVC.web.adviceImpl;

import java.lang.reflect.Method;

import org.microMVC.web.aop.BeforeAdvice;

/**   
* @Description: 后置通知实现类，目标方法执行后会回调执行此类中的before方法，实现对目标方法的增强
* @author tanjq
* @version V1.0   
*/
public class BeforeAdviceImpl implements BeforeAdvice {

	@Override
	public void before(Class<?> targetClass, Object proxyObj, Method method,
			Object[] args) {
		StringBuffer sb = new StringBuffer();
		sb.append(this.getClass().getSimpleName());
		sb.append(" targetObj: ");
		sb.append(targetClass.getSimpleName());
		sb.append(" method: ");
		sb.append(method.getName());
		System.out.println(sb.toString());
	}
}
