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
	// 动画显示的文本字符串
	private String introduction = "现在大家已经对计算机很熟悉了，如今计算机的操作"
		+ "系统可以同时执行多个任务，在听歌的同时能够打字、下载文件，在聊天窗口打"
		+ "字的时候，对方同时还能通过视频看到你；听到你。这一切都是使用多任务实现"
		+ "的，Java语言使用多线程实现一个程序中的多个任务同时运行。程序员可以在程"
		+ "序中执行多个线程，每一个线程完成一个功能，并与其他线程并发执行，这种机" 
		+ "制被称为多线程。";
	JTextArea textArea; // 文本域组件
	JLabel label;
	Thread thread;
	JButton startButton;
	JButton pauseButton;
	JButton resumeButton;
	Boolean move = true;
	public RunnableDemo() {
		setTitle("线程的控制");
		label = new JLabel("多线程简介："); // 标签组件
		getContentPane().add(label, BorderLayout.NORTH);// 添加标签到窗体
		textArea = new JTextArea("\t"); // 初始化文本域组件
		textArea.setBorder(new BevelBorder(BevelBorder.LOWERED));// 设置边框
		textArea.setLineWrap(true); // 设置自动折行
		getContentPane().add(textArea, BorderLayout.CENTER);// 添加文本域组件到文本框
		getContentPane().add(getSouthPanel(), BorderLayout.SOUTH);
		setBounds(100, 100, 383, 225); // 设置窗体大小位置
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true); // 显示窗体
		thread = new Thread(this); //创建thread线程，当前窗口做为该线程的目标对象

	}
	//Runnable接口方法，是线程的执行方法
	@Override
	public void run() {
		String[] intros = introduction.split(""); // 将字符串分割为数组

		for (String ch : intros) { // ForEach遍历字符串数组
			textArea.append(ch); // 添加一个字符到文本域
			while (!move) {
				pause();
			}
			try {
				Thread.sleep(100); // 线程休眠0.1秒
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		startButton.setEnabled(true);
	}


	public JPanel getSouthPanel() {
		JPanel panel = new JPanel();
		startButton = new JButton("开始");
		pauseButton = new JButton("暂停");
		resumeButton = new JButton("继续");
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
				//事件源判断
				if (e.getSource() == startButton) {
					thread.start(); //启动thread线程
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
			System.out.println("停止了");
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
			System.out.println("恢复了");
			notifyAll();
		}
	}

	public static void main(String args[]) {
		new RunnableDemo(); // 创建本类实例对象
	}
}
