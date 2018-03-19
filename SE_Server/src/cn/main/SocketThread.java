package cn.main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;

import cn.bean.Exam;
import cn.bean.User;
import cn.bean.UserAndExam;
import cn.dao.Oracle_UserDaoImp;
import cn.service.ExamService;
import cn.service.UserService;

public class SocketThread implements Runnable {
	/**
	 * ��ͻ������ӵ�Socket����
	 */
	private Socket socket;
	/**
	 * ���Կͻ��˵���Ϣ 
	 */
	private HashMap<String,Object> fromData;
	/**
	 * ׼�����͸��ͻ��˵���Ϣ
	 */
	private HashMap<String,Object> toData;
	/**
	 * �������տͻ�����Ϣ�� ��
	 */
	private ObjectInputStream ois;
	/**
	 * ������ͻ��˷�����Ϣ��  ��
	 */
	private ObjectOutputStream oos;
	/**
	 * ��־��  �ͻ����Ƿ�ѡ�����˳� !
	 */
	private boolean flag = true;
	
	
	public SocketThread() {
		super();
	}

	public SocketThread(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		try {
			ois = new ObjectInputStream(socket.getInputStream());
			oos = new ObjectOutputStream(socket.getOutputStream());
			while(flag ) {
				fromMessage();
				toMessage();
			}
		} catch (IOException e) {
			flag = false;
		}
		System.out.println("�ͻ����ѶϿ�~ ");
	}
	
	private void fromMessage() {
		try {
			fromData = (HashMap<String, Object>) ois.readObject();
			int msgType = (int) fromData.get("msgType");
			switch (msgType) {
			case 10010:{//�û���¼
				//�õ���¼���û���Ϣ
				User user = (User) fromData.get("user");
				//��¼���Ϊresult
				int[] result = UserService.login(user.getUsername(), user.getPassword());
				toData = new HashMap<>();
				toData.put("msgType", 20000+result[0]);
				toData.put("id", result[1]);
			}
			break;
			case 10011:{//ѧԱ�޸�����
				User user = (User) fromData.get("user");
				String newPass = (String) fromData.get("newPassword");
				boolean flag = UserService.updatePassword(user.getUsername(), user.getPassword(), newPass);
				toData = new HashMap<>();
				if(flag) {
					toData.put("msgType",20004);
				}else {
					toData.put("msgType",20005);
				}
			}
			break;
			case 10012:{//ѧԱ��ʼ����
				toData = new HashMap<>();
				HashMap<Integer,Exam> hs = ExamService.problems();
				toData.put("exam", hs);
				toMessage();//���͵��ͻ���
				hs = (HashMap<Integer, Exam>) ois.readObject();//��ȡ��Ϣ
				int num = ExamService.judge(hs);
				toData = new HashMap<>();
				toData.put("num", num);
			}
			break;
			case 10013:{//��ѯ���п��Գɼ�
				int id = (int) ois.readObject();//��ȡ��Ϣ
				System.out.println("��ѯ���п��Գɼ�:"+id);
				toData = new HashMap<>();
				HashMap<Integer,UserAndExam> hs = ExamService.queryScore(id);
				toData.put("score", hs);
			}
			break;
			
			
			case 10021:{//���ѧԱ
				User user = (User) fromData.get("user");
				//��ѧԱ�������ݿ�
				int result = UserService.insert(user);
				toData = new HashMap<>();
				if(result==1) {
					toData.put("msgType",20006);
				}else {
					toData.put("msgType",20007);
				}
			}
			break;
			case 10022:{//ɾ��ѧԱ
				String username = (String) fromData.get("username");
				boolean flag = UserService.delete(username);
				toData = new HashMap<>();
				if(flag) {
					toData.put("msgType",20008);
				}else {
					toData.put("msgType",20009);
					
				}
				
				
			}
			break;
			case 10023:{//�޸�ѧԱ
				User user = (User) fromData.get("user");
				boolean flag = UserService.update(user.getUsername(), user);
				toData = new HashMap<>();
				if(flag) {
					toData.put("msgType",20010);
				}else {
					toData.put("msgType",20011);
					
				}
			}
			break;
			
			case 10024:{//��ѯѧԱ
				String username = (String) fromData.get("username");
				User user = UserService.findUserByUsername(username);
				String userInfo = null;
				if(user!=null) {
					userInfo = "����û���id:"+user.getId()+", �ʺ�:"+user.getUsername()+", ����:"+user.getPassword()+",�ǳ�:"+user.getNickname();
				}
				
				toData = new HashMap<>();
				toData.put("msgType",20012);
				toData.put("userInfo",userInfo);
			}
			break;
			
			case 10025:{//���ӿ���
				Exam exam = (Exam) fromData.get("addExam");
				int result = ExamService.insert(exam);
				toData = new HashMap<>();
				if(result==1) {
					toData.put("msgType",20006);
				}else {
					toData.put("msgType",20007);
				}
			}
			break;

			case 10026:{//ɾ������
				int id = (int) fromData.get("examId");
				boolean flag = ExamService.delete(id);
				toData = new HashMap<>();
				if(flag) {
					toData.put("msgType",20008);
				}else {
					toData.put("msgType",20009);
					
				}
			}
			break;
			
			case 10027:{//�޸Ŀ���
				Exam exam = (Exam) fromData.get("updateExam");
				boolean flag = ExamService.update(exam.getId(),exam);
				toData = new HashMap<>();
				if(flag) {
					toData.put("msgType",20010);
				}else {
					toData.put("msgType",20011);
					
				}
			}
			break;
			
			case 10028:{//��ѯ����
				int id = (int) fromData.get("examId");
				HashMap<Integer,Exam> hs = ExamService.findIdByExam(id);
				toData = new HashMap<>();
				toData.put("select",hs);
			}
			break;
			
			}
		} catch (Exception e) {
			flag = false;
		}
	}

	private void toMessage() {
		try {
			oos.writeObject(toData);
		} catch (IOException e) {
			flag = false;
		}
	}



}
