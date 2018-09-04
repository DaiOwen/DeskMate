package com.dai.service;

import java.util.List;

import com.dai.dto.ImageHolder;
import com.dai.dto.ProductExecution;
import com.dai.entity.Product;
import com.dai.exception.ProductOperationException;

public interface ProductService {
	/**
	 * 添加商品信息以及图片处理
	 * @return
	 * @throws ProductOperationException
	 */
	ProductExecution addProduct(Product product,ImageHolder thumbnail
			,List<ImageHolder> productImgList) throws ProductOperationException;

	/**
	 * 通过商品Id获取唯一的商品信息
	 * @param productId
	 * @return
	 */
	Product getProductById(Long productId);
	/**
	 * 修改商品信息以及图片处理
	 * @param product
	 * @param thumbnail
	 * @param productImgList
	 * @return
	 */
	ProductExecution modifyProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgList);

	/**
	 * 查询商品列表并分页，可输入的条件有：商品名（模糊）、商品状态，店铺Id,商品类别
	 * @param productCondition
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	ProductExecution getProductList(Product productCondition, int pageIndex, int pageSize);
}
