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
 * shiro��������
 * @author ASUS-
 *
 */
@Configuration
public class ShiroConfig {
	
	/**
	 * ����ShiroFilterFactotyBean
	 */
	@Bean
	public ShiroFilterFactoryBean  getShiroFilterFactotyBean(@Qualifier("securityManager")DefaultWebSecurityManager securityManager){
		ShiroFilterFactoryBean shiroFilterFactoryBean=new ShiroFilterFactoryBean();
		
	 	//���ð�ȫ������
		shiroFilterFactoryBean.setSecurityManager(securityManager);
		
		//����Shiro���ù����� ����ʵ��Ȩ����ص�������
		/**
		 * Shiro���ù�����,����ʵ��Ȩ����ص�������
		 *    ���õĹ�����:
		 *       anon:������֤(��¼)���Է���  
		 *       authc:������֤�ſ��Է���
		 *       user:���ʹ��rememberMe�Ĺ��ܿ���ֱ�ӷ���
		 *       perms:����Դ����ĵ���ԴȨ�޲ſ��Է���
		 *       role:����Դ����õ���ɫȨ�޲ſ��Է���
		 */
		Map<String ,String > filterMap=new LinkedHashMap<String, String>();
		/*filterMap.put("/add", "authc");
		filterMap.put("/update", "authc");*/
		/**
		 * ���е���Դ�������� 
		 * ��Ȩ�����м�
		 * ��֤���ܷ��ʵ���Դ�����
		 */
	//  ��������Ҳ�������� ������� ������֤  ���������������
		filterMap.put("/testThymeleaf","anon");
		//����
		filterMap.put("/login","anon");
		
		
		//��Ȩ������  
		//ע��:��ǰ��Ȩ���غ�  shiro ���Զ���ս��һ��δ��Ȩ��ҳ��
		filterMap.put("/add", "perms[user:add]");
		filterMap.put("/update", "perms[user:update]");
		
		
		//����������Դ
		filterMap.put("/*","authc");
		
	
		
		//�޸���ת�ĵ�¼ҳ��   ���û�е�¼ �ͻ���ת����¼ҳ��
		shiroFilterFactoryBean.setLoginUrl("/toLogin");
		
		//����Ϊ��Ȩ����ʾҳ��
		shiroFilterFactoryBean.setUnauthorizedUrl("/unAuth");
		
		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
		
		
		return shiroFilterFactoryBean;
	}
	/**
	 * ����DefaultWebSecurityManager
	 */
	@Bean(name="securityManager")
     public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm")UserRealm userRealm){
    	 DefaultWebSecurityManager securityManager=new DefaultWebSecurityManager();
         //����realm
    	 securityManager.setRealm(userRealm);
		return securityManager;     
     }
	/**
	 * ����Realm
	 * 
	 */
	@Bean(name="userRealm")
	public UserRealm getRealm(){
		return new UserRealm();
	}
	
	
	
	
	/**
	 * ����ShiroDialect,����thymeleaf��shiro��ǩ���ʹ��
	 */
	@Bean
	public ShiroDialect getShiroDialect(){
		return new ShiroDialect();
	}
}
