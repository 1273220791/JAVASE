package cn.service;

import cn.bean.User;
import cn.dao.Oracle_UserDaoImp;
import cn.dao.UserDao;

public class UserService {

	private static UserDao dao;
	static {
		dao = new Oracle_UserDaoImp();
	}
	
	public static int insert(User user) {
		return dao.insert(user);
	}
	public static boolean delete(String username) {
		return dao.delete(username);
	}
	public static boolean updatePassword(String username,String oldPassword,String newPassword) {
		return dao.updatePassword(username, oldPassword, newPassword);
	}
	public static boolean update(String username,User user) {
		return dao.update(username, user);
	}
	public static User findUserByUsername(String username) {
		return dao.findUserByUsername(username);
	}
	public static int[] login(String username,String password) {
		return dao.login(username, password);
	}	
}
