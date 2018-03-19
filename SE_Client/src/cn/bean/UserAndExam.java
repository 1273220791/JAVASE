package cn.bean;

import java.io.Serializable;

public class UserAndExam implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private int user_id;
	private String exam_id;
	private String user_result;
	private String exam_time;
	private int souce;
	
	
	
	public UserAndExam(String user_result, String exam_time, int souce) {
		super();
		this.user_result = user_result;
		this.exam_time = exam_time;
		this.souce = souce;
	}
	public UserAndExam(int id, int user_id, String exam_id, String user_result, String exam_time, int souce) {
		super();
		this.id = id;
		this.user_id = user_id;
		this.exam_id = exam_id;
		this.user_result = user_result;
		this.exam_time = exam_time;
		this.souce = souce;
	}
	public UserAndExam() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getExam_id() {
		return exam_id;
	}
	public void setExam_id(String exam_id) {
		this.exam_id = exam_id;
	}
	public String getUser_result() {
		return user_result;
	}
	public void setUser_result(String user_result) {
		this.user_result = user_result;
	}
	public String getExam_time() {
		return exam_time;
	}
	public void setExam_time(String exam_time) {
		this.exam_time = exam_time;
	}
	public int getSouce() {
		return souce;
	}
	public void setSouce(int souce) {
		this.souce = souce;
	}
	
	
}
