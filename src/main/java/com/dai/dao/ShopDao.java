package com.dai.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dai.entity.Shop;

public interface ShopDao {
	/**
	 * 分页查询店铺，可输入的条件有：店铺名（模糊），店铺状态，店铺类别，区域Id,owner
	 * @param shopCondition
	 * @param rowIndex  从第几行开始取数据
	 * @param pageSize 返回条数
	 * @return
	 */
	List<Shop> queryShopList(@Param("shopCondition")Shop shopCondition,@Param("rowIndex") int rowIndex,
			@Param("pageSize") int pageSize); 
	
	
	int queryShopCount(@Param("shopCondition")Shop shopCondition);
	
	/**
	 * 通过Id查询店铺
	 * @param shopId
	 * @return
	 */
	Shop queryByShopId(long shopId);
	/**
	 * 添加店铺
	 * @param shop
	 * @return
	 */
	int insertShop(Shop shop);
	
	/**
	 * 更新店铺
	 * @param shop
	 * @return
	 */
	int updateShop(Shop shop);
}
