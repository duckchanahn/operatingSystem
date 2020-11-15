package ProcessManager;

import java.util.Vector;

import ProcessManager.Process.EState;

public class ProcessQueue {
	
	private Vector<Process> queue;
	public Vector<Process> getQueue() {return queue;}
	
	private int size;
	public int getSize() {return size;}
	
	public ProcessQueue() {
//		size = 10;
		queue = new Vector<Process>();
	}

	public void enQueue(Process process) {
		queue.add(process);
//		size++;
	}

	public Process deQueue(int PID) {
		for(int i = queue.size()-1; i >= 0; i--) {
			if(PID == queue.elementAt(i).getPcb().getId()) {
				return queue.elementAt(i);
			}
		}
		return null;
	}
	
	public void deleteQueue(int PID) {
		for(int i = queue.size()-1; i >= 0; i--) {
			if(PID == queue.elementAt(i).getPcb().getId()) {
				queue.remove(i);
			}
		}
	}
	
	
	public Process currentProcess() {
		if(queue.size() > 0) {
//			for(int i = 0; i < queue.size(); i++) {
//				System.out.println(this.queue.get(i)); 
//				}
//			System.out.println(this.queue.elementAt(0) + "0¹øÂ°");
		return this.queue.elementAt(0);
		} else {
			return null;
		}
		
	}
	
	public void setState() {
		if(queue.size() > 0)
			this.queue.elementAt(0).getPcb().seteState(EState.running);
		for(int i = 1; i < queue.size(); i++)
			this.queue.elementAt(i).getPcb().seteState(EState.ready);
	}
}
