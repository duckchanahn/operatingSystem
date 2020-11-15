package CPU;

import Interrupt.InterruptHandler.EInterrupt;
import Memory.Memory;

public class ControlUnit {
	
	public enum EInstruction {LDV, LDA, ADV, ADA, STA, JGZ, CMP, Halt, INT, loop};
	
	private Register pc, sp, cs, mar, mbr, ir, ac;
	public void setPC(Register pc) {this.pc = pc;}
	public void setSP(Register sp) {this.sp = sp;}
	public void setCS(Register cs) {this.cs = cs;}
	public void setAC(Register ac) {this.ac = ac;}
	public void setStatus(Status status) {this.status = status;}
	public Register getPC() {return this.pc;}
	public Register getCS() {return this.cs;}
	public Register getSP() {return this.sp;}
	public Register getAC() {return this.ac;}
	public Status getStatus() {return this.status;}
	private Status status;
	private ArithmeticLogicUnit arithmeticLogicUnit;
	private Memory memory;
	
	public ControlUnit() {
		this.loopIndex = 0;
		this.count = 0;
	}
	
	public void connect(Register pc, Register mar, Register mbr, Register ir, Register sp, Register cs, Register ac, Status status) {
		this.pc = pc;
		this.mar = mar;
		this.mbr = mbr;
		this.ir = ir;
		this.sp = sp;
		this.cs = cs;
		this.ac = ac;
		this.status = status;
	}
	public void connect(ArithmeticLogicUnit arithmeticLogicUnit) {
		this.arithmeticLogicUnit = arithmeticLogicUnit;
	}
	public void connect(Memory memory) {
	      this.memory = memory;
	}
//	public void ihr(int interruptValue, int interruptID, int PID) {
	public void run() {
			this.fetch();
			this.decode();
			if(this.status.interruptCheck()) {
				this.mbr.setData(this.ac.getData());
				this.memory.ihr(this.mbr.getData(), EInterrupt.eIOStarted.ordinal(), (this.cs.getData()/100)+1, this.mar.getData());
				this.status.deleteInterrupt();
			}
	}
	
	private void fetch() {
		int index = this.pc.getData() + this.cs.getData();
		this.mar.setData(index);
		this.memory.fetch();
		this.ir.setData(this.mbr.getData());
	}

	private void decode() {
//		for(int i = 0; i < 300; i++) {}
//		System.out.println("pc : " +this.pc.getData()
//							+ " sp : " + this.sp.getData()
//							+ " cs : " + this.cs.getData()
//							+ " ac : " + this.ac.getData()
//							);
//		System.out.println(EInstruction.values()[(this.memory.getBuffer()[107] >>> 16)] + " " + (this.memory.getBuffer()[107] & 0x0000ffff) + "#!@#!");
		int instruction = this.ir.getData() >>> 16;
//		System.out.println(this.pc.getData());
		switch(EInstruction.values()[instruction]) {
			case LDV:
				LDV();
				break;
			case LDA:
				LDA();
				break;
			case ADV:
				ADV();
				break;
			case ADA:
				ADA();
				break;
			case STA:
				STA();
				break;
			case JGZ:
				JGZ();
				break;
			case CMP:
				CMP();
				break;
			case Halt:
				Halt();
				break;
			case INT:
				INT();
				break;
			case loop:
				loop();
				break;
			default:
				break;
		}
	}

	private void LDA() {
		int address = this.ir.getData() & 0x0000ffff;	
		address = address + this.sp.getData();
		this.mar.setData(address);
		this.memory.fetch();
		this.ac.setData(this.mbr.getData());
		this.pc.setData(this.pc.getData()+1);
	}
	private void LDV() {
		int address = this.ir.getData() & 0x0000ffff;	
		this.ac.setData(address);
		this.pc.setData(this.pc.getData()+1);
	}
	private void STA() {
		int address = this.ir.getData() & 0x0000ffff;	
		address = address + this.sp.getData();
		this.mar.setData(address);
		this.mbr.setData(this.ac.getData());
		this.memory.store();
		this.pc.setData(this.pc.getData()+1);
	}
	private void ADV() {
		int address = this.ir.getData() & 0x0000ffff;	
		this.mbr.setData(address);
		this.arithmeticLogicUnit.add();
		this.pc.setData(this.pc.getData()+1);
	}
	private void ADA() {
		int address = this.ir.getData() & 0x0000ffff;	
		address = address + this.sp.getData();
		this.mar.setData(address);
		this.memory.fetch();
		this.arithmeticLogicUnit.add();
		this.pc.setData(this.pc.getData()+1);
	}
	private void Halt() {
		this.memory.ihr(0, EInterrupt.eProcessTerminated.ordinal(), (this.cs.getData()/100)+1, 0);
		this.pc.setData(this.pc.getData()+1);
	}
	private void INT() {
		this.status.setStatus(1);
		int address = this.ir.getData() & 0x0000ffff;
		this.mar.setData(address);
		this.pc.setData(this.pc.getData()+1);
	}
	int count;
	private void JGZ() {
		if(!this.status.zeroCheck() && !this.status.signCheck()) {
//			System.out.println(loopIndex + "è≥è≥");
			pc.setData(loopIndex);
		}
		this.pc.setData(this.pc.getData()+1);
	}
	private void CMP() {
		int address = this.ir.getData() & 0x0000ffff;
		this.mar.setData(address + this.sp.getData());
		this.memory.fetch();
		this.arithmeticLogicUnit.cmp();
		this.pc.setData(this.pc.getData()+1);
	}
	
	int loopIndex;
	private void loop() {
//		loopIndex = this.pc.getData() + this.cs.getData();
		loopIndex = this.pc.getData();
		this.pc.setData(this.pc.getData()+1);
	}
}
