package Device;

import Memory.Memory;

public class DMA {
	private Memory memory;
	
	public DMA() {
		
	}
	public void setDMA(Memory memory) {this.memory = memory;}
	public Memory getDMA() {return memory;}
	// Memory.getData : ���μ��� ���� �޾ƿ�
	
	// Memory.setData : read�ؼ� �о�� ���� �޾ƿ;ߵ�
	
	// Memory.setDeviceInterrupt : Error, Finish
	
}
