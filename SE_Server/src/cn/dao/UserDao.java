package cn.dao;

import cn.bean.User;

public interface UserDao {

	/**
	 * ���ڹ���Ա����  ��ͨѧԱ�����
	 * @param user  Ҫ��ӵ���ͨѧԱ��Ϣ , �������ʺ�, ����, �ǳ�!
	 * 
	 * @return �������ѧԱ�Ľ��, �Լ�ʧ�ܵ�ԭ�� !<br>
	 * 		����	1:	����ɹ� !
	 * 			2:	����ʧ�� ,�û����ظ�
	 */
	int insert(User user);
	
	/**
	 * ���ڹ���Ա ���� ��ͨѧԱ��ɾ��
	 * @param username  Ҫɾ�����û����ʺ�
	 * @return ɾ���Ľ�� true��ʾɾ���ɹ�!
	 */
	boolean delete(String username);
	/**
	 * ����ѧԱ�޸���������
	 * @param username	�û����ʺ�
	 * @param oldPassword	ѧԱ�ľ�����
	 * @param newPassword   ѧԱ��������
	 * @return �޸ĵĽ��  true��ʾ�ɹ�
	 */
	boolean updatePassword(String username,String oldPassword,String newPassword);
	
	/**
	 * ���ڹ���Ա�޸�ѧԱ����Ϣ
	 * @param username	Ҫ�޸ĵ�ѧԱ���ʺ�
	 * @param user	�µ�ѧԱ��Ϣ, ����: ����,�ǳ�!<br>
	 * 	������Ϊnullʱ, ��ʾ�޸��ǳ�!<br>
	 * 	���ǳ�Ϊnullʱ, ��ʾ�޸����� !
	 * @return �޸ĵĽ��  true��ʾ�ɹ�
	 */
	boolean update(String username,User user);
	/**
	 * ���ڹ���Ա��ѯѧԱ��Ϣ
	 * @param username Ҫ��ѯ���û��ʺ�
	 * @return ��ѯ�����û�����,  ���� null , null��ʾ��ѯʧ�� !
	 */
	User findUserByUsername(String username);
	/**
	 * ���ڵ�¼���� (����Ա���û�)
	 * @param username  Ҫ��½���ʺ�
	 * @param password  Ҫ��½������
	 * @return ��¼�Ľ��<br>
	 * 	1.	��ʾ����Ա��¼�ɹ�<br>
	 * 	2.	��ʾѧԱ��¼�ɹ�<br>
	 * 	3.	��ʾ��¼ʧ�� , �ʺŻ�������� !<br>
	 */
	int[] login(String username,String password);
}
