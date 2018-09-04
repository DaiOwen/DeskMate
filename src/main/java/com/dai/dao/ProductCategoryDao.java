package com.dai.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dai.entity.ProductCategory;

public interface ProductCategoryDao {

	/**
	 * 通过shop id 查询店铺商品类比别
	 * @param shopId
	 * @return
	 */
	List<ProductCategory> queryProductCategoryList(Long shopId);
	
	/**
	 * 批量新增商品类别
	 * @param productCategoryList
	 * @return
	 */
	int batchInsertProductCategory(List<ProductCategory> productCategoryList);
	
	/**
	 * 删除指定商品类别
	 */
	int deleteProductCategory(@Param("productCategoryId")long productCategoryId,@Param("shopId") long shopId);
}
