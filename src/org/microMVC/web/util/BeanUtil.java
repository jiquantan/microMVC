package org.microMVC.web.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**   
* @Description: 工具类
* @author tanjq
* @version V1.0   
*/
public class BeanUtil {

	/**
	 * copyBeanProperties: 拷贝bean的属性
	 * @param sourceBean
	 * @param destinationBean: void
	 */
	public static void copyBeanProperties(Object sourceBean,
			Object destinationBean) {
		Class<?> parentClass = sourceBean.getClass();
		while (parentClass != null) {
			final Field[] fields = parentClass.getDeclaredFields();
			for (Field field : fields) {
				try {
					field.setAccessible(true);
					field.set(destinationBean, field.get(sourceBean));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			parentClass = parentClass.getSuperclass();
		}
	}

	/**
	 * setBeanProperty: 设置bean的参数
	 * @param obj： bean实例
	 * @param fieldName： 参数名
	 * @param fieldValue: 参数值
	 */
	public static void setBeanProperty(Object obj, String fieldName,
			Object fieldValue) {
		Class<?> parentClass = obj.getClass();
		while (parentClass != null) {
			final Field[] fields = parentClass.getDeclaredFields();
			for (Field field : fields) {
				if (field.getName().equals(fieldName)) {
					try {
						field.setAccessible(true);
						field.set(obj, fieldValue);
					} catch (Exception e) {
						e.printStackTrace();
					}
					break;
				}
			}
			parentClass = parentClass.getSuperclass();
		}
	}

	/**
	 * invokeMethod: 真正调用目标方法
	 * @param obj： 目标类
	 * @param methodName： 目标方法名
	 * @return: Object： 返回的结果，一般返回的是microMVC封装的HandlerContext
	 */
	public static Object invokeMethod(Object obj, String methodName) {
		Object result = null;
		try {
			Method method = obj.getClass().getMethod(methodName);
			result = method.invoke(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
