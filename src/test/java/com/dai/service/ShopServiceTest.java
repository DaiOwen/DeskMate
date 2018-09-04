package com.dai.service;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Date;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.dai.BaseTest;
import com.dai.dto.ImageHolder;
import com.dai.dto.ShopExecution;
import com.dai.entity.Area;
import com.dai.entity.PersonInfo;
import com.dai.entity.Shop;
import com.dai.entity.ShopCategory;
import com.dai.enums.ShopStateEnum;

public class ShopServiceTest extends BaseTest{
	@Autowired
	private ShopService shopService;
	
	@Test	
	public void testGetShopList() {
		Shop shopCondition = new Shop();
		ShopCategory sc = new ShopCategory();
		sc.setShopCategoryId(1L);
		shopCondition.setShopCategory(sc);
		ShopExecution se = shopService.getShopList(shopCondition, 1, 2);
		System.out.println("店铺列表数为："+se.getShopList().size());
		System.out.println("店铺总数为："+se.getCount());
	}
	
	
	@Test
	@Ignore
	public void testModify() throws FileNotFoundException {
		Shop shop = new Shop();
		shop.setShopId(26L);
		shop.setShopName("modifyShopName2");
		File shopImg = new File("F:/Java/Immok/phone/Tree.jpg");
		FileInputStream fis = new FileInputStream(shopImg);
		ImageHolder imageHolder  = new ImageHolder("Tree.jpg",fis); 
		ShopExecution shopExecution = shopService.modifyShop(shop, imageHolder);
		System.out.println("新的图片地址为："+shopExecution.getShop().getShopImg());
	}
	
	@Ignore
	@Test
	public void testAddShop() throws FileNotFoundException {
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
		shop.setEnableStatus(ShopStateEnum.CHECK.getState());
		shop.setLastEditTime(new Date());
		shop.setOwner(owner);
		shop.setPhone("159912121");
		shop.setPriority(1);
		shop.setShopAddr("西邮小吃街3");
		shop.setShopCategory(shopCategory);
		shop.setShopDesc("test333s");
		shop.setShopName("Do YouSelf");
		File shopImg = new File("F:\\MyCollegelife\\我的大三\\我的照片\\2.jpg");
		FileInputStream is = new FileInputStream(shopImg);
		ImageHolder imageHolder = new ImageHolder(shopImg.getName(),is);
		ShopExecution addShop = shopService.addShop(shop, imageHolder);
		assertEquals(ShopStateEnum.CHECK.getState(), addShop.getState());
	}

}
