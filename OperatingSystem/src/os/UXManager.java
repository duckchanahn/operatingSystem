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
	private Compiler compiler; // 지금 컴파일러가 아닌 자바를 기계어로 번역하는 컴파일러
	private Login login;

	public UXManager() {
		this.login = new Login();
	}
	
	public void associate(FileManager fileManager, ProcessManager processManager, Loader loader, Compiler compiler) {
		this.fileManager = fileManager;	//화면 촤아악 보여줘야함, 프로세스 매니저한테 파일 줘야함
		this.processManager = processManager;
		this.loader = loader;
		this.compiler = compiler;

	}
	
	public void initialize() {
		
	}
	
	public void run() {
		
		Scanner scan = new Scanner(System.in);
		System.out.print("아이디 : ");
		String id = scan.next();
		System.out.print("비밀번호 : ");
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
			System.out.println("로그인에 실패하였습니다.");
		}
	}
}
