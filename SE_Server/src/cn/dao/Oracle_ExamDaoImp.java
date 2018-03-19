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
	 * ���ڲ�ѯ����������SQL���
	 */
	private static final String SQL_SELECT_COUNT_EXAM = "select count(*) from exam";
	
	/**
	 * ���ڲ�ѯ���������SQL���
	 */
	private static final String SQL_SELECT_EXAM = "select * from exam";
	
	/**
	 * ���ڲ�ѯ�����SQL���,������һ��Ԥ�������:
	 * <br>1.	����id
	 */
	private static final String SQL_ID_SELECT_EXAM = "select * from exam where id=?";
	
	
	/**
	 * �������ѧ�����Դ𰸵�SQL���,���������Ԥ�������:
	 * <br>1.	����user17���id
	 * <br>2.	����exam���id
	 * <br>3.	�û���
	 * <br>4.	�ύʱ��
	 * <br>5.	�ɼ�
	 */
	private static final String SQL_INSERT_USER_AND_EXAM = "insert into user17_exam values(user17_exam_id_seq.nextval,?,?,?,?,?)";
	
	/**
	 * ���ڲ�ѯ�ɼ���SQL���,������һ��Ԥ�������:
	 * <br>1.	�û�ID
	 */
	private static final String SQL_ID_SELECT_SOUCE = "select u.nickname nickname,ue.souce souce,ue.exam_time exam_time from(select user_id,exam_time,souce from user17_exam group by souce,exam_time,user_id having user_id = ?) ue,user17 u where u.id = ue.user_id";
	
	
	/**
	 * ������������SQL���,���������Ԥ�������:
	 * <br>1.	����
	 * <br>2.	ѡ��A
	 * <br>3.	ѡ��B
	 * <br>4.	ѡ��C
	 * <br>5.	��
	 */
	private static final String SQL_INSERT_EXAM = "insert into exam values(exam_id_seq.nextval,?,?,?,?,?,?)";
	
	/**
	 * ����ɾ�������SQL���,������һ��Ԥ�������:
	 * <br>1.	����id
	 */
	private static final String SQL_DELETE_EXAM = "delete from exam where id=?";
	
	/**
	 * �����޸�������Ŀ��SQL���,��������Ԥ�������:
	 * <br>1.	������Ŀ
	 * <br>2.	����id
	 */
	private static final String SQL_UPDATE_PROBLEMS = "update exam set problems=? where id=?";
	
	/**
	 * �����޸�����ѡ��A��SQL���,��������Ԥ�������:
	 * <br>1.	����ѡ��A
	 * <br>2.	����id
	 */
	private static final String SQL_UPDATE_OPTION_A = "update exam set OptionA=? where id=?";
	
	/**
	 * �����޸�����ѡ��B��SQL���,��������Ԥ�������:
	 * <br>1.	����ѡ��B
	 * <br>2.	����id
	 */
	private static final String SQL_UPDATE_OPTION_B = "update exam set OptionB=? where id=?";
	
	/**
	 * �����޸�����ѡ��C��SQL���,��������Ԥ�������:
	 * <br>1.	����ѡ��C
	 * <br>2.	����id
	 */
	private static final String SQL_UPDATE_OPTION_C = "update exam set OptionC=? where id=?";
	
	/**
	 * �����޸�����ѡ��D��SQL���,��������Ԥ�������:
	 * <br>1.	����ѡ��D
	 * <br>2.	����id
	 */
	private static final String SQL_UPDATE_OPTION_D = "update exam set OptionD=? where id=?";
	
	/**
	 * �����޸�����𰸵�SQL���,��������Ԥ�������:
	 * <br>1.	�����
	 * <br>2.	����id
	 */
	private static final String SQL_UPDATE_ANSWER = "update exam set answer=? where id=?";
	
	/**
	 * ѧԱ��ȡ����
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
			int[] arr = new int[num];//�������id������
			int[] brr = new int[10];//���arr���±�
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
				//���Ԥ�������
				ps.setInt(1, arr[brr[i]]);
				result = ps.executeQuery();
				while(result.next()){
//					private int id; // ����Լ�� ���
//					private String problems; // ����
//					private String optionA; // ѡ��A
//					private String optionB; // ѡ��B
//					private String optionC; // ѡ��C
//					private String optionD; // ѡ��D
//					private String answer; // ��
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
	 * ���Խ���ж�
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
			//��ȡ�û�ID
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
				//�û�ID
				state.setInt(1,user_id);
				//��ĿID
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
	 * ��ѯ���гɼ�
	 */
	@Override
	public HashMap<Integer,UserAndExam> queryScore(int id) {
		Connection conn = null;
		ResultSet result = null;
		PreparedStatement state = null;
		try {
			conn = DBCPUtil.getConnection();
			state = conn.prepareStatement(SQL_ID_SELECT_SOUCE);
			//�û�ID
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
	 * �������
	 */
	@Override
	public int insert(Exam exam) {
		Connection conn = null;
		ResultSet result = null;
		PreparedStatement state = null;
		try {
			conn = DBCPUtil.getConnection();
			state = conn.prepareStatement(SQL_INSERT_EXAM);
			//���Ԥ����Ĳ���
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
	 * ɾ������
	 */
	@Override
	public boolean delete(int id) {
		Connection conn = null;
		ResultSet result = null;
		PreparedStatement state = null;
		try {
			conn = DBCPUtil.getConnection();
			state = conn.prepareStatement(SQL_DELETE_EXAM);
			//���Ԥ����Ĳ���
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
	 * �޸�����
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
				//���Ԥ����Ĳ���
				state.setString(1,exam.getProblems());
				state.setInt(2, id);
				flag1 = state.executeUpdate();
			}
			
			if(exam.getOptionA() != null) {
				state = conn.prepareStatement(SQL_UPDATE_OPTION_A);
				//���Ԥ����Ĳ���
				state.setString(1,exam.getOptionA());
				state.setInt(2, id);
				flag2 = state.executeUpdate();
			}
			
			if(exam.getOptionB() != null) {
				state = conn.prepareStatement(SQL_UPDATE_OPTION_B);
				//���Ԥ����Ĳ���
				state.setString(1,exam.getOptionB());
				state.setInt(2, id);
				flag3 = state.executeUpdate();
			}
			
			if(exam.getOptionC() != null) {
				state = conn.prepareStatement(SQL_UPDATE_OPTION_C);
				//���Ԥ����Ĳ���
				state.setString(1,exam.getOptionC());
				state.setInt(2, id);
				flag4 = state.executeUpdate();
			}
			
			if(exam.getOptionD() != null) {
				state = conn.prepareStatement(SQL_UPDATE_OPTION_D);
				//���Ԥ����Ĳ���
				state.setString(1,exam.getOptionD());
				state.setInt(2, id);
				flag5 = state.executeUpdate();
			}
			
			if(exam.getAnswer() != null) {
				state = conn.prepareStatement(SQL_UPDATE_ANSWER);
				//���Ԥ����Ĳ���
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
	 * ��ѯ���Գɼ�
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
				//���Ԥ������� 
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
