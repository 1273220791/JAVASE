package cn.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSourceFactory;


public class DBCPUtil {
		
	private static DataSource source;
	
	static {
			// 2. �������ļ�, ת��Ϊproperties����
				InputStream is = DBCPUtil.class.getClassLoader().getResourceAsStream("dbcp.properties");
				//InputStream is = new FileInputStream("C:\\Users\\j\\Desktop\\dbcp.properties");
				Properties p = new Properties();
				try {
					p.load(is);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// 3. ͨ�����ӳع��� , ��ȡһ�����ӳض���
				try {
					source = BasicDataSourceFactory.createDataSource(p);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	}
	
	/**
	 * ��ȡ�������ӳص����Ӷ���, 
	 * @return  ���ݿ����Ӷ���
	 * @throws SQLException
	 */
	public static Connection getConnection() throws SQLException {
		return source.getConnection();
	}
	/**
	 * �����ͷ���Դ�ķ���
	 * @param conn Ҫ�ͷŵ����Ӷ���
	 * @param state Ҫ�ͷŵ�ִ�л�������
	 * @param result Ҫ�ͷŵĽ��������
	 * @throws SQLException
	 */
	public static void close(Connection conn,Statement state,ResultSet result) throws SQLException {
		if(result!=null)
			result.close();
		
		if(state!=null)
			state.close();
		
		if(conn!=null)
			conn.close();
	}
}
