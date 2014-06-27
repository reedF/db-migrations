package com.reed.aop.datasource.dynamic;

import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 动态读写分离，数据源切换拦截器
 * 
 * @author reed
 * 
 */
@Component
@Aspect
public class DynamicDsAOP {

	private Logger logger = LoggerFactory.getLogger(DynamicDsAOP.class);

	@Autowired
	private DynamicDataSource ds;

	/**
	 * 切换数据源,目标对象的方法执行前执行,根据需求配置切点： 建议在service切入，在mapper易出现事务问题导致连接获取错误
	 * 
	 * @param jp
	 */
	@Before("execution(* com..mapper..*.*(..)) || execution(* com.reed..mapper..*.*(..))")
	public void beforeInvoke(JoinPoint jp) {
		String beanName = jp.getTarget().getClass().getName();
		String methodName = jp.getSignature().getName();
		if (StringUtils.isNotBlank(methodName)) {
			String n = methodName.toLowerCase();
			if (n.startsWith("save") || n.startsWith("update")
					|| n.startsWith("insert") || n.startsWith("del")
					|| n.startsWith("remove")
					|| n.startsWith("selectbyprimarykey")) {
				JdbcContextHolder.setCustomerType(JdbcContextHolder.master);
			} else if (n.startsWith("find")
					|| n.startsWith("count")
					|| (n.startsWith("select") && !n
							.startsWith("selectbyprimarykey"))) {
				JdbcContextHolder.setCustomerType(JdbcContextHolder.slave);
			} else {
				JdbcContextHolder.clearCustomerType();
			}
		}
		logger.debug(
				"datasource aop 拦截 ，前置通知:class={},method={},ds={},realDs:{}",
				beanName, methodName, JdbcContextHolder.getCustomerType(), ds
						.determineCurrentLookupKey().toString());
	}

	@After("execution(* com..mapper..*.*(..)) || execution(* com.reed..mapper..*.*(..))")
	public void afterReturnInvoke() {
		JdbcContextHolder.clearCustomerType();
	}
}
