package Device;

import Interrupt.Interrupt;
import Interrupt.InterruptHandler.EInterrupt;

public class Monitor {
	
	public enum EDevice {eNone, eRunning, eFinished}
	public EDevice eDevice;
	public void setState(EDevice eDevice) {this.eDevice = eDevice;}
	public EDevice getState() {return eDevice;}
	
	private Interrupt interrupt;
	public Interrupt getInterrupt() {
		Interrupt temp = new Interrupt();
		if(this.eDevice == EDevice.eFinished) {
			temp.seteType(EInterrupt.eIOFinished);
		} else {
			temp.seteType(EInterrupt.eNone);
		}
		temp.setinterruptID(interrupt.getinterruptID());
		temp.setParameter(interrupt.getParameter());
		temp.setPID(interrupt.getPID());
		
		return this.interrupt;
	}
	public void setInterrupt(Interrupt interrupt) {
		this.interrupt.setinterruptID(interrupt.getinterruptID());
		this.interrupt.setParameter(interrupt.getParameter());
		this.interrupt.setPID(interrupt.getPID());
	}

	private HCI HCI; //MainFrame, 화면을 여기서 만들기
//	private IOController ioController;
//	public IOController getController() {return ioController;}
	
	public Monitor() {
		this.eDevice = EDevice.eNone;
		this.interrupt = new Interrupt();
		this.interrupt.seteType(EInterrupt.eNone);
//		this.HCI = new HCI();
//		this.HCI.run();
//		this.HCI.setVisible(true);
	}
	
	public void initialize() {
		
	}
	
	public void pointer(int x, int y) {
		//마우스위치 실시간으로 받아오기
		this.HCI.mouse(x, y);
	}
	
	public void run() {
		System.out.println("Monitor : " + this.interrupt.getParameter());
		System.out.println("");
		this.interrupt.seteType(EInterrupt.eIOFinished);
		this.eDevice = EDevice.eFinished;
//		System.out.println(this.ioController.getInterrupt().getParameter());
//		this.ioController.eState = EState.
	}
	
	public class IOController {
		
//		private enum EDevice {eNone, eRunning, eFinished}
//		public EDevice eState;
//		
//		private Interrupt interrupt;
//		public Interrupt getInterrupt() {return this.interrupt;}
//		
//		public IOController() {
//			this.eState = EState.eNone;
//		}
//		
//		public void setInterrupt(Interrupt interrupt) {
//			this.interrupt = interrupt;
//		}
//		
//		public EState finish() {return this.eState;}
	}
}
