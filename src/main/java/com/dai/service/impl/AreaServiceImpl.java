package com.dai.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dai.cache.JedisUtil;
import com.dai.dao.AreaDao;
import com.dai.entity.Area;
import com.dai.exception.AreaOperationException;
import com.dai.service.AreaService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class AreaServiceImpl implements AreaService {
	@Autowired
	private AreaDao areaDao;
	@Autowired
	private JedisUtil.Keys jedisKeys;
	@Autowired
	private JedisUtil.Strings jedisStrings;
	private static Logger logger = LoggerFactory.getLogger(AreaServiceImpl.class);


	@Transactional
	@Override
	public List<Area> getAreaList() {
		//定义redis的key
		String key = AREALISTKEY;
		//定义接受对象
		List<Area> areaList = null;
		//定义jackson数据装换操作类
		ObjectMapper mapper = new ObjectMapper();
		//判断key是否存在
		if(!jedisKeys.exists(key)) {
			//如不存在，则从数据库里面取出相应数据
			areaDao.queryArea();
			//将相关的实体类集合转换成string,存入redis里面对应的key中
			String jsonString;
			try {
				jsonString = mapper.writeValueAsString(areaList);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
				logger.error(e.getMessage());
				throw new AreaOperationException(e.getMessage());
			}
			jedisStrings.set(key, jsonString);
		}else {
			//若存在，则直接从redis里面取出相应数据
			String jsonString = jedisStrings.get(key);
			//指定要将string转换成集合类型
			JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class,Area.class);
			try {
				//将相关key对应的value里的string转换成对象的实体类集合
				areaList = mapper.readValue(jsonString,javaType);
			} catch (JsonParseException e) {
				e.printStackTrace();
				logger.error(e.getMessage());
				throw new AreaOperationException(e.getMessage());
			} catch (JsonMappingException e) {
				e.printStackTrace();
				logger.error(e.getMessage());
				throw new AreaOperationException(e.getMessage());
			} catch (IOException e) {
				e.printStackTrace();
				logger.error(e.getMessage());
				throw new AreaOperationException(e.getMessage());
			}
		}
		return areaList;
	}

}
