package Device;

public class IODevice {
	private enum EIODeviceState {eIdle, eRunning, eTerminated, eError;}

		private String deviceName; // IO ���� ����
		private EIODeviceState eIODeviceState;

		public IODevice() {
			this.eIODeviceState = EIODeviceState.eIdle;
		}
}
