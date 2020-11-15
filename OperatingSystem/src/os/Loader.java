package os;

import ProcessManager.Process;

public class Loader {
	
	private MemoryManager memoryManager;
	
	public void association(MemoryManager memoryManager) {
		this.memoryManager = memoryManager;
	}
	
	public Process load(Process process) {
		return this.memoryManager.allocate(process);

	}	
}
