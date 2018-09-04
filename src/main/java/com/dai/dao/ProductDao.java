package com.dai.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dai.entity.Product;

public interface ProductDao {
	/**
	 * 插入商品
	 * @param product
	 * @return
	 */
	int insertProduct(Product product);
	/**
	 * 更新商品信息
	 */
	int updateProduct(Product product);
	/**
	 * 通过productId查询唯一商品信息
	 * @param productId
	 * @return
	 */
	Product queryProductById(Long productId);
	/**
	 * 查询商品列表并分页，可输入的条件有： 商品名（模糊），商品状态，店铺Id,商品类别
	 * 
	 * @param productCondition
	 * @param beginIndex
	 * @param pageSize
	 * @return
	 */
	List<Product> queryProductList(@Param("productCondition") Product productCondition, @Param("rowIndex") int rowIndex,
			@Param("pageSize") int pageSize);
	/**
	 * 查询对应商品的总数
	 * @param productCondition
	 * @return
	 */
	int queryProductCount(@Param("productCondition")Product productCondition);
	
	/**
	 * 删除商品
	 * 
	 * @param productId
	 * @return
	 */
	int deleteProduct(@Param("productId") long productId, @Param("shopId") long shopId);
	
	/**
	 * 删除商品列表之前，将商品类别ID置为空。
	 * @param l
	 * @return
	 */
	int updateProductCategoryToNull(long l);
	
	
}
