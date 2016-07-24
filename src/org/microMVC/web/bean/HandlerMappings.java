package org.microMVC.web.bean;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.dom4j.DocumentException;
import org.microMVC.web.factory.ObjectFactory;
import org.microMVC.web.parser.HandlerParser;
import org.microMVC.web.util.BeanUtil;

/**   
* @Description: 处理器映射器，主要映射Web的各种请求，HandlerMappings中有一个hashMap用于保存处理器和url的映射关系，
* 				并且采用单例模式使整个web容器中只存在一个HandlerMappings。
* @author tanjq
* @version V1.0   
*/
public class HandlerMappings {
	private static HandlerMappings handlerMappings = new HandlerMappings();
	private Map<String, XMLHandler> handlerMap = new HashMap<String, XMLHandler>();
	private HandlerParser handlerParser = new HandlerParser();
	private Map<String, String> requestDataMap; 
	private Map<String, String> sessionDataMap;
	private ObjectFactory objectFactory;

	private HandlerMappings() {
	}

	public static HandlerMappings getInstance() {
		return handlerMappings;
	}

	public void setObjectFactory(ObjectFactory objectFactory) {
		this.objectFactory = objectFactory;
	}
	
	public Map<String, String> getRequestDataMap() {
		return requestDataMap;
	}

	public Map<String, String> getSessionDataMap() {
		return sessionDataMap;
	}

	public void setSessionDataMap(Map<String, String> sessionDataMap) {
		this.sessionDataMap = sessionDataMap;
	}
	
	/**
	 * init: 初始化xml配置的所有的handler
	 * @param configFile： xml文件的路径
	 * @throws DocumentException: void
	 */
	public void init(String configFile) throws DocumentException {
		handlerParser.init(configFile);
		handlerMap = handlerParser.getHandlerMap();
	}

	/**
	 * getHandler: 从HandlerMappings中维持的handlerMap获取handler
	 * @param handlerName
	 * @return: XMLHandler
	 */
	private XMLHandler getHandler(String handlerName) {
		return handlerMap.get(handlerName);
	}
	
	/**
	 * execute: 此方法类似SpringMVC中的doDispatch，根据HTTP请求，执行Handler，最后返回结果，最后将数据和结果返回
	 * @param requestMap： 请求参数map
	 * @param handlerName: handler的名字
	 * @return: 返回结果
	 * @throws Exception: String
	 */
	public String execute(Map<?, ?> requestMap, String handlerName) throws Exception {
		setRequest(requestMap);
		XMLHandler handler = getHandler(handlerName);
		String className = handler.getClassName();
		String redirectPagePath = null;
		Class<?> clazz;
		Object obj = null;
		try {
			clazz = Class.forName(className);
			obj = clazz.newInstance();
		} catch (Exception e) {
			obj = objectFactory.getInstanceByBeanName(className);
			clazz = obj.getClass();
		}
		injectData(obj);
		HandlerContext context = (HandlerContext) BeanUtil.invokeMethod(obj,handler.getMethodName());
		String result = context.getResult();
		redirectPagePath = handler.getResultMap().get(result);
		return redirectPagePath;
	}

	/**
	 * injectData: 注入参数，并保存在hashMap中
	 * @param obj: void
	 */
	private void injectData(Object obj) {
		if (requestDataMap == null){
			return;
		}
		for (Entry<String, String> entry : requestDataMap.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			injectData(obj, key, value);
		}
	}

	private void injectData(Object obj, String fieldName, Object fieldValue) {
		BeanUtil.setBeanProperty(obj, fieldName, fieldValue);
	}

	public void setRequest(Map<?, ?> requestParameterMap) {
		Map<String, String> map = new HashMap<String, String>();
		Set<?> keySet = requestParameterMap.keySet();
		Iterator<?> iter = keySet.iterator();
		while (iter.hasNext()) {
			String key = (String) iter.next();
			String[] valueArr = (String[]) requestParameterMap.get(key);
			map.put(key, valueArr[0]);
		}
		this.requestDataMap = map;
	}

}
