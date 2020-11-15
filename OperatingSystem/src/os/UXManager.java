package os;

import java.io.File; 
import java.util.Scanner;

import FileManager.FileManager;
import GUI.Login;
import ProcessManager.Process;
import ProcessManager.ProcessManager;

public class UXManager {

	private FileManager fileManager;
	private ProcessManager processManager;
	private Loader loader;
	private Compiler compiler; // ���� �����Ϸ��� �ƴ� �ڹٸ� ����� �����ϴ� �����Ϸ�
	private Login login;

	public UXManager() {
		this.login = new Login();
	}
	
	public void associate(FileManager fileManager, ProcessManager processManager, Loader loader, Compiler compiler) {
		this.fileManager = fileManager;	//ȭ�� �Ҿƾ� ���������, ���μ��� �Ŵ������� ���� �����
		this.processManager = processManager;
		this.loader = loader;
		this.compiler = compiler;

	}
	
	public void initialize() {
		
	}
	
	public void run() {
		
		Scanner scan = new Scanner(System.in);
		System.out.print("���̵� : ");
		String id = scan.next();
		System.out.print("��й�ȣ : ");
		String password = scan.next();
//		scan.close();
		
		if(login.validateUser(id, password)) {
			String fileName = "exe/p1.ppp";
			File file = this.fileManager.getFile(fileName); 
			Process process = compiler.getProcess(file);
			this.loader.load(process);
			this.processManager.dispatcher.enQueue(process);
			
			fileName = "exe/p2.ppp";
			file = this.fileManager.getFile(fileName); 
			process = compiler.getProcess(file);
			this.loader.load(process);
			this.processManager.dispatcher.enQueue(process);
			////////////////////////////////////////////////////////////
			
			this.processManager.run();
		} else {
			System.out.println("�α��ο� �����Ͽ����ϴ�.");
		}
	}
}
