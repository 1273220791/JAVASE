package cn.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import cn.bean.User;
import cn.util.DBCPUtil;

public class Oracle_UserDaoImp implements UserDao {
	/**
	 * 用于进行用户添加的SQL语句, 包含了三个预编译的参数:
	 * <br>1.	用户帐号
	 * <br>2.	用户密码
	 * <br>3.	用户昵称
	 */
	private static final String SQL_INSERT_USER = "insert into user17 values(user17_seq.nextval,?,?,?,0)";
	
	/**
	 * 用于进行删除用户的SQL语句, 包含了1个预编译的参数:
	 * <br>1.	要删除的用户帐号
	 */
	private static final String SQL_DELETE_USER = "delete from user17 where username=?";

	/**
	 * 用于进行用户修改密码的SQL语句, 包含了三个预编译的参数:
	 * <br>1.	新的密码
	 * <br>2.	用户帐号
	 * <br>3.	旧的密码
	 */
	private static final String SQL_UPDATE_PASSWORD = "update user17 set password=? where username=? and password=?";

	/**
	 * 用于管理员修改用户密码的SQL语句, 包含了2个预编译的参数:
	 * <br>1.	新的密码
	 * <br>2.	用户帐号
	 */
	private static final String SQL_UPDATE_PASSWORD_MANAGER = "update user17 set password=? where username=?";
	/**
	 * 用于管理员修改用户昵称的SQL语句, 包含了2个预编译的参数:
	 * <br>1.	新的昵称
	 * <br>2.	用户帐号
	 */
	private static final String SQL_UPDATE_NICKNAME = "update user17 set nickname=? where username=?";
	/**
	 * 用于管理员查询单个用户, 包含了1个预编译的参数:
	 * <br>1.	用户帐号
	 */
	private static final String SQL_FIND_USER_BY_USERNAME = "select * from user17 where username=?";
	/**
	 * 用于用户登录, 包含了2个预编译的参数:
	 * <br>1.	用户帐号
	 * <br>2.	用户密码
	 */
	private static final String SQL_USER_LOGIN = "select * from user17 where username=? and password=?";

	/**
	 * 添加密码
	 */
	@Override
	public int insert(User user) {
		Connection conn = null;
		ResultSet result = null;
		PreparedStatement state = null;
		try {
			conn = DBCPUtil.getConnection();
			state = conn.prepareStatement(SQL_INSERT_USER);
			//填充预编译的参数
			state.setString(1, user.getUsername());
			state.setString(2, user.getPassword());
			state.setString(3, user.getNickname());
			int rowCount = state.executeUpdate();
			if(rowCount==1) {
				return 1;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				DBCPUtil.close(conn, state, result);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return 2;
	}

	/**
	 * 删除用户信息
	 */
	@Override
	public boolean delete(String username) {
		Connection conn = null;
		ResultSet result = null;
		PreparedStatement state = null;
		try {
			conn = DBCPUtil.getConnection();
			state = conn.prepareStatement(SQL_DELETE_USER);
			//填充预编译的参数
			state.setString(1, username);
			int rowCount = state.executeUpdate();
			if(rowCount==1) {
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				DBCPUtil.close(conn, state, result);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	
	/**
	 * 用户修改密码
	 */
	@Override
	public boolean updatePassword(String username, String oldPassword, String newPassword) {
		Connection conn = null;
		ResultSet result = null;
		PreparedStatement state = null;
		try {
			conn = DBCPUtil.getConnection();
			state = conn.prepareStatement(SQL_UPDATE_PASSWORD);
			//填充预编译的参数
			state.setString(1, newPassword);
			state.setString(2, username);
			state.setString(3, oldPassword);
			int rowCount = state.executeUpdate();
			if(rowCount==1) {
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				DBCPUtil.close(conn, state, result);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	/**
	 * 管理员修改用户信息
	 */
	@Override
	public boolean update(String username, User user) {
		Connection conn = null;
		ResultSet result = null;
		PreparedStatement state = null;
		try {
			conn = DBCPUtil.getConnection();
			int flag1 = 0;
			int flag2 = 0;
			if(user.getPassword()!=null) {
				state = conn.prepareStatement(SQL_UPDATE_PASSWORD_MANAGER);
				//填充预编译的参数
				state.setString(1, user.getPassword());
				state.setString(2, username);
				flag1 = state.executeUpdate();
			}
			
			if(user.getNickname()!=null) {
				state = conn.prepareStatement(SQL_UPDATE_NICKNAME);
				//填充预编译的参数
				state.setString(1, user.getNickname());
				state.setString(2, username);
				flag2 = state.executeUpdate();
			}
			if(flag1==1 || flag2==1) {
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				DBCPUtil.close(conn, state, result);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	/**
	 * 查询用户信息
	 */
	@Override
	public User findUserByUsername(String username) {
		Connection conn = null ;
		ResultSet result = null;
		PreparedStatement state = null;
		try {
			conn = DBCPUtil.getConnection();
			state = conn.prepareStatement(SQL_FIND_USER_BY_USERNAME);
			state.setString(1, username);
			result = state.executeQuery();
			//判断是否查询到了用户
			if(result.next()) {
				//获取查询到的用户信息
				int id = result.getInt("id");
				String password = result.getString("password");
				String nickname = result.getString("nickname");
				int isManager = result.getInt("isManager");
				//将查询到的信息 组装为用户对象, 并返回
				return new User(id, username, password, nickname, isManager);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				DBCPUtil.close(conn, state, result);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * 登录
	 */
	@Override
	public int[] login(String username, String password) {
		Connection conn = null ;
		ResultSet result = null;
		PreparedStatement state = null;
		int[] isManager = new int[2];
		try {
			conn = DBCPUtil.getConnection();
			state = conn.prepareStatement(SQL_USER_LOGIN);
			state.setString(1, username);
			state.setString(2, password);
			//登录语句的结果集
			result = state.executeQuery();
			//判断是否查询到了用户
			if(result.next()) {
				//获取查询到的用户信息
				isManager[0] = result.getInt("isManager");
				isManager[1] = result.getInt("id");
				if(isManager[0] == 1){
					return isManager;
				}
				isManager[0] = 2;
				return isManager;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				DBCPUtil.close(conn, state, result);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		isManager[0] = 3;
		return isManager ;
	}
}
