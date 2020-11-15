package CPU;

import Memory.Memory;

public class CentrolProcessingUnit {
	
	//component
	private ControlUnit controlUnit;
	private ArithmeticLogicUnit arithmeticLogicUnit;
	
	//association
	private Register pc, cs, sp, mar, mbr, ir, ac;
	private Status status;
	private Memory memory;
//	private Loader loader;
	
	public Register getPC() {return controlUnit.getPC();}
	public Register getCS() {return controlUnit.getCS();}
	public Register getSP() {return controlUnit.getSP();}
	public Register getAC() {return controlUnit.getAC();}
	public Status getStatus() {return controlUnit.getStatus();}
	
	public void setPCB(Register pc, Register cs, Register sp, Register ac, Status status) {
//		this.controlUnit.connect(pc, this.mar, this.mbr, this.ir, sp, cs, ac, status);
		this.controlUnit.setPC(pc);
		this.controlUnit.setCS(cs);
		this.controlUnit.setSP(sp);
		this.controlUnit.setAC(ac);
		this.controlUnit.setStatus(status);
		
		this.arithmeticLogicUnit.setAC(ac);
		this.arithmeticLogicUnit.setStatus(status);
//		this.arithmeticLogicUnit.connect(this.ac, this.mbr, status);
//		this.controlUnit.connect(this.arithmeticLogicUnit);
	}
	
	public CentrolProcessingUnit() {
		//component
		this.controlUnit = new ControlUnit();
		this.arithmeticLogicUnit = new ArithmeticLogicUnit();
		this.pc = new Register();
		this.cs = new Register();
		this.sp = new Register();
		this.mar = new Register();
		this.mbr = new Register();
		this.ir = new Register();
		this.ac = new Register();
		this.status = new Status();
		
		this.controlUnit.connect(this.pc, this.mar, this.mbr, this.ir, this.sp, this.cs, this.ac, this.status);

		this.arithmeticLogicUnit.connect(this.ac, this.mbr,this.status);
		
		this.controlUnit.connect(this.arithmeticLogicUnit);
		
	}

	public void connect(Memory memory) {
		this.memory = memory;
		this.memory.connect(this.mar, this.mbr);
		this.controlUnit.connect(this.memory);
	}

	public void run() {
//		System.out.println("!!!!!!!!!"); 
		 this.controlUnit.run();
	}
}
