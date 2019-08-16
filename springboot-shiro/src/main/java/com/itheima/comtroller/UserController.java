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
	 * 测试方法
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
	 * 调试thymeleaf
	 * 
	 */
	@RequestMapping("/testThymeleaf")
	public String testthymeleaf(Model model){
		//把数据存入model
		model.addAttribute("name", "黑马程序员");
		//返回test.html
		return "test";
	}
	
	

	
	/**
	 * 登录逻辑处理
	 */
	
	@RequestMapping("/login")
	public String login(String name,String password,Model model){
		System.out.println( " name "+ name);
		/**
		 * 使用shiro编写认证操作
		 */
		//1.获取Subject
		Subject subject = SecurityUtils.getSubject();
		
		//2.封装用户数据
		UsernamePasswordToken Token = new UsernamePasswordToken(name,password);
		
		try {
			//3.执行登录方法
			subject.login(Token);  //这个方法 会去 UserRealm 认证操作
			
			//登录成功
			//跳转到test.html
			//重定向 
			return "redirect:/testThymeleaf";
		} catch (UnknownAccountException e) {
			// TODO: handle exception
			// UnknownAccountException  这个异常登录失败 : 用户名不存在
		    model.addAttribute("msg","用户名不存在");
		    return "login"; //直接跳转页面
		} catch (IncorrectCredentialsException e) {
			// TODO: handle exception
			// UnknownAccountException  这个异常登录失败 : 用户名不存在
		    model.addAttribute("msg","密码错误");
		    return "login";
		}
	}
}
