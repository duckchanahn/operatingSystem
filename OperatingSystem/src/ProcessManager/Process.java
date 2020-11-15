package ProcessManager;

import java.util.Vector;

import CPU.Register;
import CPU.Status;

public class Process {
	public enum EState {nnew, running, wait, ready, terminate};
//	this.controlUnit.connect(this.pc, this.mar, this.mbr, this.ir, this.sp, this.ac, this.status);
	public enum ERegister {ePC, eMAR, eMBR, eIR, eSP, eCS, eAC};
	public enum EInterrupt {eIO, eTerminate};
	
	// PCB, �ڵ弼�׸�Ʈ, ������ ���׸�Ʈ(����, ��)
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
	       /* ������ �ѰŴϱ� �갡 ���� ����(pcb)�� ��� cpu�� �Ű������
	       * �׷��� CPU�� association�� �Ǿ��־����
	       */
		this.timeSlice = timeSlice;
	}
//	public EState executeALine() {	//�ڵ�. CPU ¥�� ��
//		int instruction = this.codeSegment.fetch(this.pcb.getRegisters().get(ERegister.ePC.ordinal()).get());	//�ڵ弼�׸�Ʈ���� �� �� �޾ƿ� ��. CPU���� �����϶�� �ؾ�
//		//���ͷ�Ʈ �ɷȴ��� Ȯ���ؾ� ��
//		///////////////////////////
//		//cpu execute �����ðŷ�
//		///////////////////////////
//		if(this.pcb.getRegisters().get(ERegister.eStatus.ordinal()).get() == EInterrupt.eIO.ordinal()) {	//IHR�� ���� ����.
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

	////////////////////////////////////////////////////���μ���

	public class PCB {
		// �ϴ� �������ϱ� ���߿� �����ϰ� ���� ����
		private int id;
		private EState eState;
		//PC�� ����� ��붧���� ������
		private Vector<Register> registers;
		private Status status;
		public PCB() {
			registers = new Vector<Register>();
			for(ERegister eRegister: ERegister.values()) {
				this.registers.add(new Register());	//�޸� �����
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
			//����Ʈ ������ ��ŭ �Ҵ�
			this.memory = new int[size];		
		}
		public Segment(int[] memory) {	
			this.memory = memory;		
		}
		
		public int getSize() {return memory.length;}
	//�ڵ弼�׸�Ʈ�� ���Դ��� �����Ͱ� ���Դ��� �� ����� ���� ����.
		public void store(int address, int data) {	//������ �ִ� �뵵
			this.memory[address] = data;
		}
		public int fetch(int address) {
			return this.memory[address];
		}
	}
	
	//////////////////////////////////////////////////���׸�Ʈ
	
}
