package org.microMVC.web.adviceImpl;

import java.lang.reflect.Method;

import org.microMVC.web.aop.AfterAdvice;

/**   
* @Description: 前置通知实现类，目标方法执行前会先回调执行此类中的after方法，实现对目标方法的增强
* @author tanjq
* @version V1.0   
*/
public class AfterAdviceImpl implements AfterAdvice {

	@Override
	public void after(Class<?> targetClass, Object proxyObj, Method method,
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
