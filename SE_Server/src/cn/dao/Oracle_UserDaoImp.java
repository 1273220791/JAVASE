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
	 * ���ڽ����û���ӵ�SQL���, ����������Ԥ����Ĳ���:
	 * <br>1.	�û��ʺ�
	 * <br>2.	�û�����
	 * <br>3.	�û��ǳ�
	 */
	private static final String SQL_INSERT_USER = "insert into user17 values(user17_seq.nextval,?,?,?,0)";
	
	/**
	 * ���ڽ���ɾ���û���SQL���, ������1��Ԥ����Ĳ���:
	 * <br>1.	Ҫɾ�����û��ʺ�
	 */
	private static final String SQL_DELETE_USER = "delete from user17 where username=?";

	/**
	 * ���ڽ����û��޸������SQL���, ����������Ԥ����Ĳ���:
	 * <br>1.	�µ�����
	 * <br>2.	�û��ʺ�
	 * <br>3.	�ɵ�����
	 */
	private static final String SQL_UPDATE_PASSWORD = "update user17 set password=? where username=? and password=?";

	/**
	 * ���ڹ���Ա�޸��û������SQL���, ������2��Ԥ����Ĳ���:
	 * <br>1.	�µ�����
	 * <br>2.	�û��ʺ�
	 */
	private static final String SQL_UPDATE_PASSWORD_MANAGER = "update user17 set password=? where username=?";
	/**
	 * ���ڹ���Ա�޸��û��ǳƵ�SQL���, ������2��Ԥ����Ĳ���:
	 * <br>1.	�µ��ǳ�
	 * <br>2.	�û��ʺ�
	 */
	private static final String SQL_UPDATE_NICKNAME = "update user17 set nickname=? where username=?";
	/**
	 * ���ڹ���Ա��ѯ�����û�, ������1��Ԥ����Ĳ���:
	 * <br>1.	�û��ʺ�
	 */
	private static final String SQL_FIND_USER_BY_USERNAME = "select * from user17 where username=?";
	/**
	 * �����û���¼, ������2��Ԥ����Ĳ���:
	 * <br>1.	�û��ʺ�
	 * <br>2.	�û�����
	 */
	private static final String SQL_USER_LOGIN = "select * from user17 where username=? and password=?";

	/**
	 * �������
	 */
	@Override
	public int insert(User user) {
		Connection conn = null;
		ResultSet result = null;
		PreparedStatement state = null;
		try {
			conn = DBCPUtil.getConnection();
			state = conn.prepareStatement(SQL_INSERT_USER);
			//���Ԥ����Ĳ���
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
	 * ɾ���û���Ϣ
	 */
	@Override
	public boolean delete(String username) {
		Connection conn = null;
		ResultSet result = null;
		PreparedStatement state = null;
		try {
			conn = DBCPUtil.getConnection();
			state = conn.prepareStatement(SQL_DELETE_USER);
			//���Ԥ����Ĳ���
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
	 * �û��޸�����
	 */
	@Override
	public boolean updatePassword(String username, String oldPassword, String newPassword) {
		Connection conn = null;
		ResultSet result = null;
		PreparedStatement state = null;
		try {
			conn = DBCPUtil.getConnection();
			state = conn.prepareStatement(SQL_UPDATE_PASSWORD);
			//���Ԥ����Ĳ���
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
	 * ����Ա�޸��û���Ϣ
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
				//���Ԥ����Ĳ���
				state.setString(1, user.getPassword());
				state.setString(2, username);
				flag1 = state.executeUpdate();
			}
			
			if(user.getNickname()!=null) {
				state = conn.prepareStatement(SQL_UPDATE_NICKNAME);
				//���Ԥ����Ĳ���
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
	 * ��ѯ�û���Ϣ
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
			//�ж��Ƿ��ѯ�����û�
			if(result.next()) {
				//��ȡ��ѯ�����û���Ϣ
				int id = result.getInt("id");
				String password = result.getString("password");
				String nickname = result.getString("nickname");
				int isManager = result.getInt("isManager");
				//����ѯ������Ϣ ��װΪ�û�����, ������
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
	 * ��¼
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
			//��¼���Ľ����
			result = state.executeQuery();
			//�ж��Ƿ��ѯ�����û�
			if(result.next()) {
				//��ȡ��ѯ�����û���Ϣ
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
