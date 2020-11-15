package Memory;

import CPU.Register;
import Interrupt.Interrupt;
import Interrupt.InterruptHandler;
import Interrupt.InterruptHandler.EInterrupt;

public class Memory {
	
	int buffer[];
	public int[] getBuffer() {return this.buffer;}
//	public int limit;
//	Loader loader;
	public void setBuffer(int[] buffer) {this.buffer = buffer;}

	private Register mar, mbr;
	public void connect(Register mar, Register mbr) {
		this.mar = mar;
		this.mbr = mbr;
	}

	public Memory() {
		this.buffer = new int[1000];
		this.DMA = new int[100];
	}
	
	public void fetch() {
		this.mbr.setData(this.buffer[this.mar.getData()]);
	}
	
	public void store() {
		this.buffer[this.mar.getData()] = this.mbr.getData();
	}
	
	int DMA[];
	private InterruptHandler interruptHandler;
	public void association(InterruptHandler interruptHandler) {
		this.interruptHandler = interruptHandler;
	}
//	public InterruptHandler getIHR() {return this.interruptHandler;}
//	public void setinterruptPID(int PID) {this.interruptHandler.setPID(PID);}
	public void ihr(int parameter, int eType, int PID, int interruptID) {
		Interrupt interrupt = new Interrupt();
//		System.out.println(EInterrupt.values()[eType] + "인터럽트가 발생해버렸어");
		interrupt.seteType(EInterrupt.values()[eType]);
		interrupt.setParameter(parameter);
		interrupt.setPID(PID);
		interrupt.setinterruptID(interruptID);
		this.interruptHandler.addInterrupt(interrupt);
	}
}
