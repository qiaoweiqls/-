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
 * �Զ���Realm
 * @author ASUS-
 *
 */
public class UserRealm extends AuthorizingRealm{

	/**'
	 *ִ����Ȩ�߼�
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
		// TODO Auto-generated method stub
		System.out.println("ִ����Ȩ�߼�");
		
		//����Դ������Ȩ
		SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();
		
		//�����Դ����Ȩ�ַ���
//		info.addStringPermission("user:add");   //���ڽ���Ȩ���ܻ� �����ݿ�����ȡ
		
		//�����ݿ��ѯ��ǰ��¼�û�����Ȩ�ַ���
		//��ȡ��ǰ��¼�û�
		Subject subject=SecurityUtils.getSubject();
		User user = (User) subject.getPrincipal();
		
		User dbUSer = userService.findById(user.getId());
		info.addStringPermission(dbUSer.getPerms());
		return info;
	}

	
	@Autowired
	private UserService userService;
	/**
	 * ִ����֤�߼�
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken arg0) throws AuthenticationException {
		// TODO Auto-generated method stub
		System.out.println("ִ����֤�߼�");
		//�������ݿ���û���������
		String name="eric";
		String password="123456";
		
		//��дshiro�ж��߼�,�ж��û���������
		//1.�ж��û���
		UsernamePasswordToken token = (UsernamePasswordToken)arg0;
		User user = userService.findByName(token.getUsername());
		if(user==null){
			//�û���������
			return null;//shiro�ײ���׳�UnknownAccountException
		}
		
		//2.�ж�����
		
		return new SimpleAuthenticationInfo(user,user.getPassword(),""); //��һ��������ҪһЩ���� �ڶ������ݿ������ 
	}

}
