package com.itheima.shiro;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;

/**
 * shiro的配置类
 * @author ASUS-
 *
 */
@Configuration
public class ShiroConfig {
	
	/**
	 * 创建ShiroFilterFactotyBean
	 */
	@Bean
	public ShiroFilterFactoryBean  getShiroFilterFactotyBean(@Qualifier("securityManager")DefaultWebSecurityManager securityManager){
		ShiroFilterFactoryBean shiroFilterFactoryBean=new ShiroFilterFactoryBean();
		
	 	//设置安全管理器
		shiroFilterFactoryBean.setSecurityManager(securityManager);
		
		//设置Shiro内置过滤器 可以实现权限相关的拦截器
		/**
		 * Shiro内置过滤器,可以实现权限相关的拦截器
		 *    常用的过滤器:
		 *       anon:无需认证(登录)可以访问  
		 *       authc:必须认证才可以访问
		 *       user:如果使用rememberMe的功能可以直接访问
		 *       perms:该资源必须的到资源权限才可以访问
		 *       role:该资源必须得到角色权限才可以访问
		 */
		Map<String ,String > filterMap=new LinkedHashMap<String, String>();
		/*filterMap.put("/add", "authc");
		filterMap.put("/update", "authc");*/
		/**
		 * 放行的资源在最上面 
		 * 授权的在中间
		 * 认证才能访问的资源在最后
		 */
	//  下面把这个也给拦截了 这个放行 无需认证  必须放在拦截上面
		filterMap.put("/testThymeleaf","anon");
		//放行
		filterMap.put("/login","anon");
		
		
		//授权过滤器  
		//注意:当前授权拦截后  shiro 会自动挑战到一个未授权的页面
		filterMap.put("/add", "perms[user:add]");
		filterMap.put("/update", "perms[user:update]");
		
		
		//拦截所有资源
		filterMap.put("/*","authc");
		
	
		
		//修改跳转的登录页面   如果没有登录 就会跳转到登录页面
		shiroFilterFactoryBean.setLoginUrl("/toLogin");
		
		//设置为授权的提示页面
		shiroFilterFactoryBean.setUnauthorizedUrl("/unAuth");
		
		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
		
		
		return shiroFilterFactoryBean;
	}
	/**
	 * 创建DefaultWebSecurityManager
	 */
	@Bean(name="securityManager")
     public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm")UserRealm userRealm){
    	 DefaultWebSecurityManager securityManager=new DefaultWebSecurityManager();
         //关联realm
    	 securityManager.setRealm(userRealm);
		return securityManager;     
     }
	/**
	 * 创建Realm
	 * 
	 */
	@Bean(name="userRealm")
	public UserRealm getRealm(){
		return new UserRealm();
	}
	
	
	
	
	/**
	 * 配置ShiroDialect,用于thymeleaf和shiro标签配合使用
	 */
	@Bean
	public ShiroDialect getShiroDialect(){
		return new ShiroDialect();
	}
}
