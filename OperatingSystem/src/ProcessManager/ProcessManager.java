package ProcessManager;

import java.util.Scanner;
import java.util.Vector;

import CMD.CMD;
import CPU.CentrolProcessingUnit;
import Device.Monitor;
import Interrupt.InterruptHandler;
import os.MemoryManager;

public class ProcessManager {
	// 인터럽트 담아내는 flag 필요
//	private enum EInterrupt {eNone, eTimeOut, eIOStarted, eIOFinished, eTerminated;}


	// association
	// private FileManager fileManager;
	private MemoryManager memoryManager;

	public Dispatcher dispatcher;
	private CentrolProcessingUnit CPU;
	
	public InterruptHandler interruptHandler;

	private Monitor monitor;
	private CMD shell;
	
	// constructor 생성자
	public ProcessManager() {

		this.dispatcher = new Dispatcher();
		this.CPU = new CentrolProcessingUnit();
		this.interruptHandler = new InterruptHandler();
	}

	public void associate(MemoryManager memoryManager, Monitor monitor) {
		this.memoryManager = memoryManager;

		this.dispatcher.associate(CPU, memoryManager);
		this.monitor = monitor;
		this.interruptHandler.associate(dispatcher, monitor);
		this.memoryManager.associate(CPU, interruptHandler);
	}

	public boolean execute() {
		if(this.dispatcher.checkReady()) {
			this.CPU.run();
			return true;
		}
//		else {
//			return false;
//		}
		return false;
	}

//	Scanner console = new Scanner(System.in);
//	boolean systemRunning = true;
//		while(systemRunning) {				
//		 String consoleText = console.nextLine();
//		boolean onShell = true;
//        while(onShell) {
//           Vector<Process> processes = this.shell.interpret(consoleText);
//           this.dispatcher.enQueueMultiple(processes);
//           onShell = false;
//        }
	
	
	public void run() {
				this.dispatcher.setCPU(this.dispatcher.currentProcess());
				this.interruptHandler.resetTimer();
				Scanner console = new Scanner(System.in);
				
				while (true) { // bRunning
					
					this.interruptHandler.finish();
					this.interruptHandler.checkTime();
					this.interruptHandler.process();
					this.execute();
					if(!this.dispatcher.checkReady() && !this.dispatcher.checkIO()) {
//						boolean systemRunning = true;
//							while(systemRunning) {				
						System.out.println("[ Start fileName, newFile, newDirectory ]");
							 String consoleText = console.nextLine();
							boolean onShell = true;
					        while(onShell) {
					           this.dispatcher.enQueue(this.shell.interpret(consoleText));
					           this.dispatcher.setCPU(this.dispatcher.currentProcess());
					           this.interruptHandler.resetTimer();
					           onShell = false;
//					        }
						}
					}
				}
 			}
//			console.close();
//	}

	public void initShell(CMD shell) {
		// TODO Auto-generated method stub
		this.shell = shell;
	}
}
