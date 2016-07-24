package org.microMVC.web.adviceImpl;

import java.lang.reflect.Method;
import java.util.Date;

import org.microMVC.web.aop.AroundAdvice;

/**   
* @Description: 环绕通知实现类，目标方法执行前后执行后会分别回调before、after方法，实现对目标方法的增强
* @author tanjq
* @version V1.0   
*/
public class AroundAdviceImpl implements AroundAdvice {
	private Date enterMethodTime;
	private Date leaveMethodTime;
	
	@Override
	public void before(Class<?> targetClass, Object proxyObj, Method method,
			Object[] args) {
		enterMethodTime = new Date();
		StringBuffer sb = new StringBuffer();
		sb.append(this.getClass().getSimpleName());
		sb.append(" targetObj: ");
		sb.append(targetClass.getSimpleName());
		sb.append(" method: ");
		sb.append(method.getName());
		System.out.println(sb.toString());
	}
	
	@Override
	public void after(Class<?> targetClass, Object proxyObj, Method method,
			Object[] args) {
		
		leaveMethodTime = new Date();
		long timeElapsed = getTimeDiff(enterMethodTime,leaveMethodTime);
		StringBuffer sb = new StringBuffer();
		sb.append(this.getClass().getSimpleName());
		sb.append(" targetObj: ");
		sb.append(targetClass.getSimpleName());
		sb.append(" method: ");
		sb.append(method.getName());
		System.out.println(sb.toString());
		System.out.println("Time Elapsed: "+timeElapsed+" ms");
	}
	
	/**
	 * getTimeDiff: 计算方法执行的时间
	 * @param startDate: 执行目标方法的开始时间
	 * @param endDate： 目标方法执行完的结束时间
	 * @return: long： 时间差
	 */
	private long getTimeDiff(Date startDate,Date endDate)
	{
		return endDate.getTime()-startDate.getTime();
	}
}
