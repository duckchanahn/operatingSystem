package os;

import Device.Keyboard; 
import Device.Monitor;
import Device.Mouse;

public class Main {
	
	public static Monitor monitor;
	public static Keyboard keyboard;
	public static Mouse mouse;
	
	public static class BIOS {
		OperatingSystem operatingSystem;

//		Memory memory;
//		CPU cpu;
		
		public BIOS() {
			operatingSystem = new OperatingSystem();
			// 모니터 키보드 마우스는 Device 하나로 뭉치기
			// BIOS랑은 개별로 BIOS는 OS + Device
			monitor = new Monitor();
			keyboard = new Keyboard();
			mouse = new Mouse();
		}
		
		public void run() {
			monitor.initialize();
			keyboard.initialize(monitor);
			mouse.initialize(monitor);
			
			operatingSystem.initialize();
			operatingSystem.associate(monitor);
			operatingSystem.run();
			operatingSystem.finalize();
		}
	}
	
	public static void main(String[] args) {
		BIOS bios = new BIOS();
		bios.run();
	}
}
