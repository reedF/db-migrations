package com.reed.db.sharding;

import java.lang.reflect.Method;

import com.google.code.shardbatis.strategy.ShardStrategy;

public class MyShardingStrategy implements ShardStrategy {

	/** how many tables to shard */
	private static final int size = 3;

	@Override
	public String getTargetTableName(String baseTableName, Object params,
			String mapperId) {
		try {
			Method m = (Method) params.getClass().getMethod("getAppType");
			String id = String.valueOf(m.invoke(params));
			baseTableName += "_" + new Integer(id) % size;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return baseTableName;
	}
}
