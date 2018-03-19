package cn.view;

import java.util.Scanner;

import cn.bean.Exam;
import cn.bean.User;

public class ClientView {
	private static Scanner input = new Scanner(System.in);

	/**
	 * 欢迎视图
	 * @return
	 */
	public static User welcomeView() {
		System.out.println("***********************************************");
		System.out.println("************                    ***************");
		System.out.println("************欢迎来到在线考试管理系统          ***************");
		System.out.println("************请进行登录                                    ***************");
		System.out.println("************请先输入您的帐号                         ***************");
		String username = input.nextLine();
		System.out.println("************请先输入您的密码                         ***************");
		String password = input.nextLine();
		System.out.println("************                    ***************");
		System.out.println("************************************************");
		return new User(-1, username, password);
	}
	
	/**
	 * 学员视图
	 * @return
	 */
	public static int studentView() {
		System.out.println("***********************************************");
		System.out.println("************\t                 ***************");
		System.out.println("************\t欢迎进入学生系统               ***************");
		System.out.println("************\t请根据提示进行操作            ***************");
		System.out.println("************\t0.退出系统                         ***************");
		System.out.println("************\t1.修改密码                         ***************");
		System.out.println("************\t2.开始答题                         ***************");
		System.out.println("************\t3.查询所有考试成绩          ***************");
		System.out.println("************\t4.导出所有考试成绩          ***************");
		System.out.println("************\t                ***************");
		System.out.println("************************************************");
		String text = input.nextLine();
		int type = -1;
		try {
			type = Integer.parseInt(text);
		}catch(NumberFormatException e) {
			
		}
		if(type<0 || type>4) {
			System.out.println("您的输入存在问题 ");
			return studentView();
		}
		return type;
	}
	
	/**
	 * 学员导出成绩视图
	 * @return
	 */
	public static String outputSouce(){
		System.out.println("***********************************************");
		System.out.println("************\t                 ***************");
		System.out.println("************\t请输入您要导出文件的路径:    ***************");
		String url= input.nextLine();
		System.out.println("************\t                ***************");
		System.out.println("************************************************");
		return url;
	}
	
	/**
	 * 管理员视图
	 * @return
	 */
	public static int managerView() {
		System.out.println("***********************************************");
		System.out.println("************\t                 ***************");
		System.out.println("************\t欢迎进入管理页面               ***************");
		System.out.println("************\t请根据提示进行操作            ***************");
		System.out.println("************\t0.退出系统                         ***************");
		System.out.println("************\t1.增加考试学员                         ***************");
		System.out.println("************\t2.删除考试学员                         ***************");
		System.out.println("************\t3.修改考试学员          ***************");
		System.out.println("************\t4.查询考试学员          ***************");
		System.out.println("************\t5.增加考试题目          ***************");
		System.out.println("************\t6.删除考试题目          ***************");
		System.out.println("************\t7.修改考试题目          ***************");
		System.out.println("************\t8.查询考试题目          ***************");
		System.out.println("************\t9.批量导入考题          ***************");
		System.out.println("************\t                ***************");
		System.out.println("************************************************");
		String text = input.nextLine();
		int type = -1;
		try {
			type = Integer.parseInt(text);
		}catch(NumberFormatException e) {
			
		}
		if(type<0 || type>9) {
			System.out.println("您的输入存在问题 ");
			return managerView();
		}
		return type;
	}
	/**
	 * 管理员用来添加学员帐号的视图
	 * @return
	 */
	public static User addUserView() {
		System.out.println("***********************************************");
		System.out.println("************\t                 ***************");
		System.out.println("************\t请输入添加的学员帐号            ***************");
		String username = input.nextLine();
		System.out.println("************\t请输入添加的学员密码            ***************");
		String password = input.nextLine();
		System.out.println("************\t请输入添加的学员昵称            ***************");
		String nickname = input.nextLine();
		System.out.println("************\t                ***************");
		System.out.println("************************************************");
		return new User(username, password, nickname, 0);
	}
	/**
	 * 管理员用来删除学员帐号的视图
	 * @return
	 */
	public static String deleteUserView() {
		System.out.println("***********************************************");
		System.out.println("************\t                 ***************");
		System.out.println("************\t请输入要删除的学员帐号            ***************");
		String username = input.nextLine();
		System.out.println("************\t                ***************");
		System.out.println("************************************************");
		return username;
	}
	/**
	 * 用来修改用户密码的视图
	 * @param user 调用此方法时, 传入的一个用户对象,  当此方法执行完毕后,  这个对象中会出现用户输入的帐号 和 旧密码
	 * @return 修改的新密码
	 */
	public static String updatePasswordView(User user) {
		System.out.println("***********************************************");
		System.out.println("************\t                 ***************");
		System.out.println("************\t请输入您的学员帐号            ***************");
		String username = input.nextLine();
		System.out.println("************\t请输入旧的密码  ***************");
		String password = input.nextLine();
		System.out.println("************\t请输入新的密码  ***************");
		String newPassword = input.nextLine();
		System.out.println("************\t                ***************");
		System.out.println("************************************************");
		user.setUsername(username);
		user.setPassword(password);
		return newPassword;
	}
	/**
	 * 管理员用来修改学员密码和昵称的视图
	 * @return
	 */
	public static User updateUserView() {
		System.out.println("***********************************************");
		System.out.println("************\t                 ***************");
		System.out.println("************\t请输入修改的学员帐号            ***************");
		String username = input.nextLine();
		System.out.println("************\t请输入新的学员密码(不修改直接回车)  ***************");
		String password = input.nextLine();
		System.out.println("************\t请输入新的学员昵称(不修改直接回车)  ***************");
		String nickname = input.nextLine();
		System.out.println("************\t                ***************");
		System.out.println("************************************************");
		if(password.equals(""))
			password=null;
		if(nickname.equals(""))
			nickname=null;
		return new User(username, password, nickname, 0);
	}
	/**
	 * 用来查询学员的视图
	 * @return
	 */
	public static String selectUserView() {
		System.out.println("***********************************************");
		System.out.println("************\t                 ***************");
		System.out.println("************\t请输入要查询的学员帐号            ***************");
		String username = input.nextLine();
		System.out.println("************\t                ***************");
		System.out.println("************************************************");
		return username;
	}
	
