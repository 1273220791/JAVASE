package cn.view;

import java.util.Scanner;

import cn.bean.Exam;
import cn.bean.User;

public class ClientView {
	private static Scanner input = new Scanner(System.in);

	/**
	 * ��ӭ��ͼ
	 * @return
	 */
	public static User welcomeView() {
		System.out.println("***********************************************");
		System.out.println("************                    ***************");
		System.out.println("************��ӭ�������߿��Թ���ϵͳ          ***************");
		System.out.println("************����е�¼                                    ***************");
		System.out.println("************�������������ʺ�                         ***************");
		String username = input.nextLine();
		System.out.println("************����������������                         ***************");
		String password = input.nextLine();
		System.out.println("************                    ***************");
		System.out.println("************************************************");
		return new User(-1, username, password);
	}
	
	/**
	 * ѧԱ��ͼ
	 * @return
	 */
	public static int studentView() {
		System.out.println("***********************************************");
		System.out.println("************\t                 ***************");
		System.out.println("************\t��ӭ����ѧ��ϵͳ               ***************");
		System.out.println("************\t�������ʾ���в���            ***************");
		System.out.println("************\t0.�˳�ϵͳ                         ***************");
		System.out.println("************\t1.�޸�����                         ***************");
		System.out.println("************\t2.��ʼ����                         ***************");
		System.out.println("************\t3.��ѯ���п��Գɼ�          ***************");
		System.out.println("************\t4.�������п��Գɼ�          ***************");
		System.out.println("************\t                ***************");
		System.out.println("************************************************");
		String text = input.nextLine();
		int type = -1;
		try {
			type = Integer.parseInt(text);
		}catch(NumberFormatException e) {
			
		}
		if(type<0 || type>4) {
			System.out.println("��������������� ");
			return studentView();
		}
		return type;
	}
	
	/**
	 * ѧԱ�����ɼ���ͼ
	 * @return
	 */
	public static String outputSouce(){
		System.out.println("***********************************************");
		System.out.println("************\t                 ***************");
		System.out.println("************\t��������Ҫ�����ļ���·��:    ***************");
		String url= input.nextLine();
		System.out.println("************\t                ***************");
		System.out.println("************************************************");
		return url;
	}
	
