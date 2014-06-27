package test;

/**
 * ContractTest.java
 * Copyright (c) 2013 by lashou.com
 */

import java.sql.Array;
import java.util.Arrays;

import org.h2.jdbc.JdbcArray;
import org.h2.util.IntArray;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.postgresql.jdbc4.Jdbc4Array;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.reed.crm.domain.AppInfo;
import com.reed.crm.domain.AppInfoExample;
import com.reed.crm.mapper.AppInfoMapper;

/**
 * @author reed
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:db-test.xml")
// @TransactionConfiguration(transactionManager="txm",defaultRollback = true)
// @Transactional
public class DbTest {

	@Autowired
	private AppInfoMapper appInfoMapper;

	private AppInfo s = null;

	@Before
	public void setUp() {
		s = new AppInfo();
		s.setAppType((short) 0);
		// int r = appInfoMapper.insertSelective(s);
		// Assert.assertTrue(r > 0);
	}

	@After
	public void clear() {
		s = null;
	}

	@Test
	public final void testCount() {
		int r = appInfoMapper.countByExample(new AppInfoExample());
		Assert.assertTrue(r > 0);
	}

	@Test
	public final void testInsert() {
		int r = appInfoMapper.countByExample(new AppInfoExample());
		s.setAppType((short) 1);
		int r1 = appInfoMapper.insertSelective(s);
		s.setAppType((short) 2);
		int r2 = appInfoMapper.insertSelective(s);
		s.setAppType((short) 3);
		int r3 = appInfoMapper.insertSelective(s);
		int re = appInfoMapper.countByExample(new AppInfoExample());
		Assert.assertEquals(r + 3, re);
	}

	@Test
	public final void testArray() {
		Long[] a = { 1l, 2l };
		s.setAppType((short) 3);
		// s.setTest(a);
		int r = appInfoMapper.insertSelective(s);
		Assert.assertEquals(1, r);
	}
}