	/**
	 * 用于管理员添加考题
	 * @return 
	 */
	public static Exam addExam(){
		System.out.println("***********************************************");
		System.out.println("************\t                 ***************");
		System.out.println("************\t请输入考试题目:           ***************");
		String problems = input.nextLine();
		System.out.println("************\t请输入选项A的内容:           ***************");
		String optionA = input.nextLine();
		System.out.println("************\t请输入选项B的内容:           ***************");
		String optionB = input.nextLine();
		System.out.println("************\t请输入选项C的内容:           ***************");
		String optionC = input.nextLine();
		System.out.println("************\t请输入选项D的内容:           ***************");
		String optionD = input.nextLine();
		System.out.println("************\t请输入正确答案的选项:           ***************");
		String answer = input.nextLine().toUpperCase();
		System.out.println("************\t                ***************");
		System.out.println("************************************************");
		return new Exam(problems, optionA, optionB, optionC, optionD, answer);
	}
	
	/**
	 * 用于管理员根据id来删除试题
	 * @return 返回id
	 */
	public static int deleteExamView() {
		System.out.println("***********************************************");
		System.out.println("************\t                 ***************");
		System.out.println("************\t请输入要删除的试题的id      ***************");
		String sid = input.nextLine();
		int id = -1;
		id=Integer.parseInt(sid);
		if(id == -1){
			return deleteExamView();
		}
		System.out.println("************\t                ***************");
		System.out.println("************************************************");
		return id;
	}
	
	/**
	 * 修改试题的视图
	 * @return
	 */
	public static Exam updateExamView(){
		System.out.println("*********************************************************************");
		System.out.println("************\t                                        ***************");
		System.out.println("************\t请输入需要修改的题目id:                        ***************");
		String id = input.nextLine();
		System.out.println("************\t请输入需要修改的考试题目:(不修改直接回车)            ***************");
		String problems = input.nextLine();
		System.out.println("************\t请输入需要修改的选项A的内容:(不修改直接回车)          ***************");
		String optionA = input.nextLine();
		System.out.println("************\t请输入需要修改的选项B的内容:(不修改直接回车)          ***************");
		String optionB = input.nextLine();
		System.out.println("************\t请输入需要修改的选项C的内容:(不修改直接回车)          ***************");
		String optionC = input.nextLine();
		System.out.println("************\t请输入需要修改的选项D的内容:(不修改直接回车)          ***************");
		String optionD = input.nextLine();
		System.out.println("************\t请输入需要修改的正确答案的选项:(不修改直接回车)        ***************");
		String answer = input.nextLine().toUpperCase();
		System.out.println("************\t                                         ***************");
		System.out.println("**********************************************************************");
		return new Exam(Integer.parseInt(id),problems, optionA, optionB, optionC, optionD, answer);
	}
	
	/**
	 * 用于管理员来查询的视图
	 * @return 
	 */
	public static int selectExamView() {
		System.out.println("***********************************************");
		System.out.println("************\t                 ***************");
		System.out.println("************\t请选择查询试题的方式(1.指定id查询,2.全部查询)      ***************");
		String id = input.nextLine();
		int examId = 0 ;
		if(Integer.parseInt(id) == 1){
			System.out.println("************\t请输入要查询试题的id      ***************");
			id = input.nextLine();
			examId = Integer.parseInt(id);
		}
		System.out.println("************\t                ***************");
		System.out.println("************************************************");
		return examId;
	}
	
	/**
	 * 管理员导入试题视图
	 * @return
	 */
	public static String inputExam(){
		System.out.println("***********************************************");
		System.out.println("************\t                 ***************");
		System.out.println("试题格式: 问题xx,选项Axx,选项Bxx,选项Cxx,选项Dxx,答案xx(直接输入内容以逗号隔开)");
		System.out.println("************\t                 ***************");
		System.out.println("************\t                 ***************");
		System.out.println("************\t请输入您要导入文件的路径:    ***************");
		String url= input.nextLine();
		System.out.println("************\t                ***************");
		System.out.println("************************************************");
		return url;
	}
	
}
