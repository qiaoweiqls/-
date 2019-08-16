package com.itheima.comtroller;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {

	/**
	 * ���Է���
	 */
	
	@RequestMapping("/hello")
	@ResponseBody
	public String hello(){
		System.out.println("UserController.Hello()");
		return "OK";
	}
	
	@RequestMapping("/add")
	public String add(){
		System.out.println("UserController.Hello()");
		return "/user/add";
	}
	
	@RequestMapping("/update")
	public String update(){
		System.out.println("UserController.Hello()");
		return "/user/update";
	}
	
	@RequestMapping("/toLogin")
	public String toLogin(){
		return "/login";
	}
	
	@RequestMapping("/unAuth")
	public String unAuth(){
		return "/unAuth";
	}
	
	
	
	/**
	 * ����thymeleaf
	 * 
	 */
	@RequestMapping("/testThymeleaf")
	public String testthymeleaf(Model model){
		//�����ݴ���model
		model.addAttribute("name", "�������Ա");
		//����test.html
		return "test";
	}
	
	

	
	/**
	 * ��¼�߼�����
	 */
	
	@RequestMapping("/login")
	public String login(String name,String password,Model model){
		System.out.println( " name "+ name);
		/**
		 * ʹ��shiro��д��֤����
		 */
		//1.��ȡSubject
		Subject subject = SecurityUtils.getSubject();
		
		//2.��װ�û�����
		UsernamePasswordToken Token = new UsernamePasswordToken(name,password);
		
		try {
			//3.ִ�е�¼����
			subject.login(Token);  //������� ��ȥ UserRealm ��֤����
			
			//��¼�ɹ�
			//��ת��test.html
			//�ض��� 
			return "redirect:/testThymeleaf";
		} catch (UnknownAccountException e) {
			// TODO: handle exception
			// UnknownAccountException  ����쳣��¼ʧ�� : �û���������
		    model.addAttribute("msg","�û���������");
		    return "login"; //ֱ����תҳ��
		} catch (IncorrectCredentialsException e) {
			// TODO: handle exception
			// UnknownAccountException  ����쳣��¼ʧ�� : �û���������
		    model.addAttribute("msg","�������");
		    return "login";
		}
	}
}
