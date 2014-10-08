package com.gn.sub.config;


import javax.transaction.UserTransaction;

import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.icatch.jta.UserTransactionManager;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.jta.JtaTransactionManager;

@Configuration
@ComponentScan
@EnableTransactionManagement
public class JTAConfig {

	@Bean(name = "UserTransaction")
	public UserTransaction userTransaction() throws Throwable {
		UserTransactionImp userTransactionImp = new UserTransactionImp();
		userTransactionImp.setTransactionTimeout(10000);
		return userTransactionImp;
	}

	@Bean(name = "atomikosTransactionManager", initMethod = "init", destroyMethod = "close")
	public UserTransactionManager atomikosTransactionManager() throws Throwable {
		UserTransactionManager userTransactionManager = new UserTransactionManager();
		userTransactionManager.setTransactionTimeout(300);
		userTransactionManager.setForceShutdown(false);
		return userTransactionManager;
	}

	@Bean(name = "springTransactionManager")
	@DependsOn({ "UserTransaction", "atomikosTransactionManager" })
	public PlatformTransactionManager transactionManager() throws Throwable {
		UserTransaction userTransaction =  userTransaction();
		UserTransactionManager atomikosTransactionManager = atomikosTransactionManager();
		JtaTransactionManager jtaTransactionManager =  new JtaTransactionManager(userTransaction, atomikosTransactionManager);
		jtaTransactionManager.setAllowCustomIsolationLevels(true);
		return jtaTransactionManager;
	}
}
