package cn.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

import cn.bean.Exam;
import cn.bean.User;
import cn.bean.UserAndExam;
import cn.main.SocketThread;
import cn.util.DBCPUtil;

public class Oracle_ExamDaoImp implements ExamDao {
	/**
	 * 用于查询试题数量的SQL语句
	 */
	private static final String SQL_SELECT_COUNT_EXAM = "select count(*) from exam";
	
	/**
	 * 用于查询所有试题的SQL语句
	 */
	private static final String SQL_SELECT_EXAM = "select * from exam";
	
	/**
	 * 用于查询试题的SQL语句,包含了一个预编译参数:
	 * <br>1.	试题id
	 */
	private static final String SQL_ID_SELECT_EXAM = "select * from exam where id=?";
	
	
	/**
	 * 用于添加学生考试答案的SQL语句,包含了五个预编译参数:
	 * <br>1.	连接user17表的id
	 * <br>2.	连接exam表的id
	 * <br>3.	用户答案
	 * <br>4.	提交时间
	 * <br>5.	成绩
	 */
	private static final String SQL_INSERT_USER_AND_EXAM = "insert into user17_exam values(user17_exam_id_seq.nextval,?,?,?,?,?)";
	
	/**
	 * 用于查询成绩的SQL语句,包含了一个预编译参数:
	 * <br>1.	用户ID
	 */
	private static final String SQL_ID_SELECT_SOUCE = "select u.nickname nickname,ue.souce souce,ue.exam_time exam_time from(select user_id,exam_time,souce from user17_exam group by souce,exam_time,user_id having user_id = ?) ue,user17 u where u.id = ue.user_id";
	
	
	/**
	 * 用于添加试题的SQL语句,包含了五个预编译参数:
	 * <br>1.	问题
	 * <br>2.	选项A
	 * <br>3.	选项B
	 * <br>4.	选项C
	 * <br>5.	答案
	 */
	private static final String SQL_INSERT_EXAM = "insert into exam values(exam_id_seq.nextval,?,?,?,?,?,?)";
	
	/**
	 * 用于删除试题的SQL语句,包含了一个预编译参数:
	 * <br>1.	试题id
	 */
	private static final String SQL_DELETE_EXAM = "delete from exam where id=?";
	
	/**
	 * 用于修改试题题目的SQL语句,包含了两预编译参数:
	 * <br>1.	试题题目
	 * <br>2.	试题id
	 */
	private static final String SQL_UPDATE_PROBLEMS = "update exam set problems=? where id=?";
	
	/**
	 * 用于修改试题选项A的SQL语句,包含了两预编译参数:
	 * <br>1.	试题选项A
	 * <br>2.	试题id
	 */
	private static final String SQL_UPDATE_OPTION_A = "update exam set OptionA=? where id=?";
	
	/**
	 * 用于修改试题选项B的SQL语句,包含了两预编译参数:
	 * <br>1.	试题选项B
	 * <br>2.	试题id
	 */
	private static final String SQL_UPDATE_OPTION_B = "update exam set OptionB=? where id=?";
	
	/**
	 * 用于修改试题选项C的SQL语句,包含了两预编译参数:
	 * <br>1.	试题选项C
	 * <br>2.	试题id
	 */
	private static final String SQL_UPDATE_OPTION_C = "update exam set OptionC=? where id=?";
	
	/**
	 * 用于修改试题选项D的SQL语句,包含了两预编译参数:
	 * <br>1.	试题选项D
	 * <br>2.	试题id
	 */
	private static final String SQL_UPDATE_OPTION_D = "update exam set OptionD=? where id=?";
	
	/**
	 * 用于修改试题答案的SQL语句,包含了两预编译参数:
	 * <br>1.	试题答案
	 * <br>2.	试题id
	 */
	private static final String SQL_UPDATE_ANSWER = "update exam set answer=? where id=?";
	
