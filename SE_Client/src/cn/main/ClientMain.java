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
	 * 存放用户Id值
	 */
	private static int id;
	/**
	 * 写入流
	 */
	private static ObjectOutputStream oos;
	/**
	 * 读取流
	 */
	private static ObjectInputStream ois;
	/**
	 * 用来发送消息的集合
	 */
	private static HashMap<String, Object> toData;
	/**
	 * 用来接收消息的集合
	 */
	private static HashMap<String, Object> fromData;

	public static void main(String[] args) {
		try {
			Socket s = new Socket("localhost", 10086);
			oos = new ObjectOutputStream(s.getOutputStream());
			ois = new ObjectInputStream(s.getInputStream());
			// 提示用户输入帐号密码, 进行登录
			User user = ClientView.welcomeView();
			toData = new HashMap<>();
			toData.put("user", user);
			toMessage(10010);
			fromMessage();
			int msgType = (int) fromData.get("msgType");
			//记录当前用户的ID信息
			id = (int) fromData.get("id");
			switch (msgType) {
			case 20001: {
				// 管理员登录成功
				managerClient();
			}
				break;
			case 20002: {
				// 学员登录成功
				studentClient();
			}
				break;
			case 20003:
				// 登录失败
				System.out.println("很遗憾, 登录失败!~ ");
				break;
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 学生系统
	 */
	private static void studentClient() {
		while (true) {
			int type = ClientView.studentView();
			switch (type) {
			case 1: {
				// 修改密码
				User user = new User();
				String newPass = ClientView.updatePasswordView(user);
				toData = new HashMap<>();
				toData.put("user", user);
				toData.put("newPassword", newPass);
				toMessage(10011);
				fromMessage();
				// 处理返回的提示性信息
				messgeType();
			}
				break;
			case 2: {
				// 开始答题
				toData = new HashMap<String, Object>();
				toMessage(10012);// 发消息
				fromMessage();// 读消息

				HashMap<Integer, Exam> hs = (HashMap<Integer, Exam>) fromData.get("exam");
				HashMap<Integer, Exam> hash = new HashMap<>();
				Scanner sc = new Scanner(System.in);
				for (int i = 0; i < 10; i++) {
					Exam e = hs.get(i);
					System.out.println();
					System.out.println(i + 1 + "." + e.getProblems());
					System.out.println("A." + e.getOptionA() + "  B." + e.getOptionB() + "  C." + e.getOptionC()
							+ "  D." + e.getOptionD());
					System.out.println("请输入正确答案:(谨慎选择)");
					String str = sc.nextLine().toUpperCase();
					hash.put(i, new Exam(e.getId(), str));
				}
				hash.put(-1, new Exam(id,""));
				try {
					oos.writeObject(hash);// 发消息
					fromMessage();
					int num = (int) fromData.get("num");
					System.out.println("您的得分是:" + num);
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
				break;
			case 3: {
				// 查询所有成绩
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
							i + 1 + "." + uae.getUser_result() + ",时间:" + uae.getExam_time() + ",成绩:" + uae.getSouce());
				}
			}
				break;
			case 4:{
				// 导出所有成绩
				toData = new HashMap<String, Object>();
				toMessage(10013);
				try {
					oos.writeObject(id);
				} catch (IOException e) {
					e.printStackTrace();
				}
				fromMessage();
				HashMap<Integer, UserAndExam> hs = (HashMap<Integer, UserAndExam>) fromData.get("score");
				// 添加逐行流将信息写入文件
				System.out.println("44444444444444444444444444");
				PrintStream ps = null;
				String url = ClientView.outputSouce();
				try {
					ps = new PrintStream(new FileOutputStream(url));
					for (int i = 0; i < hs.size(); i++) {
						UserAndExam uae = hs.get(i);
						ps.println(i + 1 + "." + uae.getUser_result() + ",时间:" + uae.getExam_time() + ",成绩:"
								+ uae.getSouce());
					}
					System.out.println("数据以导出");
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
	 * 管理员系统
	 */
	private static void managerClient() {
		while (true) {

			int type = ClientView.managerView();
			switch (type) {
			case 1: {
				// 增加学员
				User user = ClientView.addUserView();
				toData = new HashMap<>();
				toData.put("user", user);
				toMessage(10021);
				fromMessage();
				messgeType();
			}
				break;
			case 2:{
				// 删除学员
				String username = ClientView.deleteUserView();
				toData = new HashMap<>();
				toData.put("username", username);
				toMessage(10022);
				fromMessage();
				messgeType();
				break;}
			case 3:{
				// 修改学员
				User user = ClientView.updateUserView();
				toData = new HashMap<>();
				toData.put("user", user);
				toMessage(10023);
				fromMessage();
				messgeType();
				break;}
			case 4:{
				// 查询学员
				String username2 = ClientView.selectUserView();
				toData = new HashMap<>();
				toData.put("username", username2);
				toMessage(10024);
				fromMessage();
				messgeType();
			}
				break;
			case 5:{
				// 增加考题
				Exam exam = ClientView.addExam();
				toData = new HashMap<>();
				toData.put("addExam", exam);
				toMessage(10025);
				fromMessage();
				messgeType();
			}
				break;
			case 6:{
				// 删除考题
				int id = ClientView.deleteExamView();
				toData = new HashMap<>();
				toData.put("examId", id);
				toMessage(10026);
				fromMessage();
				messgeType();
			}
				break;
			case 7:{
				// 修改考题
				Exam exam = ClientView.updateExamView();
				toData = new HashMap<>();
				toData.put("updateExam", exam);
				toMessage(10027);
				fromMessage();
				messgeType();
			}
				break;
			case 8:{
				// 查询考题
				int id = ClientView.selectExamView();
				toData = new HashMap<>();
				toData.put("examId", id);
				toMessage(10028);
				fromMessage();
				HashMap<Integer,Exam> hs = (HashMap<Integer,Exam>) fromData.get("select"); 
				if(hs != null){
					for(int i = 0;i<hs.size();i++){
						Exam exam = hs.get(i);
						System.out.println(i+1+".序列为:"+exam.getId()+",题目:"+exam.getProblems()+",选项A:"+exam.getOptionA()+",选项B:"+exam.getOptionB()+",选项C:"+exam.getOptionC()+",选项D:"+exam.getOptionD()+",答案:"+exam.getAnswer());
					}
				}else{
					System.out.println("查询失败, 请检查您输入的ID");
				}
				
			}
				break;
			case 9:{
				// 批量导入考题
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
					System.out.println("数据已全部导入");
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
	 * 交互后产生的结果
	 */
	private static void messgeType() {
		int type = (int) fromData.get("msgType");
		switch (type) {
		case 20004:
			System.out.println("修改成功");
			break;
		case 20005:
			System.out.println("修改失败");
			break;
		case 20006:
			System.out.println("添加成功");
			break;
		case 20007:
			System.out.println("添加失败");
			break;
		case 20008:
			System.out.println("删除成功");
			break;
		case 20009:
			System.out.println("删除失败");
			break;
		case 20010:
			System.out.println("修改成功");
			break;
		case 20011:
			System.out.println("修改失败");
			break;
		case 20012:
			String userInfo = (String) fromData.get("userInfo");
			if (userInfo == null) {
				System.out.println("查询失败, 请检查您输入的帐号");
			} else {
				System.out.println("您查询的信息如下:");
				System.out.println(userInfo);
			}
			break;
		default:
			break;
		}
	}

	/**
	 * 向流中写入所需的操作
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
	 * 读取流中的集合
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
