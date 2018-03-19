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
			// 2. 将上述文件, 转换为properties对象
				InputStream is = DBCPUtil.class.getClassLoader().getResourceAsStream("dbcp.properties");
				//InputStream is = new FileInputStream("C:\\Users\\j\\Desktop\\dbcp.properties");
				Properties p = new Properties();
				try {
					p.load(is);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// 3. 通过连接池工厂 , 获取一个连接池对象
				try {
					source = BasicDataSourceFactory.createDataSource(p);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	}
	
	/**
	 * 获取基于连接池的连接对象, 
	 * @return  数据库连接对象
	 * @throws SQLException
	 */
	public static Connection getConnection() throws SQLException {
		return source.getConnection();
	}
	/**
	 * 用于释放资源的方法
	 * @param conn 要释放的连接对象
	 * @param state 要释放的执行环境对象
	 * @param result 要释放的结果集对象
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
