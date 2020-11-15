package os;

import CMD.CMD;
import Device.Monitor;
import FileManager.FileManager;
import ProcessManager.ProcessManager;

public class OperatingSystem {

	private UXManager uxManager;	//UI
	
	private ProcessManager processManager;
	private MemoryManager memoryManager;
	private FileManager fileManager;
	// I/O매니저 빠짐
	
	private Loader loader;
	private Compiler compiler;
	private CMD shell;
	
	public OperatingSystem() {
		this.processManager= new ProcessManager();
		this.memoryManager = new MemoryManager();	
		this.fileManager = new FileManager();
		
		this.loader = new Loader();
		this.compiler = new Compiler();
		this.uxManager = new UXManager();
		this.shell = new CMD();
	}
	
	public void initialize() {
		this.login(); // UX매니저로 갈 예정
//		this.uxManager.initialize();
//		public void initialize() {
//			this.login();
//		}
	}
	public void finalize() {
		
	}
	
	public void associate(Monitor monitor) {
		this.loader.association(memoryManager);
		this.processManager.associate(this.memoryManager, monitor);
		this.uxManager.associate(this.fileManager, this.processManager, this.loader, this.compiler);
		this.processManager.initShell(this.shell);
		this.shell.associate(fileManager, compiler, loader);
	}
	
	public void run() {
		uxManager.run();
	}

	public void login() {
		// login이랑 회원가입 통해서 사용자마다 배경화면, 배치 다르게 --> 파일은 공유
	}
}