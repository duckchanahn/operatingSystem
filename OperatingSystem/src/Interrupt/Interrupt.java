package Interrupt;

import Interrupt.InterruptHandler.EInterrupt;

public class Interrupt {
	
//	public EState getState() {return eState;}
//	public void setState(EState eState) {this.eState = eState;}
	
    public Interrupt() { // 구조체 만들어서 공유해서 쓸거래. 이건 원래 register. 우린 vm형식으로 구조체로 만듦.
       this.eType = EInterrupt.eNone;
       this.parameter = 0;
       this.PID = 0;
       this.interruptID = 0;
    }

    private EInterrupt eType;
    private int parameter;
    private int PID;
	private int interruptID;
    public EInterrupt geteType() {return eType;}
    public void seteType(EInterrupt eType) {this.eType = eType;}
    public int getParameter() {return parameter;}
    public void setParameter(int parameter) {this.parameter = parameter;}
    public int getPID() {return PID;}
    public void setPID(int PID) {this.PID = PID;}
    public int getinterruptID() {return interruptID;}
    public void setinterruptID(int interruptID) {this.interruptID = interruptID;}
 }
