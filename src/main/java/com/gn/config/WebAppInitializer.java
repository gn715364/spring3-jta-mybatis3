package com.gn.config;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;



public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
   
	private final static String INITIALIZER_PACKAGE_SCAN_PATH = "com.gn.sub.config";
	private final static String INITIALIZER_MAPPING = "/";
	public static final String SERVLET_NAME = "dispatcher";
	
	@Override
	protected Class<?>[] getRootConfigClasses() {
		//return new Class<?>[] { RootConfig.class };
		return null;
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		
	    PathMatchingResourcePatternResolver scanner = new PathMatchingResourcePatternResolver();
	    Resource[] resources = null;
		try {
			resources = scanner.getResources("classpath*:" + INITIALIZER_PACKAGE_SCAN_PATH.replace(".", "/") + "/*");
		} catch (IOException e) {
			e.printStackTrace();
		}
		Class<?>[] c  = new Class<?>[resources.length];
	    for (int i=0;i<c.length;i++) {
			try {
				c[i] = Class.forName(INITIALIZER_PACKAGE_SCAN_PATH + "." + resources[i].getFilename().substring(0,resources[i].getFilename().indexOf(".")));
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
	    }
		return c;
	}
	
	@Override
	protected String[] getServletMappings() {
		return new String[] { INITIALIZER_MAPPING };
	}

	@Override
	protected Filter[] getServletFilters() {
		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
		characterEncodingFilter.setEncoding("UTF-8");
		characterEncodingFilter.setForceEncoding(true);
		return new Filter[] { characterEncodingFilter };
	}
	
	@Override
    public void onStartup(ServletContext servletContext) {
		AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
		//ctx.register(getRootConfigClasses());
		ctx.register(getServletConfigClasses());
        ctx.setServletContext(servletContext);
        ServletRegistration.Dynamic servlet = servletContext.addServlet(SERVLET_NAME, new DispatcherServlet(ctx));
        servlet.setAsyncSupported(true);
//        servletContext.setInitParameter("log4jConfigLocation","classpath:com/configure/conf/log4j.properties");
//        servletContext.addListener(new Log4jConfigListener());
//        servletContext.addListener(new ContextLoaderListener(ctx));
//        servletContext.addFilter("CharacterEncodingFilter", getServletFilters()[0]);
        servlet.addMapping(getServletMappings());
        servlet.setLoadOnStartup(1);
	}
	
    
}
