package cn.dao;

import cn.bean.User;

public interface UserDao {

	/**
	 * 用于管理员进行  普通学员的添加
	 * @param user  要添加的普通学员信息 , 包含了帐号, 密码, 昵称!
	 * 
	 * @return 返回添加学员的结果, 以及失败的原因 !<br>
	 * 		返回	1:	插入成功 !
	 * 			2:	插入失败 ,用户名重复
	 */
	int insert(User user);
	
	/**
	 * 用于管理员 进行 普通学员的删除
	 * @param username  要删除的用户的帐号
	 * @return 删除的结果 true表示删除成功!
	 */
	boolean delete(String username);
	/**
	 * 用于学员修改自身密码
	 * @param username	用户的帐号
	 * @param oldPassword	学员的旧密码
	 * @param newPassword   学员的新密码
	 * @return 修改的结果  true表示成功
	 */
	boolean updatePassword(String username,String oldPassword,String newPassword);
	
	/**
	 * 用于管理员修改学员的信息
	 * @param username	要修改的学员的帐号
	 * @param user	新的学员信息, 包含: 密码,昵称!<br>
	 * 	当密码为null时, 表示修改昵称!<br>
	 * 	当昵称为null时, 表示修改密码 !
	 * @return 修改的结果  true表示成功
	 */
	boolean update(String username,User user);
	/**
	 * 用于管理员查询学员信息
	 * @param username 要查询的用户帐号
	 * @return 查询到的用户对象,  或者 null , null表示查询失败 !
	 */
	User findUserByUsername(String username);
	/**
	 * 用于登录操作 (管理员或用户)
	 * @param username  要登陆的帐号
	 * @param password  要登陆的密码
	 * @return 登录的结果<br>
	 * 	1.	表示管理员登录成功<br>
	 * 	2.	表示学员登录成功<br>
	 * 	3.	表示登录失败 , 帐号或密码错误 !<br>
	 */
	int[] login(String username,String password);
}
