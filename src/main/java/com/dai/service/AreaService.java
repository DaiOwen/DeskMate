package com.dai.service;

import java.util.List;

import com.dai.entity.Area;

public interface AreaService {
	public static final String AREALISTKEY = "arealist";
	List<Area> getAreaList();
}
