package cn.itcast.test.action;

import javax.annotation.Resource;

import cn.itcast.test.service.TestService;

import com.opensymphony.xwork2.ActionSupport;

public class TestAction extends ActionSupport {
	
	@Resource
	TestService testService;

	public String execute(){
		testService.say();
		return SUCCESS;
	}

}
