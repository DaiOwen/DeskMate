package com.dai.service;

import java.io.InputStream;

import com.dai.dto.ImageHolder;
import com.dai.dto.ShopExecution;
import com.dai.entity.Shop;
import com.dai.exception.ShopOperationException;

public interface ShopService {
	/**
	 * 根据shopCondition分页返回相应店铺列表
	 * @param shopCondition
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public ShopExecution getShopList(Shop shopCondition,int pageIndex,int pageSize);
	/**
	 * 通过Id获取店铺信息
	 * @param id
	 * @return
	 */
	Shop getByShopId(Long shopId);
	
	/**
	 * 更新店铺信息，包括图片的处理
	 * @param shop
	 * @param shopImgInputStream
	 * @param fileName
	 * @return
	 * @throws ShopOperationException
	 */
	ShopExecution modifyShop(Shop shop,ImageHolder thumbnail)throws ShopOperationException;
	
	/**
	 * 注册店铺信息，包括图片的处理
	 * @param shop
	 * @param shopImgInputStream
	 * @param fileName
	 * @return
	 */
	ShopExecution addShop(Shop shop,ImageHolder thumbnail)throws ShopOperationException;
}
