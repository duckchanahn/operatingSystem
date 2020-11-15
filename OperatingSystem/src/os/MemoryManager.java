package os;

import java.util.Vector;

import javax.swing.text.Segment;

import CPU.CentrolProcessingUnit;
import CPU.Register;
import CPU.Status;
import Interrupt.InterruptHandler;
import Memory.Memory;
import ProcessManager.Process;

public class MemoryManager {
	
	private Memory memory;
	public Memory getMemory() {return this.memory;}
	private IODevice ioDevice;
	public IODevice getIODevice() {return ioDevice;}
	private Register relocation;
	private Register limit;
	
	private int pageSize;
//	private Vector<Boolean> pageTable; //relocation
	private boolean[] pageTable; //relocation
	private Vector<Integer> processPageTable; 
	private int PID;
	
	public MemoryManager() {
		this.memory = new Memory();
		this.devices = new Vector<IODevice>();
		this.ioDevice = new IODevice();
		this.pageSize = 100;
//		this.pageTable = new Vector<Boolean>();
		this.pageTable = new boolean[] {false, false, false, false, false, false, false, false, false, false};
		this.relocation = new Register();
		this.limit = new Register();
		this.PID = 0;
	}
 
	// allocate memory and set PCB info
	public Process allocate(Process process) {
		// 원래는 사이즈고 뭐고 다 PCB에 있어야 하는거래. PCB 다 세팅 하고 다시 리턴해줄 것. 여기서!
		// 메모리할당
		
		// PCB 셋팅
//		boolean inPage = true;
//		int pageIndex = 0;
//		while(inPage) {
//			if(!this.pageTable.elementAt(pageIndex)) {
//				this.pageTable.set(pageIndex, true);
//				process.getPcb().setId(pageIndex + 1); //PID 설정
//				System.out.println(pageIndex + 1);
//				inPage = false;
//			}	
//		}
		for(int i = 0; i < this.pageTable.length; i++) {
			if(!this.pageTable[i]) {
				process.getPcb().setId(i + 1);
				System.out.println(process.getPcb().getId() + "아이디");
				break;
			}
		}
		this.pageTable[process.getPcb().getId()-1] = true;
				
		//////////////////////////////////////페이지테이블 할당안했음
//		this.pageTable.add(process.getPcb().getId(), (process.getPcb().getId()-1) * pageSize); 
//		if((process.getCodeSegment().length + process.getStackSegment().length) > 100) {
//			int codeIndex = 0;
//			for(int i = 0; i <= (process.getCodeSegment().length + process.getStackSegment().length)/100; i++) { // 몇페이지인지 구분
////				his.memory.getBuffer()[i*1000 + this.relocation.getData() + k] = process.getCodeSegment()[codeIndex]; // i*1000 앞자리 
////				for(int i = 0; i <= (process.getCodeSegment().length + process.getStackSegment().length)/100; i++) { // 몇페이지인지 구분
////				this.memory.getBuffer()[i*1000 + this.relocation.getData() + k] = 0;
//				this.pageTable.add(process.getPcb().getId() * 1000, (process.getPcb().getId()-1) * pageSize);
//			}
//		}
//		if((process.getCodeSegment().length + process.getStackSegment().length) > 100) {
//			for(int i = 0; i <process.getCodeSegment().length/100; i++) {
//				this.pageTable.add(process.getPcb().getId(), (process.getPcb().getId()-1) * pageSize);
//			}
//		}
//		System.out.println(process.getPcb().getId());
		this.relocation.setData((process.getPcb().getId()-1) * pageSize);
		this.limit.setData(pageSize);
//		this. 
		// 페이지테이블 만들기ㅣㅣㅣㅣㅣㅣㅣㅣㅣㅣㅣ
		
		process.getPcb().getRegisters().get(Process.ERegister.ePC.ordinal()).setData(0); // PC 셋팅
		process.getPcb().getRegisters().get(Process.ERegister.eSP.ordinal()).setData(this.relocation.getData() + process.getCodeSegment().length); // SP 셋팅
		process.getPcb().getRegisters().get(Process.ERegister.eCS.ordinal()).setData(this.relocation.getData()); // CS 셋팅
		process.getPcb().setStatus(new Status()); // Status 초기화

//		if((process.getCodeSegment().length + process.getStackSegment().length) > 100) {
//			int codeIndex = 0;
//			for(int i = 0; i <= (process.getCodeSegment().length + process.getStackSegment().length)/100; i++) { // 몇페이지인지 구분
//				for(int k = 0; k < this.pageSize; k++) { // 쪽수 구분
//					if(codeIndex >= process.getCodeSegment().length) break;
//					this.memory.getBuffer()[i*1000 + this.relocation.getData() + k] = process.getCodeSegment()[codeIndex]; // i*1000 앞자리 수, k * process.getPcb().getId() 나머지 수 더해서 절대주소 계산
//					codeIndex++;
//				}
//			}
//		} else {
//			for(int i = 0; i < this.pageSize; i++) {
//				this.memory.getBuffer()[this.relocation.getData() + i] = 0;
//			}
//		}
		
		int base = relocation.getData();
		for(int i = 0; i < process.getCodeSegment().length; i++) {
			this.memory.getBuffer()[base++] = process.getCodeSegment()[i];
		}
		
		return process;
	}
	
