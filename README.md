spring3-jta-mybatis3
====================
datasource的配置

請修改MybatisDatabaseAConfig與MybatisDatabaseBConfig的DB連線設定
<pre>
  @Bean(name = "dataSourceA", initMethod = "init" , destroyMethod = "close")
	public AtomikosDataSourceBean dataSource() {
		AtomikosDataSourceBean atomikosDataSourceBean = new AtomikosDataSourceBean();
		atomikosDataSourceBean.setUniqueResourceName("DataSourceA");
		atomikosDataSourceBean.setXaDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlXADataSource");
		atomikosDataSourceBean.setPoolSize(10);
		Properties p = new Properties();
		p.setProperty("user","root");
		p.setProperty("password", "1234");
		p.setProperty("url", "jdbc:mysql://localhost:3306/test?useUnicode=true&amp;characterEncoding=utf-8");
		p.setProperty("pinGlobalTxToPhysicalConnection", "true");
		atomikosDataSourceBean.setXaProperties(p);
		return atomikosDataSourceBean;
	}
</pre>

  
====================
參考鏈結：<br>
http://my.oschina.net/baowenke/blog/99814<br>
