package Device;

import Memory.Memory;

public class DMA {
	private Memory memory;
	
	public DMA() {
		
	}
	public void setDMA(Memory memory) {this.memory = memory;}
	public Memory getDMA() {return memory;}
	// Memory.getData : 프로세스 정보 받아옴
	
	// Memory.setData : read해서 읽어온 내용 받아와야됨
	
	// Memory.setDeviceInterrupt : Error, Finish
	
}
