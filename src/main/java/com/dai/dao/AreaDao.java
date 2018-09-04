package com.dai.dao;

import java.util.List;

import com.dai.entity.Area;

/**
 * 测试商铺所在区域
 * @author 18491
 *
 */
public interface AreaDao {

	/**
	 * 列出区域列表
	 */
	List<Area> queryArea();
}