	/**
	 * ����Ա��ͼ
	 * @return
	 */
	public static int managerView() {
		System.out.println("***********************************************");
		System.out.println("************\t                 ***************");
		System.out.println("************\t��ӭ�������ҳ��               ***************");
		System.out.println("************\t�������ʾ���в���            ***************");
		System.out.println("************\t0.�˳�ϵͳ                         ***************");
		System.out.println("************\t1.���ӿ���ѧԱ                         ***************");
		System.out.println("************\t2.ɾ������ѧԱ                         ***************");
		System.out.println("************\t3.�޸Ŀ���ѧԱ          ***************");
		System.out.println("************\t4.��ѯ����ѧԱ          ***************");
		System.out.println("************\t5.���ӿ�����Ŀ          ***************");
		System.out.println("************\t6.ɾ��������Ŀ          ***************");
		System.out.println("************\t7.�޸Ŀ�����Ŀ          ***************");
		System.out.println("************\t8.��ѯ������Ŀ          ***************");
		System.out.println("************\t9.�������뿼��          ***************");
		System.out.println("************\t                ***************");
		System.out.println("************************************************");
		String text = input.nextLine();
		int type = -1;
		try {
			type = Integer.parseInt(text);
		}catch(NumberFormatException e) {
			
		}
		if(type<0 || type>9) {
			System.out.println("��������������� ");
			return managerView();
		}
		return type;
	}
	/**
	 * ����Ա�������ѧԱ�ʺŵ���ͼ
	 * @return
	 */
	public static User addUserView() {
		System.out.println("***********************************************");
		System.out.println("************\t                 ***************");
		System.out.println("************\t��������ӵ�ѧԱ�ʺ�            ***************");
		String username = input.nextLine();
		System.out.println("************\t��������ӵ�ѧԱ����            ***************");
		String password = input.nextLine();
		System.out.println("************\t��������ӵ�ѧԱ�ǳ�            ***************");
		String nickname = input.nextLine();
		System.out.println("************\t                ***************");
		System.out.println("************************************************");
		return new User(username, password, nickname, 0);
	}
	/**
	 * ����Ա����ɾ��ѧԱ�ʺŵ���ͼ
	 * @return
	 */
	public static String deleteUserView() {
		System.out.println("***********************************************");
		System.out.println("************\t                 ***************");
		System.out.println("************\t������Ҫɾ����ѧԱ�ʺ�            ***************");
		String username = input.nextLine();
		System.out.println("************\t                ***************");
		System.out.println("************************************************");
		return username;
	}
	/**
	 * �����޸��û��������ͼ
	 * @param user ���ô˷���ʱ, �����һ���û�����,  ���˷���ִ����Ϻ�,  ��������л�����û�������ʺ� �� ������
	 * @return �޸ĵ�������
	 */
	public static String updatePasswordView(User user) {
		System.out.println("***********************************************");
		System.out.println("************\t                 ***************");
		System.out.println("************\t����������ѧԱ�ʺ�            ***************");
		String username = input.nextLine();
		System.out.println("************\t������ɵ�����  ***************");
		String password = input.nextLine();
		System.out.println("************\t�������µ�����  ***************");
		String newPassword = input.nextLine();
		System.out.println("************\t                ***************");
		System.out.println("************************************************");
		user.setUsername(username);
		user.setPassword(password);
		return newPassword;
	}
	/**
	 * ����Ա�����޸�ѧԱ������ǳƵ���ͼ
	 * @return
	 */
	public static User updateUserView() {
		System.out.println("***********************************************");
		System.out.println("************\t                 ***************");
		System.out.println("************\t�������޸ĵ�ѧԱ�ʺ�            ***************");
		String username = input.nextLine();
		System.out.println("************\t�������µ�ѧԱ����(���޸�ֱ�ӻس�)  ***************");
		String password = input.nextLine();
		System.out.println("************\t�������µ�ѧԱ�ǳ�(���޸�ֱ�ӻس�)  ***************");
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
	 * ������ѯѧԱ����ͼ
	 * @return
	 */
	public static String selectUserView() {
		System.out.println("***********************************************");
		System.out.println("************\t                 ***************");
		System.out.println("************\t������Ҫ��ѯ��ѧԱ�ʺ�            ***************");
		String username = input.nextLine();
		System.out.println("************\t                ***************");
		System.out.println("************************************************");
		return username;
	}
	
	/**
	 * ���ڹ���Ա��ӿ���
	 * @return 
	 */
	public static Exam addExam(){
		System.out.println("***********************************************");
		System.out.println("************\t                 ***************");
		System.out.println("************\t�����뿼����Ŀ:           ***************");
		String problems = input.nextLine();
		System.out.println("************\t������ѡ��A������:           ***************");
		String optionA = input.nextLine();
		System.out.println("************\t������ѡ��B������:           ***************");
		String optionB = input.nextLine();
		System.out.println("************\t������ѡ��C������:           ***************");
		String optionC = input.nextLine();
		System.out.println("************\t������ѡ��D������:           ***************");
		String optionD = input.nextLine();
		System.out.println("************\t��������ȷ�𰸵�ѡ��:           ***************");
		String answer = input.nextLine().toUpperCase();
		System.out.println("************\t                ***************");
		System.out.println("************************************************");
		return new Exam(problems, optionA, optionB, optionC, optionD, answer);
	}
	
	/**
	 * ���ڹ���Ա����id��ɾ������
	 * @return ����id
	 */
	public static int deleteExamView() {
		System.out.println("***********************************************");
		System.out.println("************\t                 ***************");
		System.out.println("************\t������Ҫɾ���������id      ***************");
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
	 * �޸��������ͼ
	 * @return
	 */
	public static Exam updateExamView(){
		System.out.println("*********************************************************************");
		System.out.println("************\t                                        ***************");
		System.out.println("************\t��������Ҫ�޸ĵ���Ŀid:                        ***************");
		String id = input.nextLine();
		System.out.println("************\t��������Ҫ�޸ĵĿ�����Ŀ:(���޸�ֱ�ӻس�)            ***************");
		String problems = input.nextLine();
		System.out.println("************\t��������Ҫ�޸ĵ�ѡ��A������:(���޸�ֱ�ӻس�)          ***************");
		String optionA = input.nextLine();
		System.out.println("************\t��������Ҫ�޸ĵ�ѡ��B������:(���޸�ֱ�ӻس�)          ***************");
		String optionB = input.nextLine();
		System.out.println("************\t��������Ҫ�޸ĵ�ѡ��C������:(���޸�ֱ�ӻس�)          ***************");
		String optionC = input.nextLine();
		System.out.println("************\t��������Ҫ�޸ĵ�ѡ��D������:(���޸�ֱ�ӻس�)          ***************");
		String optionD = input.nextLine();
		System.out.println("************\t��������Ҫ�޸ĵ���ȷ�𰸵�ѡ��:(���޸�ֱ�ӻس�)        ***************");
		String answer = input.nextLine().toUpperCase();
		System.out.println("************\t                                         ***************");
		System.out.println("**********************************************************************");
		return new Exam(Integer.parseInt(id),problems, optionA, optionB, optionC, optionD, answer);
	}
	
	/**
	 * ���ڹ���Ա����ѯ����ͼ
	 * @return 
	 */
	public static int selectExamView() {
		System.out.println("***********************************************");
		System.out.println("************\t                 ***************");
		System.out.println("************\t��ѡ���ѯ����ķ�ʽ(1.ָ��id��ѯ,2.ȫ����ѯ)      ***************");
		String id = input.nextLine();
		int examId = 0 ;
		if(Integer.parseInt(id) == 1){
			System.out.println("************\t������Ҫ��ѯ�����id      ***************");
			id = input.nextLine();
			examId = Integer.parseInt(id);
		}
		System.out.println("************\t                ***************");
		System.out.println("************************************************");
		return examId;
	}
	
	/**
	 * ����Ա����������ͼ
	 * @return
	 */
	public static String inputExam(){
		System.out.println("***********************************************");
		System.out.println("************\t                 ***************");
		System.out.println("�����ʽ: ����xx,ѡ��Axx,ѡ��Bxx,ѡ��Cxx,ѡ��Dxx,��xx(ֱ�����������Զ��Ÿ���)");
		System.out.println("************\t                 ***************");
		System.out.println("************\t                 ***************");
		System.out.println("************\t��������Ҫ�����ļ���·��:    ***************");
		String url= input.nextLine();
		System.out.println("************\t                ***************");
		System.out.println("************************************************");
		return url;
	}
	
}
