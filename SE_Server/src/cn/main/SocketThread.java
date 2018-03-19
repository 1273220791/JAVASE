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
	 * 与客户端连接的Socket对象
	 */
	private Socket socket;
	/**
	 * 来自客户端的消息 
	 */
	private HashMap<String,Object> fromData;
	/**
	 * 准备发送给客户端的消息
	 */
	private HashMap<String,Object> toData;
	/**
	 * 用来接收客户端消息的 流
	 */
	private ObjectInputStream ois;
	/**
	 * 用来向客户端发送消息的  流
	 */
	private ObjectOutputStream oos;
	/**
	 * 标志着  客户端是否选择了退出 !
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
		System.out.println("客户端已断开~ ");
	}
	
	private void fromMessage() {
		try {
			fromData = (HashMap<String, Object>) ois.readObject();
			int msgType = (int) fromData.get("msgType");
			switch (msgType) {
			case 10010:{//用户登录
				//得到登录的用户信息
				User user = (User) fromData.get("user");
				//登录结果为result
				int[] result = UserService.login(user.getUsername(), user.getPassword());
				toData = new HashMap<>();
				toData.put("msgType", 20000+result[0]);
				toData.put("id", result[1]);
			}
			break;
			case 10011:{//学员修改密码
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
			case 10012:{//学员开始考试
				toData = new HashMap<>();
				HashMap<Integer,Exam> hs = ExamService.problems();
				toData.put("exam", hs);
				toMessage();//发送到客户端
				hs = (HashMap<Integer, Exam>) ois.readObject();//读取消息
				int num = ExamService.judge(hs);
				toData = new HashMap<>();
				toData.put("num", num);
			}
			break;
			case 10013:{//查询所有考试成绩
				int id = (int) ois.readObject();//读取消息
				System.out.println("查询所有考试成绩:"+id);
				toData = new HashMap<>();
				HashMap<Integer,UserAndExam> hs = ExamService.queryScore(id);
				toData.put("score", hs);
			}
			break;
			
			
			case 10021:{//添加学员
				User user = (User) fromData.get("user");
				//将学员插入数据库
				int result = UserService.insert(user);
				toData = new HashMap<>();
				if(result==1) {
					toData.put("msgType",20006);
				}else {
					toData.put("msgType",20007);
				}
			}
			break;
			case 10022:{//删除学员
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
			case 10023:{//修改学员
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
			
			case 10024:{//查询学员
				String username = (String) fromData.get("username");
				User user = UserService.findUserByUsername(username);
				String userInfo = null;
				if(user!=null) {
					userInfo = "这个用户的id:"+user.getId()+", 帐号:"+user.getUsername()+", 密码:"+user.getPassword()+",昵称:"+user.getNickname();
				}
				
				toData = new HashMap<>();
				toData.put("msgType",20012);
				toData.put("userInfo",userInfo);
			}
			break;
			
			case 10025:{//增加考题
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

			case 10026:{//删除考题
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
			
			case 10027:{//修改考题
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
			
			case 10028:{//查询考题
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
