package org.microMVC.web.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.microMVC.web.bean.HandlerContext;
import org.microMVC.web.factory.ObjectFactory;
import org.microMVC.web.util.Constants;

/**   
* @Description: 前段控制器，servlet容器初始化时，就初始化前段控制器，并且载入相关信息
* @author tanjq
* @version V1.0   
*/
public class MicroFrontServlet extends HttpServletBean{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	@Override
	public void init(ServletConfig config){
		initParams(config);
		ObjectFactory objFactory = initObjFactory(config);
		initActionMapping(config, objFactory);
	}
	
	@Override
	public void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
	
		HandlerContext actionContext = new HandlerContext(req, resp);
		HandlerContext.setContext(actionContext);
		String uri = getShortUri(req);
		
		try {
			if(isHandler(uri)){
				String str = req.getServletPath();
				System.out.println("== " + str + " ==");
			    String actionName = uri.substring(1, uri.length() - Constants.DEFAULT_ACTION_SUFFIX.length());
				String redirectPagePath = getActionMappings().execute(req.getParameterMap(), actionName);
				RequestDispatcher dispatcher = req.getRequestDispatcher(redirectPagePath);
				dispatcher.forward(req, resp);
			
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * getShortUri: 根据请求的信息获取uri，实际上就是找到对应的handler
	 * @param req
	 * @return: String
	 */
	private String getShortUri(HttpServletRequest req){
		String totalURI = req.getRequestURI();
		String contextPath = req.getContextPath();
		String uri = totalURI.substring(contextPath.length());
		return uri;
	}
	
	/**
	 * isHandler: 判断是不是microMVC需要处理的请求
	 * @param uri： 请求的地址
	 * @return: boolean
	 */
	private boolean isHandler(String uri){
		if(uri.endsWith(Constants.DEFAULT_ACTION_SUFFIX))
			return true;
		return false;
	}

	@Override
	public void destroy() {

	}
	
}
