package com.gn.web.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Scope("prototype")
public class TestService {
	
	@Autowired
	@Qualifier("sqlSessionFactoryA")
	private SqlSessionFactory sqlSessionFactoryA;
	@Autowired
	@Qualifier("sqlSessionFactoryB")
	private SqlSessionFactory sqlSessionFactoryB;
	
	public void test() throws Exception {
		SqlSession sqlSessionA = sqlSessionFactoryA.openSession();
		//SqlSession sqlSessionB = sqlSessionFactoryB.openSession();
		Map tmpMap = new HashMap();
		tmpMap.put("title", "a");
		sqlSessionA.insert("Test_dao.insertTest", tmpMap);
		sqlSessionA.close();
		sqlSessionA = sqlSessionFactoryA.openSession();
		tmpMap = new HashMap();
		tmpMap.put("title", "b");
		sqlSessionA.insert("Test_dao.insertTest", tmpMap);
		sqlSessionA.close();
		//throw new Exception("Test");
	}
	public void testQuery() throws Exception {
		SqlSession sqlSessionA = sqlSessionFactoryA.openSession();
		SqlSession sqlSessionB = sqlSessionFactoryB.openSession();
		List dataList = sqlSessionA.selectList("Test_dao.queryTask");
		List dataList1 = sqlSessionB.selectList("Test_dao.queryUsers");
		System.out.println(dataList.size());
		System.out.println(dataList1.size());
		sqlSessionA.close();
		sqlSessionB.close();
		
	}
	
}
