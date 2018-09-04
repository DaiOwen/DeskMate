package com.dai.service;

import java.util.List;

import com.dai.entity.HeadLine;

public interface HeadLineService {
	public static final String HLLISTKEY = "headlinelist";
	/**
	 * 根据传入的条件返回指定的头条列表
	 * @param headLineCondition
	 * @return
	 */
	List<HeadLine> getHeadLineList(HeadLine headLineCondition);
	
}
