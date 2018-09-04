package com.dai.dao;

import java.util.List;

import com.dai.entity.ProductImg;

public interface ProductImgDao {
	List<ProductImg> queryProductImgList(long productId);
	/**
	 * 批量添加详情图片
	 * @param productImgList
	 * @return
	 */
	int batchInsertProductImg(List<ProductImg> productImgList);
	/**
	 * 删除指定商品下的所有详情图
	 * @param productId
	 * @return
	 */
	int deleteProductImgByProductId(long productId);
}
