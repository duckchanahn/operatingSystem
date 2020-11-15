package ProcessManager;

import java.util.Vector;

import CPU.CentrolProcessingUnit;
import Interrupt.InterruptHandler;
import Interrupt.InterruptHandler.EInterrupt;
import ProcessManager.Process.ERegister;
import os.MemoryManager;

public class Dispatcher {

		private ProcessQueue readyQueue;
		private ProcessQueue ioQueue; 
		
		private Process currentProcess;
		private CentrolProcessingUnit CPU;
		
		private MemoryManager memoryManager;
		
		public void associate(CentrolProcessingUnit CPU, MemoryManager memoryManager) {
			this.CPU = CPU;
			this.memoryManager = memoryManager;
		}

		public Dispatcher() {
			this.readyQueue = new ProcessQueue();
			this.ioQueue = new ProcessQueue();
		}
		
		public void enQueue(Process process) {
			this.readyQueue.enQueue(process);
		}
		
		public Process deQueue(int PID) {
			if (readyQueue.deQueue(PID) == null) {
				return ioQueue.deQueue(PID);
			} else {
				return readyQueue.deQueue(PID);
			}
		}

		public void deleteQueue(int PID) {
			this.ioQueue.deleteQueue(PID);
			this.readyQueue.deleteQueue(PID);
		}
		
		public void contextSwitching(EInterrupt eInterrupt, int PID) {
			
			System.out.println("-------컨텍스트스위칭-------");

			if(eInterrupt.equals(EInterrupt.eIOFinished)) {
				System.out.println("PID : " + PID +"\n" +  " wait Queue --> Ready Queue");
				Process temp = this.deQueue(PID);
				this.deleteQueue(PID);
				this.enQueue(temp);
			} else {
				this.currentProcess = this.deQueue(PID);
				this.setProcess();				
			
			if(eInterrupt.equals(EInterrupt.eIOStarted)) {
				System.out.println("PID : " + PID +"\n" +  " Ready Queue --> wait Queue");
				this.deleteQueue(this.currentProcess.getPcb().getId());
				this.ioQueue.enQueue(this.currentProcess);
				
			} else if(eInterrupt.equals(EInterrupt.eProcessTerminated)) {
				System.out.println("PID : " + PID + "\n" + " Delete Queue");
				this.deleteQueue(this.currentProcess.getPcb().getId());
//				System.out.println("ready Queue : "  + this.readyQueue.getQueue().size());
				this.memoryManager.terminated(PID);
				
				
			} else if(eInterrupt.equals(EInterrupt.eTimerOut)) {
				System.out.println("PID : " + PID );
				this.deleteQueue(this.currentProcess.getPcb().getId());
				this.enQueue(this.currentProcess);
				
			}
				this.readyQueue.setState();
			
				if(this.readyQueue.getQueue().size() > 0)
				this.setCPU(this.currentProcess());
			}
			
			System.out.print("Ready Q [ ");
			for(int i = 0; i < this.readyQueue.getQueue().size(); i++) {
				System.out.print(this.readyQueue.getQueue().get(i).getPcb().getId() + " ");
			}
			System.out.println("]");
			System.out.print("Wait Q [ ");
			for(int i = 0; i < this.ioQueue.getQueue().size(); i++) {
				System.out.print(this.ioQueue.getQueue().get(i).getPcb().getId() + " ");
			}
			System.out.println("]");
			System.out.println("----------------------");
		}
		
		public void setCPU(Process process) {
			this.CPU.setPCB(process.getPcb().getRegisters().get(Process.ERegister.ePC.ordinal()),
					process.getPcb().getRegisters().get(Process.ERegister.eCS.ordinal()),
					process.getPcb().getRegisters().get(Process.ERegister.eSP.ordinal()),
					process.getPcb().getRegisters().get(Process.ERegister.eAC.ordinal()),
					process.getPcb().getStauts());
		}
		
		public void setProcess() {
			this.currentProcess.getPcb().getRegisters().get(Process.ERegister.ePC.ordinal()).setData(CPU.getPC().getData()); 
			this.currentProcess.getPcb().getRegisters().get(Process.ERegister.eAC.ordinal()).setData(CPU.getAC().getData()); 
			this.currentProcess.getPcb().setStatus(CPU.getStatus());
		}

		public Process currentProcess() {
			return this.readyQueue.currentProcess();
		}

		public boolean checkReady() {
			if(this.readyQueue.getQueue().size() > 0) {
				return true;
			} else {
				return false;
			}
		}
		public boolean checkIO() {
			if(this.ioQueue.getQueue().size() > 0) {
				return true;
			} else {
				return false;
			}
		}

	}