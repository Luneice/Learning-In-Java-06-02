import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.BevelBorder;
public class RunnableDemo extends JFrame implements Runnable {	
	// ������ʾ���ı��ַ���
	private String introduction = "���ڴ���Ѿ��Լ��������Ϥ�ˣ���������Ĳ���"
		+ "ϵͳ����ͬʱִ�ж�������������ͬʱ�ܹ����֡������ļ��������촰�ڴ�"
		+ "�ֵ�ʱ�򣬶Է�ͬʱ����ͨ����Ƶ�����㣻�����㡣��һ�ж���ʹ�ö�����ʵ��"
		+ "�ģ�Java����ʹ�ö��߳�ʵ��һ�������еĶ������ͬʱ���С�����Ա�����ڳ�"
		+ "����ִ�ж���̣߳�ÿһ���߳����һ�����ܣ����������̲߳���ִ�У����ֻ�" 
		+ "�Ʊ���Ϊ���̡߳�";
	JTextArea textArea; // �ı������
	JLabel label;
	Thread thread;
	JButton startButton;
	JButton pauseButton;
	JButton resumeButton;
	Boolean move = true;
	public RunnableDemo() {
		setTitle("�̵߳Ŀ���");
		label = new JLabel("���̼߳�飺"); // ��ǩ���
		getContentPane().add(label, BorderLayout.NORTH);// ��ӱ�ǩ������
		textArea = new JTextArea("\t"); // ��ʼ���ı������
		textArea.setBorder(new BevelBorder(BevelBorder.LOWERED));// ���ñ߿�
		textArea.setLineWrap(true); // �����Զ�����
		getContentPane().add(textArea, BorderLayout.CENTER);// ����ı���������ı���
		getContentPane().add(getSouthPanel(), BorderLayout.SOUTH);
		setBounds(100, 100, 383, 225); // ���ô����Сλ��
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true); // ��ʾ����
		thread = new Thread(this); //����thread�̣߳���ǰ������Ϊ���̵߳�Ŀ�����

	}
	//Runnable�ӿڷ��������̵߳�ִ�з���
	@Override
	public void run() {
		String[] intros = introduction.split(""); // ���ַ����ָ�Ϊ����

		for (String ch : intros) { // ForEach�����ַ�������
			textArea.append(ch); // ���һ���ַ����ı���
			while (!move) {
				pause();
			}
			try {
				Thread.sleep(100); // �߳�����0.1��
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		startButton.setEnabled(true);
	}


	public JPanel getSouthPanel() {
		JPanel panel = new JPanel();
		startButton = new JButton("��ʼ");
		pauseButton = new JButton("��ͣ");
		resumeButton = new JButton("����");
		startButton.addActionListener(action());
		pauseButton.addActionListener(action());
		resumeButton.addActionListener(action());
		panel.add(startButton);
		panel.add(pauseButton);
		panel.add(resumeButton);
		return panel;
	}
	public ActionListener action() {
		ActionListener actionlistener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//�¼�Դ�ж�
				if (e.getSource() == startButton) {
					thread.start(); //����thread�߳�
					startButton.setEnabled(false);
				}else if (e.getSource() == pauseButton) {
					move = false;

				}else if (e.getSource() == resumeButton) {
					move = true;
					resume();
				}
			}
		};
		return actionlistener;
	}

	public void pause() {
		synchronized (this) {
			System.out.println("ֹͣ��");
			try {
				this.wait();
				startButton.setEnabled(false);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public  void resume() {
		synchronized (this) {
			System.out.println("�ָ���");
			notifyAll();
		}
	}

	public static void main(String args[]) {
		new RunnableDemo(); // ��������ʵ������
	}
}
