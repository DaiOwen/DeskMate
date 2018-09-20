package com.dai.dao;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.dai.BaseTest;
import com.dai.entity.LocalAuth;
import com.dai.entity.PersonInfo;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LocalAuthDaoTest extends BaseTest {
	@Autowired
	private LocalAuthDao localAuthDao;
	private static final String username = "testusername";
	private static final String password = "testuserpasswd";

	@Test
	@Ignore
	public void testAInsertLocalAuth() throws Exception {
		// 新增一条平台帐号信息
		LocalAuth localAuth = new LocalAuth();
		PersonInfo personInfo = new PersonInfo();
		personInfo.setUserId(3L);
		// 给平台帐号绑定用户信息
		localAuth.setPersonInfo(personInfo);
		// 给平台上设置用户姓名和密码
		localAuth.setusername(username);
		localAuth.setPassword(password);
		localAuth.setCreateTime(new Date());
		int effectNum = localAuthDao.insertLocalAuth(localAuth);
		assertEquals(1, effectNum);
	}

	@Test
	@Ignore
	public void testBQueryLocalByUserAndPWD() throws Exception {
		// 按照用户id查询平台帐号，进而获取用户信息
		LocalAuth localAuth = localAuthDao.queryLocalByUserNameAndPwd(username, password);
		assertEquals("姚明", localAuth.getPersonInfo().getName());
	}

	@Test
	@Ignore
	public void testCQueryLocalByUserId() throws Exception {
		LocalAuth localAuth = localAuthDao.queryLocalByUserId(3L);
		assertEquals("姚明", localAuth.getPersonInfo().getName());
	}

	@Test
	public void testDUpdateLocalAuth() throws Exception {
		//依据用户id,平台帐号，以及旧密码修改平台帐号密码
		Date now = new Date();
		int effectNum = localAuthDao.updateLocalAuth(3L, username, password+"new", password+"daihu", now);
		assertEquals(1, effectNum);
		//查询出该条件平台帐号的最新消息
		LocalAuth localAuth = localAuthDao.queryLocalByUserId(3L);
		System.out.println(localAuth.getusername()+"==="+localAuth.getPassword());
		
	}
}
