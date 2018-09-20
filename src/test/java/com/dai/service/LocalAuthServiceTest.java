package com.dai.service;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.dai.BaseTest;
import com.dai.dto.LocalAuthExecution;
import com.dai.entity.LocalAuth;
import com.dai.entity.PersonInfo;
import com.dai.enums.WechatAuthStateEnum;

public class LocalAuthServiceTest extends BaseTest{
	@Autowired
	private LocalAuthService localAuthService;
	
	@Test
	public void testABindLocalAuth () {
		//新建一条平台账号
		LocalAuth localAuth = new LocalAuth();
		PersonInfo personInfo = new PersonInfo();
		String username = "testusername";
		String password = "testpassword";
		//给平台帐号设置上设置用户信息
		//给用户设置上用户ID，表明是那个用户绑定了该帐号
		personInfo.setUserId(3L);
		localAuth.setPersonInfo(personInfo);
		localAuth.setusername(username);
		localAuth.setPassword(password);
		LocalAuthExecution lae = localAuthService.bindLocalAuth(localAuth);
		System.out.println("lae的插入状态====="+lae.getState());
		assertEquals(WechatAuthStateEnum.SUCCESS.getState(), lae.getState());
		//通过userId找到新增的localAuth
		localAuth = localAuthService.getLocalAuthByUserId(personInfo.getUserId());
		//打印用户密码和帐号，看是否和预期的一致
		System.out.println("平台昵称："+localAuth.getusername());
		System.out.println("平台帐号密码"+localAuth.getPassword());
		
	}
	
	

}
