package cn.bean;

import java.io.Serializable;

public class Exam implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4147753380146449620L;
	private int id; // ����Լ�� ���
	private String problems; // ����
	private String optionA; // ѡ��A
	private String optionB; // ѡ��B
	private String optionC; // ѡ��C
	private String optionD; // ѡ��D
	private String answer; // ��
	
	public Exam(int id, String answer) {
		super();
		this.id = id;
		this.answer = answer;
	}
	
	

	public Exam(String problems, String optionA, String optionB, String optionC, String optionD, String answer) {
		super();
		this.problems = problems;
		this.optionA = optionA;
		this.optionB = optionB;
		this.optionC = optionC;
		this.optionD = optionD;
		this.answer = answer;
	}



	public Exam(int id, String problems, String optionA, String optionB, String optionC, String optionD) {
		super();
		this.id = id;
		this.problems = problems;
		this.optionA = optionA;
		this.optionB = optionB;
		this.optionC = optionC;
		this.optionD = optionD;
	}

	public Exam(int id, String problems, String optionA, String optionB, String optionC, String optionD,
			String answer) {
		super();
		this.id = id;
		this.problems = problems;
		this.optionA = optionA;
		this.optionB = optionB;
		this.optionC = optionC;
		this.optionD = optionD;
		this.answer = answer;
	}

	public Exam() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getProblems() {
		return problems;
	}

	public void setProblems(String problems) {
		this.problems = problems;
	}

	public String getOptionA() {
		return optionA;
	}

	public void setOptionA(String optionA) {
		this.optionA = optionA;
	}

	public String getOptionB() {
		return optionB;
	}

	public void setOptionB(String optionB) {
		this.optionB = optionB;
	}

	public String getOptionC() {
		return optionC;
	}

	public void setOptionC(String optionC) {
		this.optionC = optionC;
	}

	public String getOptionD() {
		return optionD;
	}

	public void setOptionD(String optionD) {
		this.optionD = optionD;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	@Override
	public String toString() {
		return "exam [id=" + id + ", problems=" + problems + ", optionA=" + optionA + ", optionB=" + optionB
				+ ", optionC=" + optionC + ", optionD=" + optionD + ", answer=" + answer + "]";
	}

}
