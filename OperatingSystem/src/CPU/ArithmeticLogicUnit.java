package CPU;

public class ArithmeticLogicUnit {

	private Register ac, mbr;
	public void setAC(Register ac) {this.ac = ac;}
	private Status status;
	public void setStatus(Status status) {this.status = status;}
	public Status getStatus() {return status;}
	
	public void connect(Register ac, Register mbr, Status status) {
		this.ac = ac;
		this.mbr = mbr;
		this.status = status;
	}

	public void add() {
		this.ac.setData(this.mbr.getData() + this.ac.getData());		
	}

	public void and(int data) {
		if(ac.getData() == data) {
			this.ac.setData(1);
		} else {
			this.ac.setData(0);
		}
	}
	
	public void cmp() {
		if((this.mbr.getData() - this.ac.getData()) == 0) {
			this.status.setStatus(4);
		} else if((this.mbr.getData() - this.ac.getData()) < 0) {
			this.status.setStatus(2);
		}
	}

}
