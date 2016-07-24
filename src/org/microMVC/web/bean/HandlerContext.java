package org.microMVC.web.bean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**   
* @Description: 类似于SpringMVC中的ModelAndView，主要用于封装数据和返回的结果。
* 				此类中存有HttpServletRequest、HttpServletResponse、result
* 				可将数据存入request或域中返回页面。result：需要返回的页面
* @author tanjq
* @version V1.0   
*/
public class HandlerContext {
	private static ThreadLocal<HandlerContext> ctx = new ThreadLocal<HandlerContext>();
	private HttpServletRequest request;
	private HttpServletResponse response;
	private String result;
	
	public HandlerContext(HttpServletRequest request,
			HttpServletResponse response) {
		this.request = request;
		this.response = response;
	}
	
	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	public static void setContext(HandlerContext handlerContext) {
		ctx.set(handlerContext);
	}

	public static HandlerContext getContext() {
		return ctx.get();
	}
	
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	/**
	 * setAttriInRequest: 将结果放入到request域中
	 * @param key： 方入request的键
	 * @param value: 方入request的值
	 */
	public void setAttriInRequest(String key, String value) {
		request.setAttribute(key, value);
	}

	/**
	 * setAttriInSession: 将结果放入到session域中
	 * @param key： 方入session的键
	 * @param value: 方入session的值
	 */
	public void setAttriInSession(String key, String value) {
		request.getSession().setAttribute(key, value);
	}

	public static void clearUp() {
		ctx.remove();
	}
}
