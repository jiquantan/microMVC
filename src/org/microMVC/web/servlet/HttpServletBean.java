package org.microMVC.web.servlet;

import java.io.File;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServlet;

import org.dom4j.DocumentException;
import org.microMVC.web.bean.HandlerMappings;
import org.microMVC.web.factory.AopProxyFactory;
import org.microMVC.web.factory.ObjectFactory;

/**   
* @Description: 前段控制器基类，用于定位、载入初始化信息.
* 				并且维持一个HandlerMappings，每次请求到达前段控制器，就在handlerMappings中查询Handler.
* @author tanjq
* @version V1.0   
*/
public class HttpServletBean extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String contextConfigLocation;   // mvc.xml路径
	
	private String beanConfigLocation;   //配置的bean的路径
	
	private HandlerMappings handlerMappings;
	
	public void setContextConfigLocation(String contextConfigLocation) {
		this.contextConfigLocation = contextConfigLocation;
	}

	public void setBeanConfigLocation(String beanConfigLocation) {
		this.beanConfigLocation = beanConfigLocation;
	}
	
	public void setActionMappings(HandlerMappings handlerMappings) {
		this.handlerMappings = handlerMappings;
	}

	public String getContextConfigLocation() {
		return contextConfigLocation;
	}

	public String getBeanConfigLocation() {
		return beanConfigLocation;
	}
	
	public HandlerMappings getActionMappings() {
		return handlerMappings;
	}
	
	public void initParams(ServletConfig config){
		this.contextConfigLocation = config.getInitParameter("contextConfigLocation");
		this.beanConfigLocation = config.getInitParameter("beanConfigLocation");
	}

	
	/**
	 * 
	 * initObjFactory: 初始化配置的ObjectFactory
	 * @param config： xml文件路径
	 * @return: ObjectFactory
	 */
	public ObjectFactory initObjFactory(ServletConfig config){
		if(beanConfigLocation == null || beanConfigLocation.equals("")){
			throw new RuntimeException("beanConfigLocation can not null.");
		}
		String projectPath = config.getServletContext().getRealPath("/");
		String relBeanConfigPath = projectPath +"WEB-INF"+File.separatorChar+"classes"+File.separatorChar + beanConfigLocation;
		ObjectFactory objectFactory = AopProxyFactory.getInstance();
		try {
			objectFactory.init(relBeanConfigPath);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return objectFactory;
	}
	
	/**
	 * 
	 * initActionMapping: 初始化，Action和result对应关系
	 * @param config: xml文件的路径
	 * @param objFactory: void
	 */
	public void initActionMapping(ServletConfig config, ObjectFactory objFactory){
		if(contextConfigLocation == null || contextConfigLocation.equals("")){
			throw new RuntimeException("contextConfigLocation can not null.");
		}
		String projectPath = config.getServletContext().getRealPath("/");
		String relContentConfigPath = projectPath +"WEB-INF"+File.separatorChar+"classes"+File.separatorChar+contextConfigLocation;
		handlerMappings = HandlerMappings.getInstance();
		try {
			handlerMappings.init(relContentConfigPath);
			handlerMappings.setObjectFactory(objFactory);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
}
