package com.dai.dao;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.dai.BaseTest;
import com.dai.entity.Area;
import com.dai.entity.PersonInfo;
import com.dai.entity.Shop;
import com.dai.entity.ShopCategory;

public class ShopDaoTest extends BaseTest {

	@Autowired
	private ShopDao shopDao;
	

	@Test
	public void testQueryShopList() {
		Shop shopCondition = new Shop();
		PersonInfo owner = new PersonInfo();
		owner.setUserId(1L);
		shopCondition.setOwner(owner);
		List<Shop> shopList = shopDao.queryShopList(shopCondition, 1, 5);
		int count = shopDao.queryShopCount(shopCondition);
		System.out.println("店铺总数为："+count);
		System.out.println("店铺列表的大小："+shopList.size());
		ShopCategory sc = new ShopCategory();
		sc.setShopCategoryId(1L);
		shopCondition.setShopCategory(sc);
		shopList = shopDao.queryShopList(shopCondition, 0, 2);
		System.out.println("商铺类别的列表大小为："+shopList.size());
		count = shopDao.queryShopCount(shopCondition);
		System.out.println("店铺类别总数为："+count);
	}

	@Test
	@Ignore
	public void testqueryByShopId() {
		long shopId = 1;
		Shop shop = shopDao.queryByShopId(shopId);
		System.out.println("areaId" + shop.getArea().getAreaName());
		System.out.println(shop.getArea().getAreaId());
	}

	@Test
	@Ignore
	public void testInsertshop() {
		Shop shop = new Shop();
		PersonInfo owner = new PersonInfo();
		Area area = new Area();
		ShopCategory shopCategory = new ShopCategory();
		owner.setUserId(1L);
		area.setAreaId(1);
		shopCategory.setShopCategoryId(1L);
		shop.setArea(area);
		shop.setAdvice("正在审核中...");
		shop.setCreateTime(new Date());
		shop.setEnableStatus(0);
		shop.setLastEditTime(new Date());
		shop.setOwner(owner);
		shop.setPhone("15991262121");
		shop.setPriority(1);
		shop.setShopAddr("西邮小吃街");
		shop.setShopCategory(shopCategory);
		shop.setShopDesc("test");
		shop.setShopImg("zhuyu");
		shop.setShopName("I am I");
		int effectedNum = shopDao.insertShop(shop);
		assertEquals(1, effectedNum);
	}

	@Test
	@Ignore
	public void testUpdateShop() {
		Shop shop = new Shop();
		shop.setShopId(1L);
		shop.setAdvice("审核通过");
		int effectNum = shopDao.updateShop(shop);
		assertEquals(1, effectNum);

	}
}
