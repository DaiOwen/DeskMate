package com.dai.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.dai.BaseTest;
import com.dai.entity.HeadLine;

public class HeadLineTest extends BaseTest{
	@Autowired
	private HeadLineDao headLineDao;
	
	@Test
	
	public void testQueryHeadLine() {
		List<HeadLine> headLineList = headLineDao.queryHeadLine(new HeadLine());
		assertEquals(5,headLineList.size());
	}
}
