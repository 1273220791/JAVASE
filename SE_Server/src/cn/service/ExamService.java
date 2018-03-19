package cn.service;

import java.util.HashMap;

import cn.bean.Exam;
import cn.bean.UserAndExam;
import cn.dao.ExamDao;
import cn.dao.Oracle_ExamDaoImp;

public class ExamService {
	private static ExamDao dao;
	static {
		dao = new Oracle_ExamDaoImp();
	}
	
	public static HashMap<Integer, Exam> problems(){
		return dao.problems();
	}
	
	public static int judge(HashMap<Integer,Exam> hashMap){
		return dao.judge(hashMap);
		
	}
	
	public static HashMap<Integer, UserAndExam> queryScore(int id){
		return dao.queryScore(id);
		
	}
	
	public static int insert(Exam exam){
		return dao.insert(exam);
	}
	
	public static boolean  delete(int id){
		return dao.delete(id);
	}
	
	public static boolean update(int id,Exam exam){
		return dao.update(id, exam);
	}
	
	public static HashMap<Integer,Exam> findIdByExam(int id){
		return dao.findIdByExam(id);
	}

}
