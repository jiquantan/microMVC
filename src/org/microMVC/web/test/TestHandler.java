package org.microMVC.web.test;

import org.microMVC.web.bean.HandlerContext;


public class TestHandler {

	private String name;
	
	public HandlerContext handle()
	{
		HandlerContext context = HandlerContext.getContext();
		context.setAttriInRequest("message", "hello " + name +", welcome to microMVC.");
		context.setAttriInSession("message", "hello " + name +", welcome to microMVC.");
		context.setResult("success");
		return context;
	}
	
}