	public void terminated(int PID) {
//		this.relocation.setData((process.getPcb().getId()-1) * this.pageSize);
//		if((process.getCodeSegment().length + process.getStackSegment().length) > 100) {
//			for(int i = 0; i <= (process.getCodeSegment().length + process.getStackSegment().length)/100; i++) { // 몇페이지인지 구분
//				for(int k = 0; k < this.pageSize; k++) { // 쪽수 구분
//					this.memory.getBuffer()[i*1000 + this.relocation.getData() + k] = 0; // i*1000 앞자리 수, k * process.getPcb().getId() 나머지 수 더해서 절대주소 계산
//				}
//			}
//		} else {
//		System.out.println(PID + "PID");
		this.relocation.setData((PID-1) * pageSize);
			for(int i = this.relocation.getData(); i < this.relocation.getData() + pageSize; i++) {
				this.memory.getBuffer()[i] = 0;
			}
			
//			for(int i = 0; i < 200; i++) {
//				System.out.println(this.memory.getBuffer()[i] + "mmmmmmmmmmmmmmmmmmmmm");
//			}
//		}
			this.pageTable[PID-1] = false;
//			for(int i = 0; i < pageTable.length; i++) System.out.println(pageTable[i]);
	}

	public void associate(CentrolProcessingUnit CPU, InterruptHandler interruptHandler) {
		this.memory.association(interruptHandler);
		CPU.connect(memory);
	}
	
	//IO Controller
	/////////////////////////////////////////////////////////////////////////////////IODEVICE 여기 뭔가 상당히 이상해 ;;
	public enum EIODeviceState {eRunning, eIOFinish, eError;}
	private Vector<IODevice> devices;
	
	public class IODevice { //이건 메모리매니지먼트로 가야할듯
		private String deviceName; // IO 뭔지 구분
		private EIODeviceState eIOState;
		private int PID;
		public void setPID(int PID) {this.PID = PID;}
		
		public void setName(String deviceName) {this.deviceName = deviceName;}

		public IODevice() {
			this.eIOState = EIODeviceState.eRunning;
		}
		
		public EIODeviceState getInterrupt() {return eIOState;}
		public int getPID() {return this.PID;}
	}
	
	public void addDevice(String deviceName) {
		IODevice ioDevice = new IODevice();
		ioDevice.setName(deviceName);
		this.devices.add(ioDevice);
	}
	
	private EIODeviceState eIODeviceState;
	public EIODeviceState getInterrupt() {
		for(IODevice device: devices) {
			eIODeviceState = device.getInterrupt();
			// 인터럽트 null -> 인터럽트 없는 것. ///// 채워넣어야 함. read면 IOStarted.
		}
		return eIODeviceState;
	}
	
	public void IOController() {} //디바이스로 갈 예정
	
//	
	
	/////////////////////////////////////////////////////////////////////////////////

}
