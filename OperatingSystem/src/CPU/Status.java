package CPU;

public class Status {
	
	private int statusFlag;
	// zero, sign, int
	// 4,    2,    1
	
	public Status() {
		this.statusFlag = 0;
	}
	
	public boolean interruptCheck() {
		int temp = statusFlag & 1;
		if(temp == 1) {
			return true;
		}
		return false;
	}
	
	public boolean zeroCheck() {
		int temp = statusFlag & 4;
		if(temp == 4) {
			return true;
		}
		return false;
	}
	
	public boolean signCheck() {
		int temp = statusFlag & 2;
		if(temp == 2) {
			return true;
		}
		return false;
	}
	
	public void setStatus(int flag) {
		// Status flag : zero(4), sign(2), int(1)
		if(flag == 4) {
			int temp = statusFlag & 2; 
			if(temp == 2) this.statusFlag = statusFlag - 2;
		} else if(flag == 2) {
			int temp = statusFlag & 4;
			if(temp == 4) this.statusFlag = statusFlag - 4;
		}
		int temp = statusFlag & flag;
		if(temp == 0) {
			this.statusFlag = statusFlag + flag;
		}
	}

	public void deleteInterrupt() {
		this.statusFlag = this.statusFlag - 1;
	}
}