package cn.test;
/**
 * ��Ԫ����
 */

import java.util.HashMap;

import org.junit.Test;

import cn.bean.Exam;
import cn.bean.User;
import cn.service.ExamService;
import cn.service.UserService;
import junit.framework.Assert;

public class Test1 {

	@Test
	public void test1() throws Exception {
		int count = UserService.insert(new User("haozheng", "123456", "С������", 0));
		System.out.println(count);
	}
	@Test
	public void test2() throws Exception {
		User user = new User();
		user.setPassword("654321");
		user.setNickname("88��ħ�Լ�ʦ");
		boolean f = UserService.update("zhangheng", user );
		System.out.println(f);
	}
	@Test
	public void test3() throws Exception {
		boolean flag = UserService.delete("zhangheng");
		Assert.assertEquals(true,flag);
	}
	
	
	@Test
	public void test4() throws Exception {
		boolean flag = UserService.updatePassword("zhangheng", "123456", "666666");
		System.out.println(flag);
	}
	@Test
	public void test5() throws Exception {
		User user = UserService.findUserByUsername("haozheng");
		System.out.println(user);
		
	}
	@Test
	public void test6() throws Exception {
		int[] i = UserService.login("admin", "123456");
		switch (i[0]) {
		case 1:
			System.out.println("��ӭ����Ա");
			break;
		case 2:
			System.out.println("��ӭѧԱ");
			break;
		case 3:
			System.out.println("��ϲ��, �ʺŻ������������");
			break;
		}
	}
	
	@Test
	public void test7(){
		HashMap<Integer,Exam> hs = ExamService.problems();
		System.out.println(hs);
	}
	
}
