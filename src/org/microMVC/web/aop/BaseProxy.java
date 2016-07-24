package org.microMVC.web.aop;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.microMVC.web.factory.BeanFactory;


/**   
* @Description: 代理对象基类，其中包括需要代理的类及通知对象实现类
* @author tanjq
* @version V1.0   
*/
public abstract class BaseProxy {
	private Class<?> targetClass;
	private Object targetObj;
	protected BeforeAdvice beforeAdvice;
	protected AfterAdvice afterAdvice;
	protected AroundAdvice aroundAdvice;
	protected Map<String, AopAspect> aopAspectMap;

	public BaseProxy(Map<String, AopAspect> aopAspectMap) {
		this.aopAspectMap = aopAspectMap;
	}
	private void resetAdvices()
	{
		beforeAdvice = null;
		afterAdvice = null;
		aroundAdvice = null;
	}

	protected void initAdvices(Class<?> targetClass, Object proxyObj,
			Method method, Object[] args) throws Exception {
		resetAdvices();
		
		String classFullPath = targetClass.getCanonicalName();
		String methodName = method.getName();
		for (Entry<String, AopAspect> entry : aopAspectMap.entrySet()) {
			AopAspect aopAspect = entry.getValue();
			String classPattern = preparePattern(aopAspect.getClasses());
			String methodPattern = preparePattern(aopAspect.getMethod());
			if(isMatch(classFullPath, methodName, classPattern, methodPattern))
			{
				BeanFactory beanFactory = BeanFactory.getInstance();
				beforeAdvice = (BeforeAdvice)beanFactory.getInstanceByBeanName(aopAspect.getBeforeAdvice());
				afterAdvice = (AfterAdvice)beanFactory.getInstanceByBeanName(aopAspect.getAfterAdvice());
				aroundAdvice = (AroundAdvice)beanFactory.getInstanceByBeanName(aopAspect.getAroundAdvice());
				break;
			}
		}
	}
	public boolean isClassAccepted(Class<?> clazz)
	{
		String classFullPath = clazz.getCanonicalName();
		for (Entry<String, AopAspect> entry : aopAspectMap.entrySet()) {
			AopAspect aopAspect = entry.getValue();
			String classPattern = preparePattern(aopAspect.getClasses());
			if(isMatch(classFullPath, classPattern))
			{
				return true;
			}
		}
		
		return false;
	}

	private boolean isMatch(String classFullPath, String methodName,
			String classPattern, String methodPattern) {
		if (isMatch(classFullPath, classPattern)
				&& isMatch(methodName, methodPattern))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	private boolean isMatch(String srcStr, String pattern) {
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(srcStr);
		if (m.find()) {
			return true;
		} else {
			return false;
		}
	}

	private String preparePattern(String inputPattern) {
		inputPattern = "^"+inputPattern+"$";
		return inputPattern.replaceAll("\\*", ".*");
	}

	public abstract Object getInstance(Class<?> targetClass);
	public abstract Object getInstance(Object obj);

	protected void execBeforeAdvice(Class<?> targetClass, Object proxyObj,
			Method method, Object[] args) {
		try {
			if (beforeAdvice != null)
				beforeAdvice.before(targetClass, proxyObj, method, args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected boolean isToIntercept() {
		return false;
	}

	protected void execAfterAdvice(Class<?> targetClass, Object proxyObj,
			Method method, Object[] args) {
		try {
			if (afterAdvice != null)
				afterAdvice.after(targetClass, proxyObj, method, args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void execAroundBeforeAdvice(Class<?> targetClass, Object proxyObj,
			Method method, Object[] args) {
		try {
			if (aroundAdvice != null)
				aroundAdvice.before(targetClass, proxyObj, method, args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void execAroundAfterAdvice(Class<?> targetClass, Object proxyObj,
			Method method, Object[] args) {
		try {
			if (aroundAdvice != null)
				aroundAdvice.after(targetClass, proxyObj, method, args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setBeforeAdvice(BeforeAdvice beforeAdvice) {
		this.beforeAdvice = beforeAdvice;
	}

	public void setAfterAdvice(AfterAdvice afterAdvice) {
		this.afterAdvice = afterAdvice;
	}

	public void setAroundAdvice(AroundAdvice aroundAdvice) {
		this.aroundAdvice = aroundAdvice;
	}
	
	protected Class<?> getTargetClass() {
		return targetClass;
	}
	protected void setTargetClass(Class<?> targetClass) {
		this.targetClass = targetClass;
	}
	protected Object getTargetObj() {
		return targetObj;
	}
	protected void setTargetObj(Object targetObj) {
		this.targetObj = targetObj;
	}

}
