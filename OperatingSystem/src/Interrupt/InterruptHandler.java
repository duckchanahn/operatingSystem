package Interrupt;

import java.util.Vector;

import Device.IODevice;
import Device.Monitor;
import Device.Monitor.EDevice;
import Device.Monitor.IOController;
import ProcessManager.Dispatcher;

public class InterruptHandler {

   public enum EInterrupt {eNone, eProcessStart, eProcessTerminated, eTimerOut, eIOStarted, eIOFinished;}
//   public enum EInterrupt {eNone, eProcessStart, eProcessTerminated, eTimerOut, ePRT, eRead, eWrite, eIOFinished;}
   
   private Dispatcher dispatcher;
   private IOController ioController;

//   private Vector<Interrupt> interrupts;
   
   private Interrupt[] interrupts; // 서큘러 큐
   private int front; // 앞. 쓸 때 앞에걸 쓴대
   private int rear; // 뒤. 넣을때 뒤에 넣고
   private int length;
   private int maxLength;
   
   private Clock clock;

	public InterruptHandler() {
		this.maxLength = 10;
		this.front = 0;
		this.rear = 0;
		this.length = 0;
		this.interrupts = new Interrupt[this.maxLength];
		this.ioDevices = new Vector<IODevice>();
//		this.ioController = new IOController();
	}
   
   private Monitor monitor;
   public void associate(Dispatcher dispatcher, Monitor monitor) {
	   this.dispatcher = dispatcher;
	   this.monitor = monitor;
   }
   
   private Vector<IODevice> ioDevices;
      
   public void addInterrupt(Interrupt interrupt) {
	      this.interrupts[this.rear] = interrupt;
	      this.length++;
	      this.rear = (this.rear + 1) % this.maxLength;
   }
   
   public void association(IODevice ioDevice) {
	   this.ioDevices.add(ioDevice);
   }

   public void process() {
	   
	   if (length > 0) {
      switch (this.interrupts[front].geteType()) {
//      case eProcessStart:
//    	  break;
      case eProcessTerminated:
    	  //큐에서 지우고 메모리매니지먼트 지우고
    	  System.out.println("");
    	  System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
    	  System.out.println("Terminated 발생");
    	  this.clock.cancel();
    	  this.dispatcher.contextSwitching(this.interrupts[front].geteType(), this.interrupts[front].getPID());
    	  System.out.println("vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv");
    	  System.out.println("");
    	  this.resetTimer();
    	  break;
      case eTimerOut:
    	  System.out.println("");
    	  System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
    	  System.out.println("TimeOut 발생");
    	  this.dispatcher.contextSwitching(this.interrupts[front].geteType(), this.interrupts[front].getPID());
    	  System.out.println("vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv");
    	  System.out.println("");
    	  this.resetTimer();
         break;
      case eIOStarted:
    	  // 레디 -> 웨잇 
    	  System.out.println("");
    	  System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
    	  this.clock.cancel();
    	  this.IOStarted(this.interrupts[front]);
//    	  System.out.println("인터럽트 발생");
    	  this.dispatcher.contextSwitching(this.interrupts[front].geteType(), this.interrupts[front].getPID());
    	  System.out.println("vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv");
    	  this.resetTimer();
         break;
      case eIOFinished:
    	  System.out.println("");
    	  System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
         // 웨잇 -> 레디
    	  System.out.println("인터럽트 종료");
    	  this.dispatcher.contextSwitching(this.interrupts[front].geteType(), this.interrupts[front].getPID());
    	  System.out.println("vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv");
    	  System.out.println("");
    	 break;
      default:
         break;
      }
       this.interrupts[front].seteType(EInterrupt.eNone);
      this.front = (this.front + 1) % this.maxLength;
      this.length--;
   }	   
   }
   
	public void finish() {
		try {
			if (this.monitor.getState() == EDevice.eFinished) {
				this.addInterrupt(this.monitor.getInterrupt()); 
			} 
		} catch (NullPointerException e) {}
	}
	
	
	   public void resetTimer() {
		      if (clock != null) {
		         clock.timer.cancel();
		         System.out.println("타이머 리셋!");
		      }
		      if(this.dispatcher.currentProcess() != null) {
		    	  clock = new Clock();
		      }
		   }
	   
   public void checkTime() {
	   if(clock.checkTime()) {
		   Interrupt interrupt = new Interrupt();
		   interrupt.seteType(EInterrupt.eTimerOut);
		   interrupt.setPID(this.dispatcher.currentProcess().getPcb().getId());
		   this.addInterrupt(interrupt);
		   this.clock.cancel();
	   }
	}
   
   private enum EIOStarted {PRT, READ, WRITE};
   public void IOStarted(Interrupt interrupt) {
	   Interrupt temp= new Interrupt();
	   temp.seteType(interrupt.geteType());
	   temp.setinterruptID(interrupt.getinterruptID());
	   temp.setParameter(interrupt.getParameter());
	   temp.setPID(interrupt.getPID());
//	   System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
	   switch(EIOStarted.values()[temp.getinterruptID()]) {
	   case PRT:
		   System.out.println("Print Interrupt 발생");
		   this.monitor.setInterrupt(temp);
		   this.monitor.run();
		   break;
	   case READ:
		   System.out.println("Read Interrupt 발생");
		   break;
	   case WRITE:
		   System.out.println("Write Interrupt 발생");
		   break;
	   }
   }
}