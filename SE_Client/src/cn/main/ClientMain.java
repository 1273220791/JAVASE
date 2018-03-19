package cn.main;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Scanner;

import cn.bean.Exam;
import cn.bean.User;
import cn.bean.UserAndExam;
import cn.view.ClientView;

public class ClientMain {
	/**
	 * ����û�Idֵ
	 */
	private static int id;
	/**
	 * д����
	 */
	private static ObjectOutputStream oos;
	/**
	 * ��ȡ��
	 */
	private static ObjectInputStream ois;
	/**
	 * ����������Ϣ�ļ���
	 */
	private static HashMap<String, Object> toData;
	/**
	 * ����������Ϣ�ļ���
	 */
	private static HashMap<String, Object> fromData;

	public static void main(String[] args) {
		try {
			Socket s = new Socket("localhost", 10086);
			oos = new ObjectOutputStream(s.getOutputStream());
			ois = new ObjectInputStream(s.getInputStream());
			// ��ʾ�û������ʺ�����, ���е�¼
			User user = ClientView.welcomeView();
			toData = new HashMap<>();
			toData.put("user", user);
			toMessage(10010);
			fromMessage();
			int msgType = (int) fromData.get("msgType");
			//��¼��ǰ�û���ID��Ϣ
			id = (int) fromData.get("id");
			switch (msgType) {
			case 20001: {
				// ����Ա��¼�ɹ�
				managerClient();
			}
				break;
			case 20002: {
				// ѧԱ��¼�ɹ�
				studentClient();
			}
				break;
			case 20003:
				// ��¼ʧ��
				System.out.println("���ź�, ��¼ʧ��!~ ");
				break;
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * ѧ��ϵͳ
	 */
	private static void studentClient() {
		while (true) {
			int type = ClientView.studentView();
			switch (type) {
			case 1: {
				// �޸�����
				User user = new User();
				String newPass = ClientView.updatePasswordView(user);
				toData = new HashMap<>();
				toData.put("user", user);
				toData.put("newPassword", newPass);
				toMessage(10011);
				fromMessage();
				// �����ص���ʾ����Ϣ
				messgeType();
			}
				break;
			case 2: {
				// ��ʼ����
				toData = new HashMap<String, Object>();
				toMessage(10012);// ����Ϣ
				fromMessage();// ����Ϣ

				HashMap<Integer, Exam> hs = (HashMap<Integer, Exam>) fromData.get("exam");
				HashMap<Integer, Exam> hash = new HashMap<>();
				Scanner sc = new Scanner(System.in);
				for (int i = 0; i < 10; i++) {
					Exam e = hs.get(i);
					System.out.println();
					System.out.println(i + 1 + "." + e.getProblems());
					System.out.println("A." + e.getOptionA() + "  B." + e.getOptionB() + "  C." + e.getOptionC()
							+ "  D." + e.getOptionD());
					System.out.println("��������ȷ��:(����ѡ��)");
					String str = sc.nextLine().toUpperCase();
					hash.put(i, new Exam(e.getId(), str));
				}
				hash.put(-1, new Exam(id,""));
				try {
					oos.writeObject(hash);// ����Ϣ
					fromMessage();
					int num = (int) fromData.get("num");
					System.out.println("���ĵ÷���:" + num);
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
				break;
			case 3: {
				// ��ѯ���гɼ�
				toData = new HashMap<String, Object>();
				toMessage(10013);
				try {
					oos.writeObject(id);
				} catch (IOException e) {
					e.printStackTrace();
				}
				fromMessage();
				HashMap<Integer, UserAndExam> hs = (HashMap<Integer, UserAndExam>) fromData.get("score");
				for (int i = 0; i < hs.size(); i++) {
					UserAndExam uae = hs.get(i);
					System.out.println(
							i + 1 + "." + uae.getUser_result() + ",ʱ��:" + uae.getExam_time() + ",�ɼ�:" + uae.getSouce());
				}
			}
				break;
			case 4:{
				// �������гɼ�
				toData = new HashMap<String, Object>();
				toMessage(10013);
				try {
					oos.writeObject(id);
				} catch (IOException e) {
					e.printStackTrace();
				}
				fromMessage();
				HashMap<Integer, UserAndExam> hs = (HashMap<Integer, UserAndExam>) fromData.get("score");
				// �������������Ϣд���ļ�
				System.out.println("44444444444444444444444444");
				PrintStream ps = null;
				String url = ClientView.outputSouce();
				try {
					ps = new PrintStream(new FileOutputStream(url));
					for (int i = 0; i < hs.size(); i++) {
						UserAndExam uae = hs.get(i);
						ps.println(i + 1 + "." + uae.getUser_result() + ",ʱ��:" + uae.getExam_time() + ",�ɼ�:"
								+ uae.getSouce());
					}
					System.out.println("�����Ե���");
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}finally{
					ps.close();
					ps = null;
				}
			}
				break;
			case 0:
				System.exit(0);
				break;

			}
		}
	}

	/**
	 * ����Աϵͳ
	 */
	private static void managerClient() {
		while (true) {

			int type = ClientView.managerView();
			switch (type) {
			case 1: {
				// ����ѧԱ
				User user = ClientView.addUserView();
				toData = new HashMap<>();
				toData.put("user", user);
				toMessage(10021);
				fromMessage();
				messgeType();
			}
				break;
			case 2:{
				// ɾ��ѧԱ
				String username = ClientView.deleteUserView();
				toData = new HashMap<>();
				toData.put("username", username);
				toMessage(10022);
				fromMessage();
				messgeType();
				break;}
			case 3:{
				// �޸�ѧԱ
				User user = ClientView.updateUserView();
				toData = new HashMap<>();
				toData.put("user", user);
				toMessage(10023);
				fromMessage();
				messgeType();
				break;}
			case 4:{
				// ��ѯѧԱ
				String username2 = ClientView.selectUserView();
				toData = new HashMap<>();
				toData.put("username", username2);
				toMessage(10024);
				fromMessage();
				messgeType();
			}
				break;
			case 5:{
				// ���ӿ���
				Exam exam = ClientView.addExam();
				toData = new HashMap<>();
				toData.put("addExam", exam);
				toMessage(10025);
				fromMessage();
				messgeType();
			}
				break;
			case 6:{
				// ɾ������
				int id = ClientView.deleteExamView();
				toData = new HashMap<>();
				toData.put("examId", id);
				toMessage(10026);
				fromMessage();
				messgeType();
			}
				break;
			case 7:{
				// �޸Ŀ���
				Exam exam = ClientView.updateExamView();
				toData = new HashMap<>();
				toData.put("updateExam", exam);
				toMessage(10027);
				fromMessage();
				messgeType();
			}
				break;
			case 8:{
				// ��ѯ����
				int id = ClientView.selectExamView();
				toData = new HashMap<>();
				toData.put("examId", id);
				toMessage(10028);
				fromMessage();
				HashMap<Integer,Exam> hs = (HashMap<Integer,Exam>) fromData.get("select"); 
				if(hs != null){
					for(int i = 0;i<hs.size();i++){
						Exam exam = hs.get(i);
						System.out.println(i+1+".����Ϊ:"+exam.getId()+",��Ŀ:"+exam.getProblems()+",ѡ��A:"+exam.getOptionA()+",ѡ��B:"+exam.getOptionB()+",ѡ��C:"+exam.getOptionC()+",ѡ��D:"+exam.getOptionD()+",��:"+exam.getAnswer());
					}
				}else{
					System.out.println("��ѯʧ��, �����������ID");
				}
				
			}
				break;
			case 9:{
				// �������뿼��
				String url = ClientView.inputExam();
				BufferedReader br = null;
				try {
					br = new BufferedReader(new InputStreamReader(new FileInputStream(url)));
					while(true){
						String str;
							str = br.readLine();
							if(str == null){
								break;
							}
							String[] arr = str.split(",");
							Exam exam = new Exam(arr[0],arr[1],arr[2],arr[3],arr[4],arr[5].toUpperCase());
							toData = new HashMap<>();
							toData.put("addExam", exam);
							toMessage(10025);
							fromMessage();
							messgeType();
					}
					System.out.println("������ȫ������");
				} catch (IOException e) {
					e.printStackTrace();
				}finally{
					try {
						br.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
					br = null;
				}
			}
				break;
			case 0:
				System.exit(0);
				break;

			}
		}
	}

	/**
	 * ����������Ľ��
	 */
	private static void messgeType() {
		int type = (int) fromData.get("msgType");
		switch (type) {
		case 20004:
			System.out.println("�޸ĳɹ�");
			break;
		case 20005:
			System.out.println("�޸�ʧ��");
			break;
		case 20006:
			System.out.println("��ӳɹ�");
			break;
		case 20007:
			System.out.println("���ʧ��");
			break;
		case 20008:
			System.out.println("ɾ���ɹ�");
			break;
		case 20009:
			System.out.println("ɾ��ʧ��");
			break;
		case 20010:
			System.out.println("�޸ĳɹ�");
			break;
		case 20011:
			System.out.println("�޸�ʧ��");
			break;
		case 20012:
			String userInfo = (String) fromData.get("userInfo");
			if (userInfo == null) {
				System.out.println("��ѯʧ��, ������������ʺ�");
			} else {
				System.out.println("����ѯ����Ϣ����:");
				System.out.println(userInfo);
			}
			break;
		default:
			break;
		}
	}

	/**
	 * ������д������Ĳ���
	 * 
	 * @param msgType
	 */
	private static void toMessage(int msgType) {
		try {
			toData.put("msgType", msgType);
			oos.writeObject(toData);
			oos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ��ȡ���еļ���
	 */
	private static void fromMessage() {
		try {
			fromData = (HashMap<String, Object>) ois.readObject();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
