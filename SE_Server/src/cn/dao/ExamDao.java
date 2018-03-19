package cn.dao;

import java.util.HashMap;

import cn.bean.Exam;
import cn.bean.User;
import cn.bean.UserAndExam;

public interface ExamDao {

	/**
	 * ѧԱ��ȡ��������
	 * @return �������⵽ѧ����
	 */
	HashMap<Integer,Exam> problems();
	
	/**
	 * �ж�ѧ���ɼ�
	 * @param hashMap
	 * @return ����ѧ���ɼ�
	 */
	int judge(HashMap<Integer,Exam> hashMap);
	
	/**
	 * @param id �û���ID��������û��ļ�¼
	 * ��ѯѧ���ɼ�
	 * @return ����ѧ���ɼ�
	 */
	HashMap<Integer,UserAndExam> queryScore(int id);
	
	
	/**
	 * ���ڹ���Ա����  ������������
	 * @param user  Ҫ��ӵ�������Ϣ , ����������,ѡ��A,ѡ��B,ѡ��C,ѡ��D,��
	 * 
	 * @return �����������Ľ��, �Լ�ʧ�ܵ�ԭ�� !<br>
	 * 		����	1:	����ɹ� !
	 * 			2:	����ʧ�� ,���ݴ���
	 */
	int insert(Exam exam);
	
	/**
	 * ���ڹ���Ա ���� ��ͨѧԱ��ɾ��
	 * @param username  Ҫɾ�����û����ʺ�
	 * @return ɾ���Ľ�� true��ʾɾ���ɹ�!
	 */
	boolean delete(int id);
	
	/**
	 * ���ڹ���Ա�޸�ѧԱ����Ϣ
	 * @param username	Ҫ�޸ĵ�ѧԱ���ʺ�
	 * @param user	�µ�ѧԱ��Ϣ, ����: ����,�ǳ�!<br>
	 * 	������Ϊnullʱ, ��ʾ�޸��ǳ�!<br>
	 * 	���ǳ�Ϊnullʱ, ��ʾ�޸����� !
	 * @return �޸ĵĽ��  true��ʾ�ɹ�
	 */
	boolean update(int id,Exam exam);
	/**
	 * ���ڹ���Ա��ѯ������Ŀ
	 * @param id Ҫ��ѯ��id,��id����0ʱ��ѯ��������
	 * @return ��ѯ�����Ľ��
	 */
	HashMap<Integer,Exam> findIdByExam(int id);
}
