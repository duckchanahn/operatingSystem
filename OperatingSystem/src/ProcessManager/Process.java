package ProcessManager;

import java.util.Vector;

import CPU.Register;
import CPU.Status;

public class Process {
	public enum EState {nnew, running, wait, ready, terminate};
//	this.controlUnit.connect(this.pc, this.mar, this.mbr, this.ir, this.sp, this.ac, this.status);
	public enum ERegister {ePC, eMAR, eMBR, eIR, eSP, eCS, eAC};
	public enum EInterrupt {eIO, eTerminate};
	
	// PCB, 코드세그먼트, 데이터 세그먼트(스택, 힙)
	private PCB pcb;
	private Segment codeSegment;
	public int[] getCodeSegment() {return this.codeSegment.memory;}
	private Segment stackSegment;
	public int[] getStackSegment() {return this.stackSegment.memory;}
	
	private EState eState;

	//working variables
	private int timeSlice;
	
	public PCB getPcb() {return pcb;}
	public void setPcb(PCB pcb) {this.pcb = pcb;}

	
	public Process(int stackSegmentSize, int[] codes) {
		this.eState = EState.ready;
		this.pcb = new PCB();
		this.codeSegment = new Segment(codes);
		this.stackSegment = new Segment(stackSegmentSize);
	}

	public void initialize(int timeSlice) {
		this.pcb.seteState(Process.EState.running);
	       /* 실행을 한거니까 얘가 가진 정보(pcb)를 모두 cpu로 옮겨줘야함
	       * 그래서 CPU랑 association이 되어있어야함
	       */
		this.timeSlice = timeSlice;
	}
//	public EState executeALine() {	//코드. CPU 짜는 중
//		int instruction = this.codeSegment.fetch(this.pcb.getRegisters().get(ERegister.ePC.ordinal()).get());	//코드세그먼트에서 한 줄 받아온 것. CPU에게 실행하라고 해야
//		//인터럽트 걸렸는지 확인해야 함
//		///////////////////////////
//		//cpu execute 가져올거래
//		///////////////////////////
//		if(this.pcb.getRegisters().get(ERegister.eStatus.ordinal()).get() == EInterrupt.eIO.ordinal()) {	//IHR에 따로 넣쟝.
//			return Process.EState.wait;
//		}else if(this.pcb.getRegisters().get(ERegister.eStatus.ordinal()).get() == EInterrupt.eTerminate.ordinal()) {
//			return Process.EState.terminate;
//		}else {
//			this.timeSlice--;
//			if(this.timeSlice == 0) {
//			return Process.EState.ready;
//			}
//		}
//		return null;
//	}

	////////////////////////////////////////////////////프로세스

	public class PCB {
		// 일단 귀찮으니까 나중에 정리하고 여기 만듦
		private int id;
		private EState eState;
		//PC는 빈번한 사용때문에 빼놓음
		private Vector<Register> registers;
		private Status status;
		public PCB() {
			registers = new Vector<Register>();
			for(ERegister eRegister: ERegister.values()) {
				this.registers.add(new Register());	//메모리 만들기
			}
			status = new Status();
		}
		
		public int getId() {return id;}
		public void setId(int id) {this.id = id;}
		public EState geteState() {return eState;}
		public void seteState(EState eState) {this.eState = eState;}
		public Vector<Register> getRegisters() {return registers;}
		public Status getStauts() {return status;}
		public void setRegisters(Vector<Register> registers) {this.registers = registers;}
		public void setStatus(Status status) {this.status = status;}
	}

	///////////////////////////////////////////////////PCB
	
	private class Segment {
		private int[] memory;
		public Segment(int size) {
			//바이트 사이즈 만큼 할당
			this.memory = new int[size];		
		}
		public Segment(int[] memory) {	
			this.memory = memory;		
		}
		
		public int getSize() {return memory.length;}
	//코드세그먼트가 들어왔는지 데이터가 들어왔는지 알 방법이 아직 없다.
		public void store(int address, int data) {	//데이터 넣는 용도
			this.memory[address] = data;
		}
		public int fetch(int address) {
			return this.memory[address];
		}
	}
	
	//////////////////////////////////////////////////세그먼트
	
}