	/**
	 * 学员获取试题
	 */
	@Override
	public HashMap<Integer, Exam> problems() {
		Connection conn = null;
		ResultSet result = null;
		PreparedStatement ps = null;
		Random ran = new Random();
		HashMap<Integer, Exam> hashMap = new HashMap<>();
		try {
			conn = DBCPUtil.getConnection();
			ps = conn.prepareStatement(SQL_SELECT_COUNT_EXAM);
			result = ps.executeQuery();
			int num = 0;
			while(result.next()){
				num = result.getInt(1);
			}
			int[] arr = new int[num];//存放所有id的数组
			int[] brr = new int[10];//存放arr的下标
			ps = conn.prepareStatement(SQL_SELECT_EXAM);
			result = ps.executeQuery();
			int sum = 0;
			while(result.next()){
				arr[sum] = result.getInt("id");
				sum++;
			}
			for(int i = 0 ; i<brr.length;i++){
				brr[i] = ran.nextInt(num);
				for(int j = 0; j<i;j++){
					if(brr[i] == brr[j]){
						i--;
					}
				}
			}
			for(int i = 0 ;i<brr.length;i++){
				ps = conn.prepareStatement(SQL_ID_SELECT_EXAM);
				//填充预编译参数
				ps.setInt(1, arr[brr[i]]);
				result = ps.executeQuery();
				while(result.next()){
//					private int id; // 主键约束 编号
//					private String problems; // 问题
//					private String optionA; // 选项A
//					private String optionB; // 选项B
//					private String optionC; // 选项C
//					private String optionD; // 选项D
//					private String answer; // 答案
					int id = result.getInt("id");
					String problems = result.getString("problems");
					String optionA = result.getString("optionA");
					String optionB = result.getString("optionB");
					String optionC = result.getString("optionC");
					String optionD = result.getString("optionD");
					hashMap.put(i, new Exam(id,problems,optionA,optionB,optionC,optionD));
				}
			}
			return hashMap;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				DBCPUtil.close(conn, ps, result);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 考试结果判定
	 */
	@Override
	public int judge(HashMap<Integer, Exam> hashMap) {
		Connection conn = null;
		ResultSet result = null;
		PreparedStatement state = null;
		try {
			conn = DBCPUtil.getConnection();
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String getTime = sdf.format(date.getTime());
			int num=0;
			//获取用户ID
			Exam id = hashMap.get(-1);
			int user_id = id.getId();
			for(int i = 0 ; i < 10 ;i++){
				Exam e =hashMap.get(i);
				state = conn.prepareStatement(SQL_ID_SELECT_EXAM);
				state.setInt(1, e.getId());
				result = state.executeQuery();
				while(result.next()){
					if(e.getAnswer().equals(result.getString("answer"))){
						num+=10;
					}
				}
			}
			for(int i = 0 ; i < 10 ;i++){
				Exam e =hashMap.get(i);
				state = conn.prepareStatement(SQL_INSERT_USER_AND_EXAM);
				//用户ID
				state.setInt(1,user_id);
				//题目ID
				state.setInt(2, e.getId());
				state.setString(3, e.getAnswer());
				state.setString(4, getTime);
				state.setInt(5, num);
				state.executeUpdate();
			}
			return num;
		} catch (SQLException e1) {
			e1.printStackTrace();
		}finally{
			try {
				DBCPUtil.close(conn, state, result);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return 0;
	}

	/**
	 * 查询所有成绩
	 */
	@Override
	public HashMap<Integer,UserAndExam> queryScore(int id) {
		Connection conn = null;
		ResultSet result = null;
		PreparedStatement state = null;
		try {
			conn = DBCPUtil.getConnection();
			state = conn.prepareStatement(SQL_ID_SELECT_SOUCE);
			//用户ID
			state.setInt(1, id);
			result = state.executeQuery();
			HashMap<Integer,UserAndExam> hashMap = new HashMap<>();
			int i =0;
			while(result.next()){
				String nickname = result.getString("nickname");
				String examTime = result.getString("exam_time");
				int souce = result.getInt("souce");
				hashMap.put(i, new UserAndExam(nickname,examTime,souce));
				i++;
			}
			return hashMap;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				DBCPUtil.close(conn, state, result);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 添加试题
	 */
	@Override
	public int insert(Exam exam) {
		Connection conn = null;
		ResultSet result = null;
		PreparedStatement state = null;
		try {
			conn = DBCPUtil.getConnection();
			state = conn.prepareStatement(SQL_INSERT_EXAM);
			//填充预编译的参数
			state.setString(1, exam.getProblems());
			state.setString(2, exam.getOptionA());
			state.setString(3, exam.getOptionB());
			state.setString(4, exam.getOptionC());
			state.setString(5, exam.getOptionD());
			state.setString(6, exam.getAnswer());
			int rowCount = state.executeUpdate();
			if(rowCount==1) {
				return 1;
			}
		} catch (SQLException e) {
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
	 * 删除试题
	 */
	@Override
	public boolean delete(int id) {
		Connection conn = null;
		ResultSet result = null;
		PreparedStatement state = null;
		try {
			conn = DBCPUtil.getConnection();
			state = conn.prepareStatement(SQL_DELETE_EXAM);
			//填充预编译的参数
			state.setInt(1, id);
			int rowCount = state.executeUpdate();
			if(rowCount==1) {
				return true;
			}
		} catch (SQLException e) {
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
	 * 修改试题
	 */
	@Override
	public boolean update(int id,Exam exam) {
		Connection conn = null;
		ResultSet result = null;
		PreparedStatement state = null;
		try {
			conn = DBCPUtil.getConnection();
			int flag1 = 0;
			int flag2 = 0;
			int flag3 = 0;
			int flag4 = 0;
			int flag5 = 0;
			int flag6 = 0;
			if(exam.getProblems() != null) {
				state = conn.prepareStatement(SQL_UPDATE_PROBLEMS);
				//填充预编译的参数
				state.setString(1,exam.getProblems());
				state.setInt(2, id);
				flag1 = state.executeUpdate();
			}
			
			if(exam.getOptionA() != null) {
				state = conn.prepareStatement(SQL_UPDATE_OPTION_A);
				//填充预编译的参数
				state.setString(1,exam.getOptionA());
				state.setInt(2, id);
				flag2 = state.executeUpdate();
			}
			
			if(exam.getOptionB() != null) {
				state = conn.prepareStatement(SQL_UPDATE_OPTION_B);
				//填充预编译的参数
				state.setString(1,exam.getOptionB());
				state.setInt(2, id);
				flag3 = state.executeUpdate();
			}
			
			if(exam.getOptionC() != null) {
				state = conn.prepareStatement(SQL_UPDATE_OPTION_C);
				//填充预编译的参数
				state.setString(1,exam.getOptionC());
				state.setInt(2, id);
				flag4 = state.executeUpdate();
			}
			
			if(exam.getOptionD() != null) {
				state = conn.prepareStatement(SQL_UPDATE_OPTION_D);
				//填充预编译的参数
				state.setString(1,exam.getOptionD());
				state.setInt(2, id);
				flag5 = state.executeUpdate();
			}
			
			if(exam.getAnswer() != null) {
				state = conn.prepareStatement(SQL_UPDATE_ANSWER);
				//填充预编译的参数
				state.setString(1,exam.getAnswer());
				state.setInt(2, id);
				flag6 = state.executeUpdate();
			}
			if(flag1==1 || flag2==1 || flag3==1 || flag4==1 || flag5==1 || flag6==1) {
				return true;
			}
		} catch (SQLException e) {
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
	 * 查询考试成绩
	 */
	@Override
	public HashMap<Integer, Exam> findIdByExam(int id) {
		Connection conn = null ;
		ResultSet result = null;
		PreparedStatement state = null;
		try {
			conn = DBCPUtil.getConnection();
			HashMap<Integer, Exam> hs = new HashMap<>();
			if( id == 0){
				state = conn.prepareStatement(SQL_SELECT_EXAM);
				result = state.executeQuery();
			}else{
				state = conn.prepareStatement(SQL_ID_SELECT_EXAM);
				//填充预编译参数 
				state.setInt(1, id);
				result = state.executeQuery();
			}
			int i = 0;
			while(result.next()){
				hs.put(i, new Exam(result.getInt("id"),result.getString("problems"),result.getString("optionA"),result.getString("optionB"),result.getString("optionC"),result.getString("optionD"),result.getString("answer")));
				i++;
			}
			return hs;
		} catch (SQLException e) {
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

}
