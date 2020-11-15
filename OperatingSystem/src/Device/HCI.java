package Device;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import GUI.CMD;
import GUI.Login;
import GUI.Taskbar;

public class HCI extends JFrame{
	
	private Taskbar taskbar;
	private CMD CMD;
	private Login login;

	public HCI() {
//		this.setSize(Dimension );
		this.setLocation(450, 200);
		this.setLayout(new BorderLayout());
		this.setSize(700, 500);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.taskbar = new Taskbar();
		this.add(this.taskbar, BorderLayout.SOUTH);
		
		this.CMD = new CMD();
		this.add(this.CMD, BorderLayout.CENTER);
	}
		
	public void initialize() {
	}

	public void run() {
//		this.login.login();
		// TODO Auto-generated method stub
		
	}

	public void mouse(int x, int y) {
		
	}
}
