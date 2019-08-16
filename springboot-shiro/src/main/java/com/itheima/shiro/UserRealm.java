package com.itheima.shiro;

import java.util.Set;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import com.itheima.domain.User;
import com.itheima.service.UserService;
/**
 * 自定义Realm
 * @author ASUS-
 *
 */
public class UserRealm extends AuthorizingRealm{

	/**'
	 *执行授权逻辑
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
		// TODO Auto-generated method stub
		System.out.println("执行授权逻辑");
		
		//给资源进行授权
		SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();
		
		//添加资源的授权字符串
//		info.addStringPermission("user:add");   //现在将授权智能化 从数据库里面取
		
		//到数据库查询当前登录用户的授权字符串
		//获取当前登录用户
		Subject subject=SecurityUtils.getSubject();
		User user = (User) subject.getPrincipal();
		
		User dbUSer = userService.findById(user.getId());
		info.addStringPermission(dbUSer.getPerms());
		return info;
	}

	
	@Autowired
	private UserService userService;
	/**
	 * 执行认证逻辑
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken arg0) throws AuthenticationException {
		// TODO Auto-generated method stub
		System.out.println("执行认证逻辑");
		//假设数据库的用户名和密码
		String name="eric";
		String password="123456";
		
		//编写shiro判断逻辑,判断用户名和密码
		//1.判断用户名
		UsernamePasswordToken token = (UsernamePasswordToken)arg0;
		User user = userService.findByName(token.getUsername());
		if(user==null){
			//用户名不存在
			return null;//shiro底层会抛出UnknownAccountException
		}
		
		//2.判断密码
		
		return new SimpleAuthenticationInfo(user,user.getPassword(),""); //第一个参数需要一些数据 第二个数据库的密码 
	}

}
