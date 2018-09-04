package com.dai.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dai.entity.HeadLine;

public interface HeadLineDao {
	/**
	 * 根据传入的查询条件（头条名查询头条）
	 * @param headLineCondition
	 * @return
	 */
	List<HeadLine> queryHeadLine(@Param("headLineCondition") HeadLine headLineCondition);
}
