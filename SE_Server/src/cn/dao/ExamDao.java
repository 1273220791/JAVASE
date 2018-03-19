package cn.dao;

import java.util.HashMap;

import cn.bean.Exam;
import cn.bean.User;
import cn.bean.UserAndExam;

public interface ExamDao {

	/**
	 * 学员获取考试试题
	 * @return 返回试题到学生端
	 */
	HashMap<Integer,Exam> problems();
	
	/**
	 * 判断学生成绩
	 * @param hashMap
	 * @return 返回学生成绩
	 */
	int judge(HashMap<Integer,Exam> hashMap);
	
	/**
	 * @param id 用户的ID用于添加用户的记录
	 * 查询学生成绩
	 * @return 返回学生成绩
	 */
	HashMap<Integer,UserAndExam> queryScore(int id);
	
	
	/**
	 * 用于管理员进行  考试试题的添加
	 * @param user  要添加的试题信息 , 包含了问题,选项A,选项B,选项C,选项D,答案
	 * 
	 * @return 返回添加试题的结果, 以及失败的原因 !<br>
	 * 		返回	1:	插入成功 !
	 * 			2:	插入失败 ,内容错误
	 */
	int insert(Exam exam);
	
	/**
	 * 用于管理员 进行 普通学员的删除
	 * @param username  要删除的用户的帐号
	 * @return 删除的结果 true表示删除成功!
	 */
	boolean delete(int id);
	
	/**
	 * 用于管理员修改学员的信息
	 * @param username	要修改的学员的帐号
	 * @param user	新的学员信息, 包含: 密码,昵称!<br>
	 * 	当密码为null时, 表示修改昵称!<br>
	 * 	当昵称为null时, 表示修改密码 !
	 * @return 修改的结果  true表示成功
	 */
	boolean update(int id,Exam exam);
	/**
	 * 用于管理员查询考试题目
	 * @param id 要查询的id,当id等于0时查询所有试题
	 * @return 查询出来的结果
	 */
	HashMap<Integer,Exam> findIdByExam(int id);
}
