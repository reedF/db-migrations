package com.reed.aop.datasource.dynamic;

import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 动态读写分离，数据源切换拦截器 
 * 根据需求配置切点： 建议在service切入，在mapper易出现事务问题导致连接获取错误
 * 原因：spring事务默认为required，嵌套事务后，默认内部事务会加入外部事务，
 * 而大事务使用的session为开启的获取的连接，因此，当业务逻辑中出现在一个大事务里包括：先查询，后更新的情况时，会出现update in a
 * read-only tranctions error，
 * 因为：先查询时获取连接为从库，开启事务，查询执行完毕后，返回连接给spring事务容器，接着写操作，由于在大事务中
 * ，会继续使用事务开启时的session连接，导致写操作走从库了。
 *  解决：
 *  1.配置切点为service，保证在开启事务时已确定了正确的session连接
 *  2.更改事务级别，保证内嵌事务每次重新获取连接
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
	 * 切入点
	 */
	@Pointcut("execution(* com..mapper..*.*(..)) || execution(* com.reed..mapper..*.*(..))")
	public void inMapper() {

	}

	/**
	 * 切换数据源,目标对象的方法执行前执行
	 * 
	 * @param jp
	 */
	@Before("inMapper()")
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

	@After("inMapper()")
	public void afterReturnInvoke() {
		JdbcContextHolder.clearCustomerType();
	}
}
