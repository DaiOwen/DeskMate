package com.dai.web.local;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/local")
public class LocalController {
	/**
	 * 绑定帐号页路由
	 * @return
	 */
	@RequestMapping(value = "/accountbind",method = RequestMethod.GET)
	private String accountbind() {
		return "local/accountbind";
	}
	/**
	 * 修改密码页路由
	 * @return
	 */
	@RequestMapping(value = "/changepsw",method = RequestMethod.POST)
	private String changepsw() {
		return "local/changepsw";
	}
	/**
	 * 登录页路由
	 */
	@RequestMapping(value = "/login",method = RequestMethod.GET)
	private String login() {
		return "local/login";
	}
}	
