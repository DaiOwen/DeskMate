package com.dai.service;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.dai.BaseTest;
import com.dai.entity.Area;

public class AreaServiceTest extends BaseTest{
	@Autowired
	private AreaService areaService;
	@Autowired
	private CacheService cacheService;
	@Test
	public void getAreaList() {
		List<Area> areaList = areaService.getAreaList();
		assertEquals("长安区", areaList.get(0).getAreaName());
		cacheService.removeFromCache(AreaService.AREALISTKEY);
		areaList = areaService.getAreaList();
	}
	

}
